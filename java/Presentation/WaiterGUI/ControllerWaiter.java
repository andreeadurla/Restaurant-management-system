package Presentation.WaiterGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Business.IRestaurantProcessing;
import Business.MenuItem;
import Business.Order;

class ControllerWaiter {
	private ViewWaiter view;
	private IRestaurantProcessing restaurant;
	private int idOrders = 1;

	ControllerWaiter(ViewWaiter view, IRestaurantProcessing restaurant) {
		this.view = view;
		this.restaurant = restaurant;
		view.addAddListener(new AddListener());
		view.addViewListener(new ViewListener());
		view.addBillListener(new BillListener());
		view.addSubmitAddListener(new SubmitAddListener());
		view.addRefreshListener(new AddListener());
		view.addSubmitBillListener(new SubmitBillListener());
	}

	class AddListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			view.setVisiblePanelView(false);
			view.setVisiblePanelBill(false);
			view.setVisiblePanelAdd(true);

			view.resetTable1();
			JTable table = view.getTable1();
			table.clearSelection();
			DefaultTableModel model = (DefaultTableModel) view.getTable1().getModel();

			ArrayList<MenuItem> items = restaurant.viewMenuItems();
			int lengthMax = 0;
			for(MenuItem i: items) {
				if(i.toString().length() > lengthMax)
					lengthMax = i.toString().length();
				model.addRow(new Object[]{i.toString(), String.format("%.2f", i.computePrice())});
			}
			
			if(lengthMax * 7 > 700)
				table.getColumnModel().getColumn(0).setPreferredWidth(lengthMax * 7);
			else
				table.getColumnModel().getColumn(0).setPreferredWidth(700);
			restaurant.setModifiedMenu(false);
		}
	}

	class ViewListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			view.setVisiblePanelAdd(false);
			view.setVisiblePanelBill(false);
			view.setVisiblePanelView(true);
		}
	}

	class BillListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			view.setVisiblePanelView(false);
			view.setVisiblePanelAdd(false);
			view.setVisiblePanelBill(true);
		}
	}

	private void createOrder(ArrayList<MenuItem> items) throws Exception{
		if(view.getTextTable().equals("Table") == true)
			throw new Exception("Trebuie sa introduceti numarul mesei!");
		int nrTable = Integer.parseInt(view.getTextTable());
		if(nrTable <= 0)
			throw new Exception("Numarul mesei nu este valid");

		SimpleDateFormat formatDate = new SimpleDateFormat ("dd.MM.yyyy 'at' HH:mm");
		Order order = new Order(idOrders, formatDate.format(new Date()), nrTable);
		restaurant.addOrder(order, items);

		JTable table2 = view.getTable2();
		DefaultTableModel model2 = (DefaultTableModel) table2.getModel();

		String s = "";
		int tableWidth = table2.getColumnModel().getColumn(3).getPreferredWidth();
		int lengthMax = 0;
		for(MenuItem i: items) {
			if(i.toString().length() > lengthMax)
				lengthMax = i.toString().length();
			s += i.toString() + "\n";
		}

		model2.addRow(new Object[]{order.getOrderId(), order.getDate(), order.getTable(), s, String.format("%.2f", restaurant.computeOrderPrice(order))});
		table2.setRowHeight(idOrders-1, 20 * items.size());
		if(lengthMax * 7 > tableWidth)
			table2.getColumnModel().getColumn(3).setPreferredWidth(lengthMax * 7);

		DefaultTableModel model3 = (DefaultTableModel) view.getTable3().getModel();
		model3.addRow(new Object[] {order.getOrderId(), order.getDate(), order.getTable()});

		idOrders ++;
	}

	class SubmitAddListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			try {
				if(restaurant.isModifiedMenu() == false) {
					JTable table1 = view.getTable1();

					int[] selectedRows = table1.getSelectedRows();

					if(selectedRows.length > 0) {
						ArrayList<MenuItem> items = new ArrayList<MenuItem>();
						for(int i = 0; i < selectedRows.length; i++)
							items.add(restaurant.getMenuItem(selectedRows[i]));

						createOrder(items);
						view.resetTextTable();
						view.setVisiblePanelAdd(false);
					}
					else
						throw new Exception("Trebuie sa selectati produsele");
				}
				else
					throw new Exception("Trebuie sa dati refresh deoarece s-au facut modificari in meniu");
			}
			catch(Exception ex) {
				view.showError(ex.getMessage());
			}
		}
	}

	class SubmitBillListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JTable table = view.getTable3();
			DefaultTableModel model = (DefaultTableModel) table.getModel();

			int selectedRow = table.getSelectedRow();
			if(selectedRow >= 0) {
				int idOrder = (Integer) table.getValueAt(selectedRow, 0);
				String date = (String) table.getValueAt(selectedRow, 1);
				int nrTable = (Integer) table.getValueAt(selectedRow, 2);

				model.removeRow(selectedRow);
				restaurant.generateBill(new Order(idOrder, date, nrTable));
				view.setVisiblePanelBill(false);
			}
			else
				view.showError("Trebuie sa selectati comanda pentru care se va genera factura");
		}
	}
}

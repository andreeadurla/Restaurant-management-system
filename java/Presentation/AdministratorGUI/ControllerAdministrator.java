package Presentation.AdministratorGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Business.BaseProduct;
import Business.CompositeProduct;
import Business.IRestaurantProcessing;
import Business.MenuItem;
import Data.RestaurantSerializator;

class ControllerAdministrator {

	private ViewAdministrator view;
	private IRestaurantProcessing restaurant;
	private int nrItemEdit;

	ControllerAdministrator(ViewAdministrator view, IRestaurantProcessing restaurant) {
		this.view = view;
		this.restaurant = restaurant;
		view.addAddListener(new AddListener());
		view.addEditListener(new EditListener());
		view.addDeleteListener(new DeleteListener());
		view.addViewListener(new ViewListener());
		view.addSubmitEdit1Listener(new Edit1Listener());
		view.addSubmitEdit2Listener(new Edit2Listener());
		view.addSubmitAddListener(new SubmitAddListener());
		view.addSubmitDeleteListener(new SubmitDeleteListener());
		view.addFrameListener(new FrameListener());
	}

	private void printTable() {
		view.resetTable();
		JTable table = view.getTable();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
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
	}

	class AddListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			printTable();
			view.resetTextFieldsAdd();
			view.visibleAdd();
		}
	}

	class SubmitAddListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String name = view.getTextNameA();
			try {
				if(name.equals("Name") == true)
					throw new Exception("Trebuie sa introduceti numele produsului");
				if(view.baseRBIsSelected() == true) {
					double price = 0;
					if(view.getTextPriceA().equals("Price") == true)
						throw new Exception("Trebuie sa introduceti pretul produsului");
					price = Double.parseDouble(view.getTextPriceA());
					if(price <= 0)
						throw new Exception("Pretul trebuie sa fie un numar pozitiv");
					restaurant.addMenuItem(new BaseProduct(name, price));
				}
				else if(view.compositeRBIsSelected() == true) {
					JTable table = view.getTable();
					int[] selectedRows = table.getSelectedRows();
					if(selectedRows.length > 0) {
						CompositeProduct product = new CompositeProduct(name);
						ArrayList<MenuItem> items = restaurant.viewMenuItems();

						for(int i = 0; i < selectedRows.length; i++)
							product.addProduct(items.get(selectedRows[i]));

						restaurant.addMenuItem(product);
					}
					else throw new Exception("Trebuie sa selectati produsele");
				}
				else throw new Exception("Trebuie sa selectati Base Product sau Composite Product");
			} catch(Exception ex) {
				view.showError(ex.getMessage());
				return ;
			}
			view.setVisiblePanel(false);
		}
	}

	class EditListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			printTable();
			view.resetTextFieldsEdit();
			view.visibleEdit1();
		}
	}

	class DeleteListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			printTable();
			view.visibleDelete();
		}
	}

	class ViewListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			printTable();
			view.visibleView();
		}
	}
	
	private void editBaseProduct(MenuItem itemEdit) throws Exception{
		if(view.nameERBIsSelected() == true) {
			String name = view.getTextNameE();
			if(name.equals("Name") == true)
				throw new Exception("Trebuie sa introduceti noul nume al produsului");
			restaurant.editMenuItem(itemEdit, view.getTextNameE());
		}
		else if(view.priceERBIsSelected() == true){
			double price;
			if(view.getTextPriceE().equals("Price") == true)
				throw new Exception("Trebuie sa introduceti noul pret al produsului");
			price = Double.parseDouble(view.getTextPriceE());
			if(price <= 0)
				throw new Exception("Pretul trebuie sa fie un numar pozitiv");
			restaurant.editMenuItem(itemEdit, price);
		}
		else
			throw new Exception("Nu puteti modifica elementele produsului deoarece acesta este un produs de baza");
		view.setVisiblePanel(false);
	}
	
	private void editCompositeProduct(MenuItem itemEdit) throws Exception{
		if(view.nameERBIsSelected() == true) {
			String name = view.getTextNameE();
			if(name.equals("Name") == true)
				throw new Exception("Trebuie sa introduceti noul nume al produsului");
			restaurant.editMenuItem(itemEdit, name);
			view.setVisiblePanel(false);
		}
		else if(view.elemERBIsSelected() == true){
			view.setTextComp(itemEdit.toString());
			view.visibleEdit2();
		}
		else
			throw new Exception("Nu puteti modifica pretul unui produs compus");
	}

	class Edit1Listener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JTable table = view.getTable();
			int[] selectedRows = table.getSelectedRows();

			try {
				if(selectedRows.length > 1 || selectedRows.length == 0)
					throw new Exception("Trebuie sa selectati un produs");

				ArrayList<MenuItem> items = restaurant.viewMenuItems();
				nrItemEdit = selectedRows[0];
				MenuItem itemEdit = items.get(selectedRows[0]);
				if(itemEdit instanceof BaseProduct)
					editBaseProduct(itemEdit);
				else if(itemEdit instanceof CompositeProduct)
					editCompositeProduct(itemEdit);
			} catch(Exception ex) {
				view.showError(ex.getMessage());
				return ;
			}
		}
	}

	class Edit2Listener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JTable table = view.getTable();
			int[] selectedRows = table.getSelectedRows();

			try {
				ArrayList<MenuItem> items = restaurant.viewMenuItems();
				MenuItem itemEdit = items.get(nrItemEdit);
				ArrayList<MenuItem> elements = new ArrayList<MenuItem>();
				if(selectedRows.length > 0) {
					for(int i = 0; i < selectedRows.length; i++) {
						MenuItem element = items.get(selectedRows[i]);
						if(element.equals(itemEdit) == true)
							throw new Exception("Nu selectati produsul pe care il editati");

						elements.add(element);
					}
					restaurant.editMenuItem(itemEdit, elements);
				}
				else
					view.showError("Pentru a schimba componentele produsului trebuie sa selectati noile componente,\n altfel produsul va ramane nemodificat");
			} catch(Exception ex) {
				view.showError(ex.getMessage());
				return;
			}
			view.setVisiblePanel(false);
		}
	}

	class SubmitDeleteListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JTable table = view.getTable();
			int[] selectedRows = table.getSelectedRows();

			try {
				if(selectedRows.length > 1 || selectedRows.length == 0)
					throw new Exception("Trebuie selectat un produs");
				restaurant.deleteMenuItem(selectedRows[0]);

			}catch(Exception ex) {
				view.showError(ex.getMessage());
				return ;
			}
			view.setVisiblePanel(false);
		}
	}

	class FrameListener implements WindowListener {

		public void windowOpened(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {}

		public void windowClosed(WindowEvent e) {
			RestaurantSerializator.serialization(restaurant.viewMenuItems());
		}

		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
	}
}

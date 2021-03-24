package Presentation.WaiterGUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class ViewWaiter extends JFrame{
	
	private JButton addB = new JButton("Add Order");
	private JButton viewB = new JButton("View all Orders");
	private JButton billB = new JButton("Generate Bill");
	private JButton submitAddB = new JButton("Submit");
	private JButton refreshB = new JButton("Refresh");
	private JButton submitBillB = new JButton("Submit");

	private JTextField textTable = new JTextField("Table");

	private JPanel panelButtons;
	private JPanel panelAdd;
	private JPanel panelView;
	private JPanel panelBill;

	private DefaultTableModel model1 = new DefaultTableModel(){
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	
	private DefaultTableModel model2 = new DefaultTableModel(){
		public Class getColumnClass(int columnIndex) {
	        return String.class;
	    }
		
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	
	private DefaultTableModel model3 = new DefaultTableModel(){
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	
	private JTable table1;
	private JTable table2;
	private JTable table3;
	
	ViewWaiter() {
		initUI();
	}

	private void initUI() {
		createMenu();

		this.setTitle("Waiter");
		this.setSize(900, 750);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void createMenu() {

		panelButtons = createPanelButtons();
		panelAdd = createPanelAdd();
		panelView = createPanelView();
		panelBill = createPanelBill();

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = 0; c.gridy = 0;
		panel.add(panelButtons, c);

		c.gridx = 0; c.gridy = 1;
		panel.add(panelAdd, c);
		panel.add(panelView, c);
		panel.add(panelBill, c);

		this.add(panel);
		this.pack();
	}

	private JPanel createPanelButtons() {
		this.styleButton(addB);
		this.styleButton(viewB);
		this.styleButton(billB);

		JPanel panel = new JPanel();
		styleButtonsPanel(panel);
		return panel;
	}

	private void styleButton(JButton button) {
		button.setFont(new Font("Arial", Font.BOLD, 18));
		button.setPreferredSize(new Dimension(250, 30));
	}

	private void styleButtonsPanel(JPanel panel) {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = 0;  c.gridy = 0;
		panel.add(addB, c);
		c.gridx = 1; c.gridy = 0;
		panel.add(viewB, c);
		c.gridx = 0; c.gridy = 1;
		panel.add(billB, c);
	}

	private JPanel createPanelAdd() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		
		c.gridx = 0;  c.gridy = 0;
		model1.addColumn("Product");
		model1.addColumn("Price");
		table1 = new JTable(model1);
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table1.getColumnModel().getColumn(0).setPreferredWidth(700);
		table1.getColumnModel().getColumn(1).setPreferredWidth(80);
		table1.setRowHeight(25);
		table1.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollPane = new JScrollPane(table1);
		scrollPane.setPreferredSize(new Dimension(800, 250));
		panel.add(scrollPane, c);
		
		c.gridx = 0;  c.gridy = 1;
		JTextArea text = new JTextArea("Selectati produsele apasand CTRL + click\nDaca doriti sa deselectati un produs apasati CTRL + click");
		text.setFont(new Font("Arial", Font.PLAIN, 14));
		text.setEditable(false);
		panel.add(text, c);
		
		c.gridx = 0;  c.gridy = 2;
		textTable.setFont(new Font("Arial", Font.PLAIN, 18));
		textTable.setHorizontalAlignment(JTextField.CENTER);
		textTable.setPreferredSize(new Dimension(100, 20));
		panel.add(textTable, c); 
		
		c.gridx = 0;  c.gridy = 3;
		panel.add(refreshB, c);
		
		c.gridx = 0;  c.gridy = 4;
		panel.add(submitAddB, c);
		
		panel.setVisible(false);
		return panel;
	}
	
	private JPanel createPanelView() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		
		c.gridx = 0;  c.gridy = 0;
		model2.addColumn("IdOrder");
		model2.addColumn("Date");
		model2.addColumn("Table");
		model2.addColumn("Products");
		model2.addColumn("Total Price");

		table2 = new JTable(model2);
		table2.setDefaultRenderer(String.class, new MultiLineCellRenderer());
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table2.getColumnModel().getColumn(1).setPreferredWidth(150);
		table2.getColumnModel().getColumn(3).setPreferredWidth(422);
		table2.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollPane = new JScrollPane(table2);
		scrollPane.setPreferredSize(new Dimension(800, 250));
		panel.add(scrollPane, c);
		
		panel.setVisible(false);
		return panel;
	}
	
	private JPanel createPanelBill() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		
		c.gridx = 0;  c.gridy = 0;
		model3.addColumn("IdOrder");
		model3.addColumn("Date");
		model3.addColumn("Table");
		table3 = new JTable(model3);
		table3.setPreferredScrollableViewportSize(new Dimension(500, 200));
		table3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table3.getColumnModel().getColumn(1).setPreferredWidth(200);
		table3.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollPane = new JScrollPane(table3);
		panel.add(scrollPane);
		
		c.gridx = 0;  c.gridy = 1;
		JTextArea text = new JTextArea("Apasati click pe comanda pentru care doriti sa generati factura\nDaca doriti sa deselectati o comanda apasati CTRL + click");
		text.setFont(new Font("Arial", Font.PLAIN, 14));
		text.setEditable(false);
		panel.add(text, c);
		
		c.gridx = 0;  c.gridy = 2;
		panel.add(submitBillB, c);
		
		panel.setVisible(false);
		return panel;
	}

	JTable getTable1() {
		return table1;
	}
	
	JTable getTable2() {
		return table2;
	}

	JTable getTable3() {
		return table3;
	}
	
	void setVisiblePanelAdd(boolean flag) {
		panelAdd.setVisible(flag);
	}

	void setVisiblePanelView(boolean flag) {
		panelView.setVisible(flag);
	}
	
	void setVisiblePanelBill(boolean flag) {
		panelBill.setVisible(flag);
	}
	
	String getTextTable() {
		return textTable.getText();
	}

	void resetTable1() {
		for(int i = model1.getRowCount() - 1; i >= 0; i--)
			model1.removeRow(i);
	}
	
	void resetTextTable() {
		textTable.setText("Table");
	}
	
	void showError(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	void addAddListener(ActionListener a) {
		addB.addActionListener(a);
	}

	void addViewListener(ActionListener a) {
		viewB.addActionListener(a);
	}

	void addBillListener(ActionListener a) {
		billB.addActionListener(a);
	}

	void addSubmitAddListener(ActionListener a) {
		submitAddB.addActionListener(a);
	}
	
	void addRefreshListener(ActionListener a) {
		refreshB.addActionListener(a);
	}
	
	void addSubmitBillListener(ActionListener a) {
		submitBillB.addActionListener(a);
	}
}

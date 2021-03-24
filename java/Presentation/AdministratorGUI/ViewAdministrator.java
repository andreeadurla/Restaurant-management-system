package Presentation.AdministratorGUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class ViewAdministrator extends JFrame {

	private JButton addB = new JButton("Add Menu Item");
	private JButton editB = new JButton("Edit Menu Item");
	private JButton deleteB = new JButton("Delete Menu Item");
	private JButton viewB = new JButton("View all Menu Items");
	private JButton submitAddB = new JButton("Submit");
	private JButton submitDeleteB = new JButton("Submit");
	private JButton submitEditB1 = new JButton("Submit");
	private JButton submitEditB2 = new JButton("Submit");
	
	private JPanel panelButtons = new JPanel();
	private JPanel panelAdd1 = new JPanel();
	private JPanel panelAdd2 = new JPanel();
	private JPanel panelEdit1 = new JPanel();
	private JPanel panelEdit2 = new JPanel();
	private JPanel panelDelete = new JPanel();
	private JPanel panelTable = new JPanel();
	private JPanel panelOperation = new JPanel();

	private DefaultTableModel model = new DefaultTableModel() {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private JTable table;
	
	private JRadioButton baseRB = new JRadioButton("Base Product");
	private JRadioButton compositeRB = new JRadioButton("Composite Product");
	private JTextField textName = new JTextField("Name");
	private JTextField textPrice = new JTextField("Price");
	
	private JRadioButton nameERB = new JRadioButton("Modify name");
	private JRadioButton priceERB = new JRadioButton("Modify price");
	private JRadioButton elemERB = new JRadioButton("Modify elements");
	private JTextField textNameE = new JTextField("Name");
	private JTextField textPriceE = new JTextField("Price");
	
	private JTextArea textCompProduct = new JTextArea();
	private JTextArea textLabel = new JTextArea();
	
	ViewAdministrator() {
		initUI();
	}

	private void initUI() {
		createMenu();

		this.setTitle("Administrator");
		this.setSize(900, 750);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void createMenu() {
		panelButtons = createPanelButtons();
		panelOperation = createPanel();

		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = 0; c.gridy = 0;
		panel.add(panelButtons, c);

		c.gridx = 0; c.gridy = 1;
		panel.add(panelOperation, c);

		this.add(panel);
		this.pack();
	}

	private JPanel createPanelButtons() {
		this.styleButton(addB);
		this.styleButton(editB);
		this.styleButton(deleteB);
		this.styleButton(viewB);

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
		panel.add(editB, c);
		c.gridx = 0; c.gridy = 1;
		panel.add(deleteB, c);
		c.gridx = 1; c.gridy = 1;
		panel.add(viewB, c);
	}
	
	private void styleTextField(JTextField text, int size) {
		text.setFont(new Font("Arial", Font.PLAIN, 18));
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setPreferredSize(new Dimension(size, 30));
	}
	
	private JPanel createPanelAdd1() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);

		c.gridx = 0;  c.gridy = 0;
		styleTextField(textName, 100);
		panel.add(textName, c);
		
		c.gridx = 0;  c.gridy = 1;
		panel.add(baseRB, c);
		
		c.gridx = 0;  c.gridy = 2;
		styleTextField(textPrice, 50);
		panel.add(textPrice, c);
		
		c.gridx = 0;  c.gridy = 3;
		panel.add(compositeRB, c);
		
		ButtonGroup group = new ButtonGroup(); 
		group.add(baseRB);
		group.add(compositeRB);
		
		return panel;
	}
	
	private JPanel createPanelAdd2() {
		JPanel panel = new JPanel();
		panel.add(submitAddB);
		return panel;
	}
	
	private JPanel createPanelEdit1() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);
		
		c.gridx = 0;  c.gridy = 0;
		panel.add(nameERB, c);
		
		c.gridx = 0;  c.gridy = 1;
		styleTextField(textNameE, 100);
		panel.add(textNameE, c);
		
		c.gridx = 0;  c.gridy = 2;
		panel.add(priceERB, c);
		
		c.gridx = 0;  c.gridy = 3;
		styleTextField(textPriceE, 50);
		panel.add(textPriceE, c);
		
		c.gridx = 0;  c.gridy = 4;
		panel.add(elemERB, c);
		
		c.gridx = 0;  c.gridy = 5;
		panel.add(submitEditB1, c);
		
		ButtonGroup group = new ButtonGroup(); 
		group.add(nameERB);
		group.add(priceERB);
		group.add(elemERB);
		
		return panel;
	}
	
	private JPanel createPanelEdit2() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);
		
		c.gridx = 0;  c.gridy = 0;
		panel.add(new JLabel("Produsul care va fi editat: vechile sale componente"));
		
		c.gridx = 0;  c.gridy = 1;
		textCompProduct.setFont(new Font("Arial", Font.PLAIN, 14));
		textCompProduct.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textCompProduct);
		scrollPane.setPreferredSize(new Dimension(600, 40));
		panel.add(scrollPane, c);
		
		c.gridx = 0;  c.gridy = 2;
		panel.add(submitEditB2, c);
		
		return panel;
	}
	
	private JPanel createPanelDelete() {
		JPanel panel = new JPanel();
		panel.add(submitDeleteB);
		return panel;
	}
	
	private JPanel createPanelTable() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);
		
		c.gridx = 0;  c.gridy = 0;
		model.addColumn("Product");
		model.addColumn("Price");
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.getColumnModel().getColumn(0).setPreferredWidth(700);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.setRowHeight(25);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(800, 250));
		panel.add(scrollPane, c);
		
		c.gridx = 0;  c.gridy = 1;
		textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		textLabel.setEditable(false);
		panel.add(textLabel, c);
		return panel;
	}
	
	private JPanel createPanel() {
		panelAdd1 = createPanelAdd1();
		panelAdd2 = createPanelAdd2();
		panelEdit1 = createPanelEdit1();
		panelEdit2 = createPanelEdit2();
		panelDelete = createPanelDelete();
		panelTable = createPanelTable();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);
		
		c.gridx = 0;  c.gridy = 0;
		panel.add(panelAdd1, c);
		
		c.gridx = 0;  c.gridy = 1;
		panel.add(panelTable, c);
		
		c.gridx = 0;  c.gridy = 2;
		panel.add(panelAdd2, c);
		panel.add(panelEdit1, c);
		panel.add(panelEdit2, c);
		panel.add(panelDelete, c);
		
		panel.setVisible(false);
		return panel;
	}
	
	JTable getTable() {
		return table;
	}
	
	void resetTable() {
		for(int i = model.getRowCount() - 1; i >= 0; i--)
			model.removeRow(i);
	}
	
	boolean baseRBIsSelected() {
		return baseRB.isSelected();
	}
	
	boolean compositeRBIsSelected() {
		return compositeRB.isSelected();
	}
	
	boolean nameERBIsSelected() {
		return nameERB.isSelected();
	}
	
	boolean priceERBIsSelected() {
		return priceERB.isSelected();
	}
	
	boolean elemERBIsSelected() {
		return elemERB.isSelected();
	}
	
	void unselectRows() {
		table.clearSelection();
	}
	
	void invisibleComponents() {
		panelOperation.setVisible(false);
		panelAdd1.setVisible(false);
		panelTable.setVisible(false);
		panelAdd2.setVisible(false);
		panelEdit1.setVisible(false);
		panelEdit2.setVisible(false);
		panelDelete.setVisible(false);
	}
	
	void visibleAdd() {
		textLabel.setText("Selectati produsele care compun CompositeProduct apasand CTRL + click\nDaca doriti sa deselectati un produs apasati CTRL + click");
		invisibleComponents();
		panelAdd1.setVisible(true);
		panelTable.setVisible(true);
		panelAdd2.setVisible(true);
		panelOperation.setVisible(true);
		unselectRows();
	}
	
	void visibleEdit1() {
		textLabel.setText("Apasati click pe produsul pe care doriti sa il editati\nDaca doriti sa deselectati un produs apasati CTRL + click");
		invisibleComponents();
		panelTable.setVisible(true);
		panelEdit1.setVisible(true);
		panelOperation.setVisible(true);
		unselectRows();
	}
	
	void visibleEdit2() {
		textLabel.setText("Selectati noile componente ale produsului apasand CTRL + click\nDaca doriti sa deselectati un produs apasati CTRL + click");
		invisibleComponents();
		panelTable.setVisible(true);
		panelEdit2.setVisible(true);
		panelOperation.setVisible(true);
		unselectRows();
	}
	
	void visibleDelete() {
		textLabel.setText("Apasati click pe produsul pe care doriti sa il stergeti\nDaca doriti sa deselectati un produs apasati CTRL + click");
		invisibleComponents();
		panelTable.setVisible(true);
		panelDelete.setVisible(true);
		panelOperation.setVisible(true);
		unselectRows();
	}
	
	void visibleView() {
		textLabel.setText("");
		invisibleComponents();
		panelTable.setVisible(true);
		panelOperation.setVisible(true);
		unselectRows();
	}

	void setVisibleSubmitB(boolean flag) {
		submitDeleteB.setVisible(flag);
	}
	
	void setVisiblePanel(boolean flag) {
		panelOperation.setVisible(flag);
	}

	String getTextNameA() {
		return textName.getText();
	}
	
	String getTextPriceA() {
		return textPrice.getText();
	}

	String getTextNameE() {
		return textNameE.getText();
	}
	
	String getTextPriceE() {
		return textPriceE.getText();
	}
	
	void setTextComp(String message) {
		textCompProduct.setText(message);
	}
	
	void resetTextFieldsAdd() {
		textName.setText("Name");
		textPrice.setText("Price");
	}
	
	void resetTextFieldsEdit() {
		textNameE.setText("Name");
		textPriceE.setText("Price");
	}
	
	void showError(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	void addAddListener(ActionListener a) {
		addB.addActionListener(a);
	}

	void addEditListener(ActionListener a) {
		editB.addActionListener(a);
	}

	void addDeleteListener(ActionListener a) {
		deleteB.addActionListener(a);
	}

	void addViewListener(ActionListener a) {
		viewB.addActionListener(a);
	}

	void addSubmitAddListener(ActionListener a) {
		submitAddB.addActionListener(a);
	}
	
	void addSubmitEdit1Listener(ActionListener a) {
		submitEditB1.addActionListener(a);
	}
	
	void addSubmitEdit2Listener(ActionListener a) {
		submitEditB2.addActionListener(a);
	}
	
	void addSubmitDeleteListener(ActionListener a) {
		submitDeleteB.addActionListener(a);
	}

	void addFrameListener(WindowListener w) {
		this.addWindowListener(w);
	}
}

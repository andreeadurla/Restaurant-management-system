package Presentation.ChefGUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("deprecation")
public class ChefGUI extends JFrame implements Observer{
	
	private JTextArea text = new JTextArea();
	
	public ChefGUI() {
		initUI();
		this.setVisible(true);
	}
	
	private void initUI() {
		createMenu();

		this.setTitle("Chef");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void createMenu() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		
		c.gridx = 0; c.gridy = 0;
		panel.add(new JLabel("Products to be prepared: "), c);

		c.gridx = 0; c.gridy = 1;
		text.setFont(new Font("Arial", Font.PLAIN, 15));
		text.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(text);
		scrollPane.setPreferredSize(new Dimension(600, 400));
		panel.add(scrollPane, c);

		this.add(panel);
		this.pack();
	}
	
	public void update(Observable o, Object arg) {
		text.append((String) arg);
	}
}

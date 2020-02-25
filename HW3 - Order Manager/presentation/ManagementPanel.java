package presentation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class ManagementPanel extends JPanel{

	private ProductsPanel products = new ProductsPanel();
	private CustomerPanel customers = new CustomerPanel();
	private OrdersManagementPanel orders = new OrdersManagementPanel();
	public ManagementPanel(){
		this.setPreferredSize(new Dimension(1050, 430));
		this.setLayout(null);
		
		products.setBounds(0, 0, 350, 430);
		customers.setBounds(350, 0, 350, 430);
		orders.setBounds(700, 0, 3500, 430);
		this.add(products);
		this.add(customers);
		this.add(orders);
		
	}
}

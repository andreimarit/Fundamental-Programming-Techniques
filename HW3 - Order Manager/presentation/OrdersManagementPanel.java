package presentation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import businessLayer.Customer;
import businessLayer.CustomerCollection;
import businessLayer.Order;
import businessLayer.Product;
import businessLayer.Warehouse;
import dataAccessLayer.XMLPrinter;


public class OrdersManagementPanel extends JPanel{
	
	
	private JList customers;
	private JList products;
	private ArrayList<Customer> customerList;
	private ArrayList<Product> productList;
	private String[] customerArray;
	private String[] productArray;
	private DefaultListModel customerListModel = new DefaultListModel();
	private DefaultListModel productListModel = new DefaultListModel();
	private Warehouse warehouse;
	private CustomerCollection collection;
	
	public OrdersManagementPanel(){
		this.setLayout(null);
		this.setPreferredSize(new Dimension(350, 430));
		warehouse = Warehouse.getInstance();
		collection = CustomerCollection.getInstance();
		
		customerList = collection.getCustomersList();
		productList = warehouse.getProductsList();
		
		
		updateLists();
		customers = new JList(customerListModel);
		products = new JList(productListModel);
		customers.setBounds(0, 0, 150, 350);
		customers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		products.setBounds(190, 0, 150, 350);
		products.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane customersSP = new JScrollPane(customers);
		customersSP.setBounds(0, 30, 150, 350);
		JScrollPane productsSP = new JScrollPane(products);
		productsSP.setBounds(190, 30, 150, 350);
		
		JLabel lbl = new JLabel("Order Management");
		lbl.setBounds(15, 0, 150, 30);
		
		JButton addOrder = new JButton("Place Order");
		addOrder.setBounds(0, 400, 150, 25);
		addOrder.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int customerIndex = customers.getSelectedIndex();
				int[] productIndices = products.getSelectedIndices();
				Order o = new Order();
				for(int i: productIndices){
					o.getProductList().add(productList.get(i));
				}
				o.setCustomer(customerList.get(customerIndex));
				customerList.get(customerIndex).getOrders().add(o);
				
				XMLPrinter xmlPrinter = new XMLPrinter(customerList, 2);
				xmlPrinter.printXML();
				
				customerList = collection.getCustomersList();
				updateLists();
			}
		});
		
		this.add(customersSP);
		this.add(productsSP);
		this.add(lbl);
		this.add(addOrder);
	}
	
	private void updateLists(){
		
		customerListModel.removeAllElements();
		productListModel.removeAllElements();
		for(Customer c: customerList){
			customerListModel.addElement(c.getName());			
		}		
		
		for(Product p: productList){
			productListModel.addElement(p.getName());
			
		}
	}
}

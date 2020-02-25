package businessLayer;
import java.util.ArrayList;

import bst.BinarySearchTree;
import dataAccessLayer.XMLParser;
import dataAccessLayer.XMLPrinter;


public class CustomerCollection {
	private BinarySearchTree<Customer> customers;
	
	private XMLParser xmlParser;
	private String[][] toShow;
	private ArrayList<Customer> customerList; 
	
	private static final CustomerCollection instance = new CustomerCollection();
	
	private CustomerCollection(){
		xmlParser = XMLParser.getInstance();
		xmlParser.parseDocument(0);
		
		customers = xmlParser.getCustomerBST();
		customerList = (ArrayList<Customer>) customers.toList();
	}
	
	public static CustomerCollection getInstance(){
		return instance;
	}
	

	public String[][] getCustomers(){
		toShow = new String[customerList.size()][4];
		int k = 0;
		for(Customer c: customerList){
			toShow[k][0] = c.getName();
			toShow[k][1] = c.getType();
			toShow[k][2] = c.getAddress();
			toShow[k][3] = c.getCCardNumber();
			k++;
		}
		return toShow;
	}
	
	public ArrayList<Customer> getCustomersList(){
		return customerList;
	}
	
	public String[][] addCustomer(Customer c){
		customerList.add(c);
		XMLPrinter printer = new XMLPrinter(customerList, 2);
		printer.printXML();
		
		xmlParser.parseDocument(0);
		customers = xmlParser.getCustomerBST();
		
		customerList = (ArrayList<Customer>)customers.toList();
		return getCustomers();
	}
	
	public String[][] removeCustomer(Customer c){
		
		customerList.remove(c);
		XMLPrinter printer = new XMLPrinter(customerList, 2);
		printer.printXML();
		
		xmlParser.parseDocument(0);
		customers = xmlParser.getCustomerBST();
		
		customerList = (ArrayList<Customer>)customers.toList();
		return getCustomers();
	}
}

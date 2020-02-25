package dataAccessLayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

import bst.BinarySearchTree;
import businessLayer.Customer;
import businessLayer.Order;
import businessLayer.Product;


public class XMLParser extends DefaultHandler{


	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ArrayList<Product> products = new ArrayList<Product>();
	private String tempVal;
	private Customer customer = new Customer();
	private Order order = new Order();
	private Product product = new Product();
	private int who;
	private boolean isOrder = false;
	private int k = 0;
	private static final XMLParser instance = new XMLParser();
	private XMLParser(){
	
	}

	public static XMLParser getInstance(){
		return instance;
	}

	public void parseDocument(int i){
		who = i;
		SAXParserFactory spf = SAXParserFactory.newInstance();

		try{
			SAXParser sp = spf.newSAXParser();
			customers.clear();
			products.clear();
			orders.clear();
			if(who == 0)
				sp.parse("customers.xml", this);
			if(who == 1)
				sp.parse("warehouse.xml", this);
			if(who == 2)
				sp.parse("customers.xml", this);
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		tempVal = "";
		if(who == 0)
			if(qName.equalsIgnoreCase("Customer")) {
				//create a new instance of employee
				customer = new Customer();
				customer.setType(attributes.getValue("type"));
			}
			else{
				if (qName.equalsIgnoreCase("Order")){
					order = new Order();
					isOrder = true;
				}
				else
					if(qName.equalsIgnoreCase("Product")){
						if(!isOrder) throw new SAXException("Tag where it is not supposed to be");
						product = new Product();
						product.setPrice(Integer.valueOf(attributes.getValue("price")));
						product.setIsFragile(Boolean.valueOf(attributes.getValue("isFragile")));
					}
			}

		if(who == 1){
			if(qName.equalsIgnoreCase("Warehouse")){
				products.clear();
			}
			if(qName.equalsIgnoreCase("Product")) {
				//create a new instance of employee
				product = new Product();
				product.setPrice(Integer.valueOf(attributes.getValue("price")));
				if(attributes.getValue("isFragile").equals("1"))
					product.setIsFragile(true);
				else
					product.setIsFragile(false);
			}
		}

		if(who == 2)
			if(qName.equalsIgnoreCase("Customer")) {
				customer = new Customer();
				customer.setType(attributes.getValue("type"));
			}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(who == 0){
			if(qName.equalsIgnoreCase("Customer")) {
				//add it to the list
				System.out.println(customer.getName() + " with type: " + customer.getType() + " address: " + customer.getAddress() + " cCard: " + customer.getCCardNumber());
				for(int i = 0; i < orders.size(); i++){
					System.out.println("On order " + i);
					for(int j = 0; j < orders.get(i).getProductList().size(); j++){
						System.out.println("name: " + orders.get(i).getProductList().get(j).getName() + " price: " + orders.get(i).getProductList().get(j).getPrice() + " isFragile: " + orders.get(i).getProductList().get(j).getIsFragile());
					}
				}

				customer.setOrders((ArrayList<Order>)orders.clone());

				customers.add(customer);
				orders.clear();

			}else if (qName.equalsIgnoreCase("Name")) {
				customer.setName(tempVal);
			}else if (qName.equalsIgnoreCase("Address")) {
				customer.setAddress(tempVal);
			}else if (qName.equalsIgnoreCase("cCardNumber")) {
				customer.setCCardNumber(tempVal);
			}else if(qName.equalsIgnoreCase("Product") && isOrder){
				//System.out.println(tempVal + "  " + k + " price: " + product.getPrice());
				product.setName(tempVal);
				products.add(product);

			}else if(qName.equalsIgnoreCase("Order")){
				isOrder = false;

				order.setProductList((ArrayList<Product>)products.clone());
				order.setCustomer(customer);
				for(int i = 0; i < order.getProductList().size(); i++){
					System.out.println(order.getProductList().get(i).getName());
				}
				orders.add(order);
				products.clear();
				k++;
			}

			tempVal = "";
		}

		if(who == 1){
			if(qName.equalsIgnoreCase("Product")){
				product.setName(tempVal);
				products.add(product);
			}
		}

		if(who == 2){
			if(qName.equalsIgnoreCase("Customer")) {
				//customer.setOrders(null);
				customers.add(customer);


			}else if (qName.equalsIgnoreCase("Name")) {
				customer.setName(tempVal);
			}else if (qName.equalsIgnoreCase("Address")) {
				customer.setAddress(tempVal);
			}else if (qName.equalsIgnoreCase("cCardNumber")) {
				customer.setCCardNumber(tempVal);
			}
		}


	}

	public BinarySearchTree<Customer> getCustomerBST(){
		BinarySearchTree<Customer> bst = new BinarySearchTree<Customer>();
		if(who == 0 || who == 2){
			for(Customer c: customers){
				bst.add(c);
			}
			return bst;
		}
		else
			return null;

	}

	public BinarySearchTree<Product> getProductBST(){
		BinarySearchTree<Product> bst = new BinarySearchTree<Product>();
		if(who == 1){
			for(Product p: products){
				bst.add(p);
			}
			return bst;
		}
		else
			return null;
	}
	
	



	public void printData(){

		System.out.println("No of Customers '" + customers.size() + "'.");

		for(int i = 0; i < customers.size(); i++){
			System.out.println(customers.get(i).getName());
		}
	}


}

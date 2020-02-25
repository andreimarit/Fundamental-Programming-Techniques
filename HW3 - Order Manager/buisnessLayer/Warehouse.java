package businessLayer;
import java.util.ArrayList;

import bst.BinarySearchTree;
import dataAccessLayer.XMLParser;
import dataAccessLayer.XMLPrinter;


public class Warehouse {
	
	private BinarySearchTree<Product> products;
	private XMLParser xmlParser;
	private String[][] toShow;
	private ArrayList<Product> productList;
	private static final Warehouse instance = new Warehouse();
	
	private Warehouse(){
		xmlParser = XMLParser.getInstance();
		xmlParser.parseDocument(1);
		products = xmlParser.getProductBST();
		
		productList = (ArrayList<Product>)products.toList();	
	}
	
	public static Warehouse getInstance(){
		return instance;
	}
	
	public String[][] getProducts(){
		toShow = new String[productList.size()][3];
		int k = 0;
		for(Product p: productList){
			toShow[k][0] = p.getName();
			toShow[k][1] = String.valueOf(p.getPrice());
			toShow[k][2] = String.valueOf(p.getIsFragile());
			k++;
		}
		return toShow;
	}
	
	public ArrayList<Product> getProductsList(){
		return productList;
	}
	
	public String[][] addProduct(Product p){
		productList.add(p);
		XMLPrinter printer = new XMLPrinter(productList, 1);
		printer.printXML();
		
		xmlParser.parseDocument(1);
		products = xmlParser.getProductBST();
		
		productList = (ArrayList<Product>)products.toList();
		return getProducts();
	}
	
	public String[][] removeProduct(Product p){
		
		productList.remove(p);
		XMLPrinter printer = new XMLPrinter(productList, 1);
		printer.printXML();
		
		xmlParser.parseDocument(1);
		products = xmlParser.getProductBST();
		
		productList = (ArrayList<Product>)products.toList();
		return getProducts();
	}
}

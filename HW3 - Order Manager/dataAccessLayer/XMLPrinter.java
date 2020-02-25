package dataAccessLayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import businessLayer.Customer;
import businessLayer.Order;
import businessLayer.Product;

public class XMLPrinter {
	private ArrayList data;
	Document dom;
	int who;

	public XMLPrinter(ArrayList a, int mode){

		data = a;
		who = mode;

		createDocument();
		

	}


	public void printXML(){
		System.out.println("Process started...");
		createDOMTree();
		printToFile();
		System.out.println("Generated file successfully!");
	}



	public void createDocument(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try{

			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();
		}
		catch(ParserConfigurationException pce){
			System.out.println("error " + pce);
			System.exit(1);
		}
	}

	public void createDOMTree(){
		if(who == 1){
			Element rootElem = dom.createElement("Warehouse");
			dom.appendChild(rootElem);

			Iterator i = data.iterator();

			while(i.hasNext()){
				Product p = (Product)i.next();
				Element productElement = createProductElement(p);
				rootElem.appendChild(productElement);
			}
		}
		if(who == 2){
			Element rootElem = dom.createElement("Collection");
			dom.appendChild(rootElem);
			
			Iterator i = data.iterator();
			while(i.hasNext()){
				Customer c = (Customer) i.next();
				Element customerElement = createCustomerElement(c);
				rootElem.appendChild(customerElement);
			}
		}
	}

	public Element createProductElement(Product p){
		Element productElement = dom.createElement("Product");
		productElement.setAttribute("price", String.valueOf(p.getPrice()));
		
		if(p.getIsFragile() == true)
			productElement.setAttribute("isFragile", "1");
		else
			productElement.setAttribute("isFragile", "0");

		Text productText = dom.createTextNode(p.getName());
		productElement.appendChild(productText);

		return productElement;
	}

	public Element createCustomerElement(Customer c){

		Element customerElement = dom.createElement("Customer");
		customerElement.setAttribute("type", c.getType());

		Element nameElement = dom.createElement("Name");
		Text nameText = dom.createTextNode(c.getName());
		nameElement.appendChild(nameText);
		customerElement.appendChild(nameElement);

		Element addressElement = dom.createElement("Address");
		Text addressText = dom.createTextNode(c.getAddress());
		addressElement.appendChild(addressText);
		customerElement.appendChild(addressElement);

		Element cCardElement = dom.createElement("cCardNumber");
		Text cCardText = dom.createTextNode(c.getCCardNumber());
		cCardElement.appendChild(cCardText);
		customerElement.appendChild(cCardElement);
		
		Element ordersElement = dom.createElement("Orders");
		for(Order o: c.getOrders()){
			Element orderElement = dom.createElement("Order");
			for(Product p: o.getProductList()){
				Element orderProductElement = dom.createElement("Product");
				orderProductElement.setAttribute("price", String.valueOf(p.getPrice()));
				if(p.getIsFragile() == true)
					orderProductElement.setAttribute("isFragile", "1");
				else
					orderProductElement.setAttribute("isFragile", "1");
				Text productText = dom.createTextNode(p.getName());
				orderProductElement.appendChild(productText);
				orderElement.appendChild(orderProductElement);
			}
			ordersElement.appendChild(orderElement);			
		}
		
		
		customerElement.appendChild(ordersElement);

		return customerElement;
	}

	private void printToFile(){

		try{
			OutputFormat format = new OutputFormat();

			format.setIndenting(true);
			if(who == 1){
				XMLSerializer serializer = new XMLSerializer(
						new FileOutputStream(new File("warehouse.xml")), format);

				serializer.serialize(dom);
			}
			if(who == 2){
				XMLSerializer serializer = new XMLSerializer(
						new FileOutputStream(new File("customers.xml")), format);

				serializer.serialize(dom);
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}


}

package businessLayer;
import java.util.ArrayList;
import java.util.List;

import bst.BinarySearchTree;
import bst.Node;
import dataAccessLayer.XMLParser;


public class OPDept {

	XMLParser xmlParser = XMLParser.getInstance();

	private BinarySearchTree<Customer> customerBST;
	private static final OPDept instance = new OPDept();

	private OPDept(){
		xmlParser.parseDocument(0);
		customerBST = xmlParser.getCustomerBST();

	}
	
	public void parseDocument(){
		xmlParser.parseDocument(0);
		customerBST = xmlParser.getCustomerBST();
	}
	
	public static OPDept getInstance(){
		return instance;
	}

	public String[] getAllCustomers(){
		ArrayList<Customer> c = (ArrayList<Customer>)customerBST.toList();
		for(int i = 0; i < c.size() - 1; i++){
			for(int j = i + 1; j < c.size(); j++ ){
				if(c.get(i).getName() == c.get(j).getName()){
					c.remove(j);
				}
			}
		}

		String[] temp = new String[c.size()];
		for(int i = 0; i < c.size(); i++)
			temp[i] = c.get(i).getName();

		return temp;
	}

	public String[] getAllProducts(){
		ArrayList<Customer> c = (ArrayList<Customer>)customerBST.toList();
		ArrayList<Product> p = new ArrayList<Product>();
		for(int i = 0; i < c.size(); i++){
			if(c.get(i).getOrders().size() > 1)
				for(int j = 0; j < c.get(i).getOrders().size(); j++){
					for(int z = 0; z < c.get(i).getOrders().get(j).getProductList().size(); z++)
						if(!checkProductName(p, c.get(i).getOrders().get(j).getProductList().get(z).getName()))
							p.add(c.get(i).getOrders().get(j).getProductList().get(z));
				}			
							
						
			else
				if(c.get(i).getOrders().size() != 0)
					for(int z = 0; z < c.get(i).getOrders().get(0).getProductList().size(); z++)
						if(!checkProductName(p, c.get(i).getOrders().get(0).getProductList().get(z).getName()))
							p.add(c.get(i).getOrders().get(0).getProductList().get(z));
		}

		String[] temp = new String[p.size()];
		for(int i = 0; i < p.size(); i++){
			temp[i] = p.get(i).getName();
		}

		return temp;
	}
	
	private boolean checkProductName(ArrayList<Product> p, String name){
		for(int i = 0; i < p.size(); i++){
			
			if(p.get(i).getName().equals(name)) return true;
		}
		return false;
	}

	public String[][] getOrdersOfCustomer(String c){
		ArrayList<Customer> o = new ArrayList<Customer>();
		ordersOfCustomer(customerBST.root, o, c);
		return generateMatrix(o);
	}

	public String[][] getOrdersWithProduct(String p){
		ArrayList<Customer> o = new ArrayList<Customer>();
		ordersWithProduct(customerBST.root, o, p);
		return generateMatrix(o);
	}

	public String[][] getAllOrders(){
		List<Customer> customers = customerBST.toList();

		return generateMatrix((ArrayList<Customer>)customers);
	}

	private void ordersOfCustomer(Node<Customer> node, List<Customer> goal, String c) {
		if (node != null) {
			ordersOfCustomer(node.left, goal, c);
			if(node.value.getName().equals(c))
				goal.add(node.value);
			ordersOfCustomer(node.right, goal, c);
		}
	}

	private void ordersWithProduct(Node<Customer> node, List<Customer> goal, String p){
		if (node != null) {
			ordersWithProduct(node.left, goal, p);
			if(node.value.productNameExists(p) != -1){

				Customer c = new Customer(node.value.getName(), node.value.getType(), node.value.getAddress(), node.value.getCCardNumber());
				c.getOrders().add(node.value.getOrders().get(node.value.productNameExists(p)));
				goal.add(c);
			}
			ordersWithProduct(node.right, goal, p);			
		}
	}

	private String[][] generateMatrix(ArrayList<Customer> o){
		int n = 0;
		int k = 0;
		for(int i = 0; i < o.size(); i++){
			if(o.get(i).getOrders().size() != 0)
				n = n + o.get(i).getOrders().size();
			else
				n++;

		}
		String[][] temp = new String[n][3];

		for(int i = 0; i < o.size(); i++){

			if(o.get(i).getOrders().size() != 0)
				for(int j = 0; j < o.get(i).getOrders().size(); j++){

					temp[k][0] = o.get(i).getName();
					String ord = "";
					String total = "0";
					System.out.println(total);
					for(int z = 0; z < o.get(i).getOrders().get(j).getProductList().size(); z++){
						ord = ord  + o.get(i).getOrders().get(j).getProductList().get(z).getName() + ", ";
					}
					total = String.valueOf(Integer.valueOf(total) + o.get(i).getOrders().get(j).getTotal());
					System.out.println(total);
					temp[k][1] = ord;
					temp[k][2] = total;

					k++;
				}
			else{
				temp[k][0] = o.get(i).getName();
				temp[k][1] = " ";
				temp[k][2] = "0";
				k++;
			}

		}
		return temp;

	}


}

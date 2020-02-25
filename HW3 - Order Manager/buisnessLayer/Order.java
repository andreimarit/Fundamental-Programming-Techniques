package businessLayer;
import java.util.ArrayList;


public class Order extends Item{
	
	private Customer customer;
	//private Product product;
	private ArrayList<Product> products = new ArrayList<Product>();
	private int total = 0;
	private static int number = 1;
	public Order(){
		super(number);
		number++;		
				
	}
	
	public Customer getCustomer(){
		return this.customer;
	}
	
	public void setCustomer(Customer c){
		this.customer = c;
	}
	
	public int getTotal(){
		total = 0;
		for(int i = 0; i < products.size(); i++){
			total = total + products.get(i).getPrice();
		}
		return total;
	}
	
	public ArrayList<Product> getProductList(){
		return products;
	}
	
	public void setProductList(ArrayList<Product> p){
		this.products = p;
	}
}

package businessLayer;
import java.util.ArrayList;


public class Customer extends Item {

	private String name;
	private String type;
	private String address;
	private String cCardNumber;
	private ArrayList<Order> orders = new ArrayList<Order>();

	private static int number = 0;


	public Customer(String name, String type, String address, String cCardNumber){
		super(number);
		number++;
		this.name = name;
		this.type = type;
		this.address = address;
		this.cCardNumber = cCardNumber;
	}

	public Customer(){
		super(number);
		number++;
		this.name = "";
		this.type = "";
		this.address = "";
		this.cCardNumber = "";
	}
	/**
	 * getter method for the orders collection
	 * @return the orders collection
	 */
	public ArrayList<Order> getOrders(){
		return this.orders;
	}
	/**
	 * setter method for the orders collection
	 * @param o order collection
	 */
	public void setOrders(ArrayList<Order> o){
		this.orders = o;
	}
	/**
	 * 
	 * @param p
	 * @return
	 */
	public boolean productExists(Product p){

		if(orders.size() > 1){
			for(int i = 0; i < orders.size(); i++){
				if(orders.get(i).getProductList().contains(p)) return true;
			}
			return false;
		}
		else{
			if(orders.size() != 0)
				if(orders.get(0).getProductList().contains(p)) return true;
			return false;
		}
	}

	public int productNameExists(String p){
		if(orders.size() > 1){
			for(int i = 0; i < orders.size(); i++){
				for(int j = 0; j < orders.get(i).getProductList().size(); j++){
					if(p.equals(orders.get(i).getProductList().get(j).getName()))
						return i;
				}
			}
			return -1;
		}
		else{
			if(orders.size() != 0)
				for(int j = 0; j < orders.get(0).getProductList().size(); j++){
					if(p.equals(orders.get(0).getProductList().get(j).getName()))
						return 0;
				}
			return -1;
		}
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return this.address;
	}

	public void setCCardNumber(String cCardNumber){
		this.cCardNumber = cCardNumber;
	}

	public String getCCardNumber(){
		return this.cCardNumber;
	}
}

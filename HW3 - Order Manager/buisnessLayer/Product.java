package businessLayer;

public class Product extends Item{

	private String name;
	private int price;
	private boolean isFragile;
	
	private static int number = 1;
	
	public Product(String name, int price, boolean isFragile){
		super(number);
		number++;
		
		this.name = name;
		this.price = price;
		this.isFragile = isFragile;
	}
	
	public Product(){
		this("", -1, false);
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String n){
		this.name = n;
	}
	
	public int getPrice(){
		return this.price;
	}
	
	public void setPrice(int p){
		this.price = p;
	}
	
	public boolean getIsFragile(){
		return this.isFragile;
	}
	
	public void setIsFragile(boolean f){
		this.isFragile = f;
	}
}

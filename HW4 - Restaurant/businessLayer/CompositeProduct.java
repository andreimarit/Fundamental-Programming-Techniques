package businessLayer;

import java.io.Serializable;
import java.util.ArrayList;

public class CompositeProduct extends MenuItem {
	
	
	private static final long serialVersionUID = 1L;
	private ArrayList<MenuItem> items;
	
	public CompositeProduct(String name, String description) {
		super(name, description);
		items = new ArrayList<>();
	}
	
	public CompositeProduct(String name, String description, ArrayList<MenuItem> items) {
		super(name, description);
		this.items = items;
	}
	
	public boolean addMenuItem(MenuItem item) {
		return items.add(item);
	}
	
	public boolean removeMenuItem(MenuItem item) {
		return items.remove(item);
	}

	public ArrayList<MenuItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<MenuItem> items) {
		this.items = items;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int computePrice() {
		int price = 0;
		for(MenuItem item : items) {
			price += item.computePrice();
		}
		return price;
	}
}

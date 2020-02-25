package businessLayer;

import java.awt.Menu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import dataLayer.FileWrite;
import dataLayer.RestaurantSerializator;

public class Restaurant implements Serializable, IRestaurantProcessing{
	
	private HashMap<Order, ArrayList<MenuItem>> orders;
	private ArrayList<MenuItem> items;
	
	public Restaurant() {
		items = new ArrayList<>();
		orders = new HashMap<>();
	}
	
	@Override
	/**
	 * Adds a new MenuItem to the Restaurant
	 * @pre m != null
	 * @pre wellFormed()
	 * @post getSize() == getSize()@pre + 1
	 * @post wellFormed()
	 */
	public void addMenuItem(MenuItem m) {
		assert m != null;
		assert wellFormed();
		int preSize = items.size();
		items.add(m);
		assert items.size() == preSize + 1;
		assert wellFormed();
	}
	@Override
	/**
	* Remove the MenuItem from the Restaurant
	* @pre wellFormed()
	* @pre m != null
	* @pre getSize() > 0
	* @post getSize() == getSize()@pre - 1
	* @post wellFormed()
	*/
	public void deleteMenuItem(MenuItem m) {
		assert wellFormed();
		assert m != null;
		assert items.size() > 0;
		int preSize = items.size();
		ArrayList<MenuItem> toRemove = new ArrayList<>();
		for(MenuItem item : items) {
			if(item.equals(m)) {
				toRemove.add(item);
			}
			if(item instanceof CompositeProduct) {
				((CompositeProduct) item).removeMenuItem(m);
				if(((CompositeProduct) item).getItems().size() == 0) {
					toRemove.add(item);
				}
			}
		}
		items.removeAll(toRemove);
		assert items.size() == preSize - 1;
		assert wellFormed();
	}
	@Override
	/**
	 * Edit a Composite Product
	 * @pre wellFormed()
	 * @pre m != null
	 * @pre getSize() > 0
	 * @post getSize() == getSize@pre
	 * @post wellFormed()
	 */
	public void editMenuItem(MenuItem m, String name, String description, ArrayList<MenuItem> newItems) {
		assert wellFormed();
		assert m != null;
		assert items.size() > 0;
		int preSize = items.size();
		for(MenuItem item : items) {
			if(item.equals(m)) {
				item.setName(name);
				item.setDescription(description);
				((CompositeProduct) item).setItems(newItems);
			}
		}
		assert items.size() == preSize;
		assert wellFormed();
	}
	@Override
	/**
	 * Edit a Base Product
	 * @pre wellFormed()
	 * @pre m != null
	 * @pre getSize() > 0
	 * @post getSize() == getSize@pre
	 * @post wellFormed()
	 */
	public void editMenuItem(MenuItem m, String name, String description, int price) {
		assert wellFormed();
		assert m != null;
		assert items.size() > 0;
		int preSize = items.size();
		for(MenuItem item : items) {
			if(item.equals(m)) {
				item.setName(name);
				item.setDescription(description);
				((BaseProduct) item).setPrice(price);
			}
		}
		assert items.size() == preSize;
		assert wellFormed();
	}
	@Override
	/**
	 * Adds a new Order to the Restaurant
	 * @pre wellFormed()
	 * @pre o != null
	 * @pre getSize() > 0
	 * @post getSize() == getSize()@pre + 1
	 * @post wellFormed()
	 */
	public void addOrder(Order o,  ArrayList<MenuItem> menuItems) {
		assert wellFormed();
		assert o != null;
		assert orders.size() > 0;
		int preSize = orders.size();
		orders.put(o, menuItems);
		assert orders.size() == preSize;
		assert wellFormed();
	}
	@Override
	/**
	 * Compute the price of an order
	 * @pre wellFormed()
	 * @pre o != null
	 * @pre getSize() > 0
	 * @post getSize() == getSize()@pre
	 * @post wellFormed()
	 */
	public int computeOrderPrice(Order o) {
		assert wellFormed();
		assert o != null;
		assert orders.size() > 0;
		int preSize = orders.size();
		ArrayList<MenuItem> items = orders.get(o);
		int price = 0;
		for(MenuItem item : items) {
			price += item.computePrice();
		}
		assert orders.size() == preSize;
		assert wellFormed();
		return price;
	}
	@Override
	/**
	 * Compute Bill of an order
	 * @pre wellFormed()
	 * @pre o != null
	 * @pre getSize() > 0
	 * @post getSize() == getSize()@pre
	 * @post wellFormed()
	 */
	public void computeOrderBill(Order o) {
		assert wellFormed();
		assert o != null;
		assert orders.size() > 0;
		int preSize = orders.size();
		FileWrite.createBill(o, orders.get(o), computeOrderPrice(o));
		assert orders.size() == preSize;
		assert wellFormed();
	}
	
	@Override
	public ArrayList<MenuItem> getMenuItems() {
		return items;
	}

	@Override
	public Map<Order, ArrayList<MenuItem>> getOrders() {
		return orders;
	}

	
	public void setItems(ArrayList<MenuItem> items) {
		this.items = items;
	}

	public void setOrders(HashMap<Order, ArrayList<MenuItem>> orders) {
		this.orders = orders;
	}

	private boolean wellFormed() {
		if(items == null || orders == null) {
			return false;
		}
		int n = 0;
		for (MenuItem menuItem : items) {
			if(!menuItem.equals(items.get(n))) {
				return false;
			}
			n++;
		}
		if(n != items.size()) {
			return false;
		}
		n = 0;
		for (Map.Entry<Order, ArrayList<MenuItem>> entry : orders.entrySet()) {
			int m = 0;
			ArrayList<MenuItem> currentList = entry.getValue();
			for (MenuItem menuItem : currentList) {
				if(!menuItem.equals(currentList.get(m))) {
					return false;
				}
				m++;
			}
			if(m != currentList.size()) {
				return false;
			}
			if(!entry.getValue().equals(orders.get(entry.getKey()))) {
				return false;
			}
			n++;
		}
		if(n != orders.size()) {
			return false;
		}
		return true;
	}
	

}

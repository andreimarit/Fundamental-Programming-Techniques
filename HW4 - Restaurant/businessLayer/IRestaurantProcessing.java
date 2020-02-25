package businessLayer;

import java.util.ArrayList;
import java.util.Map;

public interface IRestaurantProcessing {
	
	public void addMenuItem(MenuItem m);
		
	public void deleteMenuItem(MenuItem m);
	
	public void editMenuItem(MenuItem m, String name, String description, ArrayList<MenuItem> items);
	
	public void editMenuItem(MenuItem m, String name, String description, int price);
	
	public void addOrder(Order o, ArrayList<MenuItem> menuItems);
	
	public int computeOrderPrice(Order o);
	
	public void computeOrderBill(Order o);
	
	public ArrayList<MenuItem> getMenuItems();
	
	public Map<Order, ArrayList<MenuItem>> getOrders();

}

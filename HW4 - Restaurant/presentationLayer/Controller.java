package presentationLayer;

import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;
import businessLayer.MenuItem;
import businessLayer.Order;
import businessLayer.Restaurant;
import dataLayer.FileWrite;
import dataLayer.RestaurantSerializator;

public class Controller {
	
	private View view;
	private Restaurant restaurant;
	private AdministratorGraphicalUserInterface administrator;
	private WaiterGraphicalUserInterface waiter;
	
	private MenuItem oldItem;
	
	public Controller(View v, Restaurant r) {
		view = v;
		restaurant = r;
		administrator = view.getAdministratorPanel();
		waiter = view.getWaiterPanel();
		view.addClosingWindowListener(new FrameListener());
		
		administrator.addItemsBtnListener(new NewItemBtnListener());
		waiter.addOrdersBtnListener(new NewOrderBtnListener());
		
		administrator.addCompositeItemBtnListener(new AddCompositeItemBtnListener());
		waiter.addMenuItemBtnListener(new AddMenuItemBtnListener());
		
		administrator.addDoneBtnListener(new FinishedItemBtnListener());
		waiter.addDoneBtnListener(new FinishedOrderBtnListener());
		
		administrator.addTableListener(new TableMouseListener(administrator.getItems()));
		waiter.addOrderTableListener(new TableMouseListener(waiter.getOrders()));
		
		administrator.addDeleteItemMenuListener(new DeleteItemRowListener());
		administrator.addEditItemMenuListener(new UpdateItemRowListener());
		administrator.addUpdateBtnListener(new UpdateItemBtnListener());
		
		waiter.addComputeBillListener(new ComputeBillRowListener());
		waiter.addGeneratePriceListener(new GeneratePriceRowListener());
	}
	
	class NewItemBtnListener implements ActionListener{
		/**
		 * This method describes what should happen when the Clients Menu Item is selected from the interface.
		 */
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout) view.getCards().getLayout();
			cardLayout.show(view.getCards(), "administrator");
			administrator.updateFields();
			administrator.setClassSpecificUpdate(null);
		}
	}
	
	class NewOrderBtnListener implements ActionListener{
		/**
		 * This method describes what should happen when the Clients Menu Item is selected from the interface.
		 */
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout) view.getCards().getLayout();
			cardLayout.show(view.getCards(), "waiter");
			waiter.updateFields();
		}
	}
	
	class AddCompositeItemBtnListener implements ActionListener{
		/**
		 * This method describes what should happen when the Clients Menu Item is selected from the interface.
		 */
		public void actionPerformed(ActionEvent e) {
			String name = (String) administrator.getMenuItems().getSelectedItem();
			administrator.addItemToTextArea(name);
		}
	}
	
	class AddMenuItemBtnListener implements ActionListener{
		/**
		 * This method describes what should happen when the Clients Menu Item is selected from the interface.
		 */
		public void actionPerformed(ActionEvent e) {
			String name = (String) waiter.getMenuItems().getSelectedItem();
			waiter.addItemToTextArea(name);
		}
	}
	
	class FinishedItemBtnListener implements ActionListener{
		/**
		 * This method describes what should happen when the Clients Menu Item is selected from the interface.
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				String name = administrator.getItemName();
				String description = administrator.getItemDescription();
				ArrayList<MenuItem> items = administrator.computeCompositeItemList();
				if(!items.isEmpty()) {
					administrator.getProcessing().addMenuItem(new CompositeProduct(name, description, items));
				}
				else {
					int price = administrator.getItemPrice();
					administrator.getProcessing().addMenuItem(new BaseProduct(name, description, price));
				}
				administrator.updateFields();
				CardLayout cardLayout = (CardLayout) view.getCards().getLayout();
				cardLayout.show(view.getCards(), "empty");
			}catch(IllegalArgumentException err) {
				view.popUpErrorMessage(err.getMessage());
			}
		}
	}
	
	class FinishedOrderBtnListener implements ActionListener{
		/**
		 * This method describes what should happen when the Clients Menu Item is selected from the interface.
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				int idOrder = waiter.getOrderId();
				int table = waiter.getTable();
				ArrayList<MenuItem> items = waiter.computeMenuItemList();
				Order order = new Order(idOrder, table);
				waiter.getProcessing().addOrder(order,  items);
				waiter.updateFields();
				CardLayout cardLayout = (CardLayout) view.getCards().getLayout();
				cardLayout.show(view.getCards(), "empty");
				for(MenuItem item : items) {
					if(item instanceof CompositeProduct) {
						waiter.announceOrder(order, items);
						break;
					}
				}
				
			}catch(IllegalArgumentException err) {
				view.popUpErrorMessage(err.getMessage());
			}
		}
	}
	
	class UpdateItemBtnListener implements ActionListener{
		
		/**
		 * This method describes what should happen when the Clients Menu Item is selected from the interface.
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				String name = administrator.getItemName();
				String description = administrator.getItemDescription();
				if(oldItem.getClass().equals(CompositeProduct.class)) {
					ArrayList<MenuItem> items = administrator.computeCompositeItemList();
					administrator.getProcessing().editMenuItem(oldItem, name, description, items);
				}
				else {
					int price = administrator.getItemPrice();
					administrator.getProcessing().editMenuItem(oldItem, name, description, price);
				}
				administrator.updateFields();
				CardLayout cardLayout = (CardLayout) view.getCards().getLayout();
				cardLayout.show(view.getCards(), "empty");
			}catch(IllegalArgumentException err) {
				view.popUpErrorMessage(err.getMessage());
			}
		}
	}
	
	public class TableMouseListener extends MouseAdapter {
	     
	    private JTable table;
	     
	    /**
	     * Constructor.
	     * @param table - the table associated with this TableMouseListener.
	     */
	    public TableMouseListener(JTable table) {
	        this.table = table;
	    }
	     
	    /**
	     * This method describes what should happen when the use attempts to select a row from the table using the mouse.
	     */
	    @Override
	    public void mousePressed(MouseEvent event) {
	        Point point = event.getPoint();
	        int currentRow = table.rowAtPoint(point);
	        table.setRowSelectionInterval(currentRow, currentRow);
	    }
	}
	
	public class DeleteItemRowListener extends MouseAdapter{
		
		/**
		 * This method describes what should happen when the Delete client button from the popUpMenu associated with the Clients table is pressed from the interface.
		 */
		 @Override
		 public void mousePressed(MouseEvent event) {
			 int row = administrator.getItems().getSelectedRow();
		     String name = (String) administrator.getItems().getValueAt(row, 0);
		     for(MenuItem item : restaurant.getMenuItems()) {
		    	 if(name.equals(item.getName())) {
		    		 restaurant.deleteMenuItem(item);
		    		 administrator.populateTable(restaurant.getMenuItems());
		    		 return;
		    	 }
		     }
		 }
	}
	
	public class UpdateItemRowListener extends MouseAdapter{
		
		/**
		 * This method describes what should happen when the Update client button from the popUpMenu associated with the Clients table is pressed from the interface.
		 */
		 @Override
		 public void mousePressed(MouseEvent event) {
			 administrator.getBtn_update().setVisible(true);
			 administrator.getBtn_done().setVisible(false);
			 int row = administrator.getItems().getSelectedRow();
		     String name = (String) administrator.getItems().getValueAt(row, 0);
		     for(MenuItem item : restaurant.getMenuItems()) {
		    	 if(name.equals(item.getName())) {
		    		 CardLayout cardLayout = (CardLayout) view.getCards().getLayout();
		 			 cardLayout.show(view.getCards(), "administrator");
		 			 oldItem = item;
		 			 administrator.setItemName(item.getName());
		 			 administrator.setItemDescription(item.getDescription());
		    		 administrator.setClassSpecificUpdate(item.getClass());
		    		 if(item.getClass().equals(CompositeProduct.class)) {
		    			 administrator.setItemsAdded(((CompositeProduct)item).getItems());
		    		 }
		    		 else {
		    			 administrator.setItemPrice(((BaseProduct)item).getPrice());
		    		 }
		    		 return;
		    	 }
		     }
		 }
	}
	
	public class ComputeBillRowListener extends MouseAdapter{
		
		/**
		 * This method describes what should happen when the Delete client button from the popUpMenu associated with the Clients table is pressed from the interface.
		 */
		 @Override
		 public void mousePressed(MouseEvent event) {
			 int row = waiter.getOrders().getSelectedRow();
		     int id = Integer.parseInt((String)waiter.getOrders().getValueAt(row, 0));
		     for (Map.Entry<Order, ArrayList<MenuItem>> entry : restaurant.getOrders().entrySet()) {
		    	 if(id == entry.getKey().getOrderId()) {
		    		 restaurant.computeOrderBill(entry.getKey());
		    		 return;
		    	 }
		     }
		 }
	}

	public class GeneratePriceRowListener extends MouseAdapter{
		
		/**
		 * This method describes what should happen when the Delete client button from the popUpMenu associated with the Clients table is pressed from the interface.
		 */
		 @Override
		 public void mousePressed(MouseEvent event) {
			 int row = waiter.getOrders().getSelectedRow();
		     int id = Integer.parseInt((String)waiter.getOrders().getValueAt(row, 0));
		     for (Map.Entry<Order, ArrayList<MenuItem>> entry : restaurant.getOrders().entrySet()) {
		    	 if(id == entry.getKey().getOrderId()) {
		    		 waiter.setPriceForOrder(restaurant.computeOrderPrice(entry.getKey()));
		    		 CardLayout cardLayout = (CardLayout) view.getCards().getLayout();
		 			 cardLayout.show(view.getCards(), "price");
		    		 return;
		    	 }
		     }
		 }
	}
	
	class FrameListener extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent e) {
			RestaurantSerializator.serialize(restaurant, new View());
		}

		
	}

}

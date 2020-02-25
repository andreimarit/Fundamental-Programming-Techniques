package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import businessLayer.IRestaurantProcessing;
import businessLayer.MenuItem;
import businessLayer.Order;
import businessLayer.Restaurant;

public class WaiterGraphicalUserInterface extends Observable{
	
	private JPanel mainContent = new JPanel();
	
	private ArrayList<Observer> observers = new ArrayList<>();
	
	private IRestaurantProcessing processing;

	private JButton    btn_add_order    = new JButton("Add New Order");
    
    private JPopupMenu popUpMenu = new JPopupMenu();
    private JMenuItem  computeBill = new JMenuItem("Compute Bill");
    private JMenuItem  generatePrice = new JMenuItem("Generate Price");
    
    private JTable  orders = new JTable();
    
    private JScrollPane tableScroll = new JScrollPane(orders, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    private Font bigFont = new Font("Times New Roman", Font.PLAIN, 23);
    
    private JPanel newOrder = new JPanel();
    private JTextField orderId = new JTextField(20);
    private JTextField table = new JTextField(20);
    private JComboBox<String> menuItems = new JComboBox<>();
    private JTextArea itemsAdded = new JTextArea(5, 30);
    private JScrollPane scroll = new JScrollPane(itemsAdded);
    
    private JPanel pricePanel = new JPanel();
    private JLabel priceForOrder = new JLabel();
    
    private JButton btn_addMenuItem = new JButton("Add menu item");
    
    private JButton btn_done = new JButton("Done");
	
    /**
     * Constructor. Sets the components of the interface.
     */
	public WaiterGraphicalUserInterface(Restaurant r) {
		processing = r;
		
		popUpMenu.add(computeBill);
		popUpMenu.add(generatePrice);
		orders.setComponentPopupMenu(popUpMenu);
		orders.setRowHeight(30);
		
		tableScroll.setPreferredSize(new Dimension(1000, 100));
		
		mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
		mainContent.add(new JLabel("Waiter View"));
		mainContent.add(tableScroll);
		mainContent.add(btn_add_order);
		
		changeFont(mainContent, bigFont);
		
		newOrder.setLayout(new BoxLayout(newOrder, BoxLayout.Y_AXIS));
		JPanel aux1 = new JPanel();
		JPanel aux2 = new JPanel();
		JPanel aux3 = new JPanel();
		
		aux1.setLayout(new FlowLayout());
		aux1.add(new JLabel("Order Id: "));
		aux1.add(orderId);
		aux1.add(new JLabel("Table: "));
		aux1.add(table);
	
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		itemsAdded.setText("Order Items:");
		
		aux3.setLayout(new FlowLayout());
		aux3.add(menuItems);
		aux3.add(new JLabel("    "));
		aux3.add(btn_addMenuItem);
		aux3.add(new JLabel("    "));
		aux3.add(scroll);
		populateTable(processing.getOrders());
		
		newOrder.add(aux1);
		newOrder.add(aux3);
		newOrder.add(btn_done);
		
		changeFont(newOrder, bigFont);
		
		pricePanel.setLayout(new BorderLayout());
		aux2.setLayout(new FlowLayout());
		aux2.add(priceForOrder);
		pricePanel.add(new JLabel("  "), BorderLayout.NORTH);
		pricePanel.add(aux2, BorderLayout.CENTER);
		changeFont(pricePanel, new Font("Times New Roman", Font.PLAIN, 35));
		
	}
	
	 public JPanel getMainContent() {
		return mainContent;
	 }
	
	 public JPanel getNewOrderPanel() {
		 updateComboBox();
		 return newOrder;
	 }
	 
	 public JPanel getPricePanel() {
		 return pricePanel;
	 }
	 
	 public void setPriceForOrder(int price) {
		 priceForOrder.setText("Price is: " + price + "RON");
	 }
	 
	 private void updateComboBox() {
		 fillComboBoxItems(processing.getMenuItems());
	 }
	 
	 public void updateFields() {
		 orderId.setText("");
		 table.setText("");
		 itemsAdded.setText("Order Items:");
		 populateTable(processing.getOrders());
		 updateComboBox();
	 }
	 
	 private void fillComboBoxItems(ArrayList<MenuItem> itemList) {	
		 
		 menuItems.removeAllItems();
		 if(itemList != null) {
		 	for (MenuItem item : itemList) {
				menuItems.addItem(item.getName());
			}
		 }	
	 }
	
	 private static void changeFont ( Component component, Font font ) {
	        component.setFont ( font );
	        if ( component instanceof Container ){
	            for ( Component child : ( ( Container ) component ).getComponents () ){
	                changeFont ( child, font );
	            }
	        }
	}
	 
	 /**
	  * Generic method that populates a table with data sent as parameter, using reflection.
	  * @param table - the table that has to be populated.
	  * @param data - the data.
	  * @param object - the class of the object corresponding to the table.
	  */
	 public void populateTable(Map<Order, ArrayList<MenuItem>> data) {
		 DefaultTableModel model = new DefaultTableModel();
		 String[] collumns = {"orderId", "date", "table"};
		 
		 model.setColumnIdentifiers(collumns);
		 orders.setModel(model);

		 String[] data2 = new String[3];

		for (Map.Entry<Order, ArrayList<MenuItem>> entry : data.entrySet()) {
			data2[0] = entry.getKey().getOrderId() + "";
			data2[1] = entry.getKey().getDate().toString();
			data2[2] = entry.getKey().getTable() + "";
			model.addRow(data2);
		}
	 }
	 
	 public void addItemToTextArea(String s) {
		 itemsAdded.setText(itemsAdded.getText() + '\n' + s);
	 }
	 
	 public ArrayList<MenuItem> computeMenuItemList(){
		 ArrayList<MenuItem> menuItems = new ArrayList<>();
		 String content = itemsAdded.getText();
		 String[] items = content.split("\n");
		 for(int i = 1; i < items.length; i++) {
			 MenuItem toAdd = null;
			 for (MenuItem menuItem : processing.getMenuItems()) {
				if(items[i].equals(menuItem.getName())) {
					toAdd = menuItem;
					menuItems.add(toAdd);
					break;
				}
			}
		 }
		 return menuItems;
	 }
	 
	 
	 /**
     * Method that adds an Action Listener to the Clients button
     * @param a ActionListener object for this specific button
     */
	 public void addOrdersBtnListener(ActionListener a) {
	    btn_add_order.addActionListener(a);
	 }
	 
	 /**
     * Method that adds a Mouse Listener to the Clients table
     * @param a MouseListener object for this specific table
     */
	 public void addOrderTableListener(MouseListener a) {
	   	orders.addMouseListener(a);
	 }

	 /**
     * Method that adds a Mouse Listener to the Delete button associated to the Client popUpMenu
     * @param a MouseListener object for this specific button
     */
	 public void addComputeBillListener(MouseListener a) {
	 	computeBill.addMouseListener(a);
	 }
	 
	 public void addGeneratePriceListener(MouseListener a) {
		generatePrice.addMouseListener(a);
	 }
	 
	 public void addMenuItemBtnListener(ActionListener a) {
	 	btn_addMenuItem.addActionListener(a);
	 }
	 
	 public void addDoneBtnListener(ActionListener a) {
		 btn_done.addActionListener(a);
	 }
	 
	 /**
	  * Getter method for the clients JTable.
	  * @return the clients JTable.
	  */
	public JTable getOrders() {
		return orders;
	}
	
	public JComboBox<String> getMenuItems() {
		return menuItems;
	}

	public int getOrderId() {
		if(orderId.getText().equals("")) {
			throw new IllegalArgumentException("Id of order is missing");
		}
		else {
			try {
				int id = Integer.parseInt(orderId.getText());
				for (Map.Entry<Order, ArrayList<MenuItem>> entry : processing.getOrders().entrySet()) {
					if(entry.getKey().getOrderId() == id) {
						throw new IllegalArgumentException("Order id must be unique");
					}
				}
				return id;
			}
			catch(NumberFormatException e) {
				throw new IllegalArgumentException("Id must be an integer");
			}
		}
	}
	
	public int getTable() {
		if(table.getText().equals("")) {
			throw new IllegalArgumentException("Table number is missing");
		}
		else {
			try {
				return Integer.parseInt(table.getText());
			}
			catch(NumberFormatException e) {
				throw new IllegalArgumentException("Table must be an integer");
			}
		}
	}
	
	public IRestaurantProcessing getProcessing() {
		return processing;
	}

	public ArrayList<Observer> getObservers() {
		return observers;
	}

	public void setObservers(ArrayList<Observer> observers) {
		this.observers = observers;
	}
	
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
	 
	public void announceOrder(Order o,  ArrayList<MenuItem> menuItems) {
		String orderDetail = "Order " + o.getOrderId() + " (Date: " + o.getDate() + "):\nTable " + o.getTable() + "\nContains the following menu items:\n";
		for(MenuItem item : menuItems) {
			orderDetail += item.getName() + ", " + item.getDescription() + "\n";
		}
		setChanged();
		notifyObservers(this, orderDetail);
	}
	
	private void notifyObservers(Observable observable, String order) {
		for(Observer ob : observers) {
			ob.update(observable, order);
		}
	}
	
}

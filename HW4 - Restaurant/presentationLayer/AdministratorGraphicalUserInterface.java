package presentationLayer;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
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

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;
import businessLayer.IRestaurantProcessing;
import businessLayer.MenuItem;
import businessLayer.Restaurant;

public class AdministratorGraphicalUserInterface{
	
	IRestaurantProcessing processing;
	
	private JPanel mainContent = new JPanel();

    private JButton    btn_add_item    = new JButton("Add New Menu Item");
    
    private JPopupMenu popUpMenu = new JPopupMenu();
    private JMenuItem  deleteItem = new JMenuItem("Delete");
    private JMenuItem  editItem = new JMenuItem("Edit");
    
    private JTable  items = new JTable();
    
    private JScrollPane tableScroll = new JScrollPane(items, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    private Font bigFont = new Font("Times New Roman", Font.PLAIN, 23);
    
    private JPanel newItem = new JPanel();
    private JTextField name = new JTextField(20);
    private JTextField description = new JTextField(60);
    private JTextField price = new JTextField(20);
    private JComboBox<String> menuItems = new JComboBox<>();
    private JTextArea itemsAdded = new JTextArea(5, 30);
    private JScrollPane scroll = new JScrollPane(itemsAdded);
    private JButton btn_addCompositeItem = new JButton("Add composite item");
    private JLabel label_price = new JLabel("*Price: ");
    
    
    private JButton btn_done = new JButton("Add");
    private JButton btn_update = new JButton("Update");
	
    /**
     * Constructor. Sets the components of the interface.
     */
	public AdministratorGraphicalUserInterface(Restaurant r) {
		processing = r;
		
		popUpMenu.add(deleteItem);
		popUpMenu.add(editItem);
		items.setComponentPopupMenu(popUpMenu);
		items.setRowHeight(30);
		
		tableScroll.setPreferredSize(new Dimension(1000, 400));
		
		mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
		mainContent.add(new JLabel("Administrator View"));
		mainContent.add(tableScroll);
		mainContent.add(btn_add_item);
		
		populateTable(processing.getMenuItems());
		
		changeFont(mainContent, bigFont);
		
		newItem.setLayout(new BoxLayout(newItem, BoxLayout.Y_AXIS));
		JPanel aux1 = new JPanel();
		JPanel aux2 = new JPanel();
		JPanel aux3 = new JPanel();
		JPanel aux4 = new JPanel();
		
		aux1.setLayout(new FlowLayout());
		aux1.add(new JLabel("*Name: "));
		aux1.add(name);
		aux1.add(label_price);
		aux1.add(price);
		
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		itemsAdded.setText("Composite Items:");
		
		aux2.setLayout(new FlowLayout());
		aux2.add(btn_done);
		aux2.add(btn_update);
		
		aux3.setLayout(new FlowLayout());
		aux3.add(menuItems);
		aux3.add(new JLabel("    "));
		aux3.add(btn_addCompositeItem);
		aux3.add(new JLabel("    "));
		aux3.add(scroll);
		
		aux4.setLayout(new FlowLayout());
		aux4.add(new JLabel("*Description: "));
		aux4.add(description);
		
		newItem.add(aux1);
		newItem.add(aux4);
		newItem.add(aux3);
		newItem.add(aux2);
		
		changeFont(newItem, bigFont);
		
	}
	
	 private static void changeFont ( Component component, Font font ) {
	        component.setFont ( font );
	        if ( component instanceof Container ){
	            for ( Component child : ( ( Container ) component ).getComponents () ){
	                changeFont ( child, font );
	            }
	        }
	}
	 
	 public JPanel getNewItemPanel() {
		 updateComboBox();
		 return newItem;
	 }
	 
	 private void updateComboBox() {
		 fillComboBoxItems(processing.getMenuItems());
	 }
	 
	 public void updateFields() {
		 name.setText("");
		 description.setText("");
		 price.setText("");
		 itemsAdded.setText("Composite Items:");
		 populateTable(processing.getMenuItems());
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
	 
	 /**
	  * Generic method that populates a table with data sent as parameter, using reflection.
	  * @param table - the table that has to be populated.
	  * @param data - the data.
	  * @param object - the class of the object corresponding to the table.
	  */
	 public void populateTable(ArrayList<MenuItem> data) {
		 DefaultTableModel model = new DefaultTableModel();
		 String[] collumns = {"name", "description", "price"};
		 
		 model.setColumnIdentifiers(collumns);
		 items.setModel(model);

		 String[] data2 = new String[3];

		for (MenuItem item : data) {
			data2[0] = item.getName();
			data2[1] = item.getDescription();
			data2[2] = "" + item.computePrice();
			//data2[2] = "1";
			model.addRow(data2);
		}
 
	 }
	 
	 public void addItemToTextArea(String s) {
		 itemsAdded.setText(itemsAdded.getText() + '\n' + s);
		 menuItems.removeItem(s);
		 mainContent.revalidate();
	 }
	
	 
	 public ArrayList<MenuItem> computeCompositeItemList(){
		 ArrayList<MenuItem> compositeItems = new ArrayList<>();
		 String content = itemsAdded.getText();
		 String[] items = content.split("\n");
		 for(int i = 1; i < items.length; i++) {
			 MenuItem toAdd = null;
			 for (MenuItem menuItem : processing.getMenuItems()) {
				if(items[i].equals(menuItem.getName())) {
					toAdd = menuItem;
					compositeItems.add(toAdd);
					break;
				}
			}
		 }
		 return compositeItems;
	 }
	 
	public void setItemsAdded(ArrayList<MenuItem> items) {
		itemsAdded.setText("Composite Items:");
		for(MenuItem item : items) {
			addItemToTextArea(item.getName());
		}
	}
	 
	 
	 /**
     * Method that adds an Action Listener to the Clients button
     * @param a ActionListener object for this specific button
     */
	 public void addItemsBtnListener(ActionListener a) {
	    btn_add_item.addActionListener(a);
	 }
	 
	 public void addCompositeItemBtnListener(ActionListener a) {
		 btn_addCompositeItem.addActionListener(a);
	 }
	 
	 public void addDoneBtnListener(ActionListener a) {
		 btn_done.addActionListener(a);
	 }
	 
	 public void addUpdateBtnListener(ActionListener a) {
		 btn_update.addActionListener(a);
	 }
	 
	 /**
     * Method that adds a Mouse Listener to the Clients table
     * @param a MouseListener object for this specific table
     */
	 public void addTableListener(MouseListener a) {
	    items.addMouseListener(a);
	 }

	 /**
     * Method that adds a Mouse Listener to the Delete button associated to the Client popUpMenu
     * @param a MouseListener object for this specific button
     */
	 public void addDeleteItemMenuListener(MouseListener a) {
		 deleteItem.addMouseListener(a);
	 }
	 
	 /**
     * Method that adds a Mouse Listener to the Update button associated to the Client popUpMenu
     * @param a MouseListener object for this specific button
     */
	 public void addEditItemMenuListener(MouseListener a) {
		 editItem.addMouseListener(a);
	 }

	 /**
	  * Getter method for the clients JTable.
	  * @return the clients JTable.
	  */
	public JTable getItems() {
		return items;
	}

	public JComboBox<String> getMenuItems() {
		return menuItems;
	}
	
	public String getItemName() {
		if(name.getText().equals("")) {
			throw new IllegalArgumentException("Name of item is missing");
		}
		else {
			return name.getText();
		}
	}
	
	public String getItemDescription() {
		if(description.getText().equals("")) {
			throw new IllegalArgumentException("Descripton of item is missing");
		}
		else {
			return description.getText();
		}
	}
	
	public int getItemPrice() {
		if(price.getText().equals("")) {
			throw new IllegalArgumentException("Price of item is missing");
		}
		else {
			try {
				return Integer.parseInt(price.getText());
			}
			catch(NumberFormatException e) {
				throw new IllegalArgumentException("Price must be an integer");
			}
		}
	}


	public IRestaurantProcessing getProcessing() {
		return processing;
	}

	public void setItemName(String name) {
		this.name.setText(name);
	}

	public void setItemDescription(String description) {
		this.description.setText(description);
	}

	public void setItemPrice(int price) {
		this.price.setText("" + price);
	}
	

	public JButton getBtn_done() {
		return btn_done;
	}

	public JButton getBtn_update() {
		return btn_update;
	}
	
	public JLabel getLabel_price() {
		return label_price;
	}

	public JScrollPane getScroll() {
		return scroll;
	}

	public JButton getBtn_addCompositeItem() {
		return btn_addCompositeItem;
	}

	public JTextField getPrice() {
		return price;
	}
	
	public JPanel getMainContent() {
		return mainContent;
	}

	
	public void setClassSpecificUpdate(Class z) {
		if(z == null) {
			btn_update.setVisible(false);
			btn_done.setVisible(true);
			menuItems.setVisible(true);
			btn_addCompositeItem.setVisible(true);
			scroll.setVisible(true);
			price.setVisible(true);
			label_price.setVisible(true);
		}
		else if(z.equals(CompositeProduct.class)) {
			btn_update.setVisible(true);
			btn_done.setVisible(false);
			menuItems.setVisible(true);
			btn_addCompositeItem.setVisible(true);
			scroll.setVisible(true);
			price.setVisible(false);
			label_price.setVisible(false);
		}
		else if(z.equals(BaseProduct.class)) {
			btn_update.setVisible(true);
			btn_done.setVisible(false);
			menuItems.setVisible(false);
			btn_addCompositeItem.setVisible(false);
			scroll.setVisible(false);
			price.setVisible(true);
			label_price.setVisible(true);
		}
	}
	
}

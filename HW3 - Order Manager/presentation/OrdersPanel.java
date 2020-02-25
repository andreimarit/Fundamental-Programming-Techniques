package presentation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import businessLayer.OPDept;


public class OrdersPanel extends JPanel{
	
	private String[][] toShow;
	private OPDept orders;
	private JList clients;
	private JList products;
	private int selectedCustomer;
	private JTable table;
	private DefaultTableModel model;
	private OrdersPanel op;
	
	
	
	
	public OrdersPanel(){
		op = this;
		//XMLParser parser = new XMLParser(0);
		//parser.parseDocument();
		//ArrayList<Customer> customers = parser.getCustomerList();
		
		orders = OPDept.getInstance();
		
		toShow = orders.getAllOrders();
		String[] col = {"Name", "Order", "Total"};
		model = new DefaultTableModel(toShow, col);
		table = new JTable(model);
		
		refresh();
		products = new JList(orders.getAllProducts());
		clients = new JList(orders.getAllCustomers());
		
		clients.setBounds(720, 10, 200, 100);
		clients.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				if(!e.getValueIsAdjusting()){
					
					products.clearSelection();
					if(clients.getSelectedIndices().length == 0)
						toShow = orders.getAllOrders();
					else
						toShow = orders.getOrdersOfCustomer(clients.getSelectedValue().toString());
					String[] col = {"Name", "Order", "Total"};
					int a = table.getRowCount();
					for(int i = table.getRowCount() - 1; i >= 0; i--){
						model.removeRow(i);
					}
										
					for(int i = 0; i < toShow.length; i++){
						model.insertRow(i, toShow[i]);
					}
					
					op.revalidate();
					op.repaint();
				}
			}
		});
		
		
		products.setBounds(720, 300, 200, 100);
		products.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				clients.clearSelection();
				if(products.getSelectedIndices().length == 0)
					toShow = orders.getAllOrders();
				else
					toShow = orders.getOrdersWithProduct(products.getSelectedValue().toString());
				String[] col = {"Name", "Order", "Total"};
				int a = table.getRowCount();
				for(int i = table.getRowCount() - 1; i >= 0; i--){
					model.removeRow(i);
				}
									
				for(int i = 0; i < toShow.length; i++){
					model.insertRow(i, toShow[i]);
				}
				//table.setModel(model);
				op.revalidate();
				op.repaint();
			}
		});
		
		
		
		//table.setBounds(5, 5, 300, 500);
		table.setPreferredSize(new Dimension(300, 500));
		clients.setPreferredSize(new Dimension(200, 200));
		products.setPreferredSize(new Dimension(200, 200));
		
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(5, 5, 700, 400);
		
		JScrollPane spClients = new JScrollPane(clients);
		spClients.setBounds(720, 10, 200, 200);
		
		JScrollPane spProducts = new JScrollPane(products);
		spProducts.setBounds(720, 250, 200, 200);
		
		
		JButton deleteButton = new JButton("Remove order");
		deleteButton.setBounds(200, 430, 150, 25);
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		
		JButton refreshButton = new JButton("Refresh data");
		refreshButton.setBounds(0, 430, 150, 25);
		refreshButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				op.refresh();
				
			}
		});
		
		this.setPreferredSize(new Dimension(950, 500));
		this.setLayout(null);
		
		this.add(sp);
		this.add(spClients);
		this.add(spProducts);
		this.add(refreshButton);
		
	}
	
	public void refresh(){
		orders.parseDocument();
		toShow = orders.getAllOrders();
		String[] col = {"Name", "Order", "Total"};
		model.setDataVector(toShow, col);
	}
}

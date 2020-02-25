package presentation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import businessLayer.Customer;
import businessLayer.CustomerCollection;


public class CustomerPanel extends JPanel{
	private JTable customers;
	private DefaultTableModel customersModel;
	private CustomerCollection collection;
	private String[][] customersToShow;
	private ArrayList<Customer> customersToShowList;
	private String[] colCustomers = {"Name", "Type", "Address", "Credit Card"};
	
	public CustomerPanel(){
		this.setLayout(null);
		this.setPreferredSize(new Dimension(350, 430));
		
		collection = CustomerCollection.getInstance();
		customersToShow = collection.getCustomers();
		customersToShowList = collection.getCustomersList();
		customersModel = new DefaultTableModel(customersToShow, colCustomers);
		customers = new JTable(customersModel);
		
		JButton addCustomer = new JButton("Add Customer");
		addCustomer.setBounds(5, 400, 150, 25);
		addCustomer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final JFrame add = new JFrame("Add Customer");
				JPanel addPanel = new JPanel();
				addPanel.setLayout(null);
				addPanel.setPreferredSize(new Dimension(300, 200));

				JLabel nameLabel = new JLabel("Name: ");
				final JTextField name = new JTextField();				
				nameLabel.setBounds(5, 5, 100, 25);
				name.setBounds(105, 5, 150, 25);

				JLabel priceLabel = new JLabel("Address: ");
				final JTextField price = new JTextField();
				priceLabel.setBounds(5, 30, 100, 25);
				price.setBounds(105, 30, 150, 25);
				
				JLabel creditLabel = new JLabel("Credit Card: ");
				final JTextField credit = new JTextField();
				creditLabel.setBounds(5, 60, 100, 25);
				credit.setBounds(105, 60, 150, 25);

				JLabel fragileLabel = new JLabel("Type: ");
				String[] fragile = {"Personal", "Company"};
				final JComboBox isFragile = new JComboBox(fragile);
				fragileLabel.setBounds(5, 90, 100, 25);
				isFragile.setBounds(105, 90, 150, 25);

				JButton addProduct = new JButton("Add Customer");
				addProduct.setBounds(5, 130, 150, 25);
				addProduct.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						int k = 0;
						for(int i = 0; i < customersToShow.length; i++)
							if(name.getText().equals(customersToShow[i][0])) k = 1;
						if(k == 1 || name.getText().equals("") || price.getText().equals(""))
							JOptionPane.showMessageDialog(add, "Please check your input. Customer may already exist!");
						else{
							Customer c = new Customer();
							c.setName(name.getText());
							c.setAddress(price.getText());
							c.setCCardNumber(credit.getText());
							c.setType(isFragile.getSelectedItem().toString());
							
							collection.addCustomer(c);
							customersToShowList = collection.getCustomersList();
							customersModel.setDataVector(collection.getCustomers(), colCustomers);
						}
					}
				});

				addPanel.add(addProduct);
				addPanel.add(isFragile);
				addPanel.add(fragileLabel);
				addPanel.add(price);
				addPanel.add(priceLabel);
				addPanel.add(name);
				addPanel.add(nameLabel);
				addPanel.add(credit);
				addPanel.add(creditLabel);

				add.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				add.setContentPane(addPanel);
				add.pack();
				add.setVisible(true);
			}
		});
		
		
		JButton removeCustomer = new JButton("Remove Customer");
		removeCustomer.setBounds(175, 400, 150, 25);
		removeCustomer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int index = customers.getSelectedRow();
				collection.removeCustomer(customersToShowList.get(index));
				customersModel.setDataVector(collection.getCustomers(), colCustomers);
			}
		});
		
		JScrollPane productsSP = new JScrollPane(customers);
		customers.setBounds(0, 0, 325, 350);
		productsSP.setBounds(5, 30, 325, 350);
		
		JLabel lbl = new JLabel("Customer Management");
		lbl.setBounds(15, 0, 150, 30);

		this.add(productsSP);
		this.add(addCustomer);
		this.add(removeCustomer);
		this.add(lbl);

	}
}

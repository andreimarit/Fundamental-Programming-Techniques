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

import businessLayer.Product;
import businessLayer.Warehouse;


public class ProductsPanel extends JPanel {
	private JTable products;
	private DefaultTableModel productsModel;
	private Warehouse warehouse;
	private String[][] productsToShow;
	private ArrayList<Product> productsToShowList;
	private String[] colProducts = {"Name", "Price", "Is Fragile"};

	public ProductsPanel(){
		this.setLayout(null);
		this.setPreferredSize(new Dimension(350, 430));

		warehouse = Warehouse.getInstance();
		productsToShow = warehouse.getProducts();
		productsToShowList = warehouse.getProductsList();
		productsModel = new DefaultTableModel(productsToShow, colProducts);
		products = new JTable(productsModel);




		JButton addProduct = new JButton("Add Product");
		addProduct.setBounds(5, 400, 150, 25);
		addProduct.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final JFrame add = new JFrame("Add Product");
				JPanel addPanel = new JPanel();
				addPanel.setLayout(null);
				addPanel.setPreferredSize(new Dimension(300, 200));

				JLabel nameLabel = new JLabel("Name: ");
				final JTextField name = new JTextField();				
				nameLabel.setBounds(5, 5, 100, 25);
				name.setBounds(105, 5, 150, 25);

				JLabel priceLabel = new JLabel("Price: ");
				final JTextField price = new JTextField();
				priceLabel.setBounds(5, 30, 100, 25);
				price.setBounds(105, 30, 150, 25);

				JLabel fragileLabel = new JLabel("Is Fragile: ");
				String[] fragile = {"True", "False"};
				final JComboBox isFragile = new JComboBox(fragile);
				fragileLabel.setBounds(5, 60, 100, 25);
				isFragile.setBounds(105, 60, 150, 25);

				JButton addProduct = new JButton("Add Product");
				addProduct.setBounds(5, 100, 150, 25);
				addProduct.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						int k = 0;
						for(int i = 0; i < productsToShow.length; i++)
							if(name.getText().equals(productsToShow[i][0])) k = 1;
						if(k == 1 || name.getText() == "" || !isNumber(price.getText()))
							JOptionPane.showMessageDialog(add, "Please check your input. Product may already exist!");
						else{
							Product p = new Product();
							p.setName(name.getText());
							p.setPrice(Integer.valueOf(price.getText()));
							if(isFragile.getSelectedItem().toString().equals("True"))
								p.setIsFragile(true);
							else
								p.setIsFragile(false);

							warehouse.addProduct(p);
							productsToShowList = warehouse.getProductsList();
							productsModel.setDataVector(warehouse.getProducts(), colProducts);
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

				add.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				add.setContentPane(addPanel);
				add.pack();
				add.setVisible(true);
			}
		});
		
		JButton removeProduct = new JButton("Remove Product");
		removeProduct.setBounds(175, 400, 150, 25);
		removeProduct.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int index = products.getSelectedRow();
				warehouse.removeProduct(productsToShowList.get(index));
				productsModel.setDataVector(warehouse.getProducts(), colProducts);
			}
		});
		
		
		JScrollPane productsSP = new JScrollPane(products);
		products.setBounds(0, 0, 325, 350);
		productsSP.setBounds(5, 30, 325, 350);
		
		JLabel lbl = new JLabel("Product Management");
		lbl.setBounds(15, 0, 150, 30);
		
		this.add(productsSP);
		this.add(addProduct);
		this.add(removeProduct);
		this.add(lbl);
		
	}
	
	private boolean isNumber(String s){
		try{
			Integer.valueOf(s);
		}
		catch(Exception ex){
			return false;
		}
		return true;
	}
}

package presentation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MenuPanel extends JPanel{
	
	public MenuPanel(){
		this.setPreferredSize(new Dimension(600, 600));
		this.setOpaque(true);
		this.setLayout(null);
		
		JButton btnOrders = new JButton("Orders");
		JButton btnOrderManagement = new JButton("Order Management");
		
		
		btnOrders.setBounds(200, 150, 200, 100);
		btnOrders.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame managementFrame = new JFrame("App Management");
				managementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
				managementFrame.setContentPane(new ManagementPanel());
				managementFrame.pack();
				managementFrame.setVisible(true);
			}
		});
		
		btnOrderManagement.setBounds(200, 300, 200, 100);
		btnOrderManagement.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame orderManagementFrame = new JFrame("Order Management");
				orderManagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
				orderManagementFrame.setContentPane(new OrdersPanel());
				
				orderManagementFrame.pack();
				orderManagementFrame.setVisible(true);
				
			}
		});
		
		
		this.add(btnOrders);
		this.add(btnOrderManagement);
	}
	
}

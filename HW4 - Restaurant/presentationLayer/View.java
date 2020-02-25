package presentationLayer;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import businessLayer.Restaurant;

public class View extends JFrame{
	
	private JPanel cards = new JPanel();
	private AdministratorGraphicalUserInterface administratorPanel;
	private WaiterGraphicalUserInterface waiterPanel;
	private ChefGraphicalUserInterface chefPanel;

	public View() {}
	
	public View(Restaurant r) {
		administratorPanel = new AdministratorGraphicalUserInterface(r);
		waiterPanel = new WaiterGraphicalUserInterface(r);
		chefPanel = new ChefGraphicalUserInterface();
		
		waiterPanel.registerObserver(chefPanel);
		
		JPanel content1 = new JPanel();
		JPanel content2 = new JPanel();
		content1.setLayout(new BoxLayout(content1, BoxLayout.X_AXIS));
		content2.setLayout(new BoxLayout(content2, BoxLayout.Y_AXIS));
		
		cards.setLayout(new CardLayout());
		
		cards.add(new JPanel(), "empty");
		cards.add(administratorPanel.getNewItemPanel(), "administrator");
		cards.add(waiterPanel.getNewOrderPanel(), "waiter");
		cards.add(waiterPanel.getPricePanel(), "price");
		
		content1.add(administratorPanel.getMainContent());
		content1.add(new JLabel("     "));
		content1.add(waiterPanel.getMainContent());
		content1.add(new JLabel("     "));
		content1.add(chefPanel.getMainContent());
		
		content2.add(content1);
		content2.add(cards);
		
		this.setContentPane(content2);
        this.pack();
        this.setLocationRelativeTo(null);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(3 * screenSize.width/4, 3 * screenSize.height/4);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        this.setTitle("Restaurant Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setVisible(true);
	}

	public JPanel getCards() {
		return cards;
	}

	public AdministratorGraphicalUserInterface getAdministratorPanel() {
		return administratorPanel;
	}

	public WaiterGraphicalUserInterface getWaiterPanel() {
		return waiterPanel;
	}
	
	/**
	 * Method that generates an Error popUp message containing a message sent as parameter
	 * @param msg - the message to be displayed.
	 */
	public void popUpErrorMessage(String msg) {
    	JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
	
	public void addClosingWindowListener(WindowAdapter a) {
		this.addWindowListener(a);
	}
	
}

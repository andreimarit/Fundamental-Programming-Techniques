package presentationLayer;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ChefGraphicalUserInterface implements Observer{
	
	private JPanel mainContent = new JPanel();
	
	private JTextArea actions = new JTextArea(10, 70);
	
	private Font bigFont = new Font("Times New Roman", Font.PLAIN, 23);
	
	public ChefGraphicalUserInterface() {
		actions.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(actions);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.add(new JLabel("Chef View"));
        mainContent.add(scroll);
        mainContent.add(new JLabel("    "));
		
		changeFont(mainContent, bigFont);
	}
	
	private static void changeFont ( Component component, Font font ) {
        component.setFont ( font );
        if ( component instanceof Container ){
            for ( Component child : ( ( Container ) component ).getComponents () ){
                changeFont ( child, font );
            }
        }
	}
	
	public JPanel getMainContent() {
		return mainContent;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		actions.setText(actions.getText() + (String)arg1);
	}

}

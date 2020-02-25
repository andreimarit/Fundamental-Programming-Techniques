package gui;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;


public class InterfacePanel extends JPanel{
	
	public InterfacePanel(){
		
		DisplayPanel dp = new DisplayPanel();
		ControlPanel cp = new ControlPanel(dp);
		
		JScrollPane sp = new JScrollPane(dp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		Border etched = BorderFactory.createEtchedBorder();
		dp.setBorder(BorderFactory.createTitledBorder(etched, "Simulation Panel"));
		cp.setBorder(BorderFactory.createTitledBorder(etched, "Control Panel"));
		this.setLayout(new GridLayout(1, 2));
		this.setOpaque(true);
		this.setVisible(true);
		
		this.add(sp);
		this.add(cp);
		
	}
	
	
}

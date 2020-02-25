import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;



public class InterfacePanel extends JPanel{
	
	public InterfacePanel()
	{
		DisplayPanel dp = new DisplayPanel();
		ControlPanel cp = new ControlPanel(dp);
		OperationsPanel op = new OperationsPanel(dp);
		JScrollPane sp1 = new JScrollPane(dp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane sp2 = new JScrollPane(cp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel DisplayContainer = new JPanel();
		DisplayContainer.setLayout(new BorderLayout());
		
		DisplayContainer.add(op, BorderLayout.SOUTH);
		DisplayContainer.add(sp1, BorderLayout.NORTH);
		Border etched = BorderFactory.createEtchedBorder();
		dp.setBorder(BorderFactory.createTitledBorder(etched, "Display Panel"));
		cp.setBorder(BorderFactory.createTitledBorder(etched, "Input Panel"));
		this.setLayout(new GridLayout(1, 2));
			
		this.add(DisplayContainer);
		this.add(sp2);
	}
}

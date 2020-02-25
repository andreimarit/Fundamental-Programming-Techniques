import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import functionality.PolynomialCollection;
//old-aged like Saturday

public class DisplayPanel extends JPanel {
	public PolynomialCollection collection = new PolynomialCollection();
	public PolynomialCollection derivates = new PolynomialCollection();
	public PolynomialCollection integrals = new PolynomialCollection();
	public ArrayList<String> results = new ArrayList<String>();
	public String pwr;
	public String value;
	private int k = 10;
	private DisplayPanel dp;
	private JPopupMenu pMenu = new JPopupMenu();
	
	//constructor
	public DisplayPanel()
	{
		//set panel properties
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(400, 500));
		this.setOpaque(true);
		this.setLayout(null);
		
		dp = this;
		
		//popUpMenu code
		pMenu = new JPopupMenu();
		JMenuItem menuItem1 = new JMenuItem("Clear Derivatives");
		pMenu.add(menuItem1);
		JMenuItem menuItem3 = new JMenuItem("Clear Integrals");
		pMenu.add(menuItem3);
		JMenuItem menuItem2 = new JMenuItem("Clear Computations");
		pMenu.add(menuItem2);

		// menuItem event Handlers
		menuItem1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						derivates.clearCollection();
						dp.revalidate();
						dp.repaint();					
					}
				});
		menuItem2.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						results.removeAll(results);
						dp.revalidate();
						dp.repaint();					
					}
				});
		menuItem3.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						integrals.clearCollection();
						dp.revalidate();
						dp.repaint();					
					}
				});
		
		// show the popUpMenu on right click
		this.addMouseListener(
				new MouseAdapter()
				{
					public void mouseReleased(MouseEvent e)
					{
						if(e.isPopupTrigger())
						{
							pMenu.show(e.getComponent(), e.getX(), e.getY());
						}
					}
				});
		
	}

	//from the JPanel
	public void paintComponent(Graphics g)
	{
		k = 10;
		this.setPreferredSize(new Dimension(400, 500));
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		int i;
		this.removeAll();
		
		//print current polynomials
		for(i = 0; i < collection.getSize(); i++)
		{
			JLabel lbl = new JLabel("<html><div width=\"480\">f<sub>" + Integer.toString(i+1) + "</sub>(x) = " + collection.getPolynomial(i).printPolynomial() + "</div></html>");
			lbl.setBounds(10, k, 490, 50);
			lbl.setPreferredSize(new Dimension(500,70));
			k += 70;
			this.add(lbl);
			if(k > 500)
				this.setPreferredSize(new Dimension(600, k));
			this.revalidate();
		}
		//print derivatives
		
		if(derivates.getSize() > 0)
		{
			for(i = 0; i < derivates.getSize(); i++)
			{
				JLabel lbl = new JLabel("<html><div width=\"480\">f<sub>" + Integer.toString(i+1) + "</sub>'(x) = " + derivates.getPolynomial(i).printPolynomial() + "</div></html>");
				lbl.setForeground(Color.blue);
				lbl.setBounds(10, k, 490, 70);
				
				k += 70;
				this.add(lbl);
				if(k > 500)
					this.setPreferredSize(new Dimension(500, k));
				this.revalidate();
			}
		}
		
		//print integrals
		if(integrals.getSize() > 0)
		{
			for(i = 0; i < integrals.getSize(); i++)
			{
				JLabel lbl = new JLabel("<html><div width=\"480\">f<sub>" + Integer.toString(i+1) + "</sub>'(x) = " + integrals.getPolynomial(i).printPolynomial() + "</div></html>");
				lbl.setForeground(Color.lightGray);
				lbl.setBounds(10, k, 490, 70);
				
				k += 70;
				this.add(lbl);
				if(k > 500)
					this.setPreferredSize(new Dimension(500, k));
				this.revalidate();
			}
		}
		
		//print result computations
		if(results.size() > 0)
		{
			k +=50;
			for(i = 0; i < results.size(); i++)
			{
				JLabel lbl = new JLabel("<html><div width=\"480\">F<sub>" + Integer.toString(i+1) + "</sub>(" + value + ") = " + results.get(i) + "</div></html>");
				lbl.setForeground(Color.ORANGE);
				lbl.setBounds(10, k, 490, 70);
				k += 70;
				this.add(lbl);
				if(k > 500)
					this.setPreferredSize(new Dimension(500, k));
				this.revalidate();
			}
		}
		if(k > 430)
			this.setPreferredSize(new Dimension(500, k + 70));



	}
}

package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import functionality.Client;
import functionality.ClientQueue;


public class DisplayPanel extends JPanel implements MouseListener {

	
	DisplayPanel dp; //reference to this object for use in internal classes
	private int y = 10; //y position for ellipses
	private boolean ended = false; //true if simulation has ended

	public ClientQueue q1; //data structure
	public ClientQueue q2; //data structure
	public ClientQueue q3; //data structure

	public ClientQueue removed; //data structure holding all removed elements from the simulation queues
/**
 * called when a new object of type DisplayPanel is created
 */

	public DisplayPanel(){

		dp = this;
		q1 = new ClientQueue(this, 1);
		q2 = new ClientQueue(this, 2);
		q3 = new ClientQueue(this, 3);
		removed = new ClientQueue(this, 4);
		this.setPreferredSize(new Dimension(500, 500));
		this.setBackground(Color.gray);
		this.setOpaque(true);

		this.addMouseListener(this);




	}
	/**
	 * overrides the JPanel paintComponent method
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.blue);

		y = 30;
		for(int i = 0; i < q1.getQueueSize(); i++)
		{
			g2d.setColor(Color.orange);
			g2d.fillRect(5, 15, 30, 14);
			g2d.setColor(Color.white);
			g2d.drawString("1", 25, 25);
			g2d.setColor(Color.blue);
			g2d.fillOval(10, y, 20, 20);
			g2d.setColor(Color.lightGray);
			g2d.drawString(q1.getName(i), 17, y + 13);
			y += 21;
		}
		if(y > 500){
			this.setPreferredSize(new Dimension(500, y));
			this.revalidate();
		}

		y = 30;

		for(int i = 0; i < q2.getQueueSize(); i++)
		{
			g2d.setColor(Color.orange);
			g2d.fillRect(105, 15, 30, 14);
			g2d.setColor(Color.white);
			g2d.drawString("2", 125, 25);
			g2d.setColor(Color.blue);
			g2d.fillOval(110, y, 20, 20);
			g2d.setColor(Color.lightGray);
			g2d.drawString(q2.getName(i), 117, y + 13);
			y += 21;
		}
		if(y > 500){
			this.setPreferredSize(new Dimension(500, y));
			this.revalidate();
		}
		y = 30;
		for(int i = 0; i < q3.getQueueSize(); i++){
			g2d.setColor(Color.orange);
			g2d.fillRect(205, 15, 30, 14);
			g2d.setColor(Color.white);
			g2d.drawString("3", 225, 25);
			g2d.setColor(Color.blue);
			g2d.fillOval(210, y, 20, 20);
			g2d.setColor(Color.lightGray);
			g2d.drawString(q3.getName(i), 217, y + 13);
			y += 21;
		}
		if(y > 500){
			this.setPreferredSize(new Dimension(500, y));
			this.revalidate();
		}
		y = 30;
		g2d.setColor(Color.black);
		g2d.drawString("removed clients:", 410, 20);

		for(int i = 0; i < removed.getQueueSize(); i++){
			g2d.setColor(Color.blue);
			g2d.fillOval(410, y, 20, 20);
			g2d.setColor(Color.lightGray);
			g2d.drawString(removed.getName(i), 417, y + 13);
			g2d.setColor(Color.black);
			g2d.drawString(removed.getServiceTime(i), 450, y+13);
			y += 21;
		}
		if(y > 500){
			this.setPreferredSize(new Dimension(500, y));
			this.revalidate();
		}
		if(ended){
			dp.simulationEnded();			
		}
				

	}
	/**
	 * called when a new element is added to the queue
	 */
	public void addOval(){
		this.repaint();		
	}
	/**
	 * called when a certain phase of the simulation process has ended
	 */
	public void simulationEnded(){
		this.ended = true;
		removed.clearQueue();
		this.repaint();
	}
	/**
	 * called when removing a client form either of the simulation queues
	 * @param c : client reference
	 * @param n
	 */
	public void removeOval(Client c, int n){
		removed.addClient(c);
	}

	public void mouseClicked(MouseEvent e){		

	}

	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
}

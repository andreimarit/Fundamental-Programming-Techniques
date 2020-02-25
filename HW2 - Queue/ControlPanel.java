package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import functionality.LogPrinter;
import functionality.Server;


public class ControlPanel extends JPanel implements MouseListener {

	private DisplayPanel dp; //current DisplayPanel

	private int minArriving = 300; //will hold a value given by the user
	private int maxArriving = 800; //will hold a value given by the user
	private int minService = 4000; //will hold a value given by the user
	private int maxService = 5000; //will hold a value given by the user
	private int simulationStart; //will hold a value given by the user
	private int simulationEnd; //will hold a value given by the user
	private long simulationTime; //computed from values given by the user



	private JComboBox comboStart; //interface component
	private JComboBox comboEnd; //interface component
	private JTextField textArriveMin = new JTextField(); //interface component
	private JTextField textArriveMax = new JTextField(); //interface component
	private JTextField textServiceMin = new JTextField(); //interface component
	private JTextField textServiceMax = new JTextField(); //interface component

	/**
	 * called whenever a new ControlPanel object is instantiated
	 * @param d : reference to the current DisplayPanel
	 */
	public ControlPanel(DisplayPanel d){
		this.dp = d;

		String[] hours = new String[25];
		for(int i = 0; i <= 24; i++){
			hours[i] = String.valueOf(i);
		}


		this.setPreferredSize(new Dimension(300, 500));
		this.setBackground(Color.white);
		//this.setOpaque(true);
		this.setLayout(null);

		JLabel lblStart = new JLabel("Start time");
		lblStart.setBounds(55, 15, 150, 25);
		comboStart = new JComboBox(hours);
		comboStart.setBounds(250, 15, 200, 25);
		comboStart.setSelectedIndex(0);
		comboStart.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				int s = comboStart.getSelectedIndex();
				simulationStart = s;

			}
		});


		//JTextField textStart = new JTextField();
		//textStart.setBounds(250, 15, 200, 25);

		JLabel lblEnd = new JLabel("End time");
		lblEnd.setBounds(55, 50, 150, 25);
		comboEnd = new JComboBox(hours);
		comboEnd.setBounds(250, 50, 200, 25);
		comboEnd.setSelectedIndex(0);
		comboEnd.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				int s = comboEnd.getSelectedIndex();
				simulationEnd = s;

			}
		});
		//JTextField textEnd = new JTextField();
		//textEnd.setBounds(250, 50, 200, 25);

		JLabel lblArriveMin = new JLabel("Minimum arriving time:");
		lblArriveMin.setBounds(55, 85, 150, 25);
		textArriveMin.setBounds(250, 85, 200, 25);

		JLabel lblArriveMax = new JLabel("Maximum arriving time:");
		lblArriveMax.setBounds(55, 120, 150, 25);
		textArriveMax.setBounds(250, 120, 200, 25);

		JLabel lblServiceMin = new JLabel("Minimum service time:");
		lblServiceMin.setBounds(55, 155, 150, 25);
		textServiceMin.setBounds(250, 155, 200, 25);

		JLabel lblServiceMax = new JLabel("Maximum service time:");
		lblServiceMax.setBounds(55, 190, 150, 25);
		textServiceMax.setBounds(250, 190, 200, 25);

		JButton btn = new JButton("Start Simulation");
		btn.setBounds(130, 250, 200, 30);
		btn.addMouseListener(this);

		this.add(lblStart);
		this.add(comboStart);
		this.add(lblEnd);
		this.add(comboEnd);
		this.add(lblArriveMin);
		this.add(textArriveMin);
		this.add(lblArriveMax);
		this.add(textArriveMax);
		this.add(lblServiceMin);
		this.add(textServiceMin);
		this.add(lblServiceMax);
		this.add(textServiceMax);
		this.add(btn);


	}

	/**
	 * event handles for the only button in the interface
	 */
	public void mouseClicked(MouseEvent e){

		simulationTime = (simulationEnd - simulationStart) * 1000;

		if(simulationTime <= 0 || !isNumber(textArriveMin.getText()) || !isNumber(textArriveMax.getText()) || !isNumber(textServiceMin.getText()) || !isNumber(textServiceMax.getText())){
			JOptionPane.showMessageDialog(null, "Please check your input");
		}
		else{

			String s = textArriveMin.getText();
			minArriving = (new Integer(textArriveMin.getText()).intValue() * 1000);
			maxArriving = (new Integer(textArriveMax.getText()).intValue() * 1000);
			minService = (new Integer(textServiceMin.getText()).intValue() * 1000);
			maxService = (new Integer(textServiceMax.getText()).intValue() * 1000);

			if(minArriving >= maxArriving || minService >= maxService)
				JOptionPane.showMessageDialog(null, "Please check your input");
			else{
				LogPrinter.printStart(simulationTime, minArriving, maxArriving, minService, maxService);

				Server server = new Server(dp, minArriving, maxArriving, minService, maxService, simulationTime);
				server.startSimulation(0);
			}

		}
	}
	/**
	 * checks if the String object given as argument represents a number or not
	 * @param s
	 * @return
	 */
	public boolean isNumber(String s){
		char[] temp = s.toCharArray();
		for(int i = 0; i < temp.length; i++){
			if(temp[i] < '0' && temp[i] > '9'){
				return false;
			}
		}
		return true;
	}


	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}

}

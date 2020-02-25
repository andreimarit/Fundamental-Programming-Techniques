package gui;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import functionality.Client;
import functionality.ClientQueue;


public class AppManager {
	/*private static int minArriving = 300;
	private static int maxArriving = 800;
	private static int minService = 4000;
	private static int maxService = 5000;
	private static int time = 2000;
	private static Random rand = new Random();

		
	private static ClientQueue q1;
	private static ClientQueue q2;
	private static ClientQueue q3;
	private static ClientQueue min;

	private static int k = 0;
	*/
	public static void main(String[] args){
			
		JFrame window = new JFrame("Queue Simulatior");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			
			e.printStackTrace();
		}


		final ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(100);
		

		//final DisplayPanel d = new DisplayPanel();
		//JScrollPane sp = new JScrollPane(d, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				
		window.setContentPane(new InterfacePanel());
		window.pack();
		window.setVisible(true);
		
		/*
		q1 = new ClientQueue(d, 1);
		q2 = new ClientQueue(d, 2);
		q3 = new ClientQueue(d, 3);
		d.q1 = q1;
		d.q2 = q2;
		d.q3 = q3;

		

		
		final Callable<Void> c = new Callable<Void>(){
			public Void call(){
				try{
					if(!stpe.isShdutdown()){
						min = q1;
						if(q2.getQueueSize() < min.getQueueSize())
							min = q2;
						if(q3.getQueueSize() < min.getQueueSize())
							min = q3;
						k++;
						min.addClient(new Client(rand.nextInt(maxService - minService) + minService, System.currentTimeMillis(), String.valueOf(k)));
						
					
						
						time = rand.nextInt(maxArriving - minArriving) + minArriving;
						//System.out.print("Time: ");
						//System.out.println(time);
					}
				}
				finally{
					 	if(q1.notEmpty())
						stpe.schedule(new Runnable(){
							public void run(){
								q1.removeHeadOfQueue();
							}
						}, q1.getHeadOfQueue().getServiceTime(), TimeUnit.MILLISECONDS);
						
						if(q2.notEmpty())
						stpe.schedule(new Runnable(){
							public void run(){
								q2.removeHeadOfQueue();
							}
						}, q2.getHeadOfQueue().getServiceTime(), TimeUnit.MILLISECONDS);
						
						if(q3.notEmpty())
						stpe.schedule(new Runnable(){
							public void run(){
								q3.removeHeadOfQueue();
							}
						}, q3.getHeadOfQueue().getServiceTime(), TimeUnit.MILLISECONDS);
					
					stpe.schedule(this, time, TimeUnit.MILLISECONDS);
				}
				return null;
			}
		};
		
		
		stpe.schedule(c, 0, TimeUnit.MILLISECONDS);
		stpe.schedule(new Runnable(){
			public void run(){
				stpe.shutdownNow();
				d.simulationEnded();
				System.out.println("Simulation ended");
				System.out.println(q1.clientsAdded());
				System.out.println(q2.clientsAdded());
				System.out.println(q3.clientsAdded());
			}
		}, 15000, TimeUnit.MILLISECONDS);

		
		//q.printQueue();
		 
		 */

	}

}
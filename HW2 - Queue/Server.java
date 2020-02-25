package functionality;

import gui.DisplayPanel;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;


public class Server {

	private  long time = 500; //holds the delay time between successive arrivals
	private  int k = 0;
	private  int turn = 0;
	private  int removed = 1; //used for correctly scheduling the removal
	private  int removed1 = 1; //used for correctly scheduling the removal
	private  int removed2 = 1; //used for correctly scheduling the removal
	private  Random rand = new Random(); //used for generating random numbers in a give interval
	private int[] clientsHour = new int[30]; //used for computing the peak hour
	private int t1 = 0; // used for computing the peak hour
	
	private  DisplayPanel dp; //reference to the current displayPanel
	private  ClientQueue min; //used for finding out the shortest queue
	
	
	private  long minArriving = 300; //set by user: minimum time between successive arrivals
	private  long maxArriving = 800; //set by user: maximum time between successive arrivals
	private  long minService = 4000; //set by user: minimum bound for the randomly generated service time
	private  long maxService = 5000; //set by user: maximum bound for the randomly generated service time
	private  long simulationTime; //set by user: the period for one simulation phase
	
	
	
	private ClientQueue q1; //data structure
	private ClientQueue q2; //data structure
	private ClientQueue q3; //data structure
	
	private Server server; //reference to this object for use in internal classes
	/**
	 * called when a new object of type Server is created
	 * @param d : current DisplayPanel
	 * @param minArriving set by user
	 * @param maxArriving set by user
	 * @param minService set by user
	 * @param maxService set by user
	 * @param simulationTime set by user
	 */
	public Server(DisplayPanel d, long minArriving, long maxArriving, long minService, long maxService, long simulationTime){
		this.dp = d;
		this.minArriving = minArriving;
		this.maxArriving = maxArriving;
		this.minService = minService;
		this.maxService = maxService;
		this.simulationTime = simulationTime;
		this.server = this;
		

	}
	/**
	 * handles the simulation and log file printing for phase 1 of the simulation process
	 * @param t
	 */
	public void startSimulation(int t){
		final ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(100);
		q1 = new ClientQueue(dp, 1);
		q2 = new ClientQueue(dp, 2);
		q3 = new ClientQueue(dp, 3);

		
		dp.q1 = q1;
		dp.q2 = q2;
		dp.q3 = q3;



				
				
			if(t == 0){
				LogPrinter.printSeparator(1);
				//recursively calls itself to schedule addition(enqueue) or removal(dequeue)
				final Callable<Void> c = new Callable<Void>(){
					public Void call(){
						try{
							if(!stpe.isShutdown()){
								min = q1;
								
								k++;
								long tt = System.currentTimeMillis();
								if(k == 1)
									min.setFirstArriving(tt);
								min.addClient(new Client(Math.abs(rand.nextLong() % (maxService - minService) + minService), tt, String.valueOf(k)));

								time = rand.nextLong() % (maxArriving - minArriving) + minArriving;
								
							}
						}
						finally{
							
							if(q1.notEmpty() && removed == 1){
								removed = 0;
								long tt = q1.getHeadOfQueue().getServiceTime();
								stpe.schedule(new Runnable(){
									public void run(){
										q1.removeHeadOfQueue();
										removed = 1;
									}
								}, tt, TimeUnit.MILLISECONDS);
							}
														
							stpe.schedule(this, time, TimeUnit.MILLISECONDS);
						}
						return null;
					}
				};
				
				//first call for c : start immediately
				stpe.schedule(c, 0, TimeUnit.MILLISECONDS);
				
				//another thread for computing the peak hour
				stpe.scheduleWithFixedDelay(new Runnable(){
					public void run(){
						clientsHour[t1] = q1.getQueueSize();
						t1++;
					}
				}, 0, 1000, TimeUnit.MILLISECONDS);
				
				//thread that will stop the threadPoolExecutor
				stpe.schedule(new Runnable(){
					public void run(){
						stpe.shutdownNow();
						stpe.purge();
						int max = 0;
						for(int i = 1; i < t1; i++){
							if(clientsHour[i] > clientsHour[max]) max = i;
						}
						LogPrinter.printResults(dp.removed.getAverageServiceTime(), dp.removed.getAverageWaitingTime(), q1.getDownTime(), q1.getQueueSize(), max);
						dp.simulationEnded();
						turn++;
						
						server.startSimulation2();
						System.out.println("Simulation ended");
						
					}
				}, simulationTime, TimeUnit.MILLISECONDS);
			}

	}
	/**
	 * handles the simulation and log file printing for the third phase
	 */
	public void startSimulation3(){
		final ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(100);
		
		t1 = 0;
		removed = 1;
		removed1 = 1;
		removed2 = 1;
		q1.clearQueue();
		q2.clearQueue();
		q3.clearQueue();
		LogPrinter.printSeparator(3);
		final Callable<Void> c = new Callable<Void>(){
			public Void call(){
				try{
					if(!stpe.isShutdown()){
						min = q1;
						if(q2.getQueueSize() < min.getQueueSize())
							min = q2;
						if(q3.getQueueSize() < min.getQueueSize())
							min = q3;
						k++;
						long tt = System.currentTimeMillis();
						if(min.clientsAdded() == 0)
							min.setFirstArriving(tt);
						min.addClient(new Client(Math.abs(rand.nextLong() % (maxService - minService) + minService), tt, String.valueOf(k)));
						
						time = rand.nextLong() % (maxArriving - minArriving) + minArriving;
						//System.out.print("Time: ");
						//System.out.println(time);
					}
				}
				finally{
					if(q1.notEmpty() && removed == 1){
						removed = 0;
						stpe.schedule(new Runnable(){
							public void run(){
								q1.removeHeadOfQueue();
								removed = 1;
							}
						}, q1.getHeadOfQueue().getServiceTime(), TimeUnit.MILLISECONDS);
					}
					if(q2.notEmpty() && removed1 == 1){
						removed1 = 0;
						stpe.schedule(new Runnable(){
							public void run(){
								q2.removeHeadOfQueue();
								removed1 = 1;
							}
						}, q2.getHeadOfQueue().getServiceTime(), TimeUnit.MILLISECONDS);
					}
					if(q3.notEmpty() && removed2 == 1){
						removed2 = 0;
						stpe.schedule(new Runnable(){
							public void run(){
								q3.removeHeadOfQueue();
								removed2 = 1;
							}
						}, q3.getHeadOfQueue().getServiceTime(), TimeUnit.MILLISECONDS);
					}
					stpe.schedule(this, time, TimeUnit.MILLISECONDS);
				}
				return null;
			}
		};


		stpe.schedule(c, 0, TimeUnit.MILLISECONDS);
		stpe.scheduleWithFixedDelay(new Runnable(){
			public void run(){
				clientsHour[t1] = q1.getQueueSize();
				t1++;
			}
		}, 0, 1000, TimeUnit.MILLISECONDS);
		stpe.schedule(new Runnable(){
			public void run(){
				stpe.shutdownNow();
				stpe.purge();
				int max = 0;
				for(int i = 1; i < t1; i++){
					if(clientsHour[i] > clientsHour[max]) max = i;
				}
				LogPrinter.printResults(dp.removed.getAverageServiceTime(), dp.removed.getAverageWaitingTime(), q1.getDownTime() + q2.getDownTime() + q3.getDownTime(), q1.getQueueSize() + q1.getQueueSize() + q3.getQueueSize(), max);
				dp.simulationEnded();
				turn++;
				JOptionPane.showMessageDialog(null, "Simulation Ended!");
				
			}
		}, simulationTime, TimeUnit.MILLISECONDS);

	}
	/**
	 * handles the simulation and log file printing in the third phase
	 */
	public void startSimulation2(){
		final ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(100);
		
		t1 = 0;
		removed = 1;
		removed1 = 1;
		q1.clearQueue();
		q2.clearQueue();
		LogPrinter.printSeparator(2);
		final Callable<Void> c = new Callable<Void>(){
			public Void call(){
				try{
					if(!stpe.isShutdown()){
						min = q1;
						if(q2.getQueueSize() < min.getQueueSize())
							min = q2;
						
						k++;
						long tt = System.currentTimeMillis();
						if(min.clientsAdded() == 0)
							min.setFirstArriving(tt);
						min.addClient(new Client(Math.abs(rand.nextLong() % (maxService - minService) + minService), tt, String.valueOf(k)));
						


						time = rand.nextLong() % (maxArriving - minArriving) + minArriving;
						//System.out.print("Time: ");
						//System.out.println(time);
					}
				}
				finally{
					if(q1.notEmpty() && removed == 1){
						removed = 0;
						stpe.schedule(new Runnable(){
							public void run(){
								q1.removeHeadOfQueue();
								removed = 1;
							}
						}, q1.getHeadOfQueue().getServiceTime(), TimeUnit.MILLISECONDS);
					}
					if(q2.notEmpty() && removed1 == 1){
						removed1 = 0;
						stpe.schedule(new Runnable(){
							public void run(){
								q2.removeHeadOfQueue();
								removed1 = 1;
							}
						}, q2.getHeadOfQueue().getServiceTime(), TimeUnit.MILLISECONDS);
					}
					
					stpe.schedule(this, time, TimeUnit.MILLISECONDS);
				}
				return null;
			}
		};


		stpe.schedule(c, 0, TimeUnit.MILLISECONDS);
		stpe.scheduleWithFixedDelay(new Runnable(){
			public void run(){
				clientsHour[t1] = q1.getQueueSize();
				t1++;
			}
		}, 0, 1000, TimeUnit.MILLISECONDS);
		stpe.schedule(new Runnable(){
			public void run(){
				stpe.shutdownNow();
				stpe.purge();
				int max = 0;
				for(int i = 1; i < t1; i++){
					if(clientsHour[i] > clientsHour[max]) max = i;
				}
				LogPrinter.printResults(dp.removed.getAverageServiceTime(), dp.removed.getAverageWaitingTime(), q1.getDownTime() + q2.getDownTime(), q1.getQueueSize() + q2.getQueueSize(), max);
				dp.simulationEnded();
				turn++;
				server.startSimulation3();
				System.out.println("Simulation ended");
				System.out.println(q1.clientsAdded());
				System.out.println(q2.clientsAdded());
				System.out.println(q3.clientsAdded());
			}
		}, simulationTime, TimeUnit.MILLISECONDS);

	}
	
	
}

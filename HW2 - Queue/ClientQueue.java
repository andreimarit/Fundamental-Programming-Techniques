package functionality;

import gui.DisplayPanel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;




public class ClientQueue{

	private Queue<Client> q = new LinkedList<Client>();
	
	private int k1 = 0; //counts the number of clients added to the queue
	private int k2 = 0;
	private DisplayPanel dp; //references the current DisplayPane
	private int name; //id for the ClientQueue
	private long firstArrive; //time of arrival of the first element
	
	private long removeEmpty = 0;
	private long downTime; //total empty queue time
	
	/**
	 * called when a new object of type ClientQueue is instantiated
	 * @param d : current DisplayPanel
	 * @param n : name - id
	 */
	public ClientQueue(DisplayPanel d, int n){
		dp = d;
		this.name = n;
	}
	/**
	 * add the client to the queue
	 * @param c
	 */
	public void addClient(Client c)
	{
		long t = System.currentTimeMillis();
		if(q.size() == 0) 
			if(removeEmpty != 0)
				downTime = downTime + t - removeEmpty;
			else
				downTime = downTime + t - firstArrive;
		q.add(c);
		
		k1++;
		k2++;
		System.out.println("Added client " + c.getName());
		
		if(this.name != 4){
			LogPrinter.printAddClient(c.getName(), c.getServiceTime());
		}
		
		dp.repaint();
	}
	/**
	 * sets the arrival time of the first client
	 * @param s
	 */
	public void setFirstArriving(long s){
		this.firstArrive = s;
	}
	/**
	 * gets the arrival time of the first client
	 * @return
	 */
	public long getFirstArriving(){
		return this.firstArrive;
	}
	/**
	 * returns the number of clients currently in the queue
	 * @return
	 */
	public int getQueueSize(){
		return q.size();
	}
	/**
	 * returns the element that is currently first in the queue
	 * @return
	 */
	public Client getHeadOfQueue(){
		return q.element();
	}
	/**
	 * removes the element that is currently first in queue and prints informatory messages in the log file
	 */
	public void removeHeadOfQueue(){
		long time = System.currentTimeMillis();
		time = time - q.element().getArrivingTime();
		q.element().setWaitingTime(time);
		System.out.print("Removed client " + q.element().getName() + ". Arriving time: ");
		System.out.println(time);
		
		LogPrinter.printRemoveClient(q.element().getName(), time, firstArrive, q.element().getArrivingTime() - firstArrive, System.currentTimeMillis() - firstArrive);
		
		dp.removeOval(q.element(), this.name);
		q.remove();
		if(q.size() == 0) this.removeEmpty = System.currentTimeMillis();
		dp.repaint();
		
	}
	
	/**
	 * check if the queue has clients
	 * @return true if it doesen't and false otherwise
	 */
	public boolean notEmpty(){
		return !q.isEmpty();
	}
	/**
	 * number of clients added in queue life-time
	 * @return
	 */
	public int clientsAdded(){
		return k1;
	}
	/**
	 * get the name of a specific client
	 * @param index : index of a the client whose name we want
	 * @return the name of the client at required index
	 */
	public String getName(int index){
		Iterator<Client> i = q.iterator();
		int k = -1;
		String temp;
		while(i.hasNext())
		{
			temp = i.next().getName();
			k++;
			if(index == k)
				return temp;
		}
		return "";
	}
	/**
	 * gets the service time of a specific client
	 * @param index : index of the required client
	 * @return the service time of the client curently at required position in the queue
	 */
	public String getServiceTime(int index){
		Iterator<Client> i = q.iterator();
		int k = -1;
		String temp;
		while(i.hasNext())
		{
			temp = String.valueOf(i.next().getServiceTime());
			k++;
			if(index == k)
				return temp;
		}
		return "";
	}
	/**
	 * removes all elements from the queue
	 */
	public void clearQueue(){
		q.clear();
	}
	/**
	 * gets the average of the service times of all clients curently in the queue
	 */
	public long getAverageServiceTime(){
		Iterator<Client> i = q.iterator();
		long temp = 0;
		while(i.hasNext()){
			temp = temp + i.next().getServiceTime();
		}
		
		return temp/q.size();
	}
	/**
	 * gets the average of the waiting times of all clients currently in the queue
	 * @return
	 */
	public long getAverageWaitingTime(){
		Iterator<Client> i = q.iterator();
		long temp = 0;
		while(i.hasNext()){
			temp = temp + i.next().getWaitingTime();
		}
		
		return temp/q.size();
	}
	/**
	 * gets the total empty queue time
	 * @return
	 */
	public long getDownTime(){
		return this.downTime;
	}
	
}

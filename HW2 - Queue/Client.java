package functionality;

public class Client {

	private long serviceTime; //time needed to perform the request
	private long arrivingTime; //processor time of arrival at the back of the queue
	private long waitingTime; //waiting time
	private String name; //name of the client - id
	
	/**
	 * Constructor for the Client class.
	 * @param sTime : give value to serviceTime
	 * @param aTime : give value to arrivingTime
	 * @param n : give value to name
	 */
	public Client(long sTime, long aTime, String n){
		this.serviceTime = sTime;
		this.arrivingTime = aTime;
		this.name = n;
	}
	
	/**
	 * gets the value of the Client's service time
	 * @return 
	 */
	public long getServiceTime(){
		return serviceTime;
	}
	/**
	 * sets the value of the Client's service time
	 * @param s
	 */
	public void setServiceTime(long s){
		this.serviceTime = s;
	}
	/**
	 * gets the time in milliseconds when the Client arrived in the queue
	 * @return
	 */
	public long getArrivingTime(){
		return this.arrivingTime;
	}
	/**
	 * sets the time when the Client arrived in the queue
	 * @param s
	 */
	public void setArrivingTime(long s){
		this.arrivingTime = s;
	}
	/**
	 * gets the Client waiting time in milliseconds
	 * @return
	 */
	public long getWaitingTime(){
		return this.waitingTime;
	}
	/**
	 * sets the Client waiting time
	 * @param s
	 */
	public void setWaitingTime(long s){
		this.waitingTime = s;
	}
	/**
	 * gets the Client's id
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * sets the Client id
	 * @param s
	 */
	public void setName(String s){
		this.name = s;
	}
}

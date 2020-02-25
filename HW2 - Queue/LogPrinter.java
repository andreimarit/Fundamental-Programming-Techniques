package functionality;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class LogPrinter {

	public LogPrinter(){}
	/**
	 * prints the header of the log file
	 * @param simulationTime : set by user
	 * @param minArriving : set by user
	 * @param maxArriving : set by user
	 * @param minService : set by user
	 * @param maxService : set by user
	 */
	public static void printStart(long simulationTime, long minArriving, long maxArriving, long minService, long maxService){
		try {
			FileWriter fstream = new FileWriter("out.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("----------------------------------------------------------");
			out.newLine();
			out.write("Simulation Log file:");
			out.newLine();
			out.write("----------------------------------------------------------");
			out.newLine();
			out.write("Simulation Time: " + simulationTime + " ms");
			out.newLine();
			out.write("Minimum Arriving Time: " + minArriving + " ms");
			out.newLine();
			out.write("Maximum Arriving Time: " + maxArriving + " ms");
			out.newLine();
			out.write("Minimum Service Time: " + minService + " ms");
			out.newLine();
			out.write("Maximum Service Time: " + maxService + " ms");
			out.newLine();

			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * prints the mini-heading at the top of a simulation phase log information
	 * @param serverCount which phase simulation currently runs
	 */
	public static void printSeparator(int serverCount){
		String s = String.valueOf(serverCount);

		try {			
			FileWriter fstream = new FileWriter("out.txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("----------------------------------------------------------");
			out.newLine();
			out.write("Started simulating with " + s + " server(s)");
			out.newLine();
			out.write("----------------------------------------------------------");
			out.newLine();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * prints the computed results after the simulation phase was completed
	 * @param avgService : average service time
	 * @param avgWaiting : average waiting time
	 * @param downTime : total empty queue time
	 * @param clients : total number of unserved clients
	 * @param peak : peak hour
	 */
	public static void printResults(long avgService, long avgWaiting, long downTime, int clients, int peak){
		try {
			FileWriter fstream = new FileWriter("out.txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.newLine();
			out.write("Average Service Time: " + avgService);
			out.newLine();
			out.write("Average Waiting Time: " + avgWaiting);
			out.newLine();
			out.write("Total Server Downtime: " + downTime);
			out.newLine();
			out.write("Unserved Clients: " + clients);
			out.newLine();
			out.write("Peak Hour: " + peak);
			out.newLine();
			
			
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * print to the log file information when a new client is added
	 * @param name
	 * @param serviceTime
	 */
	public static void printAddClient(String name, long serviceTime)
	{
		try {
			FileWriter fstream = new FileWriter("out.txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Added client " + name + " with service time: " + serviceTime);
			out.newLine();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * print to the log file information when a client is removed
	 * @param name
	 * @param time
	 * @param firstArrive
	 * @param arrivingTime
	 * @param leavingTime
	 */
	public static void printRemoveClient(String name, long time, long firstArrive, long arrivingTime, long leavingTime){
		try {
			FileWriter fstream = new FileWriter("out.txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Removed client " + name + " who waited: " + time + ". Arriving time: " + arrivingTime + ". Leaving time: " + leavingTime);
			out.newLine();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

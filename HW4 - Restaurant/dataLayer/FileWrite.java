package dataLayer;

import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.IntrospectionException;
import java.io.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import businessLayer.MenuItem;
import businessLayer.Order;
import businessLayer.Restaurant;
import presentationLayer.View;


public class FileWrite {
	
	private static int nrBills = 0;
	
	public static void createBill(Order order, ArrayList<MenuItem> items, int price) {
		String fileName = "C:\\Users\\andre\\Desktop\\Grupa Speranta 2019\\PT\\Assignment4\\bill" + nrBills + ".txt";
		
		ArrayList<String> content = new ArrayList<>();
		
		content.add("Order Bill");
		content.add("\n");
		content.add("Order id : " + order.getOrderId().intValue());
		content.add("Order date : " + order.getDate());
		content.add("Order Table : " + order.getTable().intValue());
		content.add("\n");
		
		for(MenuItem item : items) {
			content.add("Item name : " + item.getName() + "            " + item.computePrice() + " RON");
			content.add("     " + item.getDescription());
		}
		
		content.add("--------------------------------------------------------");
		content.add("\n");
		content.add("Total : " + price + ".00 RON");
		
		try (FileWriter writer = new FileWriter(fileName);
	          BufferedWriter bw = new BufferedWriter(writer)) {

			for (String string : content) {
				if(!string.equals("\n")) {
					bw.write(string);
				}
				bw.newLine();
			}
			
			ProcessBuilder pb = new ProcessBuilder("Notepad.exe", fileName);
			pb.start();

			nrBills++;
	    } catch (IOException err) {
	        
	    }
	}

}

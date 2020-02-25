package dataLayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import businessLayer.MenuItem;
import businessLayer.Restaurant;
import presentationLayer.View;

public class RestaurantSerializator {
	
	private static String filename = "restaurant.ser";

	public static void serialize(Restaurant restaurant, View view) {
		FileOutputStream file;
		try {
			file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file); 
	          
	        out.writeObject(restaurant);
	              
	        out.close(); 
	        file.close(); 
		} catch (IOException e) {
			view.popUpErrorMessage(e.getMessage());
		} 
        
	}
	
	public static Restaurant deserialize(View view) {
		Restaurant obj = new Restaurant();
		try {
			ArrayList<MenuItem> items = new ArrayList<>();
			FileInputStream file = new FileInputStream(filename); 
	        ObjectInputStream in = new ObjectInputStream(file); 
	            
	        obj = (Restaurant)in.readObject(); 
	                 
	        in.close(); 
            file.close(); 
            
		}catch(IOException | ClassNotFoundException e) {
			view.popUpErrorMessage(e.getMessage());
		}
		
		return obj;
	}

}

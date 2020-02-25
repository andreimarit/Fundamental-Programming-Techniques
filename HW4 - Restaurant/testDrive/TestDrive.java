package testDrive;
import java.util.ArrayList;
import java.util.HashMap;

import businessLayer.CompositeProduct;
import businessLayer.MenuItem;
import businessLayer.Restaurant;
import dataLayer.RestaurantSerializator;
import presentationLayer.Controller;
import presentationLayer.View;

public class TestDrive {

	public static void main(String[] args) {

		Restaurant r = RestaurantSerializator.deserialize(new View());
		//Restaurant r = new Restaurant();
		View v = new View(r);
		Controller c = new Controller(v, r);
		

	}

}

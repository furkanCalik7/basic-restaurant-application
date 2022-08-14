package restaurant;

import restaurant.GUI.LoginFrame;

public class Main {
	public static void main(String[] args) {
		Restaurant restaurant = RestaurantStream.load();
		if(restaurant == null) {
			restaurant = new Restaurant("Gül Bahçesi");
		}
		new LoginFrame(restaurant);
	}
}

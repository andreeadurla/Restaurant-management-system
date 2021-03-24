package Start;

import Business.Restaurant;
import Data.RestaurantSerializator;
import Presentation.AdministratorGUI.AdministratorGUI;
import Presentation.ChefGUI.ChefGUI;
import Presentation.WaiterGUI.WaiterGUI;

public class Start {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Number of arguments is incorrect!");
		}
		else {
			Restaurant restaurant = new Restaurant();
			RestaurantSerializator.setFilename(args[0]);
			restaurant.setItems(RestaurantSerializator.deserialization());
			
			new AdministratorGUI(restaurant);
			new WaiterGUI(restaurant);
			ChefGUI viewChef = new ChefGUI();
			restaurant.addObserver(viewChef);
		}
	}
}

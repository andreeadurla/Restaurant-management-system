package Presentation.WaiterGUI;

import Business.IRestaurantProcessing;

public class WaiterGUI {
	
	public WaiterGUI(IRestaurantProcessing restaurant) {
		ViewWaiter viewWaiter = new ViewWaiter();
		ControllerWaiter contrWaiter = new ControllerWaiter(viewWaiter, restaurant);
		viewWaiter.setVisible(true);
	}
}
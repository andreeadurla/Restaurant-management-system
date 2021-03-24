package Presentation.AdministratorGUI;

import Business.IRestaurantProcessing;

public class AdministratorGUI {
	
	public AdministratorGUI(IRestaurantProcessing restaurant) {
		ViewAdministrator viewAdministrator = new ViewAdministrator();
		ControllerAdministrator contrAdministrator = new ControllerAdministrator(viewAdministrator, restaurant);
		viewAdministrator.setVisible(true);
	}
}

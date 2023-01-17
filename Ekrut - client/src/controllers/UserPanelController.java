package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * UserPanelController is the main controller class for the User Panel.
 * It updates user view screen according to user interactions.
 * It extends AbstractController and overrides methods of it.
 */
public class UserPanelController extends AbstractController{

    @FXML
    private Label errLbl, welcomeLbl;
    
	/**
	 * initialize method is a protected method that is called automatically when the FXML file is loaded.
	 * It sets the welcome label with "Welcome" string and user name.
	 * It displays a proper message if the account has not been approved yet.
	 */
    @FXML
    public void initialize() {
    	String welcome = welcomeLbl.getText() + " ";
    	welcome = myUser.getName() == null ? welcome + myUser.getUsername() : welcome + myUser.getName();
    	welcomeLbl.setText(welcome);
    	if(myUser.getRole().equals("customer")) 
    		errLbl.setText("Your account has not been approved yet.");
    }
    
	@Override
	public void back(MouseEvent event) {
		//Not implemented
	}
	
	@Override
	public void setUp(Object... objects) {
		//Not implemented		
	}

}

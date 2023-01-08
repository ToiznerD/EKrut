package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class UserPanelController extends AbstractController{

    @FXML
    private Label errLbl;
    
    @FXML
    private Label welcomeLbl;
    
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
	public void setUp(Object[] objects) {
		// TODO Auto-generated method stub
		
	}

}

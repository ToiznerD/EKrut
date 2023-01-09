package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CustomerPanelController extends AbstractController{

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMakeOrder;

    @FXML
    private Label welcomeLbl;
    
    @FXML
    public void initialize() {
    	String welcome = welcomeLbl.getText() + " ";
    	welcome = myUser.getName() == null ? welcome + myUser.getUsername() : welcome + myUser.getName();
    	welcomeLbl.setText(welcome);
    }
	@Override
	public void back(MouseEvent event) {
		// not implemented
	}
	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub
		
	}

}

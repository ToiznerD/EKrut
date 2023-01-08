package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CustomerServiceController extends AbstractController{

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnEdit;
    
    @FXML
    private Label welcomeLbl;
    
    @FXML
    public void initialize() throws IOException {
    	//On EK nothing is permitted
    	if(config.equals("EK")) {
    		start("UserPanel", "User Dashboard");
    		return;
    	}
    	
    	String welcome = welcomeLbl.getText() + " ";
    	welcome = myUser.getName() == null ? welcome + myUser.getUsername() : welcome + myUser.getName();
    	welcomeLbl.setText(welcome);
    }
    
    @FXML
    void openCreateMember(ActionEvent event) throws IOException {
    	start("CreateCustomer", "Create new customer");
    }

    @FXML
    void openEdit(ActionEvent event) throws IOException {
    	start("EditUser", "Edit Users");
    }

	@Override
	public void back(MouseEvent event) {
		//Not implemented
	}

	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub
		
	}

}

package controllers;

import java.io.IOException;

import client.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * CustomerServiceController is a controller class for the customer service employee.
 * It extends AbstractController and overrides methods of it.
 */
public class CustomerServiceController extends AbstractController{

    @FXML
    private Button btnCreate, btnEdit;
    
    @FXML
    private Label welcomeLbl;

	/**
	 * initialize method is a protected method that is called automatically when the FXML file is loaded.
	 * It sets the welcome label with "Welcome" string and user name. (Only on OL configuration)
	 */
    @FXML
    public void initialize() throws IOException {
    	//On EK nothing is permitted
    	if(Config.getConfig().equals("EK")) {
    		start("UserPanel", "User Dashboard");
    		return;
    	}
    	
    	String welcome = welcomeLbl.getText() + " ";
    	welcome = myUser.getName() == null ? welcome + myUser.getUsername() : welcome + myUser.getName();
    	welcomeLbl.setText(welcome);
    }
    
    /**
    * Opens the create customer page
    * @param event the button click event
    * @throws IOException when an error occurs loading the fxml file
    */
    @FXML
    void openCreateMember(ActionEvent event) throws IOException {
    	start("CreateCustomer", "Create new customer");
    }

    /**
    * Opens the edit user page
    * @param event the button click event
    * @throws IOException when an error occurs loading the fxml file
    */
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

package controllers;

import java.io.IOException;

import Util.Msg;
import Util.Tasks;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
*
* The CreateCustomerController class is a controller class for the "Create Customer" feature in a JavaFX application.
* It handles the creation of a new customer in the database by validating user input and sending appropriate database
* queries. It also displays error messages to the user if necessary.
*/

public class CreateCustomerController extends AbstractController{

  @FXML
    private Button btnCreate;
  
  @FXML
  private Button btnGetUser;
  
  @FXML
  private Button btnReset;

    @FXML
    private Label errAddress;

    @FXML
    private Label errCC;

    @FXML
    private Label errEmail;

    @FXML
    private Label errName;

    @FXML
    private Label errPhone;


    @FXML
    private Label lblErr;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCC;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtUser;
    
    @FXML
    private CheckBox chkboxSub;
	
    private int id;
    
    private String role;
    
    /**
     * Gets the user information for the given username.
     * Displays an error message if the user is not found or is not a new user.
     */
    public void getUser() {
    		cleanErrors();
    		String username = txtUser.getText();
    		if(username.equals("")) {
    			lblErr.setText("Please fill in a user");
    			return;
    		}
    		
    		// Get the user ID of the user
    		String query2 = String.format("SELECT * FROM users WHERE user = '%s'", username);
    		msg = new Msg(Tasks.Select, query2);
    		sendMsg(msg);
    		
    		if(!msg.getBool()) {
    			lblErr.setText(username + " is not a valid username.");
    			return;
    		}
    		
    		id = msg.getObj(0);
    		role = msg.getObj(3).equals("new_user") ? "customer" : msg.getObj(3);
    		
    		/*if(!msg.getObj(3).equals("new_user")) {
    			lblErr.setText(msg.getObj(1) + " is not a new user!");
    			return;
    		}
    		else
    			lblErr.setText("");*/
    		
    		if(msg.getObj(4) != null)
    			txtName.setText(msg.getObj(4));
    		if(msg.getObj(5) != null)
    			txtPhone.setText(msg.getObj(5));
    		if(msg.getObj(6) != null)
    			txtAddress.setText(msg.getObj(6));
    		if(msg.getObj(7) != null)
    			txtEmail.setText(msg.getObj(7));
    		txtUser.setDisable(true);
    		btnCreate.setDisable(false);
    	}

    /**
     * Resets the form fields and enables the "Create" button.
     */
    public void reset() {
    		cleanErrors();
    		lblErr.setText("");
    		txtUser.setText("");
    		txtName.setText("");
    		txtPhone.setText("");
    		txtAddress.setText("");
    		txtEmail.setText("");
    		txtCC.setText("");
    		chkboxSub.setSelected(false);
    		txtUser.setDisable(false);
    		btnCreate.setDisable(true);
    	}
    
    /**
     * Handles the creation of a new customer. Validates input and adds the customer to the database.
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void customerCreate(ActionEvent event) {
    	cleanErrors();
    	
    	if(txtUser.getText().equals("")) {
    		lblErr.setText("No user has been located.");
    		return;
    	}
    	
    	boolean legit = true;
    	
    	String name = txtName.getText();
    	if(name.equals("")) {errName.setText("Please fill in"); legit = false;}
    	
    	String phone = txtPhone.getText();
    	if(phone.equals("")) {errPhone.setText("Please fill in"); legit = false;}
    	
    	String address = txtAddress.getText();
    	if(address.equals("")) {errAddress.setText("Please fill in"); legit = false;}
    	
    	String email = txtEmail.getText();
    	if(email.equals("")) {errEmail.setText("Please fill in"); legit = false;}
    	
    	String creditcard = txtCC.getText();
    	if(creditcard.equals("")) {errCC.setText("Please fill in"); legit = false;}
    	
    	int subscriber = chkboxSub.isSelected() ? 1 : 0;
    	
    	if(!legit) {
    		return;
    	}
    	//Update user details
    	String query = String.format("UPDATE users SET role='%s', name='%s', phone = '%s', address='%s', email='%s', isLogged=0 WHERE id=%d", role, name, phone, address, email, id);
    	msg = new Msg(Tasks.Update, query);
    	sendMsg(msg);
    	if(msg.getInt() == 0) {lblErr.setText("Something went wrong"); return;}
    	
    	//Add customer to customer table
    	String query3 = String.format("INSERT INTO customer (id, status, subscriber, credit_card) VALUES (%d, '%s', %d, '%s')", id, "Pending", subscriber, creditcard);
    	msg = new Msg(Tasks.Insert, query3);
    	sendMsg(msg);
    	
    	if(msg.getInt() != 0) {
			//Email and SMS SIMULATION
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle("Success");
	        alert.setHeaderText("Customer successfuly registered");
	        alert.setContentText("A confirmation with the details has been sent to: " + phone + ", " + email);
	        alert.showAndWait();
	        
    		btnCreate.setDisable(true);
    	} else
    		lblErr.setText("Something went wrong");

    }
    
    /**
     * Clean up all the error labels
     */
	private void cleanErrors() {
		errName.setText("");
		errPhone.setText("");
		errAddress.setText("");
		errEmail.setText("");
		errCC.setText("");
		lblErr.setText("");
	}
	
	/**
     * Navigates back to the Customer Service screen.
     *
     * @param event The MouseEvent that triggered this method.
     */
    @Override
	public void back(MouseEvent event) {
		try {
			start("CustomerService", "Customer Service");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub
		
	}

}

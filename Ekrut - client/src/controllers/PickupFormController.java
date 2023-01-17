package controllers;

import Util.Msg;
import Util.Tasks;
import client.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * PickupFormController is the controller for handling orders of method: pick up.
 * It extends AbstractController and overrides methods of it.
 */
public class PickupFormController extends AbstractController{

    @FXML
    private Button btnPickup;

    @FXML
    private Label lblRecieverDetails;

    @FXML
    private TextField txtCode;

    /**
     * Initializes the form by setting up the user details.
     */
    @FXML
    public void initialize() {
    	lblRecieverDetails.setText(myUser.getName() + ", " + myUser.getPhone());
    }
    
    /**
    * When the button is clicked, the method checks if the user has entered a code in the provided text field.
    * If the text field is empty or contains an incorrect code, an error message is displayed to the user.
    * A confirmation message is displayed to the user if the order was found, then resets the text field.
    *
    * @param event The ActionEvent that triggered the method call.
    */
    @FXML
    void PickupOrder(ActionEvent event) {
    	if(txtCode.getText().equals("")) {
	    	Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a code !",ButtonType.OK);
	        alert.setTitle("Failed");
	        alert.setHeaderText("Error");
	        alert.showAndWait();
	    	}
    	
    	else {
    		String query = "SELECT o.oid FROM orders o, pickup p WHERE o.oid = p.oid AND o.cid = " + myUser.getId() + " AND o.sid = " + Config.getStore() +
    				" AND o.method = 'Pickup' AND o.ord_status = 'In Progress' AND p.orderCode = '" + txtCode.getText() + "'";
	    	msg = new Msg(Tasks.Select, query);
	    	sendMsg(msg);
	    	if(msg.getBool()) {
	    		query = "UPDATE orders SET ord_status = 'Completed' WHERE oid = " + msg.getObj(0);
	    		msg = new Msg(Tasks.Update, query);
		    	sendMsg(msg);
	    		
	    		//Display a confirmation pop-up message and resetting the fields
				Alert alert = new Alert(Alert.AlertType.NONE, "Enjoy your order !", ButtonType.FINISH);
		        alert.setTitle("Success");
		        alert.showAndWait();
		        
		        txtCode.setText("");
		    }
	    	else {
	    		//Display an error pop-up message
	    		Alert alert = new Alert(Alert.AlertType.ERROR, "No such order in this store !",ButtonType.OK);
		        alert.setTitle("Failed");
		        alert.setHeaderText("Error");
		        alert.showAndWait();
	    	}
    	}
    }

    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     * @throws IOException if there is an issue loading the FXML file
     */
	@Override
	public void back(MouseEvent event) {
		try {
			start("CustomerPanel", "Customer Dashboard");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void setUp(Object... objects) {
		// Not implemented
	}

}

package controllers;

import java.io.IOException;

import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class OrderMethodFormController extends AbstractOrderController {
	private int storeID;
	
	@FXML
    private ComboBox<StringBuilder> lstStore;
	
	@FXML
	private Button btnProceed;
	
    @FXML
    private Label lblAddress;

    @FXML
    private Label lblRecieverDetails;
    
    @FXML
    private Label lblErrStore;
    
    @FXML
    private Label lblErrCity;
    
    @FXML
    private Label lblErrStreet;
    
    @FXML
    private TextField txtCity;
    
    @FXML
    private TextField txtStreet;
    
    @FXML
    private AnchorPane apDetails;
    
    @FXML
    private RadioButton rbPickup;
    
    @FXML
    private RadioButton rbDelivery;
    
    @FXML
    private ProgressBar pb;

    /**
     * Initializes the form by setting up the combo boxes for stores and the user details.
     */
    @FXML
    public void initialize() {
    	//Initializing stores combo-box
    	String query = "SELECT name FROM store";
    	msg = new Msg(Tasks.Select, query);
    	sendMsg(msg);
    	ObservableList<StringBuilder> storesList = FXCollections.observableArrayList(msg.getArr(StringBuilder.class));
    	lstStore.setItems(storesList);
    	
    	//Getting the user details
    	lblRecieverDetails.setText(myUser.getName() + ", " + myUser.getPhone());
    	pb.setProgress(0.33);
    }
    
    /**
     * checkStore checks if the user has selected a store from the list.
     * If no store is selected, an error message is displayed.
     * 
     * @return true if a store is selected, false otherwise
     */
    public boolean checkStore() {
    	if(lstStore.getValue() == null) {
    		lblErrStore.setText("Please choose a store");
    		return false;
    	}
    	lblErrStore.setText("");
    	return true;
    }
    
    /**
     * checkAddress checks if the user has entered the address details.
     * If no, and error message is displayed.
     * 
     * @return
     */
    
    public boolean checkAddress() {
    	boolean flag = true;
    	if(txtCity.getText().equals("")) {
    		lblErrCity.setText("Please enter city");
    		flag = false;
    	}
    	else
    		lblErrCity.setText("");
    	
    	if(txtStreet.getText().equals("")) {
    		lblErrStreet.setText("Please enter street details");
    		flag = false;
    	}
    	else
    		lblErrStreet.setText("");
    	return flag;
    }
    
    /**
     * pickupSelected handles the case when the user has selected the pickup option.
     * It reset and hide of the fields that related to delivery option and make the list of store visible.
     */
    public void pickupSelected() {
    	rbDelivery.setSelected(false);
    	apDetails.setVisible(false);
    	lstStore.setVisible(true);
    	txtCity.setText("");
    	txtStreet.setText("");
    	lblErrCity.setText("");
    	lblErrStreet.setText("");
    }
    
    /**
     * deliverySelected handles the case when the user has selected the delivery option.
     * It reset and hide the fields that related to pickup option and make the user details visible.
     */
    public void deliverySelected() {
    	rbPickup.setSelected(false);
    	apDetails.setVisible(true);
    	lstStore.setValue(null);
    	lstStore.setVisible(false);
    	lblErrStore.setText("");
    }
    
    /**
    * ProceedToOrderbutton called when the user clicks on the "Proceed to Order" button.
    * It checks if either the "Pickup" or "Delivery" option is selected, and if not,
    * it displays an error message to the user.
    * If the "Pickup" option is selected, it checks if a store is selected and then retrieves the store ID to pass to the OrderScreen.
    * If the "Delivery" option is selected, it checks if a date is selected and then proceeds to the OrderScreen.
    */
    public void ProceedToOrderbutton() {
    	if(!rbPickup.isSelected() && !rbDelivery.isSelected()) {
    		//Display an error pop-up message
    		Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose an option",ButtonType.OK);
	        alert.setTitle("Failed");
	        alert.setHeaderText("Error");
	        alert.showAndWait();
    	}
    	if(rbPickup.isSelected() && checkStore())
			try {
				//Getting the store id to pass to order screen(so we'll know which supply of store we need to work on)
				String query = "SELECT sid FROM store WHERE name = '" + lstStore.getValue() + "'";
		    	msg = new Msg(Tasks.Select, query);
		    	sendMsg(msg);
		    	storeID = msg.getObj(0);
				start("OrderScreen", "Order Screen", storeID);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(rbDelivery.isSelected() && checkAddress()) {
			try {
				start("OrderScreen", "Order Screen", 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
	@Override
	public void setUp(Object... objects) {
		super.setUp(objects);
	}

	/**
     * Handles the mouse event of the back button.
     * 
     * @param event the mouse event that triggered this method
     */
	@Override
	public void back(MouseEvent event) {
		try {
			start("CustomerPanel", "Customer Dashboard");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}



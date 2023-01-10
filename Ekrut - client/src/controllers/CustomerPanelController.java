package controllers;

import Util.Msg;
import Util.Tasks;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CustomerPanelController extends AbstractController{
	private boolean subscriber;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMakeOrder;
    
    private Button btnPickup;
    
    @FXML
    private Label welcomeLbl;
    
    @FXML
    public void initialize() {
    	String welcome = welcomeLbl.getText() + " ";
    	welcome = myUser.getName() == null ? welcome + myUser.getUsername() : welcome + myUser.getName();
    	welcomeLbl.setText(welcome);
    	
    	//Get the customer status
		String customerQuery = "SELECT subscriber FROM customer WHERE id = " + myUser.getId();
		msg = new Msg(Tasks.Login, Tasks.CustomerStatus, customerQuery);
		sendMsg(msg);
		
		// OL Config OR Not a subscriber -> hide pickup
		if(config.equals("OL") || !(boolean)msg.getObj(0)) {
			btnPickup.setDisable(true);
			btnPickup.setVisible(false);
		}
    }
	@Override
	public void back(MouseEvent event) {
		// not implemented
	}
	
	public void MakeOrder() {
		try {
			start("OrderMethodForm","Order Method Form", subscriber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setUp(Object... objects) {
		subscriber = (boolean) objects[0];
		
		//if no subscriber, hide pickup option
		btnPickup.setVisible(subscriber);
		btnPickup.setDisable(subscriber);
	}

}

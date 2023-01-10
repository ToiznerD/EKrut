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
    
    @FXML
    private Button btnPickupOrder;

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
		btnPickupOrder.setVisible(subscriber);
		btnPickupOrder.setDisable(subscriber);
	}א

}

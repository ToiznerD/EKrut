package controllers;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import Util.Msg;
import Util.Tasks;
import Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/*
 * Login controller class inheritting from AbstractController
 * This class is responsible to control the Login page
 */
public class LoginController extends AbstractController {

	@FXML
	private Button btnLogin;

	@FXML
	private TextField txtPW;

	@FXML
	private TextField txtUserid;

	@FXML
	private Label errMsgLbl;


	/*
	 * connect method to login to the system
	 * this method responsible to check the login details
	 * @param event
	 */
	public void connect(ActionEvent event) throws IOException {
		//Connect to server
		//Create query based on UI input
		String userid = txtUserid.getText();
		String password = txtPW.getText();

		String query = "SELECT * FROM users WHERE user = '" + userid + "' AND password = " + password;
		msg = new Msg(Tasks.Login, Tasks.Select, query);
		sendMsg(msg);
		myUser = msg.getBool() ? msg.getArr(User.class).get(0) : null;
		
		if(myUser != null) {
			if (!myUser.isLogged()) {
				String role = myUser.getRole();
				
				//Update isLogged
				String loginQuery = "UPDATE users SET IsLogged = 1 WHERE id = " + myUser.getId();
				msg = new Msg(Tasks.Login, Tasks.Update, loginQuery);
				sendMsg(msg);
				
				switch (role) {
				case "customer":
					start("CustomerPanel", "Customer Dashboard");
					break;
				case "service":
					start("CustomerService", "Customer Service Dashboard");
					break;
				case "market_manager":
					start("MarketingManagerPanel", "Market manager Dashboard");
					break;
				case "region_manager":
					start("RegionManagerMainScreen", "Region Manager Dashboard");
					break;
				case "marketing_employee":
					start("MarketingEmployeePanel", "Marketing Employee Dashboard");
					break;
				default:
					break;
				}
			} else {
				errMsgLbl.setText(userid + " is already logged in");
			}
		}
		else {
			errMsgLbl.setText("Wrong Details");
		}
	}
	
	public void ConnectWithApp(ActionEvent event) {
		File file = new File("config.txt");
	    boolean exists = file.exists();
	    if(!exists) {
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    	alert.setTitle("Popup Message");
	    	alert.setHeaderText("Choose between EK and OL");

	    	ButtonType buttonEK = new ButtonType("EK");
	    	ButtonType buttonOL = new ButtonType("OL");

	    	alert.getButtonTypes().setAll(buttonEK, buttonOL);

	    	Optional<ButtonType> result = alert.showAndWait();

	    	if (result.get() == buttonEK) {
	    	    // EK was chosen
	    		System.out.println("EK");
	    	} else if (result.get() == buttonOL) {
	    	    // OL was chosen
	    		System.out.println("OL");
	    	}
	    }
	}

	@Override
	public void back(MouseEvent event) {
		//Not implemented
	}
	
}


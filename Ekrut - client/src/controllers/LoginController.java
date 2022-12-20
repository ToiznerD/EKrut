package controllers;

import java.io.IOException;
import java.util.ArrayList;

import Util.Tasks;
import client.ClientBackEnd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/*
 * Login controller class inheritting from AbstractController
 * This class is responsible to control the Login page
 */
public class LoginController extends AbstractController{

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPW;

    @FXML
    private TextField txtUserid;
    
    @FXML
    private Label errMsgLbl;
    
    public static boolean result;
    
    public static String role;
    
    public static Object monitor = new Object();

	
	
	/*
	 * connect method to login to the system
	 * this method responsible to check the login details
	 * @param event
	 */
	public void connect(ActionEvent event) throws IOException {
		//Connect to server
		ClientBackEnd clientBackEnd = new ClientBackEnd("127.0.0.1", 5555);
		setClientBackEnd(clientBackEnd);
		
		//Create query based on UI input
    	String userid = txtUserid.getText();
    	String password = txtPW.getText();
    	String query = "SELECT * FROM users WHERE name = '" + userid + "' AND pass = " + password;
    	
    	//Build task to server
    	ArrayList<Object> task = new ArrayList<>();
    	task.add(Tasks.Login);
    	task.add(query);
    	
    	try {
			clientBackEnd.handleMessageFromClientUI(task); //Send task to server
			
			synchronized(monitor) {
				monitor.wait(); //Wait for server response

				if(result) {
			    	switch(role) {
			    		case "customer":
			    			start(event, "CustomerPanel", "Customer Dashboard");
			    			break;
			    		default:
			    			break;
			    	}
				}
				else {
					errMsgLbl.setText("Wrong Details");
				}
			}
    	} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
    }

}

package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import Util.Msg;
import Util.Tasks;
import Util.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

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


	public static boolean result;

	public static String role;
	
	private String userid;
	
	private String password;
	
	public void regulerLogin(ActionEvent event) throws IOException {
		userid = txtUserid.getText();
		password = txtPW.getText();
		connect(event);
	}

	/*
	 * connect method to login to the system
	 * this method responsible to check the login details
	 * @param event
	 */
	public void connect(ActionEvent event) throws IOException {
		//Connect to server
		//Create query based on UI input
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
				case "marketmanager":
					start("MarketingManagerPanel", "Market manager dashboard");
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
	
	public void ConnectWithApp(ActionEvent event) throws IOException {
		File file = new File("config.txt");
	    boolean exists = file.exists();
	    if(!exists) {
	    	
	    	
	    	String username = "";
	    	String password = "";
	    	String config = "OL";
	    	Pair<String, String> result = getLoginDetails();
	    	if(result != null) {
	    		username = result.getKey();
	    	    password = result.getValue();
	    	}
	    	String configString = String.format("Configuration=%s\nUsername=%s\nPassword=%s", config, username, password);
	    	RandomAccessFile raf = new RandomAccessFile(file, "rw");
	    	raf.writeUTF(configString);
	    	raf.close();
	    	this.userid = username;
	    	this.password = password;
	    	connect(event);
	    }
	    else {
	    	try {
				Scanner scanner = new Scanner(file);
				String user = "", password = "", config = "";
				while (scanner.hasNextLine()) {
			        String line = scanner.nextLine();
			        if(line.contains("Configuration=")) {
			        	String[] configLine = line.split("=");
			        	config = configLine[1];
			        }
			        if (line.contains("Username=")) {
			            String[] userLine = line.split("=");
			            user = userLine[1];
			        }
			        if(line.contains("Password=")) {
			        	String[] passwordLine = line.split("=");
			        	password = passwordLine[1];
			        }
			    }
				scanner.close();
				if(!user.equals("") && !password.equals("")) {
					this.userid = user;
					this.password = password;
					connect(event);
				}
				else {
					Pair<String, String> result = getLoginDetails();
			    	if(result != null) {
			    		user = result.getKey();
			    	    password = result.getValue();
			    	}
			    	String configString = String.format("Configuration=%s\nUsername=%s\nPassword=%s", config, user, password);
			    	RandomAccessFile raf = new RandomAccessFile(file, "rw");
			    	raf.writeUTF(configString);
			    	raf.close();
			    	this.userid = user;
			    	this.password = password;
			    	connect(event);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	    	
	    }
	}

	private Pair<String, String> getLoginDetails() {
	    Dialog<Pair<String, String>> loginDialog = new Dialog<>();
	    loginDialog.setTitle("Login");

	    // Set the button types.
	    ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
	    loginDialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

	    // Create the username and password labels and fields.
	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(20, 150, 10, 10));

	    TextField username = new TextField();
	    username.setPromptText("Username");
	    PasswordField password = new PasswordField();
	    password.setPromptText("Password");

	    grid.add(new Label("Username:"), 0, 0);
	    grid.add(username, 1, 0);
	    grid.add(new Label("Password:"), 0, 1);
	    grid.add(password, 1, 1);

	    // Enable/Disable login button depending on whether a username was entered.
	    Node loginButton = loginDialog.getDialogPane().lookupButton(loginButtonType);
	    loginButton.setDisable(true);

	    // Do some validation (using the Java 8 lambda syntax).
	    username.textProperty().addListener((observable, oldValue, newValue) -> {
	        loginButton.setDisable(newValue.trim().isEmpty());
	    });

	    loginDialog.getDialogPane().setContent(grid);

	    // Request focus on the username field by default.
	    Platform.runLater(() -> username.requestFocus());

	    // Convert the result to a username-password-pair when the login button is clicked.
	    loginDialog.setResultConverter(dialogButton -> {
	        if (dialogButton == loginButtonType) {
	            return new Pair<>(username.getText(), password.getText());
	        }
	        return null;
	    });

	    Optional<Pair<String, String>> result = loginDialog.showAndWait();

	    if (result.isPresent()) {
	        return result.get();
	    } else {
	        return null;
	    }
	}

	@Override
	public void back(MouseEvent event) {
		//Not implemented
	}
	
}


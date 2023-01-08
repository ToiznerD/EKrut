package controllers;
import java.io.IOException;
import java.util.List;

import Entities.User;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * The EditUserController class is responsible for handling user input and updating user data in the database.
 * It provides methods for loading a user's data from the database and displaying it in the text fields,
 * and for updating a user's data in the database based on the data in the text fields.
 * It also provides a method for navigating back to the Customer Service screen.
 */
public class EditUserController extends AbstractController{

    @FXML

    private TableColumn<User, String> address_col;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<User, String> email_col;

    @FXML
    private TableColumn<User, Integer> id_col;

    @FXML
    private TableColumn<User, String> name_col;

    @FXML
    private TableColumn<User, String> password_col;

    @FXML
    private TableColumn<User, String> phone_col;

    @FXML
    private TableColumn<User, String> role_col;

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtUser;
    
    @FXML
    private Label errLbl;

    @FXML
    private TableColumn<User, String> user_col;
    
    /**
     * Loads a user's data from the database and displays it in the text fields.
     *
     * @param event The ActionEvent that triggered this method.
     */

    @FXML
    void loadUser(ActionEvent event) {
    	errLbl.setText("");
    	
    	String stringID = txtID.getText();
    	if(stringID.equals("")) {
    		errLbl.setText("Please enter ID");
    		return;
    	}
    	
    	int id = Integer.parseInt(stringID);
    	
    	String query = "Select * FROM Users WHERE id = " + id;
    	msg = new Msg(Tasks.Select, query);
    	sendMsg(msg);
    	
    	if(!msg.getBool()) {
    		errLbl.setText(id + " is invalid");
    		return;
    	}
    		
    	txtUser.setText(msg.getObj(1));
    	txtPassword.setText(msg.getObj(2));
    	txtRole.setText(msg.getObj(3));
    	txtName.setText(msg.getObj(4));
    	txtPhone.setText(msg.getObj(5));
    	txtAddress.setText(msg.getObj(6));
    	txtEmail.setText(msg.getObj(7));
    	
    	txtID.setDisable(true);
    	txtUser.setDisable(true);
    	btnUpdate.setDisable(false);
    }
    

    /**
     * Updates a user's data in the database based on the data in the text fields.
     * Displays an error message if the update was unsuccessful.
     *
     * @param event The ActionEvent that triggered this method.
     */
    public void updateUser(ActionEvent event) {
    		// Get the user data from the text fields
    		int id = Integer.parseInt(txtID.getText());
    		String username = txtUser.getText();
    		String password = txtPassword.getText();
    		String role = txtRole.getText();
    		String name = txtName.getText();
    		String phone = txtPhone.getText();
    		String address = txtAddress.getText();
    		String email = txtEmail.getText();
    		
    		// Build the update query
    		String query = String.format("UPDATE users SET user = '%s', password = '%s', role = '%s', name = '%s', phone = '%s', address = '%s', email = '%s' "
    				+ "WHERE id = %d", username, password, role, name, phone, address, email, id);
    		
    		// Send the update request to the app
    		msg = new Msg(Tasks.Update, query);
    		sendMsg(msg);
    		
    		// Check if the update was successful
    		if(msg.getInt() == 0) {
    			errLbl.setText("Something went wrong");
    			return;
    		}
    		
    		// Reset the form fields and refresh the data
    		reset();
    		initialize();
    	}

    /**
     * Resets the form fields and enables the "Update" button.
     */
    public void reset() {
    	errLbl.setText("");
    	txtID.setText("");
    	txtUser.setText("");
    	txtPassword.setText("");
    	txtRole.setText("");
    	txtName.setText("");
    	txtPhone.setText("");
    	txtAddress.setText("");
    	txtEmail.setText("");
    	txtUser.setDisable(false);
    	txtID.setDisable(false);
    	btnUpdate.setDisable(true);
    }
    
    @FXML
    /**
     * Initialize the TableView filled with all the users
     */
    public void initialize() {
    	onLoad();
    }
    
    /**
     * Loads the user data into the table.
     */
    public void onLoad() {
    	msg = new Msg(Tasks.Select, "SELECT * FROM Users");
    	sendMsg(msg);
    	List<User> userList = msg.getArr(User.class);
    	ObservableList<User> userOBList = FXCollections.observableArrayList();
    	userOBList.addAll(userList);
    	
    	id_col.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
    	user_col.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
    	password_col.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
    	role_col.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
    	name_col.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
    	phone_col.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
    	address_col.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
    	email_col.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
    	tableUsers.setItems(userOBList);
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
	public void setUp(Object[] objects) {
		// TODO Auto-generated method stub
		
	}
}

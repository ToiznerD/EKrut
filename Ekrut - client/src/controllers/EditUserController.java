package controllers;
import java.io.IOException;
import java.util.List;

import Util.Msg;
import Util.Tasks;
import Util.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditUserController extends AbstractController{

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<User, Integer> id_col;

    @FXML
    private TableColumn<User, String> password_col;

    @FXML
    private TableColumn<User, String> role_col;

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtUser;

    @FXML
    private TableColumn<User, String> user_col;

    @FXML
    void loadUser(ActionEvent event) {
    	int id = Integer.parseInt(txtID.getText());
    	String query = "Select * FROM Users WHERE id = " + id;
    	msg = new Msg(Tasks.Select, query);
    	sendMsg(msg);
    	
    	txtUser.setText(msg.getObj(1));
    	txtPassword.setText(msg.getObj(2));
    	txtRole.setText(msg.getObj(3));
    }

    @FXML
    void updateUser(ActionEvent event) {
    	int id = Integer.parseInt(txtID.getText());
    	String username = txtUser.getText();
    	String password = txtPassword.getText();
    	String role = txtRole.getText();
    	String query = "UPDATE users SET user = '" + username + "', password = '" + password + "', role = '" + role + "'"
    			+ " WHERE id = " + id;
    	msg = new Msg(Tasks.Update, query);
    	sendMsg(msg);
    	initialize();
    }
    
    @FXML
    public void initialize() {
    	onLoad();
    }
    
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
    	tableUsers.setItems(userOBList);
    }

	@Override
	public void back() {
		try {
			start("CustomerService", "Customer Service");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

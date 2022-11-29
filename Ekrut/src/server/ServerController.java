package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import DBHandler.ServerConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ServerController {
	private EKServer sv;
	private Connection conn;
    @FXML
    private Button btnConnect;

    @FXML
    private Button btnImport;

    @FXML
    private TableView<?> connected_table;

    @FXML
    private TextArea console_textbox;

    @FXML
    private TextField db_name;

    @FXML
    private PasswordField db_password;

    @FXML
    private TextField db_user;

    @FXML
    private TableColumn<?, ?> host_col;

    @FXML
    private TextField ip;

    @FXML
    private TableColumn<?, ?> ip_col;

    @FXML
    private TextField port;

    @FXML
    private TableColumn<?, ?> status_col;
    
    public void connectToServer() {
    	
      	//Start DB Connection
		//Set connection parameters
		StringBuilder sb = new StringBuilder("jdbc:mysql://");
		sb.append(ip.getText() + "/");
		sb.append(db_name.getText() + "?serverTimezone=IST");
		ServerConnection.setDB_Path(sb.toString());
		ServerConnection.setDB_User(db_user.getText());
		ServerConnection.setDB_Password(db_password.getText());
		ServerConnection.setSC(this);
    	
    	//Start server
    	sv = new EKServer(5555, this);
    	try {
			sv.listen();
			appendConsole("Server is up.");
		} catch (IOException e) {
			appendConsole("Server fail");
			e.printStackTrace();
		}
    	
  
		
		//Get database connection
		conn = ServerConnection.getConnection();
	
    }
    
    public void appendConsole(String str) {
    	console_textbox.appendText(str + "\n");
    }

}

package server;

import java.io.IOException;
import java.sql.SQLException;

import DBHandler.DBController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import server.EKServer.clientConnectionData;

public class ServerController {
	private EKServer sv;

	@FXML
	private Button btnConnect;

	@FXML
	private Button btnDisconnect;

	@FXML
	private Button btnImport;

	@FXML
	private TableView<clientConnectionData> connected_table;

	@FXML
	private TextArea console_textbox;

	@FXML
	private TextField db_name;

	@FXML
	private PasswordField db_password;

	@FXML
	private TextField db_user;

	@FXML
	private TableColumn<clientConnectionData, String> host_col;

	@FXML
	private TextField ip;

	@FXML
	private TableColumn<clientConnectionData, String> ip_col;

	@FXML
	private TextField port;

	@FXML
	private TableColumn<clientConnectionData, String> status_col;
	private final ObservableList<clientConnectionData> connectedObserv = FXCollections.observableArrayList();

	public void connectToServer() {

		// Start DB Connection
		// Set connection parameters
		StringBuilder sb = new StringBuilder("jdbc:mysql://");
		sb.append(ip.getText() + "/");
		sb.append(db_name.getText() + "?serverTimezone=IST");
		DBController.setDB_Path(sb.toString());
		DBController.setDB_User(db_user.getText());
		DBController.setDB_Password(db_password.getText());



		try {
			if (sv == null) {
				sv = new EKServer(5555, this);
				// Start server
				sv.listen();
				appendConsole("Server is up.");
			}
			
			//If listen failed, try again
			if(sv != null && !sv.isListening()) {
				sv.listen();
			}

			// Start DB connection
			DBController.connection();
			appendConsole("Driver definition succeed.");
			appendConsole("Database connected successfully.");
			
			//Disable "Connect" button & Enable "Disconnect" button
			btnConnect.setDisable(true);
			btnDisconnect.setDisable(false);

		} catch (IOException e) {
			appendConsole("Server fail.");
			e.printStackTrace();
		} catch (SQLException e) {
			appendConsole("Database connection failed.");
			e.printStackTrace();
		} catch (Exception e) {
			appendConsole("Driver definition failed.");
			e.printStackTrace();
		}

	}

	public void disconnectFromServer() {
		try {
			//Close listening
			sv.close();
			
			//Close DB connection
			DBController.dropConnection();
			
			//Enable "Connect" button, disable "Disconnect" button
			btnDisconnect.setDisable(true);
			btnConnect.setDisable(false);
			
			appendConsole("Driver definition aborted");
			appendConsole("DB connection is down");
			appendConsole("Server is down");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void appendConsole(String str) {
		console_textbox.appendText(str + "\n");
	}

	protected void fillUserTableView(clientConnectionData connected) {
		connectedObserv.add(connected);
		host_col.setCellValueFactory(new PropertyValueFactory<>("hostName"));
		ip_col.setCellValueFactory(new PropertyValueFactory<>("ip"));
		status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
		connected_table.setItems(connectedObserv);
	}

	protected void removeUserFromTable(clientConnectionData connected) {
		connected_table.getItems().remove(connected);
	}

}

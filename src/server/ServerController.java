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
import server.serverBackEnd.clientConnectionData;

public class ServerController {
	private serverBackEnd sv;

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
	private ObservableList<clientConnectionData> connectedObserv = FXCollections.observableArrayList();

	public void connectToServer() {

		// Start DB Connection
		// Set connection parameters
		DBController.setDB_Path(ip.getText(),db_name.getText());
		DBController.setDB_User(db_user.getText());
		DBController.setDB_Password(db_password.getText());
		initTable();
		
		sv = new serverBackEnd(5555, this);
		try {
			// Start server
			sv.listen();
			appendConsole("Server is up.");

			// Start DB connection
			DBController.connection();
			appendConsole("Driver definition succeed.\nDatabase connected successfully.");

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
		closeConnection();
		DBController.dropConnection();
		btnDisconnect.setDisable(true);
		btnConnect.setDisable(false);
		appendConsole("Driver definition aborted\nDB connection is down\nServer is down");
	}

	public void appendConsole(String str) {
		console_textbox.appendText(str + "\n");
	}
	protected void initTable() {
		host_col.setCellValueFactory(new PropertyValueFactory<clientConnectionData, String>("hostName"));
		ip_col.setCellValueFactory(new PropertyValueFactory<clientConnectionData, String>("ip"));
		status_col.setCellValueFactory(new PropertyValueFactory<clientConnectionData, String>("status"));
		connected_table.setItems(connectedObserv);
	}
	protected void fillUserTableView(clientConnectionData connected) {
		connectedObserv.add(connected);
	}

	protected void removeUserFromTable(clientConnectionData connected) {
		connectedObserv.remove(connected);
	}

	public void closeConnection() {
		try {
			sv.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}

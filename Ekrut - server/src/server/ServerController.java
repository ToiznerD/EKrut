package server;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import DBHandler.DBController;
import javafx.beans.property.ReadOnlyStringWrapper;
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

public class ServerController {
	private serverBackEnd sv;

	@FXML
	private Button btnConnect;

	@FXML
	private Button btnDisconnect;

	@FXML
	private Button btnImport;

	@FXML
	private TableView<InetAddress> connected_table;

	@FXML
	private TextArea console_textbox;

	@FXML
	private TextField db_name;

	@FXML
	private PasswordField db_password;

	@FXML
	private TextField db_user;

	@FXML
	private TableColumn<InetAddress, String> host_col;

	@FXML
	private TextField ip;

	@FXML
	private TableColumn<InetAddress, String> ip_col;

	@FXML
	private TextField port;

	@FXML
	private TableColumn<InetAddress, String> status_col;
	private ObservableList<InetAddress> connectedObserv = FXCollections.observableArrayList();

	public void connectToServer() {

		// Start DB Connection
		// Set connection parameters
		DBController.setDB_prop(ip.getText(),db_name.getText(),db_user.getText(),db_password.getText());
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

	public void disconnectFromServer() throws IOException {
		sv.close();
		DBController.dropConnection();
		btnDisconnect.setDisable(true);
		btnConnect.setDisable(false);
		appendConsole("Driver definition aborted\nDB connection is down\nServer is down");
	}

	public void appendConsole(String str) {
		console_textbox.appendText(str + "\n");
	}
	@FXML
	protected void initialize() {
		host_col.setCellValueFactory(new PropertyValueFactory<InetAddress, String>("HostName"));
		ip_col.setCellValueFactory(new PropertyValueFactory<InetAddress, String>("HostAddress"));
		status_col.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("Connected"));
		connected_table.setItems(connectedObserv);
	}
	protected void addConnected(InetAddress connected) {
		connectedObserv.add(connected);
	}

	protected void removeConnected(InetAddress connected) {
		connectedObserv.remove(connected);
	}

	public void closeConnection() {
		try {
			if (sv.isListening())
				sv.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}

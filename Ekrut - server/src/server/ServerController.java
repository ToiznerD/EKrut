package server;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import DBHandler.DBController;
import Util.Msg;
import Util.Tasks;
import Utils.PaymentCollector;
import Utils.ReportGenerator;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tasker.Tasker;

/**
 * ServerController class is the main controller class for the ServerApp.
 * It handles server connect and disconnect actions, users import and console appendage (From GUI)
 */
public class ServerController {
	private ObservableList<InetAddress> connectedObserv = FXCollections.observableArrayList();
	private serverBackEnd sv;

	@FXML
	private Button btnConnect, btnDisconnect, btnImport;

	@FXML
	private TableView<InetAddress> connected_table;

	@FXML
	private TextArea console_textbox;

	@FXML
	private TextField db_name, db_user, ip, port;

	@FXML
	private PasswordField db_password;


	@FXML
	private TableColumn<InetAddress, String> host_col, ip_col, status_col;

	/**
	* Connects to the server by creating a serverBackEnd object and connects to mysql DB.
	* It appends to server console the appropriate message.
	*/
	public void connectToServer() {

		DBController.setDB_prop(ip.getText(),db_name.getText(),db_user.getText(),db_password.getText());
		sv = new serverBackEnd(5555, this);
		try {

			// Start DB connection
			DBController.connection();
			appendConsole("Driver definition succeed.\nDatabase connected successfully.");
			// Start server
			sv.listen();
			appendConsole("Server is up.");
			
			btnConnect.setDisable(true);
			btnDisconnect.setDisable(false);

		} catch (IOException e) {
			appendConsole("Server fail.");
			e.printStackTrace();
		} catch (SQLException e) {
			appendConsole("Database connection failed.\npassword might be wrong.");
		} catch (Exception e) {
			appendConsole("Driver definition failed.");
			e.printStackTrace();
		}
		Scheduler();
	}

	/**
	* Schedules a ReportGenerator to run on the first day of the next month with Timer object(runs once).
	*/
	private void Scheduler() {
		Timer timer = new Timer();

		Calendar calendar = Calendar.getInstance();
		// add 1 month so that the first report generated will be from the 1st of next month
		calendar.add(Calendar.MONTH,1);

		// set day at 1st
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date firstTime = calendar.getTime();
		timer.schedule(new ReportGenerator(), firstTime);
		timer.schedule(new PaymentCollector(), firstTime);
	}

	/**
	* Disconnects from the server and drops the connection to mysql DB and appends a message to server console.
	* @throws IOException if an error occurs when closing the server
	*/
	public void disconnectFromServer() throws IOException {
		sv.close();
		DBController.dropConnection();
		btnDisconnect.setDisable(true);
		btnConnect.setDisable(false);
		appendConsole("Driver definition aborted\nDB connection is down\nServer is down");
	}
	
	/**
	* Appends a string to the server console.
	* @param str String to be appended to console.
	*/
	public void appendConsole(String str) {
		console_textbox.appendText(str + "\n");
	}
	
	/**
	* Initializes the connected client table (when server app is up)
	*/
	@FXML
	protected void initialize() {
		host_col.setCellValueFactory(new PropertyValueFactory<InetAddress, String>("HostName"));
		ip_col.setCellValueFactory(new PropertyValueFactory<InetAddress, String>("HostAddress"));
		status_col.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("Connected"));
		connected_table.setItems(connectedObserv);
	}
	
	/**
	* Adds the given IP to connectedObserv
	* @param connected The InetAddress IP to be added
	*/
	protected void addConnected(InetAddress connected) {
		connectedObserv.add(connected);
	}

	/**
	* Removes the given IP from connectedObserv
	* @param connected The InetAddress IP to be removed
	*/
	protected void removeConnected(InetAddress connected) {
		connectedObserv.remove(connected);
	}

	/**
	* Closes the server connection and exits the program.
	*/
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
	
	/**
	* Performs server back end Utility import.
	* @param event ActionEvent that triggers the action
	*/
	public void importUsers(ActionEvent event) {
		if(sv != null) {
			if(sv.importUsers()) 
				appendConsole("Users has been imported successfuly.");
			else
				appendConsole("Import has failed.");
		}
		else
			appendConsole("Import has failed. Before importing users, connect to Database");
	}
}

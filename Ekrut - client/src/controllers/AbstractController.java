package controllers;

import java.io.IOException;

import Entities.User;
import Util.Msg;
import Util.Tasks;
import client.ClientBackEnd;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Super abstract class for all controllers All the controllers inherit from
 * this class and the method start
 */
public abstract class AbstractController {
	public static Stage prStage;
	public static Object monitor = new Object();
	public static Msg msg;
	public static User myUser;

	/**
	 * starting a new scene in the stage we are on. handles disconnection of client, disconnects him from server.
	 * @param fxml - fxml filename to load
	 * @param title - Title for the stage
	 * @param objects - multiple params that can
	 * @throws IOException
	 */
	public void start(String fxml, String title, Object... objects) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource("/fxml/" + fxml + ".fxml"));
		Parent root = load.load();
		AbstractController controller = load.getController();
		controller.setUp(objects);
		Scene scene = new Scene(root);
		prStage.setTitle("Ekrut" + " " + title);
		prStage.setScene(scene);

		if (fxml != "ConnectionConfig") {
			prStage.setOnCloseRequest(event -> {
				logoutFromDb();
				ClientBackEnd.getInstance().quit();
				System.exit(0);
			});
		}
		prStage.setResizable(false);
		prStage.show();
	}

	/**
	 * wait method, mainly used for waiting for a server response
	 */
	public void Wait() { // Nave
		try {
			synchronized (monitor) {
				monitor.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sends the instance to <code>ClientBackEnd</code> to send it over to the server
	 * @param msg - Msg object containing message details
	 */
	public void sendMsg(Msg msg) { // Nave
		try {
			ClientBackEnd.getInstance().handleMessageFromClientUI(msg);
			if (msg.getTask() != Tasks.popUp)// erik
				Wait();
		} catch (Exception e) {
			e.printStackTrace();
		} // Send task to server
	}

	/**
	 * wakes up the waiting thread
	 */
	public static void Notify() { // Nave
		synchronized (monitor) {
			monitor.notify();
		}
	}

	/**
	 * send a query to db to log out
	 */
	public void logoutFromDb() {
		if (myUser != null) {
			msg = new Msg(Tasks.Logout);
			sendMsg(msg);
			myUser = null;
		}
	}

	/**
	 * raises popUp alert
	 * @param msg - A String object to set the popeUp content
	 */
	public static void popupAlert(String msg) { // ERIK
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.initOwner(prStage);
		alert.setTitle("info");
		alert.setContentText(msg);
		alert.showAndWait();
		Thread.currentThread().interrupt();
	}

	/**
	 * method that executes popUp task in a new thread
	 * @param msg - String to pass for the popUp alert
	 */
	public static void waitForAlert(String msg) { //erik
		new Thread(new Runnable() {
		    @Override public void run() {
		        Platform.runLater(new Runnable() {
		            @Override public void run() {
		            	popupAlert(msg);
		            }
		        });
		    }
		}).start();
	}

	/**
	 * call <code>logoutFromDb()</code> to disconnect from db
	 * load the LoginForm scene with the <code>start()</code> method
	 */
	public void logout() {
		logoutFromDb();
		try {
			start("LoginForm", "Login");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * abstract method to setUp the scene
	 * @param objects - can receive multiple objects
	 */
	public abstract void setUp(Object... objects);

	/**
	 * abstract method to go back to previous screen
	 * @param event - MouseEvent object
	 */
	public abstract void back(MouseEvent event);

}

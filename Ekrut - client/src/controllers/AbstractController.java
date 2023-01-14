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

	public void Wait() { // Nave
		try {
			synchronized (monitor) {
				monitor.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendMsg(Msg msg) { // Nave
		try {
			ClientBackEnd.getInstance().handleMessageFromClientUI(msg);
			if (msg.getTask() != Tasks.popUp)// erik
				Wait();
		} catch (Exception e) {
			e.printStackTrace();
		} // Send task to server
	}

	public static void Notify() { // Nave
		synchronized (monitor) {
			monitor.notify();
		}
	}

	public void logoutFromDb() {
		if (myUser != null) {
			msg = new Msg(Tasks.Logout);
			sendMsg(msg);
			myUser = null;
		}
	}
	
	public static void popupAlert(String msg) { //ERIK
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.initOwner(prStage);
		alert.setTitle("info");
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
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
	
	public void logout() {
		logoutFromDb();
		try {
			start("LoginForm", "Login");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void setUp(Object... objects);

	public abstract void back(MouseEvent event);

}

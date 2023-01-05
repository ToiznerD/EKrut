package controllers;

import java.io.IOException;

import Entities.User;
import Util.Msg;
import Util.Tasks;
import client.ClientBackEnd;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Super abstract class for all controllers
 * All the controllers inherit from this class and the method start
 */
public abstract class AbstractController {
	public static Stage prStage;
	public static Object monitor = new Object();
	public static Msg msg;
	public static User myUser;
	public static String config;
	public static int store;
	public void start(String fxml, String title) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource("/fxml/" + fxml + ".fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);

		prStage.setTitle("Ekrut" + " " + title);
		prStage.setScene(scene);
		prStage.setResizable(false);
		if (fxml != "ConnectionConfig")
			prStage.setOnCloseRequest(event -> {
				ClientBackEnd.getInstance().quit();
			});
		ClientBackEnd.setAbstractController(load.getController());
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

	public void sendMsg(Msg msg) { //Nave
		try {
			ClientBackEnd.getInstance().handleMessageFromClientUI(msg);
			Wait();
		} catch (Exception e) {
			e.printStackTrace();
		} //Send task to server
	}

	public static void Notify() { //Nave
		synchronized (monitor) {
			monitor.notifyAll();
		}
	}
	
	public void logout() throws IOException {
		String logoutQuery = "UPDATE users SET isLogged = 0 WHERE id = " + myUser.getId();
		msg = new Msg(Tasks.Logout, logoutQuery);
		sendMsg(msg);
		myUser = null;
		start("LoginForm", "Login");
	}
	
	public abstract void back(MouseEvent event);

}

package controllers;

import java.io.IOException;
import java.util.ArrayList;

import Util.Tasks;
import client.ClientBackEnd;
import javafx.fxml.FXML;
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
	public static Scene prScene, backScene;
	public static Object monitor = new Object();

	@FXML
	public void back(MouseEvent event) { //Nave
		prStage.setScene(backScene);
	}

	public void switchScreen(String fxml, String title) throws IOException {
		backScene = prStage.getScene();
		FXMLLoader load = new FXMLLoader(getClass().getResource("/fxml/" + fxml + ".fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);
		prScene = scene;
		prStage.setTitle("Ekrut" + " " + title);
		prStage.setScene(scene);
		prStage.setOnCloseRequest(event -> {
			ClientBackEnd.getInstance().quit();
		});//Nave
	}

	public void start(String fxml, String title) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource("/fxml/" + fxml + ".fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);

		prStage.setTitle("Ekrut" + " " + title);
		prStage.setScene(scene);
		prStage.setResizable(false);
		prStage.show();
	}
	public void Wait() {//Nave
		try {
		synchronized (monitor) {
				monitor.wait();
		}}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    public void sendQuery(Tasks task,String query) {		//Nave
    	ArrayList<Object> taskObj = new ArrayList<>();
    	taskObj.add(task);
    	taskObj.add(query);
		try {
			ClientBackEnd.getInstance().handleMessageFromClientUI(taskObj);
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
}

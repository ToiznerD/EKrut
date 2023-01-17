package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * ServerApp class is starting the server app.
 * It extends Application class.
 * It loads FXML file : ServerGUI and sets it controller : ServerController
 */
public class ServerApp extends Application {
	
	/**
	 * override of start method of Application class.
	 * setOnCloseRequest used when user closes the window: it will call
	 * closeConnection method of ServerController to perform propper exit
	 * @param primaryStage stage of the server application
	 * @throws Exception on error while loading the FXML file.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader serverGui = new FXMLLoader(getClass().getResource("ServerGUI.fxml"));
		Parent parent = serverGui.load();
		ServerController control = serverGui.getController();
		
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> control.closeConnection());
		primaryStage.setTitle("EKrut Server");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	/**
	* main method to launch the application.
	* @param args command line arguments
	*/
	public static void main(String[] args) {
		launch(args);
	}
}

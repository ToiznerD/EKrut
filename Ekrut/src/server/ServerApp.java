package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerApp extends Application{
	//sdfsdf
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("ServerGUI.fxml"));
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("EKrut Server");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerApp extends Application {
	//sdfsdf
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader serverGui = new FXMLLoader(getClass().getResource("ServerGUI.fxml"));
		Parent parent = serverGui.load();
		ServerController control = serverGui.getController();
		
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> control.closeConnection());
		primaryStage.setTitle("EKrut Server");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

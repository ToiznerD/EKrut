package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApp extends Application {
	public static Stage prStage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		prStage = primaryStage;
		//FXMLLoader clientGui = new FXMLLoader(getClass().getResource("ClientGui.fxml"));
		//ClientController control = clientGui.getController();
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("/clientFxml/ServerConnect.fxml"));
		Scene scene = new Scene(root);
		//primaryStage.setOnCloseRequest(e -> control.disconnect());
		primaryStage.setScene(scene);
		primaryStage.setTitle("EKrut Client");
		primaryStage.show();
		
	}
	
	
	public static Stage getStage() {
		return ClientApp.prStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}

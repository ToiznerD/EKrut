package clientControllers;
import java.io.IOException;

import client.ClientApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainController {
	
	public void setScene(String path) {
		Stage stage = ClientApp.getStage();
		stage.setScene(newScene(path));
	}
	public Scene newScene(String path) {
		Parent root;
		try {
			root = (Parent) FXMLLoader.load(getClass().getResource("/clientFxml/"+path+".fxml"));
			return new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}

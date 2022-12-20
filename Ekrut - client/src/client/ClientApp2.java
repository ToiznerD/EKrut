package client;

import controllers.AbstractController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

public class ClientApp2 extends Application {
	public static Stage prStage;

	public static Scene prScene,backScene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		prStage = primaryStage;
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("/fxml/ConnectionConfig.fxml"));
		Scene scene = new Scene(root);
		prScene = scene;
		primaryStage.setScene(scene);
		primaryStage.setTitle("EKrut Client");
		primaryStage.show();

	}

	public static void setScene(String fxmlString) {
		try {
		backScene = prScene;
		FXMLLoader loader = new FXMLLoader(ClientApp.class.getResource("/fxml/"+fxmlString+".fxml"));
		Parent root = (Parent) loader.load();
		
		Scene scene = new Scene(root);
		prScene = scene;
		prStage.setScene(scene);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void back() {
		prStage.setScene(backScene);
		backScene = prScene;
		prScene = prStage.getScene();
	}

	public static void main(String[] args) {
		launch(args);
	}

	//FXMLLoader clientGui = new FXMLLoader(getClass().getResource("/fxml/ResupplyReqScreen.fxml"));
	//Parent root = clientGui.load();
	//ClientController control = clientGui.getController();
	//primaryStage.setOnCloseRequest(e -> control.disconnect());
}

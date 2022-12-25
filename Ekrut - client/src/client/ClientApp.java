package client;

import controllers.AbstractController;
import controllers.ConnectionController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApp extends Application {
	//TEST12
	@Override
	public void start(Stage primaryStage) throws Exception {
		AbstractController abstractController = new ConnectionController();
		AbstractController.prStage = primaryStage;
		abstractController.start("ConnectionConfig", "Connection Config");
	}

	public static void main(String[] args) {
		launch(args);
	}

}

package client;

import controllers.AbstractController;
import controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		AbstractController abstractController = new LoginController();
		AbstractController.prStage = primaryStage;
		abstractController.start(null, "LoginForm", "Login");
	}


	public static void main(String[] args) {
		launch(args);
	}

}

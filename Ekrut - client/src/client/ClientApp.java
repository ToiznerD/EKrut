package client;

import controllers.AbstractController;
import controllers.ConnectionController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * ClientApp class is starting the EKrut app.
 * It extends Application class.
 * It loads FXML file : ConnectionConfig and sets it controller
 * @throws Exception on error while loading the FXML file.
 */
public class ClientApp extends Application {

	/**
	 * override of start method of Application class.
	 * @param primaryStage stage of the client application
	 * @throws Exception on error while loading the FXML file.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		AbstractController abstractController = new ConnectionController();
		AbstractController.prStage = primaryStage;
		abstractController.start("ConnectionConfig", "Connection Config");
	}

	/**
	* main method to launch the application.
	* @param args command line arguments
	*/
	public static void main(String[] args) {
		launch(args);
	}

}

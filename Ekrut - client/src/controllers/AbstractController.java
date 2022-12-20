package controllers;

import java.io.IOException;

import client.ClientBackEnd;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Super abstract class for all controllers
 * All the controllers inherit from this class and the method start
 */
public abstract class AbstractController {
	
	protected ClientBackEnd clientBackEnd;
	public static Stage prStage;
	
	public ClientBackEnd getClientBackEnd() {
		return clientBackEnd;
	}

	public void setClientBackEnd(ClientBackEnd clientBackEnd) {
		this.clientBackEnd = clientBackEnd;
	}
	
	public void start(ActionEvent event, String fxml, String title) throws IOException {
		prStage.hide();
		FXMLLoader load = new FXMLLoader(getClass().getResource("/fxml/" + fxml + ".fxml"));
		Parent root = load.load();

		Scene scene = new Scene(root);
		prStage.setTitle("Ekrut" + " " + title);
		prStage.setScene(scene);
		prStage.setResizable(false);
		prStage.show();
		prStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
				prStage.close();
				clientBackEnd.quit();
				/*boolean ans = PopUpMessage.confirmDialog("Do you want to logout and exit from system?", primaryStage);
				if (ans) {
					if (!(fxml.equals("LoginScreen"))) {
						//implement logout
					}
					primaryStage.close();
				}*/
			}
		});
	}
}

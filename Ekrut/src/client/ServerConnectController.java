package client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerConnectController {
	ClientBackEnd client;
	final public static int DEFAULT_PORT = 5555;
	
    @FXML
    private Button btnConnect;

    @FXML
    private TextField txtIP;

    @FXML
    private TextField txtPort;
    
    @FXML
    private Label errorLabel;
    
    public void openConnection(ClientController clientController) {
		String host = txtIP.getText();
		if (host.equals("") || host == null) {
			host = "localhost";
		}

		try {
			if (client == null)
				client = new ClientBackEnd(host, DEFAULT_PORT, clientController);
		} catch (IOException e) {
			errorLabel.setText("Something went wrong");
			e.printStackTrace();
		}
	}
    
    public void connect(ActionEvent event) throws IOException {
    	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	
    	Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ClientGUI.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		ClientController clientController = loader.getController();
		
		openConnection(clientController);
		clientController.setClient(client);
    	
    	primaryStage.show();	
    	
    	
    }
}

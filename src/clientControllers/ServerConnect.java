package clientControllers;

import java.io.IOException;

import client.ClientBackEnd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServerConnect {
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
		FXMLLoader root = new FXMLLoader(getClass().getResource("/clientFxml/ClientGUI.fxml"));
		Parent parent = root.load();
		ClientController clientController = root.getController();
		Scene scene = new Scene(parent);
		
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);

		primaryStage.setOnCloseRequest(e -> clientController.disconnect());
		openConnection(clientController);
		clientController.setClient(client);
    	
    	primaryStage.show();
    	
    	
    }
}

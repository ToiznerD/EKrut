package controllers;

import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SaleInitiateFormController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnInitiate;

    public void BackButton(ActionEvent event) throws Exception {
    	try {
			ClientApp.setScene("MarketingManagerPanel");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

package controllers;

import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MarketingManagerController {

    @FXML
    private Button btnInitiate;

    @FXML
    private Button btnLogout;
    
    public void InitiateSaleButton(ActionEvent event) throws Exception {
    	try {
			ClientApp.setScene("SaleInitiateForm");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void LogoutButton(ActionEvent event) throws Exception {
    	try {
			ClientApp.setScene("LoginForm");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}



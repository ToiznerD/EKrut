package controllers;

import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MarketingSalesDepartmentController {

    @FXML
    private Button btnCreateNewSaleTemplate;

    @FXML
    private Button btnLogout;
    
    public void CreateSaleTemplateButton(ActionEvent event) throws Exception {
    	try {
			ClientApp.setScene("SaleTemplateCreationForm");
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
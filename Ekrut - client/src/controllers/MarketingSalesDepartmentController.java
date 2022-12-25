package controllers;

import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import client.ClientApp2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
			ClientApp2.setScene("SaleTemplateCreationForm");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void LogoutButton(ActionEvent event) throws Exception {
    	try {
			ClientApp2.setScene("LoginForm");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
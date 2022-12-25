package controllers;

import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import client.ClientApp2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SaleTemplateCreationController {

	 @FXML
	    private Button btnBack;

	    @FXML
	    private Button btnInitiate;
    
    public void BackButton(ActionEvent event) throws Exception {
    	try {
			ClientApp2.setScene("MarketingSalesDepartmentPanel");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

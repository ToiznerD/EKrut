package controllers;

import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SaleTemplateCreationController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnInitiate;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private DatePicker dateStart;

    @FXML
    private ComboBox<?> lstLocation;

    @FXML
    private ComboBox<?> lstSaleTemplate;

    @FXML
    private TextField txtSaleName;
    
    public void BackButton(ActionEvent event) throws Exception {
    	try {
		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}

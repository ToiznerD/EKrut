package controllers;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class StockReportPanelController extends AbstractController{

    @FXML
    ComboBox<String> locationsComboBox;

    /**
     *
     * @param event
     */
    public void BackImgClick (ActionEvent event) {
        try {
        	start("ViewReportsScreen","View Reports");
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param event
     */
    public void ShowReportClick (ActionEvent event) {
        try {
            start("StockReportForm","Stock Report");
        } catch (Exception e) {

        }
    }
}

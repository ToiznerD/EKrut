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
        	switchScreen("ViewReportsScreen","View Reports");
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param event
     */
    public void ShowReportClick (ActionEvent event) {
        try {
            switchScreen("StockReportForm","Stock Report");
        } catch (Exception e) {

        }
    }
}

package controllers;

import Entities.OrderReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseReportScreen extends AbstractController {
    @FXML
    TextField yearTxtField;
    @FXML
    TextField monthTxtField;
    @FXML
    Label errorLabel;
    private String month, year;


    public static OrderReport orderReport;


    public void OrdersReportImgClick() {
        if (!getReportDetails()) {
            errorLabel.setText("* Please enter valid date");
            return;
        }

        try {
            OrderReportController.setDetails(month, year);
            start("orderReportScreen", "Order Report");
        } catch (IOException e) {
            // TODO: handle exception
        }

    }

    private boolean getReportDetails() {
        year = yearTxtField.getText();
        month = monthTxtField.getText();
        if (year.equals("") || month.equals("")) {
            return false;
        }

        return true;
    }

    @Override
    public void back(MouseEvent event) {
        try {
            // go back to previous screen
            start("RegionManagerMainScreen", "Region Manager Dashboard");
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}

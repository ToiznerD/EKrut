package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ManageInventoryController extends AbstractController{
    @FXML
    Button updateMinLimitBtn;
    @FXML
    Button sendResupplyRequestBtn;

    public void updateMinLimitBtnClick() {
        try {
            start("UpdateMinLimitInventoryScreen", "Update Min. Limit");
        } catch (Exception e) {

        }
    }

    public void sendResupplyRequestBtnClick() {
        try {
            // go back to previous screen
            start("CreateResupplyRequestScreen", "Create a new resupply request");
        } catch (IOException e) {
            // TODO: handle exception
        }

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

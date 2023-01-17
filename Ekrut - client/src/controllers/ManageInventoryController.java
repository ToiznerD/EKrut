package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * ManageInventoryController is a controller class for the operation employee inventory managing.
 * It extends AbstractController and overrides methods of it.
 */
public class ManageInventoryController extends AbstractController{
	
    @FXML
    Button updateMinLimitBtn, sendResupplyRequestBtn;

    /**
    * Opens the Update MinLimit Inventory Screen
    */
    public void updateMinLimitBtnClick() {
        try {
            start("UpdateMinLimitInventoryScreen", "Update Min. Limit");
        } catch (Exception e) {

        }
    }

    /**
    * Opens the Create Resupply Request Screen
    */
    public void sendResupplyRequestBtnClick() {
        try {
            start("CreateResupplyRequestScreen", "Create a new resupply request");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setUp(Object... objects) {

    }
    
    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     */
    @Override
    public void back(MouseEvent event) {
        try {
            start("RegionManagerMainScreen", "Region Manager Dashboard");
        } catch (IOException e) {
        }
    }
}

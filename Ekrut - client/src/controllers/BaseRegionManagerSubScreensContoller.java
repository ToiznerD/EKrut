package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseRegionManagerSubScreensContoller extends AbstractController{
    /**
     * Goes back to previous screen (RegionManagerMainScreen) after the client clicked on the "Back" button
     * @param event  an ActionEvent that captures the "Back" button click
     */
    public void backToRegionManagerScreenButtonClick(ActionEvent event) {
        try {
            start("RegionManagerMainScreen", "Region Manager Dashboard");
        } catch (Exception e) {
// TODO: handle exception
            e.printStackTrace();
        }
    }
}

package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseRegionManagerSubScreensContoller {
    /**
     * Goes back to previous screen (RegionManagerMainScreen) after the client clicked on the "Back" button
     * @param event  an ActionEvent that captures the "Back" button click
     */
    public void backToRegionManagerScreenButtonClick(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader root = new FXMLLoader(getClass().getResource("/clientFxml/RegionManagerMainScreen.fxml"));
            Parent parent = root.load();
            Scene scene = new Scene(parent);

            primaryStage.setTitle("Region Manager Screen");
            primaryStage.setScene(scene);

            primaryStage.show();

        } catch (Exception e) {
// TODO: handle exception
            e.printStackTrace();
        }
    }
}

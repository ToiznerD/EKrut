package controllers;

import javafx.event.ActionEvent;

public class RegionManagerMainScreenController extends AbstractController {

    /**
     * loads the reports screen view after the client clicked on the "View Reports" button
     * @param event  an ActionEvent that captures the "View Reports" button click
     */
    public void viewReportsClick(ActionEvent event) {
        try {
            start("ViewReportsScreen", "Choose Report");
        } catch (Exception e) {
// TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * loads the manage inventory screen view after the client clicked on the "Manage Inventory" button
     * @param event  an ActionEvent that captures the "Manage Inventory" button click
     */
    public void ManageInventoryClick(ActionEvent event) {
        try {
            start("ManageInventoryScreen", "Manage Inventory");
        } catch (Exception e) {
// TODO: handle exception
            e.printStackTrace();
        }
    }
}




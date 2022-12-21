package controllers;


import javafx.event.ActionEvent;

public class OrderReportPanelController extends AbstractController{
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
    public void showReportBtnClick(ActionEvent event) {
        try {
            switchScreen("ViewReportsScreen","View Reports");
        } catch (Exception e) {

        }
    }
}

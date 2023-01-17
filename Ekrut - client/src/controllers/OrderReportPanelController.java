package controllers;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

/**
 * OrderReportPanelController is a controller class for orders report process.
 * It extends AbstractController and overrides methods of it.
 */
public class OrderReportPanelController extends AbstractController {

    /**
     * BackImgClick is called when a action event occurs. It opens a the window "ViewReportsScreen".
     * @param event The ActionEvent that triggers the method.
     */
    public void BackImgClick (ActionEvent event) {
        try {
            start("ViewReportsScreen","View Reports");
        } catch (Exception e) {

        }
    }

    /**
     * showReportBtnClick is called when a action event occurs. It opens a the window "ViewReportsScreen".
     * @param event The ActionEvent that triggers the method.
     */
    public void showReportBtnClick(ActionEvent event) {
        try {
            start("ViewReportsScreen","View Reports");
        } catch (Exception e) {

        }
    }

	@Override
	public void back(MouseEvent event) {
		// Not implemented
	}

	@Override
	public void setUp(Object... objects) {
		// Not implemented
	}
}

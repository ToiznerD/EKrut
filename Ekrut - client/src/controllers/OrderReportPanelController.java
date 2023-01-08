package controllers;


import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class OrderReportPanelController extends AbstractController{
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
	public void setUp(Object[] objects) {
		// TODO Auto-generated method stub
		
	}
}

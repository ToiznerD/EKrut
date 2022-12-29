package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ViewReportsController extends AbstractController {

    /**
     *
     * @param event
     */
    public void stockStatusReportsBtnClick(ActionEvent event) {
        // mock object
        ArrayList arr = new ArrayList<String>();
        ObservableList data = FXCollections.observableArrayList();
        data.add("Qiryat Ata");data.add("Karmiel");


        try {
            start("StockReportPanel","Stock Reports Panel");
            
        } catch (Exception e) {
// TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     *
     * @param event
     */
    public void OrderReportsBtnClick(ActionEvent event) {
        try {
            start("OrderReport","Order Reports Panel");
        } catch (Exception e) {
// TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     *
     * @param event
     */
    public void customerReportsBtnClick(ActionEvent event) {
        try {
            start("CustomersReport","Customers Reports Panel");
        } catch (Exception e) {
// TODO: handle exception
            e.printStackTrace();
        }
    }

	@Override
	public void back(MouseEvent event) {
		// Not implemented
	}

}

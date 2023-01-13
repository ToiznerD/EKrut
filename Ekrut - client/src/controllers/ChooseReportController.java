package controllers;

import Entities.OrderReport;
import Util.Msg;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static Util.Tasks.getOrderReports;

public class ChooseReportController extends AbstractController {
    private String month, year;

    @FXML
    TextField yearTxtField;
    @FXML
    TextField monthTxtField;
    @FXML
    Label errorLabel;

// --------------------------------------- Handle Order Report Request ---------------------------------------------- //

    /**
     * proceed to next screen when clicking on the order report Img
     */
    public void OrdersReportImgClick() {
        // validate details
        if (getOrderReportDetails()) {
            try {
                OrderReportController.setDetails(month, year);
                start("orderReportScreen", "Order Report");
            } catch (IOException e) {
                // TODO: handle exception
            }
        }
    }

    /**
     * validates the report details, query the db for order reports, and save it in the report screen controller
     * @return
     */
    private boolean getOrderReportDetails() {
        // validate date input
        if (!validateDateInput())
            return false;

        // get query
        String query = getOrderReportQuery();

        // get report and save it for next screen controller, if dont exist show msg
        msg = new Msg(getOrderReports, query);
        sendMsg(msg);
        if (msg.getBool())
            OrderReportController.orderReportsToDisplay = msg.getArr(OrderReport.class);
        else {
            errorLabel.setText("* report does not exist");
            throw new RuntimeException();
            // TODO: handle exception better
        }
        return true;
    }

    /**
     * Check if ant field was left empty
     * @return true if noth not empty, false otherwise
     */
    private boolean validateDateInput() {
        year = yearTxtField.getText();
        month = monthTxtField.getText();
        if (year.equals("") || month.equals("")) {
            errorLabel.setText("* Please enter valid date");
            return false;
        }
        return true;
    }

    /**
     * Generates a query to get appropriate reports, according to user role
     * @return a db query to get all appropriate reports according to role and date that was passed
     */
    private String getOrderReportQuery() {
        String query;
        if (myUser.getRole().equals("region_manager")) {
            query = "SELECT DISTINCT s_name, num_orders, total_profit FROM order_report\n" +
                    "INNER JOIN store ON order_report.s_name = store.name\n" +
                    "INNER JOIN regions ON store.rid = " + RegionManagerMainScreenController.regionID + "\n" +
                    "WHERE month = " + month + " AND year = " + year + " AND regions.rid = store.rid";
        } else {
            query = "SELECT s_name, num_orders, total_profit FROM order_report\n" +
                    "WHERE month = " + month + " AND year = " + year;
        }
        return query;
    }


// ------------------------------------ Handle Stock Status Report Request ------------------------------------------ //


    /**
     * proceed to next screen when clicking on the stock status report Img
     */
    public void StockStatusReportImgClick() {
        // validate details
        if (validateDateInput()) {
            try {
                StockStatusReportController.setDetails(month, year);
                start("StockStatusReportScreen", "Stock Status Report");
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: handle exception
            }
        }
    }

    // ------------------------------------ Handle Customer Report Request ------------------------------------------ //


    public void CustomerReportImgClick() {
        // validate details
        if (validateDateInput()) {
            try {
                CustomerReportController.setDetails(month, year);
                start("CustomerReportScreen", "Customer Report");
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: handle exception
            }
        }
    }

    /**
     * goes back to Region manager main screen
     * @param event captures mouse event
     */
    @Override
    public void back(MouseEvent event) {
        try {
            String title = "Region Manager Dashboard";
            // go back to previous screen
            if(myUser.getRole().equals("ceo"))
                title = "CEO Dashboard";
            start("RegionManagerMainScreen", title);
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub
		
	}
}

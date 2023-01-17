package controllers;

import Util.Msg;
import Util.Tasks;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import Entities.CustomerReport;

/**
 * CustomerReportController is the controller for handling customer reports.
 * It extends AbstractController and overrides methods of it.
 */
public class CustomerReportController extends AbstractController {
    private static String month, year;

    @FXML
    BarChart customerReportBarChart;
    @FXML
    Label reportDetailsLabel;
    @FXML
    ComboBox<String> locationsComboBox;
    @FXML
    Label errorLabel;

    @FXML
    public void initialize() {
        // Set up label text
        String labelTxt = month + "\\" + year;
        if (myUser.getRole().equals("region_manager")) {
            labelTxt = labelTxt + ", " + controllers.RegionManagerMainScreenController.regionName;
        }
        reportDetailsLabel.setText(labelTxt);
        reportDetailsLabel.setStyle("-fx-font-weight: bold");

        RegionManagerMainScreenController.loadLocationsComboBox(locationsComboBox);
    }


    /**
     * static method to pass date to this screen, used by previous screen (Choose Report)
     * @param month1 report month
     * @param year1 report date
     */
    public static void setDetails(String month1, String year1) {
        month = month1; year=year1;
    }

    /**
     * gets triggered when a store is chosen, loads barChart with data from db about customer order distribution
     */
    public void loadDataToBarChart() {
        // reset components
        errorLabel.setText("");
        customerReportBarChart.getData().clear();

        // set new series
        XYChart.Series customerSeries = new XYChart.Series();
        customerSeries.setName("Customers Activity Level");

        // get reports from db
        String sname = locationsComboBox.getSelectionModel().getSelectedItem().toString();
        String query = "SELECT * FROM customer_report\n" +
                "WHERE s_name = '" + sname + "' AND year = " + year + " AND month = " + month;
        msg = new Msg(Tasks.Select, query);
        sendMsg(msg);
        if (!msg.getBool()) {
            errorLabel.setText("* an error has occured while fetching data");
            return;
        }

        // extract report and parse info
        CustomerReport customerReport = msg.getArr(CustomerReport.class).get(0);
        String histogramData = customerReport.getHistogram();
        String[] data = histogramData.split(",");
        int colNum = data.length;
        int min = customerReport.getMinNumOrders(), max = customerReport.getMaxNumOrders();

        // generate the activity ranges according to data in db
        String[] activityLevels = new String[colNum];
        int delta = (max-min)/colNum;

        for (int i=0;i<colNum-1;i++) {
            String rangeDisplay=String.format("%d-%d",min, min + delta);
            activityLevels[i] = rangeDisplay;
            min += delta+1;
        }

        String rangeDisplay=String.format("%d-%d",min, max);
        activityLevels[colNum-1] = rangeDisplay;

        // insert data to the series
        for (int i=0; i< data.length; i++)
            customerSeries.getData().add(new XYChart.Data(activityLevels[i], Integer.parseInt(data[i])));

        // add series to barChart
        customerReportBarChart.getData().addAll(customerSeries);
    }

    @Override
    public void setUp(Object... objects) {
    }

    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     * @throws IOException if there is an issue loading the FXML file
     */
    @Override
    public void back(MouseEvent event) {
            try {
                start("ChooseReportScreen","Choose Report");
            } catch (Exception e) {}
    }
}

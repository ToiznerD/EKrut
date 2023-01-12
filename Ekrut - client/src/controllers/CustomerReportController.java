package controllers;

import Util.Msg;
import Util.Tasks;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import Entities.CustomerReport;

public class CustomerReportController extends AbstractController {
    private static String month, year;

    @FXML
    BarChart customerReportBarChart;
    @FXML
    Label reportDetailsLabel;
    @FXML
    ComboBox<String> locationsComboBox;

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

    public void loadDataToBarChart() {
        customerReportBarChart.getData().clear();

        XYChart.Series currReport = new XYChart.Series();
        currReport.setName("Activity Level");

        String sname = locationsComboBox.getSelectionModel().getSelectedItem().toString();
        String query = "SELECT * FROM customer_report\n" +
                "WHERE s_name = '" + sname + "' AND year = " + year + " AND month = " + month;

        msg = new Msg(Tasks.Select, query);
        sendMsg(msg);

        if (!msg.getBool()) {
            // handle error
        }

        CustomerReport customerReport = msg.getArr(CustomerReport.class).get(0);

        String histogramData = customerReport.getHistogram();
        String[] data = histogramData.split(",");
        // parse histogram and display
        //String[] activityLevels = {"0-2","3-4","5-6","7-8","9-10"};
        String[] activityLevels = {};
        int min = 2, max = 15;
        int delta = max-min/data.length;


        for (int i=0; i< data.length; i++)
            currReport.getData().add(new XYChart.Data(activityLevels[i], Integer.parseInt(data[i])));

        customerReportBarChart.getData().addAll(currReport);
    }

    @Override
    public void setUp(Object... objects) {
    }

    @Override
    public void back(MouseEvent event) {
            try {
                start("ViewReportsScreen","View Reports");
            } catch (Exception e) {}
    }
}

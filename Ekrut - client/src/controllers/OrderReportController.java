package controllers;

import Entities.OrderReport;
import Util.Msg;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static Util.Tasks.*;
import static java.lang.Integer.parseInt;

public class OrderReportController extends AbstractController implements Initializable {
    private static String month, year;
    private List<OrderReport> orderReportToDisplay;

    @FXML
    Label reportDetailsLabel;
    @FXML
    PieChart storesPieChart;
    @FXML
    BarChart profitBarChart;

    /**
     * static method to pass date to this screen, used by previous screen (Choose Report)
     * @param month1 report month
     * @param year1 report date
     */
    public static void setDetails(String month1, String year1) {
        month = month1; year=year1;
    }

    /**
     * Overrides the javaFX Initializable interface method
     * displays bar and pie chart according to given details
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query = getReportQuery();

        msg = new Msg(getOrderReports, query);
        sendMsg(msg);
        if (!msg.getBool())
            throw new RuntimeException();
            // TODO: handle exception better

        orderReportToDisplay = msg.getArr(OrderReport.class);

        // Set up label text
        String labelTxt = month + "\\" + year;
        if (myUser.getRole().equals("region_manager")) {
            labelTxt = labelTxt + ", " + controllers.RegionManagerMainScreenController.regionName;
        }
        reportDetailsLabel.setText(labelTxt);
        reportDetailsLabel.setStyle("-fx-font-weight: bold");

        // Set up pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        XYChart.Series series = new XYChart.Series();
        series.setName("Monthly Profit");
        Integer totalOrders = 0;

        for (OrderReport orderReport : orderReportToDisplay) {
            pieChartData.add(new PieChart.Data(orderReport.getsName(), orderReport.getNumOrders()));
            series.getData().add(new XYChart.Data(orderReport.getsName(), orderReport.getTotalProfit()));
            totalOrders = orderReport.getNumOrders();
        }

        storesPieChart.setData(pieChartData);
        storesPieChart.setTitle("Total number of orders: " + totalOrders);
        profitBarChart.getData().addAll(series);
    }

    /**
     * Generates a query to get appropriate reports, according to user role
     * @return a db query to get all appropriate reports according to role and date that was passed
     */
    private String getReportQuery() {
        String query;
        if (myUser.getRole().equals("region_manager")) {
            query = "SELECT s_name, num_orders, total_profit FROM order_report\n" +
                    "INNER JOIN store ON order_report.s_name = store.name\n" +
                    "INNER JOIN regions ON store.rid = " + RegionManagerMainScreenController.regionID + "\n" +
                    "WHERE month = " + month + " AND year = " + year + " AND regions.rid = store.rid";
        } else {
            query = "SELECT s_name, num_orders, total_profit FROM order_report\n" +
                    "WHERE month = " + month + " AND year = " + year;
        }
        return query;
    }


    /**
     * triggered when back image is clicked, goes back to previous screen
     */
    @Override
    public void back(MouseEvent event) {
        try {
            // reset details that were passed to this screen
            setDetails("","");
            // go back to previous screen
            start("ChooseReportScreen", "Choose Report");
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}

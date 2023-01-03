package controllers;

import Entities.OrderReport;
import Entities.StockReport;
import Entities.StoreProduct;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class StockStatusReportController extends AbstractController {
    private static String month, year;
    protected static List<StockReport> lastThreeReports;
    private static StockReport targetStockReport;
    private ObservableList<StoreProduct> itemObsList;

    @FXML
    BarChart dataBarGraph;
    @FXML
    TableView<StoreProduct> stockTable;
    @FXML
    Label reportDetailsLabel;
    @FXML
    ComboBox<String> locationsComboBox;
    @FXML
    TableColumn<StoreProduct, String> productCol;
    @FXML
    TableColumn<StoreProduct, Integer> quantityCol;

    @FXML
    public void initialize() {
        RegionManagerMainScreenController.loadLocationsComboBox(locationsComboBox);
        productCol.setCellValueFactory(new PropertyValueFactory<StoreProduct, String>("pname"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<StoreProduct, Integer>("quantity"));
        itemObsList = FXCollections.observableArrayList();
        stockTable.setItems(itemObsList);

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
     * present a stock status report to user
     * @return
     */
    public boolean getStockStatusReportDetails(ActionEvent event) {
        // get query
        String storeLocation = locationsComboBox.getSelectionModel().getSelectedItem().toString();
        String query = getStockStatusReportQuery(storeLocation);
        msg = new Msg(Tasks.getStockStatusReport, query);
        sendMsg(msg);

        targetStockReport = msg.getArr(StockReport.class).get(0);
        fillProductList();

        int lastReportsFetchedS = getQueryForLastThreeReports(storeLocation);

        // fill barChart
        // fill table of products

        return true;

    }

    /**
     * Save last three reports in a field
     * @param storeLocation store were querying about
     * @return number of last reports that were found
     */
    private int getQueryForLastThreeReports(String storeLocation) {
        // Parse the month and year strings
        int month1 = Integer.parseInt(month);
        int year1 = Integer.parseInt(year);


        // Create a calendar object with the given month and year
        Calendar calendar = new GregorianCalendar(year1, month1, 1);

        StringBuilder query = new StringBuilder("SELECT DISTINCT s_name, stock_data, month, year FROM stock_report\n" +
                       "INNER JOIN store ON stock_report.s_name = \"" + storeLocation + "\"\n" +
                        "WHERE ");

        // store the last three months and years
        for (int i = 0; i < 3; i++) {
            // roll one month back
            calendar.roll(Calendar.MONTH, -1);
            // build string
            query.append("(month = " + calendar.get(Calendar.MONTH) + " AND year = " + calendar.get(Calendar.YEAR) + ")");
            if (i!=2)
                query.append(" OR ");
        }

        msg = new Msg(Tasks.getStockStatusReport, query.toString());
        sendMsg(msg);

        if (msg.getInt() > 0) {
            lastThreeReports = msg.getArr(StockReport.class);
            return msg.getInt();
        } else {
            return 0;
        }
    }

    /**
     * Generate query for getting Stock Status Report for specific location
     * @param storeLocation location of the store the report is for
     * @return a db query
     */
    private String getStockStatusReportQuery(String storeLocation) {
        String query = "SELECT DISTINCT s_name, stock_data, month, year FROM stock_report\n" +
                "INNER JOIN store ON stock_report.s_name = \"" + storeLocation + "\"\n" +
                "WHERE month = " + month + " AND year = " + year;
        return query;
    }

    /**
     * add store products to observable list to be displayed to the user
     */
    private void fillProductList() {
        String stock_data = targetStockReport.getStockData();
        String dataArr[] = stock_data.split(",");
        for (int i=0; i<dataArr.length; i=i+2) {
            itemObsList.add(new StoreProduct(Integer.parseInt(dataArr[i+1]), dataArr[i]));
        }
    }

    @Override
    public void back(MouseEvent event) {
        // Not implemented yet
    }
}

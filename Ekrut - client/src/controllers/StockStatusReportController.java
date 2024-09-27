package controllers;

import Entities.MinimalStoreProduct;
import Entities.StockReport;
import Entities.StoreProduct;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.*;

/**
 * StockStatusReportController is the controller for handling stock status reports.
 * It extends AbstractController and overrides methods of it.
 */
public class StockStatusReportController extends AbstractController {
    private static String month, year;
    private ObservableList<MinimalStoreProduct> itemObsList;
    private StockReport stockReportToDisplay;
    protected static List<StockReport> lastReports;
    protected HashMap<String, Integer> targetReportsItemsMap;
    protected ArrayList<HashMap<String, Integer>> lastReportsItemsMaps;
    IReportService reportService;

    public StockStatusReportController() {
    	reportService = new ReportRetriever();
    }
    
    public StockStatusReportController(IReportService reportService) {
    	this.reportService = reportService;
    }

    @FXML
    BarChart dataBarGraph;
    @FXML
    TableView<MinimalStoreProduct> stockTable;
    @FXML
    Label reportDetailsLabel;
    @FXML
    ComboBox<String> locationsComboBox;
    @FXML
    TableColumn<StoreProduct, String> productCol;
    @FXML
    TableColumn<StoreProduct, Integer> quantityCol;
    @FXML
    Label errorLabel;
    
    public StockReport getStockReportToDisplay() {
    	return stockReportToDisplay;
    }

    @FXML
    public void initialize() {
        // Set up label text
        String labelTxt = month + "\\" + year;
        if (myUser.getRole().equals("region_manager")) {
            labelTxt = labelTxt + ", " + controllers.RegionManagerMainScreenController.regionName;
        }
        reportDetailsLabel.setText(labelTxt);

        RegionManagerMainScreenController.loadLocationsComboBox(locationsComboBox);
        productCol.setCellValueFactory(new PropertyValueFactory<StoreProduct, String>("pname"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<StoreProduct, Integer>("quantity"));
        itemObsList = FXCollections.observableArrayList();
        stockTable.setItems(itemObsList);

    }

    
    class ReportRetriever implements IReportService {
    	@Override
        public void getReportFromDb(String storeLocation, String month, String year) {
       	 String query = getStockStatusReportQuery(storeLocation, month, year);
            msg = new Msg(Tasks.Select, query);
            sendMsg(msg);
            
         // if query failed - there is no report
            if (!msg.getBool()) {
                errorLabel.setText("* Report does not exist");
                return;
            }

            setStockReportToDisplay(msg.getArr(StockReport.class).get(0));
       }
    }
    
    
    public void setStockReportToDisplay(StockReport stockReport) {
    	stockReportToDisplay = stockReport;
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
     * @param event the action event that triggered the method call
     */
    public void getStockStatusReportDetails(ActionEvent event) {
        // reset all info on screen when an option is clicked
        errorLabel.setText("");
        itemObsList.clear();
        dataBarGraph.getData().clear();

        // get query
        String storeLocation = locationsComboBox.getSelectionModel().getSelectedItem().toString();
        
        reportService.getReportFromDb(storeLocation, month, year);
        
        fillProductTable();

        boolean savedLastReportsSuccessfully = getLastReports(storeLocation);
        if (savedLastReportsSuccessfully) {
            createLastReportsItemsHashMaps();
        }

        loadDataToBarGraph(savedLastReportsSuccessfully);
    }
    



    /**
     * fills the barGraph with data we fetched earlier
     * @param savedLastReportsSuccessfully boolean, indicating if there are past reports to load data to graph
     */
    private void loadDataToBarGraph (boolean savedLastReportsSuccessfully) {
        // Set up the serieses for barChart
        XYChart.Series currReport = new XYChart.Series();
        currReport.setName("Current Report Items");
        XYChart.Series prevReport = new XYChart.Series();


        // save the data in the barGraph
        for (HashMap.Entry<String, Integer> itemSet : targetReportsItemsMap.entrySet()) {
            currReport.getData().add(new XYChart.Data(itemSet.getKey(), itemSet.getValue()));
            if (savedLastReportsSuccessfully) {
                // Gather last reports info only if it was saved successfully, otherwise no info to gather
                int sum = 0, appearance_cnt = 0;
                for (HashMap<String, Integer> itemMap : lastReportsItemsMaps) {
                    try {
                        sum += itemMap.get(itemSet.getKey());
                        appearance_cnt += 1;
                    } catch (NullPointerException e) { }
                }
                if (appearance_cnt > 0)
                    prevReport.getData().add(new XYChart.Data(itemSet.getKey(), sum / appearance_cnt));
            }
        }
        dataBarGraph.getData().addAll(currReport);
        if (savedLastReportsSuccessfully)
            prevReport.setName("Average of Last " + lastReports.size() + " Reports");
            dataBarGraph.getData().addAll(prevReport);
    }

    /**
     * Save array of HashMaps for last report's items to get easily for the calculation phase
     */
    private void createLastReportsItemsHashMaps() {
        lastReportsItemsMaps = new ArrayList<>();
        for (int i = 0; i < lastReports.size(); i++) {
            // extract report
            StockReport currStockReport = lastReports.get(i);
            // create new hashmap for items
            HashMap<String, Integer> itemMap = new HashMap<>();
            // parse item data
            String[] currReportstockData = currStockReport.getStockData().split(",");
            // insert to hashMap
            for (int j = 0; j < currReportstockData.length; j = j + 2) {
                itemMap.put(currReportstockData[j], Integer.valueOf(currReportstockData[j + 1]));
            }
            lastReportsItemsMaps.add(itemMap);
        }
    }

    /**
     * Save last three reports in a field
     * @param storeLocation store were querying about
     * @return true if reports were found, false ptherwise
     */
    private boolean getLastReports(String storeLocation) {
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
            calendar.add(Calendar.MONTH, -1);
            // build string
            query.append("(month = " + calendar.get(Calendar.MONTH) + " AND year = " + calendar.get(Calendar.YEAR) + ")");
            if (i!=2)
                query.append(" OR ");
        }

        msg = new Msg(Tasks.Select, query.toString());
        sendMsg(msg);

        if (msg.getBool()) {
            lastReports = msg.getArr(StockReport.class);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generate query for getting Stock Status Report for specific location
     * @param storeLocation location of the store the report is for
     * @return a db query
     */
    private String getStockStatusReportQuery(String storeLocation, String month, String year) {
        String query = "SELECT DISTINCT s_name, stock_data, month, year FROM stock_report\n" +
                "INNER JOIN store ON stock_report.s_name = \"" + storeLocation + "\"\n" +
                "WHERE month = " + month + " AND year = " + year;
        return query;
    }

    /**
     * add store products to observable list to be displayed to the user
     * and add them to targetReportsItemsMap field
     */
    private void fillProductTable() {
        itemObsList.clear();
        targetReportsItemsMap = new HashMap<>();
        String stock_data = stockReportToDisplay.getStockData();
        String dataArr[] = stock_data.split(",");
        for (int i=0; i<dataArr.length; i=i+2) {
            itemObsList.add(new MinimalStoreProduct(Integer.valueOf(dataArr[i+1]), dataArr[i]));
            targetReportsItemsMap.put(dataArr[i], Integer.valueOf(dataArr[i+1]));
        }
    }

    @Override
    public void setUp(Object... objects) {

    }

    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     */
    @Override
    public void back(MouseEvent event) {
        try {
            start("ChooseReportScreen", "Choose Report");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}

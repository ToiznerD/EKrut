package Utils;

import DBHandler.DBController;
import Entities.Product;
import Entities.Store;
import Entities.StoreProduct;
import Util.Msg;
import Util.Tasks;
import org.apache.ibatis.jdbc.SQL;
import tasker.Tasker;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ReportGenerator extends TimerTask {
    private static List<Store> storeList;
    private static List<Product> productList;
    private static int month, year;

    /**
     * Overrides the TimerTask <code>run</code> method, setting the year and month and generating reports
     */
    @Override
    public void run() {
        // initialize year and month - get current date and roll one month back
        LocalDate today = LocalDate.now();
        month = today.minusMonths(1).getMonth().getValue();
        if (month == 12)
            year = today.minusYears(1).getYear();
        else
            year = today.getYear();


        // generate reports
        System.out.println("*** Generating Report ***");
        generateReports();
        storeList = null; productList = null;

        // set up next scheduled task
        Timer timer = new Timer();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,1);
        c.set(Calendar.DAY_OF_MONTH,1);
        Date nextExecution = c.getTime();
        timer.schedule(new ReportGenerator(), nextExecution);
    }


    /**
     * orchestrates the report generation for each store - only StockStatus and orders reports
     */
    public static void generateReports() {
        getStores();
        getProducts();
        for (Store s : storeList) {
            if (!s.getName().equals("Delivery Warehouse")) {
                generateStockStatusReport(s);
            }
            generateOrdersReports(s);
        }
    }

    /**
     * this function gets the stock info for the store <code>s</code> and generates report
     * @param s - store for which we will generate the stock status report
     * @return true if the report was generated successfully
     */
    public static boolean generateStockStatusReport(Store s) {
        String query = "SELECT store_product.*, product.pname\n" +
                "FROM store\n" +
                "JOIN store_product ON store.sid = store_product.sid\n" +
                "JOIN product ON product.pid = store_product.pid\n" +
                "WHERE store.sid = " + s.getSid();

        Msg msg = new Msg(Tasks.Select, query);
        try {
            Tasker.runSelect(msg);
        } catch (SQLException e) { e.printStackTrace(); }

        List<StoreProduct> productList = msg.getArr(StoreProduct.class);
        StringBuilder stockData = new StringBuilder();

        for (StoreProduct product : productList)
            stockData.append(product.getPname() + "," + product.getQuantity() + ",");
        stockData.setLength(stockData.length()-1);

        String insertReportQuery = String.format("INSERT INTO stock_report VALUES ('%s','%s',%d,%d)",s.getName(),stockData.toString(),month,year);
        msg = new Msg(Tasks.Insert, insertReportQuery);
        Tasker.runUpdate(msg);

        if (!msg.getBool())
            return false;

        return true;
    }

    /**
     * this function gets the orders info for the store <code>s</code> and generates report
     * @param s - store for which we will generate the stock status report
     * @return true if the report was generated successfully
     */
    public static boolean generateOrdersReports(Store s) {
        Double totalProfit;
        Long numOrders;
        String query, insertReportQuery;
        Msg msg;


        query = "SELECT SUM(total_price), COUNT(*) FROM orders " +
                "WHERE YEAR(ord_date) = " + year + " AND MONTH(ord_date) = "
                + month + " AND sid = " + s.getSid();
        msg = new Msg(Tasks.Select, query);
        try {
            Tasker.runSelect(msg);
        } catch (SQLException e) { e.printStackTrace(); }
        totalProfit = msg.getObj(0);
        numOrders = msg.getObj(1);

        if (totalProfit == null || numOrders == null) {
            return false;
        }

        insertReportQuery = String.format("INSERT INTO order_report values ('%s',%d,%d,%d,%d)", s.getName(),month,year,numOrders.intValue(),totalProfit.intValue());

        msg = new Msg(Tasks.Insert, insertReportQuery);
        Tasker.runUpdate(msg);

        if (!msg.getBool())
            return false;

        return true;
    }

    private static void getStores() {
        String query = "SELECT * FROM store";
        Msg msg = new Msg(Tasks.Select, query);
        try {
            Tasker.runSelect(msg);
        } catch (SQLException e) { e.printStackTrace(); }
        storeList = msg.getArr(Store.class);
    }

    private static void getProducts() {
        String query = "SELECT pid,price,pname FROM product";
        Msg msg = new Msg(Tasks.Select, query);
        try {
            Tasker.runSelect(msg);
        } catch (SQLException e) { e.printStackTrace(); }
        productList = msg.getArr(Product.class);
    }



}

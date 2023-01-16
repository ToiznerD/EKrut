package controllers;

import Entities.Store;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegionManagerMainScreenController extends AbstractController {
    protected static HashMap<String, Integer> storeMap;
    private static ObservableList<String> comboBoxOptions;
    public static Integer regionID;
    public static String regionName;

    @FXML
    Button manageInventoryBtn;
    @FXML
    Button approveCustomersBtn;
    @FXML
    Button viewReportsBtn;
    @FXML
    Label welcomeLabel;

    /**
     * sends a query to db to get locations according to region
     */
    @FXML
    public void initialize() {
        if (comboBoxOptions != null)
            comboBoxOptions.clear();

        welcomeLabel.setText("Welcome back, " + myUser.getName());
        sendLocationsMsg();
        saveStoreMap();
        getRegion();
        if (myUser.getRole().equals("ceo")) {
            manageInventoryBtn.setVisible(false);
            approveCustomersBtn.setVisible(false);
        }
    }

    /**
     * prepares a Message to send a query to server to get all relevant store locations of the region manager connected to the system
     */
    public void sendLocationsMsg() {
        String query;
        if (myUser.getRole().equals("ceo"))
            query = "SELECT s.* FROM store s";
        else {
            query = "SELECT s.*\n" +
                    "FROM store s\n" +
                    "JOIN regions r ON s.rid = r.rid\n" +
                    "JOIN region_employee re ON r.rid = re.rid\n" +
                    "WHERE re.uid = " + myUser.getId();
        }
        msg = new Msg(Tasks.Select, query);
        sendMsg(msg);
    }

    /**
      * create hashMap of {store_name:store_id} and saves it to storesMap
      */
    public static void saveStoreMap() {
        storeMap = new HashMap<>();
        List<Store> stores = msg.getArr(Store.class);
        // generate HashMap for storing sname:sid key-value pairs
        for (Store s : stores) {
            storeMap.put(s.getName(), s.getSid());
        }
    }

    /**
     * sets the static var region to be the region of the connected region manager
     */
    private void getRegion() {
        if (myUser.getRole().equals("region_manager")) {
            String query = "SELECT region_employee.rid, name FROM region_employee\n" +
                    "JOIN regions ON regions.rid = region_employee.rid\n" +
                    " WHERE uid = " + myUser.getId();
            msg = new Msg(Tasks.Select, query);
            sendMsg(msg);

            if (msg.getBool()) {
                regionID = msg.getObj(0);
                regionName = msg.getObj(1);
            }
        }
    }

    /**
     * loads the location comboBox with the relevant locations
     */
    public static void loadLocationsComboBox(ComboBox<String> comboBoxToLoad) {
        List<String> options = new ArrayList<>();
        options.addAll(storeMap.keySet());

        for (String s : options) {
            if (s.equals("Delivery Warehouse")) {
                options.remove(s);
                break;
            }
        }

        comboBoxOptions = FXCollections.observableArrayList(options);
        comboBoxToLoad.setItems(comboBoxOptions);
    }

    /**
     * loads the reports screen view after the client clicked on the "View Reports" button
     * @param event  an ActionEvent that captures the "View Reports" button click
     */
    public void viewReportsClick(ActionEvent event) {
        try {
            start("ChooseReportScreen", "Choose Report");
        } catch (Exception e) {
// TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * loads the manage inventory screen view after the client clicked on the "Manage Inventory" button
     * @param event  an ActionEvent that captures the "Manage Inventory" button click
     */
    public void ManageInventoryClick(ActionEvent event) {
        try {
            start("ManageInventoryScreen", "Manage Inventory");
        } catch (Exception e) {
        // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * loads the approve employees screen
     */
    public void approveCustomersBtnClick() {
        try {
            start("ApproveCustomersScreen", "Approve new Customers");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    @Override
    public void back(MouseEvent event) {
    }

	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub
		
	}
}




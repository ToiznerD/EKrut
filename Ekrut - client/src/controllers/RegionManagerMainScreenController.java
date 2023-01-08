package controllers;

import Entities.Store;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class RegionManagerMainScreenController extends AbstractController implements Initializable {
    protected static HashMap<String, Integer> storeMap = new HashMap<>();
    private static ObservableList<String> comboBoxOptions;
    public static Integer regionID;
    public static String regionName;

    /**
     * JavaFX Initializable interface method, sends a query to db to get locations according to region
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        sendMsg(prepareLocationsMsg());
        saveStoreMap();
        getRegion();
    }

    /**
     * prepares a Message to send a query to server to get all relevant store locations of the region manager connected to the system
     */
    public static Msg prepareLocationsMsg() {
        String query;
        if (myUser.getRole().equals("ceo"))
            query = "SELECT s.* FROM store s";
        else {
            query = "SELECT s.*\n" +
                    "FROM store s\n" +
                    "JOIN regions r ON s.rid = r.rid\n" +
                    "JOIN regions_managers rm ON r.rid = rm.rid\n" +
                    "WHERE rm.uid = " + myUser.getId();
        }
        msg = new Msg(Tasks.getLocations, query);
        return msg;
    }

    /**
      * create hashMap of {store_name:store_id} and saves it to storesMap
      */
    public static void saveStoreMap() {
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
            String query = "SELECT regions_managers.rid, name FROM regions_managers\n" +
                    "JOIN regions ON regions.rid = regions_managers.rid\n" +
                    " WHERE uid = " + myUser.getId();
            msg = new Msg(Tasks.getRegion, query);
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
     * triggered when log out is clicked, resets the user and send back to previous screen
     * @param event
     */
    public void logOutClick() {
        try {
            //Update isLogged
            String loginQuery = "UPDATE users SET IsLogged = 0 WHERE id = " + myUser.getId();
            msg = new Msg(Tasks.Login, Tasks.Update, loginQuery);
            sendMsg(msg);

            if (msg.getBool()) {
                myUser = null;
                start("LoginForm", "Login");
            } else {
                // TODO: handle case where you cant disconnect
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    // have to implement
    @Override
    public void back(MouseEvent event) {
    }

	@Override
	public void setUp(Object[] objects) {
		// TODO Auto-generated method stub
		
	}
}




package controllers;

import Entities.StoreProduct;
import Util.Msg;
import Util.Tasks;
import Entities.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.TableColumn.CellEditEvent;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ManageInventoryScreenController extends BaseRegionManagerSubScreensContoller implements Initializable {
    private List<Store> stores;
    private HashMap<String, Integer> storeMap = new HashMap<>();;
    private ObservableList<String> comboBoxOptions;
    private ObservableList<StoreProduct> itemObsList;
    private List<StoreProduct> updatedStoreProductsList;


    @FXML
    ComboBox<String> locationComboBox;
    @FXML
    TableView<StoreProduct> itemsTableView;
    @FXML
    TableColumn<StoreProduct, String> itemCol;
    @FXML
    TableColumn<StoreProduct, Integer> quantityCol;
    @FXML
    TableColumn<StoreProduct, Integer> limitCol;

    /**
     * Overrides the initialize method of JavaFX - initializes our controller
     * loads to locations comboBox the relevant store locations whether regionManager or CEO
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (LoginController.user.getRole().equals("region_manager")) {
            // add region store locations to ComboBox
            getRegionManagerLocations();

            // load comboBox options to comboBox
            loadLocationsComboBox();

            // setup Table columns to listen on observable list
            tableColInitialization();

        } else if (LoginController.role.equals("CEO")) {

        }
    }

    /**
     * send a query to server to get all relevant store locations of the region manager connected to the system
     */
    public void getRegionManagerLocations() {
        String query = "SELECT s.*\n" +
                "FROM store s\n" +
                "JOIN region r ON s.rid = r.rid\n" +
                "JOIN regionmanagers rm ON r.rid = rm.rid\n" +
                "WHERE rm.uid = " + LoginController.user.getId();
        msg = new Msg(Tasks.getLocations, query);
        sendMsg(msg);
        saveStoreMap();
    }

    /**
     * create hashMap of {store_name:store_id} and saves it to storesMap
     */
    public void saveStoreMap() {
        List<Store> stores = msg.getArr(Store.class);
        // generate HashMap for storing sname:sid key-value pairs
        for (Store s : stores) {
            storeMap.put(s.getName(), s.getSid());
        }
    }

    /**
     * loads the location comboBox with the relevant locations
     */
    public void loadLocationsComboBox() {
        List<String> options = new ArrayList<>();
        options.addAll(storeMap.keySet());
        comboBoxOptions = FXCollections.observableArrayList(options);
        locationComboBox.setItems(comboBoxOptions);
    }

    /**
     * initializes the tableview - sets up property factory, and initializes it with empty observable list
     */
    public void tableColInitialization() {
        itemCol.setCellValueFactory(new PropertyValueFactory<StoreProduct, String>("pname"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<StoreProduct, Integer>("quantity"));
        limitCol.setCellValueFactory(new PropertyValueFactory<StoreProduct, Integer>("minLimit"));
        itemObsList = FXCollections.observableArrayList();
        itemsTableView.setItems(itemObsList);

        // make minLim editable
        itemsTableView.setEditable(true);
        // This line makes the field editable, if we remove the instance of IntegerStringConverter, it
        // requires to change type of minLimit to String
        limitCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

    /**
     * send query to db to get all of the store products of the store that was chosen
     * and displays it in tableView
     * @param event clicking on a comboBox option
     */
    public void loadItemsToTable(ActionEvent event) {
        String storeName = locationComboBox.getSelectionModel().getSelectedItem().toString();
        int sid = storeMap.get(storeName);

        String query = "SELECT storeproduct.*\n" +
                "FROM store\n" +
                "JOIN storeproduct ON store.sid = storeproduct.sid\n" +
                "JOIN product ON product.pid = storeproduct.pid\n" +
                "WHERE store.sid = " + sid;

        msg = new Msg(Tasks.getStoreProducts, query);
        sendMsg(msg);

        List<StoreProduct> storeProducts = (msg.getArr(StoreProduct.class));
        itemObsList.clear();
        itemObsList.addAll(storeProducts);

        // Set up a new array list to store any changed minLimit for products
        updatedStoreProductsList = new ArrayList<>();
    }

    /**
     * adds the edited product to a List of products to update in db
     * the List in initialized every time we pick another store location (in <code>loadItemsToTable</code> func)
     * @param event catches the CellEditEvent
     */
    public void onMinQuantityCommit(CellEditEvent event) {
        StoreProduct storeProd = (StoreProduct) event.getRowValue();
        storeProd.setMinLimit((Integer) event.getNewValue());

        // TODO: ceck if there is a product of this type in the list: change if there is, add if not
        updatedStoreProductsList.add(storeProd);
    }

    /**
     * triggered when we click 'Save' - sends update query to db
     * @param event catches the ActionEvent
     */
    public void updateMinQuantity(ActionEvent event) {
        if (updatedStoreProductsList.size() == 0) {
            return;
        }

        String query = buildMinQuantityUpdateQuery();
        Msg msg = new Msg(Tasks.updateProdMinLimit, query);
        sendMsg(msg);
    }

    /**
     * builds an update query for updating the db with new min quantitys
     * example of a query will look like that :
     *                UPDATE storeproduct
     *                SET lim = (case WHEN pid = 1 AND sid = 1 then 15
     *       				       WHEN pid = 2 AND sid = 1 then 15
     *                           end)
     *                WHERE pid in (1,2) AND sid = 1;
     * @return an update query
     */
    public String buildMinQuantityUpdateQuery() {
        StringBuilder query = new StringBuilder();
        StringBuilder condition = new StringBuilder("WHERE pid in (");
        int currStoreId = updatedStoreProductsList.get(0).getSid();

        // Build query
        query.append("UPDATE storeproduct\n SET lim = (case \n");

        for (StoreProduct sp: updatedStoreProductsList) {
            query.append(String.format("WHEN pid = %d AND sid = %d then %d\n", sp.getPid(), currStoreId, sp.getMinLimit()));
        }
        query.append("\nend)\n");

        //
        StringBuilder pids = new StringBuilder();
        for (StoreProduct sp: updatedStoreProductsList) {
            pids.append(sp.getPid()+",");
        }
        pids.setLength(pids.length() - 1);

        query.append(String.format("WHERE pid in (%s) AND sid = %d", pids.toString(), currStoreId));

        return query.toString();
    }

}

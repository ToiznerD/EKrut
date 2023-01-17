package controllers;

import Entities.StoreProduct;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.TableColumn.CellEditEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * UpdateMinLimitController is the controller for handling minimum limit of products.
 * It extends AbstractController and overrides methods of it.
 */
public class UpdateMinLimitController extends AbstractController implements Initializable {
    private HashMap<String, Integer> storeMap;
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
        // add store locations to ComboBox
        storeMap = RegionManagerMainScreenController.storeMap;

        // load comboBox options to comboBox
        RegionManagerMainScreenController.loadLocationsComboBox(locationComboBox);

        // setup Table columns to listen on observable list
        tableColInitialization();

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
        if (locationComboBox.getSelectionModel().getSelectedItem() == null)
            return;

        String storeName = locationComboBox.getSelectionModel().getSelectedItem().toString();
        int sid = storeMap.get(storeName);

        String query = "SELECT store_product.*, product.pname\n" +
                "FROM store\n" +
                "JOIN store_product ON store.sid = store_product.sid\n" +
                "JOIN product ON product.pid = store_product.pid\n" +
                "WHERE store.sid = " + sid;

        msg = new Msg(Tasks.Select, query);
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
        // get the StoreProduct that was edited
        StoreProduct updatedstoreProd = (StoreProduct) event.getRowValue();

        // Check if this product is already in the list, if it is just update the existing
        for (StoreProduct sp: updatedStoreProductsList) {
            if (sp.getPid() == updatedstoreProd.getPid()) {
                sp.setMinLimit((Integer) event.getNewValue());
                return;
            }
        }

        // if the product is not in the list, add it
        updatedstoreProd.setMinLimit((Integer) event.getNewValue());
        updatedStoreProductsList.add(updatedstoreProd);
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
        msg = new Msg(Tasks.Update, query);
        sendMsg(msg);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (msg.getBool()) {
            alert.setTitle("Success");
            alert.setHeaderText("Minimum Limit updated successfully");
        } else {
            alert.setTitle("Error");
            alert.setHeaderText("Error updating minimum limit");
        }
        alert.showAndWait();
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
        query.append("UPDATE store_product\n SET lim = (case \n");

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

    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     */
    @Override
    public void back(MouseEvent event) {
        try {
            // go back to previous screen
            start("ManageInventoryScreen", "Manage Inventory");
        } catch (IOException e) {
            // TODO: handle exception
        }
    }



	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub
		
	}
}

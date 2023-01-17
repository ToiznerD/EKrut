package controllers;


import Entities.StoreProduct;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import Entities.ResupplyRequest;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.TableColumn.CellEditEvent;


import static Util.Tasks.*;

public class CreateResupplyRequestController extends AbstractController {
    private ObservableList<ResupplyRequest> requestsObsList;
    private HashMap<String, Integer> storeMap = RegionManagerMainScreenController.storeMap;
    private HashMap<String, Integer> productMap = new HashMap<>();
    private HashMap<String, Integer> operationEmployeesMap = new HashMap<>();


    @FXML
    ComboBox storeLocationsComboBox;
    @FXML
    ComboBox productsComboBox;
    @FXML
    ComboBox userIDComboBox;
    @FXML
    TextField pnameTxtField;
    @FXML
    TextField userIdTxtField;
    @FXML
    TextField quantityTxtField;
    @FXML
    Button addRequestBtn;
    @FXML
    Button saveRequestsBtn;
    @FXML
    TableView<ResupplyRequest> requestsTable;
    @FXML
    TableColumn<ResupplyRequest,String> storeCol;
    @FXML
    TableColumn<ResupplyRequest, String> productCol;
    @FXML
    TableColumn<ResupplyRequest, Integer> userCol;
    @FXML
    TableColumn<ResupplyRequest, Integer> quantityCol;
    @FXML
    Label formErrorLabel;
    @FXML
    Label tableErrorLabel;

    @FXML
    public void initialize() {
        initializeRequestsTable();
        // load comboBox options to comboBox
        RegionManagerMainScreenController.loadLocationsComboBox(storeLocationsComboBox);

    }

    private void initializeRequestsTable() {
        requestsObsList = FXCollections.observableArrayList();
        storeCol.setCellValueFactory(new PropertyValueFactory<ResupplyRequest, String>("sname"));
        productCol.setCellValueFactory(new PropertyValueFactory<ResupplyRequest, String>("pname"));
        userCol.setCellValueFactory(new PropertyValueFactory<ResupplyRequest, Integer>("uid"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<ResupplyRequest, Integer>("quantity"));
        requestsTable.setItems(requestsObsList);

        requestsTable.setEditable(true);
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        loadEmployeesIds();

    }

    public void onCellEdit(CellEditEvent event) {
        ResupplyRequest updatedRequest = (ResupplyRequest) event.getRowValue();
        updatedRequest.setQuantity((Integer) event.getNewValue());
    }

    public void storeOptionOnAction() {
        productsComboBox.getItems().clear();
        loadProductsComboBox();
    }

    public void loadProductsComboBox() {
        String sname = storeLocationsComboBox.getSelectionModel().getSelectedItem().toString();
        String query = "SELECT sp.*, p.pname\n" +
                        "FROM store_product sp\n" +
                        "JOIN store s ON s.sid = sp.sid\n" +
                        "JOIN product p ON p.pid = sp.pid\n" +
                        "WHERE s.name = '" + sname + "'";
        msg = new Msg(Tasks.Select, query);
        sendMsg(msg);

        if (msg.getBool()) {
            List<StoreProduct> productList = msg.getArr(StoreProduct.class);
            List<String> pnames = new ArrayList<>();
            for (StoreProduct p : productList) {
                pnames.add(p.getPname());
                productMap.put(p.getPname(), p.getPid());
            }

            ObservableList<String> comboBoxOptions = FXCollections.observableArrayList(pnames);
            productsComboBox.setItems(comboBoxOptions);
        }
    }

    public void addRequestClick() {
        formErrorLabel.setText("");

        String pname,uid,quantity, sname = storeLocationsComboBox.getSelectionModel().getSelectedItem().toString();
        Integer intUid, intQuantity;

        // get pname, uid and handle with wrong details
        try {
            pname = productsComboBox.getSelectionModel().getSelectedItem().toString();
            uid = (userIDComboBox.getSelectionModel().getSelectedItem().toString()).split(" :")[0];
        } catch(NullPointerException e) {
            e.printStackTrace();
            formErrorLabel.setText("* No Products/Operation Employees, cant create request");
            return;
        }

        // get quantity and perform input check
        quantity = quantityTxtField.getText();
        if (quantity.equals("")) {
            formErrorLabel.setText("* Please fill quantity field");
            return;
        }
        try {
            intUid = Integer.valueOf(uid);
            intQuantity = Integer.valueOf(quantity);
        } catch (NumberFormatException e) {
            formErrorLabel.setText("* Please fill a valid quantity");
            return;
        }

        // create a new request if there is no request for this product yet
        for (ResupplyRequest request : requestsObsList) {
            if (request.getSname().equals(sname) && request.getPname().equals(pname)) {
                formErrorLabel.setText("* There is a request for that product\n  please edit or remove it");
                return;
            }
        }
        ResupplyRequest request = new ResupplyRequest(sname,pname,intUid,intQuantity);
        requestsObsList.add(request);
    }


    public void loadEmployeesIds() {
        String query = "SELECT id, name FROM ekrut.users WHERE role = \"operation_employee\"";


        msg = new Msg(Select, query);
        sendMsg(msg);
        ArrayList<List<Object>> returnArr = msg.getRawArray();
        ArrayList<String> operationEmployees = new ArrayList<>();

        for (List<Object> operationEmployee: returnArr) {
            operationEmployees.add(((Integer)operationEmployee.get(0)).toString() + " : " + (String) operationEmployee.get(1));
            operationEmployeesMap.put((String) operationEmployee.get(1), (Integer)operationEmployee.get(0));
        }

        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList(operationEmployees);
        userIDComboBox.setItems(comboBoxOptions);

    }

    public void saveRequestsClick() {
        if (requestsObsList.size() == 0) {
            tableErrorLabel.setText("* No request to save");
            return ;
        }

        tableErrorLabel.setText("");
        formErrorLabel.setText("");

        StringBuilder query = new StringBuilder("INSERT INTO resupply_request (sid,pid,uid,quantity,status) VALUES ");
        String sname, pname, uid, quantity;
        for (ResupplyRequest r : requestsObsList) {
            sname = r.getSname(); pname = r.getPname(); uid = String.valueOf(r.getUid()); quantity = String.valueOf(r.getQuantity());
            query.append("("+storeMap.get(sname)+","+productMap.get(pname)+","+uid+","+quantity+",'pending'), ");
        }
        // remove ", " from end of query
        query.setLength(query.length()-2);

        msg = new Msg(Insert, query.toString());
        sendMsg(msg);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (msg.getBool()) {
            alert.setTitle("Success");
            alert.setHeaderText("Resupply requests were added successfully");
        } else {
            alert.setTitle("Error");
            alert.setHeaderText("Resupply requests were not added successfully");
        }
        alert.showAndWait();

        requestsObsList.clear();

    }

    public void removeRequestClick() {
        ResupplyRequest selectedItem = requestsTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            tableErrorLabel.setText("* No request was chosen");
        }
        requestsTable.getItems().remove(selectedItem);
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
            start("ManageInventoryScreen", "Manage Inventory");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

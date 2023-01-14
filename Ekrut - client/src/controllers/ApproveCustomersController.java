package controllers;

import Entities.User;
import Util.Msg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

import static Util.Tasks.Select;
import static Util.Tasks.Update;

public class ApproveCustomersController extends AbstractController {

    @FXML
    TableView<User> newCustomersTable;
    @FXML
    TableColumn<User, Integer> idCol;
    @FXML
    TableColumn<User, String> nameCol;
    @FXML
    TableColumn<User, String> addressCol;
    @FXML
    TableColumn<User, String> phoneCol;
    @FXML
    TableColumn<User, String> emailCol;
    @FXML
    Button approveCustomerBtn;
    @FXML
    Button disapproveCustomerBtn;
    @FXML
    Label notificationLabel;
    @FXML
    ObservableList<User> userObservableList;

    @FXML
    public void initialize() {
        tableInitialization();
        getNewCustomers();
    }

    /**
     * initialize table with observableList saved as a field
     */
    private void tableInitialization() {
        idCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        userObservableList = FXCollections.observableArrayList();
        newCustomersTable.setItems(userObservableList);
    }

    /**
     * get new customers from db (customers from all regions)
     */
    private void getNewCustomers() {
        String query = "SELECT u.* FROM customer c\n" +
                "JOIN users u ON c.id = u.id\n" +
                "WHERE status = 'Pending';";
        msg = new Msg(Select, query);
        sendMsg(msg);

        List<User> usersArray = msg.getArr(User.class);
        userObservableList.addAll(usersArray);
    }

    /**
     * gets triggered when region manager clicked on <code>approveCustomerBtn</code>
     * calls <code>sendStatusUpdate</code> that updates the user specified status to Approved
     */
    public void approveBtnClick() {
        sendStatusUpdate("Approved");
    }

    /**
     * gets triggered when region manager clicked on <code>disapproveCustomerBtn</code>
     * calls <code>sendStatusUpdate</code> that updates the user specified status to Not Approved
     */
    public void disapproveBtnClick() {
        sendStatusUpdate("Not Approved");
    }

    /**
     * sends a query to update the customer status in the db
     * @param status customer status enum to be updated in the db
     */
    private void sendStatusUpdate(String status) {
        notificationLabel.setText("");

        // extract choice from table
        User targetUser = newCustomersTable.getSelectionModel().getSelectedItem();
        if (targetUser == null)
            notificationLabel.setText("* No User Was Chosen");

        String query = "UPDATE customer\n" +
                "SET status = '" + status + "'\n" +
                "WHERE id = " + targetUser.getId();
        msg = new Msg(Update, query);
        sendMsg(msg);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (msg.getBool()) {
            alert.setTitle("Success");
            alert.setHeaderText("Customer id: " + targetUser.getId() + " changed to " + status);
        } else {
            alert.setTitle("Error");
            alert.setHeaderText("Error updating status");
        }
        alert.showAndWait();

        // reload userlist
        userObservableList.clear();
        getNewCustomers();
    }

    @Override
    public void setUp(Object... objects) {

    }

    @Override
    public void back(MouseEvent event) {
        try {
            // go back to previous screen
            start("RegionManagerMainScreen", "Region Manager Dashboard");
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}

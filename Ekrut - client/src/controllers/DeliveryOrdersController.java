package controllers;

import java.io.IOException;
import java.util.HashMap;

import Entities.TableOrders;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DeliveryOrdersController extends AbstractController{
	
	private ObservableList<TableOrders> ordersList = FXCollections.observableArrayList();
	private HashMap<String, String> statusMap;
    @FXML
    private Button approveBtn;
	
    @FXML
    private ImageView backBtn;
    
    @FXML
    private ImageView refreshBtn;
    
    @FXML
    private Label errorLbl;
    
    @FXML
    private TextField OrderIdLbl;
    
	@FXML
	private TableColumn<TableOrders, Integer> idCell;
	
	@FXML
	private TableColumn<TableOrders, String> nameCell, addressCell, phoneCell, dateCell, timeCell, statusCell;

	@FXML
	private TableView<TableOrders> ordersTable;

	
    final int distance = 3;
    final int droneAvailability = 2;
    final int shippingCharge = 1;
	
	@FXML
	protected void initialize() {
		setOrdersList();
		idCell.setCellValueFactory(new PropertyValueFactory<TableOrders, Integer>("OrderID"));
		nameCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("RecieverName"));
		addressCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("RecieverAddress"));
		phoneCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("RecieverPhone"));
		dateCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("ShippingDate"));
		timeCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("ShippingTime"));
		statusCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("Status"));
		ordersTable.setItems(ordersList);
	}
    
    private void setOrdersList() {
		msg = new Msg(Tasks.DeliveryOrders,
				   "select o.oid, u.name, o.shipping_address, u.phone, o.ord_date, o.ord_time, o.ord_status\r\n"
				+ "	from users u,orders o\r\n"
				+ "	where o.cid=u.id and o.method=\"delivery\";");
		sendMsg(msg);
		ordersList.clear();
		statusMap = new HashMap<>();
		for (TableOrders order : msg.getArr(TableOrders.class)) {
			ordersList.add(order);
			statusMap.put(Integer.toString(order.getOrderID()), order.getStatus());
		}
    }
    
	@Override
	public void back(MouseEvent event) {
		try {
			start("DeliveryOperatorPanel", "Delivery Operator Panel");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void approveDelivery(MouseEvent event) {
		String selectedID = OrderIdLbl.getText();
		errorLbl.setText("");
		errorLbl.setTextFill(Color.web("Red"));
		if (!checkInput(selectedID)) 
			return;
		msg = new Msg(Tasks.Update, "UPDATE orders SET ord_status = 'in progress' WHERE oid = "+selectedID);
		sendMsg(msg);	
		if (msg.getBool()) {
			errorLbl.setTextFill(Color.web("Green"));
			errorLbl.setText("Order approved successfully");
			initialize();
		}
	}

	private boolean checkInput(String id) {
		if (statusMap.containsKey(id) && statusMap.get(id).equals("pending"))
			return true;
		if (id.isEmpty())
			errorLbl.setText("Error: Order ID cannot be empty");
		else if (!id.matches("[0-9]+"))
			errorLbl.setText("Error: Input must be numbers [0-9]");
		else if(!statusMap.containsKey(id))
			errorLbl.setText("Order ID not found");
		else if(statusMap.get(id).equals("in progress"))
			errorLbl.setText("Error: Order already approved");
		else if(statusMap.get(id).equals("completed")) 
			errorLbl.setText("Error: Order completed");
		return false;
	}


}

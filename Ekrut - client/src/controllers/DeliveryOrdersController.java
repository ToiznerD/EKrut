package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tables.TableOrders;
import tables.TableProd;

public class DeliveryOrdersController extends AbstractController{
	
	private ObservableList<TableOrders> ordersList = FXCollections.observableArrayList();

    @FXML
    private Button approveBtn;
	
    @FXML
    private ImageView backBtn;
	
	@FXML
	private TableColumn<TableOrders, Integer> idCell;
	
	@FXML
	private TableColumn<TableOrders, String> nameCell, addressCell, phoneCell;
	
	@FXML
	private TableColumn<TableOrders, String> dateCell;
	
	@FXML
	private TableColumn<TableOrders, String> timeCell;
	
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
		ordersTable.setItems(ordersList);
	}
    
    private void setOrdersList() {
		msg = new Msg(Tasks.DeliveryOrders,
				"select o.oid, u.name, ordr.shipping_address, u.phone, o.ord_date, o.ord_time\r\n"
				+ "from users u,orders o, order_report ordr\r\n"
				+ "where o.cid=u.id and o.oid=ordr.oid and o.method=\"delivery\";\r\n");
		sendMsg(msg);
		ordersList.clear();
		ordersList.addAll(msg.getArr(TableOrders.class));
    }
    
    private String computeSupplyDate(String supplyDate) {
    	int computedTime = distance + droneAvailability + shippingCharge;
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    	Calendar cal = Calendar.getInstance();
        try {  
            cal.setTime(sdf.parse(supplyDate));  
        } catch(ParseException e){  
             e.printStackTrace();  
        }
        cal.add(Calendar.DAY_OF_MONTH, computedTime);
        return sdf.format(cal.getTime());
    }

	
	@Override
	public void back(MouseEvent event) {
		try {
			start("DeliveryOperatorPanel", "Delivery Operator Panel");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

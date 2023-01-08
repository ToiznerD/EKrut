package controllers;

import java.io.IOException;

import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import Entities.ResupplyProduct;

public class ResupplyReqController extends AbstractController {

	private ObservableList<ResupplyProduct> prodList = FXCollections.observableArrayList();;
	@FXML
	private ImageView backBtn;
	@FXML
	private Label storeLbl;
	@FXML
	private TableColumn<ResupplyProduct, Integer> pidCell, sidCell, addQuantCell, aQuantCell;
	@FXML
	private TableColumn<ResupplyProduct, String> pnameCell, snameCell, statusCell;
	@FXML
	private TextField pidText, sidText;

	@FXML
	private Button updateBtn;

	@FXML
	private TableView<ResupplyProduct> Table;
	@FXML
	private Label errorLbl;

	@FXML
	protected void initialize() {
		sidCell.setCellValueFactory(new PropertyValueFactory<ResupplyProduct, Integer>("Sid"));
		snameCell.setCellValueFactory(new PropertyValueFactory<ResupplyProduct, String>("Sname"));
		pidCell.setCellValueFactory(new PropertyValueFactory<ResupplyProduct, Integer>("Pid"));
		pnameCell.setCellValueFactory(new PropertyValueFactory<ResupplyProduct, String>("Pname"));
		aQuantCell.setCellValueFactory(new PropertyValueFactory<ResupplyProduct, Integer>("Aquant"));
		addQuantCell.setCellValueFactory(new PropertyValueFactory<ResupplyProduct, Integer>("Rquant"));
		statusCell.setCellValueFactory(new PropertyValueFactory<ResupplyProduct, String>("Status"));
		Table.setItems(prodList);
	}

	@Override
	public void back(MouseEvent event) {
		try {
			start("OperationEmpPanel", "Operation Employee panel");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void update(ActionEvent event) {
		if (checkInput()) {
			msg = new Msg(Tasks.Update,
					"UPDATE store_product SET quantity = quantity + (SELECT rs.quantity FROM resupply_request rs WHERE pid = "
							+ pidText.getText() + " AND sid = " + sidText.getText() + ") WHERE pid = "
							+ pidText.getText() + " AND sid = " + sidText.getText());
			sendMsg(msg);
			msg = new Msg(Tasks.Update,"UPDATE resupply_request SET status=\"Done\" WHERE pid=" + pidText.getText()
							+ " AND sid = " + sidText.getText());
			sendMsg(msg);
		}
		if (msg.getInt() != 0)
			setUp();
		else
			errorLbl.setText("Error: product id not found");
	}

	private boolean checkInput() {
		if (sidText.getText().isEmpty() || pidText.getText().isEmpty()) {
			errorLbl.setText("Error: Product id and Actual quantity cannot be empty");
			return false;
		}
		try {
			Integer.parseInt(pidText.getText());
			Integer.parseInt(sidText.getText());
		} catch (NumberFormatException e) {
			errorLbl.setText("Error: Input must be numbers [0-9]");
			return false;
		}
		errorLbl.setText("");
		return true;
	}

	@Override
	public void setUp(Object... objects) {
		msg = new Msg(Tasks.RequiredStock,
				"SELECT DISTINCT s.sid,s.name,p.pid,p.pname,sp.quantity,rs.quantity,rs.status FROM store s,product,store_product sp,product p,resupply_request rs WHERE s.sid=rs.sid AND p.pid=rs.pid AND sp.pid=rs.pid AND status=\"Pending\"");
		//store id,Store name,product id,product name,store_product quantity,resupply_request quantity,status
		sendMsg(msg);
		prodList.clear();
		prodList.addAll(msg.getArr(ResupplyProduct.class));
	}

}

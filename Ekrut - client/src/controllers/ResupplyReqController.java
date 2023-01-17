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
import Entities.User;

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

	/**
	 * initializes the Resupply requests table to listen to observable list <code>prodList</code>
	 */
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

    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     * @throws IOException if there is an issue loading the FXML file
     */
	@Override
	public void back(MouseEvent event) {
		try {
			start("OperationEmpPanel", "Operation Employee panel");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * updates quantity of the request that was chosen, adds the number of items that was requested to the store product in the db
	 * @param event - ActionEvent, click on update
	 */
	@FXML
	public void update(ActionEvent event) {
		if (checkInput()) {
			msg = new Msg(Tasks.Update,
					"UPDATE store_product SET quantity = quantity + (SELECT rs.quantity FROM resupply_request rs WHERE pid = "
							+ pidText.getText() + " AND sid = " + sidText.getText() + ") WHERE pid = "
							+ pidText.getText() + " AND sid = " + sidText.getText());
			sendMsg(msg);
			msg = new Msg(Tasks.Update, "UPDATE resupply_request SET status=\"Done\" WHERE pid=" + pidText.getText()
					+ " AND sid = " + sidText.getText());
			sendMsg(msg);
		}
		if (msg.getInt() != 0)
			setUp();
		else
			errorLbl.setText("Error: product id not found");
	}

	/**
	 * method to check valid inputs from text fields
	 * @return true if input is valid, false otherwise
	 */
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

	/**
	 * overrides the setUp in <code>AbstractController</code> class initializes resupply requests
	 * @param objects - can receive multiple objects
	 */
	@Override
	public void setUp(Object... objects) {
		msg = new Msg(Tasks.Select,
				"SELECT DISTINCT s.sid,s.name,p.pid,p.pname,sp.quantity as available,rs.quantity as requested ,rs.status FROM store s,store_product sp,product p,resupply_request rs " +
						"WHERE rs.uid = " + myUser.getId() + " AND s.sid=rs.sid AND p.pid=rs.pid AND sp.pid=rs.pid AND status='Pending' AND sp.sid = rs.sid");


		//store id,Store name,product id,product name,store_product quantity,resupply_request quantity,status
		sendMsg(msg);
		prodList.clear();
		prodList.addAll(msg.getArr(ResupplyProduct.class));
	}

}

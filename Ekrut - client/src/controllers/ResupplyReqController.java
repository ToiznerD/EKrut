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
import Entities.TableProd;

public class ResupplyReqController extends AbstractController {

	private ObservableList<TableProd> prodList = FXCollections.observableArrayList();;
	@FXML
	private ImageView backBtn;
	@FXML
	private Label storeLbl;
	@FXML
	private TableColumn<TableProd, Integer> idCell, rQuantCell, aQuantCell;
	@FXML
	private TableColumn<TableProd, String> nameCell, statusCell;
	@FXML
	private TextField aQuantText, pidText;

	@FXML
	private Button updateBtn;

	@FXML
	private TableView<TableProd> Table;
	@FXML
	private Label errorLbl;

	@FXML
	protected void initialize() {
		updateList();
		idCell.setCellValueFactory(new PropertyValueFactory<TableProd, Integer>("id"));
		rQuantCell.setCellValueFactory(new PropertyValueFactory<TableProd, Integer>("Rquant"));
		aQuantCell.setCellValueFactory(new PropertyValueFactory<TableProd, Integer>("Aquant"));
		nameCell.setCellValueFactory(new PropertyValueFactory<TableProd, String>("name"));
		statusCell.setCellValueFactory(new PropertyValueFactory<TableProd, String>("status"));
		Table.setItems(prodList);
	}

	private void updateList() {
		msg = new Msg(Tasks.RequiredStock,
				"SELECT sp.pid,sp.pname,sp.lim,sp.quantity FROM storeproduct sp WHERE sp.sid = 2");
		sendMsg(msg);
		prodList.clear();
		prodList.addAll(msg.getArr(TableProd.class));
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
			msg = new Msg(Tasks.Update, "UPDATE storeproduct SET quantity = " + aQuantText.getText() + " WHERE pid = "
					+ pidText.getText() + " AND sid = 2");
			sendMsg(msg);
		}
		if (msg.getInt() != 0)
			updateList();
		else
			errorLbl.setText("Error: product id not found");
	}

	private boolean checkInput() {
		if (aQuantText.getText().isEmpty() || pidText.getText().isEmpty()) {
			errorLbl.setText("Error: Product id and Actual quantity cannot be empty");
			return false;
		}
		try {
			Integer.parseInt(aQuantText.getText());
			Integer.parseInt(pidText.getText());
		} catch (NumberFormatException e) {
			errorLbl.setText("Error: Input must be numbers [0-9]");
			return false;
		}
		errorLbl.setText("");
		return true;
	}

}

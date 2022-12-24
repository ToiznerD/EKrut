package controllers;

import java.util.ArrayList;

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
import tables.TableProd;

public class ResupplyReqController extends AbstractController {

	public static ArrayList<TableProd> tprod;
	public static int updateResult;
	private ObservableList<TableProd> prodList = FXCollections.observableArrayList();;
	@FXML
	private ImageView backBtn;
	@FXML
	private Label regionLbl;
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
		//sendQuery(Tasks.RequiredStock, "SELECT * FROM reqproduct");
		prodList.clear();
		prodList.addAll(tprod);
	}
	@FXML
	public void back(ActionEvent event) {
		
	}
	@FXML
	public void update(ActionEvent event) {
		//if (checkInput())
			//sendQuery(Tasks.Update,
					//"UPDATE reqproduct SET actual = " + aQuantText.getText() + " WHERE id = " + pidText.getText());
		if (updateResult != 0)
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

package controllers;

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

public class ResupplyReqController extends AbstractController{
	//load from db
	//update quant on finish
	//change status 
	//update manager

	private ObservableList<TableProd> prodList;
    @FXML
    private ImageView backBtn;
    @FXML
    private Label regionLbl;
    @FXML
	private TableColumn<TableProd, Integer> idCell, rQuantCell, aQuantCell;
    @FXML
	private TableColumn<TableProd, String> nameCell, statusCell;
    @FXML
    private TextField aQuantText,pidText;

	@FXML
    private Button updateBtn;

    @FXML
    private TableView<TableProd> Table;
	@FXML
	private Label errorLbl;
    @FXML
    protected void initialize() {
		prodList = FXCollections.observableArrayList();
		idCell.setCellValueFactory(new PropertyValueFactory<TableProd, Integer>("id"));
		rQuantCell.setCellValueFactory(new PropertyValueFactory<TableProd, Integer>("Rquant"));
		aQuantCell.setCellValueFactory(new PropertyValueFactory<TableProd, Integer>("Aquant"));
		nameCell.setCellValueFactory(new PropertyValueFactory<TableProd, String>("name"));
		statusCell.setCellValueFactory(new PropertyValueFactory<TableProd, String>("status"));
		prodList.add(new TableProd(1,"s",2,1));
		Table.setItems(prodList);
    }

    @FXML
    public void update(ActionEvent event) {
    	String query;
    	if (checkInput())
    		 query = "UPDATE reqproduct SET actual = "+aQuantText.getText()+" WHERE id = "+pidText.getText();
    	
    }
    private boolean checkInput() {
    	if (aQuantText.getText().isEmpty() || pidText.getText().isEmpty()) {
    		errorLbl.setText("Error: Product id and Actual quantity cannot be empty");
    		return false;
    	}
    	try {
    		Integer.parseInt(aQuantText.getText());
    		Integer.parseInt(pidText.getText());
    	}catch (NumberFormatException e) {
    		errorLbl.setText("Error: Input must be numbers [0-9]");
    		return false;
    	}
    	errorLbl.setText("");
    	return true;
    }
}

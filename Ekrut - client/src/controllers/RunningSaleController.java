package controllers;

import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class RunningSaleController extends AbstractController {

	@FXML
    private ComboBox<StringBuilder> lstSales;
	
    @FXML
    private Button btnRun;

    @FXML
    private Label lblErrSales;

    
    /**
     * Initializes the form by setting up the combo box for sales,
     * 
     */
    @FXML
    public void initialize() {
    	//Initializing sales combo-box
    	String salesQuery = null;
    	int userId = myUser.getId();
    	//Getting the region sales related to this marketing employee that are not running already
    	salesQuery = "SELECT distinct s.saleName FROM sale_initiate s, region_employee r1 WHERE  s.active = 0 AND "
    			+ "r1.rid = (SELECT r.rid FROM region_employee r WHERE " + userId + " = r.uid)";
	    msg = new Msg(Tasks.Select, salesQuery);
	    sendMsg(msg);
    	ObservableList<StringBuilder> salesList = FXCollections.observableArrayList(msg.getArr(StringBuilder.class));
    	lstSales.setItems(salesList);
    }
    
    /**
     * Check if a sale has been selected and display an appropriate message.
     *
     * @return true if a sale has been selected, false otherwise
     */
    public boolean checkSale() {
    	if(lstSales.getSelectionModel().getSelectedItem() == null) {
    		lblErrSales.setText("Please choose sale");
    		return false;
    	}
    	lblErrSales.setText("");
    	return true;
    }
    
    /**
     * Method to run a sale.
     * 
     * @param event the event that triggered the method call
     */
    public void runSale(ActionEvent event) {
    	if(checkSale()) {
    		String query = "UPDATE sale_initiate SET active = 1 WHERE saleName = '" + lstSales.getValue() + "'";
    		msg = new Msg(Tasks.Update, query);
	    	sendMsg(msg);
	    	
	    	//Display a confirmation pop-up message and resetting the fields
			Alert alert = new Alert(Alert.AlertType.NONE, "Running sale succeeded !", ButtonType.FINISH);
	        alert.setTitle("Success");
	        alert.showAndWait();
	        
	        //After running a sale, initialize the rest of sales that are available from the database
	        initialize();
	        lblErrSales.setText("");
    	}
    	else {
    		//Display an error pop-up message
			Alert alert = new Alert(Alert.AlertType.ERROR, "Running sale failed !",ButtonType.OK);
	        alert.setTitle("Failed");
	        alert.setHeaderText("Error");
	        alert.showAndWait();
    	}
    }
    
    /**
     * Handles the mouse event of the back button.
     * 
     * @param event the mouse event that triggered this method
     */
    @Override
	public void back(MouseEvent event) {
		try {
    		start("MarketingEmployeePanel","Marketing Employee Panel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub
		
	}

}

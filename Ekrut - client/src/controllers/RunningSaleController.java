package controllers;

import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class RunningSaleController extends AbstractController {

	@FXML
    private ComboBox<StringBuilder> lstSales;
	
    @FXML
    private Button btnRun;

    @FXML
    private Label lblRunningMsg;

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
    	int userRegion = myUser.getId();
    	
    	//Getting the region sales related to this marketing employee that are not running already
    	if(myUser.getRole().equals("marketing_employee"))
	    	salesQuery = "SELECT saleName FROM sale_initiate WHERE rid = " + userRegion + " AND active = 0";
    	
    	//Getting all the sales that are not running already to the CEO
    	else if(myUser.getRole().equals("ceo"))
    		salesQuery = "SELECT saleName FROM sale_initiate WHERE active = 0";
    	
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
	    	lblRunningMsg.setText("Running sale succeeded");
    	}
    	else
    		lblRunningMsg.setText("Running sale failed");
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

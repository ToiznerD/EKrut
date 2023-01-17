package controllers;

import Util.Msg;
import Util.Tasks;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


/**
 * A controller class for a JavaFX application that allows marketing managers to create sale templates.
 */
public class SaleTemplateCreationController extends AbstractController{

    @FXML
    private Button btnCreation;
    
    @FXML
    private TextField txtTemplateName;
    
    @FXML
    private TextField txtDiscount;
    
    @FXML
    private Label lblErrTemplateName;
    
    @FXML
    private Label lblErrDiscount;
    
    /**
     * Checks if the template name entered in the txtTemplateName text field is valid.
     *
     * @return true if the template name is valid, false otherwise
     */
    public boolean checkTemplateName() {
    	if(txtTemplateName.getText().equals("")) {
    		lblErrTemplateName.setText("Please enter template name");
    		return false;
    	}
    	String query = "SELECT templateName FROM sale_template WHERE templateName = '" + txtTemplateName.getText() + "'";
    	msg = new Msg(Tasks.Select, query);
    	sendMsg(msg);
    	if(msg.getBool()) {
    		lblErrTemplateName.setText("This template name already exists");
    		return false;
    	}
    	lblErrTemplateName.setText("");
    	return true;
    }
    
    /**
     * Checks if the discount entered in the txtDiscount text field is valid.
     *
     * @return true if the discount is valid, false otherwise
     */
    public boolean CheckDiscount()
    {
    	int discountInt;
    	if(txtDiscount.getText().equals("")) {
    		lblErrDiscount.setText("Please enter discount");
    		return false;
    	}
    	String discount = txtDiscount.getText();
        for (int i = 0; i < discount.length(); i++) {
            if (discount.charAt(i) < '0' || discount.charAt(i) > '9') {
            	lblErrDiscount.setText("Please enter discount with numbers only");
                return false;
            }
        }
        discountInt = Integer.parseInt(discount);
        if(discountInt <= 0 || discountInt >= 100) {
        	lblErrDiscount.setText("Please enter numbers between 1-99");
        	return false;
        }
        lblErrDiscount.setText("");
        return true;
    }
    
    /**
     * Creates a new sale template in the database.
     *
     * @param event the event that triggered the method call
     */
    public void buttonCreation(ActionEvent event) {
    	
    	// Flag to track whether template creation has succeeded
    	boolean flag = true;
    	
    	 // Check template name and discount for validity
    	if(!checkTemplateName())
    		flag = false;
    	if(!CheckDiscount())
    		flag = false;
    	
    	// If both template name and discount are valid, create template in database
    	if(flag) {
    		//Insert discount as a frequent between 0 to 1 to the database
    		double discount = Double.parseDouble(txtDiscount.getText());
    		discount =  discount / 100;
    		
    		String query = "INSERT into sale_template (templateName, discount)" + "VALUES ('" + txtTemplateName.getText() + "' , " + discount +")";
	    	msg = new Msg(Tasks.Insert, query);
	    	sendMsg(msg);
	    	
	    	//Display a confirmation pop-up message and resetting the fields
			Alert alert = new Alert(Alert.AlertType.NONE, "Sale template creation succeeded !", ButtonType.FINISH);
	        alert.setTitle("Success");
	        alert.showAndWait();
	        resetFields();
    	}
		else {
			//Display an error pop-up message
			Alert alert = new Alert(Alert.AlertType.ERROR, "Sale template creation failed !",ButtonType.OK);
	        alert.setTitle("Failed");
	        alert.setHeaderText("Error");
	        alert.showAndWait();
		}
    }
    
    /**
     * Resets the fields on the GUI to their default state.
     */
    public void resetFields() {
        txtTemplateName.setText("");
        txtDiscount.setText("");
    }
	    
    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     * @throws IOException if there is an issue loading the FXML file
     */
	@Override
	public void back(MouseEvent event) {
		try {
    		start("MarketingManagerPanel","Marketing Manager Panel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setUp(Object... objects) {
		//Not implemented		
	}
}

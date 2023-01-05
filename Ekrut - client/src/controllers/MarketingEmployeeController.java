package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MarketingEmployeeController extends AbstractController {

    @FXML
    private Button btnRun;

    @FXML
    private Button btnLogout;
    
    @FXML
    private Label welcomeLbl;
    
    /**
     * Initializes the Marketing Employee Panel by setting the welcome label to display the user's name.
     * 
     */
    @FXML
    public void initialize() {
    	String welcome = welcomeLbl.getText() + " ";
    	welcome = myUser.getName() == null ? welcome + myUser.getUsername() : welcome + myUser.getName();
    	welcomeLbl.setText(welcome);
    }
    
    /**
     * This method is called when the user clicks the "Run sale" button. It opens a new window
     * with the "Running Sale Form".
     * 
     * @param event the event that triggered this method call (clicking the "Run sale" button)
     * @throws Exception if an error occurs while trying to open the new window
     */
    public void runSaleButton(ActionEvent event) throws Exception {
    	try {
    		start("RunningSaleForm", "Running Sale Form");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public void back(MouseEvent event) {
		//Not implemented
	}
}
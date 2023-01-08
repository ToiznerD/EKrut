package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MarketingManagerController extends AbstractController {

    @FXML
    private Button btnInitiate;
    
    @FXML
    private Button btnCreateNewSaleTemplate;

    @FXML
    private Button btnLogout;
    
    @FXML Label welcomeLbl;
    
    /**
     * Initializes the Marketing Manager Panel by setting the welcome label to display the user's name.
     * 
     */
    @FXML
    public void initialize() {
    	String welcome = welcomeLbl.getText() + " ";
    	welcome = myUser.getName() == null ? welcome + myUser.getUsername() : welcome + myUser.getName();
    	welcomeLbl.setText(welcome);
    }
    
    /**
     * This method is called when the user clicks the "Initiate new sale" button. It opens a new window
     * with the "Sale Initiate Form".
     * 
     * @param event the event that triggered this method call (clicking the "Initiate new sale" button)
     * @throws Exception if an error occurs while trying to open the new window
     */
    public void InitiateSaleButton(ActionEvent event) throws Exception {
    	try {
    		start("SaleInitiateForm", "Sale Initiate Form");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * This method is called when the user clicks the "Create new sale template" button. It opens a new window
     * with the "Sale Template Creation Form".
     * 
     * @param event the event that triggered this method call (clicking the "Create new sale template" button)
     * @throws Exception if an error occurs while trying to open the new window
     */
    public void CreateSaleTemplateButton(ActionEvent event) throws Exception {
    	try {
    		start("SaleTemplateCreationForm", "Sale Template Creation Form");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public void back(MouseEvent event) {
		//Not implemented
	}

	@Override
	public void setUp(Object[] objects) {
		// TODO Auto-generated method stub
		
	}
}



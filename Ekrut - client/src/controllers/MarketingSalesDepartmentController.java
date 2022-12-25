package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MarketingSalesDepartmentController extends AbstractController {

    @FXML
    private Button btnCreateNewSaleTemplate;
    
    public void CreateSaleTemplateButton(ActionEvent event) throws Exception {
    	try {
    		start("SaleTemplateCreationForm", "Sale Template Creation Form");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void back() {
		// TODO Auto-generated method stub
		
	}
}
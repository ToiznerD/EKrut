package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SaleTemplateCreationController extends AbstractController{

	    @FXML
	    private Button btnInitiate;
    
//    public void BackButton(ActionEvent event) throws Exception {
//    	try {
//    		start("MarketingSalesDepartmentPanel","Marketing Sales Department Panel");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
    
	@Override
	public void back() {
		try {
    		start("MarketingSalesDepartmentPanel","Marketing Sales Department Panel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

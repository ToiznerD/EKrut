package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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
	public void back(MouseEvent event) {
		try {
    		start("MarketingSalesDepartmentPanel","Marketing Sales Department Panel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

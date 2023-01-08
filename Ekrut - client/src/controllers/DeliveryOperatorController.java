package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;



public class DeliveryOperatorController extends AbstractController{
	

    @FXML
    private Button deliveryBtn;

    public void MoveToForm(ActionEvent event) {
		try {
			start("DeliveryOrdersForm", "Delivery Orders");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    

	@Override
	public void back(MouseEvent event) {
		// TODO Auto-generated method stub
	}


	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub
		
	}
    
}

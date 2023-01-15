package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * DeliveryOperatorController is a controller class for Main Delivery Operator panel
 * for FXML file: DeliveryOperatorPanel.fxml
 * It has one method "moveToForm" that will move to Delivery Orders Form screen
 * that method connected to 'deliveryBtn' button
 * Methods "back" and "setUp" are not implemented (extends AbstractController)
 */
public class DeliveryOperatorController extends AbstractController{
	

    @FXML
    private Button deliveryBtn;

/**
 * moveToForm is called when a mouse event occurs. It opens a the window "Delivery Orders".
 *@param event The MouseEvent that triggers the method.
 *@throws IOException if there is an issue loading the "DeliveryOrdersForm" form.
 */
    @FXML
    public void moveToForm(MouseEvent event) {
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

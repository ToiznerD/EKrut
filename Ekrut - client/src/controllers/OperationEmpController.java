package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * ManageInventoryController is the main controller class for the operation employee.
 * It extends AbstractController and overrides methods of it.
 */
public class OperationEmpController extends AbstractController {

	@FXML
	private Button logoutBtn, stockBtn;

	@FXML
	private Label welcomeText;

    /**
     * switchScreen is called when a action event occurs. It opens a the window "ResupplyReqScreen".
     * @param event The ActionEvent that triggers the method.
     */
	@FXML
	void switchScreen(ActionEvent event) {
		try {
			start("ResupplyReqScreen", "Resupply stock requirment");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void back(MouseEvent event) {		
	}

	@Override
	public void setUp(Object... objects) {		
	}

}

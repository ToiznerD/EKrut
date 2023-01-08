package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class OperationEmpController extends AbstractController {

	@FXML
	private Button logoutBtn;

	@FXML
	private Button stockBtn;

	@FXML
	private Label welcomeText;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUp(Object[] objects) {
		// TODO Auto-generated method stub
		
	}

}

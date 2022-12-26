package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class CustomerServiceController extends AbstractController{

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnEdit;

    @FXML
    void openCreateMember(ActionEvent event) {

    }

    @FXML
    void openEdit(ActionEvent event) throws IOException {
    	start("EditUser", "Edit Users");
    }

	@Override
	public void back(MouseEvent event) {
		//Not implemented
	}

}

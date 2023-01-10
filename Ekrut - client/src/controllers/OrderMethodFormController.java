package controllers;

import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class OrderMethodFormController extends AbstractController {
	private boolean subscriber;
	
	//@FXML
	//lstRegions
	
    @FXML
    private Label lblAddress;

    @FXML
    private Label lblRecieverDetails;

    @FXML
    private DatePicker lstSupplyDate;

//    @FXML
//    public void initialize() {
//    	//Initializing regions combo-box
//    	String regionsQuery = "SELECT name FROM regions";
//    	Msg msg = new Msg(Tasks.Select, regionsQuery);
//    	sendMsg(msg);
//    	ObservableList<StringBuilder> regionsList = FXCollections.observableArrayList(msg.getArr(StringBuilder.class));
//    	lstRegion.setItems(regionsList);
//    }

	@Override
	public void setUp(Object... objects) {
		subscriber = (boolean) objects[0];
	}

	@Override
	public void back(MouseEvent event) {
		try {
			start("CustomerPanel", "Customer Dashboard", subscriber);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}



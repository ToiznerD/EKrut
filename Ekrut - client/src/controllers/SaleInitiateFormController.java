package controllers;

import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SaleInitiateFormController extends AbstractController implements Initializable {
    

    @FXML
    private Button btnInitiate;

    @FXML
    private ComboBox<String> lstRegion;

    @FXML
    private ComboBox<String> lstSaleTemplate;
    
    @FXML
    private DatePicker StartingDate;
    
    @FXML
    private DatePicker EndingDate;
    
    @FXML
    private ComboBox<String> lstStartingHours;

    @FXML
    private ComboBox<String> lstEndingHours;

    @FXML
    private TextField txtSaleName;
    
    @FXML
    private Label lblErrDate;
    
    @FXML
    private Label lblErrTime;
    
    @FXML
    private Label lblErrName;
    
    
    @Override
    public void initialize(java.net.URL url, ResourceBundle rb) {
    	//Disabling past dates for StartingDate
    	StartingDate.setDayCellFactory(picker -> new DateCell() {
    		public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
    		}
    	});
    	
    	//Disabling past dates for EndingDate
    	EndingDate.setDayCellFactory(picker -> new DateCell() {
    		public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
    		}
    	});
    	
    	//Initializing combo-boxes
    	
    	ObservableList<String> RegionList = FXCollections.observableArrayList("Karmiel","Haifa","Hertzelya","Tel-Aviv","Jerusalem","Eilat");
    	lstRegion.setItems(RegionList);
    	
    	ObservableList<String> StartingHoursList = FXCollections.observableArrayList("00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00",
    			"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00");
    	lstStartingHours.setItems(StartingHoursList);
    	
    	ObservableList<String> EndingHoursList = FXCollections.observableArrayList("00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00",
    			"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00");
    	lstEndingHours.setItems(EndingHoursList);
    	
    	//ObservableList<String> TemplatesList = FXCollections.observableArrayList();
    	
    	//ResultSet rs = DBController.select("SELECT * FROM templates");
    	
    	//lstSaleTemplate.setItems(TemplatesList);
    }
    
    public void checkDates(ActionEvent event) {
    	//Checking validation of dates
    	LocalDate endingDate = EndingDate.getValue();
    	LocalDate startingDate = StartingDate.getValue();
    	if(endingDate != null && startingDate != null) {
	    	if(endingDate.isBefore(startingDate))
	    		lblErrDate.setText("Please fix the dates");
	    	else
	    		lblErrDate.setText("");
    	}
    	checkTimes(event);
    }
    
    public void checkTimes(ActionEvent event) {
    	//Checking validation of times
    	LocalDate endingDate = EndingDate.getValue();
    	LocalDate startingDate = StartingDate.getValue();
    	String startingHour = lstStartingHours.getSelectionModel().getSelectedItem();
    	String endingHour = lstEndingHours.getSelectionModel().getSelectedItem();
    	int start,end;
    	if(startingHour != null && endingHour != null) {
	    	start = Integer.parseInt(startingHour.substring(0, 2));
	    	end = Integer.parseInt(endingHour.substring(0, 2));
	    	if(endingDate != null && startingDate != null) {
		    	if(endingDate.isEqual(startingDate) && end <= start)
		    		lblErrTime.setText("Please fix the times");
		    	else
		    		lblErrTime.setText("");
	    	}
	    }
    }
    
    @Override
    public void back(MouseEvent event) {
    	try {
			start("MarketingManagerPanel","MarketManagerPanel");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void InitiateSaleButton(ActionEvent event) {
    	
    }
    
    /*public void getSelectRegion(ActionEvent event) {
    	String region = lstRegion.getSelectionModel().getSelectedItem().toString();
    }*/
    
    
}

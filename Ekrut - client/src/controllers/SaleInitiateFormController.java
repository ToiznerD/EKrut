package controllers;

import java.time.LocalDate;
import java.util.ResourceBundle;

import Util.Msg;
import Util.Region;
import Util.SaleTemplate;
import Util.Tasks;
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

public class SaleInitiateFormController extends AbstractController implements Initializable { 

    @FXML
    private Button btnInitiate;

    @FXML
    private ComboBox<String> lstSaleTemplate;
    
    @FXML
    private ComboBox<String> lstRegion;
    
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
    
    @FXML
    private Label lblInitiateMsg;
    
    @FXML
    private Label lblErrTemplate;
    
    @FXML
    private Label lblErrRegion;
    
    
    /**
     * Initializes the form by setting up the combo boxes for sale templates and regions,
     * and configuring the date picker fields to only allow selecting dates that are not in the past.
     * 
     * @param url the URL of the FXML file that defined the form
     * @param rb the resource bundle to use for localizing the form
     */
    @Override
    public void initialize(java.net.URL url, ResourceBundle rb) {
    	//Initializing templates combo-box
    	String templatesQuery = "SELECT * FROM sale_template";
    	msg = new Msg(Tasks.Select, templatesQuery);
    	sendMsg(msg);
    	ObservableList<SaleTemplate> saleTemplates = FXCollections.observableArrayList(msg.getArr(SaleTemplate.class));
    	ObservableList<String> saleTemplatesList = FXCollections.observableArrayList();
    	for(SaleTemplate s : saleTemplates) {
    		saleTemplatesList.add(s.getName());
    	}
    	lstSaleTemplate.setItems(saleTemplatesList);
    	
    	
    	//Initializing regions combo-box
    	String regionsQuery = "SELECT * FROM regions";
    	msg = new Msg(Tasks.Select, regionsQuery);
    	sendMsg(msg);
    	ObservableList<Region> regions = FXCollections.observableArrayList(msg.getArr(Region.class));
    	ObservableList<String> regionsList = FXCollections.observableArrayList();
    	//System.out.println(regionsList.toArray());
    	for(Region r : regions) {
    		regionsList.add(r.getName());
    	}
    	lstRegion.setItems(regionsList);
    	
    	
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
    	
    	//Initializing hours combo-boxes
    	
    	ObservableList<String> StartingHoursList = FXCollections.observableArrayList("00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00",
    			"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00");
    	lstStartingHours.setItems(StartingHoursList);
    	
    	ObservableList<String> EndingHoursList = FXCollections.observableArrayList("00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00",
    			"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00");
    	lstEndingHours.setItems(EndingHoursList);
    	
    	lblInitiateMsg.setText("");
    	
    }
    
    /**
     * Validates the starting and ending dates and times entered by the user.
     * If the dates and times are invalid, displays an error message to the user.
     */
    public void checkDates(ActionEvent event) {
    	//Checking validation of dates
    	LocalDate endingDate = EndingDate.getValue();
    	LocalDate startingDate = StartingDate.getValue();
    	if(endingDate != null && startingDate != null) {
	    	if(endingDate.isBefore(startingDate)) {
	    		lblErrDate.setText("Please fix the dates");
	    		if(!(lblErrTime.getText() == "Please choose times"))
	    			lblErrTime.setText("");
	    	}
	    	else
	    	{
	    		lblErrDate.setText("");
	    		checkTimes(event);
	    	}
    	}
    	
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
    
    public boolean checkSaleName() {
    	if(txtSaleName.getText() == "") {
    		lblErrName.setText("Please enter sale name");
    		return false;
    	}
    	String query = "SELECT saleName FROM sale_initiate WHERE saleName = '" + txtSaleName.getText() + "'";
    	msg = new Msg(Tasks.Select, query);
    	sendMsg(msg);
    	if(msg.getBool()) {
    		lblErrName.setText("This sale name already exists");
    		return false;
    	}
    	lblErrName.setText("");
    	return true;
    }
    
    public boolean checkTemplate() {
    	if(lstSaleTemplate.getSelectionModel().getSelectedItem() == null) {
    		lblErrTemplate.setText("Please choose template");
    		return false;
    	}
    	lblErrTemplate.setText("");
    	return true;
    }
    
    public boolean checkRegion() {
    	if(lstRegion.getSelectionModel().getSelectedItem() == null) {
    		lblErrRegion.setText("Please choose region");
    		return false;
    	}
    	lblErrRegion.setText("");
    	return true;
    }
    
    @Override
    public void back() {
    	try {
			start("MarketingManagerPanel","MarketManagerPanel");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void InitiateSaleButton(ActionEvent event) {
    	
    	//checking if there are no errors
    	
    	boolean flag = true;
    	if(!checkSaleName())
    		flag = false;
    	if(!checkRegion())
    		flag = false;
    	if(!checkTemplate()) 
    		flag = false;
    	
    	if(StartingDate.getValue() == null || EndingDate.getValue() == null) {
    		lblErrDate.setText("Please choose dates");
    		flag = false;
    	}
    	
    	if(lstStartingHours.getSelectionModel().getSelectedItem() == null || lstEndingHours.getSelectionModel().getSelectedItem() == null) {
    		lblErrTime.setText("Please choose times");
    		flag = false;
    	}
    	else if(!(lblErrTime.getText() == "Please fix the times"))
    				lblErrTime.setText("");
    	
    	if(!(lblErrDate.getText() == "") || !(lblErrTime.getText() == ""))
    		flag = false;
    	
    	if(flag) {
	    		//Getting templateId
		    	String query = "SELECT * FROM sale_template WHERE templateName = '" + lstSaleTemplate.getValue() + "'";
		    	msg = new Msg(Tasks.Select, query);
		    	sendMsg(msg);
		    	int templateId = msg.getObj(0);
		    	query = "SELECT * FROM regions WHERE name = '" + lstRegion.getValue() + "'";
		    	msg = new Msg(Tasks.Select, query);
		    	sendMsg(msg);
		    	int rid = msg.getObj(0);
		    	query = "INSERT into sale_initiate (templateId, saleName, rid, startDate, endDate, startHour, endHour) "
		    			+ "VALUES (" + templateId + " , '" + txtSaleName.getText() + "' , " + rid + " , '" + StartingDate.getValue() + 
		    			"' , '" + EndingDate.getValue() + "' , '" + lstStartingHours.getValue() + "' , '" + lstEndingHours.getValue() + "')";
		    	msg = new Msg(Tasks.Insert, query);
		    	sendMsg(msg);  
		    	lblInitiateMsg.setText("Sale initiate succeeded");
	    	}
    	else {
    		lblInitiateMsg.setText("Sale initiate failed");
    	}
    }
}

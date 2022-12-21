package controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;


public class DeliveryController{

    @FXML
    private Button btnBack, btnLogOut, btnDeliveryOdrers, btnApproveDelivery;
    
    private Stage stage;
    private Scene scene;
    
    final int distance = 3;
    final int droneAvailability = 2;
    final int shippingCharge = 1;
    
    @FXML
    public void handleButton(ActionEvent e) throws IOException{
    	
    	if (e.getSource()==btnDeliveryOdrers) {
    		start(e,"/clientFxml/DeliveryOrdersForm.fxml");
    		loadDeliveriesData();
    	}

    	else if (e.getSource()==btnLogOut) {
    		//disconnect client and exit app
    		System.exit(0);
    	}

    	else if (e.getSource()==btnBack) {
    		//back to previous screen(panel)
    		start(e,"/clientFxml/DeliveryOperatorPanel.fxml");
    	}
    	
    	else if (e.getSource()==btnApproveDelivery) {
    		/*approve delivery order, compute delivery
    		  date and time and popup msg to use*/
    		computeSupplyDate("");
    		
    	}
    	
    }
    
    private void loadDeliveriesData() {
    	//take data of deliveries from DB 
    }

    private void start(ActionEvent e, String screen) throws IOException {
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(FXMLLoader.load(getClass().getResource(screen)));
		stage.setScene(scene);
		stage.show();
    }
    
    
    private String computeSupplyDate(String supplyDate) {
    	int computedTime = distance + droneAvailability + shippingCharge;
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    	Calendar cal = Calendar.getInstance();
        try {  
            cal.setTime(sdf.parse(supplyDate));  
        } catch(ParseException e){  
             e.printStackTrace();  
        }
        cal.add(Calendar.DAY_OF_MONTH, computedTime);
        return sdf.format(cal.getTime());
    }
    
    private void messageClientShipping(String supplyDate) {
    	Alert clientAlert = new Alert(AlertType.INFORMATION);
    	clientAlert.setContentText("Estimated delivery date : "
    			+computeSupplyDate(supplyDate));
    	clientAlert.showAndWait();
    }
    
}

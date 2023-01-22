package client;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import controllers.LoginControllerTest;
import controllers.StockStatusReportControllerTest;

@Suite
@SelectClasses({ LoginControllerTest.class, StockStatusReportControllerTest.class })
public class clientAppTest {

	public clientAppTest() {
		// TODO Auto-generated constructor stub
	}

}




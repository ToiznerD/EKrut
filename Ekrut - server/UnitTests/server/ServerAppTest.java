package server;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import takser.TaskerTest;
import utils.ReportGeneratorTest;
@Suite
@SelectClasses({ TaskerTest.class, ReportGeneratorTest.class })
public class ServerAppTest {

	public ServerAppTest() {
		// TODO Auto-generated constructor stub
	}

}




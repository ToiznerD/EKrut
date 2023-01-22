package server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import takser.TaskerTest;
import utils.ReportGeneratorTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({ TaskerTest.class, ReportGeneratorTest.class })
public class ServerAppTest {

	public ServerAppTest() {
		// TODO Auto-generated constructor stub
	}

}

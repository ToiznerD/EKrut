package takser;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DBHandler.DBController;
import Util.Msg;
import Util.Tasks;
import server.UserManager;
import tasker.Tasker;


public class TaskerTest {
	Msg msg;
	
	@BeforeEach
	void setUp() throws Exception {
		// connect to DB
		DBController.setDB_prop("localhost", "ekrut", "root", "Nati147258369");
		// set all users to be logged off in db
		ResultSet rs = DBController.select("SELECT id from users");
		while(rs.next()) 
			DBController.update("UPDATE users SET isLogged = 0 WHERE id = " + rs.getInt(1));
		
	}

	// 
	//
	//
	//
	@Test
	void correctUnsubscribedCustomerLoginTest() {		
		String username = "a_customer1", password = "123";
		String query = String.format("SELECT * FROM users WHERE user='%s' AND password = '%s'", username, password);
		
		msg = new Msg(Tasks.Login, query);
		
		assertFalse(UserManager.isConnected(1));
		try {
			Tasker.taskerHandler(msg, null);
		} catch (SQLException e) {
			fail();
		}
				
		assertTrue(UserManager.isConnected(1));
		
	}
	
	
	
	
	
	
	
	
	
	
	

}

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
	
	//set up before each test case: set DB connection, reset isLogged columns for all users and clear users map
	@BeforeEach
	void setUp() throws Exception {
		// connect to DB
		DBController.setDB_prop("localhost", "ekrut", "root", "erik1502");
		// set all users to be logged off in db
		ResultSet rs = DBController.select("SELECT id from users");
		while(rs.next()) 
			DBController.update("UPDATE users SET isLogged = 0 WHERE id = " + rs.getInt(1));
		UserManager.Clear();
	}
	
	//Login process for given user
	void connectUser(String user, String pass) {
		String username = user, password = pass;
		String query = String.format("SELECT * FROM users WHERE user='%s' AND password = '%s'", username, password);
		
		msg = new Msg(Tasks.Login, query);
		try {
			Tasker.taskerHandler(msg, null);
		} catch (SQLException e) {
			fail();
		}
	}

	// checking functionality: Logging in the system with an unsubscribed customer that exists in the db
	// input data: username = "a_customer1", password = "123"
	// expected result: true: successful login
	@Test
	void ExistingUnsubscribedCustomer_Login() {	
		String username = "a_customer1", password = "123";
		int userID = 1;
		assertFalse(UserManager.isConnected(userID)); //before login
		connectUser(username, password);
		
		assertTrue(msg.getBool());
		assertTrue(UserManager.isConnected(userID));
		assertTrue(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Logging in the system with a subscribed customer that exists in the db
	// input data: username = "as_customer1", password = "123"
	// expected result: true: successful login
	@Test
	void ExistingSubscribedCustomer_Login() {
		String username = "as_customer1", password = "123";
		int userID = 4;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);
		
		assertTrue(msg.getBool());
		assertTrue(UserManager.isConnected(userID));
		assertTrue(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Logging in the system with a service user that exists in the db
	// input data: username = "service", password = "123"
	// expected result: true: successful login
	@Test
	void ExistingServiceUser_Login() {	
		String username = "service", password = "123";
		int userID = 11;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);
		
		assertTrue(msg.getBool());
		assertTrue(UserManager.isConnected(userID));
		assertTrue(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Logging in the system with a region manager that exists in the db
	// input data: username = "rm_south", password = "123"
	// expected result: true: successful login
	@Test
	void ExistingRegionManagerUserLoginTest() {	
		String username = "rm_south", password = "123";
		int userID = 12;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);
		
		assertTrue(msg.getBool());
		assertTrue(UserManager.isConnected(userID));
		assertTrue(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Logging in the system with a ceo user that exists in the db
	// input data: username = "ceo", password = "123"
	// expected result: true: successful login
	@Test
	void ExistingCeoUser_Login() {	
		String username = "ceo", password = "123";
		int userID = 19;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);
		
		assertTrue(msg.getBool());
		assertTrue(UserManager.isConnected(userID));
		assertTrue(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Logging in the system with a delivery user that exists in the db
	// input data: username = "delivery1", password = "123"
	// expected result: true: successful login
	@Test
	void ExistingDeliveryUser_Login() {	
		String username = "delivery1", password = "123";
		int userID = 20;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);
		
		assertTrue(msg.getBool());
		assertTrue(UserManager.isConnected(userID));
		assertTrue(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Logging in the system with a marketing user that exists in the db
	// input data: username = "me_south", password = "123"
	// expected result: true: successful login
	@Test
	void ExistingMarketingUser_Login() {	
		String username = "me_south", password = "123";
		int userID = 16;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);
		
		assertTrue(msg.getBool());
		assertTrue(UserManager.isConnected(userID));
		assertTrue(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Logging in the system with an operation user that exists in the db
	// input data: username = "operation1", password = "123"
	// expected result: true: successful login
	@Test
	void ExistingOperationUser_Login() {	
		String username = "operation1", password = "123";
		int userID = 22;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);
		
		assertTrue(msg.getBool());
		assertTrue(UserManager.isConnected(userID));
		assertTrue(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Trying login to logged user
	// input data: username = "a_customer1", password = "123"
	// expected result: first login in successful (true)
	//					second login failed (false + "User already logged in")
	@Test
	void AlreadyLoggedExistingCustomer_Login() {
		String username = "a_customer1", password = "123";
		int userID = 1;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);//first login
		
		assertTrue(UserManager.isConnected(userID));
		assertTrue(msg.getBool());
		
		connectUser(username, password);//second login
		assertEquals(msg.getResponse(),"User already logged in");
		assertFalse(msg.getBool());
		
		assertTrue(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Trying login to non existing user
	// input data: username = "not_a_user", password = "123"
	// expected result: login failed (false + "Wrong details")
	@Test
	void InvalidUsername_Login() {	
		String username = "not_a_user", password = "123";
		int userID = -1;
		connectUser(username, password);
		
		assertFalse(msg.getBool());
		assertEquals(msg.getResponse(),"Wrong details");
		assertFalse(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Trying login to existing user with wrong password
	// input data: username = "a_customer1", password = "12345"
	// expected result: login failed (false + "Wrong details")
	@Test
	void WrongPassword_Login() {	
		String username = "a_customer1", password = "12345";
		int userID = 1;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);

		assertFalse(msg.getBool());
		assertEquals(msg.getResponse(),"Wrong details");
		assertFalse(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Trying login to null user
	// input data: username = null, password = "123"
	// expected result: login failed (false + "Wrong details")
	@Test
	void NullUsername_Login() {	
		String username = null, password = "123";
		int userID = -1;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);

		assertFalse(msg.getBool());
		assertEquals(msg.getResponse(),"Wrong details");
		assertFalse(UserManager.containesInMap(userID));
	}
	
	// checking functionality: Trying login to existing user with null password
	// input data: username = "a_customer1", password = null
	// expected result: login failed (false + "Wrong details")
	@Test
	void NullPassword_Login() {	
		String username = "a_customer1", password = null;
		int userID = 1;
		assertFalse(UserManager.isConnected(userID));//before login
		connectUser(username, password);

		assertFalse(msg.getBool());
		assertEquals(msg.getResponse(),"Wrong details");
		assertFalse(UserManager.containesInMap(userID));
	}
	
}
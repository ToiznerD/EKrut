package controllers;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Entities.User;



class LoginControllerTest {
	User myUser, userInDb;
	LoginController loginController;
	
	public class ConnectionServiceStub implements IConnectionService {

		@Override
		public void connect(String username, String pass) throws IOException {			
			// Mock a server response
			if (!username.equals(userInDb.getUsername()) || !pass.equals(userInDb.getPassword()) || userInDb.isLogged() )
				myUser = null;
			else {
				
				setUser(new User(userInDb.getId(), userInDb.getUsername(), userInDb.getPassword(),
								  userInDb.getRole(), userInDb.getName(), userInDb.getPhone(), userInDb.getAddress(), 
								  userInDb.getEmail(), userInDb.isLogged()));
			}
		}
		
		public void setUser(User user) {
			myUser = user;
		}

		@Override
		public void appConnector(int id) {
			// check if id matches with one in the mock database user
			if (id != userInDb.getId()) {
				myUser = null;
				return;
			}
			
			try {
				connect(userInDb.getUsername(), userInDb.getPassword());
			} catch (IOException e) {
				myUser = null;
				return;
			}
 		}
			
		
	}
	
	@BeforeEach
	void setUp() throws Exception {
		loginController = new LoginController(new ConnectionServiceStub());
	}

	
	// checking functionality: Logging in the system with an unsubscribed customer that exists in the db
	// input data: username: a_customer1, password: 123, userInDb: User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
	// expected result: true: 
	// actual result = true: myUser equals to userInDb  
	@Test
	void correctUnsubscribedCustomerLoginTest() {	
		userInDb = new User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
		try {
			loginController.getConnectionService().connect("a_customer1", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertEquals(myUser, userInDb);
	}
	
	
	// checking functionality: Logging in the system with an UnsubscribedCustomer that doesnt exist in the db
	// input data: username: a_customer1, password: 123, userInDb: User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
	// expected result: true: myUser is not initializes, myUser equals null
	// actual result = true: myUser is not initializes, myUser equals null
	@Test
	void inCorrectUnsubscribedCustomerLoginTest() {	
		userInDb = new User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
		try {
			loginController.getConnectionService().connect("NotMyUsername", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertNotEquals(myUser, userInDb);
	}
	
	// checking functionality: Logging in the system with a subscribed customer that exists in the db
	// input data: username: as_customer1, password: 123, userInDb: User(4,"as_customer1","123","customer","Netanel","0503421312","yang 21","netanel@gmail.com");
	// expected result: true: myUser is initialized to have the details of userInDb 
	// actual result = true: myUser equals to userInDb  
	@Test
	void correctSubscribedCustomerLoginTest() {	
		userInDb = new User(4,"as_customer1","123","customer","Netanel","0503421312","yang 21","netanel@gmail.com");
		try {
			loginController.getConnectionService().connect("as_customer1", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertEquals(myUser, userInDb);
	}
	
	// checking functionality: Logging in the system with a service employee that exists in the db
	// input data: username: service, password: 123, userInDb: User(11,"service","123","service","danny","0554334123","palmach","service@gmail.com")
	// expected result: true: myUser equals to userInDb  
	// actual result = true: myUser equals to userInDb  
	@Test
	void correctServiceEmployeeLoginTest() {	
		userInDb = new User(11,"service","123","service","danny","0554334123","palmach","service@gmail.com");
		try {
			loginController.getConnectionService().connect("service", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	
		assertEquals(myUser, userInDb);
	}
	
	// checking functionality: Logging in the system with a region_manager that exists in the db
	// input data: username: rm_south, password: 123, userInDb: User(12,"rm_south","123","region_manager","yaniv","0505555555","radof 2","rm@gmail.com");
	// expected result: true: myUser equals to userInDb  
	// actual result = true: myUser equals to userInDb  
	@Test
	void correctRegionManagerLoginTest() {	
		userInDb = new User(12,"rm_south","123","region_manager","yaniv","0505555555","radof 2","rm@gmail.com");
		try {
			loginController.getConnectionService().connect("rm_south", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertEquals(myUser, userInDb);
	}
	
	// checking functionality: Logging in the system with a marketing_manager that exists in the db
	// input data: username: market_manager, password: 123, userInDb: User(15,"market_manager","123","marketing_manager","eliyahu","0546458137","keren hayesod 91","mm@gmail.com");
	// expected result: true: myUser equals to userInDb  
	// actual result = true: myUser equals to userInDb  	
	@Test
	void correctMarketManagerLoginTest() {	
		userInDb = new User(15,"market_manager","123","marketing_manager","eliyahu","0546458137","keren hayesod 91","mm@gmail.com");
		try {
			loginController.getConnectionService().connect("market_manager", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertEquals(myUser, userInDb);
	}
	
	// checking functionality: Logging in the system with a marketing_employee that exists in the db
	// input data: username: me_south, password: 123, userInDb: User(16,"me_south","123","marketing_employee","shaul","0546998455","ilanot 34","me@gmail.com");
	// expected result: true: myUser equals to userInDb  
	// actual result = true: myUser equals to userInDb  
	@Test
	void correctMarketingEmployeeLoginTest() {	
		userInDb = new User(16,"me_south","123","marketing_employee","shaul","0546998455","ilanot 34","me@gmail.com");
		try {
			loginController.getConnectionService().connect("me_south", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertEquals(myUser, userInDb);
	}
	
	// checking functionality: Logging in the system with a ceo that exists in the db
	// input data: username: ceo, password: 123, userInDb: User(19,"ceo","123","ceo","Martha","0544123355","palmach 12","ceo@gmail.com");
	// expected result: true: myUser equals to userInDb  
	// actual result = true: myUser equals to userInDb  
	@Test
	void correctCEOLoginTest() {	
		userInDb = new User(19,"ceo","123","ceo","Martha","0544123355","palmach 12","ceo@gmail.com");
		try {
			loginController.getConnectionService().connect("ceo", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertEquals(myUser, userInDb);
	}
	
	// checking functionality: Logging in the system with a delivery employee that exists in the db
	// input data: username: delivery1, password: 123, userInDb: User(20,"delivery1","123","delivery","Erik","0531235476","Kiryat ata 1","delivery1@gmail.com");
	// expected result: true: myUser equals to userInDb  
	// actual result = true: myUser equals to userInDb  
	@Test
	void correctDeliveryLoginTest() {	
		userInDb = new User(20,"delivery1","123","delivery","Erik","0531235476","Kiryat ata 1","delivery1@gmail.com");
		try {
			loginController.getConnectionService().connect("delivery1", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertEquals(myUser, userInDb);
	}
	
	
	// checking functionality: Logging in the system with a operation_employee that exists in the db
	// input data: username: operation1, password: 123, userInDb: User(22,"operation1","123","operation_employee","anti","0501231232","jerusalem 21","nati@gmail.com");
	// expected result: true: myUser equals to userInDb  
	// actual result = true: myUser equals to userInDb  
	@Test
	void correctOperationEmployeeLoginTest() {	
		userInDb = new User(22,"operation1","123","operation_employee","anti","0501231232","jerusalem 21","nati@gmail.com");
		try {
			loginController.getConnectionService().connect("operation1", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertEquals(myUser, userInDb);
	}
	
	
	// checking functionality: Logging in the system with empty Strings as credentials
	// input data: username: "", password: "", userInDb: User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
	// expected result: true: myUser does not equal to user in db
	// actual result = true: myUser does not equal to user in db
	@Test
	void EmptyCredentialsLoginTest() {	
		userInDb = new User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
		try {
			loginController.getConnectionService().connect("", "");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertNotEquals(myUser, userInDb);
	}
	
	// checking functionality: Logging in the system with empty password and valid username
	// input data: username: a_customer1, password: "", userInDb: User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
	// expected result: true: myUser exist in db, but the password is incorrect  - myUser not equal to user in db
	// actual result = true: myUser does not equal to user in db
	@Test
	void noPasswordLoginTest() {	
		userInDb = new User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
		try {
			loginController.getConnectionService().connect("a_customer1", "");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertNotEquals(myUser, userInDb);
	}
	
	// checking functionality: Logging in the system with valid password and empty username
	// input data: username: "", password: 123, userInDb: User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
	// expected result: true: myUser does not exist in db - myUser not equal to user in db
	// actual result = true: myUser does not equal to user in db
	@Test
	void noUsernameLoginTest() {	
		userInDb = new User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
		try {
			loginController.getConnectionService().connect("", "123");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertNotEquals(myUser, userInDb);
	}
	
	// checking functionality: Logging in the system as an unsubscribed customer that is already logged in
	// input data: username: a_customer1, password: 123, userInDb: User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com", true);
	// expected result: true: myUser exist in db, but already logged in, and cant log in again - myUser not equal to user in db
	// actual result = true: myUser does not equal to user in db
	@Test
	void loggedInUserLoginTest() {	
		userInDb = new User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com", true);
		try {
			loginController.getConnectionService().connect("", "");
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		assertNotEquals(myUser, userInDb);
	}

	
		// checking functionality: Logging in the system with an unsubscribed customer that exists in the db
		// input data: username: a_customer1, password: 123, userInDb: User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
		// expected result: true: 
		// actual result = true: myUser equals to userInDb  
		@Test
		void correctUnsubscribedCustomerAppLoginTest() {	
			userInDb = new User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
			loginController.getConnectionService().appConnector(1);

			
			assertEquals(myUser, userInDb);
		}
		

		// checking functionality: Logging in the system with an unsubscribed customer that exists in the db
		// input data: username: a_customer1, password: 123, userInDb: User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
		// expected result: true: 
		// actual result = true: myUser equals to userInDb  
		@Test
		void inCorrectUnsubscribedCustomerAppLoginTest() {	
			userInDb = new User(1,"a_customer1","123","customer","ofir","0502222222","keren hayseed","ofir@gmail.com");
			loginController.getConnectionService().appConnector(4);

			
			assertNotEquals(myUser, userInDb);
		}
		
		
	
}



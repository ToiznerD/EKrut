package Entities;

/**
 * User class represents a user in the system. (Entity)
 * It has fields for the information of the user.
 */
public class User {
	private int id;
	private String username, password, role, name, phone, address, email;
	private boolean isLogged;

    /**
     * Constructor for the User class object.
     * @param id : User unique ID
     * @param username : User username to login the system
     * @param password : User password to login the system
     * @param role : User role in the system
     * @param name : User name
     * @param phone : User phone number
     * @param address : User address
     * @param email : User email address
     * @param isLogged : User is currently connected/disconnected 
     */
	public User(int id, String username, String password, String role, String name, String phone, String address,
			String email, boolean isLogged) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.isLogged = isLogged;
	}

    /**
     * Gets the name of the user.
     * @return User name.
     */
	public String getName() {
		return name;
	}

    /**
     * Sets the name of the user.
     * @param name User name.
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets the phone of the user.
     * @return User phone.
     */
	public String getPhone() {
		return phone;
	}

    /**
     * Sets the phone of the user.
     * @param phone : User phone.
     */
	public void setPhone(String phone) {
		this.phone = phone;
	}

    /**
     * Gets the address of the user.
     * @return User address.
     */
	public String getAddress() {
		return address;
	}

    /**
     * Sets the address of the user.
     * @param address : User address.
     */
	public void setAddress(String address) {
		this.address = address;
	}

    /**
     * Gets the email of the user.
     * @return User email.
     */
	public String getEmail() {
		return email;
	}

    /**
     * Sets the email of the user.
     * @param email : User email.
     */
	public void setEmail(String email) {
		this.email = email;
	}

    /**
     * Gets true/false if User is currently logged
     * @return true if connected false if disconnected
     */
	public boolean isLogged() {
		return isLogged;
	}

    /**
     * Sets boolean for user connected/disconnected
     * @param isLogged boolean true=connected, false=disconnected
     */
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

    /**
     * Gets the ID of the user.
     * @return User ID.
     */
	public int getId() {
		return id;
	}

    /**
     * Gets the username of the user.
     * @return User username.
     */
	public String getUsername() {
		return username;
	}

    /**
     * Gets the password of the user.
     * @return User password.
     */
	public String getPassword() {
		return password;
	}

    /**
     * Gets the role of the user.
     * @return User role.
     */
	public String getRole() {
		return role;
	}
	
	
}

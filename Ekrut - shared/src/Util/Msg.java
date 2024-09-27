package Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Entities.OrderDetails;


public class Msg implements Serializable {

	/**
	 * Msg class is used to wrap the data need to be sent from client to server and vice versa.
	 * It implements serializable Interface to send objects over network.
	 * It constructor initializes fields based on the task it gets.
	 */
	private static final long serialVersionUID = 1L;
	private Tasks task;
	private String query, consoleMsg, alertMsg, response;
	private int intReturn, destinationID;
	private OrderDetails order;
	private boolean boolReturn;
	private ArrayList<List<Object>> arrayReturn = new ArrayList<>();

	/**
     * Constructor for the Msg class.
     * @param task : The task to perform.
     * @param objects : Information required for the task, such as query ,order details, messages etc.
     */
	public Msg(Tasks task, Object... objects) {
		switch (task) {
		case Disconnect:
			this.task = task;
			break;
		case popUp:
			this.task = task;
			this.destinationID = (Integer) objects[0];
			this.alertMsg = (String) objects[1];
			break;
		case Login:
			this.task = task;
			this.query = (String) objects[0];
			break;
		case Logout:
			this.task = task;
			break;
		case Order:
			this.task = task;
			order = (OrderDetails) objects[0];
			break;
		default: // sql queries
			this.task = task;
			this.query = (String) objects[0];
		}
	}

	/**
	 * @return the task of Msg sent.
	 */
	public Tasks getTask() {
		return task;
	}

	/**
	 * @return order details data
	 */
	public OrderDetails getOrder() {
		return order;
	}
	
	/**
	 * Set respone to be displayed when Msg sent
	 * @param response message
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	
	public void setArrReturn(ArrayList<List<Object>> arrayReturn) {
		 this.arrayReturn = arrayReturn;
	}
	
	/**
	 * Get the respone of sent Msg
	 * @return respone message
	 */
	public String getResponse() {
		return response;
	}
	
	/**
	 * Get the user ID for sending pop ups
	 * @return destination user ID
	 */
	public int getDestinationID() {
		return destinationID;
	}
	
	/**
	 * Get the message sent with Msg
	 * @return alert message to send
	 */
	public String getAlertMsg() {
		return alertMsg;
	}

	/**
	 * @param value
	 */
	public void setInt(int value) {
		this.intReturn = value;
	}

	/**
	 * Get number of rows updated
	 * @return number of updated row
	 */
	public int getInt() {
		return intReturn;
	}

	/**
	 * Set boolean value that returned when Msg sent
	 * @param value
	 */
	public void setBool(boolean value) {
		this.boolReturn = value;
	}

	/**
	 * Get the query sent with Msg
	 * @return query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Set the console message
	 * @param message: String message to set on console
	 */
	public void setConsole(String message) {
		this.consoleMsg = message;
	}

	/**
	 * @return List of raw returned from sql query 
	 */
	public ArrayList<List<Object>> getRawArray() {
		return arrayReturn;
	}

	/**
	 * @return false if resultset return empty from selected query, true otherwise
	 */
	public boolean getBool() {
		return boolReturn;
	}

	/**
	 * @return console message
	 */
	public String getConsole() {
		return consoleMsg;
	}

	/**
	 * @param <T> class type of wanted return
	 * @param num the number of column in table
	 * @return object class of wanted return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObj(int num) {
		return (T) arrayReturn.get(0).get(num);
	}

	/**
	 * @param <T>  class type of wanted return
	 * @param type ClassName.class , ClassName of wanted type return
	 * @return List of wanted class
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getArr(Class<T> type) {
		List<T> toConvert = new ArrayList<>();
		try {
			for (List<Object> o : arrayReturn)
				toConvert.add((T) type.getConstructors()[0].newInstance(o.toArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toConvert;
	}
}

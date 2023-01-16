package Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Entities.OrderDetails;


public class Msg implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;
	private Tasks task;
	private String query, consoleMsg, alertMsg,response;
	private int intReturn, destinationID;
	private OrderDetails order;
	private boolean boolReturn;
	private ArrayList<List<Object>> arrayReturn = new ArrayList<>();

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
	 * @return Tasks return the Msg task.
	 */
	
	public Tasks getTask() {
		return task;
	}

	public OrderDetails getOrder() {
		return order;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponse() {
		return response;
	}
	public int getDestinationID() {
		return destinationID;
	}
	
	public String getAlertMsg() {
		return alertMsg;
	}

	/**
	 * @param int value
	 */
	public void setInt(int value) {
		this.intReturn = value;
	}

	/**
	 * @return int number updated row
	 */
	public int getInt() {
		return intReturn;
	}

	/**
	 * @param boolean value
	 */
	public void setBool(boolean value) {
		this.boolReturn = value;
	}

	/**
	 * @return String query
	 */
	public String getQuery() {
		return query;
	}

	public void setConsole(String message) {
		this.consoleMsg = message;
	}

	public ArrayList<List<Object>> getRawArray() {
		return arrayReturn;
	}

	/**
	 * @return boolean false if resultset return empty from selected
	 */
	public boolean getBool() {
		return boolReturn;
	}

	/**
	 * @return String message to console
	 */
	public String getConsole() {
		return consoleMsg;
	}

	/**
	 * @param <T> class type of wanted return
	 * @param num the number of column in table
	 * @return <T> object class of wanted return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObj(int num) {
		return (T) arrayReturn.get(0).get(num);
	}

	/**
	 * @param <T>  class type of wanted return
	 * @param type ClassName.class , ClassName of wanted type return
	 * @return List<T> List of wanted class
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

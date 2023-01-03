package Util;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBHandler.DBController;

public class Msg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tasks task;
	private Tasks subTask;
	
	private String query, consoleMsg;
	private int intReturn;
	private boolean boolReturn;
	private ArrayList<List<Object>> arrayReturn = new ArrayList<>();

	public Msg(Tasks task, String query) {
		this.task = task;
		this.query = query;
	}
	
	public Msg(Tasks task, Tasks subTask, String query) {
		this.task = task;
		this.query = query;
		this.subTask = subTask;
	}

	/**
	 * @return Tasks return the Msg task.
	 */
	public Tasks getTask() {
		return task;
	}
	
	public Tasks getSubTask() {
		return subTask;
	}

	public void setSubTask(Tasks subTask) {
		this.subTask = subTask;
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
	public ArrayList<List<Object>> getRawArray(){
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
	 * @param <T> class type of wanted return
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

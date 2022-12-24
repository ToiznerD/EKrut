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
	private String query, consoleMsg;
	private int intReturn;
	private boolean boolReturn;
	private ArrayList<List<Object>> arrayReturn = new ArrayList<>();

	public Msg(Tasks task, String query) {
		this.task = task;
		this.query = query;
	}

	/**
	 * @return Tasks return the Msg task.
	 */
	public Tasks getTask() {
		return task;
	}

	/**
	 * @return int number updated row
	 */
	public int getInt() {
		return intReturn;
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

	/**
	 * @throws Exception SQLException on select error
	 */
	public void taskerHandler() throws SQLException {
		switch (task) {
		case Update:
			intReturn = DBController.update(query);
			break;
		case Select:
			runSelect();
			break;
		default:
			break;
		}
	}

	/**
	 * @throws SQLException error in DB
	 */
	private void runSelect() throws SQLException {
		ResultSet rs = DBController.select(query);
		int columnCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			List<Object> row = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++)
				row.add(rs.getObject(i));
			arrayReturn.add(row);
		}
		boolReturn = arrayReturn.size() == 0 ? false : true;
	}
}

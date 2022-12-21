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

	public Tasks getTask() {
		return task;
	}

	public int getInt() {
		return intReturn;
	}

	public boolean getBool() {
		return boolReturn;
	}

	public String getConsole() {
		return consoleMsg;
	}
	@SuppressWarnings("unchecked")
	public <T> T getObj(int num) {
		return (T) arrayReturn.get(0).get(num);
	}

	@SuppressWarnings("unchecked")

	public <T> List<T> getArr(Class<T> type) {
		List<T> toConvert = new ArrayList<>();
		try {
			for (List<Object> o : arrayReturn)
				toConvert.add((T) type.getConstructors()[0].newInstance(o.toArray()));
		} catch (Exception e) {e.printStackTrace();
		}
		return toConvert;
	}

	public void taskerHandler() throws Exception {
		switch (task) {
		case Update:
			intReturn = DBController.update(query);
			break;
		case Select:
			break;
		case Login:
			runQuery();
			boolReturn = arrayReturn.size() == 0 ? false : true;
			break;
		default:
			break;
		}
	}
	private void runQuery() throws SQLException {
		ResultSet rs = DBController.select(query);
		int columnCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			List<Object> row = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++)
				row.add(rs.getObject(i));
			arrayReturn.add(row);
		}
	}
}

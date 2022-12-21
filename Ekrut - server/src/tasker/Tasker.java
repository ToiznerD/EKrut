package tasker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBHandler.DBController;
import Util.Tasks;
import tables.TableProd;

public class Tasker {
	/*
	 * This method is responsible to handle with tasks related to login page
	 * @params client, task
	 */
	public static ArrayList<Object> parse(ArrayList<Object> msg) {
		Tasks task = (Tasks) msg.get(0);
		String query = (String) msg.get(1);
		switch (task) {
		case Login:
			return loginTask(query);
		case RequiredStock:
			return requiredTask(query);
		case Update:
			return update(query);
		}
		return null;
	}
	private static ArrayList<Object> update(String query){
		ArrayList<Object> returnMsg = new ArrayList<>();
		int result = DBController.update(query);
		returnMsg.add(Tasks.Update);
		returnMsg.add("Update db");
		returnMsg.add(result);
		return returnMsg;
	}
	private static ArrayList<Object> loginTask(String query) {
		//Run query
		ResultSet rs = DBController.select(query); 
		ArrayList<Object> returnMsg = new ArrayList<>();
		try {//Nave
			returnMsg.add(Tasks.Login);
			if(rs.next()) { //Login details found
				returnMsg.add("Sending positive login check to {ip}");
				returnMsg.add(rs.getString(4));
			}//Nave
			else { //Login details not found
				returnMsg.add("Sending negative login check to {ip}");
				returnMsg.add(null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnMsg;
	}
	private static ArrayList<Object> requiredTask(String query) {
		
		//Run query
		ResultSet rs = DBController.select(query);
		ArrayList<Object> returnMsg = new ArrayList<>();
		ArrayList<TableProd> tProd = new ArrayList<>();
		try {
			returnMsg.add(Tasks.RequiredStock);
			returnMsg.add("Sending Required table to {ip}");
			while(rs.next()) { 
				tProd.add(new TableProd(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4)));	
			}
			returnMsg.add(tProd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnMsg;
	}
}

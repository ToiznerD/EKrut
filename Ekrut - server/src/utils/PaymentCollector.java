package utils;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Util.Msg;
import Util.Tasks;
import tasker.Tasker;

/**
 * PaymentCollector class extends abstract class <code>TimerTasl</code>
 * used to collect postponed payments made by customers of the system
 */
public class PaymentCollector extends TimerTask {
	/**
	 * implements abstract method of TimerTask
	 * this is the function that the thread of the timed task will run
	 * selects all the users who have delayed payments, and sets the delayed payment to 0
	 * this task will run every 1st of the month
	 */
	@Override
	public void run() {
		String query = "SELECT oid FROM orders WHERE delayed_payment = 1";
		Msg msg = new Msg(Tasks.Select, query);
		try {
			Tasker.runSelect(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(Integer i : msg.getArr(Integer.class)) {
			query = "UPDATE orders SET delayed_payment = 0 WHERE oid = " + i;
			msg = new Msg(Tasks.Update, query);
			Tasker.runUpdate(msg);
		}

	    // set up next scheduled task
        Timer timer = new Timer();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,1);
        c.set(Calendar.DAY_OF_MONTH,1);
        Date nextExecution = c.getTime();
        timer.schedule(new PaymentCollector(), nextExecution);
	}

}

package Utils;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Util.Msg;
import Util.Tasks;
import tasker.Tasker;

public class PaymentCollector extends TimerTask {
	
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

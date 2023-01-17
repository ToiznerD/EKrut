package controllers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import Entities.OrderDetails;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * AbstractOrderController is a class for the order process
 * its added event handler to the stage, the event restart the timer when mouse move.
 * if the user don't move the mouse for 1 minute (inactive), logout occurred.
 */
public class AbstractOrderController extends AbstractController {
	protected static OrderDetails order;
	private Timer timer = new Timer();
	private TimerTask task;
	private boolean run = false; //Try change to static!.
	private EventHandler<MouseEvent> mouseMovedEvent = new EventHandler<MouseEvent>() {
		/**
		 *restart the timer when mouse move.
		 */
		@Override
		public void handle(MouseEvent event) {
			if (task != null) {
				task.cancel();
			}
			setTimer();
			run = true;
		}
	};

	/**
	 * start new timer task schedule to 1 minutes ahead.
	 */
	private void setTimer() {
		task = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						logout();
					}
				});
			}
		};
		timer.schedule(task, TimeUnit.MINUTES.toMillis(1));

	}

	/**
	 * call when loading an fxml.
	 *	make call to setTimer function to start the inactive timer.
	 *	create static order to the order process.
	 *@see OrderDetails
	 */
	@Override
	public void setUp(Object... objects) {
		setTimer();
		if (order == null)
			order = new OrderDetails(myUser.getId()); // raz
	}

    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     * @throws IOException if there is an issue loading the FXML file
     */
	@Override
	public void back(MouseEvent event) {
		// TODO Auto-generated method stub
	}

	/**
	 * if fxml related to order process add event handler to stage.
	 */
	@Override
	public void start(String fxml, String title, Object... objects) throws IOException {
		if (fxml != "OrderMethodForm" && fxml != "OrderPaymentScreen" && fxml != "OrderScreen") {
			if (task != null) {
				task.cancel();
				task = null;
			}
			order = null;
			prStage.removeEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedEvent);
			run = false;
		} else {
			if (run == false)
				prStage.addEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedEvent);
		}
		super.start(fxml, title, objects);
	}
}

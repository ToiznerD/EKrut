package controllers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import Entities.OrderDetails;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class AbstractOrderController extends AbstractController {
	protected static OrderDetails order;
	private Timer timer = new Timer();
	private TimerTask task;
	private boolean run = false;
	private EventHandler<MouseEvent> mouseMovedEvent = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (task != null) {
				task.cancel();
			}
			setTimer();
			run = true;
		}
	};

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

	@Override
	public void setUp(Object... objects) {
		setTimer();
		if (order == null)
			order = new OrderDetails(); // raz
	}

	@Override
	public void back(MouseEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void start(String fxml, String title, Object... objects) throws IOException {
		if ((fxml != "OrderMethodForm" || fxml != "OrderPaymentScreen" || fxml != "OrderScreen")) {
			if (task != null) {
				task.cancel();
				task = null;
			}
			prStage.removeEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedEvent);
			run = false;
		} else {
			if (run == false)
				prStage.addEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedEvent);
		}
		super.start(fxml, title, objects);
	}
}

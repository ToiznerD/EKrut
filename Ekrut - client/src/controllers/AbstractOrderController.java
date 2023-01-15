package controllers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import Entities.OrderDetails;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AbstractOrderController extends AbstractController {
	protected static OrderDetails order;
	private Timer timer = new Timer();
	private TimerTask task;

	@FXML
	public void onMouseMove(MouseEvent e) {
		if (task != null)
			task.cancel();
		setTimer();
	}

	@FXML
	public void onMouseExit(MouseEvent e) {
		if (task != null)
			task.cancel();
	}

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
		if (task != null && (fxml != "OrderMethodForm" || fxml != "OrderPaymentScreen" || fxml != "OrderScreen.fxml"))
			task.cancel();
		super.start(fxml, title, objects);
	}
}

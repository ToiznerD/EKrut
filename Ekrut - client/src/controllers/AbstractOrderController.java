package controllers;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AbstractOrderController extends AbstractController {
	private Timer timer = new Timer();
	private TimerTask task;

	@FXML
	public void onMouseMove(MouseEvent e) {
		if (task != null)
			task.cancel();
		setTimer();
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
	}

	@Override
	public void back(MouseEvent event) {
		// TODO Auto-generated method stub

	}

}

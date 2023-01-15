package controllers;

import java.io.IOException;

import Entities.OrderProduct;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class OrderViewCell extends ListCell<OrderProduct> {

	private Parent root;
	private OrderViewController controller;

	public OrderViewCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OrderViewList.fxml"));
			root = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void updateItem(OrderProduct product, boolean empty) {
		super.updateItem(product, empty);

		if (empty || product == null) {
			setGraphic(null);
		} else {
			controller.setTemplate(product);
			setGraphic(root);
		}
	}
}

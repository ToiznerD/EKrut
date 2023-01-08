package controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import Entities.OrderProduct;

public class CartCell extends ListCell<OrderProduct> {

	private AnchorPane root;
	private CartCellController controller;

	public CartCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CartProduct.fxml"));
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

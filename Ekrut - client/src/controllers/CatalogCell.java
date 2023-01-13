package controllers;

import java.io.IOException;

import Entities.OrderProduct;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class CatalogCell extends ListCell<OrderProduct> {

	private AnchorPane root;
	private CatlogCellController controller;

	public CatalogCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CatlogTemplate.fxml"));
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
package controllers;

import java.io.IOException;

import Entities.OrderProduct;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

/**
 * OrderViewCell is a controller class for an order list.
 * It displays OrderProduct information.
 * It extends ListCell class and overrides methods of it.
 */
public class OrderViewCell extends ListCell<OrderProduct> {

	private Parent root;
	private OrderViewController controller;

	/**
	 * Constructor for loading FXML file "OrderViewList".
	 */
	public OrderViewCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OrderViewList.fxml"));
			root = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	* Updates the view of a single OrderProduct in the ListView.
	* @param product The OrderProduct object to be displayed in the cell.
	* @param empty boolean indicating whether the cell is empty or not.
	*/	
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

package controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import Entities.OrderProduct;

/**
 * CartCell is a controller class for an order cart view.
 * It displays OrderProduct information.
 * It extends ListCell class and overrides methods of it.
 */
public class CartCell extends ListCell<OrderProduct> {

	private AnchorPane root;
	private CartCellController controller;

	/**
	 * Constructor for loading FXML file "CartProduct".
   * @throws IOException if there is an issue loading the "CartProduct".
	 */
	public CartCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CartProduct.fxml"));
			root = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	* Updates the view of a single OrderProduct in the CartCell.
	* @param product The OrderProduct object to be displayed in the cell.
	* @param empty boolean indicating whether the cell is empty or not.
	* @see	OrderProduct
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

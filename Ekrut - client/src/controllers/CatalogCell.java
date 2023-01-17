package controllers;

import java.io.IOException;
import Entities.OrderProduct;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

/**
 * CatalogCell is a controller class for item in the catalog.
 * It displays OrderProduct information.
 * It extends ListCell class and overrides methods of it.
 */
public class CatalogCell extends ListCell<OrderProduct> {
	private AnchorPane root;
	private CatlogCellController controller;


	/**
	 * Constructor for loading FXML file "CatalogCell".
   * @throws IOException if there is an issue loading the "CatalogCell".
	 */
	public CatalogCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CatlogTemplate.fxml"));
			root = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	* Updates the view of a single OrderProduct in the CatalogCell.
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

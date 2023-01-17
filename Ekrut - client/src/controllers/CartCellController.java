package controllers;

import Entities.OrderProduct;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
* CartCellController class is the controller class for the CartCell FXML file.
*/
public class CartCellController {

	@FXML
	private Text nameLbl;

	@FXML
	private Text quantLbl;

	@FXML
	private ImageView removeBtn,addBtn;

	private OrderProduct product;

	/**
	 * fill ("construct") all the node in the cell
	 * @param product a product from the catalog.
	 * @see 	OrderProduct
	 */
	public void setTemplate(OrderProduct product) {
		this.product = product;
		nameLbl.setText(product.getName());
		quantLbl.setText(String.valueOf(product.getCartQuant()));
	}

	/**
	 * @param event when button "plus" clicked, increase by 1 the cart quantity of the product represented by the cell.
	 */
	@FXML
	public void addPushed(MouseEvent event) {
		product.addToCart();
		quantLbl.setText(String.valueOf(product.getCartQuant()));
	}
	/**
	 * @param event when button "minus" clicked, reduce by 1 to cart quantity of the product represented by the cell.
	 */
	@FXML
	public void removePushed(MouseEvent event) {
		product.removeFromCart();
		quantLbl.setText(String.valueOf(product.getCartQuant()));
	}
}

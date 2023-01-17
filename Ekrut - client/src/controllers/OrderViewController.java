package controllers;

import Entities.OrderProduct;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
* OrderViewController class is the controller class for the OrderViewList FXML file.
*/
public class OrderViewController {

	@FXML
	private Text nameText, quantText, priceText;

	private OrderProduct product;

	/**
	Sets the texts for the OrderViewCell.
	It sets the name, quantity and total price of a product in the cell.
	@param p OrderProduct object that contains the information of the product.
	*/
	public void setTemplate(OrderProduct p) {
		this.product = p;
		nameText.setText(p.getName());
		quantText.setText("Qty: " + String.valueOf(p.getCartQuant()));
		priceText.setText(String.valueOf(p.getPrice() * p.getCartQuant())+"₪");
	}

}

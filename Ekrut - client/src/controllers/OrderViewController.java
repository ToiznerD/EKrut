package controllers;

import Entities.OrderProduct;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class OrderViewController {

	@FXML
	private Text nameText, quantText, priceText;

	private OrderProduct product;

	public void setTemplate(OrderProduct p) {
		this.product = p;
		nameText.setText(p.getName());
		quantText.setText("Qty: " + String.valueOf(p.getCartQuant()));
		priceText.setText(String.valueOf(p.getPrice() * p.getCartQuant())+"₪");
	}

}

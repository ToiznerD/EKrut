package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import Entities.OrderProduct;

public class CartCellController {

	@FXML
	private Button addBtn;

	@FXML
	private Text nameLbl;

	@FXML
	private Text quantLbl;

	@FXML
	private Button removeBtn;

	private OrderProduct product;

	public void setTemplate(OrderProduct p) {
		this.product = p;
		nameLbl.setText(p.getName());
		quantLbl.setText(String.valueOf(p.getCartQuant()));
	}

	@FXML
	public void addPushed(ActionEvent event) {
		product.addToCart();
		quantLbl.setText(String.valueOf(product.getCartQuant()));
	}

	@FXML
	public void removePushed(ActionEvent event) {
		product.removeFromCart();
		quantLbl.setText(String.valueOf(product.getCartQuant()));
	}
}

package controllers;

import Entities.OrderProduct;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class CartCellController {

	@FXML
	private Text nameLbl;

	@FXML
	private Text quantLbl;

	@FXML
	private ImageView removeBtn,addBtn;

	private OrderProduct product;

	public void setTemplate(OrderProduct p) {
		this.product = p;
		nameLbl.setText(p.getName());
		quantLbl.setText(String.valueOf(p.getCartQuant()));
	}

	@FXML
	public void addPushed(MouseEvent event) {
		product.addToCart();
		quantLbl.setText(String.valueOf(product.getCartQuant()));
	}

	@FXML
	public void removePushed(MouseEvent event) {
		product.removeFromCart();
		quantLbl.setText(String.valueOf(product.getCartQuant()));
	}
}

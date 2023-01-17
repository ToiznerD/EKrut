package controllers;

import java.io.InputStream;
import java.net.URL;

import Entities.OrderProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CatlogCellController {

	private OrderProduct product;

	@FXML
	private AnchorPane root;

	@FXML
	private Button btnAddToCart;

	@FXML
	private ImageView img;

	@FXML
	private Text lblID;

	@FXML
	private Text lblName;

	@FXML
	private Text lblPrice;

	public void setTemplate(OrderProduct p) {
		product = p;
		lblID.setText(String.valueOf(p.getProductID()));
		lblName.setText(p.getName());
		lblPrice.setText(String.valueOf(p.getPrice()) + " ₪");
		img.setImage(buildImg(p.getName()));
	}

	private Image buildImg(String productName) {
		InputStream stream = null;
		stream = this.getClass().getResourceAsStream("/images/" + productName.toLowerCase() + ".png");
		if (stream == null)
			stream = this.getClass().getResourceAsStream("/images/defaultCatlogImg.png");
		return new Image(stream);
	}

	@FXML
	public void addClick(ActionEvent event) {
		product.addToCart();
	}

	public OrderProduct getProduct() {
		return product;
	}

}

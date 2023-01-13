package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

import Entities.OrderProduct;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CatlogCellController {

	private static String imgPath = Paths.get("src\\").toAbsolutePath().getParent().toString() + "\\images\\";
	private static String defaultImg = imgPath + "defaultCatlogImg.png";
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

	private ChangeListener<Number> cartQuantListener = (observable, oldValue, newValue) -> {
		if (newValue.intValue() == 0) {
			btnAddToCart.setDisable(false);
		} else if (newValue.intValue() == 1) {
			btnAddToCart.setDisable(true);
		}
	};

	public void setTemplate(OrderProduct p) {
		if (product == null)
			p.getCartQuantProperty().addListener(cartQuantListener);
		product = p;
		lblID.setText(String.valueOf(p.getProductID()));
		lblName.setText(p.getName());
		lblPrice.setText(String.valueOf(p.getPrice()) + " ₪");
		//img.setImage(buildImg(p.getName()));

	}

	private Image buildImg(String productName) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(imgPath + productName.toLowerCase() + ".png");
		} catch (FileNotFoundException e) {
			try {
				stream = new FileInputStream(defaultImg);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		return new Image(stream);
	}

	@FXML
	public void addClick(ActionEvent event) {
		product.addToCart();
	}

	public OrderProduct getProduct() {
		return product;
	}

	public ChangeListener<Number> getCartQuantListener() {
		return cartQuantListener;
	}
}
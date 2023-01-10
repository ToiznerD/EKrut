package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import Entities.OrderProduct;

public class CatlogCellController {

	private static String imgPath = Paths.get("\\images\\").toAbsolutePath().toString();
	private static String defaultImg = Paths.get("\\images\\defaultCatlogImg.png").toAbsolutePath().toString();
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
		this.product = p;
		lblID.setText(lblID.getText() + " " + String.valueOf(p.getProductID()));
		lblName.setText(lblName.getText() + " " + p.getName());
		lblPrice.setText(lblPrice.getText() + " " + String.valueOf(p.getPrice()) + " ₪");
		img.setImage(buildImg(p.getName()));
		product.getCartQuantProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observe, Number oldVal, Number newVal) {
				if (newVal.intValue() == 0)
					btnAddToCart.setDisable(false);
				if (newVal.intValue() == 1)
					btnAddToCart.setDisable(true);
			}
		});
	}

	private Image buildImg(String productName) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(imgPath + productName + ".png");
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
}

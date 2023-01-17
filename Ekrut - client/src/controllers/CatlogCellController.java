package controllers;

import java.io.InputStream;

import Entities.OrderProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
* CatalogCellController class is the controller class for the CatalogCell FXML file.
*/
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

	/**
	 * fill ("construct") all the node in the cell
	 * @param product a product from catalog
	 * @see 	OrderProduct
	 */
	public void setTemplate(OrderProduct product) {
		this.product = product;
		lblID.setText(String.valueOf(product.getProductID()));
		lblName.setText(product.getName());
		lblPrice.setText(String.valueOf(product.getPrice()) + " ₪");
		img.setImage(buildImg(product.getName()));
	}

	/**
	 * @param productName a name of a product
	 * @return	the image to show in the cell.
	 */
	private Image buildImg(String productName) {
		InputStream stream = null;
		stream = this.getClass().getResourceAsStream("/images/" + productName.toLowerCase() + ".png");
		if (stream == null)
			stream = this.getClass().getResourceAsStream("/images/defaultCatlogImg.png");
		return new Image(stream);
	}

	/**
	 * @param event when button "add to cart" clicked, increase by 1 the cart quantity of the product represented by the cell.
	 */
	@FXML
	public void addClick(ActionEvent event) {
		product.addToCart();
	}

	/**
	 * @return the product of the cell.
	 */
	public OrderProduct getProduct() {
		return product;
	}

}

package controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import Entities.OrderProduct;
import Util.Msg;
import Util.Tasks;
import client.Config;


/**
 * This class handles the items picking (Cart) process for an order.
 * it shows every item in the catalog (picrute,name,price)
 * it also have a cart view, the user can add item from the catalog to his cart.
 */
public class OrderScreenController extends AbstractOrderController {
	private ObservableList<OrderProduct> productOList = FXCollections.observableArrayList();
	private ObservableList<OrderProduct> cartOList = FXCollections.observableArrayList();
	private static final DecimalFormat decimal = new DecimalFormat("0.00");
	private static final DecimalFormat decimalToInt = new DecimalFormat("0");
	private int totalPrice = 0;
	private Double discount = 1.0, discount_first_order = 1.0;
	private boolean discountInstalled = false;


	@FXML
	private ListView<OrderProduct> catlogList, cartList;
	@FXML
	private ProgressBar pb;
	@FXML
	private ImageView backBtn;
	@FXML
	private Text priceText;
	@FXML
	private Text discountLbl;
	@FXML
	private Line line;
	@FXML
	private Text discountPriceText;
	@FXML
	private Button checkoutBtn;

	/**
	 * initialize all nodes inside the controller with the first initials value.
	 */
	@FXML
	public void initialize() {
		catlogList.setCellFactory(listView -> new CatalogCell());
		cartList.setCellFactory(listView -> new CartCell());
		cartList.setItems(cartOList);
		catlogList.setItems(productOList);
		if (Config.getConfig().equals("EK"))
			pb.setProgress(0.5);
		else
			pb.setProgress(0.66);
		line.setVisible(false);
	}

	
	/**
	 * add Listeners to every product in the catalog (screen), 
	 * listen to change in product quantity in the catalog.
	 */
	private void addListeners() {
		for (OrderProduct p : productOList)
			p.getCartQuantProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observe, Number oldCartQuant,
						Number newCartQuant) {
					if (newCartQuant.intValue() == 0 && cartOList.contains(p))
						cartOList.remove(p);
					if (newCartQuant.intValue() == 1 && !cartOList.contains(p))
						cartOList.add(p);

					cartList.refresh();
					totalPrice += (newCartQuant.intValue() - oldCartQuant.intValue()) * p.getPrice();
					priceText.setText(String.valueOf(totalPrice));
					if ((discount_first_order < 1.0 || discount < 1.0) && totalPrice > 0) {
						line.setVisible(true);
						discountLbl.setText("After discount:");
						if(discount == 1.0)
							discountPriceText.setText(String.valueOf(decimal.format((totalPrice * discount_first_order))));
						else
							discountPriceText.setText(String.valueOf(decimal.format((totalPrice * (1 - discount))*discount_first_order)));
					} else {
						line.setVisible(false);
						discountLbl.setText("");
						discountPriceText.setText("");
					}
				}
			});
	}

	/**
	 * SELECT every product with a given shop_id.
	 * @return List of order products
	 */
	private ArrayList<OrderProduct> getProductList() {
		msg = new Msg(Tasks.Select,
				"SELECT p.pid,p.pname,p.price,sp.quantity FROM store_product sp ,product p WHERE sp.pid = p.pid AND sp.sid = "
						+ order.getStore_ID());
		sendMsg(msg);
		return (ArrayList<OrderProduct>) msg.getArr(OrderProduct.class);

	}

	/**
	 * Raise a pop up dialog if discount occurred.
	 */
	private void installDiscount() {
		//Check if a customer is a subscriber with first order
		msg = new Msg(Tasks.Select,
				"SELECT first_order FROM customer WHERE id = " + myUser.getId() + " AND subscriber = 1 AND first_order = 1");
		sendMsg(msg);
		if(msg.getBool()) {
			discountInstalled = true;
			discount_first_order = 0.8;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("First Order");
			alert.setHeaderText("Congratulations !");
			alert.setContentText("This is your first order, enjoy 20% discount for every item in cart.");
			alert.showAndWait();
		}
		
		msg = new Msg(Tasks.Select,
				"SELECT s.saleName ,t.discount FROM sale_initiate s,sale_template t WHERE s.active = 1 AND s.templateId=t.templateId AND \""
						+ java.sql.Time.valueOf(LocalTime.now()) + "\" BETWEEN s.startHour AND s.endHour AND \""
						+ java.sql.Date.valueOf(LocalDate.now()) + "\" BETWEEN s.startDate AND s.endDate");
		sendMsg(msg);
		if (msg.getBool()) {
			discountInstalled = true;
			discount = msg.getObj(1);
			String saleName = msg.getObj(0);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("SALE");
			alert.setHeaderText(saleName + " Sale!");
			alert.setContentText("SALE for " + saleName + " is now active you can enjoy "
					+ decimalToInt.format((double)msg.getObj(1) * 100) + "% discount for every item in cart.");
			alert.showAndWait();
		}

	}

	/**
	 * this method call on loading fxml.
	 * initialize the entire controller,
	 * call super to start timer,
	 * raise discount popup if needed,
	 * set store_id property of order.
	 * and add all product to invite summary.
	 */
	@Override
	public void setUp(Object... objects) {
		super.setUp(objects);
		if (Config.getConfig().equals("EK"))
			order.setStore_ID(Config.getStore());
		if (!discountInstalled)
			installDiscount();
		productOList.clear();
		productOList.addAll(getProductList());
		addListeners();
	}

	/**
	 * Occur when checkout button clicked 
	 * set items,discount,total_price properties of order
	 * and switch to next screen.
	 * @param event action event.
	 * @throws IOException
	 */
	@FXML
	public void checkout(ActionEvent event) throws IOException {
		if (!cartOList.isEmpty()) {
			ArrayList<OrderProduct> list = new ArrayList<OrderProduct>();
			for (OrderProduct p : productOList)
				if (p.getCartQuant() > 0)
					list.add(p);
			order.setItems(list);
			order.setDiscount(discount);
			order.setTotal_price(totalPrice);
			
			if(discount_first_order == 0.8)
				order.setFirstOrderTrue();
			start("OrderPaymentScreen", "Payment");
		} else
			setUp(); //Restart order window.
	}

	/**
	 * Handles the mouse event of the back button.
	 * If the configuration is OL, it opens the "OrderMethodForm" window.
	 * If the configuration is EK, it returns to "CustomerPanel" window.
	 * @param event the mouse event that triggered this method
	 */
	@Override
	public void back(MouseEvent event) {
		if (Config.getConfig().equals("OL")) {
			try {
				start("OrderMethodForm", "Order Method Form");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				start("CustomerPanel", "Customer Dashboard", Config.getStore());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
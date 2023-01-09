package controllers;

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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import Entities.OrderProduct;
import Util.Msg;
import Util.Tasks;

public class OrderScreenController extends AbstractController {
	private ObservableList<OrderProduct> productOList = FXCollections.observableArrayList();
	private ObservableList<OrderProduct> cartOList = FXCollections.observableArrayList();
	private int sum = 0;
	@FXML
	private ListView<OrderProduct> catlogList, cartList;

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
	private int shopID;
	private static final DecimalFormat decimal = new DecimalFormat("0.00");
	private Double discount = 1.0;

	@FXML
	public void initialize() {
		catlogList.setCellFactory(listView -> new CatalogCell());
		cartList.setCellFactory(listView -> new CartCell());
		cartList.setItems(cartOList);
		catlogList.setItems(productOList);
	}

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
					sum += (newCartQuant.intValue() - oldCartQuant.intValue()) * p.getPrice();
					priceText.setText(String.valueOf(sum));
					if (discount < 1.0 && sum > 0) {
						line.setVisible(true);
						discountLbl.setText("After discount:");
						discountPriceText.setText(String.valueOf(decimal.format(sum * discount)));
					} else {
						line.setVisible(false);
						discountLbl.setText("");
						discountPriceText.setText("");
					}
				}
			});
	}

	private boolean checkOrder() {
		ArrayList<OrderProduct> arr = getProductList();
		for (OrderProduct p : arr) {
			OrderProduct old = productOList.get(productOList.indexOf(p));
			old.setQuant(p.getQuant());
		}
		for (OrderProduct o : productOList)
			if (o.getQuant() < o.getCartQuant())
				return false;
		return true;
	}

	private ArrayList<OrderProduct> getProductList() {
		msg = new Msg(Tasks.Select,
				"SELECT p.pid,p.pname,p.price,sp.quantity FROM store_product sp ,product p WHERE sp.pid = p.pid AND sp.sid = "
						+ shopID);
		sendMsg(msg);
		return (ArrayList<OrderProduct>) msg.getArr(OrderProduct.class);
	}

	private Double getDiscount() {
		msg = new Msg(Tasks.Select,
				"SELECT s.saleName ,t.discount FROM sale_initiate s,sale_template t WHERE s.active = 1 AND s.templateId=t.templateId AND \""
						+ java.sql.Time.valueOf(LocalTime.now()) + "\" BETWEEN s.startHour AND s.endHour AND \""
						+ java.sql.Date.valueOf(LocalDate.now()) + "\" BETWEEN s.startDate AND s.endDate");
		sendMsg(msg);
		return msg.getBool() == true ? msg.getObj(1) : 1.0;
	}

	@Override
	public void setUp(Object... objects) {
		this.shopID = (int) objects[0];
		discount = getDiscount(); //To add popup
		productOList.addAll(getProductList());
		addListeners();
	}

	@FXML
	public void checkout(ActionEvent event) {
		if (checkOrder())
			System.out.println("ok to send");
		///start(nextWindow,title,cartOList);
	}

	@Override
	public void back(MouseEvent event) {
		// TODO Auto-generated method stub
	}

}
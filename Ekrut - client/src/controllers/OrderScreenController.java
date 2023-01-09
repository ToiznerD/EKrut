package controllers;

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
import Entities.OrderProduct;
import Util.Msg;
import Util.Tasks;

public class OrderScreenController extends AbstractController {
	private ObservableList<OrderProduct> productOList = FXCollections.observableArrayList();
	private ObservableList<OrderProduct> cartOList = FXCollections.observableArrayList();
	@FXML
	private ListView<OrderProduct> catlogList, cartList;

	@FXML
	private ImageView backBtn;

	@FXML
	private Button checkoutBtn;
	private int shopID;

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
				public void changed(ObservableValue<? extends Number> observe, Number oldVal, Number newVal) {
					if (newVal.intValue() == 0 && cartOList.contains(p))
						cartOList.remove(p);
					if (newVal.intValue() == 1 && !cartOList.contains(p))
						cartOList.add(p);
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

	@Override
	public void setUp(Object... objects) {
		this.shopID = (int) objects[0];
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
package Entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * OrderProduct represent a product in an order, it contains productID, product name, price, quant
 * and cartQuantity
 * the difference between quant and cartQuantity, is that quant is the quantity available in the db, and cartQuantity is the quantity in the cart
 */
public class OrderProduct implements Serializable {
	private static final long serialVersionUID = 7126677968806442791L;
	private int productID;
	private String name;
	private int price, quant;
	private transient SimpleIntegerProperty cartQuant = new SimpleIntegerProperty(0);

	/**
	 * @param productID product's id
	 * @param name product's name
	 * @param price product's price
	 * @param quant product's quantity
	 */
	public OrderProduct(int productID, String name, int price, int quant) {
		this.productID = productID;
		this.name = name;
		this.price = price;
		this.quant = quant;
	}

	/**
	 * generate a hashcode
	 * @return hashcode
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cartQuant, name, price, productID, quant);
	}

	/**
	 * overrides object's equals.
	 * check if two orders are equal by all of the class's attributes
	 * @param obj object to compare with
	 * @return true if equals, else otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderProduct other = (OrderProduct) obj;
		return Objects.equals(cartQuant, other.cartQuant) && Objects.equals(name, other.name) && price == other.price
				&& productID == other.productID && quant == other.quant;
	}

	/**
	 * productID getter
	 * @return productID attribute value
	 */
	public int getProductID() {
		return productID;
	}

	/**
	 * name getter
	 * @return name attribute value
	 */
	public String getName() {
		return name;
	}

	/**
	 * price getter
	 * @return price attribute value
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * quant getter
	 * @return quant attribute value
	 */
	public int getQuant() {
		return quant;
	}

	/**
	 * cartQuant int value getter
	 * @return cartQuant int attribute value
	 */
	public int getCartQuant() {
		return cartQuant.get();
	}

	/**
	 * cartQuantProperty getter
	 * @return CartQuantProperty attribute value
	 */
	public SimpleIntegerProperty getCartQuantProperty() {
		return cartQuant;
	}

	/**
	 * sets the quant attribute
	 * @param quant qunatity to set
	 */
	public void setQuant(int quant) {
		this.quant = quant;
	}

	/**
	 * sets the cartQuant's attribute value
	 * @param quant qunatity to set
	 */
	public void setCartQuant(int quant) {
		this.cartQuant.set(quant);
	}

	/**
	 * add 1 to cartQuant int value only if cartQuant is smaller than quant
	 * @return true if was added successfully, else otherwise
	 */
	public boolean addToCart() {
		if (cartQuant.get() < quant) {
			cartQuant.set(cartQuant.get() + 1);
			return false;
		}
		return true;
	}

	/**
	 * decrease 1 from cartQuant int value only if cartQuant is greater than 0
	 * @return cartQuant int value
	 */
	public int removeFromCart() {
		if (cartQuant.get() > 0) {
			cartQuant.set(cartQuant.get() - 1);
			return cartQuant.get();
		}
		return 0;
	}

	/**
	 * sets the productID attribute
	 * @param productID qunatity to set
	 */
	public void setProductID(int productID) {
		this.productID = productID;
	}

	/**
	 * sets the name attribute
	 * @param name name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * sets the price attribute
	 * @param price price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * the class <code>OrderProduct</code>  implements serializble, this function is to write the instance as a binary stream
	 * @param out OutputStream to write to
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeInt(productID);
		out.writeUTF(name);
		out.writeInt(price);
		out.writeInt(quant);
		out.writeInt(cartQuant.get());
	}

	/**
	 * the class <code>OrderProduct</code> implements serializable, this function is to write the instance as a binary stream
	 * @param in InputStream to write to
	 * @throws IOException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		productID = in.readInt();
		name = in.readUTF();
		price = in.readInt();
		quant = in.readInt();
		cartQuant = new SimpleIntegerProperty(in.readInt());
	}

}

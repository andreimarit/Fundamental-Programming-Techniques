package businessLayer;

import java.io.Serializable;

public class BaseProduct extends MenuItem {
	

	private static final long serialVersionUID = 1L;
	private Integer price;
	
	public BaseProduct(String name, String description, int price) {
		super(name,description);
		this.price = new Integer(price);
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = new Integer(price);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int computePrice() {
		return price.intValue();
	}

}

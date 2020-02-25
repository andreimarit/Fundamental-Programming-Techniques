package businessLayer;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{
	
	private Integer orderId;
	private Date date;
	private Integer table;
	
	public Order(int orderId, int table) {
		this.orderId = new Integer(orderId);
		this.date = new Date();
		this.table = new Integer(table);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public Date getDate() {
		return date;
	}

	public Integer getTable() {
		return table;
	}
	
	

}

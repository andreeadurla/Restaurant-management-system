package Business;

public class Order {
	
	private int orderId;
	private String date;
	private int table;
	
	public Order(int orderId, String date, int table) {
		this.orderId = orderId;
		this.date = date;
		this.table = table;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public String getDate() {
		return date;
	}

	public int getTable() {
		return table;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + orderId;
		result = 31 * result + ((date == null) ? 0 : date.hashCode());
		result = 31 * result + table;
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if(o == null || !(o instanceof Order))
			return false;
		
		Order order = (Order) o;
		
		boolean orderIdEquals = (this.orderId == order.orderId);
		boolean dateEquals = ((this.date == null && order.date == null) ||
				(this.date != null && this.date.equals(order.date)));
		boolean tableEquals = (this.table == order.table);
		return orderIdEquals && dateEquals && tableEquals;
	}
	
	public String toString() {
		return "Id Order: " + orderId + "\nDate: " + date + "\nTable: " + table;
	}
}
package Business;

public class BaseProduct extends MenuItem{
	
	private static final long serialVersionUID = 1L;
	private double price;
	
	public BaseProduct(String name, double price) {
		super(name);
		this.price = price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public double computePrice() {
		return price;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if(o == null || !(o instanceof BaseProduct))
			return false;
		
		BaseProduct product = (BaseProduct) o;
		boolean nameEquals = (super.getName() == null && product.getName() == null) || 
				(super.getName() != null && super.getName().equalsIgnoreCase(product.getName()));
		
		return nameEquals;
	}
}

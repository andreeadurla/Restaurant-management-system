package Business;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<MenuItem> products = new ArrayList<MenuItem>();
	
	public CompositeProduct(String name) {
		super(name);
	}
	
	public ArrayList<MenuItem> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<MenuItem> products) {
		this.products = products;
	}

	public void addProduct(MenuItem item) {
		products.add(item);
	}
	
	public boolean containsProduct(MenuItem item) {
		for(MenuItem p: products) {
			if(p.equals(item) == true)
				return true;
			if(p instanceof CompositeProduct) {
				if(((CompositeProduct) p).containsProduct(item) == true)
					return true;
			}
		}
		return false;
	}
	
	@Override
	public double computePrice() {
		double totalPrice = 0;
		for(MenuItem p: products)
			totalPrice += p.computePrice();
		return totalPrice;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if(o == null || !(o instanceof CompositeProduct))
			return false;
		
		CompositeProduct product = (CompositeProduct) o;
		
		if(super.getName().equalsIgnoreCase(product.getName()) == false)
			return false;
		
		if(this.products.size() != product.products.size())
			return false;
		
		for(MenuItem item1: this.products) {
			if(product.products.contains(item1) == false)
				return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		String s = super.getName() + ": ";
		for(MenuItem p: products)
			s += p.toString();
		return s;
	}
}
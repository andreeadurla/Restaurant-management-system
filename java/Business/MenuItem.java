package Business;

import java.io.Serializable;

public abstract class MenuItem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	
	public MenuItem(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract double computePrice();
	
	@Override
	public String toString() {
		return name + ", ";
	}
	
	@Override
	public boolean equals(Object o) {
		return this.equals(o);
	}
}

package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import Data.WriteFile;

/**
 * Clasa Restaurant implementeaza operatiile care pot fi executate de un administrator sau un chelner 
 * 
 * @author Andreea Durla
 * @since 30 Aprilie 2020
 * @invariant isWellFormed()
 */

@SuppressWarnings("deprecation")
public class Restaurant extends Observable implements IRestaurantProcessing{

	private ArrayList<MenuItem> items = new ArrayList<MenuItem>();
	private Map<Order, ArrayList<MenuItem>> orders = new HashMap<Order, ArrayList<MenuItem>>();
	private boolean modifiedMenu = false;
	
	public void setItems(ArrayList<MenuItem> items) {
		assert isWellFormed();
		this.items = items;
		assert isWellFormed();
	}

	public boolean isModifiedMenu() {
		return modifiedMenu;
	}

	public void setModifiedMenu(boolean modifiedMenu) {
		this.modifiedMenu = modifiedMenu;
	}

	public void addMenuItem(MenuItem item) throws Exception{
		assert item != null;
		assert isWellFormed();

		int sizePre = items.size();
		if(items.contains(item) == false) {
			items.add(item);
			modifiedMenu = true;
			
			int sizePost = items.size();
			assert sizePost == sizePre + 1;
			assert items.get(sizePost - 1).equals(item);
			assert isWellFormed();
		}
		else {
			assert items.size() == sizePre;
			assert isWellFormed();
			throw new Exception("Acest produs deja exista in meniu!");
		}
	}
	
	public void deleteMenuItem(int nrItem) {
		int size = items.size();
		assert size > 0 && nrItem >= 0 && nrItem < size;
		assert isWellFormed();

		MenuItem item = items.get(nrItem);
		items.remove(nrItem);
		size--;
		
		int i = 0;
		while(i < size) {
			MenuItem itemDel = items.get(i);
			if(itemDel instanceof CompositeProduct)
				if(((CompositeProduct)itemDel).containsProduct(item) == true) {
					items.remove(i);
					size--;
					continue;
				}
			i++;
		}
		
		modifiedMenu = true;

		assert isWellFormed();
	}
	
	public void editMenuItem(MenuItem item, Object o) throws Exception{
		int sizePre = items.size();
		assert sizePre > 0;
		assert item != null && o != null;
		assert isWellFormed();
		
		assert ((item instanceof BaseProduct) && (o.getClass() == Double.class || o.getClass() == String.class)) ||
		 ((item instanceof CompositeProduct) && (o.getClass() == ArrayList.class || o.getClass() == String.class));
		
		if(o.getClass() == String.class) {
			if(productExists(item, o) == true)
				throw new Exception("In urma editarii ar rezulta un produs deja existent. Editarea nu are loc");
			item.setName((String)o);
		}
		else if(o.getClass() == Double.class) {
			((BaseProduct)item).setPrice((Double)o);
		}
		else if(o.getClass() == ArrayList.class) {
			if(productExists(item, o) == true)
				throw new Exception("In urma editarii ar rezulta un produs deja existent. Editarea nu are loc");
			ArrayList<MenuItem> menu = (ArrayList<MenuItem>) o;
			for(MenuItem m: menu) {
				if(m instanceof CompositeProduct)
					if(((CompositeProduct)m).containsProduct(item) == true)
						throw new Exception("In urma editarii ar rezulta un produs care se contine pe el insusi");
			}
			((CompositeProduct)item).setProducts((ArrayList<MenuItem>) o);
		}
		modifiedMenu = true;
		
		int sizePost = items.size();
		assert sizePost == sizePre;
		assert isWellFormed();
	}
	
	/**
	 * Verifica daca obiectul care s-ar obtine in urma editarii exista deja in lista de obiecte MenuItem
	 * 
	 * @param item obiectul care va fi editat
	 * @param o noua valoare a atributului. Daca o este de tipul String, atunci se va modifica numele obiectului.
	 * Daca o este de tipul Double, atunci trebuie ca obiectul sa fie BaseProduct si se va modifica pretul acestuia. 
	 * Daca o este de tipul ArrayList, atunci trebuie ca obiectul sa fie CompositeProduct si se va modifica lista de
	 * produse din care este alcatuit.
	 * @return true, daca obiectul editat exista in lista, si false in caz contrar
	 */
	private boolean productExists(MenuItem item, Object o) {
		if(o.getClass() == String.class) {
			if(item instanceof BaseProduct) {
				BaseProduct aux = new BaseProduct((String)o, ((BaseProduct)item).getPrice());
				return items.contains(aux);
			}
			
			if(item instanceof CompositeProduct) {
				CompositeProduct aux = new CompositeProduct((String)o);
				aux.setProducts(((CompositeProduct)item).getProducts());
				return items.contains(aux);
			}
		}
		
		if(o.getClass() == ArrayList.class) {
			CompositeProduct aux = new CompositeProduct(item.getName());
			aux.setProducts((ArrayList<MenuItem>) o);
			return items.contains(aux);
		}
		return false;
	}

	public MenuItem getMenuItem(int nrItem) {
		assert nrItem >= 0 && nrItem < items.size();
		return items.get(nrItem);
	}

	public ArrayList<MenuItem> viewMenuItems() {
		return items;
	}

	public void addOrder(Order order, ArrayList<MenuItem> items) {
		assert order != null && items != null && !items.isEmpty();
		assert isWellFormed();

		int sizePre = orders.size();
		orders.put(order, items);
		String s = "";
		for(MenuItem i: items)
			s += i.toString() + "\n";
		setChanged();
		notifyObservers(s);

		int sizePost = orders.size();
		assert sizePost == sizePre + 1;
		assert isWellFormed();
	}

	public Map<Order, ArrayList<MenuItem>> viewOrders() {
		return orders;
	}

	public double computeOrderPrice(Order order) {
		assert order != null;
		assert isWellFormed();

		ArrayList<MenuItem> orderItems = orders.get(order);
		double totalPrice = 0;
		for(MenuItem i: orderItems) 
			totalPrice += i.computePrice();

		assert isWellFormed();
		return totalPrice;
	}

	public void generateBill(Order order) {
		assert order != null;
		assert isWellFormed();

		ArrayList<MenuItem> orderItems = orders.get(order);

		ArrayList<String> billComponents = new ArrayList<String>();
		billComponents.add(order.toString() + "\n\n\nProducts:\n\n");
		for(MenuItem i: orderItems) 
			billComponents.add(i.toString() + "  --->  " + String.format("%.2f", i.computePrice()));

		double price = computeOrderPrice(order);
		billComponents.add("\n\n\nTotal Price  --->  " + String.format("%.2f", price));

		String nameFile = order.getOrderId() + "_" + order.getDate().substring(0, 5) + "_" + order.getDate().substring(14);
		nameFile = nameFile.replace('.', '_');
		nameFile = nameFile.replace(':', '_');
		WriteFile.createBill(nameFile, billComponents);

		assert isWellFormed();
	}

	protected boolean isWellFormed() { 
		if(items == null)
			return false;

		for(MenuItem i: items) {
			if(i instanceof CompositeProduct) {
				ArrayList<MenuItem> productComponents = ((CompositeProduct)i).getProducts();
				for(MenuItem prodC: productComponents)
					if(items.contains(prodC) == false)
						return false;
			}
			else
				if(i instanceof BaseProduct) {
					if(((BaseProduct)i).getPrice() <= 0)
						return false;
				}
		}
		return true;
	}
}

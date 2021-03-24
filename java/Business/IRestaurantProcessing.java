package Business;

import java.util.ArrayList;
import java.util.Map;

/**
* Interfata IRestaurantProcessing contine operatiile care pot fi executate de un administrator sau un chelner 
* 
* @author Andreea Durla
* @since 30 Aprilie 2020
*/

public interface IRestaurantProcessing {
	
	/**
	 * Indica daca s-au facut modificari in meniu
	 * @return true, daca meniul a fost modificat, si false in caz contrar
	 * 
	 * @post @nochange
	 */
	public boolean isModifiedMenu();
	
	/**
	 * Seteaza valoarea atributului care indica daca un meniu este modificat sau nu
	 * @param modifiedMenu valoarea cu care va fi setat atributul
	 * 
	 * @post @nochange
	 */
	public void setModifiedMenu(boolean modifiedMenu);
	
	/**
	 * Adauga un nou obiect in lista de MenuItem daca acel obiect nu exista deja
	 * @param item obiectul
	 * @throws Exception daca obiectul MenuItem deja exista in lista
	 * 
	 * @pre item != null
	 * @post viewMenuItems().size() == viewMenuItems().size()@pre + 1
	 * @post item@pre == viewMenuItems().get(viewMenuItems().size() - 1)
	 * @post @forall k : [0 .. viewMenuItems().size() - 2] @ (viewMenuItems().get(k)@pre == viewMenuItems().get(k))
	 */
	public void addMenuItem(MenuItem item) throws Exception;
	
	/**
	 * Sterge obiectul de pe pozitia nrItem din lista de MenuItem. 
	 * @param nrItem pozitia obiectului
	 * 
	 * @pre viewMenuItems().size() &gt; 0
	 * @pre nrItem &gt;= 0
	 * @pre nrItem &lt; viewMenuItems().size()
	 */
	public void deleteMenuItem(int nrItem);
	
	/**
	 * Schimba valoarea unui anumit atribut al obiectului 
	 * 
	 * @param item obiectul care va fi editat
	 * @param o noua valoare a atributului. Daca o este de tipul String, atunci se va modifica numele obiectului.
	 * Daca o este de tipul Double, atunci trebuie ca obiectul sa fie BaseProduct si se va modifica pretul acestuia. 
	 * Daca o este de tipul ArrayList, atunci trebuie ca obiectul sa fie CompositeProduct si se va modifica lista de
	 * produse din care este alcatuit.
	 * @throws Exception daca in urma editarii ar rezulta un obiect MenuItem deja existent in meniu
	 * 
	 * @pre viewMenuItems().size() &gt; 0
	 * @pre item != null 
	 * @pre o != null
	 * @pre (item is instanceof BaseProduct) &amp;&amp; (o.getClass() == Double.class || o.getClass() == String.class) ||
	 *      (item is instanceof CompositeProduct) &amp;&amp; (o.getClass() == ArrayList.class || o.getClass() == String.class)
	 * 
	 * @post viewMenuItems().size() == viewMenuItems().size()@pre
	 */
	public void editMenuItem(MenuItem item, Object o) throws Exception;
	
	/**
	 * Returneaza obiectul de pe pozitia nrItem din lista de MenuItem
	 * @param nrItem pozitia obiectului
	 * @return obiectul
	 * 
	 * @pre nrItem &gt;= 0
	 * @pre nrItem &lt; viewMenuItems().size()
	 * @post @nochange
	 */
	public MenuItem getMenuItem(int nrItem);
	
	/**
	 * Returneaza lista de MenuItem
	 * @return lista cu obiectele de tipul MenuItem
	 * 
	 * @pre true
	 * @post @nochange
	 */
	public ArrayList<MenuItem> viewMenuItems();
	
	/**
	 * Adauga o noua comanda in map-ul de comenzi
	 * @param order comanda
	 * @param items produsele din care este alcatuita comanda
	 * 
	 * @pre order != null
	 * @pre items != null
	 * @pre !items.isEmpty()
	 * @post viewOrders().size() == viewOrders().size()@pre + 1
	 */
	public void addOrder(Order order, ArrayList<MenuItem> items);
	
	/**
	 * Calculeaza pretul total al unei comenzi
	 * @param order comanda
	 * @return pretul comenzii
	 * 
	 * @pre order != null
	 * @post @nochange
	 */
	public double computeOrderPrice(Order order);
	
	/**
	 * Returneaza map-ul cu toate comenzile si produsele asociate fiecareia
	 * @return map-ul cu toate comenzile si produsele asociate fiecareia
	 * 
	 * @pre true
	 * @post @nochange
	 */
	public Map<Order, ArrayList<MenuItem>> viewOrders();
	
	/**
	 * Genereaza un fisier txt cu produsele corespunzatare unei comenzi
	 * @param order comanda
	 * 
	 * @pre order != null
	 * @post @nochange
	 */
	public void generateBill(Order order);
}

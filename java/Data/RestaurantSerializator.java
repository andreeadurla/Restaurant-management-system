package Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Business.MenuItem;

public class RestaurantSerializator {

	private static String filename;

	public static void setFilename(String filename) {
		RestaurantSerializator.filename = filename;
	}

	public static void serialization(ArrayList<MenuItem> items) {

		if(filename == null)
			return ;

		try {
			FileOutputStream file = new FileOutputStream(filename); 
			ObjectOutputStream out = new ObjectOutputStream(file); 

			for(MenuItem i: items) {
				out.writeObject(i); 
			}
			out.close(); 
			file.close(); 
		}
		catch(Exception e) { 
			System.out.println(e.getMessage()); 
		} 
	}

	public static ArrayList<MenuItem> deserialization() {
		ArrayList<MenuItem> items = new ArrayList<MenuItem>();
		
		if(filename == null)
			return items;

		try
		{    
			FileInputStream file = new FileInputStream(filename);
			if(file.available() > 2) {
				ObjectInputStream in = new ObjectInputStream(file); 

				while(file.available() > 0) {
					MenuItem item = (MenuItem)in.readObject(); 
					items.add(item);
				}
				in.close(); 
			}
			file.close(); 
		}  
		catch(Exception e) { 
			System.out.println(e.getMessage()); 
		} 

		return items;
	}
}

// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class DisplayMenu {

	// load current items on the menu
	static ArrayList<MenuItem> myMenuItems = loadMenuItems("products.txt");
	

	/**
	* Method to load new menu items from a file
	*
	* @param filename (String) contains menu items
	* @return ArrayList<MenuItem> of menu items
	*/
	public static ArrayList<MenuItem> loadMenuItems(String filename){
		// Create an ArrayList to store the menu items
		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
		try{
			// Create a File object
			File file = new File(filename);
			// Create a Scanner object to read the file
			Scanner scanner	= new Scanner(file);
			// Loop through the file
			while(scanner.hasNextLine()){
				// split line at commas into array
				String[] line = scanner.nextLine().split(",", 0);
				// pull out name, price, calories via indexing
				String name = line[0];
				double price = Double.parseDouble(line[1]);
				int	   calories = Integer.parseInt(line[2]);
				// 	Instantiate a MenuItem object using the line data
				MenuItem newMenuItem = new MenuItem(name, price, calories);
				// 	add it to the ArrayList
				menuItems.add(newMenuItem);
			}
			scanner.close();
		}
		// Catch the FileNotFoundException
		catch(FileNotFoundException e){
			// Print an error message
			System.out.println("File not found: " + filename);
		}
		return menuItems;
	}
}

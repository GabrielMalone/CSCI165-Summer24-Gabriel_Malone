// Gabriel Malone / CSCI65 / Week 6 / Summer 2024

// to do: 1 - include prices / cals in menu
// 		  2 - colors for int, string, double
//        4 - checks and loops so invalid inputs don't crash program
//        5 - option to remove items from shopping cart


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Driver{
	
	// hashamp for easy ordering
	static Map<Integer, MenuItem> orderMap = new HashMap<Integer, MenuItem>(10);
	// load current items on the menu
	static ArrayList<MenuItem> myMenuItems = loadMenuItems("products.txt");
	// scanner for user input
	static Scanner order = new Scanner(System.in);
	// create customer
	

	public static void main(String[] args) {
		// clear screen and show menu
		clearSequence();
		Customer customer = createCustomer();
		// take order
		ArrayList<MenuItem> shoppingCart = takeOrder();
		// today's date
		Date today = dateInitializer();
		// print receipt // save receipt info
		printOrder(shoppingCart, customer, today);
	}
	
	/**
	 * Method to take the customer's order
	 * 
	 * @return (ArrayList<String>) shopping cart items
	 */
	public static ArrayList<MenuItem> takeOrder(){
		// output var
		String itemRequest = " Add item number to order. 'D' for done: ";
		// order total 
		double orderTotal = 0;
		// items total
		int itemsTotal = 0;
		// create shopping cart
		ArrayList<MenuItem> shoppingCart = new ArrayList<>();
		// ask for order
		System.out.printf("%s", itemRequest);
		String itemNumber = order.next().toUpperCase();
		// clear screen
		clearSequence();
		// 'D' to complete order
		try {
			while ( ! itemNumber.equals("D")) {
				// get integer from input
				int number = Integer.parseInt(itemNumber);
				// get order from hashmap (number key, object value)
				MenuItem itemForOrder = orderMap.get(number);
				// for output feedback on order
				String orderName = itemForOrder.getName();
				double orderPrice = itemForOrder.getPrice();
				orderTotal += orderPrice;
				itemsTotal += 1;
				// add item to shopping cart
				shoppingCart.add(itemForOrder);
				// order feedback
				System.out.printf(" #%d - %s, $%.2f", 
								number, orderName, orderPrice );
				System.out.printf("%n%n Total Items: %d | Total Price: $%.2f%n%n%n", 
										itemsTotal, 
										orderTotal); 
										
				// get next input
				System.out.printf("%s", itemRequest);
				itemNumber = order.next().toUpperCase();
				//clear screen // keep menu and last order on screen
				clearSequence();	
			}
		}
		catch(NumberFormatException e) {
			System.out.println("whoops");
		}
		order.close();
		return shoppingCart;
	}

	/**
	 * Method to create a new customer 
	 * 
	 * @return (Object) new customer instance
	 */
	public static Customer createCustomer(){
		System.out.printf(" %s", "Enter your name: ");
		// NextLine in case they enter just first name or first and last
		String name = order.nextLine();
		clearSequence();
		System.out.printf(" %s", "Enter a valid email: ");
		String email = order.nextLine();
		clearSequence();
		System.out.printf(" %s", "Enter 10-digit phone #: ");
		String phone = order.nextLine();
		Customer newCustomer = new Customer(name, email, phone);
		// clear these inputs from screen
		clearSequence();
		return   newCustomer;		
	}

	/**
	 * Method to load new menu items from a file
	 * 
	 * @param filename (String) contains menu items
	 * @return ArrayList<MenuItem> of menu items
	 */
	public static ArrayList<MenuItem> loadMenuItems(String filename){
		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();	// Create an ArrayList to store the menu items
		try{
			File file		= new File(filename);	// Create a File object
			Scanner scanner	= new Scanner(file);	// Create a Scanner object to read the file
			while(scanner.hasNextLine()){			// Loop through the file
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
			scanner.close();// Close the Scanner object (lots of folks NOT DOING THIS)
		}
		catch(FileNotFoundException e){	// Catch the FileNotFoundException
			System.out.println("File not found: " + filename);	// Print an error message
		}
		return menuItems;	// Return the ArrayList
	}
	/**
	 * Method to print welcome message
	 * 
	 */
	public static void printLogo(){
		String welcomeMsg = "WELCOME TO HOORDUB";
		System.out.printf("%n%55s%n",welcomeMsg);
	}

	/**
	 * Displays items on the menu
	 * 
	 * @param menuItems loaded from file
	 */
	public static void printMenu(ArrayList<MenuItem> myMenuItems){
		String lineBreak = "-";
		String leftRowSeperator = "| ";
		String rightRowSeperator = " |";
		int index = 1;
		int foodItem = 0;
		for (int rows = 0; rows < 3; rows ++){
			System.out.println();
			System.out.println(lineBreak.repeat(95));	
			for (int columns = 0; columns < 3; columns ++) {
					MenuItem item = myMenuItems.get(foodItem);
					System.out.printf("%s%d%s%-25s", 
										leftRowSeperator,
										index,
										"   ",
										item.getName()
										);
					orderMap.put(index, item);
					index += 1;
					foodItem += 1;
			}
			System.out.print(rightRowSeperator);
		}
		System.out.println();
		System.out.println(lineBreak.repeat(95));	
		System.out.printf("%33s%d%s%-24s%s%n%63s%n",
						leftRowSeperator,
						index,
						"  ",
						myMenuItems.get(9).getName(),
						rightRowSeperator,
						lineBreak.repeat(32));
		orderMap.put(index, myMenuItems.get(9));
		System.out.println();
	}



	/**
	 * Method to save order to file and print receipt
	 * 
	 * @param order Array of MenuItem objects
	 * @param customer the customer object
	 * @param today	the date object
	 */
	public static void printOrder(ArrayList<MenuItem> shoppingCart, Customer customer, Date today){
		// clear menu away
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		// for total calc
		double tax = 0.0625;
		// formatting var
		String space = " ";
		// get strings of date and time for printing
		String todaysDateComplete = dateString(today);
		String timeNow = getTime();
		// for printing money values in printf with space alignment
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		//Write to file and print       
        try{	
			File output_file = new File("output.txt");
            PrintWriter writer = new PrintWriter(output_file);
			// goodbye message
			System.out.println("Enjoy your food!");
			System.out.println();
            // print receipt and save data at each step
			System.out.printf("Customer: %s%n", customer.getName());
			writer.printf("Customer: %s%n", customer.getName());
			System.out.println();
			writer.println();
			System.out.printf("Invoice number: %s%n", getInvoiceNumber(customer, today));
			writer.printf("Invoice number: %s%n", getInvoiceNumber(customer, today));
			System.out.printf("Date: %s%n", todaysDateComplete);
			writer.printf("Date: %s%n", todaysDateComplete);
			System.out.printf("Time: %s%n", timeNow);
			writer.printf("Time: %s%n", timeNow);
			System.out.printf("%-30s%-8s%-7s%s%n","Item", "Quant", "Price", "Total");
            writer.printf("%-30s%-8s%-7s%s%n","Item", "Quant", "Price", "Total");
			System.out.println("=".repeat(52));
			writer.println("=".repeat(52));
			// sort objects in array by their name attribute
			
			// iterate through sorted array and print / save
			double subtotal = 0;
			int caloriesTotal = 0;
			int index = 0;
			// iterate through shopping cart
			while (shoppingCart.size() > 0){
				// get first item from cart
				MenuItem menuitem = shoppingCart.get(index);
				int quant = 0;
				index += 1;
				// if cart has more than one of this item, calc how many
				while (shoppingCart.contains(menuitem)){
					quant += 1;
					subtotal += menuitem.getPrice();
					caloriesTotal += menuitem.getCalories();
					// remove item when done with it
					shoppingCart.remove(menuitem);	
				}
				// total cost for each item bought
				double total = quant * menuitem.getPrice();
				// print item name, price, and total cost of each item
				System.out.printf("%-32s%-6d%-7s%s%n",
								menuitem.getName(),
								quant,
								nf.format(menuitem.getPrice()),
								nf.format(total)
								);
				writer.printf("%-32s%-6d%-7s%s%n",
								menuitem.getName(),
								quant,
								nf.format(menuitem.getPrice()),
								nf.format(total)
								);
								index = 0;
			}
			// print other total information
			double taxTotal = subtotal * tax;
			double totalWithTax = taxTotal + subtotal;
			System.out.println();
			writer.println();
			System.out.printf("Calories: %d%n", caloriesTotal);
			writer.printf("Calories: %d%n", caloriesTotal);
			System.out.println("=".repeat(52));
			writer.println("=".repeat(52));
			System.out.println();
			writer.println();			
			System.out.printf("Subtotal%s%s%n",space.repeat(15), nf.format(subtotal));
			writer.printf("Subtotal%s%s%n",space.repeat(15), nf.format(subtotal));
			System.out.println("6.25% sales tax"+space.repeat(8) + nf.format(taxTotal));
			writer.println("6.25% sales tax"+space.repeat(8) + nf.format(taxTotal));
			System.out.println("Order Total"+space.repeat(12) + nf.format(totalWithTax));
			writer.println("Order Total"+space.repeat(12) + nf.format(totalWithTax));
			writer.close();
        }catch(IOException ioe){
            System.out.print("Could not write to file");
            System.exit(0);
        }
    }

	/**
	 * Creates a (mostly) unique invoice number from the Customer data and the Date
	 * 
	 * @param customer	The customer object
	 * @param today		The date object
	 * @return			the invoice number
	 */
	public static String getInvoiceNumber(Customer customer, Date today){
		/*
			first two initials of the customer’s first name, converted to uppercase
			first two initials of the customer’s last name, converted to uppercase
			Unicode value of the first character of the first name
			Unicode value of the first character of the last name
			Add them together and multiply by the length of the full name
			Concatenate this ID to the initials described above
			Concatenate the day, month, hour and minute with no punctuation
		*/
		String invoiceNumber = "";										// Create a variable to store the invoice number
		String[] name = customer.getName().split(" ");					// Get the first name
		String firstInitials= name[0].substring(0, 2).toUpperCase();	// Get the first two initials of the first name
		String lastInitials = name[1].substring(0, 2).toUpperCase();	// Get the first two initials of the last name
		int firstUnicode 	= (int)name[0].charAt(0);					// Get the Unicode value of the first character of the first name
		int lastUnicode 	= (int)name[1].charAt(0);					// Get the Unicode value of the first character of the last name
		int id = (firstUnicode + lastUnicode) * customer.getName().length();// Calculate the ID
		invoiceNumber = firstInitials + lastInitials + id;				// Concatenate the initials and ID
		invoiceNumber += today.getDay() + today.getMonth();				// Concatenate the date (add the time in if you want)
		return invoiceNumber;											// Return the invoice number
	}

	/**
	 * Method to create today's date
	 * 
	 * @return (Date) object of tooday's date
	 */
	public static Date dateInitializer(){
		Date today = new Date();
        Calendar timeNow = Calendar.getInstance();
        int dayOfWeek = timeNow.get(Calendar.DAY_OF_WEEK);
        int month = timeNow.get(Calendar.MONTH);
        int year = timeNow.get(Calendar.YEAR);
        today.setDay(dayOfWeek - 1);
        today.setMonth(month - 1);
        today.setYear(year);
		return today;
	}

	/**
	 * Method to get string of today's date
	 * 
	 * @param today object of today's date
	 * @return string of today's date
	 */
	public static String dateString(Date today){
		String dayToday   = Integer.toString(today.getDay());
		String monthToday = Integer.toString(today.getMonth());
		String yearToday  = Integer.toString(today.getYear());
		String todaysDateComplete = dayToday + "/" + monthToday + "/" + yearToday;
		return todaysDateComplete;
	}

	/**
	 * Method to get the current time
	 * 
	 * @return (String) of the current time
	 */
	public static String getTime(){
		Calendar rightNow = Calendar.getInstance();
        String currentTime = "Time: " 
							+ rightNow.get(Calendar.HOUR_OF_DAY) 
						    + ":" + rightNow.get(Calendar.MINUTE) 
							+ ":" + rightNow.get(Calendar.SECOND);
		return currentTime;
	}

	private static void clearSequence(){
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		printLogo();
		printMenu(myMenuItems); 
	}
}
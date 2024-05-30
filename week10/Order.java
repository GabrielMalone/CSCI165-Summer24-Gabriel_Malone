// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Order {

	/**
	* Creates a (mostly) unique invoice number from the Customer data and the Date
	* 
	* @param customer	The customer object
	* @param today		The date object
	* @return			the invoice number
	*/
	public static String getInvoiceNumber(Customer customer, Date today){
		// Create a variable to store the invoice number
		String invoiceNumber = "";
		// Get the first name										
		String[] name = customer.getName().split(" ");		
		// Get the first two initials of the first name			
		String firstInitials = name[0].substring(0, 2).toUpperCase();
		// Get the first two initials of the last name	
		String lastInitials = name[1].substring(0, 2).toUpperCase();	
		int firstUnicode = (int)name[0].charAt(0);
		// Get the Unicode value of the first character of the first name					
		int lastUnicode = (int)name[1].charAt(0);	
		// Get the Unicode value of the first character of the last name
		// Calculate the ID				
		int id = (firstUnicode + lastUnicode) * customer.getName().length();
		// Concatenate the initials and ID
		invoiceNumber = firstInitials + lastInitials + id;	
		// Concatenate the date (add the time in if you want)			
		invoiceNumber += today.getDay() + today.getMonth();		
		return invoiceNumber;											
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
		String todaysDateComplete = Date.dateString(today);
		String timeNow = Date.getTime();
		// for printing money values in printf with space alignment
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		String name = Driver.customer.getName();
		if (name.equals("unknown customer"))  name =  "Name : N/A";
		String phone = customer.getPhone();
		if (phone.equals("unknown customer")) phone = "Phone : N/A";
		String email = customer.getEmail();
		if (email.equals("unknown customer")) email = "Email : N/A";

		
		//Write to file and print       
		try{
			
			// open printwriter in append mode to save all receipts to file, not just one.
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File("output.txt"), true)); 	
			// goodbye message
			System.out.println("ENJOY!");
			System.out.println();
			// print receipt and save data at each step
			System.out.printf("%s / %s / %s", name, phone, email);
			// space between receipts
			writer.println();
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
			
			// alphabetize shopping cart
			OrderItem.shoppingCartAlphabetize(shoppingCart);
			
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
			
			// clear the scanner
			OrderItem.order.nextLine();
			
			// space between receipts in save file
			writer.println();
			writer.println();
			
			// wait for user input
			System.out.println();
			System.out.print(TerminalDisplay.PRICE_COLORS + "PRESS ENTER " + TerminalDisplay.ANSI_RESET);
			String placeHolder = OrderItem.order.nextLine();
			
		}catch(IOException ioe){
			System.out.print("Could not write to file");
			System.exit(0);
		}
	}
}

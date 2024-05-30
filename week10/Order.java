// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Order {

	private String space = TerminalDisplay.space;
	private double subtotal;
	private int caloriesTotal;
	private double total;
	private double tax = 0.0625;
	private double salesTax;
	private double totalWithTax;
	private ArrayList<MenuItem> shoppingCart = Driver.orderItem.shoppingCart;
	private ArrayList<MenuItem> cartCopy = new ArrayList<>();

	/**
	* Creates a (mostly) unique invoice number from the Customer data and the Date
	* 
	* @param customer	The customer object
	* @param today		The date object
	* @return			the invoice number
	*/
	private String getInvoiceNumber(){
		// Create a variable to store the invoice number
		String invoiceNumber = "";
		// Get the first name										
		String[] name = Driver.customer.getName().split(" ");		
		// Get the first two initials of the first name			
		String firstInitials = name[0].substring(0, 2).toUpperCase();
		// Get the first two initials of the last name	
		String lastInitials = name[1].substring(0, 2).toUpperCase();	
		int firstUnicode = (int)name[0].charAt(0);
		// Get the Unicode value of the first character of the first name					
		int lastUnicode = (int)name[1].charAt(0);	
		// Get the Unicode value of the first character of the last name
		// Calculate the ID				
		int id = (firstUnicode + lastUnicode) * Driver.customer.getName().length();
		// Concatenate the initials and ID
		invoiceNumber = firstInitials + lastInitials + id;	
		// Concatenate the date (add the time in if you want)			
		invoiceNumber += Driver.today.getDay() + Driver.today.getMonth();		
		return invoiceNumber;											
	}

	/**
    * Method to alphabetize customer's shopping cart
    * 
    * @param shoppingCart list of MenuItems
    * @return ArrayList<MenuItem>
    */
    void shoppingCartAlphabetize(ArrayList<MenuItem> shoppingCart){
        if (shoppingCart.size() > 1){
            // stating letter A
            char letter = 65;
            // iterate through the menut items
            while (letter <= 90){
                for (int index = 0 ; index < shoppingCart.size() ; index ++){
                    MenuItem item = shoppingCart.get(index);
                    // put each item name into a char array
                    char [] itemname = item.getName().toCharArray();
                    // check first letter of the char in the name
                    // if A, go to front of list, if B, go next in list, etc. 
                    if (itemname[0] == letter) {
                        // take the item from it's current position
                        shoppingCart.remove(item);
                        // put it in the back of the list. A will go to the back, then B, etc..
                        shoppingCart.add(shoppingCart.size() - 1, item);
                    }			
                } // for loop end
                // move letter to next letter
                letter += 1;
            } // while loop end
            // now sort within A's, B's, etc..
            int index = 0;
            while (index < shoppingCart.size() - 1){
                MenuItem item1 = shoppingCart.get(index);
                MenuItem item2 = shoppingCart.get(index + 1);
                // if item1 comes after item2 alphabetically, swap
                if (item1.compareName(item2) == 1){
                    shoppingCart.remove(item1);
                    shoppingCart.add(index + 1, item1);
                }
                index += 1;
            }
        }	
    }

	/**
	* Method to save order to file and print receipt
	* 
	* @param order Array of MenuItem objects
	* @param customer the customer object
	* @param today	the date object
	*/
	public void printOrder(){
		copyCart();
		// get strings of date and time for printing
		String todaysDateComplete = Date.dateString(Driver.today);
		String timeNow = Date.getTime();
		String name = Driver.customer.getName();
		//Write to file and print       
		try{
			// open printwriter in append mode to save all receipts to file, not just one.
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File("output.txt"), true)); 	
			// space between receipts
			writer.println();
			writer.printf("Customer: %s%n", name);
			writer.println();
			writer.printf("Invoice number: %s%n", getInvoiceNumber());
			writer.printf("Date: %s%n", todaysDateComplete);
			writer.printf("Time: %s%n", timeNow);
			writer.printf("%-30s%-8s%-7s%s%n","Item", "Quant", "Price", "Total");
			writer.println("=".repeat(52));
			// sort objects in array by their name attribute
			// iterate through sorted array and print / save
			// alphabetize shopping cart
			shoppingCartAlphabetize(this.shoppingCart);
			// iterate through shopping cart
			while (this.shoppingCart.size() > 0){
				// get first item from cart
				int index = 0;
				MenuItem menuitem = this.shoppingCart.get(index);
				int quant = Driver.orderItem.cartMap.get(menuitem);
				index += 1;
				this.subtotal += menuitem.getPrice() * quant;
				this.caloriesTotal += menuitem.getCalories();
				// remove item when done with it
				this.shoppingCart.remove(menuitem);	
				// total cost for each item bought
				this.total = quant * menuitem.getPrice();
				writer.printf(
					"%-32s%-6d%-7s%s%n",
					menuitem.getName(),
					quant,
					TerminalDisplay.nf.format(menuitem.getPrice()),
					TerminalDisplay.nf.format(this.total)
				);
				index = 0;
			}
			// print other total information
			salesTax();
			totalWithTax();
			writer.println();
			writer.printf("Calories: %d%n", this.caloriesTotal);
			writer.println("=".repeat(52));
			writer.println();			
			writer.printf("Subtotal%s%s%n",space.repeat(15), TerminalDisplay.nf.format(this.subtotal));
			writer.println("6.25% sales tax"+space.repeat(8) + TerminalDisplay.nf.format(this.salesTax));
			writer.println("Order Total"+space.repeat(12) + TerminalDisplay.nf.format(this.totalWithTax));
			writer.close();
			// clear the scanner
			Driver.orderItem.order.nextLine();
			// space between receipts in save file
			writer.println();
			writer.println();
			
		}catch(IOException ioe){
			System.out.print("Could not write to file");
			System.exit(0);
		}
		}
		
	public void displayReceipt (){
		String todaysDateComplete = Date.dateString(Driver.today);
		String timeNow = Date.getTime();
		String name = Driver.customer.getName();
		String phone = Driver.customer.getPhone();
		String email = Driver.customer.getEmail();
		// reset totals
		this.total = 0;
		this.subtotal = 0;
		this.caloriesTotal = 0;
		// clear menu
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		// goodbye message
		System.out.println("ENJOY!");
		System.out.println();
		// print receipt and save data at each step
		System.out.printf("%s / %s / %s", name, phone, email);
		System.out.println();
		System.out.printf("Invoice number: %s%n", getInvoiceNumber());
		System.out.printf("Date: %s%n", todaysDateComplete);
		System.out.printf("Time: %s%n", timeNow);
		System.out.printf("%-30s%-8s%-7s%s%n","Item", "Quant", "Price", "Total");
		System.out.println("=".repeat(52));
		shoppingCartAlphabetize(this.cartCopy);
		// print item name, price, and total cost of each item
		while (this.cartCopy.size() > 0){
			// get first item from cart
			int index = 0;
			MenuItem menuitem = this.cartCopy.get(index);
			int quant = Driver.orderItem.cartMap.get(menuitem);
			index += 1;
			this.subtotal += menuitem.getPrice() * quant;
			this.caloriesTotal += menuitem.getCalories();
			// remove item when done with it
			this.cartCopy.remove(menuitem);	
			// total cost for each item bought
			this.total = quant * menuitem.getPrice();
			System.out.printf(
				"%-32s%-6d%-7s%s%n",
				menuitem.getName(),
				quant,
				TerminalDisplay.nf.format(menuitem.getPrice()),
				TerminalDisplay.nf.format(this.total)
			);
			index = 0;
		}
		System.out.println();
		System.out.printf("Calories: %d%n", this.caloriesTotal);
		System.out.println("=".repeat(52));
		System.out.println();
		System.out.printf("Subtotal%s%s%n",space.repeat(15), TerminalDisplay.nf.format(this.subtotal));
		System.out.println("6.25% sales tax"+space.repeat(8) + TerminalDisplay.nf.format(this.salesTax));
		System.out.println("Order Total"+space.repeat(12) + TerminalDisplay.nf.format(this.totalWithTax));
		System.out.println();
		System.out.print(TerminalDisplay.PRICE_COLORS + "PRESS ENTER " + TerminalDisplay.ANSI_RESET);
		Driver.orderItem.order.nextLine();
		// wait for user input
		System.out.println();
		System.out.print(TerminalDisplay.PRICE_COLORS + "PRESS ENTER " + TerminalDisplay.ANSI_RESET);
	}

	private double salesTax(){
		this.salesTax = this.subtotal * this.tax;
		return this.salesTax;
	}

	private double totalWithTax(){
		this.totalWithTax = this.salesTax + this.subtotal;
		return this.totalWithTax;
	}

	private void copyCart(){
		for (MenuItem item : this.shoppingCart){
			this.cartCopy.add(item);
		}
	}
}

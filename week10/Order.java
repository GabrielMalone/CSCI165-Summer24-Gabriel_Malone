// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Order {
	
	// create shopping cart Array
	ArrayList<MenuItem> shoppingCart = new ArrayList<>();
	// array to keep track of names of items in cart
	ArrayList<String> menuNames = new ArrayList<>();
	// hashamp for easy order
    Map<Integer, MenuItem> orderMap = new HashMap<Integer, MenuItem>(10);
    // hasmap for live updating cart info
    Map<MenuItem, Integer> cartMap = new HashMap<MenuItem, Integer>(10);
    // map for removing items
    Map<Integer, MenuItem> removeMap = new HashMap<Integer, MenuItem>(10);
	
	Scanner scanner = new Scanner(System.in);
	private OrderItem orderItem;
	private String space 						= TerminalDisplay.space;
	private double subtotal;
	private int caloriesTotal;
	private double total;
	private double TAX 							= 0.0625;
	private double salesTax;
	private double totalWithTax;
	private ArrayList<MenuItem> cartCopy	 	= new ArrayList<>();
	private boolean processingReceipt 			= true;
	private String name;						
	private String phone;						
	private String email;				
	private Date rightNow 						= Date.dateInitializer();
	private String todaysDateComplete			= rightNow.dateString(rightNow);
	private String timeNow 						= rightNow.getTime();	
	double orderTotal 							= 0;
	
	/**
     * No argument constructor leaves all fields default
     */
	public Order () {}

	 /**
	* Method to take the customer's order
	* @return (ArrayList<String>) shopping cart items
	*/
	public void takeOrder(Customer customer){
		this.orderItem = new OrderItem();
		// ask for order
		TerminalDisplay.headerOutput();
		TerminalDisplay.horizontalLine();
		TerminalDisplay.orderFeedback();
		TerminalDisplay.horizontalLine();
		TerminalDisplay.subTotalOutPut(orderTotal, customer);
		TerminalDisplay.horizontalLine();
		TerminalDisplay.addRequest();
		String itemNumber = scanner.next().toUpperCase();
		// valid input check
		while(! this.orderItem.validInput(itemNumber)){
			formatting();
			TerminalDisplay.orderFeedback();
			TerminalDisplay.horizontalLine();
			TerminalDisplay.subTotalOutPut(orderTotal, customer);
			TerminalDisplay.horizontalLine();
			TerminalDisplay.addRequest();
			itemNumber = scanner.next().toUpperCase();
		}
		// clear screen
		TerminalDisplay.clearSequence();
		// whilte input valid logic
		while(this.orderItem.validInput(itemNumber) && ! itemNumber.equals("D")){
			// if remove requested sequence
			while (itemNumber.equals("R") && this.shoppingCart.size() > 0){
				formatting();
				TerminalDisplay.orderFeedback();
				TerminalDisplay.horizontalLine();
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				double priceReduction = this.orderItem.removeRequest(orderTotal, customer);
				formatting();
				TerminalDisplay.orderFeedback();
				TerminalDisplay.horizontalLine();
				orderTotal -= priceReduction;
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				TerminalDisplay.addRequest();
				itemNumber = scanner.next().toUpperCase();
				// valid input check
				while(! this.orderItem.validInput(itemNumber)){
					formatting();
					TerminalDisplay.orderFeedback();
					TerminalDisplay.horizontalLine();
					TerminalDisplay.subTotalOutPut(orderTotal, customer);
					TerminalDisplay.horizontalLine();
					TerminalDisplay.addRequest();
					itemNumber = scanner.next().toUpperCase();
				}	
			}
			// remove request logic if nothing to remove
			while (itemNumber.equals("R") && this.shoppingCart.size() == 0){
				formatting();
				TerminalDisplay.orderFeedback();
				TerminalDisplay.horizontalLine();
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				TerminalDisplay.addRequest();
				itemNumber = scanner.next().toUpperCase();
				// valid input check
				while(! this.orderItem.validInput(itemNumber)){
					formatting();
					TerminalDisplay.orderFeedback();
					TerminalDisplay.horizontalLine();
					TerminalDisplay.subTotalOutPut(orderTotal, customer);
					TerminalDisplay.horizontalLine();
					TerminalDisplay.addRequest();
					itemNumber = scanner.next().toUpperCase();
				}
			}	
			TerminalDisplay.clearSequence();
			// get integer from input
			int number = 0;
			if (! itemNumber.equals("D")){
				number = Integer.parseInt(itemNumber);
			}
			else if (itemNumber.equals("D")) break;
            // update cart
			this.orderItem.updateCart(number);
			// order feedback			
			TerminalDisplay.headerOutput();
			TerminalDisplay.horizontalLine();
			TerminalDisplay.orderFeedback();
			// total feedback
			TerminalDisplay.horizontalLine();
			TerminalDisplay.subTotalOutPut(orderTotal, customer);
			TerminalDisplay.horizontalLine();
			// get next input
			TerminalDisplay.addRequest();
			itemNumber = scanner.next().toUpperCase();
			// valid input check
			while(! this.orderItem.validInput(itemNumber)){
			    formatting();
				TerminalDisplay.orderFeedback();
				// order feedback
				TerminalDisplay.horizontalLine();
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				TerminalDisplay.addRequest();
				itemNumber = scanner.next().toUpperCase();         
			}
			//clear screen // keep menu and last order on screen
			TerminalDisplay.clearSequence();
		}
	}

	/**
	* Creates a (mostly) unique invoice number from the Customer data and the Date
	* @return			the invoice number
	*/
	private String getInvoiceID(){
		
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
		invoiceNumber += rightNow.getDay() + "" + rightNow.getMonth() + "" + rightNow.getYear();		
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
	*/
	public void writeToFile(){
		copyCart();
		//Write to file and print       
		try{
			this.name 	= Driver.customer.getName();
			this.phone 	= Driver.customer.getPhone();
			this.email 	= Driver.customer.getEmail();
			// open printwriter in append mode to save all receipts to file, not just one.
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(getInvoiceID()+".txt"), true)); 	
			// space between receipts
			receiptHeaderPrint(writer);
			// sort objects in array by their name attribute
			// iterate through sorted array and print / save
			// alphabetize shopping cart
			shoppingCartAlphabetize(this.shoppingCart);
			// iterate through shopping cart
			calculateTotal(shoppingCart, writer);
			// print other total information
			salesTax();
			totalWithTax();
			receiptFooterPrint(writer);
			processingReceipt = false;
		}catch(IOException ioe){
			System.out.print("Could not write to file");
			System.exit(0);
		}
	}
	
	/**
	 * Method to display the receipt on the computer terminal
	 */
	public void displayReceipt (){
		// reset totals after calculating for print receipt
		this.total = 0;
		this.subtotal = 0;
		this.caloriesTotal = 0;
		receiptHeaderDisplay();
		shoppingCartAlphabetize(this.cartCopy);
		calculateTotal(cartCopy, null);
		receiptFooterDisplay();
	}

	private double salesTax(){
		this.salesTax = this.subtotal * this.TAX;
		return this.salesTax;
	}

	private double totalWithTax(){
		this.totalWithTax = this.salesTax + this.subtotal;
		return this.totalWithTax;
	}

	private void calculateTotal(ArrayList<MenuItem> cart, PrintWriter writer){
		while (cart.size() > 0){
			// get first item from cart
			int index = 0;
			MenuItem menuitem = cart.get(index);
			int quant = this.cartMap.get(menuitem);
			index += 1;
			this.subtotal += menuitem.getPrice() * quant;
			this.caloriesTotal += menuitem.getCalories();
			// remove item when done with it
			cart.remove(menuitem);	
			// total cost for each item bought
			this.total = quant * menuitem.getPrice();
			if (processingReceipt)
				printTotalInfo(menuitem, quant, writer);
			else 
				displayTotalInfo(menuitem, quant);
		}
	}

	private void displayTotalInfo(MenuItem menuitem, int quant){
	System.out.printf(
		"%-32s%-6d%-7s%s%n",
		menuitem.getName(),
		quant,
		TerminalDisplay.nf.format(menuitem.getPrice()),
		TerminalDisplay.nf.format(this.total)
		);
	}

	private void printTotalInfo(MenuItem menuitem, int quant, PrintWriter writer){
		writer.printf(
			"%-32s%-6d%-7s%s%n",
			menuitem.getName(),
			quant,
			TerminalDisplay.nf.format(menuitem.getPrice()),
			TerminalDisplay.nf.format(this.total)
			);
		}

	/**
	 * copy cart for displaying the receipt data in terminal (otherwise cart is null at this point)
	 */
	private void copyCart(){
		for (MenuItem item : this.shoppingCart){
			this.cartCopy.add(item);
		}
	}

	/**
     * Method to clear carts from any previous orders
     */
    public void clearCarts(){
		this.cartMap = new HashMap<MenuItem, Integer>(10);
		this.orderMap = new HashMap<Integer, MenuItem>(10);
		this.removeMap = new HashMap<Integer, MenuItem>(10);
	}

	private void receiptHeaderDisplay () {
		// clear menu
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		System.out.println("ENJOY!");
		System.out.println();
		System.out.printf("%s / %s / %s", this.name, this.phone, this.email);
		System.out.println();
		System.out.printf("Invoice number: %s%n", getInvoiceID());
		System.out.printf("Date: %s%n", this.todaysDateComplete);
		System.out.printf("Time: %s%n", this.timeNow);
		System.out.printf("%-30s%-8s%-7s%s%n","Item", "Quant", "Price", "Total");
		System.out.println("=".repeat(52));
	}

	private void receiptFooterDisplay () {
		System.out.println();
		System.out.printf("Calories: %d%n", this.caloriesTotal);
		System.out.println("=".repeat(52));
		System.out.println();
		System.out.printf("Subtotal%s%s%n",this.space.repeat(15), TerminalDisplay.nf.format(this.subtotal));
		System.out.println("6.25% sales tax"+this.space.repeat(8) + TerminalDisplay.nf.format(this.salesTax));
		System.out.println("Order Total"+this.space.repeat(12) + TerminalDisplay.nf.format(this.totalWithTax));
		System.out.println();
		System.out.print(TerminalDisplay.PRICE_COLORS + "PRESS ENTER " + TerminalDisplay.ANSI_RESET);
		this.orderItem.order.nextLine();
		// wait for user input
		System.out.println();
		System.out.print(TerminalDisplay.PRICE_COLORS + "PRESS ENTER " + TerminalDisplay.ANSI_RESET);
	}

	private void receiptHeaderPrint (PrintWriter writer) {
		writer.println("ENJOY!");
		writer.println();
		writer.printf("%s / %s / %s", this.name, this.phone, this.email);
		writer.println();
		writer.printf("Invoice number: %s%n", getInvoiceID());
		writer.printf("Date: %s%n", this.todaysDateComplete);
		writer.printf("Time: %s%n", this.timeNow);
		writer.printf("%-30s%-8s%-7s%s%n","Item", "Quant", "Price", "Total");
		writer.println("=".repeat(52));
	}

	private void receiptFooterPrint (PrintWriter writer) {
		writer.println();
		writer.printf("Calories: %d%n", this.caloriesTotal);
		writer.println("=".repeat(52));
		writer.println();
		writer.printf("Subtotal%s%s%n",this.space.repeat(15), TerminalDisplay.nf.format(this.subtotal));
		writer.println("6.25% sales tax"+this.space.repeat(8) + TerminalDisplay.nf.format(this.salesTax));
		writer.println("Order Total"+this.space.repeat(12) + TerminalDisplay.nf.format(this.totalWithTax));
		writer.println();
		writer.close();
		// space between receipts in save file
		writer.println();
		writer.println();
	}
	/**
     * Method to help format the display ouput of the cart during ordering
     */
    void formatting() {
        TerminalDisplay.clearSequence();
        TerminalDisplay.headerOutput();
        TerminalDisplay.horizontalLine();
    }

}

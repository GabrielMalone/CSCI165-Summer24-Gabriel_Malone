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
	ArrayList<OrderItem> shoppingCart = new ArrayList<>();
	// array to keep track of names of items in cart
	ArrayList<String> menuNames = new ArrayList<>();
	// hashamp for easy order
    Map<Integer, MenuItem> orderMap = new HashMap<Integer, MenuItem>(10);
    // hasmap for live updating cart info
    Map<String, Integer> cartMap = new HashMap<String, Integer>(10);
    // map for removing items
    Map<Integer, MenuItem> removeMap = new HashMap<Integer, MenuItem>(10);
	
	Scanner scanner = new Scanner(System.in);
	private String space 						= TerminalDisplay.space;
	private double subtotal;
	private int caloriesTotal;
	private double total;
	private double TAX 							= 0.0625;
	private double salesTax;
	private double totalWithTax;
	private ArrayList<OrderItem> cartCopy	 	= new ArrayList<>();
	private String name;						
	private String phone;						
	private String email;				
	private Date rightNow 						= Date.dateInitializer();
	private String todaysDateComplete			= rightNow.dateString(rightNow);
	private String timeNow 						= rightNow.getTime();	
	double orderTotal 							= 0;
	ArrayList<String> cartStringArray = new ArrayList<>();
	Customer customer;
	
	/**
     * No argument constructor leaves all fields default. clears any carts.
     */
	public Order () {
		// clear any carts from previous orders
		clearCarts();
	}
	
	/**
	 * Method to return a String of an Order's current status
	 */
	public String toString() {
		// reset so they dont double up with printing to file and screen
		this.total = 0;
		this.subtotal = 0;
		this.totalWithTax = 0;
		// get customer info 
		this.name 	= Driver.order.customer.getName();
		this.phone 	= Driver.order.customer.getPhone();
		this.email 	= Driver.order.customer.getEmail();
		// duplicate cart for the terminal display of receipt 
		// otherwise cart gets emptied out during alphabetizing process
		copyCart();
		shoppingCartAlphabetize(this.cartCopy);
		// constants for receipt
		String receipt	= "";
		String cartStr	= "";
		String enjoy	= "ENJOY!";
		String invoice 	= "Invoice number:";
		String date 	= "Date:";
		String time 	= "Time:";
		String item 	= "Item";
		String quant	= "Quant";
		String price 	= "Price";
		String total	= "Total";
		String split 	= "=";
		String calories = "Calories:";
		String subtotal	= "Subtotal";
		String salestax = "6.25% sales tax";
		String ordertot = "Order Total";
		// header string
		String receiptheader = String.format(
				"%s%n%n%s / %s / %s%n%s %s%n%s %s%n%s %s%n%-30s%-8s%-7s%s%n%s%n", 
				enjoy, this.name, this.phone, this.email, invoice, getInvoiceID(),
				date, this.todaysDateComplete, time, this.timeNow, item,
				quant, price, total, split.repeat(52));
		// iterate through cart and add items in the cart to the middle of receipt string		
		while (this.cartCopy.size() > 0){
			// get first item from cart
			int index = 0;
			OrderItem menuitem = this.cartCopy.get(index);
			int quantity = this.cartMap.get(menuitem.getMenuItem().getName());
			index += 1;
			this.subtotal += menuitem.getMenuItem().getPrice() * quantity;
			this.caloriesTotal += menuitem.getMenuItem().getCalories();
			// remove item when done with it
			this.cartCopy.remove(menuitem);	
			// total cost for each item bought
			this.total = quantity * menuitem.getMenuItem().getPrice();
			cartStr += String.format(
			"%-32s%-6d%-7s%s%n",
			menuitem.getMenuItem().getName(),
			quantity,
			TerminalDisplay.nf.format(menuitem.getMenuItem().getPrice()),
			TerminalDisplay.nf.format(this.total));
			
		}
		salesTax();
		totalWithTax();
		// footer string
		String receiptfooter = String.format(
				"%n%s %d%n%s%n%s%s%s%n%s%s%n%s%s%n%n",
				calories, this.caloriesTotal, split.repeat(52), subtotal, 
				this.space.repeat(15),TerminalDisplay.nf.format(this.subtotal),
				salestax, this.space.repeat(8) + TerminalDisplay.nf.format(this.salesTax),
				ordertot, this.space.repeat(12) + TerminalDisplay.nf.format(this.totalWithTax));
		receipt = receiptheader + cartStr + receiptfooter;
		return receipt;
	}
	
	/**
	 * Method to compare two Order objects. 
	 * @param otherOrder
	 * @return true if both orders have same contents and quantities
	 */
	public boolean equals(Order otherOrder){
		// get carts from both orders
		// check their size. if not equal false
		if (this.shoppingCart.size() != otherOrder.shoppingCart.size())
			return false;
		// compare contents if equal length. sort the carts alphabetically.
		shoppingCartAlphabetize(this.shoppingCart);
		shoppingCartAlphabetize(otherOrder.shoppingCart);
		// can do index comparison of objects with the carts sorted
		for (int index = 0 ; index < this.shoppingCart.size() ; index ++) {
			OrderItem this_orderitem = this.shoppingCart.get(index);
			OrderItem that_orderitem = otherOrder.shoppingCart.get(index);
			// false if not the same menuitem
			if (! this_orderitem.equals(that_orderitem))
				return false;
		}
		return true;
	}
	
	/**
	* Method to take the customer's order, loops until order complete. 
	* @return (ArrayList<String>) shopping cart items
	*/
	public void takeOrder(){
		customer = new Customer();
		// ask for order
		TerminalDisplay.headerOutput();
		TerminalDisplay.horizontalLine();
		TerminalDisplay.orderFeedback();
		TerminalDisplay.horizontalLine();
		TerminalDisplay.subTotalOutPut(orderTotal, customer);
		TerminalDisplay.horizontalLine();
		TerminalDisplay.addRequest();
		String itemNumber = this.scanner.next().toUpperCase();
		// valid input check
		while(! this.validInput(itemNumber)){
			formatting();
			TerminalDisplay.orderFeedback();
			TerminalDisplay.horizontalLine();
			TerminalDisplay.subTotalOutPut(orderTotal, customer);
			TerminalDisplay.horizontalLine();
			TerminalDisplay.addRequest();
			itemNumber = this.scanner.next().toUpperCase();
		}
		// clear screen
		TerminalDisplay.clearSequence();
		// whilte input valid logic
		while(this.validInput(itemNumber) && ! itemNumber.equals("D")){
			// if remove requested sequence
			while (itemNumber.equals("R") && this.shoppingCart.size() > 0){
				formatting();
				TerminalDisplay.orderFeedback();
				TerminalDisplay.horizontalLine();
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				double priceReduction = this.removeRequest(orderTotal, customer);
				formatting();
				TerminalDisplay.orderFeedback();
				TerminalDisplay.horizontalLine();
				this.orderTotal -= priceReduction;
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				TerminalDisplay.addRequest();
				itemNumber = scanner.next().toUpperCase();
				// valid input check
				while(! this.validInput(itemNumber)){
					formatting();
					TerminalDisplay.orderFeedback();
					TerminalDisplay.horizontalLine();
					TerminalDisplay.subTotalOutPut(orderTotal, customer);
					TerminalDisplay.horizontalLine();
					TerminalDisplay.addRequest();
					itemNumber = this.scanner.next().toUpperCase();
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
				itemNumber = this.scanner.next().toUpperCase();
				// valid input check
				while(! this.validInput(itemNumber)){
					formatting();
					TerminalDisplay.orderFeedback();
					TerminalDisplay.horizontalLine();
					TerminalDisplay.subTotalOutPut(orderTotal, customer);
					TerminalDisplay.horizontalLine();
					TerminalDisplay.addRequest();
					itemNumber = this.scanner.next().toUpperCase();
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
			this.updateCart(number);
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
			while(! this.validInput(itemNumber)){
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
     * Method to put the selected MenuItem object via the Menu Map into the shopping cart
     * repeating this process will increase quantity
     * @param number
     */
    void updateCart(int number){
        // get order from hashmap (number key, object value)
        MenuItem itemForOrder = this.orderMap.get(number);
        // add name to name array to check if should be placed on new line or not in terminal output
       	this.menuNames.add(itemForOrder.getName());
        // place the unique menu items in map for terminal display
        this.cartMap.putIfAbsent(itemForOrder.getName(), 0);
        // update quantity if item ordered more than once
        this.cartMap.computeIfPresent(itemForOrder.getName(), (key, val) -> val += 1);
		// if this the first instance of ordering this item, creating a new OrderItem object
		if (this.cartMap.get(itemForOrder.getName()) == 1){
			OrderItem orderitem = new OrderItem(itemForOrder, this.cartMap.get(itemForOrder.getName()));
			// add to cart
			this.shoppingCart.add(orderitem);
		}
		// otherwise just update the number associated with the orderitem
		else{
			for (OrderItem orderitem : this.shoppingCart){
				if (orderitem.getMenuItem().equals(itemForOrder)){
					int current_quant = orderitem.getQuantity();
					orderitem.updateQuantity(current_quant ++);
				}
			}
		}	
        // update price to correspond with quantity of item ordered
        double orderPrice = itemForOrder.getPrice();
       	this.orderTotal += orderPrice;	
    }

    /**
     * Method for validating a get request
     * @param itemNumber
     * @return
     */
    boolean validInput(String itemNumber){
        // checks to see if user inputted valid options
        boolean valid  = false;
        // lets make the valid input scalable to uknown menu sizes
        ArrayList<String> validInputArray = new ArrayList<String>();
        for (int index = 1; index <= DisplayMenu.myMenuItems.size(); index ++){
            String number = Integer.toString(index);
            validInputArray.add(number);
        }
        validInputArray.add("R");
        validInputArray.add("D");
        for (String valids : validInputArray){
            if (itemNumber.equals(valids)){
                valid = true;
                return valid;
            }
        }
        return valid;
    }

    /**
     * Method for removing an item from the cart
     * @param orderTotal
     * @param customer
     * @return
     */
    double removeRequest(double orderTotal, Customer customer){
        // this sets up the remove request 
        // reduce total amount variable
        double priceReduction = 0;
        // output information
        String itemRequest = "REMOVE # / 0 TO CANCEL: ";
        System.out.printf("%34s%s", TerminalDisplay.space, itemRequest);
        // initialize item to remove var
        int itemToRemove = 0; 
        // get the requested item # to remove
        String removeRequest = this.scanner.nextLine();
        // see if it's actually a number
        // ask for a number until get one
        while (true){
             try {
             itemToRemove = Integer.parseInt(removeRequest);
             break; 
             }
             catch(NumberFormatException eNumberFormatException){
                 this.formatting();
                 TerminalDisplay.orderFeedback();
                 TerminalDisplay.horizontalLine();
                 TerminalDisplay.subTotalOutPut(orderTotal, customer);
                 TerminalDisplay.horizontalLine();
                 itemRequest = "# TO REMOVE: ";
                 System.out.printf("%34s%s", TerminalDisplay.space, itemRequest);
                 removeRequest = this.scanner.next().toUpperCase();
             }	
        }
         // if remove request valid
         if (validRemoveInput(itemToRemove)){
             // remove item + 1 for index adjust
             MenuItem itemBeingRemoved = this.removeMap.get(itemToRemove+1);
             // for live cart updates, reduce the int associated with the item name
             this.cartMap.computeIfPresent(itemBeingRemoved.getName(), (key, val) -> val -= 1);
             // if int reaches 0, remove the item from the live updates
             if (this.cartMap.get(itemBeingRemoved.getName()) == 0){
                this.cartMap.remove(itemBeingRemoved.getName());
                for (OrderItem orderitem : this.shoppingCart){
					if (orderitem.getMenuItem().equals(itemBeingRemoved)){
						this.shoppingCart.remove(orderitem);
						break;
					}
				}
             }
			 else{
					for (OrderItem orderitem : this.shoppingCart){
						if (orderitem.getMenuItem().equals(itemBeingRemoved)){
							int current_quant = orderitem.getQuantity();
							orderitem.updateQuantity(current_quant --);
						}
					}
			 	}
             // reduce total price
             priceReduction = itemBeingRemoved.getPrice();
             // since now only one actual Object in the shopping cart, only remove when reaches zero
         }
            return priceReduction;
    }

    /**
     * Method for validating a remove request
     * @param itemNumber
     * @return whether or not the remove request was valid
     */
    private  boolean validRemoveInput(int itemNumber){
        // checks to see if the remove requests something that exists
        boolean valid = false;
        // create empty array
        ArrayList<Integer> validOptions = new ArrayList<Integer>();
        // fill array with integers matching the items placed in cart 
        for (int index = 0; index < this.cartMap.size(); index ++){
            validOptions.add(index + 1);
        }	
        // iterate through array and check input against the integers in array
        for (int validInt : validOptions){
            if (itemNumber == validInt){
                valid = true;
                return valid;
            }
        }
        return valid;
    }

	/**
	* Creates a (mostly) unique invoice number from the Customer data and the Date
	* @return the invoice number
	*/
	private String getInvoiceID(){
		// Create a variable to store the invoice number
		String invoiceNumber = "";
		// Get the first name										
		String[] name = Driver.order.customer.getName().split(" ");		
		// Get the first two initials of the first name			
		String firstInitials = name[0].substring(0, 2).toUpperCase();
		// Get the first two initials of the last name	
		String lastInitials = name[1].substring(0, 2).toUpperCase();	
		int firstUnicode = (int)name[0].charAt(0);
		// Get the Unicode value of the first character of the first name					
		int lastUnicode = (int)name[1].charAt(0);	
		// Get the Unicode value of the first character of the last name
		// Calculate the ID				
		int id = (firstUnicode + lastUnicode) * Driver.order.customer.getName().length();
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
    void shoppingCartAlphabetize(ArrayList<OrderItem> shoppingCart){
        if (shoppingCart.size() > 1){
            // stating letter A
            char letter = 65;
            // iterate through the menut items
            while (letter <= 90){
                for (int index = 0 ; index < shoppingCart.size() ; index ++){
                    OrderItem item = shoppingCart.get(index);
                    // put each item name into a char array
                    char [] itemname = item.getMenuItem().getName().toCharArray();
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
                OrderItem item1 = shoppingCart.get(index);
                OrderItem item2 = shoppingCart.get(index + 1);
                // if item1 comes after item2 alphabetically, swap
                if (item1.getMenuItem().compareName(item2.getMenuItem()) == 1){
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
		//Write to file and print       
		try{
			// open printwriter in append mode to save all receipts to file, not just one.
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(getInvoiceID()+".txt"), true)); 	
			writer.print(this.toString());
			writer.close();
		}catch(IOException ioe){
			System.out.print("Could not write to file");
			System.exit(0);
		}
		// display receipt on terminal
		displayReceipt();
		scanner.nextLine();
	}
	
	/**
	 * Method to display the receipt on the computer terminal
	 */
	public void displayReceipt (){
		// clear menu
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		// display receipt
		System.out.println(this.toString());
		// wait for user input
		System.out.println();
		System.out.print(TerminalDisplay.PRICE_COLORS + "PRESS ENTER " + TerminalDisplay.ANSI_RESET);
		scanner.nextLine();
	}

	/**
	 * Method to calc sales tax
	 * @return
	 */
	private double salesTax(){
		this.salesTax = this.subtotal * this.TAX;
		return this.salesTax;
	}

	/**
	* Method to calc total cart with tax
	* @return
	*/
	private double totalWithTax(){
		this.totalWithTax = this.salesTax + this.subtotal;
		return this.totalWithTax;
	}

	/**
	* copy cart for displaying the receipt data in terminal (otherwise cart is null at this point)
	*/
	private void copyCart(){
		for (OrderItem item : this.shoppingCart){
			this.cartCopy.add(item);
		}
	}

	/**
 	* Method to clear carts from any previous orders
    */
    public void clearCarts(){
		this.cartMap = new HashMap<String, Integer>(10);
		this.orderMap = new HashMap<Integer, MenuItem>(10);
		this.removeMap = new HashMap<Integer, MenuItem>(10);
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

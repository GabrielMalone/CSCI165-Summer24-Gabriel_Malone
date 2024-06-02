// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order {
	
	// create shopping cart Array
	ArrayList<OrderItem> shoppingCart = new ArrayList<>();
	// hasmap for live updating cart info
    Map<String, Integer> cartMap = new HashMap<String, Integer>(10);
	// array to keep track of names of items in cart
	ArrayList<String> menuNames = new ArrayList<>();
	// hashamp for easy order
	Map<Integer, MenuItem> orderMap = new HashMap<Integer, MenuItem>(10);
    // map for removing items
    Map<Integer, MenuItem> removeMap = new HashMap<Integer, MenuItem>(10);
	private double subtotal;
	private int caloriesTotal;
	private double total;
	private double TAX 							= 0.0625;
	private double salesTax;
	private double totalWithTax;
	private double orderTotal 					= 0;
	private ArrayList<OrderItem> cartCopy	 	= new ArrayList<>();			
	private Customer customer;
	private Date todaysDate;
	String menuSelection;
	int selectionQuantity;
	
	/**
     * No argument constructor leaves all fields default. clears any carts.
     */
	public Order () {
		// clear any carts from previous orders
		clearCarts();
		setDate();
	}

	/**
	 * Overloaded constructor to accept the customer
	 * @param newCustomer
	 */
	public Order (Customer newCustomer){
		this.customer = newCustomer;
		setDate();
	}

	/**
	 * Method to deep copy an Order object
	 * @param copy an Order object
	 */
	public Order(Order copy) {
		this.shoppingCart = copy.shoppingCart;
		this.customer = copy.customer;
		this.todaysDate = copy.todaysDate;
	}

	/**
	 * Method to return a clone of the current customer
	 * @return clone of current customer
	 */
	public Customer getCustomer() {
		Customer clone = new Customer(this.customer);
		return clone;
	}

	public Date getDate(){
		Date cloneDated = new Date(this.todaysDate);
		return cloneDated;
	}

	/**
	 * Method to return the total of an order
	 * @return double of total
	 */
	public double getTotal(){
		return this.orderTotal;
	}

	/**
	 * Method to do a deep copy of an Order's current cart
	 * @return copy of current cart
	 */
	public ArrayList<OrderItem> getCart (){
		ArrayList<OrderItem> cartCopy = new ArrayList<>();
		for (OrderItem item: this.shoppingCart){
			// deep copy the OrderItem object
			OrderItem copy = new OrderItem(item);
			cartCopy.add(copy);
		}
		return cartCopy;
	}

	/**
	 * 
	 * @return String of the invoice ID for this Order
	 */
	public String getInvoiceID(){
		String invoiceID = createInvoiceID();
		return invoiceID;
	}
	
	/**
	 * Method to return a String of an Order's current status
	 */
	public String toString() {
		// reset so they dont double up with printing to file and screen
		this.total = 0;
		this.subtotal = 0;
		this.totalWithTax = 0;
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
				enjoy, this.customer.getName(), this.customer.getPhone(), this.customer.getEmail(), invoice, createInvoiceID(),
				date, this.todaysDate.toString(), time, this.todaysDate.getTime(), item,
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
				TerminalDisplay.space.repeat(15),TerminalDisplay.nf.format(this.subtotal),
				salestax, TerminalDisplay.space.repeat(8) + TerminalDisplay.nf.format(this.salesTax),
				ordertot, TerminalDisplay.space.repeat(12) + TerminalDisplay.nf.format(this.totalWithTax));
		receipt = receiptheader + cartStr + receiptfooter;
		return receipt;
	}

	/**
	 * Method to compare an Order's date with another Order's date
	 * @param otherOrder
	 * @return int
	 */
	public int compareTo(Order otherOrder){
		if (this.getDate().compareTo(otherOrder.getDate()) ==  0) return  0;
		if (this.getDate().compareTo(otherOrder.getDate()) >   1) return  1;
		return -1;
	}
	
	/**
	 * Method to compare two Order objects. 
	 * @param otherOrder
	 * @return boolean true if both orders have same contents and quantities
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
		this.customer = new Customer();
		// ask for order
		TerminalDisplay.headerOutput();
		TerminalDisplay.horizontalLine();
		// show current cart
		TerminalDisplay.orderFeedback();
		subtotalOutputFormatting();
		TerminalDisplay.addRequest();
		menuSelection();
		// valid input check
		while(! validAddInput()){
			// re-request if invalid input
			formatting();
			TerminalDisplay.orderFeedback();
			subtotalOutputFormatting();
			TerminalDisplay.addRequest();
			menuSelection();
		}
		// clear screen
		TerminalDisplay.clearSequence();
		// whilte input valid logic and have not stated done
		while(validAddInput() && ! this.menuSelection.equals("D")){
			// if-remove-requested-sequence
			while (this.menuSelection.equals("R") && this.shoppingCart.size() > 0){
				formatting();
				TerminalDisplay.orderFeedback();
				subtotalOutputFormatting();
				// remove the item if valid request
				OrderItem removedItem = removeItem();
				formatting();
				TerminalDisplay.orderFeedback();
				TerminalDisplay.horizontalLine();
				this.orderTotal -= removedItem.getMenuItem().getPrice() * this.selectionQuantity;
				TerminalDisplay.subTotalOutPut();
				TerminalDisplay.horizontalLine();
				TerminalDisplay.addRequest();
				menuSelection();
				// valid input check
				while(!validAddInput()){
					// re-request if invalid input
					formatting();
					TerminalDisplay.orderFeedback();
					subtotalOutputFormatting();
					TerminalDisplay.addRequest();
					menuSelection();
				}	
			}
			// remove request logic if nothing to remove
			while (this.menuSelection.equals("R") && this.shoppingCart.size() == 0){
				formatting();
				TerminalDisplay.orderFeedback();
				subtotalOutputFormatting();
				TerminalDisplay.addRequest();
				menuSelection();
				// valid input check
				while(! validAddInput()){
					formatting();
					TerminalDisplay.orderFeedback();
					subtotalOutputFormatting();
					TerminalDisplay.addRequest();
					menuSelection();
				}
			}	
			TerminalDisplay.clearSequence();
			// get integer from the valid input
			int menuNumber = 0;
			if (! this.menuSelection.equals("D")){
				menuNumber = Integer.parseInt(this.menuSelection);
			}
			// break loop if customer done
			else if (this.menuSelection.equals("D")) break;
            // otherwise update cart with the valid add request
			TerminalDisplay.headerOutput();
			if (this.cartMap.size() > 0)
				TerminalDisplay.horizontalLine();
			TerminalDisplay.orderFeedback();
			TerminalDisplay.horizontalLine();
			subtotalOutputFormatting();
			TerminalDisplay.quantityRequest();
			selectionQuantity();
			addItem(menuNumber, this.selectionQuantity);
			updateTotal();
			TerminalDisplay.clearSequence();
			// order feedback
			TerminalDisplay.headerOutput();
			TerminalDisplay.horizontalLine();
			TerminalDisplay.orderFeedback();
			TerminalDisplay.horizontalLine();
			// total feedback
			subtotalOutputFormatting();
			// get next input
			TerminalDisplay.addRequest();
			menuSelection();
			// valid input check
			while(! validAddInput()){
			    formatting();
				TerminalDisplay.orderFeedback();
				// order feedback
				subtotalOutputFormatting();
				TerminalDisplay.addRequest();
				menuSelection();        
			}
			//clear screen // keep menu and last order on screen
			TerminalDisplay.clearSequence();
		}
	}
	 /**
	* Method to save order to file and print receipt
	*/
	public void writeToFile(){
		//Write to file and print       
		try{
			// open printwriter in append mode to save all receipts to file, not just one.
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(createInvoiceID()+".txt"), true)); 	
			writer.print(this.toString());
			writer.close();
		}catch(IOException ioe){
			System.out.print("Could not write to file");
			System.exit(0);
		}
		// display receipt on terminal
		displayReceipt();
		Driver.scanner.nextLine();
		
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
		Driver.scanner.nextLine();
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
     * Method to put the selected MenuItem object via the Menu Map into the shopping cart
     * repeating this process will increase quantity
     * @param number
     */
    void addItem(int menuNumber, int quantity){
        // get order from hashmap (number key, object value)
        MenuItem itemForOrder = this.orderMap.get(menuNumber);
		// see if already in cartmap. if not, create a new objectitem
		if (! this.cartMap.containsKey(itemForOrder.getName())){
			// first instance of ordering this item, creating a new OrderItem object
			OrderItem orderitem = new OrderItem(itemForOrder, quantity);
			// add to cart
			this.shoppingCart.add(orderitem);
			// place the unique menu items in map for terminal display
			this.cartMap.putIfAbsent(itemForOrder.getName(), quantity);
		}
		// otherwise just update the number associated with the orderitem
		else{
			for (OrderItem orderitem : this.shoppingCart){
				if (orderitem.getMenuItem().equals(itemForOrder)){
					orderitem.updateQuantity(quantity);
					int current_quant = orderitem.getQuantity();
					this.cartMap.replace(itemForOrder.getName(), current_quant);
					if (current_quant > 99){
						// since no setter methods allowed, will use recursion to reduce until under 100.
						while (current_quant > 99){
							orderitem.updateQuantity(-1);
						}
					}
				}
			}
		}	
		// limit the order sizes to 99 max
		if (cartMap.get(itemForOrder.getName()) > 99){
			quantity = 99; ;
			this.cartMap.replace(itemForOrder.getName(), quantity);
		}
        // add name to name array to check if should be placed on new line or not in terminal output
       	this.menuNames.add(itemForOrder.getName());	
    }

	/**
     * Method for removing an item from the cart
     * @return OrderItem being removed from the cart
     */
    OrderItem removeItem(){
		OrderItem orderItem=null;
        // this sets up the remove request
        // initialize item to remove var
		formatting();
		TerminalDisplay.orderFeedback();
		subtotalOutputFormatting();
		String itemRequest = "ITEM # FROM CART TO REMOVE: ";
		System.out.printf("%22s%s", TerminalDisplay.space, itemRequest);
		String removeRequest = Driver.scanner.next().toUpperCase();
		TerminalDisplay.clearSequence();
        // see if it's actually a number
        // ask for a number until get one
		int itemToRemove;
		// validity checks for integer input and valid remove choice
		while (true){
			try{
				itemToRemove = Integer.valueOf(removeRequest);
				if (validRemoveInput(itemToRemove))
					break;
				formatting();
				TerminalDisplay.orderFeedback();
				subtotalOutputFormatting();
				itemRequest = "ITEM # FROM CART TO REMOVE: ";
				System.out.printf("%22s%s", TerminalDisplay.space, itemRequest);
				removeRequest = Driver.scanner.next().toUpperCase();
				TerminalDisplay.clearSequence();
			}
			catch (NumberFormatException e){
				formatting();
				TerminalDisplay.orderFeedback();
				subtotalOutputFormatting();
				itemRequest = "ITEM # FROM CART TO REMOVE: ";
				System.out.printf("%22s%s", TerminalDisplay.space, itemRequest);
				removeRequest = Driver.scanner.next().toUpperCase();
				TerminalDisplay.clearSequence();
			}
		} 
		// remove item + 1 for index adjust
		MenuItem itemBeingRemoved = this.removeMap.get(itemToRemove+1);
		// for live cart updates, reduce the int associated with the item name
		formatting();
		TerminalDisplay.orderFeedback();
		subtotalOutputFormatting();
		TerminalDisplay.quantityRemoveRequest(itemBeingRemoved);
		selectionRemoveQuantity(itemBeingRemoved);
		// check to prevent a remove request creating a negative value (just remove the item if they input more than exists)
		if (this.selectionQuantity > this.cartMap.get(itemBeingRemoved.getName()))
			this.selectionQuantity = this.cartMap.get(itemBeingRemoved.getName());
		this.cartMap.computeIfPresent(itemBeingRemoved.getName(), (key, val) -> val -= this.selectionQuantity);
		// if int reaches 0, remove the item from the live updates
		if (this.cartMap.get(itemBeingRemoved.getName()) == 0){
		this.cartMap.remove(itemBeingRemoved.getName());
		for (OrderItem orderitem : this.shoppingCart){
				if (orderitem.getMenuItem().equals(itemBeingRemoved)){
				this.shoppingCart.remove(orderitem);
					return orderitem;
				}
			}
		}
		// if item still in cart after removing some quantity
		else{
			for (OrderItem orderitem : this.shoppingCart){
				if (orderitem.getMenuItem().equals(itemBeingRemoved)){
				int current_quant = orderitem.getQuantity();
				orderitem.updateQuantity(current_quant -= this.selectionQuantity);
				return orderitem;
				}
			}
		}
		return orderItem;	
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
	
    private void formatting() {
        TerminalDisplay.clearSequence();
        TerminalDisplay.headerOutput();
        TerminalDisplay.horizontalLine();
    }

	private void subtotalOutputFormatting(){
		TerminalDisplay.horizontalLine();
		TerminalDisplay.subTotalOutPut();
		TerminalDisplay.horizontalLine();
	}

	/**
     * Method for validating a get request
     * @param userInput
     * @return
     */
    private boolean validAddInput(){
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
            if (this.menuSelection.equals(valids)){
                valid = true;
                return valid;
            }
        }
        return valid;
    }

	/**
     * Method for validating a remove request
     * @param userInput
     * @return whether or not the remove request was valid
     */
    private  boolean validRemoveInput(int userInput){
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
            if (userInput == validInt){
                valid = true;
                return valid;
            }
        }
        return valid;
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
	* Creates a (mostly) unique invoice number from the Customer data and the Date
	* @return the invoice number
	*/
	private String createInvoiceID(){
		// Create a variable to store the invoice number
		String invoiceNumber = "";
		// Get the first name										
		String[] name = this.customer.getName().split(" ");		
		// Get the first two initials of the first name			
		String firstInitials = name[0].substring(0, 2).toUpperCase();
		// Get the first two initials of the last name	
		String lastInitials = name[1].substring(0, 2).toUpperCase();	
		int firstUnicode = (int)name[0].charAt(0);
		// Get the Unicode value of the first character of the first name					
		int lastUnicode = (int)name[1].charAt(0);	
		// Get the Unicode value of the first character of the last name
		// Calculate the ID				
		int id = (firstUnicode + lastUnicode) * this.customer.getName().length();
		// Concatenate the initials and ID
		invoiceNumber = firstInitials + lastInitials + id;	
		// Concatenate the date (add the time in if you want)			
		invoiceNumber += todaysDate.getDay() + "" + todaysDate.getMonth() + "" + todaysDate.getYear();		
		return invoiceNumber;											
	}

	private void setDate(){
		this.todaysDate = Date.dateInitializer();
	}

	private void menuSelection (){
		this.menuSelection = Driver.scanner.next().toUpperCase(); 
	}

	private void selectionQuantity (){
		// validity checks for item selection quantities
		while (true){
			try{
				this.selectionQuantity = Integer.valueOf(Driver.scanner.next().toUpperCase());
				if (this.selectionQuantity > 99){
					this.selectionQuantity = 99;
					break;
				}
				if (this.selectionQuantity < 0){
					this.selectionQuantity = 0;
					break;

				}
				break;
			}
			catch(NumberFormatException e){
				TerminalDisplay.clearSequence();
				TerminalDisplay.headerOutput();
				if (this.cartMap.size() > 0)
					TerminalDisplay.horizontalLine();
				TerminalDisplay.orderFeedback();
				TerminalDisplay.horizontalLine();
				subtotalOutputFormatting();
				TerminalDisplay.quantityRequest();
			}
		}
	}

	private void selectionRemoveQuantity (MenuItem itemBeingRemoved){
		// validity checks for item selection quantities
		while (true){
			try{
				this.selectionQuantity = Integer.valueOf(Driver.scanner.next().toUpperCase());
				if (this.selectionQuantity > 99){
					this.selectionQuantity = 99;
					break;
				}
				if (this.selectionQuantity < 0){
					this.selectionQuantity = 0;
					break;

				}
				break;
			}
			catch(NumberFormatException e){
				TerminalDisplay.clearSequence();
				TerminalDisplay.headerOutput();
				if (this.cartMap.size() > 0)
					TerminalDisplay.horizontalLine();
				TerminalDisplay.orderFeedback();
				TerminalDisplay.horizontalLine();
				subtotalOutputFormatting();
				TerminalDisplay.quantityRemoveRequest(itemBeingRemoved);
			}
		}
	}

	private void updateTotal () {
		this.orderTotal = 0;
		for (OrderItem orderitem : this.shoppingCart){
			this.orderTotal += (orderitem.getMenuItem().getPrice() * orderitem.getQuantity());
		}
	}

}

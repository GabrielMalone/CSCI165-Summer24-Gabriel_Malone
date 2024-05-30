// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderItem {
    // create shopping cart
    public ArrayList<MenuItem> shoppingCart = new ArrayList<>();
    // scanner for user input
    public Scanner order = new Scanner(System.in);
    // hashamp for easy ordering
    public Map<Integer, MenuItem> orderMap = new HashMap<Integer, MenuItem>(10);
    // hasmap for live updating cart info
    public Map<MenuItem, Integer> cartMap = new HashMap<MenuItem, Integer>(10);
    // map for removing items
    public Map<Integer, MenuItem> removeMap = new HashMap<Integer, MenuItem>(10);
    // array to keep track of names of items in cart
    public ArrayList<String> menuNames = new ArrayList<>();
    // order total 
    public double orderTotal = 0;

    /**
	* Method to take the customer's order
	* 
	* @return (ArrayList<String>) shopping cart items
	*/
	public void takeOrder(Customer customer){
		// ask for order
		TerminalDisplay.headerOutput();
		TerminalDisplay.horizontalLine();
		TerminalDisplay.orderFeedback();
		TerminalDisplay.horizontalLine();
		TerminalDisplay.subTotalOutPut(orderTotal, customer);
		TerminalDisplay.horizontalLine();
		TerminalDisplay.addRequest();
		String itemNumber = order.next().toUpperCase();
		// valid input check
		while(! validInput(itemNumber)){
			formatting();
			TerminalDisplay.orderFeedback();
			TerminalDisplay.horizontalLine();
			TerminalDisplay.subTotalOutPut(orderTotal, customer);
			TerminalDisplay.horizontalLine();
			TerminalDisplay.addRequest();
			itemNumber = order.next().toUpperCase();
		}
		// clear screen
		TerminalDisplay.clearSequence();
		// whilte input valid logic
		while(validInput(itemNumber) && ! itemNumber.equals("D")){
			// if remove requested sequence
			while (itemNumber.equals("R") && this.shoppingCart.size() > 0){
				formatting();
				TerminalDisplay.orderFeedback();
				TerminalDisplay.horizontalLine();
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				double priceReduction = removeRequest(orderTotal, customer);
				formatting();
				TerminalDisplay.orderFeedback();
				TerminalDisplay.horizontalLine();
				orderTotal -= priceReduction;
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				TerminalDisplay.addRequest();
				itemNumber = order.next().toUpperCase();
				// valid input check
				while(! validInput(itemNumber)){
					formatting();
					TerminalDisplay.orderFeedback();
					TerminalDisplay.horizontalLine();
					TerminalDisplay.subTotalOutPut(orderTotal, customer);
					TerminalDisplay.horizontalLine();
					TerminalDisplay.addRequest();
					itemNumber = order.next().toUpperCase();
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
				itemNumber = order.next().toUpperCase();
				// valid input check
				while(! validInput(itemNumber)){
					formatting();
					TerminalDisplay.orderFeedback();
					TerminalDisplay.horizontalLine();
					TerminalDisplay.subTotalOutPut(orderTotal, customer);
					TerminalDisplay.horizontalLine();
					TerminalDisplay.addRequest();
					itemNumber = order.next().toUpperCase();
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
			updateCart(number);
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
			itemNumber = order.next().toUpperCase();
			// valid input check
			while(! validInput(itemNumber)){
			    formatting();
				TerminalDisplay.orderFeedback();
				// order feedback
				TerminalDisplay.horizontalLine();
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				TerminalDisplay.addRequest();
				itemNumber = order.next().toUpperCase();         
			}
			//clear screen // keep menu and last order on screen
			TerminalDisplay.clearSequence();
		}
	}

    private  boolean validRemoveInput(int itemNumber){
        // checks to see if the remove requests something that exists
        boolean valid = false;
        // create empty array
        ArrayList<Integer> validOptions = new ArrayList<Integer>();
        // fill array with integers matching the items placed in cart 
        for (int index = 0; index < cartMap.size(); index ++){
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

    private double removeRequest(double orderTotal, Customer customer){
        // this sets up the remove request 
        // reduce total amount variable
        double priceReduction = 0;
        // output information
        String itemRequest = "REMOVE # / 0 TO CANCEL: ";
        System.out.printf("%34s%s", TerminalDisplay.space, itemRequest);
        order.nextLine();
        // initialize item to remove var
        int itemToRemove = 0; 
        // get the requested item # to remove
        String removeRequest = order.nextLine();
        // see if it's actually a number
        // ask for a number until get one
        while (true){
             try {
             itemToRemove = Integer.parseInt(removeRequest);
             break; 
             }
             catch(NumberFormatException eNumberFormatException){
                 formatting();
                 TerminalDisplay.orderFeedback();
                 TerminalDisplay.horizontalLine();
                 TerminalDisplay.subTotalOutPut(orderTotal, customer);
                 TerminalDisplay.horizontalLine();
                 itemRequest = "# TO REMOVE: ";
                 System.out.printf("%34s%s", TerminalDisplay.space, itemRequest);
                 removeRequest = order.next().toUpperCase();
             }	
        }
         // if remove request valid
         if (validRemoveInput(itemToRemove)){
             // remove item
             MenuItem itemBeingRemoved = removeMap.get(itemToRemove+1);
             // for live cart updates
             cartMap.computeIfPresent(itemBeingRemoved, (key, val) -> val -= 1);
             if (cartMap.get(itemBeingRemoved) == 0) cartMap.remove(itemBeingRemoved);
             // reduce total price
             priceReduction = itemBeingRemoved.getPrice();
             this.shoppingCart.remove(itemBeingRemoved);
         }
            return priceReduction;
    }

    private boolean validInput(String itemNumber){
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

    private void updateCart(int number){
        // get order from hashmap (number key, object value)
        MenuItem itemForOrder = orderMap.get(number);
        // add name to name array to check if should be placed on  new line or not in terminal output
        menuNames.add(itemForOrder.getName());
        // if name not in array, create the new menuitem 
        if (! menuNames.contains(itemForOrder.getName())){
            // create a deep copy 
            itemForOrder = new MenuItem(itemForOrder);
            // place item in cart for checkout
        }
        // place the unique menu items in map for terminal display
        cartMap.putIfAbsent(itemForOrder, 0);
        // update quantity if item ordered more than once
        cartMap.computeIfPresent(itemForOrder, (key, val) -> val += 1);
        // update price to correspond with quantity of item ordered
        double orderPrice = itemForOrder.getPrice();
        orderTotal += orderPrice;
        if (! this.shoppingCart.contains(itemForOrder))
            this.shoppingCart.add(itemForOrder);
    }

    private void formatting() {
        TerminalDisplay.clearSequence();
        TerminalDisplay.headerOutput();
        TerminalDisplay.horizontalLine();
    }

    public void clearCarts(){
		this.cartMap = new HashMap<MenuItem, Integer>(10);
		this.orderMap = new HashMap<Integer, MenuItem>(10);
		this.removeMap = new HashMap<Integer, MenuItem>(10);
	}

}

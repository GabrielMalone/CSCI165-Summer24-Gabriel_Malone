// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderItem {

    // scanner for user input
    static Scanner order = new Scanner(System.in);
    // hashamp for easy ordering
    static Map<Integer, MenuItem> orderMap = new HashMap<Integer, MenuItem>(10);
    // hasmap for live updating cart info
    static Map<MenuItem, Integer> cartMap = new HashMap<MenuItem, Integer>(10);
    // map for removing items
    static Map<Integer, MenuItem> removeMap = new HashMap<Integer, MenuItem>(10);

    /**
	* Method to take the customer's order
	* 
	* @return (ArrayList<String>) shopping cart items
	*/
	public static ArrayList<MenuItem> takeOrder(Customer customer){
		// order total 
		double orderTotal = 0;	
		// create shopping cart
		ArrayList<MenuItem> shoppingCart = new ArrayList<>();
		// ask for order
		TerminalDisplay.headerOutput();
		TerminalDisplay.horizontalLine();
		TerminalDisplay.orderFeedback(shoppingCart);
		TerminalDisplay.horizontalLine();
		TerminalDisplay.subTotalOutPut(orderTotal, customer);
		TerminalDisplay.horizontalLine();
		TerminalDisplay.addRequest();
		String itemNumber = order.next().toUpperCase();
		// valid input check
		while(! validInput(itemNumber)){
			TerminalDisplay.clearSequence();
			TerminalDisplay.headerOutput();
			TerminalDisplay.horizontalLine();
			TerminalDisplay.orderFeedback(shoppingCart);
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
			while (itemNumber.equals("R") && shoppingCart.size() > 0){
				TerminalDisplay.clearSequence();
				TerminalDisplay.headerOutput();
				TerminalDisplay.horizontalLine();
				TerminalDisplay.orderFeedback(shoppingCart);
				TerminalDisplay.horizontalLine();
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				double priceReduction = removeRequest(shoppingCart, orderTotal, customer);
				//shoppingCartAlphabetize(shoppingCart);
				TerminalDisplay.clearSequence();
				TerminalDisplay.headerOutput();
				TerminalDisplay.horizontalLine();
				TerminalDisplay.orderFeedback(shoppingCart);
				TerminalDisplay.horizontalLine();
				orderTotal -= priceReduction;
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				TerminalDisplay.addRequest();
				itemNumber = order.next().toUpperCase();
				// valid input check
				while(! validInput(itemNumber)){
					TerminalDisplay.clearSequence();
					TerminalDisplay.headerOutput();
					TerminalDisplay.horizontalLine();
					TerminalDisplay.orderFeedback(shoppingCart);
					TerminalDisplay.horizontalLine();
					TerminalDisplay.subTotalOutPut(orderTotal, customer);
					TerminalDisplay.horizontalLine();
					TerminalDisplay.addRequest();
					itemNumber = order.next().toUpperCase();
				}	
			}
			// remove request logic if nothing to remove
			while (itemNumber.equals("R") && shoppingCart.size() == 0){
				TerminalDisplay.clearSequence();
				TerminalDisplay.headerOutput();
				TerminalDisplay.horizontalLine();
				TerminalDisplay.orderFeedback(shoppingCart);
				TerminalDisplay.horizontalLine();
				TerminalDisplay.subTotalOutPut(orderTotal, customer);
				TerminalDisplay.horizontalLine();
				TerminalDisplay.addRequest();
				itemNumber = order.next().toUpperCase();
				// valid input check
				while(! validInput(itemNumber)){
					TerminalDisplay.clearSequence();
					TerminalDisplay.headerOutput();
					TerminalDisplay.horizontalLine();
					TerminalDisplay.orderFeedback(shoppingCart);
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
			// get order from hashmap (number key, object value)
			MenuItem itemForOrder = orderMap.get(number);
			cartMap.putIfAbsent(itemForOrder, 0);
			cartMap.computeIfPresent(itemForOrder, (key, val) -> val += 1);
			// for output feedback on order
			double orderPrice = itemForOrder.getPrice();
			orderTotal += orderPrice;
			shoppingCart.add(itemForOrder);
			// order feedback			
			TerminalDisplay.headerOutput();
			TerminalDisplay.horizontalLine();
			TerminalDisplay.orderFeedback(shoppingCart);
			// total feedback
			TerminalDisplay.horizontalLine();
			TerminalDisplay.subTotalOutPut(orderTotal, customer);
			TerminalDisplay.horizontalLine();
			// get next input
			TerminalDisplay.addRequest();
			itemNumber = order.next().toUpperCase();
			// valid input check
			while(! validInput(itemNumber)){
				TerminalDisplay.clearSequence();
				TerminalDisplay.headerOutput();
				TerminalDisplay.horizontalLine();
				TerminalDisplay.orderFeedback(shoppingCart);
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
		return shoppingCart;
	}

    private static boolean validRemoveInput(ArrayList<MenuItem> shoppingCart, int itemNumber){
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

    private static double removeRequest(ArrayList<MenuItem> shoppingCart, double orderTotal, Customer customer){
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
                 TerminalDisplay.clearSequence();
                 TerminalDisplay.headerOutput();
                 TerminalDisplay.horizontalLine();
                 TerminalDisplay.orderFeedback(shoppingCart);
                 TerminalDisplay.horizontalLine();
                 TerminalDisplay.subTotalOutPut(orderTotal, customer);
                 TerminalDisplay.horizontalLine();
                 itemRequest = "# TO REMOVE: ";
                 System.out.printf("%34s%s", TerminalDisplay.space, itemRequest);
                 removeRequest = order.next().toUpperCase();
             }	
        }
         // if remove request valid
         if (validRemoveInput(shoppingCart, itemToRemove)){
             // remove item
             MenuItem itemBeingRemoved = removeMap.get(itemToRemove+1);
             // for live cart updates
             cartMap.computeIfPresent(itemBeingRemoved, (key, val) -> val -= 1);
             if (cartMap.get(itemBeingRemoved) == 0) cartMap.remove(itemBeingRemoved);
             // reduce total price
             priceReduction = itemBeingRemoved.getPrice();
             shoppingCart.remove(itemBeingRemoved);
         }
            return priceReduction;
    }

    private static boolean validInput(String itemNumber){
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
    * Method to alphabetize customer's shopping cart
    * 
    * @param shoppingCart list of MenuItems
    * @return ArrayList<MenuItem>
    */
    public static void shoppingCartAlphabetize(ArrayList<MenuItem> shoppingCart){
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

}

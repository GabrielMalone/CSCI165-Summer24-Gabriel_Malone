// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

import java.util.ArrayList;
import java.util.Scanner;

public class OrderItem {
   
    // scanner for user input
    Scanner order = new Scanner(System.in);  
    // order total for terminal display
    private int item_total = 0;
    private MenuItem menuItem; 


    /**
     * No methods constructor
     */
    public OrderItem (){}

    /**
     * Overloaded method constructor
     * @param menuItem
     * @param item_total
     */
    public OrderItem(MenuItem menuItem, int item_total) {
        // clone any menuItems passed into the method
        this.menuItem = new MenuItem(menuItem);
        this.item_total = item_total;
    }

    /**
     * Method to return a cloned menu item
     * @return
     */
    public MenuItem getMenuItem () {
       return this.menuItem;
    }

    /**
     * Method to retun the number of items a OrderItem has
     * @return int of total number of items an OrderItem has
     */
    public int getItemTotal (){
        return this.item_total;
    }

    /*
     * Method to return a string detailing the current OrderItem
     */
    @Override
    public String toString() {
        return "OrderItem [orderTotal=" +
        "$" + item_total * menuItem.getPrice()
        + ", item_total=" + item_total + ", menuItem=" + menuItem + "]";
    }

    /*
     * Method to increase the quantity of a MenuItem outside of Terminal Display.
     */
    public void updateQuantity (int quant){
        if (this.item_total + quant > 0)
            this.item_total += quant;
    }

    /*
     * Method to get the quanity of a MenuItem outside of Terminal Display.
     */
    public int getQuantity () {
      return this.item_total;
    }

    /**
     * Method to put the selected MenuItem object via the Menu Map into the shopping cart
     * repeating this process will increase quantity
     * @param number
     */
    void updateCart(int number){
        // get order from hashmap (number key, object value)
        MenuItem itemForOrder = Driver.order.orderMap.get(number);
        // add name to name array to check if should be placed on  new line or not in terminal output
        Driver.order.menuNames.add(itemForOrder.getName());
        // if name not in array, create the new menuitem 
        if (! Driver.order.menuNames.contains(itemForOrder.getName())){
            // create a deep copy 
            itemForOrder = new MenuItem(itemForOrder);
            // place item in cart for checkout
        }
        // place the unique menu items in map for terminal display
        Driver.order.cartMap.putIfAbsent(itemForOrder, 0);
        // update quantity if item ordered more than once
        Driver.order.cartMap.computeIfPresent(itemForOrder, (key, val) -> val += 1);
        // update price to correspond with quantity of item ordered
        double orderPrice = itemForOrder.getPrice();
        Driver.order.orderTotal += orderPrice;
        if (! Driver.order.shoppingCart.contains(itemForOrder))
            Driver.order.shoppingCart.add(itemForOrder);
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
        String removeRequest = order.nextLine();
        // see if it's actually a number
        // ask for a number until get one
        while (true){
             try {
             itemToRemove = Integer.parseInt(removeRequest);
             break; 
             }
             catch(NumberFormatException eNumberFormatException){
                 Driver.order.formatting();
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
             // remove item + 1 for index adjust
             MenuItem itemBeingRemoved = Driver.order.removeMap.get(itemToRemove+1);
             // for live cart updates, reduce the int associated with the item name
             Driver.order.cartMap.computeIfPresent(itemBeingRemoved, (key, val) -> val -= 1);
             // if int reaches 0, remove the item from the live updates
             if (Driver.order.cartMap.get(itemBeingRemoved) == 0){
                Driver.order.cartMap.remove(itemBeingRemoved);
                Driver.order.shoppingCart.remove(itemBeingRemoved);
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
        for (int index = 0; index < Driver.order.cartMap.size(); index ++){
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
     * Method to compare two OrderItems
     * @param otherOrderItem
     * @return
     */
    public boolean equals(OrderItem otherOrderItem){
        // compare name and quantity, not memory address
        if (this.menuItem.getName() 
            == otherOrderItem.menuItem.getName()
            && this.getItemTotal() == otherOrderItem.getItemTotal())
            return true;
        else
            return false;
    }
}

// Gabriel Malone / CSCI65 / Week 6 / Summer 2024

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Driver{
    
    // text colors
    public static final String ANSI_RESET  = "\u001B[0m"; 
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_BLUE   = "\u001B[34m";

    // use this a lot
    static String space = " ";
    // hashamp for easy ordering
    static Map<Integer, MenuItem> orderMap = new HashMap<Integer, MenuItem>(10);
    // load current items on the menu
    static ArrayList<MenuItem> myMenuItems = loadMenuItems("products.txt");
    // scanner for user input
    static Scanner order = new Scanner(System.in);
    // create customer
    
    // main program loop
    public static void main(String[] args) {
        // run until machine turned off
        while(true){
        // clear screen and show menu
        clearSequence();
        Customer customer = createCustomer();
        // take order // clear shopping cart
        ArrayList<MenuItem> shoppingCart = takeOrder(customer);
        // today's date
        Date today = dateInitializer();
        // print receipt // save receipt info
        printOrder(shoppingCart, customer, today);
        }
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
        addRequest();
        String itemNumber = order.next().toUpperCase();
        // valid input check
        while(! validInput(itemNumber)){
            clearSequence();
            addRequest();
            itemNumber = order.next().toUpperCase();
        }
        // clear screen
        clearSequence();
        while(validInput(itemNumber) && ! itemNumber.equals("D")){
            // remove request sequence
            while (itemNumber.equals("R") && shoppingCart.size() > 0){
                clearSequence();
                horizontalLine();
                orderFeedback(shoppingCart);
                horizontalLine();
                subTotalOutPut(orderTotal, customer);
                horizontalLine();
                double priceReduction = removeRequest(shoppingCart, orderTotal, customer);
                shoppingCartAlphabetize(shoppingCart);
                clearSequence();
                horizontalLine();
                orderFeedback(shoppingCart);
                horizontalLine();
                orderTotal -= priceReduction;
                subTotalOutPut(orderTotal, customer);
                horizontalLine();
                addRequest();
                itemNumber = order.next().toUpperCase();
                while(! validInput(itemNumber)){
                    clearSequence();
                    horizontalLine();
                    orderFeedback(shoppingCart);
                    horizontalLine();
                    subTotalOutPut(orderTotal, customer);
                    horizontalLine();
                    addRequest();
                    itemNumber = order.next().toUpperCase();
                }	
            }
            while (itemNumber.equals("R") && shoppingCart.size() == 0){
                clearSequence();
                horizontalLine();
                orderFeedback(shoppingCart);
                horizontalLine();
                subTotalOutPut(orderTotal, customer);
                horizontalLine();
                addRequest();
                itemNumber = order.next().toUpperCase();
                while(! validInput(itemNumber)){
                    clearSequence();
                    horizontalLine();
                    orderFeedback(shoppingCart);
                    horizontalLine();
                    subTotalOutPut(orderTotal, customer);
                    horizontalLine();
                    addRequest();
                    itemNumber = order.next().toUpperCase();
                }
            }	
            clearSequence();
            // get integer from input
            int number = 0;
            if (! itemNumber.equals("D")){
                number = Integer.parseInt(itemNumber);
            }
            else if (itemNumber.equals("D")) break;
            // get order from hashmap (number key, object value)
            MenuItem itemForOrder = orderMap.get(number);
            // for output feedback on order
            double orderPrice = itemForOrder.getPrice();
            orderTotal += orderPrice;
            shoppingCart.add(itemForOrder);
            shoppingCartAlphabetize(shoppingCart);
            shoppingCartAlphabetize(shoppingCart);
            // order feedback			
            horizontalLine();
            orderFeedback(shoppingCart);
            // total feedback
            horizontalLine();
            subTotalOutPut(orderTotal, customer);
            horizontalLine();
            // get next input
            addRequest();
            itemNumber = order.next().toUpperCase();
        
            // valid input check
            while(! validInput(itemNumber)){
                clearSequence();
                horizontalLine();
                orderFeedback(shoppingCart);
                // order feedback
                horizontalLine();
                subTotalOutPut(orderTotal, customer);
                horizontalLine();
                addRequest();
                itemNumber = order.next().toUpperCase();
                                
            }
            //clear screen // keep menu and last order on screen
            clearSequence();
        }
        return shoppingCart;
    }
    
    /**
     * Method to create a new customer 
     * 
     * @return (Object) new customer instance
     */
    public static Customer createCustomer(){
        // get info 
        String name = nameRequest();
        clearSequence();
        String email = emailRequest();
        clearSequence();
        String phone = phoneRequest();
        // create customer
        Customer newCustomer = new Customer(name, email, phone);
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
            File file = new File(filename);	// Create a File object
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
        }
        catch(FileNotFoundException e){	// Catch the FileNotFoundException
            System.out.println("File not found: " + filename);	// Print an error message
        }
        return menuItems;	// Return the ArrayList
    }	

    /**
     * Displays items on the menu
     * 
     * @param menuItems loaded from file
     */
    public static void printMenu(ArrayList<MenuItem> myMenuItems){
        // create string for money values
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        // table constructors
        String lineBreak = "-";
        String leftRowSeperator = "| ";
        String rightRowSeperator = " |";
        int index = 1;
        int foodItem = 0;
        int foodItem2 = 0;
        // row constructor
        for (int rows = 0; rows < 3; rows ++){
            System.out.println();
            System.out.println(lineBreak.repeat(95));	
            // column constructor - two inner loops
            // first for item name and number
            // second for item price & cals
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
            // far right margin
            System.out.print(rightRowSeperator);
            System.out.println();
            // second inner loop for price
            for (int columns2 = 0; columns2 < 3; columns2 ++) {
                MenuItem item = myMenuItems.get(foodItem2);
                System.out.printf("%s%s%-25s", 
                leftRowSeperator,
                "    ",
                nf.format(item.getPrice())
                );
                foodItem2 += 1;
                
            }
            // far right margin
            System.out.print(rightRowSeperator);
        }
        System.out.println();
        System.out.println(lineBreak.repeat(95));	
        // the final menu item centered in its own row
        System.out.printf("%33s%d%s%-24s%s%n%33s%9s%21s%n%63s",
        leftRowSeperator,
        index,
        "  ",
        myMenuItems.get(9).getName(),
        rightRowSeperator,
        leftRowSeperator,
        nf.format(myMenuItems.get(9).getPrice()),
        rightRowSeperator,
        lineBreak.repeat(32)
        );
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
            
            // open printwriter in append mode to save all receipts to file, not just one.
            PrintWriter writer = new PrintWriter(new FileOutputStream(new File("output.txt"), true)); 	
            // goodbye message
            System.out.println("ENJOY!");
            System.out.println();
            // print receipt and save data at each step
            System.out.printf("Customer: %s%n", customer.getName());
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
            shoppingCartAlphabetize(shoppingCart);
            
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
            order.nextLine();
            
            // space between receipts in save file
            writer.println();
            writer.println();
            
            // wait for user input
            System.out.println();
            System.out.print("PRESS ENTER ");
            String placeHolder = order.nextLine();
            
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
        // Create a variable to store the invoice number
        String invoiceNumber = "";
        // Get the first name										
        String[] name = customer.getName().split(" ");		
        // Get the first two initials of the first name			
        String firstInitials= name[0].substring(0, 2).toUpperCase();
        // Get the first two initials of the last name	
        String lastInitials = name[1].substring(0, 2).toUpperCase();	
        int firstUnicode 	= (int)name[0].charAt(0);
        // Get the Unicode value of the first character of the first name					
        int lastUnicode 	= (int)name[1].charAt(0);	
        // Get the Unicode value of the first character of the last name
        // Calculate the ID				
        int id = (firstUnicode + lastUnicode) * customer.getName().length();
        // Concatenate the initials and ID
        invoiceNumber = firstInitials + lastInitials + id;	
        // Concatenate the date (add the time in if you want)			
        invoiceNumber += today.getDay() + today.getMonth() + getTime();				
        return invoiceNumber;											
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
        System.out.println(); 
    }
    
    private static void printLogo(){
        String welcomeMsg = "WELCOME TO HOORDUB";
        System.out.printf("%n%55s%n",welcomeMsg);
    }

    private static void horizontalLine(){
        System.out.printf("%69s%n","-".repeat(46));
    }

    private static void addRequest(){
        String itemRequest = "ADD (#) / (R)EMOVE / (D)ONE ";
        System.out.printf("%33s%s", space, itemRequest);
    }

    private static void subTotalOutPut(double orderTotal, Customer newCustomer){
        System.out.printf("%23s%s%23s $%.2f%n", space, newCustomer.getName(), "Subtotal", orderTotal);
    }

    private static void orderFeedback(ArrayList<MenuItem> shoppingCart){
        for ( int counter = 0 ;  counter < shoppingCart.size() ; counter ++){
            MenuItem item = shoppingCart.get(counter);
            System.out.printf("%25s%-2d - %-26s $%.2f %4d%n", space, counter + 1, item.getName(), item.getPrice(), item.getCalories());
        }
    }

    private static String nameRequest(){
        System.out.printf("%38s%s ", space,"NAME: ");
        String name = order.nextLine();
        return name;
    }

    private static String emailRequest(){
        System.out.printf("%38s%s", space, "EMAIL: ");
        String email = order.nextLine();
        return email;
    }

    private static String phoneRequest(){
        System.out.printf("%38s%s", space, "PHONE: ");
        String phone = order.nextLine();
        return phone;
    }

    private static double removeRequest(ArrayList<MenuItem> shoppingCart, double orderTotal, Customer customer){
        double priceReduction = 0;
        String itemRequest = "# TO REMOVE: ";
        System.out.printf("%34s%s", space, itemRequest);
        order.nextLine();
        int itemToRemove = 0; 
        String removeRequest = order.nextLine();
        while (true){
            try {
            itemToRemove = Integer.parseInt(removeRequest);
            break; 
            }
            catch(NumberFormatException eNumberFormatException){
                clearSequence();
                clearSequence();
                horizontalLine();
                orderFeedback(shoppingCart);
                horizontalLine();
                subTotalOutPut(orderTotal, customer);
                horizontalLine();
                addRequest();
                removeRequest = order.next().toUpperCase();
            }	
        }
        if (validRemoveInput(shoppingCart, itemToRemove)){
            clearSequence();
            MenuItem itemBeingRemoved = shoppingCart.get(itemToRemove - 1);
            priceReduction = itemBeingRemoved.getPrice();
            shoppingCart.remove(itemToRemove - 1);
        }
        return priceReduction;
    }

    private static boolean validInput(String itemNumber){
        boolean valid  = false;
        String [] validIndput = new String [] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "D", "R" };
        for (String valids : validIndput){
            if (itemNumber.equals(valids)){
                valid = true;
                return valid;
            }
        }
        return valid;
    }

    private static boolean validRemoveInput(ArrayList<MenuItem> shoppingCart, int itemNumber){
        boolean valid = false;
        // create empty array
        ArrayList<Integer> validOptions = new ArrayList<Integer>();
        // fill array with integers matching the items placed in cart 
        for (int index = 0; index < shoppingCart.size(); index ++){
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
}
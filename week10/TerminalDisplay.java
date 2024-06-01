// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

import java.text.NumberFormat;
import java.util.ArrayList;


public class TerminalDisplay {
	// text // graphic colors
	public static 	String ANSI_RESET           = "\u001B[0m"; 
	private static  String ANSI_PURPLE          = "\u001B[35m";
	private static  String ANSI_WHITE           = "\u001B[37m";
	public static  	String ANSI_BLACK           = "\u001B[31m";
	public static  	String ANSI_YELLOW          = "\u001B[33m";
	private static  String ANSI_PINK            = "\u001b[38;5;201m";
	private static  String ANSI_BLUE            = "\u001B[34m";
	public static  	String ANSI_CYAN            = "\u001B[36m";
	private static  String ANSI_BOLD            = "\u001b[1m";
	private static  String ANSI_BLUE_BACK       = "\u001B[44m";
	private static  String CALORIE_COLOR        = ANSI_PURPLE;
	private static  String MENU_NUMBER_COLOR    = ANSI_PURPLE;
	private static  String MENU_BORDER_COLOR    = ANSI_PINK;
	public 	static  String PRICE_COLORS         = ANSI_BLUE;
	private static  String SUBTOTAL_COLOR       = ANSI_BLUE_BACK + ANSI_BOLD + ANSI_WHITE;
	public static boolean display = true;
	
	// use this a lot
	static String space = " ";
	// money output used quite a bit
	static NumberFormat nf = NumberFormat.getCurrencyInstance();

	public static void clearSequence(){
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		printLogo();
		printMenu(DisplayMenu.myMenuItems);
		System.out.println(); 
	}
	
	static void printLogo(){
		String welcomeMsg = "GOOBERT'S RETRO FOODHUB";
		System.out.printf("%n%s%55s%s%n",PRICE_COLORS, welcomeMsg, ANSI_RESET);
	}

	static void horizontalLine(){
		System.out.printf("%s%71s%s%n", MENU_BORDER_COLOR, "-".repeat(50), ANSI_RESET);
	}

	static void addRequest(){
		String itemRequest = "ADD (" + MENU_NUMBER_COLOR + "#" + ANSI_RESET + ") | (" + ANSI_PURPLE + "R" + ANSI_RESET + ")EMOVE | (" + ANSI_PURPLE + "D" + ANSI_RESET + ")ONE ";
		System.out.printf("%33s%s", space, itemRequest);
	}
	
	static String nameRequest(){

		horizontalLine();
		System.out.printf("%38s%s ", space,"NAME: ");
		String name = Driver.scanner.nextLine();
		if (name.length() > 26){
			// cap name lengths to 26 char to fit nicely in menu
			name = name.substring(0, 26);
		}
		return name;
	}

	static String emailRequest(){

		horizontalLine();
		System.out.printf("%38s%s", space, "EMAIL: ");
		String email = Driver.scanner.nextLine();

		return email;
	}

	static String phoneRequest(){
		horizontalLine();
		System.out.printf("%38s%s", space, "PHONE: ");
		String phone = Driver.scanner.nextLine();
		return phone;
	}

	static void menuTopBottomBorder(){
		String lineBreak = MENU_BORDER_COLOR + "-" + ANSI_RESET;
		System.out.println(lineBreak.repeat(92));	
	}

	static String menuSeperator(){
		String leftRowSeperator  = MENU_BORDER_COLOR + "| " + ANSI_RESET;
		return leftRowSeperator;
	}

	static String rightMenuSeperator(){
		String rightRowSeperator = MENU_BORDER_COLOR + " |" + ANSI_RESET;
		return rightRowSeperator;
	}

	static void headerOutput(){
		String itemString  =  ANSI_PURPLE + "item"  +  ANSI_RESET;
		String quantString =  ANSI_PURPLE + "quant" +  ANSI_RESET;
		String priceString =  ANSI_PURPLE + "price" +  ANSI_RESET;
		String calsString  =  ANSI_PURPLE + "cals"  +  ANSI_RESET;
		System.out.printf("%-26s%-33s%-16s%-17s%s%n", space, itemString, quantString, priceString, calsString);
	}

  	/**
	* Displays items on the menu
	* 
	* @param menuItems loaded from file
	*/
	public static void printMenu(ArrayList<MenuItem> myMenuItems){
		// table constructors
		String leftRowSeperator  = menuSeperator();
		String rightRowSeperator = rightMenuSeperator();
		int index     = 1;
		int foodItem  = 0;
		int foodItem2 = 0;
		int rowsMax   = myMenuItems.size() / 3;
		// if any stragglers, add one more row
		if (myMenuItems.size() % 3 > 0) {
			rowsMax += 1;
		}
		// row constructor
		for (int rows = 0; rows < rowsMax; rows ++){
			System.out.println();
			menuTopBottomBorder();	
			// column constructor - two inner loops
			// first for item name and number
			// second for item price & cals
			for (int columns = 0; columns < 3; columns ++) {
				// if no more items in menu, complete empty row
				if (foodItem >= myMenuItems.size()){
					System.out.printf("%-45s", 
					leftRowSeperator
					);
				}
				// otherwise fill row
				else{
					MenuItem item = myMenuItems.get(foodItem);
					System.out.printf("%s%s%2d%s%s%-25s", 
					leftRowSeperator,
					MENU_NUMBER_COLOR,
					index,
					ANSI_RESET,
					" ",
					item.getName()
					);
					Driver.order.orderMap.put(index, item);
					index += 1;
					foodItem += 1;
				}    
			}
			// far right margin
			System.out.print(rightRowSeperator);
			System.out.println();
			// second inner loop for price
			for (int columns2 = 0; columns2 < 3; columns2 ++) {
				// if no more items in menu / complete empty row
				if (foodItem2 >= myMenuItems.size()){
					System.out.printf("%-45s", 
					leftRowSeperator
					);
				} 
				// otherwise fill row  
				else{
					MenuItem item = myMenuItems.get(foodItem2);
					System.out.printf("%s%s%s%-25s%s", 
					leftRowSeperator,
					"   ",
					PRICE_COLORS,
					nf.format(item.getPrice()),
					ANSI_RESET
					);
					foodItem2 += 1;
				}        
			}
			// far right margin
			System.out.print(rightRowSeperator);
		}
		System.out.println();
		menuTopBottomBorder();	
		System.out.println();
	}

	public static void subTotalOutPut(){
		   
		System.out.printf("%23s%-30s%s %s$%.2f%s%n", space, Driver.order.getCustomer().getName(), "Subtotal", SUBTOTAL_COLOR, Driver.order.getTotal(), ANSI_RESET);
	}

	public static void orderFeedback(){
		// to display cart items
		ArrayList <OrderItem> tempCart = new ArrayList<>();
		// make a hashmap, add menuitem as key and quantity as value.
		// then print from the hashmap 
		for(OrderItem items : Driver.order.shoppingCart){
			if (Driver.order.cartMap.containsKey(items.getMenuItem().getName())) {
				tempCart.add(items);
			}	
		}
		int counter = 0;
		Driver.order.shoppingCartAlphabetize(tempCart);
		Driver.order.shoppingCartAlphabetize(tempCart);
		for (OrderItem item: tempCart){
			String itemTotal = "(" +  Driver.order.cartMap.get(item.getMenuItem().getName()) + ")";
			String itemSubtotal = PRICE_COLORS + nf.format((item.getMenuItem().getPrice()) * Driver.order.cartMap.get(item.getMenuItem().getName())) +  ANSI_RESET;
			String caloriesTotal = CALORIE_COLOR +  (item.getMenuItem().getCalories() * Driver.order.cartMap.get(item.getMenuItem().getName())) +  ANSI_RESET;
			String cartList = MENU_BORDER_COLOR + (counter += 1) +  ANSI_RESET;
			System.out.printf("%23s%-18s%-26s%-4s %-17s %s%n", 
			space, cartList, item.getMenuItem().getName(),itemTotal, itemSubtotal, caloriesTotal);
			Driver.order.removeMap.put(counter + 1, item.getMenuItem());

		}
	}

}

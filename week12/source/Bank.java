// Gabriel Malone / CSCI165 / Week 12 / Summer 2024

import java.text.NumberFormat;
import java.util.Scanner;

public class Bank {
    // text // graphic colors
	private static 	String ANSI_RESET           = "\u001B[0m"; 
	private static  String ANSI_PURPLE          = "\u001B[35m";
	private static  String ANSI_WHITE           = "\u001B[37m";
	private static  String ANSI_BLACK           = "\u001B[31m";
	private static  String ANSI_YELLOW          = "\u001B[33m";
	private static  String ANSI_PINK            = "\u001b[38;5;201m";
	private static  String ANSI_BLUE            = "\u001B[34m";
	private static  String ANSI_CYAN            = "\u001B[36m";
	private static  String ANSI_BOLD            = "\u001b[1m";
	private static  String ANSI_BLUE_BACK       = "\u001b[1m\u001B[32m";
    private static  String ANSI_GREEN           = "\u001B[32m";
	private static  String CALORIE_COLOR        = ANSI_PURPLE;
	private static  String MENU_NUMBER_COLOR    = ANSI_PURPLE;
	private static  String MENU_BORDER_COLOR    = ANSI_PINK;
	private static  String PRICE_COLORS         = ANSI_BLUE;
	
    private static Scanner scanner = new Scanner(System.in);
    private AccountManager manager = new AccountManager();
	
    // use this a lot
	private static String space = " ";
	// money output used quite a bit
	private static NumberFormat nf = NumberFormat.getCurrencyInstance();
	private double dividendRate = 0.01;

	/**
	 * No arguments constructor. Begins GUI Terminal Sequence.
	 */
	public Bank(){

        clearSequence();
        manager.loadAccounts("source/accounts.txt");
        MainMenu();
        mainMenuOptions();
        String selection = scanner.nextLine().toLowerCase();
		if (selection.equals("a")){
			openAccount();
		}
		if (selection.equals("f")){
			Account account = findAccount();
			displayCustomerInfo(account);
			customerMenuOptions();
		}
        clearSequence();
    }

	public void updateAccounts(){
		
	}

	public void closeAccount(){

	}

	public void payDividends(){

	}

	private Account findAccount(){
		clearSequence();
		MainMenu();
		System.out.printf("%21s%s%n", space, ANSI_YELLOW + "Retrieve Customer Info" + ANSI_RESET);
		yellowhorizontalLine();
		System.out.printf("%21s%s", space, ANSI_CYAN 	+ "Enter Customer ID:     " + ANSI_RESET);
    	String customerID = scanner.nextLine().toLowerCase();
		// get customer account object from ID
		Account retrieved_customer = this.manager.findAccount(customerID);
		return retrieved_customer;
	}

	public void showAccounts(){

	}
	private void openAccount(){
		clearSequence();
		MainMenu();
		// Header
		System.out.printf("%21s%s%n", space, ANSI_YELLOW + "Enter New Customer Information" + ANSI_RESET);
		yellowhorizontalLine();
		// Inputs
		System.out.printf("%21s%s", space, ANSI_CYAN 	+ "First Name     " + ANSI_RESET);
    	String firstName = scanner.nextLine().toLowerCase();
		System.out.printf("%21s%s", space, ANSI_CYAN 	+ "Last Name      " + ANSI_RESET);
    	String lastName = scanner.nextLine().toLowerCase();
		System.out.printf("%21s%s", space, ANSI_CYAN 	+ "10-Digit Phone " + ANSI_RESET);
    	String phone = scanner.nextLine();
		System.out.printf( "%21s%s", space, ANSI_CYAN 	+ "DOB (1/1/1111) " + ANSI_RESET);
    	String DOBstring[] = scanner.nextLine().split("/");

		// create DOB Date object
		Date DOB = new Date(Integer.valueOf(DOBstring[0]), Integer.valueOf(DOBstring[1]), Integer.valueOf(DOBstring[2]));
		// create Person Object with the current info
		Person person = new Person(firstName, lastName, phone, DOB);
		// create customerID with this info 
		String customerID = createCustomerID(firstName,lastName, DOB);
		// Now create new Customer Object (Person, todays date, new customer ID)
		Customer customer = new Customer(person, Date.dateInitializer(), customerID);
		// Open an account
		Account account = new Account(createAccountNumber(), customer, Date.dateInitializer(), 0.0);
		// add this customer to the bank's 'database'
		this.manager.addAccount(account);
		this.manager.saveAccounts("source/accounts.txt");
	}

	private String createCustomerID(String firstName, String lastName, Date DOB){
		String customerID = "";
		String day = String.valueOf(DOB.getDay());
		String year = String.valueOf(DOB.getYear());
		char firstInitial = firstName.charAt(0);
		char lastInitial  = lastName.charAt(0);
		customerID = firstInitial + lastInitial + day + year;
		return customerID;
	}

	private int createEmployeeID(){
		int employeeID = 0;
		return employeeID;
	}

	private int createAccountNumber(){
		// return the size of the customer list + 1
		return this.manager.getAccounts().size() + 1;
	}

    private void clearSequence(){
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		System.out.println(); 
	}

    private void greenhorizontalLine(){
		System.out.printf("%s%71s%s%n", ANSI_GREEN, "-".repeat(50), ANSI_RESET);
	}

	private void yellowhorizontalLine(){
		System.out.printf("%s%71s%s%n", ANSI_YELLOW, "-".repeat(50), ANSI_RESET);
	}

	private void bluehorizontalLine(){
		System.out.printf("%s%71s%s%n", ANSI_CYAN, "-".repeat(50), ANSI_RESET);
	}

    private void Options(){
		String itemString  =  ANSI_PURPLE + "item"  +  ANSI_RESET;
		String quantString =  ANSI_PURPLE + "quant" +  ANSI_RESET;
		String priceString =  ANSI_PURPLE + "price" +  ANSI_RESET;
		String calsString  =  ANSI_PURPLE + "cals"  +  ANSI_RESET;
		System.out.printf("%-26s%-33s%-16s%-17s%s%n", space, itemString, quantString, priceString, calsString);
	}

    private void MainMenu(){
		System.out.printf("%43s%37s%n",ANSI_CYAN + "CHEMICAL BANK" + ANSI_RESET, Date.dateInitializer());
        greenhorizontalLine();
        System.out.printf("%47s",ANSI_YELLOW +  "Employee Terminal" + ANSI_RESET);
        System.out.printf("%25s %s%n",  ANSI_BLUE_BACK + String.valueOf(manager.total_acnts), "accounts on file" + ANSI_RESET);
        greenhorizontalLine();
    }

    private void mainMenuOptions(){
		String itemRequest =  "(" + ANSI_YELLOW + "A" + ANSI_RESET + ")" + ANSI_CYAN + "DD CUSTOMER" + ANSI_RESET + " |" + " (" + ANSI_YELLOW + "F" + ANSI_RESET + ")" +  ANSI_CYAN + "IND CUSTOMER" + ANSI_RESET + " | (" + ANSI_YELLOW + "U" + ANSI_RESET + ")" + ANSI_CYAN + "PDATE ACCNTS " + ANSI_RESET;
		System.out.printf("%21s%s", space, itemRequest);
	}

	private void customerMenuOptions(){
		bluehorizontalLine();
		String itemRequest =  "(" + ANSI_YELLOW + "A" + ANSI_RESET + ")" + ANSI_CYAN + "DD ACCOUNT" + ANSI_RESET + " |" + " (" + ANSI_YELLOW + "C" + ANSI_RESET + ")" +  ANSI_CYAN + "LOSE ACCOUNT" + ANSI_RESET + " | (" + ANSI_YELLOW + "U" + ANSI_RESET + ")" + ANSI_CYAN + "PDATE ACCOUNT " + ANSI_RESET;
		System.out.printf("%21s%s", space, itemRequest);
		String phone = scanner.nextLine();
	}

	private void displayCustomerInfo(Account account){
		bluehorizontalLine();
		System.out.printf("%45s%n", account);
	}
}

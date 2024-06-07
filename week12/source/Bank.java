// Gabriel Malone / CSCI165 / Week 12 / Summer 2024
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank {
 
    private static Scanner scanner = new Scanner(System.in);
    private AccountManager manager = new AccountManager();
	private static String space = " ";
	//private double dividendRate = 0.01;
	private boolean loggedIn = false;
	// current account being interacted with 
	Account currentAccount;

	/**
	 * No arguments constructor. Begins GUI Terminal Sequence.
	 */
	public Bank(){
		// load saved data 
		manager.loadManagers("source/employees.txt");
		manager.loadAccounts("source/accounts.txt");	
		// start the GUI loop
		while (true){
			clearSequence();
			if (! loggedIn)
				MainMenuLogin();
			clearSequence();
			MainMenu();
			displayAccounts();
			mainMenuOptions();
			String mainMenuSelection = scanner.nextLine().toLowerCase();
			// add a customer
			if (mainMenuSelection.equals("a")){
				// open the account
				openAccount();
			}
			// find a customer
			if (mainMenuSelection.equals("s")){
				this.currentAccount = findAccount();
				displayCustomerInfo(this.currentAccount);
				customerMenuOptions();
				String CustomerSelection = scanner.nextLine().toLowerCase();
				// add an account to an existing customer
				if (CustomerSelection.equals("a")){
					addAccountToCustomer();
					displayCustomerInfo(this.currentAccount);
					CustomerSelection = scanner.nextLine().toLowerCase();
				}
			}
			// return to main menu
			if (mainMenuSelection.equals("0")){
				clearSequence();
				MainMenu();
				mainMenuOptions();
			}
			// update a customer
			if (mainMenuSelection.equals("u")){
				
			}
			clearSequence();
		}
	}	

	/**
	 * Mehthod to update all accounts at once
	 */
	public void updateAccounts(){

	}

	public void displayAccounts(){
		int index = 1;
		String headerString =  "Customer Name" + space.repeat(23) + "Account Number";
			System.out.printf("%21s%s%n", space, headerString);
		yellowhorizontalLine();
		for (Account account : this.manager.getAccounts()){
			System.out.printf("%22s%-4d", space, index);
			System.out.printf("%-35s", account.getOwner().getName());
			String accountNumString =  account.getOwner().getID();
			System.out.printf("%s%n", accountNumString);
			index ++;
		}
		yellowhorizontalLine();
	}
	
	public void addAccountToCustomer(){
		clearSequence();
		MainMenu();
		String title =  Colors.ANSI_YELLOW + "// CURRENT ACCOUNT INFO //" + Colors.ANSI_RESET;
		System.out.printf("%21s%s%n", space, title);
		displayCustomerInfo(this.currentAccount);
		bluehorizontalLine();
		String menutitle =  Colors.ANSI_YELLOW + "// ADD ACCOUNT TYPE //" + Colors.ANSI_RESET;
		System.out.printf("%35s%s%n", space, menutitle);
		yellowhorizontalLine();
		String itemRequest =  "(" + Colors.ANSI_YELLOW 			+ "C" + Colors.ANSI_RESET + ")" 	+ Colors.ANSI_CYAN + "HECKING ACCOUNT" 	+ Colors.ANSI_RESET 
							+ " |" + " (" + Colors.ANSI_YELLOW 	+ "S" + Colors.ANSI_RESET + ")" 	+ Colors.ANSI_CYAN + "AVINGS ACCOUNT" 	+ Colors.ANSI_RESET;
		System.out.printf("%27s%s ", space, itemRequest);
		String accountType = scanner.nextLine().toLowerCase();
		ArrayList<String> options = new ArrayList<>();
		options.add("c");
		options.add("s");
		while (! options.contains(accountType)){
			clearSequence();
			MainMenu();
			displayCustomerInfo(this.currentAccount);
			bluehorizontalLine();
			yellowhorizontalLine();
			itemRequest =  "(" + Colors.ANSI_YELLOW 		+ "C" + Colors.ANSI_RESET + ")" 	+ Colors.ANSI_CYAN + "HECKING ACCOUNT" 	+ Colors.ANSI_RESET 
						+ " |" + " (" + Colors.ANSI_YELLOW 	+ "S" + Colors.ANSI_RESET + ")" 	+ Colors.ANSI_CYAN + "AVINGS ACCOUNT" 	+ Colors.ANSI_RESET;
			System.out.printf("%27s%s ", space, itemRequest);
			accountType = scanner.nextLine().toLowerCase();
		}
		yellowhorizontalLine();
		itemRequest =  Colors.ANSI_YELLOW + "DEPOSIT AMMOUNT: " + Colors.ANSI_RESET;
			System.out.printf("%28s%s ", space, itemRequest);
		String deposit = scanner.nextLine();
		double depsositDouble;
		double overDraftLimitDouble;
		while (true){
			try {
				depsositDouble = Double.parseDouble(deposit);
				break;
			} catch (Exception e) {
				clearSequence();
				MainMenu();
				displayCustomerInfo(this.currentAccount);
				bluehorizontalLine();
				yellowhorizontalLine();
				itemRequest =  "(" + Colors.ANSI_YELLOW 			+ "C" + Colors.ANSI_RESET + ")" 	+ Colors.ANSI_CYAN + "HECKING ACCOUNT" 	+ Colors.ANSI_RESET 
							+ " |" + " (" + Colors.ANSI_YELLOW 	+ "S" + Colors.ANSI_RESET + ")" 	+ Colors.ANSI_CYAN + "AVINGS ACCOUNT" 	+ Colors.ANSI_RESET;
				System.out.printf("%27s%s%n", space, itemRequest);
				yellowhorizontalLine();
				itemRequest =  Colors.ANSI_YELLOW + "DEPOSIT AMMOUNT: " + Colors.ANSI_RESET;
				System.out.printf("%28s%s ", space, itemRequest);
				deposit = scanner.nextLine();
			}
		}
		if (accountType.equals("c")){
			itemRequest =  Colors.ANSI_YELLOW + "OVERDRAFT LIMIT: " + Colors.ANSI_RESET;
			System.out.printf("%28s%s ", space, itemRequest);
			String overDraftLimit = scanner.nextLine();
			while (true){
				try {
					overDraftLimitDouble = Double.parseDouble(overDraftLimit);
					break;
				} catch (Exception e) {
					clearSequence();
					MainMenu();
					displayCustomerInfo(this.currentAccount);
					bluehorizontalLine();
					yellowhorizontalLine();
					itemRequest =  "(" + Colors.ANSI_YELLOW 			+ "C" + Colors.ANSI_RESET + ")" 	+ Colors.ANSI_CYAN + "HECKING ACCOUNT" 	+ Colors.ANSI_RESET 
								+ " |" + " (" + Colors.ANSI_YELLOW 	+ "S" + Colors.ANSI_RESET + ")" 	+ Colors.ANSI_CYAN + "AVINGS ACCOUNT" 	+ Colors.ANSI_RESET;
					System.out.printf("%27s%s%n", space, itemRequest);
					yellowhorizontalLine();
					itemRequest =  Colors.ANSI_YELLOW + "OVERDRAFT LIMIT: " + Colors.ANSI_RESET;
					System.out.printf("%28s%s ", space, itemRequest);
					overDraftLimit = scanner.nextLine();
				}
			}
			int accountID = createCheckingAccountNumber();
			// set manager to employee at terminal when account created
			// create filled out checking account object
			CheckingAccount checkingAccount = new CheckingAccount(accountID, this.currentAccount.getOwner(), Date.dateInitializer() , depsositDouble, overDraftLimitDouble);
			this.manager.addAccount(checkingAccount);
			this.manager.saveAccount("source/accounts.txt", checkingAccount);
		}
	}
			
			

	public void closeAccount(){

	}

	public void payDividends(){

	}

	private Account findAccount(){
		clearSequence();
		MainMenu();
		displayAccounts();
		System.out.printf("%35s%s%n", space, Colors.ANSI_YELLOW + "// RETRIEVE CUSTOMER INFO // " + Colors.ANSI_RESET);
		yellowhorizontalLine();
		System.out.printf("%21s%s", space, Colors.ANSI_CYAN 	+ "Enter Customer ID: " + Colors.ANSI_RESET);
    	String customerID = scanner.nextLine().toLowerCase();
		// get customer account object from ID
		while (true){
			try {
				Account retrieved_customer = this.manager.findAccount(customerID);
				return retrieved_customer;
			} catch (Exception e) {
				clearSequence();
				MainMenu();
				System.out.printf("%21s%s%n", space, Colors.ANSI_YELLOW + "Retrieve Customer Info" + Colors.ANSI_RESET);
				yellowhorizontalLine();
				System.out.printf("%21s%s", space, Colors.ANSI_CYAN 	+ "Enter Customer ID:     " + Colors.ANSI_RESET);
				customerID = scanner.nextLine().toLowerCase();
			}
		}
	}

	public void showAccounts(){
	}

	private void openAccount(){
		clearSequence();
		MainMenu();
		// Header
		System.out.printf("%21s%s%n", space, Colors.ANSI_YELLOW + "// NEW CUSTOMER INFO //" + Colors.ANSI_RESET);
		yellowhorizontalLine();
		// Inputs
		System.out.printf("%21s%s", space,Colors.ANSI_CYAN 	+ "First Name     " 	+ Colors.ANSI_RESET);
    	String firstName = scanner.nextLine().toLowerCase();
		System.out.printf("%21s%s", space, Colors.ANSI_CYAN 	+ "Last Name      " 	+ Colors.ANSI_RESET);
    	String lastName = scanner.nextLine().toLowerCase();
		System.out.printf("%21s%s", space, Colors.ANSI_CYAN 	+ "10-Digit Phone " 	+ Colors.ANSI_RESET);
    	String phone = scanner.nextLine();
		System.out.printf( "%21s%s", space, Colors.ANSI_CYAN + "DOB (1/1/1111) " 	+ Colors.ANSI_RESET);
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
		Account account = new Account(createAccountNumber(), customer, manager.findEmployee(String.valueOf(manager.getCurrentLoginID())), Date.dateInitializer());
		// add this customer to the bank's 'database'
		this.manager.addAccount(account);
		this.manager.saveAccount("source/accounts.txt", account);
		this.currentAccount = account;
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

	private int createAccountNumber(){
		// return the size of the customer list + 1
		return this.manager.getAccounts().size() + 1;
	}

	private int createCheckingAccountNumber(){
		Random rand = new Random();
		int randInt = rand.nextInt(0,1000);
		int dateCode = Date.dateInitializer().getYear();
		int accntnum = randInt + dateCode;
		return accntnum;
	}

    private void clearSequence(){
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		System.out.println(); 
	}

    private void greenhorizontalLine(){
		System.out.printf("%s%71s%s%n", Colors.ANSI_GREEN, "-".repeat(50), Colors.ANSI_RESET);
	}

	private void yellowhorizontalLine(){
		System.out.printf("%s%71s%s%n", Colors.ANSI_YELLOW, "-".repeat(50), Colors.ANSI_RESET);
	}

	private void bluehorizontalLine(){
		System.out.printf("%s%71s%s%n", Colors.ANSI_CYAN, "-".repeat(50), Colors.ANSI_RESET);
	}

    private void MainMenu(){
		System.out.printf("%43s%37s%n",Colors.ANSI_CYAN 	+ "CHEMICAL BANK" + Colors.ANSI_RESET, Date.dateInitializer());
        greenhorizontalLine();
        System.out.printf("%35s", Colors.ANSI_YELLOW 	+ "Welcome, ");  
		System.out.printf("%s",manager.findEmployee(String.valueOf(manager.getCurrentLoginID())).getFirstName() + Colors.ANSI_RESET);
        System.out.printf("%25s %s%n", Colors.ANSI_BLUE_BACK  + "// "+ String.valueOf(manager.total_acnts), "accounts on file //" + Colors.ANSI_RESET);
        greenhorizontalLine();
    }

	private void MainMenuLogin(){
		// login header
		System.out.printf("%43s%37s%n",Colors.ANSI_CYAN 	+ "CHEMICAL BANK" 		+ Colors.ANSI_RESET, Date.dateInitializer());
        greenhorizontalLine();
        System.out.printf("%54s",  Colors.ANSI_YELLOW 	+ "// EMPLOYEE TERMINAL //" 	+ Colors.ANSI_RESET);
        System.out.printf("%30s",  Colors.ANSI_CYAN 		+ "Login ID: " 	+ Colors.ANSI_RESET);
		// employee login
		// search for the employee with the given ID
		String employeeID = scanner.nextLine();
		Employee employee = manager.findEmployee(employeeID);
		while (employee == null){
			// if invalid, re-request until valid
			clearSequence();
			System.out.printf("%43s%37s%n",Colors.ANSI_CYAN 	+ "CHEMICAL BANK" 		+ Colors.ANSI_RESET, Date.dateInitializer());
			greenhorizontalLine();
			System.out.printf("%47s",  Colors.ANSI_YELLOW 	+ "Employee Terminal" 	+ Colors.ANSI_RESET);
			System.out.printf("%35s",  Colors.ANSI_CYAN 		+ "Enter Login ID: " 	+ Colors.ANSI_RESET);
			employeeID = scanner.nextLine();
			employee = manager.findEmployee(employeeID);
		}
		this.loggedIn = true;
		// set the current login ID to this employee (used to track / associate a manager with new accounts)
		this.manager.setCurrentLoginID(employee);
        greenhorizontalLine();
    }

    private void mainMenuOptions(){
		String itemRequest =  	"(" + Colors.ANSI_YELLOW + "A" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN 	+ "DD CUSTOMER" 			+ Colors.ANSI_RESET + 
								" |" + " (" + Colors.ANSI_YELLOW + "S" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN + "ELECT CUSTOMER" 	+ Colors.ANSI_RESET +
								" |" + " (" + Colors.ANSI_YELLOW + "U" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN 	+ "PDATE ALL" 	+ Colors.ANSI_RESET;
								System.out.printf("%21s%s", space, itemRequest);
	}

	private void customerMenuOptions(){
		bluehorizontalLine();
		String itemRequest =  	"(" + Colors.ANSI_YELLOW + "A" + Colors.ANSI_RESET + ")" 		+ Colors.ANSI_CYAN + "DD ACCOUNT" 				+ Colors.ANSI_RESET 
								+ " |" + " (" + Colors.ANSI_YELLOW + "C" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN + "LOSE ACCOUNT" 	+ Colors.ANSI_RESET
								+ " |" + " (" + Colors.ANSI_YELLOW + "U" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN + "PDATE ACCOUNT " 	+ Colors.ANSI_RESET;
								System.out.printf("%21s%s", space, itemRequest);
		}

	private void displayCustomerInfo(Account account){
		bluehorizontalLine();
		System.out.printf("%s%n", account);
	}
}

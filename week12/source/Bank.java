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
			Print.clearSequence();
			if (! loggedIn)
				MainMenuLogin();
			Print.clearSequence();
			Print.MainMenu(manager);
			displayAccounts();
			Print.mainMenuOptions();
			String mainMenuSelection = scanner.nextLine().toLowerCase();
			// add a customer
			if (mainMenuSelection.equals("a")){
				// open the account
				openAccount();
			}
			// find a customer
			if (mainMenuSelection.equals("s")){
				this.currentAccount = findAccount();
				Print.displayCustomerInfo(this.currentAccount);
				Print.customerMenuOptions();
				String CustomerSelection = scanner.nextLine().toLowerCase();
				// add an account to an existing customer
				if (CustomerSelection.equals("u")){
					Print.displayCustomerInfo(this.currentAccount);
					CustomerSelection = scanner.nextLine().toLowerCase();
				}
			}
			// return to main menu
			if (mainMenuSelection.equals("m")){
				Print.clearSequence();
				Print.MainMenu(manager);
				Print.mainMenuOptions();
			}
			// update a customer
			if (mainMenuSelection.equals("u")){	
			}
			Print.clearSequence();
		}
	}	

	/**
	 * Mehthod to update all accounts at once
	 */
	public void updateAccounts(){
	}
	public void closeAccount(){

	}
	public void payDividends(){
	}
				
	private void openAccount(){
		Print.clearSequence();
		Print.MainMenu(manager);
		// Header
		Print.newCustomerHeader();
		Print.yellowhorizontalLine();
		// Inputs
		Print.firstNameHeader();
    	String firstName = scanner.nextLine().toLowerCase();
		Print.lasttNameHeader();
    	String lastName = scanner.nextLine().toLowerCase();
		Print.phoneHeader();
    	String phone = scanner.nextLine();
		Print.DOBHeader();
    	String DOBstring[] = scanner.nextLine().split("/");
		Print.yellowhorizontalLine();
		String accountType = checkingsOrSavingsRequest();
		// create DOB Date object
		Date DOB = new Date(Integer.valueOf(DOBstring[0]), Integer.valueOf(DOBstring[1]), Integer.valueOf(DOBstring[2]));
		// create Person Object with the current info
		Person person = new Person(firstName, lastName, phone, DOB);
		// create customerID with this info 
		String customerID = createCustomerID(firstName,lastName, DOB);
		// Now create new Customer Object (Person, todays date, new customer ID)
		Customer customer = new Customer(person, Date.dateInitializer(), customerID);
		// Open an account
		if (accountType.equals("c")){
			Print.yellowhorizontalLine();
			double deposit = depositRequest();
			Print.yellowhorizontalLine();
			double overDraftLimit = setOverDraftLimit();
			CheckingAccount checkingAccount =  new CheckingAccount(createAccountNumber(), customer, manager.findEmployee(String.valueOf(manager.getCurrentLoginID())), Date.dateInitializer(), overDraftLimit);
			checkingAccount.deposit(deposit);
			this.manager.addAccount(checkingAccount);
			this.manager.saveAccount("source/accounts.txt", checkingAccount);
			this.currentAccount = checkingAccount;
		}
		if (accountType.equals("s")){
			Print.yellowhorizontalLine();
			double deposit = depositRequest();
			Print.yellowhorizontalLine();
			double interestRate = setInterestRate();
			SavingsAccount savingsAccount =  new SavingsAccount(createAccountNumber(), customer, manager.findEmployee(String.valueOf(manager.getCurrentLoginID())), Date.dateInitializer(), interestRate);
			savingsAccount.deposit(deposit);
			this.manager.addAccount(savingsAccount);
			this.manager.saveAccount("source/accounts.txt", savingsAccount);
			this.currentAccount = savingsAccount;
		}	
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
		Random rand = new Random();
		int randInt = rand.nextInt(0,1000);
		int dateCode = Date.dateInitializer().getYear();
		int accntnum = randInt + dateCode;
		return accntnum;
	}

	private void MainMenuLogin(){
		Print.loginHeader();
		// employee login
		// search for the employee with the given ID
		String employeeID = scanner.nextLine();
		Employee employee = manager.findEmployee(employeeID);
		while (employee == null){
			// if invalid, re-request until valid
			Print.clearSequence();
			Print.loginHeader();
			employeeID = scanner.nextLine();
			employee = manager.findEmployee(employeeID);
		}
		this.loggedIn = true;
		// set the current login ID to this employee (used to track / associate a manager with new accounts)
		this.manager.setCurrentLoginID(employee);
		Print. greenhorizontalLine();
    }

	private void displayAccounts(){
		Print.customerIDHeader();
		Print.yellowhorizontalLine();
		for (Account account : this.manager.getAccounts()){
			// only show accounts associatd to the manager logged in
			if (account.getManager().equals(manager.bankEmployeeMap.get(String.valueOf(manager.getCurrentLoginID())))){
				System.out.printf("%22s", space);
				System.out.printf("%-35s", account.getOwner().getID());
				double accountNumString =  account.getAccountNumber();
				System.out.printf("%.0f%n", accountNumString);
			}
		}
		Print.yellowhorizontalLine();
	}

	private String checkingsOrSavingsRequest(){
		Print.accountTypeHeader();
		Print.yellowhorizontalLine();
		Print.checkingOrSavingsSelectPrint();
		String accountType = scanner.nextLine().toLowerCase();
		ArrayList<String> options = new ArrayList<>();
		options.add("c");
		options.add("s");
		while (! options.contains(accountType)){
			Print.clearSequence();
			Print.MainMenu(manager);
			Print.displayCustomerInfo(this.currentAccount);
			Print.bluehorizontalLine();
			Print.yellowhorizontalLine();
			Print.checkingOrSavingsSelectPrint();
			accountType = scanner.nextLine().toLowerCase();
		}
		return accountType;
	}

	private double depositRequest(){
		Print.depositRequestHeader();
		String deposit = scanner.nextLine();
		double depsositDouble;
		while (true){
			try {
				depsositDouble = Double.parseDouble(deposit);
				break;
			} catch (Exception e) {
				Print.clearSequence();
				Print.MainMenu(manager);
				Print.displayCustomerInfo(this.currentAccount);
				Print.bluehorizontalLine();
				Print.yellowhorizontalLine();
				Print.checkingOrSavingsSelectPrint();
				Print.yellowhorizontalLine();
				Print.depositRequestHeader();
				deposit = scanner.nextLine();
			}
		}
		return depsositDouble;
	}

	private double setOverDraftLimit(){
			Print.OverDraftHeader();
			String overDraftLimit = scanner.nextLine();
		while (true){
			try {
				Double overDraftLimitDouble = Double.parseDouble(overDraftLimit);
				return overDraftLimitDouble;
			} catch (Exception e) {
				Print.clearSequence();
				Print.MainMenu(manager);
				Print.displayCustomerInfo(this.currentAccount);
				Print.bluehorizontalLine();
				Print.yellowhorizontalLine();
				Print.checkingOrSavingsSelectPrint();
				Print.yellowhorizontalLine();
				Print.OverDraftHeader();
				overDraftLimit = scanner.nextLine();
			}
		}
	}

	private double setInterestRate(){
		Print.interestRateHeader();
		String interestRate = scanner.nextLine();
	while (true){
		try {
			Double interestRateDouble = Double.parseDouble(interestRate);
			// return a percentage
			return interestRateDouble / 100;
		} catch (Exception e) {
			Print.clearSequence();
			Print.MainMenu(manager);
			Print.displayCustomerInfo(this.currentAccount);
			Print.bluehorizontalLine();
			Print.yellowhorizontalLine();
			Print.checkingOrSavingsSelectPrint();
			Print.yellowhorizontalLine();
			Print.interestRateHeader();
			interestRate = scanner.nextLine();
			}
		}
	}

	private Account findAccount(){
		Print.clearSequence();
		Print.MainMenu(manager);
		displayAccounts();
		Print.accountInfoRetrieveHeader();
    	String accntNum = scanner.nextLine();
		// get customer account object from ID
		while (true){
			try {
				Account retrieved_customer = this.manager.findAccount(accntNum);
				return retrieved_customer;
			} catch (Exception e) {
				Print.clearSequence();
				Print.MainMenu(manager);
				displayAccounts();
				Print.accountInfoRetrieveHeader();
				accntNum = scanner.nextLine();
			}
		}
	}
}

// Gabriel Malone / CSCI165 / Week 12 / Summer 2024
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank {
 
    private static Scanner scanner = new Scanner(System.in);
    private AccountManager manager = new AccountManager();
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
			// await user input
			String mainMenuSelection = scanner.nextLine().toLowerCase();
			if (mainMenuSelection.equals("a")){
				// add a customer
				openAccount();
			}
			if (mainMenuSelection.equals("s")){
				// find a customer
				this.currentAccount = findAccount();
				Print.displayCustomerInfo(this.currentAccount);
				Print.customerMenuOptions();
				// await user input
				String CustomerSelection = scanner.nextLine().toLowerCase();
				if (CustomerSelection.equals("u")){
					Print.updateAccountHeader();
					Print.updateMenuOptions(currentAccount);
					String updateSelection = scanner.nextLine().toLowerCase();
					if (updateSelection.equals("d")){
						Print.despoitAccountHeader();
						double deposit = dwRequest();
						// update object state
						currentAccount.deposit(deposit);
						// remove old info from text file // arrays
						manager.deleteAccount(currentAccount);
						// replace new info in maps/arrays
						manager.addAccount(currentAccount);
						// save new info to text file
						manager.saveAccount("source/accounts.txt", currentAccount);
						Print.clearSequence();
						Print.MainMenu(manager);
						displayAccounts();
						Print.accountInfoRetrieveHeader();
						Print.displayCustomerInfo(this.currentAccount);
						Print.customerMenuOptions();
						updateSelection = scanner.nextLine().toLowerCase();
					}
					if (updateSelection.equals("w")){
						Print.withdrawAccountHeader();
						double withdrawl = dwRequest();
						// update object state
						currentAccount.withdraw(withdrawl);
						// remove old info from text file // arrays
						manager.deleteAccount(currentAccount);
						// replace new info in maps/arrays
						manager.addAccount(currentAccount);
						// save new info to text file
						manager.saveAccount("source/accounts.txt", currentAccount);
						Print.clearSequence();
						Print.MainMenu(manager);
						displayAccounts();
						Print.accountInfoRetrieveHeader();
						Print.displayCustomerInfo(this.currentAccount);
						Print.customerMenuOptions();
						updateSelection = scanner.nextLine().toLowerCase();
					}
				}
				if (CustomerSelection.equals("c")){
					// delete an account
					manager.deleteAccount(this.currentAccount);
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
			double deposit = dwRequest();
			Print.yellowhorizontalLine();
			double overDraftLimit = setOverDraftLimit();
			CheckingAccount checkingAccount =  new CheckingAccount(createAccountNumber(), customer, manager.findEmployee(String.valueOf(manager.getCurrentLoginID())), Date.dateInitializer(), overDraftLimit);
			checkingAccount.deposit(deposit);
			this.manager.addAccount(checkingAccount);
			this.manager.saveAccount("source/accounts.txt", checkingAccount);
			this.currentAccount = checkingAccount;
			this.currentAccount.setType(Account.TYPE.CHECKING);
	
		}
		if (accountType.equals("s")){
			Print.yellowhorizontalLine();
			double deposit = dwRequest();
			Print.yellowhorizontalLine();
			double interestRate = setInterestRate();
			SavingsAccount savingsAccount =  new SavingsAccount(createAccountNumber(), customer, manager.findEmployee(String.valueOf(manager.getCurrentLoginID())), Date.dateInitializer(), interestRate);
			savingsAccount.deposit(deposit);
			this.manager.addAccount(savingsAccount);
			this.manager.saveAccount("source/accounts.txt", savingsAccount);
			this.currentAccount = savingsAccount;
			this.currentAccount.setType(Account.TYPE.SAVINGS);
		
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
				Print.displayINFO(account);
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

	private double dwRequest(){
		Print.depositRequestHeader();
		String sum = scanner.nextLine();
		double sumdouble;
		while (true){
			try {
				sumdouble = Double.parseDouble(sum);
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
				sum = scanner.nextLine();
			}
		}
		return sumdouble;
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

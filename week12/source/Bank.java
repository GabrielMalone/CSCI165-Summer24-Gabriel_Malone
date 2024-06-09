// Gabriel Malone / CSCI165 / Week 12 / Summer 2024
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
		GUIloop();
	}	

	/**
	 * Mehthod to update all accounts at once
	 * without using type checks
	 */
	public void updateAccounts(){
		// only update accounts associate with the logged in managers
		for (Account account : this.manager.getAccounts()){
			if (account.getManager().equals(manager.bankEmployeeMap.get(String.valueOf(manager.getCurrentLoginID())))){
				if (account.getBalance() < 0){
					emailer(account);
				}
				// if interest bearing, pay interest
				account.addInterest();
				manager.deleteAccount(account);
				// replace new info in maps/arrays
				manager.addAccount(account);
				// save new info to text file
				manager.saveAccount("source/accounts.txt", account);
			}
		}
	}

	private void emailer(Account account){
		try{
			Print.emailString(account);
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(Print.emailFileString(account)), false));
			writer.print(Print.emailString(account));
			writer.close();
		}catch(IOException ioe){
			System.out.print("Could not write to file");
			System.exit(0);
		}	
	}

	/**
	 * Method to pay divends to each account 
	 */
	public void payDividends(){
		double dividentRate = 0.025;
		// only pay dividends to accounts under the logged in manager
		for (Account account : this.manager.getAccounts()){
			if (account.getManager().equals(manager.bankEmployeeMap.get(String.valueOf(manager.getCurrentLoginID())))){
				// calculate the gains
				double current_balance 	= account.getBalance();
				double divident_gains 	= current_balance * dividentRate;
				// deposit the gains
				account.deposit(divident_gains); 
				// delete old account info
				manager.deleteAccount(account);
				// replace new info in maps/arrays
				manager.addAccount(account);
				// save new info to text file
				manager.saveAccount("source/accounts.txt", account);
			}
		}
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
		}
		if (accountType.equals("s")){
			Print.yellowhorizontalLine();
			double deposit = dwRequest();
			Print.yellowhorizontalLine();
			double interestRate = setInterestRate();
			SavingsAccount savingsAccount =  new SavingsAccount(createAccountNumber(), customer, manager.findEmployee(String.valueOf(manager.getCurrentLoginID())), Date.dateInitializer(), interestRate);
			savingsAccount.setInterestBearing(true);
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

	private void GUIloop(){
		ArrayList<String> managerSelectionOptions = new ArrayList<>();
		managerSelectionOptions.add("c");
		managerSelectionOptions.add("u");
		managerSelectionOptions.add("m");
		// 0 to escape any menu
		managerSelectionOptions.add("0");
		ArrayList<String> updateSelectionOptions = new ArrayList<>();
		updateSelectionOptions.add("d");
		updateSelectionOptions.add("w");
		updateSelectionOptions.add("s");
		updateSelectionOptions.add("t");
		updateSelectionOptions.add("0");
		ArrayList<String> updateAllSelectionOptions = new ArrayList<>();
		updateAllSelectionOptions.add("d");
		updateAllSelectionOptions.add("u");
		updateAllSelectionOptions.add("0");
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
				Print.clearSequence();
				Print.MainMenu(manager);
				displayAccounts();
				Print.accountInfoRetrieveHeader();
				this.currentAccount = findAccount();
				Print.displayCustomerInfo(this.currentAccount);
				Print.customerMenuOptions();
				// await user input
				String managerSelection = scanner.nextLine().toLowerCase();
				while (! managerSelectionOptions.contains(managerSelection)){
					Print.clearSequence();
					Print.MainMenu(manager);
					displayAccounts();
					Print.accountInfoRetrieveHeader();
					Print.displayCustomerInfo(this.currentAccount);
					Print.customerMenuOptions();
					managerSelection = scanner.nextLine().toLowerCase();
				}
				// m to return to main menu - update account loop
				while (! managerSelection.equals("m") || ! managerSelection.equals("0")){
					Print.clearSequence();
					Print.MainMenu(manager);
					displayAccounts();
					Print.accountInfoRetrieveHeader();
					Print.displayCustomerInfo(this.currentAccount);
					if (managerSelection.equals("u")){
						Print.updateAccountHeader();
						Print.updateMenuOptions(currentAccount);
						String updateSelection = scanner.nextLine().toLowerCase();
						while (! updateSelectionOptions.contains(updateSelection)){
							Print.clearSequence();
							Print.MainMenu(manager);
							displayAccounts();
							Print.accountInfoRetrieveHeader();
							Print.displayCustomerInfo(this.currentAccount);
							Print.updateAccountHeader();
							Print.updateMenuOptions(currentAccount);
							updateSelection = scanner.nextLine().toLowerCase();
						}
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
							Print.updateAccountFooter();
							Print.customerMenuOptions();
							managerSelection = scanner.nextLine().toLowerCase();
							while (! managerSelectionOptions.contains(managerSelection)){
								Print.clearSequence();
								Print.MainMenu(manager);
								displayAccounts();
								Print.accountInfoRetrieveHeader();
								Print.displayCustomerInfo(this.currentAccount);
								Print.updateAccountFooter();
								Print.customerMenuOptions();
								managerSelection = scanner.nextLine().toLowerCase();
							}
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
							Print.updateAccountFooter();
							Print.customerMenuOptions();
							managerSelection = scanner.nextLine().toLowerCase();
							while (! managerSelectionOptions.contains(managerSelection)){
								Print.clearSequence();
								Print.MainMenu(manager);
								displayAccounts();
								Print.accountInfoRetrieveHeader();
								Print.displayCustomerInfo(this.currentAccount);
								Print.updateAccountHeader();
								Print.customerMenuOptions();
								managerSelection = scanner.nextLine().toLowerCase();
							}
						}
						if (updateSelection.equals("t")){
							// transfer money sequence
							Print.transferAccountHeader();
							// get amount to transfer
							double withdrawl = dwRequest();
							// find another account
							Print.accountInfoRetrieveHeader();
							Account otherAccount = findAccount();
							// transfer money if the other account is valid
							if (this.currentAccount.transferTo(otherAccount, withdrawl)){
								currentAccount.withdraw(withdrawl);
								otherAccount.deposit(withdrawl);
								// remove old info from text file // arrays
								manager.deleteAccount(currentAccount);
								manager.deleteAccount(otherAccount);
								// replace new info in maps/arrays
								manager.addAccount(currentAccount);
								manager.addAccount(otherAccount);
								// save new info to text file
								manager.saveAccount("source/accounts.txt", currentAccount);
								manager.saveAccount("source/accounts.txt", otherAccount);
								Print.clearSequence();
								Print.MainMenu(manager);
								displayAccounts();
								Print.accountInfoRetrieveHeader();
								Print.displayCustomerInfo(this.currentAccount);
								Print.updateAccountFooter();
								Print.customerMenuOptions();		
								managerSelection = scanner.nextLine().toLowerCase();
								while (! managerSelectionOptions.contains(managerSelection)){
									Print.clearSequence();
									Print.MainMenu(manager);
									displayAccounts();
									Print.accountInfoRetrieveHeader();
									Print.displayCustomerInfo(this.currentAccount);
									Print.updateAccountHeader();
									Print.customerMenuOptions();
									managerSelection = scanner.nextLine().toLowerCase();
								}
							}
							else{
								Print.clearSequence();
								Print.MainMenu(manager);
								displayAccounts();
								Print.accountInfoRetrieveHeader();
								Print.displayCustomerInfo(this.currentAccount);
								Print.updateAccountInvalidFooter();
								Print.customerMenuOptions();
								managerSelection = scanner.nextLine().toLowerCase();
								while (! managerSelectionOptions.contains(managerSelection)){
									Print.clearSequence();
									Print.MainMenu(manager);
									displayAccounts();
									Print.accountInfoRetrieveHeader();
									Print.displayCustomerInfo(this.currentAccount);
									Print.updateAccountHeader();
									Print.customerMenuOptions();
									managerSelection = scanner.nextLine().toLowerCase();
								}
							}
						}
						// change interest rates or overdraft limits on an account
						if (updateSelection.equals("s")){
							// rates for savings
							if (currentAccount.getClass() == SavingsAccount.class){
								// downcast to get interest rate set method
								SavingsAccount current_savings = (SavingsAccount) currentAccount;
								Print.rateAccountHeader();
								double interestRate = setInterestRate();
								// update object state
								current_savings.setInterest(interestRate);
								// remove old info from text file // arrays
								manager.deleteAccount(current_savings);
								// replace new info in maps/arrays
								manager.addAccount(current_savings);
								// save new info to text file
								manager.saveAccount("source/accounts.txt", current_savings);
							}
							// limits for checking
							if (currentAccount.getClass() == CheckingAccount.class){
								// downcast to get interest rate set method
								CheckingAccount current_checking = (CheckingAccount) currentAccount;
								Print.limitAccountHeader();
								double overDraftLimit = setOverDraftLimit();
								// update object state
								current_checking.setOverdraftLimit(overDraftLimit);
								// remove old info from text file // arrays
								manager.deleteAccount(current_checking);
								// replace new info in maps/arrays
								manager.addAccount(current_checking);
								// save new info to text file
								manager.saveAccount("source/accounts.txt", current_checking);
							}
							Print.clearSequence();
							Print.MainMenu(manager);
							displayAccounts();
							Print.accountInfoRetrieveHeader();
							Print.displayCustomerInfo(this.currentAccount);
							Print.updateAccountFooter();
							Print.customerMenuOptions();
							managerSelection = scanner.nextLine().toLowerCase();
							while (! managerSelectionOptions.contains(managerSelection)){
								Print.clearSequence();
								Print.MainMenu(manager);
								displayAccounts();
								Print.accountInfoRetrieveHeader();
								Print.displayCustomerInfo(this.currentAccount);
								Print.updateAccountHeader();
								Print.customerMenuOptions();
								managerSelection = scanner.nextLine().toLowerCase();
						}
						}
					}
					if (managerSelection.equals("c")){
						// delete an account
						manager.deleteAccount(this.currentAccount);
						Print.clearSequence();
						Print.MainMenu(manager);
						displayAccounts();
						Print.accountInfoRetrieveHeader();
						Print.displayCustomerInfo(this.currentAccount);
						Print.deleteAccountFooter();
						Print.customerMenuOptionsAfterDelete();
						scanner.nextLine().toLowerCase();
						// give only option as returning to main meny (anykey)
						managerSelection = "m";
					}
					if (managerSelection.equals("m") || managerSelection.equals("0") ){
						break;
					}
				}
			}
			// return to main menu
			if (mainMenuSelection.equals("m")){
				Print.clearSequence();
				Print.MainMenu(manager);
				Print.mainMenuOptions();
			}
			// pay interest / email overdrafted accounts / pay dividends
			if (mainMenuSelection.equals("u")){	
				Print.updateAllAccountsHeader();
				Print.updateAllMenuOptions();
				String updateAllSelection = scanner.nextLine().toLowerCase();
				// stay in updat all menu until user states done with '0'
				while (! updateAllSelection.equals("0")){
					while (! updateAllSelectionOptions.contains(updateAllSelection)){
						Print.clearSequence();
						Print.MainMenu(manager);
						displayAccounts();
						Print.mainMenuOptions();
						System.out.println();
						Print.updateAllAccountsHeader();
						Print.updateAllMenuOptions();
						updateAllSelection = scanner.nextLine().toLowerCase();
					}
					// pay dividends
					if (updateAllSelection.equals("d")){
						payDividends();
						Print.clearSequence();
						Print.MainMenu(manager);
						displayAccounts();
						Print.mainMenuOptions();
						System.out.println();
						Print.updateAllAccountsHeader();
						Print.updateAllMenuOptions();
						updateAllSelection = scanner.nextLine().toLowerCase();
					}
					if (updateAllSelection.equals("u")){
						updateAccounts();
						Print.clearSequence();
						Print.MainMenu(manager);
						displayAccounts();
						Print.mainMenuOptions();
						System.out.println();
						Print.updateAllAccountsHeader();
						Print.updateAllMenuOptions();
						updateAllSelection = scanner.nextLine().toLowerCase();
					}
				}
			}
			Print.clearSequence();
		}
	}
}

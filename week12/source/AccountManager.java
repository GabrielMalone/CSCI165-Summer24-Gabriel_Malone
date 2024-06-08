// Gabriel Malone / CSCI65 / Week 12 / Summer 2024

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AccountManager {
	// customer vars
	private String firstName;
	private String lastName;
	private String phone;
	private String birthMonth;
	private String birthDay;
	private String birthYear;
	private String joinMonth;
	private String joinDay;
	private String joinYear;
	private String customerID;
	private String accountID;
	private String accountType;
	private String accntDateM;
	private String accntDateD;
	private String accntDateY;
	private String accountBal;
	private String limit_rate;
	// employee vars
	private String managerF;
	private String managerL;
	private String managerPhone;
	private String managerBirthM;
	private String managerBirthD;
	private String managerBirthY;
	private String managerHireM;
	private String managerHireD;
	private String managerHireY;
	private String managerID;
	private String managerDept;
	// total accounts at bank
	public int total_acnts;
	// load current accounts
	public ArrayList<Account> bankAccounts = new ArrayList<>();
	// load manager accounts
	public ArrayList<Employee> employeeAccounts = new ArrayList<>();
	// Employee ID to Employee Object
	public Map<String, Employee> bankEmployeeMap = new HashMap<>(); 
	// Customer ID and Account Object -- not very useful
	public Map<String, Account> bankAccountMap = new HashMap<>();
	// Account Num to Account Map
	public Map<Double, Account> accountNumToAccountMap = new HashMap<>();
	private int currentEmployeeLoggedInID; 

	/**
	 * Method to iterate through the array of accounts and save them to a text file
	 */
	public void saveAccount(String filename, Account account){     
		try{
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename), true));
			
			String accountString = "";
			this.firstName		= account.getOwner().getFirstName();
			accountString 		+= this.firstName + ",";

			this.lastName		= account.getOwner().getLastName();
			accountString 		+= this.lastName + ",";

			this.phone			= account.getOwner().rawPhoneNumber(account.getOwner().getPhoneNumber());
			accountString 		+= this.phone + ",";
			
			this.birthMonth		= String.valueOf(account.getOwner().getDOB().getMonth());
			accountString 		+= this.birthMonth + ",";

			this.birthDay		= String.valueOf(account.getOwner().getDOB().getDay());
			accountString 		+= this.birthDay + ",";

			this.birthYear		= String.valueOf(account.getOwner().getDOB().getYear());
			accountString 		+= this.birthYear + ",";

			this.joinMonth		= String.valueOf(account.getOwner().getDateJoined().getMonth());
			accountString 		+= this.joinMonth + ",";

			this.joinDay		= String.valueOf(account.getOwner().getDateJoined().getDay());
			accountString 		+= this.joinDay + ",";

			this.joinYear		= String.valueOf(account.getOwner().getDateJoined().getYear());
			accountString 		+= this.joinYear + ",";

			this.customerID		= account.getOwner().getID();
			accountString 		+= this.customerID + ",";

			this.accountID		= String.valueOf((int)account.getAccountNumber());
			accountString 		+= this.accountID + ",";

			if (account.getClass() == SavingsAccount.class){
				this.accountType = "savings";
				accountString += this.accountType + ",";
			}
			if (account.getClass() == CheckingAccount.class){
				this.accountType = "checking";
				accountString += this.accountType + ",";
			}
			if (account.getClass() == Account.class){
				this.accountType = " ";
				accountString += this.accountType + ",";
			}

			this.accntDateM		= String.valueOf(account.getDateCreated().getMonth());
			accountString 		+= this.accntDateM + ",";

			this.accntDateD		= String.valueOf(account.getDateCreated().getDay());
			accountString 		+= this.accntDateD + ",";

			this.accntDateY		= String.valueOf(account.getDateCreated().getYear());
			accountString 		+= this.accntDateY + ",";

			this.accountBal		= String.valueOf(account.getBalance());
			accountString 		+= this.accountBal + ",";

			if (account.getClass() == SavingsAccount.class){
				SavingsAccount savings_account = (SavingsAccount) account;
				this.limit_rate = String.valueOf(savings_account.getInterest());
				accountString 	+= this.limit_rate + ",";
			}
			if (account.getClass() == CheckingAccount.class) {
				CheckingAccount checking_account = (CheckingAccount) account;
				this.limit_rate = String.valueOf(checking_account.getOverdraftLimit());
				accountString 	+= this.limit_rate + ",";
			}
			if (account.getClass() == Account.class) {
				this.limit_rate = " ";
				accountString 	+= this.limit_rate + ",";
			}

			this.managerID		= String.valueOf(this.currentEmployeeLoggedInID);
			accountString 		+= this.managerID + ",";

			// need to fix it so that it only saves a customer if they aren't already present 
			// so that the correct manage is not overwritten
			writer.print(accountString + "\n");
		
			writer.close();
		}catch(IOException ioe){
			System.out.print("Could not write to file");
			System.exit(0);
		}	
	}

	public void loadManagers(String filename){
		try{
			// Create a File object
			File file = new File(filename);
			// Create a Scanner object to read the file
			Scanner scanner	= new Scanner(file);
			// Loop through the file
			while(scanner.hasNextLine()){
				// split line at commas into array
				String[] line = scanner.nextLine().split(",", 0);
				// pull out name, price, calories via indexing
				this.managerF  		= line[0];
				this.managerL   	= line[1];
				this.managerPhone	= line[2];
				this.managerBirthM 	= line[3];
				this.managerBirthD	= line[4];
				this.managerBirthY  = line[5];
				this.managerHireM  	= line[6];
				this.managerHireD   = line[7];
				this.managerHireY   = line[8];
				this.managerID 		= line[9];
				this.managerDept 	= line[10];

				Employee employee = createEmployee();
				// add it to the ArrayList
				this.employeeAccounts.add(employee);
				this.bankEmployeeMap.put(this.managerID, employee);
			}
			scanner.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found: " + filename);
		}
	}

	/**
	* Method to load text info of accounts from a file
	*
	* @param filename (String) contains account infos
	*/
	public void loadAccounts(String filename){
		try{
			// Create a File object
			File file = new File(filename);
			// Create a Scanner object to read the file
			Scanner scanner	= new Scanner(file);
			// Loop through the file
			while(scanner.hasNextLine()){
				// split line at commas into array
				String[] line = scanner.nextLine().split(",", 0);
				// pull out name, price, calories via indexing
				this.firstName  	= line[0];
				this.lastName   	= line[1];
				this.phone	  		= line[2];
				this.birthMonth 	= line[3];
				this.birthDay	  	= line[4];
				this.birthYear  	= line[5];
				this.joinMonth  	= line[6];
				this.joinDay    	= line[7];
				this.joinYear   	= line[8];
				this.customerID 	= line[9];
				this.accountID 		= line[10];
				this.accountType	= line[11];
				this.accntDateM  	= line[12];
				this.accntDateD  	= line[13];
				this.accntDateY 	= line[14];
				this.accountBal 	= line[15];
				this.limit_rate   	= line[16];
				this.managerID 		= line[17];
				
				Account account = createAccount();
				// need to connect all accounts associated with one customer
				this.accountNumToAccountMap.put(Double.valueOf(this.accountID), account);
				this.bankAccountMap.put(this.customerID, account);
				this.bankAccounts.add(account);
				// add to map for searching function
				
				this.total_acnts ++;
			}
			scanner.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found: " + filename);
		}
	}
	
	/**
	 * 
	 * @returns an instance of the appropriate account type from the data in a text file
	 */
	public Account createAccount(){
		// set manager to employee at terminal when account created
		Employee employee = findEmployee(String.valueOf(this.managerID));
		// create person's DOB object
		Date DOB = new Date(Integer.valueOf(this.birthMonth), Integer.valueOf(this.birthDay), Integer.valueOf(this.birthYear));
		// create person's join date object
		Date joinDate = new Date(Integer.valueOf(this.joinMonth), Integer.valueOf(this.joinDay), Integer.valueOf(this.joinYear));
		//create person's accountDate object
		Date accDate = new Date(Integer.valueOf(this.accntDateM), Integer.valueOf(this.accntDateD), Integer.valueOf(this.accntDateY));
		//convert account ID to int
		int actID = Integer.valueOf(this.accountID);
		//convert account bal to double
		double balance = Double.valueOf(this.accountBal);
		if (this.accountType.equals("checking")){
			//convert account rate/limit to double
			double rate_l = Double.valueOf(this.limit_rate);
			// create filled out checking account object
			CheckingAccount checkingAccount = new CheckingAccount(actID, new Customer( new Person(this.firstName, this.lastName, this.phone, DOB), joinDate, this.customerID),accDate, balance, rate_l);
			checkingAccount.setManager(employee);
			checkingAccount.setType(Account.TYPE.CHECKING);
			return checkingAccount;
		}
		if (this.accountType.equals("savings")){
			double rate_l = Double.valueOf(this.limit_rate);
			// create filled out checking account object
			SavingsAccount savingsAccount = new SavingsAccount(actID, new Customer( new Person(this.firstName, this.lastName, this.phone, DOB), joinDate, this.customerID), accDate, balance, rate_l);
			savingsAccount.setManager(employee);
			savingsAccount.setType(Account.TYPE.SAVINGS);
			return savingsAccount;
		}
		if (this.accountType.equals(" ")){
			// create filled out checking account object
			Account account = new Account (actID, new Customer( new Person(this.firstName, this.lastName, this.phone, DOB), joinDate, this.customerID), accDate, balance);
			account.setManager(employee);
			return account;
		}
		Account acnt = new Account(actID, new Customer( new Person(this.firstName, this.lastName, this.phone, DOB), joinDate, this.customerID), accDate, balance);
		acnt.setManager(employee);
		return acnt;
	}

	/**
	 * 
	 * @return an employee object with the variable states provided by the text file
	 */
	public Employee createEmployee(){
		// create a new employee from the data in the text file
		Employee employee = new Employee(new Person(this.managerF, this.managerL, this.managerPhone, new Date(Integer.valueOf(this.managerBirthM),Integer.valueOf(this.managerBirthD),Integer.valueOf(this.managerBirthY))), new Date(Integer.valueOf(this.managerHireM), Integer.valueOf(this.managerHireD), Integer.valueOf(this.managerHireY)), Integer.valueOf(this.managerID), this.managerDept);
		return employee;
	}

	/**
	 * 
	 * @returns a copy of the accounts on file
	 */
	public ArrayList<Account> getAccounts(){
		ArrayList<Account> accountsCopy = new ArrayList<>();
		for (Account account : this.bankAccounts){
			accountsCopy.add(account);
		}
		return accountsCopy;
	}

	/**
	 * Method to add accounts to the manager's acccount array
	 * @param account
	 */
	public void addAccount(Account account){
		this.bankAccounts.add(account);
		this.bankAccountMap.put(account.getOwner().getID(), account);
		this.accountNumToAccountMap.put(account.getAccountNumber(), account);
		this.total_acnts ++;
	}

	/**
	 * Method to get the customer via their ID from the bank accounts map
	 * @param accntNum
	 * @return
	 */
	public Account findAccount(String accntNum){
		double accntNumInt = Double.valueOf(accntNum);
		Account foundAccount = this.accountNumToAccountMap.get(accntNumInt);
		// privacy protection
		if (foundAccount.getClass() == SavingsAccount.class){
			// downcast
			SavingsAccount found_savings_account = (SavingsAccount) foundAccount;
			found_savings_account = new SavingsAccount(found_savings_account);
			return found_savings_account;
		}
		if (foundAccount.getClass() == CheckingAccount.class){
			// downcast
			CheckingAccount found_checking_account = (CheckingAccount) foundAccount;
			found_checking_account = new CheckingAccount(found_checking_account);
			return found_checking_account;
		}
		else{
			return foundAccount;
		}
	}

	/**
	 * Method to get an employee via their ID from the employee map
	 * @param employeeID
	 * @return
	 */
	public Employee findEmployee(String employeeID){
		Employee foundEmployee = this.bankEmployeeMap.get(employeeID);
		return foundEmployee;
	}

	public void setCurrentLoginID(Employee employee){
		this.currentEmployeeLoggedInID = employee.getId();
	}

	public int getCurrentLoginID(){
		return this.currentEmployeeLoggedInID;
	}
}

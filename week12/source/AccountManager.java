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
	//private String managerF;
	//private String managerL;
	public int total_acnts;

	// load current accounts
	public ArrayList<Account> bankAccounts = new ArrayList<>();
	// Map of customer ID and their account for searching purposes
	public Map<String, Account> bankAccountMap = new HashMap<String, Account>(10); 

	/**
	 * Method to iterate through the array of accounts and save them to a text file
	 */
	public void saveAccounts(String filename){     
		try{
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename)));
			for (Account account : bankAccounts){

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

				if (account.getClass().toString().equals("class SavingsAccount")){
					this.accountType = "savings";
					accountString += this.accountType + ",";
				}
				else{
					this.accountType = "checking";
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
					accountString 	+= this.limit_rate;
				}
				else {
					CheckingAccount checking_account = (CheckingAccount) account;
					this.limit_rate = String.valueOf(checking_account.getOverdraftLimit());
					accountString 	+= this.limit_rate;
				}
				writer.print(accountString);
			} 	
			writer.close();
		}catch(IOException ioe){
			System.out.print("Could not write to file");
			System.exit(0);
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
				//this.managerF 	= line[17];
				//this.managerL   	= line[18];
				Account account = createAccount();
				// add it to the ArrayList
				this.bankAccounts.add(account);
				// add to map for searching function
				this.bankAccountMap.put(customerID, account);
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
		//convert account rate/limit to double
		double rate_l = Double.valueOf(this.limit_rate);
		if (this.accountType.equals("checking")){
			// create filled out checking account object
			CheckingAccount checkingAccount = new CheckingAccount(actID, new Customer( new Person(this.firstName, this.lastName, this.phone, DOB), joinDate, this.customerID), accDate, balance, rate_l);
			return checkingAccount;
		}
		if (this.accountType.equals("savings")){
			// create filled out checking account object
			SavingsAccount savingsAccount = new SavingsAccount(actID, new Customer( new Person(this.firstName, this.lastName, this.phone, DOB), joinDate, this.customerID), accDate, balance, rate_l);
			return savingsAccount;
		}
		Account acnt = new Account(actID, new Customer( new Person(this.firstName, this.lastName, this.phone, DOB), joinDate, this.customerID), accDate, balance);
		return acnt;
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
		this.total_acnts ++;
	}

	/**
	 * Method to get the customer via their ID from the bank accounts map
	 * @param customerID
	 * @return
	 */
	public Account findAccount(String customerID){
		Account foundAccount = this.bankAccountMap.get(customerID);
		// privacy protection
		if (foundAccount.getClass() == SavingsAccount.class){
			// downcast
			SavingsAccount found_savings_account = (SavingsAccount) foundAccount;
			found_savings_account = new SavingsAccount(found_savings_account);
			return found_savings_account;
		}
		else {
			// downcast
			CheckingAccount found_checking_account = (CheckingAccount) foundAccount;
			found_checking_account = new CheckingAccount(found_checking_account);
			return found_checking_account;
		}
	}

}

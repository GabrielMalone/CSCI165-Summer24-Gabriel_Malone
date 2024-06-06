// Gabriel Malone / CSCI65 / Week 12 / Summer 2024

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class LoadAccounts {

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

	// load current accounts
	private ArrayList<Account> bankAccounts = new ArrayList<>();

	
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
				this.accountType	= line[10];
				this.accountID 		= line[11];
				this.accntDateM  	= line[12];
				this.accntDateD  	= line[13];
				this.accntDateY 	= line[14];
				this.accountBal 	= line[15];
				this.limit_rate   	= line[16];
				//this.managerF 	= line[17];
				//this.managerL   	= line[18];

				Account account = createAccount();
				// 	add it to the ArrayList
				this.bankAccounts.add(account);
			}
			scanner.close();
		}
		// Catch the FileNotFoundException
		catch(FileNotFoundException e){
			// Print an error message
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
		for (Account account : bankAccounts){
			accountsCopy.add(account);
		}
		return accountsCopy;
	}

}

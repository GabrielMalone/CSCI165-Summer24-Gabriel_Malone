/*
	File:	Account.java
	Author:	Ken Whitener
	Date:	4/15/2024

 	A class for bank accounts.
	This class provides the basic functionality of accounts.
	It allows deposits and withdrawals but not overdraft limits or interest rates.
*/

public class Account {


	private Customer	owner;			// the owner of the account
	private Employee	accountManager;	// the manager of the account
	private Date		dateCreated; 	// date the account was created
	private int 		accountNumber;	// The account number
    private double 		balance;  		// The current balance
	private double 		interest;
	private boolean		interestBearing;
	

	/**
	 * Overloaded constructor
	 * @param accountNumber
	 * @param owner
	 * @param created
	 * @param balance
	 */
	public Account(int accountNumber, Customer owner, Date created, double balance){
		this.accountNumber 	= accountNumber;
		this.owner			= new Customer(owner);
		this.dateCreated	= new Date(created);
		this.balance		= balance;
		// default manager set
		this.accountManager = new Employee();
	}

	/**
	 * Overloaded constructor
	 * @param accountNumber
	 * @param owner
	 * @param manager
	 * @param created
	 */
	public Account(int accountNumber, Customer owner, Employee manager, Date created){
		this.accountNumber 	= accountNumber;
		this.owner			= new Customer(owner);
		setDateCreated(created);
		this.accountManager = new Employee(manager);
	}

	/**
	 * Copy constructor
	 * @param toCopy
	 */
	public Account (Account toCopy){
		this.owner = toCopy.getOwner();
		this.accountManager = toCopy.getManager();
		this.dateCreated = toCopy.getDateCreated();
		this.accountNumber = toCopy.accountNumber;
		this.balance = toCopy.balance;
	}

	/**
	 * Method to set an account to be interest bearing
	 * @param interestBearing
	 */
	public void setInterestBearing(boolean interestBearing) {
		this.interestBearing = interestBearing;
	}

	/**
	 * Method to see if an account is interest bearing
	 * @return boolean
	 */
	public boolean getInterestBearing() {
		return this.interestBearing;
	}


	/**
	 * 
	 * @param sum
	 */
	public void deposit(double sum){
		if (sum > 0) 	this.balance += sum;
		else 			System.err.println("Account.deposit(...): cannot deposit negative amount.");    
	}

	/**
	 * 
	 * @param sum
	 */
	public void withdraw(double sum){
		if (sum > 0) 	this.balance -= sum;
		else			System.err.println("Account.withdraw(...): cannot withdraw negative amount.");
	}

	public boolean transferTo(Account otherAccount, double amount){
		// added on more check so cant send money to self (was causing a weird bug)
		if(this.owner.equals(otherAccount.owner) && amount < this.balance && this.getAccountNumber() != otherAccount.getAccountNumber()){
			return true;
		}
		else return false;	
	}

	/**
	 * 
	 * @return the current balance
	 */
	public double getBalance(){
		return this.balance;
	}

	/**
	 * 
	 * @return the account number
	 */
	public double getAccountNumber(){
		return this.accountNumber;
	}

	/**
	 * 
	 * @param manager
	 */
	public void setManager(Employee manager){
		this.accountManager = new Employee(manager);	// privacy protection
	}

	/**
	 * 
	 * @return The account manager for this account
	 */
	public Employee getManager(){
		return new Employee(this.accountManager);		// privacy protection
	}

	/**
	 * 
	 * @return The date the account was created
	 */
	public Date getDateCreated(){	
		return new Date(dateCreated);					// privacy protection
	}

	/**
	 * Method to check that the account creation date makes logical sense
	 */
	public void setDateCreated(Date createdDate){
		if (createdDate.equals(getOwner().getDateJoined()) 
			||  createdDate.compareTo(getOwner().getDateJoined()) == -1)
			// if date created before date joined, set date created to date joined
			this.dateCreated = getOwner().getDateJoined();
		else
			// privacy protection 
			this.dateCreated = new Date(createdDate);
	}

	/**
	 * 
	 * @return The account owner
	 */
	public Customer getOwner(){
		return new Customer(this.owner);				// privacy protection
	}

	/**
	 * Change the owner of this account with new Customer object
	 * @param newOwner
	 */
	public void setOwner(Customer newOwner){
		this.owner = newOwner;
	}

	/**
	 * Change the owner of this account with new Customer object
	 * @param newOwner
	 */
	public void setOwner(Person newPerson, Date DOB, String custID){
		this.owner = new Customer(newPerson, DOB, custID);
	}


	/**
	 * Method to add interest to this account if it is an interest bearing account
	 */
	public void addInterest(){
		if (this.interestBearing){
			double interestEarned = 0;
			interestEarned = this.balance * this.interest;
			this.balance += interestEarned;
		}
	}

	/**
	 * Method to set the interest rate for this account
	 * if it is an interest bearing account
	 * @param interest
	 */
	public void setInterest(double interest) {
		if (this.interestBearing){
			this.interest = interest;
		}
	}

	@Override
	public String toString() {
		String space = " ";
		return 	"\n" + Colors.ANSI_CYAN + space.repeat(21)	+ "Account Number: "				+ Colors.ANSI_RESET+ accountNumber 										+ 
				"\n" + Colors.ANSI_CYAN + space.repeat(21) 	+ "Opened: "						+ Colors.ANSI_RESET	+ dateCreated 										+	
				"\n" + space.repeat(21) + Colors.ANSI_CYAN 	+ "Owner: "							+ Colors.ANSI_RESET	+ owner + Colors.ANSI_RESET							+ 	
				"\n" + Colors.ANSI_CYAN + space.repeat(21) 	+ "Acnt Manager: " + accountManager + 
				"\n" + Colors.ANSI_RESET;				
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)					return true;	// identity check
		if (obj == null)					return false;	// null check
		if (getClass() != obj.getClass())	return false;	// origin check

		Account other = (Account) obj;						// down cast

		if (accountManager == null) {
			if (other.accountManager != null)
				return false;
		} else if (!accountManager.equals(other.accountManager))	// calls Employee.equals (composition)
			return false;
		if (accountNumber != other.accountNumber)					// check account number (primitive)
			return false;
		if (balance != other.balance)								// check balance (primitive)
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))						// calls Customer.equals (composition)
			return false;
		return true;
	}
}

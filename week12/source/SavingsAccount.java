// Gabriel Malone / CSCI165 / Week 12 / Summer 2024

public class SavingsAccount extends Account{
	
	private double interest;
	private double savaingsBalance;

	/**
	 * Overloaded constructor
	 * no mananager given
	 * @param accountNumber
	 * @param owner
	 * @param created
	 * @param savaingsBalance
	 * @param interest
	 */
	public SavingsAccount(int accountNumber, Customer owner, Date created, double savaingsBalance, double interest){
		super(accountNumber, owner, created, savaingsBalance);
		this.interest = interest;
		this.savaingsBalance = savaingsBalance;
	}

	/**
	 * Overloaded constructor
	 * @param accountNumber
	 * @param owner
	 * @param manager
	 * @param created
	 * @param interest
	 */ 
	public SavingsAccount(int accountNumber, Customer owner, Employee manager, Date created, double interest){
		super(accountNumber, owner, manager, created);
		this.interest = interest;
	
	}

	/**
	 * Copy constructor
	 * @param toCopy
	 */
	public SavingsAccount (SavingsAccount toCopy){
		super(toCopy);
		this.savaingsBalance = toCopy.getBalance();
		this.interest = toCopy.getInterest();
	}

	/**
	 * Method to return the interest rate of an account
	 * @return
	 */
	public double getInterest() {
		return this.interest;
	}

	/**
	 * Method to set the interest rate for this account
	 * @param interest
	 */
	public void setInterest(double interest) {
		this.interest = interest;
	}

	/**
	 * 
	 * @return the current balance
	 */
	@Override
	public double getBalance(){
		return this.savaingsBalance;
	}

	/**
	 * 
	 * @param sum
	 */
	@Override
	public void deposit(double sum){
		if (sum > 0) 	this.savaingsBalance += sum;
		else 			System.err.println("Account.deposit(...): cannot deposit negative amount.");    
	}


	/**
	 * Method to add interest to this account
	 */
	public void addInterest(){
		double interestEarned = 0;
		interestEarned = this.savaingsBalance * this.interest;
		this.savaingsBalance += interestEarned;
	}
	/**
	 * 
	 * @param sum
	 */
	@Override
	public void withdraw(double sum){
		// if withdrawing a positive value that does not result in a negative savaingsBalance
		// okay
		// needed to override to check for the negative savaingsBalance result
		if (sum > 0 && this.savaingsBalance - sum >= 0){ 	
			this.savaingsBalance -= sum;
		}
		// otherwise no action on account savaingsBalance
		else			
			System.err.println("Account.withdraw(...): cannot withdraw negative amount.");
	}

	@Override
	public String toString() {
		return 	super.toString()+ "\n" + getClass()  + "\nInterest Rate: " + getInterest() + "\nSavings Acnt savaingsBalance: " + this.savaingsBalance;
	}        

	/**
	 * Method to check if two accounts are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)					return true;	// identity check
		if (obj == null)					return false;	// null check
		if (getClass() != obj.getClass())	return false;	// origin check

		SavingsAccount other = (SavingsAccount) obj;			

		if (getManager() == null) {
			if (other.getManager() != null)
				return false;
		} 
		else if (!getManager().equals(other.getManager()))	
			return false;
		if (getAccountNumber() != other.getAccountNumber())			
			return false;
		if (this.savaingsBalance != other.getBalance())							
			return false;
		if (getOwner() == null) {
			if (other.getOwner() != null)
				return false;
		}
		if (getDateCreated() == null) {
			if (other.getDateCreated() != null)
				return false;
		}
		if (! getDateCreated().equals(other.getDateCreated()))
				return false;
		if (getInterest() != other.getInterest()) 
				return false;
		else if (!getOwner().equals(other.getOwner()))					
			return false;
		return true;
	}
}

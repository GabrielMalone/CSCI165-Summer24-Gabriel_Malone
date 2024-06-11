// Gabriel Malone / CSCI165 / Week 12 / Summer 2024

public class SavingsAccount extends Account{
	
	private double interest;
	private double savingsBalance;

	/**
	 * Overloaded constructor
	 * no mananager given
	 * @param accountNumber
	 * @param owner
	 * @param created
	 * @param savingsBalance
	 * @param interest
	 */
	public SavingsAccount(int accountNumber, Customer owner, Date created, double savingsBalance, double interest){
		super(accountNumber, owner, created, savingsBalance);
		this.interest = interest;
		this.savingsBalance = savingsBalance;
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
		this.savingsBalance = toCopy.getBalance();
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
		return this.savingsBalance;
	}

	/**
	 * 
	 * @param sum
	 */
	@Override
	public void deposit(double sum){
		if (sum > 0) 	this.savingsBalance += sum;
		else 			System.err.println("Account.deposit(...): cannot deposit negative amount.");    
	}


	/**
	 * Method to add interest to this account
	 */
	public void addInterest(){
		double interestEarned = 0;
		interestEarned = this.savingsBalance * this.interest;
		this.savingsBalance += interestEarned;
	}

	/**
	 * Method to add interest to this account if it is an interest bearing account
	 */	
	@Override
	public void updateAccount(){
		double interestEarned = 0;
		interestEarned = this.savingsBalance * this.interest;
		this.savingsBalance += interestEarned;
	}

	/**
	 * 
	 * @param sum
	 */
	@Override
	public void withdraw(double sum){
		// if withdrawing a positive value that does not result in a negative savingsBalance
		// okay
		// needed to override to check for the negative savingsBalance result
		if (sum > 0 && this.savingsBalance - sum >= 0){ 	
			this.savingsBalance -= sum;
		}
		// otherwise no action on account savingsBalance
		else			
			System.err.println("Account.withdraw(...): cannot withdraw negative amount.");
	}

	@Override
	public String toString() {
		String space = " ";
		return 	super.toString()
		+ "\n" + space.repeat(21) + Colors.ANSI_PURPLE + getClass() + Colors.ANSI_RESET 
		+ "\n" + space.repeat(21) + Colors.ANSI_CYAN + "Checking Accnt Num: " + Colors.ANSI_RESET  + (int)getAccountNumber() 
		+ "\n" + space.repeat(21) + Colors.ANSI_CYAN + "Interest rate: " + Colors.ANSI_RESET + Colors.ANSI_PURPLE + (getInterest() * 100) + "%" + Colors.ANSI_RESET
		+ "\n" + space.repeat(21) + Colors.ANSI_CYAN + "Checking Acnt Balance: " + Colors.ANSI_RESET +  Colors.ANSI_GREEN + Print.nf.format(getBalance()) + Colors.ANSI_RESET
		+ "\n" ;
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
		if (this.savingsBalance != other.getBalance())							
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

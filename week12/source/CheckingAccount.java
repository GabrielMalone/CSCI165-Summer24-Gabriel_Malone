// Gabriel Malone / CSCI165 / Week 12 / Summer 2024



public class CheckingAccount extends Account {
    
    private double  overdraftLimit;
    private boolean inOverdraft;
    private double  checkingBalance;

    /**
     * Overloaded constructor
     * no mananager given
     * @param accountNumber
     * @param owner
     * @param created
     * @param checkingBalance
     * @param interest
     */
	public CheckingAccount(int accountNumber, Customer owner, Date created, double checkingBalance, double overdraftLimit){
		super(accountNumber, owner, created, checkingBalance);
        this.overdraftLimit = overdraftLimit;
        this.checkingBalance = checkingBalance;

	}
    /**
     * Overloaded constructor
     * @param accountNumber
     * @param owner
     * @param manager
     * @param created
     * @param interest
     */ 
	public CheckingAccount(int accountNumber, Customer owner, Employee manager, Date created, double overdraftLimit){
		super(accountNumber, owner, manager, created);
        this.overdraftLimit = overdraftLimit;
	}

    /**
     * Copy constructor
     * @param toCopy
     */
    public CheckingAccount (CheckingAccount toCopy){
        super(toCopy);
        this.checkingBalance = toCopy.getBalance();
        this.overdraftLimit = toCopy.overdraftLimit;
    }

    /**
     * 
     * @return this account's overdraft limit
     */
    public double getOverdraftLimit(){
        return this.overdraftLimit;
    }

    /**
     * 
     * @return this accounts checking balance
     */
    @Override
    public double getBalance(){
        return this.checkingBalance;
    }

    /**
	 * 
	 * @param sum
	 */
    @Override
	public void deposit(double sum){
		if (sum > 0) 	this.checkingBalance += sum;
		else 			System.err.println("Account.deposit(...): cannot deposit negative amount.");    
	}

    @Override
    public void updateAccount(){
        if (this.checkingBalance < 0){
            Bank.emailer(this);
        }
    }

    /**
     * Method to set this account's overdraft limit
     * @param overdraftLimit
     */
    public void setOverdraftLimit(double overdraftLimit){
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * Method to check if the current checkingBalance is in overdraft
     * @param checkingBalance
     * @return
     */
    public boolean isInOverDraft(double checkingBalance){ 
        if (checkingBalance < 0){
            this.inOverdraft = true;
            return true;
        }
        else
            return false;
    }

    /**
     * 
     * @return if this account is in overdraft or not
     */
    public boolean getOverdraftStatus(){
        return this.inOverdraft;
    }

    /**
	 * Method to withdraw money from account with overdraft limits
	 * @param sum
	 */
    @Override
	public void withdraw(double sum){
        // get current checkingBalance
        double currentcheckingBalance = this.checkingBalance;
        // see what checkingBalance will be with requested withdrawl amount
        currentcheckingBalance -= sum;
        // see if this would cause account to be in overdraft
        // if in overdraft, allow for a withdrawl up to the overdraft-limit amount
		if (isInOverDraft(currentcheckingBalance)){ 
            // if overdraft within overdraft limit
            // go into the negative for that amount
            if (currentcheckingBalance + overdraftLimit >= 0)
                this.checkingBalance = currentcheckingBalance;
            // otherwise if withrdawl beyond the overdraft limit
            // only go into the negative up to the overdraft limit
            else{
                this.checkingBalance = 0 - overdraftLimit;
            }
        }
        // otherwise regular withdrawl
		else			
            this.checkingBalance = currentcheckingBalance;
    }

    @Override
	public String toString() {
        String space = " ";
		return 	super.toString() 
                 + "\n" + space.repeat(21) + Colors.ANSI_PURPLE + getClass() + Colors.ANSI_RESET 
                 + "\n" + space.repeat(21) + Colors.ANSI_CYAN + "Checking Accnt Num: " + Colors.ANSI_RESET  + (int)getAccountNumber() 
                 + "\n" + space.repeat(21) + Colors.ANSI_CYAN + "Overdraft Limit: " + Colors.ANSI_RESET + Colors.ANSI_PURPLE + Print.nf.format(getOverdraftLimit()) + Colors.ANSI_RESET
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

		CheckingAccount other = (CheckingAccount) obj;			

		if (getManager() == null) {
			if (other.getManager() != null)
				return false;
		} 
        else if (!getManager().equals(other.getManager()))	
			return false;
		if (getAccountNumber() != other.getAccountNumber())			
			return false;
		if (getBalance() != other.getBalance())							
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
        if (getOverdraftLimit() != other.getOverdraftLimit()) 
                return false;
		else if (!getOwner().equals(other.getOwner()))					
			return false;
		return true;
	}

}

// Gabriel Malone / CSCI165 / Week 12 / Summer 2024



public class Bank {
	
	
	private double dividendRate = 0.01;


	public void updateAccounts(){
		
	}

	public void openAccount(){
			
	}

	public void closeAccount(){

	}

	public void payDividends(){

	}

	public void findAccount(Customer customer){

	}

	public void findAccount(String customerID){

	}

	public void showAccounts(){

	}

	private String createCustomerID(String firstName, String lastName){
		String customerID = "";
		Date today = Date.dateInitializer();
		String day = String.valueOf(today.getDay());
		String year = String.valueOf(today.getYear());
		char firstInitial = firstName.charAt(0);
		char lastInitial  = lastName.charAt(0);
		customerID = firstInitial + lastInitial + day + year;
		return customerID;
	}

	private int createEmployeeID(){
		int employeeID = 0;
		return employeeID;
	}

	private int createAccountNumber(){
		int accountNumber = 0;
		return accountNumber;
	}

}

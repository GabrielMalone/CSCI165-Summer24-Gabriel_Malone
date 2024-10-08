// Gabriel Malone / CSCI165 / Week 12 / Summer 2024

import java.text.NumberFormat;

public class Print {

    public static String space = " ";
	public static NumberFormat nf = NumberFormat.getCurrencyInstance();


    public static void mainMenuOptions(){
		String itemRequest =  	"(" + Colors.ANSI_YELLOW + "A" + Colors.ANSI_RESET 	+ ")" 			+ Colors.ANSI_CYAN 	+ "DD ACCOUNT" 	    + Colors.ANSI_RESET + 
								" |" + " (" + Colors.ANSI_YELLOW + "S" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN  + "ELECT ACCOUNT" 	+ Colors.ANSI_RESET +
								" |" + " (" + Colors.ANSI_YELLOW + "U" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN 	+ "PDATE ALL" 	    + Colors.ANSI_RESET;
		System.out.printf("%22s%s", space, itemRequest);
	}
	public static void updateMenuOptions(Account currAccount){
		String itemRequestA =	 " ("   + Colors.ANSI_YELLOW + "D"  + Colors.ANSI_RESET 	    + ")" 	+ Colors.ANSI_PURPLE  + "EPOSIT" 	+ Colors.ANSI_RESET
								+ " |"  + " (" + Colors.ANSI_YELLOW + "W" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_PURPLE  + "WITHDRAW" 	 
								+ " |"  + " (" + Colors.ANSI_YELLOW + "S" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_PURPLE  + "ET RATE" + Colors.ANSI_RESET
								+ " |"  + " (" + Colors.ANSI_YELLOW + "T" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_PURPLE  + "RANSFER" + Colors.ANSI_RESET;
		String itemRequestB =	 " ("   + Colors.ANSI_YELLOW + "D"  + Colors.ANSI_RESET 	    + ")" 	+ Colors.ANSI_PURPLE  + "EPOSIT" 	+ Colors.ANSI_RESET
								+ " |"  + " (" + Colors.ANSI_YELLOW + "W" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_PURPLE  + "WITHDRAW" 	 
								+ " |"  + " (" + Colors.ANSI_YELLOW + "S" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_PURPLE  + "ET LIMIT" + Colors.ANSI_RESET
								+ " |"  + " (" + Colors.ANSI_YELLOW + "T" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_PURPLE  + "RANSFER" + Colors.ANSI_RESET;
		if (currAccount.getClass() == SavingsAccount.class )
			System.out.printf("%20s%s", space, itemRequestA);
		else if (currAccount.getClass() == CheckingAccount.class)
			System.out.printf("%20s%s", space, itemRequestB);
	}
	public static void updateAccountHeader(){
		yellowhorizontalLine();
		System.out.printf("%37s%s%n", space, Colors.ANSI_YELLOW 	+ "// UPDATE ACCOUNT // " 	+ Colors.ANSI_RESET);
		yellowhorizontalLine();
	}
	public static void transferAccountHeader(){
		yellowhorizontalLine();
		System.out.printf("%37s%s%n", space, Colors.ANSI_YELLOW 	+ "// TRANSFER CASH // " 	+ Colors.ANSI_RESET);
		yellowhorizontalLine();
	}
	public static void updateAllAccountsHeader(){
		bluehorizontalLine();
		System.out.printf("%38s%s%n", space, Colors.ANSI_YELLOW 	+ "// UPDATE ALL // " 	+ Colors.ANSI_RESET);
		bluehorizontalLine();
	}
	public static void updateAccountFooter(){
		yellowhorizontalLine();
		System.out.printf("%35s%s%n", space, Colors.ANSI_PURPLE 	+ "// ACCOUNT UPDATED // " 	+ Colors.ANSI_RESET);
		yellowhorizontalLine();
	}
	public static void updateAccountInvalidFooter(){
		yellowhorizontalLine();
		System.out.printf("%32s%s%n", space, Colors.ANSI_RED 	+ "// INVALID TRANSFER REQUEST // " 	+ Colors.ANSI_RESET);
		yellowhorizontalLine();
	}
	public static void deleteAccountFooter(){
		yellowhorizontalLine();
		System.out.printf("%35s%s%n", space, Colors.ANSI_RED 	+ "// ACCOUNT DELETED // " 	+ Colors.ANSI_RESET);
		yellowhorizontalLine();
	}
	public static void despoitAccountHeader(){
		greenhorizontalLine();
		System.out.printf("%22s%s%n", space, Colors.ANSI_GREEN 	+ "// CASH DEPOSIT // " 	+ Colors.ANSI_RESET);
		greenhorizontalLine();
	}
	public static void rateAccountHeader(){
		greenhorizontalLine();
		System.out.printf("%22s%s%n", space, Colors.ANSI_GREEN 	+ "// INTEREST RATE // " 	+ Colors.ANSI_RESET);
		greenhorizontalLine();
	}
	public static void limitAccountHeader(){
		greenhorizontalLine();
		System.out.printf("%22s%s%n", space, Colors.ANSI_GREEN 	+ "// OVERDRAFT LIMIT // " 	+ Colors.ANSI_RESET);
		greenhorizontalLine();
	}
	public static void withdrawAccountHeader(){
		greenhorizontalLine();
		System.out.printf("%22s%s%n", space, Colors.ANSI_GREEN 	+ "// CASH WITHDRAWL // " 	+ Colors.ANSI_RESET);
		greenhorizontalLine();
	}
	public static void updateAllMenuOptions(){
		String itemRequest =	 " ("   + Colors.ANSI_YELLOW + "D"  + Colors.ANSI_RESET 	    + ")" 	+ Colors.ANSI_CYAN  + "IVIDENDS PAY" 	+ Colors.ANSI_RESET
								+ " |"  + " (" + Colors.ANSI_YELLOW + "U" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN  + "PDATE SAVINGS & CHECKINGS " + Colors.ANSI_RESET;
		System.out.printf("%21s%s", space, itemRequest);
	}
	public static void customerMenuOptions(){
		bluehorizontalLine();
		String itemRequest =	 " ("   + Colors.ANSI_YELLOW + "C"  + Colors.ANSI_RESET 	    + ")" 	+ Colors.ANSI_CYAN  + "LOSE ACCOUNT" 	+ Colors.ANSI_RESET
								+ " |"  + " (" + Colors.ANSI_YELLOW + "U" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN  + "PDATE ACCOUNT " 	 
								+ " |"  + " (" + Colors.ANSI_YELLOW + "M" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN  + "AIN MENU" 	+ Colors.ANSI_RESET;
		System.out.printf("%20s%s", space, itemRequest);
	}
	public static void customerMenuOptionsAfterDelete(){
		bluehorizontalLine();
		String itemRequest = Colors.ANSI_YELLOW + "M" + Colors.ANSI_RESET 	+ ")" 	+ Colors.ANSI_CYAN  + "AIN MENU" 	+ Colors.ANSI_RESET;
		System.out.printf("%39s%s", space, itemRequest);
	}
	public static void displayCustomerInfo(Account account){
		System.out.printf("%s%n", account);
	}
	public static  void customerIDHeader (){
		String headerString = Colors.ANSI_CYAN + "Accnt #" + space.repeat(6) + "Cust ID" + space.repeat(6) + "Type" + space.repeat(8) + "Balance" +  Colors.ANSI_RESET;
		System.out.printf("%21s%s%n", space, headerString);
	}
	public static  void enterContinue (){
		String continueString = Colors.ANSI_CYAN + "Return to Menu " +Colors.ANSI_RESET;
		System.out.printf("%21s%s", space, continueString);
	}
	public static void accountTypeHeader(){
		String menutitle =  Colors.ANSI_YELLOW + "// SET ACCOUNT TYPE //" + Colors.ANSI_RESET;
		System.out.printf("%35s%s%n", space, menutitle);
	}
	public static void checkingOrSavingsSelectPrint(){
		String itemRequest =  "(" + Colors.ANSI_YELLOW + "C" + Colors.ANSI_RESET + ")" 	+ Colors.ANSI_CYAN 	+ "HECKING ACCOUNT" + Colors.ANSI_RESET + " |" + " (" + Colors.ANSI_YELLOW 	+ "S" + Colors.ANSI_RESET + ")" + Colors.ANSI_CYAN + "AVINGS ACCOUNT" + Colors.ANSI_RESET;
		System.out.printf("%27s%s ", space, itemRequest);
	}
	public static void accountInfoRetrieveHeader(){
		System.out.printf("%35s%s%n", space, Colors.ANSI_YELLOW 	+ "// RETRIEVE ACCOUNT INFO // " 	+ Colors.ANSI_RESET);
		yellowhorizontalLine();
		System.out.printf("%21s%s", space, Colors.ANSI_CYAN 		+ "Enter Accnt #: " 				+ Colors.ANSI_RESET);
	}

	public static void loginHeader(){
		System.out.printf("%43s%37s%n",Colors.ANSI_CYAN 	+ "CHEMICAL BANK" 			+ Colors.ANSI_RESET, Date.dateInitializer());
        greenhorizontalLine();
        System.out.printf("%54s",  Colors.ANSI_YELLOW 	+ "// EMPLOYEE TERMINAL //" + Colors.ANSI_RESET);
        System.out.printf("%30s",  Colors.ANSI_CYAN 		+ "Login ID: " 				+ Colors.ANSI_RESET);
	}
	public static void clearSequence(){
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		System.out.println(); 
	}
    public static void greenhorizontalLine(){
		System.out.printf("%s%71s%s%n", Colors.ANSI_GREEN, "-".repeat(50), Colors.ANSI_RESET);
	}
	public static void yellowhorizontalLine(){
		System.out.printf("%s%71s%s%n", Colors.ANSI_YELLOW, "-".repeat(50), Colors.ANSI_RESET);
	}
	public static void bluehorizontalLine(){
		System.out.printf("%s%71s%s%n", Colors.ANSI_CYAN, "-".repeat(50), Colors.ANSI_RESET);
	}
	public static void depositRequestHeader(){
		String itemRequest =  Colors.ANSI_YELLOW + "AMMOUNT: " + Colors.ANSI_RESET;
		System.out.printf("%22s%s ", space, itemRequest);
	} 
	public static void withdrawRequestHeader(){
		String itemRequest =  Colors.ANSI_YELLOW + "WITHDRAWL AMMOUNT: " + Colors.ANSI_RESET;
		System.out.printf("%26s%s ", space, itemRequest);
	} 
	public static void newCustomerHeader(){
		System.out.printf("%21s%s%n", space, Colors.ANSI_YELLOW 	+ "// NEW CUSTOMER INFO //" + Colors.ANSI_RESET);
	}
	public static void firstNameHeader(){
		System.out.printf("%21s%s", space,Colors.ANSI_CYAN 		+ "First Name     " 		+ Colors.ANSI_RESET);
	}
	public static void lasttNameHeader(){
		System.out.printf("%21s%s", space, Colors.ANSI_CYAN 		+ "Last Name      " 		+ Colors.ANSI_RESET);
	}
	public static void phoneHeader(){
		System.out.printf("%21s%s", space, Colors.ANSI_CYAN 		+ "10-Digit Phone " 		+ Colors.ANSI_RESET);
	}
	public static void DOBHeader(){
		System.out.printf( "%21s%s", space, Colors.ANSI_CYAN 	+ "DOB (1/1/1111) " 		+ Colors.ANSI_RESET);
	}
	public static void OverDraftHeader(){
		String itemRequest =  Colors.ANSI_YELLOW + "OVERDRAFT LIMIT: " 	+ Colors.ANSI_RESET;
		System.out.printf("%22s%s ", space, itemRequest);
	}
	public static void interestRateHeader(){
		String itemRequest =  Colors.ANSI_YELLOW + "INTEREST RATE % :" 	+ Colors.ANSI_RESET;
		System.out.printf("%22s%s ", space, itemRequest);
	}
}

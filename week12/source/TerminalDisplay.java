// Gabriel Malone / CSCI165 / Week 12 / Summer 2024

import java.text.NumberFormat;
import java.util.Scanner;

public class TerminalDisplay {
    // text // graphic colors
	public static 	String ANSI_RESET           = "\u001B[0m"; 
	private static  String ANSI_PURPLE          = "\u001B[35m";
	private static  String ANSI_WHITE           = "\u001B[37m";
	public static  	String ANSI_BLACK           = "\u001B[31m";
	public static  	String ANSI_YELLOW          = "\u001B[33m";
	private static  String ANSI_PINK            = "\u001b[38;5;201m";
	private static  String ANSI_BLUE            = "\u001B[34m";
	public static  	String ANSI_CYAN            = "\u001B[36m";
	private static  String ANSI_BOLD            = "\u001b[1m";
	private static  String ANSI_BLUE_BACK       = "\u001b[1m\u001B[32m";
    private static  String ANSI_GREEN           = "\u001B[32m";
	private static  String CALORIE_COLOR        = ANSI_PURPLE;
	private static  String MENU_NUMBER_COLOR    = ANSI_PURPLE;
	private static  String MENU_BORDER_COLOR    = ANSI_PINK;
	public 	static  String PRICE_COLORS         = ANSI_BLUE;

	public static boolean display = true;
    public static Scanner scanner = new Scanner(System.in);
    public AccountManager manager = new AccountManager();

    // use this a lot
	static String space = " ";
	// money output used quite a bit
	static NumberFormat nf = NumberFormat.getCurrencyInstance();

    public TerminalDisplay(){
        clearSequence();
        manager.loadAccounts("source/accounts.txt");
        MainMenu();
        addRequest();
        scanner.nextLine();
        clearSequence();
    }

    public void clearSequence(){
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		System.out.println(); 
	}

    public void horizontalLine(){
		System.out.printf("%s%71s%s%n", ANSI_GREEN, "-".repeat(50), ANSI_RESET);
	}

    public void Options(){
		String itemString  =  ANSI_PURPLE + "item"  +  ANSI_RESET;
		String quantString =  ANSI_PURPLE + "quant" +  ANSI_RESET;
		String priceString =  ANSI_PURPLE + "price" +  ANSI_RESET;
		String calsString  =  ANSI_PURPLE + "cals"  +  ANSI_RESET;
		System.out.printf("%-26s%-33s%-16s%-17s%s%n", space, itemString, quantString, priceString, calsString);
	}

    public void MainMenu(){
		System.out.printf("%43s%35s%n",ANSI_CYAN + "TERMINAL BANK" + ANSI_RESET, Date.dateInitializer().toString());
        horizontalLine();
        System.out.printf("%47s",ANSI_YELLOW +  "Employee Terminal" + ANSI_RESET);
        System.out.printf("%25s %s%n",  ANSI_BLUE_BACK + String.valueOf(manager.total_acnts), "accounts on file" + ANSI_RESET);
        horizontalLine();
    }

    public void addRequest(){
		String itemRequest =  "(" + ANSI_YELLOW + "A" + ANSI_RESET + ")" + ANSI_CYAN + "DD CUSTOMER" + ANSI_RESET + " |" + " (" + ANSI_YELLOW + "F" + ANSI_RESET + ")" +  ANSI_CYAN + "IND CUSTOMER" + ANSI_RESET + " | (" + ANSI_YELLOW + "U" + ANSI_RESET + ")" + ANSI_CYAN + "PDATE ACNTS " + ANSI_RESET;
		System.out.printf("%21s%s", space, itemRequest);
	}
}

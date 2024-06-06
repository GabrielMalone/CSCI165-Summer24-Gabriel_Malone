import java.util.ArrayList;

public class BankDriver {

    public static LoadAccounts loadAccounts;
    

    public static void main(String[] args) {
        
        loadAccounts = new LoadAccounts();
        loadAccounts.loadAccounts("source/accounts.txt");
        ArrayList<Account> bankAccounts = loadAccounts.getAccounts();
        for (Account bankaccount : bankAccounts){
            System.out.println("------------------------");
            System.out.println(bankaccount);
        }
        
    }
    
}

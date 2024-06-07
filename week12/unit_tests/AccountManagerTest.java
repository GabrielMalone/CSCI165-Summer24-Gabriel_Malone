import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;



import org.junit.jupiter.api.Test;

public class AccountManagerTest {


    @Test
    void testSaveAccount() {
       
        // new manager loaded
        AccountManager manager = new AccountManager();

        // new account created
        SavingsAccount savingsAccount = new SavingsAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421"), new Date(5, 5, 1955), 69.0, 0.25);

        // add the account to the array and the map
        manager.addAccount(savingsAccount);
        // save the account info to txt file
        manager.saveAccounts("source/accounts.txt");

        // clear the arrays and map
        manager.bankAccountMap.clear();
        manager.bankAccounts.clear();

        //load the data from file into the map and array
        manager.loadAccounts("source/accounts.txt");

        // let's test to see that the account was added safely and kept the correct information
        Account foundAccount = manager.findAccount("4346-23421");
        assertTrue(savingsAccount.equals(foundAccount));

        // privacy protection test
        assertFalse(savingsAccount == foundAccount);


    }

    @Test
    void testFindMethod() {

        // create a new account and add the account to the Map

        // new manager loaded
        AccountManager manager = new AccountManager();

        // new account created
        SavingsAccount savingsAccount = new SavingsAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421"), new Date(5, 5, 1955), 69.0, 0.25);

        // add this account to the manager's Map
        manager.addAccount(savingsAccount);

        // let's test to see that the account was added safely and kept the correct information
        Account foundAccount = manager.findAccount("4346-23421");
        assertTrue(savingsAccount.equals(foundAccount));
        
        // privacy protection test
        assertFalse(savingsAccount == foundAccount);
    }

}

// Gabriel Malone / CSCI165 / Week 12 / Summer 2024

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class CheckingAccountTest {

    @Test
    void testConstructorA() {

        // constructor vars
        int accountNumber = 1111;
        double overdraftLimit = 100;
        Date dateCreated = new Date(4, 1, 1900);
        Person customerPerson = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person employeePerson = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(customerPerson, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(employeePerson, new Date(1, 1, 2020), 56789, "CEO");
        CheckingAccount checkingAccount = new CheckingAccount(accountNumber, customer, manager, dateCreated, overdraftLimit);
        double deposit = 100;
        checkingAccount.deposit(deposit);

        // see that all variables set correctly with appropriate privacy protections

        // account number
        assertTrue(checkingAccount.getAccountNumber() == accountNumber);

        // owner with privacy protection check
        assertTrue(checkingAccount.getOwner().equals(customer));
        assertFalse(checkingAccount.getOwner() == customer);

        // manager with privacy protection check
        assertTrue(checkingAccount.getManager().equals(manager));
        assertFalse(checkingAccount.getManager() == manager);

        // date created with privacy protection check and logic check
        // this will return false since created date set at a date before join
        assertFalse(checkingAccount.getDateCreated().equals(dateCreated));

        // set the join date a day prior to account date creation
        Date newCreationDate = new Date(4, 1, 2001);
        checkingAccount.setDateCreated(newCreationDate);

        // date should now be set as inputted with appropriate privacy protection
        assertFalse(checkingAccount.getDateCreated() == newCreationDate);
        assertTrue(checkingAccount.getDateCreated().equals(newCreationDate));

        // balance check
        assertTrue(checkingAccount.getBalance() == deposit);

        // interest check
        assertTrue(checkingAccount.getOverdraftLimit() == overdraftLimit);

        // overdraft status check (default should be false)
        assertFalse(checkingAccount.getOverdraftStatus());

    }

    @Test
    void testConstructorB() {

        // constructor vars
        double balace = 9000;
        double overdraftLimit = 300;
        int accountNumber = 9933;
        Date created = new Date(5, 5, 1955);
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");

        // instantiating all but manager
        CheckingAccount checkingAccount = new CheckingAccount(accountNumber, customer, created, balace, overdraftLimit);

         //manager should be set to default values and should not return null
         assertFalse(checkingAccount.getManager() == null);

        // check that all variables set correctly with appropriate privacy protections
        // account number
        assertTrue(checkingAccount.getAccountNumber() == 9933);

        // owner with privacy protection check
        assertTrue(checkingAccount.getOwner().equals(customer));
        assertFalse(checkingAccount.getOwner() == customer);

        // date created with privacy protection check
        assertTrue(checkingAccount.getDateCreated().equals(created));
        assertFalse(checkingAccount.getDateCreated() == created);

        // balance
        assertTrue(checkingAccount.getBalance() == balace);

        // overdraft limit
        assertTrue(checkingAccount.getOverdraftLimit() == overdraftLimit); 

        // overdraft status check (default should be false) 
        assertFalse(checkingAccount.getOverdraftStatus());
    }

    @Test
    void testCopyConstructor() {
        
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

        CheckingAccount checkingAccountB = new CheckingAccount(8765, new Customer(new Person("Goobert", "Doobert","1111111111", new Date (1,2, 2020)), new Date(4, 2, 2021), "123-2") , new Date(5, 5, 2022), 90, 3);

        // At this point A and B should be different in both var state and memory
        assertFalse(checkingAccountA.equals(checkingAccountB));
        assertFalse(checkingAccountA == checkingAccountB);

        // copy A to B
        checkingAccountB = new CheckingAccount(checkingAccountA);

        //A and B should now be equal in var states
        assertTrue(checkingAccountA.equals(checkingAccountB));

        //A and B should not be equal in memory address
        assertFalse(checkingAccountA == checkingAccountB);
    }

    @Test
    void testEqualsA() {

        // EQUALITY WITH CHANGING MANAGERS

        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

        CheckingAccount checkingAccountB = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

         //A and B should now be equal in var states
         assertTrue(checkingAccountA.equals(checkingAccountB));
 
         //A and B should not be equal in memory address
         assertFalse(checkingAccountA == checkingAccountB);

         // changing any variable state in either account should cause the accounts to no longer be equal

         // current managers should be equal at this point
         assertTrue(checkingAccountA.getManager().equals(checkingAccountB.getManager()));
        
         // changing manager
         checkingAccountA.setManager(new Employee(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), 4444, "Sales"));

         // managers should no longer be equal
         assertFalse(checkingAccountA.getManager().equals(checkingAccountB.getManager()));

         // and, therefore checking accounts A and B should no longer be equal
         assertFalse(checkingAccountA.equals(checkingAccountB));
    }

    @Test
    void testEqualsB() {

        // EQUALITY WITH CHANGING BALANCES
        
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

        CheckingAccount checkingAccountB = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

         //A and B should now be equal in var states
         assertTrue(checkingAccountA.equals(checkingAccountB));
 
         //A and B should not be equal in memory address
         assertFalse(checkingAccountA == checkingAccountB);

         // changing any variable state in either account should cause the accounts to no longer be equal

         // current balances should be equal at this point
         assertTrue(checkingAccountA.getBalance() == checkingAccountB.getBalance());

         // changing balance of A
         checkingAccountA.deposit(300);

         // balances should no longer be equal
         assertFalse(checkingAccountA.getBalance() == checkingAccountB.getBalance());

         // therefore accounts should no longer be equal
         assertFalse(checkingAccountA.equals(checkingAccountB));
    }

    @Test
    void testEqualsC() {

        // EQUALITY WITH CHANGING OVERDRAFT LIMITS
        
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

        CheckingAccount checkingAccountB = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

         //A and B should now be equal in var states
         assertTrue(checkingAccountA.equals(checkingAccountB));
 
         //A and B should not be equal in memory address
         assertFalse(checkingAccountA == checkingAccountB);

         // changing any variable state in either account should cause the accounts to no longer be equal

         // current overdraft limits should be equal at this point
         assertTrue(checkingAccountA.getOverdraftLimit() == checkingAccountB.getOverdraftLimit());

         // changing overdraft limit of A
         checkingAccountA.setOverdraftLimit(9);

         // balances should no longer be equal
         assertFalse(checkingAccountA.getOverdraftLimit() == checkingAccountB.getOverdraftLimit());

         // therefore accounts should no longer be equal
         assertFalse(checkingAccountA.equals(checkingAccountB));
    }

    @Test
    void testEqualsD() {

        // EQUALITY AFTER WIThDRAWING
        
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

        CheckingAccount checkingAccountB = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

         //A and B should now be equal in var states
         assertTrue(checkingAccountA.equals(checkingAccountB));
 
         //A and B should not be equal in memory address
         assertFalse(checkingAccountA == checkingAccountB);

         // changing any variable state in either account should cause the accounts to no longer be equal

         // current balances should be equal at this point
         assertTrue(checkingAccountA.getBalance() == checkingAccountB.getBalance());

         // withrawing from A
         checkingAccountA.withdraw(.01);

         // balances should no longer be equal
         assertFalse(checkingAccountA.getBalance() == checkingAccountB.getBalance());

         // therefore accounts should no longer be equal
         assertFalse(checkingAccountA.equals(checkingAccountB));
    }

    @Test
    void testEqualsE() {

        // EQUALITY AFTER CHANGING THE ACCOUNT'S DATE
        
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

        CheckingAccount checkingAccountB = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

         //A and B should now be equal in var states
         assertTrue(checkingAccountA.equals(checkingAccountB));
 
         //A and B should not be equal in memory address
         assertFalse(checkingAccountA == checkingAccountB);

         // changing any variable state in either account should cause the accounts to no longer be equal

         // current account creation dates should be equal at this point
         assertTrue(checkingAccountA.getDateCreated().equals(checkingAccountB.getDateCreated()));

         // changing date associated with owner of A
         checkingAccountA.setDateCreated(new Date(6, 6, 2024));

         // dates should no longer be equal
         assertFalse(checkingAccountA.getDateCreated().equals(checkingAccountB.getDateCreated()));

         // therefore accounts should no longer be equal
         assertFalse(checkingAccountA.equals(checkingAccountB));
    }

    @Test
    void testEqualsF() {

        // EQUALITY AFTER CHANGING THE ACCOUNT'S OWNER
        
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

        CheckingAccount checkingAccountB = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 300);

         //A and B should now be equal in var states
         assertTrue(checkingAccountA.equals(checkingAccountB));
 
         //A and B should not be equal in memory address
         assertFalse(checkingAccountA == checkingAccountB);

         // changing any variable state in either account should cause the accounts to no longer be equal

         // current account owners should be equal at this point
         assertTrue(checkingAccountA.getOwner().equals(checkingAccountB.getOwner()));

         // changing ownder associated with owner of A
         checkingAccountA.setOwner(new Customer());

         // ownders should no longer be equal
         assertFalse(checkingAccountA.getOwner().equals(checkingAccountB.getOwner()));

         // therefore accounts should no longer be equal
         assertFalse(checkingAccountA.equals(checkingAccountB));
    }


    @Test
    void testGetOverdraftLimit() {

        double overdraftLimit = 300;
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, overdraftLimit);

        // overdraft limit set at 300
        assertTrue(checkingAccountA.getOverdraftLimit() == overdraftLimit);
    }

    @Test
    void testGetOverdraftStatus() {

        double overdraftLimit = 300;
        double balance = 9000;
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), balance, overdraftLimit);

        // overdraft limit set at 300 / current account should not be overdrawn at this point
        assertFalse(checkingAccountA.getOverdraftStatus());

        // let's withrdaw an amount less than current balance
        // should not put account into overdraft status
        checkingAccountA.withdraw(1000);
        assertFalse(checkingAccountA.getOverdraftStatus());

        // let's withrdaw an amount that puts account at 0
        // should not put account into overdraft status
        checkingAccountA.withdraw(checkingAccountA.getBalance());
        assertFalse(checkingAccountA.getOverdraftStatus());
        assertTrue(checkingAccountA.getBalance() == 0);

        // let's withdraw more than customer has (currently at 0)
        checkingAccountA.withdraw(1);

        // account should now be in overdraft
        assertTrue(checkingAccountA.getOverdraftStatus());
    }

    @Test
    void testIsInOverDraft() {

        
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 100, 300);

        // if balance positive - account not in overdraft
        double balance = 100;
        assertFalse(checkingAccountA.isInOverDraft(balance));

        // if negative balance - in overdraft true
        double balance2 = -100;
        assertTrue(checkingAccountA.isInOverDraft(balance2));

    }

    @Test
    void testSetOverdraftLimit() {

        double OGoverdraftLimit = 300;
        double newLimit = 10;
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, OGoverdraftLimit);

        // overdraft limit set at 300 
        assertTrue(checkingAccountA.getOverdraftLimit() == OGoverdraftLimit);

        // change limit
        checkingAccountA.setOverdraftLimit(newLimit);

        // checking account limit should no longer equal original limit
        assertFalse(checkingAccountA.getOverdraftLimit() == OGoverdraftLimit);

        // current limit should equal newLimit
        assertTrue(checkingAccountA.getOverdraftLimit() == newLimit);
    }

    @Test
    void testWithdrawAndOverdraftLimit() {

        double overdraftLimit = 300;
        double balance = 9000;
        CheckingAccount checkingAccountA = new CheckingAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), balance, overdraftLimit);

        // overdraft limit set at 300
        assertTrue(checkingAccountA.getOverdraftLimit() == overdraftLimit);

        // that means customer should be allowed to withraw up to account balance + overdraft limit 
        checkingAccountA.withdraw(9300);

        // (9000 +300 = 9300. 9000-9300 = -300).
        assertTrue(checkingAccountA.getBalance() == overdraftLimit * -1);
        assertTrue(checkingAccountA.getBalance() == -300);

        // that also means customer should not be allowed to withdraw beyond this point. If they attempt to do so, they will be limited
        // try withdrawing one more dollar
        checkingAccountA.withdraw(1);

        // cannot go into the negative any further
        assertFalse(checkingAccountA.getBalance() == -301);
        assertTrue(checkingAccountA.getBalance()  == -300);
    }
}

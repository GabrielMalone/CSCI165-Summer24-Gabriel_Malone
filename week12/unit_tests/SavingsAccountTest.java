// Gabriel Malone / CSCI165 / Week 12 / Summer 2025

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

public class SavingsAccountTest {
    
  
    @Test
    void constructorTestA(){

        // constructor vars
        double interestRate = 0.25;
        Date created = new Date(4, 1, 1900);
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // instantiating fully overloaded constructor
        SavingsAccount savingsAccount = new SavingsAccount(9933, customer, manager, created, 0.25);
        double deposit = 100;
        savingsAccount.deposit(deposit);

        // check that all variables set correctly with appropriate privacy protections

        // account number
        assertTrue(savingsAccount.getAccountNumber() == 9933);

        // owner with privacy protection check
        assertTrue(savingsAccount.getOwner().equals(customer));
        assertFalse(savingsAccount.getOwner() == customer);

        // manager with privacy protection check
        assertTrue(savingsAccount.getManager().equals(manager));
        assertFalse(savingsAccount.getManager() == manager);

        // date created with privacy protection check and logic check
        // this will return false since created date set at a date before join
        assertFalse(savingsAccount.getDateCreated().equals(created));

        // set the join date a day prior to account date creation
        Date newCreationDate = new Date(4, 1, 2001);
        savingsAccount.setDateCreated(newCreationDate);
        
        // date should now be set as inputted with appropriate privacy protection
        assertFalse(savingsAccount.getDateCreated() == newCreationDate);
        assertTrue(savingsAccount.getDateCreated().equals(newCreationDate));

        // balance check
        assertTrue(savingsAccount.getBalance() == deposit);

        // interest check
        assertTrue(savingsAccount.getInterest() == interestRate);
    }

    @Test
    void constructorTestB(){

        // constructor vars
        Date created = new Date(5, 5, 1955);
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");

        // instantiating all but manager
        SavingsAccount savingsAccount = new SavingsAccount(9933, customer, created, 90000, 0.25);

         //manager should be set to default values and should not return null
         assertFalse(savingsAccount.getManager() == null);

        // check that all variables set correctly with appropriate privacy protections
        // account number
        assertTrue(savingsAccount.getAccountNumber() == 9933);

        // owner with privacy protection check
        assertTrue(savingsAccount.getOwner().equals(customer));
        assertFalse(savingsAccount.getOwner() == customer);

        // date created with privacy protection check
        assertTrue(savingsAccount.getDateCreated().equals(created));
        assertFalse(savingsAccount.getDateCreated() == created);

        // balance
        assertTrue(savingsAccount.getBalance() == 90000);

        // interest
        assertTrue(savingsAccount.getInterest() == 0.25);  
    }

    @Test
    void testCopyConstructor() { 
          
        SavingsAccount savingsAccountA = new SavingsAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421") , new Date(5, 5, 1955), 9000, 0.01);

        SavingsAccount savingsAccountB = new SavingsAccount(8765, new Customer(new Person("Goobert", "Doobert","1111111111", new Date (1,2, 2020)), new Date(4, 2, 2021), "123-2") , new Date(5, 5, 2022), 90, 0.001);

        // At this point A and B should be different in both var state and memory
        assertFalse(savingsAccountA.equals(savingsAccountB));
        assertFalse(savingsAccountA == savingsAccountB);

        //copy A to B
        savingsAccountB = new SavingsAccount(savingsAccountA);

        //A and B should now be equal in var states
        assertTrue(savingsAccountA.equals(savingsAccountB));

        //A and B should not be equal in memory address
        assertFalse(savingsAccountA == savingsAccountB);
    }

    @Test
    void testEqualsA() {

        // DIFFERENT ACCOUNT BALANCES AFTER DEPOSIT
        // savings account vars
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // savings account objects
        SavingsAccount savingsAccountA = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);

        // A and B current assigned samed variable states
        SavingsAccount savingsAccountB = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);

        // should be equal with regard to variable states
        assertTrue(savingsAccountA.equals(savingsAccountB));

        // should not be equal with regard to memory addresses
        assertFalse(savingsAccountA == savingsAccountB);

        // change one variable state in savingsAccountA
        // deposit a dollar into A. Now Accounts A and B should not be equal
        savingsAccountA.deposit(1);
        assertFalse(savingsAccountA.equals(savingsAccountB));
    }

    @Test
    void testEqualsB() {

        // DIFFERENT ACCOUNT BALANCES AFTER INTEREST ADDED
        // savings account vars
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // savings account objects
        SavingsAccount savingsAccountA = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);
        // A and B current assigned samed variable states
        SavingsAccount savingsAccountB = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);
        savingsAccountA.deposit(100);
        savingsAccountB.deposit(100);

        // should be equal with regard to variable states
        assertTrue(savingsAccountA.equals(savingsAccountB));

        // should not be equal with regard to memory addresses
        assertFalse(savingsAccountA == savingsAccountB);

        // change one variable state in savingsAccountA
        // add interest for accountA. 
        savingsAccountA.addInterest();

        // Now Accounts A and B should not be equal
        assertFalse(savingsAccountA.equals(savingsAccountB));
    }

    @Test
    void testEqualsC() {

        // DIFFERENT ACCOUNT MANAGERS
        // savings account vars
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // savings account objects
        SavingsAccount savingsAccountA = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);

        // A and B current assigned samed variable states
        SavingsAccount savingsAccountB = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);

        // should be equal with regard to variable states
        assertTrue(savingsAccountA.equals(savingsAccountB));

        // should not be equal with regard to memory addresses
        assertFalse(savingsAccountA == savingsAccountB);

        // change one variable state in savingsAccountA
        // change managers for Account A. 
        savingsAccountA.setManager(new Employee());

        //Now Accounts A and B should not be equal
        assertFalse(savingsAccountA.equals(savingsAccountB));
    }

    @Test
    void testEqualsD() {

        // DIFFERENT ACCOUNT BALANCES AFTER WITHDRAWL
        // savings account vars
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // savings account objects
        SavingsAccount savingsAccountA = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);
        // A and B current assigned samed variable states
        SavingsAccount savingsAccountB = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);
        savingsAccountA.deposit(100);
        savingsAccountB.deposit(100);

        // should be equal with regard to variable states
        assertTrue(savingsAccountA.equals(savingsAccountB));

        // should not be equal with regard to memory addresses
        assertFalse(savingsAccountA == savingsAccountB);
        
        // change one variable state in savingsAccountA
        // withrdaw 1 dollar. 
        savingsAccountA.withdraw(1);
        // accounts should no longer be equal.
        assertFalse(savingsAccountA.equals(savingsAccountB));
    }

    @Test
    void testEqualsE() {

        // DIFFERENT CUSTOMER IDs
        // savings account vars
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Customer customer2 = new Customer(person, new Date(4, 2, 1900), "4346-23420");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // savings account objects
        SavingsAccount savingsAccountA = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);

        // A and B same everywhere except custID
        SavingsAccount savingsAccountB = new SavingsAccount(9933, customer2, manager, new Date(4, 1, 2000), 0.25);

        // A and B should not be equal
        assertFalse(savingsAccountA.equals(savingsAccountB));    
    }

    @Test
    void testEqualsF() {

        // DIFFERENT ACCOUNT CREATED DATES
        // savings account vars
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // savings account objects
        SavingsAccount savingsAccountA = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);

        // A and B same everywhere except customer join date
        SavingsAccount savingsAccountB = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);

        // A and B should be equal at this point
        assertTrue(savingsAccountA.equals(savingsAccountB)); 

        // change join date on savings account A   
        savingsAccountA.setDateCreated(new Date(4, 4, 2000));

        // accounts should no longer be equal
        assertFalse(savingsAccountA.equals(savingsAccountB)); 
    }

    @Test
    void testEqualsG() {

        // DIFFERENT CUSTOMER PERSON OBJECTS vs DIFFERENT CUSTOMER PERSON VAR STATES
        // savings account vars
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23420");
        Customer customer2 = new Customer(person, new Date(4, 2, 1900), "4346-23420");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // savings account objects
        SavingsAccount savingsAccountA = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);
        
        // A and B same everywhere 
        SavingsAccount savingsAccountB = new SavingsAccount(9933, customer2, manager, new Date(4, 1, 2000), 0.25);

        // A and B should be equal at this point
        assertTrue(savingsAccountA.equals(savingsAccountB)); 
        
        // keep all vars the same except Person objects with differnt variable states
        // but first check that adding a new customer object itself isn't the cause of the inequality
        // keep all vars the same to start
        savingsAccountA.setOwner(new Customer(person,new Date(4, 2, 1900), "4346-23420"));

        // A and B should still be equal
        assertTrue(savingsAccountA.equals(savingsAccountB)); 

        // now let's change a variable state in the Person object of a new Customer object and see
        // first name changed, all other var states left the same as before
        savingsAccountA.setOwner(new Customer(new Person("Goob", "Malorkus","5555555555", new Date (12,25, 1876)),new Date(4, 2, 1900), "4346-23420"));

        // A and B should no longer be equal
        assertFalse(savingsAccountA.equals(savingsAccountB));  
    }

    @Test
    void testEqualsH() {

        // DIFFERENT ACCOUNT INTEREST RATES
        // savings account vars
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // savings account objects
        SavingsAccount savingsAccountA = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);

        // A and B current assigned samed variable states
        SavingsAccount savingsAccountB = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);

        // should be equal with regard to variable states
        assertTrue(savingsAccountA.equals(savingsAccountB));

        // should not be equal with regard to memory addresses
        assertFalse(savingsAccountA == savingsAccountB);

        // change one variable state in savingsAccountA
        // change interest rate
        savingsAccountA.setInterest(1.25);
        assertFalse(savingsAccountA.equals(savingsAccountB));
    }


    @Test
    void testAddInterest() {

        // savings account vars
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // balance used for comparing before and after interest
        double deposit = 100;
        SavingsAccount savingsAccount = new SavingsAccount(9933, customer, manager, new Date(4, 1, 2000), 0.25);
        savingsAccount.deposit(deposit);

        // current balance
        double currentBalance = savingsAccount.getBalance();
        assertTrue(currentBalance == deposit);

        // add interest to account
        savingsAccount.addInterest();

        // new balance should no longer be equal to previous balance
        currentBalance = savingsAccount.getBalance();

        assertFalse(currentBalance == deposit);

        // new account balance should be 100 + (100 * 0.25) --> 125
        assertTrue(currentBalance == 125); 
    }

    @Test
    void testGetInterest() {

        // savings account vars
        double interest = 0.025;
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // instantiating fully overloaded constructor
        SavingsAccount savingsAccount = new SavingsAccount(9933, customer, manager, new Date(4, 1, 1900), interest);

        // get interest and check against set variable
        assertTrue(savingsAccount.getInterest() == interest);
    }

    @Test
    void testSetInterest() {

        // savings account vars
        double interest = 0.025;
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));
        Person person2 = new Person("Scrooge", "McDuck","1234567890", new Date (6,25, 1904));
        Customer customer = new Customer(person, new Date(4, 2, 1900), "4346-23421");
        Employee manager = new Employee(person2, new Date(1, 1, 2020), 56789, "CEO");

        // instantiating fully overloaded constructor
        SavingsAccount savingsAccount = new SavingsAccount(9933, customer, manager, new Date(4, 1, 1900), interest);

        // get interest and check against set variable
        assertTrue(savingsAccount.getInterest() == interest);

        // set a new interest rate
        double newInterest = 0.045;
        savingsAccount.setInterest(newInterest);

        // new interest should not equal old interest
        assertFalse(savingsAccount.getInterest() == interest);

        // new interest should equal newInterest var
        assertTrue(savingsAccount.getInterest() == newInterest);
    }

    @Test
    void testDeposit(){

        // instantiating all but manager
        double balance = 9000;
        double deposit = 1;
        SavingsAccount savingsAccount = new SavingsAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421"), new Date(5, 5, 1955), balance, 0.25);

        // test that current balance ammount is what was set at object instantiation
        assertTrue(savingsAccount.getBalance() == balance);

        // deposit a valid amount. new balance should reflect the addition of this deposit. 
        savingsAccount.deposit(deposit);

        // new balance should not equal old balance
        assertFalse(savingsAccount.getBalance() == balance);

        // balance should equal newbalance
        assertTrue(savingsAccount.getBalance() == balance + deposit);
        
        // depositing a negative value should not work
        // new balance == balance + deposit
        double newbalance = savingsAccount.getBalance();
        savingsAccount.deposit(-100);

        // trying to add -100 should not result in removing that value
        assertFalse(savingsAccount.getBalance() == newbalance - 100);

        // trying to add a negative value should result in no change
        assertTrue(savingsAccount.getBalance() == newbalance);
    }
    @Test

    void testWithdrawl(){

        double balance = 9000;
        double withdrawal = 1;
        SavingsAccount savingsAccount = new SavingsAccount(9933, new Customer(new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876)), new Date(4, 2, 1900), "4346-23421"), new Date(5, 5, 1955), balance, 0.25);

        // test that current balance ammount is what was set at object instantiation
        assertTrue(savingsAccount.getBalance() == balance);

        // withdraw from account
        savingsAccount.withdraw(withdrawal);

        // account should now be less than before by the withdrawl ammount
        // and thus no longer equal
        assertFalse(savingsAccount.getBalance() == balance);
        assertTrue(savingsAccount.getBalance() == balance - withdrawal);
        assertTrue(savingsAccount.getBalance() == 8999);

        // should not be able to withrdaw more than available
        double currentBalance = savingsAccount.getBalance();
        savingsAccount.withdraw(currentBalance + 1);

        // trying to withdraw more than available results in no action
        // nothing should have changed trying this illegal action
        assertTrue(savingsAccount.getBalance() == currentBalance);
    }
}

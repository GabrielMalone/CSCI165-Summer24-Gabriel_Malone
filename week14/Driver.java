// Gabriel Malone / CSCI165 / Week 14 / Summer 2024
import java.util.Scanner;

public class Driver {

    public static Scanner getInput = new Scanner(System.in);
    public static void main(String[] args) {

        // vars for customer, employee, account set up
        Date DOB = new Date(6, 6, 1996);
        Date joinDate = new Date(1, 1, 2000);
        Date createDate = new Date(1, 1, 2001);
        Person person1 = new Person("Goobert", "Malone", "6072620842", DOB);
        
        // ID-NOTWELLFORMEDEXCEPTION DEMO
        // invalid ID
        String custID = "DookMCboob";
        // instantiating a new customer with an invalid customerID
        System.out.println("\n" + "INVALID ID SET EXAMPLE:" + "\n");
        while (true){
            try {
                Customer customer = new Customer(person1, joinDate, custID);
                // check the ID, invalid at first, throws exception
                customer.setID(custID);
                // show successfull valid ID set
                System.out.println(customer);
                break;
                // will prompt for new atempt until correct
            } catch (IDNotWellFormedException e) {
                custID = getInput.nextLine();
            }
        }

        // INVALIDACCOUNTNUMBEREXCEPTION DEMO
        // invalid account number
        int accntNum = 11;
        System.out.println("\n" + "INVALID ACCNT NUM SET EXAMPLE:" + "\n");
        while (true){
            try {
                // same logic as above
                // instantiating an account with an invalid account number
                Account account = new Account(accntNum, new Customer(person1, joinDate, "g111"),createDate, 100.0);
                // show invalid account num set
                account.setAccountNumber(accntNum);
                System.out.println(account);
                break;
            } catch (InvalidAccountNumberException e) {
                String accntString = getInput.nextLine();
                // second loop and try/catch for the integer conversion attempt
                while (true){
                    try {
                        accntNum = Integer.valueOf(accntString);
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.print("Integers only: ");
                        accntString = getInput.nextLine();  
                    }
                }
            // needed for instantiating this customer in the account
            } catch (IDNotWellFormedException e) {
                // ID valid, no action needed
            }
        }
        
        // INVALIDBALANCEEXCEPTION (deposit) DEMO
        // invalid account number
        double  deposit = 1000000000;
        System.out.println("\n" + "INVALID DEPOSIT ATTEMPT EXAMPLE:" + "\n");
        while (true){
            try {
                // same logic as above
                // instantiating a checking account with $981.24 in deposits. 
                CheckingAccount checkingaccount = new CheckingAccount(accntNum, new Customer(person1, joinDate, "g111"), createDate, 981.24, 10);
                checkingaccount.deposit(deposit);
                System.out.println(checkingaccount);
                break;
            } catch (InvalidBalanceException e) {
                String depositString = getInput.nextLine();
                // second loop and try/catch for the double conversion attempt
                while (true){
                    try {
                        deposit = Double.valueOf(depositString);
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.print("monetary values only: ");
                        depositString = getInput.nextLine();  
                    }
                }
            // needed for instantiating this customer in the account
            // ID valid, no action needed
            } catch (IDNotWellFormedException e) {
            }// ID valid, no action needed
            catch (InvalidAccountNumberException e){}
        }
        
        // OVERDRAFTEXCEPTION (too many withrdawls) DEMO
        // attempting to exceed withdrawl limit (a relaxed 10,000 limit)
        System.out.println("\n" + "INVALID NUMBER OF WITHDRAWL ATTEMPTS EXAMPLE:" + "\n");
        // instantiating a checking account with $98001.24 in deposits. 
        try {
            SavingsAccount savingsAccount = new SavingsAccount(11111, new Customer(person1, joinDate, "g111") , createDate, 98001.24, 0.01);
            for (int times = 0 ;times <= SavingsAccount.maxNumWithdrawls + 1; times ++){
                // try to withrdaw more than one time beyond the set limit
                savingsAccount.withdraw(.01);
                System.out.println(savingsAccount);
            }
        // needed for the Customer object instantiation, but our ID is valid, so no action needed
        } catch (IDNotWellFormedException e) {}
        // no action needed, customer will just be prevented from withrdawing more
        catch (OverdraftException e){}
        // no action needed, customer will just be prevented from withrdawing more
        catch (InvalidBalanceException e){}
        // ID valid, no action needed
        catch (InvalidAccountNumberException e){}
      
     
        // OVERDRAFTEXCEPTION (exceed overdraft limit) DEMO
        System.out.println("\n" + "\n" + "EXCEEDING OVERDRAFT LIMIT EXAMPLE:" + "\n");
        // attempting to withdraw one dollar more than allowed
        double withrawAmnt = 111;
        while (true){
            try {
                // same logic as above
                // instantiating a checking account with $100.00 in deposits and $10 overdraft limit. 
                CheckingAccount checkingaccount = new CheckingAccount(11111, new Customer(person1, joinDate, "g111"), createDate, 100, 10);
                checkingaccount.withdraw(withrawAmnt);
                System.out.println(checkingaccount);
                break;
            } catch (OverdraftException e) {
                String withdrawString = getInput.nextLine();
                // second loop and try/catch for the double conversion attempt
                while (true){
                    try {
                        withrawAmnt = Double.valueOf(withdrawString);
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.print("monetary values only: ");
                        withdrawString = getInput.nextLine();  
                    }
                }
            // needed for instantiating this customer in the account
            // ID valid, no action needed
            } catch (IDNotWellFormedException e) {
            // ID valid, no action needed
            } catch (InvalidAccountNumberException e){}
        }

        // OVERDRAFTEXCEPTION (exceed withdrawal limit dollar amount) DEMO
        System.out.println("\n" + "\n" + "EXCEEDING WITHDRAWAL LIMIT DOLLAR AMOUNT EXAMPLE" + "\n");
        // attempting to withdraw one dollar more than allowed (10% of current balance)
        withrawAmnt = 21;
        while (true){
            try {
                // same logic as above
                // instantiating a savings account with $200.00 in deposits // 200 * .10 = 20 withdrawal limit
                SavingsAccount savingsAccount = new SavingsAccount(11111, new Customer(person1, joinDate, "g111"), createDate, 200, .01);
                savingsAccount.withdraw(withrawAmnt);
                System.out.println(savingsAccount);
                break;
            } catch (OverdraftException e) {
                String withdrawString = getInput.nextLine();
                // second loop and try/catch for the double conversion attempt if Invalid Overdraft Exception
                while (true){
                    try {
                        withrawAmnt = Double.valueOf(withdrawString);
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.print("monetary values only: ");
                        withdrawString = getInput.nextLine();  
                    }
                }
            }
            catch (InvalidBalanceException e) {
                String withdrawString = getInput.nextLine();
                // third loop and try/catch for the double conversion attempt if Invalid Balance Exception
                while (true){
                    try {
                        withrawAmnt = Double.valueOf(withdrawString);
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.print("monetary values only: ");
                        withdrawString = getInput.nextLine();  
                    }
                }
            // needed for instantiating this customer in the account
            // ID valid, no action needed
            } catch (IDNotWellFormedException e) {
            // ID valid, no action needed
            } catch (InvalidAccountNumberException e){}
        }

         // INVALIDBALANCEEXCEPTION (min balance) DEMO
         System.out.println("\n" + "\n" + "BALANCE WOULD FALL BELOW MIN BALANCE EXAMPLE " + "\n");
         withrawAmnt = 4;
         while (true){
            try {
                // same logic as above
                // instantiating a checking account with $200.00 in deposits and a 100 dollar min balance 
                SavingsAccount savingsAccount = new SavingsAccount(11111, new Customer(person1, joinDate, "g111"), createDate, 103, .01);
                savingsAccount.withdraw(withrawAmnt);
                System.out.println(savingsAccount);
                break;
            } catch (OverdraftException e) {
                String withdrawString = getInput.nextLine();
                // second loop and try/catch for the double conversion attempt if Invalid Overdraft Exception
                while (true){
                    try {
                        withrawAmnt = Double.valueOf(withdrawString);
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.print("monetary values only: ");
                        withdrawString = getInput.nextLine();  
                    }
                }
            }
            catch (InvalidBalanceException e) {
                String withdrawString = getInput.nextLine();
                // third loop and try/catch for the double conversion attempt if Invalid Balance Exception
                while (true){
                    try {
                        withrawAmnt = Double.valueOf(withdrawString);
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.print("monetary values only: ");
                        withdrawString = getInput.nextLine();  
                    }
                }
            // needed for instantiating this customer in the account
            // ID valid, no action needed
            } catch (IDNotWellFormedException e) {
            // ID valid, no action needed
            } catch (InvalidAccountNumberException e){}
        }
    }
}


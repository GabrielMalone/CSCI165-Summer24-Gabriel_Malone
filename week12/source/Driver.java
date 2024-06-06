// Gabriel Malone / CSCI165 / Week 12 / Summer 2024
/* 
import java.util.Scanner;
public class Driver {
  
    public static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        // vars for demonstrations
        String first = "Goob";
        String last = "Malone";
        String phone = "6072620842";
        String dept = "dorkville";
        int ID = 56789;
        int acntNum = 9933;
        double balance = 900;
        String custID = "434623421";
        Date DOB = new Date(6, 22, 1983);
        Date hired = new Date(1, 1, 2020);
        Date joined = new Date(4, 2, 1900);
        Date created = new Date(5, 5, 1955);
        Person person = new Person("Dorksu", "Malorkus","5555555555", new Date (12,25, 1876));

        // EMPLOYEE CONSTRUCTORS

        /**
        * Null pointer issues with the no argument Employee constructor since
        * this constructor does not provide default values.
        * will provide methods to prompt for the info with no argument constructors.
        * Also null pointer issues with the Person constructors not setting 
        * default values for missing arguments.
        * will again provide methods to get this info for
        * first name, last name, phone number in this super class.
        * The way I've done it seems fine compared to automatically setting defaults
        * but would be annoying for big batches of employees since it doesn't 
        * identify which employee's info is being requested.
        * 
        * went back and just made default vars since was too annoying with the prompts
        * but left the code in, commented out.
        */
        /*
        // no argument constructor
        // will be prompted for missing info // or default vars provided
        Employee e1 = new Employee();
        
        // overloaded constfuctor:
        // objects, int, and String constructor
        // no prompting required // no default vars required
   
        Employee e2 = new Employee(person, hired, ID, dept);

        // string argument constructor
        // will be prompted for missing info // or default vars provided
        Employee e3 = new Employee("Stella", "Cat");
 
        // print info
        Employee [] employees = new Employee[3];
        employees[0] = e1;
        employees[1] = e2;
        employees[2] = e3;
        for(Employee employee : employees){
            System.out.println("\n" + employee);
        }
        
        // CUSTOMER CONSTRUCTORS
        
        // overloaded constructorA
        Customer c1 = new Customer(first, last, phone, DOB, joined, custID);
        // overloaded constructorB
        Customer c2 = new Customer(person, joined, custID);
        // toString method demonstration
        System.out.println("\n" + c1);
        System.out.println("\n" + c2);
        // Customer Class inherited get methods from Person Class demonstration
        System.out.println("\n" + c1.getDOB());
        System.out.println(c1.getName());
        System.out.println(c1.getPhoneNumber());
        // Customer Class inherited set methods from Person Class
        c2.setFirstName("Dorkus");
        c2.setLastName("Malorkus");
        c2.setDOB(new Date (1, 1, 1901));
        c2.setPhoneNumber("9999999999");
        System.out.println("\n" + c2);

        // ACCOUNT CLASS DEMONSTRATION

        // overloaded constructorA
        Account custAccount1 = new Account(acntNum, c1, created, balance);
        System.out.println("\n" + custAccount1);
        // withdrawing some cash
        custAccount1.withdraw(100);
        // check balance
        System.out.println("\n" + "New Balance: " +custAccount1.getBalance());
        // get a new shady money manager
        custAccount1.setManager(e3);
        System.out.println("\n" + "Your new manager: " +custAccount1.getManager());

        // overloaded constructorB
        // making a new employee
        Employee e4 = new Employee(new Person ("Sir", "Makesalot", "5555555555", new Date(3, 3, 1933)), new Date(3, 4, 1933), 1, "CEO");
        // creating account info
        Account custAccount2 = new Account(acntNum, c1, e4, created);
        // depositing some cash
        custAccount2.deposit(1000000);
        System.out.println("\n" + custAccount2);
     
    }
}
*/
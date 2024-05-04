// Gabriel Malone / CSCI165 / Week 6 / Summer 2024

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class CustomerTests {
    
    Customer customer1 , customer2;

    @BeforeEach	// this annotation tells JUnit to run this method before each test
	public void setup(){
		customer1 = null; // kill current instance, hand over to garbage collector
		customer2 = null; // kill current instance, hand over to garbege collector
	}

    @Test 
    public void testConstructors(){
        // test that a an empyt constructor returs default values
        // name should == "unknown customer"
        customer1 = new Customer();
        assertTrue(customer1.getName().equals("unknown customer"));
        // phone should == "uknown"
        assertTrue(customer1.getPhone().equals("unknown customer"));
        // email should == "uknown"
        assertTrue(customer1.getEmail().equals("unknown customer"));
        // test overloaded constructor
        customer2 = new Customer("goobert", "goobert@gmail.com", "6072620842");
        assertTrue(customer2.getName().equals("GOOBERT DOE"));
        // name should == "goobert"
        assertTrue(customer2.getEmail().equals("goobert@gmail.com"));
        // email should == "goobert@gmail.com"
        assertTrue(customer2.getPhone().equals("6072620842"));
        // phone should == "6072620842"
    }

    @Test 
    public void testSetName(){
        // test that set name returns a different name than the original
        customer1 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogName = customer1.getName();
        customer1.setName("Goobert");
        assertFalse(customer1.getName().equals(ogName));
        // test that the new name equals the attempted set name
        assertTrue(customer1.getName().equals("GOOBERT DOE"));
    }

    @Test 
    public void testSetEmail(){
        // test that set name with valid email returns a different email than the original
        customer1 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail = customer1.getEmail();
        customer1.setEmail("goobert@gmail.com");
        assertFalse(customer1.getEmail().equals(ogEmail));
        // test that the new name equals the attempted set name
        assertTrue(customer1.getEmail().equals("goobert@gmail.com"));
        
        // test results when an invalid email entered - . at beginning of prefix
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail2 = customer2.getEmail();
        customer2.setEmail(".goobert@gmail.com");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail2));
       
        // test results when an invalid email entered  - . at end of prefix 
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail3 = customer2.getEmail();
        customer2.setEmail("goobert.@gmail.com");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail3));

        // test results when an invalid email entered - an email that's > 64 char
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail4 = customer2.getEmail();
        customer2.setEmail("goobertgoobertgoobertgoobertgoobertgoobertgoobertgoobert.@gmail.com");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail4));
        
        // test results when an invalid email entered - an email that has no @
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail6 = customer2.getEmail();
        customer2.setEmail("gabe#mail.com");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail6));

        // test results when an invalid email entered - consecutive dots in prefix
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail7 = customer2.getEmail();
        customer2.setEmail("gabe..@mail.com");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail7));

        // test results when an invalid email entered - invalid character in prefix
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail8 = customer2.getEmail();
        customer2.setEmail("ga(be@mail.com");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail8));

        // test results when an invalid email entered - @ in the domain
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail9 = customer2.getEmail();
        customer2.setEmail("gabe@mai@l.com");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail9));

        // test results when an invalid email entered - @ in the domain
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail10 = customer2.getEmail();
        customer2.setEmail("gabe@mail.@com");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail10));

        // test results when an invalid email entered - @ as first character
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail11 = customer2.getEmail();
        customer2.setEmail("@gabe@mail.com");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail11));

        // test results when an invalid email entered - @ as last character
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail12 = customer2.getEmail();
        customer2.setEmail("gabe@mail.com@");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail12));

        // test results when an invalid email entered - no @ in email
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogEmail14 = customer2.getEmail();
        customer2.setEmail("gabemail.com");
        // test that the invalid email did not take effect
        // email should equal the old email
        assertTrue(customer2.getEmail().equals(ogEmail14));

        // test results when a valid email entered with a special prefix
        customer2 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        customer2.setEmail("ga+be@mail.com");
        // email should equal new email
        assertTrue(customer2.getEmail() == "ga+be@mail.com");        
    }

    @Test 
    public void testSetPhone(){
        
        // test that setPhone returns a different phone number
        // than the original when a valid phone number entered
        customer1 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogPhone = customer1.getPhone();
        customer1.setPhone("1111111111");
        // test that new phone number doesn't equal old phone number
        assertFalse(customer1.getPhone().equals(ogPhone));
        // redundant, but test that the new phone number equals the attempted set phone nunmber
        assertTrue(customer1.getPhone().equals("1111111111"));

        // test result when an invalid phone number is entered - not all digits
        customer1 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogPhone2 = customer1.getPhone();
        customer1.setPhone("111111111a");
        // test that new phone number equals old phone number
        assertTrue(customer1.getPhone().equals(ogPhone2));

        // test result when an invalid phone number is entered - too many digits
        customer1 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogPhone3 = customer1.getPhone();
        customer1.setPhone("11111111111");
        // test that new phone number equals old phone number
        assertTrue(customer1.getPhone().equals(ogPhone3));

        // test result when an invalid phone number is entered - too few digits
        customer1 = new Customer("Gabe", "gabe@gmail.com", "2222222222");
        String ogPhone4 = customer1.getPhone();
        customer1.setPhone("111111111");
        // test that new phone number equals old phone number
        assertTrue(customer1.getPhone().equals(ogPhone4));
    }

}
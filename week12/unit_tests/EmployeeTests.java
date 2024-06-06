import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeTests {
	
	@Test
	public void testEqualsTrue(){

		Date dob1 		= new Date(3, 21, 1970);
		Date hired1 	= new Date(3, 21, 2021);
		Person p1 		= new Person("Frank", "Stein", "1234567890", dob1);
		Employee c1		= new Employee(p1, hired1, 123, "IT");

		Date dob2 		= new Date(3, 21, 1970);
		Date hired2 	= new Date(3, 21, 2021);
		Person p2 		= new Person("Frank", "Stein", "1234567890", dob2);
		Employee c2		= new Employee(p2, hired2, 123, "IT");

		assertTrue(c2.equals(c1));
	}

	@Test
	public void testCopyConstructor(){

		Date dob1 		= new Date(3, 21, 1970);
		Date hired1 	= new Date(3, 21, 2021);
		Person p1 		= new Person("Frank", "Stein", "1234567890", dob1);
		Employee c1		= new Employee(p1, hired1, 123, "IT");
		
		Employee c2		= new Employee(c1); // call copy constructor

		assertTrue(c1 != c2);				// identity check
		assertTrue(c1.equals(c2));			// state check
	}

	@Test 
	public void testDOBPrivacy(){
		// set up some test instances
		Date dob1 		= new Date(3, 21, 1970);
		Date hired1 	= new Date(3, 21, 2021);
		Person p1 		= new Person("Frank", "Stein", "1234567890", dob1);
		Employee c1		= new Employee(p1, hired1, 123, "IT");

		Date dob_test	= c1.getDOB();

		assertTrue(dob_test != dob1);		// identity check
		assertTrue(dob_test.equals(dob1));	// state check
	}

	@Test 
	public void testDateHiredPrivacy(){
		// set up some test instances
		Date dob1 		= new Date(3, 21, 1970);
		Date hired1 	= new Date(3, 21, 2021);
		Person p1 		= new Person("Frank", "Stein", "1234567890", dob1);
		Employee c1		= new Employee(p1, hired1, 123, "IT");

		Date dob_test	= c1.getHireDate();

		assertTrue(dob_test != hired1);		// identity check
		assertTrue(dob_test.equals(hired1));	// state check
	}

	@Test
	public void testEqualsFalse(){

		// set up some test instances
		Date dob1 		= new Date(3, 21, 1970);
		Date hired1 	= new Date(3, 21, 2021);
		Person p1 		= new Person("Frank", "Stein", "1234567890", dob1);
		Employee c1		= new Employee(p1, hired1, 123, "IT");

		Date dob2 		= new Date(3, 21, 1970);
		Date hired2 	= new Date(3, 21, 2021);
		Person p2 		= new Person("Frank", "Stein", "1234567890", dob2);
		Employee c2		= new Employee(p2, hired2, 123, "IT");

		// Last Name
		System.out.println(c1);
		System.out.println(c2);
		c1.setLastName("Whitener");
		assertFalse(c2.equals(c1));
		c1.setLastName("Stein");
		assertTrue(c2.equals(c1));

		// First Name
		c1.setFirstName("Ken");
		assertFalse(c2.equals(c1));
		c1.setFirstName("Frank");
		assertTrue(c2.equals(c1));

		// DOB
		c1.setDOB(new Date(1, 2, 2013));
		assertFalse(c2.equals(c1));
		c1.setDOB(new Date(3, 21, 1970));
		assertTrue(c2.equals(c1));

		// Date Joined
		c1.setHireDate(new Date(1, 2, 2013));
		assertFalse(c2.equals(c1));
		c1.setHireDate(new Date(3, 21, 2021));
		assertTrue(c2.equals(c1));

		// Phone Number
		c1.setPhoneNumber("1112223333");
		assertFalse(c2.equals(c1));
		c1.setPhoneNumber("1234567890");
		assertTrue(c2.equals(c1));
	}
}

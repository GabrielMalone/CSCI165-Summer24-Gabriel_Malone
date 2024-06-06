import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomerTests {

	String msg = "";

	@Test
	public void testConstructors(){

		Date dob 	= new Date(1, 2, 2021);
		Person p 	= new Person("Frank", "Stein", "1111111111", dob);
		Date joined = new Date(3, 4, 2021);
		Customer c2	= new Customer(p, joined, "12345");

		// test for constructor privacy leak on data joined
		Date joinedTest = c2.getDateJoined();
		msg = "Date joined privacy leak. Identities are the same";
		assertFalse(msg, joinedTest == joined);
		msg = "Identies are different but states are not logically equal";
		assertTrue(msg, joinedTest.equals(joined));

		// test privacy leak on setting date
		joined = new Date(3, 4, 2021);
		c2.setDateJoined(joined);
		msg = "Date joined privacy leak. Identities are the same";
		assertFalse(msg, c2.getDateJoined() == joined);
		msg = "Identies are different but states are not logically equal";
		assertTrue(msg, c2.getDateJoined().equals(joined));
	}

	@Test
	public void testCopyConstructor(){

		// set up some test instances
		Date dob1 		= new Date(3, 21, 1970);
		Date joined1 	= new Date(3, 21, 2021);
		Person p1 		= new Person("Frank", "Stein", "1234567890", dob1);
		Customer c1		= new Customer(p1, joined1, "123");

		Customer c2		= new Customer(c1); // call copy constructor

		assertTrue(c1 != c2);				// identity check
		assertTrue(c1.equals(c2));			// state check
	}

	@Test
	public void testSomeInheritedMethods(){

		Date dob 	= new Date(1, 2, 2021);
		Person p 	= new Person("Frank", "Stein", "1111111111", dob);
		Date joined = new Date(3, 4, 2021);
		Customer c2	= new Customer(p, joined, "12345");

		// test name getters
		String first	= c2.getFirstName();
		String last		= c2.getLastName();
		String full		= c2.getName();
		assertTrue(full.equals(first + " " + last));

		// test phone number getter
		String phone	= c2.getPhoneNumber();
		assertTrue(phone.equals("(111)111-1111"));
	}
	
	@Test
	public void testEqualsTrue(){

		Date dob1 		= new Date(3, 21, 1970);
		Date joined1 	= new Date(3, 21, 2021);
		Person p1 		= new Person("Frank", "Stein", "1234567890", dob1);
		Customer c1		= new Customer(p1, joined1, "123");

		Date dob2 		= new Date(3, 21, 1970);
		Date joined2 	= new Date(3, 21, 2021);
		Person p2 		= new Person("Frank", "Stein", "1234567890", dob2);
		Customer c2		= new Customer(p2, joined2, "123");

		msg = "Customers are not logically equal";
		assertTrue(c2.equals(c1));

		c2.setFirstName("Ken");
		c1.setFirstName("Ken");
		msg = "After changing both test instances name, customers are not logically equal";
		assertTrue(msg, c2.equals(c1));
	}

	@Test 
	public void testDOBPrivacy(){
		// set up some test instances
		Date dob1 		= new Date(3, 21, 1970);
		Date joined1 	= new Date(3, 21, 2021);
		Person p1 		= new Person("Frank", "Stein", "1234567890", dob1);
		Customer c1		= new Customer(p1, joined1, "123");

		Date dob_test	= c1.getDOB();

		msg = "DOB privacy leak. Identities are the same";
		assertTrue(msg, dob_test != dob1);	// identity check
		msg = "Identies are different but states are not logically equal";
		assertTrue(dob_test.equals(dob1));	// state check
	}

	@Test 
	public void testDateJoinedPrivacy(){
		// set up some test instances
		Date dob1 		= new Date(3, 21, 1970);
		Date joined1 	= new Date(3, 21, 2021);
		Person p1 		= new Person("Frank", "Stein", "1234567890", dob1);
		Customer c1		= new Customer(p1, joined1, "123");

		Date dob_test	= c1.getDateJoined();

		msg = "Date joined privacy leak. Identities are the same";
		assertTrue(msg, dob_test != joined1);		// identity check
		msg = "Identies are different but states are not logically equal";
		assertTrue(msg, dob_test.equals(joined1));	// state check
	}

	@Test
	public void testEqualsFalse(){

		// set up some test instances
		Date dob1 		= new Date(3, 21, 1970);
		Date joined1 	= new Date(3, 21, 2021);
		Person p1 		= new Person("Frank", "Stein", "1234567890", dob1);
		Customer c1		= new Customer(p1, joined1, "123");

		Date dob2 		= new Date(3, 21, 1970);
		Date joined2 	= new Date(3, 21, 2021);
		Person p2 		= new Person("Frank", "Stein", "1234567890", dob2);
		Customer c2		= new Customer(p2, joined2, "123");

		// Last Name
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
		c1.setDateJoined(new Date(1, 2, 2013));
		assertFalse(c2.equals(c1));
		c1.setDateJoined(new Date(3, 21, 2021));
		assertTrue(c2.equals(c1));

		// Phone Number
		c1.setPhoneNumber("1112223333");
		assertFalse(c2.equals(c1));
		c1.setPhoneNumber("1234567890");
		assertTrue(c2.equals(c1));
	}
}

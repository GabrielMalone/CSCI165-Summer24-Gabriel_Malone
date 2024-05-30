import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderItemTests {
	OrderItem orderItemOne;				// order item
	MenuItem menuItemOne, menuItemTwo;	// menu items
	String msg;							// test message

	@BeforeEach
	public void setup() {
		orderItemOne= null;
		menuItemOne	= null;
		menuItemTwo = null;
	}

	@Test
	public void testMenuItemPrivacyProtection() {
		// create a menu item and add it to an order item
		menuItemOne	= new MenuItem("Burger", 5.99, 700);		// name, price, calories
		orderItemOne= new OrderItem(menuItemOne, 2);			// item, quantity

		// Ensure that getMenuItem() returns a copy of the MenuItem
		MenuItem menuItemOneCopy = orderItemOne.getMenuItem();	// get the menu item
		msg = "The states are not the same. They should be";
		assertTrue(msg, menuItemOneCopy.equals(menuItemOne));	// state check should be equal
		msg = "The identities are the same. They should not be";
		assertFalse(msg, menuItemOneCopy == menuItemOne);		// identity check should be false

		// test two
		menuItemOne	= new MenuItem("Hakarl", 11.99, 300);		// name, price, calories
		orderItemOne= new OrderItem(menuItemOne, 7);			// item, quantity

		// Ensure that getMenuItem() returns a copy of the MenuItem
		menuItemOneCopy = orderItemOne.getMenuItem();			// get the menu item
		msg = "The states are not the same. They should be";
		assertTrue(msg, menuItemOneCopy.equals(menuItemOne));	// state check should be equal
		msg = "The identities are the same. The should not be";
		assertFalse(msg, menuItemOneCopy == menuItemOne);		// identity check should be false
	}

	@Test
	public void testConstructorPrivacyProtection(){
		// test constructor privacy protection
		menuItemOne = new MenuItem("Tacos", 9.99, 623);		// create a menu item
		orderItemOne= new OrderItem(menuItemOne, 3);		// create an order item
		menuItemOne.setName("Pizza");						// change the name of the menu item
		String name = orderItemOne.getMenuItem().getName();	// get the name of the menu item
		msg = "The name was incorrectly modified. Should be the same as the original name";
		assertFalse(msg, name.equals("Pizza"));				// the name should not have changed
		
		// test constructor privacy protection
		menuItemOne = new MenuItem("Tacos", 9.99, 623);		// create a menu item
		orderItemOne= new OrderItem(menuItemOne, 3);		// create an order item
		menuItemOne.setPrice(3);							// change the name of the menu item
		double price = orderItemOne.getMenuItem().getPrice();// get the name of the menu item
		msg = "The price was incorrectly modified. Should be the same as the original price";
		assertFalse(msg, price == 3);						// the name should not have changed
	}

	@Test
	public void testUpdateQuantity() {
		// create a menu item and add it to an order item
		menuItemOne	= new MenuItem("Burger", 5.99, 700);	// name, price, calories
		orderItemOne= new OrderItem(menuItemOne, 2);		// item, quantity

		// Update the quantity of the order item with a positive number
		orderItemOne.updateQuantity(3);						// increase the quantity by 3
		msg = "The quantity was not updated correctly. Should be 5";
		assertTrue(msg, orderItemOne.getQuantity() == 5);	// was the quantity updated correctly?

		// test two. Allow negative numbers whose absolute value is less than or equal to the current quantity
		menuItemOne	= new MenuItem("Hakarl", 11.99, 300);	// name, price, calories
		orderItemOne= new OrderItem(menuItemOne, 7);		// item, quantity
		orderItemOne.updateQuantity(-5);					// decrease the quantity by 5
		msg = "The quantity was not updated correctly. Should be 2";
		assertTrue(msg, orderItemOne.getQuantity() == 2);	// was the quantity updated correctly?

		// test three. Prohibit negative numbers whose absolute value is greater than the current quantity
		menuItemOne	= new MenuItem("Hakarl", 11.99, 300);	// name, price, calories
		orderItemOne= new OrderItem(menuItemOne, 7);		// item, quantity
		orderItemOne.updateQuantity(-10);					// decrease the quantity by 10
		msg = "The quantity was updated when it should remain unmodified. Should be 7";
		assertTrue(msg, orderItemOne.getQuantity() == 7);	// was the quantity updated correctly?
	
	}

	@Test
	public void testEqualsMethod(){
		// create a menu item and add it to an order item
		menuItemOne	= new MenuItem("Burger", 5.99, 700);	// name, price, calories
		orderItemOne= new OrderItem(menuItemOne, 2);		// item, quantity

		// create a second order item with the same menu item and quantity
		OrderItem itemTwo = new OrderItem(menuItemOne, 2);	// item, quantity
		msg = "The order items are not equal. They should be";
		assertTrue(msg, orderItemOne.equals(itemTwo));		// should be equal

		// update the quantity of the second order item. They should no longer be equal
		itemTwo.updateQuantity(3);							// increase the quantity by 3
		msg = "The order items are equal. They should not be";
		assertFalse(msg, orderItemOne.equals(itemTwo));		// should not be equal

		// update the quantity of the first order item. They should now be equal
		orderItemOne.updateQuantity(3);						// increase the quantity by 3
		msg = "The order items are not equal. They should be";
		assertTrue(msg, orderItemOne.equals(itemTwo));		// should be equal

		// create a third order item with a different menu item and quantity
		menuItemTwo = new MenuItem("Hakarl", 11.99, 300);	// name, price, calories
		OrderItem itemThree = new OrderItem(menuItemTwo, 7);// item, quantity
		msg = "The order items are equal. They should not be";
		assertFalse(msg, orderItemOne.equals(itemThree));	// should not be equal
	}
}

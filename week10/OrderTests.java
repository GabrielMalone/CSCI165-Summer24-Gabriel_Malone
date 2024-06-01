// Gabriel Malone / CS165 / Summer 2024 / Week 10

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class OrderTests {
    
    Order       order1, order2;
    OrderItem   orderItem1, orderItem2, orderItem3;
    MenuItem    menuItemOne, menuItemTwo, menuItemThree;
    Customer    customer;
    Date        date1, date2;

    @BeforeEach
    public void setup() {
		order1      = null;
		order2      = null;
        orderItem1  = null;
        orderItem2  = null;
        menuItemOne = null;
        menuItemTwo = null;
        customer    = null;
	}
    @Test
	public void testOrderEqualsMethodA() {
        menuItemOne = new MenuItem("Burger", 12.99, 9000);
        menuItemTwo = new MenuItem("Fries", 1.89, 10);
        // Order items different by quantity only
        orderItem1 = new OrderItem(menuItemOne, 2);
        orderItem2 = new OrderItem(menuItemOne, 1);
        // Orders with same sized carts and with same menuitem but different quantities of that item should not be equal
        order1 = new Order();
        order2 = new Order();
        order1.shoppingCart.add(orderItem1);
        order2.shoppingCart.add(orderItem2);
        assertFalse(order1.equals(order2));
    }
    @Test
	public void testOrderEqualsMethodB() {
        menuItemOne = new MenuItem("Burger", 12.99, 9000);
        menuItemTwo = new MenuItem("Fries", 1.89, 10);
        // Order items with same menuitems and quantity
        orderItem1 = new OrderItem(menuItemOne, 2);
        orderItem2 = new OrderItem(menuItemOne, 2);
        // Orders with same sized carts and with same menuitem and with same quantities of that item should be equal
        order1 = new Order();
        order2 = new Order();
        order1.shoppingCart.add(orderItem1);
        order2.shoppingCart.add(orderItem2);
        assertTrue(order1.equals(order2));	
    }
    @Test
	public void testOrderEqualsMethodC() {
        menuItemOne = new MenuItem("Burger", 12.99, 9000);
        menuItemTwo = new MenuItem("Fries", 1.89, 10);
        menuItemThree = new MenuItem("Burgre", 12.99, 9000);
        orderItem1 = new OrderItem(menuItemOne, 2);
        orderItem2 = new OrderItem(menuItemOne, 2);
        orderItem3 = new OrderItem(menuItemThree, 10);
        // Orders with differently sized carts should not be equal
        order1 = new Order();
        order2 = new Order();
        order1.shoppingCart.add(orderItem1);
        order2.shoppingCart.add(orderItem2);
        order2.shoppingCart.add(orderItem3);
        assertFalse(order1.equals(order2));	
    }
    @Test
	public void testConstructorPrivacyProtection() {
        menuItemOne = new MenuItem("Burger", 12.99, 9000);
        orderItem1 = new OrderItem(menuItemOne, 2);
        orderItem2 = new OrderItem(menuItemOne, 2);
        // Orders with differently sized carts should not be equal
        order1 = new Order();
        order1.shoppingCart.add(orderItem1);
        // deep copy of order1 to order 2
        order2 = new Order(order1);
        // order1 and order2 should not have the same memory address
        assertFalse(order1 == order2);
        // order1 and order2 should have the same variable states though
        assertTrue(order1.equals(order2));
    }
    @Test
	public void testgetCustomerPrivacy() {
        // get customer privacy test
        customer = new Customer("Gabe Malone", "gabe@gabe.com", "6072620842");
        order1 = new Order(customer);
        // customer returned from order should not be same memory address
        Customer returnedCustomer = order1.getCustomer();
        assertFalse(returnedCustomer == customer);
        // but customer should have same variable states as returnedCustomer
        assertTrue(returnedCustomer.equals(customer));
    }
    @Test
	public void testgetCartPrivacy() {
        // create an Order with shopping cart OrderItems
        menuItemOne = new MenuItem("Burger", 12.99, 9000);
        menuItemTwo = new MenuItem("Fries", 1.89, 10);
        menuItemThree = new MenuItem("Burgre", 12.99, 9000);
        orderItem1 = new OrderItem(menuItemOne, 2);
        orderItem2 = new OrderItem(menuItemOne, 2);
        orderItem3 = new OrderItem(menuItemThree, 10);
        order1 = new Order();
        order1.shoppingCart.add(orderItem1);
        order1.shoppingCart.add(orderItem2);
        order1.shoppingCart.add(orderItem3);
        // cart returned from order should not be same memory address
        ArrayList <OrderItem> returnedCart = order1.getCart();
        assertFalse(returnedCart == order1.shoppingCart);
        // the carts objects should not have the same memory address either
        assertFalse(returnedCart.equals(order1.shoppingCart));
        // but they should have the same variable states
        OrderItem item1 = returnedCart.get(0);
        OrderItem item2 = order1.shoppingCart.get(0);
        assertTrue(item1.equals(item2));
    }
    @Test
	public void testgetDatePrivacy() {
        // create a new Date object with today's current time info
        Date today = new Date();
        today = Date.dateInitializer();
        // create a new Order object which will also create a date object with currrent time info
        order1 = new Order();
        Date OrderDate = order1.getDate();
        // today and orderDate should not have the same memory address
        assertFalse(OrderDate == today);
        // but today and orderDate should have the same variable states
        assertTrue(today.equals(OrderDate));
    }
}


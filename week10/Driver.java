// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

public class Driver{

	public static Customer customer;
	public static OrderItem orderItem;
	public static Order order;
	// main program loop
	public static void main(String[] args) {
		// run until machine turned off
		/* 
		while(true){
			
			// create new shoppingcart
			orderItem = new OrderItem();
			order = new Order();
			// clear screen and show menu
			TerminalDisplay.clearSequence();
			// clear any carts from previous orders
			orderItem.clearCarts();
			// create new customer and get new customer info
			customer = new Customer();
			// take order
			orderItem.takeOrder(customer);
			// save receipt info
			order.writeToFile();
			// display receipt on computer terminal
			order.displayReceipt();
			 
		}
		*/
		customer = new Customer();
		MenuItem menuItemOne = new MenuItem("Burger", 5.99, 700);
		OrderItem orderItemOne = new OrderItem(menuItemOne, 2);	
		System.out.println(orderItemOne);

	}
} 
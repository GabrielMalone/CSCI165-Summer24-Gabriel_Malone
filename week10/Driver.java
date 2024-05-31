// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

public class Driver{

	public static Customer customer;
	public static Order order;
	// main program loop
	public static void main(String[] args) {
		// run until machine turned off
		while(true){
			// create new shoppingcart
			order = new Order();
			// clear screen and show menu
			TerminalDisplay.clearSequence();
			// clear any carts from previous orders
			order.clearCarts();
			// create new customer and get new customer info
			customer = new Customer();
			// take order
			order.takeOrder(customer);
			// save receipt info
			order.writeToFile();
			// display receipt on computer terminal
			order.displayReceipt();
		}
	}
} 
	// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

	import java.util.ArrayList;
	import java.util.HashMap;

   public class Driver{

		public static Customer customer;

		// main program loop
		public static void main(String[] args) {
			// run until machine turned off
			while(true){
				// clear screen and show menu
				TerminalDisplay.clearSequence();
				// clear any carts
				OrderItem.cartMap = new HashMap<MenuItem, Integer>(10);
				OrderItem.orderMap = new HashMap<Integer, MenuItem>(10);
				OrderItem.removeMap = new HashMap<Integer, MenuItem>(10);
				// create new customer and get new customer info
				customer = new Customer();
				// take order // clear shopping cart
				ArrayList<MenuItem> shoppingCart = OrderItem.takeOrder(customer);
				// today's date for receipt
				Date today = Date.dateInitializer();
				// print receipt // save receipt info
				Order.printOrder(shoppingCart, customer, today);
			}
		}
	
}
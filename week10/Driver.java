	// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

   public class Driver{

		public static Customer customer;
		public static OrderItem orderItem;
		public static Order order;
		public static Date today;

		// main program loop
		public static void main(String[] args) {
			// run until machine turned off
			while(true){
				// create new shoppingcart
				orderItem = new OrderItem();
				order = new Order();
				today = Date.dateInitializer();
				// clear screen and show menu
				TerminalDisplay.clearSequence();
				// clear any carts
				orderItem.clearCarts();
				// create new customer and get new customer info
				customer = new Customer();
				// take order // 
				orderItem.takeOrder(customer);
				// print receipt // save receipt info
				order.printOrder();
				// display receipt //
				order.displayReceipt();
			}
		}
	
}
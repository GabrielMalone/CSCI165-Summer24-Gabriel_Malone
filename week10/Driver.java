// Gabriel Malone / CSCI65 / Week 10 / Summer 2024



public class Driver{
	
	public static Order order;
	public static Customer customer;
	// main program loop
	public static void main(String[] args) {
		// run until machine turned off
		while(true){
			// create Customer
			customer = new Customer();
			// create Order
			order = new Order();
			// take order
			order.takeOrder();
			// save and display receipt info
			order.writeToFile();
		}
	}
} 
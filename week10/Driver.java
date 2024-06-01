// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

import java.util.Scanner;

public class Driver{
	
	public static Order order;
	public static Scanner scanner;
	// main program loop
	public static void main(String[] args) {
		// run until machine turned off
		while(true){
			scanner = new Scanner(System.in);
			// create new shoppingcart
			order = new Order();
			// take order
			order.takeOrder();
			// save and display receipt info
			order.writeToFile();
		}
	}
} 
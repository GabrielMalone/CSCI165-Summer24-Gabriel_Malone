// Gabriel Malone // CSCI165 // Week 13 / Summer 2024

import java.awt.Color;
import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {

		// Task 3 
		// circles
		Circle c1 = new Circle(3.2);
		Circle c2 = new Circle(5.5);
		Circle c3 = new Circle(10.2);
		// rectangles
		Rectangle r1 = new Rectangle(3.2, 4.4);
		Rectangle r2 = new Rectangle(5.5, 10.2);
		Rectangle r3 = new Rectangle(10.5, 12.5);
		// squares
		Square s1 = new Square(5.5);
		Square s2 = new Square(4.5);
		Square s3 = new Square(14);
		// add to array
		ArrayList<Shape> shapes = new ArrayList<>();
		shapes.add(c1);shapes.add(c2);shapes.add(c3);
		shapes.add(r1);shapes.add(r2);shapes.add(r3);
		shapes.add(s1);shapes.add(s2);shapes.add(s3);
		// call method
		Shape largestShape = findLargest(shapes);
		System.out.println(largestShape);
		System.out.println(c2.getArea());
		
	}

	/**
	 * Task 2 method
	 */
	public static void testClasses(){ 
		Point p     = new Point(1,2);
		Shape s1    = new Circle(5.5, Color.red, false, p);
		System.out.println(s1);                 // calling the Circle's toPrint method since both Shape and Circle share this method, it can call the circle's version.
		System.out.println(s1.getArea());       // calling the Circle's version 
		System.out.println(s1.getPerimeter());  // calling the Circle's version
		System.out.println(s1.getColor());      // super's version
		System.out.println(s1.isFilled());      // super's version
		//System.out.println(s1.getRadius());   // not allowed to call this since this method only in the Cirlce class
		Circle c1 = (Circle)s1;                 // downcasting
		System.out.println(c1);                 // Circle's toString method
		System.out.println(c1.getArea());       // calling the Circle's version
		System.out.println(c1.getPerimeter());  // calling the Circle's version
		System.out.println(c1.getColor());      // super's version
		System.out.println(c1.isFilled());      // super's version
		System.out.println(c1.getRadius());     // circle's version
		Shape s3 = new Rectangle(1.0, 2.0, Color.red, false, p);
		System.out.println(s3);                 // calling the Rectangles's toPrint method since both Shape and Circle share this method, it can call the circle's version.
		System.out.println(s3.getArea());       // calling the Rectangles getArea method
		System.out.println(s3.getPerimeter());  // calling the Rectangles getPerimeter method
		System.out.println(s3.getColor());      // calling the super's getColor method
		//System.out.println(s3.getLength());   // can't call this method from Shape class since Shape does not have this method. 
		Rectangle r1 = (Rectangle)s3;           // downcasting
		System.out.println(r1);                 // calling the Rectangles's toPrint method 
		System.out.println(r1.getArea());       // calling the Rectangles getArea method
		System.out.println(r1.getColor());      // calling the super's getColor method
		System.out.println(r1.getHeight());     // calling Rectangle's getHeight
		Shape s4 = new Square(6.6);     
		System.out.println(s4);                 // calling Square's toString method
		System.out.println(s4.getArea());       // calling Rectangle's getArea method      
		System.out.println(s4.getColor());      // calling Shape's getColor  method
		//System.out.println(s4.getSide());     // Shape does not have a getSide method
		Rectangle r2 = (Rectangle)s4;           // downcasting
		System.out.println(r2);                 // calling Square's toString method
		System.out.println(r2.getArea());       // calling Rectangle's getArea method  
		System.out.println(r2.getColor());      // calling Shape's getColor  method
		// System.out.println(r2.getSide());    // Rectangle does not have a getSide method
		// System.out.println(r2.getLength());  // getLength does not exist in any method
		//Shape s2 = new Shape();               // can't instantiate an abstract class on its own
		Square sq1 = (Square) r2;
		System.out.println(sq1);                // calling Square's toString method
		System.out.println(sq1.getArea());      // calling Rectangle's getArea method
		System.out.println(sq1.getColor());     // calling Shape's getColor  method
		System.out.println(sq1.getSide());      // calling Square's getSide method
		// System.out.println(sq1.getLength()); // getLength does not exist in any method}
	}

	public static Shape findLargest(ArrayList<Shape> shapes){
		// var to hold the current largest calcualted area
		double largest_area = 0;
		// place to hold the current largest shape
		Shape [] largestShape = new Shape[1];
		// iterate through shapes in array
		for (Shape shape : shapes){
			// get area of shape
			if (shape.getArea() > largest_area){
				// if area larger than current largest, update that var
				largest_area = shape.getArea();
				// and place the shape in the array
				largestShape[0] = shape;
			}
		}
		// return the largest shape found
		return largestShape[0];
	} 
}
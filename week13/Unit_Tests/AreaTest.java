import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class AreaTest {

    @Test
    void testGetArea() {
        // circles
		Circle c1 = new Circle(5.5);
        // circle with radius 5.5 should have area of 5.5^2 * 3.14 --> 95.03..
        assertTrue(c1.getArea() == 95.03317777109125);
        // recatangle with area 5, 10 should equal 5*10 -- > 50.
        Rectangle r2 = new Rectangle(5, 10);
        assertTrue(r2.getArea() == 50.0);
        // square with area 5, 5 should equal 5*5 -- > 25
        Square s3 = new Square(5);
        assertTrue(s3.getArea() == 25.0);

        // of these three shapes, circle one has the largest area.
        // comparing all the shapes should result in the circle being declared the shape
        // with the largest area.

        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(c1);shapes.add(r2);shapes.add(s3);
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
		// largest area found should be the circle's area
		assertTrue(largest_area ==  c1.getArea());
	} 
}

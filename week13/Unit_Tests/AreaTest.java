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
        // add shapes to array
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(c1);shapes.add(r2);shapes.add(s3);
        // call largestShape method
        Shape largestShape = Driver.findLargest(shapes);
        // largestShape should equal the c1 Circle
        largestShape.equals(c1);
	}

    @Test
    void testTotalArea(){
        Circle c1 = new Circle(5.5);
        // circle with radius 5.5 should have area of 5.5^2 * 3.14 --> 95.03..
        assertTrue(c1.getArea() == 95.03317777109125);
        // recatangle with area 5, 10 should equal 5*10 -- > 50.
        Rectangle r2 = new Rectangle(5, 10);
        assertTrue(r2.getArea() == 50.0);
        // square with area 5, 5 should equal 5*5 -- > 25
        Square s3 = new Square(5);
        assertTrue(s3.getArea() == 25.0);
        // total area should be (95.03317777109125 + 50 + 25 == 170.03317777109125)
        // var to hold the total area
        double total_area = c1.getArea() + r2.getArea() + s3.getArea();
        // add shapes to array
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(c1);shapes.add(r2);shapes.add(s3);
        // call totalArea method
        double totalArea = Driver.totalArea(shapes);
        // total_area and totalArea should be equal
        assertTrue(totalArea == total_area);
    }
    
}

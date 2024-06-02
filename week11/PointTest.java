// Gabriel Malone / CSCI165 / Summer 2024 / Week 11

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PointTest {

    Point pointOne, pointTwo;
    double distance;

    @BeforeEach
	public void setup() {
		pointOne= null;
		pointTwo= null;
        distance= 0;
	}
    
    @Test
    void testDistance() {
    pointOne = new Point(1, 2);
    pointTwo = new Point(4, 6);
    distance = pointOne.distance(pointTwo);
    assertTrue(distance == 5);
    }

    @Test
    void testDistance2() {
        // pythagorean triples
        // origin
        pointOne = new Point(3, 4);
        distance = pointOne.distance();
        assertTrue(distance == 5);
    }

    @Test
    void testDistance3() {
        // pythagorean triples
        // points given 
        pointOne = new Point(1, 2);
        distance = pointOne.distance(4, 6);
        assertTrue(distance == 5);
    }

    @Test
    void testDistance4() {
        // pythagorean triples
        pointOne = new Point(1, 2);
        distance = pointOne.distance(4, 6);
        assertTrue(distance == 5);
    }

    @Test
    void testEquals() {
        pointOne = new Point(5, 10);
        pointTwo = new Point(5, 10);
        assertTrue(pointOne.equals(pointTwo));
        assertFalse(pointOne == pointTwo);

    }

    @Test
    void testCopyProtectionOfPointObject() {
        pointOne = new Point(5, 10);
        pointTwo = new Point(pointOne);
        assertTrue(pointOne.equals(pointTwo));
        assertFalse(pointOne == pointTwo);
    }

    @Test
    void testGetCoords() {
        int x = 5, y = 10;
        pointOne = new Point(x, y);
        assertTrue(pointOne.getCoords()[0] == pointOne.getX());
        assertTrue(pointOne.getCoords()[1] == pointOne.getY());
    }

    @Test
    void testGetX() {
        int x = 5;
        pointOne = new Point();
        pointOne.setX(x);
        assertTrue(pointOne.getX() == x);
    }

    @Test
    void testGetY() {
        int y = 5;
        pointOne = new Point();
        pointOne.setY(y);
        assertTrue(pointOne.getY() == y);
    }

    @Test
    void testSetCoords() {
        int x = 5, y =10;
        pointOne = new Point();
        pointOne.setCoords(x, y);
        int [] coords = pointOne.getCoords();
        assertTrue(coords[0] == pointOne.getX());
        assertTrue(coords[1] == pointOne.getY());
    }

    @Test
    void testSetX() {
        int x = 5;
        pointOne = new Point();
        pointOne.setX(x);
        assertTrue(pointOne.getX() == x);
        pointOne.setX(10);
        assertFalse(pointOne.getX() == x);
    }

    @Test
    void testSetY() {
        int y = 5;
        pointOne = new Point();
        pointOne.setY(y);
        assertTrue(pointOne.getY() == y);
        pointOne.setY(10);
        assertFalse(pointOne.getY() == y);
    }

    @Test
    void testToString() {
        String idealString = "(X: 5, Y: 10)";
        int x = 5, y = 10;
        pointOne = new Point(x, y);
        assertTrue(idealString.equals(pointOne.toString()));
        String idealString2 = "(X: -5, Y: -10)";
        int x1 = -5, y1 = -10;
        pointTwo = new Point(x1, y1);
        assertTrue(idealString2.equals(pointTwo.toString()));
    }
}

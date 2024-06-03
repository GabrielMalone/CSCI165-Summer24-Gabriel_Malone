// Gabriel Malone / CSCI165 / Summer 2024 / Week 11

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PointTest {

    Point pointOne, pointTwo;
    MoveablePoint movePoint;
    Point3D point3dOne;
    double distance;

    @BeforeEach
	public void setup() {
		pointOne    = null;
		pointTwo    = null;
        movePoint   = null;
        point3dOne  = null;
        distance    = 0;
	}

    @Test
    void copyConstructorTest() {
    pointOne = new Point(3,4);
    pointTwo = new Point(pointOne);
    assertTrue(pointTwo.getX() == 3);
    assertTrue(pointTwo.getX() == pointOne.getX());
    assertTrue(pointTwo.getY() == 4);
    assertTrue(pointTwo.getY() == pointOne.getY());
    assertTrue(pointOne.equals(pointTwo));
    assertFalse(pointOne == pointTwo);
    }
    
    @Test
    void testConstructorA() {
    pointOne = new Point();
    assertTrue(pointOne.getX() == 0);
    assertTrue(pointOne.getY() == 0);
    }

    @Test
    void testConstructorB() {
    pointOne = new Point(5, 10);
    assertTrue(pointOne.getX() == 5);
    assertTrue(pointOne.getY() == 10);
    }

    @Test
    void testDistanceA() {
    // testing distance from two Point objects
    // pythagorean triples
    pointOne = new Point(1, 2);
    pointTwo = new Point(4, 6);
    distance = pointOne.distance(pointTwo);
    assertTrue(distance == 5);
    }

    @Test
    void testDistanceB() {
        // pythagorean triples
        // Testing distance from one Point object 
        // and no arguments in distance method 
        // thus distance from origin 0,0
        pointOne = new Point(3, 4);
        distance = pointOne.distance();
        assertTrue(distance == 5);
    }

    @Test
    void testDistanceC() {
        // pythagorean triples
        // Testing distance from one Point object 
        // and distance to points given 
        // in arguments of distance method
        pointOne = new Point(1, 2);
        distance = pointOne.distance(4, 6);
        assertTrue(distance == 5);
    }

    @Test
    void StaticTestDistance() {
        // pythagorean triples
        // Testing the class distance method using two
        // point objects
        Point pointOne = new Point(1, 2);
        Point pointTwo = new Point(4, 6);
        distance = Point.distance(pointOne, pointTwo);
        assertTrue(distance == 5);
    }

    @Test
    void testEqualsA() {
        pointOne = new Point(5, 10);
        pointTwo = new Point(5, 10);
        assertTrue(pointOne.equals(pointTwo));
        assertFalse(pointOne == pointTwo);
    }

    @Test
    void testEqualsB() {
        // Testing equals method on Point Objects that
        // have been updated with MovePoint objects
        // create new Point object
        pointOne = new Point(3, 4);
        // copy pointOne for checking equals at the end
        Point ogPointOne = new Point(pointOne);
        // pointOne and ogPointOne should equal each other
        assertTrue(ogPointOne.equals(pointOne));
        // original disgtance from origin
        double ogDistance = pointOne.distance();
        int originalX = pointOne.getX();
        int originalY = pointOne.getY();
        // create new MovePoint object with new Point instance info
        movePoint = new MoveablePoint(pointOne, 2.0f, 8.0f);
        // create a new MovePoint object with updated coordinates (adding the xy speeds to original xy)
        MoveablePoint newMovePoint = movePoint.move();
        // update the original Point object with the new movePoint coordinates
        pointOne.setCoords(newMovePoint.getX(), newMovePoint.getY());
        // new coordiantes should be X=7, Y=12
        assertTrue(pointOne.getX() == 5);
        assertTrue(pointOne.getX() == originalX + movePoint.getXSpeed());
        assertTrue(pointOne.getY() == 12);
        assertTrue(pointOne.getY() == originalY + movePoint.getYSpeed());
        // pythagorean triple
        assertTrue(ogDistance == 5);
        // updated distance with side lengths 5, 12
        assertTrue(pointOne.distance() == 13);
        // thusly
        assertFalse(ogPointOne.equals(pointOne));
    }

    @Test
    void testEqualsC() {
        // Testing that the various objects do not equals each other
        pointOne    = new Point(3, 4);
        movePoint   = new MoveablePoint(pointOne, 2.0f, 8.0f);
        point3dOne  = new Point3D(3,4,5);
        assertFalse(pointOne.equals(movePoint));
        assertFalse(pointOne.equals(point3dOne));
        assertFalse(point3dOne.equals(pointOne));
        assertFalse(point3dOne.equals(movePoint));
        assertFalse(movePoint.equals(pointOne));
        assertFalse(movePoint.equals(point3dOne));
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
        String idealString = "X=5 Y=10";
        int x = 5, y = 10;
        pointOne = new Point(x, y);
        assertTrue(idealString.equals(pointOne.toString()));
        String idealString2 = "X=-5 Y=-10";
        int x1 = -5, y1 = -10;
        pointTwo = new Point(x1, y1);
        assertTrue(idealString2.equals(pointTwo.toString()));
    }
}

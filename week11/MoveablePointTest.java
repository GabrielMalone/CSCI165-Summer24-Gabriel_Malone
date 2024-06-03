// Gabriel Malone / CSCI165 / Summer 2024/ Week 11

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveablePointTest {

    Point pointOne, pointTwo;
    MoveablePoint movePointOne, movePointTwo;

    @BeforeEach
    public void setup(){
        pointOne     = null;
        pointTwo     = null;
        movePointOne = null;
        movePointTwo = null;
    }

    @Test
    void testDistance1(){
        //create pythagorean triples with move method
        movePointOne =  new MoveablePoint(1.0f, 2.0f);
        // moving x,y from origin 0,0
        movePointOne.move();
        movePointTwo =  new MoveablePoint(4.0f, 6.0f);
        // moving x,y from origin 0,0
        movePointTwo.move();
        // static Point method to compare the two MoveablePoint Objects' xy coords
        assertTrue(Point.distance(movePointOne, movePointTwo) == 5);
    }

    @Test
    void testDistance2(){
        //create a 5, 12, 13 triangle
        Point pointOne = new Point(1, 2);
        movePointOne = new MoveablePoint(pointOne, 4.0f, 10.0f);
        // take pointOne coords and move them to x5, y12
        movePointOne.move();
        assertTrue(movePointOne.distance() == 13);
    }

    @Test
    void testConstructorOverloadA() {
        movePointOne = new MoveablePoint();
        // should have default values of Point
        assertTrue(movePointOne.getX() == 0);
        assertTrue(movePointOne.getY() == 0);
        assertTrue(movePointOne.getXSpeed() == 0.0);
        assertTrue(movePointOne.getYSpeed() == 0.0);
    }

    @Test
    void testConstructorOverloadB() {
        // two floats constructor
        movePointOne = new MoveablePoint(5.1f, 10.1f);
        // default x, y
        assertTrue(movePointOne.getX() == 0);
        assertTrue(movePointOne.getY() == 0);
        // matches arguments
        assertTrue(movePointOne.getXSpeed() == 5.1f);
        assertTrue(movePointOne.getYSpeed() == 10.1f);
    }   

    @Test
    void testConstructorOverloadC() {
        // all args constructor
        movePointOne = new MoveablePoint(5, 10, 5.1f, 10.1f);
        assertTrue(movePointOne.getX() == 5);
        assertTrue(movePointOne.getY() == 10);
        assertTrue(movePointOne.getXSpeed() == 5.1f);
        assertTrue(movePointOne.getYSpeed() == 10.1f);

    }

    @Test
    void testConstructorOverloadD() {
        // Point object for xy constructor
        pointOne = new Point();
        pointOne.setX(5);
        pointOne.setY(10);
        movePointOne = new MoveablePoint(pointOne,5.0f, 10.0f);
        assertTrue(movePointOne.getX() == 5);
        assertTrue(movePointOne.getY() == 10);
        assertTrue(movePointOne.getXSpeed() == 5.0f);
        assertTrue(movePointOne.getYSpeed() == 10.0f);

    }

    @Test
    void testEquals() {
        pointOne = new Point();
        pointOne.setX(5);
        pointOne.setY(10);
        movePointOne = new MoveablePoint(pointOne,5.0f, 10.0f);
        movePointTwo = new MoveablePoint(pointOne,5.0f, 10.0f);
        // Point objects do not share the same memory address
        assertFalse(movePointOne == movePointTwo);
        // Point objects share the same variable states
        assertTrue(movePointOne.equals(movePointTwo));
    }

    @Test
    void testCopyConstructorPrivacy() {
        pointOne = new Point();
        pointOne.setX(5);
        pointOne.setY(10);
        movePointOne = new MoveablePoint(pointOne,6.0f, 10.0f);
        movePointTwo = new MoveablePoint(movePointOne);
        // explicit and implicit equality demo
        assertTrue(movePointTwo.getX() == 5);
        assertTrue(movePointTwo.getX() == pointOne.getX());
        assertTrue(movePointTwo.getY() == 10);
        assertTrue(movePointTwo.getY() == pointOne.getY());
        // Point objects do not share the same memory address
        assertFalse(movePointOne == movePointTwo);
        // Point objects share the same variable states
        assertTrue(movePointOne.equals(movePointTwo));
    }

    @Test
    void testGetSpeeds() {
        movePointOne = new MoveablePoint(3.0f, 4.0f);
        float [] returnedSpeeds = movePointOne.getSpeeds();
        assertTrue(returnedSpeeds[0] == 3.0f);
        assertTrue(returnedSpeeds[1] == 4.0f);
    }

    @Test
    void testGetXSpeed() {
        movePointOne = new MoveablePoint();
        assertTrue(movePointOne.getXSpeed() == 0.0f);
        movePointOne.setXSpeed(5.0f);
        assertTrue(movePointOne.getXSpeed() == 5.0f);
    }

    @Test
    void testGetYSpeed() {
        movePointOne = new MoveablePoint();
        assertTrue(movePointOne.getYSpeed() == 0.0f);
        movePointOne.setYSpeed(5.0f);
        assertTrue(movePointOne.getYSpeed() == 5.0f);

    }

    @Test
    void testMoveA() {
        pointOne = new Point(3, 4);
        movePointOne = new MoveablePoint(pointOne, 10, 10);
        MoveablePoint movePointOneMoved = movePointOne.move();
        assertTrue(movePointOneMoved.getX() == pointOne.getX() + movePointOne.getXSpeed());
        assertTrue(movePointOneMoved.getY() == pointOne.getY() + movePointOne.getYSpeed());
    }

    @Test
    void testMoveB() {
        pointOne = new Point(3, 4);
        movePointOne = new MoveablePoint(pointOne, 10.2f, 10.5f);
        MoveablePoint movePointOneMoved = movePointOne.move();
        assertTrue(movePointOneMoved.getX() == 13);
        assertTrue(movePointOneMoved.getY() == 14);
    }

    @Test
    void testSetSpeeds() {
        movePointOne = new MoveablePoint(2.5f, 4.9f);
        float [] returnedSpeeds = movePointOne.getSpeeds();
        assertTrue(returnedSpeeds[0] == 2.5f);
        assertTrue(returnedSpeeds[1] == 4.9f);

        movePointOne.setSpeeds(4.0f, 5.0f);
        float [] returnedSpeeds2 = movePointOne.getSpeeds();
        assertTrue(returnedSpeeds2[0] == 4.0f);
        assertTrue(returnedSpeeds2[1] == 5.0f);
    }

    @Test
    void testSetXSpeed() {
        movePointOne = new MoveablePoint(4.0f, 5.0f);
        assertTrue(movePointOne.getXSpeed() == 4.0f);
        movePointOne.setXSpeed(5.0f);
        assertTrue(movePointOne.getXSpeed() == 5.0f);
    }

    @Test
    void testSetYSpeed() {
        movePointOne = new MoveablePoint(4.0f, 5.0f);
        assertTrue(movePointOne.getYSpeed() == 5.0f);
        movePointOne.setYSpeed(6.0f);
        assertTrue(movePointOne.getYSpeed() == 6.0f);
    }

    @Test
    void testToString() {
        String idealString = "X=3 Y=4 Xspeed=10.0 Yspeed=10.0";
        pointOne = new Point(3, 4);
        movePointOne = new MoveablePoint(pointOne, 10, 10);
        assertTrue(idealString.equals(movePointOne.toString()));
    }
}

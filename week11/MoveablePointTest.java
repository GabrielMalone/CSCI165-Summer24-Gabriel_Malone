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
        movePointOne = new MoveablePoint(5.1f, 10.1f);
        assertTrue(movePointOne.getX() == 0);
        assertTrue(movePointOne.getY() == 0);
        assertTrue(movePointOne.getXSpeed() == 5.1f);
        assertTrue(movePointOne.getYSpeed() == 10.1f);
    }   

    @Test
    void testConstructorOverloadC() {
        movePointOne = new MoveablePoint(5, 10, 5.1f, 10.1f);
        assertTrue(movePointOne.getX() == 5);
        assertTrue(movePointOne.getY() == 10);
        assertTrue(movePointOne.getXSpeed() == 5.1f);
        assertTrue(movePointOne.getYSpeed() == 10.1f);

    }

    @Test
    void testConstructorOverloadD() {
        pointOne = new Point();
        pointOne.setX(5);
        pointOne.setY(10);
        movePointOne = new MoveablePoint(pointOne,5.1f, 10.1f);
        assertTrue(movePointOne.getX() == 5);
        assertTrue(movePointOne.getY() == 10);
        assertTrue(movePointOne.getXSpeed() == 5.1f);
        assertTrue(movePointOne.getYSpeed() == 10.1f);

    }

    @Test
    void testEquals() {
        pointOne = new Point();
        pointOne.setX(5);
        pointOne.setY(10);
        movePointOne = new MoveablePoint(pointOne,5.1f, 10.1f);
        movePointTwo = new MoveablePoint(pointOne,5.1f, 10.1f);
        assertFalse(movePointOne == movePointTwo);
        assertTrue(movePointOne.equals(movePointTwo));
    }

    @Test
    void testCopyPrivacy() {
        pointOne = new Point();
        pointOne.setX(5);
        pointOne.setY(10);
        movePointOne = new MoveablePoint(pointOne,5.1f, 10.1f);
        movePointTwo = new MoveablePoint(movePointOne);
        assertFalse(movePointOne == movePointTwo);
        assertTrue(movePointOne.equals(movePointTwo));
    }

    @Test
    void testGetSpeeds() {
        movePointOne = new MoveablePoint(2.5f, 4.9f);
        float [] returnedSpeeds = movePointOne.getSpeeds();
        assertTrue(returnedSpeeds[0] == 2.5f);
        assertTrue(returnedSpeeds[1] == 4.9f);
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


    }
}

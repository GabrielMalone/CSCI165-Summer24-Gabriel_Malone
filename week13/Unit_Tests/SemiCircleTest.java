// Gabriel Malone / CSCI165 / Week 13 / Summer 2024
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import java.awt.Color;

public class SemiCircleTest {
    
    @Test 
    void constructorTest(){
        SemiCircle sc1 = new SemiCircle(10, Color.red, true, new Point(3,5));
        assertTrue(sc1.getRadius() == 10);
        assertTrue(sc1.getColor() == Color.red);
        assertTrue(sc1.filled == true);
        assertTrue(sc1.getLocation().getX() == 3);
        assertTrue(sc1.getLocation().getY() == 5);
    }
    @Test 
    void areaTest(){
        // semicircle area should be half of a circle area with same radius
        SemiCircle sc1 = new SemiCircle(10);
        Circle c1 = new Circle(10);
        assertTrue(sc1.getArea() == c1.getArea() / 2);
    }
    @Test 
    void perimeterTest(){
        // semicricle perimeter should equal the perimter of a circle with the same radius / 2 + the distance to travel to the other side of the semi circle (2r)
        SemiCircle sc1 = new SemiCircle(10);
        Circle c1 = new Circle(10);
        assertTrue(sc1.getPerimeter() == (c1.getPerimeter() / 2) + c1.getRadius() * 2);
    }

    
}
// Gabriel Malone / CS165 / Summer 2024 / Week 13
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;


public class ResizeTest {

    @Test
    void testGetArea() {
        int percent = 5;
        double percentConversion = (double)percent/100;
        // circles
		Circle c1 = new Circle(5.5);
        Circle c2 = new Circle(5.5);
        // circle with radius 5.5 should have area of 5.5^2 * 3.14 --> 95.03..
        assertTrue(c1.getArea() == 95.03317777109125);
        // same radius / area / perimeter at this point
        assertTrue(c1.getRadius() == c2.getRadius());
        assertTrue(c1.getArea() == c2.getArea());
        assertTrue(c1.getPerimeter() == c2.getPerimeter());
        // double size (200%)
        c2.resize(percent);
        // should no longer be the same sized radius
        assertFalse(c1.getRadius() == c2.getRadius());
        // should be doubled in size
        assertTrue(c2.getRadius() == c1.getRadius() * percentConversion);
        // area should be now be one of a circle with twice the radius length as original
        assertTrue(c2.getArea() == new Circle(c1.getRadius() * percentConversion).getArea());
        // perimeter should be now be one of a circle with twice the radius length as original
        assertTrue(c2.getPerimeter() == new Circle(c1.getRadius() * percentConversion).getPerimeter());

        // rectangles
        Rectangle r1 = new Rectangle(5,5);
        Rectangle r2 = new Rectangle(5,5);
        assertTrue(r1.getArea() == r2.getArea());
        percent = 5;
        r1.resize(percent);
        assertFalse(r1.getArea() == r2.getArea());
        assertTrue(r1.getArea() == r2.getHeight() * percentConversion * (r2.getWidth() * percentConversion));

        // squares
        Rectangle s1 = new Square(5);
        Rectangle s2 = new Square(5);
        assertTrue(s1.getArea() == s2.getArea());
        
        s1.resize(percent);
        assertFalse(s1.getArea() == s2.getArea());
        assertTrue(s1.getArea() == s2.getHeight() * percentConversion * (r2.getWidth() * percentConversion));

        // triangles
        Triangle t1 = new Triangle(3,4,5);
        Triangle t2 = new Triangle(3,4,5);
        assertTrue(t1.getArea() == t2.getArea());
        percent = 5;
        t1.resize(percent);
        assertFalse(t1.getArea() == t2.getArea());
        // new area should equal a triangle's area whose sides are multiplied by the percent change
        assertTrue(t1.getArea() == new Triangle((t2.getSideA() * percentConversion), (t2.getSideB() * percentConversion), (t2.getSideC() * percentConversion)).getArea());


        // semicircles
        SemiCircle sc1 = new SemiCircle(5);
        SemiCircle sc2 = new SemiCircle(5);
        assertTrue(sc1.getArea() == sc2.getArea());
        sc1.resize(percent);
        assertFalse(sc1.getArea() == sc2.getArea());
        // new area should equal that of a semi circle whose radius has been multipled by the percent
        assertTrue(sc1.getArea() == new SemiCircle(sc2.getRadius() * percentConversion).getArea());
        
	}
}

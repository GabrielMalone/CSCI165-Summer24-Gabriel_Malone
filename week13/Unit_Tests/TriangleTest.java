// Gabriel Malone / CSCI165 / Week 13 / Summer 2024
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

public class TriangleTest {
    @Test 
    void triangleValidSideLengthsCheck(){
        // side length 0 for any side should cuase null values for all sides
        Triangle t1 = new Triangle(1, 0, 2);
        assertTrue(t1.getSideA() == 0);
        assertTrue(t1.getSideB() == 0);
        assertTrue(t1.getSideC() == 0);
        // side length in which one side is too short should case null values for all
        Triangle t2 = new Triangle(10, 1, 12);
        assertTrue(t2.getSideA() == 0);
        assertTrue(t2.getSideB() == 0);
        assertTrue(t2.getSideC() == 0);
        // side length in which one side is too short should case null values for all
        Triangle t3 = new Triangle(1, 10, 12);
        assertTrue(t3.getSideA() == 0);
        assertTrue(t3.getSideB() == 0);
        assertTrue(t3.getSideC() == 0);
        // side length in which one side is too short should case null values for all
        Triangle t4 = new Triangle(10, 12, 1);
        assertTrue(t4.getSideA() == 0);
        assertTrue(t4.getSideB() == 0);
        assertTrue(t4.getSideC() == 0);
    }
}

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Point3DTest {
    Point pointOne, pointTwo;
    Point3D point3Done, point3Dtwo;

    @BeforeEach
    public void setup(){
        pointOne   = null;
        pointTwo   = null;
        point3Done = null;
        point3Dtwo = null;
    }
    
    @Test
    void constructorTestA(){
        // no arguments constructor
        point3Done = new Point3D();
        int [] XYZcoords = new int[3];
        XYZcoords = point3Done.getXYZ();
        // all default points should be 0
        assertTrue(XYZcoords[0] == 0);
        assertTrue(XYZcoords[1] == 0);
        assertTrue(XYZcoords[2] == 0);

    }

    @Test
    void constructorTestB(){
        // ints for xyz constructor
        point3Done = new Point3D(3, 4, 5);
        int [] XYZcoords = new int[3];
        XYZcoords = point3Done.getXYZ();
        assertTrue(XYZcoords[0] == 3);
        assertTrue(XYZcoords[1] == 4);
        assertTrue(XYZcoords[2] == 5);
    }

    @Test
    void copyConstructorTest(){
        point3Done = new Point3D(3, 4, 5);
        // copied Point3D object
        point3Dtwo = new Point3D(point3Done);
        // Should have all same variables states
        // redundant get method and explicit checks
        assertTrue(point3Dtwo.getX() == 3);
        assertTrue(point3Dtwo.getX() == point3Done.getX());
        assertTrue(point3Dtwo.getY() == 4);
        assertTrue(point3Dtwo.getY() == point3Done.getY());
        assertTrue(point3Dtwo.z == 5);
        // equals method finally confirms all states equal
        assertTrue(point3Done.equals(point3Dtwo));
        // but different memory location for both objects
        assertFalse(point3Dtwo == point3Done);
    }
    
    @Test
    void testEqualsA() {
        point3Done = new Point3D(3, 4, 5);
        point3Dtwo = new Point3D(3, 4, 5);
        // same variable states
        assertTrue(point3Done.equals(point3Dtwo));
        // different mem address
        assertFalse(point3Done == pointTwo);
    }

    @Test
    void testEqualsB() {
        point3Done = new Point3D(3, 4, 5);
        point3Dtwo = new Point3D(1, 2, 3);
        // different variable states
        assertFalse(point3Done.equals(point3Dtwo));
    }

    @Test
    void testGetXYZ() {
        point3Done = new Point3D(3, 4, 5);
        // see that coords in array match original constructor variables
        int [] XYZcoords = new int[3];
        XYZcoords = point3Done.getXYZ();
        assertTrue(XYZcoords[0] == 3);
        assertTrue(XYZcoords[1] == 4);
        assertTrue(XYZcoords[2] == 5);
    }

    @Test
    void testSetXYZ() {
        // new point3d object with 3,4,5 coords
        point3Done = new Point3D(3, 4, 5);
        // see that these coords are set
        int [] XYZcoords = new int[3];
        XYZcoords = point3Done.getXYZ();
        assertTrue(XYZcoords[0] == 3);
        assertTrue(XYZcoords[1] == 4);
        assertTrue(XYZcoords[2] == 5);
        // set new coords
        point3Done.setXYZ(6, 7, 8);
        // see that they don't match original
        assertFalse(point3Done.getX()   == 3);
        assertFalse(point3Done.getY()   == 4);
        assertFalse(point3Done.z        == 5);
        // see that they match new coords with various get methods
        assertTrue(point3Done.getX()    == 6);
        assertTrue(point3Done.getY()    == 7);
        assertTrue(point3Done.z         == 8);
        int [] XYZcoords2 = new int[3];
        XYZcoords2 = point3Done.getXYZ();
        assertTrue(XYZcoords2[0] == 6);
        assertTrue(XYZcoords2[1] == 7);
        assertTrue(XYZcoords2[2] == 8);
    }

    @Test
    void testSetXYZ2() {
        // test with Point Object for x, y coords
        pointOne = new Point(6, 7);
        // new point3d object with 3,4,5 coords
        point3Done = new Point3D(3, 4, 5);
        // see that these coords are set
        int [] XYZcoords = new int[3];
        XYZcoords = point3Done.getXYZ();
        assertTrue(XYZcoords[0] == 3);
        assertTrue(XYZcoords[1] == 4);
        assertTrue(XYZcoords[2] == 5);
        // set new coords with Point object for xy
        point3Done.setXYZ(pointOne, 8);
        // see that they don't match original
        assertFalse(point3Done.getX()   == 3);
        assertFalse(point3Done.getY()   == 4);
        assertFalse(point3Done.z        == 5);
        // see that they match new coords with various get methods
        assertTrue(point3Done.getX()    == 6);
        assertTrue(point3Done.getY()    == 7);
        assertTrue(point3Done.z         == 8);
        int [] XYZcoords2 = new int[3];
        XYZcoords2 = point3Done.getXYZ();
        assertTrue(XYZcoords2[0] == 6);
        assertTrue(XYZcoords2[1] == 7);
        assertTrue(XYZcoords2[2] == 8);
    }

    @Test
    void testSetZ() {
        // new point3d object with 3,4,5 coords
        point3Done = new Point3D(3, 4, 5);
        // see that these coords are set
        int [] XYZcoords = new int[3];
        XYZcoords = point3Done.getXYZ();
        assertTrue(XYZcoords[0] == 3);
        assertTrue(XYZcoords[1] == 4);
        assertTrue(XYZcoords[2] == 5);
        // set a new Z coord
        point3Done.setZ(10);
        // see that Z does not equal the old Z
        assertFalse(point3Done.z == 5);
        assertTrue(point3Done.z == 10);
    }

    @Test
    void testToString() {
        String idealString = "X=3 Y=4 Z=5";
        point3Done = new Point3D(3, 4, 5);
        assertTrue(idealString.equals(point3Done.toString()));
    }
}

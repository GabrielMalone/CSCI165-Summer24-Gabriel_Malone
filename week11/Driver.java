// Gabriel Malone / CSCI165 / Summer 2024 / Week 11

public class Driver {
    public static Point           p1, p2, p3;
    public static MoveablePoint   mp1, mp2, mp3, mp4, mp5;
    public static Point3D         p3d1, p3d2, p3d3;
    public static void main(String[] args) {

    // POINT CONSTRUCTORS
        System.out.println();
        System.out.println("POINT CONSTRUCTORS:");
        // toString implicitly called for these

        // no arguments constructor
        p1 = new Point();
        // should print default xy coords
        System.out.println("Point p1 coords (no args):                   " + p1);

        // overloaded constructor
        p2 = new Point(3, 5);
        // should print 3, 5 xy coords
        System.out.println("Point p2 coords (overloaded):                " + p2);

        // copy constructor
        p3 = new Point(p2);
        System.out.println("Point p3 coords (copy of P2):                " + p3);

    // MOVEPOINT CONSTRUCTORS
        System.out.println();
        System.out.println("MOVEPOINT CONSTRUCTORS:");

        // no argument constructor
        mp1 = new MoveablePoint();
        System.out.println("MovePoint mp1 coords (no args):              " + mp1);

        // float xspeed , yspeed constructor
        mp2 = new MoveablePoint(3.0f, 5.0f);
        System.out.println("MovePoint mp2 coords (float args):           " + mp2);

        // intx, int y, float xspeed , float yspeed constructor
        mp3 = new MoveablePoint(2, 4, 3.0f, 5.0f);
        System.out.println("MovePoint mp3 coords (int and float args):   " + mp3);

        // point, float yspeed constructor
        mp4 = new MoveablePoint(p2, 3.0f, 5.0f);
        System.out.println("MovePoint mp4 coords (Point and float args): " + mp4);

        // copy constructor
        mp5 = new MoveablePoint(mp4);
        System.out.println("MovePoint mp5 coords (copy of mp4):          " + mp5);

    // POINT3D CONSTRUCTORS
        System.out.println();
        System.out.println("POINT3D CONSTRUCTORS:");

        // no argument constructor 
        p3d1 = new Point3D();
        // default xyz coords
        System.out.println("Point3D p3d1 coords (no args)                " + p3d1);

        // xyz int constructor
        p3d2 = new Point3D(3, 4, 5);
        System.out.println("Point3D p3d2 coords (int overload)           " + p3d2);

        // copy constructor
        p3d3 = new Point3D(p3d2);
        System.out.println("Point3D p3d2 coords (copy of p3d2)           " + p3d3);

    // INHERITED METHOD DEMOS / toSTRING CHAINING
        System.out.println();
        System.out.println("INHERITED METHODS / toString CHAIN DEMO:");

        // MovePoint inheriting methods from Point
        p1 = new Point(1, 2);
        mp1 = new MoveablePoint(2.0f, 4.0f);
        // inherited method of setting coords
        mp1.setCoords(p1.getX(), p1.getY());
        // chaining toSTring
        System.out.println("MovePoint mp1 full state info:               " + mp1);
        // get inheritance demos from MovePoint object
        System.out.println("MovePoint mp1 getX inheritance demo:         " + mp1.getX());
        System.out.println("MovePoint mp1 getY inheritance demo:         " + mp1.getY());
        
        // Point3D inheriting methods from Point
        p1 = new Point(1, 2);
        p3d1 = new Point3D();
        // inherited method of setting coords
        p3d1.setX(p1.getX());
        p3d1.setY(p1.getY());
        p3d1.setZ(3);
        // chaining toSTring
        System.out.println("Point3D p3d1 full state info:                " + p3d1);
        // get inheritance demos from Point3D object
        System.out.println("Point3D p3d1 getX inheritance demo:          " + p3d1.getX());
        System.out.println("Point3D p3d1 getY inheritance demo:          " + p3d1.getY());

    // DISTANCE METHODS
        System.out.println();
        System.out.println("DISTANCE METHODS DEMO:");

        // distance from origin (0,0) from a point object
        p1 = new Point(3, 4);
        System.out.println("The Distance between X=0 Y=0 and " + p1 + " is " + p1.distance());

        // distance from origin (two given int points) from a point object
        p1 = new Point(3, 4);
        System.out.println("The Distance between " + p1 + " and X=8 Y=16 is " + p1.distance(8, 16));

        // distance from origin (0,0) from a MovePoint Ojbect (original xy set at 1, 2 then moved to 3, 4)
        mp1 = new MoveablePoint( new Point (1, 2), 2.0f, 2.0f);
        mp1.move();
        System.out.println("The Distance between points 0,0 and " + mp1.getX() + "," + mp1.getY() + " is " + mp1.distance());

        // dstance between two Point Objects (Point class statically called)
        double distance = Point.distance(new Point(3,4), new Point (8, 16));
        System.out.println("The distance between point obj xy = 3,4 and point obj xy = 8,16 is " + distance);

        // distance between different types of Point objects (move and 3d)
        mp1 = new MoveablePoint( new Point (1, 2), 2.0f, 2.0f);
        p3d1 = new Point3D( new Point (4, 6), 5);
        double distance2 = mp1.distance(p3d1);
        System.out.println("The distance between movepoint object (1,2) and point3d object (4,6) is " + distance2); 

    }
}

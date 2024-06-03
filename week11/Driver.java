// Gabriel Malone / CSCI165 / Summer 2024 / Week 11

public class Driver {
    public static Point           p1, p2, p3;
    public static MoveablePoint   mp1, mp2, mp3, mp4, mp5;
    public static Point3D         p3d1, p3d2, p3d3;
    
    public static void main(String[] args) {

    // POINT CONSTRUCTORS

        System.out.println();
        System.out.println("POINT CONSTRUCTORS:");
        System.out.println();
        // toString implicitly called for these

        // no arguments constructor
        p1 = new Point();
        // should print default xy coords
        System.out.println(p1.getClass() + " coords (no args):     " + p1);

        // overloaded constructor
        p2 = new Point(3, 5);
        // should print 3, 5 xy coords
        System.out.println(p2.getClass() + " coords (overloaded):  " + p2);

        // copy constructor
        p3 = new Point(p2);
        System.out.println(p3.getClass() + " coords (copy of P2):  " + p3);

    // MOVEPOINT CONSTRUCTORS

        System.out.println();
        System.out.println("MOVEPOINT CONSTRUCTORS:");
        System.out.println();

        // no argument constructor
        mp1 = new MoveablePoint();
        System.out.println(mp1.getClass() + " coords (no args):                 " + mp1);

        // float xspeed , yspeed constructor
        mp2 = new MoveablePoint(3.0f, 5.0f);
        System.out.println(mp2.getClass() + " coords (float args):              " + mp2);

        // intx, int y, float xspeed , float yspeed constructor
        mp3 = new MoveablePoint(2, 4, 3.0f, 5.0f);
        System.out.println(mp3.getClass() + " coords (int and float args):      " + mp3);

        // point, float yspeed constructor
        mp4 = new MoveablePoint(p2, 3.0f, 5.0f);
        System.out.println(mp4.getClass() + " coords (Point and float args):    " + mp4);

        // copy constructor
        mp5 = new MoveablePoint(mp4);
        System.out.println(mp5.getClass() + " coords (copy of mp4):             " + mp5);

    // POINT3D CONSTRUCTORS
        System.out.println();
        System.out.println("POINT3D CONSTRUCTORS:");
        System.out.println();
        // no argument constructor 
        p3d1 = new Point3D();
        // default xyz coords
        System.out.println(p3d1.getClass() + " coords (no args)                " + p3d1);

        // xyz int constructor
        p3d2 = new Point3D(3, 4, 5);
        System.out.println(p3d2.getClass() + " coords (int overload)           " + p3d2);

        // copy constructor
        p3d3 = new Point3D(p3d2);
        System.out.println(p3d3.getClass() + " coords (copy of p3d2)           " + p3d3);

    // INHERITED METHOD DEMOS / toSTRING CHAINING

        System.out.println();
        System.out.println("INHERITED METHODS / toString CHAIN DEMO:");
        System.out.println();

        // MovePoint inheriting methods from Point
        p1 = new Point(1, 2);
        mp1 = new MoveablePoint(2.0f, 4.0f);
        // inherited method of setting coords
        mp1.setCoords(p1.getX(), p1.getY());
        // chaining toSTring
        System.out.println(mp1.getClass()+ " full state info:         " + mp1);
        // get inheritance demos from MovePoint object
        System.out.println(mp1.getClass() + " getX inheritance demo:   " + mp1.getX());
        System.out.println(mp1.getClass() + " getY inheritance demo:   " + mp1.getY());
        
        // Point3D inheriting methods from Point
        p1 = new Point(1, 2);
        p3d1 = new Point3D();
        // inherited method of setting coords
        p3d1.setX(p1.getX());
        p3d1.setY(p1.getY());
        p3d1.setZ(3);
        // chaining toSTring
        System.out.println(p3d1.getClass() + " full state info:               " + p3d1);
        // get inheritance demos from Point3D object
        System.out.println(p3d1.getClass() + " getX inheritance demo:         " + p3d1.getX());
        System.out.println(p3d1.getClass() + " getY inheritance demo:         " + p3d1.getY());

    // DISTANCE METHODS

        System.out.println();
        System.out.println("DISTANCE METHODS DEMO:");
        System.out.println();

        // distance from origin (0,0) from a point object
        p1 = new Point(3, 4);
        System.out.println("The Distance between X=0 Y=0 and " + p1 + " is " + p1.distance());

        // distance between two given int points from a point object
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

    // POLYMORPHISM FORESHADOWING

        System.out.println();
        System.out.println("POLYMORPHISM DEMO:");
        System.out.println();
        Point [] pointArray = new Point [9];
        pointArray [0] = new Point(1, 2);
        pointArray [1] = new Point(2, 3);
        pointArray [2] = new Point(4, 5);
        pointArray [3] = new MoveablePoint(1, 2, 3, 4);
        pointArray [4] = new MoveablePoint(2, 3, 4, 5);
        pointArray [5] = new MoveablePoint(3, 4, 5, 6);
        pointArray [6] = new Point3D(10, 11, 12);
        pointArray [7] = new Point3D(11, 12, 13);
        pointArray [8] = new Point3D(12, 13, 14);
        // call toString on each point
        int index = 0;
        for (Point point : pointArray){
            System.out.println(point.getClass() + " at index " + index + "'s state: " + point + "\nthis Point object distance from 0,0: " + point.distance());
            // point.getXYZ();
            // cannot call getXYZ as it is only defined for the subclass Point3D. Point3D is a Point, but a Point is not all things a 3Dpoint is, although they share similarities. Just as a dog is an animal and is defined by all things that make an animal, not all animals can bark. Polymorphism flows in a top-down path, moving from general to specific. 
            // 
            System.out.println();
            index ++;
        }

        System.out.println();
        System.out.println("POLYMORPHISM DEMO 2:");
        System.out.println();
        Object [] objectArray = new Object [9];
        objectArray [0] = new Point(1, 2);
        objectArray [1] = new Point(2, 3);
        objectArray [2] = new Point(4, 5);
        objectArray [3] = new MoveablePoint(1, 2, 3, 4);
        objectArray [4] = new MoveablePoint(2, 3, 4, 5);
        objectArray [5] = new MoveablePoint(3, 4, 5, 6);
        objectArray [6] = new Point3D(10, 11, 12);
        objectArray [7] = new Point3D(11, 12, 13);
        objectArray [8] = new Date(12, 2, 1999);
        int index2 = 0;
        for (Object object : objectArray){
            System.out.println(object.getClass() + " at index " + index2 + "'s state: " + object);
            // point.getXYZ();
            // Again, cannot call getXYZ (or getdistance anymore) as it is only defined for the Class Point and subclass Point3D. Point3D is a Point and Point is an object, but an Object is not all things a Point is much less all things a 3Dpoint is. 
            // can still call toString since that is a method that exists in the Object class. The correct toStirng method called for these various sublcasses due to Overriding the toString method to call the one particular to that class. 
            System.out.println();
            index2 ++;
        }
    }
}

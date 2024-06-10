// Gabriel Malone / CS165 / Summer 2024 / Week 11

public class Point {

    private int x, y;
    private int [] XYcoords = new int [2];
    
    /**
     * No argument constructor
     */
    public Point (){
        this.x = 0;
        this.y = 0;
        this.XYcoords [0] = 0;
        this.XYcoords [1] = 0;
    }

    /**
     * Overloaded constructors
     * @param x_given
     * @param y_given
     */
    public Point (int x_given, int y_given){
        this.x = x_given;
        this.y = y_given;
        this.XYcoords =  new int[2];
        this.XYcoords [0] = x_given;
        this.XYcoords [1] = y_given;
    }

    /**
     * Copy constructor
     * @param copyCoordinate
     */
    public Point (Point copyCoordinate){
        this.x = copyCoordinate.getX();
        this.y = copyCoordinate.getY();  
    }

    /**
     * Return the X coordinate for Point
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Return the Y coordinate for Point
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Set X coordinate for Point
     * @param x
     */
    public void setX(int x) {
        this.x = x;
        this.XYcoords[0] = x;
    }

    /**
     * Set Y coordinate for Point
     * @param y
     */
    public void setY(int y) {
        this.y = y;
        this.XYcoords[1] = y;
    }

    /**
     * Set coordinates for Point
     * @param x_coord
     * @param y_coord
     */
    public void setCoords(int x_coord, int y_coord){
        this.XYcoords [0] = x_coord;
        this.XYcoords [1] = y_coord;
        this.x = x_coord;
        this.y = y_coord;
    }

    /**
     * Return a copy of Point's coords array
     * @return int array of coordinates
     */
    public int[] getCoords(){
        // clone this.coords array
        int [] XYcoordCopy = new int[2];
        XYcoordCopy [0] = this.XYcoords[0];
        XYcoordCopy [1] = this.XYcoords[1];  
        return XYcoordCopy;
    }
    
    @Override
    public String toString(){
        String coordinateString = "X=" + getX() + " Y=" + getY();
        return coordinateString;
    }

    @Override
    public boolean equals(Object obj){

        if (this == obj)                    return true;
        if (obj == null)                    return false;
        if (getClass() != obj.getClass())   return false;
        // downcasting
        Point otherPoint = (Point) obj;

        if (    getCoords() == null 
                && otherPoint.getCoords() != null){
                return false;
            } 
        else if (this.x != otherPoint.getX() 
                ||  this.y != otherPoint.getY()){
                return false;
            }
        return true;
    }

    /**
     * returns the distance from this point to 
     * another point at the given (x, y) coordinates
     * @param other_x
     * @param other_y
     * @return
     */
    public double distance (int other_x, int other_y){
        double d = Math.sqrt(Math.pow(other_x - this.x, 2)+Math.pow(other_y - this.y, 2));
        return d;
    }
    
    /**
     * returns the distance from this point to 
     * another point from another Point object
     * @param other_x
     * @param other_y
     * @return
     */
    public double distance (Point otherPoint){
        double d = Math.sqrt(Math.pow(otherPoint.getX() - this.x, 2)+Math.pow(otherPoint.getY() - this.y, 2));
        return d;
    }

    /**
     * returns the distance from this point to origin (0,0)
     * 
     * @param other_x
     * @param other_y
     * @return
     */
    public double distance (){
        double d = Math.sqrt(Math.pow(0 - this.x, 2)+Math.pow(0 - this.y, 2));
        return d;
    }

    public static double distance (Point one, Point two){
        double d = Math.sqrt(Math.pow(one.getX() - two.getX(), 2)+Math.pow(one.getY() - two.getY(), 2));
        return d;
    }

}
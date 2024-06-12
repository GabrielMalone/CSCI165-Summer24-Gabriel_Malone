// Gabriel Malone // CSCI165 // Week 13 / Summer 2024
import java.awt.Color;
import java.awt.Graphics2D;

public class Circle extends Shape implements Resizable{

    double radius;

    /**
     * no arguments / null / constructor
     */
    public Circle(){}
    /**
     * radius only constructor
     * @param radius
     */
    public Circle (double radius){
        this.radius = radius;
    }

    /**
     * fully overloaded constructor
     * @param radius
     * @param color
     * @param filled
     * @param point
     */
    public Circle (double radius, Color color, boolean filled, Point point){
        super(color, filled, point);
        this.radius = radius;
    }
    /**
     * copy constructor
     * @param toCopy
     */
    public Circle (Circle toCopy){
        super(toCopy.color, toCopy.filled, toCopy.point);
        this.radius = toCopy.radius;
    }
    /**
     * 
     * @return the radius for this circle
     */
    public double getRadius() {
        return this.radius;
    }
    /**
     * sets the radius for this circle
     * @param radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }
    /**
     * calculates the area for this circle
     */
    @Override
    public double getArea (){
        // piRsquared
        return  Math.pow(this.radius, 2) * Math.PI;
    }
    /**
     * calculates the circumference of this circle
     */
    @Override
    public double getPerimeter(){
        // 2piR
        return this.radius * 2 * Math.PI;
    }

    @Override
    public String toString() {
        return 
        " Circle [radius=" + radius + " area=" + getArea() + " circumference=" + getPerimeter() + "]"
        + "\n " + super.toString();
    }

    /**
     * Method to resize a circle
     */
    @Override
    public void resize(int percent){
        double percentDouble = (double)percent / 100;
        this.radius *= percentDouble;
    }

    /**
     * Method to draw this shape
     */
    @Override
    public void drawObject (Graphics2D g2d){
        int [] center = new int[2];
        // need to place shape around the point.
        center[0] = (int)(getCenterPoint().getX());
        center[1] = (int)(getCenterPoint().getY());
        g2d.setColor(this.color);
        if (filled) 
            g2d.fillOval(center[0], center[1], (int)getRadius(), (int)getRadius());
        else        
            g2d.drawOval(center[0], center[1], (int)getRadius(), (int)getRadius());
        g2d.setColor(Color.white);
    }

    /**
     * 
     * @return a point object containing the center point of this circle
     */
    @Override
    public Point getCenterPoint(){
        Point centerPoint = new Point((int)getLocation().getX() - (int)getRadius() / 2, (int)getLocation().getY() - (int)getRadius() / 2);
        return centerPoint;
    }

    @Override
    public double computeDistance(Point cameraPoint){
        double distance = Point.distance(getLocation(), cameraPoint) -  radius / 2;
        return distance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Circle other = (Circle) obj;
        if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
            return false;
        return true;
    }
    
}

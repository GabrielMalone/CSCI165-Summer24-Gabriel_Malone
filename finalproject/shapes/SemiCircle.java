// Gabriel Malone / CSCI165 / Week 13 / Summer 2024
import java.awt.Color;
import java.awt.Graphics2D;

public class SemiCircle extends Circle {
    
    /**
     * no arguments constructor
     */
    public SemiCircle(){}
    /**
     * radius only constructor
     * @param radius
     */
    public SemiCircle (double radius){
        this.radius = radius;
    }
    
    /**
     * fully overloaded constructor
     * @param radius
     * @param color
     * @param filled
     * @param point
     */
    public SemiCircle (double radius, Color color, boolean filled, Point point){
        this.radius = radius;
        this.color  = color;
        this.filled = filled;
        this.point  = point;
    }

    /**
     * copy constructor
     * @param toCopy
     */
    public SemiCircle (Circle toCopy){
        this.radius = toCopy.radius;
        this.color  = toCopy.color;
        this.filled = toCopy.filled;
        this.point  = toCopy.point;
    }

    /**
     * Method to get area of a semi-circle
     */
    @Override
    public double getArea(){
        // regular area for circle divided by 2
        return  (Math.pow(this.radius, 2) * Math.PI) / 2;
    }

    /**
     * Method to get perimeter of a semi-circle
     */
    @Override
    public double getPerimeter(){
        // semicircle perimeter
        return (this.radius * Math.PI) + 2 * this.radius;
    }

    /**
     * Method to draw a semi-circle
     */
    @Override
    public void drawObject (Graphics2D g2d){
        int [] center = new int[2];
        // need to place shape around the point.
        center[0] = (int)getLocation().getX() - (int)getRadius() / 2;
        center[1] = (int)getLocation().getY() - (int)getRadius() / 2;
        double diameter = 2 * this.radius;
        g2d.setColor(this.color);
        g2d.drawArc(center[0], center[1], (int)diameter, (int)diameter, 0, 180); 
    }

    @Override
    public String toString() {
        return 
        "SemiCircle [area= " + getArea() + ", perimeter=" + getPerimeter() +"]"
        + "\n"+ "Color: "   + this.color 
        + "\n" + "Filled: " + this.filled 
        + "\n" + "Point: "  + this.point ;
    }

    

}

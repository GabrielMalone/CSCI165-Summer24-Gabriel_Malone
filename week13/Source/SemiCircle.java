// Gabriel Malone / CSCI165 / Week 13 / Summer 2024
import java.awt.Color;

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

    @Override
    public double getArea(){
        // regular area for circle divided by 2
        return  (Math.pow(this.radius, 2) * Math.PI) / 2;
    }

    @Override
    public double getPerimeter(){
        // semicircle perimeter
        return (this.radius * Math.PI) + 2 * this.radius;
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

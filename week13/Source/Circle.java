// Gabriel Malone // CSCI165 // Week 13 / Summer 2024
import java.awt.Color;

public class Circle extends Shape {

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
    /*
     * fully overloaded constructor
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
        return super.toString() + " Circle [radius=" + radius + "]";
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

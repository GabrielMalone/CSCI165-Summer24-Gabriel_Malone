// Gabriel Malone / CS165 / Summer 2024 / Week 13
import java.awt.Color;

public class Rectangle extends Shape {

    double height;
    double width;

    /**
     * no arguments constructor
     */
    public Rectangle (){}
    /**
     * size parameters constructor
     * @param height
     * @param width
     */
    public Rectangle (double height, double width){
        this.height = height;
        this.width  = width;
    }
    /**
     * fully loaded constructor
     * @param height
     * @param width
     * @param color
     * @param filled
     * @param point
     */
    public Rectangle (double height, double width, Color color, boolean filled, Point point){
        super(color, filled, point);
        this.height = height;
        this.width  = width;
    }
    /**
     * copy constructor
     * @param toCopy
     */
    public Rectangle (Rectangle toCopy){
        super(toCopy.color, toCopy.filled, toCopy.point);
        this.height = toCopy.height;
        this.width  = toCopy.width;
    }
    /**
     * 
     * @return height of this rectangle
     */
    public double getHeight() {
        return height;
    }
    /**
     * set height for this rectangle
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }
    /**
     * get width for this rectangle
     * @return
     */
    public double getWidth() {
        return width;
    }
    /**
     * set width for this rectangle
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
    }
    /**
     * calculate area for this rectangle
     */
    @Override
    public double getArea(){
        return this.width * this.height;
    }
    /**
     * calculate perimeter for this rectangle
     */
    @Override
    public double getPerimeter(){
        return (this.width * 2) + (this.height * 2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rectangle other = (Rectangle) obj;
        if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
            return false;
        if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " Rectangle [height=" + height + ", width=" + width + "]";
    }
    

}

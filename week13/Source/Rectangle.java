// Gabriel Malone / CS165 / Summer 2024 / Week 13
import java.awt.Color;

public class Rectangle extends Shape {

    double height;
    double width;

    public Rectangle (){}

    public Rectangle (double height, double width){
        this.height = height;
        this.width  = width;
    }
    public Rectangle (double height, double width, Color color, boolean filled, Point point){
        super(color, filled, point);
        this.height = height;
        this.width  = width;
    }
    public Rectangle (Rectangle toCopy){
        super(toCopy.color, toCopy.filled, toCopy.point);
        this.height = toCopy.height;
        this.width  = toCopy.width;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    @Override
    public double getArea(){
        return this.width * this.height;
    }
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

// Gabriel Malone // CSCI165 // Week 13 / Summer 2024
import java.awt.Color;

public abstract class Shape {
    Color color;
    boolean filled;
    Point point;

    /**
     * no arguments constructor
     */
    public Shape (){}

    /**
     * fully loaded constructor
     * @param color
     * @param filled
     * @param point
     */
    public Shape (Color color, Boolean filled, Point point){
        this.color  = color;
        this.filled = filled;
        this.point  = point;
    }

    /**
     * copy constructor
     * @param toCopy
     */
    public Shape (Shape toCopy){
        this.color  = toCopy.color;
        this.filled = toCopy.filled;
        this.point  = toCopy.getLocation();
    }

    /**
     * 
     * @return the color of this shape
     */
    public Color getColor() {
        return color;
    }
    /**
     * set the color of this shape
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * 
     * @return whether this shape is filled
     */
    public boolean isFilled() {
        return filled;
    }
    /**
     * fill this shape
     * @param filled
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    /**
     * 
     * @return the point location of this shape
     */
    public Point getLocation() {
        Point pointcopy = new Point(this.point);
        return pointcopy;
    }
    /**
     * set the point of this location
     * @param point
     */
    public void setLocation(Point point) {
        this.point = point;
    }
    /**
     * abstract method to be used by subclasses
     * @return
     */
    public abstract double getArea ();

    /**
     * abstract method to be used by subclasses
     * @return
     */
    public abstract double getPerimeter ();

    @Override
    public String toString() {
        return "Shape [color=" + color + ", filled=" + filled + ", point=" + point + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Shape other = (Shape) obj;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (filled != other.filled)
            return false;
        if (point == null) {
            if (other.point != null)
                return false;
        } else if (!point.equals(other.point))
            return false;
        return true;
    }
}

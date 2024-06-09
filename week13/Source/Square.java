// Gabriel Malone // CSCI165 // Week 13 / Summer 2024
import java.awt.Color;

public class Square extends Rectangle {

    /**
     * no arguments constructor method
     */
    public Square(){}

    /**
     * height (side) argument constructor method
     * @param height
     */
    public Square(double height){
        setSide(height);   
     }

     /**
      * fully loaded constructor method
      * @param height
      * @param color
      * @param filled
      * @param point
      */
    public Square(double height, Color color, boolean filled, Point point){
        setSide(height);
        this.color  = color;
        this.filled = filled;
        this.point  = point;
    }

    /**
     * set sides equal for square
     * @param side
     */
    public void setSide (double side){
        this.width  = side;
        this.height = side;
    }

    /**
     * 
     * @return this square's side length
     */
    public double getSide(){
        return this.width;
    }

    /**
     * if you set a width, set the heigh the same length for square
     */
    @Override
    public void setWidth(double width){
        this.width  = width;
        this.height = width;
    }

    /**
     * if you set a height, set the heigh the same width for square
     */
    @Override
    public void setHeight(double height){
        this.height = height;
        this.width  = height;
    }

    @Override
    public void resize(int percent){
        double percentDouble = (double)percent/100;
        setSide(this.width *= percentDouble);
    }

    @Override
    public String toString() {
        return "Square [sides=" + getSide() + " area=" + getArea() + "]"
        + "\n" + "Color: "   + this.color 
        + "\n" + "Filled: " + this.filled 
        + "\n" + "Point: "  + this.point ;
    }
}

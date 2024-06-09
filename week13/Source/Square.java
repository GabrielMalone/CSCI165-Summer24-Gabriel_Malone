// Gabriel Malone // CSCI165 // Week 13 / Summer 2024
import java.awt.Color;

public class Square extends Rectangle{
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
    }
    /**
     * set sides equal for square
     * @param side
     */
    public void setSide (double side){
        super.setWidth(side);
        super.setHeight(side);
    }
    /**
     * 
     * @return this square's side length
     */
    public double getSide(){
        return super.getWidth();
    }
    /**
     * if you set a width, set the heigh the same length for square
     */
    @Override
    public void setWidth(double width){
        super.setWidth(width);
        super.setHeight(width);
    }
    /**
     * if you set a height, set the heigh the same width for square
     */
    @Override
    public void setHeight(double height){
        super.setHeight(height);
        super.setWidth(height);
    }
    @Override
    public String toString() {
        return super.toString() + " Square [sides=" + getSide() + "]";
    }
}

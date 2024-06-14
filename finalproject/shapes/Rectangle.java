// Gabriel Malone / CS165 / Summer 2024 / Week 13
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Rectangle extends Shape implements Resizable{

    double width, height;
    Point p1, p2, p3, p4;

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
        computePoits();
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
        computePoits();
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
    public void resize(int percent){
        double percentDouble = (double)percent / 100;
        this.width  *= percentDouble;
        this.height *= percentDouble;
    }

    /**
     * Method to create the four point objects of a rectangle from given side lengths
     */
    public void computePoits(){
        this.p1 = new Point(getLocation());
        this.p2 = new Point(getLocation().getX() + width, getLocation().getY());
        this.p3 = new Point(p2.getX(), p2.getY() + height);
        this.p4 = new Point(p3.getX() - width, p3.getY());
    }

    /**
     * Method to draw this shape
     */
    @Override
    public void drawObject (Graphics2D g2d){
        // four points of the rectangle based on the side lengths
        g2d.setColor(this.color);
        if (filled){ 
            g2d.fillRect((int)getLocation().getX() - 10 , (int)getLocation().getY() - 10 , (int)getWidth(), (int)getHeight());      
        }
        else{        
            g2d.drawLine((int)this.p1.getX() - 10 , (int)this.p1.getY()- 10 , (int)this.p2.getX()- 10, (int)this.p2.getY()- 10);
            g2d.drawLine((int)this.p2.getX()- 10 , (int)this.p2.getY()- 10 , (int)this.p3.getX()- 10, (int)this.p3.getY()- 10);
            g2d.drawLine((int)this.p3.getX()- 10 , (int)this.p3.getY()- 10 , (int)this.p4.getX()- 10, (int)this.p4.getY()- 10);
            g2d.drawLine((int)this.p4.getX()- 10 , (int)this.p4.getY()- 10 , (int)this.p1.getX()- 10, (int)this.p1.getY()- 10);
        }
       
    }

    @Override
    public Point getCenterPoint(){
        Point centerPoint = new Point(getLocation().getX() - getWidth() / 2, getLocation().getY() - getHeight() / 2);
        return centerPoint;
    }

    @Override
    public double computeDistance(Point cameraPoint){
    
        double distance1 = Line2D.ptSegDist(this.p1.getX(), this.p1.getY(), this.p2.getX(), this.p2.getY(), cameraPoint.getX(), cameraPoint.getY());
        double distance2 = Line2D.ptSegDist(this.p2.getX(), this.p2.getY(), this.p3.getX(), this.p3.getY(), cameraPoint.getX(), cameraPoint.getY());
        double distance3 = Line2D.ptSegDist(this.p3.getX(), this.p3.getY(), this.p4.getX(), this.p4.getY(), cameraPoint.getX(), cameraPoint.getY());
        double distance4 = Line2D.ptSegDist(this.p1.getX(), this.p1.getY(), this.p4.getX(), this.p4.getY(), cameraPoint.getX(), cameraPoint.getY());
      
        ArrayList<Double> distanceArray = new ArrayList<>();
        distanceArray.add(distance1);distanceArray.add(distance2);distanceArray.add(distance3);distanceArray.add(distance4);

        double shortest_distance = 999999;
        for (double distance : distanceArray){
            if (distance < shortest_distance){
                shortest_distance = distance;
            }
        }
        return shortest_distance;
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

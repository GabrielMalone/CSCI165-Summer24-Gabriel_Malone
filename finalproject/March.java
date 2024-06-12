// Gabriel Malone / CSCI165 / Final Project / Summer 2024
import java.awt.Color;
import java.awt.Graphics2D;

public class March implements Drawable{

    Circle circle;
    Point startinPoint, endPoint;

    public March (Circle circle, Point startingPoint, Point endPoint){
        this.circle = circle;
        this.startinPoint = startingPoint;
        this.endPoint = endPoint;   
    }
    
    @Override
    public void drawObject(Graphics2D g2d) {
        g2d.setColor(Color.green);
        g2d.drawLine((int)startinPoint.getX() -10, (int)startinPoint.getY()-10, (int)(endPoint.getX())-10, (int)endPoint.getY()-10); 
        circle.drawObject(g2d);
    }

}

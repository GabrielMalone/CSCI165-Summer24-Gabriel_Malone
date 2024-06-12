// Gabriel Malone / CSCI165 / Final Project / Summer 2024

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Camera extends Circle implements MouseMotionListener{
    
    int camera_radius = 10;

    public Camera (){}

    @Override
    public void drawObject(Graphics2D g2d) {
        int [] center = new int[2];
        // need to place shape around the point.
        center[0] = (int)getLocation().getX() - camera_radius - 5;
        center[1] = (int)getLocation().getY() - camera_radius - 5;
        g2d.setColor(Color.white);
        g2d.fillOval(center[0], center[1], camera_radius, camera_radius);
      
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        setLocation(new Point(e.getX() + this.camera_radius, e.getY() + this.camera_radius)); 
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

}

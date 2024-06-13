// Gabriel Malone / CSCI165 / Final Project / Summer 2024

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class Camera extends Circle implements MouseMotionListener, MouseListener, MouseWheelListener{
	
	int camera_radius = 10;
	double angle = 0;

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
	public void mousePressed(MouseEvent e){
		if (e.getButton() == MouseEvent.BUTTON1){
				// stop
				RaymarcherPanel.speed = 0;
		}
		if (e.getButton() == MouseEvent.BUTTON3){
				// change directions
				RaymarcherPanel.speed *= -1;
			}
		}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double speed = e.getPreciseWheelRotation();
				// rotate freely 
				RaymarcherPanel.speed -= speed/1000;
	}
	public void mouseDragged(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseClicked(MouseEvent e){}

  
}
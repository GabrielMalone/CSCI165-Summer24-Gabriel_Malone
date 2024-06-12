// Gabriel Malone / CSCI165 / Final Project / Summer 2024
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Color;

/**
 * Displays and updates the logic for the raymarcher.
 */
public class RaymarcherPanel extends JPanel { 
	
	private final RaymarcherRunner raymarcherRunner; // reference to the parent app
	Camera camera;

	public RaymarcherPanel(RaymarcherRunner raymarcherRunner) {
		this.raymarcherRunner = raymarcherRunner;
		this.setPreferredSize(new Dimension(this.raymarcherRunner.getFrame().getHeight(), this.raymarcherRunner.getFrame().getHeight())); 
		addMouseMotionListener(camera = new Camera());
	}
	
	// All drawing code goes here
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// background color
		g2d.setBackground(Color.BLACK);
		g2d.fillRect(0,0,getWidth(), getHeight());
		// get shapes from arrary
		ArrayList<Shape> shape_array = getShapes();
		double shortest_distance = 99999;
		// iterate through array
		for(Shape shape : shape_array){
			// get distance of each shape from camera
			double distance_from_camera = shape.computeDistance(camera.getLocation());
			if (distance_from_camera < shortest_distance){
				// if distance is the shortest, save that distance
				shortest_distance = distance_from_camera;
			} 
			// draw the current shape in the iteration
			shape.drawObject(g2d);	
		}
		// draw the camera object
		camera.drawObject(g2d);
		// draw a circle whose radius extends to the nearest object
		Circle cameraCircle = new Circle(shortest_distance * 2, Color.white, false, camera.getLocation());
	
		cameraCircle.drawObject(g2d);
	}

	ArrayList<Shape> getShapes(){
		// shapes
		Circle c1 		= new Circle(100.5, Color.orange, true, new Point(320,320.0));
		Circle c2 		= new Circle(134.5, Color.magenta, true, new Point(50.1,70.0));
		Rectangle r1 	= new Rectangle(25.5, 100.2, Color.green, true, new Point(300.1,400.4));
		SemiCircle sc1 	= new SemiCircle(200, Color.green, true, new Point(130,373));
		Square s1		= new Square(104, Color.PINK, true, new Point(445, 245));
		Triangle t1 	= new Triangle(new Point(200, 10), new Point(345, 300), new Point(90, 120), Color.red, true);
		// array
		ArrayList<Shape> shape_array = new ArrayList<>();
		shape_array.add(c1);shape_array.add(r1);shape_array.add(s1);shape_array.add(c2);shape_array.add(t1);shape_array.add(sc1);
		return shape_array;
	}
}

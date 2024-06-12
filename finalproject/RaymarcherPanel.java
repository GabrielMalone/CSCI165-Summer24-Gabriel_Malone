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
		g2d.setBackground(Color.BLACK);
		g2d.fillRect(0,0,getWidth(), getHeight());
		ArrayList<Shape> shape_array = shapes();
		for(Shape shape : shape_array){
			double distance = shape.computeDistance(camera.getLocation());
			Circle cameraCircle = new Circle(distance * 2, Color.white, false, camera.getLocation());
			cameraCircle.drawObject(g2d);
			shape.drawObject(g2d);
		}
		camera.drawObject(g2d);
	}

	ArrayList<Shape> shapes(){
		// shapes
		Circle c1 		= new Circle(100.5, Color.orange, true, new Point(320,320.0));
		Circle c2 		= new Circle(134.5, Color.magenta, true, new Point(50.1,70.0));
		Rectangle r1 	= new Rectangle(25.5, 100.2, Color.white, false, new Point(300.1,400.4));
		//SemiCircle sc1 	= new SemiCircle(54, Color.green, true, new Point(30,373));
		Square s1		= new Square(104, Color.PINK, false, new Point(445, 245));
		Triangle t1 	= new Triangle(new Point(200, 10), new Point(345, 300), new Point(90, 120), Color.red, false);
		// array
		ArrayList<Shape> shape_array = new ArrayList<>();
		shape_array.add(c1);;shape_array.add(r1);shape_array.add(s1);shape_array.add(c2);shape_array.add(t1);
		return shape_array;
	}
}

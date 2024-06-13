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
	public static  ArrayList<Shape> shape_array = getShapes();
	public static double speed = 0;
	Ray ray;


	public RaymarcherPanel(RaymarcherRunner raymarcherRunner) {
		this.raymarcherRunner = raymarcherRunner;
		this.setPreferredSize(new Dimension(this.raymarcherRunner.getFrame().getHeight(), this.raymarcherRunner.getFrame().getHeight())); 
		addMouseMotionListener(camera = new Camera());
		addMouseListener(camera);
		addMouseWheelListener(camera);
	}
		
			
	
	// All drawing code goes here
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(Color.BLACK);
		g2d.fillRect(0,0,getWidth(), getHeight());
		ArrayList<March> marchArray = createMarchArray(g2d);
		drawShapes(g2d);
		this.camera.drawObject(g2d);
		this.ray = new Ray();
		this.ray.drawMarches(marchArray, g2d);
		this.camera.angle += speed;
	}
	
	private static ArrayList<Shape> getShapes(){
		// shapes
		Circle c1 		= new Circle(100.5, Color.orange, true, new Point(320,320.0));
		Circle c2 		= new Circle(134.5, Color.magenta, true, new Point(50.1,70.0));
		Rectangle r1 	= new Rectangle(25.5, 100.2, Color.green, true, new Point(300.1,400.4));
		//SemiCircle sc1 	= new SemiCircle(200, Color.green, true, new Point(130,373));
		Square s1		= new Square(104, Color.LIGHT_GRAY, true, new Point(445, 245));
		Triangle t1 	= new Triangle(new Point(200, 10), new Point(345, 300), new Point(90, 120), Color.CYAN, true);
		// array
		ArrayList<Shape> shape_array = new ArrayList<>();
		shape_array.add(c1);shape_array.add(r1);shape_array.add(s1);shape_array.add(c2);shape_array.add(t1);//shape_array.add(sc1);
		return shape_array;
	}

	private double findShortestDistanceInArray(Point nextStepPoint){
		double shortestDistance = 9999;
		for (Shape shape : shape_array){
			double distance = shape.computeDistance(nextStepPoint);
			if (distance < shortestDistance){
				shortestDistance = distance;
			}
		}
		return shortestDistance;
	}

	private ArrayList<March> createMarchArray(Graphics2D g2d){
		ArrayList<March> marchArray = new ArrayList<>();
		Point nextStepPoint = camera.getLocation();
		double shortestDistance = 1;
		while (shortestDistance > 0.01 && shortestDistance < 640){
			for (Shape shape : shape_array){
				shape.drawObject(g2d);
				shortestDistance = findShortestDistanceInArray(nextStepPoint);
				// draw camera
				this.camera.drawObject(g2d);
				// create objects for march
				Circle c 			= new Circle(shortestDistance * 2, Color.white, false, nextStepPoint);
				Point currentPoint 	= new Point (nextStepPoint.getX(), nextStepPoint.getY());
				nextStepPoint 		= new Point(nextStepPoint.getX() + shortestDistance * Math.cos(camera.angle), nextStepPoint.getY() + shortestDistance * Math.sin(camera.angle));
				March march 		= new March(c, currentPoint, nextStepPoint);
				marchArray.add(march);
			}
		}
		return marchArray;
	}

	private void drawShapes(Graphics2D g2d){
		for (Shape shape : shape_array){
			shape.drawObject(g2d);
		}
	}

}
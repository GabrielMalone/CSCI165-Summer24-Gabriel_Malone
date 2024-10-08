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
	public static  ArrayList<Shape> shape_array = getShapes();
	public static double speed = 0;
	Ray ray;
	Camera camera;
	Shape nearestShape;


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
		ArrayList<Shape> shape_array = new ArrayList<>();
		for (int i = 0 ; i < 2 ; i ++){
			Circle 		c 	= RandomShapeGenerator.randomCircleGenerator();
			Circle 		c2	= RandomShapeGenerator.randomCircleGenerator();
			Circle 		c3 	= RandomShapeGenerator.randomCircleGenerator();
			Circle 		c4 	= RandomShapeGenerator.randomCircleGenerator();
			Circle 		c5 	= RandomShapeGenerator.randomCircleGenerator();
			Triangle 	t1  = RandomShapeGenerator.randomTriangleGenerator();
			Square		s1  = RandomShapeGenerator.randomSquareGenerator();
			Rectangle	r1	= RandomShapeGenerator.randomRectangleGenerator();
		
			shape_array.add(c);
			shape_array.add(c2);
			shape_array.add(c3);
			shape_array.add(c4);
			shape_array.add(c5);
			shape_array.add(t1);
			shape_array.add(s1);
			shape_array.add(r1);
		}
		return shape_array;
	}

	private double findShortestDistanceInArray(Point nextStepPoint){
		double shortestDistance = 9999;
		for (Shape shape : shape_array){
			double distance = shape.computeDistance(nextStepPoint);
			if (distance < shortestDistance){
				shortestDistance = distance;
				this.nearestShape = shape;
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
				rayShove();
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
		// draws the shapes in the shape array
		for (Shape shape : shape_array){
			shape.drawObject(g2d);
		}
	}

	private void rayShove(){
		if (this.nearestShape.getClass() == Circle.class && (this.nearestShape.getLocation().getX() >650 || this.nearestShape.getLocation().getY() > 650 || this.nearestShape.getLocation().getX() < 0 || this.nearestShape.getLocation().getY() < 0)){
			shape_array.remove(nearestShape);
			//shape_array.add(RandomShapeGenerator.randomCircleGenerator());
		}
		if (this.nearestShape.getClass() == Circle.class && this.nearestShape.getLocation().getX() + .01 <= 700 && this.nearestShape.getLocation().getY() + .01 <= 700
		&& this.nearestShape.getLocation().getX() - .01 >= -100 && this.nearestShape.getLocation().getY() - .01 >= -100){
			if (camera.getLocation().getX() < this.nearestShape.getLocation().getX() && camera.getLocation().getY() < this.nearestShape.getLocation().getY()){
				this.nearestShape.setLocation(new Point(this.nearestShape.getLocation().getX() + 0.1, this.nearestShape.getLocation().getY() + 0.1));
			}
			if (camera.getLocation().getX() > this.nearestShape.getLocation().getX() && camera.getLocation().getY() > this.nearestShape.getLocation().getY()){
				this.nearestShape.setLocation(new Point(this.nearestShape.getLocation().getX() - 0.1, this.nearestShape.getLocation().getY() - 0.1));
			}
			if (camera.getLocation().getX() < this.nearestShape.getLocation().getX() && camera.getLocation().getY() > this.nearestShape.getLocation().getY()){
				this.nearestShape.setLocation(new Point(nearestShape.getLocation().getX() + 0.1, this.nearestShape.getLocation().getY() - 0.1));
			}
			if (camera.getLocation().getX() > nearestShape.getLocation().getX() && camera.getLocation().getY() < this.nearestShape.getLocation().getY()){
				this.nearestShape.setLocation(new Point(this.nearestShape.getLocation().getX() - 0.1, this.nearestShape.getLocation().getY() + 0.1));
			}
		}		
	}

}
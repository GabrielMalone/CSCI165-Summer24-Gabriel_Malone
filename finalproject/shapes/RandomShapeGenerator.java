import java.util.Random;
import java.awt.Color;

public class RandomShapeGenerator {

	/**
	 * 
	 * @return randomly generator rectangle
	 */
    public static Rectangle randomRectangleGenerator(){
		Random r = new Random();
		int randomColor = r.nextInt(100000,999999);
        String color = "#" + randomColor;
		int boolInt = r.nextInt(1,4);
		boolean filled;
		if (boolInt == 1)
			filled = true;
		else
			filled = false;
		double height = r.nextDouble(1,639);
		double width = r.nextDouble(1,639);
		double x = r.nextDouble(1,639);
		double y = r.nextDouble(1,639);
		Point p = new Point(x, y);
		Rectangle rec = new Rectangle(height, width, Color.decode(color), filled, p);
		return rec;
	}
	
	/**
	 * 
	 * @return randomly generated triangle
	 */
	public static Triangle randomTriangleGenerator(){
		Random r = new Random();
		int randomColor = r.nextInt(100000,999999);
        String color = "#" + randomColor;
		int boolInt = r.nextInt(1,4);
		boolean filled;
		if (boolInt == 1)
			filled = true;
		else
			filled = false;
		double x1 = r.nextDouble(1,639);
		double y1 = r.nextDouble(1,639);
		Point p1 = new Point(x1, y1);
		double x2 = r.nextDouble(1,639);
		double y2 = r.nextDouble(1,639);
		Point p2 = new Point(x2, y2);
		double x3 = r.nextDouble(1,639);
		double y3 = r.nextDouble(1,639);
		Point p3 = new Point(x3, y3);
		Triangle t = new Triangle(p1, p2, p3, Color.decode(color), filled);
		return t;
	}

	/**
	 * 
	 * @return ranbdomly generator Circle
	 */
	public static Circle randomCircleGenerator(){
		Random r = new Random();
		double radius = r.nextDouble(10,100);
		int randomColor = r.nextInt(100000,999999);
        String color = "#" + randomColor;
		int boolInt = r.nextInt(1,4);
		boolean filled;
		if (boolInt == 1)
			filled = true;
		else
			filled = false;
		double x = r.nextDouble(1,639);
		double y = r.nextDouble(1,639);
		Point p = new Point(x, y);
		Circle c = new Circle(radius, Color.decode(color), filled, p);
		return c;
	}

	/**
	 * 
	 * @return randomly generated Square
	 */
	public static Square randomSquareGenerator(){
		Random r = new Random();
		double side = r.nextDouble(10,100);
		int randomColor = r.nextInt(100000,999999);
        String color = "#" + randomColor;
		int boolInt = r.nextInt(1,2);
		boolean filled;
		if (boolInt == 1)
			filled = true;
		else
			filled = false;
		double x = r.nextDouble(1,639);
		double y = r.nextDouble(1,639);
		Point p = new Point(x, y);
		Square s = new Square(side, Color.decode(color), filled, p);
		return s;
	}    
    
}

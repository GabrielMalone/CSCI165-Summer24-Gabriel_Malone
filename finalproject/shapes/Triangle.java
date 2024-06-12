// Gabriel Malone / CS165 / Summer 2024 / Week 13
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Triangle extends Shape implements Resizable{

	double sideA, sideB, sideC;
	Point p1, p2, p3;

	/**
	 * base and height constructor
	 * @param base
	 * @param height
	 */
	public Triangle(double sideA, double sideB, double sideC){
		if (sideLengthsValid(sideA, sideB, sideC)){
			this.sideA = sideA;
			this.sideB = sideB;
			this.sideC = sideC;
		}
	}

	/**
	 * base and height constructor
	 * @param base
	 * @param height
	 */
	public Triangle(Point p1, Point p2, Point p3, Color color, boolean filled){
		if (sideLengthsValid(p1, p2, p3)){
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
			this.sideA = Point.distance(p1, p2);
			this.sideB = Point.distance(p2, p3);
			this.sideC = Point.distance(p1, p3);
		}
		this.color 	= color;
		this.filled = filled;
	}

	/**
	 * fully loaded constructor
	 * @param base
	 * @param height
	 * @param color
	 * @param filled
	 * @param point
	 */
	public Triangle (double sideA, double sideB, double sideC, Color color, boolean filled, Point point){
		if (sideLengthsValid(sideA, sideB, sideC)){
			this.sideA = sideA;
			this.sideB = sideB;
			this.sideC = sideC;
			this.filled = filled;
			this.color = color;
			this.point = point;
		}
	}

	public Triangle (Triangle toCopy){
		this.sideA 	= toCopy.sideA;
		this.sideB 	= toCopy.sideB;
		this.sideC	= toCopy.sideC;
		this.filled = toCopy.filled;
		this.color 	= toCopy.color;
		this.point 	= toCopy.point;
	}

	private boolean sideLengthsValid(double sideA, double sideB, double sideC){
		if (sideC > sideA + sideB )return false;
		if (sideB > sideC + sideA )return false;
		if (sideA > sideC + sideB )return false;
		return true;
	}
	
	private boolean sideLengthsValid(Point p1, Point p2, Point p3){
		double sideA = Point.distance(p1, p2);
		double sideB = Point.distance(p2, p3);
		double sideC = Point.distance(p1, p3);
		if (sideC > sideA + sideB )return false;
		if (sideB > sideC + sideA )return false;
		if (sideA > sideC + sideB )return false;
		return true;
	} 

	public void setSides(double sideA, double sideB, double sideC){
		if (sideLengthsValid(sideA, sideB, sideC)){
			this.sideA = sideA;
			this.sideB = sideB;
			this.sideC = sideC;
		}
	}

	public double getSideA() {
		return sideA;
	}

	public double getSideB() {
		return sideB;
	}

	public double getSideC() {
		return sideC;
	}


	/**
	 * 
	 * @return point object at the center of this triangle
	 */
	@Override
	public Point getCenterPoint(){
		double x = (p1.getX() + p2.getX() + p3.getX()) / 3;
		double y = (p1.getY() + p2.getY() + p3.getY()) / 3;
		Point centerPoint = new Point(x, y);
		return centerPoint;
	}

	/**
	 * return area of triangle 1/2base*height
	 */
	@Override
	public double getArea(){
		// Heron's formula for area of any triangle with known sides
		// get semiperimeter
		double s = (this.sideA + this.sideB + this.sideC) / 2;
		// apply Heron's formula
		return Math.sqrt(s*(s-sideA)*(s-sideB)*(s-sideC));
	}

	/**
	 * 
	 * @return height of triangle
	 */
	public double getHeight(){
		// finding height with Heron's formula applied for area
		return (2 * getArea()) / sideB;
	}

	/**
	 * Method to calculate perimeter of triangle
	 */
	@Override
	public double getPerimeter(){
		return this.sideA + this.sideB + this.sideC;
	}

	/**
	 * Method to resize triangle
	 */
	@Override
	public void resize(int percent){
		double percentDouble = (double)percent / 100;
		this.sideA *= percentDouble;
		this.sideB *= percentDouble;
		this.sideC *= percentDouble;
	}
	/**
	 * Method to draw a triangle on JPanel
	 */
	@Override
    public void drawObject (Graphics2D g2d){
		g2d.setColor(this.color);

		int[] xarray = new int[3];
		int[] yarray = new int[3];

		xarray[0] = (int)this.p1.getX();
		xarray[1] =	(int)this.p2.getX();
		xarray[2] =	(int)this.p3.getX();

		yarray[0] =	(int)this.p1.getY();
		yarray[1] =	(int)this.p2.getY();
		yarray[2] =	(int)this.p3.getY();
		
		if (this.filled)
			g2d.fillPolygon(xarray, yarray, 3);
		else 
			g2d.drawPolygon(xarray, yarray, 3);
	
    }

	@Override
    public double computeDistance(Point cameraPoint){
        double distance1 = Line2D.ptSegDist(this.p1.getX(), this.p1.getY(), this.p2.getX(), this.p2.getY(), cameraPoint.getX(), cameraPoint.getY());
        double distance2 = Line2D.ptSegDist(this.p2.getX(), this.p2.getY(), this.p3.getX(), this.p3.getY(), cameraPoint.getX(), cameraPoint.getY());
        double distance3 = Line2D.ptSegDist(this.p1.getX(), this.p1.getY(), this.p3.getX(), this.p3.getY(), cameraPoint.getX(), cameraPoint.getY());
        ArrayList<Double> distanceArray = new ArrayList<>();
        distanceArray.add(distance1);distanceArray.add(distance2);distanceArray.add(distance3);
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
		Triangle other = (Triangle) obj;
		if (Double.doubleToLongBits(sideA) != Double.doubleToLongBits(other.sideA))
			return false;
		if (Double.doubleToLongBits(sideB) != Double.doubleToLongBits(other.sideB))
			return false;
		if (Double.doubleToLongBits(sideC) != Double.doubleToLongBits(other.sideC))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return " Triangle [sideA=" + this.sideA + ", sideB=" + this.sideB + ", sideC=" + this.sideC + ", area=" + getArea() + ", perimeter=" + getPerimeter() +", height=" + getHeight() +  "]"
		+ "\n" + super.toString();
	}

}

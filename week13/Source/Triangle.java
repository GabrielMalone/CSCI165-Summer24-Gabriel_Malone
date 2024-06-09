// Gabriel Malone / CS165 / Summer 2024 / Week 13
import java.awt.Color;

public class Triangle extends Shape{

	double sideA;
	double sideB;
	double sideC;

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
			setColor(color);
			setFilled(filled);
			setLocation(point);
		}
	}

	private boolean sideLengthsValid(double sideA, double sideB, double sideC){
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
	 *  probably won't use this
	 * @return longest side of triangle
	 */
	public double longestSide(){
		double longestSide = 0;
		double [] sides = new double[3];
		sides [0] = this.sideA;
		sides [1] = this.sideB;
		sides [2] = this.sideC;
		for (double side : sides){
			if (side > longestSide){
				longestSide = side;
			}
		}
		return longestSide;
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

	public double getHeight(){
		// finding height with Heron's formula applied for area
		return (2 * getArea()) / sideB;
	}

	@Override
	public double getPerimeter(){
		return this.sideA + this.sideB + this.sideC;
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
		return super.toString() + " Triangle [sideA=" + this.sideA + ", sideB=" + this.sideB + ", sideC=" + this.sideC + ", area=" + getArea() + ", perimeter=" + getPerimeter() +", height=" + getHeight() +  "]";
	}

}

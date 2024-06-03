// Gabriel Malone / CSCI165 / Summer 2024/ Week 11

public class Point3D extends Point {

   	public int z;
	private int [] XYZcoords = new int [3];

	/**
	 * No arguments constructor
	 */
	public Point3D (){}

	/**
	 * Overloaded constructor
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3D (int x, int y, int z){
		setX(x);
		setY(y);
		this.z = z;
		this.XYZcoords[0] = x;
		this.XYZcoords[1] = y;
		this.XYZcoords[2] = z;
	}

	/**
	 * Copy constructor
	 * @param copy
	 */
	public Point3D (Point3D copy){
		setX(copy.getX());
		setY(copy.getY());
		this.z = copy.z;
		this.XYZcoords[0] = copy.getX();
		this.XYZcoords[1] = copy.getY();
		this.XYZcoords[2] = copy.z;
	}

	/**
	 * set Z coordinate
	 * @param z
	 */
	public void setZ(int z){
		this.z = z;
		this.XYZcoords[0] = getX();
		this.XYZcoords[1] = getY();
		this.XYZcoords[2] = z;
	}

	/**
	 * Set XYZ coordinates with ints for all coordinates
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setXYZ(int x, int y, int z){
		setX(x);
		setY(y);
		this.z = z;
		this.XYZcoords[0] = x;
		this.XYZcoords[1] = y;
		this.XYZcoords[2] = z;
	}

	/**
	 * set XYZ coordinate with Point object for x and y coordinates
	 * @param xy
	 * @param z
	 */
	public void setXYZ(Point xy, int z){
		setCoords(xy.getX(),xy.getY());
		this.z = z;
		this.XYZcoords[0] = getX();
		this.XYZcoords[1] = getY();
		this.XYZcoords[2] = z;
	}

	/**
	 * return array of x y z coordinates
	 * @return
	 */
	public int [] getXYZ(){
        int [] XYZcoordCopy = new int[3];
       	XYZcoordCopy [0] = this.XYZcoords[0]; 
		XYZcoordCopy [1] = this.XYZcoords[1];
		XYZcoordCopy [2] = this.XYZcoords[2]; 
        return XYZcoordCopy;
	}

	/**
	 * return string of 3Dpoints state
	 */
	@Override
	public String toString(){
		String XYZcoordsString = super.toString() + " Z=" + this.z;
		return XYZcoordsString;
	}
	
	/**
     * returns boolean true if one object equals another
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj)                    return true;
        if (obj == null)                    return false;
        if (getClass() != obj.getClass())   return false;
        // downcasting
        Point3D otherPoint3D = (Point3D) obj;
		if (getCoords() == null 
			&& otherPoint3D.getCoords() != null){
			return false;
            } 
		int [] otherXYZcoords = new int[3];
		otherXYZcoords = otherPoint3D.getXYZ();
		if (this.XYZcoords[0] != otherXYZcoords[0]) return false;
		if (this.XYZcoords[1] != otherXYZcoords[1]) return false;
		if (this.XYZcoords[2] != otherXYZcoords[2]) return false;
        return true;
    }
}

// Gabriel Malone / CS165 / Summer 2024 / Week 11

public class MoveablePoint extends Point {
    public float xSpeed = 0.0f, ySpeed = 0.0f;

    public MoveablePoint (){}
    /**
     * Overloaded constructor
     * @param xSpeed float
     * @param ySpeed float
     */
    public MoveablePoint (float xSpeed, float ySpeed){
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    /**
     * Overloaded constructor
     * @param x int
     * @param y int
     * @param xSpeed float
     * @param ySpeed float
     */
    public MoveablePoint (int x, int y, float xSpeed, float ySpeed){
        this.setX(x);
        this.setY(y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    /**
     * Overloaded constructor
     * @param xy Point object
     * @param xSpeed float
     * @param ySpeed float
     */ 
    public MoveablePoint (Point xy, float xSpeed, float ySpeed){
        this.setCoords(xy.getX(),xy.getY());
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    /**
     * Copy constructor for privacy
     * @param toCopy
     */
    public MoveablePoint (MoveablePoint toCopy){
        toCopy.setY(this.getY());
        toCopy.setX(this.getX());
        toCopy.xSpeed = this.xSpeed;
        toCopy.ySpeed = this.ySpeed;
    }

    /**
     * set xSpeed
     * @param xSpeed float
     */
    public void setXSpeed(float xSpeed){
        this.xSpeed = xSpeed;
    }

    /**
     * set ySpeed
     * @param ySpeed float
     */
    public void setYSpeed(float ySpeed){
        this.ySpeed = ySpeed;
    }

    /**
     * Set speed for x and y 
     * @param xSpeed float
     * @param ySpeed float
     */
    public void setSpeeds(float xSpeed, float ySpeed){
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    /**
     * Set speeds for both coordinates
     * @return MoveablePoint object
     */
    public MoveablePoint move(){
        setX(getX() + (int)this.xSpeed);
        setY(getY() + (int)this.ySpeed);
        return this;
    }

    /**
     * get x speed
     * @return float
     */
    public float getXSpeed (){
        return this.xSpeed;
    }

    /**
     * get y speed
     * @return float
     */
    public float getYSpeed (){
        return this.ySpeed;
    }

    /**
     * get x and y speeds in an array
     * @return float
     */
    public float[] getSpeeds(){
        float [] result = new float[2];
        result [0] = this.xSpeed;
        result [1] = this.ySpeed;
        return result;
    }

    /**
     * Returns a string of the current Object's state
     */
    @Override
    public String toString(){
        String MoveablePointString = super.toString() + " X speed: " + getXSpeed() + "Y speed: " + getYSpeed();
        return MoveablePointString;
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
        Point otherPoint = (Point) obj;

        if (super.getCoords() == null 
            && otherPoint.getCoords() != null){
            return false;
            } 
        else if (super.getX() != otherPoint.getX() 
            &&  super.getY() != otherPoint.getY()){
            return false;
            }
        MoveablePoint otherMoveablePoint = (MoveablePoint) obj;
        if (this.xSpeed != otherMoveablePoint.getXSpeed()
            || this.ySpeed != otherMoveablePoint.getYSpeed()){
            return false;
        }
        return true;
    }
}

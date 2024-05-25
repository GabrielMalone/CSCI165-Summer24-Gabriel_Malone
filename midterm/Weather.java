// Gabriel Malone / CS165 / Midterm / Summer 2024

public class Weather {
	
	//private Random rand = new Random();
    
	public static enum DIRECTION {
		NORTH,
		EAST,
		SOUTH,
		WEST;
	}
    public DIRECTION direction;
    public String windDirection;

	/**
	 * Method to create wind pattern
	 *
	 */
	public void pattern(){
		// 	this combo of random spots set to windy then their neighbors
		// 	randomly being set to windy seems to create the
		//	most pleasing wind pattern so far
		windOn();
	}

	/**
	 * Method to set ENUM direction of a cell's weather pattern
	 * @param direction
	 */
    public void setDirection (DIRECTION direction){
		this.direction = direction;
        switch (this.direction) {
            case NORTH: this.windDirection 	= "NORTH";
                break;
            case SOUTH: this.windDirection	= "SOUTH";
                break;
            case EAST: 	this.windDirection 	= "EAST";
                break;
            case WEST: 	this.windDirection 	= "WEST";
				break;
			default: 	this.windDirection 	= "X";
                break;
	    }
    }

	/**
	 * 
	 * @return DIRECTION of wind
	 */
    public DIRECTION getDirection(){
		return this.direction;
	}

	/**
	 * Method to return the string of a weather direction of a cell
	 * @return String  of a weather direction of a cell
	 */
	public String getStringDirection(){
		if (this.direction == null) return "X";
		switch (this.direction) {
            case NORTH: return 	"NORTH";
            case SOUTH: return 	"SOUTH";
            case EAST: 	return 	"EAST";
            case WEST: 	return 	"WEST";
			default:	return 	"x";
	    }
	}


	/**
	 * Method to clear the current weather pattern
	 */
	public void clearWeatherPattern(){
		for (int i = 0; i < Driver.neWorld.worldMatrix.length ; i ++ ){
			for (int j = 0; j < Driver.neWorld.worldMatrix.length ; j ++ ){
				Driver.neWorld.worldMatrix[i][j].setWeather(Cell.WEATHER.CALM);
			}
		}
	}

	/**
	 * Method to set a random spot in the matrix as windy
	 */
	public void windOn(){
		for (int i = 0; i < Driver.neWorld.worldMatrix.length ; i ++ ){
			for (int j = 0; j < Driver.neWorld.worldMatrix.length ; j ++ ){
				Driver.neWorld.worldMatrix[i][j].setWeather(Cell.WEATHER.WINDY);
			}
		}
	}
}









public class Cell {

	// static means these are availabe for any method to use 
	// and that these variables do not change when a new instance of 
	// this class is created

	public static enum STATES {
		EMPTY, 
		TREE, 
		BURNING,
		NULL;
	}
	public static enum WEATHER {
		WINDY,
		CALM;
	}
	private STATES state;
	private WEATHER weather = WEATHER.CALM;
	private String position;
	double burnMultiplier = 1.0;
	String cellColor;
	String coordinates;
	
	/**
	 * Method to assign a color to a cell object
	 * 
	 */
	public void SetCellColor(){
		switch (this.state) {
			case BURNING: 	this.cellColor = Terminal_Graphics.RED;
							break;
			case TREE: 		this.cellColor = Terminal_Graphics.GREEN;
							break;
			case EMPTY: 	this.cellColor = Terminal_Graphics.YELLOW;
							break;
			case NULL: 	this.cellColor = Terminal_Graphics.PINK;
							break;
			default:
							break;
		}
	}

	public void setState (STATES state){
		this.state = state;
		}

	public STATES getState(){
		if (this.state == null) return Cell.STATES.NULL;
		return this.state;
	}

	public void setRelativePosition(String position){
		this.position = position;
	}

	public String getRelativePosition(){
		return this.position;
	}

	public void setWeather(WEATHER weather){
		this.weather = weather;
	}

	public WEATHER getCellWeather(){
		return this.weather;
	}

	public double getBurnMultiplier(){
		// if cell burning and windy, increase burn chance of cell in path of wind
		if (this.weather.equals(WEATHER.WINDY)){
			this.burnMultiplier *= -1;
		}
		return this.burnMultiplier;
	}
	/**
	 * Method to return the cell's coordinates in integer form
	 * @return array of integers
	 */
	public int[] convertCoordsToInteger(){
		String [] coordinateStrings = this.coordinates.split(",");
		int x = Integer.valueOf(coordinateStrings[0]);
		int y = Integer.valueOf(coordinateStrings[1]);
		int [] intCoords = {x, y};
		return intCoords;
	}

	public boolean equals(Cell otherCell){
		if (this.coordinates.equals(otherCell.coordinates)) return true;
		else return false;
	}
}
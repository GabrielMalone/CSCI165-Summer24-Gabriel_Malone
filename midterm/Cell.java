
public class Cell {

	// static means these are availabe for any method to use 
	// and that these variables do not change when a new instance of 
	// this class is created

	public static enum STATES {
		EMPTY, 
		TREE, 
		BURNING,
	}

	public static enum WEATHER {
		WINDY,
		CALM;
	}

	public static enum OBJECTS{
		VOID,
		WILDLIFEALIVE,
		WILDLIFEDEAD;
	}
	
	private STATES state;
	private WEATHER weather = WEATHER.CALM;
	private OBJECTS object = OBJECTS.VOID;
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
			default:
							break;
		}
	}

	public void setObject (OBJECTS object){
		this.object = object;
	}

	public OBJECTS getObject(){
		return this.object;
	}
	
	public void setState (STATES state){
		this.state = state;
		}

	public STATES getState(){
		return this.state;
	}

	public void setWeather(WEATHER weather){
		this.weather = weather;
	}

	public WEATHER getCellWeather(){
		return this.weather;
	}

	public double burnMultiplier(){
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
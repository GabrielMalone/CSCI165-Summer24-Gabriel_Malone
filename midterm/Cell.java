


public class Cell {

	// static means these are availabe for any method to use 
	// and that these variables do not change when a new instance of 
	// this class is created

	public static enum STATES {
		EMPTY, 
		TREE, 
		BURNING,
		EMBERS;
	}
	public static enum WEATHER {
		WINDY,
		CALM;
	}
	private STATES state;
	private WEATHER weather;
	String cellColor;
	String coordinates;
	private String position;

	
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
			case EMBERS: 	this.cellColor = Terminal_Graphics.PINK;
							break;
			default:
							break;
		}
	}

	public void setState (STATES state){
		this.state = state;
		}

	public STATES getState(){
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

}


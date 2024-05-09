


public class Cell {
	// static means these are availabe for any method to use 
	// and that these variables do not change when a new instance of 
	// this class is created
	public static enum STATES {
		EMPTY, 
		TREE, 
		BURNING;
	}
	public static String YELLOW = "255-255-051";
	public static String GREEN  = "000-204-000";
	public static String RED    = "255-000-000";
	private STATES state;
	String cellColor;
	private String position;
	
	/**
	 * Method to assign a color to a cell object
	 * 
	 */
	public void SetCellColor(){
		switch (this.state) {
			case BURNING: 	this.cellColor = RED;
				break;
			case TREE: 		this.cellColor = GREEN;
			break;
			case EMPTY: 	this.cellColor = YELLOW;
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



}


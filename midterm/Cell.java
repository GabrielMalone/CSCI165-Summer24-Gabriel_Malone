
import java.util.Random;
import java.awt.image.BufferedImage;

public class Cell {

	
	// static means being availabe for any method to use 
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
		WILDLIFEDEAD,
		BOMB;
	}
	public static enum FIREMOVING{
		VOID,
		NORTH,
		SOUTH,
		EAST,
		WEST,
		NORTHEAST,
		NORTHWEST,
		SOUTHEAST,
		SOUTHWEST;
	}

	public static enum POSITIONASNEIGHBOR{
		NORTH,
		SOUTH,
		EAST,
		WEST,
		NORTHEAST,
		NORTHWEST,
		SOUTHEAST,
		SOUTHWEST;
	}

	private STATES state;
	private WEATHER weather = WEATHER.CALM;
	private OBJECTS object = OBJECTS.VOID;
	private FIREMOVING firemoving = FIREMOVING.VOID;
	private POSITIONASNEIGHBOR position;
	double burnMultiplier = 1.0;
	public String cellColor;
	int row;
	int column;
	String coordinates;
	public BufferedImage stateimage;
	public BufferedImage animalimage;

	/**
	 * Method to assign a color to a cell object
	 * 
	 */
	public void SetCellColor(){
		switch (this.state) {

			case BURNING: 		this.cellColor = Terminal_Graphics.RED;
							break;
			case TREE: 			this.cellColor = Terminal_Graphics.GREEN;
							break;
			case EMPTY: 		this.cellColor = Terminal_Graphics.YELLOW;
							break;
			default:
							break;
		}
	}

	public void setPositionAsNeighbor (POSITIONASNEIGHBOR position){
		this.position = position;
	}

	public POSITIONASNEIGHBOR getPosition(){
		return this.position;
	}

	public void setFireMoving(FIREMOVING firemoving){
		this.firemoving = firemoving;
	}

	public FIREMOVING getFireMoving(){
		return this.firemoving;
	}

	public void setObject (OBJECTS object){
		this.object = object;
		//Random rand = new Random();
		//int randindex = rand.nextInt(2);
		if (this.object == Cell.OBJECTS.WILDLIFEALIVE) 	this.animalimage = World.anima[1];
		//if (this.object == Cell.OBJECTS.WILDLIFEDEAD) 	this.animalimage = World.anima[0];
	}

	public OBJECTS getObject(){
		return this.object;
	}
	
	public void setState (STATES state){

		this.state = state;

		Random rand = new Random();
		int randindex = rand.nextInt(4);

		if (this.state == Cell.STATES.TREE){
			this.stateimage = World.trees[randindex];
		}

		else if (this.state == Cell.STATES.BURNING && this.firemoving == Cell.FIREMOVING.EAST){
			this.stateimage = World.fires[0];
		}

		else if (this.state == Cell.STATES.BURNING && this.firemoving == Cell.FIREMOVING.NORTH){
			this.stateimage = World.fires[1];
		}

		else if (this.state == Cell.STATES.BURNING && this.firemoving == Cell.FIREMOVING.NORTHWEST){
			this.stateimage = World.fires[1];
		}

		else if (this.state == Cell.STATES.BURNING && this.firemoving == Cell.FIREMOVING.NORTHEAST){
			this.stateimage = World.fires[1];
		}

		else if (this.state == Cell.STATES.BURNING && this.firemoving == Cell.FIREMOVING.WEST){
			this.stateimage = World.fires[2];
		}

		else if (this.state == Cell.STATES.BURNING && this.firemoving == Cell.FIREMOVING.SOUTH){
			this.stateimage = World.fires[3];
		}

		else if (this.state == Cell.STATES.BURNING && this.firemoving == Cell.FIREMOVING.SOUTHWEST){
			this.stateimage = World.fires[3];
		}

		else if (this.state == Cell.STATES.BURNING && this.firemoving == Cell.FIREMOVING.SOUTHEAST){
			this.stateimage = World.fires[3];
		}

		else if (this.state == Cell.STATES.EMPTY){
			this.stateimage = World.burnt[0];
		}

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
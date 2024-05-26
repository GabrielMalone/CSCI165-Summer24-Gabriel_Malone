// Gabriel Malone / CS165 / Midterm / Summer 2024

import java.util.Random;
import java.awt.image.BufferedImage;


public class Cell {

	// static means that a variable/method will be shared by all instances of that class
	// and that each instance of a class does not have a different value for that variable
	// or logic for that method. 

	// basic states for a cell
	public static enum STATES {
		EMPTY,
		TREE,
		BURNING,
		BURNT;
	}
	// weather states for a cell
	public static enum WIND {
		WINDY,
		CALM,
	}
	// weather states for a cell
	public static enum RAIN {
		RAINING,
		NOT_RAINING;
	}
	// objects a cell can 'hold'
	public static enum OBJECTS{
		VOID,
		WILDLIFEALIVE,
		WILDLIFEDEAD,
		DECAYING,
		BOMB,
	}
	// data about fire direction a cell can have
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
	// data about a position a cell can have
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
	// initialize enums/variables
	private STATES state;
	private WIND weather = WIND.CALM;
	private RAIN rain;
	private OBJECTS object = OBJECTS.VOID;
	private FIREMOVING firemoving = FIREMOVING.VOID;
	private POSITIONASNEIGHBOR position;
	double windMultiplier = 0;
	double rainMultiplier = 0;
	int row;
	int column;
	private String [] Colors = {"255-255-051","000-204-000","255-000-000", "000-000-000"};
	public String cellColor;
	public String coordinates;
	public BufferedImage stateimage;
	public BufferedImage animalimage;
	public BufferedImage weatherimage;
	public boolean moved = false;
	public boolean rain_moved = false;
	private Random rand = new Random();
	public boolean raining = false;

	public Cell(){

	}

	/**
	 * Method to assign a color to a cell object
	 * 
	 */
	public void SetCellColor(){
		switch (this.state) {
			case BURNING: 		this.cellColor = Colors[2];
							break;
			case TREE: 			this.cellColor = Colors[1];
							break;
			case EMPTY: 		this.cellColor = Colors[0];
							break;
			case BURNT: 		this.cellColor = Colors[3];
			break;
					default:
							break;
		}
	}

	/*
	 * Method to set a cells position as a neighbor
	 */
	public void setPositionAsNeighbor (POSITIONASNEIGHBOR position){
		this.position = position;
	}

	/**
	 * Method to return a cell's position as a neighbor
	 * @return cell's position as a neighbor
	 */
	public POSITIONASNEIGHBOR getPosition(){
		return this.position;
	}

	/*
	 * Method to set the directio a fire is moving through a cell
	 */
	public void setFireMoving(FIREMOVING firemoving){
		this.firemoving = firemoving;
	}

	/**
	 * Method to get the direction a fire is moving through a cell
	 * @return cell's fire direction
	 */
	public FIREMOVING getFireMoving(){
		return this.firemoving;
	}

	/*
	 * Method to set a cell's object
	 */
	public void setObject (OBJECTS object){
		this.object = object;
		// set image for animals
		if (this.object == Cell.OBJECTS.WILDLIFEALIVE) this.animalimage = World.anima[0];
		if (this.object == Cell.OBJECTS.WILDLIFEDEAD) this.animalimage = World.anima[1];
	}

	/**
	 * Method to get a cell's object
	 * @return cell's object
	 */
	public OBJECTS getObject(){
		return this.object;
	}

	/*
	 * Method to set the state of a cell
	 */
	public void setState (STATES state){
		this.state = state;
		setStateImages();
	}

	/*
	 * Method to set images for a cell
	 */
	private void setStateImages(){
	
		// sets a cell's tree image randomly
		int randindex = this.rand.nextInt(4);

		if (this.state == Cell.STATES.TREE){
			this.stateimage = World.trees[randindex];
		}
		// if cell on fire, sets the fire image based on direction the cell is moving
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
		else if (this.state == Cell.STATES.BURNING && this.firemoving == Cell.FIREMOVING.VOID){
			this.stateimage = World.fires[3];
		}
		else if (this.state == Cell.STATES.EMPTY){
			this.stateimage = World.burnt[0];
		}
		else if (this.state == Cell.STATES.BURNT){
				this.stateimage = World.burnt[0];
		}
	}

	/**
	 * Method to get state of current cell
	 * @return cell's state
	 */
	public STATES getState(){
		return this.state;
	}
		/*
	 * Method to set the cell's rain
	 */
	public void setRain(RAIN rain){
		this.rain = rain; 
		//int randindex = this.rand.nextInt(3);
		this.weatherimage = World.winds[1];
	}
	/**
	 * Method to get a cell's weather
	 * @return cell's weather
	 */
	public RAIN getRain(){
		return this.rain;
	}

	/*
	 * Method to set the cell's weather
	 */
	public void setWind(WIND weather){
		this.weather = weather; 
	}

	/**
	 * Method to get a cell's weather
	 * @return cell's weather
	 */
	public WIND getCellWind(){
		return this.weather;
	}

	/**
	 * Method to determine a cell's readiness to burn
	 * @return double of cell's increased/decreased burn value
	 */
	
	public double rainEffects (double chanceToBurn) {
		double chance = rand.nextDouble(0, 1);
		return chanceToBurn += chance;
	}
	/*
	 * Method to see if one cell is the same as another cell in the matrix
	 */
	public boolean cellEquals(Cell otherCell){
		if (this.coordinates.equals(otherCell.coordinates)) return true;
		else return false;
	}
}
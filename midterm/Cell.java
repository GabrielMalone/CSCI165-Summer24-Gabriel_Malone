// Gabriel Malone / CS165 / Midterm / Summer 2024

import java.util.Random;
import java.awt.image.BufferedImage;

public class Cell {

	// static means that variable/method will be shared by all instances of that class

	// basic states for a cell
	public static enum STATES {
		EMPTY,
		TREE,
		BURNING,
		BURNT;
	}
	// weather states for a cell
	public static enum WEATHER {
		WINDY,
		CALM;
	}
	// objects a cell can 'hold'
	public static enum OBJECTS{
		VOID,
		WILDLIFEALIVE,
		WILDLIFEDEAD,
		DECAYING,
		BOMB;
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
	private WEATHER weather = WEATHER.CALM;
	private OBJECTS object = OBJECTS.VOID;
	private FIREMOVING firemoving = FIREMOVING.VOID;
	private POSITIONASNEIGHBOR position;
	double burnMultiplier = 0;
	public String cellColor;
	int row;
	int column;
	String coordinates;
	public BufferedImage stateimage;
	public BufferedImage animalimage;
	public boolean moved = false;

	public Cell(){

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
		if (this.object == Cell.OBJECTS.WILDLIFEALIVE) 	this.animalimage = World.anima[1];
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
		Random rand = new Random();
		// sets a cell's tree image randomly
		int randindex = rand.nextInt(4);

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
	 * Method to set the cell's weather
	 */
	public void setWeather(WEATHER weather){
		this.weather = weather;
	}

	/**
	 * Method to get a cell's weather
	 * @return cell's weather
	 */
	public WEATHER getCellWeather(){
		return this.weather;
	}

	/**
	 * Method to determine a cell's readiness to burn
	 * @return double of cell's increased/decreased burn value
	 */
	public double burnMultiplier(){
		// if cell burning and windy, increase burn chance of cell in path of wind
		if (this.weather.equals(WEATHER.WINDY)){
			this.burnMultiplier -= .005;
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

	/*
	 * Method to see if one cell is the same as another cell in the matrix
	 */
	public boolean cellEquals(Cell otherCell){
		if (this.coordinates.equals(otherCell.coordinates)) return true;
		else return false;
	}
}
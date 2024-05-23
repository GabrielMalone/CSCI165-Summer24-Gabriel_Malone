// Gabriel Malone / CS165 / Midterm / Summer 2024

import java.util.Random;

public class Weather {
	Random rand = new Random();
    public static enum DIRECTION {
		NORTH, 
		EAST, 
		SOUTH,
		WEST;
	}
    public DIRECTION direction;
    public String windDirection;
	public double windyProb = .35;

	/**
	 * Method to create wind pattern
	 * 
	 */
	public void pattern(){
		// 	this combo of random spots set to windy then their neighbors
		// 	randomly being set to windy seems to create the 
		//	most pleasing wind pattern so far. 
		randomWindSpot();
		designatetNeighborsWindy();
	}

	/*
	 * Method to set ENUM direction of a cell's weather pattern
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

	/*
	 * Method to get the weather direction of a cell
	 */
    public DIRECTION getDirection(){
		return this.direction;
	}

	/*
	 * Method to return the string of a weather direction of a cell
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

	/*
	* Method to clear the current weather pattern
	 */ 
	public void clearWeatherPattern(){
		for (int i = 0; i < Driver.neWorld.worldMatrix.length ; i ++ ){
			for (int j = 0; j < Driver.neWorld.worldMatrix.length ; j ++ ){
				Driver.neWorld.worldMatrix[i][j].setWeather(Cell.WEATHER.CALM);
			}
		}
	}

	/*
	 * Method to see if the current cell is windy
	 */
	private  boolean somethingWindy(Cell currentCell){
		if (currentCell.getCellWeather().equals(Cell.WEATHER.WINDY)) return true;
		return false;
	}

	/*
	 * Method to set nearby cells as windy
	 */
	void designatetNeighborsWindy(){
        for (int f = 1 ; f < Driver.neWorld.worldMatrix.length - 1; f ++ ){
            for(int g = 1 ; g < Driver.neWorld.worldMatrix.length - 1; g ++){    
				Cell currentCell = Driver.neWorld.worldMatrix[f][g];
				if (somethingWindy(currentCell)){
					currentCell.setWeather(Cell.WEATHER.CALM);
                    	Cell [] neighboringCells = Driver.neWorld.findNeighbors(f, g);
                    	seeWhatBlows(neighboringCells, currentCell);
					}
                }
            }
        } 
	
	/*
	* Method to see if a cell should be set as windy or not
	*/	
	private void seeWhatBlows(Cell[] neighboringcells, Cell homeCell){
		for (Cell cell : neighboringcells){
            if(cell.getCellWeather().equals(Cell.WEATHER.CALM)){
				double chancetoBlow = Driver.neWorld.probCatch();
				if 	(chancetoBlow < this.windyProb){
					Driver.neWorld.nextStep[cell.row][cell.column].setWeather(Cell.WEATHER.WINDY);
                }
            }
		}
	}

	/*
	 * Method to set a random spot in the matrix as windy
	 */
	public void randomWindSpot(){
		// set 3 random fires after main fire goes out
		for (int i = 0 ; i < Driver.numberOfFires ; i ++){
			int rand_index = rand.nextInt(1, Driver.neWorld.worldMatrix.length-1);
			int rand_index_b = rand.nextInt(1, Driver.neWorld.worldMatrix.length-1);
			Cell currentCell = Driver.neWorld.worldMatrix[rand_index][rand_index_b];
			if (currentCell.getCellWeather() == Cell.WEATHER.CALM){
				currentCell.setWeather(Cell.WEATHER.WINDY);
				
			}	
		}
	}
}

   

    

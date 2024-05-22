
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
		randomWindSpot();
		designatetNeighborsWindy();
	}

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
    public DIRECTION getDirection(){
		return this.direction;
	}
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
	public void clearWeatherPattern(){
		for (int i = 0; i < Driver.neWorld.worldMatrix.length ; i ++ ){
			for (int j = 0; j < Driver.neWorld.worldMatrix.length ; j ++ ){
				Driver.neWorld.worldMatrix[i][j].setWeather(Cell.WEATHER.CALM);
			}
		}
	}
	public void setCenterWindy(){
		// find center
		int center = Driver.size / 2;
		// set center after the loop complete
		Cell centerCell = new Cell();
		centerCell.setWeather(Cell.WEATHER.WINDY);
		Driver.neWorld.worldMatrix[center][center] =  centerCell;
	}
	public  boolean somethingWindy(Cell currentCell){
		if (currentCell.getCellWeather().equals(Cell.WEATHER.WINDY)) return true;
		return false;
	}
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

   

    

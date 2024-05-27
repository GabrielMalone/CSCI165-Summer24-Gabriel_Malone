import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Rain {
    
	Random rand = new Random();
	ArrayList<Cell> alltherain = new ArrayList<>();

	public void getTheRain(){
		// if not fleeing a fire - randomly move to a nearby cell if all clear
		for( int i = 1 ; i < Driver.neWorld.size - 1 ; i ++){
			for (int j = 1 ; j < Driver.neWorld.size - 1 ; j ++){
				Cell current_location = Driver.neWorld.worldMatrix[i][j];
				if (current_location.getRain() == Cell.RAIN.RAINING && ! current_location.rain_moved && current_location.row != 0 && current_location.column != 0){
					this.alltherain.add(current_location);
				}
			}
		}
	}

	public void letItRain(){
		for (int i = 0; i < Driver.startingRain; i++){
			// find location on map for object
			int row_location = rand.nextInt(1, Driver.neWorld.size - 1);
			int column_location = rand.nextInt(1, Driver.neWorld.size - 1);
			// get cell at this location
			Cell cell_at_this_location = Driver.neWorld.worldMatrix[row_location][column_location];
			// update cell
			if (cell_at_this_location.getState() != Cell.STATES.BURNING) 
				cell_at_this_location.setRain(Cell.RAIN.RAINING);
		}
	}

	public void drip(){
		for (int i = 0; i < Driver.startingRain; i++){
			// find location on map for object
			int row_location = rand.nextInt(1, Driver.neWorld.size - 1);
			int column_location = rand.nextInt(1, Driver.neWorld.size - 1);
			// get cell at this location
			Cell cell_at_this_location = Driver.neWorld.worldMatrix[row_location][column_location];
			// update cell
			double chance_to_rain = rand.nextDouble(1);
			if (chance_to_rain < .1){
				if (cell_at_this_location.getRain() == Cell.RAIN.NOT_RAINING) 
					cell_at_this_location.setRain(Cell.RAIN.RAINING);
			}
		}
	}
	public void dry(){
		for (int i = 0; i < Driver.startingRain; i++){
			// find location on map for object
			int row_location = rand.nextInt(1, Driver.neWorld.size - 1);
			int column_location = rand.nextInt(1, Driver.neWorld.size - 1);
			// get cell at this location
			Cell cell_at_this_location = Driver.neWorld.worldMatrix[row_location][column_location];
			// update cell
			double chance_to_rain = rand.nextDouble(1);
			if (chance_to_rain < .02){
				if (cell_at_this_location.getRain() == Cell.RAIN.RAINING) 
					cell_at_this_location.setRain(Cell.RAIN.NOT_RAINING);
			}
		}
	}

	/**
	 * Method to see if the current cell, which is on fire, has any moisture, if so, clear it.
	 * @param current_cell
	 * @param burningCell
	 */
	public void checkIfDriedOut(Cell current_cell, Cell burningCell){
		// if caught in fire, dry
		if (current_cell.getRain() == (Cell.RAIN.RAINING)){
			burningCell.setRain(Cell.RAIN.NOT_RAINING);
		}
	}
	
    /**
	 * Method to regrow burnt areas
	 */
	public void waterTrees(){
		for(int j = 0 ; j < Driver.size - 1; j ++){
			for (int i = 0 ; i < Driver.size - 1 ; i ++){
                Cell currentCell = Driver.neWorld.worldMatrix[j][i];
                if (currentCell.getState() == Cell.STATES.BURNT && currentCell.getRain() == Cell.RAIN.RAINING){
                    double chance_to_dry = rand.nextDouble(1);
                    double chance_of_dry = rand.nextDouble(.1);
                     if (chance_to_dry < chance_of_dry){
                    currentCell.setState(Cell.STATES.TREE);
                     }
                }
            }
        }
    }

	/**
	 * Method increase reproduction rates of animals if they have lots of water
	 */
	public void waterAnimals(){
		for(int j = 0 ; j < Driver.size - 1; j ++){
			for (int i = 0 ; i < Driver.size - 1 ; i ++){
                Cell currentCell = Driver.neWorld.worldMatrix[j][i];
                if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEALIVE && currentCell.getRain() == Cell.RAIN.RAINING){
                    double chance_to_quench_thirst = rand.nextDouble(1);
                    double chance_of_thirst = rand.nextDouble(.5);
                     if (chance_to_quench_thirst < chance_of_thirst){
                    	currentCell.age -= 10;
                     }
                }
            }
        }
    }


    /**
	 * Method to have rain move around randomly
	 */
	public void scatterRain(){
// switched to two while loops since the for loop was not creating a random movement overall 
		// animals trended up and to the left
		getTheRain();
		while ( alltherain.size() > 0 ) {
			int rand_index_a = this.rand.nextInt(0,alltherain.size());
			Cell current_location = alltherain.get(rand_index_a);
			this.alltherain.remove(current_location);
			ArrayList<Cell> neighborArray = new ArrayList<>();
			Cell [] neighbors = Driver.neWorld.findNeighbors(current_location.row, current_location.column);
			for (Cell neighbor : neighbors){
				neighborArray.add(neighbor);
			}
			// randomly search the nearby cells and see if any spots suitable for moving to
			// if don't do this, animals just move to the next open cell and will just move one direction
			while (neighborArray.size() > 0){
				int rand_index = this.rand.nextInt(0,neighborArray.size());
				Cell neighboring_cell = neighborArray.get(rand_index);
				if (clearMoveChoice(neighboring_cell)){
					current_location.setRain(Cell.RAIN.NOT_RAINING);
					neighboring_cell.setRain(Cell.RAIN.RAINING);
					if (Driver.weatherOn)
						windDirectionEffectOnRain(current_location, neighboring_cell);
					neighboring_cell.rain_moved = true;
					break;
					}
				neighborArray.remove(neighboring_cell);
			}
		}
		if (Driver.weatherOn){
			drip();
			dry();
		}
	
	}

	/**
	 * Method to see if a cell is a clear spot to move to for a wandering animal
	 * @param option
	 * @return
	 */
	private boolean clearMoveChoice(Cell option){
		// can only escape map if running from fire
		// verbose conditional because wanted to be able tweak a lot of settings.
		if (option.getRain() != Cell.RAIN.RAINING && option.getState() != Cell.STATES.BURNING && option.row >= 0 && option.column >= 0 && option.row <= Driver.neWorld.size && option.column <= Driver.neWorld.size)
			return true;
		return false;
	}

	
	/**
	 * 
	 *  Clear any rain that made it to the border
	 */
	public void clearBorderRain(){
		for( int i = 0 ; i < Driver.neWorld.size; i ++){
			for (int j = 0 ; j < Driver.neWorld.size; j ++){
				Cell current_location = Driver.neWorld.worldMatrix[i][j];
				if (current_location.getRain() == Cell.RAIN.RAINING && current_location.row 	== 0 ||
					current_location.getRain() == Cell.RAIN.RAINING && current_location.column 	== 0 ||
					current_location.getRain() == Cell.RAIN.RAINING && current_location.row 	== Driver.neWorld.size- 1 ||
					current_location.getRain() == Cell.RAIN.RAINING && current_location.column 	== Driver.neWorld.size- 1){
						current_location.setRain(Cell.RAIN.NOT_RAINING);
				}
			}
		}
	}

    /**
	 * Method to clear the current rain
	 */
	public void dryTheEarth(){
		for (int i = 0; i < Driver.neWorld.worldMatrix.length ; i ++ ){
			for (int j = 0; j < Driver.neWorld.worldMatrix.length ; j ++ ){
				Driver.neWorld.worldMatrix[i][j].setRain(Cell.RAIN.NOT_RAINING);
			}
		}
	}

	/**
	 * Method to reset cell's moved state from true to false
	 */
	public void resetMoveState(){
		for( int i = 0 ; i < Driver.neWorld.size - 1 ; i ++){
			for (int j = 0 ; j < Driver.neWorld.size - 1 ; j ++){
				Cell current_location = Driver.neWorld.worldMatrix[i][j];
				if (current_location.getRain() == Cell.RAIN.RAINING){
					current_location.rain_moved = false;
				}
			}
		}
	}

	public void windDirectionEffectOnRain(Cell currentCell, Cell neighboringCell){
		
			Cell[] neighbors = Driver.neWorld.findNeighbors(currentCell.row, currentCell.column);

			Cell north			= neighbors[0];
			Cell south			= neighbors[1];
			Cell east			= neighbors[2];
			Cell west			= neighbors[3];
			Cell northeast 		= neighbors[4];
			Cell northwest 		= neighbors[5];
			Cell southeast 		= neighbors[6];
			Cell southwest 		= neighbors[7];

			HashMap<Cell, String> directions = new HashMap<>();

			directions.put(north, "NORTH");
			directions.put(south, "SOUTH");
			directions.put(east, "EAST");
			directions.put(west, "WEST");
			// didn't end up using the non-cardinal directions
			directions.put(northeast, "northeast");
			directions.put(northwest, "northwest");
			directions.put(southeast, "southeast");
			directions.put(southwest, "southwest");
			// if cell set to potentially raining and in path of wind, more likely to rain
			double precipitation_chance = rand.nextDouble();
			for (Cell neighbor : neighbors){
				if (directions.get(neighbor).equals(Driver.neWorld.todaysWind.getStringDirection()) && precipitation_chance < .75){
					neighbor.setRain(Cell.RAIN.RAINING);
				}
				else if ( precipitation_chance < .5 ) neighbor.setRain(Cell.RAIN.NOT_RAINING);
			}
		} 

}



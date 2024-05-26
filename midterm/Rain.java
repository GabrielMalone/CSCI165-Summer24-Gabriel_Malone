import java.util.ArrayList;
import java.util.Random;

public class Rain {
    
	Random rand = new Random();


	/**
	 * Method to rain steadily
	 */
	public void drip(){
		for(int j = 0 ; j < Driver.size - 1; j ++){
			for (int i = 0 ; i < Driver.size - 1 ; i ++){
                Cell currentCell = Driver.neWorld.worldMatrix[j][i];
                
                double chance_to_rain = rand.nextDouble(1);
                double chance_of_rain = rand.nextDouble(0,Driver.rainAdjust+.001);
                if (chance_to_rain < chance_of_rain && currentCell.getState() != Cell.STATES.BURNT){
                    currentCell.setRain(Cell.RAIN.RAINING);
                }
            }
        }
    }
    	/**
	 * Method to see if the current cell, which is on fire, has any wildlife.
	 * If so, animal dies.
	 * @param current_cell
	 * @param burningCell
	 */
	public void checkIfDriedOut(Cell current_cell, Cell burningCell){
		// if caught in fire, deadd
		if (current_cell.getRain() == (Cell.RAIN.RAINING)){
			burningCell.setRain(Cell.RAIN.DRY);
			//this.deadanimals.add(burningCell);
		}
	}

    /**
	 * Method to dry a cell
	 */
	public void dry(){
		for(int j = 0 ; j < Driver.size - 1; j ++){
			for (int i = 0 ; i < Driver.size - 1 ; i ++){
                Cell currentCell = Driver.neWorld.worldMatrix[j][i];
                if (currentCell.getRain() == Cell.RAIN.RAINING){
                    double chance_to_dry = rand.nextDouble(1);
                    double chance_of_dry = rand.nextDouble(.01);
                     if (chance_to_dry < chance_of_dry){
                    currentCell.setRain(Cell.RAIN.DRY);
                     }
                }
            }
        }
    }
        /**
	 * Method to dry a cell
	 */
	public void waterTrees(){
		for(int j = 0 ; j < Driver.size - 1; j ++){
			for (int i = 0 ; i < Driver.size - 1 ; i ++){
                Cell currentCell = Driver.neWorld.worldMatrix[j][i];
                if (currentCell.getState() == Cell.STATES.BURNT){
                    double chance_to_dry = rand.nextDouble(1);
                    double chance_of_dry = rand.nextDouble(.02);
                     if (chance_to_dry < chance_of_dry){
                    currentCell.setState(Cell.STATES.TREE);
                     }
                }
            }
        }
    }


    /**
	 * Method to have rain move around randomly
	 */
	public void scatterRain(){
		// if not fleeing a fire - randomly move to a nearby cell if all clear
		for( int i = 0 ; i < Driver.neWorld.size - 1 ; i ++){
			for (int j = 0 ; j < Driver.neWorld.size - 1 ; j ++){
				Cell current_location = Driver.neWorld.worldMatrix[i][j];
				if (current_location.getRain() == Cell.RAIN.RAINING && ! current_location.rain_moved && current_location.row != 0 && current_location.column != 0){
					ArrayList<Cell> neighborArray = new ArrayList<>();
					Cell [] neighbors = Driver.neWorld.findNeighbors(current_location.row, current_location.column);
					for (Cell neighbor : neighbors){
						neighborArray.add(neighbor);
					}
					while (neighborArray.size() > 0){
						int rand_index = rand.nextInt(neighborArray.size());
						Cell neighboring_cell = neighborArray.get(rand_index);
						if (neighboring_cell.getRain() == Cell.RAIN.DRY){
							current_location.setRain(Cell.RAIN.DRY);
							neighboring_cell.setRain(Cell.RAIN.RAINING);
							neighboring_cell.moved = true;
							break;
						}
						neighborArray.remove(neighboring_cell);
					}
				}
			}
		}
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
						current_location.setRain(Cell.RAIN.DRY);
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
				Driver.neWorld.worldMatrix[i][j].setRain(Cell.RAIN.DRY);
			}
		}
	}

}

// Gabriel Malone / CS165 / Midterm / Summer 2024

import java.util.ArrayList;
import java.util.Random;


public class Wildlife {
	// track active wildlife on map
	//public ArrayList<Cell> activeWildlifeCells = new ArrayList<>();
	//public ArrayList<Cell> deadanimals = new ArrayList<>();
	// excapedanimals -- > a metric I never used
	private Random rand = new Random();
	public ArrayList<Cell> alltheanimals = new ArrayList<>();
	public int space_needed_for_reproduction = 3;
	public int neighbors_needed_for_reproduction = 1;


	/**
	 * Method to place wildlife randomly on the map
	 */
	public void placeWildlife(){
		for (int i = 0; i < Driver.startingPop; i++){
			// find location on map for object
			int row_location = rand.nextInt(1, Driver.neWorld.size - 1);
			int column_location = rand.nextInt(1, Driver.neWorld.size - 1);
			// get cell at this location
			Cell cell_at_this_location = Driver.neWorld.worldMatrix[row_location][column_location];
			// update cell
			if (cell_at_this_location.getState() != Cell.STATES.BURNT) {
				cell_at_this_location.setObject(Cell.OBJECTS.WILDLIFEALIVE);
				cell_at_this_location.age = 0;
			}
			//this.activeWildlifeCells.add(cell_at_this_location);
		}
	}

	/**
	 * Method to have animals migrate in from the borders
	 */
	public void regrowWildlife(){
		// animals migrate in from the borders
		for( int i = 0 ; i < Driver.neWorld.size; i ++){
			for (int j = 0 ; j < Driver.neWorld.size; j ++){
				Cell current_location = Driver.neWorld.worldMatrix[i][j];
				if (current_location.getObject() == Cell.OBJECTS.VOID && current_location.row 		== 1 ||
					current_location.getObject() == Cell.OBJECTS.VOID && current_location.column 	== 1 ||
					current_location.getObject() == Cell.OBJECTS.VOID && current_location.row 		== (Driver.neWorld.size - 2) ||
					current_location.getObject() == Cell.OBJECTS.VOID && current_location.column 	== (Driver.neWorld.size - 2) ){
						double repop_chance = rand.nextDouble();
						if (repop_chance < Driver.popRegrowth)
							current_location.setObject(Cell.OBJECTS.WILDLIFEALIVE);
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
	public void checkIfDead(Cell current_cell, Cell burningCell){
		// if caught in fire, deadd
		if (current_cell.getObject() == (Cell.OBJECTS.WILDLIFEALIVE)){
			burningCell.setObject(Cell.OBJECTS.WILDLIFEDEAD);
		}
	}

	/**
	 * Method to help animals flee from fire effectively
	 */
	public void makeAnEscape(){
		// iterate through world
		for( int i = 0 ; i < Driver.neWorld.size - 1 ; i ++){
			for (int j = 0 ; j < Driver.neWorld.size - 1 ; j ++){
				Cell current_location = Driver.neWorld.worldMatrix[i][j];
				// if wildife present
				if (current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && current_location.row != 0 && current_location.column != 0){
					// get the wildlife's neighbors
					Cell [] neighbors = Driver.neWorld.findNeighbors(current_location.row, current_location.column);
					myBreakLabel:
					for (Cell neighboring_cell : neighbors){
						// see if next to any fire
						if (neighboring_cell.getState() == Cell.STATES.BURNING){
							// vacate current spot
							// find a clear spot to go to
							// if no fire or other animals , go to clear spot
							// pick a spot opposite the direction the fire came from
							for (Cell move_option : neighbors){
								if (oppositeDirection(neighboring_cell) == move_option.getPosition() && clearEscapeChoice(move_option)){
									current_location.setObject(Cell.OBJECTS.VOID);
									move_option.setObject(Cell.OBJECTS.WILDLIFEALIVE);
									move_option.moved = true;
									break myBreakLabel;
								}
							}
						}
					}
				}
			}
		}
	}
	/**
	 * Method to create an array of all the animals on the map
	 * this will be used to pick random animals and move them
	 * this helps to create turly random movement overall instead of 
	 * using a for loop to iterate through the world
	 */
	public void getTheAnimals(){
		this.alltheanimals.clear();
		// if not fleeing a fire - randomly move to a nearby cell if all clear
		for( int i = 1 ; i < Driver.neWorld.size - 1 ; i ++){
			for (int j = 1 ; j < Driver.neWorld.size - 1 ; j ++){
				Cell current_location = Driver.neWorld.worldMatrix[i][j];
				if (current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && ! current_location.moved && current_location.row != 0 && current_location.column != 0){
					this.alltheanimals.add(current_location);
				}
			}
		}
	}

	/**
	 * Method to have animals move around randomly to open/safe spots while not
	 * fleeing any fires.
	 */
	public void moveAround(){
		// switched to two while loops since the for loop was not creating a random movement overall 
		// animals trended up and to the left
		//reproduce();
		//overPopulation();
		getTheAnimals();
		while ( this.alltheanimals.size() > 0 ) {
			int rand_index_a = this.rand.nextInt(0,alltheanimals.size());
			Cell current_location = alltheanimals.get(rand_index_a);
			this.alltheanimals.remove(current_location);
			ArrayList<Cell> neighborArray = new ArrayList<>();
			Cell [] neighbors = Driver.neWorld.findNeighbors(current_location.row, current_location.column);
			for (Cell neighbor : neighbors){
				neighborArray.add(neighbor);
			}
			findRandomOpenSpot(neighborArray, current_location);
		}
		
	}
	
	public void reproduce(){
		// put all the animals in the map into an array so can pick from them randomly
		getTheAnimals();
		while ( this.alltheanimals.size() > 0 ) {
			// loop through the array at random spots
			int rand_index_a = this.rand.nextInt(0,alltheanimals.size());
			Cell current_location = alltheanimals.get(rand_index_a);
			// pop that animal from arrary
			this.alltheanimals.remove(current_location);
			// when a spot picked, check the neighbors of the cell in this location
			ArrayList<Cell> emptySpot = new ArrayList<>();
			int num_neighbors = 0;
			Cell [] potential_mates = Driver.neWorld.findNeighbors(current_location.row, current_location.column);
			for (Cell mate : potential_mates){
				if (mate.getObject() == Cell.OBJECTS.WILDLIFEALIVE)
					num_neighbors ++;
				else if (mate.getObject() == Cell.OBJECTS.VOID && mate.getState() == Cell.STATES.TREE)
					emptySpot.add(mate);
			}
			if (num_neighbors > neighbors_needed_for_reproduction && emptySpot.size() ==  space_needed_for_reproduction){
				current_location.setObject(Cell.OBJECTS.WILDLIFEDEAD);
				int counter = 1;
				while (counter <= space_needed_for_reproduction){
					int rand_index_b = rand.nextInt(emptySpot.size());
					emptySpot.get(rand_index_b).setObject(Cell.OBJECTS.WILDLIFEALIVE);
					emptySpot.get(rand_index_b).naturalBirth = true;
					emptySpot.get(rand_index_b).controllable = true;
					emptySpot.get(rand_index_b).age = 0;
					counter ++ ;
					}
				}
			}
		}
	
	/**
	 * Method to kill off wildlife if too many wildlife in an cell block
	 */
	public void overPopulation(){
		getTheAnimals();
		while ( this.alltheanimals.size() > 0 ) {
			// loop through the array at random spots
			int rand_index_a = this.rand.nextInt(0,alltheanimals.size());
			Cell current_location = alltheanimals.get(rand_index_a);
			this.alltheanimals.remove(current_location);
			// when a spot picked, check the neighbors of the cell in this location
			Cell [] neighbors = Driver.neWorld.findNeighbors(current_location.row, current_location.column);
			ArrayList<Cell> aliveNeighbors = new ArrayList<>();
			int total_neighbors = 0;
			for (Cell neighbor : neighbors){
				if (neighbor.getObject() == Cell.OBJECTS.WILDLIFEALIVE){
					total_neighbors ++;
					aliveNeighbors.add(neighbor);
				}
			}
			if (total_neighbors > 4){
				current_location.setObject(Cell.OBJECTS.WILDLIFEDEAD);
				while (aliveNeighbors.size() > 1){
					int rand_index_c = rand.nextInt(0 , aliveNeighbors.size());
					aliveNeighbors.get(rand_index_c).setObject(Cell.OBJECTS.WILDLIFEDEAD);
					aliveNeighbors.remove(rand_index_c);
				}
			}
		}
	}
	


	private void findRandomOpenSpot(ArrayList<Cell> neighborArray, Cell current_location) {
		// randomly search the nearby cells and see if any spots suitable for moving to
		// if don't do this, animals just move to the next open cell and will just move one direction
		while (neighborArray.size() > 0){
			int rand_index = this.rand.nextInt(0,neighborArray.size());
			Cell neighboring_cell = neighborArray.get(rand_index);
			if (clearMoveChoice(neighboring_cell)){
				current_location.setObject(Cell.OBJECTS.VOID);
				neighboring_cell.setObject(Cell.OBJECTS.WILDLIFEALIVE);
				neighboring_cell.moved = true;
				break;
				}
			neighborArray.remove(neighboring_cell);
		}
	}


	/**
	 * Method to reset cell's moved state from true to false (to prevent an animal from moving more than once per turn)
	 */
	public void resetMoveState(){
		for( int i = 0 ; i < Driver.neWorld.size - 1 ; i ++){
			for (int j = 0 ; j < Driver.neWorld.size - 1 ; j ++){
				Cell current_location = Driver.neWorld.worldMatrix[i][j];
				if (current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE){
					current_location.moved = false;
				}
			}
		}
	}

	/**
	 * 
	 *  Clear any animals who made it to the border safely when fleeing fire
	 */
	public void clearEscaped(){
		for( int i = 0 ; i < Driver.neWorld.size; i ++){
			for (int j = 0 ; j < Driver.neWorld.size; j ++){
				Cell current_location = Driver.neWorld.worldMatrix[i][j];
				if (current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && current_location.row 		== 0 ||
					current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && current_location.column 	== 0 ||
					current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && current_location.row 		== Driver.neWorld.size- 1 ||
					current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && current_location.column 	== Driver.neWorld.size- 1){
						current_location.setObject(Cell.OBJECTS.VOID);
				}
			}
		}
	}

	/**
	 * Method to see if the current cell is a safe spot to move to.
	 * @param option
	 * @return
	 */
	public boolean clearEscapeChoice(Cell option){
		// can leave the map
		if (option.getState() != Cell.STATES.BURNING && option.getObject() == Cell.OBJECTS.VOID && option.getState() != Cell.STATES.BURNT && option.row >= 0 && option.column >= 0)
			return true;
		return false;
	}

	/**
	 * Method to see if a cell is a clear spot to move to for a wandering animal
	 * @param option
	 * @return
	 */
	private boolean clearMoveChoice(Cell option){
		// can only escape map if running from fire
		// verbose conditional because wanted to be able tweak a lot of settings.
		if (option.getState() != Cell.STATES.BURNING && option.getObject() == Cell.OBJECTS.VOID && option.getState() != Cell.STATES.BURNT && option.row >= 0 && option.column >= 0 && option.row <= Driver.neWorld.size && option.column <= Driver.neWorld.size)
			return true;
		return false;
	}

	/**
	 * Method to randomly clear dead animals from the map
	 * 
	 */
	public void clearDead(){
		for (int f = 0 ; f < Driver.neWorld.size - 1; f ++ ){
            for(int g = 0 ; g < Driver.neWorld.size - 1; g ++){
				Cell currentCell = Driver.neWorld.worldMatrix[f][g];
				double chance_to_decay = rand.nextDouble(1);
				if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEDEAD && chance_to_decay < .1){
					currentCell.setObject(Cell.OBJECTS.VOID);

				}
			}
		}
	}

	/**
	 * Method to clear all animals from a map
	 */
	public void clearAnimals(){
		for (int f = 0 ; f < Driver.neWorld.size - 1; f ++ ){
            for(int g = 0 ; g < Driver.neWorld.size - 1; g ++){
				Cell currentCell = Driver.neWorld.worldMatrix[f][g];
				if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEALIVE)
					currentCell.setObject(Cell.OBJECTS.VOID);
			}
		}
	}

	/**
	 * Method to determine the opposite direction of a neighboring cell (using a cell's relative position info). Used in fleeing fires.
	 * @param neighboringCell
	 * @return
	 */
	public Cell.POSITIONASNEIGHBOR oppositeDirection(Cell neighboringCell){
		// default
		Cell.POSITIONASNEIGHBOR oppositeDirection = Cell.POSITIONASNEIGHBOR.NORTH;

		switch (neighboringCell.getFireMoving()) {

			case NORTH: 	return Cell.POSITIONASNEIGHBOR.NORTH;

			case SOUTH:		return Cell.POSITIONASNEIGHBOR.SOUTH;

			case EAST:		return Cell.POSITIONASNEIGHBOR.EAST;

			case WEST:		return Cell.POSITIONASNEIGHBOR.WEST;

			case NORTHEAST:	return Cell.POSITIONASNEIGHBOR.NORTH;

			case NORTHWEST: return Cell.POSITIONASNEIGHBOR.NORTH;

			case SOUTHEAST: return Cell.POSITIONASNEIGHBOR.SOUTH;

			case SOUTHWEST: return Cell.POSITIONASNEIGHBOR.SOUTH;
			// shouldn't come up
			case VOID: return Cell.POSITIONASNEIGHBOR.NORTH;
		}
		return oppositeDirection;
	}

	public void naturalDeath(){
		for (int f = 0 ; f < Driver.neWorld.size - 1; f ++ ){
			for(int g = 0 ; g < Driver.neWorld.size - 1; g ++){
				Cell currentCell = Driver.neWorld.worldMatrix[f][g];
				if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEALIVE && currentCell.age > 15)
					currentCell.setObject(Cell.OBJECTS.VOID);
			}
		}
	}		

}






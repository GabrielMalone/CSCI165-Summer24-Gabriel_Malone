
import java.util.ArrayList;
import java.util.Random;


public class Wildlife {
	// track active wildlife on map
	public ArrayList<Cell> activeWildlifeCells = new ArrayList<>();
	public ArrayList<Cell> deadanimals = new ArrayList<>();

	int escapedanimals = 0;
	Random rand = new Random();

	public void placeWildlife(){
		
		for (int i = 0; i < Driver.startingPop; i++){
			// find location on map for object
			int row_location = rand.nextInt(1, World.worldMatrix.length - 1);
			int column_location = rand.nextInt(1, World.worldMatrix.length - 1);
			// get cell at this location
			Cell cell_at_this_location = World.worldMatrix[row_location][column_location];
			// update cell
			cell_at_this_location.setObject(Cell.OBJECTS.WILDLIFEALIVE);
			activeWildlifeCells.add(cell_at_this_location);	
			}
		}

	public void repopulate(){
	
		for (int i = 0; i < Driver.popRegrowth ; i++){
			// find location on map for object
			int row_location = rand.nextInt(1, World.worldMatrix.length - 1);
			int column_location = rand.nextInt(1, World.worldMatrix.length - 1);
			// get cell at this location
			Cell cell_at_this_location = World.worldMatrix[row_location][column_location];
			if (cell_at_this_location.getObject() != Cell.OBJECTS.WILDLIFEALIVE){
				// update cell
				cell_at_this_location.setObject(Cell.OBJECTS.WILDLIFEALIVE);
				activeWildlifeCells.add(cell_at_this_location);	
			}
		}
	}
	
	public void checkIfDead(Cell current_cell, Cell burningCell){
		// if caught in fire, deadd
		if (current_cell.getObject() == (Cell.OBJECTS.WILDLIFEALIVE)){
			burningCell.setObject(Cell.OBJECTS.WILDLIFEDEAD);
			deadanimals.add(burningCell);
		}
	}
	
	public void makeAnEscape(){
		// iterate through world
		for( int i = 0 ; i < World.worldMatrix.length - 1 ; i ++){
			for (int j = 0 ; j < World.worldMatrix.length - 1 ; j ++){
				Cell current_location = World.worldMatrix[i][j];
				// if wildife present
				if (current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && current_location.row != 0 && current_location.column != 0){
					// get the wildlife's neighbors
					Cell [] neighbors = World.findNeighbors(current_location.row, current_location.column);
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

	public void moveAround(){
		// if not fleeing a fire - randomly move to a nearby cell if all clear
		for( int i = 0 ; i < World.worldMatrix.length - 1 ; i ++){
			for (int j = 0 ; j < World.worldMatrix.length - 1 ; j ++){
				Cell current_location = World.worldMatrix[i][j];
				if (current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && ! current_location.moved && current_location.row != 0 && current_location.column != 0){
					ArrayList<Cell> neighborArray = new ArrayList<>();
					Cell [] neighbors = World.findNeighbors(current_location.row, current_location.column);
					for (Cell neighbor : neighbors){
						neighborArray.add(neighbor);
					}
					while (neighborArray.size() > 0){
						int rand_index = rand.nextInt(neighborArray.size());
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
			}	
		}
	}

	public void resetMoveState(){
		for( int i = 0 ; i < World.worldMatrix.length - 1 ; i ++){
			for (int j = 0 ; j < World.worldMatrix.length - 1 ; j ++){
				Cell current_location = World.worldMatrix[i][j];
				if (current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE){
					current_location.moved = false;
				}
			}
		}
	}

	public void clearEscaped(){
		for( int i = 0 ; i < World.worldMatrix.length; i ++){
			for (int j = 0 ; j < World.worldMatrix.length; j ++){
				Cell current_location = World.worldMatrix[i][j];
				if (current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && current_location.row 		== 0 || 
					current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && current_location.column 	== 0 || 
					current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && current_location.row 		== World.worldMatrix.length - 1 || 
					current_location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && current_location.column 	== World.worldMatrix.length - 1){
						current_location.setObject(Cell.OBJECTS.VOID);
						escapedanimals ++ ;
				}
			}
		}
	}
		
	private boolean clearEscapeChoice(Cell option){
		// can leave the map
		if (option.getState() != Cell.STATES.BURNING && option.getObject() == Cell.OBJECTS.VOID && option.getState() != Cell.STATES.BURNT && option.row >= 0 && option.column >= 0)
			return true;
		return false;
	}

	private boolean clearMoveChoice(Cell option){
		// can only escape map if running from fire
		if (option.getState() != Cell.STATES.BURNING && option.getObject() == Cell.OBJECTS.VOID && option.getState() != Cell.STATES.BURNT && option.row > 0 && option.column > 0 && option.row < World.worldMatrix.length - 1 && option.column < World.worldMatrix.length - 1)
			return true;
		return false;
	}

	public void clearDead(){
		for (int f = 0 ; f < World.worldMatrix.length - 1; f ++ ){
            for(int g = 0 ; g < World.worldMatrix.length - 1; g ++){    
				Cell currentCell = World.worldMatrix[f][g];
				double chance_to_decay = rand.nextDouble(1);
				if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEDEAD && chance_to_decay < .1){
					currentCell.setObject(Cell.OBJECTS.VOID);
					currentCell.SetCellColor();
			
				}
			}
		}
	}

	
	
	private Cell.POSITIONASNEIGHBOR oppositeDirection(Cell neighboringCell){
		// default
		Cell.POSITIONASNEIGHBOR oppositeDirection = Cell.POSITIONASNEIGHBOR.NORTH;
		
		switch (neighboringCell.getFireMoving()) {

			case NORTH: 	return Cell.POSITIONASNEIGHBOR.NORTH;
		
			case SOUTH:		return Cell.POSITIONASNEIGHBOR.SOUTH;
			
			case EAST: 		return Cell.POSITIONASNEIGHBOR.EAST;
			
			case WEST: 		return Cell.POSITIONASNEIGHBOR.WEST;
		
			case NORTHEAST:	return Cell.POSITIONASNEIGHBOR.NORTH;

			case NORTHWEST: return Cell.POSITIONASNEIGHBOR.NORTH;
		
			case SOUTHEAST: return Cell.POSITIONASNEIGHBOR.SOUTH;
			
			case SOUTHWEST: return Cell.POSITIONASNEIGHBOR.SOUTH;
			// shouldn't come up
			case VOID: return Cell.POSITIONASNEIGHBOR.NORTH;
		}
		return oppositeDirection;
	}

}






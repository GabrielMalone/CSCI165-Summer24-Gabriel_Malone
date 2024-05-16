
import java.util.ArrayList;
import java.util.Random;


public class Wildlife {
	// track active wildlife on map
	public ArrayList<Cell> activeWildlifeCells = new ArrayList<>();
	int deadanimals = 0;
	int animal_pop = World.worldMatrix.length;

	public void placeWildlife(){
		
		for (int i = 0; i < animal_pop; i++){
			// find location on map for object
			Random random = new Random();
			int row_location = random.nextInt(1, World.worldMatrix.length - 1);
			int column_location = random.nextInt(1, World.worldMatrix.length - 1);
			// get cell at this location
			Cell cell_at_this_location = World.worldMatrix[row_location][column_location];
			// update cell
			cell_at_this_location.setObject(Cell.OBJECTS.WILDLIFEALIVE);
			activeWildlifeCells.add(cell_at_this_location);	
			}
		}
	
	public void checkIfDead(Cell current_cell, Cell burningCell){
		// if caught in fire, deadd
		if (current_cell.getObject() == (Cell.OBJECTS.WILDLIFEALIVE)){
			burningCell.setObject(Cell.OBJECTS.WILDLIFEDEAD);
		}
	}
	
	public void makeAnEscape(){
		// iterate through world
		for( int i = 0 ; i < World.worldMatrix.length - 1 ; i ++){
			for (int j = 0 ; j < World.worldMatrix.length - 1 ; j ++){
				Cell location = World.worldMatrix[i][j];
				// if wildife present
				if (location.getObject() == Cell.OBJECTS.WILDLIFEALIVE && location.row != 0 && location.column != 0){
					// get the wildlife's neighbors
					Cell [] neighbors = World.findNeighbors(location.row, location.column);
					myBreakLabel:
					for (Cell neighboring_cell : neighbors){
						// see if next to any fire
						if (neighboring_cell.getState() == Cell.STATES.BURNING){
							// vacate current spot
							location.setObject(Cell.OBJECTS.VOID);
							// find a clear spot to go to
							// if no fire or other animals , go to clear spot
							// next need to pick a spot opposite the direction the fire came from
							// need to record fire direction
							for (Cell option : neighbors){
								if (oppositeDirection(neighboring_cell) == option.getPosition() && clearEscapeChoice(option)){
									option.setObject(Cell.OBJECTS.WILDLIFEALIVE);
									break myBreakLabel;
								}
							}
							// otherwise stay put
							location.setObject(Cell.OBJECTS.WILDLIFEALIVE);
						}
					}
				}	
			}	
		}
	}	
		
	private boolean clearEscapeChoice(Cell option){
		if (option.getState() != Cell.STATES.BURNING && option.getObject() == Cell.OBJECTS.VOID && option.getState() != Cell.STATES.BURNT && option.row >= 0 && option.column >= 0)
			return true;
		return false;
	}
	
	private Cell.POSITIONASNEIGHBOR oppositeDirection(Cell neighboringCell){
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






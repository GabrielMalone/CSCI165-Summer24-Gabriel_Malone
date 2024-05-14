
import java.util.ArrayList;
import java.util.Random;


public class Wildlife {
	// track active wildlife on map
	public ArrayList<Cell> activeWildlifeCells = new ArrayList<>();
	int deadanimals = 0;
	int animal_pop = 25;

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
							// if no fire or other animals , go to random clear spot nearby	
							// next need to pick a spot opposite the direction the fire came from
							for (Cell option : neighbors){
								if (escapeChoice(option)){
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
		

	private boolean escapeChoice(Cell option){
		if (option.getState() != Cell.STATES.BURNING && option.getObject() == Cell.OBJECTS.VOID && option.row >= 0 && option.column >= 0)
			return true;
		return false;
	}	

}






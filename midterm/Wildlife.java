
import java.util.ArrayList;
import java.util.Random;


public class Wildlife {
	// track active wildlife on map
	public ArrayList<Cell> activeWildlifeCells = new ArrayList<>();
	double deadanimals = 0;
	double animal_pop = Weather.doubleProb(World.worldMatrix.length / 2, World.worldMatrix.length);

	public void placeObject(){
		
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
	
	public void evadeFire(){
		// iterate through the cells that have wildlife
		for (Cell wildlife : activeWildlifeCells){
			// if wildlife alive
			if (wildlife.getObject() == Cell.OBJECTS.WILDLIFEALIVE){
				int[] coords = wildlife.convertCoordsToInteger();
				Cell [] neighborCells = World.findNeighbors(coords[0], coords[1]);
				ArrayList<Cell> calmCells = new ArrayList<>();
				ArrayList<Cell> fireCells = new ArrayList<>();
				// iterate through the neighbors and see which cells burning
				// and which ones calm
				for (Cell cell : neighborCells){
					if (cell.getState().equals(Cell.STATES.BURNING)){
						fireCells.add(cell);
					}
					else calmCells.add(cell);
				}
			}
		}
	}






}

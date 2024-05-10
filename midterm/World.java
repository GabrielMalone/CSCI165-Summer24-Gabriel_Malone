import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class World {

	public static int size = Driver.size;
	// create blank Cell matrix
	public static Cell[][] worldMatrix = new Cell[size][size];
	// matrix to hold the upcoming iteration of world
	public static Cell [][] nextStep = new Cell [size][size];
	// timestep recorder
	public static int timeStep;
	// probability of a tree catching on fire
	private static double CATCHPROBABILITY = .3;

	/**
	 * Method to fill in a matrix with Cell objects
	 */
	public void fillWorld(){
		// find center
		int center = size / 2;
		// fill the matrix with cells
		for (int i = 0 ; i < worldMatrix.length ; i ++){
			for (int j = 0 ; j < worldMatrix.length; j++){
				// create new cell object for each step
				Cell newCell = new Cell();
				newCell.coordinates = i + "," + j;
				// conditions for empty and tree
				// if at edge tops/bottom
				if (i == 0 || i == worldMatrix.length - 1){
					newCell.setState(Cell.STATES.EMPTY);
					newCell.SetCellColor();
				}
				// if at edge left/right
				else if ( j == 0 || j == worldMatrix.length - 1){
					newCell.setState(Cell.STATES.EMPTY);
					newCell.SetCellColor();
				}
				// if not at edge - tree
				else {
					newCell.setState(Cell.STATES.TREE);
					newCell.SetCellColor();
				}
				// put the appropriate cell in the matrix
				worldMatrix[i][j] = newCell;
				// also write to color file
			}
		}
		// set center after the loop complete
		Cell centerCell = new Cell();
		//middle cell on fire
		centerCell.setState(Cell.STATES.BURNING);
		centerCell.SetCellColor();
		worldMatrix[center][center] =  centerCell;
		centerCell.coordinates = center + "," + center;
	}
	/**
	 * Method to get a random percetage 1-100%
	 *
	 * @return
	 */
	public static double probCatch(){
		Random rand = new Random();
		// random value from  0.0 - 1.0
		double catchProb = rand.nextDouble(1);
		return catchProb;
	}
	/**
	 * Method to model the spread of a fire
	 *
	 */
	public static void applySpread(){
		
		boolean burning = true;
		
		// worldMatrix decides what happens next
		// nextStep matrix applies those changes
		// this way the changes dont affect the current iteration
		// of the world map
		
		// copy world matrix sets the updated matrix to the current world matrix
		copyWorldMatrix();
		
		//applies weather conditions to map in sinewave pattern
		
		Weather.sinewave();	
		// iterate through the matrix up to the fireboundaries (1 -> length - 1)
		// until burning has ceased
		while (burning){
			trackSteps();
			copyNextStepMatrix();
			Weather.setSineWave();
			displayWorld();
			// update the matix to contain next step data
			// iterate through world map
			// rows
			for (int i = 1; i < worldMatrix.length - 1 ; i ++ ){
				// colums
				for (int j = 1; j < worldMatrix.length - 1 ; j ++ ){
					// get all the nearby cells

					int north 			= i - 1;
					int south 			= i + 1;
					int west  			= j - 1;
					int east  			= j + 1;

					Cell currentCell  	= worldMatrix[i][j];
					Cell northernCell	= worldMatrix[north][j];
					Cell southernCell	= worldMatrix[south][j];
					Cell westernCell	= worldMatrix[i][west];
					Cell easternCell	= worldMatrix[i][east];
					Cell noreastCell	= worldMatrix[north][east];
					Cell norwestCell	= worldMatrix[north][west];
					Cell southwestCell  = worldMatrix[south][west];
					Cell southeastCell 	= worldMatrix[south][east];

					// put nearby cells in an array so can iterate through them
					Cell [] nearbyCells = {	northernCell,
											southernCell,
											westernCell,
											easternCell,
											noreastCell,
											norwestCell,
											southwestCell,
											southeastCell};

					// hashmap to set the correct position
					// of cells that are affected by fire in the next step matrix
					Map<Cell, String> cellPositionMap = new HashMap<Cell, String>(8);
					//for hashmap
					String [] nearbyCellPosition = {"north",
													"south",
													"west",
													"east",
													"noreast",
													"norwest",
													"southwest",
													"southeast"};

					// populate the hashmap
					for (int index2 = 0 ;  index2 < nearbyCells.length ; index2 ++){
						cellPositionMap.put(nearbyCells[index2], nearbyCellPosition[index2]);
					}
					
					if (Cell.STATES.BURNING == currentCell.getState()){
						
						// if current cell burning change to empty
						Cell cellNextStep = new Cell();
						// carry over coordinate information
						cellNextStep.coordinates = currentCell.coordinates;
						// change from burnign to empty next turn
						cellNextStep.setState(Cell.STATES.EMPTY);
						cellNextStep.SetCellColor();
						//Weather.windCheck(cellNextStep);
						// place new cell in nextStep matrix
						nextStep[i][j] = cellNextStep;
						// calculate if nearby cells catch on fire
						// by iterating through nearby cells
						
						// if burn cell windy, increase burn probability of nearby cells
						// in direction of the wind
						// decrease burn probability of cells not in directin path
						double chance = probCatch();
						if (currentCell.getCellWeather().equals(Cell.WEATHER.WINDY))
							// for now just checking eastern wind flow
							chance *= nearbyCells[3].getBurnMultiplier();
							// if fire spreads to this cell
							if (chance <= CATCHPROBABILITY){
								// set the next step cell to be on fire
								Cell cellNearbyNextStep = new Cell();
								cellNearbyNextStep.coordinates = nearbyCells[3].coordinates;
								cellNearbyNextStep.setState(Cell.STATES.BURNING);
								cellNearbyNextStep.SetCellColor();
								cellNearbyNextStep.setRelativePosition(nearbyCellPosition[3]);
								String position = cellNearbyNextStep.getRelativePosition();
								// assign correct matrix spot
								setPosition(cellNearbyNextStep, position, north, south, east, west, i, j);
							}

						for (int index = 0 ; index < nearbyCells.length ; index ++)	{
							Cell nearbyCell = nearbyCells[index];
							if (! nearbyCell.equals(nearbyCells[3])){
								// assign cell for the next step its relative position
								Cell cellNearbyNextStep = new Cell();
								cellNearbyNextStep.coordinates = nearbyCell.coordinates;
								cellNearbyNextStep.setRelativePosition(nearbyCellPosition[index]);
								// if cell is a tree, and burning cell next to it not windy:
								if (nearbyCell.getState() == Cell.STATES.TREE && ! currentCell.getCellWeather().equals(Cell.WEATHER.WINDY)){
									chance = probCatch();
								}
								// if cell is a tree and burning cell next to it is windy (blowing away from this cell)
								// decrease prob of fire catch
								else {

									chance = probCatch() + 1;
								}
								// if fire spreads to this cell
								if (chance <= CATCHPROBABILITY){
									// set the next step cell to be on fire
									cellNearbyNextStep.setState(Cell.STATES.BURNING);
									cellNearbyNextStep.SetCellColor();
									// place new cell in nextStep matrix
									String position = cellNearbyNextStep.getRelativePosition();
									// assign correct matrix spot
									setPosition(cellNearbyNextStep, position, north, south, east, west, i, j);
								}
							}
						}
					}	
				}
			}
			if (! stillBurning()) burning = false;	
		}
		trackSteps();
	}

	private static void copyNextStepMatrix(){
		for(int g = 0 ; g < worldMatrix.length ; g ++){
			for(int h = 0 ; h < worldMatrix.length ; h ++){
				worldMatrix[g][h] = nextStep[g][h];
			}
		}
	}

	private static void copyWorldMatrix(){
		for(int g = 0 ; g < worldMatrix.length ; g ++){
			for(int h = 0 ; h < worldMatrix.length ; h ++){
				nextStep[g][h] = worldMatrix[g][h];
			}
		}
	}

	private static void displayWorld(){
		// for displaying the matrices in terminal
		Terminal_Graphics graphics = new Terminal_Graphics();
		// display the world
		try{
			Thread.sleep(50);
			}
		catch (InterruptedException iException){
			}
		graphics.rgbWorld();
		graphics.displayWorld();
	}

	private static void trackSteps(){
		// track steps
		timeStep += 1;
		System.out.println();
		System.out.println("Steps: " + timeStep);
	}

	private static boolean stillBurning(){
		// checks to see if any fires still burning
		for(int x = 0; x < worldMatrix.length; x ++){
			for (int y = 0; y < worldMatrix.length; y ++){
				if (worldMatrix[x][y].getState() == Cell.STATES.BURNING) return true;
			}
		}
		return false;
	}
	/**
	 * Method to set the position of the updated cell for the next iteration of the matrix
	 * 
	 * @param cellNearbyNextStep
	 * @param position
	 * @param north
	 * @param south
	 * @param east
	 * @param west
	 * @param i
	 * @param j
	 */
	private static void setPosition(Cell cellNearbyNextStep, String position, 
		int north, int south, int east, int west, int i, int j) {
	
		switch (position){

			case "north" 	: 	nextStep[north][j] 		= cellNearbyNextStep;
							break;
			case "south"	: 	nextStep[south][j]		= cellNearbyNextStep;
							break;
			case "west" 	: 	nextStep[i][west] 		= cellNearbyNextStep;
							break;
			case "east" 	: 	nextStep[i][east] 		= cellNearbyNextStep;
							break;
			case "noreast" 	: 	nextStep[north][east] 	= cellNearbyNextStep;
							break;
			case "norwest" 	: 	nextStep[north][west] 	= cellNearbyNextStep;
							break;
			case "southwest" : 	nextStep[south][west] 	= cellNearbyNextStep;
							break;
			case "southeast" : 	nextStep[south][east] 	= cellNearbyNextStep;
							break;
		}
	}

	private static void setCenterCellonFire(){
		// find center
		int center = size / 2;
		// set center after the loop complete
		Cell centerCell = new Cell();
		//middle cell on fire
		centerCell.setState(Cell.STATES.BURNING);
		centerCell.SetCellColor();
		worldMatrix[center][center] =  centerCell;
		centerCell.coordinates = center + "," + center;
	}
}


import java.util.Random;

public class World {

	public static int size = Driver.size;
	// create blank Cell matrix
	public static Cell[][] worldMatrix = new Cell[size][size];
	// matrix to hold the upcoming iteration of world
	public static Cell [][] nextStep = new Cell [size][size];
    // list of all the current burning cells (used in loop)
    
	// timestep recorder
	public static int timeStep = 0;
	// probability of a tree catching on fire
	private static double CATCHPROBABILITY = .25;
   

	/**
	 * Method to fill in a matrix with Cell objects
	 */
	public void fillWorld(){
        
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
					newCell.coordinates = i + "," + j;
				}
				// if at edge left/right
				else if ( j == 0 || j == worldMatrix.length - 1){
					newCell.setState(Cell.STATES.EMPTY);
					newCell.SetCellColor();
					newCell.coordinates = i + "," + j;
				}
				// if not at edge - tree
				else {
					newCell.setState(Cell.STATES.TREE);
					newCell.SetCellColor();
					newCell.coordinates = i + "," + j;
				}
				// put the appropriate cell in the matrix
				worldMatrix[i][j] = newCell;
				// also write to color file
			}
		}
		// set center after the loop complete
        setCenterCellonFire();
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
	public void applySpread(){
		
		boolean burning = true;
		
		// worldMatrix decides what happens next
		// nextStep matrix applies those changes
		// this way the changes dont affect the current iteration
		// of the world map
		copyWorldMatrix();
		//applies weather conditions to map in sinewave pattern
		Weather.sinewave();	
		while (burning){
			if (timeStep > 0) clearPreviousFire();
			copyNextStepMatrix();
			trackSteps();
            displayWorld();
            Weather.setSineWave();
            designatetNeighborsOnFire();
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

	private static boolean somethingBurning(Cell currentCell){
		if (currentCell.getState().equals(Cell.STATES.BURNING)) return true;
		return false;
	}

	private void setCenterCellonFire(){
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

    private void seeWhatBurns(Cell[] neighboringcells){
        for (Cell cell : neighboringcells){
            if(cell.getState().equals(Cell.STATES.TREE)){
                double catchProbability = probCatch();
                if (catchProbability < CATCHPROBABILITY){
					int [] coordinates = cell.convertCoordsToInteger();
					int rows = coordinates   [0];
					int columns = coordinates[1];
					Cell nextCell = new Cell();
					nextCell.coordinates = cell.coordinates;
					nextCell.setState(Cell.STATES.BURNING);
					nextCell.SetCellColor();
					nextStep[rows][columns] = nextCell;
                }
            }
        }
    }

    private void designatetNeighborsOnFire(){
        
        for (int f = 0 ; f < worldMatrix.length - 1; f ++ ){
            for(int g = 0 ; g < worldMatrix.length - 1; g ++){    
                if (somethingBurning(worldMatrix[f][g])){

                    int row     = f;
                    int column  = g; 
                    
                    Cell north      = worldMatrix   [row + 1]   [column];
                    Cell south      = worldMatrix   [row - 1]   [column];
                    Cell east       = worldMatrix   [row]       [column + 1];
                    Cell west       = worldMatrix   [row]       [column - 1];
                    Cell northeast  = worldMatrix   [row + 1]   [column + 1];
                    Cell northwest  = worldMatrix   [row + 1]   [column - 1];
                    Cell southeast  = worldMatrix   [row - 1]   [column + 1];
                    Cell southwest  = worldMatrix   [row - 1]   [column - 1];

                    Cell [] neighboringCells = {north, south, east, west, northeast, northwest, southeast, southwest};
                    
                    seeWhatBurns(neighboringCells);
                    
                }
            }
        }     
    }

    private boolean stillBurning(){
        for (int f = 0 ; f < worldMatrix.length - 1; f ++ ){
            for(int g = 0 ; g < worldMatrix.length - 1; g ++){    
                if (somethingBurning(worldMatrix[f][g])){
                    return true;
                }
            }     
        }
        return false;
    }  
	
	private void clearPreviousFire(){
		for (int f = 0 ; f < worldMatrix.length - 1; f ++ ){
            for(int g = 0 ; g < worldMatrix.length - 1; g ++){    
				Cell currentCell = worldMatrix[f][g];
				if (somethingBurning(currentCell)){
					currentCell.setState(Cell.STATES.EMPTY);
					currentCell.SetCellColor();
				}
			}
		}
	}


}
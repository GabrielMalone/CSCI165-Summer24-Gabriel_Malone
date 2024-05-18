

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class World {

	public static int size = Driver.size;
	// create blank Cell matrix
	public static Cell[][] worldMatrix = new Cell [size][size];
	// matrix to hold the upcoming iteration of world
	public static Cell [][] nextStep = new Cell [size][size];
	// matrix to hold Cell colors
	public static String [][] rgbWorld = new String [size][size];
	// timestep recorder
	public static int timeStep = 0;
	// probability of a tree catching on fire
	public static double catchprobability = Driver.catchprobability;
	// for terminating loop
	private static boolean burning = true;
	// get weather for world map
	public static Weather todaysWeather = Driver.todaysWeather;
	// get wildlife
	public static Wildlife wildlife = new Wildlife();
	// graphics
	public static BufferedImage [] trees = new BufferedImage[4];
	public static BufferedImage [] fires = new BufferedImage[4];
	public static BufferedImage [] burnt = new BufferedImage[4];
	public static BufferedImage [] anima = new BufferedImage[4];

	public static Random rand = new Random();
	
	public World(){

		createAnimals();
        createTrees();
        createFires();
        createBurnt();
		fillWorld();
	}


	// MAIN LOOP

	/**
	 * Method to model the spread of a fire
	 *
	 */
	public void applySpread(){
		// worldMatrix decides what happens next
		// nextStep matrix applies those changes
		// this way the changes dont affect the current iteration
		// of the world map
		todaysWeather.pattern();
		wildlife.placeWildlife();
		copyWorldMatrix();
		todaysWeather.setWeatherPattern();
		while (true){
			if (timeStep > 0) clearPreviousFire();
			if (burning == false) {
				randomFireSpot(); burning = true; 
				wildlife.repopulate();
				wildlife.clearDead(); 
			}
			applyChangesToWorld();
            displayWorld();
			regrowTrees();
			wildlife.resetMoveState();
			wildlife.makeAnEscape();
			wildlife.clearEscaped();
			wildlife.moveAround();
            designatetNeighborsOnFire();
			displayData();
            if (! stillBurning()) {
				burning = false;
			}
		}
	}


	// WORLD LOGIC

	/**
	 * Method to fill in a matrix with Cell objects
	 */
	public void fillWorld(){
		// fill the matrix with cells
		for (int i = 0 ; i < worldMatrix.length ; i ++){
			for (int j = 0 ; j < worldMatrix.length; j++){
				// create new cell object for each step
				Cell newCell = new Cell();
				newCell.row = i;
				newCell.column = j;
				newCell.coordinates = i + "," + j;
				// conditions for empty and tree
				// if at edge tops/bottom
				if (i == 0 || i == worldMatrix.length - 1){
					newCell.setState(Cell.STATES.EMPTY);
					newCell.SetCellColor();
					rgbWorld[i][j] = newCell.cellColor;
					newCell.row = i;
					newCell.column = j;
					newCell.coordinates = i + "," + j;
				}
				// if at edge left/right
				else if ( j == 0 || j == worldMatrix.length - 1){
					newCell.setState(Cell.STATES.EMPTY);
					newCell.SetCellColor();
					rgbWorld[i][j] = newCell.cellColor;
					newCell.row = i;
					newCell.column = j;
					newCell.coordinates = i + "," + j;
				}
				// if not at edge - tree
				else {
					newCell.setState(Cell.STATES.TREE);
					newCell.SetCellColor();
					rgbWorld[i][j] = newCell.cellColor;
					newCell.row = i;
					newCell.column = j;
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
		// random value from  0.0 - 1.0
		double catchProb = rand.nextDouble(1);
		return catchProb;

	}

	private static void applyChangesToWorld(){
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
        // Terminal_Graphics t_graphics = new Terminal_Graphics();
		// display the world
		try{
			Thread.sleep(Driver.speed);
			}
		catch (InterruptedException iException){
			}
		// turn off to prevent slow down of bigger maps
		// t_graphics.displayWorld();
	}

	private static int trackSteps(){
		// track steps
		if (burning)
			timeStep += 1;
			return timeStep;
	
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
		rgbWorld[center][center] = centerCell.cellColor;
		worldMatrix[center][center] =  centerCell;
		centerCell.coordinates = center + "," + center;
		centerCell.row = center;
		centerCell.column = center;
	}

	private double windDirectionEffect(Cell homeCell, Cell currentCell, double chanceToBurn){
		
		Cell[] neighbors = findNeighbors(homeCell.row, homeCell.column);
		
		Cell north 		= neighbors[0];
		Cell south 		= neighbors[1];
		Cell east 		= neighbors[2];
		Cell west		= neighbors[3];
		Cell northeast 	= neighbors[4];
		Cell northwest 	= neighbors[5];
		Cell southeast 	= neighbors[6];
		Cell southwest 	= neighbors[7];

		HashMap<Cell, String> directions = new HashMap<>();

		directions.put(north, "NORTH");
		directions.put(south, "SOUTH");
		directions.put(east, "EAST");
		directions.put(west, "WEST");
		directions.put(northeast, "northeast");
		directions.put(northwest, "northwest");
		directions.put(southeast, "southeast");
		directions.put(southwest, "southwest");

		if (directions.get(currentCell).equals(todaysWeather.windDirection)){
			chanceToBurn *= currentCell.burnMultiplier();
			return chanceToBurn;
		} 
		else chanceToBurn += .1;
		return chanceToBurn;
	}
	
	private void seeWhatBurns(Cell[] neighboringcells, Cell homeCell){
        for (Cell cell : neighboringcells){
            if(	cell.getState().equals(Cell.STATES.TREE) 
			&& 	cell.getCellWeather().equals(Cell.WEATHER.CALM)){
				double chanceToBurn = probCatch();
				if 	(chanceToBurn < catchprobability){
					setMapOnFire(cell, homeCell);
                }
            }
			
			else if (cell.getState().equals(Cell.STATES.TREE)
			&& 	cell.getCellWeather().equals(Cell.WEATHER.WINDY)){
				double chanceToBurn = probCatch();
				chanceToBurn = windDirectionEffect(homeCell, cell, chanceToBurn);
				if 	(chanceToBurn < catchprobability){
					setMapOnFire(cell, homeCell);
					
                }
			}
        }
    }

    private void designatetNeighborsOnFire(){
        
        for (int f = 0 ; f < worldMatrix.length - 1; f ++ ){
            for(int g = 0 ; g < worldMatrix.length - 1; g ++){    
                
				Cell currentCell = worldMatrix[f][g];
				if (somethingBurning(currentCell)){

					if (currentCell.getCellWeather().equals(Cell.WEATHER.CALM)){
                    	Cell [] neighboringCells = findNeighbors(f, g);
                    	seeWhatBurns(neighboringCells, currentCell);
					}
					else if (currentCell.getCellWeather().equals(Cell.WEATHER.WINDY)){
						Cell [] neighboringCells = findNeighbors(f, g);
                    	seeWhatBurns(neighboringCells, currentCell);
					}
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
					currentCell.setState(Cell.STATES.BURNT);
					currentCell.SetCellColor();
				}
			}
		}
	}

	public static void setMapOnFire(Cell cell, Cell homeCell){
		// for determining direction of fire
		int homerow = homeCell.row;
		int hoomecolumn = homeCell.column;
		// create new cell for next step
		Cell nextCell = new Cell();
		nextCell.setWeather(cell.getCellWeather());
		// see if any wildlife present to kill
		wildlife.checkIfDead(cell, nextCell);
		nextCell.setState(cell.getState());
		// set coordinates of new cell
		nextCell.coordinates = cell.coordinates;
		nextCell.row = cell.row;
		nextCell.column = cell.column;
		// calculate direction of fire
		Cell.FIREMOVING direction = fireDirection(homerow, hoomecolumn, nextCell.row, nextCell.column);
		// set fire direction
		nextCell.setFireMoving(direction);
		// set it on fire
		nextCell.setState(Cell.STATES.BURNING);
		nextCell.SetCellColor();
		// place it on next map iteration
		nextStep[nextCell.row][nextCell.column] = nextCell;
	}

	private static Cell.FIREMOVING fireDirection(int homerow, int hoomecolumn, int nextCellrow, int nextCellcolumn){
		Cell.FIREMOVING direction = Cell.FIREMOVING.VOID;
		if (nextCellrow - homerow > 0 && nextCellcolumn - hoomecolumn == 0){
			direction = Cell.FIREMOVING.SOUTH;
		}
		else if (nextCellrow - homerow < 0 && nextCellcolumn - hoomecolumn == 0){
			direction = Cell.FIREMOVING.NORTH;
		}
		else if (nextCellrow - homerow == 0 && nextCellcolumn - hoomecolumn < 0 ){
			direction = Cell.FIREMOVING.WEST;
		}
		else if (nextCellrow - homerow == 0 && nextCellcolumn - hoomecolumn > 0 ){
			direction = Cell.FIREMOVING.EAST;
		}
		else if (nextCellrow - homerow < 0 && nextCellcolumn - hoomecolumn < 0 ){
			direction = Cell.FIREMOVING.NORTHWEST;
		}
		else if (nextCellrow - homerow < 0 && nextCellcolumn - hoomecolumn > 0 ){
			direction = Cell.FIREMOVING.NORTHEAST;
		}
		else if (nextCellrow - homerow > 0 && nextCellcolumn - hoomecolumn > 0 ){
			direction = Cell.FIREMOVING.SOUTHEAST;
		}
		else if (nextCellrow - homerow > 0 && nextCellcolumn - hoomecolumn < 0 ){
			direction = Cell.FIREMOVING.SOUTHWEST;
		}

		return direction;
	}

	public static Cell [] findNeighbors(int row, int column){
		
		Cell north      = worldMatrix   [row - 1]   [column];
		Cell south      = worldMatrix   [row + 1]   [column];
		Cell east       = worldMatrix   [row]       [column + 1];
		Cell west       = worldMatrix   [row]       [column - 1];
		Cell northeast  = worldMatrix   [row + 1]   [column + 1];
		Cell northwest  = worldMatrix   [row + 1]   [column - 1];
		Cell southeast  = worldMatrix   [row - 1]   [column + 1];
		Cell southwest  = worldMatrix   [row - 1]   [column - 1];

		southwest.setPositionAsNeighbor(Cell.POSITIONASNEIGHBOR.SOUTHWEST);
		north.setPositionAsNeighbor(Cell.POSITIONASNEIGHBOR.NORTH);
		south.setPositionAsNeighbor(Cell.POSITIONASNEIGHBOR.SOUTH);
		east.setPositionAsNeighbor(Cell.POSITIONASNEIGHBOR.EAST);
		west.setPositionAsNeighbor(Cell.POSITIONASNEIGHBOR.WEST);
		northeast.setPositionAsNeighbor(Cell.POSITIONASNEIGHBOR.NORTHEAST);
		northwest.setPositionAsNeighbor(Cell.POSITIONASNEIGHBOR.NORTHWEST);
		southeast.setPositionAsNeighbor(Cell.POSITIONASNEIGHBOR.SOUTHEAST);

		Cell [] neighboringCells = {north, south, east, west, northeast, northwest, southeast, southwest};
		return neighboringCells;
	}


	private static void regrowTrees(){
		for(int j = 0 ; j < worldMatrix.length - 1; j ++){
			for (int i = 0 ; i < worldMatrix.length - 1 ; i ++)	{
				Cell currentCell = worldMatrix[j][i];
				if (currentCell.getState() == Cell.STATES.BURNT){
					double chance_to_regorw = rand.nextDouble(1);
					if (chance_to_regorw < .01){
						currentCell.setState(Cell.STATES.TREE);
					}
				}
			}
		}
	}

	private static void randomFireSpot(){
		// set 3 random fires after main fire goes out
		for (int i = 0 ; i < 5 ; i ++){
			int rand_index = rand.nextInt(1, worldMatrix.length-1);
			int rand_index_b = rand.nextInt(1, worldMatrix.length-1);
			Cell currentCell = worldMatrix[rand_index][rand_index_b];
			if (currentCell.getState() == Cell.STATES.TREE){
				currentCell.setState(Cell.STATES.BURNING);
				currentCell.SetCellColor();
			}	
		}
	}

	// METRICS FUNCTIONS 

	private static double totalBurned(){
		double burned_area = 0;
		for(int j = 0 ; j < worldMatrix.length - 1; j ++){
			for (int i = 0 ; i < worldMatrix.length - 1 ; i ++)	{
				if (worldMatrix[j][i].getState() == Cell.STATES.BURNT){
					burned_area += 1;
				}
			}
		}
		return burned_area;
	}

	public static double burnPercentage(){
		double burn_area = totalBurned();
		double total_cells = (worldMatrix.length-1) * (worldMatrix.length-1);
		double percetage_burned = (burn_area / total_cells) * 100;
		return percetage_burned;
	}

	public static double mortalityRate(){
		double total_wildlife = wildlife.activeWildlifeCells.size();
		double total_dead = totalDead();
		double mortality_rate = (total_dead / total_wildlife) * 100;
		return mortality_rate;
		}

	public static int totalDead(){
		int total_dead = 0;
		for (int i = 1;  i < worldMatrix.length - 1; i ++){
			for (int j = 1 ; j < worldMatrix.length - 1; j ++){
				if (worldMatrix[i][j].getObject() == Cell.OBJECTS.WILDLIFEDEAD){
					total_dead += 1;
					wildlife.deadanimals += 1;
					}	
				}
			}
			return total_dead;
		}
		
	public void displayData(){
		int steps = trackSteps();
		double percentage = burnPercentage();
		double mortality_rate = mortalityRate();
		String direction = todaysWeather.getStringDirection();
		int animal_pop = wildlife.activeWildlifeCells.size() - totalDead();
		long pop = Math.round(animal_pop);
		System.out.printf("%nSteps:          %d%nBurn area:      %.2f%%%nAnimal pop:     %d%nMortality rate: %.2f%%%nWind direction: %s%nMap Size:       %dx%d acres%n", 
		steps, percentage, pop, mortality_rate, direction, worldMatrix.length, worldMatrix.length);
	}

	// WORLD IMAGES

	public void createTrees(){
        	
		try{

			BufferedImage tree2 = ImageIO.read(getClass().getResourceAsStream("/trees/tree1.png"));
			BufferedImage tree3 = ImageIO.read(getClass().getResourceAsStream("/trees/tree3.png"));
			BufferedImage tree4 = ImageIO.read(getClass().getResourceAsStream("/trees/tree4.png"));
			BufferedImage tree5 = ImageIO.read(getClass().getResourceAsStream("/trees/tree5.png"));
			
			trees[0] = tree2;
			trees[1] = tree3;
			trees[2] = tree4;
			trees[3] = tree5;
	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void createFires(){
        	
		try{

			BufferedImage fire1 = ImageIO.read(getClass().getResourceAsStream("/fires/fireEast.png"));
			BufferedImage fire2 = ImageIO.read(getClass().getResourceAsStream("/fires/fireNorth.png"));
			BufferedImage fire3 = ImageIO.read(getClass().getResourceAsStream("/fires/fireWest.png"));
			BufferedImage fire4 = ImageIO.read(getClass().getResourceAsStream("/fires/fireSouth.png"));
			
			fires[0] = fire1;
			fires[1] = fire2;
			fires[2] = fire3;
			fires[3] = fire4;
	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void createBurnt(){
        	
		try{

			BufferedImage burnt1 = ImageIO.read(getClass().getResourceAsStream("/burnt/bunt1.png"));
			BufferedImage burnt2 = ImageIO.read(getClass().getResourceAsStream("/burnt/burnt2.png"));
			burnt[0] = burnt1;
			burnt[1] = burnt2;
		
	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void createAnimals(){
        	
		try{

			BufferedImage animal1 = ImageIO.read(getClass().getResourceAsStream("/animals/deaddino.png"));
			BufferedImage animal2 = ImageIO.read(getClass().getResourceAsStream("/animals/dino2.png"));
			anima[0] = animal1;
			anima[1] = animal2; 
		
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
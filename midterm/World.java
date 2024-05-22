

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class World {

	public  double total_alive = 0;
	public  double total_dead = 0;
	public  int size = Driver.size;
	public  boolean weatherSet = false;
	// create blank Cell matrix
	public  Cell[][] worldMatrix = new Cell [size][size];
	// matrix to hold the upcoming iteration of world
	public  Cell [][] nextStep = new Cell [size][size];
	// matrix to hold Cell colors
	public  String [][] rgbWorld = new String [size][size];
	// timestep recorder
	public int timeStep = 0;
	// probability of a tree catching on fire
	public  double catchprobability = Driver.catchprobability;
	// for terminating loop
	public boolean burning = true;
	// get weather for world map
	public Weather todaysWeather = Driver.todaysWeather;
	// get wildlife
	public Wildlife wildlife = new Wildlife();
	// graphics
	public  static BufferedImage [] trees = new BufferedImage[4];
	public  static BufferedImage [] fires = new BufferedImage[4];
	public  static BufferedImage [] burnt = new BufferedImage[4];
	public  static BufferedImage [] anima = new BufferedImage[4];
	private Random rand = new Random();
	
	public World(){
		
		createAnimals();
        createTrees();
        createFires();
        createBurnt();
		fillWorld();

	}
	
	public void initializeFire(){
		// SET WEATHER AND ANIMALS
		if (Driver.animalsOn)	
			this.wildlife.placeWildlife();
		copyWorldMatrix();
		// initial fire
		setCenterCellonFire();
		if (this.weatherSet)
			this.todaysWeather.setCenterWindy();
		designatetNeighborsOnFire();
	}
	
	public void spreadFire(){
		// SIMULATION LOOP
		displayData();
		if (this.weatherSet){
			this.todaysWeather.pattern();
			//this.weatherSet = false;
		} 	
		this.wildlife.clearDead();
		if (this.timeStep > 0) {
			clearPreviousFire();
			if (! this.burning){
				if (Driver.animalsOn)
				randomFireSpot();
				this.burning = true;
			}
		}
		applyChangesToWorld();
		if (Driver.endlessMode)
			regrowTrees();
		if (Driver.animalsOn){
			this.wildlife.resetMoveState();
			this.wildlife.makeAnEscape();
			if (Driver.endlessMode)
				this.wildlife.regrowWildlife();
				this.wildlife.clearEscaped();
			if (Driver.animalsWander)
				this.wildlife.moveAround();
		}
		Bomb.explodeBomb();
		designatetNeighborsOnFire();
		if (this.timeStep > 0){
			if (! stillBurning()){ 
				this.burning = false;
				if (! Driver.endlessMode){
					Driver.world.timer.stop();
				}
			}	
		}
	}


	// WORLD LOGIC

	/**
	 * Method to fill in a matrix with Cell objects
	 */
	public void fillWorld(){
		// fill the matrix with cells
		for (int i = 0 ; i < this.worldMatrix.length ; i ++){
			for (int j = 0 ; j < this.worldMatrix.length; j++){
				// create new cell object for each step
				Cell newCell = new Cell();
				newCell.row = i;
				newCell.column = j;
				newCell.coordinates = i + "," + j;
				// conditions for empty and tree
				// if at edge tops/bottom
				if (i == 0 || i == this.worldMatrix.length - 1){
					newCell.setState(Cell.STATES.EMPTY);
	
					this.rgbWorld[i][j] = newCell.cellColor;
					newCell.row = i;
					newCell.column = j;
					newCell.coordinates = i + "," + j;
				}
				// if at edge left/right
				else if ( j == 0 || j == this.worldMatrix.length - 1){
					newCell.setState(Cell.STATES.EMPTY);
			
					this.rgbWorld[i][j] = newCell.cellColor;
					newCell.row = i;
					newCell.column = j;
					newCell.coordinates = i + "," + j;
				}
				// if not at edge - tree
				else {
					newCell.setState(Cell.STATES.TREE);
				
					this.rgbWorld[i][j] = newCell.cellColor;
					newCell.row = i;
					newCell.column = j;
					newCell.coordinates = i + "," + j;
					
				}
				// put the appropriate cell in the matrix
				this.worldMatrix[i][j] = newCell;
				// also write to color file
			}
		}
	}

	/**
	 * Method to get a random percetage 1-100%
	 *
	 * @return
	 */
	public double probCatch(){
		// random value from  0.0 - 1.0
		double catchProb = rand.nextDouble(1);
		return catchProb;

	}

	public void applyChangesToWorld(){
		for(int g = 0 ; g < this.worldMatrix.length ; g ++){
			for(int h = 0 ; h < this.worldMatrix.length ; h ++){
				worldMatrix[g][h] = nextStep[g][h];
			}
		}
	}

	void copyWorldMatrix(){
		for(int g = 0 ; g < this.worldMatrix.length ; g ++){
			for(int h = 0 ; h < this.worldMatrix.length ; h ++){
				nextStep[g][h] = this.worldMatrix[g][h];
			}
		}
	}

	/* 
	private void displayWorld(){
		
		// for displaying the matrices in terminal
        //Terminal_Graphics t_graphics = new Terminal_Graphics();
		// display the world
		try{
			Thread.sleep(Driver.speed);
			}
		catch (InterruptedException iException){
			}
		// turn off to prevent slow down of bigger maps
		//t_graphics.displayWorld();
	}
	*/
	
	public int trackSteps(){
		// track steps
		if (this.burning)
			this.timeStep += 1;
			return this.timeStep;
	
	}

	private  boolean somethingBurning(Cell currentCell){
		if (currentCell.getState().equals(Cell.STATES.BURNING)) return true;
		return false;
	}

	void setCenterCellonFire(){
		// find center
		int center = size / 2;
		// set center after the loop complete
		Cell centerCell = new Cell();
		//middle cell on fire
		centerCell.setState(Cell.STATES.BURNING);
		this.rgbWorld[center][center] = centerCell.cellColor;
		this.worldMatrix[center][center] =  centerCell;
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

    void designatetNeighborsOnFire(){
        for (int f = 0 ; f < this.worldMatrix.length - 1; f ++ ){
            for(int g = 0 ; g < this.worldMatrix.length - 1; g ++){    
				Cell currentCell = this.worldMatrix[f][g];
				if (somethingBurning(currentCell)){
					Cell [] neighboringCells = findNeighbors(f, g);
					seeWhatBurns(neighboringCells, currentCell);
					}
                }
            }
        }     

    public boolean stillBurning(){
        for (int f = 0 ; f < this.worldMatrix.length - 1; f ++ ){
            for(int g = 0 ; g < this.worldMatrix.length - 1; g ++){    
                if (somethingBurning(worldMatrix[f][g])){
                    return true;
                }
            }     
        }
        return false;
    }  
	
	void clearPreviousFire(){
		for (int f = 0 ; f < this.worldMatrix.length - 1; f ++ ){
            for(int g = 0 ; g < this.worldMatrix.length - 1; g ++){    
				Cell currentCell = worldMatrix[f][g];
				if (somethingBurning(currentCell)){
					currentCell.setState(Cell.STATES.BURNT);
		
				}
			}
		}
	}

	public void setMapOnFire(Cell cell, Cell homeCell){
		// for determining direction of fire
		int homerow = homeCell.row;
		int hoomecolumn = homeCell.column;
		// create new cell for next step
		Cell nextCell = new Cell();
		nextCell.setWeather(cell.getCellWeather());
		// see if any wildlife present to kill
		this.wildlife.checkIfDead(cell, nextCell);
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
	
		// place it on next map iteration
		this.nextStep[nextCell.row][nextCell.column] = nextCell;
	}

	private Cell.FIREMOVING fireDirection(int homerow, int hoomecolumn, int nextCellrow, int nextCellcolumn){
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

	public  Cell [] findNeighbors(int row, int column){
		
		Cell north      = this.worldMatrix    [row - 1]   [column];
		Cell south      = this.worldMatrix    [row + 1]   [column];
		Cell east       = this.worldMatrix    [row]       [column + 1];
		Cell west       = this.worldMatrix    [row]       [column - 1];
		Cell northeast  = this.worldMatrix    [row + 1]   [column + 1];
		Cell northwest  = this.worldMatrix    [row + 1]   [column - 1];
		Cell southeast  = this.worldMatrix    [row - 1]   [column + 1];
		Cell southwest  = this.worldMatrix    [row - 1]   [column - 1];

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


	public void regrowTrees(){
		for(int j = 0 ; j < this.worldMatrix.length - 1; j ++){
			for (int i = 0 ; i < this.worldMatrix.length - 1 ; i ++)	{
				Cell currentCell = this.worldMatrix [j][i];
				if (currentCell.getState() == Cell.STATES.BURNT){
					double chance_to_regorw = rand.nextDouble(1);
					if (chance_to_regorw < Driver.chanceToRegrow){
						currentCell.setState(Cell.STATES.TREE);
					}
				}
			}
		}
	}

	public void randomFireSpot(){
		// set 3 random fires after main fire goes out
		for (int i = 0 ; i < Driver.numberOfFires ; i ++){
			int rand_index = rand.nextInt(1, this.worldMatrix.length-1);
			int rand_index_b = rand.nextInt(1, this.worldMatrix.length-1);
			Cell currentCell = worldMatrix[rand_index][rand_index_b];
			if (currentCell.getState() == Cell.STATES.TREE){
				currentCell.setState(Cell.STATES.BURNING);
				
			}	
		}
	}

	// METRICS FUNCTIONS 

	private double totalBurned(){
		double burned_area = 0;
		for(int j = 0 ; j < this.worldMatrix .length - 1; j ++){
			for (int i = 0 ; i < this.worldMatrix .length - 1 ; i ++)	{
				if (this.worldMatrix [j][i].getState() == Cell.STATES.BURNT){
					burned_area += 1;
				}
			}
		}
		return burned_area;
	}

	public double burnPercentage(){
		double burn_area = totalBurned();
		double total_cells = (this.worldMatrix .length-1) * (this.worldMatrix .length-1);
		double percetage_burned = (burn_area / total_cells) * 100;
		return percetage_burned;
	}

	public double mortalityRate(){
		double mortality_rate = 0.1;
		if (this.wildlife.deadanimals.size()> 0){
			mortality_rate = ((double)wildlife.deadanimals.size() / (double)wildlife.activeWildlifeCells.size() ) * 100;
		}
		return mortality_rate;
	}

	public double totalDead(){
		int total_dead = 0;
		for (int i = 1;  i < this.worldMatrix .length - 1; i ++){
			for (int j = 1 ; j < this.worldMatrix .length - 1; j ++){
				if (this.worldMatrix [i][j].getObject() == Cell.OBJECTS.WILDLIFEDEAD){
					total_dead += 1;
					}	
				}
			}
			return total_dead;
		}

	public double totalAlive(){
		double total_alive = 0;
		for (int i = 1;  i < this.worldMatrix .length - 1; i ++){
			for (int j = 1 ; j < this.worldMatrix .length - 1; j ++){
				if (this.worldMatrix [i][j].getObject() == Cell.OBJECTS.WILDLIFEALIVE){
					total_alive ++;
					}	
				}
			}
			return total_alive;
		}

	public void displayData(){
		System.out.print("\033[H\033[2J");  
        System.out.flush(); 
		int steps = trackSteps();
		double percentage = burnPercentage();
		double mortality_rate = mortalityRate();
		String direction = todaysWeather.getStringDirection();
		double animal_pop = totalAlive();
		long pop = Math.round(animal_pop);
		//if (animal_pop == 0) wildlife.repopulate();
		int area = worldMatrix.length * worldMatrix.length;
		System.out.printf("%nSteps:          %d%nBurn area:      %.2f%%%nAnimal pop:     %d%nMortality rate: %.2f%%%nWind direction: %s%nMap Size:       %d SQ acres%n", 
		steps, percentage, pop, mortality_rate, direction, area);
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
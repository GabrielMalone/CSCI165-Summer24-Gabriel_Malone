// Gabriel Malone / CS165 / Midterm / Summer 2024

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class World {
	// data output info
	public  double total_alive = 0;
	public  double total_dead = 0;
	// map size (width) also used in calculating animal population maxes
	public  int size = Driver.size;
	// used to manage flow of main loop logic
	// create blank Cell matrix
	public  Cell[][] worldMatrix = new Cell [size][size];
	// matrix to hold the upcoming iteration of world
	public  Cell [][] nextStep = new Cell [size][size];
	// matrix to hold Cell colors
	public  String [][] rgbWorld = new String [size][size];
	// for unit testing purposes
	public Cell centerCell;
	// timestep recorder
	public int timeStep = 0;
	// probability of a tree catching on fire
	public  double catchprobability = Driver.catchprobability;
	// for terminating loop
	public boolean burning = true;
	// get weather for world map
	public Wind todaysWind = Driver.todaysWind;
	// get wildlife
	public Wildlife wildlife = new Wildlife();
	// get rain
	public Rain todaysRain = new Rain();
	// graphics
	public  static BufferedImage [] trees = new BufferedImage[4];
	public  static BufferedImage [] fires = new BufferedImage[4];
	public  static BufferedImage [] burnt = new BufferedImage[4];
	public  static BufferedImage [] anima = new BufferedImage[4];
	public  static BufferedImage [] winds = new BufferedImage[4];
	// for stochatic method
	private Random rand = new Random();

	/**
	 * Instance method
	 */
	public World(){
		// load images
		createAnimals();
  		createTrees();
  		createFires();
  		createBurnt();
		createWind();
		// create base world
		fillWorld();
	}

	// INITIALIZE LOOP

	/*
	 * Method to set up the simulation to run in a loop
	 */
	public void initializeFire(){
		// creates next instance of the world
		copyWorldMatrix();
		// initial fire
		// setCenterCellonFire();
		// see which neighbors burn
		designatetNeighborsOnFire();
	}

	// MAIN LOOP

	/**
	 * Method to simulate fire burning
	 */
	public void spreadFire(){
		// SIMULATION LOOP
		if (this.timeStep > 1) {
			clearPreviousFire();
			// if fires go out and endless mode on
			// reset the fires at random spots
			if (! this.burning){
				randomFireSpot();
				this.burning = true;
			}
		}
		applyChangesToWorld();
		// set the cells for the next iteration
		if (Driver.rainOn){
			this.todaysRain.resetMoveState();
			this.todaysRain.scatterRain();
			if (Driver.weatherOn)
				this.todaysRain.drip();
			this.todaysRain.waterTrees();
			this.todaysRain.clearBorderRain();
		}
		if (Driver.endlessMode)
			regrowTrees();
		if (Driver.animalsOn){
			this.wildlife.resetMoveState();
			this.wildlife.makeAnEscape();
			if (Driver.endlessMode)
				this.wildlife.regrowWildlife();
				this.wildlife.clearEscaped();
				this.wildlife.clearDead();
			if (Driver.animalsWander)
				this.wildlife.moveAround();
			else if (! Driver.animalsWander){
				this.wildlife.reproduce();
				this.wildlife.overPopulation();
			}
			
		}
		// mouse input actions
		Bomb.explodeBomb();
		// see what cells next iteration will be on fire
		designatetNeighborsOnFire();
		if (this.timeStep > 0){
			// if single-run mode, end here
			if (! stillBurning()){
				this.burning = false;
				if (! Driver.endlessMode)
					 Driver.world.timer.stop();	
			}
		}
	}

	// WORLD LOGIC

	/**
	 * Method to fill in a matrix with Cell objects
	 */
	public void fillWorld(){
		// fill the matrix with cells
		for (int i = 0 ; i < this.size ; i ++){
			for (int j = 0 ; j < this.size; j++){
				// create new cell object for each step
				Cell newCell = new Cell();
				newCell.row = i;
				newCell.column = j;
				newCell.coordinates = i + "," + j;
				// conditions for empty and tree
				// if at edge tops/bottom
				if (i == 0 || i == this.size - 1){
					newCell.setState(Cell.STATES.EMPTY);
					newCell.SetCellColor();
					this.rgbWorld[i][j] = newCell.cellColor;
					newCell.row = i;
					newCell.column = j;
					newCell.coordinates = i + "," + j;
				}
				// if at edge left/right
				else if ( j == 0 || j == this.size - 1){
					newCell.setState(Cell.STATES.EMPTY);
					newCell.SetCellColor();
					this.rgbWorld[i][j] = newCell.cellColor;
					newCell.row = i;
					newCell.column = j;
					newCell.coordinates = i + "," + j;
				}
				// if not at edge - tree
				else {
					newCell.setState(Cell.STATES.TREE);
					newCell.SetCellColor();
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
		setCenterCellonFire();
	}

	/**
	 * Method to get a random percetage 1-100%
	 *
	 * @return
	 */
	double probCatch(){
		// random value from  0.0 - 1.0
		double catchProb = this.rand.nextDouble(1);
		return catchProb;

	}

	/**
	 * Method to copy previous iteration to current one
	 */
	void applyChangesToWorld(){
		for(int g = 0 ; g < this.size ; g ++){
			for(int h = 0 ; h < this.size ; h ++){
				worldMatrix[g][h] = nextStep[g][h];
			}
		}
	}

	/**
	 * Method to copy the current world iteration to the next one
	 */
	void copyWorldMatrix(){
		for(int g = 0 ; g < this.size ; g ++){
			for(int h = 0 ; h < this.size ; h ++){
				nextStep[g][h] = this.worldMatrix[g][h];
			}
		}
	}

	/**
	 * Method to track steps (only tallies when a cell is burning)
	 * @return steps 
	 */
	public int trackSteps(){
		// track steps
		if (this.burning)
			this.timeStep += 1;
			return this.timeStep;

	}

	/**
	 *  Method to see if the current cell is burning
	 * @param currentCell
	 * @return
	 */
	private  boolean somethingBurning(Cell currentCell){
		if (currentCell.getState().equals(Cell.STATES.BURNING)) return true;
		return false;
	}

	/**
	 * Method to find/set the center cell of the matrix
	 */
	void placeCenterCell(){
		double size = this.size;
		// find center (round up e.g. 5.5 --> 6.0)
		double center = Math.ceil(size / 2);
		// convert to an int (e.g. 6.0 --> 6) for matrix placement
		int centered = (int)center;
		// set center after the loop complete
		this.centerCell = new Cell();
		// for old graphics set up
		// place center cell and assign its coordinates for other logic
		this.worldMatrix[centered][centered] =  this.centerCell;
		this.centerCell.coordinates = centered + "," + centered;
		this.centerCell.row = centered;
		this.centerCell.column = centered;

	}
	
	/**
	 * Method to set the cell at the center of a matrix on fire
	 */
	void setCenterCellonFire(){
		placeCenterCell();
		this.centerCell.setState(Cell.STATES.BURNING);
		this.centerCell.SetCellColor();
	}

	/**
	 * Method to change burn characteristics of a cell in path or near the path of wind
	 * @param homeCell cell that was burning
	 * @param currentCell cell that is currently burning
	 * @param chanceToBurn	cell's chance to burn
	 * @return
	 */
	private double windDirectionEffect(Cell homeCell, Cell currentCell, double chanceToBurn){
		// array for all of a cell's direct neighbors
		Cell[] neighbors = findNeighbors(homeCell.row, homeCell.column);

		Cell north			= neighbors[0];
		Cell south			= neighbors[1];
		Cell east			= neighbors[2];
		Cell west			= neighbors[3];
		Cell northeast 		= neighbors[4];
		Cell northwest 		= neighbors[5];
		Cell southeast 		= neighbors[6];
		Cell southwest 		= neighbors[7];

		HashMap<Cell, String> directions = new HashMap<>();

		directions.put(north, "NORTH");
		directions.put(south, "SOUTH");
		directions.put(east, "EAST");
		directions.put(west, "WEST");
		// didn't end up using the non-cardinal directions
		directions.put(northeast, "northeast");
		directions.put(northwest, "northwest");
		directions.put(southeast, "southeast");
		directions.put(southwest, "southwest");
		// if cell set to potentially be on fire and in path of wind, more likely to burn
		if (directions.get(currentCell).equals(todaysWind.windDirection)){
			chanceToBurn *= -1;
			return chanceToBurn;
		}
		// if outside of path, cell set to be on fire is less likely to burn than usual
		else chanceToBurn += .05;
		return chanceToBurn;
	}
	/**
	 * Method to see which neighbors of a burning cell catch on fire
	 * @param neighboringcells
	 * @param homeCell
	 */
	private void seeWhatBurns(Cell[] neighboringcells, Cell homeCell){
		for (Cell cell : neighboringcells){
			double chanceToBurn = rand.nextDouble(0, 1);
			if  (cell.getState().equals(Cell.STATES.TREE)){
				// wind multiplier
				if (cell.getCellWind().equals(Cell.WIND.WINDY)){
					chanceToBurn += windDirectionEffect(homeCell, cell, chanceToBurn);
				}
				// rain multiiplier
				if (cell.getRain() == Cell.RAIN.RAINING)
					chanceToBurn += cell.rainEffects(chanceToBurn);
				if 	(chanceToBurn <= this.catchprobability){
					setMapOnFire(cell, homeCell);
				}
			}
		}
	}

	/**
	 * Method to see which of a cell's neighbors should bet set on fire
	 */
	void designatetNeighborsOnFire(){
		for (int f = 1 ; f < this.size - 1; f ++ ){
			for(int g = 1 ; g < this.size - 1; g ++){
				Cell currentCell = this.worldMatrix[f][g];
				if (somethingBurning(currentCell)){
					Cell [] neighboringCells = findNeighbors(f, g);
					seeWhatBurns(neighboringCells, currentCell);
					}
				}
			}
		}

	/**
	 * See if there are any burning cells anywhere in the matrix
	 * @return
	 */
	public boolean stillBurning(){
		for (int f = 0 ; f < this.size - 1; f ++ ){
			for(int g = 0 ; g < this.size - 1; g ++){
				if (somethingBurning(worldMatrix[f][g])){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Clear any fires from the previous iteration
	 */
	void clearPreviousFire(){
		for (int f = 0 ; f < this.size - 1; f ++ ){
			for(int g = 0 ; g < this.size - 1; g ++){
				Cell currentCell = worldMatrix[f][g];
				if (somethingBurning(currentCell)){
					currentCell.setState(Cell.STATES.BURNT);
					currentCell.SetCellColor();
				}
			}
		}
	}

	/**
	 *  Method to set a cell on fire once it has been determined that it should be set on fire.
	 * @param cell current cell to be set on fire
	 * @param homeCell previous cell on fire to determine direction of fire
	 */
	public void setMapOnFire(Cell cell, Cell homeCell){
		// for determining direction of fire
		int homerow = homeCell.row;
		int hoomecolumn = homeCell.column;
		// create new cell for next step
		Cell nextCell = new Cell();
		nextCell.setWind(cell.getCellWind());
		// see if any wildlife present to kill
		this.wildlife.checkIfDead(cell, nextCell);
		this.todaysRain.checkIfDriedOut(cell, cell);
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
		this.nextStep[nextCell.row][nextCell.column] = nextCell;
	}

	/**
	 * Method to determind the direction of fire. used to help animals flee in a safe direction
	 * @param homerow
	 * @param hoomecolumn
	 * @param nextCellrow
	 * @param nextCellcolumn
	 * @return
	 */
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

	/**
	 * Method to determine the relative cardinal directions of a cell to another cell.
	 * Helps animals flee more effectively
	 * @param row
	 * @param column
	 * @return
	 */
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

	/**
	 * Method to regrow trees on the map where there was once a fire
	 */
	public void regrowTrees(){
		for(int j = 0 ; j < this.size - 1; j ++){
			for (int i = 0 ; i < this.size - 1 ; i ++)	{
				Cell currentCell = this.worldMatrix [j][i];
				if (currentCell.getState() == Cell.STATES.BURNT){
					double chance_to_regorw = this.rand.nextDouble(1);
					if (chance_to_regorw < Driver.chanceToRegrow){
						currentCell.setState(Cell.STATES.TREE);
						currentCell.SetCellColor();
					}
				}
			}
		}
	}

	/**
	 * Method to place random cells in the matrix on fire.
	 */
	public void randomFireSpot(){
		// set 3 random fires after main fire goes out
		for (int i = 0 ; i < Driver.numberOfFires ; i ++){
			int rand_index = this.rand.nextInt(1, this.size-1);
			int rand_index_b = this.rand.nextInt(1, this.size-1);
			Cell currentCell = this.worldMatrix[rand_index][rand_index_b];
			Cell [] neighbors = findNeighbors(currentCell.row, currentCell.column);
			seeWhatBurns(neighbors, currentCell);
			}
		}
	

	// METRICS FUNCTIONS

	/**
	 * Method to determine what percentage of the matrix is burned
	 * @return area of map burned
	 */
	private double totalBurned(){
		double burned_area = 0;
		for(int j = 0 ; j < this.size - 1; j ++){
			for (int i = 0 ; i < this.size - 1 ; i ++)	{
				if (this.worldMatrix [j][i].getState() == Cell.STATES.BURNT){
					burned_area += 1;
				}
			}
		}
		return burned_area;
	}

	/**
	 * Method to display the burn percentage
	 * @return burn percentage
	 */
	public double burnPercentage(){
		double burn_area = totalBurned();
		double total_cells = (this.size-1) * (this.size-1);
		double percetage_burned = (burn_area / total_cells) * 100;
		return percetage_burned;
	}


	/**
	 * Method to determine how many dead animals there are on the map
	 * @return
	 */
	public double totalDead(){
		int total_dead = 0;
		for (int i = 1;  i < this.size - 1; i ++){
			for (int j = 1 ; j < this.size - 1; j ++){
				if (this.worldMatrix [i][j].getObject() == Cell.OBJECTS.WILDLIFEDEAD){
					total_dead += 1;
					}
				}
			}
			return total_dead;
		}

	/**
	 * Method to show the total live animals on the map
	 * @return
	 */
	public double totalAlive(){
		double total_alive = 0;
		for (int i = 1;  i < this.size - 1; i ++){
			for (int j = 1 ; j < this.size - 1; j ++){
				if (this.worldMatrix [i][j].getObject() == Cell.OBJECTS.WILDLIFEALIVE){
					total_alive ++;
					}
				}
			}
			return total_alive;
		}
		
	/**
	 *  Method to dispay metrics in the terminal
	 */
	public void displayData(){
		System.out.print("\033[H\033[2J");
		System.out.flush();
		int steps = trackSteps();
		double percentage = burnPercentage();
		String direction = todaysWind.getStringDirection();
		double animal_pop = totalAlive();
		long pop = Math.round(animal_pop);
		//if (animal_pop == 0) wildlife.repopulate();
		int area = size * size;
		System.out.printf("%nSteps:          %d%nBurn area:      %.2f%%%nAnimal pop:     %d%nWind direction: %s%nMap Size:       %d SQ acres%n",
		steps, percentage, pop, direction, area);
	}

	// WORLD IMAGES
	/**
	 * Method to load tree images for the map
	 */
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

	/**
	 * Method to laod fire images for the map
	 */
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

	/**
	 * Method to load burnt cell images for the map
	 */
	public void createBurnt(){

		try{

			BufferedImage burnt1 = ImageIO.read(getClass().getResourceAsStream("/burnt/bunt1.png"));
			BufferedImage burnt2 = ImageIO.read(getClass().getResourceAsStream("/burnt/burnt2.png"));
			BufferedImage moist = ImageIO.read(getClass().getResourceAsStream("/trees/moist.png"));
			burnt[0] = burnt1;
			burnt[1] = burnt2;
			burnt[2] = moist;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to load animal images for the map
	 */
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

	/**
	 * Method to load wind images for the map
	 */
	public void createWind(){

		try{

			BufferedImage wind1 = ImageIO.read(getClass().getResourceAsStream("/winds/wind.png"));
			BufferedImage wind2 = ImageIO.read(getClass().getResourceAsStream("/winds/wind2.png"));
			BufferedImage wind3 = ImageIO.read(getClass().getResourceAsStream("/winds/wind3.png"));
			winds[0] = wind1;
			winds[1] = wind2;
			winds[2] = wind3;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
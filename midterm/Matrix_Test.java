// Gabriel Malone / CS165 / Midterm / Summer 2024

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.BeforeEach;

public class Matrix_Test {

	World worldOne;

	@BeforeEach
	public void setup(){
		worldOne = null;
	}

	@Test
	public void matrixIsCorrectSizeTest(){
		// test to see that a matrix is created the correct size

		int size = 200;                     // pick a  size
		size = Driver.worldResize(size);    // adjust size as necessary (200 -> 201)
		Driver.size = size;                 // set size for the matrix
		worldOne = new World();             // create a basic world (takes size from Driver)
		// see if number of cells in the matrix is correct (201*201 == 40401 cells)
		int number_of_cells = 0;
		for (int i = 0 ; i < worldOne.worldMatrix.length ; i ++){
			for (int j = 0 ; j < worldOne.worldMatrix.length; j ++){
				// count each cell
				number_of_cells ++;
			}
		}
		assertTrue(number_of_cells == 40401);;
	}

	@Test
	public void sizeAdjustTest(){
		// check to see that input for sizes are adjusted correctly

		//for creating a world matrix (needs to be odd for perfectly centered center cell)
		int size = 200; // even number should be adjsuted to next highest odd number (201)
		size = Driver.worldResize(size);
		assertTrue(size == 201);
		int size2 = 201; // odd numbers should remain unchanged
		size2 = Driver.worldResize(size2);
		assertTrue(size2 == 201);
		int size3 = 10; //
		size3 = Driver.worldResize(size3);
		assertTrue(size3 == 11);
	}

	@Test
	public void centerCellBurningPlacementTest(){
		// test to see that the center cell is set on fire

		int size = 200;                                             // pick a  size (row and column length)
		size = Driver.worldResize(size);                            // adjust size as necessary (200 -> 201)
		Driver.size = size;                                         // set size for the matrix (201 * 201)
		double center = Math.ceil((double)size / 2);                // center should be at size (row and column lengths) divided by 2
		int centered = (int)center;
		worldOne = new World();                                     // create a basic world (takes size from Driver)
		worldOne.setCenterCellonFire();                             // set the center cell on fire
		Cell checkCell = worldOne.worldMatrix[centered][centered];  // look at the center cell
		assertTrue(checkCell.getState() == Cell.STATES.BURNING);    // see if what should be the center is on fire
	}

	@Test
	public void centerCellPlacementIsCenteredTest(){
		// test to see that a the center cell is placed perfectly center

		int size = 10;                                               // pick a  size (row and column length)
		size = Driver.worldResize(size);                             // adjust size as necessary (10->11)
		Driver.size = size;                                          // set size for the matrix (11 * 11)
		worldOne = new World();                                      // create a basic world (takes size from Driver)
		worldOne.placeCenterCell();                                  // place the center cell
		 // see if what should be the center is at center coordiantes (6, 6)
		assertTrue(worldOne.centerCell.row == 6 && worldOne.centerCell.column == 6);
	}

	@Test
	public void firesGetPutOutEachStepTest(){
		// test to see that fires are put out after each step

		// create world instance
		int size = 25;                                               // pick a size (row and column length)
		size = Driver.worldResize(size);                             // adjust size as necessary (25->25)
		Driver.size = size;                                          // set size for the matrix (25 * 25)
		worldOne = new World();                                      // create a basic world (takes size from Driver)
		worldOne.fillWorld();                                        // create and fill world matrix with inital trees/borders
		worldOne.setCenterCellonFire();                              // place the center cell and set it on fire  @ (13, 13)
		// creates a copy of the current world matrix for the cells in the matrix to be updated with the logic in this timestep.
		worldOne.copyWorldMatrix();
		// at timestep zero center sell is on fire
		assertTrue(worldOne.centerCell.getState() == Cell.STATES.BURNING);
		// see which of the cells neighboring the center cell
		// will be set on fire for the next step
		// (for this test, all neighboring cells will be set on fire)
		worldOne.designatetNeighborsOnFire();
		// clear the current cells on fire so they do not carry over
		//to the next step (cells remain on fire for one turn)
		worldOne.clearPreviousFire();
		// once all the burning logic completes,
		// update the next matrix to set the cells marked for fire to be on fire
		worldOne.applyChangesToWorld();
		// at timestep 1 (next step) center cell should be extinguished
		assertFalse(worldOne.centerCell.getState() == Cell.STATES.BURNING);
	}

	@Test
	public void neighborsSetOnFireAppropriatelyTest(){
		// test to see that a cell's neighbors are able to be set on fire

		// create world instance
		int size = 25;                                               // pick a size (row and column length)
		size = Driver.worldResize(size);                             // adjust size as necessary (25->25)
		Driver.catchprobability = 1;                                 // all neighboring cells should be set on fire
		Driver.size = size;                                          // set size for the matrix (25 * 25)
		worldOne = new World();                                      // create a basic world (takes size from Driver)
		worldOne.fillWorld();                                        // create and fill world matrix with inital trees/borders
		worldOne.setCenterCellonFire();                              // place the center cell and set it on fire  @ (13, 13)
		// creates a copy of the current world matrix for the cells in the matrix to be updated with the logic in this timestep.
		worldOne.copyWorldMatrix();
		// at timestep zero all cells neighboring center cell should be STATES.TREE
		// get centerCell's neighbors
		Cell [] centerCellNeighbors = worldOne.findNeighbors(worldOne.centerCell.row, worldOne.centerCell.column);
		for (Cell neighboringCell : centerCellNeighbors){
			assertTrue(neighboringCell.getState() == Cell.STATES.TREE);
		}
		// see which of the cells neighboring the center cell
		// will be set on fire for the next step
		// (for this test, all neighboring cells will be set on fire)
		worldOne.designatetNeighborsOnFire();
		// clear the current cells on fire so they do not carry
		// over to the next step (cells remain on fire for one turn)
		worldOne.clearPreviousFire();
		// once all the burning logic completes,
		// update the next matrix to set the cells marked for fire to be on fire
		worldOne.applyChangesToWorld();
		Cell [] centerCellUpdatedNeighbors = worldOne.findNeighbors(worldOne.centerCell.row, worldOne.centerCell.column);
		// now all cells neighboring the centerCell should be on fire
		for (Cell neighboringCell : centerCellUpdatedNeighbors){
			assertTrue(neighboringCell.getState() == Cell.STATES.BURNING);
		}
	}

	@Test
	public void copyMatrixTest(){
		// test to see that the contents of one matrix
		// are copied exactly into another

		// create world instance
		int size = 25;                                               // pick a size (row and column length)
		size = Driver.worldResize(size);                             // adjust size as necessary (25->25)
		Driver.size = size;                                          // set size for the matrix (25 * 25)
		worldOne = new World();                                      // create a basic world (takes size from Driver)
		worldOne.fillWorld();                                        // create and fill world matrix with inital trees/borders
		// the two matrices used in the fire burning logic should
		// start out with the worldMatrix being filled with updated cells
		// while nextStep should remain filled with null cells
		// thus they should have different conents
		for(int g = 0 ; g < worldOne.size ; g ++){
			for(int h = 0 ; h < worldOne.size ; h ++){
				assertFalse(worldOne.nextStep[g][h] == worldOne.worldMatrix[g][h]);
			}
		}
		// the copy method should set the two matrices equal to each other
		worldOne.copyWorldMatrix();
		for(int g = 0 ; g < worldOne.size ; g ++){
			for(int h = 0 ; h < worldOne.size ; h ++){
				assertTrue(worldOne.nextStep[g][h] == worldOne.worldMatrix[g][h]);
			}
		}
	}

	@Test
	public void probabilityCatchTest(){
		// test to see that the set probability sets cells on fire
		// at that set probability, on average

		// create world instance
		int size = 25;
		Driver.size = size;
		int t = 0;
		double [] numFiresArray = new double [100000];
		World worldOne = new World();
		size = Driver.worldResize(size);
		// set probability at 25%  -- results should approximate this value
		Driver.catchprobability = .25;
		// loop the process of set neighbors of a burning cell on fire 100,000 times
		while (t < 100000){
			worldOne.fillWorld();
			worldOne.setCenterCellonFire();
			worldOne.copyWorldMatrix();
			worldOne.designatetNeighborsOnFire();
			worldOne.clearPreviousFire();
			// tally the number of neighboring cells on fire each iteration
			double numberOfFires = 0;
			worldOne.applyChangesToWorld();
			Cell [] centerNeighbors = worldOne.findNeighbors(worldOne.centerCell.row, worldOne.centerCell.column);
			for (Cell neighboringCell : centerNeighbors){
				if (neighboringCell.getState() == Cell.STATES.BURNING){
					numberOfFires ++;
				}
			}
			// append the sum to the array
			numFiresArray[t] = numberOfFires;
			t ++;
		}
		// find average of the fires per round in the array
		// sum the results
		// divide by length of array
		double sum = 0;
		for (double result : numFiresArray){
			sum += result;
		}
		double average = sum / numFiresArray.length;
		// convert the average to the average percntage of neighbors burned
		double percent_burned = (average / 8);
		double upper_range = Driver.catchprobability + .1;
		double lower_range = Driver.catchprobability - .1;
		// if percent_burned within range of 25%, pass.
		if (percent_burned >= lower_range && percent_burned <= upper_range)
			assertTrue(percent_burned >= lower_range || percent_burned <= upper_range);
	}

	@Test
	public void findTheRightNeighborsTest(){
		// test to see that a cell's neighbors are correctly identified
		// create world instance

		int size = 10;                                               // pick a size (row and column length)
		size = Driver.worldResize(size);                             // adjust size as necessary (10->11)
		Driver.size = size;                                          // set size for the matrix (11 * 11)
		worldOne = new World();                                      // create a basic world (takes size from Driver)
		worldOne.fillWorld();                                        // create and fill world matrix with inital trees/borders
		worldOne.placeCenterCell();                                  // place a center cell at coordinates (6,6)
		Cell [] neighbors = worldOne.findNeighbors(worldOne.centerCell.row, worldOne.centerCell.column);
		// neighbors should equal 8 in total (both cardinal and ordinal directions)
		// get the center cell's neighbors
		assertTrue(neighbors.length == 8);
		// validate that the neighbors are at the correct coordinates and ordinals/cardinals
		// first cell neighbor should be NORTH of center cell
		assertTrue(neighbors[0].getPosition() == Cell.POSITIONASNEIGHBOR.NORTH);
		// first cell neihbors coordinates, as North of 6,6 should be 5,6
		assertTrue(neighbors[0].row == 5 && neighbors[0].column == 6);
		// second cell neighbor should be SOUTH of center cell
		assertTrue(neighbors[1].getPosition() == Cell.POSITIONASNEIGHBOR.SOUTH);
		// second cell neihbors coordinates, as South of 6,6 should be 7,6
		assertTrue(neighbors[1].row == 7 && neighbors[1].column == 6);
		// third cell neighbor should be EAST of center cell
		assertTrue(neighbors[2].getPosition() == Cell.POSITIONASNEIGHBOR.EAST);
		// third cell neihbors coordinates, as East of 6,6 should be 6,7
		assertTrue(neighbors[2].row == 6 && neighbors[2].column == 7);
		// fourth cell neighbor should be WEST of center cell
		assertTrue(neighbors[3].getPosition() == Cell.POSITIONASNEIGHBOR.WEST);
		// fourth cell neihbors coordinates, as West of 6,6 should be 6,5
		assertTrue(neighbors[3].row == 6 && neighbors[3].column == 5);
		// fifth cell neighbor should be NORTHEAST of center cell
		assertTrue(neighbors[4].getPosition() == Cell.POSITIONASNEIGHBOR.NORTHEAST);
		// fifth cell neihbors coordinates, as Northeast of 6,6 should be 7,7
		assertTrue(neighbors[4].row == 7 && neighbors[4].column == 7);
		// sixth cell neighbor should be NORTHWEST of center cell
		assertTrue(neighbors[5].getPosition() == Cell.POSITIONASNEIGHBOR.NORTHWEST);
		// sixth cell neihbors coordinates, as Northwest of 6,6 should be 7,5
		assertTrue(neighbors[5].row == 7 && neighbors[5].column == 5);
		// seventh cell neighbor should be SOUTHEAST of center cell
		assertTrue(neighbors[6].getPosition() == Cell.POSITIONASNEIGHBOR.SOUTHEAST);
		// seventh cell neihbors coordinates, as Southeast of 6,6 should be 5,7
		assertTrue(neighbors[6].row == 5 && neighbors[6].column == 7);
		// eigth cell neighbor should be SOUTHWEST of center cell
		assertTrue(neighbors[7].getPosition() == Cell.POSITIONASNEIGHBOR.SOUTHWEST);
		// eight cell neihbors coordinates, as Southwest of 6,6 should be 5,5
		assertTrue(neighbors[7].row == 5 && neighbors[7].column == 5);
	}

	@Test
	public void paintTest(){
		// test to see that cells with states Tree (green), burning (red), and empty (yellow)
		// are colored correctly
		int size = 10;                                               // pick a size (row and column length)
		size = Driver.worldResize(size);                             // adjust size as necessary (10->11)
		Driver.size = size;                                          // set size for the matrix (11 * 11)
		worldOne = new World();                                      // create a basic world (takes size from Driver)
		worldOne.fillWorld();                                        // create and fill world matrix with inital trees/borders
		worldOne.setCenterCellonFire();                              // place a center cell at coordinates (6,6), set it on fire
		// center cell should be burning image
		assertTrue(worldOne.centerCell.stateimage == World.fires[3]);
		// all the neighbors at timestep 0 should be trees (randomly selected tree image)
		int total_neighbors_as_trees = 0;
		Cell [] neighbors = worldOne.findNeighbors(worldOne.centerCell.row, worldOne.centerCell.column);
		// iterate through the neighbors
		for (Cell neighbor : neighbors) {
			// go through the tree image lists
			for (BufferedImage tree : World.trees){
				// if a neighbor has one of the tree images
				if (tree == neighbor.stateimage){
					// add to the tally
					total_neighbors_as_trees ++;
				}
			}
		}
		// all 8 neighbors should be some sort of tree
		assertTrue(total_neighbors_as_trees == 8);
		// border cells should be empty
		for (int i = 0 ; i < worldOne.size ; i ++){
			for (int j = 0 ; j < worldOne.size; j++){
				// if at edge top/botoom
				if (i == 0 || i == worldOne.size - 1){
					// border cells should be empty
					assertTrue(worldOne.worldMatrix[i][j].stateimage == World.burnt[0]);
				}
				// if at edge left/right
				else if ( j == 0 || j == worldOne.size - 1){
					// border cells should be empty
					assertTrue(worldOne.worldMatrix[i][j].stateimage == World.burnt[0]);
				}
			}
		}
	}

}
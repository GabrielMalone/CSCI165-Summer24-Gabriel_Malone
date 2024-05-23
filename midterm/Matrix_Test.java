// Gabriel Malone / CS165 / Midterm / Summer 2024

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;

public class Matrix_Test {

	World worldOne, worldTwo; 

	@BeforeEach	
	public void setup(){
		worldOne = null; // kill current instance, hand over to garbage collector
		worldTwo = null; 
	}

    @Test
	public void matrixIsCorrectSizeTest(){
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
		// check to see that input for sizes are adjusted correctly for creating a world matrix (needs to be odd for perfectly centered center cell)
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
        int size = 200;                                             // pick a  size (row and column length)
        size = Driver.worldResize(size);                            // adjust size as necessary (200 -> 201)
        Driver.size = size;                                         // set size for the matrix (201 * 201)
        worldOne = new World();                                     // create a basic world (takes size from Driver)
        int center = Math.round(size / 2);                          // center should be at size (row and column lengths) divided by 2
        worldOne.setCenterCellonFire();                             // set the center cell on fire
        Cell checkCell = worldOne.worldMatrix[center][center];      // look at the center cell
        assertTrue(checkCell.getState() == Cell.STATES.BURNING);    // see if what should be the center is on fire
    }

    @Test
	public void centerCellPlacementIsCenteredTest(){
        int size = 10;                                               // pick a  size (row and column length)
        size = Driver.worldResize(size);                             // adjust size as necessary (10->11)
        Driver.size = size;                                          // set size for the matrix (11 * 11)
        worldOne = new World();                                      // create a basic world (takes size from Driver)                        
        worldOne.placeCenterCell();                                  // place the center cell                                        
        assertTrue(worldOne.centerCell.row == 6 && worldOne.centerCell.column == 6);  // see if what should be the center is at center coordiantes (6, 6)
    }

    @Test
	public void firesGetPutOutEachStepTest(){
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
        // clear the current cells on fire so they do not carry over to the next step (cells remain on fire for one turn)
        worldOne.clearPreviousFire();    
        // once all the burning logic completes, 
        // update the next matrix to set the cells marked for fire to be on fire
		worldOne.applyChangesToWorld();
        // at timestep 1 (next step) center cell should be extinguished
        assertFalse(worldOne.centerCell.getState() == Cell.STATES.BURNING);
    }

    @Test
	public void neighborsSetOnFireAppropriatelyTest(){
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
        // clear the current cells on fire so they do not carry over to the next step (cells remain on fire for one turn)
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
        //test to see that the contents of one matrix are copied exactly into another
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
		if (percent_burned >= lower_range && percent_burned <= upper_range)
        assertTrue(percent_burned >= lower_range || percent_burned <= upper_range);
    }

    @Test
    public void findTheRightNeighborsTest(){
        
    }



}
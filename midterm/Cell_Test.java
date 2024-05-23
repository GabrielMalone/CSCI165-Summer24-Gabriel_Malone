import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Cell_Test {

	Cell cellOne, cellTwo; 

	@BeforeEach	
	public void setup(){
		cellOne = null; // kill current instance, hand over to garbage collector
		cellTwo = null; 
	}

    @Test
	public void testGetStateA(){
		// check to see if getState gets the correct Cell State
		cellOne = new Cell();	                            
        cellOne.setState(Cell.STATES.EMPTY);
		assertTrue(cellOne.getState() == Cell.STATES.EMPTY);
    }

	@Test	
	public void testSetStateA(){
		// check to see if setState sets a state other than null.
		cellOne = new Cell();	                        
		Cell.STATES og_state = cellOne.getState();		// save expected state (null)
		cellOne.setState(Cell.STATES.BURNING);		    // set a state (burning)
		Cell.STATES newState = cellOne.getState();		// get the state to test if it was modified
		assertNotEquals( og_state, newState );		   
    }

    @Test	
	public void testSetStateB(){
		// check to see if setState sets the correct State
		cellOne = new Cell();	                       
		cellOne.setState(Cell.STATES.BURNING);		    // set a state (burning)
		Cell.STATES newState = cellOne.getState();		// get the state to test if it was modified
		assertTrue(newState == Cell.STATES.BURNING);    
    }

    @Test
	public void testSetStateC(){
		// check to see if setState sets correct state for two different cells
        // check if cells not equal with different states set
		cellOne = new Cell();	                                
        cellTwo = new Cell();
		cellOne.setState(Cell.STATES.BURNING);		             // set a state (burning)
        cellTwo.setState(Cell.STATES.TREE);                      // set a state (tree)
		assertNotEquals(cellOne.getState(), cellTwo.getState()); 
    }

    @Test	
	public void testSetStateD(){
		// check to see if setState sets correct state for two different cells
        // check if cells not equal with different states set
		cellOne = new Cell();	                               
        cellTwo = new Cell();                           
		cellOne.setState(Cell.STATES.TREE);		               // set a state (burning)
        cellTwo.setState(Cell.STATES.TREE);                    // set a state (tree)
		assertEquals(cellOne.getState(), cellTwo.getState()); 
    }

    @Test	
	public void testEquals(){
		// check to see if cellEquals checks true correctly with matching coordinates
        // check if cells not equal with different
        cellOne = new Cell();
        cellTwo = new Cell();                           
        cellOne.coordinates = "10,20";
        cellTwo.coordinates = "10,20";
		assertTrue(cellOne.cellEquals(cellTwo));                 
    }
    @Test	
	public void testEqualsB(){
		// check to see if cellEquals checks false correctly with mismatched coordinates
        // check if cells not equal with different states set
		cellOne = new Cell();	                  
        cellTwo = new Cell();                           
        cellOne.coordinates = "10,20";
        cellTwo.coordinates = "20,10";
		assertFalse(cellOne.cellEquals(cellTwo));      
    }
}

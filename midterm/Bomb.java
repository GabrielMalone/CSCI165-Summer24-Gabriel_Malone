// Gabriel Malone / CS165 / Midterm / Summer 2024

public class Bomb {

    /*
    * Method to place a bomb on the map
    */
    public static void placeBomb(int row, int column){  
        // put bomb at this location
        Cell cell_at_this_location = Driver.neWorld.worldMatrix[row][column];
        // update cell
        cell_at_this_location.setObject(Cell.OBJECTS.BOMB);    
		}
	
    /*
     * Method to explode any bombs placed on the map
     */
    public static void explodeBomb(){
        for (int i = 1 ; i < Driver.neWorld.worldMatrix.length - 1 ; i ++){
            for (int j = 1 ; j < Driver.neWorld.worldMatrix.length - 1 ; j ++){
                Cell currentCell = Driver.neWorld.worldMatrix[i][j];
                Cell [] neighbors = Driver.neWorld.findNeighbors(currentCell.row, currentCell.column);
                for (Cell cell : neighbors){
                    // iterate through map. If bomb present...
                    if (cell.getObject() == Cell.OBJECTS.BOMB){
                        Driver.neWorld.setMapOnFire(cell, currentCell);
                        Cell [] bombNeighbors = Driver.neWorld.findNeighbors(cell.row, cell.column);
                        // set all neighboring cells on fire. 
                        for (Cell bombcells: bombNeighbors){
                            Driver.neWorld.setMapOnFire(bombcells, currentCell);
                        }
                    }
                }
            }
        }
    }

}

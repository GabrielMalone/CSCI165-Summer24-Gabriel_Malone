

public class Bomb {
    
    	public static void placeBomb(int row, int column){  
        // put bomb at this location
        Cell cell_at_this_location = Driver.neWorld.worldMatrix[row][column];
        // update cell
        cell_at_this_location.setObject(Cell.OBJECTS.BOMB);    
		}
	
    public static void explodeBomb(){
        for (int i = 1 ; i < Driver.neWorld.worldMatrix.length - 1 ; i ++){
            for (int j = 1 ; j < Driver.neWorld.worldMatrix.length - 1 ; j ++){
                Cell currentCell = Driver.neWorld.worldMatrix[i][j];
                Cell [] neighbors = Driver.neWorld.findNeighbors(currentCell.row, currentCell.column);
                for (Cell cell : neighbors){
                    if (cell.getObject() == Cell.OBJECTS.BOMB){
                        Driver.neWorld.setMapOnFire(cell, currentCell);
                        Cell [] bombNeighbors = Driver.neWorld.findNeighbors(cell.row, cell.column);
                        for (Cell bombcells: bombNeighbors){
                            Driver.neWorld.setMapOnFire(bombcells, currentCell);
                        }
                    }
                }
            }
        }
    }

    public static void nuke(int row, int column){
        // come back and make this recursive 
        Cell cell_at_this_location = Driver.neWorld.worldMatrix[row][column];
        Cell [] neighbors = Driver.neWorld.findNeighbors(cell_at_this_location.row, cell_at_this_location.column);
        for (Cell cell : neighbors){
            Driver.neWorld.setMapOnFire(cell, cell_at_this_location);
            Cell [] further_neighbors = Driver.neWorld.findNeighbors(cell.row, cell.column);
            for (Cell furtherCell : further_neighbors){
                Driver.neWorld.setMapOnFire(furtherCell, cell);
                Cell [] furthest_neighbors = Driver.neWorld.findNeighbors(cell.row, cell.column);
                for (Cell furthestCell : furthest_neighbors){
                    Driver.neWorld.setMapOnFire(furthestCell, furtherCell);
                }
            }
        }
    }

}

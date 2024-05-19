

public class Bomb {
    
    	public static void placeBomb(int row, int column){  
        // put bomb at this location
        Cell cell_at_this_location = World.worldMatrix[row][column];
        // update cell
        cell_at_this_location.setObject(Cell.OBJECTS.BOMB);    
		}
	
    public static void explodeBomb(){
        for (int i = 1 ; i < World.worldMatrix.length - 1 ; i ++){
            for (int j = 1 ; j < World.worldMatrix.length - 1 ; j ++){
                Cell currentCell = World.worldMatrix[i][j];
                Cell [] neighbors = World.findNeighbors(currentCell.row, currentCell.column);
                for (Cell cell : neighbors){
                    if (cell.getObject() == Cell.OBJECTS.BOMB){
                        World.setMapOnFire(cell, currentCell);
                        Cell [] bombNeighbors = World.findNeighbors(cell.row, cell.column);
                        for (Cell bombcells: bombNeighbors){
                            World.setMapOnFire(bombcells, currentCell);
                        }
                    }
                }
            }
        }
    }

    public static void nuke(int row, int column){
        Cell cell_at_this_location = World.worldMatrix[row][column];
        Cell [] neighbors = World.findNeighbors(cell_at_this_location.row, cell_at_this_location.column);
        for (Cell cell : neighbors){
            World.setMapOnFire(cell, cell_at_this_location);
            Cell [] further_neighbors = World.findNeighbors(cell.row, cell.column);
            for (Cell furtherCell : further_neighbors){
                World.setMapOnFire(furtherCell, cell);
                Cell [] furthest_neighbors = World.findNeighbors(cell.row, cell.column);
                for (Cell furthestCell : furthest_neighbors){
                    World.setMapOnFire(furthestCell, furtherCell);
                }
            }
        }
    }

}

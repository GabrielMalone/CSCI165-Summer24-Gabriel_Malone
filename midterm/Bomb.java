import java.util.Random;

public class Bomb {
    
    	public static void placeBomb(){  
		for (int i = 0; i < World.worldMatrix.length / 10 ; i++){
           
            // find location on map for object
            Random random = new Random();
            int row_location = random.nextInt(2, World.worldMatrix.length - 10);
            int column_location = random.nextInt(2, World.worldMatrix.length - 10);
            // get cell at this location
            Cell cell_at_this_location = World.worldMatrix[row_location][column_location];
            // update cell
            cell_at_this_location.setObject(Cell.OBJECTS.BOMB);
            
		}
	}

    public static void explodeBomb(){
        for (int i = 1 ; i < World.worldMatrix.length - 1 ; i ++){
            for (int j = 1 ; j < World.worldMatrix.length - 1 ; j ++){
                Cell currentCell = World.worldMatrix[i][j];
                Cell [] neighbors = World.findNeighbors(currentCell.row, currentCell.column);
                for (Cell cell : neighbors){
                    if (cell.getObject() == Cell.OBJECTS.BOMB){
                        World.setMapOnFire(cell);
                        Cell [] bombNeighbors = World.findNeighbors(cell.row, cell.column);
                        for (Cell bombcells: bombNeighbors){
                            World.setMapOnFire(bombcells);
                        }
                    }
                }
            }
        }
    }



}

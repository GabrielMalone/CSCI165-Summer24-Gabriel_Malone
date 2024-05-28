
public class AlienFireCube extends Wildlife{
    
    public Cell player;
    public boolean player_set;
    public double player_size;
    public boolean burning;
    public int time_without_eating;
    private int move_distance;
    private int rain_effect;
    

    AlienFireCube () {
        this.player_set = false;
        this.player_size = 20;
        this.burning = false;
        this.time_without_eating = 0;
        this.move_distance = 4;
        this.rain_effect = 2;
    }
    
  
    public void movePlayerUp(){
        if (Driver.rainOn) this.move_distance /= this.rain_effect;
        if (this.player.row - this.move_distance >= (int)( this.player_size / 4 )) {
            Driver.neWorld.worldMatrix[this.player.row][this.player.column].setObject(Cell.OBJECTS.VOID);
            Driver.neWorld.worldMatrix[this.player.row - this.move_distance][this.player.column].setObject(Cell.OBJECTS.PLAYER);
            this.player = Driver.neWorld.worldMatrix[this.player.row - this.move_distance][this.player.column];
            if (Driver.rainOn) this.move_distance *= this.rain_effect;
        }
    }
    public void movePlayerRight(){
        if (Driver.rainOn) this.move_distance /= this.rain_effect;
        int next_position = this.player.column + this.move_distance;
        int right_border = (Driver.size - (int)this.player_size / 4);
        if (next_position <= right_border) {
            Driver.neWorld.worldMatrix[this.player.row][this.player.column].setObject(Cell.OBJECTS.VOID);
            Driver.neWorld.worldMatrix[this.player.row][this.player.column + this.move_distance].setObject(Cell.OBJECTS.PLAYER);
            this.player = Driver.neWorld.worldMatrix[this.player.row][this.player.column + this.move_distance];
            if (Driver.rainOn) this.move_distance *= this.rain_effect;
        }
    }
    public void movePlayerLeft(){
        if (Driver.rainOn) this.move_distance /= this.rain_effect;
        int next_position = this.player.column - this.move_distance;
        int left_border = ((int)(this.player_size) / 4);
        if (next_position >= left_border){
            Driver.neWorld.worldMatrix[this.player.row][this.player.column].setObject(Cell.OBJECTS.VOID);
            Driver.neWorld.worldMatrix[this.player.row][this.player.column - move_distance].setObject(Cell.OBJECTS.PLAYER);
            this.player = Driver.neWorld.worldMatrix[this.player.row][this.player.column - this.move_distance];
        }
        if (Driver.rainOn) this.move_distance *= this.rain_effect;
    }
    public void movePlayerDown(){
        if (Driver.rainOn) this.move_distance /= this.rain_effect;
        int next_position = this.player.row + this.move_distance;
        int bottom_border = (Driver.size - (int)this.player_size / 4);
        if (next_position <= bottom_border){
            Driver.neWorld.worldMatrix[this.player.row][this.player.column].setObject(Cell.OBJECTS.VOID);
            Driver.neWorld.worldMatrix[this.player.row + move_distance][this.player.column].setObject(Cell.OBJECTS.PLAYER);
            this.player = Driver.neWorld.worldMatrix[this.player.row + this.move_distance][this.player.column];
        }
        if (Driver.rainOn) this.move_distance *= this.rain_effect;
    }

    private void time_without_eating_check() {
        if (this.player.row > 1 && this.player.row < Driver.size - 1 && this.player.column > 1 && this.player.column < Driver.size - 1){
            Cell [] player_neighbors = Driver.neWorld.findNeighbors(this.player.row, this.player.column);
            int total_void_neighbors = 0;
            for (Cell neighbor : player_neighbors){
                if (neighbor.getObject() == Cell.OBJECTS.VOID){
                    total_void_neighbors ++ ;
                }
            }
            if (total_void_neighbors == 8){
                this.time_without_eating ++;
            }
        }
    }

    public void starve (){
        time_without_eating_check();
        if (this.time_without_eating > 100 && this.player_size > 10){
            this.player_size -= .5;
        }
        for( int i = 0 ; i < Driver.neWorld.size; i ++){
			for (int j = 0 ; j < Driver.neWorld.size; j ++){
                if (Driver.neWorld.worldMatrix[i][j].getObject() == Cell.OBJECTS.PLAYER){
                    Driver.neWorld.worldMatrix[i][j].setObject(Cell.OBJECTS.VOID);
                }
            }
        }
    }

    public void eat (){
        int total_food = 0;
        for( int i = 0 ; i < Driver.neWorld.size; i ++){
			for (int j = 0 ; j < Driver.neWorld.size; j ++){
                Cell currCell = Driver.neWorld.worldMatrix[i][j];
                if (currCell.row >= this.player.row - (int)this.player_size / 2.5
                    && currCell.row <= this.player.row + (int)this.player_size / 2.5
                    && currCell.column >= this.player.column - (int)this.player_size / 2.5 
                    && currCell.column <= this.player.column + (int)this.player_size / 2.5
                    && currCell.getObject() == Cell.OBJECTS.WILDLIFEALIVE){
                        currCell.setObject(Cell.OBJECTS.WILDLIFEDEAD);
                        total_food ++;
                    }
            }
        }
        for( int i = 0 ; i < Driver.neWorld.size; i ++){
			for (int j = 0 ; j < Driver.neWorld.size; j ++){
                Cell currCell = Driver.neWorld.worldMatrix[i][j];
                if (currCell.row >= this.player.row - (int)this.player_size / 2.5
                    && currCell.row <= this.player.row + (int)this.player_size / 2.5
                    && currCell.column >= this.player.column - (int)this.player_size / 2.5 
                    && currCell.column <= this.player.column + (int)this.player_size / 2.5
                    && currCell.getState() == Cell.STATES.TREE){
                        Bomb.placeBomb(i, j);
                        //total_food ++;
                    }
            }
        }
        if (total_food > 0) this.time_without_eating = 0;
        if (this.player_size < Driver.size / 2.5)
            this.player_size += total_food * .1;
    }

    public void burnVictim () {
        int total_burns = 0;
        for( int i = 0 ; i < Driver.neWorld.size; i ++){
			for (int j = 0 ; j < Driver.neWorld.size; j ++){
                Cell currCell = Driver.neWorld.worldMatrix[i][j];
                if (currCell.row >= this.player.row - (int)this.player_size / 2.5
                    && currCell.row <= this.player.row + (int)this.player_size / 2.5
                    && currCell.column >= this.player.column - (int)this.player_size / 2.5 
                    && currCell.column <= this.player.column + (int)this.player_size / 2.5
                    && currCell.getState() == Cell.STATES.BURNING){
                        total_burns ++;
                        this.burning = true;
                    }
            }
        }
        if (this.player_size > 10)
            this.player_size -= total_burns * .2;
        if (total_burns == 0) this.burning = false;
    }

    public void fireFighter() {
        for( int i = 0 ; i < Driver.neWorld.size; i ++){
            for (int j = 0 ; j < Driver.neWorld.size; j ++){
                Cell currCell = Driver.neWorld.worldMatrix[i][j];
                if (currCell.row >= this.player.row - (int)this.player_size / 2.5
                    && currCell.row <= this.player.row + (int)this.player_size / 2.5
                    && currCell.column >= this.player.column - (int)this.player_size / 2.5 
                    && currCell.column <= this.player.column + (int)this.player_size / 2.5
                    && currCell.getState() == Cell.STATES.BURNING){
                        currCell.setState(Cell.STATES.TREE);
                    }
            }
        }
    }


}




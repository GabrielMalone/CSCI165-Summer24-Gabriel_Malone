
import java.util.Random;

public class AlienFireCube extends Wildlife{
    
    public Cell player;
    public boolean player_set;
    public int player_size;
    public boolean burning;
    public boolean attacked;
    public int time_without_being_attacked;
    private int move_distance;
    private int rain_effect;
    public boolean laserShow;
    
    
    Random rand = new Random();
    
    AlienFireCube () {
        this.player_set = false;
        this.player_size = 20;
        this.burning = false;
        this.time_without_being_attacked = 0;
        this.move_distance = 2;
        this.rain_effect = 1;
        this.laserShow = false;
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
     
    public void fireUp() {
        // fire up
        int rand_col_up = rand.nextInt(this.player.column - (int)(this.player_size / 4), this.player.column + (int)(this.player_size / 4));
        for (int j = (int)(this.player.row - (this.player_size/2.5)) ; j > 0 ; j --){
            Cell currentCell = Driver.neWorld.worldMatrix[j][rand_col_up];
            Bomb.placeBomb(currentCell.row, currentCell.column);
            
        }
    }
    public void fireRight() {
        // fire right
        Random rand = new Random();
        int rand_row_right = rand.nextInt(this.player.row - (int)(this.player_size / 4), this.player.row + (int)(this.player_size / 4));
            for (int j = (int)(this.player.column + (this.player_size/2.5)) ; j < Driver.size ; j ++){
                Cell currentCell = Driver.neWorld.worldMatrix[rand_row_right][j];
                Bomb.placeBomb(currentCell.row, currentCell.column);
                
            }
    }
    public void fireDown() {
        // fire down
        int rand_col_down = rand.nextInt(this.player.column - (int)(this.player_size / 4), this.player.column + (int)(this.player_size / 4));
        for (int j = (int)(this.player.row - (this.player_size/2.5)) ; j < Driver.size ; j ++){
            Cell currentCell = Driver.neWorld.worldMatrix[j][rand_col_down];
            Bomb.placeBomb(currentCell.row, currentCell.column);
            
        }
    }
    public void fireLeft() {
        // fire left
        int rand_row_left = rand.nextInt(this.player.row - (int)(this.player_size / 4), this.player.row + (int)(this.player_size / 4));
        for (int j = (int)(this.player.column - (this.player_size/2.5)) ; j > 0 ; j --){
            Cell currentCell = Driver.neWorld.worldMatrix[rand_row_left][j];
            Bomb.placeBomb(currentCell.row, currentCell.column);
           
        }
    }


    public void regenerate (){
        // grow based on how many animals killed each step
        // total dead at one step
        if (Driver.neWorld.totalDead() > 0){
                this.player_size +=1;
            }
        }    
    

    public void camoFireSheild (){
        for( int i = 0 ; i < Driver.neWorld.size; i ++){
			for (int j = 0 ; j < Driver.neWorld.size; j ++){
                Cell currCell = Driver.neWorld.worldMatrix[i][j];
                if (currCell.row >= this.player.row - (int)this.player_size / 2.5
                    && currCell.row <= this.player.row + (int)this.player_size / 2.5
                    && currCell.column >= this.player.column - (int)this.player_size / 2.5 
                    && currCell.column <= this.player.column + (int)this.player_size / 2.5
                    && currCell.getState() == Cell.STATES.TREE){
                        Bomb.placeBomb(i, j);
                    }
            }
        }
    }

    public void animalVictim () {
        int total_attacks = 0;
        for( int i = 0 ; i < Driver.neWorld.size; i ++){
			for (int j = 0 ; j < Driver.neWorld.size; j ++){
                Cell currCell = Driver.neWorld.worldMatrix[i][j];
                if (currCell.row >= this.player.row - (int)this.player_size / 2
                    && currCell.row <= this.player.row + (int)this.player_size / 2
                    && currCell.column >= this.player.column - (int)this.player_size / 2
                    && currCell.column <= this.player.column + (int)this.player_size / 2
                    && currCell.getObject() == Cell.OBJECTS.WILDLIFEALIVE){
                        total_attacks ++;
                        this.attacked = true;
                    }
            }
        }
        int potential_size = this.player_size -= (total_attacks * .2);
        if (potential_size < 10)
            this.player_size = 10;
        else 
            this.player_size -= total_attacks * .02;
        if (total_attacks == 0) this.attacked = false;
    }

    public void fireProofed() {
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




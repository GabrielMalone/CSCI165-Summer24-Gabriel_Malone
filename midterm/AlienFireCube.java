// Gabriel Malone / CS165 / Midterm / Summer 2024


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
        this.player_size = 40;
        this.burning = false;
        this.time_without_being_attacked = 0;
        this.move_distance = 2;
        this.rain_effect = 1;
        this.laserShow = false;
    }
    /**
     * Method to move a cube up with the keyboard
     */
    public void movePlayerUp(){
        // if rain on , movement slowed
        if (Driver.rainOn) this.move_distance /= this.rain_effect;
        // check to prevent user from exiting map
        if (this.player.row - this.move_distance >= (int)( this.player_size / 4 )) {
            // if legit move, make current spot void of player object
            Driver.neWorld.worldMatrix[this.player.row][this.player.column].setObject(Cell.OBJECTS.VOID);
            // move player to desired spot by updating matrix
            Driver.neWorld.worldMatrix[this.player.row - this.move_distance][this.player.column].setObject(Cell.OBJECTS.PLAYER);
            // give the player cell the new coordinate info
            this.player = Driver.neWorld.worldMatrix[this.player.row - this.move_distance][this.player.column];
            if (Driver.rainOn) this.move_distance *= this.rain_effect;
        }
    }
    /**
     * Method to move a cube to the right with the keyboard
     */
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
    /**
     * Method to move a player left with the keyboard
     */
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
    /**
     * Method to move a player down with the keyboard
     */
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
    /**
     * Method to fire lasers from the top of the cube with keyboard input
     */
    public void fireUp() {
        // fire up from a random row on the top of the player's cube
        int rand_col_up = rand.nextInt(this.player.column - (int)(this.player_size / 4), this.player.column + (int)(this.player_size / 4));
        // fire from top of cube to top edge of map (row 0)
        for (int j = (int)(this.player.row - (this.player_size/4)) ; j > 0 ; j --){
            Cell currentCell = Driver.neWorld.worldMatrix[j][rand_col_up];
            Bomb.placeBomb(currentCell.row, currentCell.column);
            
        }
    }
    public void fireRight() {
        // fire right
        Random rand = new Random();
        int rand_row_right = rand.nextInt(this.player.row - (int)(this.player_size / 4), this.player.row + (int)(this.player_size / 4));
            for (int j = (int)(this.player.column + (this.player_size/4)) ; j < Driver.size ; j ++){
                Cell currentCell = Driver.neWorld.worldMatrix[rand_row_right][j];
                Bomb.placeBomb(currentCell.row, currentCell.column);
                
            }
    }
    public void fireDown() {
        // fire down
        int rand_col_down = rand.nextInt(this.player.column - (int)(this.player_size / 4), this.player.column + (int)(this.player_size / 4));
        for (int j = (int)(this.player.row - (this.player_size/4)) ; j < Driver.size ; j ++){
            Cell currentCell = Driver.neWorld.worldMatrix[j][rand_col_down];
            Bomb.placeBomb(currentCell.row, currentCell.column);
            
        }
    }
    public void fireLeft() {
        // fire left
        int rand_row_left = rand.nextInt(this.player.row - (int)(this.player_size / 4), this.player.row + (int)(this.player_size / 4));
        for (int j = (int)(this.player.column - (this.player_size/4)) ; j > 0 ; j --){
            Cell currentCell = Driver.neWorld.worldMatrix[rand_row_left][j];
            Bomb.placeBomb(currentCell.row, currentCell.column);
           
        }
    }

    public void regenerate (){
        // grow based on how many animals killed each step
        // total dead at one step
        if (Driver.neWorld.totalDead() > 0 && this.player_size < 60){
                this.player_size +=1;
            }
        }    
    
    /**
     * Method for the cube to place bombs around its border
     */
    public void camoFireSheild (){
        for( int i = 0 ; i < Driver.neWorld.size; i ++){
			for (int j = 0 ; j < Driver.neWorld.size; j ++){
                Cell currCell = Driver.neWorld.worldMatrix[i][j];
                if (currCell.row >= this.player.row - (int)this.player_size / 2.5
                    && currCell.row <= this.player.row + (int)this.player_size / 2.5
                    && currCell.column >= this.player.column - (int)this.player_size / 2.5 
                    && currCell.column <= this.player.column + (int)this.player_size / 2.5
                    && currCell.getState() == Cell.STATES.TREE){
                        Bomb.placeBomb(currCell.row, currCell.column);
                    }
            }
        }
    }
    /**
     * Method to shrink a cube when wildlife enters an area of the cube's border
     */
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
            this.player_size -= total_attacks * .0002;
        if (total_attacks == 0) this.attacked = false;
    }
    /**
     * Method to prevent the area within the cube from catching on fire
     * Also creates the camo effect
     */
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

    /**
     * Method to have a ring of fire protect the cube
     */
    public void forcefield(){
        int alien_location_row = (int)(Driver.alien.player.row);
        int alien_location_col = (int)(Driver.alien.player.column); 
        for( int i = 1 ; i < Driver.neWorld.size - 1; i ++){
            for (int j = 1 ; j < Driver.neWorld.size -1 ; j ++){
                Cell currentCell = Driver.neWorld.worldMatrix[i][j];
                double radius = Math.sqrt(Math.pow((Driver.alien.player_size), 2) * 1);
                // equation for a cirlce -- if within the radius surrounding the cube then attack
                if  ( Math.sqrt((Math.pow(alien_location_row - currentCell.row , 2) + Math.pow(alien_location_col - currentCell.column, 2)))  < (radius + 1)
                && Math.sqrt((Math.pow(alien_location_row - currentCell.row , 2) + Math.pow(alien_location_col - currentCell.column, 2)))  > (radius - 1)
                ){  
                    Cell [] neighbors = Driver.neWorld.findNeighbors(currentCell.row, currentCell.column);
                    Driver.neWorld.seeWhatBurns(neighbors, currentCell);
                }
                if  ( Math.sqrt((Math.pow(alien_location_row - currentCell.row , 2) + Math.pow(alien_location_col - currentCell.column, 2)))  < (radius + 15)
                && Math.sqrt((Math.pow(alien_location_row - currentCell.row , 2) + Math.pow(alien_location_col - currentCell.column, 2)))  > (radius + 14)
                ){  
                    //if (clearMoveChoice(currentCell))
                       // currentCell.setObject(Cell.OBJECTS.WILDLIFEALIVE);
                }
            }
        }
    }
    /**
     * Method to have a ring of fire protect the cube
     */
    public void animalAttractor(){
        getTheAnimals();
        int alien_location_row = (int)(Driver.alien.player.row);
        int alien_location_col = (int)(Driver.alien.player.column); 
        while ( this.alltheanimals.size() > 0 ) {
            // loop through the array at random spots
            int rand_index_a = this.rand.nextInt(0,alltheanimals.size());
            Cell currentCell = alltheanimals.get(rand_index_a);
            this.alltheanimals.remove(currentCell);
            double radius = Math.sqrt(Math.pow((Driver.alien.player_size), 2));
            // equation for a cirlce -- if within the radius surrounding the cube then attack
            if  ( Math.sqrt((Math.pow(alien_location_row - currentCell.row , 2) + Math.pow(alien_location_col - currentCell.column, 2)))  >= (radius)
            ){  
                if (currentCell.row <= alien_location_row){
                    if (clearMoveChoiceB(Driver.neWorld.worldMatrix[currentCell.row + 1][currentCell.column])){
                        currentCell.setObject(Cell.OBJECTS.VOID);
                        Driver.neWorld.worldMatrix[currentCell.row + 1][currentCell.column].setObject(Cell.OBJECTS.WILDLIFEALIVE);
                    }
                }
                if (currentCell.row >= alien_location_row){
                    if (clearMoveChoiceB(Driver.neWorld.worldMatrix[currentCell.row - 1][currentCell.column])){
                        currentCell.setObject(Cell.OBJECTS.VOID);
                        Driver.neWorld.worldMatrix[currentCell.row - 1][currentCell.column].setObject(Cell.OBJECTS.WILDLIFEALIVE);
                    }
                }
                if (currentCell.column >= alien_location_col){
                    if (clearMoveChoiceB(Driver.neWorld.worldMatrix[currentCell.row][currentCell.column - 1])){
                        currentCell.setObject(Cell.OBJECTS.VOID);
                        Driver.neWorld.worldMatrix[currentCell.row][currentCell.column - 1].setObject(Cell.OBJECTS.WILDLIFEALIVE);
                    }
                }
                if (currentCell.column <= alien_location_col){
                    if (clearMoveChoiceB(Driver.neWorld.worldMatrix[currentCell.row][currentCell.column + 1])){
                        currentCell.setObject(Cell.OBJECTS.VOID);
                        Driver.neWorld.worldMatrix[currentCell.row][currentCell.column + 1].setObject(Cell.OBJECTS.WILDLIFEALIVE);
                    }
                }
            }
        }
    }
        
    
    public void cubeTeleport(){
        for( int i = 0 ; i < Driver.neWorld.size; i ++){
            for (int j = 0 ; j < Driver.neWorld.size; j ++){
                Cell currentCell = Driver.neWorld.worldMatrix[i][j];
                if (currentCell.getObject() == Cell.OBJECTS.PLAYER){
                      currentCell.setObject(Cell.OBJECTS.BOMB);
                      break;
                }   
            }
        }
    }

    public void removeCube(){
        for( int i = 0 ; i < Driver.neWorld.size; i ++){
            for (int j = 0 ; j < Driver.neWorld.size; j ++){
                Cell currentCell = Driver.neWorld.worldMatrix[i][j];
                if (currentCell.getObject() == Cell.OBJECTS.PLAYER){
                      currentCell.setObject(Cell.OBJECTS.VOID);
                }   
            }
        }
    }


}




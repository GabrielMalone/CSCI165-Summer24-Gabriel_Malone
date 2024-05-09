

public class Terminal_Graphics {
    
    // text // graphic colors
	public static final String ANSI_RESET           = "\u001B[0m"; 
	public static final String ANSI_GREEN          	= "\u001B[42m";
	public static final String ANSI_YELLOW          = "\u001B[43m";
	public static final String ANSI_RED				= "\u001B[41m";
    // create blank Color matrix
	public static int [][] rgbWorld = new int[World.size][World.size*3];

	/**
	 * Method to fill in a matrix with color values (given by each Cell object)
	 */
	public void rgbWorld(){
		for(int i = 0; i < World.worldMatrix.length; i++){ 
			// inner loop processes the number of "columns"
			int r = 0, g = 1, b = 2;
			for(int j = 0; j < World.worldMatrix[i].length; j++){
				// split at the dash
				String[] rgbArray = World.worldMatrix[i][j].cellColor.split("-");
				rgbWorld[i][r] = Integer.parseInt(rgbArray[0]); // index 0 is "red"	
				rgbWorld[i][g] = Integer.parseInt(rgbArray[1]); // index 1 is "green"
				rgbWorld[i][b] = Integer.parseInt(rgbArray[2]); // index 2 is "blue"
				r += 3;
				g += 3;
				b += 3;
			} 
		} 
	}
	/**
	 * Method to test results of the matrix creators
	 */
	public void displayWorld(){
        clearSequence();
		for (int i = 0 ; i < World.worldMatrix.length ; i++){
			System.out.println();
			for (int j = 0 ; j < World.worldMatrix[i].length; j++){
				Cell currentCell = World.worldMatrix[i][j];
				String color = currentCell.cellColor;
				if (color.equals(Cell.YELLOW)) 	System.out.print(ANSI_YELLOW + "YEL" + ANSI_RESET);
				if (color.equals(Cell.GREEN)) 	System.out.print(ANSI_GREEN + "GRE" + ANSI_RESET);
				if (color.equals(Cell.RED))   	System.out.print(ANSI_RED + "RED" + ANSI_RESET);
				}
			}
		}
    
  
    private static void clearSequence(){
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }
}
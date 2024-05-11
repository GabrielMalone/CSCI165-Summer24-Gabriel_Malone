
public class Terminal_Graphics {
    
	// RBG Colors
    public static String YELLOW = "255-255-051";
	public static String GREEN  = "000-204-000";
	public static String RED    = "255-000-000";
	public static String PINK   = "255-153-255";
	
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

			// number markers on grid
			/* 
			if (i > 0 && i < World.worldMatrix.length-1)
				System.out.printf("%s%3d%s", ANSI_YELLOW, i, ANSI_RESET); 
			*/
			
			for (int j = 0 ; j < World.worldMatrix[i].length; j++){
				
				Cell cell = World.worldMatrix[i][j];
				String color = cell.cellColor;
			
				if (cell.getCellWeather() == Cell.WEATHER.WINDY && World.todaysWeather.windDirection.equals("EAST")){
					// conditional for animation
					if (World.timeStep % 2 == 0){
					if (color.equals(YELLOW)) 		System.out.print(ANSI_YELLOW + "-->");
					else if (color.equals(GREEN)) 	System.out.print(ANSI_GREEN  + "-->");
					else if (color.equals(RED))   	System.out.print(ANSI_RED	 + "-->");
					System.out.print(ANSI_RESET);
					}// conditional for animation
					else if (World.timeStep % 3 == 0){
						if (color.equals(YELLOW)) 		System.out.print(ANSI_YELLOW + "->-");
						else if (color.equals(GREEN)) 	System.out.print(ANSI_GREEN  + "->-");
						else if (color.equals(RED))   	System.out.print(ANSI_RED	 + "->-");
						System.out.print(ANSI_RESET);
						}
					else{// conditional for animation
						if (color.equals(YELLOW)) 		System.out.print(ANSI_YELLOW + ">--");
						else if (color.equals(GREEN)) 	System.out.print(ANSI_GREEN  + ">--");
						else if (color.equals(RED))   	System.out.print(ANSI_RED	 + ">--");
						System.out.print(ANSI_RESET);
					}
				}
				else
					if (color.equals(YELLOW)) 		System.out.print(ANSI_YELLOW + "   ");
					else if (color.equals(GREEN)) 	System.out.print(ANSI_GREEN  + "   ");
					else if (color.equals(RED))   	System.out.print(ANSI_RED	 + "   ");
					System.out.print(ANSI_RESET);
				}
			}
		}
	
    public static void clearSequence(){
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }
}
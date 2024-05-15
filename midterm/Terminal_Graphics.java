
public class Terminal_Graphics {
    
	// RBG Colors
    public static String YELLOW = "255-255-051";
	public static String GREEN  = "000-204-000";
	public static String RED    = "255-000-000";
	public static String PINK   = "255-153-255";
	
	// text // graphic colors
	public static final String ANSI_RESET           = "\u001B[0m"; 
	public static final String ANSI_GREEN          	= "\u001B[42m";
	public static final String ANSI_green          	= "\u001B[32m";
	public static final String ANSI_YELLOW          = "\u001B[43m";
	public static final String ANSI_yellow          = "\u001B[33m";
	public static final String ANSI_RED				= "\u001B[41m";
	public static final String ANSI_red				= "\u001B[31m";
	public static final String ANSI_PINK			= "\u001B[40m";
    // create blank Color matrix
	public static int [][] rgbWorld2 = new int[World.size][World.size];

	/**
	 * Method to fill in a matrix with color values (given by each Cell object)
	 */
	public static void rgbWorld(){
		for(int i = 0; i < World.worldMatrix.length; i++){ 
			// inner loop processes the number of "columns"
			int r = 0, g = 1, b = 2;
			for(int j = 0; j < World.worldMatrix[i].length/3; j++){
				// split at the dash
				String[] rgbArray = World.worldMatrix[i][j].cellColor.split("-");
				rgbWorld2[i][r] = Integer.parseInt(rgbArray[0]); // index 0 is "red"	
				rgbWorld2[i][g] = Integer.parseInt(rgbArray[1]); // index 1 is "green"
				rgbWorld2[i][b] = Integer.parseInt(rgbArray[2]); // index 2 is "blue"
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
        
		String arrow_step1_east = "-->";
		String arrow_step2_east = "->-";
		String arrow_step3_east = ">--";
		String arrow_step1_west = "<--";
		String arrow_step2_west = "-<-";
		String arrow_step3_west = "--<";
		

		clearSequence();
		
		for (int i = 0 ; i < World.worldMatrix.length ; i++){
			System.out.println();

			for (int j = 0 ; j < World.worldMatrix[i].length; j++){
				
				Cell cell = World.worldMatrix[i][j];
				String color = cell.cellColor;
			
				if (cell.getCellWeather() == Cell.WEATHER.WINDY && Driver.todaysWeather.windDirection.equals("EAST") && cell.getObject() == Cell.OBJECTS.WILDLIFEALIVE){
					// conditional for animation
					if (World.timeStep % 2 == 0){
					if (color.equals(YELLOW)) 				System.out.print(ANSI_YELLOW + ">-.");
					else if (color.equals(GREEN)) 			System.out.print(ANSI_GREEN  + ">-.");
					else if (color.equals(RED))   			System.out.print(ANSI_RED	 + ">-.");
					else if (color.equals(PINK))   			System.out.print(ANSI_PINK	 + ">-.");
					
					}// conditional for animation
					else if (World.timeStep % 3 == 0){
						if (color.equals(YELLOW)) 			System.out.print(ANSI_YELLOW + "->.");
						else if (color.equals(GREEN)) 		System.out.print(ANSI_GREEN  + "->.");
						else if (color.equals(RED))   		System.out.print(ANSI_RED	 + "->.");
						else if (color.equals(PINK))   		System.out.print(ANSI_PINK	 + "->.");
						
					}
					else{// conditional for animation
						if (color.equals(YELLOW)) 			System.out.print(ANSI_YELLOW + ">-.");
						else if (color.equals(GREEN)) 		System.out.print(ANSI_GREEN  + ">-.");
						else if (color.equals(RED))   		System.out.print(ANSI_RED	 + ">-.");
						else if (color.equals(PINK))   		System.out.print(ANSI_PINK	 + ">-.");
						
					}
				}
				else if (cell.getCellWeather() == Cell.WEATHER.WINDY && Driver.todaysWeather.windDirection.equals("EAST") && cell.getObject() == Cell.OBJECTS.WILDLIFEDEAD){
					// conditional for animation
					if (World.timeStep % 2 == 0){
						if (color.equals(YELLOW)) 				System.out.print(ANSI_YELLOW + ">-x");
						else if (color.equals(GREEN)) 			System.out.print(ANSI_GREEN  + ">-x");
						else if (color.equals(RED))   			System.out.print(ANSI_RED	 + ">-x");
						else if (color.equals(PINK))   			System.out.print(ANSI_PINK	 + ">-x");
						
						}// conditional for animation
						else if (World.timeStep % 3 == 0){
							if (color.equals(YELLOW)) 			System.out.print(ANSI_YELLOW + "->x");
							else if (color.equals(GREEN)) 		System.out.print(ANSI_GREEN  + "->x");
							else if (color.equals(RED))   		System.out.print(ANSI_RED	 + "->x");
							else if (color.equals(PINK))   		System.out.print(ANSI_PINK	 + "->x");
							
						}
						else{// conditional for animation
							if (color.equals(YELLOW)) 			System.out.print(ANSI_YELLOW + ">-x");
							else if (color.equals(GREEN)) 		System.out.print(ANSI_GREEN  + ">-x");
							else if (color.equals(RED))   		System.out.print(ANSI_RED	 + ">-x");
							else if (color.equals(PINK))   		System.out.print(ANSI_PINK	 + ">-x");
							
						}
				}
				else if (cell.getCellWeather() == Cell.WEATHER.WINDY && Driver.todaysWeather.windDirection.equals("EAST")){
					// conditional for animation
					if (World.timeStep % 2 == 0){
					if (color.equals(YELLOW)) 				System.out.print(ANSI_YELLOW + ANSI_yellow + arrow_step1_east);
					else if (color.equals(GREEN)) 			System.out.print(ANSI_GREEN  + ANSI_green + arrow_step1_east);
					else if (color.equals(RED))   			System.out.print(ANSI_RED	 + ANSI_red + arrow_step1_east);
					else if (color.equals(PINK))   			System.out.print(ANSI_PINK	 + ANSI_red + arrow_step1_east);
					
					}// conditional for animation
					else if (World.timeStep % 3 == 0){
						if (color.equals(YELLOW)) 			System.out.print(ANSI_YELLOW + ANSI_yellow +arrow_step2_east);
						else if (color.equals(GREEN)) 		System.out.print(ANSI_GREEN  + ANSI_green + arrow_step2_east);
						else if (color.equals(RED))   		System.out.print(ANSI_RED	 + ANSI_red + arrow_step2_east);
						else if (color.equals(PINK))   		System.out.print(ANSI_PINK	 + ANSI_red + arrow_step2_east);
						
					}
					else{// conditional for animation
						if (color.equals(YELLOW)) 			System.out.print(ANSI_YELLOW + ANSI_yellow +arrow_step3_east);
						else if (color.equals(GREEN)) 		System.out.print(ANSI_GREEN  + ANSI_green + arrow_step3_east);
						else if (color.equals(RED))   		System.out.print(ANSI_RED	 + ANSI_red + arrow_step3_east);
						else if (color.equals(PINK))   		System.out.print(ANSI_PINK	 + ANSI_red + arrow_step3_east);
						
					}
				}
				else if (cell.getCellWeather() == Cell.WEATHER.WINDY && Driver.todaysWeather.windDirection.equals("WEST")){
						// conditional for animation
						if (World.timeStep % 2 == 0){
						if (color.equals(YELLOW)) 			System.out.print(ANSI_YELLOW + arrow_step1_west);
						else if (color.equals(GREEN)) 		System.out.print(ANSI_GREEN  + arrow_step1_west);
						else if (color.equals(RED))   		System.out.print(ANSI_RED	 + arrow_step1_west);
						
						}// conditional for animation
						else if (World.timeStep % 3 == 0){
							if (color.equals(YELLOW)) 		System.out.print(ANSI_YELLOW + arrow_step2_west);
							else if (color.equals(GREEN)) 	System.out.print(ANSI_GREEN  + arrow_step2_west);
							else if (color.equals(RED))   	System.out.print(ANSI_RED	 + arrow_step2_west);
							
						}
						else{// conditional for animation
							if (color.equals(YELLOW)) 		System.out.print(ANSI_YELLOW + arrow_step3_west);
							else if (color.equals(GREEN)) 	System.out.print(ANSI_GREEN  + arrow_step3_west);
							else if (color.equals(RED))   	System.out.print(ANSI_RED	 + arrow_step3_west);
							
						}
				}
				else if(cell.getCellWeather() == Cell.WEATHER.WINDY && Driver.todaysWeather.windDirection.equals("SOUTH")){
					// conditional for animation
					if (World.timeStep % 2 == 0){
						if (color.equals(YELLOW)) 			System.out.print(ANSI_YELLOW + "<--");
						else if (color.equals(GREEN)) 		System.out.print(ANSI_GREEN  + "<--");
						else if (color.equals(RED))   		System.out.print(ANSI_RED	 + "<--");
						
						}// conditional for animation
						else if (World.timeStep % 3 == 0){
							if (color.equals(YELLOW)) 		System.out.print(ANSI_YELLOW + "-<-");
							else if (color.equals(GREEN)) 	System.out.print(ANSI_GREEN  + "-<-");
							else if (color.equals(RED))   	System.out.print(ANSI_RED	 + "-<-");
							
						}
						else{// conditional for animation
							if (color.equals(YELLOW)) 		System.out.print(ANSI_YELLOW + "--<");
							else if (color.equals(GREEN)) 	System.out.print(ANSI_GREEN  + "--<");
							else if (color.equals(RED))   	System.out.print(ANSI_RED	 + "--<");
							
						}
				}
				else if(cell.getCellWeather() == Cell.WEATHER.WINDY && Driver.todaysWeather.windDirection.equals("NORTH")){
					// conditional for animation
					if (World.timeStep % 2 == 0){
						if (color.equals(YELLOW)) 			System.out.print(ANSI_YELLOW + "<--");
						else if (color.equals(GREEN)) 		System.out.print(ANSI_GREEN  + "<--");
						else if (color.equals(RED))   		System.out.print(ANSI_RED	 + "<--");
						
						}// conditional for animation
						else if (World.timeStep % 3 == 0){
							if (color.equals(YELLOW)) 		System.out.print(ANSI_YELLOW + "-<-");
							else if (color.equals(GREEN)) 	System.out.print(ANSI_GREEN  + "-<-");
							else if (color.equals(RED))   	System.out.print(ANSI_RED	 + "-<-");
							
						}
						else{// conditional for animation
							if (color.equals(YELLOW)) 		System.out.print(ANSI_YELLOW + "--<");
							else if (color.equals(GREEN)) 	System.out.print(ANSI_GREEN  + "--<");
							else if (color.equals(RED))   	System.out.print(ANSI_RED	 + "--<");
							
						}
				}
				else if (cell.getObject().equals(Cell.OBJECTS.WILDLIFEALIVE)){
					if (color.equals(YELLOW)) 				System.out.print(ANSI_YELLOW + " . ");
					else if (color.equals(GREEN)) 			System.out.print(ANSI_GREEN  + " . ");
					else if (color.equals(RED))   			System.out.print(ANSI_RED	 + " . ");
				}		
				else if (cell.getObject().equals(Cell.OBJECTS.WILDLIFEDEAD)){
					if (color.equals(YELLOW)) 				System.out.print(ANSI_YELLOW + " x ");
					else if (color.equals(GREEN)) 			System.out.print(ANSI_GREEN  + " x ");
					else if (color.equals(RED))   			System.out.print(ANSI_RED	 + " x ");
				}	
				else
					if (color.equals(YELLOW)) 				System.out.print(ANSI_YELLOW + "   ");
					else if (color.equals(GREEN)) 			System.out.print(ANSI_GREEN  + "   ");
					else if (color.equals(RED))   			System.out.print(ANSI_RED	 + "   ");
					else if (color.equals(PINK))   			System.out.print(ANSI_PINK	 + "   ");
					System.out.print(ANSI_RESET);
				}
			}
		}
	
    public static void clearSequence(){
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }
}
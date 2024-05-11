
import java.util.ArrayList;
import java.util.Random;



public class Weather {


	public static ArrayList<String> windCoordinates = new ArrayList<>();
    public static enum DIRECTION {
		NORTH, 
		EAST, 
		SOUTH,
		WEST;
	}
    private DIRECTION direction;
    String windDirection;

	/**
	 * Method to create appropriate random variables for the sinewave
	 * @param range
	 * @return
	 */
	public static double doubleProb(double range){
		Random rand = new Random();
		double doubleResult = rand.nextDouble(range);
		return doubleResult;
	}

	/**
	 * Method to create a sinewave and overlay it onto the world matrix
	 * 
	 */
	public void pattern(){
		// will be able to randomize these values in a range
		// modified sinewave output from stackoverflow
		
		double midlinea  	=   World.worldMatrix.length / 8;	//doubleProb(World.worldMatrix.length/3);
		double midlineb  	=   World.worldMatrix.length / 5 * -1;	//doubleProb(World.worldMatr ix.length/3) * -1;
		double amplitude 	=   .5;	//doubleProb(1.0);
		double frequency 	=   .5;	//doubleProb(1.0);
		double bottomwidth	=   World.worldMatrix.length / 11;
		double topwidth		=   World.worldMatrix.length / 11;
		double phaseShift	=   3;
		int period 			=   World.worldMatrix.length - 1;
		// create the wave
		int a =0;  // index positions for map
		int b = 0; // index positions for map

		for (double y = midlinea; y > midlineb; y-= amplitude) {
			a += 1;
			b  = 0;
			for (double x = phaseShift; x <= period; x+=frequency) {
				double sin = Math.sin(x);
				if ((bottomwidth+y) >= sin && (y-topwidth) <= sin){
				// if cell in path of sinewayve:
				if (b < World.worldMatrix.length-1 && b > 0){
					// update cells in worldmap to be windy
					String coordinate = a + "," + b;
					windCoordinates.add(coordinate);
				} 
				// checking purposes
				//System.out.print("*");
	
				}
				else
					// why does this need to stay for the world map to graph properly??
					System.out.print(" ");
					b+=1;
		  	}
			// checking purposes
		  System.out.println();

		}
	}

	/**
	 * Method to set the cell in the coordinates within the path of the sinewave as windy
	 * 
	 */
	public void setWeatherPattern(){
		for (int i = 0; i < World.worldMatrix.length ; i ++ ){
			for (int j = 0; j < World.worldMatrix.length ; j ++ ){
				if (windCoordinates.contains(World.worldMatrix[i][j].coordinates)) World.worldMatrix[i][j].setWeather(Cell.WEATHER.WINDY);
			}
		}
	}

    public void setDirection (DIRECTION direction){
		this.direction = direction;
        switch (this.direction) {
            case NORTH: this.windDirection = "NORTH";
                break;
            case SOUTH: this.windDirection = "SOUTH";
                break;
            case EAST: this.windDirection = "EAST";
                break;
            case WEST: this.windDirection = "WEST";
                break;
	    }
    }

    public DIRECTION getDirection(){
		return this.direction;
	}
}

   

    


import java.util.ArrayList;
import java.util.Random;

public class Weather {

	private static int direction;
	public static ArrayList<String> windCoordinates = new ArrayList<>();

	public static int direcProb(){
		Random rand = new Random();
		// random value from  0.0 - 1.0
		double direcProb = rand.nextDouble(.8);
		if (direcProb <= .10)       direction = 1;
		else if (direcProb <= .20)  direction = 2;
		else if (direcProb <= .30)  direction = 3;
		else if (direcProb <= .40)  direction = 4;
		else if (direcProb <= .50)  direction = 5;
		else if (direcProb <= .60)  direction = 6;
		else if (direcProb <= .70)  direction = 7;
		else if (direcProb <= .80)  direction = 8;
		return direction;
	}  
	public static void sinewave(){
		// will be able to randomize these values in a range
		// modified sinewave output from stackoverflow
		double midlinea  	=   3;
		double midlineb  	=  -9;
		double amplitude 	= 0.2;
		double frequency 	=  .5;
		double bottomwidth	= 0.9;
		double topwidth		= 0.9;
		double phaseShift	= 	0;
		int period 			= World.worldMatrix.length;
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
		  //System.out.println();

		}
	}

	public static void setSineWave(){
		for (int i = 0; i < World.worldMatrix.length ; i ++ ){
			// colums
			for (int j = 0; j < World.worldMatrix.length ; j ++ ){
				if (windCoordinates.contains(World.worldMatrix[i][j].coordinates)) World.worldMatrix[i][j].setWeather(Cell.WEATHER.WINDY);
			}
		}
	}

	public static void windCheck(Cell currentCell){
		for (String coord : windCoordinates){
			if (currentCell.coordinates.equals(coord)) currentCell.setWeather(Cell.WEATHER.WINDY);
			else currentCell.setWeather(Cell.WEATHER.CALM);
		}
	}

}


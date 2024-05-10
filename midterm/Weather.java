
import java.util.ArrayList;
import java.util.Random;

public class Weather {


	public static ArrayList<String> windCoordinates = new ArrayList<>();

	public static double doubleProb(double range){
		Random rand = new Random();
		double doubleResult = rand.nextDouble(range);
		return doubleResult;
	}

	public static void sinewave(){
		// will be able to randomize these values in a range
		// modified sinewave output from stackoverflow
		Terminal_Graphics.clearSequence();
		double midlinea  	=   7;//doubleProb(World.worldMatrix.length/3);
		double midlineb  	=  -15;//doubleProb(World.worldMatrix.length/3) * -1;
		double amplitude 	=  .5;//doubleProb(1.0);
		double frequency 	=  .5;//doubleProb(1.0);
		double bottomwidth	=   3;
		double topwidth		=   3;
		double phaseShift	=   0;
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
		  System.out.println();

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


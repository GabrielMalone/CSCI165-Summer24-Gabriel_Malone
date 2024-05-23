// Gabriel Malone / CS165 / Midterm / Summer 2024

public class Driver {

	// DEFAULTS
	public static boolean displayMode = false;
	public static boolean weatherOn = false;
	public static boolean metricsOn = true;
	public static boolean centerStart;
	public static boolean endlessMode = true;
	public static boolean animalsOn = false;
	public static boolean animalsWander = false;
	public static int size = 200;
	public static int speed = 20;
	public static int startingPop = 0;
	public static double popRegrowth = 0;
	public static int numberOfFires = 6;
	public static double chanceToRegrow =.01;
	public static double catchprobability = .25;
	public static World_Graphics world;
	public static World neWorld;
	public static Menu options;
	public static Weather todaysWeather = new Weather();
	public static boolean start = false;

	public static void main(String[] args) {
		// create world instance
		int size = 6;                                               // pick a size (row and column length)
		size = Driver.worldResize(size);                             // adjust size as necessary (25->25)
		Driver.catchprobability = .25;                                 // over time, 25% of neighbors, on average, should be set on fire
		Driver.size = size;                                          // set size for the matrix (25 * 25)
		int t = 0;
		double [] numFiresArray = new double [100000];
		World worldOne = new World(); 
		while (t < 100000){                          
			worldOne.fillWorld();
			worldOne.setCenterCellonFire();
			worldOne.copyWorldMatrix();
			worldOne.designatetNeighborsOnFire();
			worldOne.clearPreviousFire();  
			double numberOfFires = 0;
			worldOne.applyChangesToWorld();
			Cell [] centerNeighbors = worldOne.findNeighbors(worldOne.centerCell.row, worldOne.centerCell.column);
			for (Cell neighboringCell : centerNeighbors){
				if (neighboringCell.getState() == Cell.STATES.BURNING){
					numberOfFires ++;
				}
			}
			numFiresArray[t] = numberOfFires;
			t ++;
		}
		// find average of ints in the array
		// sum the results
		// divide by length of array
		double sum = 0;
		for (double result : numFiresArray){
			sum += result;
		}
		double average = sum / numFiresArray.length;
		double percent_of_neighbors_burned = (average / 8);
		double upper_range = Driver.catchprobability + .1;
		double lower_range = Driver.catchprobability - .1;
		if (percent_of_neighbors_burned >= lower_range && percent_of_neighbors_burned <= upper_range)
			System.out.println(percent_of_neighbors_burned);
		// main options menu
		// this loads first
		options = new Menu();

	}

	public static int worldResize(int size){
		// resize input sizes to allow for a perfect center
		if (size % 2 == 0) return size += 1;
		return size;
	}
}
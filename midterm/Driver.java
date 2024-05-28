// Gabriel Malone / CS165 / Midterm / Summer 2024

public class Driver {

	// DEFAULTS
	public static boolean rainOn = false;
	public static boolean weatherOn = false;
	public static boolean endlessMode = true;
	public static boolean animalsOn = false;
	public static boolean animalsWander = false;
	public static int size = 200;
	public static int speed = 20;
	public static int startingPop = 0;
	public static int startingRain = 0;
	public static double popRegrowth = 0;
	public static double chanceOfRain = .01;
	public static double rainAdjust =.01;
	public static double chanceToDry = .25;
	public static int numberOfFires = 3;
	public static double chanceToRegrow =.01;
	public static double catchprobability = .25;
	public static World_Graphics world;
	public static World neWorld;
	public static Menu options;
	public static Wind todaysWind = new Wind();
	public static boolean start = false;
	public static AlienFireCube alien;

	public static void main(String[] args) {
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
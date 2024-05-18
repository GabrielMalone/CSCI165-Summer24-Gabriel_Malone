import java.util.Scanner;

public class Driver {
	public static int size;
    public static int speed;
    public static int startingPop;
    public static double chanceToRegrow;
    public static double catchprobability;
    public static int popRegrowth;
    public static int numberOfFires;
    public static Weather todaysWeather = new Weather();
    public static World_Graphics world;
    public static World neWorld;
    public static boolean weatherOn;
    public static boolean metricsOn;
    public static boolean centerStart;
 

 
	public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        size = getMapSize(input);                           // map size
        speed = getMapSpeed(input);                         // step rate ms
        catchprobability = getBurnProb(input);                             // base probability of trees catching on fire
        chanceToRegrow = .01;                               // chance for tree to regrow after burning
        startingPop = size;                                 // starting populatin (map size gives good ratio of space to animals)
        popRegrowth = size;                                 // number of animals to return after fire goes out
        numberOfFires = 5;
        size = worldResize(size);		                    // map size adjustment for center positioning
        neWorld = new World();
        todaysWeather.setDirection(Weather.DIRECTION.EAST); // set weather pattern direction
        world = new World_Graphics();
        neWorld.applySpread();  
    }

    public static int worldResize(int size){
        if (size % 2 == 0) return size += 1;	
        return size;
    }

    public static void startSequence(){
        neWorld.applySpread();
    }

    public static int getMapSize(Scanner input){
        System.out.print("Enter Map Size (20, 50, 100, 150, 200, 500)");
        String size_string = input.nextLine();
        int size = Integer.valueOf(size_string);
        return size;
    }

    public static int getMapSpeed(Scanner input){
        System.out.print("Enter Map Speed (ms)");
        String speed_string = input.nextLine();
        int speed = Integer.valueOf(speed_string);
        return speed;
    }

    public static double getBurnProb(Scanner input){
        System.out.print("Enter burn probability (0-1) (e.g. .5 for 50%)");
        String burn_string = input.nextLine();
        double speed = Double.valueOf(burn_string);
        return speed;
    }

}


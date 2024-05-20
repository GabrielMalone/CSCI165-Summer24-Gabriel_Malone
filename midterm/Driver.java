
import java.util.Scanner;


public class Driver {
    
    // DEFAULTS
    public static boolean displayMode = false;
    public static boolean weatherOn = false;
    public static boolean metricsOn = true;
    public static boolean centerStart;
    public static boolean endlessMode = true;
    public static boolean animalsOn = true;
    public static boolean animalsWander = false;
    public static int size = 400; 
    public static int speed = 20;
    public static int startingPop = size * 10;
    public static int popRegrowth;
    public static int numberOfFires = 6;
    public static double chanceToRegrow =.01;
    public static double catchprobability = .25;
    public static World_Graphics world;
    public static World neWorld;
    public static Menu options;
    public static Weather todaysWeather = new Weather();
    public static boolean start = false;

 
	public static void main(String[] args) {
        initializer();
        options = new Menu();
        //world = new World_Graphics();
        //neWorld.applySpread();

    }
  
    public static void initializer(){
        /* 
        System.out.println("Sim options / 'Enter' to use defaults");
        Scanner input = new Scanner(System.in);
        size = getMapSize(input);  
        speed = getMapSpeed(input);                        
        catchprobability = getBurnProb(input); 
        animalsOn(input);   
        startingPop = animalPop(input);  
        animalsMove(input);                             
        windOn(input);
        if (weatherOn) windDirection(input);                                            
        simMode(input);
        if (endlessMode)
            numberOfFires = numFires(input);
        dataOverlay(input);
        */
        size = worldResize(size);
        popRegrowth = size / 2; 
        if (size == 21)   
            chanceToRegrow = .05;
        neWorld = new World();      
    }
    
    public static int worldResize(int size){
        if (size % 2 == 0) return size += 1;	
        return size;
    }

    public static void startSequence(){
        neWorld.applySpread();
    }

    public static int getMapSize(Scanner input){
        System.out.print("Enter Map Size (20, 50, 100, 150, 200, 400, 500, 1000) (Default 150) ");
        String size_string = input.nextLine();
        if (size_string.equals("")) return size;
        int size = Integer.valueOf(size_string);
        return size;
    }

    public static int getMapSpeed(Scanner input){
        System.out.print("Enter Map Speed (ms) (Default 50ms) ");
        String speed_string = input.nextLine();
        if (speed_string.equals("")) return speed;
        int speed = Integer.valueOf(speed_string);
        return speed;
    }

    public static double getBurnProb(Scanner input){
        System.out.print("Enter burn probability (0-1) (e.g. .5 for 50%) (Default .25) ");
        String burn_string = input.nextLine();
        if (burn_string.equals("")) return catchprobability;
        double catchprobability = Double.valueOf(burn_string);
        return catchprobability;
    }

    public static void animalsOn(Scanner input){
        System.out.print("Animals On - (1) / Off (2) (Default On): ");
        String animals_string = input.nextLine();
        if (animals_string.equals("2"))  animalsOn = false;
    }

    public static void animalsMove(Scanner input){
        System.out.print("Animals Wander Randomly - On (1) / Off (2) (Default Off): ");
        String animals_wander = input.nextLine();
        if (animals_wander.equals("2"))  animalsWander = false;
    }

    public static int animalPop(Scanner input){
        System.out.print("Starting populaion (Default sqrt map area) ");
        String pop_string = input.nextLine();
        if (pop_string.equals("")) return startingPop;
        int startingPop = Integer.valueOf(pop_string);
        return startingPop;
    }

    public static void windOn(Scanner input){
        System.out.print("Wind On - (1) / Off (2) (Default Off): ");
        String burn_string = input.nextLine();
        if (burn_string.equals("1"))  weatherOn = true;
    }

    public static void windDirection(Scanner input){
        System.out.print("Weather Direction (N, E, S, W) (Default East): ");
        String direction = input.nextLine();
        if (direction.equals("")) todaysWeather.setDirection(Weather.DIRECTION.EAST);
        if (weatherOn){
            if (direction.equals("N")) todaysWeather.setDirection(Weather.DIRECTION.NORTH);
            if (direction.equals("E")) todaysWeather.setDirection(Weather.DIRECTION.EAST);
            if (direction.equals("S")) todaysWeather.setDirection(Weather.DIRECTION.SOUTH);
            if (direction.equals("W")) todaysWeather.setDirection(Weather.DIRECTION.WEST);
        }
    } 

    public static void simMode(Scanner input){
        System.out.print("(S)tandard / (E)ndless Mode (Default Endless) ");
        String mode = input.nextLine();
        if (mode.equals("S")) endlessMode = false;
    } 

    public static void dataOverlay(Scanner input){
        System.out.print("Show Data Overlay: - On (1) / Off (2) (Default Off): ");
        String mode = input.nextLine();
        if (mode.equals("1")) displayMode = true;
    } 

    public static int numFires(Scanner input){
        if (endlessMode){
            System.out.print("Number of regenerating fires: (Defaul 3) " );
            String num_fires_string = input.nextLine();
            if (num_fires_string.equals("")) return numberOfFires;
            numberOfFires = Integer.valueOf(num_fires_string);
            return numberOfFires;
        }
        return numberOfFires;
    }
}
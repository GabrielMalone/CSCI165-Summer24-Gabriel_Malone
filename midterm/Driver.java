

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
       
        size = 150;                                         // map size
        speed = 20;                                         // step rate ms
        catchprobability = .27;                             // base probability of trees catching on fire
        chanceToRegrow = .01;                               // chance for tree to regrow after burning
        startingPop = size;                                 // starting populatin (map size gives good ratio of space to animals)
        popRegrowth = size;                                 // number of animals to return after fire goes out
        numberOfFires = 5;
        size = worldResize(size);		                    // map size adjustment for center positioning
        neWorld = new World();
        todaysWeather.setDirection(Weather.DIRECTION.EAST); // set weather pattern direction
        world = new World_Graphics();
       // neWorld.applySpread();                              // start fire
       

    }

    public static int worldResize(int size){
        if (size % 2 == 0) return size += 1;	
        return size;
    }

    public static void startSequence(){
        neWorld.applySpread();                              // start fire
    } 

}


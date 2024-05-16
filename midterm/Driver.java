

public class Driver {

	public static int size;
    public static double catchprobability;
    public static Weather todaysWeather = new Weather();
    public static World_Graphics world;
    public static World neWorld;
 
	public static void main(String[] args) {
        size = 100;                                         // map size
        catchprobability = .25;                             // base probability of trees catching on fire
        size = worldResize(size);		                    // map size adjustment for center positioning
        neWorld = new World();
        todaysWeather.setDirection(Weather.DIRECTION.WEST); // set weather pattern direction
        world = new World_Graphics();                       // Jpanel
        neWorld.applySpread();                              // start fire
    }

    public static int worldResize(int size){
        if (size % 2 == 0) return size += 1;	
        return size;
    }

}

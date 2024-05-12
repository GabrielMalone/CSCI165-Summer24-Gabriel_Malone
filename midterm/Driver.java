
public class Driver {

	public static int size;
    public static double catchprobability;
    public static Weather todaysWeather = new Weather();
    

	public static void main(String[] args) {
		// map size
        size = 20;
        // base probability of trees catching on fire
        catchprobability = .25;
        // map size adjustment for center positioning
		if (size % 2 == 0) size += 1;
        // create new world matrix
		World neWorld = new World();
        // fill matrix with cells
		neWorld.fillWorld();
        // set weather pattern direction
		todaysWeather.setDirection(Weather.DIRECTION.EAST);
        // start fire
		neWorld.applySpread();
    }
}

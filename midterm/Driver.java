

public class Driver {

	public static int size;
    public static double catchprobability;
    public static Weather todaysWeather = new Weather();
    public static World_Graphics world;
    public static Animal_Graphics animals;
    

	public static void main(String[] args) {
        // map size
        size = 20;
        // base probability of trees catching on fire
        catchprobability = .25;
        // map size adjustment for center positioning
        size = worldResize(size);		
        // fill matrix with cells
        World neWorld = createWorld();
        // set weather pattern direction
        todaysWeather.setDirection(Weather.DIRECTION.EAST);
        // Jpanel
        animals = new Animal_Graphics();
        world = new World_Graphics();
        // start fire
        neWorld.applySpread();
    }

    public static int worldResize(int size){
        if (size % 2 == 0) return size += 1;	
        return size;
    }

    public static World createWorld(){
        World neWorld = new World();
        neWorld.createAnimals();
        neWorld.createTrees();
        neWorld.createFires();
        neWorld.createBurnt();
		neWorld.fillWorld();
        return neWorld;
    }

}

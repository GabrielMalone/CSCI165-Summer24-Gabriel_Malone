
import java.util.Random;

public class Weather {

	Random rand = new Random();

    public static enum DIRECTION {
		NORTH, 
		EAST, 
		SOUTH,
		WEST;
	}
    public DIRECTION direction;
    public String windDirection;


	/**
	 * Method to create wind pattern
	 * 
	 */
	public void pattern(){

		if (this.windDirection.equals("WEST") || this.windDirection.equals("EAST")){
			// get width of wind vein randomly // repeat randomly
			int repeat = rand.nextInt(0, Driver.neWorld.worldMatrix.length);
			for (int x = 0; x < repeat ; x ++){
				int point1 = rand.nextInt(0, Driver.neWorld.worldMatrix.length);
				int point2 = rand.nextInt(0, Driver.neWorld.worldMatrix.length);
				for (int i = point1; i < point2 ; i ++ ){
					for (int j = 0; j < Driver.neWorld.worldMatrix.length ; j ++ ){
						Driver.neWorld.worldMatrix[i][j].setWeather(Cell.WEATHER.WINDY);
					}
				}
			}	
		}
		else if (this.windDirection.equals("NORTH") || this.windDirection.equals("SOUTH")){
			// get width of wind vein 
			int point1 = rand.nextInt(0, Driver.neWorld.worldMatrix.length);
			int point2 = rand.nextInt(0, Driver.neWorld.worldMatrix.length);
			for (int i = 0; i < Driver.neWorld.worldMatrix.length ; i ++ ){
				for (int j = point1; j < point2 ; j ++ ){
					Driver.neWorld.worldMatrix[i][j].setWeather(Cell.WEATHER.WINDY);
				}
			}
		}
	}

    public void setDirection (DIRECTION direction){
		this.direction = direction;
        switch (this.direction) {
            case NORTH: this.windDirection 	= "NORTH";
                break;
            case SOUTH: this.windDirection	= "SOUTH";
                break;
            case EAST: 	this.windDirection 	= "EAST";
                break;
            case WEST: 	this.windDirection 	= "WEST";
				break;
			default: 	this.windDirection 	= "X";
                break;
	    }
    }

    public DIRECTION getDirection(){
		return this.direction;
	}

	public String getStringDirection(){
		if (this.direction == null) return "X";
		switch (this.direction) {
            case NORTH: return 	"NORTH";
            case SOUTH: return 	"SOUTH";
            case EAST: 	return 	"EAST";
            case WEST: 	return 	"WEST";
			default:	return 	"x";
	    }
	}

	public void clearWeatherPattern(){
		for (int i = 0; i < Driver.neWorld.worldMatrix.length ; i ++ ){
			for (int j = 0; j < Driver.neWorld.worldMatrix.length ; j ++ ){
				Driver.neWorld.worldMatrix[i][j].setWeather(Cell.WEATHER.CALM);
			}
		}
	}
	
}

   

    

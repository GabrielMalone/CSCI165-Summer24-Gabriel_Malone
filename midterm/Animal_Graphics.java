
// import the Graphics class


import java.awt.Graphics;
import java.awt.Graphics2D;
public class Animal_Graphics{
	

    public static int IMAGE_HEIGHT 		= World_Graphics.IMAGE_HEIGHT;
	public static int IMAGE_WIDTH 		= World_Graphics.IMAGE_WIDTH;
	public static int WINDOW_HEIGHT 	= World_Graphics.WINDOW_HEIGHT;

    // method to render the world in Java 2D Graphics
    public static void animalMap(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;
        // cartesian points, to control where rectangles are drawn
        int x = 1, y = 1;
        for(int i = 0; i < World.worldMatrix.length; i++){ 
            // inner loop processes the number of "columns"
            for(int j = 0; j < World.worldMatrix.length; j++){
                Cell currentCell = World.worldMatrix[i][j];
                if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEALIVE){
                    // display scaled versions of trees depending on map size
                    graphics2d.drawImage(currentCell.animalimage, x, y, IMAGE_HEIGHT*2, IMAGE_WIDTH*2, null);
                }
                if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEDEAD){
                    // display scaled versions of trees depending on map size
                    graphics2d.drawImage(currentCell.animalimage, x, y, IMAGE_HEIGHT, IMAGE_WIDTH, null);
                }
                x += IMAGE_WIDTH;
            } 
            y += IMAGE_HEIGHT;	
            x = 1;						
        } 
    }
    
}


// import the Graphics class

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
// A JPanel is a simple container for graphics
import javax.swing.JPanel;			
// Graphics objects
// GUI objects to create the window
import javax.swing.JFrame;
// GUI apps should be added to the JRE event queue

public class World_Graphics extends JPanel{
	
	// create a JFrame instance to contain the JPanel	
	private JFrame window = new JFrame();
	
	public static int IMAGE_HEIGHT 		= setImageSize();
	public static int IMAGE_WIDTH 		= setImageSize();
	public static int WINDOW_HEIGHT 	= setWindowSize(IMAGE_HEIGHT);

	@Override
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		baseMap(g);
		animalMap(g);
		this.repaint();
	}

	public World_Graphics() {

		// add "this" JPanel to the JFrame			
		window.add(this);	
		// give it a title bar							
		window.setTitle("Forest Fire Simulation");
		// how big is the window?					
		window.setSize(WINDOW_HEIGHT, (int)(WINDOW_HEIGHT * 1.05));
		// place window in the middle of the screen, not relative to any other GUI object						
		window.setLocationRelativeTo(null);			
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setBackground(Color.green);
						
		}

	public static void baseMap(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		// cartesian points, to control where rectangles are drawn
		int x = 1, y = 1;
		for(int i = 0; i < World.worldMatrix.length; i++){ 
			// inner loop processes the number of "columns"
			for(int j = 0; j < World.worldMatrix.length; j++){
				Cell currentCell = World.worldMatrix[i][j];
				if (currentCell.getState() == Cell.STATES.TREE){
					// display scaled versions of trees depending on map size
					graphics2d.drawImage(currentCell.stateimage, x, y, IMAGE_WIDTH, IMAGE_HEIGHT, null);
				}
				if (currentCell.getState() == Cell.STATES.BURNING){
					// display scaled versions of trees depending on map size
					graphics2d.drawImage(currentCell.stateimage, x, y, IMAGE_WIDTH, IMAGE_HEIGHT, null);
				}
				if (currentCell.getState() == Cell.STATES.EMPTY){
					// display scaled versions of trees depending on map size
					graphics2d.drawImage(currentCell.stateimage, x, y, IMAGE_WIDTH, IMAGE_HEIGHT, null);
				}
				x += IMAGE_WIDTH;	// move x position to the right by "rectangle width"
			} 
			y += IMAGE_HEIGHT;		// move y position down by "rectangle height"
			x = 1;						
		} 
	}

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
                    graphics2d.setColor(Color.yellow);graphics2d.fillOval(x, y, IMAGE_HEIGHT, IMAGE_WIDTH);
                }
                if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEDEAD){
                    // display scaled versions of trees depending on map size
					graphics2d.setColor(Color.red);graphics2d.fillOval(x, y, IMAGE_HEIGHT, IMAGE_WIDTH);
                }
                x += IMAGE_WIDTH;
            } 
            y += IMAGE_HEIGHT;	
            x = 1;						
        } 
    }


	private static int setImageSize(){
		int size = 0;
		if (Driver.size <= 21) {
			return size = 30;
		}
		else if (Driver.size <= 51){
		return size = (int)(Driver.size / 2); 
		}
		else if (Driver.size <= 71){
			return size = (int)(Driver.size / 6); 
			}
		else if (Driver.size <= 101){
			return size = (int)(Driver.size / 12); 
			}
		else if (Driver.size > 101){
			return size = (int)(Driver.size / 14); 
			}
		return size;
		
	}

	private static int setWindowSize(int IMAGE_HEIGHT){
		int size;
		size = (Driver.size * IMAGE_HEIGHT);
		return size;
	}

}
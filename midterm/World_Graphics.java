
// import the Graphics class

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class World_Graphics extends JPanel{
	
	// create a JFrame instance to contain the JPanel	
	private JFrame window = new JFrame();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("size");
	private JMenuItem size = new JMenuItem("size");

	public static int IMAGE_HEIGHT 		= setImageSize();
	public static int IMAGE_WIDTH 		= setImageSize();
	public static int WINDOW_HEIGHT 	= setWindowSize(IMAGE_HEIGHT);

	@Override
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		baseMap(g);
		animalMap(g);
		windMap(g);
		//dataOverlay(g);
		this.repaint();
	}

	public World_Graphics() {

		// add "this" JPanel to the JFrame			
		window.add(this);	
		window.isOpaque();
		// give it a title bar							
		window.setTitle("Forest Fire Simulation");
		// how big is the window?		
		window.getSize();			
		window.setSize(WINDOW_HEIGHT, (int)(WINDOW_HEIGHT * 1.05));
		// place window in the middle of the screen, not relative to any other GUI object						
		window.setLocationRelativeTo(null);			
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setBackground(Color.black);
		menu.add(size);
		menuBar.add(menu);
		window.setJMenuBar(menuBar);
	
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
				if (currentCell.getState() == Cell.STATES.BURNT){
					// display scaled versions of trees depending on map size
					graphics2d.drawImage(currentCell.stateimage, x, y, IMAGE_WIDTH, IMAGE_HEIGHT, null);
				}
				x += IMAGE_WIDTH;	
			} 
			y += IMAGE_HEIGHT;		
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

	public static void windMap(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;
		Color wind_small_map = new Color(255, 255, 255, 150);
		Color wind_large_map = new Color(255, 255, 255, 50);
		Color wind_medium_map = new Color(255, 255, 255, 100);
        int x = 1, y = 1;
        for(int i = 0; i < World.worldMatrix.length; i++){ 
            for(int j = 0; j < World.worldMatrix.length; j++){
                Cell currentCell = World.worldMatrix[i][j];
                if (currentCell.getCellWeather() == Cell.WEATHER.WINDY){
					if (Driver.size <= 51) {graphics2d.setColor(wind_small_map); graphics2d.drawArc(x, y, 10, 10, y, x);}
					if (Driver.size <= 101) {graphics2d.setColor(wind_medium_map); graphics2d.drawArc(x, y, 10, 10, y, x);}
					else if (Driver.size <= 501){graphics2d.setColor(wind_large_map); graphics2d.drawArc(x, y, 5, 5, y, x);}
                }
                x += IMAGE_WIDTH;
            } 
            y += IMAGE_HEIGHT;	
            x = 1;						
        } 
    }

	public static void dataOverlay(Graphics g) {

        Graphics2D graphics2d = (Graphics2D) g;
		
		// STEPS BOX AND INFO
		Color transparentback = new Color(0f, 1f, .5f, .7f);
		Color transparenttitle = new Color(0f, 0f, 0f, .9f);
		Color transparentinfo = new Color(1f, 1f, 1f, .9f);
		Font FontTitle = new Font("SansSerif", Font.BOLD, 12);
		Font FontData = new Font("SansSerif", Font.BOLD, 44);
		
		graphics2d.setColor(transparentback);
		graphics2d.fillRoundRect(WINDOW_HEIGHT - 150, 20, 100, 100, 10, 10);
		graphics2d.setColor(transparenttitle);
		graphics2d.setFont(FontTitle);
		graphics2d.drawString("STEPS", WINDOW_HEIGHT - 120,40);
		graphics2d.setFont(FontData);
		graphics2d.setColor(transparentinfo);
		if (World.timeStep < 9) graphics2d.drawString(String.valueOf(World.timeStep), WINDOW_HEIGHT - 115, 95);
		else if (World.timeStep < 100) graphics2d.drawString(String.valueOf(World.timeStep), WINDOW_HEIGHT - 130,95);
		else graphics2d.drawString(String.valueOf(World.timeStep), WINDOW_HEIGHT - 143, 95);
		
		// BURN AREA BOX AND INFO

		graphics2d.setColor(transparentback);
		graphics2d.fillRoundRect(WINDOW_HEIGHT - 150, 125, 100, 100, 10, 10);
		graphics2d.setColor(transparenttitle);
		graphics2d.setFont(FontTitle);
		graphics2d.drawString("BURN AREA", WINDOW_HEIGHT - 135, 145);
		graphics2d.setColor(transparentinfo);
		graphics2d.setFont(FontData);
		String burn_output = String.format("%.0f%%", World.burnPercentage());
		if (World.burnPercentage() < 9) graphics2d.drawString(burn_output, WINDOW_HEIGHT - 130, 200);
		else if (World.burnPercentage() < 100) graphics2d.drawString(burn_output, WINDOW_HEIGHT - 148, 200);
		else graphics2d.drawString(burn_output, WINDOW_HEIGHT - 158, 200);

		// DEATH TOLL BOX AND INFO

		graphics2d.setColor(transparentback);
		graphics2d.fillRoundRect(WINDOW_HEIGHT - 150, 230, 100, 100, 10, 10);
		graphics2d.setFont(FontTitle);
		graphics2d.setColor(transparenttitle);
		graphics2d.drawString("MORT RATE", WINDOW_HEIGHT - 135, 250);
		graphics2d.setFont(FontData);
		graphics2d.setColor(transparentinfo);
		String death_output = String.format("%.0f%%", World.mortalityRate());
		if (World.mortalityRate() < 9) graphics2d.drawString(death_output, WINDOW_HEIGHT - 127, 305);
		else if (World.mortalityRate() < 99) graphics2d.drawString(death_output, WINDOW_HEIGHT - 148, 305);
		else graphics2d.drawString(death_output, WINDOW_HEIGHT - 155, 305);

		// WIND DIRECTION

		graphics2d.setColor(transparentback);
		graphics2d.fillRoundRect(WINDOW_HEIGHT - 150, 335, 100, 100, 10, 10);
		graphics2d.setFont(FontTitle);
		graphics2d.setColor(transparenttitle);
		graphics2d.drawString("WIND DIR", WINDOW_HEIGHT - 130, 355);
		graphics2d.setFont(FontData);
		graphics2d.setColor(transparentinfo);
		switch (Driver.todaysWeather.getStringDirection()) {
			case "NORTH": 	graphics2d.drawString("N", WINDOW_HEIGHT - 115, 405);
				break;
			case "SOUTH": 	graphics2d.drawString("S", WINDOW_HEIGHT - 115, 405);
				break;
			case "EAST": 	graphics2d.drawString("E", WINDOW_HEIGHT - 115, 405);
				break;
			case "WEST": 	graphics2d.drawString("W", WINDOW_HEIGHT - 120, 405);
				break;
			default:
				break;
		}
    }

	private static int setImageSize(){
		int size = 0;
		if (Driver.size == 21) {
			return size = 30;
		}
		else if (Driver.size == 51){
		return size = (int)(Driver.size / 4); 
		}
		else if (Driver.size == 101){
			return size = (int)(Driver.size / 15); 
			}
		else if (Driver.size == 151){
			return size = (int)(Driver.size / 35); 
			}
		else if (Driver.size == 201){
			return size = (int)(Driver.size / 60); 
			}
		else if (Driver.size == 501){
			return size = (int)(Driver.size / 500); 
			}
		return size;
		
	}

	private static int setWindowSize(int IMAGE_HEIGHT){
		int size;
		size = (Driver.size * IMAGE_HEIGHT);
		return size;
	}

}

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
		dataOverlay(g);
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
		window.setSize(WINDOW_HEIGHT, (int)(WINDOW_HEIGHT * 1.05 + 150));
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
        int x = 1, y = 1;
        for(int i = 0; i < World.worldMatrix.length; i++){ 
            for(int j = 0; j < World.worldMatrix.length; j++){
                Cell currentCell = World.worldMatrix[i][j];
                if (currentCell.getCellWeather() == Cell.WEATHER.WINDY){
                    graphics2d.setColor(Color.gray);
					graphics2d.drawArc(x, y, 5, 5, y, x);
                }
                x += IMAGE_WIDTH;
            } 
            y += IMAGE_HEIGHT;	
            x = 1;						
        } 
    }

	public static void dataOverlay(Graphics g) {

        Graphics2D graphics2d = (Graphics2D) g;
		
		// DEATH TOLL BOX AND INFO

		graphics2d.setColor(Color.black);
		graphics2d.drawRoundRect(20, (int)(WINDOW_HEIGHT) + 20, 100, 100, 10, 10);
		
		Font Fonta = new Font("SansSerif", Font.BOLD, 12);
		graphics2d.setFont(Fonta);
		graphics2d.drawString("MORT RATE", 32, (int)(WINDOW_HEIGHT) + 40);
		Font Fontb = new Font("SansSerif", Font.BOLD, 44);
		graphics2d.setFont(Fontb);
		String death_output = String.format("%.0f%%", World.mortalityRate());
		if (World.mortalityRate() < 9) graphics2d.drawString(death_output, 37, (int)(WINDOW_HEIGHT) + 90);
		else if (World.mortalityRate() < 99) graphics2d.drawString(death_output, 25, (int)(WINDOW_HEIGHT) + 90);
		else graphics2d.drawString(death_output, 10, (int)(WINDOW_HEIGHT) + 90);

		// STEPS BOX AND INFO

		graphics2d.setColor(Color.black);
		graphics2d.drawRoundRect(150, (int)(WINDOW_HEIGHT) + 20, 100, 100, 10, 10);
		
		Font Fontc = new Font("SansSerif", Font.BOLD, 12);
		graphics2d.setFont(Fontc);
		graphics2d.drawString("HOURS", 178, (int)(WINDOW_HEIGHT) + 40);
		Font Fontd = new Font("SansSerif", Font.BOLD, 44);
		graphics2d.setFont(Fontd);
		if (World.timeStep < 10) graphics2d.drawString(String.valueOf(World.timeStep), 185, (int)(WINDOW_HEIGHT) + 90);
		else if (World.timeStep < 100) graphics2d.drawString(String.valueOf(World.timeStep), 170, (int)(WINDOW_HEIGHT) + 90);
		else graphics2d.drawString(String.valueOf(World.timeStep), 157, (int)(WINDOW_HEIGHT) + 90);
		
		// BURN AREA BOX AND INFO

		graphics2d.setColor(Color.black);
		graphics2d.drawRoundRect(280, (int)(WINDOW_HEIGHT) + 20, 100, 100, 10, 10);
		
		Font Fonte = new Font("SansSerif", Font.BOLD, 12);
		graphics2d.setFont(Fonte);
		graphics2d.drawString("BURN AREA", 293, (int)(WINDOW_HEIGHT) + 40);
		Font Fontf = new Font("SansSerif", Font.BOLD, 44);
		graphics2d.setFont(Fontf);
		String burn_output = String.format("%.0f%%", World.burnPercentage());
		if (World.burnPercentage() < 9) graphics2d.drawString(burn_output, 300, (int)(WINDOW_HEIGHT) + 90);
		else if (World.burnPercentage() < 100) graphics2d.drawString(burn_output, 285, (int)(WINDOW_HEIGHT) + 90);
		else graphics2d.drawString(burn_output, 277, (int)(WINDOW_HEIGHT) + 90);
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
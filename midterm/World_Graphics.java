
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;


public class World_Graphics extends JPanel{
	// private Timer timer = new Timer(0, this); 
	// create a JFrame instance to contain the JPanel	
	private JFrame window = new JFrame();
	

	// MAP SIZE DRAW SETTINGS
	public static int IMAGE_HEIGHT 		= setImageSize();
	public static int IMAGE_WIDTH 		= setImageSize();
	public static int WINDOW_HEIGHT 	= setWindowSize(IMAGE_HEIGHT) ;

	
	public World_Graphics() {
		// mouse clicks to place bombs
		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent e){
				int column = e.getX() / IMAGE_HEIGHT;
				int row = e.getY() / IMAGE_HEIGHT;
				if (column > 1 && row > 1 && World.worldMatrix[row][column].getState() == Cell.STATES.TREE){
					//World.worldMatrix[row][column].setState(Cell.STATES.BURNING);
					Bomb.placeBomb(row, column);
				}
			}
		});
		// mouse movement to trail with fire
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				int column = e.getX() / IMAGE_HEIGHT;
				int row = e.getY() / IMAGE_HEIGHT;
				if (World.worldMatrix[row][column].getState() == Cell.STATES.TREE){
					//World.worldMatrix[row][column].setState(Cell.STATES.BURNING);
					Bomb.nuke(row, column);
				}
			}
		});
		//timer.start();
		window.add(this);
		// MAIN WINDOW	
		window.isOpaque();
		// give it a title bar							
		window.setTitle("Goobs Fire Sim");
		// how big is the window?		
		window.getSize();			
		window.setSize( WINDOW_HEIGHT, (int)(WINDOW_HEIGHT * 1.045));
		// place window in the middle of the screen, not relative to any other GUI object						
		window.setLocationRelativeTo(null);		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setBackground(Color.BLACK);
		
	}

	public void paintComponent(Graphics g){
		//super.paintComponent(g);
		baseMap(g);
		animalMap(g);
		windMap(g);
		if (Driver.displayMode) 
			dataOverlay(g);
		this.repaint();
		
	}

	public void baseMap(Graphics g) {
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

	public void animalMap(Graphics g) {
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

	public void windMap(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;
		Color map20  = new Color(255, 255, 255, 150);
		Color map50  = new Color(255, 255, 255, 50);
		Color map100 = new Color(255, 255, 255, 40);
		Color map150 = new Color(255, 255, 255, 30);
		Color map200 = new Color(255, 255, 255, 10);
		Color map500 = new Color(255, 255, 255, 5);
        int x = 1, y = 1;
        for(int i = 0; i < World.worldMatrix.length; i++){ 
            for(int j = 0; j < World.worldMatrix.length; j++){
                Cell currentCell = World.worldMatrix[i][j];
                if (currentCell.getCellWeather() == Cell.WEATHER.WINDY){
					if (Driver.size == 51) 	{graphics2d.setColor(map50); 	graphics2d.drawArc(x, y, 10, 10, y, x);}
					else if (Driver.size == 101) 	{graphics2d.setColor(map100); 	graphics2d.drawArc(x, y, 10, 10, y, x);}
					else if (Driver.size == 151) 	{graphics2d.setColor(map150); 	graphics2d.drawArc(x, y, 5, 5, y, x);}
					else if (Driver.size == 201) 	{graphics2d.setColor(map200); 	graphics2d.drawArc(x, y, 10, 10, y, x);}
					else if (Driver.size <= 501)	{graphics2d.setColor(map500);	graphics2d.drawArc(x, y, 5, 5, y, x);}
					else 							{graphics2d.setColor(map20); 	graphics2d.drawArc(x, y, 15, 15, y, x);}

                }
                x += IMAGE_WIDTH;
            } 
            y += IMAGE_HEIGHT;	
            x = 1;						
        } 
    }

	public void dataOverlay(Graphics g) {
	
        Graphics2D graphics2d = (Graphics2D) g;
		// STEPS BOX AND INFO
		Color transparentback = new Color(0f, 1f, .5f, .7f);
		Color transparenttitle = new Color(0f, 0f, 0f, .9f);
		Color transparentinfo = new Color(1f, 1f, 1f, .9f);
		Font FontTitle = new Font("SansSerif", Font.BOLD, 8);
		Font FontData = new Font("SansSerif", Font.BOLD, 17);
		
		graphics2d.setColor(transparentback);
		graphics2d.fillRoundRect(WINDOW_HEIGHT - 60, 20, 50, 50, 10, 10);
		graphics2d.setColor(transparenttitle);
		graphics2d.setFont(FontTitle);
		graphics2d.drawString("STEPS", WINDOW_HEIGHT - 48,35);
		graphics2d.setFont(FontData);
		graphics2d.setColor(transparentinfo);
		if (World.timeStep < 9) 			graphics2d.drawString(String.valueOf(World.timeStep), WINDOW_HEIGHT - 40, 55);
		else if (World.timeStep < 100) 		graphics2d.drawString(String.valueOf(World.timeStep), WINDOW_HEIGHT - 45,55);
		else if (World.timeStep < 1000 ) 	graphics2d.drawString(String.valueOf(World.timeStep), WINDOW_HEIGHT - 52, 55);
		else 								graphics2d.drawString(String.valueOf(World.timeStep), WINDOW_HEIGHT - 57, 55);
		
		// BURN AREA BOX AND INFO
		graphics2d.setColor(transparentback);
		graphics2d.fillRoundRect(WINDOW_HEIGHT - 60, 75, 50, 50, 10, 10);
		graphics2d.setColor(transparenttitle);
		graphics2d.setFont(FontTitle);
		graphics2d.drawString("BURN %", WINDOW_HEIGHT - 52, 90);
		graphics2d.setColor(transparentinfo);
		graphics2d.setFont(FontData);
		String burn_output = String.format("%.0f%%", World.burnPercentage());
		if (World.burnPercentage() < 10.001) graphics2d.drawString(burn_output, WINDOW_HEIGHT - 45, 110);
		else if (World.burnPercentage() < 100) graphics2d.drawString(burn_output, WINDOW_HEIGHT - 50, 110);
		else graphics2d.drawString(burn_output, WINDOW_HEIGHT - 57, 110);

		// DEATH TOLL BOX AND INFO
		if (Driver.animalsOn){
			graphics2d.setColor(transparentback);
			graphics2d.fillRoundRect(WINDOW_HEIGHT - 60, 130, 50, 50, 10, 10);
			graphics2d.setFont(FontTitle);
			graphics2d.setColor(transparenttitle);
			graphics2d.drawString("MORT %", WINDOW_HEIGHT - 52, 145);
			graphics2d.setFont(FontData);
			graphics2d.setColor(transparentinfo);
			String death_output = String.format("%.0f%%", World.mortalityRate());
			if (World.mortalityRate() < 10.001) graphics2d.drawString(death_output, WINDOW_HEIGHT - 45, 165);
			else if (World.mortalityRate() < 99) graphics2d.drawString(death_output, WINDOW_HEIGHT - 50, 165);
			else graphics2d.drawString(death_output, WINDOW_HEIGHT - 57, 165);
		}

		// WIND DIRECTION
		if (Driver.weatherOn){
			graphics2d.setColor(transparentback);
			graphics2d.fillRoundRect(WINDOW_HEIGHT - 60, 185, 50, 50, 10, 10);
			graphics2d.setFont(FontTitle);
			graphics2d.setColor(transparenttitle);
			graphics2d.drawString("WIND  ", WINDOW_HEIGHT - 47, 200);
			graphics2d.setFont(FontData);
			graphics2d.setColor(transparentinfo);
			switch (Driver.todaysWeather.getStringDirection()) {
				case "NORTH": 	graphics2d.drawString("N", WINDOW_HEIGHT - 43, 220);
					break;
				case "SOUTH": 	graphics2d.drawString("S", WINDOW_HEIGHT - 40, 220);
					break;
				case "EAST": 	graphics2d.drawString("E", WINDOW_HEIGHT - 43, 220);
					break;
				case "WEST": 	graphics2d.drawString("W", WINDOW_HEIGHT - 43, 220);
					break;
				default:		graphics2d.drawString("X", WINDOW_HEIGHT - 43, 220);
					break;
			}
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
			return size = (int)(Driver.size / 100); 
			}
		else if (Driver.size == 401){
			return size = (int)(Driver.size / 200); 
			}
		else if (Driver.size == 501){
			return size = (int)(Driver.size / 500); 
			}
		else if (Driver.size == 1001){
			return size = (int)(Driver.size / 1000); 
			}
		return size;
		
	}

	private static int setWindowSize(int IMAGE_HEIGHT){
		int size;
		size = (Driver.size * IMAGE_HEIGHT);
		return size;
	}

}
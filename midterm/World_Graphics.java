
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;


public class World_Graphics extends JPanel implements ActionListener{

	public int DELAY = Driver.speed;
	public Timer timer;
	private  World world = Driver.neWorld;
	
	// create a JFrame instance to contain the JPanel	
	public  JFrame window = new JFrame();
	// MAP SIZE DRAW SETTINGS
	public  int IMAGE_HEIGHT 		= setImageSize();
	public  int IMAGE_WIDTH 		= IMAGE_HEIGHT;
	public  int WINDOW_HEIGHT 		= setWindowSize(IMAGE_HEIGHT) ;

	@Override
	public void actionPerformed(ActionEvent e){
		// stop and restart timer for live update purpose
		this.timer.stop();
		initTimer();
		world.spreadFire();
		this.repaint();
	}

	public void initTimer() {
		this.timer = new Timer(DELAY, this);	
		this.timer.start();					
	}

	public World_Graphics() {
		
		world.initializeFire();
		initTimer();
		// mouse clicks to place bombs
		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent e){
				int column = e.getX() / IMAGE_HEIGHT;
				int row = e.getY() /  IMAGE_HEIGHT;
				if (column > 1 && row > 1 && Driver.neWorld.worldMatrix[row][column].getState() == Cell.STATES.TREE){
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
				if (Driver.neWorld.worldMatrix[row][column].getState() == Cell.STATES.TREE){
					//World.worldMatrix[row][column].setState(Cell.STATES.BURNING);
					Bomb.placeBomb(row, column);
				}
			}
		});
		this.window.add(this);
		// MAIN WINDOW	
		// give it a title bar							
		this.window.setTitle("Goobs Fire Sim");
		// how big is the window?		
		this.window.getSize();			
		this.window.setSize( WINDOW_HEIGHT, (int)(WINDOW_HEIGHT * 1.045));
		// place window in the middle of the screen, not relative to any other GUI object			
		this.window.setLocation(0,0);				
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.window.setVisible(true);
		
	}
	
	@Override
	public void paintComponent(Graphics g){
	
		super.paintComponent(g);
		baseMap(g);
		animalMap(g);
		windMap(g);
		if (Driver.displayMode) 
			dataOverlay(g);
	
	
	}

	public void baseMap(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		// cartesian points, to control where rectangles are drawn
		int x = 1, y = 1;
		for(int i = 0; i < Driver.neWorld.worldMatrix.length; i++){ 
			// inner loop processes the number of "columns"
			for(int j = 0; j < Driver.neWorld.worldMatrix.length; j++){
				Cell currentCell = Driver.neWorld.worldMatrix[i][j];
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
        for(int i = 0; i < Driver.neWorld.worldMatrix.length; i++){ 
            // inner loop processes the number of "columns"
            for(int j = 0; j < Driver.neWorld.worldMatrix.length; j++){
                Cell currentCell = Driver.neWorld.worldMatrix[i][j];
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
        for(int i = 0; i < Driver.neWorld.worldMatrix.length; i++){ 
            for(int j = 0; j < Driver.neWorld.worldMatrix.length; j++){
                Cell currentCell = Driver.neWorld.worldMatrix[i][j];
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
		if (Driver.neWorld.timeStep < 9) 			graphics2d.drawString(String.valueOf(Driver.neWorld.timeStep), WINDOW_HEIGHT - 40, 55);
		else if (Driver.neWorld.timeStep < 100) 		graphics2d.drawString(String.valueOf(Driver.neWorld.timeStep), WINDOW_HEIGHT - 45,55);
		else if (Driver.neWorld.timeStep < 1000 ) 	graphics2d.drawString(String.valueOf(Driver.neWorld.timeStep), WINDOW_HEIGHT - 52, 55);
		else 								graphics2d.drawString(String.valueOf(Driver.neWorld.timeStep), WINDOW_HEIGHT - 57, 55);
		
		// BURN AREA BOX AND INFO
		graphics2d.setColor(transparentback);
		graphics2d.fillRoundRect(WINDOW_HEIGHT - 60, 75, 50, 50, 10, 10);
		graphics2d.setColor(transparenttitle);
		graphics2d.setFont(FontTitle);
		graphics2d.drawString("BURN %", WINDOW_HEIGHT - 52, 90);
		graphics2d.setColor(transparentinfo);
		graphics2d.setFont(FontData);
		String burn_output = String.format("%.0f%%", Driver.neWorld.burnPercentage());
		if (Driver.neWorld.burnPercentage() < 10.001) graphics2d.drawString(burn_output, WINDOW_HEIGHT - 45, 110);
		else if (Driver.neWorld.burnPercentage() < 100) graphics2d.drawString(burn_output, WINDOW_HEIGHT - 50, 110);
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
			String death_output = String.format("%.0f%%", Driver.neWorld.mortalityRate());
			if (Driver.neWorld.mortalityRate() < 10.001) graphics2d.drawString(death_output, WINDOW_HEIGHT - 45, 165);
			else if (Driver.neWorld.mortalityRate() < 99) graphics2d.drawString(death_output, WINDOW_HEIGHT - 50, 165);
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

	public int setImageSize(){
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
		else if (Driver.size == 401){
			return size = (int)(Driver.size / 200); 
			}
		else if (Driver.size == 501){
			return size = (int)(Driver.size / 250); 
			}
		else if (Driver.size == 1001){
			return size = (int)(Driver.size / 1000); 
			}
		return size;
		
	}

	public int setWindowSize(int IMAGE_HEIGHT){
		int size;
		size = (Driver.size * IMAGE_HEIGHT);
		return size;
	}

}
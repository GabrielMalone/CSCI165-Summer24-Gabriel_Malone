// Gabriel Malone / CS165 / Midterm / Summer 2024

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;

public class World_Graphics extends JPanel implements ActionListener{
	// Timer to repaint map
	public int DELAY = Driver.speed;
	public Timer timer;
	private  World world = Driver.neWorld;
	// create a JFrame instance to contain the JPanel
	public  JFrame window = new JFrame();
	// MAP SIZE DRAW SETTINGS
	public  int IMAGE_SIZE 		= setImageSize();
	public  int WINDOW_HEIGHT 	= setWindowSize(IMAGE_SIZE);
	public 	int r;
	public 	int a;
	public 	int b;
	

	public World_Graphics() {
		// MAIN WINDOW
		// mouse clicks to place player
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				int column = e.getX() / IMAGE_SIZE;
				int row = e.getY() / IMAGE_SIZE;
				if (! Driver.player_set){
					Driver.neWorld.worldMatrix[row][column].setObject(Cell.OBJECTS.PLAYER);
					Driver.alien = new AlienFireCube();
					Driver.alien.player = Driver.neWorld.worldMatrix[row][column];
					Driver.player_set = true;
				}
				else{
					Driver.player_set = false;
					Driver.alien.cubeTeleport();
				}
			}
			public void mouseReleased(MouseEvent e){
				int column = e.getX() / IMAGE_SIZE;
				int row = e.getY() / IMAGE_SIZE;
				if (! Driver.player_set){
					Driver.neWorld.worldMatrix[row][column].setObject(Cell.OBJECTS.PLAYER);
					Driver.alien = new AlienFireCube();
					Driver.alien.player = Driver.neWorld.worldMatrix[row][column];
					Driver.player_set = true;
				}
			}
		});

		
		addMouseMotionListener(new MouseAdapter(){
			// mouse click and dragged to place bombs
			public void mouseDragged(MouseEvent e){
				// line up the coordinates of the window to the matrix[][]Cell beneath the mouse pointer
				// window size = world matrix size * size of image
				// example : if matrix size is 200x200, each image is set to 3x3 pixels -> (600x600 window size)
				// so from 0->3 window pixels the mouse will be assigned to the first cell
				int coordinates_adjustment = IMAGE_SIZE;
				int column = e.getX() 	/ coordinates_adjustment;
				int row = e.getY() 		/ coordinates_adjustment;
				Bomb.placeBomb(row, column);
			}
		});
		
	
		// add key listener
		this.window.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e){
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_W){
					Driver.alien.movePlayerUp();
				}
				if (keyCode == KeyEvent.VK_D){
					Driver.alien.movePlayerRight();
				}
				if (keyCode == KeyEvent.VK_A){
					Driver.alien.movePlayerLeft();
				}
				if (keyCode == KeyEvent.VK_S){
					Driver.alien.movePlayerDown();
				}
				if (keyCode == KeyEvent.VK_UP){
					Driver.alien.fireUp();
				}
				if (keyCode == KeyEvent.VK_RIGHT){
					Driver.alien.fireRight();
				}
				if (keyCode == KeyEvent.VK_DOWN){
					Driver.alien.fireDown();
				}
				if (keyCode == KeyEvent.VK_LEFT){
					Driver.alien.fireLeft();
				}
				if (keyCode == KeyEvent.VK_0){
					Driver.alien.removeCube();
					Driver.player_set = false;
					Driver.forcefield = false;
				}
				if (keyCode == KeyEvent.VK_SPACE){
					if (Driver.forcefield)
						Driver.forcefield = false;
					else 
						Driver.forcefield = true;	
				}
			}
		});

		world.initializeFire();
		initTimer(); 
		this.window.add(this);
		this.window.setTitle("Goobs Fire Sim");
		this.window.setLocation(0,0);
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.window.setSize(this.WINDOW_HEIGHT, (int)(this.WINDOW_HEIGHT * 1.04));
		this.window.setVisible(true);

	}

	/**
	 * Method to loop the main logic
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		// stop and restart timer for live update purpose
		this.timer.stop();
		initTimer();
		world.spreadFire();
		this.repaint();
	}

	/**
	 * Method to initialize the timer for calling the paint methods
	 */
	public void initTimer() {
		this.timer = new Timer(DELAY, this);
		this.timer.start();
	}

	/**
	 * Method to paint the map and its layers
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		baseMap(g);
		animalMap(g);
		rainMap(g);
	}

	/**
	 * Method to paint the base map (trees, fire, burnt)
	 * @param g
	 */
	public void baseMap(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;	

	// ORIGINAL GRAPHICS REQUIRED FOR PROJECT

		if (Driver.size == 21){
			// cartesian points, to control where rectangles are drawn
			int x = 1, y = 1;
			// height of the rectangle to be drawn
			final int RECT_HEIGHT	= this.IMAGE_SIZE; 
			// width of the rectangle to be drawn
			final int RECT_WIDTH	= this.IMAGE_SIZE; 
			// nested for loops to iterate through matrix in row major order
			// outer loop processes the number of "rows"
			for(int i = 0; i < Driver.size; i++){ 
				// inner loop processes the number of "columns"
				r = 0;
				a = 1; 
				b = 2;
				for(int j = 0; j < Driver.size; j++){
					String[] rgbArray = Driver.neWorld.worldMatrix[i][j].cellColor.split("-");
					int red		= Integer.parseInt(rgbArray[0]);
					int green	= Integer.parseInt(rgbArray[1]);
					int blue 	= Integer.parseInt(rgbArray[2]);
					// using RGB values from above, set the COLOR for this draw
					graphics2d.setColor(new Color(red, green, blue));	
					// Draw a filled rectangle at x, y of size defined by constants
					graphics2d.fillRect(x, y, RECT_HEIGHT, RECT_WIDTH);
					x += RECT_WIDTH;	// move x position to the right by "rectangle width"
					r += 3;				
					a += 3;			
					b += 3;				
				} // end inner loop
				y += RECT_HEIGHT;		// move y position down by "rectangle height"
				x = 1;					// reset x to left axis
			} // end outer loop
		}
		else {

	// MY ATTEMPT AT USING IMAGES FOR SOME GRAPHICS

			// cartesian points, to control where rectangles are drawn
			int x = 1, y = 1;
			for(int i = 0; i < Driver.size; i++){
				// inner loop processes the number of "columns"
				for(int j = 0; j < Driver.size; j++){
					Cell currentCell = Driver.neWorld.worldMatrix[i][j];
					if (currentCell.getState() == Cell.STATES.TREE){
						// display scaled versions of trees depending on map size
						graphics2d.drawImage(currentCell.stateimage, x, y, this.IMAGE_SIZE, this.IMAGE_SIZE, null);
					}
					if (currentCell.getState() == Cell.STATES.BURNING){
						// display scaled versions of trees depending on map size
						graphics2d.drawImage(currentCell.stateimage, x, y, this.IMAGE_SIZE, this.IMAGE_SIZE, null);
					}
					if (currentCell.getState() == Cell.STATES.EMPTY){
						// display scaled versions of trees depending on map size
						graphics2d.drawImage(currentCell.stateimage, x, y, this.IMAGE_SIZE, this.IMAGE_SIZE, null);
					}
					if (currentCell.getState() == Cell.STATES.BURNT){
						// display scaled versions of trees depending on map size
						graphics2d.drawImage(currentCell.stateimage, x, y, this.IMAGE_SIZE, this.IMAGE_SIZE, null);
					}
					x += IMAGE_SIZE;
				}
				y += IMAGE_SIZE;
				x = 1;
			}
		}
	}

	/**
	 * Method to display animals on the map
	 * @param g
	 */ 
	public void animalMap(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;
        // cartesian points, to control where rectangles are drawn
        int x = 1, y = 1;
        for(int i = 0; i < Driver.size; i++){
            // inner loop processes the number of "columns"
            for(int j = 0; j < Driver.size; j++){
                Cell currentCell = Driver.neWorld.worldMatrix[i][j];
                if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEALIVE){
                    // display scaled versions of animsls depending on map size
                    graphics2d.drawImage(currentCell.animalimage, x, y, this.IMAGE_SIZE, this.IMAGE_SIZE, null);
                }
				if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEDEAD){
                    // display scaled versions of animsls depending on map size
                    graphics2d.drawImage(currentCell.animalimage, x, y, this.IMAGE_SIZE, this.IMAGE_SIZE, null);
                }
                if (currentCell.getObject() == Cell.OBJECTS.PLAYER){
                    // display scaled versions of animals depending on map size
					Color clear = new Color(0,0,0,0);
					graphics2d.setColor(clear);
					graphics2d.fillRect(x - (int)Driver.alien.player_size, y - (int)Driver.alien.player_size, 2 * (int)Driver.alien.player_size, 2 * (int)Driver.alien.player_size);
					//graphics2d.drawImage(World.anima[2], x - (int)Driver.me.player_size, y - (int)Driver.me.player_size, 2 * (int)Driver.me.player_size, 2 * (int)Driver.me.player_size, null);
                }
                x += this.IMAGE_SIZE;
            }
            y += this.IMAGE_SIZE;
            x = 1;
        }
    }

	/**
	 * Method to display animals on the map
	 * @param g
	 */ 
	public void rainMap(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;
        int x = 1, y = 1;
        for(int i = 0; i < Driver.size; i++){
            for(int j = 0; j < Driver.size; j++){
                Cell currentCell = Driver.neWorld.worldMatrix[i][j];
                if (currentCell.getRain() == Cell.RAIN.RAINING){
                    // display scaled versions of animsls depending on map size
                    graphics2d.drawImage(currentCell.weatherimage, x, y, this.IMAGE_SIZE, this.IMAGE_SIZE, null);
				}
                x += this.IMAGE_SIZE;
            }
            y += this.IMAGE_SIZE;
            x = 1;
			}
		}
	
	/**
	 * Method to adjust image sizes depending on size of world matrix
	 * @return image size
	 */
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
			return size = (int)(Driver.size / 50);
			}
		else if (Driver.size == 301){
				return size = (int)(Driver.size / 100);
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

	/**
	 * Method to set window size depending on size of world matrix
	 * @param IMAGE_SIZE
	 * @return window size
	 */
	public int setWindowSize(int IMAGE_SIZE){
		int size;
		size = (Driver.size * IMAGE_SIZE);
		return size;
	}
}
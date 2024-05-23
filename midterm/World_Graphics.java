// Gabriel Malone / CS165 / Midterm / Summer 2024

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;

public class World_Graphics extends JPanel implements ActionListener {
	// Timer to repaint map
	public int DELAY = Driver.speed;
	public Timer timer;
	private  World world = Driver.neWorld;
	// create a JFrame instance to contain the JPanel
	public  JFrame window = new JFrame();
	// MAP SIZE DRAW SETTINGS
	public  int IMAGE_SIZE 		= setImageSize();
	public  int WINDOW_HEIGHT 	= setWindowSize(IMAGE_SIZE) ;

	public World_Graphics() {
		world.initializeFire();
		initTimer();
		// mouse clicks to place bombs
		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent e){
				int column = e.getX() / IMAGE_SIZE;
				int row = e.getY() /  IMAGE_SIZE;
				if (column > 1 && row > 1 && Driver.neWorld.worldMatrix[row][column].getState() == Cell.STATES.TREE){
					//World.worldMatrix[row][column].setState(Cell.STATES.BURNING);
					Bomb.placeBomb(row, column);
				}
			}
		});
		// mouse movement to trail with fire
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				int column = e.getX() / IMAGE_SIZE;
				int row = e.getY() / IMAGE_SIZE;
				if (Driver.neWorld.worldMatrix[row][column].getState() == Cell.STATES.TREE){
					//World.worldMatrix[row][column].setState(Cell.STATES.BURNING);
					Bomb.placeBomb(row, column);
				}
			}
		});

		this.window.add(this);
		this.window.setPreferredSize(getPreferredSize());
		// MAIN WINDOW
		// give it a title bar
		this.window.setTitle("Goobs Fire Sim");
		// set window size to
		this.window.getSize();
		this.window.setSize( WINDOW_HEIGHT, (int)(WINDOW_HEIGHT * 1.045));
		// place window in the middle of the screen, not relative to any other GUI object
		this.window.setLocation(0,0);
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.window.setVisible(true);

	}
	/*
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

	/*
	 * Method to initialize the timer for calling the paint methods
	 */
	public void initTimer() {
		this.timer = new Timer(DELAY, this);
		this.timer.start();
	}

	/*
	 * Method to paint the map and its layers
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		baseMap(g);
		animalMap(g);
	}

	/*
	 * Method to paint the base map (trees, fire, burnt)
	 */
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

	/*
	 * Method to display animals on the map
	 */
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
                    graphics2d.setColor(Color.yellow);graphics2d.fillOval(x, y, this.IMAGE_SIZE, this.IMAGE_SIZE);
                }
                if (currentCell.getObject() == Cell.OBJECTS.WILDLIFEDEAD){
                    // display scaled versions of trees depending on map size
					graphics2d.setColor(Color.red);graphics2d.fillOval(x, y, this.IMAGE_SIZE, this.IMAGE_SIZE);
                }
                x += this.IMAGE_SIZE;
            }
            y += this.IMAGE_SIZE;
            x = 1;
        }
    }
	
	/*
	 * Method to adjust image sizes depending on size of world matrix
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
			return size = (int)(Driver.size / 60);
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

	/*
	 * Method to set window size depending on size of world matrix
	 */
	public int setWindowSize(int IMAGE_SIZE){
		int size;
		size = (Driver.size * IMAGE_SIZE);
		return size;
	}
}
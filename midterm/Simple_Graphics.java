
// import the Graphics class
import java.awt.Graphics;				
// import the Graphics2D class
import java.awt.Graphics2D;				
// Timer kicks out ActionEvents
import java.awt.event.ActionEvent;		
// The class needs to listen for ActionEvents
import java.awt.event.ActionListener;	
// A JPanel is a simple container for graphics
import javax.swing.JPanel;				
// A Timer object kicks out ActionEvents at regular intervals
import javax.swing.Timer;				
// Graphics objects
import java.awt.Color;
// GUI objects to create the window
import javax.swing.JFrame;
// GUI apps should be added to the JRE event queue

// Don't worry too much about "extends JPanel" and "implements ActionListener"
// We will cover that in excruciating detail in the future
public class Simple_Graphics extends JPanel implements ActionListener{
	private final int DELAY = 0;	// delay for ActionListener Timer. Change this to change the speed of the simulation
	// Timer object to control repainting.
	public Timer timer;
	// create a JFrame instance to contain the JPanel	
	private JFrame window = new JFrame();



	/**
	 * Constructor
	 * @param grid - the World object to be rendered
	 */
	public Simple_Graphics() {
		
		// initialize the timer			
		initTimer();	
		// add "this" JPanel to the JFrame			
		window.add(this);	
		// give it a title bar							
		window.setTitle("2D Drawing");
		// how big is the window?					
		window.setSize(420, 450);
		// place window in the middle of the screen, not relative to any other GUI object						
		window.setLocationRelativeTo(null);
		// What happens when you close the window?				
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// make it visible 
		window.setVisible(true);	
						
		}
	
	/**
	 * Method to initialize and start the Timer object
	 */
	private void initTimer() {
		timer = new Timer(DELAY, this);	// create a new Timer object with delay and reference to ActionListener class
		timer.start();					// start the timer
	}

	// method to render the world in Java 2D Graphics
	private void doDrawing(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics; // cast graphics instance to a 2D instance
	
		// cartesian points, to control where rectangles are drawn
		int x = 1, y = 1;
		// height of the rectangle to be drawn
		final int RECT_HEIGHT	= 20; 
		// width of the rectangle to be drawn
		final int RECT_WIDTH	= 20; 
		// nested for loops to iterate through matrix in row major order
		// outer loop processes the number of "rows"
		for(int i = 0; i < World.worldMatrix.length; i++){ 
			// inner loop processes the number of "columns"
			int r = 0, g = 1, b = 2;
			for(int j = 0; j < World.worldMatrix.length; j++){
				String[] rgbArray = World.worldMatrix[i][j].cellColor.split("-");
				int red		= Integer.parseInt(rgbArray[0]);
				int green	= Integer.parseInt(rgbArray[1]);
				int blue 	= Integer.parseInt(rgbArray[2]);
				// using RGB values from above, set the COLOR for this draw
				g2d.setColor(new Color(red, green, blue));	
				// Draw a filled rectangle at x, y of size defined by constants
				g2d.fillRect(x, y, RECT_HEIGHT, RECT_WIDTH);
				x += RECT_WIDTH;	// move x position to the right by "rectangle width"
				r += 3;				
				g += 3;			
				b += 3;				
			} // end inner loop
			y += RECT_HEIGHT;		// move y position down by "rectangle height"
			x = 1;					// reset x to left axis
		} // end outer loop
		
	} // end doDrawing

	@Override // method will be called each time timer "ticks"
	public void actionPerformed(ActionEvent arg0) {
		doDrawing(getGraphics());
	}

}
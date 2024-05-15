
// import the Graphics class
import java.awt.Graphics;
import java.awt.Graphics2D;

// A JPanel is a simple container for graphics
import javax.swing.JPanel;				
// A Timer object kicks out ActionEvents at regular intervals
import javax.swing.Timer;				
// Graphics objects
// GUI objects to create the window
import javax.swing.JFrame;
// GUI apps should be added to the JRE event queue


public class Simple_Graphics extends JPanel{
	// Timer object to control repainting.
	public Timer timer;
	// create a JFrame instance to contain the JPanel	
	private JFrame window = new JFrame();
	public static int RECT_HEIGHT	= 20; 
	public static int RECT_WIDTH	= 20; 


	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawing(g);
	}

	/**
	 * Constructor
	 * @param grid - the World object to be rendered
	 */
	public Simple_Graphics() {
		   	
		// add "this" JPanel to the JFrame			
		window.add(this);	
		// give it a title bar							
		window.setTitle("2D Drawing");
		// how big is the window?					
		window.setSize(Driver.size * 20, Driver.size * 20);
		// place window in the middle of the screen, not relative to any other GUI object						
		window.setLocationRelativeTo(null);			
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);	
						
		}

	// method to render the world in Java 2D Graphics
	public void doDrawing(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		// cartesian points, to control where rectangles are drawn
		int x = 1, y = 1;
		for(int i = 0; i < World.worldMatrix.length; i++){ 
			// inner loop processes the number of "columns"
			for(int j = 0; j < World.worldMatrix.length; j++){
				Cell currentCell = World.worldMatrix[i][j];
				if (currentCell.getState() == Cell.STATES.TREE){
					// display scaled versions of trees depending on map size
					graphics2d.drawImage(currentCell.treeImage, x, y, 20, 20, null);
				}
				x += 20;	// move x position to the right by "rectangle width"	
			} 
			y += 20;		// move y position down by "rectangle height"
			x = 1;						// reset x to left axis
		} 
		this.repaint();
	} 	
}
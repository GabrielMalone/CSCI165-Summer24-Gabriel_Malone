import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * This is essentially a wrapper for a Swing JFrame and a Thread starter, so the
 * user doesn't have to mess with it.
 */
public abstract class SwingApplication {

	private static final int SECONDS_TO_MS = 1000;	// Number of milliseconds per second.
	private final JFrame FRAME;						// JFrame to add content to.
	private Timer timer;							// Timer for updating the JFrame.
	private int fps;								// Frames per second to run the timer
	private int ms;									// Milliseconds to specify the speed of our timer.
	private boolean isRunning = false;				// Boolean to set the timer to run.

	public SwingApplication(int width, int height, int fps, String title) {
		System.setProperty("sun.java2d.opengl", "true");

		// Create the JFrame.
		this.FRAME = new JFrame(title);
		this.FRAME.setSize(width, height);
		this.FRAME.setResizable(false);
		this.FRAME.setLocationRelativeTo(null);
		this.FRAME.setVisible(true);
		this.FRAME.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.FRAME.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				stop();
			}
		});

		this.fps = fps;	// Sets the current FPS and the millisecond update time for timer
		this.ms = SECONDS_TO_MS / fps;

		// Starts the timer.
		SwingUtilities.invokeLater(() -> { this.start(); });
	}

	/**
	 * Adds a Component object to the content pane of this JFrame.
	 * 
	 * @param component	component to add.
	 */
	public void addComponent(Component component) {
		this.FRAME.getContentPane().add(component);
	}

	/**
	 * Sets the location of the frame to the center of the screen, and packs all
	 * components together, updating their dimensions.
	 */
	public void packComponents() {
		this.FRAME.pack();
		this.FRAME.setLocationRelativeTo(null);
	}

	/**
	 * Invokes the thread loop for this swing application. Any code that needs
	 * to be repeated should be placed inside this overridden method.
	 */
	public abstract void run();

	/**
	 * Starts the program if it is not already running.
	 */
	private synchronized void start() {
		if (this.isRunning) return;

		this.isRunning = true;
		this.update();
	}

	/**
	 * Stops and destroys the timer and frame if it is not already stopped.
	 */
	private synchronized void stop() {
		if (!this.isRunning) return;
		
		this.isRunning = false;
		this.timer.stop();
		this.FRAME.dispose();
		System.exit(0);
	}

	/**
	 * Initializes the update loop timer.
	 */
	private void update() {
		this.setupAppTimer();
		this.timer.start();
	}

	/**
	 * Sets up the application and render timer.
	 */
	private void setupAppTimer() {
		this.timer = new Timer(this.ms, timerListener -> {
			this.run();
			this.FRAME.repaint();
		});
	}

	// =================== GETTERS AND SETTERS ====================== //

	/**
	 * 
	 * @param fps	frames per second
	 */
	public void setFPS(int fps) {
		this.fps = fps;
		this.ms = SECONDS_TO_MS / fps;
		this.timer.setDelay(this.ms);
	}

	/**
	 * 
	 * @return	frames per second
	 */
	public int getFPS() {
		return this.fps;
	}

	/**
	 * 
	 * @return whether it is running or not
	 */
	public boolean isRunning() {
		return this.isRunning;
	}

	/**
	 * 
	 * @param running set run status
	 */
	public void setRunning(boolean running) {
		this.isRunning = running;
	}

	/**
	 * 
	 * @return	frame visibility
	 */
	public boolean isVisible() {
		return this.FRAME.isVisible();
	}

	/**
	 * 
	 * @param visible	set frames visibility
	 */
	public void setVisible(boolean visible) {
		this.FRAME.setVisible(true);
	}

	/**
	 * 
	 * @return the JFrame
	 */
	public JFrame getFrame() {
		return this.FRAME;
	}
}

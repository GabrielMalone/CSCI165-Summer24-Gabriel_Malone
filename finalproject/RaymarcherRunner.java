

/**
 * RaymarcherRunner is the driver class where the JPanel
 * is initialized and instantiated.
 * 
 * This class will be run to being the program
 */
public class RaymarcherRunner extends SwingApplication {
	
	private static final int WIDTH		= 640;			// Width of the JFrame.
	private static final int HEIGHT		= 640;			// Height of the JFrame.
	private static final int TARGET_FPS = 60;			// The frames-per-second for the app.
	private static final String TITLE	= "Raymarcher";	// Title of the JFrame.
	
	public RaymarcherRunner(int width, int height, int fps, String title) {
		super(width, height, fps, title);
		// instantiate and add the raymarcher panel.
		RaymarcherPanel raymarcherPanel = new RaymarcherPanel(this);
		this.addComponent(raymarcherPanel);
		this.packComponents();
		this.setVisible(true);
	}
	
	@Override
	public void run() {
	}
	
	public static void main(String[] args) {
		RaymarcherRunner runner = new RaymarcherRunner(WIDTH, HEIGHT, TARGET_FPS, TITLE);
		runner.run();
	}
}

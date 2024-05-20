
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Menu extends JPanel implements ActionListener {

	public  JFrame optionsWindow = new JFrame();
    public  JMenuBar menuBar     = new JMenuBar();
	public  JMenuBar startMenu   = new JMenuBar();

	// MENU OPTIONS
	
	//start button
	private JButton start           = new JButton("Start");
	// map menu
	private JMenu map               = new JMenu("Map");
	private JMenu size_option 		= new JMenu("Map Size");
	private JMenuItem size_1 		= new JMenuItem("20");
	private JMenuItem size_2 		= new JMenuItem("50");
	private JMenuItem size_3 		= new JMenuItem("100");
	private JMenuItem size_4 		= new JMenuItem("150");
	private JMenuItem size_5 		= new JMenuItem("200");
	private JMenuItem size_6 		= new JMenuItem("500");
	private JMenu speed 			= new JMenu("Map Speed");
	private JMenuItem slow 			= new JMenuItem("Slow");
	private JMenuItem medium		= new JMenuItem("Medium");
	private JMenuItem fast 			= new JMenuItem("Fast");
	//fire menus
	private JMenu fire 				= new JMenu("Fire");
	private JMenuItem center		= new JMenuItem("Center Start");
	private JMenuItem random		= new JMenuItem("Random Start");
	private JMenu catch_prob 		= new JMenu("Burn Probability");
	// wind menus
	private JMenu wind 				= new JMenu("Wind");
	private JMenuItem wind_on 		= new JMenuItem("Wind On / Off");
	private JMenu wind_direction 	= new JMenu("Wind Direction");
	private JMenuItem north 		= new JMenuItem("North");
	private JMenuItem east 			= new JMenuItem("East");
	private JMenuItem south 		= new JMenuItem("South");
	private JMenuItem west 			= new JMenuItem("West");
	// animal menus
	private JMenu animal            = new JMenu("Animals");
	private JMenuItem animals_on 	= new JMenuItem("Toggle Animals");
	private JMenuItem animals_num 	= new JMenuItem("Starting Pop");
	private JMenuItem animals_move 	= new JMenuItem("Animals Wander");
	private JMenuItem animals_evade = new JMenuItem("Animals Evade Fire");
	private JMenuItem animals_repop = new JMenuItem("Animals Repopulate");
	// mode menus
	private JMenu mode              = new JMenu("Mode");
	private JMenuItem single_run 	= new JMenuItem("Single Run");
	private JMenuItem endless_mode	= new JMenuItem("Endless Mode");



    public Menu(){

		
		optionsWindow.add(this);
		// MAIN WINDOW	
		// give it a title bar							
		optionsWindow.setTitle("FireSim Options");
		// how big is the window?		
		optionsWindow.getSize();			
		optionsWindow.setSize( 200, 200);
		optionsWindow.add(startMenu);
		// place window in the middle of the screen, not relative to any other GUI object						
		optionsWindow.setLocationRelativeTo(null);		
		optionsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		optionsWindow.setVisible(true);
		
        // MENU ACTIONS
        menuBar.add(map);
        map.setMnemonic('M');
        map.add(size_option);
        size_option.add(size_1);
        size_option.add(size_2);
        size_option.add(size_3);
        size_option.add(size_4);
        size_option.add(size_5);
        size_option.add(size_6);
        size_1.addActionListener(this);
        size_2.addActionListener(this);
        size_3.addActionListener(this);
        size_4.addActionListener(this);
        size_5.addActionListener(this);
        size_6.addActionListener(this);

        map.add(speed);
        speed.add(slow);
        speed.add(medium);
        speed.add(fast);
        slow.addActionListener(this);
        medium.addActionListener(this);
        fast.addActionListener(this);

        menuBar.add(fire);
        fire.setMnemonic('F');
        fire.add(center);
        fire.add(random);
        fire.add(catch_prob);
        center.addActionListener(this);
        random.addActionListener(this);
        catch_prob.addActionListener(this);

        menuBar.add(wind);
        wind.setMnemonic('W');
        wind.add(wind_on);
        wind.add(wind_direction);
        wind_direction.add(north);
        wind_direction.add(east);
        wind_direction.add(south);
        wind_direction.add(west);
        north.addActionListener(this);
        east.addActionListener(this);
        south.addActionListener(this);
        west.addActionListener(this);
        
        menuBar.add(animal);
        animal.setMnemonic('A');
        animal.add(animals_on);
        animal.add(animals_num);
        animal.add(animals_move);
        animal.add(animals_evade);
        animal.add(animals_repop);
        animals_on.addActionListener(this);
        animals_num.addActionListener(this);
        animals_move.addActionListener(this);
        animals_evade.addActionListener(this);
        animals_repop.addActionListener(this);

        menuBar.add(mode);
        mode.setMnemonic('O');
        mode.add(single_run);
        mode.add(endless_mode);
        single_run.addActionListener(this);
        endless_mode.addActionListener(this);

        startMenu.add(start);
        start.addActionListener(this);
    }


	@Override
	public void actionPerformed(ActionEvent evt){

		if (evt.getSource() == start){
			Driver.world = new World_Graphics();
		}
		// RUN MODES
		if (evt.getSource() == endless_mode){
		}
		if (evt.getSource() == single_run){

		}

		// MAP SIZES
		if ( evt.getSource() == size_1){
			Driver.size = 20;
		}
		if (evt.getSource() == size_2){
			Driver.size = 50;
		}
		if (evt.getSource() == size_3){
			Driver.size = 100;
		}
		if (evt.getSource() == size_4){
			Driver.size = 150;
		}
		if (evt.getSource() == size_5){
			Driver.size = 200;
		}
		if (evt.getSource() == size_6){
			Driver.size = 500;
		}

		// STEP SPEED
		if (evt.getSource() == slow){
			Driver.speed = 200;
		}
		if (evt.getSource() == medium){
			Driver.speed = 70;
		}
		if (evt.getSource() == fast){
			Driver.speed = 20;
		}

		// FIRE SETTINGS
		if (evt.getSource() == center){
			
		}
		if (evt.getSource() == random){
			
		}
		if (evt.getSource() == catch_prob){
			
		}

		// WIND SETTINGS
		if (evt.getSource() == wind_on){
			
		}
		if (evt.getSource() == north){
			Driver.todaysWeather.setDirection(Weather.DIRECTION.NORTH);
		}
		if (evt.getSource() == east){
			Driver.todaysWeather.setDirection(Weather.DIRECTION.EAST);
		}
		if (evt.getSource() == west){
			Driver.todaysWeather.setDirection(Weather.DIRECTION.WEST);
		}
		if (evt.getSource() == south){
			Driver.todaysWeather.setDirection(Weather.DIRECTION.SOUTH);
		}

		// ANIMAL SETTINGS
		if (evt.getSource() == animals_on){
			
		}
		if (evt.getSource() == animals_num){

		}
		if (evt.getSource() == animals_evade){
			
		}
		if (evt.getSource() == animals_repop){
			
		}
	}
	
}
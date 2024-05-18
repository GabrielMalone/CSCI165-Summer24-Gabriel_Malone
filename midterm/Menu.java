import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenu implements ActionListener {
    
    JMenuBar menuBar        = new JMenuBar();
	
	// MENU OPTIONS
	//start button
	JButton start           = new JButton("Start");
	// map menu
	JMenu map               = new JMenu("Map");
	JMenu size_option 		= new JMenu("Map Size");
	JMenuItem size_1 		= new JMenuItem("20");
	JMenuItem size_2 		= new JMenuItem("50");
	JMenuItem size_3 		= new JMenuItem("100");
	JMenuItem size_4 		= new JMenuItem("150");
	JMenuItem size_5 		= new JMenuItem("200");
	JMenuItem size_6 		= new JMenuItem("500");
	JMenu speed 			= new JMenu("Map Speed");
	JMenuItem slow 			= new JMenuItem("Slow");
	JMenuItem medium		= new JMenuItem("Medium");
	JMenuItem fast 			= new JMenuItem("Fast");
	//fire menus
	JMenu fire 				= new JMenu("Fire");
	JMenuItem center		= new JMenuItem("Center Start");
	JMenuItem random		= new JMenuItem("Random Start");
	JMenu catch_prob 		= new JMenu("Burn Probability");
	// wind menus
	JMenu wind 				= new JMenu("Wind");
	JMenuItem wind_on 		= new JMenuItem("Wind On / Off");
	JMenu wind_direction 	= new JMenu("Wind Direction");
	JMenuItem north 		= new JMenuItem("North");
	JMenuItem east 			= new JMenuItem("East");
	JMenuItem south 		= new JMenuItem("South");
	JMenuItem west 			= new JMenuItem("West");
	// animal menus
	JMenu animal            = new JMenu("Animals");
	JMenuItem animals_on 	= new JMenuItem("Toggle Animals");
	JMenuItem animals_num 	= new JMenuItem("Starting Pop");
	JMenuItem animals_move 	= new JMenuItem("Animals Wander");
	JMenuItem animals_evade = new JMenuItem("Animals Evade Fire");
	JMenuItem animals_repop = new JMenuItem("Animals Repopulate");
	// mode menus
	JMenu mode              = new JMenu("Mode");
	JMenuItem single_run 	= new JMenuItem("Single Run");
	JMenuItem endless_mode	= new JMenuItem("Endless Mode");

    public Menu(){
      
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

        menuBar.add(start);
        start.addActionListener(this);
    }


	@Override
	public void actionPerformed(ActionEvent evt){

		if (evt.getSource() == start){
			Driver.startSequence();
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
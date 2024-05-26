// Gabriel Malone / CS165 / Midterm / Summer 2024

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.KeyEvent;


public class Menu extends JPanel implements ActionListener, ChangeListener{
	// MENU INITIALIZERS
	// for puase / play button
	public Timer timer;
	public boolean paused = false;
	public boolean finished_start_up = false;
	public String pop_number;
	public String weather_initializer = "";

	// main layout
	JFrame optionsWindow 		= new JFrame();
	//start button
	JButton start 				= new JButton("Start");
	// seperators
	JSeparator separator		= new JSeparator();
	JSeparator separator2		= new JSeparator();
	JSeparator separator3		= new JSeparator();
	JSeparator separator4		= new JSeparator();
	JSeparator separator5		= new JSeparator();
	JSeparator separator6		= new JSeparator();
	JSeparator separator7		= new JSeparator();
	JSeparator separator8		= new JSeparator();
	JSeparator separator9		= new JSeparator();
	JSeparator separator10		= new JSeparator();
	JSeparator separator11		= new JSeparator();
	JSeparator separator12		= new JSeparator();
	JSeparator separator13		= new JSeparator();
	JSeparator separator14		= new JSeparator();
	JSeparator separator15		= new JSeparator();
	// fonts
	Font labelfont 				= new Font("SansSerif", Font.PLAIN, 10);
	Font popFont 				= new Font("SansSerif", Font.PLAIN, 10);
	Font infinityFont 			= new Font("SansSerif", Font.PLAIN, 12);
	// pause button
	Icon play_icon				= new ImageIcon("buttons/play.png");
	Icon pause_icon				= new ImageIcon("buttons/pause.png");
	JButton pause 				= new JButton(pause_icon);
	// play button
	JButton	reset_button		= new JButton("End");
	// map combo box
	String [] mapchoices 		= {"200 (Default)", "20", "50", "100", "150", "400", "500", "1000"};
	JComboBox<String> map_box 	= new JComboBox<String>(mapchoices);
	JLabel size_option 			= new JLabel("Map Size (m/sq)");
	// speed slider
	JSlider speed_slider		= new JSlider(0,300);
	double 	speed_perctentage	= (double)speed_slider.getValue();
	String 	speed_string 		= "step delay: " + speed_perctentage + " m/s";
	JLabel 	speed_label			= new JLabel(speed_string);
	// burn rate
	JSlider burn_slider			= new JSlider();
	double burn_percent			= (double)burn_slider.getValue() / 100;
	String burn_slider_string 	= burn_percent + "% burn rate";
	JLabel burn_Label			= new JLabel(burn_slider_string);
	// tree regrow rate
	JSlider tree_Slider			= new JSlider();
	double tree_regrow_percent	= (double)tree_Slider.getValue() / 100;
	String	tree_slider_string 	= tree_regrow_percent + "% tree regrow rate";
	JLabel	tree_slider_Label	= new JLabel(tree_slider_string);
	// animals on/off buttons
	ButtonGroup animals_group 	= new ButtonGroup();
	JRadioButton animals_on 	= new JRadioButton("On");
	JRadioButton animals_off	= new JRadioButton("Off");
	JLabel animals_on_off_label	= new JLabel("Animals");
	// animals wander
	ButtonGroup  wander_group 	= new ButtonGroup();
	JRadioButton wander_on 		= new JRadioButton("On");
	JRadioButton wander_off		= new JRadioButton("Off");
	JLabel wanders_label		= new JLabel("Meander");
	// animal pop
	JSlider animal_pop_slider 	= new JSlider();
	String startng_pop_string 	= "animals: ";
	JLabel animal_pop_label		= new JLabel(startng_pop_string);
	JLabel animal_confirm_label	= new JLabel(startng_pop_string);
	// animal repop
	JSlider animal_repop_slider = new JSlider();
	double animal_grow_percent	= (double)animal_repop_slider.getValue() / 100;
	String animal_repop_String	= "repopulate rate";
	JLabel animal_repop_label 	= new JLabel(animal_repop_String);
	// pop confirmation
	JTextField pop_displayField = new JTextField(String.valueOf(Driver.startingPop), 5);
	// weather on/off
	ButtonGroup weather_group 	= new ButtonGroup();
	JRadioButton wind_on 		= new JRadioButton("On");
	JRadioButton wind_off		= new JRadioButton("Off");
	JLabel wind_on_off_label	= new JLabel("Wind");
	String[] windDirections 	= {"North", "East", "South", "West"};
	JComboBox<String> windBox	= new JComboBox<>(windDirections);
	JLabel wind_direction_Label = new JLabel("Direction");
	// rain on/off
	ButtonGroup rain_group 		= new ButtonGroup();
	JRadioButton rain_on 		= new JRadioButton("On");
	JRadioButton rain_off		= new JRadioButton("Off");
	JLabel rain_Label			= new JLabel("Rain");
	JSlider rain_slider 		= new JSlider();
	int rain_precipt			= rain_slider.getValue();
	String rain_slid_str		= "precipitation rate " + rain_precipt;
	JLabel rain_slider_label 	= new JLabel(rain_slid_str);
	// Modes
	JLabel mode_label			= new JLabel("Modes");
	ButtonGroup mode_Group	 	= new ButtonGroup();
	JRadioButton endless 		= new JRadioButton("\u221e" + " mode");
	JRadioButton single_run		= new JRadioButton("single run");
	// Data
	JLabel data_title 			= new JLabel("DATA");
	JLabel data_pop_title		= new JLabel("Animal Pop:");
	JLabel data_pop_output		= new JLabel("");
	JLabel data_steps_title		= new JLabel("Steps:");
	JLabel data_steps_output	= new JLabel("");
	JLabel data_burn_area_title	= new JLabel("Burn Area:");
	JLabel data_burn_area_output= new JLabel("");

	public Menu(){

		timer = new Timer(1, this);
		timer.start();
		// map combo box
		this.map_box.addActionListener(this);
		this.size_option.setFont(labelfont);
		// speed slider
		this.speed_slider.addChangeListener(this);
		this.speed_slider.setValue(25);
		this.speed_label.setFont(labelfont);
		// animals select
		this.animals_on.addActionListener(this);
		this.animals_off.addActionListener(this);
		this.animals_off.setSelected(true);
		this.animals_on.setFont(labelfont);
		this.animals_off.setFont(labelfont);
		this.animal_confirm_label.setFont(popFont);
		this.animals_group.add(this.animals_on);
		this.animals_group.add(this.animals_off);
		// wanders select
		this.wander_on.addActionListener(this);
		this.wander_off.addActionListener(this);
		this.wander_off.setSelected(true);
		this.wander_off.setEnabled(false);
		this.wander_on.setEnabled(false);
		this.wander_on.setFont(labelfont);
		this.wander_off.setFont(labelfont);
		this.wanders_label.setForeground(Color.GRAY);
		this.wander_group.add(this.wander_on);
		this.wander_group.add(this.wander_off);
		// animal data display
		this.animal_pop_slider.addChangeListener(this);
		this.animal_pop_slider.setValue(0);
		this.animal_pop_slider.setEnabled(false);
		this.animal_confirm_label.setForeground(Color.GRAY);
		// animal repop
		this.animal_repop_slider.addChangeListener(this);
		this.animal_repop_slider.setValue(0);
		this.animal_repop_slider.setEnabled(false);
		this.animal_repop_label.setForeground(Color.GRAY);
		this.animal_repop_label.setFont(popFont);
		// start / pause button
		this.start.addActionListener(this);
		this.pause.addActionListener(this);
		this.pause.setEnabled(false);
		this.reset_button.addActionListener(this);
		this.reset_button.setEnabled(false);
		// weather on / off
		this.wind_on.addActionListener(this);
		this.wind_off.addActionListener(this);
		this.windBox.addActionListener(this);
		this.wind_off.setSelected(true);
		this.weather_group.add(this.wind_on);
		this.weather_group.add(this.wind_off);
		this.wind_direction_Label.setForeground(Color.GRAY);
		this.windBox.setEnabled(false);
		// rain on/off
		this.rain_group.add(this.rain_on);
		this.rain_group.add(this.rain_off);
		this.rain_on.addActionListener(this);
		this.rain_on.setFont(labelfont);
		this.rain_off.addActionListener(this);
		this.rain_off.setSelected(true);
		this.rain_off.setFont(labelfont);
		this.rain_Label.setForeground(Color.BLACK);
		this.rain_slider.addChangeListener(this);
		this.rain_slider.setValue(0);
		this.rain_slider_label.setFont(labelfont);
		this.rain_slider_label.setForeground(Color.GRAY);
		this.rain_slider.setEnabled(false);
		// burn rate slider
		this.burn_slider.addChangeListener(this);
		this.burn_slider.setValue(25);
		this.burn_Label.setFont(labelfont);
		// tree rate slider
		this.tree_Slider.addChangeListener(this);
		this.tree_slider_Label.setFont(labelfont);
		this.tree_Slider.setValue(5);
		this.tree_slider_Label.setForeground(Color.BLACK);
		// modes
		this.endless.addActionListener(this);
		this.single_run.addActionListener(this);
		this.mode_Group.add(endless);
		this.mode_Group.add(single_run);
		this.endless.setSelected(true);
		this.single_run.setFont(infinityFont);
		// data
		this.data_title.setForeground(Color.GRAY);
		this.data_pop_title.setForeground(Color.GRAY);
		this.data_steps_title.setForeground(Color.GRAY);
		this.data_burn_area_title.setForeground(Color.GRAY);
		this.data_burn_area_output.setForeground(Color.GRAY);
		// MAIN WINDOW
		this.optionsWindow.add(this);
		this.optionsWindow.setTitle("Options");
		this.optionsWindow.getSize();
		this.optionsWindow.setSize( 195, 850);
		this.optionsWindow.setLayout(null);
		this.optionsWindow.setLocationRelativeTo(null);
		this.optionsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.optionsWindow.setResizable(false);
		// start button
		this.start.setBounds(10,10,50,20);
		this.pause.setBounds(70, 10, 50, 20);
		this.reset_button.setBounds(130, 10, 50, 20);
		this.separator2.setBounds(0, 35, 200, 10);
		// map size box
		this.size_option.setBounds(90, 50, 150, 20);
		this.map_box.setBounds(5, 70, 180, 25);
		// speed box
		this.speed_label.setBounds(50, 110, 150, 20);
		this.speed_slider.setBounds(5, 130, 180, 25);
		// burn slider
		this.burn_Label.setBounds(65, 160, 150, 20);
		this.burn_slider.setBounds(5, 180, 180, 20);
		// tree slider
		this.tree_slider_Label.setBounds(40, 210, 150, 20);
		this.tree_Slider.setBounds(5, 230, 180, 20);
		// animals section
		this.separator.setBounds(0, 265, 250, 10);
		this.animals_on_off_label.setBounds(30, 285, 150, 20);
		this.separator5.setBounds(30, 300, 50, 10);
		this.separator7.setBounds(110, 300, 50, 10);
		this.animals_on.setBounds(30, 295, 50, 50);
		this.animals_off.setBounds(30, 315, 60, 60);
		this.wanders_label.setBounds(110, 285, 150, 20);
		this.wander_on.setBounds(110, 295, 50, 50);
		this.wander_off.setBounds(110, 315, 60, 60);
		this.separator6.setBounds(30, 355, 50, 10);
		this.separator8.setBounds(110, 355, 50, 10);
		this.animal_confirm_label.setBounds(70, 371, 200, 20);
		this.animal_pop_slider.setBounds(5, 395, 180, 20);
		this.animal_repop_label.setBounds(45, 415, 150, 20);
		this.animal_repop_slider.setBounds(5, 439, 180, 20);
		this.separator3.setBounds(0, 465, 200, 10);
		// wind section
		this.wind_on_off_label.setBounds(40, 487, 150, 20);
		this.wind_on.setBounds(70, 467, 50, 60);
		this.wind_on.setFont(labelfont);
		this.wind_off.setBounds(110, 467, 50, 60);
		this.wind_off.setFont(labelfont);
		this.wind_direction_Label.setFont(labelfont);
		this.wind_direction_Label.setBounds(102, 517, 150, 20);
		this.windBox.setBounds(25, 537, 140, 25);
		this.separator4.setBounds(0, 567, 200, 20);
		// rain section
		this.rain_Label.setBounds(40, 587, 150, 20);
		this.rain_on.setBounds(70, 567, 50, 60);
		this.rain_off.setBounds(110, 567, 60, 60);
		this.rain_slider_label.setBounds(37,620, 150, 20 );
		this.rain_slider.setBounds(5,645, 180, 20);
		this.separator15.setBounds(0, 668, 200, 20);
		// modes
		this.endless.setBounds(15, 674, 90, 30);
		this.single_run.setBounds(90, 674, 90, 30);
		this.separator9.setBounds(0, 700, 200, 20);
		// data
		this.data_title.setBounds(25, 710, 90, 30);
		this.separator10.setBounds(0, 735, 200, 20);
		this.data_steps_title.setBounds(25, 740, 90, 20);
		this.data_steps_output.setBounds(120, 740, 90, 20);
		this.separator11.setBounds(0, 755, 200, 20);
		this.data_pop_title.setBounds(25, 760, 90, 20);
		this.data_pop_output.setBounds(120, 760, 90, 20);
		this.separator12.setBounds(0, 775, 200, 20);
		this.data_burn_area_title.setBounds(25, 780, 90, 20);
		this.data_burn_area_output.setBounds(120, 780, 90, 20);
		this.separator13.setBounds(0, 795, 200, 20);

		// put everything in the frame
		this.optionsWindow.add(this);
		this.optionsWindow.add(start);
		this.optionsWindow.add(pause);
		this.optionsWindow.add(reset_button);
		this.optionsWindow.add(map_box);
		this.optionsWindow.add(size_option);
		this.optionsWindow.add(speed_slider);
		this.optionsWindow.add(speed_label);
		this.optionsWindow.add(burn_Label);
		this.optionsWindow.add(burn_slider);
		this.optionsWindow.add(tree_slider_Label);
		this.optionsWindow.add(tree_Slider);
		this.optionsWindow.add(animals_on_off_label);
		this.optionsWindow.add(animals_on);
		this.optionsWindow.add(animals_off);
		this.optionsWindow.add(wanders_label);
		this.optionsWindow.add(animal_pop_label);
		this.optionsWindow.add(wander_on);
		this.optionsWindow.add(wander_off);
		this.optionsWindow.add(animal_pop_slider);
		this.optionsWindow.add(animal_confirm_label);
		this.optionsWindow.add(wind_on_off_label);
		this.optionsWindow.add(wind_on);
		this.optionsWindow.add(wind_off);
		this.optionsWindow.add(wind_direction_Label);
		this.optionsWindow.add(windBox);
		this.optionsWindow.add(mode_label);
		this.optionsWindow.add(endless);
		this.optionsWindow.add(single_run);
		this.optionsWindow.add(data_pop_output);
		this.optionsWindow.add(data_pop_title);
		this.optionsWindow.add(data_title);
		this.optionsWindow.add(data_steps_title);
		this.optionsWindow.add(data_steps_output);
		this.optionsWindow.add(data_burn_area_title);
		this.optionsWindow.add(data_burn_area_output);
		this.optionsWindow.add(animal_repop_label);
		this.optionsWindow.add(animal_repop_slider);
		this.optionsWindow.add(rain_Label);
		this.optionsWindow.add(rain_slider_label);
		this.optionsWindow.add(rain_off);
		this.optionsWindow.add(rain_slider);
		this.optionsWindow.add(rain_on);
		this.optionsWindow.add(separator);
		this.optionsWindow.add(separator2);
		this.optionsWindow.add(separator3);
		this.optionsWindow.add(separator4);
		this.optionsWindow.add(separator5);
		this.optionsWindow.add(separator6);
		this.optionsWindow.add(separator7);
		this.optionsWindow.add(separator8);
		this.optionsWindow.add(separator9);
		this.optionsWindow.add(separator10);
		this.optionsWindow.add(separator11);
		this.optionsWindow.add(separator12);
		this.optionsWindow.add(separator13);
		this.optionsWindow.add(separator14);
		this.optionsWindow.add(separator15);
		this.optionsWindow.setVisible(true);

	}

	public void keyPressed (KeyEvent e){
		System.out.println("test");
	}

	public void stateChanged(ChangeEvent e){
		if (e.getSource() == burn_slider){
			this.burn_percent = (double)this.burn_slider.getValue() / 100;
			Driver.catchprobability = this.burn_percent;
			// after world initialized update directly
			if (this.finished_start_up) Driver.neWorld.catchprobability = this.burn_percent;
			this.burn_slider_string = (int)(burn_percent * 100) + "% burn rate";
			this.burn_Label.setText(this.burn_slider_string);

		}
		else if (e.getSource() == speed_slider){
			this.speed_perctentage = (double)this.speed_slider.getValue();
			Driver.speed = (int)this.speed_perctentage;
			// after graphics initialized update directly
			if (this.finished_start_up) Driver.world.DELAY = (int)this.speed_perctentage;
			this.speed_string = "step delay: " + (int)(this.speed_perctentage)  + " m/s";
			this.speed_label.setText(this.speed_string);
		}
		else if (e.getSource() == animal_pop_slider){
			this.pop_number = String.valueOf(this.animal_pop_slider.getValue() * (Driver.size));
			Driver.startingPop = this.animal_pop_slider.getValue() * Driver.size;
			this.animal_confirm_label.setText(this.startng_pop_string + this.pop_number);
	
		if (this.finished_start_up){
			Driver.neWorld.wildlife.clearAnimals();
			Driver.neWorld.wildlife.placeWildlife();
			}
		}
		else if (e.getSource() == tree_Slider){
			this.tree_regrow_percent = (double)this.tree_Slider.getValue();
			Driver.chanceToRegrow 	 = (double)this.tree_Slider.getValue() / 1000;
			this.tree_slider_string  = (int)this.tree_Slider.getValue() + "% tree regrow rate";
			this.tree_slider_Label.setText(tree_slider_string);
		}
		else if (e.getSource() == animal_repop_slider){
			this.animal_grow_percent = (double)this.animal_repop_slider.getValue();
			Driver.popRegrowth 		 = (double)this.animal_repop_slider.getValue() / 10000;
			this.animal_repop_String = "repopulation rate " + (int)this.animal_repop_slider.getValue() + "%";
			this.animal_repop_label.setText(animal_repop_String);
		}
		else if (e.getSource() == rain_slider){
			Driver.startingRain 	 = (this.rain_slider.getValue() * Driver.size);
			this.rain_slid_str		 = "precipitation rate " + this.rain_slider.getValue() * Driver.size;
			this.rain_slider_label.setText(rain_slid_str);
			if (this.finished_start_up){
				Driver.neWorld.todaysRain.dryTheEarth();
				Driver.neWorld.todaysRain.letItRain();
				}
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt){
		// live data updates with timer
		if (finished_start_up){
			this.data_pop_output.setText(String.valueOf((int)Driver.neWorld.totalAlive()));
			if (! paused)
			this.data_steps_output.setText(String.valueOf(Driver.neWorld.trackSteps()));
			this.data_burn_area_output.setText(String.valueOf((int)Driver.neWorld.burnPercentage()) + "%");

		}

		// start button action
		if (evt.getSource() == start){
			this.start.setEnabled(false);
			this.reset_button.setEnabled(true);
			this.pause.setEnabled(true);
			this.map_box.setEnabled(false);
			this.size_option.setForeground(Color.GRAY);
			this.speed_slider.setEnabled(true);
			this.speed_label.setForeground(Color.BLACK);
			this.endless.setEnabled(false);
			this.single_run.setEnabled(false);
			this.data_title.setForeground(Color.BLACK);
			this.data_pop_title.setForeground(Color.BLACK);
			this.data_steps_title.setForeground(Color.BLACK);
			this.data_burn_area_title.setForeground(Color.BLACK);
			this.data_burn_area_output.setForeground(Color.BLACK);
			if (! Driver.endlessMode){
				this.endless.setEnabled(false);
				this.burn_slider.setEnabled(false);
				this.animals_off.setEnabled(false);
				this.animals_on.setEnabled(false);
				this.wander_off.setEnabled(false);
				this.wander_on.setEnabled(false);
				this.wind_on_off_label.setForeground(Color.GRAY);
				this.wind_off.setEnabled(false);
				this.wind_on.setEnabled(false);
				this.animals_on_off_label.setForeground(Color.GRAY);
				this.wanders_label.setForeground(Color.GRAY);
				this.animal_pop_label.setForeground(Color.GRAY);
			}

			Driver.size = Driver.worldResize(Driver.size);
			Driver.popRegrowth = Driver.startingPop / 4;
			if (Driver.size == 21)
				Driver.chanceToRegrow = .05;
			Driver.neWorld = new World();
			Driver.world = new World_Graphics();
			if (this.weather_initializer.equals("NORTH")){
				Driver.neWorld.todaysWind.setDirection(Wind.DIRECTION.NORTH);
				
			}
			if (this.weather_initializer.equals("SOUTH")){
				Driver.neWorld.todaysWind.setDirection(Wind.DIRECTION.SOUTH);
		
			}
			if (this.weather_initializer.equals("EAST")){
				Driver.neWorld.todaysWind.setDirection(Wind.DIRECTION.EAST);
			
			}
			if (this.weather_initializer.equals("WEST")){
				Driver.neWorld.todaysWind.setDirection(Wind.DIRECTION.WEST);

			}
			this.finished_start_up = true;

		}
		// pause button action
		else if (evt.getSource() == pause){

			if (! this.paused){
				this.paused = true;
				Driver.world.timer.stop();
				this.pause.setIcon(play_icon);
			}
			else {
				this.paused = false;
				this.pause.setIcon(pause_icon);
				Driver.world.timer.start();
			}

		}
		// play button action
		else if (evt.getSource() == reset_button){

			Driver.world.window.dispose();
			Driver.world.window.setVisible(false);
			Driver.world.timer.stop();
			Driver.neWorld = new World();
			this.start.setEnabled(true);
			this.map_box.setEnabled(false);
			this.size_option.setForeground(Color.GRAY);
			this.speed_slider.setEnabled(false);
			this.speed_label.setForeground(Color.GRAY);
			this.start.setEnabled(true);
			this.reset_button.setEnabled(false);
			this.pause.setEnabled(false);
			this.map_box.setEnabled(true);
			this.size_option.setForeground(Color.BLACK);
			this.speed_slider.setEnabled(true);
			this.speed_label.setForeground(Color.BLACK);
			this.paused = false;
			this.pause.setIcon(pause_icon);
			this.finished_start_up = false;
			this.endless.setEnabled(true);
			this.single_run.setEnabled(true);
			this.burn_slider.setEnabled(true);
			Driver.startingPop = 0;
			this.animal_pop_label.setText("Starting pop: " + Driver.startingPop);
			this.animals_on.setEnabled(true);
			this.animals_off.setEnabled(true);
			this.animals_off.setSelected(true);
			this.wind_on.setEnabled(true);
			this.wind_off.setEnabled(true);
			this.wind_off.setSelected(true);
			this.windBox.setEnabled(false);
			this.rain_slider.setEnabled(false);
			this.rain_slider_label.setForeground(Color.GRAY);
			this.rain_off.setSelected(true);
			Driver.startingRain = 0;
		}

		// map combo box actions
		else if (evt.getSource() == map_box){
			int index = map_box.getSelectedIndex();
			switch (index) {
				case 0:	Driver.size = 200;
					break;
				case 1: Driver.size = 20;
					break;
				case 2: Driver.size = 50;
					break;
				case 3: Driver.size = 100;
					break;
				case 4: Driver.size = 150;
					break;
				case 5: Driver.size = 400;
					break;
				case 6: Driver.size = 500;
					break;
				case 7: Driver.size = 1000;
					break;
				default:
					Driver.size = 200;
					break;
			}
		}
		// animal radio buttons
		else if (evt.getSource() == animals_on){
			Driver.animalsOn = true;
			this.wander_on.setEnabled(true);
			this.wander_off.setEnabled(true);
			this.wanders_label.setForeground(Color.BLACK);
			this.animal_pop_label.setForeground(Color.BLACK);
			this.animal_confirm_label.setForeground(Color.BLACK);
			this.pop_displayField.setForeground(Color.BLACK);
			this.animal_pop_slider.setEnabled(true);
			this.animal_repop_label.setForeground(Color.BLACK);
			this.animal_repop_slider.setEnabled(true);
			if (finished_start_up) Driver.neWorld.wildlife.placeWildlife();
			}
		else if (evt.getSource() == animals_off){
			Driver.animalsOn = false;
			this.wander_on.setEnabled(false);
			this.wander_off.setEnabled(false);
			this.wanders_label.setForeground(Color.GRAY);
			this.animal_pop_label.setForeground(Color.GRAY);
			this.animal_pop_slider.setEnabled(false);
			this.animal_pop_slider.setValue(0);
			if (this.finished_start_up) Driver.neWorld.wildlife.clearAnimals();
			Driver.startingPop = 0;
			this.animal_pop_label.setText("Starting pop: " + Driver.startingPop);
			}
		// animal radio buttons
		else if (evt.getSource() == wander_on){
			Driver.animalsWander = true;
			}
		else if (evt.getSource() == wander_off){
			Driver.animalsWander = false;
			}
		else if (evt.getSource() == wind_on){
			this.windBox.setEnabled(true);
			this.windBox.setSelectedIndex(0);
			if (finished_start_up){
				Driver.neWorld.todaysWind.clearWeatherPattern();
				Driver.neWorld.todaysWind.setDirection(Wind.DIRECTION.NORTH);
				Driver.neWorld.todaysWind.windOn();
				Driver.weatherOn = true;
			}
			else{
				this.weather_initializer = "NORTH";
			}
			this.wind_direction_Label.setForeground(Color.BLACK);
		}
		else if (evt.getSource() == wind_off){
			if (finished_start_up){
				Driver.neWorld.todaysWind.clearWeatherPattern();
				Driver.weatherOn = false;
			}
			this.windBox.setEnabled(false);
			this.wind_direction_Label.setForeground(Color.GRAY);
			}
		else if (evt.getSource() == windBox){
			int index = windBox.getSelectedIndex();
			switch (index){
				case 0:		if (! finished_start_up) this.weather_initializer = "NORTH";
							else{
								Driver.neWorld.todaysWind.clearWeatherPattern();
								Driver.neWorld.todaysWind.setDirection(Wind.DIRECTION.NORTH);
								Driver.neWorld.todaysWind.windOn();
							}
							break;
				case 1:		if (! finished_start_up) this.weather_initializer = "EAST";
							else{
								Driver.neWorld.todaysWind.clearWeatherPattern();
								Driver.neWorld.todaysWind.setDirection(Wind.DIRECTION.EAST);
								Driver.neWorld.todaysWind.windOn();
							}
							break;
				case 2:		if (! finished_start_up) this.weather_initializer = "SOUTH";
							else{
								Driver.neWorld.todaysWind.clearWeatherPattern();
								Driver.neWorld.todaysWind.setDirection(Wind.DIRECTION.SOUTH);
								Driver.neWorld.todaysWind.windOn();
							}
							break;
				case 3:		if (! finished_start_up) this.weather_initializer = "WEST";
							else{
								Driver.neWorld.todaysWind.clearWeatherPattern();
								Driver.neWorld.todaysWind.setDirection(Wind.DIRECTION.WEST);
								Driver.neWorld.todaysWind.windOn();
							}
							break;
			}
		}
		else if (evt.getSource() == rain_on){
			rain_slider_label.setForeground(Color.BLACK);
			rain_slider.setEnabled(true);
			Driver.rainOn = true;
		}
		else if (evt.getSource() == rain_off){
			rain_slider_label.setForeground(Color.GRAY);
			rain_slider.setEnabled(false);
			Driver.rainOn = false;
			Driver.neWorld.todaysRain.dryTheEarth();
		}
		else if (evt.getSource() == endless){
			Driver.endlessMode = true;
		}
		else if (evt.getSource() == single_run){
			Driver.endlessMode = false;

		}
	}
}
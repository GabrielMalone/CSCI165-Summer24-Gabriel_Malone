
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

public class Menu extends JPanel implements ActionListener, ChangeListener{
	// MENU INITIALIZERS
	// for puase / play button
	public boolean paused = false;
	public boolean finished_start_up = false;
	public String pop_number;
	// main layout
	JFrame optionsWindow 		= new JFrame();
	//start button
	JButton start 				= new JButton("Start");
	// seperators
	JSeparator separator		= new JSeparator();
	JSeparator separator2		= new JSeparator();
	JSeparator separator3		= new JSeparator();
	JSeparator separator4		= new JSeparator();
	// fonts
	Font labelfont 				= new Font("SansSerif", Font.PLAIN, 10);
	Font popFont 				= new Font("SansSerif", Font.BOLD, 10);
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
	JSlider speed_slider		= new JSlider(0,1000);
	double 	speed_perctentage	= (double)speed_slider.getValue();
	String 	speed_string 		= "step delay: " + speed_perctentage + " m/s";
	JLabel 	speed_label			= new JLabel(speed_string);
	// Burn rate
	JSlider burn_slider			= new JSlider();
	double burn_percent			= (double)burn_slider.getValue() / 100;
	String burn_slider_string 		= burn_percent + "% burn rate";
	JLabel burn_Label			= new JLabel(burn_slider_string);
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
	JButton set_pop_button		= new JButton("Set");
	JTextField animal_pop_Field	= new JTextField("",5);
	JLabel animal_pop_label		= new JLabel("Enter starting population");
	String startng_pop_string 	= "Starting pop: ";
	JLabel animal_confirm_label	= new JLabel(startng_pop_string);
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
	
    public Menu(){
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
		// animal pop input
		this.animal_pop_label.setForeground(Color.GRAY);
		this.animal_pop_Field.addActionListener(this);
		this.set_pop_button.addActionListener(this);
		this.set_pop_button.setEnabled(false);
		this.animal_pop_Field.setBackground(Color.GRAY);
		this.animal_pop_Field.setEnabled(false);
		this.animal_pop_label.setFont(labelfont);
		// animal data display
		this.pop_displayField.setEnabled(true);
		this.pop_displayField.setForeground(Color.GRAY);
		this.pop_displayField.setBackground(Color.GRAY);
		this.pop_displayField.setEditable(false);
		this.animal_confirm_label.setForeground(Color.GRAY);
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
		// burn rate slider
		this.burn_slider.addChangeListener(this);
		this.burn_slider.setValue(25);
		this.burn_Label.setFont(labelfont);
		// MAIN WINDOW	
		this.optionsWindow.add(this);		
		this.optionsWindow.setTitle("Options");
		this.optionsWindow.getSize();			
		this.optionsWindow.setSize( 195, 700);
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
		this.burn_Label.setBounds(65, 200, 150, 20);
		this.burn_slider.setBounds(5, 180, 180, 20);
		// animals section
		this.separator.setBounds(0, 220, 200, 10);
		this.animals_on_off_label.setBounds(30, 230, 150, 20);
		this.animals_on.setBounds(30, 240, 50, 50);
		this.animals_off.setBounds(30, 260, 60, 60);
		this.wanders_label.setBounds(110, 230, 150, 20);
		this.wander_on.setBounds(110, 240, 50, 50);
		this.wander_off.setBounds(110, 260, 60, 60);
		this.animal_pop_label.setBounds(33, 310, 150, 20);
		this.animal_pop_Field.setBounds(30, 330, 90, 20);
		this.set_pop_button.setBounds(120, 331, 40, 19);
		this.animal_confirm_label.setBounds(33, 351, 200, 20);
		this.separator3.setBounds(0, 370, 200, 10);
		// wind section
		this.wind_on_off_label.setBounds(40, 390, 150, 20);
		this.wind_on.setBounds(70, 370, 50, 60);
		this.wind_on.setFont(labelfont);
		this.wind_off.setBounds(110, 370, 50, 60);
		this.wind_off.setFont(labelfont);
		this.wind_direction_Label.setFont(labelfont);
		this.wind_direction_Label.setBounds(102, 420, 150, 20);
		this.windBox.setBounds(25, 440, 140, 25);
		this.separator4.setBounds(0, 470, 200, 20);
		
		// put everything in the frame
		this.optionsWindow.add(start);
		this.optionsWindow.add(pause);
		this.optionsWindow.add(reset_button);
		this.optionsWindow.add(map_box);
		this.optionsWindow.add(size_option);
		this.optionsWindow.add(speed_slider);
		this.optionsWindow.add(speed_label);
		this.optionsWindow.add(burn_Label);
		this.optionsWindow.add(burn_slider);
		this.optionsWindow.add(animals_on_off_label);
		this.optionsWindow.add(animals_on);
		this.optionsWindow.add(animals_off);
		this.optionsWindow.add(wanders_label);
		this.optionsWindow.add(animal_pop_label);
		this.optionsWindow.add(wander_on);
		this.optionsWindow.add(wander_off);
		this.optionsWindow.add(animal_pop_Field);
		this.optionsWindow.add(pop_displayField);
		this.optionsWindow.add(set_pop_button);
		this.optionsWindow.add(animal_confirm_label);
		this.optionsWindow.add(wind_on_off_label);
		this.optionsWindow.add(wind_on);
		this.optionsWindow.add(wind_off);
		this.optionsWindow.add(wind_direction_Label);
		this.optionsWindow.add(windBox);
		this.optionsWindow.add(separator);
		this.optionsWindow.add(separator2);
		this.optionsWindow.add(separator3);
		this.optionsWindow.add(separator4);
		this.optionsWindow.setVisible(true);

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
	}

	@Override
	public void actionPerformed(ActionEvent evt){
		// start button actio
		if (evt.getSource() == start){
			this.start.setEnabled(false);
			this.reset_button.setEnabled(true);
			this.pause.setEnabled(true);
			this.map_box.setEnabled(false);
			this.size_option.setForeground(Color.GRAY);
			this.speed_slider.setEnabled(true);
			this.speed_label.setForeground(Color.BLACK);
			this.finished_start_up = true;

			Driver.size = Driver.worldResize(Driver.size);
			Driver.popRegrowth = Driver.startingPop / 4; 
			if (Driver.size == 21)   
				Driver.chanceToRegrow = .05;
			Driver.neWorld = new World(); 
			Driver.world = new World_Graphics();   
		
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
					break;
			}
		} 
		// animal radio buttons
		else if (evt.getSource() == animals_on){
			Driver.animalsOn = true;
			this.wander_on.setEnabled(true);
			this.wander_off.setEnabled(true);
			this.wanders_label.setForeground(Color.BLACK);
			this.animal_pop_Field.setEnabled(true);
			this.animal_pop_Field.setBackground(Color.WHITE);
			this.set_pop_button.setEnabled(true);
			this.animal_pop_label.setForeground(Color.BLACK);
			Driver.neWorld.wildlife.placeWildlife();
			}
		else if (evt.getSource() == animals_off){
			Driver.animalsOn = false;
			this.wander_on.setEnabled(false);
			this.wander_off.setEnabled(false);
			this.wanders_label.setForeground(Color.GRAY);
			this.animal_pop_Field.setEnabled(false);
			this.animal_pop_Field.setBackground(Color.GRAY);
			this.set_pop_button.setEnabled(false);
			this.animal_pop_label.setForeground(Color.GRAY);
			Driver.neWorld.wildlife.clearAnimals();
			}
		// animal radio buttons
		else if (evt.getSource() == wander_on){
			Driver.animalsWander = true;
			}
		else if (evt.getSource() == wander_off){
			Driver.animalsWander = false;
			}
		// animal pop field	
		else if (evt.getSource() == set_pop_button){
			pop_number = animal_pop_Field.getText();
				Driver.startingPop = Integer.valueOf(pop_number);
				Driver.popRegrowth = Integer.valueOf(pop_number);
			if (this.finished_start_up){
				Driver.neWorld.wildlife.clearAnimals();
				Driver.neWorld.wildlife.placeWildlife();
				
			}
			this.animal_pop_Field.setText("");
			this.pop_displayField.setForeground(Color.WHITE);
			this.pop_displayField.setText(String.valueOf(Driver.startingPop));
			this.animal_confirm_label.setText("Starting pop: " + pop_number);

		}
		else if (evt.getSource() == wind_on){
			this.windBox.setEnabled(true);
			this.wind_direction_Label.setForeground(Color.BLACK);
			Driver.neWorld.weatherSet = true;
			}
		else if (evt.getSource() == wind_off){
			this.windBox.setEnabled(false);
			this.wind_direction_Label.setForeground(Color.GRAY);
			Driver.weatherOn = false;
			Driver.neWorld.todaysWeather.clearWeatherPattern();
			}
		else if (evt.getSource() == windBox){
			int index = windBox.getSelectedIndex();
			switch (index){
				case 0:		Driver.neWorld.todaysWeather.clearWeatherPattern();
							Driver.todaysWeather.setDirection(Weather.DIRECTION.NORTH);
							Driver.neWorld.weatherSet = true;
							break;
				case 1:		Driver.neWorld.todaysWeather.clearWeatherPattern();
							Driver.todaysWeather.setDirection(Weather.DIRECTION.EAST);
							Driver.neWorld.weatherSet = true;
							break;
				case 2:		Driver.neWorld.todaysWeather.clearWeatherPattern();
							Driver.todaysWeather.setDirection(Weather.DIRECTION.SOUTH);
							Driver.neWorld.weatherSet = true;
							break;
				case 3:		Driver.neWorld.todaysWeather.clearWeatherPattern();
							Driver.todaysWeather.setDirection(Weather.DIRECTION.WEST);
							Driver.neWorld.weatherSet = true;
							break;
			}
		}
	}

}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Graphics;

import java.awt.*;  

public class Menu extends JPanel implements ActionListener {
	
	JFrame optionsWindow 		= new JFrame();
	//start button
	JPanel startMenu 			= new JPanel(new FlowLayout());
	JButton start 				= new JButton("Start");
	// map combo box
	String [] mapchoices = {
		"200 (Default)", "20", "50", "100", 
		"150", "400", "500", "1000"
		};
	JComboBox<String> map_box 	= new JComboBox<String>(mapchoices);
	JPanel map_panel 			= new JPanel(new FlowLayout());
	JLabel size_option 			= new JLabel("Map Size (m/sq)");
	// speed combo box
	String [] speedchoices = {
		"20  (Default)", "50", "100", "150", 
		"200","500", "1000"
		};
	JComboBox<String> speed_box = new JComboBox<String>(speedchoices);
	JPanel speed_panel 			= new JPanel(new FlowLayout());
	JLabel speed_options 		= new JLabel("Sim Speed (ms)  ");
	// animals on/off buttons
	ButtonGroup animals_group 	= new ButtonGroup();
	JRadioButton animals_on 	= new JRadioButton("On");
	JRadioButton animals_off	= new JRadioButton("Off");
	JPanel animals_panel		= new JPanel();
	JLabel animals_on_off_label	= new JLabel("Animals            ");
	// animals wander
	ButtonGroup  wander_group 	= new ButtonGroup();
	JRadioButton wander_on 		= new JRadioButton("On");
	JRadioButton wander_off		= new JRadioButton("Off");
	JPanel wanders_panel		= new JPanel();
	JLabel wanders_label		= new JLabel("Animals Wander");
	// animal pop
	JPanel animal_pop_panel 	= new JPanel();
	JButton set_pop_button		= new JButton("Set");
	JTextField animal_pop_Field	= new JTextField("",5);
	JLabel animal_pop_label		= new JLabel("Starting Pop     ");

	// pop confirmation
	JPanel pop_confirm_panel 	= new JPanel();
	JLabel confirm_label		= new JLabel("                        ");
	JTextField pop_displayField = new JTextField(String.valueOf(Driver.startingPop), 5);
	
	
	

    public Menu(){
        // MENU ACTIONS
		// construct 	map combo box
		this.map_box.addActionListener(this);
		this.map_panel.add(this.size_option);
        this.map_panel.add(this.map_box);
		this.map_panel.setOpaque(false);
		// construct speed combo box
		this.speed_box.addActionListener(this);
		this.speed_panel.add(this.speed_options);
		this.speed_panel.add(this.speed_box);
		this.speed_panel.setOpaque(false);
		// animals select
		this.animals_on.addActionListener(this);
		this.animals_off.addActionListener(this);
		this.animals_off.setSelected(true);
		this.animals_panel.add(this.animals_on_off_label);
		this.animals_group.add(this.animals_on);
		this.animals_group.add(this.animals_off);
		this.animals_panel.add(this.animals_on);
		this.animals_panel.add(this.animals_off);
		// wanders select
		this.wander_on.addActionListener(this);
		this.wander_off.addActionListener(this);
		this.wander_off.setSelected(true);
		this.wander_off.setEnabled(false);
		this.wander_on.setEnabled(false);
		this.wanders_label.setForeground(Color.GRAY);
		this.wanders_panel.add(this.wanders_label);
		this.wander_group.add(this.wander_on);
		this.wander_group.add(this.wander_off);
		this.wanders_panel.add(this.wander_on);
		this.wanders_panel.add(this.wander_off);
		// animal pop input
		this.animal_pop_Field.addActionListener(this);
		this.set_pop_button.addActionListener(this);
		this.set_pop_button.setEnabled(false);
		this.animal_pop_Field.setEnabled(false);
		this.animal_pop_label.setForeground(Color.GRAY);
		this.animal_pop_panel.add(this.animal_pop_label);
		this.animal_pop_panel.add(this.animal_pop_Field);
		this.animal_pop_panel.add(set_pop_button);
		
		// animal data display
		this.pop_confirm_panel.add(confirm_label);
		this.pop_displayField.setEnabled(true);
		this.pop_displayField.setForeground(Color.WHITE);
		this.pop_displayField.setBackground(Color.GRAY);
		this.pop_displayField.setEditable(false);
		this.pop_confirm_panel.add(pop_displayField);
		
		// start button
        this.start.addActionListener(this);
		this.size_option.setLabelFor(this.map_box);
		// add all of the above to JFrame
		this.startMenu.add(this.map_panel);
		this.startMenu.add(this.speed_panel);
		this.startMenu.add(this.animals_panel);
		this.startMenu.add(this.wanders_panel);
		this.startMenu.add(this.animal_pop_panel);
		this.startMenu.add(this.pop_confirm_panel);
		this.startMenu.add(this.start);
		// MAIN WINDOW	
		this.optionsWindow.add(this);						
		this.optionsWindow.setTitle("Options");
		this.optionsWindow.getSize();			
		this.optionsWindow.setSize( 260, 500);
		this.optionsWindow.setLocationRelativeTo(null);	
		this.optionsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.startMenu.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		this.optionsWindow.add(this.startMenu);
		this.optionsWindow.setResizable(false);
		this.optionsWindow.setVisible(true);
    }


	@Override
	public void actionPerformed(ActionEvent evt){

		if (evt.getSource() == start){
			Driver.size = Driver.worldResize(Driver.size);
			Driver.popRegrowth = Driver.size / 2; 
			if (Driver.size == 21)   
				Driver.chanceToRegrow = .05;
			Driver.neWorld = new World(); 
			Driver.world = new World_Graphics();   
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
		// speed combo box actions
		else if (evt.getSource() == speed_box){
			int index = speed_box.getSelectedIndex();
			switch (index) {
				case 0:	Driver.speed = 20;
					break;
				case 1: Driver.speed = 50;
					break;
				case 2: Driver.speed = 100;
					break;
				case 3: Driver.speed = 150;
					break;
				case 4: Driver.speed = 200;
					break;
				case 5: Driver.speed = 500;
					break;
				case 6: Driver.speed = 1000;
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
		else if (evt.getSource() == set_pop_button){
			int pop_number = Integer.valueOf(animal_pop_Field.getText());
			Driver.startingPop = pop_number;
			this.animal_pop_Field.setText("");
			this.pop_displayField.setText(String.valueOf(Driver.startingPop));
		}
	}

}
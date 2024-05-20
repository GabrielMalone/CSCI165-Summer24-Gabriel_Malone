
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.*;  

public class Menu extends JPanel implements ActionListener {
	
	JFrame optionsWindow = new JFrame();
	//start button
	JPanel startMenu = new JPanel(new FlowLayout());
	JButton start = new JButton("Start");

	// map combo box
	String [] mapchoices = {
		"200 (Default)", "20", "50", "100", 
		"150", "400", "500", "1000"
		};
	JComboBox<String> map_box 	= new JComboBox<String>(mapchoices);
	JPanel map_panel 		= new JPanel(new FlowLayout());
	JLabel size_option 		= new JLabel("Map Size (m/sq)");

	// speed combo box
	String [] speedchoices = {
		"20  (Default)", "50", "100", "150", 
		"200","500", "1000"
		};
	JComboBox<String> speed_box = new JComboBox<String>(speedchoices);
	JPanel speed_panel 		= new JPanel(new FlowLayout());
	JLabel speed_options 	= new JLabel("Sim Speed (ms)  ");
	
	// animals on/off buttons
	ButtonGroup animals_group 	= new ButtonGroup();
	JRadioButton animals_on 	= new JRadioButton("On");
	JRadioButton animals_off	= new JRadioButton("Off");
	JPanel animals_panel		= new JPanel();
	JLabel animals_on_off_label	= new JLabel("Animals");

	// animals wander
	ButtonGroup  wander_group 	= new ButtonGroup();
	JRadioButton wander_on 		= new JRadioButton("On");
	JRadioButton wander_off		= new JRadioButton("Off");
	JPanel wanders_panel		= new JPanel();
	JLabel wanders_label		= new JLabel("Animals Wander");

    public Menu(){
        // MENU ACTIONS
		// construct 	map combo box
		this.map_box.addActionListener(this);
		this.map_panel.add(size_option);
        this.map_panel.add(map_box);
		this.map_panel.setOpaque(false);
		// construct speed combo box
		this.speed_box.addActionListener(this);
		this.speed_panel.add(speed_options);
		this.speed_panel.add(speed_box);
		this.speed_panel.setOpaque(false);
		// animals select
		this.animals_on.addActionListener(this);
		this.animals_off.addActionListener(this);
		this.animals_panel.add(animals_on_off_label);
		this.animals_group.add(animals_on);
		this.animals_group.add(animals_off);
		this.animals_panel.add(animals_on);
		this.animals_panel.add(animals_off);
		// wanders select
		this.wander_on.addActionListener(this);
		this.wander_off.addActionListener(this);
		this.wanders_panel.add(wanders_label);
		this.wander_group.add(wander_on);
		this.wander_group.add(wander_off);
		this.wanders_panel.add(wander_on);
		this.wanders_panel.add(wander_off);
		// construct start button
        this.start.addActionListener(this);
		this.size_option.setLabelFor(map_box);
		// add above to JFrame
		this.startMenu.add(this.map_panel);
		this.startMenu.add(this.speed_panel);
		this.startMenu.add(this.animals_panel);
		this.startMenu.add(this.wanders_panel);
		this.startMenu.add(this.start);
		// MAIN WINDOW	
		this.optionsWindow.add(this);						
		this.optionsWindow.setTitle("Options");
		this.optionsWindow.getSize();			
		this.optionsWindow.setSize( 260, 500);
		this.optionsWindow.setLocationRelativeTo(null);	
		this.optionsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// start button
		this.startMenu.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		this.optionsWindow.add(startMenu);
		// map size options
		optionsWindow.setVisible(true);
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
		if (evt.getSource() == map_box){
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
		if (evt.getSource() == speed_box){
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
		if (evt.getSource() == animals_on){
			Driver.animalsOn = true;
			}
		if (evt.getSource() == animals_off){
			Driver.animalsOn = false;
			}
		// animal radio buttons
		if (evt.getSource() == wander_on){
			Driver.animalsWander = true;
			}
		if (evt.getSource() == wander_off){
			Driver.animalsWander = false;
			}
	}

}
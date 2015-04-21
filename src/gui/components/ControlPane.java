package gui.components;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.components.ControlPaneController;
/**
 * ControlPane is a part of the MainWindow in the start and the stop button are located.
 * The class contains a start, a stop button (both JButton) and its parent view of type MainWindow.
 * @author julianschwab
 *
 */
public class ControlPane extends JPanel {
	
	
	private static final long serialVersionUID = 1L;

	public MainWindow parent;
	
	JButton start;
	JButton stop;

	ControlPaneController controller;
	
	/**
	 * Constructor creates new ControlPane Object with the MainWindow where its part of
	 * @param parent
	 */
	public ControlPane(MainWindow parent) {
		
		this.parent = parent;
		
		
		/*Initialize buttons and controller*/
		this.start = new JButton("Start");
		this.stop = new JButton("Stop");
		this.stop.setEnabled(false);
		
		this.controller = new ControlPaneController(this, parent.getController());
		
		/*Add start/stop button to view*/
		this.add(start);
		this.add(stop);
		
		/*Register ControlPaneController on buttons*/
		this.start.addActionListener(controller);
		this.stop.addActionListener(controller);

	}
	
	/**
	 * isStartButton checks, if an Object is the startbutton.
	 * This method is needed in Controller to check, where the action was fired
	 * @param component
	 * @return true if Object is this.start, else false
	 */
	public boolean isStartButton(Object component) {
		return this.start.equals(component);
	}
	
	/**
	 * isStopButton checks, if an Object is the stopbutton.
	 * This method is needed in Controller to check, where the action was fired
	 * @param component
	 * @return true if Object is this.stop, else false
	 */
	public boolean isStopButton(Object component) {
		return this.stop.equals(component);
	}
	
	public JButton getStartButton() {
		return start;
	}
	
	public JButton getStopButton() {
		return stop;
	}
	
}

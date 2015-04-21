/**
 * 
 */
package gui.components.resultTabs;

import gui.components.ResultsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.EventListenerList;

import controller.MainController;

/**
 * Menu of the ResultsView window with save, quit and stop run functionality
 * 
 * @author Johannes Schoellhorn
 *
 */
public class ResultsMenu extends JMenuBar implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7284972906245394225L;
	
	MainController main;
	ResultsView parent;
	
	EventListenerList listeners = new EventListenerList();
	
	// File Menu - add new entries here!
	JMenu fileMenu = new JMenu("File");
		JMenu fileMenuSave = new JMenu("Save ...");
			JMenuItem fileMenuSaveData = new JMenuItem("Save Data");
			JMenuItem fileMenuSaveGraph = new JMenuItem("Save Graph");
			JMenuItem fileMenuSaveDetails = new JMenuItem("Save Details");
		JMenuItem fileMenuSaveAll = new JMenuItem("Save All");
		
		JMenuItem fileMenuQuit = new JMenuItem("Quit");
	
	JMenu runMenu = new JMenu("Run");
		JMenuItem runMenuStop = new JMenuItem("Stop");
		
	public ResultsMenu(MainController main, ResultsView parent) {
		this.main = main;
		this.parent = parent;
		
		this.addActionListener(main);
		
		this.initMenu();
	}
	
	/**
	 * Initialise the menu
	 */
	private void initMenu() {
		this.add(fileMenu);
		fileMenu.add(fileMenuSave);
			fileMenuSave.add(fileMenuSaveData);
			fileMenuSaveData.setName("saveData");
			fileMenuSaveData.setEnabled(false);
			fileMenuSaveData.addActionListener(this);
			fileMenuSave.add(fileMenuSaveGraph);
			fileMenuSaveGraph.setName("saveGraph");
			fileMenuSaveGraph.setEnabled(false);
			fileMenuSaveGraph.addActionListener(this);
			fileMenuSave.add(fileMenuSaveDetails);
			fileMenuSaveDetails.setName("saveDetails");
			fileMenuSaveDetails.setEnabled(false);
			fileMenuSaveDetails.addActionListener(this);
		fileMenu.add(fileMenuSaveAll);
		fileMenuSaveAll.setName("saveAll");
		fileMenuSaveAll.setEnabled(false);
		fileMenuSaveAll.addActionListener(this);
		fileMenu.addSeparator();
		fileMenu.add(fileMenuQuit);
		fileMenuQuit.setName("quit");
		fileMenuQuit.addActionListener(this);
		
		
		this.add(runMenu);
			runMenu.add(runMenuStop);
			runMenuStop.setName("stop");
			runMenuStop.addActionListener(this);

	}

	/**
	 * @return the fileMenuSaveData
	 */
	public JMenuItem getFileMenuSaveData() {
		return fileMenuSaveData;
	}

	/**
	 * @return the fileMenuSaveGraph
	 */
	public JMenuItem getFileMenuSaveGraph() {
		return fileMenuSaveGraph;
	}

	/**
	 * @return the fileMenuSaveDetails
	 */
	public JMenuItem getFileMenuSaveDetails() {
		return fileMenuSaveDetails;
	}

	/**
	 * @return the fileMenuSaveAll
	 */
	public JMenuItem getFileMenuSaveAll() {
		return fileMenuSaveAll;
	}

	/**
	 * @return the runMenuStop
	 */
	public JMenuItem getRunMenuStop() {
		return runMenuStop;
	}

	/**
	 * filter actions and invoke appropriate methods
	 */
	public void actionPerformed(ActionEvent e) {
		// stop the run
		if (runMenuStop.equals(e.getSource())) {
			this.notifyActions(new ActionEvent(this,0,"stop"));
		// close the window (invoke a WINDOW_CLOSING event like clicking close)
		} else if (fileMenuQuit.equals(e.getSource())) {
			this.parent.getToolkit().getSystemEventQueue().postEvent(new WindowEvent(this.parent, WindowEvent.WINDOW_CLOSING));
		// save data only
		} else if (fileMenuSaveData.equals(e.getSource())) {
			this.parent.saveDataToFile();
			if (this.parent.isAllSaved()) {
				this.parent.setTitle(this.parent.getResultsData().getFileName());
			}
		} else if (fileMenuSaveGraph.equals(e.getSource())) {
			this.parent.saveGraphToSVG();
			if (this.parent.isAllSaved()) {
				this.parent.setTitle(this.parent.getResultsData().getFileName());
			}
		} else if (fileMenuSaveDetails.equals(e.getSource())) {
			this.parent.saveDetailsToFile();
			if (this.parent.isAllSaved()) {
				this.parent.setTitle(this.parent.getResultsData().getFileName());
			}
		} else if (fileMenuSaveAll.equals(e.getSource())) {
			this.parent.saveAll();
			if (this.parent.isAllSaved()) {
				this.parent.setTitle(this.parent.getResultsData().getFileName());
			}
		}		
	}
	
	// methods to notify the MainController if the Run - Stop menu entry
	// was used
	public void addActionListener(ActionListener listener) {
		this.listeners.add(ActionListener.class, listener);
	}
	
	public void removeActionListener(ActionListener listener) {
		this.listeners.remove(ActionListener.class, listener);
	}
	
	
	protected synchronized void notifyActions(ActionEvent e) {
		for(ActionListener l : listeners.getListeners(ActionListener.class)){
			l.actionPerformed(e);
		}
	}

}

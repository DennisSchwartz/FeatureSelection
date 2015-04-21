/**
 * 
 */
package gui.components;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.MainController;
import controller.components.MenuController;

/**
 * @author Johannes Schoellhorn
 *
 */
public class Menu extends JMenuBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MenuController controller;
	
	public Menu(MainController mainController) {
		super();
		this.controller = new MenuController(this, mainController);
		this.init();
	}
	
	// File Menu - add new entries here!
	JMenu fileMenu = new JMenu("File");
		JMenu fileMenuOpenFile = new JMenu("Open File...");
			JMenuItem fileMenuOpenCSV = new JMenuItem("Open CSV File");
		
		JMenuItem exit = new JMenuItem("Quit");
	
	private void initFileMenu() {
		this.add(fileMenu);
		fileMenu.add(fileMenuOpenFile);
			fileMenuOpenFile.add(fileMenuOpenCSV);
			fileMenuOpenCSV.addActionListener(controller);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		exit.addActionListener(controller);
		
	}
	
	// Help Menu - add new entries here!
	JMenu helpMenu = new JMenu("Help");
		JMenuItem helpMenuAbout = new JMenuItem("About");
	
	private void initHelpMenu() {
		this.add(helpMenu);
		helpMenu.add(helpMenuAbout);
	}
	
	private void init() {
		initFileMenu();
		initHelpMenu();
	}
	
	public boolean isOpenCSVFile(Object o) {
		return this.fileMenuOpenCSV.equals(o);
	}
	
	public boolean isExit(Object o) {
		return this.exit.equals(o);
	}
	
	public MenuController getMenuController() {
		return this.controller;
	}
	
}

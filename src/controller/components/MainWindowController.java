/**
 * 
 */
package controller.components;

import gui.components.MainWindow;
import dataStructures.FeatureData;

/**
 * @author Johannes Schoellhorn
 *
 */
public class MainWindowController {
	
	MainWindow view;
	
	FeatureData data;
	
	public MainWindowController(MainWindow view) {
		this.view = view;
	}
	
	public void setData(FeatureData data) {
		this.data = data;
	}
	
	public void setWindowTitle(String title) {
		this.view.setTitle(title);
	}

}

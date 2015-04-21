/**
 * 
 */
package controller.components;

import gui.components.Components;
import gui.components.Menu;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.zip.DataFormatException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import reader.CSVReader;
import controller.FeatureDataEvent;
import controller.FeatureDataListener;
import controller.MainController;

/**
 * @author Johannes Schoellhorn
 *
 */
public class MenuController implements ActionListener {
	
	// listener field for the main controller
	FeatureDataListener listener;
	
	MainController mainController;
	Menu view;
	
	/**
	 * constructs a new MenuController, setting fields and registering listeners
	 * @param view the view the controller controls
	 * @param mainController reference to the main controller
	 */
	public MenuController(Menu view, MainController mainController) {
		this.mainController = mainController;
		this.view = view;
		this.addFeatureDataListener(mainController);
	}

	/**
	 * main method to filter all the actions which can be performed on
	 * the menu
	 */
	public void actionPerformed(ActionEvent e) {
		
		// action to perform if the OpenCSVFile menu entry was triggered
		if (this.view.isOpenCSVFile(e.getSource())) {
			this.openCSVFile();
		}
		
		// action to perform if the quit menu item is triggered
		if (this.view.isExit(e.getSource())) {
			System.exit(0);
		}	
	}
	
	/**
	 * Open a JFileChooserDialog asking for a file to open
	 * @return FileReader a FileReader Object of the chosen file
	 */
	private File askFile() {
		File res = null;
		JFileChooser fileChooser = new JFileChooser();
		
		//sets the initial directory of the file chooser to the dir of the project
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		int returnVal = fileChooser.showOpenDialog(view);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			res = fileChooser.getSelectedFile();
		}
		
		return res;
	}
	
	/**
	 * Open a Dialog asking for the CSVOptions to use
	 * @return String[] which contains the separator in its first field and
	 * 					and the string indicating a case in its second field
	 */
	private String askOptions() {
		String res;
		
		JPanel csvOptions = new JPanel();
		JTextField separator = new JTextField(";", 5);
		GridBagLayout gbl = new GridBagLayout();
		
		csvOptions.setLayout(gbl);
		
		Components.add(csvOptions, gbl, new JLabel("Column Separator: "),
					   0, 0, 1, 1, 0, 0);
		Components.add(csvOptions, gbl, separator,
					   1, 0, 1, 1, 0, 0);
		
		int returnVal = JOptionPane.showConfirmDialog(view, csvOptions, "Specify .csv Options", JOptionPane.OK_CANCEL_OPTION);
		
		if (returnVal == JOptionPane.OK_OPTION) {
			res = separator.getText();
		} else {
			res = null;
		}
		
		return res;
		
	}
	
	public void openCSVFile() {
		
		// get output stream from the main controller
		PrintStream output = this.mainController.getOutput();
		
		File file = this.askFile();
		
		if (file != null) {
			CSVReader reader = new CSVReader(",");
			
			try {
				output.println("Reader: Trying to read file with default separator \",\".");
				this.notifyFeatureDataUpdated(new FeatureDataEvent(this,
						reader.read(new FileReader(file)), file));
				return;
			} catch (FileNotFoundException e1) {
				output.println("Error: The specified file doesn't exist!");
				return;
			} catch (IOException e1) {
				output.println(e1.getMessage());
				return;
			} catch (DataFormatException e1) {
				output.println("Reader: Default separator not valid for this file.");
				
				String separator = this.askOptions();
				
				if (separator != null) {
					reader = new CSVReader(separator);
					
					try {
						this.notifyFeatureDataUpdated(new FeatureDataEvent(this,
								reader.read(new FileReader(file)), file));
					} catch (FileNotFoundException e2) {
						output.println("Error: The specified file doesn't exist!");
						return;
					} catch (IOException e2) {
						output.println(e1.getMessage());
						return;
					} catch (DataFormatException e2) {
						output.println(e1.getMessage());
						return;
					}
				} else {
					// if you hit cancel on the Options dialog, display warning and don't open file
					output.println("Warning: No file selected! You have to specify a separator!");
				}
			}
		}
	}
	
	public void addFeatureDataListener(FeatureDataListener listener) {
		this.listener = listener;
	}
	
	public void removeFeatureDataListener(FeatureDataListener listener) {
		this.listener = null;
	}
	
	protected synchronized void notifyFeatureDataUpdated(FeatureDataEvent e) {
		this.listener.featureDataUpdated(e);
	}

}

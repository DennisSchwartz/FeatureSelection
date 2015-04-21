package gui.components;

import gui.components.resultTabs.DataTab;
import gui.components.resultTabs.GraphTab;
import gui.components.resultTabs.ResultsMenu;
import gui.components.resultTabs.RunDetailsTab;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileFilter;

import wrapper.WrapperThreadListener;
import controller.MainController;
import dataStructures.ResultsData;

/**
 * Class for the results view window which holds the calculated results.
 * It holds the tabs of the different data generated and implements
 * functionality to save the data.
 * 
 * @author Dennis Schwartz, Johannes Schoellhorn, Julian Schwab
 * 
 */

public class ResultsView extends JFrame implements WrapperThreadListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1339228816670705558L;
	
	ResultsData resultsData;
	MainController main;
	
	JTabbedPane tabs = new JTabbedPane();
	ResultsMenu menu;
	
	// keeping track of saved data
	boolean dataSaved = false;
	boolean graphSaved = false;
	boolean detailsSaved = false;

	public ResultsView(String title) {
		this.setTitle(title);
	}

	public void init(MainController main) {
		this.main = main;
		this.menu = new ResultsMenu(main, this);
		
		// sets window size
		this.setBounds(0, 0, 400, 300);
		
		// initialize tab contents
		DataTab dataTab = new DataTab(this.main);
		GraphTab graphTab = new GraphTab(this.main, this);
		RunDetailsTab runDetails = new RunDetailsTab(this.main.getResultsData().toString());
		
		// add tabs
		this.tabs.addTab("Data", dataTab);
		this.tabs.addTab("Graph", graphTab);
		this.tabs.addTab("Run Details", runDetails);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				ResultsView resView = (ResultsView) winEvt.getSource();

				// dialog asking if you want to save data first
				// but only if data wasn't saved before
				if (resView.resultsData == null) {
					return;
				} else if (!isAllSaved()) {
					int result = JOptionPane.showConfirmDialog(resView,
						"Save Data before closing?", "save ...", JOptionPane.YES_NO_CANCEL_OPTION);
					switch (result) {
					case JOptionPane.YES_OPTION:
						if (saveAll()) {
							this.disposeAll(resView);
						}
						break;
					case JOptionPane.NO_OPTION:
						this.disposeAll(resView);
						break;
					}
				} else {
					this.disposeAll(resView);
				}
			}
			
			/**
			 * dispose all open windows
			 * @param resView
			 */
			private void disposeAll(ResultsView resView) {
				
				((GraphTab) resView.tabs.getComponent(resView.tabs.indexOfTab("Graph"))).getNewWindow().dispose();
				resView.dispose();
				
			}
			
//			public void windowGainedFocus(WindowEvent winEvt) {
//				ResultsView resView = (ResultsView) winEvt.getSource();
//
//				((GraphTab) resView.tabs.getComponent(resView.tabs.indexOfTab("Graph"))).getNewWindow().toFront();
//			}
		});
		
		// add the tabs
		this.add(tabs);
		
		// add menus
		this.menu.add(graphTab.getMenu());
		this.setJMenuBar(this.menu);
		
		this.main.startCountDown();
	}
	
	/**
	 * @return boolean all files saved?
	 */
	public boolean isAllSaved() {
		return this.dataSaved /*&& this.graphSaved*/ && this.detailsSaved;
	}
	
	public ResultsData getResultsData() {
		return this.resultsData;
	}
	
	public void threadFinished() {
		this.resultsData = this.main.getResultsData();
		
		// enable/disable menu options
		this.menu.getFileMenuSaveData().setEnabled(true);
		this.menu.getFileMenuSaveGraph().setEnabled(true);
		this.menu.getFileMenuSaveDetails().setEnabled(true);
		this.menu.getFileMenuSaveAll().setEnabled(true);
		this.menu.getRunMenuStop().setEnabled(false);

		// update details in Run Details tab
		RunDetailsTab runDetails = (RunDetailsTab) this.tabs.getComponent(this.tabs.indexOfTab("Run Details"));
		runDetails.getDetails().setText(this.resultsData.toString());
		runDetails.validate();
		// set title to processed file with * as indicator for not yet saved
		this.setTitle(this.resultsData.getFileName() + "*");
	}

	/**
	 * Opens JFileChooser save dialog to choose the file where the new
	 * .csv file has to be saved
	 * 
	 * @param filter ExtensionFileFilterBase to filter files
	 * @param directory boolean to change between directory and file mode
	 * @return absolute path of file
	 */
	protected File askForSaveFile(ExtensionFileFilterBase filter, boolean directory) {

		JFileChooser fileChooser = new ConfirmingFileChooser(filter);

		// sets the initial directory of the file chooser to the dir of the
		// users home directory
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setMultiSelectionEnabled(false);
		
		fileChooser.setDialogTitle("Choose file ...");
		if (directory) {
			fileChooser.setDialogTitle("Choose directory ...");
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}

		int returnVal = fileChooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			// if the filter was set and enabled, but the extension was not
			// specified, add the extension to the file
			if (filter != null && filter.equals(fileChooser.getFileFilter()) && !filter.accept(file)) {
				return new File(file.getAbsolutePath() + filter.getExtension());
			} else {
				return file;
			}
		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
			return null;
		}

		return null;
	}
	
	/**
	 * saves data from ResultsData to a .csv file
	 */
	public boolean saveDataToFile() {
		// ask for file with csv filter
		File file = this.askForSaveFile(new CSVFileFilter(), false);
		// Creates new csv-File
		try {
			if (file != null) {
				ResultsData.tableModeltoCSV(this.resultsData.getTable(), file);
				this.dataSaved = true;
				return this.dataSaved;
			} else {
				this.main.getOutput().println("Warning: File not saved!");
				return this.dataSaved;
			}
		} catch (IOException e1) {
			this.main.getOutput().println("Error: " + e1.getMessage());
			return this.dataSaved;
		}
	}
	
	public boolean saveGraphToSVG() {
		// ask for file with svg filter
		File file = this.askForSaveFile(new SVGFileFilter(), false);
		// Create new svg file
		try {
			if (file != null) {
				GraphTab gt = (GraphTab) this.tabs.getComponent(this.tabs.indexOfTab("Graph"));
				ResultsData.graphToSVG(gt.getPerformanceGraph(), file);
				this.graphSaved = true;
				return this.graphSaved;
			} else {
				this.main.getOutput().println("Warning: File not saved!");
				return this.graphSaved;
			}
		} catch (IOException e1) {
			this.main.getOutput().println("Error: " + e1.getMessage());
			return this.graphSaved;
		}
	}
	
	/**
	 * saves details from ResultsData to a .txt file
	 * 
	 * @return boolean true for success, false otherwise
	 */
	public boolean saveDetailsToFile() {
		// ask for file with txt filter
		File file = this.askForSaveFile(new TXTFileFilter(), false);
		
		// save the details to given file
		try {
			if (file != null) {
				ResultsData.detailsToTXT(this.resultsData.toString(), file);
				this.detailsSaved = true;
				return this.detailsSaved;
			} else {
				this.main.getOutput().println("Warning: File not saved!");
				return this.detailsSaved;
			}
		} catch (IOException e1) {
			this.main.getOutput().println("Error: " + e1.getMessage());
			return this.detailsSaved;
		}
	}
	
	/**
	 * save all data related to the current run
	 * 
	 * @return boolean true for success, false otherwise
	 */
	public boolean saveAll() {
		File file = this.askForSaveFile(null, true);
		
		// save all
		try {
			if (file != null) {
				// if file is a file, delete file
				if (file.isFile()) {
					file.delete();
				}
				// create directory
				file.mkdir();
				// save all data to files starting with end time of run
				// and ending with a description of the data contained
				File dataFile = new File(file.getAbsolutePath() + "/" + this.resultsData.getEndTime() + "_data.csv");
				File graphFile = new File(file.getAbsolutePath() + "/" + this.resultsData.getEndTime() + "_graph.svg");
				File detailsFile = new File(file.getAbsolutePath() + "/" + this.resultsData.getEndTime() + "_details.txt");
				ResultsData.tableModeltoCSV(this.resultsData.getTable(), dataFile);
				GraphTab gt = (GraphTab) this.tabs.getComponent(this.tabs.indexOfTab("Graph"));
				ResultsData.graphToSVG(gt.getPerformanceGraph(), graphFile);
				ResultsData.detailsToTXT(this.resultsData.toString(), detailsFile);
				this.dataSaved = true;
				this.graphSaved = true;
				this.detailsSaved = true;
				return this.isAllSaved();
			} else {
				this.main.getOutput().println("Warning: No files saved!");
				return this.isAllSaved();
			}
		} catch (IOException e1) {
			this.main.getOutput().println("Error: " + e1.getMessage());
			return this.isAllSaved();
		}
	}
}


/**
 * JFileChooser with modified approveSelection() method to check if file exists
 * and ask for confirmation to override file<br>
 * uses filter given in constructor as default filter
 * @author Johannes Schoellhorn
 */
class ConfirmingFileChooser extends JFileChooser {
	
	private static final long serialVersionUID = -1218187657224709869L;
	ExtensionFileFilterBase filter;

	public ConfirmingFileChooser(ExtensionFileFilterBase filter) {
		this.filter = filter;
		if (filter != null) {
			this.setFileFilter(filter);
		}
	}
	
	/**
	 * modifying the approveSelection() method to check if file exists before approval
	 * and if it already exists asks for override confirmation.
	 */
	public void approveSelection(){
	    File f = getSelectedFile();
	    boolean isFilter = this.filter != null && this.filter.equals(this.getFileFilter());
	    boolean existsWithExtension = isFilter && new File(f.getAbsolutePath() + filter.getExtension()).exists();
	    if (!f.isDirectory() && ((this.getFileFilter().accept(f) && f.exists()) || existsWithExtension) && getDialogType() == SAVE_DIALOG) {
	        int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_OPTION);
	        switch(result){
	            case JOptionPane.YES_OPTION:
	                super.approveSelection();
	                return;
	            case JOptionPane.NO_OPTION:
	                return;
	        }
	    }
	    super.approveSelection();
	}
}

/**
 * 
 * FileFilter with method to get the extension of the filtered file
 * 
 * @author Johannes Schoellhorn
 *
 */
abstract class ExtensionFileFilterBase extends FileFilter {
	
	/**
	 * @return the file extension to filter
	 */
	abstract public String getExtension();
}

/**
 * filter filtering for .csv files
 * @author Johannes Schoellhorn
 *
 */
class CSVFileFilter extends ExtensionFileFilterBase {

	public boolean accept(File file) {
		String filename = file.getName();
		return filename.endsWith(".csv");
	}

	public String getDescription() {
		return "CSV Files";
	}
	
	public String getExtension() {
		return ".csv";
	}
	
}

/**
 * filter filtering for .txt files
 * @author Johannes Schoellhorn
 *
 */
class TXTFileFilter extends ExtensionFileFilterBase {
	
	public boolean accept(File file) {
		String filename = file.getName();
		return filename.endsWith(".txt");
	}
	
	public String getDescription() {
		return "Text Files";
	}
	
	public String getExtension() {
		return ".txt";
	}
}

/**
 * file filtering for .svg files
 * @author Johannes Schoellhorn
 *
 */
class SVGFileFilter extends ExtensionFileFilterBase {
	
	public boolean accept(File file) {
		String filename = file.getName();
		return filename.endsWith(".svg");
	}
	
	public String getDescription() {
		return "Scalable Vector Graphics (SVG)";
	}
	
	public String getExtension() {
		return ".svg";
	}
}
package consoleClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import logger.LoggerConfig;
import logger.LoggerSetup;
import reader.CSVReader;
import wrapper.EvA2Wrapper;
import wrapper.EvA2WrapperConfig;
import wrapper.ForwardSelectionWrapper;
import wrapper.ForwardSelectionWrapperConfig;
import wrapper.WrapperBase;
import wrapper.WrapperConfigBase;
import wrapper.WrapperThreadListener;
import wrapper.WrapperTypes;
import wrapper.crossvalidation.KFoldCrossValidation;
import wrapper.crossvalidation.KFoldCrossValidationConfig;
import wrapper.crossvalidation.classifier.CSVCClassifier;
import wrapper.crossvalidation.classifier.CSVCClassifierConfig;
import wrapper.crossvalidation.classifier.ClassifierBase;
import wrapper.crossvalidation.classifier.ClassifierTypes;
import wrapper.performance.Accuracy;
import wrapper.performance.MCCPerformance;
import wrapper.performance.PerformanceI;
import wrapper.performance.PerformanceTypes;
import controller.FeatureDataEvent;
import controller.FeatureDataListener;
import controller.MainController;
import controller.WrapperEvent;
import dataStructures.FeatureData;
import eva2.server.go.operators.terminators.GenerationTerminator;

/**
 * console client to start feature selection in console<br><br>
 * 2 ways to run the client:<br>
 * &nbsp;&nbsp;&nbsp;-i | --interactive ask for options<br>
 * &nbsp;&nbsp;&nbsp;or controlled by arguments
 * <br><br>
 * refer to printHelp() for more information
 * @author julianschwab, Johannes Schoellhorn
 *
 */
public class ConsoleClient implements TableModelListener, WrapperThreadListener {

	MainController main;
	PrintStream output;
	FeatureDataListener listener;
	private String name = "consoleClient";
	
	File file;
	String csvSeparator = ",";
	
	WrapperTypes wrapperT;
	ClassifierTypes classifierT;
	PerformanceTypes performanceT;
	
	int fold = Integer.MIN_VALUE;
	long CrossValSeed = Long.MIN_VALUE;
	
	int EvA2P = Integer.MIN_VALUE;
	int EvA2T = Integer.MIN_VALUE;
	int EvA2Seed = Integer.MIN_VALUE;
	
	WrapperBase wrapper;
	
    public static final Logger log = Logger.getLogger(ConsoleClient.class.getName());
	
	/**
	 * creates a new ConsoleClient<br><br>
	 * sets the output, creates new MainController,
	 * and adds the controller as a listener
	 * @param output PrintStream output stream
	 */
	public ConsoleClient(PrintStream output){
		this.output = output;
		this.main = new MainController(output);
		this.addFeatureDataListener(main);
	}
	
	/**
	 * main method
	 * @param argsv String[] arguments
	 * @throws IOException 
	 */
	public static void main(String[] argsv) throws IOException {
		ConsoleClient client = new ConsoleClient(System.out);
		client.run(argsv);
	}
	
	/**
	 * run a console client with arguments
	 * @param argsv String[] arguments
	 * @throws IOException 
	 */
	public void run(String[] argsv) throws IOException {
		if (this.parseArguments(argsv)) {
			LoggerSetup.setup(new LoggerConfig());
	        log.info("ConsoleClient started");
			if (this.buildWrapper()) {
				this.main.addWrapperThreadListener(this);
				this.notifyWrapper(new WrapperEvent(this, this.wrapper, new CountDownLatch(0)));
				this.main.getResultsData().getTable().addTableModelListener(this);
			}
		}
	}
	
	/**
	 * build a wrapper from given OPTIONS/arguments
	 * @return boolean true for success else false
	 */
	private boolean buildWrapper() {
		CSVReader reader = new CSVReader(this.csvSeparator);
		FeatureData data;
		
		// try to read data from file
		try {
			data = reader.read(new FileReader(this.file));
		} catch (FileNotFoundException e) {
			output.println(e.getMessage());
			return false;
		} catch (IOException e) {
			output.println(e.getMessage());
			return false;
		} catch (DataFormatException e) {
			output.println(e.getMessage());
			return false;
		}
		
		// notify the mainController that a new FeatureData is available
		this.notifyFeatureDataUpdated(new FeatureDataEvent(this, data, this.file));
		output.println("Reader: read data from file: " + this.file.getName());
		
		// test if file was the only argument given, set the wrapper and return
		if (wrapperT == null) {
			this.wrapper = new ForwardSelectionWrapper(new ForwardSelectionWrapperConfig(data));
			return true;
		}
		
		// set classifier
		ClassifierBase cl = null;
		switch (this.classifierT) {
		case CSVC:
			cl = new CSVCClassifier(new CSVCClassifierConfig());
			output.println("Classifier: using CSVC");
			break;
		default:
			return false;
		}
		
		KFoldCrossValidationConfig crossValCfg = new KFoldCrossValidationConfig();
		crossValCfg.setClassifier(cl);
		// set fold if option was specified
		if (this.fold != Integer.MIN_VALUE) {
			crossValCfg.setFold(this.fold);
			output.println("Cross Validation: fold set to " + this.fold);
		}
		// set random seed if option was specified
		if (this.CrossValSeed != Long.MIN_VALUE) {
			crossValCfg.setRandomSeed(CrossValSeed);
			output.println("Cross Validation: random seed set to " + this.CrossValSeed);
		}
		KFoldCrossValidation crossVal = new KFoldCrossValidation(crossValCfg);
		
		// set performance
		PerformanceI perf = null;
		switch (this.performanceT) {
		case MCC:
			perf = new MCCPerformance();
			output.println("Performance: using MCC");
			break;
		case ACCURACY:
			perf = new Accuracy();
			output.println("Performance: using Accuracy");
			break;
		default:
			return false;
		}
		
		// set wrapper
		WrapperConfigBase wrpCfg = new ForwardSelectionWrapperConfig(data);
		WrapperBase wrp = null;
		wrpCfg.setCrossVal(crossVal);
		wrpCfg.setPerformance(perf);
		switch (this.wrapperT) {
		case FORWARDSELECTION:
			wrpCfg = new ForwardSelectionWrapperConfig(data);
			wrp = new ForwardSelectionWrapper((ForwardSelectionWrapperConfig) wrpCfg);
			output.println("Wrapper: using Forward Selection");
			break;
		case EVA:
			wrpCfg = new EvA2WrapperConfig(data);
			output.println("Wrapper: using EvA2");
			// set population size if option was specified
			if (this.EvA2P != Integer.MIN_VALUE) {
				((EvA2WrapperConfig) wrpCfg).setPopulation(this.EvA2P);
				output.println("Wrapper: population set to " + this.EvA2P);
			}
			// set terminator if option was specified
			if (this.EvA2T != Integer.MIN_VALUE) {
				((EvA2WrapperConfig) wrpCfg).setTerminator(new GenerationTerminator(this.EvA2T));
				output.println("Wrapper: terminator set to " + this.EvA2T);
			}
			// set random seed if option was specified
			if (this.EvA2Seed != Integer.MIN_VALUE) {
				((EvA2WrapperConfig) wrpCfg).setPopulation(this.EvA2Seed);
				output.println("Wrapper: random seed set to " + this.EvA2Seed);
			}
			wrp = new EvA2Wrapper((EvA2WrapperConfig) wrpCfg);
			break;
		default:
			return false;
		}
		
		// set the wrapper and return;
		this.wrapper = wrp;
		return true;
	}
	
	/**
	 * parse arguments for OPTIONS
	 * @param argsv String[] with arguments
	 * @return boolean true for success else false
	 * @throws IOException 
	 */
	private boolean parseArguments(String[] argsv) throws IOException {
		int i = 0;
		int args = argsv.length;
		
		// read first argument as file
		this.file = new File(argsv[i]);
		i++;
		
		// if only one argument is specified, start feature selection on file
		// with default parameters
		if (args == 1) {
			return true;
		// if only two arguments are specified, 2nd argument has to be -i
		} else if (args == 2) {
			if (argsv[i].equals("-i") || argsv[i].equals("--interactive")) {
				if (this.interactive()) {
					return true;
				}
				this.printHelp();
				return false;
			} else {
				output.println("2nd argument has to be -i for interactive mode!");
				this.printHelp();
				return false;
			}
		// if more than two arguments are specified, there have to be at least 4
		} else if (args >= 4) {
			while (i < args) {
				// 2nd argument has to be the wrapper
				if (i == 1) {
					if (argsv[i].equals("FWD")) {
						this.wrapperT = WrapperTypes.valueOf("FORWARDSELECTION");
					} else if (argsv[i].equals("EVA")) {
						this.wrapperT = WrapperTypes.valueOf("EVA");
					} else {
						output.println("2nd argument has to be a wrapper type!");
						this.printHelp();
						return false;
					}
				// 3rd argument has to be the classifier
				} else if (i == 2) {
					if (argsv[i].equals("CSVC")) {
						this.classifierT = ClassifierTypes.valueOf("CSVC");
					} else {
						output.println("3rd argument has to be a classifier type!");
						this.printHelp();
						return false;
					}
				// 4th argument has to be the performance
				} else if (i == 3) {
					if (argsv[i].equals("MCC")) {
						this.performanceT = PerformanceTypes.valueOf("MCC");
					} else if (argsv[i].equals("ACC")) {
						this.performanceT = PerformanceTypes.valueOf("ACCURACY");
					} else {
						output.println("4th argument has to be a performance type!");
						this.printHelp();
						return false;
					}
				// the rest have to be options, parse the options
				} else {
					if (++i >= args) {
						output.println("Something went wrong with " + argsv[--i] + "!");
						this.printHelp();
						return false;
					} else {
						if (argsv[i-1].equals("-csv")) {
							this.csvSeparator = argsv[i];
						} else if (argsv[i-1].equals("-f") || argsv[i-1].equals("--fold")) {
							this.fold = this.parseInt(argsv[i]);
							if (this.fold == Integer.MIN_VALUE) {
								this.errorForArgument(argsv[i-1], argsv[i]);
								return false;
							}
						} else if (argsv[i-1].equals("--CrossValSeed")) {
							this.CrossValSeed = this.parseLong(argsv[i]);
							if (this.CrossValSeed == Long.MIN_VALUE) {
								this.errorForArgument(argsv[i-1], argsv[i]);
								return false;
							}
						} else if (argsv[i-1].equals("-EvA2P") || argsv[i-1].equals("--EvA2Population")) {
							this.EvA2P = this.parseInt(argsv[i]);
							if (this.EvA2P == Integer.MIN_VALUE) {
								this.errorForArgument(argsv[i-1], argsv[i]);
								return false;
							}
						} else if (argsv[i-1].equals("-EvA2T") || argsv[i-1].equals("--EvA2Termination")) {
							this.EvA2T = this.parseInt(argsv[i]);
							if (this.EvA2T == Integer.MIN_VALUE) {
								this.errorForArgument(argsv[i-1], argsv[i]);
								return false;
							}
						} else if (argsv[i-1].equals("--EvA2Seed")) {
							this.EvA2Seed = this.parseInt(argsv[i]);
							if (this.EvA2Seed == Integer.MIN_VALUE) {
								this.errorForArgument(argsv[i-1], argsv[i]);
								return false;
							}
						} else {
							output.println("Error: " + argsv[i] + " not a valid argument!");
							this.printHelp();
							return false;
						}
					}
				}
				
				i++;
				
			}
			return true;
		}
		
		output.println("Number of arguments not valid!");
		this.printHelp();
		return false;
		
	}

	/**
	 * print error message
	 * @param argument argument where the error occurred
	 * @param value value of argument where the error occurred
	 */
	private void errorForArgument(String argument, String value) {
		output.println("Error: " + value + " not a valid value for " + argument + "!");
	}
	
	/**
	 * parse a string as an integer
	 * @param s String to parse
	 * @return integer value or Integer.MIN_VALUE
	 */
	
	private int parseInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return Integer.MIN_VALUE;
		}
	}

	/**
	 * parse a string as a long
	 * @param s String to parse
	 * @return long value or Long.MIN_VALUE
	 */
	private long parseLong(String s) {
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			return Long.MIN_VALUE;
		}
	}

	
	/**
	 * ask for all options
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	private boolean interactive() throws IOException {
		BufferedReader bfR = new BufferedReader(new InputStreamReader(System.in));
		String selectionString;
		int selectionInt;
		
		while (true) {
			output.print("Choose a Wrapper (0 = FwdSelect, 1 = EvA): ");
			selectionString = bfR.readLine();
			try {
				selectionInt = Integer.parseInt(selectionString);
			} catch (NumberFormatException e) {
				output.println("Error: wrong Input!");
				continue;
			}
			if (selectionInt == 0) {
				this.wrapperT = WrapperTypes.FORWARDSELECTION;
				break;
			} else if (selectionInt == 1) {
				this.wrapperT = WrapperTypes.EVA;
				break;
			}
			output.println("Error: wrong Input!");
		}
		while (true) {
			output.print("Choose a Classifier (0 - CSVC): ");
			selectionString = bfR.readLine();
			try {
				selectionInt = Integer.parseInt(selectionString);
			} catch (NumberFormatException e) {
				output.println("Error: wrong Input!");
				continue;
			}
			if (selectionInt == 0) {
				this.classifierT = ClassifierTypes.CSVC;
				break;
			}
			output.println("Error: wrong Input!");
		}
		while (true) {
			output.print("Choose a Performance (0 - MCC, 1 - ACC): ");
			selectionString = bfR.readLine();
			try {
				selectionInt = Integer.parseInt(selectionString);
			} catch (NumberFormatException e) {
				output.println("Error: wrong Input!");
				continue;
			}
			if (selectionInt == 0) {
				this.performanceT = PerformanceTypes.MCC;
				break;
			} else if (selectionInt == 1) {
				this.performanceT = PerformanceTypes.ACCURACY;
			}
			output.println("Error: wrong Input!");
		}
		while (true) {
			output.print("Specifiy csv separator: ");
			selectionString = bfR.readLine();
			if (!selectionString.equals("")) {
				this.csvSeparator = selectionString;
				break;
			}
			output.println("Error: no Input!");
		}
		while (true) {
			output.print("Specify fold dept: ");
			selectionString = bfR.readLine();
			try {
				this.fold = Integer.parseInt(selectionString);
				break;
			} catch (NumberFormatException e) {
				output.println("Error: wrong Input!");
				continue;
			}
		}
		while (true) {
			output.print("Specify cross validation random seed: ");
			selectionString = bfR.readLine();
			try {
				this.CrossValSeed = Long.parseLong(selectionString);
				break;
			} catch (NumberFormatException e) {
				output.println("Error: wrong Input!");
				continue;
			}
		}
		switch (this.wrapperT) {
		case FORWARDSELECTION:
			break;
		case EVA:
			while (true) {
				output.print("Specify EvA2 population size: ");
				selectionString = bfR.readLine();
				try {
					this.EvA2P = Integer.parseInt(selectionString);
					break;
				} catch (NumberFormatException e) {
					output.println("Error: wrong Input!");
					continue;
				}
			}
			while (true) {
				output.print("Specify EvA2 termination: ");
				selectionString = bfR.readLine();
				try {
					this.EvA2T = Integer.parseInt(selectionString);
					break;
				} catch (NumberFormatException e) {
					output.println("Error: wrong Input!");
					continue;
				}
			}
			while (true) {
				output.print("Specify EvA2 random seed: ");
				selectionString = bfR.readLine();
				try {
					this.EvA2Seed = Integer.parseInt(selectionString);
					break;
				} catch (NumberFormatException e) {
					output.println("Error: wrong Input!");
					continue;
				}
			}
			break;
		}
		return true;
		
	}
	
	/**
	 * print help to PrintStream
	 */
	private void printHelp() {
		output.println("usage:");
		output.println(name + " file [wrapper classifier performance] [ -OPTIONS ]");
		output.println(name + " file -i | --interactive");
		output.println();
		output.println("\t" + "file" + "\t\t" + "file to read the data from");
		output.println();
		output.println("\t" + "wrapper" + "\t\t" + "FWD | EVA");
		output.println("\t\t\t" + "Method to choose Features.");
		output.println("\t\t\t" + "    FWD: use Fowrard Selection");
		output.println("\t\t\t" + "    EVA: use Evolution Algorithms (EvA2)");
		output.println();
		output.println("\t" + "classifier" + "\t" + "CSVC");
		output.println("\t\t\t" + "Classifier to use to train.");
		output.println("\t\t\t" + "    CSVC: use CSVC classifier");
		output.println();
		output.println("\t" + "performance" + "\t" + "MCC | ACC");
		output.println("\t\t\t" + "Method to use to calculate performance.");
		output.println("\t\t\t" + "    MCC: use Matthews Correlation Coefficient");
		output.println("\t\t\t" + "    ACC: use Accuracy");
		output.println();
		output.println("\t" + "OPTIONS");
		output.println("\t" + "file options:");
		output.println("\t\t" + "-csv");
		output.println("\t\t" + "    specify csv separator and use CSVReader to parse file");
		output.println("\t" + "wrapper options:");
		output.println("\t\t" + "-EvA2P | --EvA2Population");
		output.println("\t\t" + "    specify the population size");
		output.println("\t\t" + "-EvA2T | --EvA2Termination");
		output.println("\t\t" + "    specify the termination criteria");
		output.println("\t\t" + "--EvA2Seed");
		output.println("\t\t" + "    specify the random seed to use in EvA2");
		output.println("\t" + "cross validation options:");
		output.println("\t\t" + "-f | --fold");
		output.println("\t\t" + "    specify the fold to use in cross validation");
		output.println("\t\t" + "--CrossValSeed");
		output.println("\t\t" + "    specify the random seed to shuffle in cross validation");
	}
	
	/*
	 * 
	 * add listener and notification methods here
	 *
	 */

	public void addFeatureDataListener(FeatureDataListener listener) {
		this.listener = listener;
	}
	
	public void removeFeatureDataListener(FeatureDataListener listener) {
		this.listener = null;
	}
	
	protected synchronized void notifyFeatureDataUpdated(FeatureDataEvent e) {
		this.listener.featureDataUpdated(e);
	}

	/**
	 * Sends wrapper to all listeners when fired
	 * @param WrapperEvent e
	 */
	private void notifyWrapper(WrapperEvent e){
		main.wrapperUpdated(e);
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		String sampleFormat = " %7.7s |";
		if (e.getType() == TableModelEvent.INSERT) {
			DefaultTableModel table = (DefaultTableModel) e.getSource();
			if (table.getRowCount() == 1) {
				output.print("|");
				for (int i = 0; i < table.getColumnCount(); i++) {
					output.print(String.format(sampleFormat, table.getColumnName(i)));
				}
				output.println();
			}
			output.print("|");
			for (int i = 0; i < table.getColumnCount(); i++) {
				output.print(String.format(sampleFormat, table.getValueAt(e.getLastRow(), i)));
			}
			output.println();
		}
		
	}

	/* (non-Javadoc)
	 * @see wrapper.WrapperThreadListener#threadFinished()
	 */
	@Override
	public void threadFinished() {
		// TODO save data if save file was set
		
	}

}

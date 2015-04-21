package wrapper;

import java.io.PrintStream;
import java.util.BitSet;

import javax.swing.event.EventListenerList;

import wrapper.crossvalidation.CrossValidationBase;
import wrapper.filter.FilterBase;
import wrapper.performance.PerformanceI;
import dataStructures.FeatureData;
import dataStructures.ResultsData;
import dataStructures.SubsetPerformanceData;

/**
 * Wrapper to control feature selection
 * 
 * @author Johannes Schoellhorn, Julian Schwab
 * 
 */
public abstract class WrapperBase {
	
	protected WrapperConfigBase config;
	
	protected FilterBase filter;
	protected CrossValidationBase crossVal;
	protected PerformanceI performance;
	protected EventListenerList listener = new EventListenerList();
	protected FeatureData data;
	protected SubsetPerformanceData subsetPerformance;
	
	//breakPoint quits process if false..This is to force the termination of thread
	protected volatile boolean breakPoint = false;
	
	protected PrintStream output;
	
	
	
	/**
	 * Normal constructor for a wrapper
	 * @param config Configuration file
	 */
	
	public WrapperBase(WrapperConfigBase config) {
		this.data = config.getData();
		this.config = config;
		this.filter = config.getFilter();
		this.crossVal = config.getCrossVal();
		this.performance = config.getPerformance();
		this.output = config.getOutput();

	}

	/**
	 * starts the wrapper
	 */
	public abstract void run();
	
	/**
	 * Creates new ResultsData object
	 * @return ResultsData for the given file
	 */
	public abstract ResultsData createResultsData(String fileName, String absoluteFileName);
	
	/**
	 * get the best feature subset
	 * @return String[] containing the String representation of the best features
	 * @throws Exception if subsetPerformance is not yet set
	 */
	public SubsetPerformanceData getBestFeatureSubset() throws NullPointerException {
		try {
			return this.subsetPerformance;
		} catch (NullPointerException ex) {
			System.err.print("Best subset has not been computed yet!");
			throw ex;
		}
	}
	
	public String[] getBestFeatures() throws NullPointerException {
		try {
			BitSet featureBitSet = this.getBestFeatureSubset().getFeatureBitSet();
			String[] features = new String[featureBitSet.cardinality()];
			int pos = 0;
			for (int i = 0; i < featureBitSet.length(); i++) {
				if (featureBitSet.get(i)) {
					features[pos] = this.data.getFeatures()[i];
					pos++;
				}
			}
			return features;
		} catch (NullPointerException ex) {
			System.err.print("Best subset has not been computed yet!");
			throw ex;
		}
	}
	
	public String getFeatures(BitSet bitset) {
		String result = "";
		for (int i = 0; i < data.getFeatures().length; i++) {
			if (bitset.get(i)) {
				result += data.getFeatures()[i] + " ";
			}
		}
		return result;
	}
	
	/**
	 * setBreakPoint is called if the process has to quit unnaturally(e.g. with the stop button)
	 */
	public synchronized void setBreakPoint() {
		this.breakPoint = true;
	}
	
	
	/*
	 * 
	 * Add/Remove and notification method for listeners
	 * 
	 */
	
	
	
	public void addResultEventListener(ResultListener l) {
		listener.add(ResultListener.class, l);
	}
	
	public void removeResultEventListener(ResultListener l) {
		listener.remove(ResultListener.class, l);
	}
	
	
	public synchronized void notifyNewResults(ResultEvent e) {
		for (ResultListener listen : listener.getListeners(ResultListener.class)) {
			listen.resultsUpdated(e);
		}
	}

}
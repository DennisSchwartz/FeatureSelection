package wrapper;

import java.util.EventObject;

import dataStructures.SubsetPerformanceData;

/**
 * ResultEvent holds a double array, which is fired with the Event
 * In the array : mean, stdDev , best, bestOverAll Values of featureSelection (of each Population)
 * @author julianschwab
 *
 */
public class ResultEvent extends EventObject{

	
	
	private static final long serialVersionUID = 1L;
	Object[] results;
	SubsetPerformanceData bestSubset;
	
	/**
	 * Constructor
	 * @param source Object that fired event
	 * @param res resultvalues that have to be fired
	 * @param subsetPerformanceData 
	 */
	public ResultEvent(Object source,Object[] res, SubsetPerformanceData subsetPerformanceData) {
		super(source);
		
		results = res;
		bestSubset = subsetPerformanceData;
	}


	/**
	 * Getter for results array.
	 * Creates copy of Array in Double Type round the results
	 * @return the results
	 */
	public Object[] getResults() {
		Object[] res = new Object[results.length];
		for(int i = 0; i< results.length; i++){
			res[i] = results[i];

		}
		
		return res;
	}
	
	public SubsetPerformanceData getBestSubset() {
		return bestSubset;
	}
	
	
	
	
	
}

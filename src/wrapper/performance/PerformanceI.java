package wrapper.performance;

import dataStructures.ConfusionMatrix;
/**
 * Interface for functions every performance class must have implemented
 * @author julianschwab
 *
 */
public interface PerformanceI {


	/**
	 * calculatePerformance calculates the double value performance on a given confusionMatrix. 
	 * 
	 * @param confusionMatrix
	 * @return performance value
	 */
	public double calculatePerformance(ConfusionMatrix confusionMatrix);
	
	public String toString();
	
}

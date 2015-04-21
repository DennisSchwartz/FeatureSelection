package wrapper.performance;

import dataStructures.ConfusionMatrix;
/**
 * Accuracy is a type of measure the performance of the crossvalidation
 * Accuracy need a confusion matrix to calculate the performance
 * 
 * @author julianschwab
 *
 */
public class Accuracy implements PerformanceI {

	@Override
	/**
	 * calculates performance based on the formula of accuracy 
	 */
	public double calculatePerformance(ConfusionMatrix confusionMatrix) {
		
		// declaration of variables 
		int tp = confusionMatrix.getTruePositive();
		int fp = confusionMatrix.getFalsePositive();
		int tn = confusionMatrix.getTrueNegative();
		int fn = confusionMatrix.getFalseNegative();
		
		if((tp + fp + fn + tn) == 0){						//If confusion matrix was empty, the denomitor in the formula is 0, so an error occurs
			System.err.print("confusion matrix was empty");
			return 0;
		}
		
		else { 	//else return accuracy calculated with formula from wikipedia	
			return (tp + tn) / (double)(tp + fp + fn + tn);
		
		}
		
	}
	
	public String toString() {
		String res = "";
		res += "Performance Type:\n";
		res += "   Accuracy";
		return res;
	}

}

package wrapper.performance;

import dataStructures.ConfusionMatrix;

/**
 * Class that calculates performance quality with Matthews Correlation Coefficient
 * @author julianschwab
 *
 */
public class MCCPerformance implements PerformanceI {

	@Override
	/**
	 * calculatePerformance calculates the performance value with the Matthews Correlation Coefficient
	 */
	public double calculatePerformance(ConfusionMatrix confusionMatrix) {
		
		
		// declaration of variables 
		int tp = confusionMatrix.getTruePositive();
		int fp = confusionMatrix.getFalsePositive();
		int tn = confusionMatrix.getTrueNegative();
		int fn = confusionMatrix.getFalseNegative();
		
		double denominator;
		if (tp + fp == 0 || tp + fn == 0 || tn + fp == 0 || tn + fn == 0) {
			denominator = 1;
		} else {
			denominator = Math.sqrt(((tp + fp) * (tp + fn) * (tn + fp) * (tn + fn)));
		}
		
		return ((tp * tn) - (fp * fn)) / denominator; //calculation of MCC
		
	}
	
	public String toString() {
		String res = "";
		res += "Performance Type:\n";
		res += "   Matthews Correlation Efficient";
		return res;
	}

	

}

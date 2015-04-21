package x_playground.statistics;


import dataStructures.FeatureData;
/**
 * StatisticsData is a storage class for FeatureData object and the results of the statistic evaluation based on the values of the FeatureData object
 * @author julianschwab
 *
 */
public class StatisticsData implements CsvStatisticsI{

	private double[] meanAll; 
	private double[] stdDevAll;
	
	private double[] meanCase;
	private double[] meanControl;
	
	private double[] stdDevCase;
	private double[] stdDevControl;
	
	private FeatureData data;
	
	/**
	 * Constructor creates StatisticsData Object with given FeatureData Object in its attribute data 
	 * @param d FeatureData Object where the statistic values are based on
	 */
	public StatisticsData(FeatureData d) {
		this.data = d;
	}
	



	/**
	 * @return the meanAll
	 */
	public double[] getMeanAll() {
		return meanAll;
	}



	/**
	 * @return the stdDevAll
	 */
	public double[] getStdDevAll() {
		return stdDevAll;
	}



	/**
	 * @return the meanCase
	 */
	public double[] getMeanCase() {
		return meanCase;
	}



	/**
	 * @return the meanControl
	 */
	public double[] getMeanControl() {
		return meanControl;
	}



	/**
	 * @return the stdDevCase
	 */
	public double[] getStdDevCase() {
		return stdDevCase;
	}



	/**
	 * @return the stdDevControl
	 */
	public double[] getStdDevControl() {
		return stdDevControl;
	}



	/**
	 * @return the data
	 */
	public FeatureData getData() {
		return data;
	}
	
	

	/**
	 * Calculates the mean values of each column in general and the mean values of all values of case or control in each column
	 * Calls the private methods meanAll(),mean(boolean cl)
	 * Writes results in class attributes meanAll,meanCase,meanControl
	 */
	@Override
	public void mean() {
		
		meanAll();
//		meanCC();
		
		
	}
	
	/**
	 * Calculates the standard deviation values of each column in general and the standard deviation values of all values of case or control in each column
	 * Calls the private methods stdDevAll(),stdDev(boolean cl)
	 * Writes results in class attributes stdDevAll,stdDevCase,stdDevControl
	 */
	@Override
	public void stdDev() {
		
		stdDevAll();
//		stdDevCC();
		
	}
	
	
	/**
	 * Calculates the mean values of each column, no matter on the class of the samples
	 * Writes results in class attribute meanAll  
	 */
	private void meanAll() {
		this.meanAll = new double[this.data.getFeatures().length];
		
		for(int j=0;j<this.data.getValues()[0].length;j++) { // loop calculates sum all values in one column
			for(int i=0;i<this.data.getValues().length;i++) {
				this.meanAll[j] += this.data.getValues()[i][j];
			}
			this.meanAll[j] = this.meanAll[j]/this.data.getValues().length; //calculates mean value by dividing the sum of one column by the number of values
		}
	}
	
	
	/**
	 * Calculates the mean values of case and control samples in each column 
	 * Results are written in meanCase and meanControl
	 */
//	private void meanCC() {
//		
//		this.meanCase = new double[this.data.getFeatures().length];
//		this.meanControl = new double[this.data.getFeatures().length];
//		
//		for(int j=0;j<this.data.getValues()[0].length;j++) { // loop calculates sum all values in one column (case and control are summed separately)
//			double[] tempCase = this.data.getFeatureCol(j,true);
//			double[] tempControl = this.data.getFeatureCol(j,false);
//				
//					 for (double value : tempCase) {
//						 this.meanCase[j] += value;
//					 }
//				
//					for (double value : tempControl) {
//						this.meanControl[j] += value;
//				
//				}
//			
//			
//			
//				this.meanCase[j] = this.meanCase[j]/tempCase.length;
//				this.meanControl[j] = this.meanControl[j]/tempControl.length;
//			
//		}
//	}
	
	
	/**
	 * Calculates the standard deviation values of each column, no matter on the class of the samples
	 * Writes results in class attribute stdDevAll  
	 */
	private void stdDevAll() {
		
		
		if(this.meanAll==null){
			this.mean();
			System.err.print("calculated mean value automatically.");
		}
		this.stdDevAll = new double[this.data.getFeatures().length];
		
		for(int j=0;j<this.data.getValues()[0].length;j++) {
			for(int i=0;i<this.data.getValues().length;i++) {
				
				 this.stdDevAll[j] += Math.pow((this.data.getValues()[i][j] - this.meanAll[j]),2); // loop summs (difference of current value and mean value)^2. This is the variance
			}
			
			
			this.stdDevAll[j] = Math.sqrt(this.stdDevAll[j]/this.data.getValues().length); //standart deviation = sqrt of variance divided by number of features
		}
	}
	
	/**
	 * Calculates standard deviation of case/control values in each column of FeatureData object 
	 */
//	private void stdDevCC() {
//		
//		this.stdDevCase = new double[this.data.getFeatures().length];
//		this.stdDevControl = new double[this.data.getFeatures().length];
//		
//		
//		
//			for(int j=0;j<this.data.getValues()[0].length;j++) { //loops calculates variance of case and control values in each column
//				double[] tempCase = this.data.getFeatureCol(j,true);
//				double[] tempControl = this.data.getFeatureCol(j,false);
//				
//				for(double value : tempCase) {
//					stdDevCase[j] += Math.pow(value-this.meanCase[j],2);
//				}
//				for(double value : tempControl) {
//					stdDevControl[j] += Math.pow(value-this.meanControl[j],2);
//				}
//			
//				this.stdDevCase[j] = Math.sqrt(this.stdDevCase[j]/tempCase.length); //calculates standard deviation
//				this.stdDevControl[j] = Math.sqrt(this.stdDevControl[j]/tempControl.length);
//			}
//			
//			
//		
//	}




}

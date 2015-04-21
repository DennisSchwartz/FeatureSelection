package wrapper.filter;

import java.util.BitSet;

import dataStructures.FeatureData;

/**
 * CsvFilter class that provides several filtering methods
 * 
 * @author Dennis
 * 
 */

public class BitSetFilter extends FilterBase {
	
	public BitSetFilter(FeatureData data) {
		super(data);
	}
	


	/**
	 * Fills the field featureSubset according to the given BitSet
	 * @param bitSet
	 * @return Subset of FeatureData
	 */
	public FeatureData selectSubset(BitSet bitSet) {
		
		FeatureData data = this.originalData;
		
		// get set bits in the range from 0 to length of features in data - 1
		int resFeaturesLength = 0;
		for (int i = 0; i < data.getFeatures().length; i++) {
			if (bitSet.get(i)) {
				resFeaturesLength++;
			}
		}
		
		//Initialize String array for the returned features with the number of ones in the BitSet as length
		String[] resFeatures = new String[resFeaturesLength];
		double[][] resValues = new double[data.getValues().length][resFeatures.length];

		int pos = 0;
		//Loop iterates over the new array filling the columns with values extracted from the original DataObject. 
		//Only features that are specified with 1 in the BitSet are extracted.
		for (int i = 0; i < data.getFeatures().length; i++) {
			//Go to the next 1 in the BitSet
			if (bitSet.get(i)) {
				resFeatures[pos] = data.getFeatures()[i];
				double[] col = data.getFeatureCol(i);
				for (int k = 0; k < data.getValues().length; k++) {
					resValues[k][pos] = col[k];
				}
			
				pos++;
			}

		}
		// Return new FeatureData using the extracted values.
		return new FeatureData(resFeatures, data.getSamples(), data.getClasses(), resValues);
	}
}

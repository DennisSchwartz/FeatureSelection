package wrapper.filter;

import java.util.BitSet;

import dataStructures.FeatureData;

public class OddEvenFilter extends FilterBase {

	public OddEvenFilter(FeatureData data) {
		super(data);
	}
	
	/**
	 * Function returning odd or even columns(features) depending on the
	 * function called (odd(FeatureData data) or odd(FeatureData data)).
	 * It generates a BitSet that has every other bit set to 1. 
	 * @param data
	 *            FeatureDataObject to filter
	 * @param even
	 *            Boolean specifying if even or odd columns(features) are
	 *            extracted
	 * @return
	 */
	private FeatureData getOddEven(boolean even) {
		
		FeatureData data = this.originalData;
		int stringLength = data.getFeatures().length;
		int start;
		BitSet res = new BitSet(stringLength);
		
		if (even) {
			start = 0;
		} else {
			start = 1;
		}
		
		for (int i = start; i < stringLength; i += 2) {
			res.set(start);
		}
		return this.selectSubset(res);
	}

	public void odd() {
		this.getOddEven(false);
	}

	public void even() {
		this.getOddEven(true);
	}
	
public FeatureData selectSubset(BitSet bitSet) {
		
		FeatureData data = this.originalData;
		
		//Initialize String array for the returned features with the number of ones in the BitSet as length
		String[] resFeatures = new String[bitSet.cardinality()];
		double[][] resValues = new double[data.getValues().length][resFeatures.length];

		int i = 0;
		//Loop iterates over the new array filling the columns with values extracted from the original DataObject. 
		//Only features that are specified with 1 in the BitSet are extracted.
		for (int j = 0; j < resFeatures.length; j++) {
			//Go to the next 1 in the BitSet
			while (!(bitSet.get(i))) {
				i++;
			}
			resFeatures[j] = data.getFeatures()[i];
			double[] col = data.getFeatureCol(i);
			for (int k = 0; k < data.getValues().length; k++) {
				resValues[k][j] = col[k];
			}
			i++;
		}
		// Return new FeatureData using the extracted values.
		return new FeatureData(resFeatures, data.getSamples(), data.getClasses(), resValues);
	}

}

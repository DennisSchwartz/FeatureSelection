package wrapper.filter;

import java.util.BitSet;

import dataStructures.FeatureData;

/**
 * Interface for CsvFilter
 * @author Dennis
 *
 */
public abstract class FilterBase {
	
	FeatureData originalData;
	
	public FilterBase(FeatureData data) {
		this.originalData = data;
	}
	
	public abstract FeatureData selectSubset(BitSet bitSet);
	
		

}

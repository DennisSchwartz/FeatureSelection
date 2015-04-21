package dataStructures;

import java.util.ArrayList;
import java.util.List;

import wrapper.WrapperConfigBase;
import wrapper.WrapperTypes;

/**
 * ResultsData class for FowardSelection wrapper
 * 
 * @author Dennis Schwartz
 * 
 */

public class ForwardSelectionResultsData extends ResultsData {

	protected List<SubsetPerformanceData> subsets = new ArrayList<SubsetPerformanceData>();
	
	/**
	 * The table of this ResultsObject has 2 Columns
	 */
	public ForwardSelectionResultsData(String fileName, String absoluteFileName, FeatureData data, WrapperConfigBase wrpCfg) {
		super(new String[] { "Iteration", "Best", "Feature" }, fileName, absoluteFileName, data, wrpCfg);
	}

	@Override
	public void add(Object[] results) {

		this.table.insertRow(0, results);

	}
	
	public void setBestSubset(SubsetPerformanceData subset) {
		super.setBestSubset(subset);
		this.subsets.add(subset);
	}

	public WrapperTypes getWrapperType() {
		return WrapperTypes.FORWARDSELECTION;
	}

}

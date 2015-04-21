package dataStructures;

import java.util.ArrayList;
import java.util.List;

import wrapper.WrapperConfigBase;
import wrapper.WrapperTypes;
import eva2.server.go.populations.Population;

/**
 * ResultsData class for EvA2 wrapper
 * @author Dennis Schwartz
 *
 */

public class EvA2ResultsData extends ResultsData {

	private int populationCounter = 1;
	private List<Population> populations;
	
	public EvA2ResultsData(String fileName, String absoluteFileName, FeatureData data, WrapperConfigBase wrpCfg) {
	super(new String[]{"Population", "Mean", "Best", "StdDev"}, fileName, absoluteFileName, data, wrpCfg);

		populations = new ArrayList<Population>();
	}

	@Override
	public void add(Object[] results) {
		populations.add((Population) results[results.length - 1]);
		
		Object[] temp = new Object[results.length];
		temp[0] = populationCounter;
		for (int i = 0; i < results.length-1; i++) {
			temp[i+1] = results[i];
		}
		this.table.insertRow(0, temp);
		populationCounter++;
	}
	
	public List<Population> getPopulations() {
		return this.populations;
	}
	
	public Population getPopulation(int index){
		return (Population) this.populations.get(index);
	}

	public WrapperTypes getWrapperType() {
		return WrapperTypes.EVA;
	}
	
}

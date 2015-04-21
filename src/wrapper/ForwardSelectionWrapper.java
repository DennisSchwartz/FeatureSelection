package wrapper;

import java.util.BitSet;

import dataStructures.ConfusionMatrix;
import dataStructures.FeatureData;
import dataStructures.ForwardSelectionResultsData;
import dataStructures.ResultsData;
import dataStructures.SubsetPerformanceData;

/**
 * Wrapper to control feature selection by forward selection.
 * 
 * Forward selection starts on empty selection and adds the feature which
 * brings the most performance improvement, if adding features doesn't improve
 * the performance anymore we have selected our features.
 * 
 * @author Johannes Schoellhorn
 *
 */
public class ForwardSelectionWrapper extends WrapperBase {

	protected BitSet latestBitSet;

	public ForwardSelectionWrapper(ForwardSelectionWrapperConfig config) {
		super(config);
	}
	
//	/**
//	 * gets the ForwardSelectionWrapperConfig object stored in this.config
//	 * @return ForwardSelectionWrapperConfig
//	 */
//	private ForwardSelectionWrapperConfig getConfig() {
//		return (ForwardSelectionWrapperConfig) this.config;
//	}

	/**
	 * Starts the wrapper
	 */
	public void run() {
		// Initializing variables
		boolean selected = false;
		int bitSetLength = data.getFeatures().length;
		this.latestBitSet = new BitSet(bitSetLength);
		int iterationCounter = 1;
		/*
		 *  new subsetPerformance with all false BitSet and a performance value
		 *  smaller than -1 (minimum performance = -1)
		 */
		this.subsetPerformance = new SubsetPerformanceData(new BitSet(), -1.1);		

		// run wrapper until best performance with a subset is reached or breakPoint is false
		while (!selected && !breakPoint) {

			double subPerformance;
			BitSet subBitSet;
			
			/*
			 * iterate over featureBitSet and create new BitSet lastestBitSet +
			 * one of the features of featureBitSet (featureBitSet[i])
			 */
			for (int i = 0; i < bitSetLength; i++) {
				subBitSet = (BitSet) this.latestBitSet.clone(); // clone BitSet
				if (subBitSet.get(i)) {
					continue;
				}
				subBitSet.set(i);								// add a feature
				
				/*
				 * calculate performance of subset
				 */
				FeatureData subset = this.filter.selectSubset(subBitSet);
				ConfusionMatrix confMatrix = this.crossVal.run(subset);
				subPerformance = this.performance.calculatePerformance(confMatrix);
				
				/*
				 * if new performance is better or equal to previously best
				 * performance create new SubsetPerformanceData in
				 * subsetPerformance with subBitSet and subPerformance
				 */
				if (subPerformance > this.subsetPerformance.getPerformance()) {
					this.subsetPerformance = new SubsetPerformanceData(subBitSet,
							subPerformance);
					BitSet newFeature = (BitSet) subBitSet.clone();
					newFeature.xor(latestBitSet);
					this.notifyNewResults(new ResultEvent(this,
							new Object[]{iterationCounter,
							this.subsetPerformance.getPerformance(),
							ResultsData.getFeatures(this.data, newFeature)[0]},
							this.getBestFeatureSubset()));
				}
			}
			
			/*
			 * if set bits in subsetPerformance.featureBitSet are less or
			 * equal to the set bits in latestBitSet we have found the best
			 * subset performance
			 */
			if (this.subsetPerformance.getFeatureBitSet().cardinality() <=
					latestBitSet.cardinality()) {
				selected = true;
			/*
			 * else we have to start with a new latestBitSet based on the
			 * subsetPerformance.featureBitSet
			 */
			} else {
				this.latestBitSet = this.subsetPerformance.getFeatureBitSet();
				iterationCounter++;
			}
		}
		
		if(breakPoint) {
			output.println("Warning: Process forced to quit! Last result will be shown.");
		}
		output.println();
		output.println(this.getFeatures(this.getBestFeatureSubset().getFeatureBitSet()));
		output.println(this.getBestFeatureSubset().getPerformance());

	}

	@Override
	public ResultsData createResultsData(String fileName, String absoluteFileName) {
		return new ForwardSelectionResultsData(fileName, absoluteFileName, this.data.clone(), this.config);
	}
	
}

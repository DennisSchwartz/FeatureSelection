package wrapper;

import java.util.BitSet;

import dataStructures.ConfusionMatrix;
import dataStructures.EvA2ResultsData;
import dataStructures.FeatureData;
import dataStructures.ResultsData;
import dataStructures.SubsetPerformanceData;
import eva2.OptimizerFactory;
import eva2.server.go.InterfaceTerminator;
import eva2.server.go.PopulationInterface;
import eva2.server.go.individuals.AbstractEAIndividual;
import eva2.server.go.operators.terminators.CombinedTerminator;
import eva2.server.go.populations.InterfaceSolutionSet;
import eva2.server.go.populations.Population;
import eva2.server.go.problems.AbstractProblemBinary;
import eva2.server.go.problems.InterfaceOptimizationProblem;
import eva2.server.modules.GOParameters;

/**
 * @author Johannes Schoellhorn
 *
 */
public class EvA2Wrapper extends WrapperBase {
	
	public EvA2Wrapper(EvA2WrapperConfig config) {
		super(config);
		EvA2Problem.dimension = data.getFeatures().length;
	}
	
	/**
	 * gets the EvA2WrapperConfig object stored in this.config
	 * @return EvA2WrapperConfig
	 */
	private EvA2WrapperConfig getConfig() {
		return (EvA2WrapperConfig) this.config;
	}

	public void run() {
		
		EvA2Problem problem = new EvA2Problem(this);
		AbstractEAIndividual bestInd;
		//Combined Terminator with Terminator from config + our BreakPointTerminator
		CombinedTerminator convT = new CombinedTerminator(this.getConfig().getTerminator(),new BreakPointTerminator(this),CombinedTerminator.OR);
		// default go-parameter instance with a GA
		GOParameters gaParams = OptimizerFactory.standardGA(problem);                
		// add an evaluation terminator
		gaParams.setTerminator(convT);
		// set a specific random seed
		gaParams.setSeed(this.getConfig().getRandomSeed());

		// run optimization and store best individual
		bestInd = (AbstractEAIndividual) OptimizerFactory.optimizeToInd(gaParams, null);
		
		this.subsetPerformance = new SubsetPerformanceData(
				(BitSet) AbstractEAIndividual.getDefaultDataObject(bestInd),
				(-1) * bestInd.getFitness(0));
		
		output.println(OptimizerFactory.terminatedBecause());
		output.println();
		output.println(this.getFeatures(this.getBestFeatureSubset().getFeatureBitSet()));
		output.println(this.getBestFeatureSubset().getPerformance());
	}

	@Override
	public ResultsData createResultsData(String fileName, String absoluteFileName) {
		return new EvA2ResultsData(fileName, absoluteFileName, this.data.clone(), this.config);
	}
	
}

class EvA2Problem extends AbstractProblemBinary {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1206988003180062055L;
	public static int dimension;
	private EvA2Wrapper parent;
	
	public EvA2Problem(EvA2Wrapper parent) {
		this.parent = parent;
	}

	public double[] eval(BitSet bs) {
		// Initialize
		double[] fitness = new double[1];
		
		// filter FD with given BitSet
		FeatureData subset = this.parent.filter.selectSubset(bs);
		ConfusionMatrix confMatrix = this.parent.crossVal.run(subset);
		
		// calculate Fitness and negate result
		fitness[0] = (-1) * this.parent.performance.calculatePerformance(confMatrix);
		
		return fitness;
	}

	public int getProblemDimension() {
		return dimension;
	}
	

	
	public void evaluatePopulationEnd(Population population) {
		
		double meanFitness = (-1 * population.getMeanFitness()[0]);
		double bestFitness = (-1 * population.getBestFitness()[0]);
		double stdDevFitness = this.stdDev(population);
		
		if (this.parent.subsetPerformance == null) {
			this.parent.subsetPerformance = new SubsetPerformanceData((BitSet) AbstractEAIndividual.getDefaultDataObject(population.getBestEAIndividual()),
																	  bestFitness);
		} else if (bestFitness > this.parent.subsetPerformance.getPerformance()) {
			this.parent.subsetPerformance = new SubsetPerformanceData((BitSet) AbstractEAIndividual.getDefaultDataObject(population.getBestEAIndividual()),
					  												   bestFitness);
		}
		
		this.parent.notifyNewResults(new ResultEvent(this.parent, new Object[]{meanFitness, bestFitness, stdDevFitness, population.clone()}, this.parent.getBestFeatureSubset()));
		
    }

	public Object clone() {
		return new EvA2Problem(this.parent);
	}
	
	private double stdDev(Population population) {
		double res = 0;
		// calculate variance
		for (Object ind : population) {
			res += Math.pow(((AbstractEAIndividual) ind).getFitness()[0] - population.getMeanFitness()[0], 2);
		}
		
		// std deviation = sqrt of variance / number of individuals
		res = Math.sqrt(res/population.size());
		
		return res;
	}
	
}

/**
 * BreakPointTerminator forces EvA2 to quit, if wrapper.breakpoint is false
 * @author julianschwab
 *
 */
class BreakPointTerminator implements InterfaceTerminator{

	
	EvA2Wrapper wrapper;
	
	public BreakPointTerminator(EvA2Wrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	@Override
	public void init(InterfaceOptimizationProblem arg0) {
		
	}

	@Override
	public boolean isTerminated(PopulationInterface arg0) {
		if(wrapper.breakPoint){
			return true;
		}
		return false;
	}

	@Override
	public boolean isTerminated(InterfaceSolutionSet arg0) {
		if(wrapper.breakPoint){
			return true;
		}
		return false;
	}

	@Override
	public String lastTerminationMessage() {
		return "Warning: Process forced to quit! Last result will be shown.";
	}

}

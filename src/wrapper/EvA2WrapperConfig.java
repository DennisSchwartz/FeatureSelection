package wrapper;

import dataStructures.FeatureData;
import eva2.server.go.InterfaceTerminator;
import eva2.server.go.operators.selection.SelectXProbRouletteWheel;
import eva2.server.go.operators.terminators.EvaluationTerminator;
import eva2.server.go.populations.Population;

/**
 * @author Johannes Schoellhorn
 *
 */
public class EvA2WrapperConfig extends WrapperConfigBase {
	
	/**
	 * static default values
	 */
	public final static int TERMINATOR_SIZE = 20000;
	public final static int POPULATION_SIZE = 250;
	public final static int RANDOM_SEED = 2342;
	public final static double CONVERGENCE_TRESHOLD = 0.00001;
	
	/**
	 * @param data
	 */
	public EvA2WrapperConfig(FeatureData data) {
		super(data);
	}
	
	public EvA2WrapperConfig(WrapperConfigBase config) {
		super(config.getData());
		this.crossVal = config.crossVal;
		this.performance = config.performance;
		this.output = config.output;
	}

	int randomSeed = 2342;
	int populationSize = 250;
	boolean elitism = false;
	InterfaceTerminator terminator = new EvaluationTerminator(20000);
	SelectXProbRouletteWheel parentSelection = new SelectXProbRouletteWheel();
	Population population = new Population(populationSize);

	/**
	 * @return the elitism
	 */
	public boolean isElitism() {
		return elitism;
	}

	/**
	 * @return the parentSelection
	 */
	public SelectXProbRouletteWheel getParentSelection() {
		return parentSelection;
	}

	/**
	 * @return the population
	 */
	public Population getPopulation() {
		return population;
	}

	/**
	 * @return the randomSeed
	 */
	public int getRandomSeed() {
		return randomSeed;
	}

	/**
	 * @return the terminator
	 */
	public InterfaceTerminator getTerminator() {
		return terminator;
	}

	/**
	 * @param randomSeed the randomSeed to set
	 */
	public void setRandomSeed(int randomSeed) {
		this.randomSeed = randomSeed;
	}
	
	/**
	 * Sets the population
	 * @param populationSize Size of the population
	 */
	public void setPopulation(int populationSize) {
		this.populationSize = populationSize;
		this.population = new Population(populationSize);
	}

	/**
	 * @param terminator the terminator to set
	 */
	public void setTerminator(InterfaceTerminator terminator) {
		this.terminator = terminator;
	}
	
	/**
	 * @param parentSelection the parentSelection to set
	 */
	public void setParentSelection(SelectXProbRouletteWheel parentSelection) {
		this.parentSelection = parentSelection;
	}
	
	/**
	 * @param elitism the elitism to set
	 */
	public void setElitism(boolean elitism) {
		this.elitism = elitism;
	}

	/* (non-Javadoc)
	 * @see wrapper.WrapperConfigBase#getWrapperInfo()
	 */
	@Override
	public String getWrapperInfo() {
		String res = "";
		res += "Wrapper Type:\n";
		res += "   EvA2\n";
		res += "   EvA2 Population Size: " + this.populationSize + "\n";
		res += "   EvA2 Terminator:     " + this.terminator + "\n";
		res += "   EvA2 RandomSeed:      " + this.randomSeed;
		return res;
	}

}

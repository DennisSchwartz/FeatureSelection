package wrapper.crossvalidation;

import wrapper.crossvalidation.classifier.CSVCClassifier;
import wrapper.crossvalidation.classifier.CSVCClassifierConfig;
import wrapper.crossvalidation.classifier.ClassifierBase;

public abstract class CrossValidationConfigBase {
	
	ClassifierBase classifier = new CSVCClassifier(new CSVCClassifierConfig());
	long randomSeed = 7L;

	/**
	 * @return the randomSeed
	 */
	public long getRandomSeed() {
		return randomSeed;
	}
	/**
	 * @return the classifier
	 */
	public ClassifierBase getClassifier() {
		return classifier;
	}

	/**
	 * @param randomSeed the randomSeed to set
	 */
	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}
	
	/**
	 * @param classifier the classifier to set
	 */
	public void setClassifier(ClassifierBase classifier) {
		this.classifier = classifier;
	}
	
	public String toString() {
		return this.getCrossValInfo();
	}
	
	abstract protected String getCrossValInfo();

}

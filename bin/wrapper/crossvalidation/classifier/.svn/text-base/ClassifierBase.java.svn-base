package wrapper.crossvalidation.classifier;

import java.util.List;

import dataStructures.FeatureData;

public abstract class ClassifierBase {
	
	ClassifierConfigBase config;
	
	FeatureData train;
	FeatureData test;
	List<Boolean> predict;
	
	public ClassifierBase(ClassifierConfigBase config) {
		this.config = config;
	}
	
	public List<Boolean> getPredict() {
		return this.predict;
	}
	
	public abstract void run(FeatureData train, FeatureData test);
	
	public ClassifierConfigBase getConfig() {
		return this.config;
	}
	
}

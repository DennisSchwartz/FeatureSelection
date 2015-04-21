package wrapper.crossvalidation.classifier;

public abstract class ClassifierConfigBase {
	
	public String toString() {
		return this.getClassifierInfo();
	}

	abstract public String getClassifierInfo();
}

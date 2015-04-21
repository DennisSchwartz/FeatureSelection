package dataStructures;

/**
 * Datastorage for processed feature data
 * @author Johannes Schoellhorn
 */
public class FeatureData {

	private String[] features;
	private String[] samples;
	private boolean[] classes;
	/**
	 * row (float[i]) corresponds to this.samples[i] and column (float[0][j])
	 * to this.features[j]
	 */
	private double[][] values;
	
	/**
	 * constructs a FeatureData Object which can't be altered
	 * @param features String[] containing the features
	 * @param samples int[] containing the samples
	 * @param classes boolean[] containing the classes
	 * @param values float[][] containing the values
	 */
	public FeatureData(String[] features, String[] samples, boolean[] classes, double[][] values) {
		this.features = features;
		this.samples = samples;
		this.classes = classes;
		this.values = values;
	}
	
	/**
	 * constructor to clone a FeatureData object
	 * @param data FeatureData object
	 */
	private FeatureData(FeatureData data) {
		this.features = data.getFeatures();
		this.samples = data.getSamples();
		this.classes = data.getClasses();
		this.values = data.getValues();
	}
	
	/**
	 * @return the features
	 */
	public String[] getFeatures() {
		return features.clone();
	}

	/**
	 * @return the samples
	 */
	public String[] getSamples() {
		return samples.clone();
	}

	/**
	 * @return the classes
	 */
	public boolean[] getClasses() {
		return classes.clone();
	}

	/**
	 * @return the values
	 */
	public double[][] getValues() {
		return values.clone();
	}

	/**
	 * extracts all sample values associated with the feature
	 * at given position
	 * @param pos position of the feature
	 * @return float[] with sample data
	 */
	public double[] getFeatureCol(int pos) {
		double[] r = new double[this.samples.length];
		
		for (int i = 0; i < this.samples.length; i++) {
			r[i] = this.values[i][pos];
		}
		
		return r;
	}

	public String toString() {
		return this.toString(this.samples[0].toString().length(), this.features[0].length());
	}
	
	/**
	 * converts the FeatureData data structure to a String representation
	 * using given arguments to format columns
	 * @param sampleWidth width of the samples column
	 * @param featureWidth width of the features columns
	 */
	public String toString(int sampleWidth, int featureWidth) {
		if (sampleWidth <= 0 || featureWidth <= 0) {
			System.err.println("Use proper values for width in toString(int sampleWidth, int featureWidth)! Using toString() method instead!");
			return this.toString();
		}
		int classWidth = 5;
		String sampleFormat = "| %" + sampleWidth + "." + sampleWidth + "s |";
		String classFormat = " %" + classWidth + "." + classWidth + "s |";
		String featureFormat = " %" + featureWidth + "." + featureWidth + "s |";
		String r = String.format(sampleFormat, "samples") + String.format(classFormat, "classes");
		for (String feature : this.features) {
			r += String.format(featureFormat, feature);
		}
		r += "\n";
		int rows = samples.length;
		for (int i = 0; i < rows; i++) {
			r += String.format(sampleFormat, samples[i]);
			r += String.format(classFormat, classes[i]);
			for (double value : this.values[i]) {
				r += String.format(featureFormat, value);
			}
			r += "\n";
		}
		
		return r;
	}
	
	public FeatureData clone() {
		return new FeatureData(this);
	}
}

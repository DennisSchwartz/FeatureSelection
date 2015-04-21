package wrapper.crossvalidation.classifier;

import libsvm.svm_parameter;
import libsvm.svm_print_interface;

public class CSVCClassifierConfig extends ClassifierConfigBase {

	// default parameters
	protected int svm_type = svm_parameter.C_SVC;
	protected int kernelType = svm_parameter.LINEAR;
	protected int degree = 3;
	protected double gamma = 0;	// 1/num_features
	protected double coef0 = 0;
	protected double nu = 0.5;
	protected double cache_size = 100;
	protected double C = 1;
	protected double eps = 1e-3;
	protected double p = 0.1;
	protected int shrinking = 1;
	protected int probability = 0;
	protected int nrWeight = 0;
	protected int[] weightLabel = new int[0];
	protected double[] weight = new double[0];
	
	protected svm_print_interface printInterface = new QuietInterface();
	
	public svm_parameter getSVMParameter() {
		svm_parameter r = new svm_parameter();
		r.svm_type = this.svm_type;
		r.kernel_type = this.kernelType;
		r.degree = this.degree;
		r.gamma = this.gamma;
		r.coef0 = this.coef0;
		r.nu = this.nu;
		r.cache_size = this.cache_size;
		r.C = this.C;
		r.eps = this.eps;
		r.p = this.p;
		r.shrinking = this.shrinking;
		r.probability = this.probability;
		r.nr_weight = this.nrWeight;
		r.weight_label = this.weightLabel;
		r.weight = this.weight;
		return r;
	}

	public String getClassifierInfo() {
		String res = "";
		res += "Classifier Type:\n";
		res += "   SVM - CSVC";
		return res;
	}

}

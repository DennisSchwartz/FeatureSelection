package wrapper.crossvalidation.classifier;

import java.util.ArrayList;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_print_interface;
import libsvm.svm_problem;
import dataStructures.FeatureData;

/**
 * CSVCClassifier class does classification with LibSvm and the svm type = CSV_C.
 * @author julianschwab
 *
 */
public class CSVCClassifier extends ClassifierBase {

	/**
	 * constructor which hast CSVCClassifierConfig object to give parameters we need for CSV_C svm
	 * @param config
	 */
	public CSVCClassifier(CSVCClassifierConfig config) {
		super(config);

	}
	
	/**
	 * gets the CSVCClassifierConfig object stored in this.config
	 * @return CSVCClassifierConfig
	 */
	public CSVCClassifierConfig getConfig() {
		return (CSVCClassifierConfig) this.config;
	}

	@Override
	/**
	 * Starts CSVClassifier with libSVM vector machine. 
	 * Parameters for the svm are CSVC, kerneltype linear, nr_weight = 0, nr_weight_label = null, weight = null
	 * @param train FeatureData object we use to train the svm
	 * @param test FeatureData object where we use knowledge from our training on. On this data we give a prediction that is stored in predict[]
	 */
	public void run(FeatureData train, FeatureData test) {

		/*
		 * Initializing of Objects we need to process the CSVCClassification
		 */
		
		svm.svm_set_print_string_function(this.getConfig().printInterface);

		//svm_parameter object param with default parameters
		svm_parameter param = this.getConfig().getSVMParameter();
		//predict[] to store class values we get from tested data
		predict = new ArrayList<Boolean>();
		//l of svm_problem with length = number of samples
		int length = train.getSamples().length;
		//y of svm_problem with values of train.classes
		double[] classValues = new double[train.getClasses().length];
		//svm_problem which gets length (l), classValues (y) after these objects are declared
		svm_problem problem = new svm_problem();
		//model where we store trained data later
		svm_model trained; 
		//svm_node[][] where are test.values stored in vector representation. converting is done with createSVMNodes
		svm_node[][] toTest = createSVMNodes(test);

		/*
		 * Declaration of Objects we initialized before
		 */

		//Loop fills classValues with values of train.classes. 1.0 for true, 0.0 for false.
		for(int i = 0;i < train.getClasses().length;i++) {
			if(train.getClasses()[i]) {
				classValues[i] = 1.0;
			}
			else {
				classValues[i] = 0.0;
			}
		}

		//Declaration of svm_problem problem
		problem.l = length;
		problem.y = classValues;
		problem.x = this.createSVMNodes(train);


		//Declaration of svm_model trained with svm.svm_train method from LibSvm
		trained = svm.svm_train(problem, param);


		//fill predict[] with values given from svm.svm_predict method
		for(int i = 0; i<toTest.length; i++) {		//go through test data

			//convert double from predict method in boolean to have the same type as in classes[] from feature data
			//store predicted value in predict field from classifier class
			// if 1 store true. if 0 store false 
			if(svm.svm_predict(trained, createSVMNodes(test)[i])==1){ 
				predict.add(i, true);
			}
			else{
				predict.add(i, false);
			}


		}

	}



	/**
	 * createSVMNodes converts values of FeatureData object in svm_node representation
	 * @param data FeatureData which should be converted
	 * @return svm_node[][]what can be used to create a svm_problem object
	 */
	svm_node[][] createSVMNodes(FeatureData data) {

		//Initialize svm_node[][]where converted data.values[][] come in by going through data.values[][] with 2 for loops
		svm_node[][] problemNodes = new svm_node[data.getValues().length][data.getValues()[0].length];

		for(int i = 0;i < data.getValues().length;i++) {		//Go through columns of data.values 
			for(int j = 0; j < data.getValues()[i].length;j++) { // Go through lines of data.values
				svm_node node = new svm_node();
				node.index = j;									//node.index is set with column number
				node.value = data.getValues()[i][j];			//node.value is filled with value in field[i][j]

				problemNodes[i][j] = node;						//Put node in array
			}
		}

		return problemNodes;
	}
}

/**
 * Quiet Interface to use with svm.svm_set_print_string_function(svm_print_interface interface)
 * which supresses all output from the svm library
 * @author Johannes Schoellhorn
 *
 */
final class QuietInterface implements svm_print_interface {

	/**
	 * doesn't output anything
	 */
	public void print(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

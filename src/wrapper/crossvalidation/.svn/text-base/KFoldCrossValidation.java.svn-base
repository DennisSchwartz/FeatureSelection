package wrapper.crossvalidation;

import java.util.ArrayList;
import java.util.List;

import dataStructures.ConfusionMatrix;
import dataStructures.FeatureData;

/**
 * Class extends CrossValidationBase implementing k-fold crossvalidation.
 * It will split the data in k parts. Afterwards it will test all parts by testing one of the parts at a time 
 * after training on the other k-1.
 * @author Dennis Schwartz & Johannes Schoellhorn
 *
 */
public class KFoldCrossValidation extends CrossValidationBase {

	/**
	 * Constructor needs a configuration object.
	 * @param config configuration file
	 */
	public KFoldCrossValidation(KFoldCrossValidationConfig config) {
		super(config);
	}
	
	/**
	 * gets the KFoldCrossValidationConfig object stored in this.config
	 * @return KFoldCrossValidationConfig
	 */
	public KFoldCrossValidationConfig getConfig() {
		return (KFoldCrossValidationConfig) this.config;
	}

	@Override
	/**
	 * This will start the k-fold cross validation. It contains the splitting of the Data and will run the classification.
	 */
	public ConfusionMatrix run(FeatureData subset) {
		//Shuffle given data, leaving its content in a random order.
		FeatureData shuffledData = this.shuffleSamples(subset);
		//Initialize array/list for the partial data and resulting list of results.
		this.splittedList = new FeatureData[this.getConfig().getFold()];
		this.predictList = new ArrayList<List<Boolean>>();
		
		this.splitList(shuffledData);
		
		for (int i = 0; i < this.splittedList.length; i++) {
			splittedList[i] = this.shuffleSamples(splittedList[i]);
		}
		
		/*
		 * The k-1 parts of the split list that are not used to test, are merged to form the training data using the 
		 * concatFeatureData function below.
		 * Afterwards the classification over the test and training data is run and the results are added to the predictList.
		 * This is repeated for all parts of the split list until every part has been tested.
		 */
		for (int i = 0; i < this.splittedList.length; i++) {
			FeatureData[] trainArray = new FeatureData[this.splittedList.length - 1];
			FeatureData test = this.splittedList[i];
			
			int pos = 0;
			for (int j = 0; j < this.splittedList.length; j++) {
				if (j != i) {
					trainArray[pos] = this.splittedList[j];
					pos++;
				}
			}
			
			FeatureData train = this.concatFeatureData(trainArray);
			
			this.classifier.run(train, test);
			
			this.predictList.add(i, this.classifier.getPredict());
			
		}
		//Calculate confusion matrix according to the computed predict list.
		return this.calculateConfusionMatrix();

	}
	
	/**
	 * Extracts the samples of specified class
	 * @param shuffledData FeatureData to extract from
	 * @param cl class of samples to extract
	 * @return FeatureData consisting only of samples of class cl 
	 */
	protected FeatureData extractClass(FeatureData shuffledData, boolean cl) {
		// determine how many samples of class cl we have in our FeatureData
		int count = 0;
		for (int i = 0; i < shuffledData.getClasses().length; i++) {
			if (shuffledData.getClasses()[i] == cl) {
				count++;
			}
		}
		
		String[] tmpSamples = new String[count];
		boolean[] tmpClasses = new boolean[count];
		double[][] tmpValues = new double[count][shuffledData.getFeatures().length];
		
		// extract all samples of class cl by iterating over the FeaturesData
		int counter = 0;
		for (int i = 0; i < shuffledData.getSamples().length; i++) {
			if (shuffledData.getClasses()[i] == cl) {
				tmpSamples[counter] = shuffledData.getSamples()[i];
				tmpClasses[counter] = shuffledData.getClasses()[i];
				tmpValues[counter] = shuffledData.getValues()[i];
				counter++;
			}
		}
		
		return new FeatureData(shuffledData.getFeatures(),
				tmpSamples, tmpClasses, tmpValues);
	}
	
	/**
	 * Splits the given FeatureData into fold * packages and stratifies the
	 * data in the process<br><br>
	 * packages are stored in this.splittedList<br>
	 * packages are of size n or n+1 (where n is number of samples divided by the fold)
	 * @param shuffledData FeatureData to split
	 */
	protected void splitList(FeatureData shuffledData) {
		int fold = this.getConfig().getFold();
		
		FeatureData trueSamp = this.extractClass(shuffledData, true);
		FeatureData falseSamp = this.extractClass(shuffledData, false);
		
		// calculate ratio of true samples vs. false samples
		double trueRatio = trueSamp.getSamples().length / (double) shuffledData.getSamples().length;
		double falseRatio = falseSamp.getSamples().length / (double) shuffledData.getSamples().length;
		
		// minimal size of a package in splittedList
		int sizePerFold = shuffledData.getSamples().length / fold;
		// number of packages which have an additional element
		int biggerFolds = shuffledData.getSamples().length % fold;
		
		// calculate how many true/false elements to use per fold
		// by converting to integer we use the minimal number of elements
		int truePerFold = (int) (trueRatio * sizePerFold);
		int falsePerFold = (int) (falseRatio * sizePerFold);
		
		// calculate how many true/false elements remain after filling the
		// packages with minimal number of true/false per fold
		int trueRem = trueSamp.getSamples().length - truePerFold * fold;
		int falseRem = falseSamp.getSamples().length - falsePerFold * fold;
		
		// position of sample in the true/false samples FeatureData objects
		int truePos = 0;
		int falsePos = 0;
		// fill packages
		for (int i = 0; i < fold; i++) {
			// position of the sample in the package
			int pos = 0;
			// size of the package
			int foldSize = sizePerFold;
			
			// determine if we still need packages with a bigger size than
			// the minimal package size
			if (biggerFolds > 0) {
				foldSize++;
				biggerFolds--;
			}

			// temporary storage for FeatureData elements
			String[] tmpSamples = new String[foldSize];
			boolean[] tmpClasses = new boolean[foldSize];
			double[][] tmpValues = new double[foldSize][shuffledData.getFeatures().length];
			
			// as long as we're at a valid position in the package insert
			// new samples in package
			while (pos < foldSize) {
				// insert minimal number of true samples
				for (int j = 0; j < truePerFold; j++) {
					
					tmpSamples[pos] = trueSamp.getSamples()[truePos];
					tmpClasses[pos] = trueSamp.getClasses()[truePos];
					tmpValues[pos] = trueSamp.getValues()[truePos];
					
					truePos++;
					pos++;
				}
				// insert minimal number of false samples
				for (int j = 0; j < falsePerFold; j++) {
					
					tmpSamples[pos] = falseSamp.getSamples()[falsePos];
					tmpClasses[pos] = falseSamp.getClasses()[falsePos];
					tmpValues[pos] = falseSamp.getValues()[falsePos];
					
					falsePos++;
					pos++;
				}
				
				// initialize variable to switch between true/false samples
				// to get an equal distribution of samples in the package
				boolean switcher = true;
				while (pos < foldSize) {
					// try to insert a true sample if there are any remaining
					// else insert a false sample
					if (switcher) {
						if (trueRem > 0) {

							tmpSamples[pos] = trueSamp.getSamples()[truePos];
							tmpClasses[pos] = trueSamp.getClasses()[truePos];
							tmpValues[pos] = trueSamp.getValues()[truePos];

							trueRem--;
							truePos++;
							pos++;
						} else {

							tmpSamples[pos] = falseSamp.getSamples()[falsePos];
							tmpClasses[pos] = falseSamp.getClasses()[falsePos];
							tmpValues[pos] = falseSamp.getValues()[falsePos];

							falseRem--;
							falsePos++;
							pos++;
						}
						
						// switch the switcher
						switcher = !switcher;
					// try to insert a false sample if there are any remaining
					// else insert a true sample
					} else {
						if (falseRem > 0) {

							tmpSamples[pos] = falseSamp.getSamples()[falsePos];
							tmpClasses[pos] = falseSamp.getClasses()[falsePos];
							tmpValues[pos] = falseSamp.getValues()[falsePos];

							falseRem--;
							falsePos++;
							pos++;
						} else {

							tmpSamples[pos] = trueSamp.getSamples()[truePos];
							tmpClasses[pos] = trueSamp.getClasses()[truePos];
							tmpValues[pos] = trueSamp.getValues()[truePos];

							trueRem--;
							truePos++;
							pos++;
						}
						// switch the switcher
						switcher = !switcher;
					}
				}
			}
			// create FeatureData from temporary elements and insert in
			// splitList
			this.splittedList[i] = new FeatureData(shuffledData.getFeatures(),
					tmpSamples, tmpClasses, tmpValues);
		}
	}

	@Deprecated
	protected void splitListOld(FeatureData shuffledData) {

		shuffledData = this.stratifySamples(shuffledData);
		
		//Determine and store the total length and the length of the k parts.
		int partsLength = shuffledData.getSamples().length / this.getConfig().getFold();
		int totalLength = shuffledData.getClasses().length;
		
		//Splitting the List
		for (int i = 0; i < this.getConfig().getFold(); i++) {
			int limit;
			int length;
			
			/*
			 * Loop above will iterate over the k parts of the shuffled data extracting belonging data to the 
			 * current cell of the splitList array.
			 * The last of the k parts may not have the same length, but the total length mod(k).
			 * This is the discrimination between the last iteration and the preceding.
			 */
			if (i == this.getConfig().getFold() - 1) {
				limit = totalLength;
				length = totalLength - (i * partsLength);
			} else {
				limit = (i + 1) * partsLength;
				length = partsLength;
			}
			
			//Initialize temporary storage for data
			String[] tempSamples = new String[length];
			boolean[] tempClasses = new boolean[length];
			double[][] tempValues = new double[length][shuffledData.getFeatures().length];
			
			//Interates over the shuffledData extracting it.
			for (int j = i * partsLength; j < limit; j++) {

				tempSamples[j - (i * partsLength)] = shuffledData.getSamples()[j];
				tempClasses[j - (i * partsLength)] = shuffledData.getClasses()[j];
				tempValues[j - (i * partsLength)] = shuffledData.getValues()[j];
			}
			//Stores extracted data to splitList. 
			this.splittedList[i] = new FeatureData(shuffledData.getFeatures(),
					tempSamples, tempClasses, tempValues);

		}
	}
	
	//Concatenates an array of FeatureData.
	protected FeatureData concatFeatureData(FeatureData[] data) {
		int totalLength = 0;
		String[] samples;
		boolean[] classes;
		double[][] values;
		
		for (int i = 0; i < data.length; i++) {
			totalLength += data[i].getClasses().length;
		}
		
		samples = new String[totalLength];
		classes = new boolean[totalLength];
		values = new double[totalLength][data[0].getFeatures().length];
		
		int pos = 0;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].getClasses().length; j++) {
				samples[pos] = data[i].getSamples()[j];
				classes[pos] = data[i].getClasses()[j];
				values[pos] = data[i].getValues()[j];
				pos++;
			}
		}

		return new FeatureData(data[0].getFeatures(), samples, classes, values);
	}
	
	@Deprecated
	/**
	 * stratify Data is to ensure that every featureData in the splittedList has more or less the same case/control ratio
	 * as the whole feature data obejct has.
	 * There for the FeatureData object is sorted in k packages (k for the kfold factor). Each package has more or less the same ratio.
	 * @param data
	 * @author Julian Schwab
	 */
	public FeatureData stratifySamples(FeatureData data) {
		
		/*Initialize*/
		
		List<Integer> casePositions = new ArrayList<Integer>(); //store position of case/control classes
		List<Integer> controlPositions = new ArrayList<Integer>();
		
		int timesCase;
		int timesControl;
		
		int countCase = 0;
		int countControl = 0;
		
		int partsLength = data.getSamples().length / this.getConfig().getFold();
		int totalLength = data.getClasses().length;
		
	
		//Get rows with class case
		for(int i = 0; i < data.getClasses().length; i++) {
			if(data.getClasses()[i]) {
				casePositions.add(i);
			}
			else {
				controlPositions.add(i);
			}
		}
		
		//Fill splittedList with config.getFold FD objects
		//therefore we initialize a temp array for each FD attribute
		String[] tempSamples = new String[data.getSamples().length];
		boolean[] tempClasses = new boolean[data.getClasses().length];
		double[][] tempValues = new double[data.getValues().length][data.getFeatures().length];
		
		
		for (int i = 0; i < this.getConfig().getFold(); i++) {
			int limit;
			
			//Get amount of case/control samples in each splitList
			timesCase = casePositions.size()/this.getConfig().getFold();
			timesControl = controlPositions.size()/this.getConfig().getFold();
			
			/*
			 * This part is from splitListMethode above
			 * Loop above will iterate over the k parts of the shuffled data extracting belonging data to the 
			 * current cell of the splitList array.
			 * The last of the k parts may not have the same length, but the total length mod(k).
			 * This is the discrimination between the last iteration and the preceding.
			 */
			if (i == this.getConfig().getFold() - 1) {
				limit = totalLength;
				timesCase = casePositions.size() - countCase;
				timesControl = controlPositions.size() - countControl;
			} else {
				limit = (i + 1) * partsLength;
				
			}
			
			
			
			//Interates over the FD data
			//the inner if statements ensure that we have the right case/control relation in each of the k packages
			for (int j = i * partsLength; j < limit; j++) {
				
				if(timesCase > 0) {	//fills temp array timesCase-times with case Samples
					tempSamples[j] = data.getSamples()[casePositions.get(countCase)];
					tempClasses[j] = data.getClasses()[casePositions.get(countCase)];
					tempValues[j] = data.getValues()[casePositions.get(countCase)];
					countCase++;
					timesCase--;
				}
				
				else if(timesControl > 0) { //fills temp array timesControl-times with control Samples
					tempSamples[j] = data.getSamples()[controlPositions.get(countControl)];
					tempClasses[j] = data.getClasses()[controlPositions.get(countControl)];
					tempValues[j] = data.getValues()[controlPositions.get(countControl)];
					countControl++;
					timesControl--;
					
				}
				//We have floor(timesCase) and floor(timesControl) inserted now
				//but because of the floor(we get biy dividing with int) there may miss a value
				//with the i mod 2 = 0 statement we ensure that case and control are set in the missing value alternating
				else{
					if((i % 2) == 0) {
						tempSamples[j] = data.getSamples()[casePositions.get(countCase)];
						tempClasses[j] = data.getClasses()[casePositions.get(countCase)];
						tempValues[j] = data.getValues()[casePositions.get(countCase)];
						countCase++;
					}
					
					else {
						tempSamples[j] = data.getSamples()[controlPositions.get(countControl)];
						tempClasses[j] = data.getClasses()[controlPositions.get(countControl)];
						tempValues[j] = data.getValues()[controlPositions.get(countControl)];
						countControl++;
					}
				}
				
			}
			
			

		}
		//returns FeatureData with k-packages within. 
		//Each package has more or less the same case/control ratio as the whole feature data object has
		return new FeatureData(data.getFeatures(), tempSamples, tempClasses, tempValues);
	}
	
	
}

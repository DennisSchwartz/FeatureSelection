package wrapper.crossvalidation;

import java.util.List;
import java.util.Random;

import wrapper.crossvalidation.classifier.ClassifierBase;
import dataStructures.ConfusionMatrix;
import dataStructures.FeatureData;

/**
 * Abstract class CrossValidationBase has all fields and and functions every CrossValidation child must have
 * @author julianschwab & Dennis Schwartz
 *
 */
public abstract class CrossValidationBase {
	
		CrossValidationConfigBase config;

		ClassifierBase classifier;
		
		FeatureData[] splittedList;
		List<List<Boolean>> predictList;
		
		/**
		 * Constructor that every CrossValidation class must have
		 * @param config Configuration file
		 */
		public CrossValidationBase(CrossValidationConfigBase config) {
			this.config = config;
			this.classifier = config.getClassifier();
		}

		/**
		 * run starts the cross validation
		 */
		public abstract ConfusionMatrix run(FeatureData subset);
		
		/**
		 * Shuffles the content of a FeatureData object, leaving the samples in a random order. This will guarantee that the given samples are not sorted when they are tested.
		 * @param data FeatureData object to be shuffled.
		 * @return FeatureData with shuffled sample order.
		 */
		
		protected FeatureData shuffleSamples(FeatureData data) {
			//Initialization of the resulting class, sample and value arrays.
			boolean[] shuffledClasses = data.getClasses();
			String[] shuffledSamples = data.getSamples();
			double[][] shuffledValues = data.getValues();
			//This will generate a pseudorandom integer number.
			Random randGen = new Random(this.config.getRandomSeed());
			//Temporary values used to switch samples, class and values at the current position with random position.
			boolean tempClass;
			String tempSample;
			double[] tempValues;
			
			//Loop iterates over all samples switching the current one with one at a random position.
			for (int i = 0; i < shuffledSamples.length; i++) {
				//Generates new int number.
				int randomPosition = randGen.nextInt(shuffledSamples.length);
				//Values at current position are stored temporarily.
				tempClass = shuffledClasses[i];
				tempSample = shuffledSamples[i];
				tempValues = shuffledValues[i];
				//Values from a random position are assigned to the current position
				shuffledClasses[i] = shuffledClasses[randomPosition];
				shuffledSamples[i] = shuffledSamples[randomPosition];
				shuffledValues[i] = shuffledValues[randomPosition];
				//The temporary values of the former current position are assigned to the given random position
				shuffledClasses[randomPosition] = tempClass;
				shuffledSamples[randomPosition] = tempSample;
				shuffledValues[randomPosition] = tempValues;
				
				
			}
			return new FeatureData(data.getFeatures(), shuffledSamples, shuffledClasses, shuffledValues);
			
			
		}
		
		/**
		 * calculateConfusionMatrix() calculates confusionmatrix on the trained and tested data. The method proves the results of the classification. 
		 * If tested and real dataset have the same class it is either true positive or false positive (belongs to class true/false). 
		 * If the tested dataset differs from the real class its either true negative or false negative. The created Matrix is stored in class attribute confusionMatrix.
		 */
		protected ConfusionMatrix calculateConfusionMatrix() {
			//declaration of variables for confusionMatrix
			int tp = 0;
			int fp = 0;
			int tn = 0;
			int fn = 0;
			
			
			for(int i=0;i<predictList.size();i++) { //loop to go through predictList and splitList
				for(int j=0;j<predictList.get(i).size();j++) { //loop to go trough predictList[i], splitList[i].getClasses
					
					if(predictList.get(i).get(j) == splittedList[i].getClasses()[j]) { //guess of svm was true
						
						if(predictList.get(i).get(j)) {
							tp++; //svm tipped class true and it was true
						}
						else {
							tn++; //svm tipped class false and it was false
						}
					}
					else {
						if(predictList.get(i).get(j)) {
							fp++; //svm tipped class true but it was class false
						}
						else{
							fn++; //svm tipped class false but it was class true
						}
					}
					
					
				}
			}
			
			return new ConfusionMatrix(tp,fp,tn,fn);
			
		}
		
		public CrossValidationConfigBase getConfig() {
			return this.config;
		}
}

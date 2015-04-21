package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import dataStructures.FeatureData;

/**
 * converts Data from a CSV source to internal FeatureData object
 * @author Johannes Schoellhorn
 *
 */
public class CSVReader implements ReaderI {
	
	private String separator;
	
	/**
	 * construct new CSVReader with given separator
	 * @param separator
	 */
	public CSVReader(String separator) {
		this.separator = separator.trim();
	}

	public FeatureData read(Reader r) throws IOException, DataFormatException {
		BufferedReader br = new BufferedReader(r);
		
		// columns in a row, to be set after splitting first line
		int colCount = 0;
		// initialize case indicator with null
		String ca = null;
		
		// initialize temporary storage
		String[] features;
		List<String> tmpSamples = new ArrayList<String>();
		String[] samples;
		List<Boolean> tmpClasses = new ArrayList<Boolean>();
		boolean[] classes;
		List<double[]> tmpValues = new ArrayList<double[]>();
		double[][] values;
		
		String tmpLine = br.readLine();
		String[] tmpCols;
		
		/* checking for errors and throwing exceptions
		 */
		if (tmpLine == null) {
			throw new IOException("Error: empty file!");
		}

		/* parse first line containing the features and
		 * write on appropriate location in feature array 
		 */
		tmpCols = tmpLine.trim().split(this.separator);
		colCount = tmpCols.length;
		
		/* checking for errors and throwing exceptions
		 */
		if (colCount < 3) {
			throw new DataFormatException("Error: not enough columns detected!");
		}
		
		features = new String[tmpCols.length-2];
		for (int i = 2; i < tmpCols.length; i++) {
			features[i-2] = tmpCols[i].trim();
		}
		
		tmpLine = br.readLine();
		
		/* iterate over lines in Reader Object split on
		 * separator and adds fields to appropriate 
		 * temporary storage variables 
		 */
		while (tmpLine != null) {
			tmpCols = tmpLine.trim().split(this.separator);
			/* checking for errors and throwing exceptions
			 */
			if (tmpCols.length != colCount) {
				throw new DataFormatException("Error: number of columns in rows not consistent!");
			}
			// first field is sample
			tmpSamples.add(tmpCols[0].trim());
			// second field defines class
			// use value to set case indicator, if not yet set
			if (ca == null) {
				ca = tmpCols[1].trim();
			}
			// test for case or control and add to list
			if (tmpCols[1].trim().equals(ca)) {
				tmpClasses.add(false);
			} else {
				tmpClasses.add(true);
			}

			double[] row = new double[features.length];
			// rest of fields represent values relating to a feature
			for (int i = 2; i < tmpCols.length; i++) {
				row[i-2] = Double.parseDouble(tmpCols[i].trim());
			}
			tmpValues.add(row);
			// read next line
			tmpLine = br.readLine();
		}

		// convert to arrays
		samples = new String[tmpSamples.size()];
		for (int i = 0; i < tmpSamples.size(); i++) {
			samples[i] = tmpSamples.get(i);
		}
		classes = new boolean[tmpClasses.size()];
		for (int i = 0; i < tmpClasses.size(); i++) {
			classes[i] = tmpClasses.get(i);
		}
		values = new double[tmpValues.size()][tmpValues.get(0).length];
		for (int i = 0; i < tmpValues.size(); i++) {
			values[i] = tmpValues.get(i);
		}
		
		// return FeatureData Object
		return new FeatureData(features, samples, classes, values);

	}

}
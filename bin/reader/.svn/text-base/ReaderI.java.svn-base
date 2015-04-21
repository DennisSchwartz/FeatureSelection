package reader;

import java.io.IOException;
import java.io.Reader;
import java.util.zip.DataFormatException;

import dataStructures.FeatureData;

/**
 * converts Data to internal FeatureData object
 * @author Johannes Schoellhorn
 *
 */
public interface ReaderI {
	
	/**
	 * reads from an Reader Object and creates a FeatureData Object
	 * from the given Data
	 * @param r Reader Object containing data to be read
	 * @return FeatureData Object
	 * @throws IOException throws caught IOExceptions and
	 *         Exceptions if Reader Object is empty
	 * @throws DataFormatException throws an DataFormatException
	 * 		   if it detects inconsistencies in the data representation
	 *         of the file
	 */
	public FeatureData read(Reader r) throws IOException, DataFormatException;
}

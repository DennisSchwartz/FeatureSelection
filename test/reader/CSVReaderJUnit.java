/**
 * 
 */
package reader;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.zip.DataFormatException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataStructures.FeatureData;

/**
 * @author Johannes Schoellhorn
 *
 */
public class CSVReaderJUnit {
	
	CSVReader csvR;
	Reader r;
	
	static FeatureData result = new FeatureData(new  String[] {"f1", "f2", "f3"},
											    new  String[] {"hans", "1", "2"},
											    new boolean[] {false, true, false},
											    new double[][]{{1.0, 2,   3},
															   {1,   1,   1},
															   {3,   5.0, 2.4}});
	static FeatureData result2 = new FeatureData(new  String[] {"f1", "f2", "f3"},
		    									 new  String[] {"hans", "1", "2"},
		    									 new boolean[] {true, true, true},
		    									 new double[][]{{1.0, 2,   3},
						   										{1,   1,   1},	
						   										{3,   5.0, 2.4}});
	static String separator = ",";
	static String fileLocation = "resources/readerTest.csv";
	static double delta = 0.000001;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		csvR = new CSVReader(separator);
		r = new FileReader(fileLocation);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link reader.CSVReader#read(java.io.Reader)}.
	 * @throws IOException 
	 * @throws DataFormatException 
	 * 
	 */
	@Test
	public void testRead() throws IOException, DataFormatException {
		FeatureData fd = csvR.read(r);
		assertArrayEquals(result.getFeatures(), fd.getFeatures());
		assertArrayEquals(result.getSamples(), fd.getSamples());
		for (int i = 0; i < fd.getClasses().length; i++) {
			assertEquals(result.getClasses()[i], fd.getClasses()[i]);
		}
		for (int i = 0; i < fd.getValues().length; i++) {
			for (int j = 0; j < fd.getValues()[i].length; j++) {
				assertEquals(result.getValues()[i][j], fd.getValues()[i][j], delta);
			}
		}
	}

}

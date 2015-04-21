/**
 * 
 */
package dataStructures;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Johannes Schoellhorn
 *
 */
public class FeatureDataJUnit {
	
	FeatureData featureData;
	FeatureData stringTest;
	
	static String[] features = {"Feature1", "Feature2", "Feature3"};
	static String[] samples = {"1", "2", "3", "Hans Wurst", "5"};
	static boolean[] classes = {true, true, false, true, false};
	static double[][] values = {{ 1.0,  2.0,   3.0},
								{-1.0, -2.0,  -3.0},
								{-0.5, -2.3,   4.0},
								{-0.0,  0.0,   0},
								{ 0.45, 0.342, 0.09}};
	
	static String[] fString = {"F1", "F2"};
	static String[] sString = {"1", "2"};
	static boolean[] cString = {true, false};
	static double[][] vString = {{ 1.0,  2.0},
								 { 2.0, -2.0}};
	
	static double delta = 0.000000001;

	static String rString = 
		"| s | class | F1 | F2 |\n" +
		"| 1 |  true | 1. | 2. |\n" +
		"| 2 | false | 2. | -2 |\n";
	static String rString2 = 
		"| sampl | class |  F1 |  F2 |\n" +
		"|     1 |  true | 1.0 | 2.0 |\n" +
		"|     2 | false | 2.0 | -2. |\n";

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
		featureData = new FeatureData(features, samples, classes, values);
		stringTest = new FeatureData(fString, sString, cString, vString);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link dataStructures.FeatureData#getFeatureCol(int)}.
	 */
	@Test
	public void testGetFeatureColInt() {
		double[] valCol;
		for (int i = 0; i < this.featureData.getFeatures().length; i++) {
			valCol = this.featureData.getFeatureCol(i);
			for (int j = 0; j < valCol.length; j++) {
				assertEquals(values[j][i], valCol[j], delta);
			}
		}
	}

	/**
	 * Test method for {@link dataStructures.FeatureData#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(rString, stringTest.toString());
	}

	/**
	 * Test method for {@link dataStructures.FeatureData#toString(int, int)}.
	 */
	@Test
	public void testToStringIntInt() {
		assertEquals(rString2, stringTest.toString(5, 3));
		assertEquals(rString, stringTest.toString(-1, 20));
	}

}

package wrapper.performance;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataStructures.ConfusionMatrix;

public class AccuracyJUnit {
	
	
	static ConfusionMatrix perfectHit = new ConfusionMatrix(10, 0, 10, 0);
	static ConfusionMatrix worstHit = new ConfusionMatrix(0, 10, 0, 10);
	static ConfusionMatrix confusion = new ConfusionMatrix(10,5,3,2);
	static ConfusionMatrix nullDenomitor = new ConfusionMatrix(0,0,0,0);
	static ConfusionMatrix empty = new ConfusionMatrix(0,0,0,0);
	static double delta = 0.000000001;
	static Accuracy acc = new Accuracy();
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCalculatePerformance() {
		assertEquals(1, acc.calculatePerformance(perfectHit),delta);
		assertEquals(0, acc.calculatePerformance(worstHit),delta);
		assertEquals(0, acc.calculatePerformance(nullDenomitor),delta);
		assertEquals(13/20.0, acc.calculatePerformance(confusion),delta);
		
		
	}

}

package wrapper.performance;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataStructures.ConfusionMatrix;

public class MCCPerformanceJUnit {

	static ConfusionMatrix confusion = new ConfusionMatrix(10,5,3,2);
	
	static ConfusionMatrix nullDenomitor = new ConfusionMatrix(0,0,1,2);
	
	static ConfusionMatrix perfectHit = new ConfusionMatrix(10, 0, 10, 0);
	
	static ConfusionMatrix worstHit = new ConfusionMatrix(0, 10, 0, 10);
	
	static MCCPerformance perform = new MCCPerformance();
	
	static double delta = 0.000000001;
	
	
	
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
		//Testing of "normal" dataset
		assertEquals(0.23570226,perform.calculatePerformance(confusion), delta);
		//Testing if MCCPerformance does right, if any sum in the denomitor is 0
		assertEquals(0.0,perform.calculatePerformance(nullDenomitor),delta);
		assertEquals(-1.0,perform.calculatePerformance(worstHit),delta);
		assertEquals(1.0,perform.calculatePerformance(perfectHit),delta);
	}

}

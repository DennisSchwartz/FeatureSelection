package wrapper.crossvalidation.classifier;

import static org.junit.Assert.assertEquals;
import libsvm.svm_node;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataStructures.FeatureData;

public class CSVCClassifierJUnit {
	
	static CSVCClassifier toTest;
	static FeatureData test;
	static String[] features = {"Feature1", "Feature2"};
	static String[] samples = {"1", "2"};
	static boolean[] classes = {true, true};
	static double[][] values = {{ 1.0,  2.0},
								{-1.0, -2.0,}};
	
	static double delta = 000000.1;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new FeatureData(features,samples,classes,values);
		toTest = new CSVCClassifier(new CSVCClassifierConfig());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRun() {
		
	}

	@Test
	public void testCreateSVMNodes() {
		svm_node[][] svm = new svm_node[2][2];
		
		svm_node insert= new svm_node();
		svm_node insert2= new svm_node();
		svm_node insert3= new svm_node();
		svm_node insert4= new svm_node();
		
		insert.index = 0;
		insert.value = 1.0;
		svm[0][0] = insert;
		
		
		insert2.index = 1;
		insert2.value = 2.0;
		svm[0][1] = insert2;
		
		insert3.index = 0;
		insert3.value = -1.0;
		svm[1][0] = insert3;
		
		insert4.index = 1;
		insert4.value = -2.0;
		svm[1][1] = insert4;
		
		assertEquals(svm[0][0].index,toTest.createSVMNodes(test)[0][0].index);
		assertEquals(svm[0][0].value,toTest.createSVMNodes(test)[0][0].value,delta);
		
		
		assertEquals(svm[1][1].index,toTest.createSVMNodes(test)[1][1].index);
		assertEquals(svm[1][1].value,toTest.createSVMNodes(test)[1][1].value,delta);
		
	}

}

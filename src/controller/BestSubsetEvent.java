package controller;

import java.util.EventObject;

import dataStructures.SubsetPerformanceData;

public class BestSubsetEvent extends EventObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SubsetPerformanceData bestSubset;

	public BestSubsetEvent(Object source, SubsetPerformanceData bestSubset) {
		super(source);
		this.bestSubset = bestSubset;
	}

}

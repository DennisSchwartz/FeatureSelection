package controller;

import java.io.File;
import java.util.EventObject;

import dataStructures.FeatureData;

/**
 * FeatureData event used to notify listeners about a change in FeatureData
 * 
 * @author Johannes Schoellhorn
 *
 */
public class FeatureDataEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	FeatureData featureData;
	File file;
	
	public FeatureDataEvent(Object source, FeatureData featureData, File file) {
		super(source);
		this.featureData = featureData;
		this.file = file;
	}
	
	public FeatureData getFeatureData() {
		return this.featureData;
	}
	
	public File getFile() {
		return this.file;
	}
}
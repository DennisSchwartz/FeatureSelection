package controller;

import java.util.EventListener;

/**
 * FeatureData listener defining methods which need to be implemented
 * by a class listening for changes in FeatureData
 * 
 * @author Johannes Schoellhorn
 *
 */
public interface FeatureDataListener extends EventListener {
	
	/**
	 * what to do if the FeatureData was updated
	 * @param e
	 */
	public void featureDataUpdated(FeatureDataEvent e);

}

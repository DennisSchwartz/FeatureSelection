/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import javax.swing.event.EventListenerList;

import wrapper.ResultEvent;
import wrapper.ResultListener;
import wrapper.WrapperThread;
import wrapper.WrapperThreadListener;
import dataStructures.FeatureData;
import dataStructures.ResultsData;

/**
 * The main controller listens for changes to its attributes by the clients and
 * provides notification for clients if its attributes have changed.
 * 
 * provides notifications for changes in FeatureData and ActionPerformed (tells
 * a clients control unit what to do)
 * 
 * @author Johannes Schoellhorn, Julian Schwab
 * 
 */
public class MainController implements FeatureDataListener, WrapperListener,
		ActionListener, ResultListener, WrapperThreadListener {

	EventListenerList listeners = new EventListenerList();

	FeatureData featureData;
	File file;
	String absoluteFilename;
	ResultsData resultsData;
	
	WrapperThread wrapperThread;
	CountDownLatch startSignal;
	
	PrintStream output;

	/**
	 * Constructor sets Output Stream For GUI choose e.g TextAreaStream, for
	 * Console system.out
	 * 
	 * @param output
	 */
	public MainController(PrintStream output) {
		this.output = output;
	}

	/**
	 * @return the output
	 */
	public PrintStream getOutput() {
		return output;
	}

	/**
	 * Getter for results this.resultsData
	 * 
	 * @return the computed results as ResultsData object
	 */
	public ResultsData getResultsData() {
		return this.resultsData;
	}

	/**
	 * 
	 * sets new FeatureData and the corresponding file name if FeatureData to
	 * operate on in client has changed. fires notification that FeatureData has
	 * changed.
	 * 
	 */
	public void featureDataUpdated(FeatureDataEvent e) {
		this.featureData = e.getFeatureData();
		this.file = e.getFile();
		this.notifyFeatureDataUpdated(new FeatureDataEvent(this,
				this.featureData, this.file));

	}

	/**
	 * Sets and starts the wrapper Process
	 */
	public void wrapperUpdated(WrapperEvent e) {

		/* Initialize new WrapperThread */
		if (wrapperThread == null || !wrapperThread.isAlive()) { // No wrapper
																	// set(first
																	// use), or
																	// old
																	// process
																	// is
																	// finished

			e.getWrapper().addResultEventListener(this); // Set Listener of
															// Wrapper, to get
															// results
			/* Creates new ResultsData */
			this.resultsData = e.getWrapper().createResultsData(this.file.getName(), this.file.getAbsolutePath());
			
			/* start new thread */
			wrapperThread = new WrapperThread(e.getWrapper(), e.getStartSignal());
			// store the startSignal counter
			this.startSignal = e.getStartSignal();
			// get all listeners, listening for the thread to end and add them to the wrapper
			for (WrapperThreadListener l : listeners.getListeners(WrapperThreadListener.class)) {
				wrapperThread.addWrapperThreadListener(l);
			}
			wrapperThread.addResultListener(this);
			this.resultsData.setStart(new Date());
			wrapperThread.start();
			// remove all listeners, listening for the thread to end from the list
			for (WrapperThreadListener l : listeners.getListeners(WrapperThreadListener.class)) {
				this.listeners.remove(WrapperThreadListener.class, l);
			}

			output.println("Processing..."); // Print that thread is working

//			this.notifyActions(new ActionEvent(this, 0, "showDetail")); // fire,
//																		// to
//																		// enable
//																		// showDetail
//																		// button

		}

		// If another thread still is working, don't start a new one, and print
		// in output
		else {
			output.println("Another Thread is running");
		}
	}

	/**
	 * counts down the start signal
	 */
	public void startCountDown() {
		this.startSignal.countDown();
	}
	
	/**
	 * Reacts on ActionEvents. if a ActionEvent with command "stop" was fired,
	 * the working thread is forced to be stopped if a ActionEvent with command
	 * "detail" was fired, the detailView window is initialized
	 */
	public void actionPerformed(ActionEvent e) {

		// Reacts on stop command and forces process to stop
		if (e.getActionCommand() == "stop") {

			if (this.wrapperThread != null) {
				this.resultsData.setStoppedByUser(true);
				this.wrapperThread.forceStop();

			}
		}
	}

	/**
	 * Adds new result received by ResultEvent to resultsData
	 */
	public void resultsUpdated(ResultEvent e) {
		this.resultsData.setBestSubset(e.getBestSubset());
		this.resultsData.add(e.getResults()); // Add received Values to results
	}
	
	public void resultsUpdated(Date date) {
		this.resultsData.setStop(date);
	}

	/*
	 * 
	 * 
	 * add listener and notification methods here
	 */

	public void addFeatureDataListener(FeatureDataListener listener) {
		this.listeners.add(FeatureDataListener.class, listener);
	}

	public void removeFeatureDataListener(FeatureDataListener listener) {
		this.listeners.remove(FeatureDataListener.class, listener);
	}

	protected synchronized void notifyFeatureDataUpdated(FeatureDataEvent e) {
		for (FeatureDataListener l : listeners
				.getListeners(FeatureDataListener.class)) {
			l.featureDataUpdated(e);
		}
	}

	public void addActionListener(ActionListener listener) {
		this.listeners.add(ActionListener.class, listener);
	}

	public void removeActionListener(ActionListener listener) {
		this.listeners.remove(ActionListener.class, listener);
	}

	protected synchronized void notifyActions(ActionEvent e) {
		for (ActionListener l : listeners.getListeners(ActionListener.class)) {
			l.actionPerformed(e);
		}
	}
	
	public void addWrapperThreadListener(WrapperThreadListener l) {
		this.listeners.add(WrapperThreadListener.class, l);
	}
	
	public void removeWrapperThreadListener(WrapperThreadListener l) {
		this.listeners.remove(WrapperThreadListener.class, l);
	}

	public void threadFinished() {
		this.resultsData.setStop(new Date());
	}

}

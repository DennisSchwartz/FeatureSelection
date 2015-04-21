package controller.components;

import gui.components.ControlPane;
import gui.components.Menu;
import gui.components.ResultsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;

import javax.swing.event.EventListenerList;

import wrapper.EvA2Wrapper;
import wrapper.EvA2WrapperConfig;
import wrapper.ForwardSelectionWrapper;
import wrapper.ForwardSelectionWrapperConfig;
import wrapper.WrapperBase;
import wrapper.WrapperConfigBase;
import wrapper.WrapperThreadListener;
import wrapper.crossvalidation.KFoldCrossValidation;
import wrapper.crossvalidation.KFoldCrossValidationConfig;
import wrapper.crossvalidation.classifier.CSVCClassifier;
import wrapper.crossvalidation.classifier.CSVCClassifierConfig;
import wrapper.performance.Accuracy;
import wrapper.performance.MCCPerformance;
import controller.FeatureDataEvent;
import controller.FeatureDataListener;
import controller.MainController;
import controller.WrapperEvent;
import controller.WrapperListener;
import dataStructures.FeatureData;
import eva2.server.go.operators.terminators.EvaluationTerminator;
import eva2.server.go.operators.terminators.FitnessConvergenceTerminator;
import eva2.server.go.operators.terminators.GenerationTerminator;

/**
 * Controller for ControlPane view. The ControlPane class holds a start and a stop button. 
 * The Controller should be able to react on the Events fired by these two buttons
 * @author julianschwab
 *
 */
public class ControlPaneController implements ActionListener, FeatureDataListener, WrapperThreadListener {
	
	ControlPane view;
	MainController main;
	EventListenerList listener = new EventListenerList();
	FeatureData data;

	/**
	 * Constructor that links ControlPane with Controller
	 * @param view
	 */
	public ControlPaneController(ControlPane view, MainController main){
		this.view = view;
		this.main = main;
		
		//ControlPaneController listens to MainController and WrapperThreadListener
		this.main.addFeatureDataListener(this);
		
		// add MainController to listen for changes
		this.addWrapperListener(main);
		this.addActionListener(main);
	}

	/**
	 * actionPerformed, checks whether the start or the stop button fired the event. 
	 * In regard of the source of the event a threat will be created or stopped
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(view.isStartButton(e.getSource())) {
			
			PrintStream output = main.getOutput();
			
			if (this.data == null) {
				output.println("No File selected! Please select a file and start!");
				((Menu) this.view.parent.getJMenuBar()).getMenuController().openCSVFile();
				return;
			}
			
			/*Init*/
			WrapperConfigBase wrapperConfig = new ForwardSelectionWrapperConfig(this.data);
			WrapperBase wrapper = new ForwardSelectionWrapper(new ForwardSelectionWrapperConfig(wrapperConfig));
			KFoldCrossValidationConfig crossValConfig = new KFoldCrossValidationConfig();
			
			/*Get selected Items and declare components in regard of the selection*/
			switch (view.parent.contentPane.getPerformanceBox()) {
			case MCC : 
				wrapperConfig.setPerformance(new MCCPerformance()); 
				break;
			
			case ACCURACY :
				wrapperConfig.setPerformance(new Accuracy());	
				break;
			}
			
			switch (view.parent.contentPane.getClassifierBox()) {
			case CSVC : 
				crossValConfig.setClassifier(new CSVCClassifier(new CSVCClassifierConfig()));
				break;
			
			}
			
			// Get selected fold from GUI and check if fold is valid
			int kFold = view.parent.contentPane.getKFold();
			if (kFold == Integer.MIN_VALUE) {
				output.println("Warning: Input for fold was not a valid number, fold was set to default!");
				this.view.parent.contentPane.setKFold(2);
				return;
			} else if (kFold > this.data.getSamples().length) {
				output.println("Warning: You can't choose a fold greater than the number of samples in your data, fold was set to maximum!");
				this.view.parent.contentPane.setKFold(this.data.getSamples().length);
				return;
			} else if (kFold < 2) {
				output.println("Warning: You have to choose a fold greater than 1, fold was set to 2!");
				this.view.parent.contentPane.setKFold(2);
				return;
			}
			crossValConfig.setFold(kFold);
			// set output to text area
			wrapperConfig.setCrossVal(new KFoldCrossValidation(crossValConfig));
			wrapperConfig.setOutput(output);
			
			switch(view.parent.contentPane.getWrapperBox()) {
			case FORWARDSELECTION :
				wrapper = new ForwardSelectionWrapper(new ForwardSelectionWrapperConfig(wrapperConfig));
				break;
			
			case EVA : 
				//Create config by reading parameters out of GUI (parent)
				EvA2WrapperConfig evaConfig = new EvA2WrapperConfig(wrapperConfig);
				evaConfig.setPopulation(view.parent.contentPane.getEva2populationSize()); //Add population
				evaConfig.setRandomSeed(view.parent.contentPane.getEva2randomSeed()); // Set seed
				//reads out EvA2ChooseTerminatorBox and changes the EvA2config accordingly
				switch(view.parent.contentPane.getEvA2ChooseTerminatorBox()) {
				
				case EVALUATIONTERMINATOR :
					
					evaConfig.setTerminator(new EvaluationTerminator(view.parent.contentPane.getEva2termination()));
					break;
					
				case GENERATIONTERMINATOR :
					
					evaConfig.setTerminator(new GenerationTerminator(view.parent.contentPane.getEva2termination()));
					break;
					
				case FITNESSCONVERGENCETERMINATOR :
					
					FitnessConvergenceTerminator terminator = new FitnessConvergenceTerminator(view.parent.contentPane.getEva2convergenceTreshold(), view.parent.contentPane.getEva2termination(), view.parent.contentPane.getEva2convergenceStagnationTypeBox(), view.parent.contentPane.getEva2convergenceChangeTypeBox(), view.parent.contentPane.getEva2convergenceDirectionTypeBox());
					evaConfig.setTerminator(terminator);
					break;
				}
				wrapper = new EvA2Wrapper(evaConfig);
				break;
			}
			
			// create ResultsView
			ResultsView resultsView = new ResultsView("Processing ...");
			/*
			 * register ResultsView and ContorlPaneController as WrapperThreadListener
			 * both have to be registered here, because they will be stored in the 
			 * MainControllers EventListenersList, added to the newly created thread
			 * and after registering them with the new thread they are removed from
			 * the list
			 * so everything listening on the thread should be registered here, else
			 * they will only be able to listen to it once.
			 */
			this.main.addWrapperThreadListener(resultsView);
			this.main.addWrapperThreadListener(this);
			
			//Fire notification to start thread
			this.notifyWrapperUpdated(new WrapperEvent(this, wrapper, new CountDownLatch(2)));
			
			// disable/enable start/stop button till WrapperThread finished
			this.view.getStartButton().setEnabled(false);
			this.view.getStopButton().setEnabled(true);
			
			this.main.startCountDown();
			
			// initialize ResultView and make visible
			resultsView.init(this.main);
			resultsView.setVisible(true);

		}
		
		/*StopButton : if hit, fire "stop" ActionEvent*/
		if(view.isStopButton(e.getSource())) {
			this.notifyActions(new ActionEvent(this,0,"stop"));	
			
		}
	}

	
	/**
	 * Receives FeatureData event that is fired
	 * Sets fired featureData to this.data
	 */
	@Override
	public void featureDataUpdated(FeatureDataEvent e) {
		this.data = e.getFeatureData();
	}


	/*
	 * 
	 * Add/remove and notification methods for listener
	 * 
	 */
	
	//Methods for WrapperEvent that sends wrapper and starts process in main controller, that main controller is able to listen
	
	public void addWrapperListener(WrapperListener listener) {
		this.listener.add(WrapperListener.class, listener);
	}
	
	public void removeWrapperListener(WrapperListener listener) {
		this.listener.remove(WrapperListener.class, listener);
	}

	protected synchronized void notifyWrapperUpdated(WrapperEvent e) {
		for(WrapperListener l : listener.getListeners(WrapperListener.class)){
			l.wrapperUpdated(e);
		}
	}

	//Methods for ActionEvent created by hitting the stop-Button, sends stop command to maincontroller
	public void addActionListener(ActionListener listener) {
		this.listener.add(ActionListener.class, listener);
	}
	
	public void removeActionListener(ActionListener listener) {
		this.listener.remove(ActionListener.class, listener);
	}
	
	
	protected synchronized void notifyActions(ActionEvent e) {
		for(ActionListener l : listener.getListeners(ActionListener.class)){
			l.actionPerformed(e);
		}
	}

	/* (non-Javadoc)
	 * @see wrapper.WrapperThreadListener#threadFinished()
	 */
	@Override
	public void threadFinished() {
		this.view.getStartButton().setEnabled(true);
		this.view.getStopButton().setEnabled(false);
		
	}


	
	
	
	

}

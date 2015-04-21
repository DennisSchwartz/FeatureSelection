package gui.components;

import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import wrapper.EvA2TerminatorTypes;
import wrapper.EvA2WrapperConfig;
import wrapper.WrapperTypes;
import wrapper.crossvalidation.KFoldCrossValidationConfig;
import wrapper.crossvalidation.classifier.ClassifierTypes;
import wrapper.performance.PerformanceTypes;
import controller.components.ContentPaneController;
import eva2.server.go.operators.terminators.PopulationMeasureTerminator;
import eva2.server.go.operators.terminators.PopulationMeasureTerminator.ChangeTypeEnum;
import eva2.server.go.operators.terminators.PopulationMeasureTerminator.DirectionTypeEnum;
import eva2.server.go.operators.terminators.PopulationMeasureTerminator.StagnationTypeEnum;

public class ContentPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4901623929171132939L;

	MainWindow parent;

	JLabel logoLabel;
	JComboBox wrapperBox;
	JComboBox performanceBox;
	JComboBox classifierBox;

	// This is for Eva2 setting only:
	
	JLabel eva2terminationLabel;
	JLabel eva2randomSeedLabel;
	JLabel eva2populationSizeLabel;
	JLabel eva2chooseTerminatorLabel;
	JLabel eva2convergenceChangeTypeLabel;
	JLabel eva2convergenceDirectionTypeLabel;
	JLabel eva2convergenceStagnationTypeLabel;
	JComboBox eva2chooseTerminatorBox;
	JComboBox eva2convergenceChangeTypeBox;
	JComboBox eva2convergenceDirectionTypeBox;
	JComboBox eva2convergenceStagnationTypeBox;
	JLabel eva2convergenceTresholdLabel;
	JTextField eva2convergenceTreshold;
	JTextField eva2termination;
	JTextField eva2randomSeed;
	JTextField eva2populationSize;

	JLabel kFoldLabel;
	JTextField kFold;
	ContentPaneController controller;

	public ContentPane(MainWindow parent) {

		this.parent = parent;

		GridBagLayout gbl = new GridBagLayout();

		this.setLayout(gbl);

		

		this.wrapperBox = new JComboBox(WrapperTypes.values());
		this.performanceBox = new JComboBox(PerformanceTypes.values());
		this.classifierBox = new JComboBox(ClassifierTypes.values());

		this.eva2chooseTerminatorLabel = new JLabel("Use terminator: ");
		this.eva2chooseTerminatorBox = new JComboBox(EvA2TerminatorTypes.values());
		this.eva2convergenceChangeTypeLabel = new JLabel("Change type: ");
		this.eva2convergenceChangeTypeBox = new JComboBox(PopulationMeasureTerminator.ChangeTypeEnum.values());
		this.eva2convergenceDirectionTypeLabel = new JLabel("Direction: ");
		this.eva2convergenceDirectionTypeBox = new JComboBox(PopulationMeasureTerminator.DirectionTypeEnum.values());
		this.eva2convergenceStagnationTypeLabel = new JLabel("Stagnation type: ");
		this.eva2convergenceStagnationTypeBox = new JComboBox(PopulationMeasureTerminator.StagnationTypeEnum.values());
		this.eva2convergenceTresholdLabel = new JLabel("Treshold: ");
		this.eva2convergenceTreshold = new JTextField(Double.toString(EvA2WrapperConfig.CONVERGENCE_TRESHOLD));
		this.eva2terminationLabel = new JLabel("Evaluation: ");
		this.eva2randomSeedLabel = new JLabel("Random seed: ");
		this.eva2populationSizeLabel = new JLabel("Population size: ");
		this.eva2termination = new JTextField("" + EvA2WrapperConfig.TERMINATOR_SIZE, 2);
		this.eva2randomSeed = new JTextField("" + EvA2WrapperConfig.RANDOM_SEED, 2);
		this.eva2populationSize = new JTextField("" + EvA2WrapperConfig.POPULATION_SIZE, 2);

		this.kFold = new JTextField("" + KFoldCrossValidationConfig.FOLD, 2);

		this.controller = new ContentPaneController(this);

		// Add Labels for parameters
		Components.add(this, gbl, new JLabel("Wrapper :"), 1, 0, 1, 1, 0, 0);
		Components.add(this, gbl, new JLabel("Classifier :"), 2, 0, 1, 1, 0, 0);
		Components
				.add(this, gbl, new JLabel("Performance :"), 3, 0, 1, 1, 0, 0);
		Components.add(this, gbl, new JLabel("Fold :"), 4, 0, 1, 1, 0, 0);

		// Add parameters
		Components.add(this, gbl, wrapperBox, 1, 1, 1, 1, 0, 0);
		Components.add(this, gbl, classifierBox, 2, 1, 1, 1, 0, 0);
		Components.add(this, gbl, performanceBox, 3, 1, 1, 1, 0, 0);

		Components.add(this, gbl, eva2chooseTerminatorLabel, 0, 2, 1, 1, 0, 0);
		Components.add(this, gbl, eva2chooseTerminatorBox, 1, 2, 1, 1, 0, 0);
		Components.add(this, gbl, eva2convergenceChangeTypeLabel, 0, 3, 1, 1, 0, 0);
		Components.add(this, gbl, eva2convergenceChangeTypeBox, 1, 3, 1, 1, 0, 0);
		Components.add(this, gbl, eva2convergenceDirectionTypeLabel, 0, 4, 1, 1, 0, 0);
		Components.add(this, gbl, eva2convergenceDirectionTypeBox, 1, 4, 1, 1, 0, 0);
		Components.add(this, gbl, eva2convergenceStagnationTypeLabel, 0, 5, 1, 1, 0, 0);
		Components.add(this, gbl, eva2convergenceStagnationTypeBox, 1, 5, 1, 1, 0, 0);
		Components.add(this, gbl, eva2convergenceTresholdLabel, 0, 6, 1, 1, 0, 0);
		Components.add(this, gbl, eva2convergenceTreshold, 1, 6, 1, 1, 0, 0);
		Components.add(this, gbl, eva2terminationLabel, 0, 7, 1, 1, 0, 0);
		Components.add(this, gbl, eva2termination, 1, 7, 1, 1, 0, 0);
		Components.add(this, gbl, eva2populationSizeLabel, 0, 8, 1, 1, 0, 0);
		Components.add(this, gbl, eva2populationSize, 1, 8, 1, 1, 0, 0);
		Components.add(this, gbl, eva2randomSeedLabel, 0, 9, 1, 1, 0, 0);
		Components.add(this, gbl, eva2randomSeed, 1, 9, 1, 1, 0, 0);
		Components.add(this, gbl, kFold, 4, 1, 1, 1, 0, 0);


		this.toggleEva2Visibility();
		this.toggleEva2TerminatorSettingsVisibility();

		this.wrapperBox.addItemListener(controller);
		this.performanceBox.addItemListener(controller);
		this.classifierBox.addItemListener(controller);
		this.eva2chooseTerminatorBox.addItemListener(controller);

	}

	/**
	 * @return the wrapperBox
	 */
	public WrapperTypes getWrapperBox() {
		return (WrapperTypes) wrapperBox.getSelectedItem();
	}

	/**
	 * 
	 * @return the selected terminator for EvA2
	 */
	public EvA2TerminatorTypes getEvA2ChooseTerminatorBox() {
		return (EvA2TerminatorTypes) eva2chooseTerminatorBox.getSelectedItem();
	}
	

	public ChangeTypeEnum getEva2convergenceChangeTypeBox() {
		return (ChangeTypeEnum) eva2convergenceChangeTypeBox.getSelectedItem();
	}

	public DirectionTypeEnum getEva2convergenceDirectionTypeBox() {
		return (DirectionTypeEnum) eva2convergenceDirectionTypeBox.getSelectedItem();
	}

	public StagnationTypeEnum getEva2convergenceStagnationTypeBox() {
		return (StagnationTypeEnum) eva2convergenceStagnationTypeBox.getSelectedItem();
	}

	/**
	 * @return the performanceBox
	 */
	public PerformanceTypes getPerformanceBox() {
		return (PerformanceTypes) performanceBox.getSelectedItem();
	}

	/**
	 * @return the classifierBox
	 */
	public ClassifierTypes getClassifierBox() {
		return (ClassifierTypes) classifierBox.getSelectedItem();
	}

	/**
	 * @return the eva2termination
	 */
	public int getEva2termination() {
		return Integer.parseInt(eva2termination.getText());
	}

	public void setEva2terminationLabel(String label) {
		this.eva2terminationLabel.setText(label);
		this.validate();
	}

	/**
	 * @return the eva2randomSeed
	 */
	public int getEva2randomSeed() {
		return Integer.parseInt(eva2randomSeed.getText());
	}

	/**
	 * @return the kFold Value
	 */
	public int getKFold() {
		try {
			return Integer.parseInt(kFold.getText());
		} catch (NumberFormatException e) {
			return Integer.MIN_VALUE;
		}
	}

	/**
	 * @return the eva2populationSize
	 */
	public int getEva2populationSize() {
		return Integer.parseInt(eva2populationSize.getText());
	}

	public Double getEva2convergenceTreshold() {
		return Double.parseDouble(eva2convergenceTreshold.getText());
	}

	public boolean isWrapperBox(Object component) {
		return this.wrapperBox.equals(component);
	}

	public boolean isPerformanceBox(Object component) {
		return this.performanceBox.equals(component);
	}
	
	public boolean isEvA2ChooseTerminatorBox(Object component) {
		return this.eva2chooseTerminatorBox.equals(component);
	}
	
	public boolean isEvA2ConvergenceOptionsVisible() {
		return this.eva2convergenceTresholdLabel.isVisible();
	}

	public void toggleEva2Visibility() {
		this.eva2chooseTerminatorBox.setVisible(!this.eva2chooseTerminatorBox
				.isVisible());
		this.eva2chooseTerminatorLabel
				.setVisible(!this.eva2chooseTerminatorLabel.isVisible());
		this.eva2termination.setVisible(!this.eva2termination.isVisible());
		this.eva2randomSeed.setVisible(!this.eva2randomSeed.isVisible());
		this.eva2populationSize
				.setVisible(!this.eva2populationSize.isVisible());
		this.eva2populationSizeLabel.setVisible(!this.eva2populationSizeLabel
				.isVisible());
		this.eva2randomSeedLabel.setVisible(!this.eva2randomSeedLabel
				.isVisible());
		this.eva2terminationLabel.setVisible(!this.eva2terminationLabel
				.isVisible());
		this.validate();
	}

	public void setKFold(int fold) {
		this.kFold.setText("" + fold);
	}

	public void toggleEva2TerminatorSettingsVisibility() {
		this.eva2convergenceChangeTypeLabel.setVisible(!this.eva2convergenceChangeTypeLabel.isVisible());
		this.eva2convergenceChangeTypeBox.setVisible(!this.eva2convergenceChangeTypeBox.isVisible());
		this.eva2convergenceDirectionTypeLabel.setVisible(!this.eva2convergenceDirectionTypeLabel.isVisible());
		this.eva2convergenceDirectionTypeBox.setVisible(!this.eva2convergenceDirectionTypeBox.isVisible());
		this.eva2convergenceStagnationTypeLabel.setVisible(!this.eva2convergenceStagnationTypeLabel.isVisible());
		this.eva2convergenceStagnationTypeBox.setVisible(!this.eva2convergenceStagnationTypeBox.isVisible());
		this.eva2convergenceTresholdLabel.setVisible(!this.eva2convergenceTresholdLabel.isVisible());
		this.eva2convergenceTreshold.setVisible(!this.eva2convergenceTreshold.isVisible());
		this.validate();
		
	}

}

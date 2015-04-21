package controller.components;

import gui.components.ContentPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import wrapper.EvA2TerminatorTypes;
import wrapper.WrapperTypes;

public class ContentPaneController implements ActionListener, ItemListener {

	ContentPane view;

	public ContentPaneController(ContentPane view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (this.view.isWrapperBox(e.getSource())) {
				switch ((WrapperTypes) e.getItem()) {

				case FORWARDSELECTION:
					this.view.toggleEva2Visibility();
					if (this.view.isEvA2ConvergenceOptionsVisible()) {
						this.view.toggleEva2TerminatorSettingsVisibility();
					}
					break;

				case EVA:
					this.view.toggleEva2Visibility();
					break;

				}

			} else if (this.view.isEvA2ChooseTerminatorBox(e.getSource())) {
				switch ((EvA2TerminatorTypes) e.getItem()) {

				case EVALUATIONTERMINATOR:
					this.view.setEva2terminationLabel(
							"Number of Evaluations: ");
					if (this.view.isEvA2ConvergenceOptionsVisible()) {
						this.view.toggleEva2TerminatorSettingsVisibility();
					}
					break;

				case GENERATIONTERMINATOR:
					this.view.setEva2terminationLabel("Number of Generations: ");
					if (this.view.isEvA2ConvergenceOptionsVisible()) {
						this.view.toggleEva2TerminatorSettingsVisibility();
					}
					break;

				case FITNESSCONVERGENCETERMINATOR:
					this.view.setEva2terminationLabel("Stagnation period: ");
					this.view.toggleEva2TerminatorSettingsVisibility();
					break;
				}
			}

		}
	}

}

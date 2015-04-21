package gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.table.DefaultTableModel;

import controller.MainController;
import dataStructures.EvA2ResultsData;
import eva2.server.go.individuals.AbstractEAIndividual;
import eva2.server.go.individuals.AbstractEAIndividualComparator;
import eva2.server.go.populations.Population;

public class PerformanceGraphEvA extends PerformanceGraphBase {

	
	/**
	 * Generated SerialID
	 */
	private static final long serialVersionUID = -8744047421744319250L;
	
	volatile boolean individuals;
	volatile boolean stdDev;
	volatile boolean mean;
	volatile boolean legend;

	/**
	 * Constructor
	 * @param main The main controller
	 * @param view The graphs parent results view
	 */
	public PerformanceGraphEvA(MainController main, ResultsView parent) {
		super(main, parent);
		
		this.individuals = true;
		this.stdDev = true;
		this.mean = true;
		this.individuals = true;
		
		this.menu = new EvAGraphMenu(this);
	}
		
	/**
	 * @return the individuals
	 */
	public boolean isIndividuals() {
		return individuals;
	}

	/**
	 * @return the stdDev
	 */
	public boolean isStdDev() {
		return stdDev;
	}

	/**
	 * @return the mean
	 */
	public boolean isMean() {
		return mean;
	}

	/**
	 * @param individuals the individuals to set
	 */
	public void setIndividuals(boolean individuals) {
		this.individuals = individuals;
		repaint();
	}

	/**
	 * @param stdDev the stdDev to set
	 */
	public void setStdDev(boolean stdDev) {
		this.stdDev = stdDev;
		repaint();
	}

	/**
	 * @param mean the mean to set
	 */
	public void setMean(boolean mean) {
		this.mean = mean;
		repaint();
	}
	
	/**
	 * returns the worst performance of the collected populations as
	 * the minimum to set on the y axis
	 */
	public void setMin() {
		// get the data
		EvA2ResultsData result;
		if (this.parent.getResultsData() != null) {
			result = (EvA2ResultsData) this.parent.getResultsData();
		} else {
			result = (EvA2ResultsData) this.main.getResultsData();
		}
		
		double min = 0;
		for (Population pop : result.getPopulations()) {
			min = Math.min(min, (-1) * pop.getWorstFitness()[0]);
		}
		if (min < 0) {
			this.min = Math.floor(min*10)/10;
		}
	}
	
	/**
	 * overwrites super.paintComponent. Draws x-axis,y-axis and trend of performance
	 */
	protected void paintComponent(Graphics g) { 
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        DefaultTableModel model;
        if (parent.getResultsData() == null) {
        	model = main.getResultsData().getTable();
        } else {
        	model = parent.getResultsData().getTable();
        }
        
        int bestCol = model.findColumn("Best");
        int stdDevCol = model.findColumn("StdDev");
        int meanCol = model.findColumn("Mean");
        
		// draw label for the x-axis
        g2.setColor(Color.BLACK);
        g2.draw(new Line2D.Double(this.getXCoord(0, model.getRowCount()),
        		this.getYCoord(0)-2,
        		this.getXCoord(0, model.getRowCount()),
        		this.getYCoord(0)+2));
        this.drawCenteredLabel(g2, this.getXCoord(0, model.getRowCount()),
        		getYCoord(0)+20, "0");
        
        g2.draw(new Line2D.Double(this.getXCoord(model.getRowCount()/2, model.getRowCount()),
        		this.getYCoord(0)-2,
        		this.getXCoord(model.getRowCount()/2, model.getRowCount()),
        		this.getYCoord(0)+2));
        this.drawCenteredLabel(g2, this.getXCoord(model.getRowCount()/2, model.getRowCount()),
        		this.getYCoord(0)+20, (model.getRowCount()/2) + "");
        
        g2.draw(new Line2D.Double(this.getXCoord(model.getRowCount(), model.getRowCount()),
        		this.getYCoord(0)-2,
        		this.getXCoord(model.getRowCount(), model.getRowCount()),
        		this.getYCoord(0)+2));
        this.drawCenteredLabel(g2, this.getXCoord(model.getRowCount(), model.getRowCount()),
        		this.getYCoord(0)+20, model.getRowCount() + "");
        this.drawCenteredLabel(g2, (getWidth() - (leftOffset + rightOffset))/2 + leftOffset,
        		getHeight()-15, "Generations");
        
        int point = 1;
        int numberOfPoints = model.getRowCount();
        // Mark data points.
        for(int i = model.getRowCount() - 1; i >= 0 ; i--) {
        	
        	double curValue = (Double) model.getValueAt(i, bestCol);
        	double curStdDev = (Double) model.getValueAt(i, stdDevCol);
        	double curMean = (Double) model.getValueAt(i, meanCol);
        	double prevValue;
        	double prevStdDev;
        	double prevMean;
    		
        	if(i == model.getRowCount() - 1) {
        		prevValue = 0;
        		prevStdDev = 0;
        		prevMean = 0;
        	} else {
        		prevValue = (Double) model.getValueAt(i+1, bestCol);
        		prevStdDev = (Double) model.getValueAt(i+1, stdDevCol);
        		prevMean = (Double) model.getValueAt(i+1, meanCol);
        	}
        	
        	// calculate x coordinate of points
        	int xValue = this.getXCoord(point, numberOfPoints);
        	
        	// draw best values in population
        	g2.setColor(Color.RED);
        	g2.draw(new Line2D.Double(xValue,
        			this.getYCoord(curValue),
        			this.getXCoord(point-1, numberOfPoints),
        			this.getYCoord(prevValue)));
        	
        	// draw stdDev around the mean, if stdDev is set
        	if (this.stdDev) {
        		g2.setColor(Color.GREEN);
        		// draw line for stdDeviation above value
            	g2.draw(new Line2D.Double(xValue,
            			this.getYCoord(curMean + curStdDev),
            			this.getXCoord(point-1, numberOfPoints),
            			this.getYCoord(prevMean + prevStdDev)));
            	// draw line for stdDeviation beneath value
            	g2.draw(new Line2D.Double(xValue,
            			this.getYCoord(curMean - curStdDev),
            			this.getXCoord(point-1, numberOfPoints),
            			this.getYCoord(prevMean - prevStdDev)));
        	}
        	
        	// draw mean if mean is set
        	if (this.mean) {
        		g2.setColor(Color.MAGENTA);
        		g2.draw(new Line2D.Double(xValue,
        				this.getYCoord(curMean),
            			this.getXCoord(point-1, numberOfPoints),
            			this.getYCoord(prevMean)));
        	}
        	
        	// draw all individuals if individuals is set
        	if (this.individuals) {
        		g2.setColor(Color.DARK_GRAY);
        		Population pop = (Population) ((EvA2ResultsData) main.getResultsData()).getPopulation(point - 1);
        		
        		List<AbstractEAIndividual> individuals = pop.getSorted(new AbstractEAIndividualComparator());
        		
        		for (AbstractEAIndividual individual : individuals) {
        			g2.draw(new Line2D.Double(xValue,
                			this.getYCoord((-1) * individual.getFitness(0)),
                			xValue,
                			this.getYCoord((-1) * individual.getFitness(0))));
        		}
        	}
        	
        	point++;
        }
        
        if(legend){
        	
        }
    }
}

/**
 * the menu to controll the EvA performance graph
 * @author Johannes Schoellhorn
 *
 */
class EvAGraphMenu extends JMenu implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452760254388245278L;

	PerformanceGraphEvA parent;

	JMenuItem showMean = new JMenuItem("Hide mean");
	JMenuItem showStdDev = new JMenuItem("Hide stdDev");
	JMenuItem showIndividual = new JMenuItem("Hide all Individuals");
	
	public EvAGraphMenu(PerformanceGraphEvA parent) {
		this.parent = parent;
		this.init("Graph", null);
		this.initMenu();
	}
	
	public void initMenu() {
			this.add(showIndividual);
			showIndividual.addActionListener(this);
			this.add(showMean);
			showMean.addActionListener(this);
			this.add(showStdDev);
			showStdDev.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (showIndividual.equals(e.getSource())) {
			parent.setIndividuals(!parent.isIndividuals());
			if (parent.isIndividuals()) {
				showIndividual.setText("Hide all individuals");
			} else {
				showIndividual.setText("Show all individuals");
			}
		} else if (showMean.equals(e.getSource())) {
			parent.setMean(!parent.isMean());
			if (parent.isMean()) {
				showMean.setText("Hide mean");
			} else {
				showMean.setText("Show mean");
			}
		} else if (showStdDev.equals(e.getSource())) {
			parent.setStdDev(!parent.isStdDev());
			if (parent.isStdDev()) {
				showStdDev.setText("Hide stdDev");
			} else {
				showStdDev.setText("Show stdDev");
			}
		}
		
	}
	
}
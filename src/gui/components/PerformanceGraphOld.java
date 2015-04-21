package gui.components;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import controller.MainController;
import dataStructures.EvA2ResultsData;
import eva2.server.go.individuals.AbstractEAIndividual;
import eva2.server.go.individuals.AbstractEAIndividualComparator;
import eva2.server.go.populations.Population;

/**
 * PerformanceGraph is a Panel to plot the progress of the performance value.
 * @author julianschwab
 *
 */
public class PerformanceGraphOld extends JPanel implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -295243221049685977L;
	
	JButton save;
	MainController main;
	ResultsView view;
	int offset = 20;
	volatile boolean individuals;
	volatile boolean stdDev;
	volatile boolean mean;
	volatile boolean legend;
	
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
	 * Constructor
	 * @param main The main controller
	 * @param view The graphs parent results view
	 */
	public PerformanceGraphOld(MainController main, ResultsView view) {
		//set fields
		this.main = main;
		this.view = view;
		this.main.getResultsData().getTable().addTableModelListener(this);
		this.individuals = true;
		this.stdDev = true;
		this.mean = true;
		this.individuals = true;
		
		this.setForeground(Color.BLUE);
		this.setBackground(Color.WHITE);

	}
	

	
	/**
	 * overwrites super.paintComponent. Draws x-axis,y-axis and trend of performance
	 */
	//TODO implement other ways of plotting, e.g. plot each individual as point
	//TODO create private method to do scaling of x,y-values
	protected void paintComponent(Graphics g) {
      //TODO super 
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        //get bounds of panel
        int w = getWidth();
        int h = getHeight();
        
        // Draw y-axis.
        g2.draw(new Line2D.Double(offset, h - offset, offset, offset));
        // Draw x-axis.
        // overwritten by scaling line
        // g2.draw(new Line2D.Double(offset, h - offset, w - offset, h - offset));
        
        /* Draw grid, to get better view on performance value in plot*/
        
        for(int i = 0; i < 10; i++) {
        	//Draw line at i / 10
        	g2.setColor(Color.LIGHT_GRAY);
        	g2.draw(new Line2D.Double(offset, h - (i * ((h - 2*offset) / 10.0) + offset), w - offset , h - (i * ((h - 2*offset) / 10.0) + offset)));
        	
        	//Draw labeling of lines
        	g2.setColor(Color.BLACK);
        	g2.drawString(((Double)(i / 10.0)).toString(), (int)(offset / 3), (int)(h - (i * ((h - 2*offset) / 10.0) + offset)));
        }
        
        
        //TODO just for EvA here
        /*Draw labeling of x-axis (last value in the end, and one value in the middle*/
        
       

        //Factor that scales values at the correct position of x-axis
        //whole x-axis is divided in yValues.size + 1 parts
        DefaultTableModel model;
        if(view.getResultsData() == null){
        	model = main.getResultsData().getTable();
        }
        else{
        	model = view.getResultsData().getTable();
        }
        
        int bestCol = model.findColumn("Best");
        int stdDevCol = model.findColumn("StdDev");
        int meanCol = model.findColumn("Mean");
        double xScaling = ( (w - (2 * offset)) / (model.getRowCount() + 1.0)); //one step more
        
        
        int colCount = 1;
        // Mark data points.
        for(int i = model.getRowCount() - 1; i > 0 ; i--) {
        	g2.setColor(Color.RED);
        	
        	//Print value of Point
    		//g2.drawString(yValues.get(i).toString(), (float)(i * xScaling),  (float)(h - (yValues.get(i) * (h + offset)) + 15));
    		
        	if(i == model.getRowCount() - 1){ //Draw first line from origin to first point read 
        		g2.draw(new Line2D.Double(xScaling + offset, h - ((Double)model.getValueAt(i, bestCol) * (h - offset)), offset ,  (h - offset) ));
        		if(stdDev) {
        			g2.setColor(Color.GREEN);
        			g2.draw(new Line2D.Double(xScaling + offset, h -( ((Double)model.getValueAt(i, meanCol) + ((Double)model.getValueAt(i, stdDevCol) )) * (h - offset)), offset ,  (h - offset) ));
        			g2.draw(new Line2D.Double(xScaling + offset, h -( ((Double)model.getValueAt(i, meanCol) - ((Double)model.getValueAt(i, stdDevCol) )) * (h - offset)), offset ,  (h - offset) ));
        		}//inner if
        		if(mean){
        			g2.setColor(Color.MAGENTA);
        			g2.draw(new Line2D.Double(xScaling + offset, h - ((Double)model.getValueAt(i, meanCol) * (h - offset)), offset ,  (h - offset) ));
        		}
        	}//if
        	else{ //Draw other lines, from last point to latest point
        		g2.draw(new Line2D.Double(colCount * xScaling + offset, h - ((Double)model.getValueAt(i, bestCol) * (h - offset)), (colCount - 1) * xScaling + offset ,  h - ((Double)model.getValueAt(i + 1, bestCol) * (h - offset) )));
        		if(stdDev) {
        			g2.setColor(Color.GREEN);
        			g2.draw(new Line2D.Double(colCount * xScaling + offset, h - ( ((Double)model.getValueAt(i, meanCol) + ((Double)model.getValueAt(i, stdDevCol))) * (h - offset)), (colCount - 1) * xScaling + offset ,  h - ( ((Double)model.getValueAt(i + 1, meanCol) + ((Double)model.getValueAt(i + 1, stdDevCol))) * (h - offset) )));
        			g2.draw(new Line2D.Double(colCount * xScaling + offset, h - ( ((Double)model.getValueAt(i, meanCol) - ((Double)model.getValueAt(i, stdDevCol))) * (h - offset)), (colCount - 1) * xScaling + offset ,  h - ( ((Double)model.getValueAt(i + 1, meanCol) - ((Double)model.getValueAt(i + 1, stdDevCol))) * (h - offset) )));
        		}
        		if(mean){
        			g2.setColor(Color.MAGENTA);
        			g2.draw(new Line2D.Double(colCount * xScaling + offset, h - ((Double)model.getValueAt(i, meanCol) * (h - offset)), (colCount - 1) * xScaling + offset ,  h - ((Double)model.getValueAt(i + 1, meanCol) * (h - offset) )));
        		}
        	}
        	
        	colCount++;
        }
        
        if(legend){
        	
        }
        
        if(individuals) {
        	
        	colCount = 1;
        	for(int i = model.getRowCount() - 1; i > 0; i--) {
        		g2.setColor(Color.DARK_GRAY);
        		Population pop = (Population) ((EvA2ResultsData)main.getResultsData()).getPopulation(colCount - 1);
        		
        		List<AbstractEAIndividual> individuals = pop.getSorted(new AbstractEAIndividualComparator());
        		g2.setColor(Color.DARK_GRAY);
        		for(AbstractEAIndividual individual : individuals) {
        			g2.draw(new Line2D.Double((colCount * xScaling + offset), (h - (-1)*individual.getFitness(0) * (h - offset)), (colCount * xScaling + offset),( h - (-1)*individual.getFitness(0) * (h - offset))));
        			
        			
        		}
        		
        		colCount++;
        	}
        }
        
    }
		
		
		
	
	
	public void tableChanged(TableModelEvent e) {
		
		if(e.getType() == TableModelEvent.INSERT){ //React only if first value is already inserted
			//Double y = (Double) mod.getValueAt(0, bestCol); //get new value out of table
			
//			if(y.doubleValue() > yValues.get(yValues.size() - 1).doubleValue()){
				
				paintComponents(getGraphics());
				repaint();
//			}
		}
	}
		
	
	/**
	 * Open filechooser with save dialog and choose file  
	 * @return filepath where file is to be saved
	 */
	//TODO double code, method already in resultsview, maybe make static to acces 
	protected String askNewFile() {

		String res = "";
		JFileChooser fileChooser = new JFileChooser();

		//sets the initial directory of the file chooser to the dir of the project
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		int returnVal = fileChooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			//TODO ask if file already exists
			res = fileChooser.getSelectedFile().getAbsolutePath();
		}

		return res;
	}


	


	
	


	
	
	
	

	
	
	
	
	
}
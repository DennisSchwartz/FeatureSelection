package gui.components.resultTabs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import controller.MainController;

/**
 * PerformanceGraph is a Panel to plot the progress of the performance value.
 * @author julianschwab
 *
 */
public class PerformanceGraph extends JPanel implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -295243221049685977L;
	
	JButton save;
	MainController main;
	List<Double> yValues = new ArrayList<Double>();
	int offset = 20;
	
	/**
	 * Constructor
	 * @param main The main controller
	 */
	public PerformanceGraph(MainController main) {
		//set fields
		this.main = main;
		
		this.main.getResultsData().getTable().addTableModelListener(this);
		
		yValues.add(new Double(0.0));
		this.setForeground(Color.BLUE);
		this.setBackground(Color.WHITE);

	}
	

	
	/**
	 * overwrites super.paintComponent. Draws x-axis,y-axis and trend of performance
	 */
	//TODO implement other ways of plotting, e.g. plot each individual as point
	//TODO create private method to do scaling of x,y-values
	protected void paintComponent(Graphics g) {
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
        g2.draw(new Line2D.Double(offset, h - offset, w - offset, h - offset));

        //Factor that scales values at the correct position of x-axis
        //whole x-axis is divided in yValues.size + 1 parts
        double xScaling = (w-offset) / (yValues.size() + 1.0); //one step more
        
        
        
        // Mark data points.
        for(int i = 1; i < yValues.size() ; i++) {
        	g2.setColor(Color.RED);
        	
        	//Print value of Point
    		g2.drawString(yValues.get(i).toString(), (float)(i * xScaling),  (float)(h - (yValues.get(i) * (h + offset)) + 15));
    		
        	if(i == 1){ //Draw first line from origin to first point read 
        		g2.draw(new Line2D.Double(i * xScaling, h - (yValues.get(i) * (h + offset)), offset ,  (h - offset) ));
        	}//if
        	else{ //Draw other lines, from last point to latest point
        		g2.draw(new Line2D.Double(i * xScaling, h - (yValues.get(i) * (h + offset)), (i-1) * xScaling ,  h - (yValues.get(i - 1) * (h + offset) )));
        	}
        }
        
    }
		
		
	
	
	public void tableChanged(TableModelEvent e) {
		
		DefaultTableModel mod = (DefaultTableModel)e.getSource();
		int bestCol = mod.findColumn("Best");
		if(e.getType() == TableModelEvent.INSERT){ //React only if first value is already inserted
			Double y = (Double) mod.getValueAt(0, bestCol); //get new value out of table
			
			if(y.doubleValue() > yValues.get(yValues.size() - 1).doubleValue()){
				yValues.add(y);
				paintComponents(getGraphics());
				repaint();
			}
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
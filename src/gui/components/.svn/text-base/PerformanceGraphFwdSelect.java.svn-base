package gui.components;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import controller.MainController;

/**
 * class to plot the data of an forward selection wrapper run
 * @author Johannes Schoellhorn
 *
 */
public class PerformanceGraphFwdSelect extends PerformanceGraphBase implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6597713383689245128L;
	
	volatile boolean showLabels;
	volatile boolean rotateLabels;
	
	/**
	 * Constructor
	 * @param main Main controller
	 * @param parent The graphs parent result view
	 */
	public PerformanceGraphFwdSelect(MainController main, ResultsView parent) {
		super(main, parent);
		
		this.showLabels = true;
		this.rotateLabels = true;
		
		this.menu = new FwdSelectGraphMenu(this);
	}
	
	/**
	 * @return the showLabels
	 */
	public boolean isShowLabels() {
		return showLabels;
	}

	/**
	 * @param showLabels the showLabels to set
	 */
	public void setShowLabels(boolean showLabels) {
		this.showLabels = showLabels;
		repaint();
	}
	
	/**
	 * @return the rotateLabels
	 */
	public boolean isRotateLabels() {
		return this.rotateLabels;
	}
	
	/**
	 * @param rotateLabels the rotateLabels to set
	 */
	public void setRotateLabels(boolean rotateLabels) {
		this.rotateLabels = rotateLabels;
		repaint();
	}
	
	/**
	 * returns the first best fitness found in the table model
	 */
	public void setMin() {
		// get the data
		DefaultTableModel model;
		if (this.parent.getResultsData() != null) {
			model = this.parent.getResultsData().getTable();
		} else {
			model = this.main.getResultsData().getTable();
		}
		
		double min = (Double) model.getValueAt(model.getRowCount()-1, model.findColumn("Best"));
		if (min < 0) {
			this.min = Math.floor(min*10)/10;
		}
	}

	/**
	 * overwrites super.paintComponent. Draws x-axis,y-axis and trend of performance
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		
		DefaultTableModel model;
        if (parent.resultsData == null) {
        	model = this.main.getResultsData().getTable();
        } else {
        	model = parent.getResultsData().getTable();
        }
        
        int bestCol = model.findColumn("Best");
        int labelCol = model.findColumn("Feature");
        
		// draw label for the x-axis
        g2.setColor(Color.BLACK);
        this.drawCenteredLabel(g2, (getWidth() - (leftOffset + rightOffset))/2 + leftOffset,
        		getHeight()-15, "Iteration");
        
        int point = 1;
        int numberOfPoints = model.getRowCount();
        // draw all data points
        for (int i = model.getRowCount() - 1; i >= 0 ; i--) {
        	g2.setColor(Color.RED);
        	
        	double value = (Double) model.getValueAt(i, bestCol);
        	double prevValue;
        	if(i == model.getRowCount() - 1) {
        		prevValue = 0;
        	} else {
        		prevValue = (Double) model.getValueAt(i+1, bestCol);
        	}
        	String label = (String) model.getValueAt(i, labelCol);
        	String xLabel = (Integer) model.getValueAt(i, 0) + "";
        	
        	int xValue = this.getXCoord(point, numberOfPoints);
        	int yValue = this.getYCoord(value);

        	// draw red line between points
        	g2.setColor(Color.red);
        	g2.draw(new Line2D.Double(xValue, yValue,
        			this.getXCoord(point-1, numberOfPoints),
        			this.getYCoord(prevValue)));

        	// draw a black cross on point
        	g2.setColor(Color.BLACK);
        	int length = 2;
        	g2.draw(new Line2D.Double(xValue-length, yValue+length,
        			xValue+length, yValue-length));
        	g2.draw(new Line2D.Double(xValue-length, yValue-length,
        			xValue+length, yValue+length));
        	
        	if (showLabels) {
        		if (rotateLabels) {
        			// draw the label above the point (centered & rotated)
        			this.drawCenteredRotatedString(g2, xValue, yValue - 10, label, false);
        		} else {
        			// draw the label beneath the point
        			g2.drawString(label, xValue, yValue + 15);
        		}
        	}
        	
    		// draw the label/ marker for the x-axis
    		g2.draw(new Line2D.Double(xValue, this.getYCoord(0)-2,
    				xValue, this.getYCoord(0)+2));
    		this.drawCenteredLabel(g2, xValue, this.getYCoord(0)+20, xLabel);
    		
        	point++;
        } 
    }
}

/**
 * the menu to control the forward selection performance graph
 * @author Johannes Schoellhorn
 *
 */
class FwdSelectGraphMenu extends JMenu implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818961773906280482L;

	PerformanceGraphFwdSelect parent;

	JMenuItem showLabels = new JMenuItem("Hide labels");
	JMenuItem rotateLabels = new JMenuItem("Rotate labels");
	
	public FwdSelectGraphMenu(PerformanceGraphFwdSelect parent) {
		this.parent = parent;
		this.init("Graph", null);
		this.initMenu();
	}
	
	public void initMenu() {
		this.add(showLabels);
		showLabels.addActionListener(this);
		this.add(rotateLabels);
		rotateLabels.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (showLabels.equals(e.getSource())) {
			parent.setShowLabels(!parent.isShowLabels());
			if (parent.isShowLabels()) {
				showLabels.setText("Hide labels");
			} else {
				showLabels.setText("Show labels");
			}
		} else if (rotateLabels.equals(e.getSource())) {
			parent.setRotateLabels(!parent.isRotateLabels());
		}
	}
	
}

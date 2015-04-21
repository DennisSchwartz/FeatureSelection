package gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import controller.MainController;


public abstract class PerformanceGraphBase  extends JPanel implements TableModelListener {

	
	/**
	 * Genererated SerialID
	 */
	private static final long serialVersionUID = 1114442487562777857L;
	
	ResultsView parent;
	MainController main;
	
	JMenu menu;
	
	int leftOffset = 60;
	int rightOffset = 40;
	int bottomOffset = 50;
	int topOffset = 35;
	
	double range = 1;
	double min = 0;

	/**
	 * Constructor
	 * @param main Main controller
	 * @param parent The graphs parent results view
	 */
	public PerformanceGraphBase(MainController main, ResultsView parent) {
		//set fields
		this.main = main;
		this.parent = parent;
		this.setForeground(Color.BLUE);
		this.setBackground(Color.WHITE);
		
		this.menu = new GraphMenuBase();
		
		this.main.getResultsData().getTable().addTableModelListener(this);
	}
	
	
	/**
	 * overwrites super.paintComponent. Draws x-axis,y-axis and scaling lines, rest is to be done by classes that inherit from this class
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw y-axis.
        g2.draw(new Line2D.Double(leftOffset, this.getYCoord(1), leftOffset, this.getYCoord(min)));
        // draw labeling for y-axis
    	g2.setColor(Color.BLACK);
        this.drawCenteredRotatedString(g2, 15, this.getYCoord(range/2+min), "Performance", true);
        
        
        /* Draw grid, to get better view on performance value in plot*/
        double steps = 10;
        for (int i = 0; i <= (int) (steps * range); i++) {
        	// calculate unscaled value to draw the line
        	double value = i / steps + min;
        	value = Math.round(value*100) / 100.0;
        	//Draw line
        	g2.setColor(Color.LIGHT_GRAY);
        	g2.draw(new Line2D.Double(leftOffset,
        			this.getYCoord(value),
        			getWidth() - rightOffset,
        			this.getYCoord(value)));
        	
        	//Draw labeling of lines
        	g2.setColor(Color.BLACK);
        	this.drawCenteredLabel(g2, leftOffset-5, this.getYCoord(value), ((Double) value).toString(), true, true);
        	// draw labels on the right sight too
        	g2.setColor(Color.LIGHT_GRAY);
        	this.drawCenteredLabel(g2, getWidth()-5, this.getYCoord(value), ((Double) value).toString(), true, true);
        	//g2.drawString(((Double)(i / max)).toString(), leftOffset - 25, this.getYCoord(i/max));
        }
    	// draw x-axis in blue
    		g2.setColor(Color.BLUE);
    		g2.draw(new Line2D.Double(leftOffset,
    				this.getYCoord(0),
    				getWidth() - rightOffset,
    				this.getYCoord(0)));
	}
	
	/**
	 * draws a label on coordinate x,y which is centered and aligned on that
	 * coordinate in the given direction
	 * @param g Graphics object to draw to
	 * @param x coordinate
	 * @param y coordinate
	 * @param label String to draw
	 * @param verticalCentered boolean true for vertical, false for horizontal
	 * @param rightAligned boolean true for right aligned text, false for left aligned
	 */
	protected void drawCenteredLabel(Graphics g, int x, int y, String label, boolean verticalCentered, boolean rightAligned) {
		Graphics2D g2 = (Graphics2D) g;
		
		// Find the size of this text so we can center it.
        FontMetrics fm = g2.getFontMetrics(g2.getFont());  // metrics for this object
        Rectangle2D rect = fm.getStringBounds(label, g2); // size of string
        int textWidth  = (int)(rect.getWidth());
        int textHeight = (int)(rect.getHeight());
        
        // if vertical centered
        if (verticalCentered) {
        	// if vertical centered, we can right align the text
        	if (rightAligned) {
        		x -= textWidth;
        	}
        	y += textHeight/2;
        // else we center horizontally
        } else {
        	x -= textWidth/2;
        }
        
        // draw the string
        g2.drawString(label, x, y);
	}
	
	/**
	 * draws a label on coordinate x,y which is horizontally centered and left
	 * aligned on that coordinate
	 * @param g Graphics object to draw to
	 * @param x coordinate
	 * @param y coordinate
	 * @param label String to draw
	 */
	protected void drawCenteredLabel(Graphics g, int x, int y, String label) {
		this.drawCenteredLabel(g, x, y, label, false, false);
	}
	
	/**
	 * draws a label which is rotated on coordinate x,y centered on that coordinate by given direction
	 * @param g Graphics object to draw to
	 * @param x coordinate
	 * @param y coordinate
	 * @param label String to draw
	 * @param verticalCentered boolean true for vertical, false for horizontal
	 */
	protected void drawCenteredRotatedString(Graphics g, int x, int y, String label, boolean verticalCentered) {

	     Graphics2D g2 = (Graphics2D)g;
	     // transform angle in rad
	     double theta = 270 * java.lang.Math.PI/180;

	    // Create a rotation transformation for the font.
	    AffineTransform fontAT = new AffineTransform();

	    // get the current font
	    Font theFont = g2.getFont();

	    // Derive a new font using a rotation transform
	    fontAT.rotate(theta);
	    Font theDerivedFont = theFont.deriveFont(fontAT);

	    // set the derived font in the Graphics2D context
	    g2.setFont(theDerivedFont);
	    
	    // draw the label
	    // Find the size of this text so we can center it.
        FontMetrics fm = g2.getFontMetrics(theFont);  // metrics for this object
        Rectangle2D rect = fm.getStringBounds(label, g2); // size of string
        int textWidth  = (int)(rect.getWidth());
        int textHeight = (int)(rect.getHeight());
        
        if (verticalCentered) {
        	y += textWidth/2;
        } else {
        	x += (textHeight/2-2);
        }
        
        g2.drawString(label, x, y);

	    // put the original font back
	    g2.setFont(theFont);

	}
	
	/**
	 * calculate x coordinate
	 * @param point which point we're looking at on the x-axis
	 * @param numberOfPoints how many points the x-axis displays
	 * @return the x coordinate for a point
	 */
	protected int getXCoord(int point, int numberOfPoints) {
		double xScaling = ((this.getWidth() - (leftOffset + rightOffset)) / (numberOfPoints + 1.0));
		int res =  (int) (point * xScaling + leftOffset);
		return res;
	}
	
	/**
	 * calculate y coordinate
	 * @param value value to be transformed
	 * @return the y coordinate for a point
	 */
	protected int getYCoord(double value) {
		// transform the value to a value in our range
		double scaledValue = (value - min) / range;
		// calculate with doubles for precision and cast return value to int
		double ySpace = this.getHeight() - (topOffset + bottomOffset);
		double yFromOrigin = ySpace * scaledValue;
		double yFromTop = ySpace - yFromOrigin + topOffset;
		return (int) yFromTop;
	}
	
	/**
	 * @return the menu
	 */
	public JMenu getMenu() {
		return this.menu;
	}
	
	/**
	 * set the range of the y-axis by determining the minimum
	 * and adding 1 to the absolute value of the minimum
	 * <br />
	 * <br />
	 * ATM the range is meant to be between -1 and 1 giving it
	 * a maximum value of 2
	 */
	public void setRange() {
		this.setMin();
		if (this.min != 0) {
			this.range = 1 + Math.abs(this.min);
		}
	}
	
	/**
	 * set the min of the y-axis
	 * <br />
	 * <br />
	 * Every class extending PerformanceGraphBase has to implement this method.
	 * If you want a fixed range, like from -1 to 1, just let the function return
	 * -1 every time.
	 */
	public abstract void setMin();

	public void tableChanged(TableModelEvent e) {
		
		if(e.getType() == TableModelEvent.INSERT){ //React only if first value is already inserted
			// set the range
			this.setRange();
			repaint();
			
		}
	}

}

/**
 * a basic menu for a performance graph with no functionality
 * @author Johannes Schoellhorn
 *
 */
class GraphMenuBase extends JMenu {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5419745165748002298L;

	public GraphMenuBase() {
		this.setText("Graph");
		this.setEnabled(false);
		this.add(new JMenuItem("nothing to do!"));
	}
}

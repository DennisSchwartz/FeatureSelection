/**
 * 
 */
package gui.components.resultTabs;

import gui.components.Components;

import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Class for the Run Details tab in the ResultsView window
 * 
 * @author Johannes Schoellhorn
 *
 */
public class RunDetailsTab extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 146887971575364592L;
	
	JTextArea details;
	
	public RunDetailsTab(String detailsStr) {
		this.details = new JTextArea(detailsStr);
		this.details.setEditable(false);
		this.details.setLineWrap(true);
		// select a monospaced font to get proper formatting of lines
		this.details.setFont(new Font(Font.MONOSPACED, Font.PLAIN, this.details.getFont().getSize()));
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		Components.add(this, gbl,
				new JScrollPane(details),
				0, 0, 1, 1, 1, 1);
	}
	
	/**
	 * @return the details
	 */
	public JTextArea getDetails() {
		return this.details;
	}

}

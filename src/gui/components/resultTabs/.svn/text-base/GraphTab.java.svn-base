/**
 * 
 */
package gui.components.resultTabs;

import gui.components.Components;
import gui.components.PerformanceGraphBase;
import gui.components.PerformanceGraphEvA;
import gui.components.PerformanceGraphFwdSelect;
import gui.components.ResultsView;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;

import controller.MainController;

/**
 * @author Johannes Schoellhorn
 *
 */
public class GraphTab extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1298159507530792725L;

	GraphTab self;
	
	MainController main;
	ResultsView parent;
	
	JButton showInNewWindow;
	PerformanceGraphBase perfGraph;
	JFrame newWindow;
	
	public GraphTab(MainController main, ResultsView parent) {
		this.self = this;
		
		this.main = main;
		this.parent = parent;
		
		// select the right performance graph to plot data
		switch (this.main.getResultsData().getWrapperType()) {
		case EVA:
			perfGraph = new PerformanceGraphEvA(main, parent);
			break;
		case FORWARDSELECTION:
			perfGraph = new PerformanceGraphFwdSelect(main, parent);
			break;
		default:
			perfGraph = null;
		}
		
		showInNewWindow = new JButton("Open Graph window");
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		Components.add(this, gbl,
				new JLabel("Graph opened in its own window!", JLabel.CENTER),
				0, 0, 1, 1, 1, 1);
		Components.add(this, gbl,
				showInNewWindow,
				0, 1, 1, 1, 0, 0);
		
		newWindow = new JFrame("Graph");
		
		this.showInNewWindow.addActionListener(this);
		
		newWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		newWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				self.removeAll();
				Components.add(self, (GridBagLayout) self.getLayout(),
						perfGraph,
						0, 0, 1, 1, 1, 1);
				Components.add(self, (GridBagLayout) self.getLayout(),
						showInNewWindow,
						0, 1, 1, 1, 1, 0);
				self.showInNewWindow.setEnabled(true);
				self.validate();
				((JFrame) winEvt.getSource()).setVisible(false);
			}
		});
		
		this.openGraphInNewWindow();
	}
	
	/**
	 * @return the menu
	 */
	public JMenu getMenu() {
		return this.perfGraph.getMenu();
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		this.openGraphInNewWindow();
		this.invalidate();
	}
	
	private void openGraphInNewWindow() {

		newWindow.setBounds(this.parent.getX() + this.parent.getWidth(),
				this.parent.getY(),
				this.parent.getWidth(),
				this.parent.getHeight());
		
		showInNewWindow.setEnabled(false);
		
		newWindow.add(this.perfGraph);
		
		this.removeAll();
		Components.add(this, (GridBagLayout) this.getLayout(),
				new JLabel("Graph opened in its own window!", JLabel.CENTER),
				0, 0, 1, 1, 1, 1);
		Components.add(this, (GridBagLayout) this.getLayout(),
				showInNewWindow,
				0, 1, 1, 1, 1, 0);
		
		this.validate();
		newWindow.setVisible(true);
	}
	
	public JFrame getNewWindow() {
		return this.newWindow;
	}
	
	public PerformanceGraphBase getPerformanceGraph() {
		return this.perfGraph;
	}
	
	public void test(){
		System.out.println("done!");
	}

}

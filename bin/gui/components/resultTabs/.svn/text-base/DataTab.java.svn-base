/**
 * 
 */
package gui.components.resultTabs;

import gui.components.Components;

import java.awt.GridBagLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import controller.MainController;
import dataStructures.ResultsData;

/**
 * Class for the Data tab of the ResultsView window
 * 
 * @author Dennis Schwartz
 * 
 */

public class DataTab extends JPanel implements TableModelListener {

	private static final long serialVersionUID = 1L;

	MainController main;
	ResultsData data;
	
	JTextField bestResult;
	JTable dataTable;

	/**
	 * Empty constructor
	 */
	public DataTab(MainController main) {
		this.main = main;
		// sets window size
		this.setBounds(0, 0, 400, 300);
		// set CellRenderer of table to FormattedTableCellRenderer for formatted double values
		dataTable = new JTable(main.getResultsData().getTable());
		dataTable.setDefaultRenderer(Object.class, new FormattedTableCellRenderer());
		// space for the results table
		JScrollPane scrollableTable = new JScrollPane(dataTable);
		// text field for the overall best result
		bestResult = new JTextField(5);
		this.bestResult.setEditable(false);

		// layout
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[2]; // Layout should have 1 column
		gbl.rowHeights = new int[4]; // and 3 rows
		this.setLayout(gbl);

		Components.add(this, gbl, new JLabel("Best Result:"), 0, 0, 1, 1, 0, 0);
		Components.add(this, gbl, bestResult, 1, 0, 1, 1, 1, 0);

		Components.add(this, gbl, scrollableTable, 1, 1, 1, 1, 1, 1);

		this.main.getResultsData().getTable().addTableModelListener(this);
	}

	/**
	 * Updates the table in view if new results are available and deletes the
	 * best results on a new start.
	 */
	public void tableChanged(TableModelEvent e) {
		if (e.getType() == TableModelEvent.INSERT) {
			this.bestResult.setText(this.main.getResultsData()
					.getSubsetPerformanceData().getPerformance()
					+ "");
		} else if (e.getType() == TableModelEvent.DELETE) {
			this.bestResult.setText("");
		}
	}

}

/**
 * Custom TableCellRenderer for a formatted output of double values in the ResultsTable
 * @author Dennis Schwartz
 *
 */
class FormattedTableCellRenderer extends DefaultTableCellRenderer {
	/**
	 * Generated VersionID
	 */
	private static final long serialVersionUID = 4831461569192199496L;

	@Override
	public void setValue(Object value) {
		if (value instanceof Double) {
			DecimalFormat format = new DecimalFormat("0.00000");
			setText(format.format(value)+ "");
		} else
			super.setValue(value);
	}
}

/**
 * 
 */
package dataStructures;

import gui.components.PerformanceGraphBase;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import wrapper.WrapperConfigBase;
import wrapper.WrapperTypes;

/**
 * @author Johannes Schoellhorn, Dennis Schwartz
 * 
 */
public abstract class ResultsData {

	String fileName;
	String absoluteFileName;
	FeatureData data;
	WrapperConfigBase wrpCfg;

	Date start;
	Date end;
	boolean stoppedByUser = false;

	SubsetPerformanceData bestSubset = new SubsetPerformanceData(new BitSet(),
			0);
	DefaultTableModel table = new DefaultTableModel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6869913721714794112L;

		public boolean isCellEditable(int rowIndex, int mColIndex) {
			return false;
		}
	};

	/**
	 * Creates new SelectionData Object with a table specifier in subclasses
	 * 
	 * @param colTitles
	 *            The titles of the columns
	 */
	public ResultsData(String[] colTitles, String fileName,
			String absoluteFileName, FeatureData data, WrapperConfigBase wrpCfg) {
		this.initTable(colTitles);
		this.fileName = fileName;
		this.absoluteFileName = absoluteFileName;
		this.data = data;
		this.wrpCfg = wrpCfg;
	}

	/**
	 * Adds Columns to the table with names specified in Array
	 * 
	 * @param colTitles
	 */
	private void initTable(String[] colTitles) {
		for (int i = 0; i < colTitles.length; i++) {
			this.table.addColumn(colTitles[i]);
		}
	}

	/**
	 * Adds a new line of results to the table
	 * 
	 * @param results An array of objects to be added
	 */
	public abstract void add(Object[] results);

	/**
	 * get the features encoded by the bitset as a array of strings
	 * 
	 * @param bitset BitSet encoding the features
	 * @return String[] of features
	 */
	public static String[] getFeatures(FeatureData data, BitSet bitset) {
		String[] res;

		List<String> resList = new ArrayList<String>();
		for (int i = 0; i < data.getFeatures().length; i++) {
			if (bitset.get(i)) {
				resList.add(data.getFeatures()[i]);
			}
		}

		res = new String[resList.size()];
		res = resList.toArray(res);

		return res;
	}

	public boolean isEmpty() {
		return (this.table == null || this.bestSubset == null);
	}

	public void setBestSubset(SubsetPerformanceData subset) {
		this.bestSubset = subset;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @param stop the stop to set
	 */
	public void setStop(Date stop) {
		this.end = stop;
	}

	/**
	 * @param stoppedByUser the stoppedByUser to set
	 */
	public void setStoppedByUser(boolean stoppedByUser) {
		this.stoppedByUser = stoppedByUser;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the absoluteFileName
	 */
	public String getAbsoluteFileName() {
		return absoluteFileName;
	}

	public SubsetPerformanceData getSubsetPerformanceData() {
		return this.bestSubset;
	}

	public DefaultTableModel getTable() {
		return this.table;
	}

	public static void tableModeltoCSV(DefaultTableModel model, File file)
			throws IOException {
		File result = file;
		String row = "";
		FileWriter writer = new FileWriter(result);
		String escape = System.getProperty("line.separator");

		// column labels for csv file
		for (int i = 0; i < model.getColumnCount(); i++) {
			if (i == model.getColumnCount() - 1) {
				row += model.getColumnName(i);
			} else {
				row += model.getColumnName(i) + " , ";
			}
		}
		writer.write(row + escape);
		row = "";

		/*
		 * reads the table upside down, leaving the first results on top, as
		 * opposed to the results table which shows the latest results on top
		 */
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			for (int j = 0; j < model.getColumnCount(); j++) {
				if (j == (model.getColumnCount() - 1)) {
					row += model.getValueAt(i, j).toString();
				} else {
					row += model.getValueAt(i, j).toString() + " , ";
				}
			}
			writer.write(row + escape);
			row = "";
		}
		writer.close();
	}

	public static void graphToSVG(PerformanceGraphBase graph, File file)
			throws IOException {
		// Get a DOMImplementation.
		DOMImplementation domImpl = GenericDOMImplementation
				.getDOMImplementation();
		// Create an instance of org.w3c.dom.Document.
		String svgNS = "http://www.w3.org/2000/svg";
		Document document = domImpl.createDocument(svgNS, "svg", null);

		// Create an instance of the SVG Generator.
		SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

		// save old dimension and
		Dimension oldDim = new Dimension(graph.getWidth(), graph.getHeight());
		// TODO generate dimension by data?
		Dimension newDim = new Dimension(1280, 800);

		// set dimension of output
		graph.setSize(newDim);
		// Print the graph to the generator
		graph.print(svgGenerator);
		// revert dimension
		graph.setSize(oldDim);

		// Finally, stream out SVG to the standard output using
		// UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
		FileWriter out = new FileWriter(file);
		svgGenerator.stream(out, useCSS);

		out.close();
	}

	public static void detailsToTXT(String details, File file)
			throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(details));
		FileWriter writer = new FileWriter(file);
		String line = "";
		String newLine = System.getProperty("line.separator");

		line = br.readLine();
		// reads all lines from br and writes them to file, ends
		// each line with the systems newline character(s)
		while (line != null) {
			writer.write(line + newLine);
			line = br.readLine();
		}
		writer.close();
		br.close();
	}

	public String getEndTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		return df.format(this.end);
	}
	
	public abstract WrapperTypes getWrapperType();

	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String res = "";
		res += this.absoluteFileName + "\n\n";

		res += "Start: ";
		if (start != null) {
			res += df.format(start);
		} else {
			res += "0000-00-00_00:00:00";
		}
		res += "\n";

		res += "End:   ";
		if (end != null) {
			res += df.format(end);
		} else {
			res += "running ...";
		}
		res += "\n";

		if (stoppedByUser) {
			res += "Terminated by User!\n\n";
		} else {
			res += "\n";
		}

		if (this.bestSubset != null) {
			res += "Selcted Features:";
			String[] features = ResultsData.getFeatures(this.data,
					this.bestSubset.getFeatureBitSet());
			for (int i = 0; i < features.length; i++) {
				res += " " + features[i];
				if (i != features.length - 1) {
					res += " ,";
				}
			}
			res += "\n\n";
		}

		res += this.wrpCfg.toString();
		return res;
	}

}

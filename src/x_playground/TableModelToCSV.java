package x_playground;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;

/**
 * Contains static method tableModeltoCSV.
 * This method gets a DefaultTableModel and a String and converts the model to a .csv file at String directory
 * @author julianschwab
 *
 */

public class TableModelToCSV {

	/**
	 * Converts DefaultTableModel to .csv-File at path directory
	 * @param model to convert
	 * @param directory file direction
	 * @throws IOException
	 */
	
	public static void tableModeltoCSV(DefaultTableModel model, String directory) throws IOException{
		File result = new File(directory);
		String row = "";
		FileWriter writer = new FileWriter(result);
		String escape = System.getProperty("line.separator");
	
		for (int i = 0; i< model.getRowCount(); i++) {
			for (int j = 0; j<model.getColumnCount(); j++) {
				if(j == (model.getColumnCount() - 1)) {
					row += model.getValueAt(i,j).toString();
				}
				else{
					row += model.getValueAt(i,j).toString() + " , ";
				}
			}
			writer.write(row + escape);
			row = "";
		}
		
		
		
		writer.close();
	}
	
}

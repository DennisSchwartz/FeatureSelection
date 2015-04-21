package gui.stream;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
/**
 * TextAreaOutput is to redirect an Output stream to JTextArea
 * @author julianschwab
 *
 */
public class TextAreaOutput extends OutputStream {

	private JTextArea output;
	
	/**
	 * Constructor 
	 * @param out JTextArea which is to report output
	 */
	public TextAreaOutput(JTextArea out) {
		this.output = out;
	}
	
	/**
	 * Appends output Stream in TextArea
	 */
	@Override
	public void write(final int arg0) throws IOException {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				output.append(String.valueOf((char)arg0));
				output.setCaretPosition(output.getText().length());
				output.validate();
			}
		});
		
	}

}

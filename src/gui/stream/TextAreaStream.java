package gui.stream;

import java.io.PrintStream;

import javax.swing.JTextArea;

/**
 * PrintStream that writes in JTextArea
 * @author julianschwab
 *
 */
public class TextAreaStream extends PrintStream {

	public TextAreaStream(JTextArea area) {
		super(new TextAreaOutput(area));
		
	}

}

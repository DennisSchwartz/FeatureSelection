package logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;


/**
 * Class to set up all loggers used in the src package. To use a logger in a
 * class, use
 * {@code public static final Logger log = Logger.getLogger(ThisClass.class.getName())}
 * and {@code LoggerSetup.setup() } to set it up.
 * 
 * @author Dennis Schwartz
 * 
 */

public class LoggerSetup {

	

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss";

	/**
	 * Gets the current date and time to add to filename
	 * 
	 * @return String of current Date and Time
	 */
	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());

	}


	/**
	 * Sets up all loggers in src package and in any additional packages
	 * specified using the given log level.
	 * 
	 * @param level Log level to be used.
	 * @param packages Additional packages in which loggers are to be set up
	 * @param filePath Path to were the .log-file is saved
	 */
	public static void setup(LoggerConfig config) {
		config.setLevel();
		FileHandler fh = null;
		try {
			fh = new FileHandler(config.filePath, true);
		} catch (SecurityException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		//set level to all handlers in package
		for (Handler h : Logger.getLogger("").getHandlers()) {
			h.setLevel(config.level);
		}
		//set level to root logger
		Logger.getLogger("FeatureSelection/src").setLevel(config.level);
		//add handler to root logger
		Logger.getLogger("").addHandler(fh);

		// additional packages to log
//		if (config.packages != null) {
//			for (String s : config.packages) {
//				for (Handler h : Logger.getLogger(s).getHandlers()) {
//					h.setLevel(config.logLevel);
//				}
//				Logger.getLogger(s).setLevel(config.logLevel);
//			}
//		}

	}

}

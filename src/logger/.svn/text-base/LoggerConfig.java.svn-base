package logger;

import java.net.URL;
import java.util.logging.Level;

import controller.MainController;

public class LoggerConfig {

	
	static String standardPath = "log.log";
	
	String filePath = standardPath;
	
	String packages = null;
	
	Level level;


	public void setLevel() {
		this.level = Level.OFF;
	}


	/**
	 * Will set the path of the log file, to the path of program itself
	 */
	static void setStandardPath() {
		// get the location of the main controller on this device
		URL propPathUrl = MainController.class.getProtectionDomain()
				.getCodeSource().getLocation();
		// split the path by "/"
		String[] path = propPathUrl.getPath().split("/");
		String propPath = "";
		// drop the last folder of the path, thereby jumping to the parent
		// directory
		for (int i = 0; i < path.length - 1; i++) {
			if (propPath == "") {
				propPath = path[i];
			} else {
	
				propPath = propPath + "/" + path[i];
			}
		}
		// go into the resources folder and specify the filename for the log
		// file.
		standardPath = propPath + "/resources" + "/FS - " + LoggerSetup.now() + ".log";
	}

}

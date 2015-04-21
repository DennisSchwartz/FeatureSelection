/**
 * 
 */
package gui;

import java.util.logging.Logger;

import javax.swing.UIManager;

import logger.LoggerConfig;
import logger.LoggerSetup;



/**
 * @author Johannes Schoellhorn
 *
 */
public class FeatureSelectionLauncher {

    public static final Logger log = Logger.getLogger(FeatureSelectionLauncher.class.getName());
    
	public static void main(String[] args) {
		
		// try to set mac style menus
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Feature Selection");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	System.out.println("Not on Mac OS, using default menu style!");
        }
        
        
//        catch(ClassNotFoundException e) {
//            System.out.println("ClassNotFoundException: " + e.getMessage());
//        }
//
//        catch(InstantiationException e) {
//            System.out.println("InstantiationException: " + e.getMessage());
//        }
//
//        catch(IllegalAccessException e) {
//            System.out.println("IllegalAccessException: " + e.getMessage());
//        }
//
//        catch(UnsupportedLookAndFeelException e) {
//            System.out.println("UnsupportedLookAndFeelException: " + e.getMessage());
//        }
        LoggerSetup.setup(new LoggerConfig());
        log.info("GUI started");
        StartGui.main(null);
	}

}

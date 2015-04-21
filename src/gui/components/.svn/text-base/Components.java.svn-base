package gui.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * Helperclass to make adding new components a little easier
 * @author julianschwab
 *
 */
public class Components {

	
	public static void add(Container container,
								GridBagLayout gbl,
								Component component,
								int x,
								int y,
								int width,
								int height,
								double weightx,
								double weighty) {
		
		 GridBagConstraints gbc = new GridBagConstraints(); 
		    gbc.fill = GridBagConstraints.BOTH;
		    gbc.gridwidth = GridBagConstraints.REMAINDER;
		    gbc.gridx = x;
		    gbc.gridy = y; 
		    gbc.gridwidth = width; 
		    gbc.gridheight = height; 
		    gbc.weightx = weightx; 	
		    gbc.weighty = weighty;
		    gbl.setConstraints(component, gbc); 
		    container.add(component); 
		
	}
}

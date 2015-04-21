/**
 * 
 */
package wrapper;

import java.util.EventListener;

/**
 * Use interface to receive notification when a WrapperThread has finished
 * @author Johannes Schoellhorn
 *
 */
public interface WrapperThreadListener extends EventListener {
	
	public void threadFinished();

}

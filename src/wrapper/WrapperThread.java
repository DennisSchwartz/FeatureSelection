package wrapper;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import javax.swing.event.EventListenerList;

/**
 * WrapperThread inherits from Thread but has a new stop-method, myStop().
 * myStop() quits the thread, by setting the breakPoint variable in the wrapper base object to false.
 * In every wrapper class such a breakPoin should be implemented 
 * @author julianschwab
 *
 */

public class WrapperThread extends Thread {

	EventListenerList listeners = new EventListenerList();
	
	protected WrapperBase wrapper;
	protected CountDownLatch startSignal;
	
	public WrapperThread(WrapperBase wrapper, CountDownLatch startSignal) {
		super();
		this.startSignal = startSignal;
		this.wrapper = wrapper;
	}
	
	public void run() {
		try {
			startSignal.await();
			// run the thread
			this.wrapper.run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// when thread finished notify all listeners
			this.notifyResultListeners(new Date());
			this.notifyThreadListeners();
		}
	}
	
	/**
	 * forceStop() quits Thread by setting breakpoint of wrapper to false
	 */
	public void forceStop() {
		this.wrapper.setBreakPoint();
	}
	
	public void addWrapperThreadListener(WrapperThreadListener listener) {
		listeners.add(WrapperThreadListener.class, listener);
	}
	
	public void removeWrapperThreadListener(WrapperThreadListener listener) {
		listeners.remove(WrapperThreadListener.class, listener);
	}
	
	protected synchronized void notifyThreadListeners() {
		for (WrapperThreadListener l : listeners.getListeners(WrapperThreadListener.class)) {
			l.threadFinished();
		}
	}
	
	public void addResultListener(ResultListener listener) {
		listeners.add(ResultListener.class, listener);
	}
	
	public void removeResultListener(ResultListener listener) {
		listeners.remove(ResultListener.class, listener);
	}
	
	protected synchronized void notifyResultListeners(Date date) {
		for (ResultListener l : listeners.getListeners(ResultListener.class)) {
			l.resultsUpdated(date);
		}
	}
}

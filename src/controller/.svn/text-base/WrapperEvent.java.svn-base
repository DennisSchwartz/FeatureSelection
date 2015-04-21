package controller;

import java.util.EventObject;
import java.util.concurrent.CountDownLatch;

import wrapper.WrapperBase;

public class WrapperEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	WrapperBase wrapper;
	CountDownLatch startSignal;
	
	public WrapperEvent(Object source, WrapperBase wrapper, CountDownLatch startSignal) {
		super(source);
		this.startSignal = startSignal;
		this.wrapper = wrapper;
	}

	/**
	 * @return the wrapper
	 */
	public WrapperBase getWrapper() {
		return wrapper;
	}
	
	/**
	 * @return the startSignal
	 */
	public CountDownLatch getStartSignal() {
		return startSignal;
	}


}

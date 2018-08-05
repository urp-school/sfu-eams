//$Id: ArrangeResult.java,v 1.2 2006/12/06 03:37:25 duanth Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2005-11-8         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import java.util.Enumeration;
import java.util.Vector;


public class ArrangeResult {
	protected int runArranges;

	private Vector failures;

	private Vector listeners;

	public ArrangeResult() {
		failures = new Vector(10);
		listeners = new Vector(5);
		runArranges = 0;
	}

	/**
	 * Adds a failure to the list of failures. The passed in exception caused
	 * the failure.
	 */
	public void addFailure(Arrange arrange, GeneralArrangeFailure t) {
		failures.addElement(new ArrangeFailure(arrange, t));
		for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
			((ArrangeListener) e.nextElement()).addFailure(arrange, t);
		}
	}

	/**
	 * Registers a ArrangeListener
	 */
	public void addListener(ArrangeListener listener) {
		listeners.addElement(listener);
	}

	/**
	 * Unregisters a ArrangeListener
	 */
	public void removeListener(ArrangeListener listener) {
		listeners.removeElement(listener);
	}

	/**
	 * Gets the number of run arranges.
	 */
	public int runCount() {
		return runArranges;
	}

	public void startArrange(Arrange arrange) {
		final int count = arrange.countArrangeCases();
		runArranges += count;
		for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
			((ArrangeListener) e.nextElement()).startArrange(arrange);
		}
	}

	public void notifyStart() {
		for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
			((ArrangeListener) e.nextElement()).notifyStart();
		}
	}
	public void notify(String msg) {
		for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
			((ArrangeListener) e.nextElement()).notify(msg);
		}
	}
	public void notifyEnd() {
		for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
			((ArrangeListener) e.nextElement()).notifyEnd();
		}
	}

	/**
	 * Runs a ArrangeCase.
	 */
	protected void arrange(final ArrangeCase arrange) {
		startArrange(arrange);
		Protectable p = new Protectable() {
			public void protect(ArrangeResult rs) throws GeneralArrangeFailure {
				arrange.arrangeBare(rs);
			}
		};
		arrangeProtected(arrange, p);
		endArrange(arrange);
	}

	/**
	 * Runs a ArrangeCase.
	 */
	public void arrangeProtected(final Arrange arrange, Protectable p) {
		try {
			p.protect(this);
		} catch (GeneralArrangeFailure e) {
			addFailure(arrange, e);
		} catch (Exception e) {
			// TODO
			//e.printStackTrace();
			addFailure(arrange, new GeneralArrangeFailure("system error"));
		}
	}

	/**
	 * 通知监听器，安排结束
	 * 
	 * @param arrange
	 */
	public void endArrange(Arrange arrange) {
		for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
			((ArrangeListener) e.nextElement()).endArrange(arrange);
		}
	}

	/**
	 * Returns whether the entire test was successful or not.
	 */
	public boolean wasSuccessful() {
		return failureCount() == 0;
	}

	public int failureCount() {
		return failures.size();
	}

	/**
	 * @return Returns the failures.
	 */
	public Enumeration getFailures() {
		return failures.elements();
	}

}

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
 * chaostone             2006-12-9            Created
 *  
 ********************************************************************************/
package com.shufe.service.util.stat;

public class StatItem implements StatCountor {

	Object what;

	Comparable[] countors;

	public StatItem(Object what) {
		this.what = what;
	}

	public StatItem(Object what, Comparable count) {
		this.what = what;
		this.countors = new Comparable[] { count };
	}

	public StatItem(Object what, Comparable count1, Comparable count2,
			Comparable count3) {
		this.what = what;
		this.countors = new Comparable[] { count1, count2, count3 };
	}

	public StatItem(Object what, Comparable count1, Comparable count2,
			Comparable count3, Comparable count4) {
		this.what = what;
		this.countors = new Comparable[] { count1, count2, count3, count4 };
	}

	public StatItem(Object what, Comparable count1, Comparable count2) {
		this.what = what;
		this.countors = new Comparable[] { count1, count2 };
	}

	public Comparable[] getCountors() {
		return countors;
	}

	public void setCountors(Comparable[] countor) {
		this.countors = countor;
	}

	public Object getWhat() {
		return what;
	}

	public void setWhat(Object what) {
		this.what = what;
	}

}

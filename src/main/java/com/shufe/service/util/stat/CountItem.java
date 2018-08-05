package com.shufe.service.util.stat;

public class CountItem implements Comparable {
	Number count;

	Object what;

	public CountItem(Number count, Object what) {
		this.count = count;
		this.what = what;
	}

	public int compareTo(Object arg0) {
		return count.intValue() - ((CountItem) arg0).count.intValue();
	}

	public Number getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Object getWhat() {
		return what;
	}

	public void setWhat(Object what) {
		this.what = what;
	}

}
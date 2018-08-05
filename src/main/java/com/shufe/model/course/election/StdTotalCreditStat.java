package com.shufe.model.course.election;

import com.shufe.model.std.Student;

public class StdTotalCreditStat {

	private Student std;
	private Number totalCredit;

	public StdTotalCreditStat(Long stdId, Number totalCredit) {
		super();
		this.std = new Student(stdId);
		this.totalCredit = totalCredit;
	}

	public void setStd(Student std) {
		this.std = std;
	}
	
	public Student getStd() {
		return std;
	}
	
	public void setTotalCredit(Number totalCredit) {
		this.totalCredit = totalCredit;
	}
	
	public Number getTotalCredit() {
		return totalCredit;
	}

}

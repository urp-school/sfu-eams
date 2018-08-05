package com.shufe.model.fee.stat;

import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.model.std.Student;

public class FeeTypeStat {

	private Student std;
	private FeeType type;
	private Number shouldPayed;
	private Number payed;

	public FeeTypeStat(Long stdId, Long typeId, Number shouldPayed, Number payed) {
		super();
		this.std = new Student(stdId);
		this.type = new FeeType(typeId);
		this.shouldPayed = shouldPayed;
		this.payed = payed;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}

	public FeeType getType() {
		return type;
	}

	public void setType(FeeType type) {
		this.type = type;
	}

	public Number getShouldPayed() {
		return shouldPayed;
	}

	public void setShouldPayed(Number shouldPayed) {
		this.shouldPayed = shouldPayed;
	}

	public Number getPayed() {
		return payed;
	}

	public void setPayed(Number payed) {
		this.payed = payed;
	}

}

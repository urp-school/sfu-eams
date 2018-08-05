package com.shufe.model.course.grade;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;

public class BonusStudent extends LongIdObject {

	private Student std;

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}
	
}

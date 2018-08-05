package com.shufe.model.degree.instruct;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 学生老师指导关系
 * 
 * @author chaostone
 * 
 */
public class Instruction extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5702493012308561274L;

	/** 学生 */
	private Student std;

	/** 指导老师 */
	private Teacher tutor;

	/** 学年学期 */
	private TeachCalendar calendar;

	/** 是否是毕业指导 */
	private Boolean isGraduation;

	/** 专业类别 */
	private MajorType majorType;

	public Instruction() {
		super();
	}

	/**
	 * 默认为毕业指导
	 * 
	 * @param calendar
	 * @param std
	 * @param tutor
	 */
	public Instruction(TeachCalendar calendar, Student std, Teacher tutor) {
		super();
		this.std = std;
		this.tutor = tutor;
		this.calendar = calendar;
		this.isGraduation = Boolean.TRUE;
	}

	public Instruction(TeachCalendar calendar, Student std, Teacher tutor,
			Boolean isGraduation) {
		super();
		this.std = std;
		this.tutor = tutor;
		this.calendar = calendar;
		this.isGraduation = isGraduation;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}

	public Teacher getTutor() {
		return tutor;
	}

	public void setTutor(Teacher tutor) {
		this.tutor = tutor;
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public Boolean getIsGraduation() {
		return isGraduation;
	}

	/**
	 * @param isGraduation
	 *            The isGraduation to set.
	 */
	public void setIsGraduation(Boolean isGraduation) {
		this.isGraduation = isGraduation;
	}

	public MajorType getMajorType() {
		return majorType;
	}

	public void setMajorType(MajorType majorType) {
		this.majorType = majorType;
	}

}

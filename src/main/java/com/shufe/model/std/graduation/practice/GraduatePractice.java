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
 * 塞外狂人             2006-8-10            Created
 *  
 ********************************************************************************/
package com.shufe.model.std.graduation.practice;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.PracticeSource;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 毕业实习
 * 
 * @author 塞外狂人,chaostone
 * @author cwx 2007-3-14修改
 * 
 */
public class GraduatePractice extends LongIdObject {

	private static final long serialVersionUID = 958397366871918710L;
	private Student student = new Student();
	private String practiceCompany;// 实习单位
	private PracticeSource practiceSource = new PracticeSource(); // 实习来源
	private Boolean isPractictBase;// 是否实习基地
	private Teacher practiceTeacher = new Teacher();// 实习指导教师
	private String practiceDesc; // 实习描述
	private TeachCalendar teachCalendar = new TeachCalendar(); // 教学日历
	private MajorType majorType;// 专业类别
	private String remark; // 实习备注

	public Boolean getIsPractictBase() {
		return isPractictBase;
	}

	public void setIsPractictBase(Boolean isPractictBase) {
		this.isPractictBase = isPractictBase;
	}

	public String getPracticeCompany() {
		return practiceCompany;
	}

	public void setPracticeCompany(String practiceCompany) {
		this.practiceCompany = practiceCompany;
	}

	public Teacher getPracticeTeacher() {
		return practiceTeacher;
	}

	public void setPracticeTeacher(Teacher practiceTeacher) {
		this.practiceTeacher = practiceTeacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return Returns the teachCalendar.
	 */
	public TeachCalendar getTeachCalendar() {
		return teachCalendar;
	}

	/**
	 * @param teachCalendar
	 *            The teachCalendar to set.
	 */
	public void setTeachCalendar(TeachCalendar teachCalendar) {
		this.teachCalendar = teachCalendar;
	}

	/**
	 * @return Returns the practiceSource.
	 */
	public PracticeSource getPracticeSource() {
		return practiceSource;
	}

	/**
	 * @param practiceSource
	 *            The practiceSource to set.
	 */
	public void setPracticeSource(PracticeSource practiceSource) {
		this.practiceSource = practiceSource;
	}

	/**
	 * @return Returns the practiceDesc.
	 */
	public String getPracticeDesc() {
		return practiceDesc;
	}

	/**
	 * @param practiceDesc
	 *            The practiceDesc to set.
	 */
	public void setPracticeDesc(String practiceDesc) {
		this.practiceDesc = practiceDesc;
	}

	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MajorType getMajorType() {
		return majorType;
	}

	public void setMajorType(MajorType majorType) {
		this.majorType = majorType;
	}

}

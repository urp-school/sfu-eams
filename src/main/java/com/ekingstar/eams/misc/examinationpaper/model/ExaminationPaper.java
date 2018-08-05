package com.ekingstar.eams.misc.examinationpaper.model;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.calendar.TeachCalendar;

public class ExaminationPaper extends LongIdObject {

	private static final long serialVersionUID = 681552175384401408L;

	/** 学年度学期 */
    private TeachCalendar calendar;
    
    /** 所属课程 */
    private Course course;
    
    /** 文档名称 */
	private String fileName;
	
	/** 文档路径 */
	private String filePath;
    
    /** 备注 */
	private String remark;

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}

package com.shufe.model.system.baseinfo;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 新开课程基本信息
 * 
 * 
 */
public class NewCourse extends LongIdObject{
	private Long id;//数据库中的标识
	private Course course;//所有课程信息
	private Integer ordernum;//顺序号
	private Integer priority;//是否置顶
	
	
	public NewCourse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NewCourse(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Integer getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	
	
	
}

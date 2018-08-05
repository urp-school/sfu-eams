package com.ekingstar.eams.research.project.model;

import com.ekingstar.commons.model.pojo.LongIdObject;

public class ProjectDocument extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7729001078874817936L;

	/**
	 * 文档名称
	 */
	private String fileName;
	
	/** 
	 * 文档路径 
	 */
	private String filePath;
	
	/**
	 * 建设项目
	 */
	protected TeachProject teachProject;
	
	/**
	 * 文档描述
	 */
	private String describe;	

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public TeachProject getTeachProject() {
		return teachProject;
	}
	public void setTeachProject(TeachProject teachProject) {
		this.teachProject = teachProject;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}

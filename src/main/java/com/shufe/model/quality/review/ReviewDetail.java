package com.shufe.model.quality.review;

import com.ekingstar.commons.model.pojo.LongIdObject;

public class ReviewDetail extends LongIdObject  {
	private static final long serialVersionUID = 7721290142381072819L;
	private String studyStyle;
	private String teachStyle;
	private String courseBuild;
	private String teachDoc;
	private String graduteDoc;
	private String teachOutline;
	private String other;
	public String getCourseBuild() {
		return courseBuild;
	}
	public void setCourseBuild(String courseBuild) {
		this.courseBuild = courseBuild;
	}
	public String getGraduteDoc() {
		return graduteDoc;
	}
	public void setGraduteDoc(String graduteDoc) {
		this.graduteDoc = graduteDoc;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getStudyStyle() {
		return studyStyle;
	}
	public void setStudyStyle(String studyStyle) {
		this.studyStyle = studyStyle;
	}
	public String getTeachDoc() {
		return teachDoc;
	}
	public void setTeachDoc(String teachDoc) {
		this.teachDoc = teachDoc;
	}
	public String getTeachOutline() {
		return teachOutline;
	}
	public void setTeachOutline(String teachOutline) {
		this.teachOutline = teachOutline;
	}
	public String getTeachStyle() {
		return teachStyle;
	}
	public void setTeachStyle(String teachStyle) {
		this.teachStyle = teachStyle;
	}
}

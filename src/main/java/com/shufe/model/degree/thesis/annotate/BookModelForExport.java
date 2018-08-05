//$Id: BookModelForExport.java,v 1.1 2007-1-30 21:09:15 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-1-30         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis.annotate;

import com.shufe.model.std.Student;

public class BookModelForExport {

	private Student student; //学生
	private String thesisTopicOpen; //开题
	private ThesisAnnotateBook thesiAnnotateBook; //评阅书
	private Float mark;//得分
	private Float avgMark;//平均分
	private String degreeLevelName;//学位水平
	private String isPassedName; //是否同意答辩
	
	/**
	 * @return Returns the avgMark.
	 */
	public Float getAvgMark() {
		return avgMark;
	}
	/**
	 * @param avgMark The avgMark to set.
	 */
	public void setAvgMark(Float avgMark) {
		this.avgMark = avgMark;
	}
	/**
	 * @return Returns the mark.
	 */
	public Float getMark() {
		return mark;
	}
	/**
	 * @param mark The mark to set.
	 */
	public void setMark(Float mark) {
		this.mark = mark;
	}
	/**
	 * @return Returns the thesiAnnotateBook.
	 */
	public ThesisAnnotateBook getThesiAnnotateBook() {
		return thesiAnnotateBook;
	}
	/**
	 * @param thesiAnnotateBook The thesiAnnotateBook to set.
	 */
	public void setThesiAnnotateBook(ThesisAnnotateBook thesiAnnotateBook) {
		this.thesiAnnotateBook = thesiAnnotateBook;
	}
	/**
	 * @return Returns the degreeLevelName.
	 */
	public String getDegreeLevelName() {
		return degreeLevelName;
	}
	/**
	 * @param degreeLevelName The degreeLevelName to set.
	 */
	public void setDegreeLevelName(String degreeLevelName) {
		this.degreeLevelName = degreeLevelName;
	}
	/**
	 * @return Returns the isPassedName.
	 */
	public String getIsPassedName() {
		return isPassedName;
	}
	/**
	 * @param isPassedName The isPassedName to set.
	 */
	public void setIsPassedName(String isPassedName) {
		this.isPassedName = isPassedName;
	}
	/**
	 * @return Returns the student.
	 */
	public Student getStudent() {
		return student;
	}
	/**
	 * @param student The student to set.
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
	/**
	 * @return Returns the thesisTopicOpen.
	 */
	public String getThesisTopicOpen() {
		return thesisTopicOpen;
	}
	/**
	 * @param thesisTopicOpen The thesisTopicOpen to set.
	 */
	public void setThesisTopicOpen(String thesisTopicOpen) {
		this.thesisTopicOpen = thesisTopicOpen;
	}
	
	
	
	
}

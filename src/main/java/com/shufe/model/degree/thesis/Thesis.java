//$Id: ThesisSelf.java,v 1.1 2007-5-23 13:23:49 Administrator Exp $
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
 * chenweixiong              2007-5-23         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis;

import java.util.Date;

import com.ekingstar.eams.system.basecode.industry.ThesisTopicSource;
import com.ekingstar.eams.system.basecode.industry.ThesisType;
import com.shufe.model.std.Student;

/**
 * 学生定稿论文
 * 
 * @author chaostone
 * 
 */
public class Thesis extends ThesisStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7728191165216299893L;
	private Student student = new Student(); // 学生
	private String name; // 定稿的论文题目
	private String keyWords; // 论文主题词
	private Float thesisNum; // 论文字数
	private String abstract_cn; // 中文摘要
	private String abstract_en; // 英文摘要
	private Date startOn; // 论文开始时间
	private Date endOn; // 论文定稿时间
	private Boolean affirm; // 确认
	private ThesisTopicSource thesisSource = new ThesisTopicSource(); // 论文选题来源
	private ThesisType thesisType = new ThesisType(); // 论文类型
	private ThesisManage thesisManage; // 论文管理
	private Boolean isExcellentThesis; // 是否优秀论文
	private String remark; // 备注

	/**
	 * @return Returns the thesiType.
	 */
	public ThesisType getThesisType() {
		return thesisType;
	}

	/**
	 * @param thesiType
	 *            The thesiType to set.
	 */
	public void setThesisType(ThesisType thesisType) {
		this.thesisType = thesisType;
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

	/**
	 * @return Returns the student.
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            The student to set.
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return Returns the abstract_cn.
	 */
	public String getAbstract_cn() {
		return abstract_cn;
	}

	/**
	 * @param abstract_cn
	 *            The abstract_cn to set.
	 */
	public void setAbstract_cn(String abstract_cn) {
		this.abstract_cn = abstract_cn;
	}

	/**
	 * @return Returns the abstract_en.
	 */
	public String getAbstract_en() {
		return abstract_en;
	}

	/**
	 * @param abstract_en
	 *            The abstract_en to set.
	 */
	public void setAbstract_en(String abstract_en) {
		this.abstract_en = abstract_en;
	}

	/**
	 * @return Returns the endOn.
	 */
	public Date getEndOn() {
		return endOn;
	}

	/**
	 * @param endOn
	 *            The endOn to set.
	 */
	public void setEndOn(Date endOn) {
		this.endOn = endOn;
	}

	/**
	 * @return Returns the keyWords.
	 */
	public String getKeyWords() {
		return keyWords;
	}

	/**
	 * @param keyWords
	 *            The keyWords to set.
	 */
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the startOn.
	 */
	public Date getStartOn() {
		return startOn;
	}

	/**
	 * @param startOn
	 *            The startOn to set.
	 */
	public void setStartOn(Date startOn) {
		this.startOn = startOn;
	}

	/**
	 * @return Returns the thesisManage.
	 */
	public ThesisManage getThesisManage() {
		return thesisManage;
	}

	/**
	 * @param thesisManage
	 *            The thesisManage to set.
	 */
	public void setThesisManage(ThesisManage thesisManage) {
		this.thesisManage = thesisManage;
	}

	/**
	 * @return Returns the thesisSource.
	 */
	public ThesisTopicSource getThesisSource() {
		return thesisSource;
	}

	/**
	 * @param thesisSource
	 *            The thesisSource to set.
	 */
	public void setThesisSource(ThesisTopicSource thesisSource) {
		this.thesisSource = thesisSource;
	}

	/**
	 * @return Returns the affirm.
	 */
	public Boolean getAffirm() {
		return affirm;
	}

	/**
	 * @param affirm
	 *            The affirm to set.
	 */
	public void setAffirm(Boolean affirm) {
		this.affirm = affirm;
	}

	/**
	 * @return Returns the thesisNum.
	 */
	public Float getThesisNum() {
		return thesisNum;
	}

	/**
	 * @param thesisNum
	 *            The thesisNum to set.
	 */
	public void setThesisNum(Float thesisNum) {
		this.thesisNum = thesisNum;
	}

	/**
	 * @return Returns the isExcellentThesis.
	 */
	public Boolean getIsExcellentThesis() {
		return isExcellentThesis;
	}

	/**
	 * @param isExcellentThesis
	 *            The isExcellentThesis to set.
	 */
	public void setIsExcellentThesis(Boolean isExcellentThesis) {
		this.isExcellentThesis = isExcellentThesis;
	}
}

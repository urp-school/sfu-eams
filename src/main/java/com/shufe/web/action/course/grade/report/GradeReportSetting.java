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
 * chaostone             2007-1-4            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade.report;

import java.util.Date;

import com.ekingstar.commons.query.Order;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MajorType;

/**
 * 报表设置
 * 
 * @author chaostone
 */
public class GradeReportSetting {
    
    public static final Integer PASS_GRADE = new Integer(0);
    
    public static final Integer ALL_GRADE = new Integer(1);
    
    public static final Integer BEST_GRADE = new Integer(2);
    
    /**
     * 打印绩点
     */
    Boolean printGP;
    
    /**
     * 打印成绩类型<br>
     * 0.及格成绩<br>
     * 1.所有成绩<br>
     * 2.最好成绩
     */
    Integer gradePrintType;
    
    /**
     * 每页打印的成绩数量
     */
    Integer pageSize;
    
    /**
     * 只打印已发布的成绩
     */
    Boolean published;
    
    /**
     * 成绩中的字体大小
     */
    Integer fontSize;
    
    /**
     * 第一专业成绩
     */
    MajorType majorType;
    
    /**
     * 打印奖励学分
     */
    Boolean printAwardCredit;
    
    /** 是否打印校外考试成绩 */
    Boolean printOtherGrade;
   
    /** 是否打印备注 */
    Boolean bz;
    
    /** 是否打印学位课标识 */
    Boolean degreeCourse;
    
    /** 是否打印每学期绩点 */
    Boolean printTermGP;
    
    /**
     * 成绩依照什么进行排序,具体含义要依照报表样式
     */
    Order order;
    
    /**
     * 打印成绩的类型
     */
    GradeType gradeType;
    
    /** 打印责任人 */
    String printBy;
    
    /** 院长/系主任 */
    String prior;
    
    /** 教务处长 */
    String chief;
    
    /** 打印消息*/
    String message; 
    
    public Boolean getBz() {
        return bz;
    }

    
    public void setBz(Boolean bz) {
        this.bz = bz;
    }

    /** 打印模板 */
    String template;
    
    /** 打印时间 */
    Date printAt;
    
    public GradeReportSetting() {
        printGP = Boolean.TRUE;
        gradePrintType = PASS_GRADE;
        pageSize = new Integer(80);
        published = Boolean.TRUE;
        fontSize = new Integer(10);
        majorType = new MajorType(MajorType.FIRST);
        printAwardCredit = Boolean.TRUE;
        order = new Order();
        gradeType = new GradeType(GradeType.FINAL);
        printOtherGrade = Boolean.TRUE;
        printTermGP = Boolean.TRUE;
        printAt = new Date();
        bz = Boolean.TRUE;
        degreeCourse =Boolean.TRUE;
        message ="";
    }
    
    public Boolean getPrintOtherGrade() {
        return printOtherGrade;
    }
    
    public void setPrintOtherGrade(Boolean printOtherGrade) {
        this.printOtherGrade = printOtherGrade;
    }
    
    public Boolean getPrintTermGP() {
        return printTermGP;
    }
    
    public void setPrintTermGP(Boolean printTermGP) {
        this.printTermGP = printTermGP;
    }
    
    public Integer getGradePrintType() {
        return gradePrintType;
    }
    
    public void setGradePrintType(Integer gradePrintType) {
        this.gradePrintType = gradePrintType;
    }
    
    public Integer getFontSize() {
        return fontSize;
    }
    
    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public Boolean getPrintGP() {
        return printGP;
    }
    
    public void setPrintGP(Boolean printGP) {
        this.printGP = printGP;
    }
    
    public Boolean getPublished() {
        return published;
    }
    
    public void setPublished(Boolean published) {
        this.published = published;
    }
    
    public Boolean getPrintAwardCredit() {
        return printAwardCredit;
    }
    
    public void setPrintAwardCredit(Boolean printAwardCredit) {
        this.printAwardCredit = printAwardCredit;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public GradeType getGradeType() {
        return gradeType;
    }
    
    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }
    
    public String getPrintBy() {
        return printBy;
    }
    
    public void setPrintBy(String printBy) {
        this.printBy = printBy;
    }
    
    /**
     * @return the template
     */
    public String getTemplate() {
        return template;
    }
    
    public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	/**
     * @param template
     *            the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }
    
    public MajorType getMajorType() {
        return majorType;
    }
    
    public void setMajorType(MajorType majorType) {
        this.majorType = majorType;
    }
    
    public Date getPrintAt() {
        return printAt;
    }
    
    public void setPrintAt(Date printAt) {
        this.printAt = printAt;
    }
    
    public String getPrior() {
        return prior;
    }
    
    public String getChief() {
        return chief;
    }
    
    public void setPrior(String prior) {
        this.prior = prior;
    }
    
    public void setChief(String chief) {
        this.chief = chief;
    }


    
    public Boolean getDegreeCourse() {
        return degreeCourse;
    }


    
    public void setDegreeCourse(Boolean degreeCourse) {
        this.degreeCourse = degreeCourse;
    }
}

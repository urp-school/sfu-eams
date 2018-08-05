//$Id: FeeDetail.java,v 1.5 2006/10/12 12:20:13 duanth Exp $
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
 * chenweixiong          2005-11-6         Created
 * chenweixiong 		 2005-12-6		   Modified
 ********************************************************************************/

package com.shufe.model.fee;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CurrencyCategory;
import com.ekingstar.eams.system.basecode.industry.FeeMode;
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 收费明细信息
 * 
 * @author chaostone
 * 
 */
public class FeeDetail extends LongIdObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5868193073466043875L;

	/** 学生 */
    private Student std;

    /** hSK成绩 */
    private String HSKGrade;

    /** 收费部门 */
    private Department depart;

    /** 发票号 */
    private String invoiceCode;

    /** 交费类型 */
    private FeeType type;

    /** 收费方式 */
    private FeeMode mode;

    /** 应缴费用（单位：人民币） */
    private Float shouldPay;

    /** 货币类别 */
    private CurrencyCategory currencyCategory;

    /** 汇率 */
    private Float rate;

    /** 实收金额(单位是货币类别) */
    private Float payed;

    /** 折合成人民币 */
    private Float toRMB;

    /** 添加收费时间 */
    private Date createAt;

    /** 修改收费时间 */
    private Date modifyAt;

    /** 学年度学期 */
    private TeachCalendar calendar;

    /** 收费人 */
    private String whoAdded;

    /** 修改人 */
    private String whoModified;

    /** 备注 */
    private String remark;

    public TeachCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date chargeAt) {
        this.createAt = chargeAt;
    }

    public CurrencyCategory getCurrencyCategory() {
        return currencyCategory;
    }

    public void setCurrencyCategory(CurrencyCategory currencyCategory) {
        this.currencyCategory = currencyCategory;
    }

    public Department getDepart() {
        return depart;
    }

    public void setDepart(Department depart) {
        this.depart = depart;
    }

    public String getHSKGrade() {
        return HSKGrade;
    }

    public void setHSKGrade(String grade) {
        HSKGrade = grade;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public Float getPayed() {
        return payed;
    }

    public void setPayed(Float payed) {
        this.payed = payed;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Float getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(Float shouldPay) {
        this.shouldPay = shouldPay;
    }

    public Student getStd() {
        return std;
    }

    public void setStd(Student std) {
        this.std = std;
    }

    public Float getToRMB() {
        return toRMB;
    }

    public void setToRMB(Float toRMB) {
        this.toRMB = toRMB;
    }

    public FeeType getType() {
        return type;
    }

    public void setType(FeeType type) {
        this.type = type;
    }

    public String getWhoAdded() {
        return whoAdded;
    }

    public void setWhoAdded(String whoCharge) {
        this.whoAdded = whoCharge;
    }

    public FeeMode getMode() {
        return mode;
    }

    public void setMode(FeeMode mode) {
        this.mode = mode;
    }

    public String getWhoModified() {
        return whoModified;
    }

    public void setWhoModified(String whoModified) {
        this.whoModified = whoModified;
    }

}

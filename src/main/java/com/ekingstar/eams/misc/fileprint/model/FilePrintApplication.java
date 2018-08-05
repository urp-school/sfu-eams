package com.ekingstar.eams.misc.fileprint.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.security.User;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;

public class FilePrintApplication extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5603632293759417842L;

	/** 申请人 */
	private User applyBy;
	
	/** 学年度学期 */
    private TeachCalendar calendar;
    
    /** 请印单编号 */
    private String applicationCode;
    
    /** 请印部门 */
    private Department depart;
    
    /** 请印文件名 */
    private String fileName;
    
    /** 文档路径 */
	private String filePath;
    
    /** 审核人员 */
    private User auditBy;

    /** 经办人 */
    private User managerBy;
    
    /** 添加时间 */
    private Date addAt;
    
    /** 审核时间 */
    private Date auditAt;

    /** 请印时间 */
    private Date printAt;
    
    /** 复印时间 */
    private Date copyAt;
    
    /** 审核状态 */
    private Boolean auditState;
    
    /** 请印状态 */
    private Boolean filePrintState;
    
    /** 请印耗材 */
	private Set materials = new HashSet();
    
    /** 收费方式 *//*
    private FeeMode mode;
    
    *//** 货币类别 *//*
    private CurrencyCategory currencyCategory;*/
    
    /** 请印数量 */
	private Integer value;
	
    /** 总费用金额(单位是货币类别) */
    private Float payed;
    
    /** 备注 */
	private String remark;

	public Date getAddAt() {
		return addAt;
	}

	public void setAddAt(Date addAt) {
		this.addAt = addAt;
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public Date getCopyAt() {
		return copyAt;
	}

	public void setCopyAt(Date copyAt) {
		this.copyAt = copyAt;
	}

	public Department getDepart() {
		return depart;
	}

	public void setDepart(Department depart) {
		this.depart = depart;
	}

	public Float getPayed() {
		return payed;
	}

	public void setPayed(Float payed) {
		this.payed = payed;
	}

	public Date getPrintAt() {
		return printAt;
	}

	public void setPrintAt(Date printAt) {
		this.printAt = printAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getFilePrintState() {
		return filePrintState;
	}

	public void setFilePrintState(Boolean filePrintState) {
		this.filePrintState = filePrintState;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Date getAuditAt() {
		return auditAt;
	}

	public void setAuditAt(Date auditAt) {
		this.auditAt = auditAt;
	}

	public Set getMaterials() {
		return materials;
	}

	public void setMaterials(Set materials) {
		this.materials = materials;
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public User getApplyBy() {
		return applyBy;
	}

	public void setApplyBy(User applyBy) {
		this.applyBy = applyBy;
	}

	public User getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(User auditBy) {
		this.auditBy = auditBy;
	}

	public User getManagerBy() {
		return managerBy;
	}

	public void setManagerBy(User managerBy) {
		this.managerBy = managerBy;
	}

	public Boolean getAuditState() {
		return auditState;
	}

	public void setAuditState(Boolean auditState) {
		this.auditState = auditState;
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

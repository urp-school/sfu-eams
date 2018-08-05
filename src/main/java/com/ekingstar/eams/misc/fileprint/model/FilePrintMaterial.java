package com.ekingstar.eams.misc.fileprint.model;

import com.ekingstar.commons.model.pojo.LongIdObject;

public class FilePrintMaterial extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2223743122047506714L;

	/** 请印申请 */
    private FilePrintApplication filePrint = new FilePrintApplication();
	
	/** 耗材代码 */
    private String materialCode;
    
    /** 耗材名称 */
    private String materialName;
    
    /** 收费方式 *//*
    private FeeMode mode;
    
    *//** 货币类别 *//*
    private CurrencyCategory currencyCategory;*/
    
    /** 耗材数量 */
	private Integer value;
	
	/** 单价(单位是货币类别) */
    private Float payedOne;
	
    /** 费用金额(单位是货币类别) */
    private Float payed;
    
    /** 备注 */
	private String remark;

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Float getPayed() {
		return payed;
	}

	public void setPayed(Float payed) {
		this.payed = payed;
	}

	public Float getPayedOne() {
		return payedOne;
	}

	public void setPayedOne(Float payedOne) {
		this.payedOne = payedOne;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public FilePrintApplication getFilePrint() {
		return filePrint;
	}

	public void setFilePrint(FilePrintApplication filePrint) {
		this.filePrint = filePrint;
	}
}

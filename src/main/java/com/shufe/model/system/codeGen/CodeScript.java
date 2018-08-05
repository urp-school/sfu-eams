//$Id: CodeScript.java,v 1.1 2007-2-4 上午10:18:57 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-2-4         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.codeGen;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 系统编码规则
 * 
 * @author chaostone
 * 
 */
public class CodeScript extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2771592539431465165L;

	/** 编码对象 */
	private String codeName;

	/** 编码属性 */
	private String attr;

	/** 编码对象的类名 */
	private String codeClassName;

	/** 编码脚本 */
	private String script;

	/** 编码简要描述 */
	private String description;

	/** 创建时间 */
	private Date createAt;

	/** 修改时间 */
	private Date modifyAt;

	public CodeScript() {
		super();
	}

	public CodeScript(String codeClassName, String attr, String script) {
		this.codeClassName = codeClassName;
		this.attr = attr;
		this.script = script;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getCodeClassName() {
		return codeClassName;
	}

	public void setCodeClassName(String codeClassName) {
		this.codeClassName = codeClassName;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getModifyAt() {
		return modifyAt;
	}

	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}

}

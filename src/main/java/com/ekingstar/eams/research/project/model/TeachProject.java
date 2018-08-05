package com.ekingstar.eams.research.project.model;

import java.sql.Date;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.TeachProjectState;
import com.ekingstar.eams.system.basecode.industry.TeachProjectType;
import com.ekingstar.security.User;

/**
 * 项目管理 对应数据表是 JXRW_XMGL_T
 * 
 * @author chaostone 2008-5-26
 */
public class TeachProject extends LongIdObject {
    
    private static final long serialVersionUID = -1576806455981989159L;
    
    /**
	 * 项目代码
	 */
    private String code;

	/**
	 * 项目名称
	 */
    private String name;

	/**
	 * 项目描述
	 */
    private String describe;

	/**
	 * 项目状态
	 */
    private TeachProjectState teachProjectState;
	
	/**
	 * 项目类型
	 */
    private TeachProjectType teachProjectType;

	/**
	 * 创建时间
	 */
    private Date createAt;

	/**
	 * 申请人
	 */

    private User petitionBy;
    
    /**
	 * 负责人
	 */

    private String principal;

	/**
	 * 项目建设材料
	 */
    private Set projectDocuments;
	
	/**
	 * 项目团队建设
	 */
    private Set projectMembers;


	public Set getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(Set projectMembers) {
		this.projectMembers = projectMembers;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getPetitionBy() {
		return petitionBy;
	}

	public void setPetitionBy(User petitionBy) {
		this.petitionBy = petitionBy;
	}

	public Set getProjectDocuments() {
		return projectDocuments;
	}

	public void setProjectDocuments(Set projectDocuments) {
		this.projectDocuments = projectDocuments;
	}

	public TeachProjectState getTeachProjectState() {
		return teachProjectState;
	}

	public void setTeachProjectState(TeachProjectState teachProjectState) {
		this.teachProjectState = teachProjectState;
	}

	public TeachProjectType getTeachProjectType() {
		return teachProjectType;
	}

	public void setTeachProjectType(TeachProjectType teachProjectType) {
		this.teachProjectType = teachProjectType;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

}

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
 * chaostone             2006-9-10            Created
 *  
 ********************************************************************************/
package com.shufe.model.system.file;

import java.sql.Date;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.security.User;

/**
 * 系统文档
 * 
 * @author chaostone
 * 
 */
public class Document extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8214332984656765733L;

	String name;

	User uploaded;

	String path;

	Date uploadOn;

	String remark;
	
	/** 有效开始日期 */
    private Date startDate;
    
    /** 有效结束日期 */
    private Date finishDate;
    
    /** 是否置顶 */
	private Boolean isUp;
    
    public Boolean getIsUp() {
        return isUp;
    }
    
    public void setIsUp(Boolean isUp) {
        this.isUp = isUp;
    }

    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getUploadOn() {
		return uploadOn;
	}

	public void setUploadOn(Date uploadOn) {
		this.uploadOn = uploadOn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getUploaded() {
		return uploaded;
	}

	public void setUploaded(User uploaded) {
		this.uploaded = uploaded;
	}

	public String getFileExt(){
		if(StringUtils.isNotEmpty(getPath())){
			return StringUtils.substringAfterLast(getName(),".");
		}else {
			return "";
		}
	}
}

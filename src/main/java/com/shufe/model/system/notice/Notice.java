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

package com.shufe.model.system.notice;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.security.User;

/**
 * 系统公告
 * 
 * @author chaostone
 */
public class Notice extends LongIdObject {

	private static final long serialVersionUID = 8724150029736700L;

	/** 标题 */
	private String title;

	/** 公告内容 */
	private NoticeContent content;

	/** 修改时间 */
	private Date modifyAt;

	/** 发布人 */
	private User publisher;
    
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

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getModifyAt() {
		return modifyAt;
	}

	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

	public NoticeContent getContent() {
		return content;
	}

	public void setContent(NoticeContent content) {
		this.content = content;
	}

}

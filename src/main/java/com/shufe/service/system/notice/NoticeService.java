//$Id: NoticeService.java,v 1.3 2006/10/12 12:05:06 duanth Exp $
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
 * chenweixiong              2006-9-21         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.notice;

import java.util.Collection;
import java.util.List;

import com.shufe.dao.system.notice.NoticeDAO;
import com.shufe.model.std.Student;
import com.shufe.model.system.notice.Notice;
import com.shufe.util.DataRealmLimit;

public interface NoticeService {

	/**
	 * 根据notice查询条件 得到权限内的notice
	 * 
	 * @param notice
	 * @param departSeq
	 * @param stdTypeSeq
	 * @return
	 */
	public Collection getNotices(Notice notice, DataRealmLimit limit, List orderList);

	/**
	 * 根据学生得到该学生的对应的通知
	 * 
	 * @param std
	 * @return
	 */
	public List getStudentNotices(Student std);

	/**
	 * 根据学生得到该教师的对应的通知
	 * 
	 * @param teacher
	 * @return
	 */
	public List getTeacherNotices();

	/**
	 * 根据学生得到该教师的对应的通知
	 * 
	 * @param teacher
	 * @return
	 */
	public List getManagerNotices();

	public void setNoticeDAO(NoticeDAO noticeDAO);
}

//$Id: NoticeServiceImpl.java,v 1.3 2006/10/12 12:05:06 duanth Exp $
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

package com.shufe.service.system.notice.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ekingstar.commons.query.Order;
import com.shufe.dao.system.notice.NoticeDAO;
import com.shufe.model.std.Student;
import com.shufe.model.system.notice.ManagerNotice;
import com.shufe.model.system.notice.Notice;
import com.shufe.model.system.notice.StudentNotice;
import com.shufe.model.system.notice.TeacherNotice;
import com.shufe.service.BasicService;
import com.shufe.service.system.notice.NoticeService;
import com.shufe.util.DataRealmLimit;

public class NoticeServiceImpl extends BasicService implements NoticeService {

	private NoticeDAO noticeDAO;
	
	public Collection getNotices(Notice notice, DataRealmLimit limit, List orderList) {
		return noticeDAO.getNotices(notice, limit, orderList);
	}

	public List getStudentNotices(Student std) {
		StudentNotice notice =new StudentNotice();
		List orderList= new ArrayList();
		orderList.add(new Order("isUp",Order.DESC));
		return noticeDAO.getStudentNotices(notice, std, orderList);
	}

	public List getTeacherNotices() {
		TeacherNotice notice =new TeacherNotice();
		List orderList= new ArrayList();
		orderList.add(new Order("isUp",Order.DESC));
		return noticeDAO.getTeacherNotices(notice,  orderList);
	}

	public List getManagerNotices() {
		ManagerNotice notice =new ManagerNotice();
		List orderList= new ArrayList();
		orderList.add(new Order("isUp",Order.DESC));
		return noticeDAO.getManagerNotices(notice, orderList);
	}

	public void setNoticeDAO(NoticeDAO noticeDAO) {
		this.noticeDAO = noticeDAO;
	}




}

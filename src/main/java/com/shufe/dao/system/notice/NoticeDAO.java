//$Id: NoticeDAO.java,v 1.3 2006/10/12 12:00:34 duanth Exp $
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

package com.shufe.dao.system.notice;

import java.util.Collection;
import java.util.List;

import com.shufe.model.std.Student;
import com.shufe.model.system.notice.Notice;
import com.shufe.util.DataRealmLimit;

public interface NoticeDAO {
	public Collection getNotices(Notice notice, DataRealmLimit limit, List orderList);

	public List getStudentNotices(Notice notice, Student std, List orderList);

	public List getTeacherNotices(Notice notice,  List orderList);

	public List getManagerNotices(Notice notice,  List orderList);
}

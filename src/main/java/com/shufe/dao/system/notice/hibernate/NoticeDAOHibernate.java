//$Id: NoticeDAOHibernate.java,v 1.3 2006/10/12 12:00:34 duanth Exp $
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

package com.shufe.dao.system.notice.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.system.notice.NoticeDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.std.Student;
import com.shufe.model.system.notice.ManagerNotice;
import com.shufe.model.system.notice.Notice;
import com.shufe.model.system.notice.StudentNotice;
import com.shufe.model.system.notice.TeacherNotice;
import com.shufe.util.DataRealmLimit;

public class NoticeDAOHibernate extends BasicHibernateDAO implements NoticeDAO {

	public Collection getNotices(Notice notice, DataRealmLimit limit, List sortList) {
		Criteria criteria = getSession().createCriteria(
				(null == notice) ? Notice.class : notice.getClass());
		List criterions = CriterionUtils.getEntityCriterions(notice);
		CriterionUtils.addCriterionsFor(criteria, criterions);
		addDataRealmLimt(criteria, new String[] { "studentType.id", "depart.id" }, limit);
		addSortListFor(criteria, sortList);
		if (null != limit && null != limit.getPageLimit()) {
			return dynaSearch(criteria, limit.getPageLimit());
		} else
			return criteria.list();
	}

	public List getStudentNotices(Notice notice, Student std, List sortList) {
        EntityQuery entityQuery = new EntityQuery(StudentNotice.class,"notice");
        entityQuery.add(new Condition("current_date()>notice.startDate and current_date()<notice.finishDate or notice.startDate is null or notice.finishDate is null"));
        entityQuery.addOrder(OrderUtils.parser("notice.isUp DESC,notice.modifyAt DESC"));
        entityQuery.join("notice.studentTypes", "stdType");
        entityQuery.join("notice.departs", "department");
        entityQuery.add(new Condition("stdType.id = (:stdTypeId)", std.getType().getId()));
        entityQuery.add(new Condition("department.id = (:departmentId)", std.getDepartment()
                .getId()));
		return (List)utilDao.search(entityQuery);
	}

	public List getTeacherNotices(Notice notice, List sortList) {
        EntityQuery entityQuery = new EntityQuery(TeacherNotice.class,"notice");
        entityQuery.add(new Condition("current_date()>notice.startDate and current_date()<notice.finishDate or notice.startDate is null or notice.finishDate is null"));
        entityQuery.addOrder(OrderUtils.parser("notice.isUp DESC,notice.modifyAt DESC"));
		return (List)utilDao.search(entityQuery);
	}

	public List getManagerNotices(Notice notice, List sortList) {
        EntityQuery entityQuery = new EntityQuery(ManagerNotice.class,"notice");
        entityQuery.add(new Condition("current_date()>notice.startDate and current_date()<notice.finishDate or notice.startDate is null or notice.finishDate is null"));
        entityQuery.addOrder(OrderUtils.parser("notice.isUp DESC,notice.modifyAt DESC"));
        return (List)utilDao.search(entityQuery);
	}

}

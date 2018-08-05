//$Id: ExamTakeDAOHibernate.java,v 1.1 2007-2-15 下午04:07:58 chaostone Exp $
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
 *chaostone      2007-2-15         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.arrange.exam.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.SqlQuery;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.arrange.exam.ExamTakeDAO;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.calendar.TeachCalendar;

public class ExamTakeDAOHibernate extends BasicHibernateDAO implements
		ExamTakeDAO {

	public List statTakeCountWithTurn(TeachCalendar calendar, List examTypes) {
		Long examTypeId = null;
		if (examTypes.size() == 1) {
			examTypeId = ((ExamType) (examTypes.get(0))).getId();
		}
		String queryStr = "select cc,count(xsid) from (select xsid,count(*) cc from pk_jg_t where jxrlid="
				+ calendar.getId();
		if (null != examTypeId) {
			queryStr += " and kslxid =" + examTypeId;
		} else {
			queryStr += " and instr('" + EntityUtils.extractIdSeq(examTypes)
					+ "',','||kslxid||',')>0 ";
		}
		queryStr += " group by xsid) group by cc order by cc";

		return (List) utilDao.search(new SqlQuery(queryStr));
	}

	public List statTakeCountInCourse(TeachCalendar calendar, List examTypes) {
		EntityQuery query = new EntityQuery(ExamTake.class, "take");
		query.add(new Condition("take.calendar.id=" + calendar.getId()));
		Long examTypeId = null;
		if (examTypes.size() == 1) {
			examTypeId = ((ExamType) (examTypes.get(0))).getId();
		}
		if (null != examTypeId) {
			query.add(new Condition("take.examType.id=" + examTypeId));
		} else {
			query.add(new Condition(
					"instr(:examTypeIds,','||take.examType.id||',')>0",
					EntityUtils.extractIdSeq(examTypes)));
		}
		query.setSelect("take.task.course.id,count(*)");
		query.groupBy("take.task.course.id");
		query.addOrder(new Order("count(*) desc"));

		List dd = (List) search(query);
		List takesOfCourse = new ArrayList(dd.size());
		for (Iterator iter = dd.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			takesOfCourse.add(new Object[] {
					get(Course.class, (Long) element[0]), element[1] });
		}
		return takesOfCourse;
	}
}

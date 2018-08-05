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
 * chaostone             2006-11-7            Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.arrange.exam.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.arrange.exam.ExamArrangeDAO;

public class ExamArrangeDAOHibernate extends BasicHibernateDAO implements ExamArrangeDAO {

	public void removeExamArranges(List tasks, ExamType examType) {
		if (tasks.isEmpty())
			return;
		// 清理排考学生名单
		String removeQuery = "";
		if (null == examType || examType.isVO()) {
			removeQuery = "delete from ExamTake  where task in (:tasks)";
		} else {
			if (examType.getId().equals(ExamType.AGAIN) || examType.getId().equals(ExamType.DELAY)
					|| examType.getId().equals(ExamType.DELAY_AGAIN)) {
				removeQuery = "update  ExamTake  set activity =null where id in(select id from ExamTake where task in (:tasks) "
						+ " and activity.examType =:examType)";
			} else {
				removeQuery = "delete from ExamTake where id in(select id from ExamTake where task in (:tasks) "
						+ " and activity.examType =:examType)";
			}
		}
		Query query0 = getSession().createQuery(removeQuery);
		if (examType.isPO())
			query0.setParameter("examType", examType);
		query0.setParameterList("tasks", tasks);
		query0.executeUpdate();

		// 清空监考信息
		String updateQuery = "update ExamActivity set examMonitor=null where  task in (:tasks) ";
		if (ValidEntityPredicate.INSTANCE.evaluate(examType)) {
			updateQuery += " and examType =:examType ";
		}
		query0 = getSession().createQuery(updateQuery);
		if (ValidEntityPredicate.INSTANCE.evaluate(examType))
			query0.setParameter("examType", examType);
		query0.setParameterList("tasks", tasks);
		query0.executeUpdate();

		// 删除监考信息
		if (ValidEntityPredicate.INSTANCE.evaluate(examType)) {
			removeQuery = "delete from ExamMonitor where id in("
					+ "select id from ExamMonitor as monitor  "
					+ "where monitor.examActivity.task in (:tasks)"
					+ "and monitor.examActivity.examType=:examType)";
		} else {
			removeQuery = "delete from ExamMonitor where id in("
					+ "select id from ExamMonitor as monitor  "
					+ "where monitor.examActivity.task in (:tasks))";
		}
		query0 = getSession().createQuery(removeQuery);
		if (ValidEntityPredicate.INSTANCE.evaluate(examType))
			query0.setParameter("examType", examType);
		query0.setParameterList("tasks", tasks);
		query0.executeUpdate();

		// 删除排考活动
		if (ValidEntityPredicate.INSTANCE.evaluate(examType)) {
			removeQuery = "delete from ExamActivity where id in("
					+ "select id from ExamActivity as ae where  ae.task in (:tasks)"
					+ "and ae.examType=:examType)";
		} else {
			removeQuery = "delete from ExamActivity where id in(" + "select id from ExamActivity  "
					+ "where task in (:tasks))";
		}
		Query query1 = getSession().createQuery(removeQuery);
		query1.setParameterList("tasks", tasks);
		if (examType.isPO())
			query1.setParameter("examType", examType);

		query1.executeUpdate();
		return;
	}

}

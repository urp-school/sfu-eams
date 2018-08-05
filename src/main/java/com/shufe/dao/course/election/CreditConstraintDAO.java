//$Id: CreditConstraintDAO.java,v 1.10 2006/12/20 05:48:50 duanth Exp $
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
 * chaostone             2005-12-11         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.election;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.shufe.dao.BasicDAO;
import com.shufe.model.course.election.CreditConstraint;
import com.shufe.model.course.election.CreditInitParams;
import com.shufe.model.course.election.SpecialityCreditConstraint;
import com.shufe.model.course.election.StdCreditConstraint;
import com.shufe.model.course.task.TeachCommon;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.util.DataRealmLimit;

public interface CreditConstraintDAO extends BasicDAO {
	/**
	 * 查询单个学分限制
	 * 
	 * @param id
	 * @param constraintClass
	 * @return
	 */
	public CreditConstraint getCreditConstraint(Serializable id,
			Class constraintClass);

	/**
	 * 查询指定id串的学分限制
	 * 
	 * @param ids
	 * @param constraintClass
	 * @return
	 */
	public List getCreditConstraints(Serializable[] ids, Class constraintClass);

	/**
	 * 通过专业学分限制查找对应的学生选课学分限制
	 * 
	 * @param specialityCreditConstraintIds
	 * @return
	 */
	public List getStdCreditConstraintsBySpeciality(
			Serializable[] specialityCreditConstraintIds);

	/**
	 * 查询专业选课学分限制
	 * 
	 * @param creditConstrant
	 * @return
	 */
	public List getSpecialityCreditConstraints(
			SpecialityCreditConstraint creditConstrant);

	/**
	 * 查询专业选课学分限制
	 * 
	 * @param common
	 * @return
	 */
	public List getSpecialityCreditConstraints(TeachCommon common);

	/**
	 * 查询学生个人选课学分限制
	 * 
	 * @param creditConstrant
	 * @param scope
	 * @param limit
	 * @param orderList
	 * @return
	 */
	public Collection getStdCreditConstraints(List conditions,
			DataRealmLimit limit, List orderList);

	/**
	 * 查询学生个人选课学分限制
	 * 
	 * @param creditConstrant
	 * @return
	 */
	public List getStdCreditConstraints(StdCreditConstraint creditConstrant);

	/**
	 * * 获得单个学生某个学期的学分限制
	 * 
	 * @param stdId
	 * @param calendar
	 * @return
	 */
	public StdCreditConstraint getStdCreditConstraint(Long stdId,
			TeachCalendar calendar);

	/**
	 * 查询学生学分限制
	 * 
	 * @param stdId
	 * @param calendars
	 * @return
	 */
	public List getStdCreditConstraints(Student std, Collection calendars);

	/**
	 * 选课学分初始化<br>
	 * 1.设置专业学分上限credit<br>
	 * 2.设置专业学分下限0<br>
	 * 3.设置该专业下的学生的选分上限credit<br>
	 * 4.设置该专业下的学生的选分下限0<br>
	 * 
	 * @param common
	 * @param credit
	 */
	public void initCreditConstraint(TeachCommon common,
			TeachCalendar calendar, Float credit);

	/**
	 * 重新计算学生的以选学分、奖励学分合本学期的平均绩点
	 * 
	 * @param stdId
	 * @param calendarId
	 * @param preCalendarId
	 */
	public StdCreditConstraint statStdCreditConstraint(Long stdId,
			CreditInitParams params);

	/**
	 * 查询拥有学分限制的学号
	 * 
	 * @param std
	 * @return
	 */
	public List getStdIdHasConstraint(StdCreditConstraint constraint);

	/**
	 * 权限范围内的所有在校学生中,没有学分上限的学生
	 * 
	 * @param std
	 * @param calendar
	 * @param limit
	 * @param orderList
	 * @return
	 */
	public Collection getStdWithoutCredit(Student std, TeachCalendar calendar,
			DataRealmLimit limit, List orderList);

	/**
	 * 添加权限范围内(在校学生),学生的学分上限
	 * 
	 * @param std
	 * @param realm
	 * @param credit
	 */
	public void batchAddCredit(Student std, DataRealm realm,
			CreditConstraint credit);

	/**
	 * 添加学生的学分上限
	 * 
	 * @see #batchAddCredit(Student, DataRealm, CreditConstraint)
	 * @param stdIds
	 * @param credit
	 */
	public void batchAddCredit(Collection stdIds, CreditConstraint credit);
}

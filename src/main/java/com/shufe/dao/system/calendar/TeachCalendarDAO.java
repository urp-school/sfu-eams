//$Id: TeachCalendarDAO.java,v 1.8 2006/12/24 08:57:21 duanth Exp $
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
 * chaostone             2005-9-13         Created
 *  
 ********************************************************************************/

package com.shufe.dao.system.calendar;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.BasicDAO;
import com.shufe.model.system.calendar.OnCampusTime;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学日历数据存取类.
 * 
 * @see <code>com.shufe.model.system.calenar.Calendar</code>
 * @author chaostone 2005-9-14
 */
public interface TeachCalendarDAO extends BasicDAO {
	/**
	 * 依据非业务主键查询对应的教学日历. 若id为空或不存在将抛出异常.
	 * 
	 * @param id
	 * @return
	 */
	public TeachCalendar getTeachCalendar(Long id);

	/**
	 * 依据日历中设置的条件查询
	 * 
	 * @param calendar
	 * @return
	 */
	public List getTeachCalendars(TeachCalendar calendar);

	/**
	 * 查找在指定的日历时间范围内，时间有重叠的"其他"学期(学生类别不同)<br>
	 * (不包括自身)
	 * 
	 * @param calendar
	 * @return
	 */
	public List getTeachCalendarsOfOverlapped(TeachCalendar calendar);

	/**
	 * 获得stdTypeId适用的教学日历，如果没有直接对应的，凡返回上级对象的日历
	 * 
	 * @param stdTypeId
	 * @param year
	 * @param term
	 * @return
	 */
	public TeachCalendar getTeachCalendar(Long stdTypeId, String year, String term);

	/**
	 * 返回学校所有学生类别的当前学期设置
	 * 
	 * @return
	 */
	public List getCurTeachCalendars();

	/**
	 * 依据学生类别的代码查询
	 * 
	 * @param studentTypeID
	 * @return
	 */
	public List getYearsOrderByDistance(Long stdTypeId);

	/**
	 * 依据学生类别和学年度查询对应的学期设置<br>
	 * 如果两者有一为空，或者对应的id没有设置， 或库中没有对应的纪录，将会抛出异常.<br>
	 * 学年度格式xxxx-xxxx;
	 * 
	 * @param sty
	 * @param year
	 * @return
	 */
	public List getTermsOrderByDistance(Long stdTypeId, String year);

	/**
	 * 保存新建的教学日历. 又重复时抛出异常.
	 * 
	 * @param calendar
	 */
	public void saveTeachCalendar(TeachCalendar calendar);

	/**
	 * 更新已有的教学日历.
	 */

	public void updateTeachCalendar(TeachCalendar calendar);

	/**
	 * 查询对应学生类别的当前日历设置
	 * 
	 * @param stdTypeId
	 * @return
	 */
	public TeachCalendar getCurTeachCalendar(Long stdTypeId);

	/**
	 * 查询对应学生类别的距离当前时间最近的日历设置
	 * 
	 * @param stdTypeId
	 * @return
	 */
	public TeachCalendar getNearestCalendar(Long stdTypeId);

	/**
	 * 查询对应学生类别的上个学期日历设置
	 * 
	 * @param stdTypeId
	 * @return
	 */
	public TeachCalendar getPreviousTeachCalendar(Long stdTypeId);

	/**
	 * 查询对应学生类别的下个学期日历设置
	 * 
	 * @param stdTypeId
	 * @return
	 */
	public TeachCalendar getNextTeachCalendar(Long stdTypeId);

	/**
	 * 查询指定学期内仍在校的学生的所在年级
	 * 
	 * @param calendar
	 * @return
	 */
	public List getActiveEnrollTurns(TeachCalendar calendar, Long[] stdTypeIds);

	/**
	 * 删除教学日历，如果不存在则抛出异常.
	 * 
	 * @param id
	 */
	public void removeTeachCalendar(Long id);

	/**
	 * 得到所有学生类型对应的学年学期设置，全校的教学日历
	 * 
	 * @return
	 */
	public List getTeachCalendars();

	/**
	 * 返回指定学生类别串的所有入学学期日历
	 * 
	 * @param stdTypeIds
	 * @return
	 */
	public List getOnCampusTimes(Long[] stdTypeIds);

	/**
	 * 分页返回指定学生类别串的所有入学学期日历
	 * 
	 * @param stdTypeIds
	 * @param limit
	 * @return
	 */
	public Collection getOnCampusTimes(Long[] stdTypeIds, PageLimit limit);

	/**
	 * 返回指定学生类别的入学学期日历
	 * 
	 * @param stdType
	 * @param enrollTurn
	 * @return
	 */
	public OnCampusTime getOnCampusTime(StudentType stdType, String enrollTurn);
	/**
	 * 
	 * @param stdTypeIds
	 * @return
	 */
	public List getStdTypesHasCalendar(Long[] stdTypeIds);

	/**
	 * 计算first到second教学日历之间的学期个数.<br>
	 * first在second之前或等于second则返回正数(>=1)，否则返回负数.<br>
	 * [first,second]之间的学期数.
	 * 
	 * @param first
	 * @param second
	 * @param omitSmallTerm
	 *            计算学期间隔中,是否忽略小学期
	 * @return
	 */
	public Integer getTermsBetween(TeachCalendar first, TeachCalendar second, Boolean omitSmallTerm);

	/**
	 * 查询指定的教学日历中，每一周对应的开始日期和结束日期
	 * 
	 * @param stdTypeId
	 * @param year
	 * @param term
	 * @return
	 */
	public Map getWeekTimeMap(Long stdTypeId, String year, String term);

	/**
	 * 根据学生类别串得到所有的教学日历的 list
	 * 
	 * @param stdTypeIdSeq
	 * @return
	 * @author cwx
	 */
	public List gerCalendarsByStdTypeIdSeq(String stdTypeIdSeq);

	/**
	 * 根据日历方案，查询日历的id和yearTerm.
	 * @param schemeId
	 * @return mapList
	 */
	public List getTeachCalendarNames(Long schemeId);

}
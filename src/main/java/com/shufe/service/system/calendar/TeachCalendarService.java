//$Id: TeachCalendarService.java,v 1.8 2007/01/24 03:25:17 duanth Exp $
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
 * Hc					 2005-9-17	       add getCalendar method	
 *  
 ********************************************************************************/

package com.shufe.service.system.calendar;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.system.calendar.TeachCalendarDAO;
import com.shufe.model.system.calendar.OnCampusTime;
import com.shufe.model.system.calendar.TeachCalendar;

public interface TeachCalendarService {
    
    /**
     * 依据非业务主键查询对应的教学日历. 若id为空或不存在返回null.
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
     * 具有级联查找功能的日历查找
     * 
     * @param calendar
     * @return
     */
    public List getTeachCalendars(StudentType stdType);
    
    /**
     * 查找在指定的日历时间范围内，时间有重叠的其他学期<br>
     * 前置条件：calendar中的id/开始日期和截止日期不能为null;<br>
     * 包含自身.
     * 
     * @param calendar
     * @return
     */
    public List getTeachCalendarsOfOverlapped(TeachCalendar calendar);
    
    /**
     * 更新已有的教学日历，更新空对象将直接返回.
     */
    
    public void updateTeachCalendar(TeachCalendar calendar);
    
    /**
     * 根据学期、学年度、学生类型返回一个Calendar对象
     * 
     * @param stdType
     * @param year
     * @param term
     * 
     * @return
     */
    public TeachCalendar getTeachCalendar(StudentType stdType, String year, String term);
    
    /**
     * 根据学期、学年度、学生类型返回一个Calendar对象<br>
     * 没有直接对应的，凡返回上级对象的日历
     * 
     * @param stdType
     * @param year
     * @param term
     * 
     * @return
     */
    public TeachCalendar getTeachCalendar(Long stdTypeId, String year, String term);
    
    /**
     * 查询对应学生类别的当前日历设置,如果没有对应的日历，则查找父类别的日历信息
     * 
     * @param stdType
     * @return
     */
    public TeachCalendar getCurTeachCalendar(StudentType stdType);
    
    /**
     * 查询对应学生类别的当前日历设置
     * 
     * @param stdTypeId
     * @return
     */
    public TeachCalendar getCurTeachCalendar(Long stdTypeId);
    
    /**
     * 返回学校所有学生类别的当前学期设置
     * 
     * @return
     */
    public List getCurTeachCalendars();
    
    /**
     * 查询对应学生类别的距离当前时间最近的日历设置
     * 
     * @param stdTypeId
     * @return
     */
    public TeachCalendar getNearestCalendar(StudentType stdType);
    
    /**
     * 查询对应学生类别的下个学期日历设置,如果没有对应的日历，则查找父类别的日历信息
     * 
     * @param stdType
     * @return
     */
    public TeachCalendar getNextTeachCalendar(StudentType stdType);
    
    /**
     * 查询对应学生类别的上个学期日历设置
     * 
     * @param stdTypeId
     * @return
     */
    public TeachCalendar getPreviousTeachCalendar(Long stdTypeId);
    
    /**
     * 查询对应学生类别的上个学期日历设置,如果没有对应的日历，则查找父类别的日历信息
     * 
     * @param stdType
     * @return
     */
    public TeachCalendar getPreviousTeachCalendar(StudentType stdType);
    
    /**
     * 查询对应学生类别的下个学期日历设置
     * 
     * @param stdTypeId
     * @return
     */
    public TeachCalendar getNextTeachCalendar(Long stdTypeId);
    
    /**
     * 首先查找是否处于当前学期，如果不处于在查找下个学期<br>
     * 如果没有制定下个学期的教学日历，则查找上个学期的日历<br>
     * 如果没有上个学期，则返回null<br>
     * 在获得指定日历之前，该方法将学生对应的所有教学日历.<br>
     * TODO 以避免stdtype.getTeachCalendars hibernate 抛出集合以已经被擦出的异常.
     * 
     * @param stdType
     * @return
     */
    public TeachCalendar getTeachCalendar(StudentType stdType);
    
    /**
     * 得到所有学生类型对应的学年学期设置，全校的教学日历
     * 
     * @return
     */
    public List getTeachCalendars();
    
    /**
     * 返回指定学生类别的教学日历, 返回的教学日历按照日历的开始时间降序排列
     * 
     * @param stdTypeIdSeq
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Collection getTeachCalendars(String stdTypeIdSeq, PageLimit limit);
    
    /**
     * 查询某一学生类别，从开始到结束学期之间的所有学期（包含首尾）
     * 
     * @param stdTypeId
     * @param startYear
     * @param startTerm
     * @param endYear
     * @param term
     * @return
     */
    public List getTeachCalendars(Long stdTypeId, String startYear, String startTerm,
            String endYear, String endTerm);
    
    /**
     * 查询指定学期内仍在校的学生的所在年级
     * 
     * @param calendar
     * @return
     */
    public List getActiveEnrollTurns(TeachCalendar calendar, String stdTypeIds);
    
    /**
     * 返回指定学生类别的在校时间日历<br>
     * 如果存在则直接返回，否则查找教学日历的最近上级类别的指定所在年级的入学日历.<br>
     * 
     * @param stdType
     * @param enrollTurn
     * @return
     */
    public OnCampusTime getOnCampusTime(StudentType stdType, String enrollTurn);
    
    /**
     * 返回所有该学生类别的所有所在年级在校日历的分页表示
     * 
     * @param stdType
     * @return
     */
    public Collection getOnCampusTimesFor(StudentType stdType, PageLimit limit);
    
    /**
     * 计算first到second教学日历之间的学期数.<br>
     * first在second之前则返回正整数，否则返回1或负整数.<br>
     * [first,second]包含两段的学期数.<br>
     * 如果给出两个教学日历中的学生类别不一致，则返回null<br>
     * 相同教学日历,则返回1<br>
     * 
     * @param first
     * @param second
     * @param omitSmallTerm
     *            计算学期间隔中,是否忽略小学期
     * @return
     */
    public Integer getTermsBetween(TeachCalendar first, TeachCalendar second, Boolean omitSmallTerm);
    
    /**
     * 删除教学日历. 将该教学日历删除，并将该日历的前后日历传接起来
     */
    public void removeTeachCalendar(TeachCalendar calendar);
    
    /**
     * 在preCalendar之后插入一个教学日历<br>
     * 将preCalendar之后的日历插入到现有日历的后面<br>
     * 
     * @param preCalendar
     * @param calendar
     */
    public void saveTeachCalendarWithPrevious(TeachCalendar calendar, TeachCalendar preCalendar);
    
    /**
     * 在postCalendar之前后插入一个教学日历<br>
     * 将preCalendar之前的日历插入到现有日历的前面<br>
     * 
     * @param calendar
     * @param nextCalendar
     */
    public void saveTeachCalendarWithNext(TeachCalendar calendar, TeachCalendar nextCalendar);
    
    /**
     * 检查同学生类别和学年度中的学期设置是否存在日期冲突现象.
     * 
     * @param calendar
     */
    public boolean checkDateCollision(TeachCalendar calendar);
    
    /**
     * 检查是否存在同一学生类别、所在年级的在校时间是否存在
     * 
     * @param time
     * @return
     */
    public boolean checkOnCampusTimeExist(OnCampusTime time);
    
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
     * 根据学生类别id串得到所有的教学日历list
     * 
     * @param stdTypeIdSeq
     * @return
     * @author cwx
     */
    public List getTeachCalendars(String stdTypeIdSeq);
    
    /**
     * 查找某一学类别下没有个性化日历的学生类别
     * 
     * @return 返回结果中包含自身
     */
    public List getCalendarStdTypes(Long stdTypeId);
    
    /**
     * 获取内部的dao
     * 
     * @return
     */
    public TeachCalendarDAO getTeachCalendarDAO();
    
    /**
     * 设置教学日历存取类对象
     * 
     * @param calendarDAO
     */
    public void setTeachCalendarDAO(TeachCalendarDAO calendarDAO);
    
    public TeachCalendar getTeachCalendar(Date currentAt);
}

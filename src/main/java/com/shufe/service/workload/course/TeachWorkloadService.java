//$Id: TeachWorkloadService.java,v 1.17 2007/01/10 06:17:24 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-21         Created
 *  
 ********************************************************************************/

package com.shufe.service.workload.course;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.workload.course.TeachWorkload;
/**
 * 工作量查询，统计服务
 * @author chaostone
 *
 */
public interface TeachWorkloadService {
	
	/**
	 * 根据权限得到对应教师或院系确认教学工作量相关的教学任务Id列表
	 * @param teachWorkload
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @return
	 */
	public List getTeachWorkloadAffirmTaskIds(
			TeachWorkload teachWorkload, String stdTypeIdSeq,
			String departIdSeq,String calendarIdSeq);
	
	/**
	 * 根据学生类别串 得到当前教学日历set
	 * @param stdTypeSeq
	 * @return
	 */
	public Set getCurrenTeachCalendars(String stdTypeSeq);
	
	/**
	 * 根据查询条件得到工作量数据的idlist;
	 * @param teachWorkload
	 * @param departmentSeq
	 * @param stdTypeSeq
	 * @return
	 */
	public List getIdListBycondition(TeachWorkload teachWorkload,
			String departmentSeq, String stdTypeSeq);
	
	
	/**
	 * 关于工作量的确认
	 * @param affirmType
	 * @param teachWorkloadIds
	 */
	public void affirmType(String affirmType,String teachWorkloadIds,boolean b);
	
	/**
	 * 全部确认或者全部取消
	 * @param departmentIds
	 * @param affirmType
	 * @param b
	 */
	public void affirmAll(String departmentIds,String affirmType,boolean b);
	
	/**
	 * @param studentTypeCollect
	 * @return
	 */
	public List getTeachCalendar(Collection studentTypeCollect);

	/**根据当前学期教学日历 得到上个学期的教学日历list
	 * @param currenCalendarList
	 * @return
	 */
	public List getPreviousCalendars(Collection currenCalendarList);
	
	/**
	 * @param stdTypeList
	 * @return
	 */
	public Collection getHistoryCalendarByStdList(Collection stdTypeList);
	/**批量删除这个学期的已经统计好的教学工作量.
	 * @param departmentSeq
	 * @param stdTypeSeq
	 */
	public void batchDeleteTeachWorkload(String departmentSeq,String stdTypeSeq);
	
	/**
	 * @param conditionsSeq
	 */
	public void batchDeleteBySeq(Map conditionsSeq);
	/**
	 * @param taskCollection
	 */
	public void batchDeleteByTaskCollection(Collection taskCollection);
	
	/**
	 * 根据条件得到一个查询list
	 * @param teachWorkload
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @param age1
	 * @param age2
	 * @return
	 */
	public List getStatisticsObjectList(TeachWorkload teachWorkload,
			String stdTypeIdSeq, String departIdSeq, String calendarIdSeq,
			String age1, String age2);
	
	/**
	 *  根据条件得到分页对象
	 * @param teachWorkload
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @param age1
	 * @param age2
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getStatisticsObjectPagi(TeachWorkload teachWorkload,
			String stdTypeIdSeq, String departIdSeq, String calendarIdSeq,
			String age1, String age2,int pageNo,int pageSize);
	
	/**
	 * 统计教学工作量
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @return
	 */
	public Map statTeachWorkload(String stdTypeIdSeq, String departIdSeq, String calendarIdSeq);

    public TeachWorkload buildTeachWorkload(Teacher teacher, TeachTask teachTask, List categorys, List teachModulus);
}


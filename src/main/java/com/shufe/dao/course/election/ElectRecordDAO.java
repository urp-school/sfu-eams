//$Id: ElectRecordDAO.java,v 1.9 2006/12/26 06:27:55 duanth Exp $
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
 * chaostone             2005-12-12         Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.election;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.shufe.dao.BasicDAO;
import com.shufe.model.course.election.ElectRecord;
import com.shufe.model.course.election.ElectState;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;

/**
 * 选课结果存取接口
 * 
 * @author chaostone
 * 
 */
public interface ElectRecordDAO extends BasicDAO {
	/**
	 * 查询学生的选课结果
	 * 
	 * @param record
	 * @return
	 */
	public Pagination getElectRecords(ElectRecord record, int pageNo,
			int pageSize);

	/**
	 * 查询学生的选课结果
	 * 
	 * @param record
	 * @return
	 */
	public List getElectRecords(ElectRecord record);

	/**
	 * 查询某个教学任务中的学生最新的选课信息
	 * 
	 * @param task
	 * @param std
	 * @return
	 */
	public ElectRecord getLatestElectRecord(TeachTask task, Long stdId);

	/**
	 * 删除某个教学任务中的单个学生选课信息
	 * 
	 * @param task
	 * @param std
	 */
	public void removeElectRecord(TeachTask task, Student std);

	/**
	 * 删除某个教学任务中的一批学生选课信息
	 * 
	 * @param task
	 * @param stdIds
	 */
	public void removeElectRecords(TeachTask task, Long[] stdIds);

	/**
	 * 存储一次选课结果
	 * 
	 * <pre>
	 *     影响结果
	 *     1.班级人数加一
	 *     2.学生已选学分加上教学任务学分
	 *     3.增加选课记录,该课的状态设置为&quot;选中&quot;
	 *     4.增加教学班学生记录
	 *     返回代码表示:
	 *     0:表示成功
	 *     -1:未知错误
	 *     -2:超过学分上限
	 *     -3:超过人数上限
	 * </pre>
	 * 
	 * @param task
	 * @param takeType
	 * @param state
	 * @return
	 */
	public int saveElection(TeachTask task, CourseTakeType takeType,
			ElectState state, boolean checkMaxLimit);

	/**
	 * 删除一次选课结果<br>
	 * 
	 * <pre>
	 *    影响结果
	 *    1.班级人数减一
	 *    2.学生已选学分减去教学任务学分
	 *    3.增加退课记录
	 *    4.将选课记录中所有关于该课的状态设置为&quot;未选中&quot;
	 *    返回代码表示:
	 *    0:表示成功
	 *    -1:未知错误
	 *    -2:未选该课
	 *    -3:低于人数下限
	 * </pre>
	 * 
	 * @param task
	 * @param state
	 * @return
	 */
	public int removeElection(TeachTask task, ElectState state);

	/**
	 * 学生对于制定的教学任务的选课次数
	 * 
	 * @param std
	 * @param task
	 * @return
	 */
	public boolean isElected(Student std, TeachTask task, Integer turn);

	/**
	 * 将学生的选课选中状态改为指定的值
	 * 
	 * @param task
	 * @param stds
	 * @param isPitchOn
	 */
	public void updatePitchOn(TeachTask task, Collection stdIds,
			Boolean isPitchOn);

	/**
	 * 查询教学任务在指定轮次中的选课学生id
	 * 
	 * @param task
	 * @param turn null 为所有轮次
	 * @param isPitchOn 允许为null
	 * @return
	 */
	public Set getElectStdIdSet(TeachTask task, Integer turn,
			Boolean isPitchOn);
	/**
	 * 得到任务的最后最大的选课轮次
	 * @param tasks
	 * @return [task.id -> turn]
	 */
	public Map getLastElectTurn(List tasks);
}

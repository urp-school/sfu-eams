//$Id: QuestionnaireStatServiceImpl.java,v 1.1 2007-3-19 上午11:32:04 chaostone Exp $
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
 *chaostone      2007-3-19         Created
 *  
 ********************************************************************************/

package com.shufe.service.quality.evaluate.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.security.User;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.dao.quality.evaluate.QuestionnaireStatDAO;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.quality.evaluate.EvaluateResult;
import com.shufe.model.quality.evaluate.QuestionType;
import com.shufe.model.quality.evaluate.QuestionTypeStat;
import com.shufe.model.quality.evaluate.QuestionnaireStat;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.BasicService;
import com.shufe.service.quality.evaluate.QuestionnaireStatService;
import com.shufe.service.util.stat.StatUtils;

public class QuestionnaireStatServiceImpl extends BasicService implements QuestionnaireStatService {

	private QuestionnaireStatDAO questionnaireStatDAO;

	private TeachTaskDAO teachTaskDAO;

	public void setQuestionnaireStatDAO(QuestionnaireStatDAO questionnaireStatDAO) {
		this.questionnaireStatDAO = questionnaireStatDAO;
	}

	public void statEvaluateResult(String stdTypeIdSeq, String departIdSeq, String calendarIdSeq,
			User user) {
		// 2006_12_8日修改了关于统计前批量删除已经统计过的数据。
		if (beforStat(stdTypeIdSeq, departIdSeq, calendarIdSeq)) {
			int count = batchDeleteQuestionnaireStat(stdTypeIdSeq, departIdSeq, calendarIdSeq);
			info(user.getName() + "before,delete about" + count + "datas");
			Iterator resultIterator = getQuestionTypeStats(stdTypeIdSeq, departIdSeq, calendarIdSeq)
					.iterator();
			List savingEvaluateResults = new ArrayList();
			Long teacherId = new Long(0);
			Long taskId = new Long(0);
			QuestionnaireStat questionnaireStat = null;
			while (resultIterator.hasNext()) {
				Object[] resultsObject = (Object[]) resultIterator.next();
				// 0该教师对应的任务的该问题类型的总得分
				// 0该教师对应的任务的该问题类型的总分数
				// 2对应的 有效票数
				// 3教师
				// 4教学任务
				// 5问题类别
				float totleMark = ((Double) resultsObject[0]).floatValue();
				float totleScore = ((Double) resultsObject[1]).floatValue();
				Integer vaildTicket = (null == resultsObject[2]) ? new Integer(0) : new Integer(
						((Long) resultsObject[2]).intValue());

				Long newTeacherId = (Long) resultsObject[3];
				Long newTaskId = (Long) resultsObject[4];
				TeachTask teachTask = teachTaskDAO.getTeachTask(newTaskId);
				Long questionTypeId = (Long) resultsObject[5];
				// 如果 教师和教学任务不一样的话 新建保存记录 不然就不做
				if (!ObjectUtils.equals(teacherId, newTeacherId) || !taskId.equals(newTaskId)) {
					// 计算上一个对象的总分
					if (null != questionnaireStat) {
						questionnaireStat.calcScore();
					}
					questionnaireStat = new QuestionnaireStat(teachTask,
							newTeacherId == null ? null : new Teacher(newTeacherId), vaildTicket);
					savingEvaluateResults.add(questionnaireStat);
				}
				teacherId = newTeacherId;
				taskId = newTaskId;
				questionnaireStat.addQuestionTypeStat(new QuestionTypeStat(new QuestionType(
						questionTypeId), (totleMark <= 0) ? new Float(0) : new Float(
						(totleScore / totleMark) * 100)));
			}
			if (null != questionnaireStat) {
				questionnaireStat.calcScore();
			}

			//针对课程评教，教师为null的克隆统计对象
			Map resultMap = new HashMap();
			for (Iterator it1 = savingEvaluateResults.iterator(); it1.hasNext();) {
				QuestionnaireStat stat = (QuestionnaireStat) it1.next();
				if (null == stat.getTeacher()) {
					if (Boolean.FALSE
							.equals(stat.getTask().getRequirement().getEvaluateByTeacher())) {
						for (Iterator it2 = stat.getTask().getArrangeInfo().getTeachers()
								.iterator(); it2.hasNext();) {
							Teacher teacher = (Teacher) it2.next();
							QuestionnaireStat cloned = (QuestionnaireStat) stat.clone();
							cloned.setTeacher(teacher);
							resultMap.put(genQuestionnaireStatKey(cloned), cloned);
						}
					}
				} else {
					resultMap.put(genQuestionnaireStatKey(stat), stat);
				}
			}
			
			utilService.saveOrUpdate(resultMap.values());
			info(user.getName() + " has evaluated '" + departIdSeq
					+ "''s studentEvaluate.  StudentTypeId is '" + stdTypeIdSeq
					+ "' teachCalendarIdSeq" + calendarIdSeq + "statistic about "
					+ savingEvaluateResults.size() + " datas");
		}
	}

	private String genQuestionnaireStatKey(QuestionnaireStat stat) {
		return stat.getTaskSeqNo() + "_" + stat.getCalendar().getId() + "_"
				+ ((null == stat.getTeacher()) ? "" : stat.getTeacher().getId().toString());
	}

	/**
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 */
	private boolean beforStat(String stdTypeIdSeq, String departIdSeq, String calendarIdSeq) {
		boolean flag = false;
		EntityQuery entityQuery = new EntityQuery(EvaluateResult.class, "result");
		entityQuery.add(new Condition("result.department.id in(:departIds)", SeqStringUtil
				.transformToLong(departIdSeq)));
		entityQuery.add(new Condition("result.stdType.id in(:stdTypeIds)", SeqStringUtil
				.transformToLong(stdTypeIdSeq)));
		entityQuery.add(new Condition("result.teachCalendar.id in(:calendarIds)", SeqStringUtil
				.transformToLong(calendarIdSeq)));
		entityQuery.setSelect("select count(*)");
		Collection results = utilService.search(entityQuery);
		Long result = (Long) results.iterator().next();
		if (result.intValue() > 0) {
			flag = true;
		}
		return flag;
	}

	public int batchDeleteQuestionnaireStat(String stdTypeIdSeq, String departIdSeq,
			String calendarIdSeq) {
		return questionnaireStatDAO.batchDeleteQuestionnaireStat(stdTypeIdSeq, departIdSeq,
				calendarIdSeq);

	}

	/**
	 * @see com.shufe.service.quality.evaluate.QuestionnaireStatService#getQuestionTypeStats(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List getQuestionTypeStats(String stdTypeIdSeq, String departIdSeq, String calendarIdSeq) {
		return questionnaireStatDAO.getQuestionTypeStats(stdTypeIdSeq, departIdSeq, calendarIdSeq,
				Boolean.TRUE);
	}

	public List getQuestionTypes(String stdTypeIdSeq, String departIdSeq, Collection teachCalendars) {
		return questionnaireStatDAO.getQuestionTypes(stdTypeIdSeq, departIdSeq, teachCalendars);
	}

	/**
	 * 得到各院系的 优良中差的分布 数字图
	 * 
	 * @see com.shufe.service.quality.evaluate.QuestionnaireStatService#getDataByDepartAndRelated(java.lang.String,
	 *      java.lang.String, java.lang.Long, java.util.Map)
	 */
	public Map getDataByDepartAndRelated(String stdTypeIdSeq, String departIdSeq,
			Long calendarIdSeq, Map optionNameMap) {
		List tempList = questionnaireStatDAO.getDataByDepartAndRelated(stdTypeIdSeq, departIdSeq,
				calendarIdSeq);
		float excellentvalue = ((Float) optionNameMap.get("A")).floatValue();
		float bettervalue = ((Float) optionNameMap.get("B")).floatValue();
		float ordinaryvalue = ((Float) optionNameMap.get("C")).floatValue();
		Map returnMap = new HashMap();
		for (Iterator iter = tempList.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			float score = ((Float) element[0]).floatValue();
			Department department = (Department) element[1];
			String keyValue = department.getId().toString();
			if (excellentvalue <= score) {
				keyValue += "-A";
			} else if (bettervalue <= score) {
				keyValue += "-B";
			} else if (ordinaryvalue <= score) {
				keyValue += "-C";
			} else
				keyValue += "-D";
			StatUtils.setValueToMap(keyValue, new Integer(1), "integer", returnMap);
			StatUtils.setValueToMap(department.getId() + "-all", new Integer(1), "integer",
					returnMap);
			keyValue = "0" + keyValue.substring(keyValue.indexOf("-"));
			StatUtils.setValueToMap(keyValue, new Integer(1), "integer", returnMap);
			StatUtils.setValueToMap("0-0", new Integer(1), "integer", returnMap);
		}
		return returnMap;
	}

	/**
	 * 得到饼图的数据收集
	 * 
	 * @see com.shufe.service.quality.evaluate.QuestionnaireStatService#getDatasOfPieChar(java.lang.String,
	 *      java.lang.Long, java.lang.Long, java.util.Map)
	 */
	public Map getDatasOfPieChar(String stdTypeIdSeq, Long departId, Long calendarId,
			Map optionNameMap) {
		List chartPieList = questionnaireStatDAO.getDatasForPieChart(stdTypeIdSeq, departId,
				calendarId);
		float excellentvalue = ((Float) optionNameMap.get("A")).floatValue();
		float bettervalue = ((Float) optionNameMap.get("B")).floatValue();
		float ordinaryvalue = ((Float) optionNameMap.get("C")).floatValue();
		Map returnMap = new HashMap();
		for (Iterator iter = chartPieList.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			float score = ((Float) element[0]).floatValue();
			if (excellentvalue <= score) {
				StatUtils.setValueToMap("A", new Integer(1), "integer", returnMap);
			} else if (bettervalue <= score) {
				StatUtils.setValueToMap("B", new Integer(1), "integer", returnMap);
			} else if (ordinaryvalue <= score) {
				StatUtils.setValueToMap("C", new Integer(1), "integer", returnMap);
			} else
				StatUtils.setValueToMap("D", new Integer(1), "integer", returnMap);
			StatUtils.setValueToMap("all", new Integer(1), "integer", returnMap);
		}
		return returnMap;
	}

	public void setTeachTaskDAO(TeachTaskDAO taskDAO) {
		this.teachTaskDAO = taskDAO;
	}

}

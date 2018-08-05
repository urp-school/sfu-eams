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
 * chaostone             2006-12-11            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.exam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shufe.model.system.baseinfo.Teacher;

/**
 * 监考通知
 * 
 * @author chaostone
 * 
 */
public class MonitorNotice {

	/** 教师姓名（主要用于自定义教师） */
	private String teacherName;

	/** 监考教师 */
	private Teacher teacher;

	/** 监考活动 */
	private List examActivities = new ArrayList();

	/** 监考通知 */
	private String notice;

	public MonitorNotice() {
		super();
	}

	public MonitorNotice(String teacherName) {
		this.teacherName = teacherName;
	}

	public MonitorNotice(Teacher teacher) {
		this.teacher = teacher;

	}

	public List getExamActivities() {
		return examActivities;
	}

	public void setExamActivities(List examActivities) {
		this.examActivities = examActivities;
	}

	public void add(ExamActivity examActivity) {
		if (!examActivities.contains(examActivity)) {
			this.examActivities.add(examActivity);
		}
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getTeacherName() {
		if (null != getTeacher()) {
			return getTeacher().getName();
		} else
			return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * 按照时间和课程进行排序
	 * 
	 */
	public void sortActivities() {
		class ActivityComparator implements Comparator {
			public int compare(Object arg0, Object arg1) {
				ExamActivity activity0 = (ExamActivity) arg0;
				ExamActivity activity1 = (ExamActivity) arg1;
				int rs = activity0.getDate().compareTo(activity1.getDate());
				if (rs == 0) {
					return activity0.getTask().getCourse().compareTo(
							activity1.getTask().getCourse());
				} else
					return rs;
			}
		}
		Collections.sort(getExamActivities(), new ActivityComparator());
	}

	/**
	 * 根据一串排考活动,构建监考通知.<br>
	 * 如果老师是监考老师或者是主考老师.
	 * 
	 * @param examActivities
	 * @param departs
	 *            限定院系权限,主考老师按照任务的开课院系,监考老师按照监考院系
	 * @return
	 */
	public static List builidNotices(List examActivities, Collection departs) {
		Map monitorMap = new HashMap();
		for (Iterator iter = examActivities.iterator(); iter.hasNext();) {
			ExamActivity activity = (ExamActivity) iter.next();
			// 打印主考老师
			if (departs.contains(activity.getTask().getArrangeInfo()
					.getTeachDepart())) {
				if (null != activity.getExamMonitor().getExaminer()) {
					MonitorNotice notice = (MonitorNotice) monitorMap
							.get(activity.getExamMonitor().getExaminer()
									.getId());
					if (null == notice) {
						notice = new MonitorNotice(activity.getExamMonitor()
								.getExaminer());
						monitorMap.put(activity.getExamMonitor().getExaminer()
								.getId(), notice);
					}
					notice.add(activity);
				}
				// 自定主考老师
				if (StringUtils.isNotBlank(activity.getExamMonitor()
						.getExaminerName())) {
					MonitorNotice notice = (MonitorNotice) monitorMap
							.get(activity.getExamMonitor().getExaminerName());
					if (null == notice) {
						notice = new MonitorNotice(activity.getExamMonitor()
								.getExaminerName());
						monitorMap.put(activity.getExamMonitor()
								.getExaminerName(), notice);
					}
					notice.add(activity);
				}
			}
			// 打印监考老师
			if (departs.contains(activity.getExamMonitor().getDepart())) {
				if (null != activity.getExamMonitor().getInvigilator()) {
					MonitorNotice notice = (MonitorNotice) monitorMap
							.get(activity.getExamMonitor().getInvigilator()
									.getId());
					if (null == notice) {
						notice = new MonitorNotice(activity.getExamMonitor()
								.getInvigilator());
						monitorMap.put(activity.getExamMonitor()
								.getInvigilator().getId(), notice);
					}
					notice.add(activity);
				}
				// 自定监考老师
				if (StringUtils.isNotBlank(activity.getExamMonitor()
						.getInvigilatorName())) {
					MonitorNotice notice = (MonitorNotice) monitorMap
							.get(activity.getExamMonitor().getInvigilatorName());
					if (null == notice) {
						notice = new MonitorNotice(activity.getExamMonitor()
								.getInvigilatorName());
						monitorMap.put(activity.getExamMonitor()
								.getInvigilatorName(), notice);
					}
					notice.add(activity);
				}
			}
		}
		List monitors = new ArrayList(monitorMap.values());
		for (Iterator iter = monitors.iterator(); iter.hasNext();) {
			MonitorNotice notice = (MonitorNotice) iter.next();
			notice.sortActivities();
		}
		return monitors;
	}

	public MonitorNotice getMonitorNoticeBy(Map monitorMap, Teacher teacher) {
		MonitorNotice notice = (MonitorNotice) monitorMap.get(teacher.getId());
		return notice;
	}
}

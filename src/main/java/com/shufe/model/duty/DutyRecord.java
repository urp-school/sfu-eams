//$Id: DutyRecord.java,v 1.13 2007/01/26 03:17:42 yd Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-30         Created
 *  
 ********************************************************************************/

package com.shufe.model.duty;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;

/**
 * 学生考勤记录（对应一个学生，一个教学任务的考勤记录，如考勤次数，各状态出勤次数、出勤率、旷课率等）
 */
public class DutyRecord extends LongIdObject implements Cloneable {

	/** 退课修课类别（数据库中无此类别，仅为考勤检测和展示修读类别而添加） */
	public static final CourseTakeType WITHDRAWCOURSETAKETYPE = new CourseTakeType();
	static {
		WITHDRAWCOURSETAKETYPE.setId(new Long(-1));
		WITHDRAWCOURSETAKETYPE.setCode("null");
		WITHDRAWCOURSETAKETYPE.setName("退课");
		WITHDRAWCOURSETAKETYPE.setEngName("Withdraw");
	}

	private static final long serialVersionUID = 2034903220526520783L;

	/** 对应学生 */
	private Student student;

	/** 对应教学任务 */
	private TeachTask teachTask;

	/** 出勤次数 */
	private Integer dutyCount;

	/** 旷课次数 */
	private Integer absenteeismCount;

	/** 迟到次数 */
	private Integer lateCount;

	/** 早退次数 */
	private Integer leaveEarlyCount;

	/** 请假次数 */
	private Integer askedForLeaveCount;

	/** 记考勤次数 */
	private Integer totalCount;

	/** 出勤率 */
	private Float dutyRatio;

	/** 旷课率 */
	private Float absenteeismRatio;

	/** 记录考勤详细信息 */
	private Set recordDetailSet = new HashSet(0);

	/**
	 * @param recordDetail
	 */
	public void addRecordDetail(RecordDetail recordDetail) {
		recordDetailSet.add(recordDetail);
	}

	/**
	 * @param recordDetail
	 */
	public void removeRecordDetail(RecordDetail recordDetail) {
		recordDetailSet.remove(recordDetail);
	}

	/**
	 * @return Returns the recordDetailSet.
	 */
	public Set getRecordDetailSet() {
		return recordDetailSet;
	}

	/**
	 * @param recordDetailSet
	 *            The recordDetailSet to set.
	 */
	public void setRecordDetailSet(Set recordDetailSet) {
		this.recordDetailSet = recordDetailSet;
	}

	/**
	 * @return Returns the dutyCount.
	 */
	public Integer getDutyCount() {
		return dutyCount;
	}

	/**
	 * @param dutyCount
	 *            The dutyCount to set.
	 */
	public void setDutyCount(Integer dutyCount) {
		if (dutyCount != null && dutyCount.intValue() < 0) {
			throw new RuntimeException("出勤次数为空或负数");
		} else {
			this.dutyCount = dutyCount;
		}
	}

	/**
	 * 获取出勤率
	 * 
	 * @return Returns the dutyRatio.
	 */
	public Float getDutyRatio() {
		if (new Integer(0).compareTo(totalCount) < 0) {
			return new Float((this.getDutyCount(new Boolean(true)) == null ? 0f : this
					.getDutyCount(new Boolean(true)).floatValue())
					/ totalCount.floatValue());
		} else {
			return new Float(0);
		}
	}

	/**
	 * @param dutyRatio
	 *            The dutyRatio to set.
	 */
	public void setDutyRatio(Float dutyRatio) {
		this.dutyRatio = dutyRatio;
	}

	/**
	 * @return Returns the student.
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            The student to set.
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return Returns the teachTask.
	 */
	public TeachTask getTeachTask() {
		return teachTask;
	}

	/**
	 * @param teachTask
	 *            The teachTask to set.
	 */
	public void setTeachTask(TeachTask teachTask) {
		this.teachTask = teachTask;
	}

	/**
	 * @return Returns the totalCount.
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            The totalCount to set.
	 */
	public void setTotalCount(Integer totalCount) {
		if (totalCount != null && totalCount.intValue() < 0) {
			throw new RuntimeException("考勤次数为负数");
		} else {
			this.totalCount = totalCount;
		}
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * @return 返回 absenteeismCount.
	 */
	public Integer getAbsenteeismCount() {
		return absenteeismCount;
	}

	/**
	 * @param absenteeismCount
	 *            要设置的 absenteeismCount.
	 */
	public void setAbsenteeismCount(Integer absenteeismCount) {
		if (absenteeismCount != null && absenteeismCount.intValue() < 0) {
			throw new RuntimeException("旷课次数为负数");
		} else {
			this.absenteeismCount = absenteeismCount;
		}
	}

	/**
	 * 获取旷课率
	 * 
	 * @return 返回 absenteeismRatio.
	 */
	public Float getAbsenteeismRatio() {
		if (new Integer(0).compareTo(totalCount) < 0) {
			return new Float(((this.getAbsenteeismCount(new Boolean(true)) == null) ? 0f : this
					.getAbsenteeismCount(new Boolean(true)).floatValue())
					/ totalCount.floatValue());
		} else {
			return new Float(0);
		}
	}

	/**
	 * @param absenteeismRatio
	 *            要设置的 absenteeismRatio.
	 */
	public void setAbsenteeismRatio(Float absenteeismRatio) {
		this.absenteeismRatio = absenteeismRatio;
	}

	/**
	 * @param convert
	 * @return 返回 convertAbsenteeismCount.
	 */
	public Integer getAbsenteeismCount(Boolean convert) {
		return this.absenteeismCount;
	}

	public Integer getDutyCount(Boolean convert) {
		if (Boolean.TRUE.equals(convert)) {
			return new Integer(
					((this.lateCount == null ? 0 : this.lateCount.intValue())
							+ (this.leaveEarlyCount == null ? 0 : this.leaveEarlyCount.intValue()) + (this.dutyCount == null ? 0
							: this.dutyCount.intValue()))
							- ((this.lateCount == null ? 0 : this.lateCount.intValue()) - (this.leaveEarlyCount == null ? 0
									: this.leaveEarlyCount.intValue()))
							/ Integer.parseInt(SystemConfigLoader.getConfig().getConfigItemValue("system.dutyRuleInf").toString()));
		} else {
			return this.dutyCount;
		}
	}

	public void setPresence(Integer presence) {
		this.dutyCount = presence;
	}

	public Integer getPresence() {
		return this.dutyCount;
	}

	/**
	 * @return 返回 askedForLeaveCount.
	 */
	public Integer getAskedForLeaveCount() {
		return askedForLeaveCount;
	}

	/**
	 * @param askedForLeaveCount
	 *            要设置的 askedForLeaveCount.
	 */
	public void setAskedForLeaveCount(Integer askedForLeaveCount) {
		if (askedForLeaveCount != null && askedForLeaveCount.intValue() < 0) {
			throw new RuntimeException("请假次数为负数");
		} else {
			this.askedForLeaveCount = askedForLeaveCount;
		}
	}

	/**
	 * @return 返回 lateCount.
	 */
	public Integer getLateCount() {
		return lateCount;
	}

	/**
	 * @param lateCount
	 *            要设置的 lateCount.
	 */
	public void setLateCount(Integer lateCount) {
		if (lateCount != null && lateCount.intValue() < 0) {
			throw new RuntimeException("迟到次数为负数");
		} else {
			this.lateCount = lateCount;
		}
	}

	/**
	 * @return 返回 leaveEarlyCount.
	 */
	public Integer getLeaveEarlyCount() {
		return leaveEarlyCount;
	}

	/**
	 * @param leaveEarlyCount
	 *            要设置的 leaveEarlyCount.
	 */
	public void setLeaveEarlyCount(Integer leaveEarlyCount) {
		if (leaveEarlyCount != null && leaveEarlyCount.intValue() < 0) {
			throw new RuntimeException("早退次数为负数");
		} else {
			this.leaveEarlyCount = leaveEarlyCount;
		}
	}

	public CourseTakeType getCourseTakeType() {
		return getCourseTakeType(Boolean.FALSE);
	}

	/**
	 * 获得修读类别{@link CourseTakeType}
	 * 
	 * @param isReturnNull
	 *            返回null或空对象
	 * @return 修读类别{@link CourseTakeType}
	 */
	public CourseTakeType getCourseTakeType(Boolean isReturnNull) {
		CourseTake courseTake = this.teachTask.getTeachClass().getCourseTake(student);
		if (courseTake == null) {
			if (Boolean.TRUE.equals(isReturnNull)) {
				return null;
			} else {
				return WITHDRAWCOURSETAKETYPE;
			}
		} else {
			return this.teachTask.getTeachClass().getCourseTake(student).getCourseTakeType();
		}
	}

	/**
	 * 是否在所给修读类别范围内
	 * 
	 * @param typeIds
	 *            修读类别Id的串
	 * @return
	 */
	public boolean isCourseTakeTypeIn(String typeIds) {
		Long[] typeIdArray = SeqStringUtil.transformToLong(typeIds);
		if (ArrayUtils.isEmpty(typeIdArray)) {
			return false;
		}
		Long typeId = getCourseTakeType().getId();
		for (int i = 0; i < typeIdArray.length; i++) {
			Long element = typeIdArray[i];
			if (element.equals(typeId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得指定时间段内的考勤记录
	 * 
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public List getRecordDetailList(final Date dateBegin, final Date dateEnd) {
		return (List) CollectionUtils.select(this.getRecordDetailSet(), new Predicate() {

			public boolean evaluate(Object object) {
				RecordDetail recordDetail = (RecordDetail) object;
				int i = (dateBegin == null) ? 1 : recordDetail.getDutyDate().compareTo(dateBegin);
				int j = (dateEnd == null) ? -1 : recordDetail.getDutyDate().compareTo(dateEnd);
				if (i >= 0 && j <= 0) {
					return true;
				} else {
					return false;
				}
			}
		});
	}
}

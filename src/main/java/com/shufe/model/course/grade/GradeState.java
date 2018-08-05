package com.shufe.model.course.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.shufe.model.course.task.TeachTask;

/**
 * 成绩状态表<br>
 * 记录了对应教学任务成绩<br>
 * 1)记录方式,<br>
 * 2)各种成绩成分的百分比,<br>
 * 3)各种成绩的确认状态,<br>
 * 4)各种成绩的发布状态<br>
 * 每种成绩在状态的站位参见GradeType.mark
 * 
 * @author 塞外狂人,chaostone
 * 
 */
public class GradeState extends LongIdObject implements
		com.ekingstar.eams.course.grade.course.MoralGradeState {

	private static final long serialVersionUID = 3297067284042522108L;

	public static final Integer INIT = new Integer(0);

	/**
	 * 教学任务
	 */
	private TeachTask teachTask;

	/**
	 * 成绩记录方式
	 */
	private MarkStyle markStyle = new MarkStyle();

	/**
	 * 百分比描述
	 */
	private String percents;

	/**
	 * 成绩录入确认状态
	 */
	private Integer confirmStatus;

	/**
	 * 成绩录入状态
	 */
	private Integer inputStatus;

	/**
	 * 成绩发布状态
	 */
	private Integer publishStatus;

	/**
	 * 百分比的关联数组映射[gradeType,percent](不需要持久化)
	 */
	private transient Map percentMap;

	/**
	 * 小数点后保留几位
	 */
	private Integer precision;

	/**
	 * 德育成绩百分比
	 */
	private Float moralGradePercent;

	public GradeState() {
	}

	public GradeState(TeachTask teachTask) {
		this.teachTask = teachTask;
		this.confirmStatus = INIT;
		this.inputStatus = INIT;
		this.publishStatus = INIT;
		markStyle.setId(MarkStyle.PERCENT);
		precision = new Integer(0);
	}

	public void addPublish(int mark) {
		int status = (null == getPublishStatus()) ? 0 : getPublishStatus().intValue();
		status |= mark;
		setPublishStatus(new Integer(status));
	}

	public void addConfirm(int mark) {
		int status = (null == getConfirmStatus()) ? 0 : getConfirmStatus().intValue();
		status |= mark;
		setConfirmStatus(new Integer(status));
	}

	public void addInput(int mark) {
		int status = (null == getInputStatus()) ? 0 : getInputStatus().intValue();
		status |= mark;
		setInputStatus(new Integer(status));
	}

	public Integer getConfirmStatus() {
		return confirmStatus;
	}

	public List getGradeTypeInPercent() {
		toPercentMap();
		return new ArrayList(percentMap.keySet());
	}

	public Integer getInputStatus() {
		return inputStatus;
	}

	public MarkStyle getMarkStyle() {
		return markStyle;
	}

	public Float getPercent(GradeType gradeType) {
		toPercentMap();
		return (Float) percentMap.get(gradeType);
	}

	public Map getPercentMap() {
		toPercentMap();
		return percentMap;
	}

	public String getPercents() {
		return percents;
	}

	public Integer getPrecision() {
		return precision;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public TeachTask getTeachTask() {
		return teachTask;
	}

	/**
	 * 是否确认
	 * 
	 * @param gradeType
	 * @return
	 */
	public boolean isConfirmed(GradeType gradeType) {
		if (null != getConfirmStatus()) {
			return (getConfirmStatus().intValue() & gradeType.getMark().intValue()) > 0;
		} else {
			return false;
		}
	}

	/**
	 * 是否录入
	 * 
	 * @param gradeType
	 * @return
	 */
	public boolean isInput(GradeType gradeType) {
		if (null != getInputStatus()) {
			return (getInputStatus().intValue() & gradeType.getMark().intValue()) > 0;
		} else {
			return false;
		}
	}

	/**
	 * 是否发布
	 * 
	 * @param gradeType
	 * @return
	 */
	public boolean isPublished(GradeType gradeType) {
		if (null != getPublishStatus()) {
			return (getPublishStatus().intValue() & gradeType.getMark().intValue()) > 0;
		} else {
			return false;
		}
	}

	/**
	 * 将成绩的确认状态位置为false
	 * 
	 * @param mark
	 */
	public void removeConfirm(int mark) {
		int status = (null == getConfirmStatus()) ? 0 : getConfirmStatus().intValue();
		if ((status & mark) > 0) {
			status ^= mark;
			setConfirmStatus(new Integer(status));
		}
	}

	/**
	 * 将成绩的录入状态位置为false
	 * 
	 * @param mark
	 */
	public void removeInput(int mark) {
		int status = (null == getInputStatus()) ? 0 : getInputStatus().intValue();
		if ((status & mark) > 0) {
			status ^= mark;
			setInputStatus(new Integer(status));
		}
	}

	/**
	 * 将成绩的发布状态位置为false
	 * 
	 * @param mark
	 */
	public void removePublish(int mark) {
		int status = (null == getPublishStatus()) ? 0 : getPublishStatus().intValue();
		if ((status & mark) > 0) {
			status ^= mark;
			setPublishStatus(new Integer(status));
		}
	}

	public void setConfirmStatus(Integer inputStatus) {
		this.confirmStatus = inputStatus;
	}

	public void setInputStatus(Integer inputStatus) {
		this.inputStatus = inputStatus;
	}

	public void setMarkStyle(MarkStyle markStyle) {
		this.markStyle = markStyle;
	}

	/**
	 * 设置百分比的同时,讲其映射map设置为null
	 * 
	 * @param percents
	 */
	public void setPercents(String percents) {
		this.percents = percents;
		this.percentMap = null;
	}

	public void setPrecision(Integer percision) {
		this.precision = percision;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public void setTeachTask(TeachTask teachTask) {
		this.teachTask = teachTask;
	}

	public Float getMoralGradePercent() {
		return moralGradePercent;
	}

	public void setMoralGradePercent(Float moralGradePercent) {
		this.moralGradePercent = moralGradePercent;
	}

	/**
	 * 将百分比描述转化为map[gradeType,percent]
	 * 
	 */
	private void toPercentMap() {
		if (null == percentMap) {
			percentMap = new HashMap();
			if (!StringUtils.isEmpty(getPercents())) {
				String[] percentStrs = StringUtils.split(getPercents(), ";");
				for (int i = 0; i < percentStrs.length; i++) {
					String percentStr = percentStrs[i];
					String[] gradeTypeIdAndPercent = StringUtils.split(percentStr, "=");
					percentMap.put(new GradeType(Long.valueOf(gradeTypeIdAndPercent[0])), Float
							.valueOf(gradeTypeIdAndPercent[1]));
				}
			}
		}
	}

}

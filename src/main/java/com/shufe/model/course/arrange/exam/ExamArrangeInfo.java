package com.shufe.model.course.arrange.exam;

import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.ExamMode;

/**
 * 考试安排
 * @author chaostone
 *
 */
public class ExamArrangeInfo extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8657880756173823301L;
	/**
	 * 排考课程组
	 */
	private Set examGroups;
	/**
	 * 具体排考结果
	 */
	private Set examActivities;
	/**
	 * 考试方式
	 */
	private ExamMode examMode;
}

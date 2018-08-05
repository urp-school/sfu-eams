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
 * chaostone             2006-6-3            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.election.impl;

import com.shufe.model.course.task.TeachTask;
import com.shufe.service.course.election.StdCountLimitedPolicy;

/**
 * 简单的设置任务的选课人数上下限<br>
 * 需要指定要设置得是否为上限，以及限定的数量值<br>
 * 
 * @author chaostone
 * 
 */
public class SingleLimitedPolicy implements StdCountLimitedPolicy {

	private Integer stdCountLimited;

	private Boolean isMaxLimited;

	public void updateStdCountLimited(TeachTask task) {
		if (isMaxLimited.equals(Boolean.TRUE)) {
			task.getElectInfo().setMaxStdCount(stdCountLimited);
		} else {
			task.getElectInfo().setMinStdCount(stdCountLimited);
		}
	}

	public SingleLimitedPolicy() {
		super();
	}

	public SingleLimitedPolicy(Integer stdCountLimited, Boolean isMaxLimited) {
		if (stdCountLimited.intValue() < 0)
			throw new IllegalArgumentException(
					"stdCountLimited should great than one! but it's "
							+ stdCountLimited);
		this.stdCountLimited = stdCountLimited;
		this.isMaxLimited = isMaxLimited;
	}

	/**
	 * @return Returns the isMaxLimited.
	 */
	public Boolean getIsMaxLimited() {
		return isMaxLimited;
	}

	/**
	 * @param isMaxLimited
	 *            The isMaxLimited to set.
	 */
	public void setIsMaxLimited(Boolean isMaxLimited) {
		this.isMaxLimited = isMaxLimited;
	}

	/**
	 * @return Returns the stdCountLimited.
	 */
	public Integer getStdCountLimited() {
		return stdCountLimited;
	}

	/**
	 * @param stdCountLimited
	 *            The stdCountLimited to set.
	 */
	public void setStdCountLimited(Integer stdCountLimited) {
		this.stdCountLimited = stdCountLimited;
	}

}

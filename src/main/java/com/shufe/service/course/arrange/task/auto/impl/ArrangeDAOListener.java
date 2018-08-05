//$Id: ArrangeDAOListener.java,v 1.2 2006/12/06 03:37:25 duanth Exp $
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
 * chaostone             2005-11-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto.impl;

import com.shufe.dao.course.arrange.task.CourseActivityDAO;
import com.shufe.service.course.arrange.task.auto.Arrange;
import com.shufe.service.course.arrange.task.auto.ArrangeListener;
import com.shufe.service.course.arrange.task.auto.GeneralArrangeFailure;

public class ArrangeDAOListener implements ArrangeListener {

	private CourseActivityDAO courseActivityDAO;

	/**
	 * @see com.shufe.service.course.arrange.task.auto.ArrangeListener#addFailure(com.shufe.service.course.arrange.task.auto.Arrange,
	 *      com.shufe.service.course.arrange.task.auto.GeneralArrangeFailure)
	 */
	public void addFailure(Arrange arrange, GeneralArrangeFailure g) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.ArrangeListener#endArrange(com.shufe.service.course.arrange.task.auto.Arrange)
	 */
	public void endArrange(Arrange arrange) {
		courseActivityDAO.saveActivities(arrange.getTasks());
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.ArrangeListener#startArrange(com.shufe.service.course.arrange.task.auto.Arrange)
	 */
	public void startArrange(Arrange arrange) {
	}

	public void notify(String msg) {
	}

	/**
	 * @param courseActivityDAO
	 *            The courseActivityDAO to set.
	 */
	public void setCourseActivityDAO(CourseActivityDAO courseActivityDAO) {
		this.courseActivityDAO = courseActivityDAO;
	}

	/**
	 * 
	 */
	public void notifyEnd() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.course.arrange.ArrangeListener#notifyStart()
	 */
	public void notifyStart() {
		// TODO Auto-generated method stub

	}

}

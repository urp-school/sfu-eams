//$Id: TimeAllocator.java,v 1.2 2006/11/11 08:13:31 duanth Exp $
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
 * chaostone             2005-11-8         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.task.ArrangeParams;

/**
 * A<code>TimeAllocator</code>allocate TimeUnit for a <code>Arrange</code>
 * 
 * @see Arrange
 * @see TimeUnit
 * @author chaostone
 * 
 */
public interface TimeAllocator {

	/**
	 * Allocate TimeUnit for Arrange.Every invokation return diffrent result.<br>
	 * Arrange.getUnitOccupy() determine whether has time to allocate.<br>
	 * It records use history of every allocation.the return value
	 * <code>AvailableTime</code><br>
	 * of Arrange.getAvailableTime() indicate priority of every available unit.<br>
	 * 0 represent not available.
	 * 
	 * Allcation may detect available of teacher and adminClasses in a arrange.<br>
	 * First, allcator will choose highest available time according to
	 * arrange.getAvailableTime()<br>
	 * and params.getDensity() which represent in how many units we arrange one
	 * <code>Activity</code><br>
	 * 
	 * Then detecct teacher and adminClasses's availablity if the<br>
	 * params.isTeacherTimeCollisionDetecte()<br>
	 * and params.isAdminClassTimeCollisionDetecte() was setted. <br>
	 * 
	 * <pre>
	 *  precondition
	 *   1)arrange.getArrangeInfo()
	 *   2)arrange.getArrangeInfo().getWeekUnits() not null
	 *   4)arrange.getArrangeInfo.getWeekCycle() not null
	 *   5)arrange.getArrangeInfo.getCourseUnits()
	 *   6)arrange.getUnitOccupy() not null，and length equal  84
	 *   7)arrange.getAvailableTime.availablenot null，and length equal  84
	 *   8)this.getTimeParams.getDensity()not null
	 *   9)arrange.getCalendar().getYear()not null
	 *   postcondition
	 *   1)if allocate successful,change unitOccupy in arrange            
	 * </pre>
	 * 
	 * @see Arrange#getAvailUnitBitMap()
	 * @see Arrange#getAvailableTime()
	 * @see ArrangeParams#isTeacherTimeCollisionDetecte()
	 * @see ArrangeParams#isAdminClassTimeCollisionDetecte()
	 * 
	 * @param arrange
	 * @return If not every attempt success return the semiSuccessful result.
	 */
	public TimeUnit[] allocTimes(Arrange arrange);

	/**
	 * 
	 * Similar with allocTimes function.The diffrence is just allocate one
	 * TimeUnit.<br>
	 * precondition and postcondition is same to allocTimes function.<br>
	 * 
	 * @see ArrangeParams#isTeacherTimeCollisionDetecte()
	 * @see ArrangeParams#isAdminClassTimeCollisionDetecte()
	 * @see TimeAllocator#allocTimes(Arrange)
	 * @param arrange
	 * @return <tt>null</tt>, if allaction failure
	 */
	public TimeUnit allocTime(Arrange arrange);

	/**
	 * @return Returns the timeParams.
	 */
	public ArrangeParams getTimeParams();

	/**
	 * @param timeParams
	 *            The timeParams to set.
	 */
	public void setTimeParams(ArrangeParams timeParams);
}

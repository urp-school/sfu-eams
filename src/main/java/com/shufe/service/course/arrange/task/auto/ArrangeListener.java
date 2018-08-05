//$Id: ArrangeListener.java,v 1.2 2006/12/06 03:37:25 duanth Exp $
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

/**
 * 课程安排信息监听接口
 * 
 * @author chaostone
 * 
 */
public interface ArrangeListener {
	public void startArrange(Arrange arrage);

	public void endArrange(Arrange arrange);

	public void addFailure(Arrange arrange, GeneralArrangeFailure g);

	public void notifyStart();

	public void notify(String msg);

	public void notifyEnd();
}

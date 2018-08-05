//$Id: EvaluateValueRemember.java,v 1.1 2006/11/09 11:22:28 duanth Exp $
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
 * chaostone             2005-12-20         Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task.predicate;

/**
 * 验证谓词的能记住，要验证或已经验证的值
 * 
 * @author Water
 * 
 */
public interface EvaluateValueRemember {
	/**
	 * 返回验证的值
	 * @param obj
	 * @return
	 */
	public Object getEvaluateValue(Object obj);
}

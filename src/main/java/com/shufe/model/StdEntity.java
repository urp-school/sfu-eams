//$Id: StdEntity.java,v 1.1 2007-6-27 下午08:38:48 chaostone Exp $
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
 * chenweixiong              2007-6-27         Created
 *  
 ********************************************************************************/

package com.shufe.model;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;

/**
 * 含有学生的基类
 * 
 * @author chaostone
 * 
 */
public class StdEntity extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5192901166922990587L;
	/**
	 * 学生
	 */
	protected Student std;

}

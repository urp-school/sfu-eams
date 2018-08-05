// $Id: Occupy.java,v 1.3 2008/01/28 15:06:30 duanth Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved. This source code is the property of
 * KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is
 * not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2008-1-28         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.course.arrange;

import java.sql.Date;

import com.ekingstar.commons.model.LongIdEntity;
import com.ekingstar.eams.system.time.OccupyTime;
import com.shufe.model.system.baseinfo.Classroom;
/**
 * 教室时间占用
 * @author chaostone
 *
 */
public interface Occupy extends LongIdEntity {

	// 教室/考场/活动场地
	public Classroom getRoom();

	// 时间安排
	public OccupyTime getTime();

	/**
	 * 第一次活动时间<br>
	 * 细致到具体时间
	 * 
	 * @return
	 */
	public Date getFirstTime();
}

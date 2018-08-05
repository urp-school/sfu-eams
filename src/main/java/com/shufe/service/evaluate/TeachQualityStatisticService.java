//$Id: TeachQualityStatisticService.java,v 1.2 2006/12/19 13:08:40 duanth Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-5-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.evaluate;

import java.util.List;
import java.util.Map;

public interface TeachQualityStatisticService {	
	
	/**
	 * 根据学年度学期 统计得到分数段内的优良中差人数
	 * 
	 * @param floatSegments
	 * @param calendars
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param excellentMark TODO
	 * @return
	 */
	public Map statEvaluatePageByNum(List floatSegments, List calendars,
			String stdTypeIdSeq, String departIdSeq, float excellentMark);
}

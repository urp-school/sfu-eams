//$Id: ListUtils.java,v 1.1 2007-5-7 下午05:44:26 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-5-7         Created
 *  
 ********************************************************************************/

package com.shufe.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

	/**
	 * 将一个集合按照固定大小查分成若干个集合。
	 * @param list
	 * @param count
	 * @return
	 */
	public static List split(List list, int count) {
		List subIdLists = new ArrayList();
		if (list.size() < count) {
			subIdLists.add(list);
		} else {
			int i = 0;
			while (i < list.size()) {
				int end = i + count;
				if (end > list.size())
					end = list.size();
				subIdLists.add(list.subList(i, end));
				i += count;
			}
		}
		return subIdLists;
	}
}

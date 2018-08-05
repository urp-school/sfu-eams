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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.service.std;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;

public class AuthorityPredicate implements Predicate {
	
	private Map authorityMap;
	
	private boolean emptyIsTrue;

	public AuthorityPredicate(Map authorityMap, boolean emptyIsTrue) {
		super();
		this.authorityMap = authorityMap;
		this.emptyIsTrue = emptyIsTrue;
	}

	public boolean evaluate(Object object) {
		if(authorityMap==null||authorityMap.size()==0){
			return emptyIsTrue;
		}
		for (Iterator iter = authorityMap.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Object[] valueArray = (Object[]) authorityMap.get(key);
			if(ArrayUtils.isEmpty(valueArray)){
				if(emptyIsTrue){
					continue ;
				}else{
					return false;
				}				
			}
			try{
				Object propertyValue = PropertyUtils.getProperty(object, key);
				if(ArrayUtils.contains(valueArray, propertyValue)){
					continue ;
				}else{
					return false;
				}
			}catch (Exception e) {
				return false;
			}
		}
		return true;
	}

}

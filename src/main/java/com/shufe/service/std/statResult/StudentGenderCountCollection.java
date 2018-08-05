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
package com.shufe.service.std.statResult;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.ekingstar.eams.system.basecode.state.Gender;
public class StudentGenderCountCollection {
	
	private Object key;
	
	private String dataName;
	
	private Integer index;
	
	private Integer sizeShow;
	
	private List data = new ArrayList();
	
	private List dataShow = new ArrayList();
	
	private StudentGenderCount totalCount = new StudentGenderCount();
	
	private Map countShow = new HashMap();

	public StudentGenderCountCollection() {
		super();
	}
	
	public StudentGenderCountCollection(Object key, String dataName, Integer index, List data) {
		this.key = key;
		this.dataName = dataName;
		this.index = index;
		if(data!=null){
			this.data = data;
		}		
	}
	
	public void setTotalCount(StudentGenderCount totalCount) {
		this.totalCount = totalCount;
	}

	public int size() {
		return data.size();
	}

	public void clear() {
		data.clear();
	}

	public boolean isEmpty() {		
		return data.isEmpty();
	}

	public Object[] toArray() {
		return data.toArray();
	}

	public boolean add(Object o) {
		return data.add(o);
	}

	public boolean contains(Object o) {
		return data.contains(o);
	}

	public boolean remove(Object o) {
		return data.remove(o);
	}

	public boolean addAll(Collection c) {
		return data.addAll(c);
	}

	public boolean containsAll(Collection c) {
		return data.containsAll(c);
	}

	public boolean removeAll(Collection c) {
		return data.removeAll(c);
	}

	public boolean retainAll(Collection c) {
		return data.retainAll(c);
	}

	public Iterator iterator() {
		return data.iterator();
	}

	public Object[] toArray(Object[] a) {
		return data.toArray(a);
	}
	
	public StudentGenderCount getTotalCount(){
		if(totalCount.isEmpty()){
			for (Iterator iter = this.data.iterator(); iter.hasNext();) {
				Map element = (Map) iter.next();
				totalCount.add((Gender)element.get("student_basicInfo_gender"),(Number) element.get("count"));
			}
		}
		return totalCount;		
	}
	
	public StudentGenderCount getCount(String dataName,Object data){
		StudentGenderCount studentGenderCount = (StudentGenderCount) countShow.get(data);
		if(studentGenderCount != null){
			
		}else{
			studentGenderCount = new StudentGenderCount();
			for (Iterator iter = this.data.iterator(); iter.hasNext();) {
				Map element = (Map) iter.next();
				if(data.equals(element.get(dataName))){					
					studentGenderCount.add((Gender)element.get("student_basicInfo_gender"),(Number) element.get("count"));
				}				
			}
		}
		return studentGenderCount;
	}
	
	/*public List getList(String countPropertyName){
		MultiValueMap resultMap = new MultiValueMap();
		for (Iterator iter = this.data.iterator(); iter.hasNext();) {
			Map element = (Map) iter.next();
			resultMap.put(element.get(countPropertyName),element);
		}
		List resultList = new ArrayList();
		for (Iterator iter = resultMap.keySet().iterator(); iter.hasNext();) {
			Object keyTemp = (Object) iter.next();
			resultList.add(new StudentGenderCountCollection(keyTemp,(List)resultMap.get(keyTemp)));
		}
		return resultList;
	}*/
	
	public List getList(List propertyList, Integer index, String sort){
		Comparator comparator = new Comparator(){
			public int compare(Object o1, Object o2) {
				return o2==null?-1:(o1==null?1:Collator.getInstance(Locale.CHINA).compare(o1, o2));
			}};
		BeanComparator beanComparator = new BeanComparator(sort, comparator){
			private static final long serialVersionUID = -3553696849831974759L;
			public int compare(Object o1, Object o2) {
				try{
					if( o2==null ){
						return -1;
					}else if( o1==null ){
						return 1;
					}
					if ( getProperty() == null ) {
			            return getComparator().compare( o1, o2 );
			        }
					Object value1 = null;
					Object value2 = null;
			        try {
			        	value1 = PropertyUtils.getProperty( o1, getProperty() );
			        }catch (NestedNullException e) {
						return 1;
					}
			        try {
			            value2 = PropertyUtils.getProperty( o2, getProperty() );
			        }
			        catch ( NestedNullException e ) {
			        	return -1;
			        }
			        return getComparator().compare( value1, value2 );
				}catch (Exception e) {
					throw new ClassCastException( e.toString() );
				}
			}};
		if(CollectionUtils.isNotEmpty(this.dataShow)){
			if(StringUtils.isNotBlank(sort)){
				Collections.sort(this.dataShow, beanComparator);
			}
			return this.dataShow;
		}
		if(index.intValue()==propertyList.size()){
			return new ArrayList();
		}
		MultiValueMap resultMap = new MultiValueMap();
		for (Iterator iter = this.data.iterator(); iter.hasNext();) {
			Map element = (Map) iter.next();
			resultMap.put(element.get(((BaseValueBean)propertyList.get(index.intValue())).getDataName()),element);
		}
		List resultList = new ArrayList();
		Set keySet = resultMap.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			Object keyTemp = (Object) iter.next();
			resultList.add(new StudentGenderCountCollection(keyTemp,((BaseValueBean)propertyList.get(index.intValue())).getDataName(),index,(List)resultMap.get(keyTemp)));
		}
		this.dataShow = resultList;
		if(StringUtils.isNotBlank(sort)){
			Collections.sort(this.dataShow, beanComparator);
		}
		return this.dataShow;
	}
	
	public Integer getSize(List propertyList, Integer fromIndex, Integer toIndex) {
		if(this.sizeShow!=null){
			return this.sizeShow;
		}
		if(fromIndex==null){
			return new Integer(0);
		}else{
			if(toIndex!=null&&fromIndex.compareTo(toIndex)>=0){
				return new Integer(1);
			}
		}
		int countTemp = 0;
		for (Iterator iter = this.getList(propertyList, fromIndex, null).iterator(); iter.hasNext();) {
			StudentGenderCountCollection element = (StudentGenderCountCollection) iter.next();
			Integer temp = element.getSize(propertyList, new Integer(fromIndex.intValue()+1), toIndex);
			if(new Integer(fromIndex.intValue()+1).compareTo(toIndex)>=0){
				countTemp += (temp==null?0:temp.intValue());
			}else{
				countTemp += (temp==null?0:temp.intValue())+1;
			}
		}
		this.sizeShow = new Integer(countTemp+1);
		return this.sizeShow;
	}
	
	/*public StudentGenderCount getCount(List propertyList, Integer index, Object data){
		if(this.index.compareTo(index)>0){
			return new StudentGenderCount();
		}
		StudentGenderCount studentGenderCount = (StudentGenderCount) countShow.get(data);
		if(studentGenderCount!=null){
			return studentGenderCount;
		}else{
			if(this.index.compareTo(index)==0){
				if(this.key.equals(data)){
					countShow.put(data, this.getTotalCount());
					return this.getTotalCount();
				}else{
					return new StudentGenderCount();
				}				
			}else{
				studentGenderCount = new StudentGenderCount();
				for (Iterator iter = this.getList(propertyList, new Integer(this.index.intValue()+1)).iterator(); iter.hasNext();) {
					StudentGenderCountCollection element = (StudentGenderCountCollection) iter.next();
					studentGenderCount.add(element.getCount(propertyList, index, data));
				}
				countShow.put(data, studentGenderCount);
				return studentGenderCount;
			}
		}
	}*/

	/**
	 * @return 返回 key.
	 */
	public Object getKey() {
		return key;
	}

	/**
	 * @param key 要设置的 key.
	 */
	public void setKey(Object key) {
		this.key = key;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof StudentGenderCountCollection)) {
			return false;
		}
		StudentGenderCountCollection rhs = (StudentGenderCountCollection) object;
		return new EqualsBuilder().append(this.key, rhs.key).isEquals();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("key", this.key).append("totalCount", this.totalCount).toString();
	}

	/**
	 * @return 返回 dataName.
	 */
	public String getDataName() {
		return dataName;
	}

	/**
	 * @param dataName 要设置的 dataName.
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	/**
	 * @return 返回 index.
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index 要设置的 index.
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

}

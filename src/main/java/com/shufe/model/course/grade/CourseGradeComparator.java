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
 * chaostone             2007-1-6            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade;

import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.course.grade.report.StdGrade;
import com.shufe.web.action.course.grade.TeachClassGradeHelper;

/**
 * 课程成绩比较
 * 
 * @author chaostone
 * @see TeachClassGradeHelper#info(com.shufe.model.course.task.TeachTask,
 *      javax.servlet.http.HttpServletRequest)
 * @see StdGrade#sortGrades(CourseGradeComparator)
 */
public class CourseGradeComparator implements Comparator {

	String cmpWhat;

	boolean isAsc;

	Map gradeTypeMap;

	public CourseGradeComparator(String cmpWhat, boolean isAsc, List gradeTypes) {
		this.cmpWhat = cmpWhat;
		this.isAsc = isAsc;
		this.gradeTypeMap = new HashMap();
		for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
			GradeType gradeType = (GradeType) iter.next();
			gradeTypeMap.put(gradeType.getShortName(), gradeType);
		}
	}

	public int compare(Object arg0, Object arg1) {
		CourseGrade g0 = (CourseGrade) arg0;
		CourseGrade g1 = (CourseGrade) arg1;
		if (cmpWhat.startsWith("gradeType")) {
			String cmp = cmpWhat.substring(cmpWhat.indexOf(".") + 1);
			GradeType gradeType = (GradeType) gradeTypeMap.get(cmp);
			return cmpScore(g0.getScore(gradeType), g1.getScore(gradeType), isAsc);
		} else {
			try {
				Collator myCollator = Collator.getInstance();
				Object what0 = PropertyUtils.getProperty(arg0, cmpWhat);
				Object what1 = PropertyUtils.getProperty(arg1, cmpWhat);
				if (isAsc) {
					return myCollator.compare((null == what0) ? "" : what0.toString(),
							(null == what1) ? "" : what1.toString());
				} else {
					return myCollator.compare((null == what1) ? "" : what1.toString(),
							(null == what0) ? "" : what0.toString());
				}
			} catch (Exception e) {
				throw new RuntimeException("[CourseGradeComparator]reflection error:" + cmpWhat
						+ e.getMessage());
			}
		}
	}

	private int cmpScore(Float score0, Float score1, boolean isAsc) {
		float fs0 = (null == score0) ? 0 : score0.floatValue();
		float fs1 = (null == score1) ? 0 : score1.floatValue();
		if (isAsc) {
			return Float.compare(fs0, fs1);
		} else {
			return Float.compare(fs1, fs0);
		}
	}
}

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
 * 塞外狂人             2006-9-13            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.web.action.course.grade.report.GradeReportSetting;

/**
 * 多名学生的成绩打印模型<br>
 * 通常以一个班级为单位
 * 
 * @author chaostone
 * 
 */
public class MultiStdGrade {

	private TeachCalendar calendar;

	private AdminClass adminClass;

	private List courses;

	/**
	 * @see StdGrade
	 */
	private List stdGrades;// [stdGrade列表]

	private Map extraGradeMap;// 每个学生除了共同课程之外的其他课程[std.id.toString,courseGrades]

	private Integer maxDisplay;// 最大显示列

	private Float ratio;

	public MultiStdGrade(TeachCalendar calendar, List grades, Float ratio) {
		if (grades.isEmpty()) {
			return;
		}
		this.calendar = calendar;
		this.ratio = ratio;
		Map gradesMap = new HashMap();
		Map courseStdNumMap = new HashMap();
		// 组装成绩,把每一个成绩放入对应学生的stdGrade中,并记录每一个成绩中课程对应的学生人数.
		for (Iterator iter = grades.iterator(); iter.hasNext();) {
			CourseGrade grade = (CourseGrade) iter.next();
			StdGrade stdGrade = (StdGrade) gradesMap
					.get(grade.getStd().getId());
			if (null == stdGrade) {
				stdGrade = new StdGrade(grade.getStd(), new ArrayList(),null,GradeReportSetting.ALL_GRADE);
				gradesMap.put(grade.getStd().getId(), stdGrade);
			}
			stdGrade.getGrades().add(grade);

			CourseStdNum courseStdNum = (CourseStdNum) courseStdNumMap
					.get(grade.getCourse());
			if (null == courseStdNum) {
				courseStdNumMap.put(grade.getCourse(), new CourseStdNum(grade
						.getCourse(), new Integer(1)));
			} else {
				courseStdNum.setCount(new Integer(courseStdNum.getCount()
						.intValue() + 1));
			}
		}
		this.stdGrades = new ArrayList(gradesMap.values());
		// 按照课程人数倒序排列,找到符合人数底线的公共课程
		List courseStdNums = new ArrayList(courseStdNumMap.values());
		Collections.sort(courseStdNums);
		int maxStdCount = ((CourseStdNum) (courseStdNums.get(0))).getCount()
				.intValue();
		courses = new ArrayList();
		for (int i = 0; i < courseStdNums.size(); i++) {
			CourseStdNum rank = (CourseStdNum) courseStdNums.get(i);
			if (new Float(rank.getCount().intValue()).floatValue()
					/ maxStdCount > ratio.floatValue()) {
				courses.add(rank.getCourse());
			}
		}
		// 记录每个学生超出公共课程的成绩,并找出最大的显示多余列
		int maxExtra = 0;
		extraGradeMap = new HashMap();
		Set commonCourseSet = new HashSet(courses);
		for (Iterator iter = stdGrades.iterator(); iter.hasNext();) {
			StdGrade stdGrade = (StdGrade) iter.next();
			int myExtra = 0;
			List extraGrades = new ArrayList();
			for (Iterator iterator = stdGrade.getGrades().iterator(); iterator
					.hasNext();) {
				CourseGrade courseGrade = (CourseGrade) iterator.next();
				if (!commonCourseSet.contains(courseGrade.getCourse())) {
					extraGrades.add(courseGrade);
					myExtra++;
				}
			}
			if (myExtra > maxExtra) {
				maxExtra = myExtra;
			}
			if (!extraGrades.isEmpty()) {
				extraGradeMap.put(stdGrade.getStd().getId().toString(),
						extraGrades);
			}
		}
		maxDisplay = new Integer(courses.size() + maxExtra);
	}

	public AdminClass getAdminClass() {
		return adminClass;
	}

	public void setAdminClass(AdminClass adminClass) {
		this.adminClass = adminClass;
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public List getStdGrades() {
		return stdGrades;
	}

	public void setStdGrades(List stdGrades) {
		this.stdGrades = stdGrades;
	}

	public List getCourses() {
		return courses;
	}

	public void setCourses(List courseRankList) {
		this.courses = courseRankList;
	}

	public Integer getMaxDisplay() {
		return maxDisplay;
	}

	public void setMaxDisplay(Integer maxCourse) {
		this.maxDisplay = maxCourse;
	}

	public Float getRatio() {
		return ratio;
	}

	public void setRatio(Float ratio) {
		this.ratio = ratio;
	}

	public Map getExtraGradeMap() {
		return extraGradeMap;
	}

	public void setExtraGradeMap(Map extraGradeMap) {
		this.extraGradeMap = extraGradeMap;
	}

	public void sortStdGrades(String cmpWhat, boolean isAsc) {
		if (null != stdGrades) {
			PropertyComparator cmp = new PropertyComparator(cmpWhat, isAsc);
			Collections.sort(stdGrades, cmp);
		}
	}

	/**
	 * 返回超出显示课程数量之外的课程数
	 * 
	 * @return
	 */
	public int getExtraCourseNum() {
		return getMaxDisplay().intValue() - getCourses().size();
	}
}

/**
 * 课程对应的学生人数
 * 
 * @author chaostone
 * 
 */
class CourseStdNum implements Comparable {

	Course course;

	Integer count;

	public CourseStdNum(com.ekingstar.eams.system.baseinfo.Course course2, Integer count) {
		this.course = (Course) course2;
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		CourseStdNum myClass = (CourseStdNum) object;
		return new CompareToBuilder().append(myClass.count, this.count)
				.toComparison();
	}
}
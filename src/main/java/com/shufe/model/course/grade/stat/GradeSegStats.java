package com.shufe.model.course.grade.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.service.util.stat.FloatSegment;

public class GradeSegStats {

	public static final String COURSE = "course";
	public static final String TASK = "task";
	/** 各类成绩分段统计数据 */
	protected List gradeSegStats;

	protected List scoreSegments;

	protected List courseGrades;

	protected boolean ignoreNull = true;

	public GradeSegStats() {
		super();
	}

	public GradeSegStats(int segs) {
		scoreSegments = new ArrayList(segs);
		for (int i = 0; i < segs; i++) {
			scoreSegments.add(new FloatSegment());
		}
	}

	/**
	 * 删除空的统计段，并把统计段按照从大到小的顺序排列
	 * 
	 */
	public void buildScoreSegments() {
		for (Iterator iter = scoreSegments.iterator(); iter.hasNext();) {
			FloatSegment ss = (FloatSegment) iter.next();
			if (ss.emptySeg())
				iter.remove();
		}
		Collections.sort(scoreSegments);
	}

	/**
	 * 进行统计
	 * 
	 * @param gradeTypes
	 */
	public void stat(List gradeTypes) {
		gradeSegStats = new ArrayList();
		for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
			GradeType gradeType = (GradeType) iter.next();
			List grades = new ArrayList();
			for (Iterator iterator = courseGrades.iterator(); iterator.hasNext();) {
				CourseGrade courseGrade = (CourseGrade) iterator.next();
				ExamGrade examGrade = null;
				if (gradeType.getId().equals(GradeType.GA)) {
					examGrade = new ExamGrade(gradeType, courseGrade.getGA());
				} else {
					examGrade = courseGrade.getExamGrade(gradeType);
				}
				if (null != examGrade) {
					if (null != examGrade.getScore() || !ignoreNull) {
						grades.add(examGrade);
					}
				}
			}
			if (!grades.isEmpty()) {
				gradeSegStats.add(new GradeSegStat(gradeType, scoreSegments, grades));
			}
		}
	}

	public List getCourseGrades() {
		return courseGrades;
	}

	public void setCourseGrades(List courseGrades) {
		this.courseGrades = courseGrades;
	}

	public List getGradeSegStats() {
		return gradeSegStats;
	}

	public void setGradeSegStats(List gradeSegStats) {
		this.gradeSegStats = gradeSegStats;
	}

	public List getScoreSegments() {
		return scoreSegments;
	}

	public void setScoreSegments(List scoreSegments) {
		this.scoreSegments = scoreSegments;
	}

}

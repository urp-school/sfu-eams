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
 * chaostone             2006-12-27            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.course.grade.Grade;
import com.shufe.service.util.stat.FloatSegment;

/**
 * 成绩分段统计
 * 
 * @author chaostone
 * 
 */
public class GradeSegStat {

	/** 成绩类型 */
	private GradeType gradeType;

	/** 总人数 */
	private int stdCount;

	/** 各段统计人数数据 */
	private List scoreSegments;

	/** 最高分 */
	private Float heighest;

	/** 最底分 */
	private Float lowest;

	/** 平均分 */
	private Float average;

	public GradeSegStat(GradeType gradeType, List scoreSegments, List grades) {
		this.gradeType = gradeType;
		this.scoreSegments = new ArrayList();
		for (Iterator iter = scoreSegments.iterator(); iter.hasNext();) {
			FloatSegment ss = (FloatSegment) iter.next();
			this.scoreSegments.add(ss.clone());
		}
		Collections.sort(grades);
		heighest = ((Grade) grades.get(0)).getScore();
		lowest = ((Grade) grades.get(grades.size() - 1)).getScore();
		double sum = 0;
		this.stdCount = grades.size();
		for (Iterator iter = grades.iterator(); iter.hasNext();) {
			Grade grade = (Grade) iter.next();
			Float score = grade.getScore();
			if (null == score) {
				score = new Float(0);
			}
			sum += score.doubleValue();
			for (Iterator iterator = this.scoreSegments.iterator(); iterator.hasNext();) {
				FloatSegment scoreSeg = (FloatSegment) iterator.next();
				if (scoreSeg.add(score)) {
					break;
				}
			}
		}
		if (0 != this.stdCount) {
			this.average = new Float(sum / this.stdCount);
		}

	}

	public GradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(GradeType gradeType) {
		this.gradeType = gradeType;
	}

	public List getScoreSegments() {
		return scoreSegments;
	}

	public void setScoreSegments(List scoreSegments) {
		this.scoreSegments = scoreSegments;
	}

	public int getStdCount() {
		return stdCount;
	}

	public void setStdCount(int stdCount) {
		this.stdCount = stdCount;
	}

	public Float getHeighest() {
		return heighest;
	}

	public Float getLowest() {
		return lowest;
	}

	public Float getAverage() {
		return average;
	}

}

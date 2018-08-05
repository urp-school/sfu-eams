package com.ekingstar.eams.course.grade.course.calculator;

import java.util.Iterator;
import java.util.Map;

import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.course.grade.course.CourseGrade;
import com.ekingstar.eams.course.grade.course.GradeCalculator;
import com.ekingstar.eams.course.grade.course.GradeState;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.config.SystemConfigLoader;

public class DefaultCalculator implements GradeCalculator {
    
    protected UtilDao utilDao;
    
    /** 补考成绩是否是最终成绩，如果不是则按照和原来的得分进行比较取最好成绩 */
    protected boolean makeupIsFinal = true;
    
    public DefaultCalculator() {
        makeupIsFinal = SystemConfigLoader.getConfig()
                .getBooleanParam("course.grade.makeupIsFinal");
    }
    
    public void calcFinal(CourseGrade grade) {
        Float makeUpScore = grade.getScore(new GradeType(GradeType.MAKEUP));
        if (null != makeUpScore) { // 判断补考成绩是否为null
            if (!makeupIsFinal) { // 判断补考成绩是否是最终成绩
                Float score = grade.getScore(); // 取得最终成绩
                if (null != score) { // 判断最终成绩是否为null
                    // 最终成绩不为null , 在总评不为null 的情况下把总评作为最终成绩
                    if (grade.getGA() != null) {
                        score = grade.getGA();
                    }
                    // 判断此时的最终成绩 是否小于 补考成绩
                    if (score.floatValue() < makeUpScore.floatValue()) {
                        // 成立则把补考成绩作为最终成绩
                        score = makeUpScore;
                    }
                } else { // 若最终成绩为null 则判断总评是否为null 不为null 则把总评赋予最终成绩
                    if (grade.getGA() != null) {
                        score = grade.getGA(); // 判断 最终成绩 是否小于 补考成绩 , 成立则把补考成绩作为最终成绩
                        if (score.floatValue() < makeUpScore.floatValue()) {
                            score = makeUpScore;
                        }
                    } else { // 若最终成绩和总评成绩都为null 则把补考成绩赋予最终成绩
                        score = makeUpScore;
                    }
                }
                grade.setScore(score); // 更新最终成绩
            } else {
                grade.setScore(makeUpScore);
            }
        } else {
            Float ga = grade.getGA();
            if (null == ga) {
                grade.setScore(grade.getBestScoreCanConvertToFinal());
            } else {
                grade.setScore(grade.getGA());
            }
        }
    }
    
    public void calcGA(CourseGrade grade) {
        if (null != grade.getGradeTask() && null != grade.getCourseTakeType()) {
            Float endScore = grade.getScore(new GradeType(GradeType.END));
            if (null == endScore) {
                endScore = grade.getScore(new GradeType(GradeType.DELAY));
            }
            // 免修不免试的学生单独将期末成绩或者缓考成绩计入总评
            if (grade.getCourseTakeType().getId().equals(CourseTakeType.REEXAM)) {
                grade.setGA(endScore);
                return;
            }
            // 按百分比计算
            GradeState state = grade.getGradeTask().getCourseGradeState();
            if (null == state)// 无法计算,放弃
                return;
            
            // 百分比是空的也不计算
            Map percentMap = state.getPercentMap();
            if (percentMap.isEmpty()) {
                grade.setGA(null);
                return;
            }
            // 期末或者缓考没有成绩不计算总评
            if (null == endScore) {
                Float endGradePercent = (Float) percentMap.get(new GradeType(GradeType.END));
                if (null != endGradePercent && endGradePercent.floatValue() != 0) {
                    grade.setGA(null);
                    return;
                }
            }
            float ga = 0;
            for (Iterator iter = percentMap.keySet().iterator(); iter.hasNext();) {
                GradeType gradeType = (GradeType) iter.next();
                Float gradeTypeScore = null;
                if (gradeType.getId().equals(GradeType.END)) {
                    gradeTypeScore = endScore;
                } else {
                    gradeTypeScore = grade.getScore(gradeType);
                }
                if (null != gradeTypeScore) {
                    ga += state.getPercent(gradeType).floatValue() * gradeTypeScore.floatValue();
                }
            }
            grade.setGA(reservedPrecision(ga, state.getPrecision().intValue()));
        }
        return;
    }
    
    protected Float reservedPrecision(float ga, int precision) {
        int mutilply = (int) Math.pow(10, precision);
        ga *= mutilply;
        if (ga % 1 >= 0.5)
            ga += 1;
        ga -= ga % 1;
        return (new Float(ga / mutilply));
    }
    
    public UtilDao getUtilDao() {
        return utilDao;
    }
    
    public void setUtilDao(UtilDao utilDao) {
        this.utilDao = utilDao;
    }
    
}

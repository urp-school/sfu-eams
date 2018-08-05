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
 * chaostone             2006-6-10            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.election;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.course.arrange.resource.TimeTable;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.web.action.course.election.CreditNeed;

/**
 * 学生选课的状态数据
 * 
 * @author chaostone
 * 
 */
public class ElectState implements Serializable {
    
    private static final long serialVersionUID = -7987996716147870563L;
    
    /** 学生选课用的简要信息 */
    public SimpleStdInfo std;
    
    /** 建议学分 */
    public List creditNeeds = new ArrayList();
    
    /** 是否完成评教 */
    public Boolean isEvaluated;
    
    /** 学分上限(已有上限+奖励学分+浮动学分) */
    public float maxCredit;
    
    /** 奖励学分 */
    public float awardedCredit;
    
    /** 已选学分 */
    public float electedCredit;
    
    /** 选课参数 */
    public ElectParams params;
    
    /** 选课参数学年学期内，有时间耦合的所有学年学期 */
    public List calendars;
    
    /** 历史已选的课程IDs */
    public Map hisCourses = new HashMap(0);
    
    /** 本次已选的课程IDs */
    public Set electedCourseIds = new HashSet();
    
    /** 计划内课程plans */
    public Map planCourses = new HashMap();
    
    /** 时间占用表 */
    public TimeTable table;
    
    /** 建议必修课 */
    public Set compulsoryCourseIds = new HashSet();
    
    public Set substituteIds =new HashSet();
    
    /** 已经通过的有替代课程的课程 */
    public Set passSubstituteIds =new HashSet();
    
    public ElectState(StdCreditConstraint constraint, ElectParams params) {
        std = new SimpleStdInfo();
        Student student = constraint.getStd();
        std.setId(constraint.getStd().getId());
        std.setStdNo(student.getCode());
        std.setEnrollTurn(student.getEnrollYear());
        std.setGenderId(student.getGender().getId());
        std.setStdTypeId(student.getType().getId());
        std.setDepartId(student.getDepartment().getId());
        std.setSpecialityId((student.getFirstMajor() == null) ? null : student.getFirstMajor()
                .getId());
        std.setAspectId((student.getFirstAspect() == null) ? null : student.getFirstAspect()
                .getId());
        std.setSchoolDistrictId((student.getSchoolDistrict() == null) ? null : student
                .getSchoolDistrict().getId());
        
        for (Iterator iter = student.getAdminClasses().iterator(); iter.hasNext();) {
            AdminClass adminClass = (AdminClass) iter.next();
            this.std.adminClassIds.add(adminClass.getId());
        }
        if (null != student.getAbroadStudentInfo()) {
            if (null == student.getAbroadStudentInfo().getHSKDegree()) {
                std.HSKDegree = new Integer(1);
            } else {
                std.HSKDegree = student.getAbroadStudentInfo().getHSKDegree().getDegree();
            }
        }
        std.languageAbility = (null == student.getLanguageAbility()) ? new Integer(0) : student
                .getLanguageAbility().getLevel();
        this.params = params;
        updateCredit(constraint);
    }
    
    public Set getUnPassedCourseIds() {
        if (null == hisCourses) {
            return Collections.EMPTY_SET;
        } else {
            Set unPassedCourseIds = new HashSet();
            Set courseSet = hisCourses.keySet();
            for (Iterator iterator = courseSet.iterator(); iterator.hasNext();) {
                Long courseId = (Long) iterator.next();
                Boolean rs = (Boolean) hisCourses.get(courseId);
                if (Boolean.FALSE.equals(rs)) {
                    unPassedCourseIds.add(courseId);
                }
            }
            return unPassedCourseIds;
        }
    }
    
    /**
     * 依据学分限制，更新学分
     * 
     * @param constraint
     */
    public void updateCredit(StdCreditConstraint constraint) {
        float awarded = 0;
        if (params.getIsAwardCreditConsider().equals(Boolean.TRUE)) {
            awarded = (null == constraint.getAwardedCredit()) ? 0 : constraint.getAwardedCredit()
                    .floatValue();
        }
        // 更新评教和学分上限(已有上限+奖励学分+浮动学分)
        maxCredit = constraint.getMaxCredit().floatValue() + awarded
                + params.getFloatCredit().floatValue();
        electedCredit = (null == constraint.getElectedCredit()) ? 0 : constraint.getElectedCredit()
                .floatValue();
        awardedCredit = awarded;
    }
    
    public void addCreditNeeded(CourseType type, Float credit) {
        creditNeeds.add(new CreditNeed(type, credit));
    }
    
    public float getAwardedCredit() {
        return awardedCredit;
    }
    
    public List getCalendars() {
        return calendars;
    }
    
    public List getCreditNeeds() {
        return creditNeeds;
    }
    
    public float getElectedCredit() {
        return electedCredit;
    }
    
    public Set getElectedCourseIds() {
        return electedCourseIds;
    }
    
    public Map getHisCourses() {
        return hisCourses;
    }
    
    public float getMaxCredit() {
        return maxCredit;
    }
    
    public ElectParams getParams() {
        return params;
    }
    
    public SimpleStdInfo getStd() {
        return std;
    }
    
    public TimeTable getTable() {
        return table;
    }
    
    public Boolean getIsEvaluated() {
        return isEvaluated;
    }
    
    public Set getCompulsoryCourseIds() {
        return compulsoryCourseIds;
    }
    
    public boolean isCoursePass(Long courseId) {
        Boolean rs = (Boolean) hisCourses.get(courseId);
        return Boolean.TRUE.equals(rs);
    }
    
    public Map getPlanCourses() {
        return planCourses;
    }

    
    public Set getSubstituteIds() {
        return substituteIds;
    }

    
    public void setSubstituteIds(Set substituteIds) {
        this.substituteIds = substituteIds;
    }

    
    public Set getPassSubstituteIds() {
        return passSubstituteIds;
    }

    
    public void setPassSubstituteIds(Set passSubstituteIds) {
        this.passSubstituteIds = passSubstituteIds;
    }
    
}

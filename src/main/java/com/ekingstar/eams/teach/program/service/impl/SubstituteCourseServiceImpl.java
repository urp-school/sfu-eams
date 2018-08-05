//$Id: SubstituteCourseServiceImpl.java,v 1.25 2008/11/14 09:43:18 cheneystar Exp $
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
 * @author cheneystar
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * cheneystar             2008-11-14         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.teach.program.service.impl;

import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.teach.program.major.MajorSubstituteCourse;
import com.ekingstar.eams.teach.program.service.SubstituteCourseService;
import com.ekingstar.eams.teach.program.std.DefaultStdSubstituteCourse;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;

public class SubstituteCourseServiceImpl extends BasicService implements SubstituteCourseService {
    
    public List getStdSubstituteCourses(Student student, MajorType majorType) {
        // 得到个人的替代课程
        List substituteList = getStdSubstituteCourse(student);
        // 得到专业的替代课程
        substituteList.addAll(getMajorSubstituteCourse(student,majorType));
        return substituteList;
    }
    
    public List getStdSubstituteCourses(Student std) {
        return getStdSubstituteCourses(std,null);
    }
    
    /** 得到个人的替代课程* */
    private List getStdSubstituteCourse(Student student) {
        EntityQuery query = new EntityQuery(DefaultStdSubstituteCourse.class, "stdSubstituteCourse");
        query.add(new Condition("stdSubstituteCourse.std=:std", student));
        return (List) utilService.search(query);
    }
    
    /** 得到专业的替代课程* */
    private List getMajorSubstituteCourse(Student student, MajorType majorType) {
        EntityQuery query = new EntityQuery(MajorSubstituteCourse.class, "majorCourse");
        query.add(new Condition("majorCourse.enrollTurn = :enrollTurn", student.getEnrollYear()));
        query.add(new Condition("majorCourse.stdType = :stdType", student.getStdType()));
        if (null == student.getDepartment()) {
            query.add(new Condition("majorCourse.department is null"));
        } else {
            query.add(new Condition(
                    "majorCourse.department is null or majorCourse.department = :department",
                    student.getDepartment()));
        }
        if (null == student.getFirstMajor()) {
            query.add(new Condition("majorCourse.major is null"));
        } else {
            query.add(new Condition("majorCourse.major is null or majorCourse.major = :major",
                    student.getFirstMajor()));
        }
        if (null == student.getFirstAspect()) {
            query.add(new Condition("majorCourse.majorField is null"));
        } else {
            query.add(new Condition(
                    "majorCourse.majorField is null or majorCourse.majorField = :majorField",
                    student.getFirstAspect()));
        }
        return (List) utilService.search(query);
    }
}

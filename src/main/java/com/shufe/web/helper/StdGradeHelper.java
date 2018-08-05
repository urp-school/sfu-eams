package com.shufe.web.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.service.BaseCodeService;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.std.Student;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.web.action.common.DispatchBasicAction;

public class StdGradeHelper {
    
    /**
     * 得到当前学生的成绩
     * 
     * @param request
     * @param std
     * @param gradeService
     * @param baseCodeService
     * @param gradePointService
     */
    public void searchStdGrade(HttpServletRequest request, Student std, GradeService gradeService,
            BaseCodeService baseCodeService, GradePointService gradePointService) {
        Long majorTypeId = RequestUtils.getLong(request, "majorType.id");
        if (null == majorTypeId) {
            majorTypeId = MajorType.FIRST;
        }
        MajorType majorType = (MajorType) baseCodeService.getCode(MajorType.class, majorTypeId);
        request.setAttribute("majorType.id", majorTypeId);
        
        CourseGrade gradeExample = new CourseGrade();
        gradeExample.setStd(std);
        gradeExample.setStatus(new Integer(Grade.PUBLISHED));
        gradeExample.setMajorType(majorType);
        List grades = gradeService.getCourseGrades(gradeExample);
        Collections.sort(grades, new PropertyComparator("calendar.yearTerm", false));
        Set gradeSet = new HashSet();
        for (Iterator iter = grades.iterator(); iter.hasNext();) {
            CourseGrade grade = (CourseGrade) iter.next();
            for (Iterator iterator = grade.getExamGrades().iterator(); iterator.hasNext();) {
                ExamGrade examGrade = (ExamGrade) iterator.next();
                if (Boolean.TRUE.equals(examGrade.getIsPublished())) {
                    gradeSet.add(examGrade.getGradeType());
                }
            }
        }
        List gradeTypes = new ArrayList(gradeSet);
        Collections.sort(gradeTypes, new PropertyComparator("priority"));
        DispatchBasicAction.addCollection(request, "gradeTypes", gradeTypes);
        DispatchBasicAction.addCollection(request, "grades", grades);
        request.setAttribute("has2ndSpeciality", Boolean.valueOf(null != std.getSecondMajor()));
        request.setAttribute("FINAL", baseCodeService.getCode(GradeType.class, GradeType.FINAL));
        request.setAttribute("MAKEUP", baseCodeService.getCode(GradeType.class, GradeType.MAKEUP));
        request.setAttribute("stdGP", gradePointService.statStdGPA(std,
                gradeExample.getMajorType(), Boolean.TRUE));
        request.setAttribute("majorType", majorType);
    }
    
}

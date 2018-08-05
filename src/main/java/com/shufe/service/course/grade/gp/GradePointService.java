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
 * chaostone             2007-1-9            Created
 *  
 ********************************************************************************/

package com.shufe.service.course.grade.gp;

import java.util.Collection;
import java.util.List;

import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.grade.gp.GradePointRule;
import com.shufe.model.course.grade.gp.MultiStdGP;
import com.shufe.model.course.grade.gp.StdGP;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 绩点统计和管理服务
 * 
 * @author chaostone
 */
public interface GradePointService {
    
    /**
     * 统计学生的平均绩点<br>
     * 除"学生"之外的其他参数均为可选参数。<br>
     * 平均绩点为： GPA=(∑(绩点*学分))/∑(学分) 平均绩点以截断的方式保留后面两位
     * 
     * @param std
     * @param calendar
     *            可以为null
     * @param majorType
     * @param isPublished
     * @param isBestGrade
     *            TODO
     * 
     * @return
     */
    public Float statStdGPA(Student std, TeachCalendar calendar, MajorType majorType,
            Boolean isPublished, Boolean isBestGrade);
    
    public Float statStdGPA(Student std, TeachCalendar calendar, MajorType majorType,
            Boolean isPublished);
    
    /**
     * 统计学生的在校所有学期的平均绩点
     * 
     * <pre>
     *      平均绩点为： GPA=(∑(绩点*学分))/∑(学分)
     *      平均分为： GA=(∑(得分*学分))/∑(学分)
     * </pre>
     * 
     * @param std
     * @param majorType
     * @param calendar
     *            TODO
     * @param isPublished
     * @return
     */
    public StdGP statStdGPA(Student std, MajorType majorType, Boolean isPublished);
    
    public StdGP statStdGPAByYear(Student std, MajorType majorType, Boolean isPublished);
    
    public StdGP statStdGPA(Student std, List grades);
    
    public StdGP statStdGPA(Student std, MajorType majorType, Boolean isPublished, List grades,
            Boolean isBestGrade);
    
    /**
     * 统计多个学生的平均绩点和其他信息
     * 
     * @param stds
     * @param majorType
     * @param isPublished
     * @return
     */
    public MultiStdGP statMultiStdGPA(Collection stds, MajorType majorType, Boolean isPublished);
    
    /**
     * 重新计算每个学期的学生单个课程的绩点<br>
     * 学生类别信息包含在了日历中。
     * 
     * @param rule
     * @param calendarIds
     */
    public void reStatGP(GradePointRule rule, String calendarIds);
}

//$Id: ElectCourseService.java,v 1.8 2006/12/22 04:58:08 duanth Exp $
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
 * chaostone             2005-12-12         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.election;

import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.course.election.ElectRecordDAO;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.model.course.election.ElectState;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.service.quality.evaluate.EvaluateSwitchService;

public interface ElectCourseService {
    
    /*-------elect result list for election----------
     */
    public static String selectSuccess = "prompt.elect.success";
    
    public static String notEvaluateComplete = "error.elect.notEvaluateComplete";
    
    public static String overCeilCreditConstraint = "error.elect.overCeilCreditConstraint";
    
    public static String overMaxStdCount = "error.elect.overMaxStdCount";
    
    public static String notExistsCreditConstraint = "error.elect.notExistsCreditConstraint";
    
    public static String notInElectScope = "error.elect.notInElectScope";
    
    public static String reStudiedNotAllowed = "error.elect.reStudiedNotAllowed";
    
    public static String reStudyPassedCourseNotAllowed = "error.elect.reStudyPassedCourseNotAllowed";
    
    public static String elected = "error.elect.elected";
    
    public static String electClosed = "error.elect.electClosed";
    
    public static String noAverageGrade = "error.elect.noAverageGrade";
    
    public static String timeCollision = "error.elect.timeCollision";
    
    public static String courseNotInPlan = "error.course.elect.notInPlan";
    
    public static String needSelectOnTimeCollsion = "error.elect.needSelectOnTimeCollsion";
    
    public static String noDateSuitable = "error.elect.noSuitableDate";
    
    public static String noTimeSuitable = "error.elect.noSuitableTime";
    
    public static String noParamsNotExists = "error.electParams.notExists";
    
    public static String courseIsNotCancelable = "error.cancelElect.courseIsNotCancelable";
    
    public static String cancelCourseOfPreviousTurn = "error.cancelElect.cancelCourseOfPreviousTurn";
    
    public static String cancelCourseOfPointTurn = "error.cancelElect.cancelCourseOfPointTurn";
    
    public static String underMinStdCount = "error.cancelElect.underMinStdCount";
    
    public static String cancelUnElected = "error.cancelElect.cancelUnElected";
    
    public static String cancelSuccess = "prompt.cancelElect.success";
    
    public static String HSKNotSatisfied = "error.elect.HSKNotSatisfied";
    
    public static String languageAbilityNotSatisfied = "error.elect.languageAbilityNotSatisfied";
    
    public static String prerequisteCoursesNotMeeted = "error.elect.prerequisteCoursesNotMeeted";
    
    public static String notInSchoolDistrict = "error.elect.notInSchoolDistrict";
    
    public static String notGenderDistrict = "error.elect.noGenderDistrict";
    
    /**
     * 查询给定学期能选课的课程.<br>
     * 如果查看一个学生一个学年度内可以选课的课程，可以将两个学期作为参数.<br>
     * 是否参照班级建议课表在params指定.<br>
     * 
     * @param task
     * @param state
     * @param time
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getElectableTasks(TeachTask task, ElectState state, Collection courseIds,
            TimeUnit time, boolean isScopeConstraint, int pageNo, int pageSize);
    
    /**
     * 收集必须课程
     * 
     * @param state
     * @param student
     * @param calendars
     */
    public void addCompulsoryCourse(ElectState state, Student student, List calendars);
    
    /**
     * 
     * @param task
     * @param time
     * @param calendars
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getElectableTasks(TeachTask task, TimeUnit time, Collection calendars,
            int pageNo, int pageSize);
    
    /**
     * 如果成功则学生选课 （更新个人课表，和已选学分）<br>
     * 1)生成草选和实选结果<br>
     * 2)更新教学任务的教学班人数<br>
     * 3)更新学生选课的学分限制<br>
     * 4)更新实选学生数
     * 
     * @param std
     * @param task
     * @param params
     *            选课参数
     * @param takeType
     *            修读类别
     * @return
     */
    public String elect(TeachTask task, ElectState state, CourseTakeType takeType);
    
    /**
     * 学生退课 （更新个人课表，和已选学分） 1)删除草选和实选结果<br>
     * 2)更新教学任务的教学班人数<br>
     * 3)更新学生选课的学分限制<br>
     * 4)更新实选学生数
     * 
     * @param std
     * @param task
     * @param state
     */
    public String cancel(TeachTask task, ElectState state);
    
    /**
     * 检查学生是否可以不必评教
     * 
     * @param std
     * @param params
     * @return
     */
    public boolean isPassEvaluation(Long stdId);
    
    /**
     * 教学任务存取对象
     * 
     * @param taskDAO
     */
    public void setTeachTaskDAO(TeachTaskDAO taskDAO);
    
    public void setElectRecordDAO(ElectRecordDAO electRecordDAO);
    
    public void setEvaluateSwitchService(EvaluateSwitchService evaluateSwitchService);
    
}

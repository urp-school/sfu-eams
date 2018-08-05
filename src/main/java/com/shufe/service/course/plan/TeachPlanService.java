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
 * chaostone             2006-3-25            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.plan;

import java.util.Collection;
import java.util.List;

import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.basecode.dao.BaseCodeDao;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.dao.course.plan.TeachPlanDAO;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.calendar.OnCampusTimeNotFoundException;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.std.StudentService;
import com.shufe.util.DataRealmLimit;

/**
 * 培养计划服务接口
 * 
 * @author chaostone
 * 
 */
public interface TeachPlanService {
	/**
	 * 根据培养计划id查询培养计划
	 * 
	 * @param id
	 * @return
	 */
	public TeachPlan getTeachPlan(Long id);

	/**
	 * 查询班级的培养计划
	 * 
	 * @param adminClass
	 * @return
	 */
	public TeachPlan getTeachPlan(AdminClass adminClass);
	
	/**
	 * 查询指定所在年级和专业的 培养计划
	 * 
	 * @param speciality
	 * @param stdType
	 * @param enrollTurn
	 * @return
	 */
	public List getTeachPlan(Speciality speciality, String stdTypeCode, String enrollTurn);

	/**
	 * 获得指定id的培养计划
	 * 
	 * @param planIdSeq
	 * @return
	 */
	public List getTeachPlans(String planIdSeq);

	/**
	 * 列出培养计划
	 * 
	 * @param plan
	 * @param limit
	 * @param isSpecialityPlan
	 * @return
	 */
	public Collection getTeachPlans(TeachPlan plan, DataRealmLimit limit,
			List sortList, Boolean isSpecialityPlan, Boolean isExactly);

	/**
	 * 根据学号查询培养计划.<br>
	 * 返回的培养计划列表中,<br>
	 * 1)如果有个人培养计划,则增加到列表中;没有则将专业培养计划增至列表中<br>
	 * 2)如果学生有双转业的情况，按照第一专业处理.<br>
	 * 2.1)如果学生在双专业有个人培养计划,增加到列表中;<br>
	 * 2.2)如果学生在双专业没有个人培养计划,将双专业的专业培养计划增加到列表中;<br>
	 * 
	 * 返回学生可用的培养计划（毕业审核用的）
	 * 
	 * @param std
	 * @return
	 */
	public List getTeachPlans(Student std);

	/**
	 * 返回培养计划<br>
	 * 查询专业培养计划时，先按照所在年级、类别、院系、专业、方向严格匹配.<br>
	 * 有专业的学生将忽略学生所在的院系属性<br>
	 * 学生类别回依次向上回溯
	 * 
	 * @param std
	 * @param isFirstSpeciality
	 *            是否第一专业,如果为null,则默认为第一专业
	 * @param isSpeciality
	 *            是否为对应的专业计划,如果为null,则以个人培养计划优先返回
	 * 
	 * @return
	 */
	public TeachPlan getTeachPlan(Student std, Boolean isFirstSpeciality,
			Boolean isSpeciality);

	/**
	 * 查找在指定学期，是否生成任务的培养计划
	 * 
	 * @param plan
	 * @param calendar
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param active
	 * @param omitSmallTerm
	 *            是否忽略小学期
	 * @return
	 */
	public Collection getTeachPlansOfTaskGeneration(TeachPlan plan,
			TeachCalendar calendar, String stdTypeIdSeq, String departIdSeq,
			Boolean generated, boolean omitSmallTerm);

	/**
	 * 查找在指定学期，是否活跃的培养计划
	 * 
	 * @param plan
	 * @param calendar
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param active
	 * @param omitSmallTerm
	 *            是否忽略小学期
	 * @return
	 */
	public Collection getTeachPlansOfActive(TeachCalendar calendar,
			String stdTypeIdSeq, String departIdSeq, Boolean isSpeciality);

	/**
	 * 检查所有项均匹配的情况下，是否存在查询的培养计划
	 * 
	 * @param enrollTurn
	 * @param stdTypeId
	 * @param departmentId
	 * @param specialityId
	 * @param aspectId
	 * @return
	 */
	public boolean isExist(String enrollTurn, Long stdTypeId,
			Long departmentId, Long specialityId, Long aspectId, Long stdId);

	/**
	 * 检查所有项均匹配的情况下，是否存在查询的培养计划<br>
	 * 
	 * @see isExist
	 * @param plan
	 * @return
	 */
	public boolean isTeachPlanExists(TeachPlan plan);

	/**
	 * 是否重复
	 * @param plan
	 * @return
	 */
	public boolean isDuplicate(TeachPlan plan);
	/**
	 * 查找对于固定学期培养计划中要求的学分值.
	 * 
	 * @param plan
	 * @param term
	 *            [1..maxTerm]
	 * @return
	 */
	public Float getCreditByTerm(TeachPlan plan, int term);

	/**
	 * 获得一个学生计划内应该上的课程(有双专业的学生，也一同考虑)
	 * 
	 * @param std
	 *            完整的学生对象,学生类别、所在年级和院系、专业信息不能为空
	 * @return
	 */
	public List getPlanCourses(Student std);

	/**
	 * 获得一个学生计划内应该上的课程(有双专业的学生，也一同考虑)
	 * 
	 * @param stdId
	 *            学号
	 * @return
	 */
	public List getPlanCourses(Long stdId);
    
    /**
     * 获得一个学生计划内应该上的学位课程
     * 
     * @param std
     *            完整的学生对象,学生类别、所在年级和院系、专业信息不能为空
     * @return
     */
    public List getPlanCourseOfDegree(Student std, MajorType majorType);

	/**
	 * 保存新的培养计划
	 * 
	 * @param plan
	 * @return
	 */
	void saveTeachPlan(TeachPlan plan);

	/**
	 * 更新培养计划
	 * 
	 * @param plan
	 */
	void updateTeachPlan(TeachPlan plan);

	/**
	 * 删除培养计划
	 */
	void removeTeachPlan(Long planId);

	/**
	 * 给定一批培养计划,生成相同专业的指定年份的专业计划<br>
	 * 如果给定的计划是学生培养计划，则忽略<br>
	 * 如果要生成的计划已经存在，则忽略<br>
	 * 
	 * @param plans
	 * @param enrollTurn
	 * @return
	 */
	public List genTeachPlanOfSpecialityBy(Collection plans, String enrollTurn);

	/**
	 * 依据单个培养计划，为指定的学生复制或者生成新的培养计划<br>
	 * 1)这些培养计划均没有确认<br>
	 * 2)培养计划中的修改时间和制定时间为系统当前时间<br>
	 * 
	 * @param stdIdSeq
	 * @param targetPlan
	 * @return
	 */
	public List genTeachPlanForStd(String stdIdSeq, TeachPlan targetPlan);

	public List genTeachPlanForStd(Collection stds, TeachPlan targetPlan);

	/**
	 * 查找没有个人培养计划的学生名单
	 * 
	 * @return
	 */
	public List getStdsWithoutPersonalPlan(TeachPlan plan);

	/**
	 * 更新培养计划的确认状态
	 * 
	 * @param plans
	 * @param isConfirm
	 */
	public void updateConfirmState(List plans, Boolean isConfirm);

	/**
	 * 更新培养计划的确认状态
	 * 
	 * @param planIds
	 * @param isConfirm
	 */
	public void updateConfirmState(Long[] planIds, Boolean isConfirm);

	/**
	 * 查找已经某个培养计划使用的课程类别
	 * 
	 * @param plan
	 * @return
	 */
	public Collection getUsedCourseTypes(TeachPlan plan);

	/**
	 * 查找没有在某个培养计划使用的课程类别
	 * 
	 * @param plan
	 * @return
	 */
	public Collection getUnusedCourseTypes(TeachPlan plan);

	/**
	 * 将符合指定培养计划的课程返回
	 * 
	 * @param plan
	 * @param courses
	 * @return
	 */
	public Collection filterCourses(TeachPlan plan, final List courses);

	/**
	 * 统计培养计划的总学分
	 * 
	 * @param planId
	 * @return
	 */
	public float statOverallCredit(Long planId);

	/**
	 * 统计培养计划的总学分
	 * 
	 * @param planId
	 * @return
	 */
	public int statOverallCreditHour(Long planId);

	/**
	 * 查询指定课程在学生计划中的对应课程类别.<br>
	 * 如果不存在则返回任意选修课<@see> CourseType.PUBLIC_COURSID<br>
	 * planType为学生的专业种类,1为第一专业,2为第二专业<br>

	 * @param stdNo
	 * @param course
	 * @param courseType 参考的课程类别
	 * @param majorType
	 * @return  null 当plan 不在[1,2]中
	 */
	public CourseType getCourseTypeOfStd(String stdNo, Course course,
			CourseType courseType, MajorType majorType);

	/**
	 * 查询一个学生第几专业的培养计划中的所有课程类别
	 * 
	 * @param stdNo
	 * @param isFirstSpeciality
	 * @return
	 */
	public List getCourseTypesOf(Long stdId, Boolean isFirstSpeciality);

	/**
	 * 设置培养计划存取类
	 * 
	 * @param teachPlanDAO
	 */
	public void setTeachPlanDAO(TeachPlanDAO teachPlanDAO);

	/**
	 * 设置基础数据访问接口
	 * 
	 * @param baseCodeDao
	 */
	public void setBaseCodeDao(BaseCodeDao baseCodeDao);

	/**
	 * 设置学生信息存取接口
	 * 
	 * @param studentService
	 */
	public void setStudentService(StudentService studentService);

	/**
	 * 查询指定学生和课程查询该课程在计划中的课程类别
	 * 
	 * @param std
	 *            学生信息应具备学生类别,院系,专业等信息
	 * @param course
	 *            [课程的id]
	 * @param isFirstSpeciality
	 *            是否是第一专业
	 * @return
	 */
	public PlanCourse getPlanCourse(Student std, Course course,
			Boolean isFirstSpeciality);

	/**
	 * 查询指定计划在几个离散学期的所对应的教学日历
	 * 
	 * @param plan
	 * @param termList
	 *            学期从1开始,
	 * @return
	 * @exception OnCampusTimeNotFoundException
	 *                如果找不到计划中学生类别和所在年级对应的在校时间
	 * @exception IllegalArgumentException
	 *                如果给定的学期存在负值和最大值超过计划中的学期个数
	 */
	public List getTeachCalendarsOf(TeachPlan plan, List termList);

	/**
	 * 汇总各个开课院系在指定学期内的开设的不同课程
	 * 
	 * @param calendar
	 * @return department,course,credit,weekHour,creditHour
	 */
	public Collection statDepartCourse(TeachCalendar calendar, List stdTypes,
			List departs, PageLimit limit);

	/**
	 * 汇总各个开课院系在指定学期内的开设的不同课程数量
	 * 
	 * @param calendar
	 * @return department,count
	 */
	public Collection statDepartCourseCount(TeachCalendar calendar,
			List stdTypes, List departs);
}

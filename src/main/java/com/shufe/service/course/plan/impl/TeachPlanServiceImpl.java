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
package com.shufe.service.course.plan.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.IntRange;
import org.apache.commons.lang.math.NumberUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.course.task.TeachTask;
import com.ekingstar.eams.system.basecode.dao.BaseCodeDao;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.course.plan.CourseGroupDAO;
import com.shufe.dao.course.plan.TeachPlanDAO;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachCommon;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.model.system.calendar.OnCampusTime;
import com.shufe.model.system.calendar.OnCampusTimeNotFoundException;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.DataRealmLimit;

public class TeachPlanServiceImpl extends BasicService implements TeachPlanService {

  TeachPlanDAO teachPlanDAO;

  BaseCodeDao baseCodeDao;

  StudentService studentService;

  TeachCalendarService calendarService;

  CourseGroupDAO groupDAO;

  public TeachPlan getTeachPlan(Long id) {
    if (null != id) {
      return teachPlanDAO.getTeachPlan(id);
    } else {
      return null;
    }
  }

  public TeachPlan getTeachPlan(AdminClass adminClass) {
    TeachPlan examplePlan = new TeachPlan(adminClass);
    Collection plans = teachPlanDAO.getTeachPlans(examplePlan, null, null, Boolean.TRUE, Boolean.TRUE);
    for (Iterator iter = plans.iterator(); iter.hasNext();) {
      TeachPlan plan = (TeachPlan) iter.next();
      if (StringUtils.equals(plan.getEnrollTurn(), adminClass.getEnrollYear())) { return plan; }
    }
    return null;

  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#getTeachPlans(java.lang.String)
   */
  public List getTeachPlans(String planIdSeq) {
    if (StringUtils.isEmpty(planIdSeq)) {
      return Collections.EMPTY_LIST;
    } else {
      return teachPlanDAO.getTeachPlans(SeqStringUtil.transformToLong(planIdSeq));
    }
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#isExist(java.lang.String, java.lang.Long,
   *      java.lang.Long, java.lang.Long, java.lang.Long,Long)
   */
  public boolean isExist(String enrollTurn, Long stdTypeId, Long departmentId, Long specialityId,
      Long aspectId, Long stdId) {
    return isTeachPlanExists(
        new TeachPlan(enrollTurn, stdTypeId, departmentId, specialityId, aspectId, stdId));
  }

  public TeachPlan getTeachPlan(Student std, Boolean isFirstSpeciality, Boolean isSpeciality) {
    Boolean isFirstMjor = isFirstSpeciality;
    if (null == isFirstMjor) {
      isFirstMjor = Boolean.TRUE;
    }
    if (isFirstMjor.equals(Boolean.TRUE)) {
      return getTeachPlan(std, std.getFirstMajor(), std.getFirstAspect(), isSpeciality);
    } else {
      if (null == std.getSecondMajor() && null == std.getSecondAspect()) {
        return null;
      } else {
        return getTeachPlan(std, std.getSecondMajor(), std.getSecondAspect(), isSpeciality);
      }
    }
  }

  /**
   * 查找学生的培养计划.
   * 
   * @param std
   * @param speciality
   * @param aspect
   * @param isSpeciality
   * @return
   */
  private TeachPlan getTeachPlan(Student std, Speciality speciality, SpecialityAspect aspect,
      Boolean isSpeciality) {
    if (null != isSpeciality) {
      if (isSpeciality.equals(Boolean.FALSE)) {
        return getPersonTeachPlan(std, speciality, aspect);
      } else {
        return getSpecialityPlan(std, speciality, aspect);
      }
    } else {
      TeachPlan plan = getPersonTeachPlan(std, speciality, aspect);
      if (plan != null) {
        return plan;
      } else {
        return getSpecialityPlan(std, speciality, aspect);
      }
    }
  }

  /**
   * 查找个人培养计划
   * 
   * @param std
   * @param speciality
   * @param aspect
   * @return
   */
  private TeachPlan getPersonTeachPlan(Student std, Speciality speciality, SpecialityAspect aspect) {
    TeachPlan plan = new TeachPlan();
    if (null != speciality) {
      plan.setSpeciality(speciality);
    } else {
      plan.setDepartment(std.getDepartment());
    }
    plan.setStd(std);
    Collection plans = getTeachPlans(plan, null, null, Boolean.FALSE, Boolean.FALSE);
    if (plans.isEmpty()) {
      return null;
    } else {
      return (TeachPlan) plans.iterator().next();
    }
  }

  /**
   * 查找专业培养计划<br>
   * 在查找不到的情况下,依次按照方向、专业、院系为null的培养计划<br>
   * 
   * @param std
   * @param speciality
   * @param aspect
   * @return
   */
  private TeachPlan getSpecialityPlan(Student std, Speciality speciality, SpecialityAspect aspect) {
    TeachPlan plan = new TeachPlan();
    // 不用设置专业方向
    if (null != speciality) {
      plan.setSpeciality(speciality);
    } else {
      // 惨了,只有院系
      plan.setDepartment(std.getDepartment());
    }
    plan.setEnrollTurn(std.getEnrollYear());
    plan.setStdType(std.getType());
    plan.setAspect(aspect);

    while (null != plan.getStdType()) {
      Collection plans = getTeachPlans(plan, null, null, Boolean.TRUE, Boolean.FALSE);
      if (plans.isEmpty()) {
        plan.setStdType((StudentType) plan.getStdType().getSuperType());
        continue;
      }
      TeachPlan find = null;
      for (Iterator iter = plans.iterator(); iter.hasNext();) {
        TeachPlan one = (TeachPlan) iter.next();
        // 模糊查询可能造成查出多个所在年级相似的计划
        if (!one.getEnrollTurn().equals(std.getEnrollYear())) {
          continue;
        }
        if (ObjectUtils.equals(one.getSpeciality(), speciality)
            && ObjectUtils.equals(one.getAspect(), aspect)) {
          find = one;
          break;
        }
      }
      return find;
    }
    return null;
  }

  public Collection getTeachPlans(TeachPlan plan, DataRealmLimit limit, List sortList,
      Boolean isSpecialityPlan, Boolean isExactly) {
    return teachPlanDAO.getTeachPlans(plan, limit, sortList, isSpecialityPlan, isExactly);
  }

  /**
   * 
   */
  public boolean isTeachPlanExists(TeachPlan plan) {
    if (ValidEntityPredicate.INSTANCE.evaluate(plan.getStd())) {
      return !getTeachPlans(plan.getSimpleIdCopy(), null, null, Boolean.FALSE, Boolean.TRUE).isEmpty();
    } else {
      return !getTeachPlans(plan.getSimpleIdCopy(), null, null, Boolean.TRUE, Boolean.TRUE).isEmpty();
    }
  }

  public boolean isDuplicate(TeachPlan plan) {
    Collection plans = null;
    if (ValidEntityPredicate.INSTANCE.evaluate(plan.getStd())) {
      plans = getTeachPlans(plan.getSimpleIdCopy(), null, null, Boolean.FALSE, Boolean.TRUE);
    } else {
      plans = getTeachPlans(plan.getSimpleIdCopy(), null, null, Boolean.TRUE, Boolean.TRUE);
    }
    if (plan.isVO()) {
      return !plans.isEmpty();
    } else {
      for (Iterator iter = plans.iterator(); iter.hasNext();) {
        TeachPlan one = (TeachPlan) iter.next();
        if (!one.getId().equals(plan.getId())) { return true; }
      }
      return false;
    }
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#getTeachPlans(com.shufe.model.std.Student)
   */
  public List getTeachPlans(Student std) {
    List plans = new ArrayList();
    TeachPlan firstPlan = getTeachPlan(std, Boolean.TRUE, null);
    if (null != firstPlan) {
      plans.add(firstPlan);
    }
    TeachPlan secondPlan = getTeachPlan(std, Boolean.FALSE, null);
    if (null != secondPlan) {
      plans.add(getTeachPlan(std, Boolean.FALSE, null));
    }
    return plans;
  }

  /**
   * @see TeachTaskService#getTeachPlans(String, String, TeachCalendar, boolean,int,Long)
   */
  public Collection getTeachPlansOfTaskGeneration(TeachPlan plan, TeachCalendar calendar, String stdTypeIdSeq,
      String departIdSeq, Boolean generated, boolean omitSmallTerm) {
    if (null == calendar) {
      return Collections.EMPTY_LIST;
    } else {
      DataRealmLimit limit = new DataRealmLimit(stdTypeIdSeq, departIdSeq);
      limit.setPageLimit(null);
      return filterTeachPlanOfTaskGen(calendar, generated, omitSmallTerm,
          getTeachPlans(plan, limit, null, Boolean.TRUE, Boolean.FALSE));
    }
  }

  public Collection getTeachPlansOfActive(TeachCalendar calendar, String stdTypeIdSeq, String departIdSeq,
      Boolean isSpeciality) {
    if (StringUtils.isEmpty(stdTypeIdSeq) || StringUtils.isEmpty(departIdSeq)) {
      return Collections.EMPTY_LIST;
    } else {
      EntityQuery planQuery = new EntityQuery(TeachPlan.class, "plan");
      planQuery.add(
          new Condition("plan.stdType.id in (:stdTypeIds)", SeqStringUtil.transformToLong(stdTypeIdSeq)));
      planQuery.add(
          new Condition("plan.department.id in (:departIds)", SeqStringUtil.transformToLong(departIdSeq)));
      List enrollTurns = calendarService.getActiveEnrollTurns(calendar, stdTypeIdSeq);
      if (CollectionUtils.isEmpty(enrollTurns)) { return Collections.EMPTY_LIST; }
      planQuery.add(new Condition("plan.enrollTurn in (:enrollTurns)", enrollTurns));
      if (Boolean.TRUE.equals(isSpeciality)) {
        planQuery.add(new Condition("plan.std is null"));
      } else {
        planQuery.add(new Condition("plan.std is not null"));
      }
      return utilDao.search(planQuery);
    }

  }

  /**
   * 过滤掉无效的培养计划
   * 
   * @param calendar
   * @param active
   * @param schemes
   * @return
   */
  private Collection filterTeachPlanOfTaskGen(TeachCalendar calendar, Boolean generated,
      boolean omitSmallTerm, Collection plans) {
    Vector tobeRemoved = new Vector();
    TermCalculator termCalc = new TermCalculator(calendarService, calendar);

    for (Iterator iter = plans.iterator(); iter.hasNext();) {
      TeachPlan plan = (TeachPlan) iter.next();
      int term = 0;
      try {
        // 忽略小学期 TODO
        term = termCalc.getTerm(plan.getStdType(), plan.getEnrollTurn(), new Boolean(omitSmallTerm));
      } catch (OnCampusTimeNotFoundException e) {
        info("without OnCampusTime" + e.getMessage());
        tobeRemoved.add(plan);
        continue;
      }
      debug("[getValidTeachPlans]calculate terms for [" + new TeachCommon(plan).toString() + "] result:"
          + term);

      if ((term < 1) || (term > plan.getTermsCount().intValue())) tobeRemoved.add(plan);
      boolean hasGenerated = isGenerated(calendar,plan);
      if (Boolean.TRUE.equals(generated)) {
        if (hasGenerated) {
          tobeRemoved.add(plan);
        }
      } else {
        // remove active
        if (!hasGenerated) {
          tobeRemoved.add(plan);
        }
      }
    }
    plans.removeAll(tobeRemoved);
    return plans;
  }

  private boolean isGenerated(TeachCalendar calendar, TeachPlan plan) {
    List<Object> rs = utilDao.searchHQLQuery("select count(*) from " + com.shufe.model.course.task.TeachTask.class.getName()
        + " task where task.fromPlan.id=" + plan.getId() + " and task.calendar.id=" + calendar.getId());
    return ((Number) rs.get(0)).intValue() > 0;
  }

  public List genTeachPlanOfSpecialityBy(Collection plans, String enrollTurn) {
    List genedPlans = new ArrayList(plans.size());
    for (Iterator iter = plans.iterator(); iter.hasNext();) {
      TeachPlan plan = (TeachPlan) iter.next();
      if (!plan.isStdTeachPlan()) {
        String orignEnrollTurn = plan.getEnrollTurn();
        plan.setEnrollTurn(enrollTurn);
        if (!isTeachPlanExists(plan)) {
          TeachPlan genPlan = new TeachPlan();
          genPlan.addCourseGroups(plan.getCourseGroups());
          genPlan.setStdType(plan.getStdType());
          genPlan.setDepartment(plan.getDepartment());
          genPlan.setSpeciality(plan.getSpeciality());
          genPlan.setAspect(plan.getAspect());
          genPlan.setEnrollTurn(enrollTurn);

          genPlan.setIsConfirm(Boolean.FALSE);
          genPlan.setCredit(plan.getCredit());
          genPlan.setCreditHour(plan.getCreditHour());
          genPlan.setRemark(plan.getRemark());
          genPlan.setTermsCount(plan.getTermsCount());
          genPlan.setTeacherNames(plan.getTeacherNames());

          genPlan.setCreateAt(new Date(System.currentTimeMillis()));
          genPlan.setModifyAt(new Date(System.currentTimeMillis()));
          genPlan.setStd(null);
          genedPlans.add(genPlan);
        }
        plan.setEnrollTurn(orignEnrollTurn);
      }
    }
    if (!genedPlans.isEmpty()) {
      utilDao.saveOrUpdate(genedPlans);
    }
    return genedPlans;
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#genTeachPlanForStd(java.lang.String,
   *      com.shufe.model.course.plan.TeachPlan)
   */
  public List genTeachPlanForStd(String stdIdSeq, TeachPlan targetPlan) {
    if (StringUtils.isNotEmpty(stdIdSeq)) {
      return genTeachPlanForStd(
          utilService.load(Student.class, "id", SeqStringUtil.transformToLong(stdIdSeq)), targetPlan);
    } else {
      return Collections.EMPTY_LIST;
    }
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#genTeachPlanForStd(java.lang.String,
   *      com.shufe.model.course.plan.TeachPlan)
   */
  public List genTeachPlanForStd(Collection stds, TeachPlan targetPlan) {
    if (!stds.isEmpty()) {
      List genPlans = new ArrayList();
      try {
        for (Iterator iter = stds.iterator(); iter.hasNext();) {
          Student std = (Student) iter.next();
          TeachPlan plan = (TeachPlan) targetPlan.clone();
          plan.setStd(std);
          if (!isTeachPlanExists(plan)) {
            EntityUtils.evictEmptyProperty(plan);
            saveTeachPlan(plan);
            genPlans.add(plan);
          }
        }
      } catch (Exception e) {
        info("exception occuured in genTeachPlanForStd:" + ExceptionUtils.getFullStackTrace(e));
      }
      return genPlans;
    } else return Collections.EMPTY_LIST;
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#getUnusedCourseTypes(com.shufe.model.course.plan.TeachPlan)
   */
  public Collection getUnusedCourseTypes(TeachPlan plan) {
    List allCourseTypes = baseCodeDao.getCodes(CourseType.class);
    List usedCourseTypes = teachPlanDAO.getUsedCourseTypes(plan);
    return CollectionUtils.subtract(allCourseTypes, usedCourseTypes);
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#getUsedCourseTypes(com.shufe.model.course.plan.TeachPlan)
   */
  public Collection getUsedCourseTypes(TeachPlan plan) {
    return teachPlanDAO.getUsedCourseTypes(plan);
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#getStdsWithoutPersonalPlan(TeachPlan)
   */
  public List getStdsWithoutPersonalPlan(TeachPlan plan) {
    // 查找一专业相符的学生
    Student std = new Student();
    std.setEnrollYear(plan.getEnrollTurn());
    // std.getType().setId(plan.getStdType().getId()); nullPointException
    std.setType(plan.getStdType());
    std.getDepartment().setId(plan.getDepartment().getId());
    if (null != plan.getSpeciality()) {
      std.getFirstMajor().setId(plan.getSpeciality().getId());
    }
    if (null != plan.getAspect()) {
      std.getFirstAspect().setId(plan.getAspect().getId());
    }
    List stds = studentService.findStudent(std);
    // 查找二专业相符的学生
    std.setDepartment(null);
    std.setFirstMajor(null);
    std.setFirstAspect(null);
    if (null != plan.getSpeciality()) {
      std.getSecondMajor().setId(plan.getSpeciality().getId());
    }
    if (null != plan.getAspect()) {
      std.getSecondAspect().setId(plan.getAspect().getId());
    }
    stds.addAll(studentService.findStudent(std));

    stds.removeAll(teachPlanDAO.getStdHasPlan(plan));
    return stds;
  }

  public void removeTeachPlan(Long planId) {
    teachPlanDAO.removeTeachPlan(planId);
  }

  public void saveTeachPlan(TeachPlan plan) {
    if (null == plan.getCreateAt()) {
      plan.setCreateAt(new Date(System.currentTimeMillis()));
    }
    plan.setModifyAt(new Date(System.currentTimeMillis()));
    EntityUtils.evictEmptyProperty(plan);
    if (null == plan.getIsConfirm()) {
      plan.setIsConfirm(Boolean.FALSE);
    }
    teachPlanDAO.saveTeachPlan(plan);
  }

  /**
   * rvice#filterCourses(com.shufe.model.course.plan.TeachPlan, java.util.List)
   */
  public Collection filterCourses(TeachPlan plan, List courses) {
    List coursesInPlan = new ArrayList();
    List planCourses = plan.getPlanCourses();

    for (Iterator iter = planCourses.iterator(); iter.hasNext();) {
      PlanCourse planCourse = (PlanCourse) iter.next();
      coursesInPlan.add(planCourse.getCourse());
    }
    return CollectionUtils.intersection(coursesInPlan, courses);
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#statOverallCreditHour(java.lang.Long)
   */
  public int statOverallCreditHour(Long planId) {
    return teachPlanDAO.statOverallCreditHour(planId);
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#statOverallCredits(java.lang.Long)
   */
  public float statOverallCredit(Long planId) {
    return teachPlanDAO.statOverallCredit(planId);
  }

  public CourseType getCourseTypeOfStd(String stdNo, Course course, CourseType courseType,
      MajorType majorType) {
    if (null == majorType) { return null; }
    Student std = (Student) utilService.load(Student.class, "code", stdNo).get(0);
    TeachPlan plan = getTeachPlan(std,
        ((majorType.getId().equals(MajorType.FIRST)) ? Boolean.TRUE : Boolean.FALSE), null);
    CourseType planCourseType = null;
    // 如果没有计划
    if (null == plan) {
      planCourseType = courseType;
    } else {
      planCourseType = groupDAO.getCourseType(plan.getId(), course.getId());
      // 计划里面没有该课程
      if (planCourseType == null) {
        for (Iterator iter = plan.getCourseGroups().iterator(); iter.hasNext();) {
          CourseGroup group = (CourseGroup) iter.next();
          // 不管里面有没有课程
          if (group.getCourseType().equals(courseType)) {
            planCourseType = courseType;
            break;
          }
        }
      }
    }

    if (null == planCourseType) {
      planCourseType = (CourseType) utilDao.get(CourseType.class, CourseType.PUBLIC_COURSID);
    }
    return planCourseType;
  }

  public List getCourseTypesOf(Long stdId, Boolean isFirstSpeciality) {
    Student std = (Student) utilDao.get(Student.class, stdId);
    TeachPlan plan = getTeachPlan(std, isFirstSpeciality, null);
    if (null == plan) {
      return Collections.EMPTY_LIST;
    } else {
      List courseTypes = new ArrayList();
      for (Iterator iter = plan.getCourseGroups().iterator(); iter.hasNext();) {
        CourseGroup group = (CourseGroup) iter.next();
        courseTypes.add(group.getCourseType());
      }
      return courseTypes;
    }

  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#getCreditByTerm(java.lang.Long,
   *      java.lang.Integer)
   */
  public Float getCreditByTerm(TeachPlan plan, int term) {
    IntRange termRange = new IntRange(1, plan.getTermsCount().intValue());
    if (!termRange.containsInteger(term)) {
      throw new RuntimeException("term out range");
    } else {
      float credit = 0;
      // calculate for each group
      for (Iterator iter = plan.getCourseGroups().iterator(); iter.hasNext();) {
        CourseGroup group = (CourseGroup) iter.next();
        String creditPerTerms = group.getCreditPerTerms();
        if (StringUtils.isNotEmpty(creditPerTerms)) {
          String[] credits = StringUtils.split(creditPerTerms, ",");
          if (credits.length < term) {
            continue;
          }
          // remember minus 1 for array index start with 0
          credit += NumberUtils.toFloat(credits[term - 1]);
        }
      }
      return new Float(credit);
    }
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#getPlanCourses(Long)
   */
  public List getPlanCourses(Long stdId) {
    return getPlanCourses((Student) utilDao.get(Student.class, stdId));
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#getPlanCourses(com.shufe.model.std.Student)
   */
  public List getPlanCourses(Student std) {
    List plans = getTeachPlans(std);
    List planCourses = new ArrayList();
    for (Iterator iter = plans.iterator(); iter.hasNext();) {
      TeachPlan plan = (TeachPlan) iter.next();
      planCourses.addAll(plan.getPlanCourses());
    }
    return planCourses;
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#getPlanCourseOfDegree(com.shufe.model.std.Student,
   *      com.ekingstar.eams.system.basecode.industry.MajorType)
   */
  public List getPlanCourseOfDegree(Student std, MajorType majorType) {
    TeachPlan plan = new TeachPlan();
    if (MajorType.FIRST.equals(majorType.getId())) {
      plan = getTeachPlan(std, std.getFirstMajor(), std.getFirstAspect(), null);
    } else {
      if (null == std.getSecondMajor() && null == std.getSecondAspect()) {
        plan = null;
      } else {
        plan = getTeachPlan(std, std.getSecondMajor(), std.getSecondAspect(), null);
      }
    }

    List degreePlanCourses = new ArrayList();
    for (Iterator iter = plan.getPlanCourses().iterator(); iter.hasNext();) {
      PlanCourse planCourse = (PlanCourse) iter.next();
      if (Boolean.TRUE == planCourse.getIsDegreeCourse()) {
        degreePlanCourses.add(planCourse);
        planCourse.getCourse().getName();
        plan.getDepartment().getName();
        plan.getSpeciality().getName();
      }
    }
    return degreePlanCourses;
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#updateConfirmState(java.util.List,
   *      java.lang.Boolean)
   */
  public void updateConfirmState(List plans, Boolean isConfirm) {
    for (Iterator iter = plans.iterator(); iter.hasNext();) {
      TeachPlan plan = (TeachPlan) iter.next();
      plan.setIsConfirm(isConfirm);
      plan.setModifyAt(new Date(System.currentTimeMillis()));
    }
    utilDao.saveOrUpdate(plans);
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#updateConfirmState(java.lang.Long[],
   *      java.lang.Boolean)
   */
  public void updateConfirmState(Long[] planIds, Boolean isConfirm) {
    List plans = utilService.load(TeachPlan.class, "id", planIds);
    updateConfirmState(plans, isConfirm);
  }

  public void updateTeachPlan(TeachPlan plan) {
    plan.setModifyAt(new Date(System.currentTimeMillis()));
    EntityUtils.evictEmptyProperty(plan);
    teachPlanDAO.updateTeachPlan(plan);
  }

  public PlanCourse getPlanCourse(Student std, Course course, Boolean isFirstSpeciality) {
    std = (Student) utilDao.get(Student.class, std.getId());
    course = (Course) utilDao.get(Course.class, course.getId());
    TeachPlan plan = getTeachPlan(std, isFirstSpeciality, null);
    PlanCourse planCourse = null;
    if (null != plan) {
      List planCourses = plan.getPlanCourses();
      for (Iterator iter = planCourses.iterator(); iter.hasNext();) {
        PlanCourse one = (PlanCourse) iter.next();
        if (one.getCourse().equals(course)) {
          planCourse = one;
        }
      }
    }
    if (null == planCourse) {
      planCourse = new PlanCourse();
      planCourse.setCourse(course);
      planCourse.getCourseGroup()
          .setCourseType((CourseType) utilDao.get(CourseType.class, CourseType.PUBLIC_COURSID));
    }
    return planCourse;
  }

  public List getTeachCalendarsOf(TeachPlan plan, List termList) {
    if (null == termList || termList.size() == 0) { return Collections.EMPTY_LIST; }

    OnCampusTime onCampusTime = calendarService.getOnCampusTime(plan.getStdType(), plan.getEnrollTurn());
    if (null == onCampusTime) { throw new OnCampusTimeNotFoundException(
        plan.getStdType().getName() + "  " + plan.getEnrollTurn()); }
    TeachCalendar calendar = onCampusTime.getEnrollCalendar();
    Collections.sort(termList);
    int maxTerm = ((Number) termList.get(termList.size() - 1)).intValue();
    if (maxTerm > plan.getTermsCount().intValue()) { throw new IllegalArgumentException(
        "plan has max term:" + plan.getTermsCount() + ". but need:" + termList); }

    List calendars = new ArrayList();
    for (int i = 1; i <= maxTerm; i++) {
      if (termList.contains(new Integer(i))) {
        calendars.add(calendar);
      }
      calendar = calendar.getNext();
      if (null == calendar) {
        break;
      }
    }
    return calendars;
  }

  public Collection statDepartCourse(TeachCalendar calendar, List stdTypes, List departs, PageLimit limit) {
    return teachPlanDAO.statDepartCourse(calendar, stdTypes, departs, limit);
  }

  public Collection statDepartCourseCount(TeachCalendar calendar, List stdTypes, List departs) {
    return teachPlanDAO.statDepartCourseCount(calendar, stdTypes, departs);
  }

  /**
   * @param teachPlanDAO
   *          The teachPlanDAO to set.
   */
  public void setTeachPlanDAO(TeachPlanDAO teachPlanDAO) {
    this.teachPlanDAO = teachPlanDAO;
  }

  /**
   * @see com.shufe.service.course.plan.TeachPlanService#setBaseCodeDao(com.ekingstar.eams.system.basecode.dao.BaseCodeDao)
   */
  public void setBaseCodeDao(BaseCodeDao baseCodeDao) {
    this.baseCodeDao = baseCodeDao;
  }

  /**
   * @param studentService
   *          The studentService to set.
   */
  public void setStudentService(StudentService studentService) {
    this.studentService = studentService;
  }

  /**
   * @param teachCalendarService
   *          The teachCalendarService to set.
   */
  public void setCalendarService(TeachCalendarService calendarService) {
    this.calendarService = calendarService;
  }

  public void setCourseGroupDAO(CourseGroupDAO groupDAO) {
    this.groupDAO = groupDAO;
  }

  public List getTeachPlan(Speciality speciality, String stdTypeCode, String enrollTurn) {
    return teachPlanDAO.getTeachPlan(speciality, stdTypeCode, enrollTurn);
  }
}

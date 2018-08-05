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
 * chaostone             2006-3-26            Created
 *  
 ********************************************************************************/

package com.shufe.service.course.plan.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.dao.course.plan.CourseGroupDAO;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.service.BasicService;
import com.shufe.service.course.plan.CourseGroupService;
import com.shufe.service.course.plan.TeachPlanService;

/**
 * 培养计划课程组服务实现类
 * 
 * @author chaostone
 */
public class CourseGroupServiceImpl extends BasicService implements CourseGroupService {

  private TeachPlanService teachPlanService;

  private CourseGroupDAO courseGroupDAO;

  /**
   * @see com.shufe.service.course.plan.CourseGroupService#getCourseGroup(java.lang.Long)
   */
  public CourseGroup getCourseGroup(Long id) {
    if (null != id) return courseGroupDAO.getCourseGroup(id);
    else return null;
  }

  /**
   * @see com.shufe.service.course.plan.CourseGroupService#removeCourseGroup(java.lang.Long)
   */
  public void removeCourseGroup(Long groupId) {
    if (null != groupId) courseGroupDAO.removeCourseGroup(groupId);
  }

  /**
   * @see com.shufe.service.course.plan.CourseGroupService#removeCourseGroup(java.lang.Long,
   *      java.lang.Long)
   */
  public void removeCourseGroup(Long groupId, Long planId) {
    TeachPlan plan = teachPlanService.getTeachPlan(planId);
    CourseGroup group = getCourseGroup(groupId);
    removeCourseGroup(group, plan);
  }

  public void removeCourseGroup(CourseGroup group, TeachPlan plan) {
    // remove the group from the plan
    plan.removeCourseGroup(group);
    teachPlanService.saveTeachPlan(plan);
      removeCourseGroup(group.getId());
  }

  /**
   * @see com.shufe.service.course.plan.CourseGroupService#saveCourseGroup(com.shufe.model.course.plan.CourseGroup)
   */
  public void saveCourseGroup(CourseGroup group) {
    // TODO for deep check
    if (null != group) {
      EntityUtils.evictEmptyProperty(group);
      courseGroupDAO.saveCourseGroup(group);
    }
  }

  /**
   * @see com.shufe.service.course.plan.CourseGroupService#saveCourseGroup(com.shufe.model.course.plan.CourseGroup,
   *      com.shufe.model.course.plan.TeachPlan)
   */
  public void updateCourseGroup(CourseGroup group, TeachPlan plan) {
    CourseGroup savedGroup = getCourseGroup(group.getId());
    EntityUtils.merge(savedGroup, group);
    saveCourseGroup(savedGroup);
  }

  /**
   * @see com.shufe.service.course.plan.CourseGroupService#addCourseGroup(com.shufe.model.course.plan.CourseGroup,
   *      com.shufe.model.course.plan.TeachPlan)
   */
  public void addCourseGroup(CourseGroup group, TeachPlan plan) {
    if (group.isVO()) {
      saveCourseGroup(group);
    }
    plan.addCourseGroup(group);
    teachPlanService.saveTeachPlan(plan);
  }

  /**
   * @see com.shufe.service.course.plan.CourseGroupService#updateCourseGroup(com.shufe.model.course.plan.CourseGroup)
   */
  public void updateCourseGroup(CourseGroup group) {
    // TODO for deep check
    if (null != group) courseGroupDAO.updateCourseGroup(group);
  }

  /**
   * 为非学生培养计划的克隆该组.<br>
   * 如果课程组所在培养计划(groupPlan)是学生的培养计划，<br>
   * 则为所有相关的培养计划克隆该组.<br>
   * 
   * @param group
   * @param groupPlan
   */
  public CourseGroup keepUniqueGroupFor(CourseGroup group, TeachPlan groupPlan) {
    if (!groupPlan.equals(group.getTeachPlan())) {
      CourseGroup sameTypeGroup = groupPlan.getCourseGroup(group.getCourseType());
      if (null != sameTypeGroup) {
        return sameTypeGroup;
      } else {
        throw new RuntimeException(
            "group[" + group.getId() + "]was not associated with teachPlan [" + groupPlan.getId() + "]");
      }
    }
    saveCourseGroup(group);
    return group;
  }

  /**
   * @see com.shufe.service.course.plan.CourseGroupService#cloneGroupFor(com.shufe.model.course.plan.CourseGroup,
   *      java.lang.Long[])
   */
  public CourseGroup cloneGroupFor(CourseGroup group, Collection plans) {
    if (null != plans && plans.size() != 0 && null != group && group.getTeachPlan() !=null) {
      CourseGroup newGroup = (CourseGroup) group.clone();
      courseGroupDAO.saveCourseGroup(newGroup);
      for (Iterator iter = plans.iterator(); iter.hasNext();) {
        TeachPlan plan = (TeachPlan) iter.next();
        if (plan.getCourseGroups().contains(group)) {
          plan.removeCourseGroup(group);
          plan.addCourseGroup(newGroup);
          utilService.saveOrUpdate(plan);
        }
      }
      // for hibernate bug HHH-2634
      utilDao.refresh(newGroup);
      return newGroup;
    } else return group;
  }

  /**
   * @see com.shufe.service.course.plan.CourseGroupService#getCourseGroupsOfStd(CourseType,
   *      com.shufe.model.course.plan.TeachPlan)
   */
  public List getCourseGroupsOfStd(CourseGroup group, TeachPlan specialityPlan) {
    return courseGroupDAO.getCourseGroupsOfStd(group, specialityPlan);
  }

  /**
   * @param teachPlanDAO
   *          The teachPlanDAO to set.
   */
  public void setTeachPlanService(TeachPlanService teachPlanService) {
    this.teachPlanService = teachPlanService;
  }

  public TeachPlanService getTeachPlanService() {
    return teachPlanService;
  }

  /**
   * @param courseGroupDAO
   *          The courseGroupDAO to set.
   */
  public void setCourseGroupDAO(CourseGroupDAO courseGroupDAO) {
    this.courseGroupDAO = courseGroupDAO;
  }

}

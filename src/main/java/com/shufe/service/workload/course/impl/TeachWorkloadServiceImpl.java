//$Id: TeachWorkloadServiceImpl.java,v 1.19 2007/01/10 06:17:24 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-21         Created
 *  
 ********************************************************************************/

package com.shufe.service.workload.course.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.workload.course.TeachWorkloadDAO;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.workload.course.TeachCategory;
import com.shufe.model.workload.course.TeachModulus;
import com.shufe.model.workload.course.TeachWorkload;
import com.shufe.service.BasicService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.service.workload.course.TeachWorkloadService;

/**
 * @author hj
 * 
 */
public class TeachWorkloadServiceImpl extends BasicService implements TeachWorkloadService {
    
    private TeachWorkloadDAO teachWorkloadDAO;
    
    private TeachCalendarService teachCalendarService;
    
    /**
     * @param teachCalendarService
     *            The teachCalendarService to set.
     */
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadService#affirmType(java.lang.String,
     *      java.lang.String)
     */
    public void affirmType(String affirmType, String teachWorkloadIds, boolean b) {
        teachWorkloadDAO.affirmType(affirmType, teachWorkloadIds, b);
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadService#affirmAll(java.lang.String,
     *      java.lang.String, boolean)
     */
    public void affirmAll(String departmentIds, String affirmType, boolean b) {
        teachWorkloadDAO.updateAffirmAll(departmentIds, affirmType, b);
    }
    
    /**
     * 得到所有的当前学期的教学日历
     * 
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getCurrenTeachCalendars()
     */
    public Set getCurrenTeachCalendars(String stdTypeSeq) {
        if (StringUtils.isBlank(stdTypeSeq)) {
            return Collections.EMPTY_SET;
        }
        stdTypeSeq = "," + stdTypeSeq + ",";
        Set teachCalendars = new HashSet();
        List currenCalendars = teachCalendarService.getCurTeachCalendars();
        for (Iterator iter = currenCalendars.iterator(); iter.hasNext();) {
            TeachCalendar element = (TeachCalendar) iter.next();
            if (stdTypeSeq.indexOf("," + element.getStudentType().getId() + ",") != -1) {
                teachCalendars.add(element);
            }
        }
        return teachCalendars;
    }
    
    /**
     * 根据学生类别的collection 得到教学日历的list
     * 
     * @see com.shufe.service.workload.course.TeachWorkloadService#getTeachCalendar(java.util.Collection)
     */
    public List getTeachCalendar(Collection studentTypeCollect) {
        return teachWorkloadDAO.getTeachCalendarOfTeacher(studentTypeCollect);
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadService#getLastCalendars(java.util.List)
     */
    public List getPreviousCalendars(Collection currenCalendarList) {
        List tempList = new ArrayList();
        for (Iterator iter = currenCalendarList.iterator(); iter.hasNext();) {
            TeachCalendar element = (TeachCalendar) iter.next();
            tempList.add(element.getPrevious());
        }
        return tempList;
    }
    
    /**
     * 根据学生类别collection 得到所有的历史学期的教学日历.
     * 
     * @see com.shufe.service.workload.course.TeachWorkloadService#getHistoryCalendarByStdList(java.util.Collection)
     */
    public Collection getHistoryCalendarByStdList(Collection stdTypeList) {
        if (null != stdTypeList && stdTypeList.size() < 1) {
            return Collections.EMPTY_LIST;
        }
        List allTeachCalendas = getTeachCalendar(stdTypeList);
        String stdSeq = "";
        for (Iterator iter = stdTypeList.iterator(); iter.hasNext();) {
            StudentType element = (StudentType) iter.next();
            stdSeq += element.getId() + ",";
        }
        Set currentList = getCurrenTeachCalendars(stdSeq);
        return CollectionUtils.subtract(allTeachCalendas, currentList);
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadService#batchDeleteTeachWorkload(java.lang.String,
     *      java.lang.String)
     */
    public void batchDeleteTeachWorkload(String departmentSeq, String stdTypeSeq) {
        Set currentCalendars = getCurrenTeachCalendars(stdTypeSeq);
        String calendarSeq = "";
        for (Iterator iter = currentCalendars.iterator(); iter.hasNext();) {
            TeachCalendar element = (TeachCalendar) iter.next();
            if (iter.hasNext()) {
                calendarSeq += element.getId() + ",";
            } else {
                calendarSeq += element.getId();
            }
        }
        teachWorkloadDAO.batchDeleteTeachWorkload(departmentSeq, stdTypeSeq, calendarSeq);
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadService#batchDeleteBySeq(java.util.Map)
     */
    public void batchDeleteBySeq(Map conditionsSeq) {
        teachWorkloadDAO.batchDeleteBySeq(conditionsSeq);
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadService#getTeachWorkloadAffirmTaskIds(com.shufe.model.workload.course.TeachWorkload,
     *      java.lang.String, java.lang.String)
     */
    public List getTeachWorkloadAffirmTaskIds(TeachWorkload teachWorkload, String stdTypeIdSeq,
            String departIdSeq, String calendarIdSeq) {
        return teachWorkloadDAO.getTeachWorkloadAffirmedTaskIds(teachWorkload, stdTypeIdSeq,
                departIdSeq, calendarIdSeq);
    }
    
    public void batchDeleteByTaskCollection(Collection taskCollection) {
        teachWorkloadDAO.batchDeleteByTaskCollection(taskCollection);
    }
    
    public List getIdListBycondition(TeachWorkload teachWorkload, String departmentSeq,
            String stdTypeSeq) {
        return teachWorkloadDAO.getIdListBycondition(teachWorkload, departmentSeq, stdTypeSeq);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.TeachWorkloadService#getStatisticsObjectList(com.shufe.model.workload.TeachWorkload,
     *      java.lang.String, java.lang.String, java.util.Collection, java.lang.String,
     *      java.lang.String)
     */
    public List getStatisticsObjectList(TeachWorkload teachWorkload, String stdTypeIdSeq,
            String departIdSeq, String calendarIdSeq, String age1, String age2) {
        if (StringUtils.isBlank(stdTypeIdSeq) || StringUtils.isBlank(departIdSeq)
                || StringUtils.isBlank(calendarIdSeq)) {
            return Collections.EMPTY_LIST;
        }
        return teachWorkloadDAO.getExampleTeachWorkloads(teachWorkload, stdTypeIdSeq, departIdSeq,
                calendarIdSeq, age1, age2).list();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.TeachWorkloadService#getStatisticsObjectPagi(com.shufe.model.workload.TeachWorkload,
     *      java.lang.String, java.lang.String, java.util.Collection, java.lang.String,
     *      java.lang.String, int, int)
     */
    public Pagination getStatisticsObjectPagi(TeachWorkload teachWorkload, String stdTypeIdSeq,
            String departIdSeq, String calendarIdSeq, String age1, String age2, int pageNo,
            int pageSize) {
        if (StringUtils.isBlank(stdTypeIdSeq) || StringUtils.isBlank(departIdSeq)
                || StringUtils.isBlank(calendarIdSeq)) {
            Result result = new Result(0, new ArrayList());
            return new Pagination(pageNo, pageSize, result);
        }
        return teachWorkloadDAO.dynaSearch(teachWorkloadDAO.getExampleTeachWorkloads(teachWorkload,
                stdTypeIdSeq, departIdSeq, calendarIdSeq, age1, age2), pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadService#statTeachWorkload(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public Map statTeachWorkload(String stdTypeIdSeq, String departIdSeq, String calendarIdSeq) {
        Map returnMap = new HashMap();
        // 查询到的权限内的已经确认的工作量教学任务id串
        List affirmTaskIds = getTeachWorkloadAffirmTaskIds(new TeachWorkload(), stdTypeIdSeq,
                departIdSeq, calendarIdSeq);
        // 用户权限内所能查询到的教学任务
        List teachTaskList = teachWorkloadDAO.getTasksOfTeacherByChargeDepart(stdTypeIdSeq,
                departIdSeq, calendarIdSeq);
        int successNum = 0;
        int statisticTaskNum = 0;
        int noArrangeInfoNum = 0;
        int noModuleNum = 0;
        // 在查询到的教学任务里面去掉已经确认过的教学任务
        List newTeachTasks = new ArrayList();
        for (Iterator iter = teachTaskList.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (!affirmTaskIds.contains(task.getId())) {
                newTeachTasks.add(task);
            }
        }
        teachTaskList = newTeachTasks;
        // 删除哪些还没有确认的教学工作量
        teachWorkloadDAO.batchDeleteByTaskCollection(teachTaskList);
        String noTeachModulus = "";
        String noArrangeInfos = "";
        Long[] stdTypeIds = SeqStringUtil.transformToLong(stdTypeIdSeq);
        List teachModulus = utilService.loadAll(TeachModulus.class);
        List categorys = utilService.load(TeachCategory.class, "studentType.id", stdTypeIds);
        // 开始计算
        List teachWorkloads = new ArrayList();
        
        for (Iterator taskIter = teachTaskList.iterator(); taskIter.hasNext();) {
            TeachTask teachTask = (TeachTask) taskIter.next();
            if (affirmTaskIds.contains(teachTask.getId())) {
                continue;
            }
            List teachers = teachTask.getArrangeInfo().getTeachers();
            if (teachers.isEmpty()) {
                continue;
            }
            /* String[] teacherId = StringUtils.split(teacherIdc,","); */
            for (Iterator iter = teachers.iterator(); iter.hasNext();) {
                TeachWorkload teachWorkload = null;
                Teacher teacher = (Teacher) iter.next();
                try {
                    teachWorkload = buildTeachWorkload(teacher, teachTask, categorys, teachModulus);
                } catch (Exception e) {
                    e.printStackTrace();
                    info("NO:" + teacher.getCode() + "teachTaskNo:" + teachTask.getId()
                            + ":baseCode errors");
                    noArrangeInfos += "课程序号:" + teachTask.getSeqNo() + "&课程名称:"
                            + teachTask.getCourse().getName() + ",";
                    noArrangeInfoNum++;
                    continue;
                }
                if (teachWorkload.getTeachModulus() == null) {
                    noTeachModulus += "课程序号:" + teachTask.getSeqNo() + "&课程名称:"
                            + teachTask.getCourse().getName() + ",";
                    noModuleNum++;
                    continue;
                }
                successNum++;
                teachWorkloads.add(teachWorkload);
            }
            statisticTaskNum++;
        }
        returnMap.put("successNum", new Integer(successNum));
        returnMap.put("statisticTaskNum", new Integer(statisticTaskNum));
        returnMap.put("noArrangeInfoNum", new Integer(noArrangeInfoNum));
        returnMap.put("noModuleNum", new Integer(noModuleNum));
        if (StringUtils.isNotBlank(noTeachModulus)) {
            returnMap.put("noTeachModulus", noTeachModulus);
        }
        if (StringUtils.isNotBlank(noArrangeInfos)) {
            returnMap.put("noArrangeInfos", noArrangeInfos);
        }
        utilService.saveOrUpdate(teachWorkloads);
        return returnMap;
    }
    
    /**
     * 根据教师教学任务 教学种类s，教学系数s得到一个教学工作量 FIXME 写死的体教部
     */
    public TeachWorkload buildTeachWorkload(Teacher teacher, TeachTask teachTask,
            List teachCatgoryList, List teachModulList) {
        TeachWorkload teachWorkload = new TeachWorkload(teacher, teachTask);
        CourseCategory courseCategory = new CourseCategory(teachTask.getRequirement()
                .getCourseCategory().getId());
        teachWorkload.setCourseCategory(courseCategory);
        teachWorkload.setTeachCategory(getCategory(teachTask.getTeachClass().getStdType(),
                teachTask.getCourseType().getId(), teachCatgoryList));
        StringBuffer classIds = new StringBuffer();
        StringBuffer classNames = new StringBuffer();
        Set classSet = null == teachTask.getTeachClass().getAdminClasses() ? new HashSet()
                : teachTask.getTeachClass().getAdminClasses();
        for (Iterator iter = classSet.iterator(); iter.hasNext();) {
            AdminClass element = (AdminClass) iter.next();
            classIds.append(element.getId());
            classNames.append(element.getName());
            if (iter.hasNext()) {
                classIds.append(",");
                classNames.append(",");
            }
        }
        teachWorkload.setClassIds(classIds.toString());
        teachWorkload.setClassNames(classNames.toString());
        Integer studentNumber = teachTask.getTeachClass().getStdCount();
        teachWorkload.setStudentNumber(studentNumber);
        TeachModulus teachModulus = getModulu(teachTask.getTeachClass().getStdType(),
                courseCategory.getId(), studentNumber, teachModulList);
        teachWorkload.setTeachModulus(teachModulus);
        ArrangeInfo arrangeInfo = teachTask.getArrangeInfoOfTeacher(teacher);
        Float weeks = new Float(arrangeInfo.getWeeks().floatValue());
        teachWorkload.setWeeks(weeks);
        Float totleCourse = new Float(arrangeInfo.getOverallUnits().floatValue());
        teachWorkload.setTotleCourses(totleCourse);
        if (null != teachModulus) {
            Float totleWorkload = new Float(teachModulus.getModulusValue().floatValue()
                    * totleCourse.floatValue());
            teachWorkload.setTotleWorkload(totleWorkload);
        }
        Float numOfWeek = arrangeInfo.getWeekUnits();
        if (null == numOfWeek && null != weeks && weeks.intValue() != 0) {
            numOfWeek = new Float(totleCourse.intValue() / weeks.intValue());
        }
        teachWorkload.setClassNumberOfWeek(null == numOfWeek ? new Float(0) : numOfWeek);
        return teachWorkload;
    }
    
    /**
     * 根据条件从所有的系数中找到合适的 如果没有返回null
     * 
     * @param stdTypeId
     * @param courseCategroyId
     * @param stdNumber
     * @param modules
     * @return
     */
    public static TeachModulus getModulu(StudentType stdType, Long courseCategroyId,
            Integer stdNumber, List modules) {
        TeachModulus teachModulus = null;
        while (null != stdType) {
            for (Iterator iter = modules.iterator(); iter.hasNext();) {
                TeachModulus element = (TeachModulus) iter.next();
                if (null != courseCategroyId
                        && courseCategroyId.equals(element.getCourseCategory().getId())
                        && null != stdNumber && stdNumber.compareTo(element.getMinPeople()) >= 0
                        && stdNumber.compareTo(element.getMaxPeople()) < 0 && null != stdType
                        && element.getStudentType().getId().equals(stdType.getId())) {
                    teachModulus = element;
                    stdType = null;
                    break;
                }
            }
            if (null != stdType) {
                stdType = (StudentType) stdType.getSuperType();
            }
        }
        return teachModulus;
    }
    
    /**
     * 根据教学性质得到相应的合适的教学性质
     * 
     * @param stdTypeId
     * @param courseTypeId
     * @param teachCategorys
     * @return
     */
    public static TeachCategory getCategory(StudentType stdType, Long courseTypeId,
            List teachCategorys) {
        TeachCategory teachCategory = null;
        while (null != stdType) {
            for (Iterator iter = teachCategorys.iterator(); iter.hasNext();) {
                TeachCategory element = (TeachCategory) iter.next();
                if (null != courseTypeId && courseTypeId.equals(element.getCourseType().getId())
                        && null != stdType
                        && element.getStudentType().getId().equals(stdType.getId())) {
                    teachCategory = element;
                    stdType = null;
                    break;
                }
            }
            if (null != stdType)
                stdType = (StudentType) stdType.getSuperType();
        }
        return teachCategory;
    }
    
    public void setTeachWorkloadDAO(TeachWorkloadDAO teachWorkloadDAO) {
        this.teachWorkloadDAO = teachWorkloadDAO;
    }
    
}

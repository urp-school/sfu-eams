//$Id: TeachWorkloadStatServiceImpl.java,v 1.19 2007/01/21 10:47:21 cwx Exp $
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
 * chenweixiong              2005-11-28         Created
 *  
 ********************************************************************************/

package com.shufe.service.workload.course.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.TeacherType;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.workload.course.TeachWorkloadStatDAO;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.util.stat.StatUtils;
import com.shufe.service.workload.course.TeachWorkloadStatService;

public class TeachWorkloadStatServiceImpl extends BasicService implements TeachWorkloadStatService {
    
    private TeachWorkloadStatDAO teachWorkloadStatDAO;
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.TeachWorkloadStatService#getAllSdtTypeContainSelf(java.lang.String)
     */
    public String getAllSdtTypeContainSelf(String studentTypeIds, String dataRealIdSeq) {
        if (StringUtils.isBlank(studentTypeIds)) {
            return "0";
        }
        List stdTypeList = utilService.load(StudentType.class, "id", SeqStringUtil
                .transformToLong(studentTypeIds));
        StringBuffer tempStdIdSeq = new StringBuffer();
        for (int i = 0; i < stdTypeList.size(); i++) {
            StudentType studentType = (StudentType) stdTypeList.get(i);
            tempStdIdSeq.append(studentType.getId()).append(",");
            for (Iterator iter = studentType.getDescendants().iterator(); iter.hasNext();) {
                StudentType element = (StudentType) iter.next();
                if (("," + dataRealIdSeq + ",").indexOf("," + element.getId() + ",") != -1) {
                    tempStdIdSeq.append(element.getId()).append(",");
                }
            }
        }
        if (tempStdIdSeq.length() > 0) {
            tempStdIdSeq.deleteCharAt(tempStdIdSeq.length() - 1);
        }
        return tempStdIdSeq.toString();
    }
    
    /*
     * (non-Javadoc)
     * 
     */
    public Map getTeacherNumberByConditions(String departIdSeq, String stdTypeIdSeq,
            Long teacherTypeId, TeachCalendar teachCalendar) {
        List tempList = teachWorkloadStatDAO.getTeacherNumberByConditions(departIdSeq,
                stdTypeIdSeq, teacherTypeId, teachCalendar);
        Map m = new HashMap();
        for (Iterator iter = tempList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            Department deparment = (Department) element[1];
            TeacherTitle teacherTitle = (TeacherTitle) element[2];
            String key = deparment.getId() + "-";
            if (null == teacherTitle) {
                m.put(key + "null", element[0]);
                StatUtils.setValueToMap("0-null", element[0], "integer", m);
            } else {
                m.put(key + teacherTitle.getId(), element[0]);
                StatUtils.setValueToMap("0-" + teacherTitle.getId(), element[0], "integer", m);
            }
            StatUtils.setValueToMap(key + "0", element[0], "integer", m);
            StatUtils.setValueToMap("0-0", element[0], "integer", m);
        }
        return m;
    }
    
    /**
     * 
     */
    public Map getTotalWorkloadByConditions(String deparIdSeq, String stdTypeIdSeq,
            Long teacherTypeId, TeachCalendar teachCalendar) {
        List tempList = teachWorkloadStatDAO.getTotalWorkloadByConditions(deparIdSeq, stdTypeIdSeq,
                teacherTypeId, teachCalendar);
        Map m = new HashMap();
        for (Iterator iter = tempList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            Department deparment = (Department) element[1];
            TeacherTitle teacherTitle = (TeacherTitle) element[2];
            String key = deparment.getId() + "-";
            if (null == teacherTitle) {
                m.put(key + "null", element[0]);
                StatUtils.setValueToMap("0-null", element[0], "float", m);
            } else {
                m.put(key + teacherTitle.getId(), element[0]);
                StatUtils.setValueToMap("0-" + teacherTitle.getId(), element[0], "float", m);
            }
            StatUtils.setValueToMap(key + "0", element[0], "float", m);
            StatUtils.setValueToMap("0-0", element[0], "float", m);
        }
        return m;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.TeachWorkloadStatService#getHunmanNumberAndTotleWorkloadByCalendarAndStudentType(com.shufe.model.system.calendar.TeachCalendar,
     *      java.util.Collection)
     */
    public List getNumberAndWorkloadOfTitle(TeachCalendar teachCalendar, String stdTypeIdSeq) {
        return teachWorkloadStatDAO.getNumberAndWorkloadOfTitle(teachCalendar, stdTypeIdSeq);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.TeachWorkloadStatService#getPeopleOfDepartment(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.lang.String, boolean)
     */
    public List getPeopleOfDepartment(TeachCalendar teachCalendar, String studentTypeIds,
            String departmentIds) {
        return teachWorkloadStatDAO.getPeopleOfDepartment(teachCalendar, studentTypeIds,
                departmentIds);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.TeachWorkloadStatService#getCourseNumberOfDepartment(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public Map getCourseTypesOfDepartment(TeachCalendar teachCalendar, String stdTypeIdSeq,
            String collegeIdSeq) {
        List courseTypesList = teachWorkloadStatDAO.getCourseNumberOfDepartment(teachCalendar,
                stdTypeIdSeq, collegeIdSeq);
        Map couseTypeDepartMap = new HashMap();
        for (Iterator iter = courseTypesList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            CourseType courseType = (CourseType) element[2];
            Department department = (Department) element[1];
            if (null == courseType) {
                couseTypeDepartMap.put(department.getId() + "-null", element[0]);
                StatUtils.setValueToMap("0-null", element[0], "integer", couseTypeDepartMap);
            } else {
                couseTypeDepartMap.put(department.getId() + "-" + courseType.getId(), element[0]);
                StatUtils.setValueToMap("0-" + courseType.getId(), element[0], "integer",
                        couseTypeDepartMap);
            }
            StatUtils.setValueToMap(department.getId() + "-0", element[0], "integer",
                    couseTypeDepartMap);
            StatUtils.setValueToMap("0-0", element[0], "integer", couseTypeDepartMap);
        }
        return couseTypeDepartMap;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.TeachWorkloadStatService#getRateOfTitleAndStdType(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.lang.String)
     */
    public Map getTitleAndStdTypesNo(TeachCalendar teachCalendar, String departIdSeq,
            String stdIdSeq) {
        List tempList = teachWorkloadStatDAO.getTitleAndStdTypes(teachCalendar, departIdSeq,
                stdIdSeq);
        Map m = new HashMap();
        for (Iterator iter = tempList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            StudentType stdType = (StudentType) element[1];
            TeacherTitle teacherTitle = (TeacherTitle) element[2];
            if (null == teacherTitle) {
                m.put(stdType.getId() + "-null", element[0]);
                StatUtils.setValueToMap("0-null", element[0], "integer", m);
            } else {
                m.put(stdType.getId() + "-" + teacherTitle.getId(), element[0]);
                StatUtils.setValueToMap("0-" + teacherTitle.getId(), element[0], "integer", m);
            }
            StatUtils.setValueToMap(stdType.getId() + "-0", element[0], "integer", m);
            StatUtils.setValueToMap("0-0", element[0], "integer", m);
        }
        return m;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.TeachWorkloadStatService#getWorkloadByStdTypeAndTeacherType(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.util.List, java.util.List)
     */
    public Map getWorkloadByDepartAndTeacherType(TeachCalendar teachCalendar,
            String studentTypeIds, String departmentIdSeq, Boolean isCaculate) {
        List tempList = teachWorkloadStatDAO.getWorkloadOfTeacherTypeAndDepart(teachCalendar,
                studentTypeIds, departmentIdSeq, isCaculate);
        Map requiredMap = new HashMap();
        for (Iterator iter = tempList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            Department college = (Department) element[1];
            TeacherType teacherType = (TeacherType) element[2];
            if (null == teacherType) {
                requiredMap.put(college.getId() + "-null", element[0]);
                StatUtils.setValueToMap("0-null", element[0], "float", requiredMap);
            } else {
                requiredMap.put(college.getId() + "-" + teacherType.getId(), element[0]);
                StatUtils.setValueToMap("0-" + teacherType.getId(), element[0], "float",
                        requiredMap);
            }
            StatUtils.setValueToMap(college.getId() + "-0", element[0], "float", requiredMap);
            StatUtils.setValueToMap("0-0", element[0], "float", requiredMap);
        }
        return requiredMap;
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getWorkloadByDepartmentAndStudent(java.util.List,
     *      java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
     */
    public Map getWorkloadByDataRealm(String departIdSeq, String stdTypeIdSeq,
            TeachCalendar teachCalendar) {
        List tempList = teachWorkloadStatDAO.getWorkloadByDataRealm(departIdSeq, stdTypeIdSeq,
                teachCalendar);
        return returnStdAndDeartmentMap(tempList, "float");
    }
    
    /**
     * @param tempList
     * @param sumDataType
     *            TODO
     * @return
     */
    private Map returnStdAndDeartmentMap(List tempList, String sumDataType) {
        Map returnMap = new HashMap();
        for (Iterator iter = tempList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            Department college = (Department) element[1];
            StudentType stdType = (StudentType) element[2];
            String key = college.getId() + "-" + stdType.getId();
            StatUtils.setValueToMap(key, element[0], sumDataType, returnMap);
            StatUtils.setValueToMap(college.getId() + "-0", element[0], sumDataType, returnMap);
            StatUtils.setValueToMap("0-" + stdType.getId(), element[0], sumDataType, returnMap);
            StatUtils.setValueToMap("0-0", element[0], sumDataType, returnMap);
        }
        return returnMap;
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getWorkloadByDataRealm(java.lang.String,
     *      java.lang.String, java.util.Collection)
     */
    public Map getWorkloadByDataRealm(String departIdSeq, String stdTypeIdSeq,
            Collection teachCalendars) {
        List tempList = teachWorkloadStatDAO.getWorkloadByDataRealm(departIdSeq, stdTypeIdSeq,
                teachCalendars);
        Map returnMap = returnStdAndDeartmentMap(tempList, "float");
        return returnMap;
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getWorkloadByTeacherAndCalendar(java.lang.String,
     *      java.lang.String, java.util.Collection)
     */
    public Map getWorkloadByTeacherAndCalendar(String departIdSeq, String stdTypeIdSeq,
            Collection teachCalendars) {
        List tempList = teachWorkloadStatDAO.getWorkloadOfTeacherAndCalendar(departIdSeq,
                stdTypeIdSeq, teachCalendars);
        Map returnMap = new HashMap();
        Long tempId = new Long(0);
        List teacherList = new ArrayList();
        for (Iterator iter = tempList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            Teacher teacher = (Teacher) element[1];
            TeachCalendar calendar = (TeachCalendar) element[2];
            if (!tempId.equals(teacher.getId())) {
                tempId = teacher.getId();
                teacherList.add(teacher);
            }
            String key = teacher.getId() + "-" + calendar.getId();
            StatUtils.setValueToMap(key, element[0], "float", returnMap);
            StatUtils.setValueToMap(teacher.getId() + "-0", element[0], "float", returnMap);
            StatUtils.setValueToMap("0-" + calendar.getId(), element[0], "float", returnMap);
            StatUtils.setValueToMap("0-0", element[0], "float", returnMap);
        }
        returnMap.put("teachers", teacherList);
        return returnMap;
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getTotalAndRegisterWorkload(java.util.List,
     *      java.lang.Long)
     */
    public List getTotalAndRegisterWorkload(List collegeList, Long teachCalendarId) {
        return teachWorkloadStatDAO.getTotalAndRegisterWorkload(collegeList, teachCalendarId);
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getEduDegreeAndTitle(java.util.List,
     *      java.lang.String, java.lang.Long, java.lang.Integer, String)
     */
    public Map getEduDegreeAndTitle(String departmentIds, String propertyName, Long calendarId,
            Integer age, String stdTypeIdSeq) {
        List tempList = teachWorkloadStatDAO.getEduDegreeAndTitle(departmentIds, propertyName,
                calendarId, age, stdTypeIdSeq);
        Map m = new HashMap();
        for (Iterator iter = tempList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            if (null == element[1]) {
                m.put("null", element[0]);
            } else {
                m.put(((Entity) element[1]).getEntityId().toString(), element[0]);
            }
        }
        return m;
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getEduDegreeAndTitleInTeacher(java.util.List,
     *      java.lang.String, java.lang.Integer)
     */
    public Map getEduDegreeAndTitleInTeacher(String propertyName, String departmentIds, Integer age) {
        List list = teachWorkloadStatDAO.getEduDegreeAndTitleInTeacher(propertyName, departmentIds,
                age);
        Map m = new HashMap();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            if (null == element[1]) {
                m.put("null", element[0]);
            } else {
                m.put(((Entity) element[1]).getEntityId().toString(), element[0]);
            }
        }
        return m;
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getAvgCourseNo(String,
     *      String, TeachCalendar)
     */
    public List getAvgCourseNo(String departmentIdSeq, String stdTypeIdSeq,
            TeachCalendar teachCalendar) {
        return teachWorkloadStatDAO.getAvgCourseNoOfWokload(departmentIdSeq, stdTypeIdSeq,
                teachCalendar);
    }
    
    /**
     * m1 表示授课教师人数<br>
     * m2 表示在编教师授课工作量<br>
     * m3表示授课工作量<br>
     * m4在编授课人数
     * 
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getWorkloadOfDepartment(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.lang.String)
     */
    public List getRegisterWorkloadOfDepartment(TeachCalendar teachCalendar, String studentTypeIds,
            String departmentIds) {
        List workloadOfDepartmentList = teachWorkloadStatDAO.getWorkloadOfDepartment(teachCalendar,
                studentTypeIds, departmentIds);
        return workloadOfDepartmentList;
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getRegisterPeopleOfDepartment(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.lang.String)
     */
    public void getRegisterTeacherOfDepartment(String departmentIds, Map registerTeacherMap) {
        List teacherList = teachWorkloadStatDAO.getRegisterPeopleOfDepartment(departmentIds);
        for (Iterator iter = teacherList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            TeacherType teacherType = (TeacherType) element[1];
            Department department = (Department) element[2];
            String departIdSeq = department.getId().toString();
            if (null != teacherType && TeacherType.REGISTERTYPEID.equals(teacherType.getId())) {
                StatUtils.setValueToMap(departIdSeq, element[0], "integer", registerTeacherMap);
            }
        }
    }
    
    /**
     * @see com.shufe.service.workload.course.TeachWorkloadStatService#getPropertyOfTeachWorkload(java.lang.String,
     *      java.lang.Long)
     */
    public List getPropertyOfTeachWorkload(String propertyOfTeachWorkload, Long teachCalendarId) {
        return teachWorkloadStatDAO.getPropertyOfTeachWorkload(propertyOfTeachWorkload,
                teachCalendarId);
    }
    
    public void setTeachWorkloadStatDAO(TeachWorkloadStatDAO teachWorkloadStatDAO) {
        this.teachWorkloadStatDAO = teachWorkloadStatDAO;
    }
    
}

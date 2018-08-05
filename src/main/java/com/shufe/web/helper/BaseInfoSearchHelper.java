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
 * chaostone            2006-12-04          Created
 * zq                   2007-10-11          更正了buildAdminClassQuery()方法，实
 *                                          现了大类查询
 ********************************************************************************/

package com.shufe.web.helper;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.baseinfo.Building;
import com.ekingstar.security.Resource;
import com.shufe.model.Constants;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.NewCourse;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.util.DataRealmUtils;

public class BaseInfoSearchHelper extends SearchHelper {
    
    /**
     * 查询班级 支持班级中学生类别基于上下级关系的查找.<br>
     * 例如班级中的学生类别如果是研究生,则能查询出所有包括硕士和博士在内的班级.
     * 
     * @param request
     * @return
     */
    public Collection searchAdminClass(HttpServletRequest request) {
        return utilService.search(buildAdminClassQuery(request));
    }
    
    public Collection searchTeacher(HttpServletRequest request) {
        return utilService.search(buildTeacherQuery(request));
    }
    
    public Collection searchClassroom(HttpServletRequest request) {
        return utilService.search(buildClassroomQuery(request));
    }
    
    /**
     * 构建一个班级查询功能 查询的参数以adminClass开头，如果查询一专业/二专业班级，采用majorTypeId
     * 
     * @param request
     * @param isDataRealm
     * @return
     */
    public EntityQuery buildAdminClassQuery(HttpServletRequest request, Boolean isDataRealm) {
        EntityQuery query = new EntityQuery(AdminClass.class, Constants.ADMINCLASS);
        QueryRequestSupport.populateConditions(request, query,
                "adminClass.stdType.id,calendar.studentType.id");
        Long stdTypeId = RequestUtils.getLong(request, "adminClass.stdType.id");
        if (stdTypeId == null) {
            stdTypeId = RequestUtils.getLong(request, "calendar.studentType.id");
        }
        // 是否限权限
        if (null == isDataRealm) {
            isDataRealm = Boolean.TRUE;
        }
        if (Boolean.TRUE.equals(isDataRealm)) {
            String resourceName = getResourceName(request);
            Resource resource = (Resource) authorityService.getResource(resourceName);
            if (null != resource && !resource.getPatterns().isEmpty()) {
                DataRealmUtils.addDataRealms(query, new String[] { "adminClass.stdType.id",
                        "adminClass.department.id" }, dataRealmHelper.getDataRealmsWith(stdTypeId,
                        request));
            } else {
                if (ValidEntityKeyPredicate.INSTANCE.evaluate(stdTypeId)) {
                    dataRealmHelper.addStdTypeTreeDataRealm(query, stdTypeId,
                            "adminClass.stdType.id", request);
                }
            }
        }
        
        query.join("left", "adminClass.speciality", "speciality").join("left", "adminClass.aspect",
                "aspect");
        // 查询一专业/二专业班级
        Long majorTypeId = RequestUtils.getLong(request, "majorTypeId");
        if (ObjectUtils.equals(majorTypeId, new Long(1))) {
            query.add(new Condition("speciality is null or speciality.majorType.id = 1"));
        }
        if (ObjectUtils.equals(majorTypeId, new Long(2))) {
            query.add(new Condition("speciality.majorType.id = 2"));
        }
        query.setLimit(getPageLimit(request));
        String orderBy = request.getParameter("orderBy");
        if (StringUtils.isNotEmpty(orderBy))
            query.addOrder(OrderUtils.parser(orderBy));
        return query;
    }
    
    /**
     * 限权限的查询班级
     * 
     * @param request
     * @return
     */
    public EntityQuery buildAdminClassQuery(HttpServletRequest request) {
        return buildAdminClassQuery(request, Boolean.TRUE);
    }
    
    /**
     * 构建一个新开课程查询
     * 
     * @param request
     * @param moduleName
     * @return
     */
    public EntityQuery buildNewCourseQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(NewCourse.class, "newCourse");
        QueryRequestSupport.populateConditions(request, entityQuery);
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return entityQuery;
    }
    /**
     * 构建一个教师查询功能
     * 
     * @param request
     * @return
     */
    public EntityQuery buildTeacherQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(Teacher.class, "teacher");
        QueryRequestSupport.populateConditions(request, entityQuery);
        String resourceName = getResourceName(request);
        Resource resource = (Resource) authorityService.getResource(resourceName);
        if (null != resource && !resource.getPatterns().isEmpty()) {
            entityQuery.add(new Condition("teacher.department.id in (:departIds)", SeqStringUtil
                    .transformToLong(dataRealmHelper.getDepartmentIdSeq(request))));
        }
        entityQuery.setLimit(getPageLimit(request));
        
        String orderBy = request.getParameter("orderBy");
        if (StringUtils.isNotEmpty(orderBy)) {
            entityQuery.addOrder(OrderUtils.parser(orderBy));
        }
        return entityQuery;
    }
    
    /**
     * 构建一个教室查询
     * 
     * @param request
     * @return
     */
    public EntityQuery buildClassroomQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(Classroom.class, "classroom");
        QueryRequestSupport.populateConditions(request, entityQuery);
        entityQuery.setSelect("select classroom");
        
        String departIds = request.getParameter("roomDepartId");
        String resourceName = getResourceName(request);
        Resource resource = (Resource) authorityService.getResource(resourceName);
        // FIXME buildClassroomQuery.StringUtils.isEmpty(departIds)
        if (StringUtils.isEmpty(departIds) && null != resource && !resource.getPatterns().isEmpty()) {
            departIds = dataRealmHelper.getDepartmentIdSeq(request);
        }
        if (StringUtils.isNotEmpty(departIds)) {
            entityQuery.add(new Condition(
                    "exists (from Department depart join depart.classrooms  room where"
                            + " room.id=classroom.id and depart.id in(:departIds))", SeqStringUtil
                            .transformToLong(departIds)));
        }
        // 考试人数
        Integer examCountFrom = RequestUtils.getInteger(request, "examCountFrom");
        if (null != examCountFrom) {
            entityQuery.add(new Condition("classroom.capacityOfExam >= (:examCountFrom)",
                    examCountFrom));
        }
        Integer examCountTo = RequestUtils.getInteger(request, "examCountTo");
        if (null != examCountTo) {
            entityQuery
                    .add(new Condition("classroom.capacityOfExam <= (:examCountTo)", examCountTo));
        }
        // 听课人数
        Integer courseCountFrom = RequestUtils.getInteger(request, "courseCountFrom");
        if (null != courseCountFrom) {
            entityQuery.add(new Condition("classroom.capacityOfCourse >= (:courseCountFrom)",
                    courseCountFrom));
        }
        Integer courseCountTo = RequestUtils.getInteger(request, "courseCountTo");
        if (null != courseCountTo) {
            entityQuery.add(new Condition("classroom.capacityOfCourse <= (:courseCountTo)",
                    courseCountTo));
        }
        // 正真容量
        Integer capacityFrom = RequestUtils.getInteger(request, "capacityFrom");
        if (null != capacityFrom) {
            entityQuery.add(new Condition("classroom.capacity >= (:capacityFrom)", capacityFrom));
        }
        Integer capacityTo = RequestUtils.getInteger(request, "capacityTo");
        if (null != capacityTo) {
            entityQuery.add(new Condition("classroom.capacity <= (:capacityTo)", capacityTo));
        }
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return entityQuery;
    }
    
    /**
     * 构建一个课程查询
     * 
     * @param request
     * @param moduleName
     * @return
     */
    public EntityQuery buildCourseQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(Course.class, "course");
        QueryRequestSupport.populateConditions(request, entityQuery);
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return entityQuery;
    }
    
    /**
     * 构建一个教学楼查询
     * 
     * @param request
     * @param moduleName
     * @return
     */
    public EntityQuery buildBuildingQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(Building.class, "building");
        QueryRequestSupport.populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
    /**
     * 构建一个专业查询
     * 
     * @param request
     * @param moduleName
     * @return
     */
    public EntityQuery buildSpecialityQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(Speciality.class, "speciality");
        QueryRequestSupport.populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
    /**
     * 构建一个专业方向查询
     * 
     * @param request
     * @param moduleName
     * @return
     */
    public EntityQuery buildSpecialityAspectQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(SpecialityAspect.class, "specialityAspect");
        QueryRequestSupport.populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
    /**
     * 构建一个部门查询
     * 
     * @param request
     * @param moduleName
     * @return
     */
    public EntityQuery buildDepartmentQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(Department.class, "department");
        QueryRequestSupport.populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
}

/*
 * 
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use
 * of KINGSTAR MEDIA application development. Reengineering, reproduction arose from modification of
 * the original source, or other redistribution of this source is not permitted without written
 * permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/***************************************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name Date Description ============ ============ ============ chaostone 2007-1-2 Created
 * 
 **************************************************************************************************/
package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.Constants;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 成绩查询界面响应类
 * 
 * @author chaostone
 * 
 */
public class StdGradeSearchAction extends CalendarRestrictionSupportAction {
    
    protected GradeService gradeService;
    
    protected TeachTaskService teachTaskService;
    
    /**
     * 成绩查询主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        addCollection(request, "courseTypes", teachTaskService.getCourseTypesOfTask(
                stdTypeDataRealm, departDataRealm,
                (TeachCalendar) request.getAttribute(Constants.CALENDAR)));
        Results.addObject("studentStateList", utilService.loadAll(StudentState.class));
        initBaseCodes(request, "teachDepartmentList", Department.class);
        return forward(request);
    }
    
    /**
     * 成绩查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if ("Y".equals(get(request, "kind"))) {
            addCollection(request, "courseGrades",
                    utilService.search(buildGradeQueryWithNormal(request)));
        } else {
            addCollection(request, "courseGrades", utilService.search(buildGradeQuery(request)));
        }
        return forward(request);
    }
    
    protected EntityQuery buildGradeQueryWithNormal(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "courseGrade");
        QueryRequestSupport.populateConditions(request, entityQuery, "courseGrade.std.type.id");
        MajorType majorType = new MajorType(getLong(request, "courseGrade.majorType.id"));
        Long departId = getLong(request, "department.id");
        Long teachDepartmentId = getLong(request, "teachDepartment.id");
        Long specialityId = getLong(request, "speciality.id");
        Long aspectId = getLong(request, "specialityAspect.id");
        if (MajorType.FIRST.equals(majorType.getId())) {
            if (null != aspectId) {
                entityQuery.add(new Condition("courseGrade.std.firstAspect.id=" + aspectId));
            }
            if (null != teachDepartmentId) {
                entityQuery.add(new Condition("courseGrade.task.arrangeInfo.teachDepart.id=" + teachDepartmentId));
            }
            if (null != specialityId) {
                entityQuery.add(new Condition("courseGrade.std.firstMajor.id=" + specialityId));
            } else {
                if (null != departId) {
                    entityQuery.add(new Condition("courseGrade.std.department.id=" + departId));
                }
            }
        } else {
            if (null != aspectId) {
                entityQuery.add(new Condition("courseGrade.std.secondAspect.id=" + aspectId));
            } else {
                if (null != specialityId) {
                    entityQuery.add(new Condition("courseGrade.std.secondMajor.id=" + specialityId));
                } else {
                    if (null != departId) {
                        entityQuery.add(new Condition("courseGrade.std.secondMajor.department.id=" + departId));
                    }
                }
            }
        }
        Float scoreFrom = getFloat(request, "scoreFrom");
        if (null != scoreFrom) {
            entityQuery.add(new Condition("courseGrade.score >=:scoreFrom", scoreFrom));
        }
        Float scoreTo = getFloat(request, "scoreTo");
        if (null != scoreTo) {
            entityQuery.add(new Condition("courseGrade.score <=:scoreTo", scoreTo));
        }
        Integer isPassed = getInteger(request, "isPass");
        if (null != isPassed) {
            if (isPassed.intValue() == 1) {
                entityQuery.add(new Condition("courseGrade.isPass=true"));
            } else if (isPassed.intValue() == 0) {
                entityQuery.add(new Condition("courseGrade.isPass=false"));
            } else if (isPassed.intValue() == 3) {
                entityQuery.add(new Condition("courseGrade.isPass=false and not exists(from CourseGrade cg where cg.std=courseGrade.std and cg.course=courseGrade.course and cg.isPass=true)" + " and exists (from ExamGrade eg where eg.courseGrade.id=courseGrade.id and eg.examStatus.id =1 and eg.gradeType.id = 2)"));
            }
        }
        // 添加权限
        String departName = "department.id";
        if (MajorType.SECOND.equals(majorType.getId())) {
            departName = "secondMajor.department.id";
        }
        addDataRealms_new(entityQuery, "courseGrade.std", new String[] { "type.id", departName },
                getDataRealmsWith(getLong(request, "courseGrade.std.type.id"), request));
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return entityQuery;
    }
    
    protected EntityQuery buildGradeQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "courseGrade");
        populateConditions(request, entityQuery, "courseGrade.std.type.id");
        MajorType majorType = new MajorType(getLong(request, "courseGrade.majorType.id"));
        Long departId = getLong(request, "department.id");
        Long specialityId = getLong(request, "speciality.id");
        Long aspectId = getLong(request, "specialityAspect.id");
        Long teachDepartmentId = getLong(request, "teachDepartment.id");
        if (MajorType.FIRST.equals(majorType.getId())) {
            if (null != aspectId) {
                entityQuery.add(new Condition("courseGrade.std.firstAspect.id=" + aspectId));
            }
            if (null != teachDepartmentId) {
                entityQuery.add(new Condition("courseGrade.task.arrangeInfo.teachDepart.id=" + teachDepartmentId));
            }
            if (null != specialityId) {
                entityQuery.add(new Condition("courseGrade.std.firstMajor.id=" + specialityId));
            } else {
                if (null != departId) {
                    entityQuery.add(new Condition("courseGrade.std.department.id=" + departId));
                }
            }
        } else {
            if (null != aspectId) {
                entityQuery.add(new Condition("courseGrade.std.secondAspect.id=" + aspectId));
            } else {
                if (null != specialityId) {
                    entityQuery.add(new Condition("courseGrade.std.secondMajor.id=" + specialityId));
                } else {
                    if (null != departId) {
                        entityQuery.add(new Condition("courseGrade.std.secondMajor.department.id=" + departId));
                    }
                }
            }
        }
        Float scoreFrom = getFloat(request, "scoreFrom");
        if (null != scoreFrom) {
            entityQuery.add(new Condition("courseGrade.score >=:scoreFrom", scoreFrom));
        }
        Float scoreTo = getFloat(request, "scoreTo");
        if (null != scoreTo) {
            entityQuery.add(new Condition("courseGrade.score <=:scoreTo", scoreTo));
        }
        Integer isPassed = getInteger(request, "isPass");
        if (null != isPassed) {
            if (isPassed.intValue() == 1) {
                entityQuery.add(new Condition("courseGrade.isPass=true"));
            } else if (isPassed.intValue() == 0) {
                entityQuery.add(new Condition("courseGrade.isPass=false"));
            } else if (isPassed.intValue() == 3) {
                entityQuery.add(new Condition("courseGrade.isPass=false and not exists(from CourseGrade cg where cg.std=courseGrade.std and cg.course=courseGrade.course and cg.isPass=true)"));
            }
        }
        // 添加权限
        String departName = "department.id";
        if (MajorType.SECOND.equals(majorType.getId())) {
            departName = "secondMajor.department.id";
        }
        addDataRealms_new(entityQuery, "courseGrade.std", new String[] { "type.id", departName },
                getDataRealmsWith(getLong(request, "courseGrade.std.type.id"), request));
        entityQuery.setLimit(getPageLimit(request));
        String stdAdminClassName = get(request, "stdAdminClass");
        if (StringUtils.isNotEmpty(stdAdminClassName)) {
            entityQuery.join("courseGrade.std.adminClasses", "adminClass");
            entityQuery.add(Condition.like("adminClass.name", stdAdminClassName));
        }
        entityQuery.addOrder(OrderUtils.parser(get(request, "orderBy")));
        return entityQuery;
    }
    
    private static void addDataRealms_new(EntityQuery query, String entity, String[] attrs,
            List dataRealms) {
        if (CollectionUtils.isEmpty(dataRealms))
            return;
        List datas = new ArrayList();
        String hql = "exists(from " + entity + " where ";
        StringBuffer buffer = new StringBuffer(hql);
        for (int i = 0; i < dataRealms.size(); i++) {
            if (i > 0)
                buffer.append(" or ");
            DataRealm dataRealm = (DataRealm) dataRealms.get(i);
            if (attrs.length > 0) {
                if (StringUtils.isNotEmpty(dataRealm.getStudentTypeIdSeq()) && StringUtils.isNotEmpty(attrs[0])) {
                    buffer.append(attrs[0] + " in(:mytypeIds" + RandomUtils.nextInt() + ")");
                    datas.add(SeqStringUtil.transformToLong(dataRealm.getStudentTypeIdSeq()));
                }
            }
            if (attrs.length > 1) {
                if (StringUtils.isNotEmpty(dataRealm.getDepartmentIdSeq()) && StringUtils.isNotEmpty(attrs[1])) {
                    if (buffer.length() > 0) {
                        buffer.append(" and ");
                    }
                    buffer.append(attrs[1] + " in(:myDepartIds" + RandomUtils.nextInt() + ")");
                    datas.add(SeqStringUtil.transformToLong(dataRealm.getDepartmentIdSeq()));
                }
            }
        }
        buffer.append(")");
        Condition con = new Condition(buffer.toString());
        con.setValues(datas);
        query.add(con);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery entityQuery = buildGradeQuery(request);
        entityQuery.setLimit(null);
        return utilService.search(entityQuery);
    }
    
    /**
     * 打印学生成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long courseGradeId = getLong(request, "courseGradeId");
        CourseGrade grade = (CourseGrade) utilService.get(CourseGrade.class, courseGradeId);
        
        CourseGrade example = new CourseGrade();
        example.setMajorType(grade.getMajorType());
        example.setStatus(new Integer(Grade.PUBLISHED));
        example.setStd(grade.getStd());
        List grades = gradeService.getCourseGrades(example);
        addCollection(request, "courseGrades", grades);
        request.setAttribute("std", grade.getStd());
        return forward(request);
    }
    
    /**
     * 查看成绩详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long courseGradeId = getLong(request, "courseGradeId");
        CourseGrade courseGrade = (CourseGrade) utilService.get(CourseGrade.class, courseGradeId);
        request.setAttribute("courseGrade", courseGrade);
        return forward(request);
    }
    
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
}

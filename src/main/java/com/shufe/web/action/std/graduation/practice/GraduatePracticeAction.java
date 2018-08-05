package com.shufe.web.action.std.graduation.practice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.PracticeSource;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.std.Student;
import com.shufe.model.std.graduation.practice.GraduatePractice;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.std.graduation.practice.GraduatePracticeImportListener;
import com.shufe.service.std.graduation.practice.GraduatePracticeService;
import com.shufe.service.util.stat.StatHelper;
import com.shufe.util.DataRealmUtils;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.helper.StdSearchHelper;

public class GraduatePracticeAction extends CalendarRestrictionSupportAction {
    
    private GraduatePracticeService GraduatePracticeService;
    
    private StdSearchHelper stdSearchHelper;
    
    /**
     * 查询列表页面
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
        EntityQuery entityQuery = buildQuery(request, getPageLimit(request));
        addCollection(request, "graduatePractices", utilService.search(entityQuery));
        return forward(request, "practiceList");
    }
    
    /**
     * 建立查询条件
     * 
     * @param request
     * @param pageLimit
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request, PageLimit pageLimit) {
        EntityQuery entityQuery = new EntityQuery(GraduatePractice.class, "graduatePractice");
        populateConditions(request, entityQuery);
        DataRealmUtils.addDataRealms(entityQuery, new String[] {
                "graduatePractice.student.type.id", "graduatePractice.student.department.id" },
                getDataRealms(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        entityQuery.setLimit(getPageLimit(request));
        return entityQuery;
    }
    
    /**
     * 列表编辑页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Long practiceId = getLong(request, "practiceId");
        GraduatePractice graduatePractice = null;
        if (null == practiceId || new Long(0).equals(practiceId)) {
            graduatePractice = (GraduatePractice) RequestUtil.populate(request,
                    GraduatePractice.class, "graduatePractice");
            request.setAttribute("calendar", utilService.get(TeachCalendar.class, getLong(request,
                    "calendarId")));
        } else {
            graduatePractice = (GraduatePractice) utilService.load(GraduatePractice.class,
                    practiceId);
        }
        request.setAttribute("graduatePractice", graduatePractice);
        addCollection(request, "practiceSources", utilService.load(PracticeSource.class, "state",
                Boolean.TRUE));
        setDataRealm(request, hasStdTypeDepart);
        return forward(request);
    }
    
    /**
     * 保存
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long practiceId = getLong(request, "graduatePractice.id");
        GraduatePractice graduatePractice = new GraduatePractice();
        if (null != practiceId && !new Long(0).equals(practiceId)) {
            graduatePractice = (GraduatePractice) utilService.load(GraduatePractice.class,
                    practiceId);
        }
        if (null != graduatePractice.getStudent()) {
            String code = request.getParameter("graduatePractice.student.code");
            List stds = utilService.load(Student.class, "code", code);
            if (stds.size() != 1) {
                saveErrors(request.getSession(), ForwardSupport
                        .buildMessages(new String[] { "error.model.notExist" }));
                return forward(request, new Action(this.clazz, "edit"));
            } else {
                graduatePractice.setStudent((Student) stds.get(0));
            }
        }
        graduatePractice.setTeachCalendar(new TeachCalendar());
        populate(getParams(request, "graduatePractice"), graduatePractice);
        utilService.saveOrUpdate(graduatePractice);
        String isNext = request.getParameter("isNext");
        if (StringUtils.isNotBlank(isNext)) {
            return redirect(request, "edit", "info.action.success");
        }
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 删除
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String graduatePracticeIds = request.getParameter("practiceIds");
        List graduatePractices = utilService.load(GraduatePractice.class, "id", SeqStringUtil
                .transformToLong(graduatePracticeIds));
        utilService.remove(graduatePractices);
        return redirect(request, "search", "info.delete.success");
    }
    
    /**
     * 导出的数据
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery entityQuery = buildQuery(request, null);
        entityQuery.setLimit(null);
        return utilService.search(entityQuery);
    }
    
    /**
     * 毕业实习单位统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward practiceDepartStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(getLong(request,
                "graduatePractice.teachCalendar.id"));
        GraduatePractice graduatePractice = new GraduatePractice();
        graduatePractice.setTeachCalendar(teachCalendar);
        List departments = GraduatePracticeService.getPropertyOfPractices(graduatePractice,
                getDepartmentIdSeq(request), getStdTypeIdSeq(request), "student.department",
                Boolean.FALSE);
        List sources = GraduatePracticeService.getPropertyOfPractices(graduatePractice,
                getDepartmentIdSeq(request), getStdTypeIdSeq(request), "practiceSource",
                Boolean.FALSE);
        Map datasMap = GraduatePracticeService.getDatasOfDepartStat(getDataRealm(request),
                teachCalendar);
        request.setAttribute("departments", departments);
        request.setAttribute("sources", sources);
        request.setAttribute("datasMap", datasMap);
        return forward(request);
    }
    
    /**
     * 带队老师统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward practiceTeacherStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(GraduatePractice.class, "graduatePractice");
        Long stdTypeId = getLong(request, "practice.student.type.id");
        if (null != stdTypeId) {
            request.setAttribute("stdType", utilService.get(StudentType.class, stdTypeId));
        }
        populateConditions(request, query);
        DataRealmUtils.addDataRealms(query, new String[] { "graduatePractice.student.type.id",
                "graduatePractice.student.department.id" }, getDataRealms(request));
        query
                .setSelect("select  new com.shufe.service.util.stat.StatItem(graduatePractice.practiceTeacher.id,count(*))");
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.groupBy("graduatePractice.practiceTeacher.id");
        query.setLimit(getPageLimit(request));
        Collection teacherStats = (Collection) utilService.search(query);
        new StatHelper(utilService).setStatEntities(teacherStats, Teacher.class);
        addCollection(request, "teacherStats", teacherStats);
        return forward(request);
    }
    
    /**
     * 导入毕业实习数据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransferResult tr = new TransferResult();
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request,
                GraduatePractice.class, tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new GraduatePracticeImportListener(utilService.getUtilDao(), teachCalendarService
                        .getTeachCalendarDAO()));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "search", "info.import.success");
        }
    }
    
    /**
     * 查询没有毕业实习的学生列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = stdSearchHelper.buildStdQuery(request);
        Long calendarId = getLong(request, "graduatePractice.calendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        query
                .add(new Condition(
                        "not exists( from GraduatePractice practice where practice.student.id=std.id and practice.teachCalendar.id=:calendarId)",
                        calendarId));
        query.add(new Condition("std.type in(:types)", getStdTypesOf(calendar.getStudentType()
                .getId(), request)));
        addCollection(request, "students", utilService.search(query));
        return forward(request, "stdList");
    }
    
    /**
     * 查询没有毕业实习的学生列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String practiceCompany = get(request, "graduatePractice.practiceCompany");
        if (StringUtils.isEmpty(practiceCompany)) {
            addCollection(request, "practiceSources", utilService.load(PracticeSource.class,
                    "state", Boolean.TRUE));
            return forward(request, "batchAddForm");
        } else {
            List stds = utilService.load(Student.class, "id", SeqStringUtil.transformToLong(request
                    .getParameter("stdIds")));
            List practices = new ArrayList();
            Long calendarId = getLong(request, "graduatePractice.calendar.id");
            Map params = getParams(request, "graduatePractice");
            for (Iterator iter = stds.iterator(); iter.hasNext();) {
                Student std = (Student) iter.next();
                GraduatePractice practice = new GraduatePractice();
                practice.setStudent(std);
                practice.setPracticeTeacher(std.getTeacher());
                practice.setTeachCalendar(new TeachCalendar(calendarId));
                EntityUtils.populate(params, practice);
                practices.add(practice);
            }
            utilService.saveOrUpdate(practices);
            return redirect(request, "search", "info.save.success");
        }
    }
    
    public void setGraduatePracticeService(GraduatePracticeService graduatePracticeService) {
        GraduatePracticeService = graduatePracticeService;
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
    
}

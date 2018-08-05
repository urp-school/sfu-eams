/**
 * 
 */

package com.shufe.web.action.std;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.AlterMode;
import com.ekingstar.eams.system.basecode.industry.EducationMode;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.std.Student;
import com.shufe.model.std.alteration.StudentAlteration;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.util.RequestUtil;

/**
 * @author dell
 */
public class StudentManager extends StudentSearchSupportAction {
    
    /**
     * 检索学生学籍信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return studentManager.ftl
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        searchStudent(form, request, "SearchStudent");
        initBaseCodes("studentStateList", StudentState.class);
        initBaseCodes(request, "educationModes", EducationMode.class);
        return forward(mapping, request, SUCCESS);
    }
    
    /**
     * 管理人员查看学籍信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        request.setAttribute("std", utilService.load(Student.class, getLong(request, "stdId")));
        return this.forward(mapping, request, "detail");
    }
    
    /**
     * 管理学生学籍信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return studentManager.ftl
     */
    public ActionForward manager(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Results.addObject("alterationTypeList", utilService.loadAll(AlterMode.class));
        initBaseCodes("studentStateList", StudentState.class);
        initBaseCodes(request, "educationModes", EducationMode.class);
        searchStudent(form, request, "StudentManager");
        return forward(mapping, request, SUCCESS);
    }
    
    /**
     * 列出给定模块的可管理的学生列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward managerByModuleName(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        searchStudent(form, request, request.getParameter("moduleName"));
        Results.addObject("studentStateList", utilService.loadAll(StudentState.class));
        return forward(mapping, request, SUCCESS);
    }
    
    /**
     * 列出给定模块的可管理的学生列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward managerSecondSpecialityStudentList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // String moduleName = request.getParameter("moduleName");
        searchStudentWith2ndSpeciality(form, request, new Boolean(true), null);
        return this.forward(request);
    }
    
    /**
     * 进入更新双专业学生信息的页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward updateSecondSpecialityStudentForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long[] stdIdArray = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
        List studentList = null;
        if (ArrayUtils.isEmpty(stdIdArray)) {
            studentList = new ArrayList();
        } else {
            studentList = utilService.load(Student.class, "id", stdIdArray);
        }
        Results.addObject("studentList", studentList);
        Speciality speciality = new Speciality();
        speciality.setMajorType(new MajorType(MajorType.SECOND));
        Results.addObject("secondSpecialityList", specialityService.getSpecialities(speciality));
        return this.forward(request);
    }
    
    /**
     * 进入新增双专业学生信息页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward addSecondSpecialityStudentForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Speciality speciality = new Speciality();
        speciality.setMajorType(new MajorType(MajorType.SECOND));
        Results.addObject("secondSpecialityList", specialityService.getSpecialities(speciality));
        return this.forward(request);
    }
    
    /**
     * 增加双专业学生信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addSecondSpecialityStudent(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student student = studentService.getStudent(request.getParameter("student.code"));
        if (student == null) {
            return this.forwardError(mapping, request, "student.notExist.error");
        }
        if (student.getSecondMajor() != null && student.getSecondMajor().isPO()) {
            return this.forwardError(mapping, request, "student.secondMajor.exists");
        }
        student = (Student) RequestUtil.populate(request, student, "student", true);
        EntityUtils.evictEmptyProperty(student);
        utilService.saveOrUpdate(student);
        studentService.updateStudentAdminClass(student.getId(), SeqStringUtil
                .transformToLong(request.getParameter("adminClassIds")), new Boolean(true));
        
        Action action = new Action("studentManager", "managerSecondSpecialityStudentList");
        return this.redirect(request, action, null, "moduleName=SecondSpecialityStudentManage");
    }
    
    /**
     * 更新双专业学生信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateSecondSpecialityStudent(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Long[] stdIdArray = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
        List studentList = utilService.load(Student.class, "id", stdIdArray);
        for (Iterator iter = studentList.iterator(); iter.hasNext();) {
            Student element = (Student) iter.next();
            Student student = (Student) RequestUtil.populate(request, Student.class, "student");
            EntityUtils.merge(element, student);
            utilService.saveOrUpdate(element);
            studentService.updateStudentAdminClass(element.getId(), SeqStringUtil
                    .transformToLong(request.getParameter("adminClassIds")), new Boolean(true));
        }
        Action action = new Action("studentManager", "managerSecondSpecialityStudentList");
        return this.redirect(request, action, null, "moduleName=SecondSpecialityStudentManage");
    }
    
    /**
     * 批量更新学生状态（学籍有效性、学籍状态）
     * 
     * @deprecated
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateStudentStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 从request中组装实体条件
        EntityQuery entityQuery = getStdQuery(request);
        entityQuery.setSelect("select student.id as stdId");
        Collection stdIdList = utilService.search(entityQuery);
        Long updateStatus = getLong(request, "updateStatus");
        // 更新条件的列表
        List updateList = new ArrayList();
        // 组装更新学生学籍状态的条件
        updateList.addAll(addStatusInfoQuery((List) stdIdList, updateStatus));
        stdIdList = new ArrayList();
        // 批量更新学籍有效性、学籍状态信息
        utilService.saveOrUpdate(updateList);
        return redirect(request, "managerByModuleName", "attr.updateSuccess");
    }
    
    /**
     * 打印学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printReview(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] stdIds = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
        List students = utilService.load(Student.class, "id", stdIds);
        addCollection(request, "students", students);
        
        Map map = new HashMap();
        for (Iterator it = students.iterator(); it.hasNext();) {
            Student std = (Student) it.next();
            EntityQuery query = new EntityQuery(StudentAlteration.class, "stdAlteration");
            query.add(new Condition("stdAlteration.std.id = (:stdId)", std.getId()));
            List list = (List) utilService.search(query);
            map.put(std.getId().toString(), list);
        }
        
        request.setAttribute("stdAlterationMap", map);
        
        return forward(request, "printReview");
    }
    
    /**
     * 组装更新学生学籍状态的条件
     * 
     * @param updateList
     * @param infoIdList
     * @param updateStatus
     */
    private List addStatusInfoQuery(List stdIdList, Long updateStatus) {
        if (updateStatus != null) {
            List updateList = utilService.load(Student.class, "id", stdIdList);
            for (Iterator it = updateList.iterator(); it.hasNext();) {
                Student std = (Student) it.next();
                std.setState(new StudentState(updateStatus));
            }
            return updateList;
        }
        return Collections.EMPTY_LIST;
    }
    
    /**
     * 组装更新学生学籍状态的条件
     * 
     * @deprecated
     * @param updateList
     * @param stdIdList
     * @param updateState
     */
    private void addStudentQuery(List updateList, List stdIdList, Boolean updateState) {
        if (updateState != null) {
            EntityQuery studentQuery = new EntityQuery(Student.class, "student");
            StringBuffer studentQueryBuf = new StringBuffer(50);
            studentQueryBuf
                    .append("update Student student set student.state = :updateState where student.id in (:stdIdList) ");
            studentQuery.setQueryStr(studentQueryBuf.toString());
            Map studentQueryParams = new HashMap();
            studentQueryParams.put("updateState", updateState);
            studentQueryParams.put("stdIdList", stdIdList);
            studentQuery.setParams(studentQueryParams);
            updateList.add(studentQuery);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportKeys(javax.servlet.http.HttpServletRequest)
     */
    protected String getExportKeys(HttpServletRequest request) {
        String keys = request.getParameter("keys");
        if (StringUtils.isEmpty(keys)) {
            MessageResources message = getResources(request, "excelconfig");
            return message.getMessage(getLocale(request), "student.export.keys");
        }
        return super.getExportKeys(request);
    }
    
    protected String getExportTitles(HttpServletRequest request) {
        String titles = request.getParameter("titles");
        if (StringUtils.isEmpty(titles)) {
            MessageResources message = getResources(request, "excelconfig");
            return message.getMessage(getLocale(request), "student.export.showkeys");
        }
        return super.getExportTitles(request);
    }
    
    /**
     * 学生信息导出
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        Long[] stdIds = SeqStringUtil.transformToLong(request.getParameter("ids"));
        if (stdIds == null || stdIds.length == 0) {
            // FIXME 学籍查询和管理共用此方法，但是module是放在了页面上
            EntityQuery query = buildQuery(request, request.getParameter("moduleName"));
            query.setLimit(null);
            return utilService.search(query);
        } else {
            return utilService.load(Student.class, "id", stdIds);
        }
    }
}

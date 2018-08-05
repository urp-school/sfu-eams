package com.shufe.web.action.selector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.baseinfo.AdminClassService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

public class AdminClassSelector extends RestrictionSupportAction {
    
    protected StudentService studentService;
    
    protected AdminClassService adminClassService;
    
    /**
     * 列出专业或方向下所有专业方向
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward withSpeciality(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String moduleName = get(request, "moduleName");
        if (StringUtils.isEmpty(moduleName)) {
            moduleName = "AdminClassManager";
        }
        Long adminClassId = getLong(request, "adminClassId");
        Long selectorId = getLong(request, "selectorId");
        AdminClass adminClass = (AdminClass) RequestUtil.populate(request, AdminClass.class,
                "adminClass");
        int pageNo = getPageNo(request);
        int pageSize = getPageSize(request);
        Pagination adminClassList = adminClassService.getAdminClasses(adminClass, null, null,
                pageNo, pageSize);
        
        Results.addObject("adminClassList", adminClassList);
        Results.addObject("specialityId", adminClass.getSpeciality().isPO() ? adminClass
                .getSpeciality().getId() : null);
        Results.addObject("aspectId", adminClass.getAspect().isPO() ? adminClass.getAspect()
                .getId() : null);
        Results.addObject("departmentId", adminClass.getDepartment().isPO() ? adminClass
                .getDepartment().getId() : null);
        Results.addObject("stdTypeId", adminClass.getStdType() != null
                && adminClass.getStdType().isPO() ? adminClass.getStdType().getId() : null);
        Results.addObject("enrollYearId", StringUtils.isEmpty(adminClass.getEnrollYear()) ? null
                : adminClass.getEnrollYear());
        MajorType majorType = adminClass.getSpeciality().getMajorType();
        Results.addObject("majorType", majorType);
        Results.addObject("adminClassId", adminClassId);
        Results.addObject("selectorId", selectorId);
        return this.forward(mapping, request, "success");
    }
    
    /**
     * @param studentService
     *            要设置的 studentService.
     */
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    /**
     * @param adminClassService
     *            要设置的 adminClassService.
     */
    public void setAdminClassService(AdminClassService adminClassService) {
        this.adminClassService = adminClassService;
    }
    
}

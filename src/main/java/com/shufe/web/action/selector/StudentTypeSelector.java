package com.shufe.web.action.selector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.service.system.baseinfo.StudentTypeService;
import com.shufe.web.action.common.RestrictionSupportAction;

public class StudentTypeSelector extends RestrictionSupportAction {
    
    private StudentTypeService studentTypeService;
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward withAuthority(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * 学生类别此处不做限制:
         * 2008-9-9,周琦-to-ezhouwenzi
         * String studentTypeIds = this.getStdTypeIdSeq(request);
         *  if (StringUtils.isNotEmpty(studentTypeIds)) {
         *  Results.addObject("studentTypeList", utilService.load(StudentType.class, "id",
         *  SeqStringUtil.transformToLong(studentTypeIds)));
         *  }
         */
    	
    	 Results.addObject("studentTypeList", baseInfoService.getBaseInfos(StudentType.class));
    	 return forward(mapping, request, "success");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward withoutAuthority(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Results.addObject("studentTypeList", utilService.loadAll(StudentType.class));
        return this.forward(mapping, request, "success");
    }
    
    /**
     * @return Returns the studentTypeService.
     */
    public StudentTypeService getStudentTypeService() {
        return studentTypeService;
    }
    
    /**
     * @param studentTypeService
     *            The studentTypeService to set.
     */
    public void setStudentTypeService(StudentTypeService studentTypeService) {
        this.studentTypeService = studentTypeService;
    }
    
}

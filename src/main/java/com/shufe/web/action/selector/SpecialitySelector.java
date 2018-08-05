package com.shufe.web.action.selector;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.web.action.common.DispatchBasicAction;

public class SpecialitySelector extends DispatchBasicAction {
    
    /**
     * 列出部门下所有专业
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward withDepartment(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long stdTypeId = getLong(request, "studentTypeId");
        EntityQuery query = new EntityQuery(Speciality.class, "speciality");
        Long departmentId = getLong(request, "departmentId");
        if (null != stdTypeId) {
            StudentType stdType = (StudentType) utilService.get(StudentType.class, stdTypeId);
            List stdTypeIds = new ArrayList();
            stdTypeIds.add(stdType.getId());
            while (null != stdType.getSuperType()) {
                stdTypeIds.add(stdType.getSuperType().getId());
                stdType = (StudentType) stdType.getSuperType();
            }
            query.add(new Condition("speciality.stdType.id in (:stdTypeIds)", stdTypeIds));
        }
        if (departmentId != null) {
            query.add(new Condition("speciality.department.id = :departmentId", departmentId));
        }
        query.add(new Condition("speciality.state = true"));
        List specialitList = (List) utilService.search(query);
        Results.addObject("specialitList", specialitList);
        return forward(mapping, request, "success");
    }
    
    /**
     * 列出所有专业
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward withoutDepartment(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Results.addObject("specialitList", utilService.loadAll(Speciality.class));
        return forward(mapping, request, "success");
    }
}

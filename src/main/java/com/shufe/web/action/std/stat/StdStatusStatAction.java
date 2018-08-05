package com.shufe.web.action.std.stat;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.std.Student;
import com.shufe.model.system.security.DataRealm;
import com.shufe.util.DataRealmLimit;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 学籍统计
 * 
 * @author maomao
 */
public class StdStatusStatAction extends CalendarRestrictionSupportAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        DataRealmLimit dataRealmLimit = getDataRealmLimit(request);
        
        String studentTypeIds = dataRealmLimit.getDataRealm().getStudentTypeIdSeq();
        String departmentIds = dataRealmLimit.getDataRealm().getDepartmentIdSeq();
        List stdTypes = studentTypeService.getStudentTypes(studentTypeIds);
        Collections.sort(stdTypes, new PropertyComparator("code"));
        List studentStateList = baseCodeService.getCodes(StudentState.class);
        
        request.setAttribute("stdTypeList", stdTypes);
        request.setAttribute("departmentList", departmentService.getColleges(departmentIds));
        request.setAttribute("stdInfoStatusList", studentStateList);
        return forward(request);
    }
    
    public ActionForward statByDegree(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(Student.class, "std");
        populateConditions(request, query, "std.type.id");
        Long stdType = getLong(request, "std.type.id");
        // 支持大类查找
        if (stdType != null) {
            query.add(new Condition("std.type.id in (:stdTypeTreeIds)", SeqStringUtil
                    .transformToLong(getStdTypeIdSeqOf(stdType, request))));
        }
        
        // 添加权限
        DataRealm dataRealm = getDataRealm(request);
        if (null != dataRealm) {
            DataRealmUtils.addDataRealm(query, new String[] { "std.type.id", "std.department.id" },
                    dataRealm);
        }
        
        query.setSelect("std.type.degree.name,count(*)");
        query.groupBy("std.type.degree.name");
        query.addOrder(new Order("std.type.degree.name desc"));
        request.setAttribute("statDatas", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward statByAbroad(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // String enrollYear = request.getParameter("std.enrollYear");
        // Long department = getLong(request, "std.department.id");
        // Long status = getLong(request, "std.state.id");
        // Boolean state = getBoolean(request, "std.active");
        
        EntityQuery query = new EntityQuery(Student.class, "std");
        populateConditions(request, query, "std.type.id");
        // 只限制部门权限
        DataRealm dataRealm = getDataRealm(request);
        if (null != dataRealm) {
            DataRealmUtils.addDataRealm(query, new String[] { "", "std.department.id" }, dataRealm);
        }
        /** get the number of student abroad */
        query.setSelect("count(*)");
        query.add(new Condition("std.type.id in (14,15,16,17,18,30,32,33,82)"));
        List list = (List) utilService.search(query);
        Long abroadNum = (Long) list.get(0);
        request.setAttribute("abroadNum", abroadNum);
        
        /** get the number of all student */
        EntityQuery queryAll = new EntityQuery(Student.class, "std");
        populateConditions(request, queryAll, "std.type.id");
        // 只限制部门权限
        if (null != dataRealm) {
            DataRealmUtils.addDataRealm(queryAll, new String[] { "", "std.department.id" },
                    dataRealm);
        }
        queryAll.setSelect("count(*)");
        List listAll = (List) utilService.search(queryAll);
        Long all = (Long) listAll.get(0);
        request.setAttribute("all", all);
        
        return forward(request);
    }
    
    public ActionForward statByHomeplace(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(Student.class, "std");
        populateConditions(request, query, "std.type.id");
        Long stdType = getLong(request, "std.type.id");
        
        // 支持大类查找
        if (stdType != null) {
            query.add(new Condition("std.type.id in (:stdTypeTreeIds)", SeqStringUtil
                    .transformToLong(getStdTypeIdSeqOf(stdType, request))));
        }
        
        // 添加权限
        DataRealm dataRealm = getDataRealm(request);
        if (null != dataRealm) {
            DataRealmUtils.addDataRealm(query, new String[] { "std.type.id", "std.department.id" },
                    dataRealm);
        }
        query.setSelect("std.studentStatusInfo.originalAddress,count(*)");
        
        query.groupBy("std.studentStatusInfo.originalAddress");
        request.setAttribute("statDatas", utilService.search(query));
        return forward(request);
    }
}

/**
 * 
 */

package com.shufe.web.action.std;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.std.Student;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;
import com.shufe.web.helper.StdSearchHelper;

/**
 * @author dell
 */
public class StudentCodeManager extends RestrictionExampleTemplateAction {
    
    public StdSearchHelper stdSearchHelper;
    
    /**
     * 学籍状态
     */
    public void indexSetting(HttpServletRequest request) throws Exception {
        addCollection(request, "statusList", baseCodeService.getCodes(StudentState.class));
    }
    
    /**
     * 保存修改
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception  {
        Student student = (Student)utilService.load(Student.class, getLong(request, "std.id"));
        student.setCode(get(request, "std.code"));
        utilService.saveOrUpdate(student);
        return redirect(request, "search", "info.save.sucess");
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
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        addCollection(request, "students", utilService.search(stdSearchHelper
                .buildStdQuery(request)));
        return forward(request);
    }
    
    /**
     * 组建所选择学生的查询条件
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildChoiceStudent(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(Student.class, "student");
        entityQuery.add(new Condition("student.id in (:stdIds)", SeqStringUtil
                .transformToLong(request.getParameter("stdIds"))));
        entityQuery.addOrder(OrderUtils.parser("student.code"));
        return entityQuery;
    }
    
    /**
     * @param stdSearchHelper
     *            the stdSearchHelper to set
     */
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }

}

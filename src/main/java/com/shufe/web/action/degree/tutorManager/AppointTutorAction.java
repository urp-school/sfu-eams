//$Id: StudyAwardAction.java,v 1.1 2007-3-7 下午02:26:54 chaostone Exp $
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
 * zq                   2007-09-17          见下面的 tutorSearch() 方法
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.tutorManager;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.TutorType;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Tutor;
import com.shufe.service.degree.tutorManager.TutorService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 指定导师(学生模块)
 * 
 * @author 塞外狂人
 * @author cwx 2007-4-20日修改
 * 
 */
public class AppointTutorAction extends RestrictionSupportAction {
    private TutorService tutorService;

    /**
     * 进入指定导师的学生查询列表页面
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
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }

    /**
     * 导师查询
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
        EntityQuery entityQuery = tutorSearch(request);
        addCollection(request, "stds", utilService.search(entityQuery));
        return forward(request, "stdList");
    }

    /**
     * 查询选中的导师
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdIdSeq = request.getParameter("stdIdSeq");
        List stdList = utilService.load(Student.class, "id", SeqStringUtil
                .transformToLong(stdIdSeq));
        request.setAttribute("stdIdSeq", stdIdSeq);
        request.setAttribute("stdList", stdList);
        EntityQuery entityQuery = new EntityQuery(Tutor.class, "tutor");
        populateConditions(request, entityQuery);
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        List tutorTypeList = baseCodeService.getCodes(TutorType.class);
        addCollection(request, "tutors", utilService.search(entityQuery));
        request.setAttribute("tutorTypeList", tutorTypeList);
        return forward(request, "tutorList");
    }

    /**
     * 为选定的学生指定导师
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward appointTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tutorId = request.getParameter("requireId");
        String stdIdSeq = request.getParameter("stdIdSeq");
        tutorService.batchModifyTeacherOfStd(stdIdSeq, StringUtils.isNotBlank(tutorId) ? Long
                .valueOf(tutorId) : null);
        return redirect(request, "search", "info.save.success");
    }

    /**
     * @param tutorService
     *            The tutorService to set.
     */
    public void setTutorService(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    private EntityQuery tutorSearch(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(Student.class, "student");
        // FIXME 增加了下面语句的参数
        populateConditions(request, query, "student.type.id");
        DataRealmUtils.addDataRealms(query, new String[] { "student.type.id",
                "student.department.id" }, getDataRealmsWith(getLong(request, "student.type.id"),
                request));
        // FIXME 上面的语句代替了下面的语句
        // DataRealmUtils.addDataRealms(query, new String[] { "student.type.id",
        // "student.department.id" }, getDataRealms(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.setLimit(getPageLimit(request));
        String enrollClassName = request.getParameter("enrollClassName");
        if (StringUtils.isNotBlank(enrollClassName)) {
            query.join("student.adminClasses", "adminClass");
            query.add(Condition.like("adminClass.name", enrollClassName));
        }
        String isTutor = request.getParameter("isHasTutor");
        if (StringUtils.isNotBlank(isTutor)) {
            if ("true".equals(isTutor)) {
                query.add(new Condition("student.teacher.id is not null"));
            } else {
                query.add(new Condition("student.teacher.id is null"));
            }
        }
        return query;
    }

    /**
     * 导出为 excel 表
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        // TODO Auto-generated method stub
        EntityQuery query = tutorSearch(request);
        query.setLimit(null);
        return utilService.search(query);
    }

}

//$Id: FormalAnswerAdminAction.java,v 1.1 2007-1-31 15:46:11 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong         2007-01-31          Created
 * zq                   2007-09-17          修改了下面的doQuery()方法
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.formalAnswer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.answer.FormalAnswer;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.service.degree.thesis.formalAnswer.FormalAnswerService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

public class FormalAnswerAdminAction extends RestrictionSupportAction {
    
    private FormalAnswerService formalAnswerService;
    
    private ThesisManageService thesisManageService;
    
    /**
     * @param formalAnswerService
     *            The formalAnswerService to set.
     */
    public void setFormalAnswerService(FormalAnswerService formalAnswerService) {
        this.formalAnswerService = formalAnswerService;
    }
    
    /**
     * @param thesisManageService
     *            The thesisManageService to set.
     */
    public void setThesisManageService(ThesisManageService thesisManageService) {
        this.thesisManageService = thesisManageService;
    }
    
    /**
     * 初始化答辩数据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doInitializated(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List thesisManages = thesisManageService.getThesissNullNotNull(new ThesisManage(),
                getDepartmentIdSeq(request), getStdTypeIdSeq(request), "", "formalAnswer");
        List tempManageList = new ArrayList();
        List tempAnswerList = new ArrayList();
        for (Iterator iter = thesisManages.iterator(); iter.hasNext();) {
            ThesisManage element = (ThesisManage) iter.next();
            if (null == element.getFormalAnswer()) {
                FormalAnswer formalAnswer = new FormalAnswer();
                formalAnswer.setStudent(element.getStudent());
                formalAnswer.setThesisManage(element);
                formalAnswer.setIsPassed(Boolean.FALSE);
                tempAnswerList.add(formalAnswer);
                element.setFormalAnswer(formalAnswer);
                tempManageList.add(element);
            }
        }
        utilService.saveOrUpdate(tempAnswerList);
        utilService.saveOrUpdate(tempManageList);
        return redirect(request, "doQuery", "&flag=datas");
    }
    
    /**
     * 查询得到具体的数据
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String flag = request.getParameter("flag");
        if ("datas".equals(flag)) {
            addCollection(request, "answerPagi", utilService.search(buildQuery(request)));
            return forward(request, "formalAnswerList");
        } else {
            setDataRealm(request, hasStdTypeCollege);
            return forward(request, "loadConditions");
        }
    }
    
    /**
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(FormalAnswer.class, "formalAnswer");
        populateConditions(request, query, "formalAnswer.student.type.id");
        DataRealmUtils.addDataRealms(query, new String[] { "formalAnswer.student.type.id",
                "formalAnswer.student.department.id" }, getDataRealmsWith(getLong(request,
                "formalAnswer.student.type.id"), request));
        java.util.Date finishOnStart = RequestUtils.getDate(request, "finishOnStart");
        if (null != finishOnStart) {
            query.add(new Condition("formalAnswer.finishOn >= (:finishOnStart)", finishOnStart));
        }
        java.util.Date finishOnEnd = RequestUtils.getDate(request, "finishOnEnd");
        if (null != finishOnEnd) {
            query.add(new Condition("formalAnswer.finishOn <= (:finishOnEnd))", finishOnEnd));
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        return query;
    }
    
    /**
     * 录入分数
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doInsertMark(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String isInsert = request.getParameter("isInsert");
        String formalAnswerIdSeq = request.getParameter("formalAnswerIds");
        if ("load".equals(isInsert)) {
            List formalAnswers = utilService.load(FormalAnswer.class, "id", SeqStringUtil
                    .transformToLong(formalAnswerIdSeq));
            request.setAttribute("formalAnswers", formalAnswers);
            request.setAttribute("formalAnswerIdSeq", formalAnswerIdSeq);
            return forward(request, "insertMark");
        } else if ("insert".equals(isInsert)) {
            Long[] formalAnswerIds = SeqStringUtil.transformToLong(formalAnswerIdSeq);
            List formalAnswerList = new ArrayList();
            for (int i = 0; i < formalAnswerIds.length; i++) {
                String mark = request.getParameter("mark" + formalAnswerIds[i]);
                if (StringUtils.isBlank(mark)) {
                    continue;
                }
                FormalAnswer formalAnswer = (FormalAnswer) utilService.get(FormalAnswer.class,
                        formalAnswerIds[i]);
                formalAnswer.setFormelMark(Float.valueOf(mark));
                formalAnswerList.add(formalAnswer);
            }
            utilService.saveOrUpdate(formalAnswerList);
            return redirect(request, "doQuery", "info.action.success", "&flag=datas");
        } else {
            return null;
        }
    }
    
    /**
     * 更新时间和地点
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] propertys = null;
        Object[] values = null;
        String flag = request.getParameter("flag");
        if ("isPassed".equals(flag)) {
            String isPassed = request.getParameter("isPassed");
            propertys = new String[] { "isPassed" };
            values = new Object[] { Boolean.valueOf(isPassed) };
        } else if ("timeAndAddress".equals(flag)) {
            String time = request.getParameter("time");
            String adress = request.getParameter("address");
            propertys = new String[] { "time", "address" };
            values = new Object[] { Date.valueOf(time), adress };
        }
        String formalAnswerIdSeq = request.getParameter("formalAnswerIds");
        formalAnswerService.batchUpdate(propertys, values, formalAnswerIdSeq);
        return redirect(request, "doQuery", "", "&flag=datas");
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = buildQuery(request);
        query.setLimit(null);
        return utilService.search(query);
    }
    
    /**
     * 取消学生预答辩的确认
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancelAffirm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String formalAnswerIdSeq = request.getParameter("formalAnswerIds");
        List formalAnswers = utilService.load(FormalAnswer.class, "id", SeqStringUtil
                .transformToLong(formalAnswerIdSeq));
        for (Iterator iter = formalAnswers.iterator(); iter.hasNext();) {
            FormalAnswer element = (FormalAnswer) iter.next();
            element.setFinishOn(null);
        }
        utilService.saveOrUpdate(formalAnswers);
        return redirect(request, "doQuery", "info.action.success", "&flag=datas");
    }
}


package com.shufe.web.action.degree.tutorManager.tutorChoose;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.TutorType;
import com.shufe.model.degree.tutorManager.tutor.TutorApply;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.baseinfo.Tutor;
import com.shufe.service.degree.tutorManager.TutorService;
import com.shufe.web.action.system.file.FileAction;

/**
 * 导师遴选
 * 
 * @author 塞外狂人
 * @param cwx2007-2-25修改
 */
public class TutorChooseAction extends FileAction {
    
    private TutorService tutorService;
    
    /**
     * 得到所有申请教师遴选的教师列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        initBaseCodes(request, "departments", Department.class);
        initBaseCodes(request, "tutorTypes", TutorType.class);
        return forward(request, "../loadCondition");
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        addCollection(request, "tutorApplys", utilService.search(query));
        return forward(request, "../list");
    }
    
    /**
     * 建立导师遴选查询条件
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        Date applyTimeFrom = RequestUtils.getDate(request, "applyTimeFrom");
        Date applyTimeTo = RequestUtils.getDate(request, "applyTimeTo");
        EntityQuery query = new EntityQuery(TutorApply.class, "tutorApply");
        populateConditions(request, query);
        if (null != applyTimeFrom) {
            query
                    .add(new Condition(
                            "to_date(to_char(tutorApply.applyTime, 'yyyy-MM-dd'), 'yyyy-MM-dd') >= (:applyTimeFrom)",
                            applyTimeFrom));
        }
        if (null != applyTimeTo) {
            query
                    .add(new Condition(
                            "to_date(to_char(tutorApply.applyTime, 'yyyy-MM-dd'), 'yyyy-MM-dd') <= (:applyTimeTo)",
                            applyTimeTo));
        }
        query.setOrders(OrderUtils.parser(request.getParameter("orderBy")));
        query.setLimit(getPageLimit(request));
        return query;
    }
    
    /**
     * 确认教师成为导师信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doAffirm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tutorApplyIdSeq = request.getParameter("tutorApplyIdSeq");
        Long[] tutorApplyId = SeqStringUtil.transformToLong(tutorApplyIdSeq);
        List confirmList = new ArrayList();
        for (int i = 0; i < tutorApplyId.length; i++) {
            TutorApply tutorApply = (TutorApply) utilService.get(TutorApply.class, tutorApplyId[i]);
            Teacher teacher = (Teacher) tutorApply.getTeacher();
            // 修改为导师
            tutorService.changeTutor(teacher.getId(),true);
            // 修改对应的导师级别
            Tutor tutor = (Tutor) utilService.load(Tutor.class, teacher.getId());
            tutor.setTutorType(tutorApply.getTutorType());
            confirmList.add(tutor);
            // 修改申请信息
            tutorApply.setIsPass(Boolean.TRUE);
            tutorApply.setPassTime(new Date(System.currentTimeMillis()));
            confirmList.add(tutorApply);
        }
        utilService.saveOrUpdate(confirmList);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 下载对应文档
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void doDownloadApplyDoc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = request.getParameter("fileName");
        String fileAbsolutePath = getFileRealPath("tutorApply", request) + fileName;
        String displayName = request.getParameter("displayName");
        download(request, response, fileAbsolutePath, displayName);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        return utilService.search(buildQuery(request));
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportKeys(javax.servlet.http.HttpServletRequest)
     */
    protected String getExportKeys(HttpServletRequest request) {
        String key = request.getParameter("keyValue");
        return key;
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportTitles(javax.servlet.http.HttpServletRequest)
     */
    protected String getExportTitles(HttpServletRequest request) {
        String title = request.getParameter("titleValue");
        return title;
    }
    
    /**
     * @param tutorService The tutorService to set.
     */
    public void setTutorService(TutorService tutorService) {
        this.tutorService = tutorService;
    }
}

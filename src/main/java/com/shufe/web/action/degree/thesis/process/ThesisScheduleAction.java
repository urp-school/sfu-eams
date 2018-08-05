//$Id: ThesisScheduleAction.java,v 1.1 2007-3-8 15:03:18 Administrator Exp $
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
 * chenweixiong         2007-03-08          Created
 * zq                   2007-09-17          修改了search()方法
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.process;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.mvc.struts.misc.StrutsMessageResource;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.DefaultPropertyExtractor;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.Tache;
import com.shufe.model.degree.thesis.process.Schedule;
import com.shufe.model.degree.thesis.process.TacheSetting;
import com.shufe.model.system.file.DegreeDocument;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

public class ThesisScheduleAction extends RestrictionSupportAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdType);
        return forward(request);
    }
    
    /**
     * 学生查询
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
        EntityQuery query = new EntityQuery(Schedule.class, "schedule");
        populateConditions(request, query, "schedule.studentType.id");
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        DataRealmUtils.addDataRealms(query, new String[] { "schedule.studentType.id", null },
                getDataRealmsWith(getLong(request, "schedule.studentType.id"), request));
        // FIXME 上面一句代替下面的一句
        // DataRealmUtils.addDataRealms(query, new String[] { "schedule.studentType.id" },
        // getDataRealms(request));
        addCollection(request, "schedules", utilService.search(query));
        return forward(request, "scheduleList");
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long scheduleId = getLong(request, "scheduleId");
        Schedule schedule = new Schedule();
        if (null == scheduleId) {
            populate(getParams(request, "schedule"), schedule);
        } else {
            schedule = (Schedule) utilService.get(Schedule.class, scheduleId);
        }
        request.setAttribute("schedule", schedule);
        setDataRealm(request, hasStdType);
        return forward(request, "scheduleForm");
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String scheduleIds = request.getParameter("scheduleIds");
        List schedules = utilService.load(Schedule.class, "id", SeqStringUtil
                .transformToLong(scheduleIds));
        try {
            utilService.remove(schedules);
        } catch (Exception e) {
            info("delete Schedule errors and the scheduleId is" + scheduleIds);
            return redirect(request, "search", "error.delete.failure.using");
        }
        return redirect(request, "search", "info.delete.success");
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Schedule schedule = (Schedule) populateEntity(request, Schedule.class, "schedule");
        EntityQuery query = new EntityQuery(Schedule.class, "schedule");
        query.add(new Condition("schedule.studentType.id = :stId", schedule.getStudentType()
                .getId()));
        query.add(new Condition("schedule.enrollYear = :ey", schedule.getEnrollYear()));
        query.add(new Condition("schedule.studyLength = :sl", schedule.getStudyLength()));
        List list = (List) utilService.search(query);
        
        if (list.size() > 0) {
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Schedule exists = (Schedule) iterator.next();
                if (null == schedule.getId() || !exists.getId().equals(schedule.getId())) {
                    return forward(request, new Action("", "edit"), "error.code.existed");
                }
            }
        }
        utilService.saveOrUpdate(schedule);
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward loadCopyPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String copyType = request.getParameter("copyType");
        String scheduleIdSeq = "scheduleIds";
        if ("single".equals(copyType)) {
            scheduleIdSeq = "scheduleId";
        }
        String scheduleIds = request.getParameter(scheduleIdSeq);
        if ("single".equals(copyType)) {
            request.setAttribute("schedule", utilService.load(Schedule.class, Long
                    .valueOf(scheduleIds)));
            setDataRealm(request, hasStdType);
        }
        request.setAttribute("scheduleIds", scheduleIds);
        request.setAttribute("copyType", copyType);
        return forward(request);
    }
    
    /**
     * 复制页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long scheduleId = getLong(request, "scheduleId");
        Schedule schedule = (Schedule) utilService.get(Schedule.class, scheduleId);
        Map valueMap = RequestUtils.getParams(request, "schedule");
        Schedule copeSchedule = schedule.copy();
        EntityUtils.populate(valueMap, copeSchedule);
        EntityQuery entityQuery = new EntityQuery(Schedule.class, "schedule");
        populateConditions(request, entityQuery, "schedule.remark");
        Collection collect = utilService.search(entityQuery);
        if (collect.size() > 0) {
            Schedule temp = (Schedule) collect.iterator().next();
            request.setAttribute("copyErrors", temp.getStudentType().getName() + "/"
                    + temp.getEnrollYear() + "/" + temp.getStudyLength());
            return forward(request, "copyFail");
        }
        utilService.saveOrUpdate(copeSchedule);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 批量设置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchCopy(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String scheduleIds = request.getParameter("scheduleIds");
        List schedules = utilService.load(Schedule.class, "id", SeqStringUtil
                .transformToLong(scheduleIds));
        List copySchedules = new ArrayList();
        List existsSchedule = utilService.load(Schedule.class, "studentType.id",
                SeqStringUtil.transformToLong(getStdTypeIdSeq(request)));
        String enrollYear = request.getParameter("schedule.enrollYear");
        String remark = request.getParameter("schedule.remark");
        String copyErrors = "";
        for (Iterator iter = schedules.iterator(); iter.hasNext();) {
            Schedule schedule = (Schedule) iter.next();
            Schedule copy = schedule.copy();
            copy.setEnrollYear(enrollYear);
            if (existsSchedule.contains(copy)) {
                copyErrors += copy.getStudentType().getName() + "/" + copy.getEnrollYear() + "/"
                        + copy.getStudyLength() + "<br>";
                continue;
            }
            copy.setRemark(remark);
            copySchedules.add(copy);
            existsSchedule.add(copy);
        }
        utilService.saveOrUpdate(copySchedules);
        if (StringUtils.isNotBlank(copyErrors)) {
            request.setAttribute("copySchedules", copySchedules);
            request.setAttribute("copyErrors", copyErrors);
            return forward(request, "copyFail");
        }
        return redirect(request, "search", "info.action.success");
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long scheduleId = getLong(request, "scheduleId");
        Schedule schedule = (Schedule) utilService.get(Schedule.class, scheduleId);
        request.setAttribute("schedule", schedule);
        return forward(request, "scheduleInfo");
    }
    
    public ActionForward editTache(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long tacheSettingId = getLong(request, "tacheSettingId");
        TacheSetting tacheSetting = new TacheSetting();
        if (null == tacheSettingId) {
            populate(getParams(request, "schedule"), tacheSetting);
        } else {
            tacheSetting = (TacheSetting) utilService.get(TacheSetting.class, tacheSettingId);
        }
        request.setAttribute("tacheSetting", tacheSetting);
        initBaseCodes(request, "taches", Tache.class);
        List allDocuments = utilService.loadAll(DegreeDocument.class);
        addCollection(request, "degreeDocuments", allDocuments);
        addCollection(request, "supportDocuments", CollectionUtils.subtract(allDocuments,
                tacheSetting.getThesisDocuments()));
        addCollection(request, "supportModels", CollectionUtils.subtract(allDocuments, tacheSetting
                .getThesisModels()));
        request.setAttribute("scheduleId", request.getParameter("scheduleId"));
        return forward(request, "settingForm");
    }
    
    /**
     * 批量修改步骤的时间
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchEditTache(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String batch = request.getParameter("batch");
        String scheduleId = request.getParameter("scheduleId");
        String tacheSettingIds = request.getParameter("tacheSettingIds");
        List tacheSettings = utilService.load(TacheSetting.class, "id", SeqStringUtil
                .transformToLong(tacheSettingIds));
        if ("load".equals(batch)) {
            request.setAttribute("scheduleId", scheduleId);
            request.setAttribute("tacheSettings", tacheSettings);
            request.setAttribute("tacheSettingIds", tacheSettingIds);
            return forward(request, "loadBatchEditTaches");
        } else {
            Map valueMap = new HashMap();
            for (Iterator iter = tacheSettings.iterator(); iter.hasNext();) {
                TacheSetting element = (TacheSetting) iter.next();
                valueMap.put(element.getId(), element);
            }
            Long[] tacheSettingId = SeqStringUtil.transformToLong(tacheSettingIds);
            List tempList = new ArrayList();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < tacheSettingId.length; i++) {
                TacheSetting tacheSetting = (TacheSetting) valueMap.get(tacheSettingId[i]);
                tacheSetting.setPlanedTimeOn(df.parse(request.getParameter("planTime"
                        + tacheSettingId[i])));
                tacheSetting.setIsTutorAffirm(Boolean.valueOf(request.getParameter("isTutorAffirm"
                        + tacheSettingId[i])));
                tempList.add(tacheSetting);
            }
            utilService.saveOrUpdate(tempList);
            return redirect(request, "info", "info.action.success", "&scheduleId=" + scheduleId);
        }
    }
    
    public ActionForward removeTache(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tacheSettingIds = request.getParameter("tacheSettingIds");
        List tacheSettings = utilService.load(TacheSetting.class, "id", SeqStringUtil
                .transformToLong(tacheSettingIds));
        try {
            utilService.remove(tacheSettings);
        } catch (Exception e) {
            return redirect(request, "info", "info.delete.failure", "&scheduleId="
                    + request.getParameter("scheduleId"));
        }
        return redirect(request, "info", "info.delete.success", "&scheduleId="
                + request.getParameter("scheduleId"));
    }
    
    /**
     * 保存维护步骤
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveTache(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long tacheSettingId = getLong(request, "tacheSetting.id");
        TacheSetting tacheSetting = new TacheSetting();
        if (null != tacheSettingId) {
            tacheSetting = (TacheSetting) utilService.get(TacheSetting.class, tacheSettingId);
        }
        // populate(getParams(request, "tacheSetting"), tacheSetting);
        tacheSetting = (TacheSetting) populateEntity(request, TacheSetting.class, "tacheSetting");
        
        if (!tacheSetting.isPO()) {
            EntityUtils.evictEmptyProperty(tacheSetting);
        }
        
        Long scheduleId = getLong(request, "scheduleId");
        tacheSetting.setSchedule((Schedule) utilService.get(Schedule.class, scheduleId));
        
        EntityQuery query = new EntityQuery(TacheSetting.class, "tacheSetting");
        query
                .add(new Condition("tacheSetting.tache.id = :tacheId", tacheSetting.getTache()
                        .getId()));
        query.add(new Condition("tacheSetting.schedule.id = :scheduleId", tacheSetting
                .getSchedule().getId()));
        if (tacheSetting.getId() != null) {
            query.add(new Condition("tacheSetting.id <> :id", tacheSetting.getId()));
        }
        List list = (List) utilService.search(query);
        if (list.size() > 0) {
            return forward(request, new Action("", "editTache"),
                    "degree.tacheSetting.duplicateTacheSetting");
        }
        
        String thesisDocumentIdSeq = request.getParameter("thesisDocumentIdSeq");
        Set thesisDocuments = new HashSet();
        if (StringUtils.isNotBlank(thesisDocumentIdSeq)) {
            thesisDocuments.addAll(utilService.load(DegreeDocument.class, "id", SeqStringUtil
                    .transformToLong(thesisDocumentIdSeq)));
        }
        tacheSetting.setThesisDocuments(thesisDocuments);
        String thesisModelIdSeq = request.getParameter("thesisModelIdSeq");
        Set thesisModels = new HashSet();
        if (StringUtils.isNotBlank(thesisModelIdSeq)) {
            thesisModels.addAll(utilService.load(DegreeDocument.class, "id", SeqStringUtil
                    .transformToLong(thesisModelIdSeq)));
        }
        tacheSetting.setThesisModels(thesisModels);
        utilService.saveOrUpdate(tacheSetting);
        return redirect(request, "info", "info.save.success", "&scheduleId="
                + request.getParameter("scheduleId"));
    }
    
    /**
     * 导出数据库里面的数据
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        List tacheSettings = new ArrayList();
        String scheduleIds = request.getParameter("scheduleIds");
        if (StringUtils.isNotBlank(scheduleIds)) {
            List schedules = utilService.load(Schedule.class, "id", SeqStringUtil
                    .transformToLong(scheduleIds));
            for (Iterator iter = schedules.iterator(); iter.hasNext();) {
                Schedule element = (Schedule) iter.next();
                tacheSettings.addAll(element.getTacheSettings());
            }
        } else {
            Map params = RequestUtils.getParamsMap(request, "schedule", "");
            List conditions = new ArrayList();
            EntityQuery query = new EntityQuery(TacheSetting.class, "tacheSetting");
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
                String attr = (String) iter.next();
                String strValue = (String) params.get(attr);
                conditions.add(new Condition("tacheSetting." + attr + " like :"
                        + attr.replace('.', '_'), "%" + (String) strValue + "%"));
            }
            query.getConditions().addAll(conditions);
            tacheSettings.addAll(utilService.search(query));
        }
        Collections.sort(tacheSettings);
        return tacheSettings;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getPropertyExporter(javax.servlet.http.HttpServletRequest)
     */
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        DefaultPropertyExtractor simplePropertyExporter = new DefaultPropertyExtractor();
        simplePropertyExporter.setLocale(getLocale(request));
        simplePropertyExporter.setBuddle(new StrutsMessageResource(getResources(request)));
        return simplePropertyExporter;
    }
    
    /**
     * 进度的详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doDetailInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long scheduleId = getLong(request, "scheduleId");
        Schedule schedule = (Schedule) utilService.load(Schedule.class, scheduleId);
        request.setAttribute("schedule", schedule);
        return forward(request, "scheduleDetailInfo");
    }
}

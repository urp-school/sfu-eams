//$Id: Level2SubjectAction.java,v 1.9 2007/01/19 05:08:17 cwx Exp $
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
 * @author lzs
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong         2006-08-11          Created
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.subject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.degree.subject.Level1Subject;
import com.shufe.model.degree.subject.Level2Subject;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.service.degree.subject.Level2SubjectService;
import com.shufe.web.action.common.RestrictionSupportAction;

public class Level2SubjectAction extends RestrictionSupportAction {
    
    private Level2SubjectService subjectService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setAttribute("departmentList", departmentService
                .getDepartments(getDepartmentIdSeq(request)));
        addOldPage(request, "level2Subjects", subjectService.getLevel2Subjects(
                (Level2Subject) populate(request, Level2Subject.class), getPageSize(request),
                getPageNo(request)));
        return forward(request);
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Level2Subject level2Subject = (Level2Subject) getEntity(request, Level2Subject.class,
                "level2Subject");
        request.setAttribute("categoryList", utilService.loadAll(Level1Subject.class));
        if (!level2Subject.isPO()) {
            EntityQuery query = new EntityQuery(Speciality.class, "speciality");
            query.add(new Condition(
                    "not exists(from Level2Subject s where s.speciality.id=speciality.id)"));
            addCollection(request, "specialityList", utilService.search(query));
        }
        addEntity(request, level2Subject);
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 获取form中的信息
        Level2Subject level2Subject = (Level2Subject) populateEntity(request, Level2Subject.class,
                "level2Subject");
        try {
            if (!level2Subject.isPO()) {
                logHelper.info(request, "Create a level2Subject with name:"
                        + level2Subject.getSpeciality().getName());
                utilService.saveOrUpdate(level2Subject);
            } else {
                logHelper.info(request, "Update a level2Subject with name:"
                        + level2Subject.getSpeciality().getName());
                Level2Subject savedSubject = (Level2Subject) utilService.get(Level2Subject.class,
                        level2Subject.getId());
                utilService.saveOrUpdate(savedSubject);
            }
        } catch (PojoExistException e) {
            logHelper.info(request, "Failure save or update a level2Subject with name:"
                    + level2Subject.getSpeciality().getName(), e);
            return forward(mapping, request, new String[] { "entity.level2Subject",
                    "error.model.existed" }, "error");
        } catch (Exception e) {
            logHelper.info(request, "Failure save or update a level2Subject with name:"
                    + level2Subject.getSpeciality().getName(), e);
            return forward(mapping, request, "error.occurred", "error");
        }
        if (null != request.getParameter("addAnother")) {
            return redirect(request, "edit", "info.save.success");
        } else
            return redirect(request, "index", "info.save.success");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author cwx
     */
    public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List level2Subjects = subjectService.getLevel2Subjects(null);
        Set categorySet = new HashSet();
        Set level1SubjectSet = new HashSet();
        int doctorCount = 0;
        int masterCount = 0;
        for (Iterator iter = level2Subjects.iterator(); iter.hasNext();) {
            Level2Subject level2Subject = (Level2Subject) iter.next();
            if (!level2Subject.getIsSpecial().booleanValue()) {
                level1SubjectSet.add(level2Subject.getLevel1Subject());
                if (null != level2Subject.getLevel1Subject()) {
                    categorySet.add(level2Subject.getLevel1Subject().getCategory());
                }
            }
            if (null != level2Subject.getDateForMaster()) {
                masterCount++;
            }
            if (null != level2Subject.getDateForDoctor()) {
                doctorCount++;
            }
        }
        addCollection(request, "level2Subjects", level2Subjects);
        request.setAttribute("categoryCount", new Integer(categorySet.size()));
        request.setAttribute("level1SubjectCount", new Integer(level1SubjectSet.size()));
        request.setAttribute("doctorCount", new Integer(doctorCount));
        request.setAttribute("masterCount", new Integer(masterCount));
        return forward(request);
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String level2SubjectId = request.getParameter("level2Subject.id");
        if (StringUtils.isEmpty(level2SubjectId))
            return forward(mapping, request, new String[] { "entity.level2Subject",
                    "error.model.id.needed" }, "error");
        Level2Subject level2Subject = (Level2Subject) utilService.get(Level2Subject.class,
                new Long(level2SubjectId));
        request.setAttribute("level2Subject", level2Subject);
        return forward(request);
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statByCollege(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List departments = subjectService.getDepartsOfSpeciality(new Level2Subject());
        Level2Subject l2 = new Level2Subject();
        l2.setIsSelfForDoctor(null);
        l2.setIsSelfForMaster(null);
        Map statByCollegeMap = subjectService.getAllStatisticDataByCollege(l2);
        request.setAttribute("departments", departments);
        request.setAttribute("statByCollegeMap", statByCollegeMap);
        return forward(request);
    }
    
    /**
     * @param Level2SubjectService
     *            The subjectService to set.
     */
    public void setLevel2SubjectService(Level2SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    
}

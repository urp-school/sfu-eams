//$Id: CourseAction.java,v 1.5 2006/12/30 01:29:01 duanth Exp $
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
 * chaostone            2005-09-15          Created
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 *  
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.TrainingType;
import com.ekingstar.eams.system.basecode.state.LanguageAbility;
import com.ekingstar.eams.system.baseinfo.Department;
import com.ekingstar.eams.system.baseinfo.service.CourseService;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.service.system.baseinfo.importer.CourseImportListener;
import com.shufe.service.util.ImporterCodeGenListener;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.web.action.system.baseinfo.search.CourseSearchAction;

/**
 * 课程信息管理的action.包括课程信息的增改查.
 * 
 * @author chaostone 2005-9-22
 */
public class CourseAction extends CourseSearchAction {
    
    private CourseService courseService;
    
    /**
     * 修改和新建课程时调用的动作.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        Course course = (Course) getEntity(request, Course.class, "course");
        if (ValidEntityPredicate.INSTANCE.evaluate(course)) {
            if (!DataAuthorityUtil.isInDataRealm("Course", course, stdTypeDataRealm, null))
                return forwardError(mapping, request, "error.stdType.dataRealm.insufficient");
        }
        addCollection(request, "stdTypes", studentTypeService.getStudentTypes(stdTypeDataRealm));
        addCollection(request, "languangeAblities", baseCodeService.getCodes(LanguageAbility.class));
        addCollection(request, "trainingLevels", baseCodeService.getCodes(TrainingType.class));
        addCollection(request, "courseCategories", baseCodeService.getCodes(CourseCategory.class));
        addCollection(request, "courseTypes", baseCodeService.getCodes(CourseType.class));
        addCollection(request, "departments", baseInfoService.getBaseInfos(Department.class));
        addEntity(request, course);
        return forward(request);
    }
    
    /**
     * 保存课程信息，新建的课程或修改的课程. <br>
     * 接受主键冲突异常，跳转到异常页面.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String courseCode = request.getParameter("course.code");
        Long courseId = getLong(request, "course.id");
        
        // 检查是否重复
        if (utilService.duplicate(Course.class, courseId, "code", courseCode)) {
            return forward(request, new Action("", "edit"), "error.code.existed");
        }
        
        Map params = getParams(request, "course");
        Course course = null;
        try {
            if (null == courseId) {
                course = new Course();
                populate(params, course);
                logHelper.info(request, "Create course with name:" + course.getName());
            } else {
                course = (Course) utilService.get(Course.class, courseId);
                populate(params, course);
                logHelper.info(request, "Update course with name:" + course.getName());
            }
            ActionForward f = super.saveOrUpdate(request, course);
            if (null != f) {
                return f;
            }
        } catch (PojoExistException e) {
            logHelper.info(request, "Failure save or update cource with name:" + course.getName(),
                    e);
            return forward(mapping, request,
                    new String[] { "entity.course", "error.model.existed" }, "error");
        } catch (Exception e) {
            logHelper.info(request, "Failure save or update cource with name:" + course.getName(),
                    e);
            return forward(mapping, request, "error.occurred", "error");
        }
        if (null != request.getParameter("addAnother")) {
            return redirect(request, "edit", "info.save.success");
        } else {
            return redirect(request, "search", "info.save.success");
        }
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Course course = (Course) getOpEntity(request);
        try {
            // 与成绩日志脱离关系
//            EntityQuery query = new EntityQuery(GradeLog.class, "gradeCatalog");
//            query.add(new Condition("gradeCatalog.course = :course", course));
//            Collection gradeCatalogs = utilService.search(query);
//            for (Iterator it = gradeCatalogs.iterator(); it.hasNext();) {
//                GradeLog gradeCatalog = (GradeLog) it.next();
//                gradeCatalog.setCourse(null);
//            }
//            utilService.saveOrUpdate(gradeCatalogs);
            utilService.remove(course);
        } catch (Exception e) {
            return redirect(request, "search", "info.delete.failure");
        }
        return redirect(request, "search", "info.delete.success");
    }
    
    /**
     * 导入班级信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransferResult tr = new TransferResult();
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request, Course.class, tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new ImporterCodeGenListener(codeGenerator)).addListener(
                new CourseImportListener(courseService));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "search", "info.import.success");
        }
    }
    
    /**
     * @param courseService
     *            The courseService to set.
     */
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    
    protected void onSave(Entity entity) {
        courseService.saveOrUpdate((Course) entity);
    }
    
}

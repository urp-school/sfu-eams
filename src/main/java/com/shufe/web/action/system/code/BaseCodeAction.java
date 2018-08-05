//$Id: BaseCodeAction.java,v 1.10 2007/01/14 03:16:30 duanth Exp $
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
 * chaostone             2005-9-7         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.code;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.BaseCode;
import com.ekingstar.eams.system.basecode.Coder;
import com.ekingstar.eams.system.basecode.industry.AlterMode;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.basecode.industry.OtherExamKind;
import com.ekingstar.eams.system.basecode.industry.PressLevel;
import com.ekingstar.eams.system.basecode.industry.PublicationLevel;
import com.ekingstar.eams.system.basecode.industry.TeachProjectType;
import com.ekingstar.eams.system.basecode.state.TeacherTitleLevel;
import com.shufe.model.Constants;
import com.shufe.service.system.codeGen.CodeGenerator;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 基础代码信息管理的action.<br>
 * 包括基础代码信息的增改查.
 * 
 * <pre>
 *          对所有基础代码采用了一致的修改方式。
 *          特殊代码的修改采用单独的修改和保存方法。
 *          特殊代码的修改页面和新增页面以及列表页面统一放在ext/下   
 * </pre>
 * 
 * @author chaostone 2005-9-23
 */
public class BaseCodeAction extends DispatchBasicAction {
    
    public static final String EXT = "ext/";
    
    private CodeGenerator codeGenerator;
    
    private EntityQuery buildQuery(HttpServletRequest request) {
        try {
            String codeName = get(request, "codeName");
            EntityQuery query = new EntityQuery(Class.forName(codeName), "baseCode");
            populateConditions(request, query, "baseCode.codeName");
            query.setLimit(getPageLimit(request));
            query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
            return query;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private void commonEdit(HttpServletRequest request) {
        // 从数据库中加载
        Long codeId = getLong(request, Constants.BASECODE_KEY);
        String codeName = request.getParameter("codeName");
        BaseCode code = null;
        if ((null != codeId) && StringUtils.isNotEmpty(codeName))
            code = baseCodeService.getCode(codeName, codeId);
        if (null == code) {
            code = new BaseCode();
        }
        Coder coder = (Coder) utilService.load(Coder.class, "className", codeName).iterator()
                .next();
        addEntity(request, coder);
        request.setAttribute(Constants.BASECODE, code);
    }
    
    /**
     * 修改和新建基础代码时调用的动作.<br>
     * 从request接受一个基础代码的courseId，从库中找出其信息.
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
        try {
            String shortName = StringUtils
                    .capitalize(getShortName(request.getParameter("codeName")));
            getMethod("edit" + shortName);
            return forward(request, new Action(this.getClass(), "edit" + shortName));
        } catch (java.lang.NoSuchMethodException e) {
        }
        commonEdit(request);
        return forward(request);
    }
    
    /**
     * 变动原因
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editAlterReason(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "alterModes", baseCodeService.getCodes(AlterMode.class));
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 国家地区
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editCountry(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 修改课程类别
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editCourseType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 修改课程类别
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editOtherExamCategory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        List beenChoiceKinds = (List) baseCodeService.getCodes(OtherExamKind.class);
        request.setAttribute("beenChoiceKinds", beenChoiceKinds);
        return getExtEditForward(request);
    }
    
    /**
     * 修改币种
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editCurrencyCategory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 缓考原因
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editExamDelayReason(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 考试情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editExamStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 成绩类型
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editGradeType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "examTypes", baseCodeService.getCodes(ExamType.class));
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * HSK级别
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editHSKDegree(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * HSK级别
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editPunishmentType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 语种熟练能力
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editLanguageAbility(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 成绩记录方式
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editMarkStyle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 出版社
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editPress(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "pressLevels", baseCodeService.getCodes(PressLevel.class));
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 出版物
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editPublication(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "publicationLevels", baseCodeService
                .getCodes(PublicationLevel.class));
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 学籍状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editStudentInfoStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    /**
     * 教师职称
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editTeacherTitle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonEdit(request);
        addCollection(request, "titleLevels", baseCodeService.getCodes(TeacherTitleLevel.class));
        return getExtEditForward(request);
    }
    
    /**
     * 科研项目类别
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editTeachProjectType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "superTypes", baseCodeService.getCodes(TeachProjectType.class));
        commonEdit(request);
        return getExtEditForward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = buildQuery(request);
        query.setLimit(null);
        return utilService.search(query);
    }
    
    /**
     * 基础代码管理主界面
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
        addCollection(request, "coders", baseCodeService.getCoders());
        return forward(request);
    }
    
    /**
     * 基础代码管理主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "coders", baseCodeService.getCoders());
        return forward(request, "searchHome");
    }
    
    /**
     * 保存基础代码信息，
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
        String codeName = request.getParameter("codeName");
        Class codeClass = Class.forName(codeName);
        Long codeId = getLong(request, "baseCode.id");
        BaseCode baseCode = null;
        if (null == codeId) {
            baseCode = (BaseCode) codeClass.newInstance();
        } else {
            baseCode = baseCodeService.getCode(codeClass, codeId);
        }
        
        Map params = getParams(request, "baseCode");
        String code = (String) params.get("code");
        populate(params, baseCode);
        if (!codeGenerator.isValidCode(code)) {
            code = codeGenerator.gen(baseCode, null);
            if (codeGenerator.isValidCode(code)) {
                baseCode.setCode(code);
            } else {
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        "system.codeGen.failure"));
                addErrors(request, messages);
                return forward(request, new Action(this.getClass(), "edit"));
            }
        }
        
        if (utilService.duplicate(codeClass, codeId, "code", baseCode.getCode())) {
            return forward(request, new Action("", "edit"), "error.code.existed");
        }
        try {
            baseCodeService.saveOrUpdate(baseCode);
        } catch (PojoExistException e) {
            return forwardError(mapping, request, new String[] { "entity.baseCode",
                    "error.model.existed" });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 查找信息action.
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
        Collection datas = utilService.search(buildQuery(request));
        addCollection(request, "baseCodes", datas);
        if (!datas.isEmpty()) {
            BaseCode code = (BaseCode) datas.iterator().next();
            if (code.hasExtPros()) {
                return forward(request, EXT + getShortName(request.getParameter("codeName"))
                        + "List");
            }
        }
        return forward(request);
    }
    
    /**
     * 批量修改状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateState(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String codeName = request.getParameter("codeName");
        Long[] ids = SeqStringUtil.transformToLong(get(request, "ids"));
        Boolean status = getBoolean(request, "status");
        Class clazz = null;
        if (StringUtils.isEmpty(codeName)) {
            clazz = BaseCode.class;
        } else {
            clazz = Class.forName(codeName);
        }
        
        List codes = (List) utilService.load(clazz, "id", ids);
        for (Iterator it = codes.iterator(); it.hasNext();) {
            BaseCode code = (BaseCode) it.next();
            code.setState(status);
        }
        utilService.saveOrUpdate(codes);
        
        return redirect(request, "search", "info.action.success");
    }
    
    public ActionForward getExtEditForward(HttpServletRequest request) {
        return forward(request, EXT + getShortName(request.getParameter("codeName")) + "Form");
    }
    
    public String getShortName(String className) {
        return StringUtils.uncapitalize(StringUtils.substringAfterLast(className, "."));
    }
    
    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }
    
}

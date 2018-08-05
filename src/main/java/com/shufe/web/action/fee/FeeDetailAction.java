//$Id: FeeDetailAction.java,v 1.21 2006/11/29 01:31:31 duanth Exp $
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
 * chenweixiong              2005-11-6         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.fee;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.eams.system.basecode.industry.CurrencyCategory;
import com.ekingstar.eams.system.basecode.industry.FeeMode;
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.model.fee.FeeDetail;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.fee.FeeDetailImportListener;

public class FeeDetailAction extends FeeSearchAction {
    
    /**
     * 收费维护主页面
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
        setCalendarDataRealm(request, hasStdTypeCollege);
        addCollection(request, "stdTypeList", getStdTypes(request));
        return forward(request);
    }
    
    /**
     * 添加收费信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        FeeDetail fee = (FeeDetail) populate(request, FeeDetail.class);
        if (null == fee.getCurrencyCategory()) {
            CurrencyCategory currencyCategory = new CurrencyCategory();
            currencyCategory.setId(CurrencyCategory.RMB);
            fee.setCurrencyCategory(currencyCategory);
        }
        if (null == fee.getCreateAt())
            fee.setCreateAt(new Date(System.currentTimeMillis()));
        if (null == fee.getRate()) {
            fee.setRate(new Float(1));
        }
        if (null == fee.getType()) {
            FeeType type = new FeeType();
            type.setId(FeeType.TUITION);
            fee.setType(type);
        }
        addEntity(request, fee);
        prepare(request);
        return forward(request);
    }
    
    /**
     * 修改收费信息
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
        Long feeId = getLong(request, "feeDetail.id");
        FeeDetail fee = null;
        if (null == feeId) {
            return forwardError(mapping, request, "error.parameters.needed");
        } else {
            fee = (FeeDetail) utilService.get(FeeDetail.class, feeId);
            if (null != fee.getStd().getStudentStatusInfo()) {
                request.setAttribute("std_outof_shool", new Boolean(!fee.getStd().isInSchool()));
            }
        }
        addEntity(request, fee);
        prepare(request);
        return forward(request);
    }
    
    /**
     * 保存收费信息
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
        FeeDetail feeDetail = (FeeDetail) populateEntity(request, FeeDetail.class, "feeDetail");
        TeachCalendar example = (TeachCalendar) populate(request, new TeachCalendar(), "calendar");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(example.getStudentType()
                .getId(), example.getYear(), example.getTerm());
        feeDetail.setCalendar(calendar);
        // 设置其他信息
        feeDetail.setModifyAt(new Date(System.currentTimeMillis()));
        feeDetail.setWhoModified(getUser(request.getSession()).getName());
        // 计算汇率
        if (null == feeDetail.getRate()) {
            CurrencyCategory category = (CurrencyCategory) utilService.get(CurrencyCategory.class,
                    feeDetail.getCurrencyCategory().getId());
            feeDetail.setRate(category.getRateToRMB());
        }
        // 如果应缴为零,则置为null
        if (null != feeDetail.getShouldPay() && feeDetail.getShouldPay().floatValue() == 0) {
            feeDetail.setShouldPay(null);
        }
        feeDetail.setToRMB(new Float(feeDetail.getRate().floatValue()
                * feeDetail.getPayed().floatValue()));
        
        Student student = null;
        if (!feeDetail.isPO()) {
            List stds = utilService.load(Student.class, "code", feeDetail.getStd().getCode());
            if (stds.isEmpty())
                return forwardError(mapping, request, "filed.student.notExists");
            
            student = (Student) stds.get(0);
            feeDetail.setWhoAdded(getUser(request.getSession()).getName());
            feeDetail.setStd(student);
            utilService.saveOrUpdate(feeDetail);
        } else {
            FeeDetail saved = (FeeDetail) utilService.get(FeeDetail.class, feeDetail.getId());
            // EntityUtils.merge(saved, feeDetail);
            student = saved.getStd();
            utilService.saveOrUpdate(saved);
        }
        
        if (null != request.getParameter("addAnother")) {
            StringBuffer extraParams = new StringBuffer();
            extraParams.append("&feeDetail.calendar.studentType.id=").append(
                    example.getStudentType().getId()).append("&feeDetail.calendar.year=").append(
                    example.getYear()).append("&feeDetail.calendar.term=")
                    .append(example.getTerm()).append("&feeDetail.currencyCategory.id=").append(
                            feeDetail.getCurrencyCategory().getId()).append("&feeDetail.rate=")
                    .append(feeDetail.getRate()).append("&feeDetail.type.id=").append(
                            feeDetail.getType().getId()).append("&feeDetail.mode.id=").append(
                            feeDetail.getMode().getId()).append("&feeDetail.shouldPay=").append(
                            feeDetail.getShouldPay()).append(feeDetail.getShouldPay()).append(
                            "&feeDetail.chargeAt=").append(feeDetail.getCreateAt()).append(
                            "&feeDetail.depart.id=").append(feeDetail.getDepart().getId());
            if (null != request.getParameter("sameStd")) {
                extraParams.append("&feeDetail.std.code=").append(student.getCode());
            }
            return redirect(request, "add", "info.save.success", extraParams.toString());
        } else {
            return redirect(request, "info", "info.save.success", "&feeDetail.id="
                    + feeDetail.getId());
        }
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long feeId = getLong(request, "feeDetail.id");
        FeeDetail fee = (FeeDetail) utilService.get(FeeDetail.class, feeId);
        addEntity(request, fee);
        return forward(request);
    }
    
    /**
     * 删除收费信息
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String feeDetailIdSeq = request.getParameter("feeDetailIds");
        Long[] feeDetailIds = SeqStringUtil.transformToLong(feeDetailIdSeq);
        if (null == feeDetailIds) {
            return forwardError(mapping, request, "error.parameters.needed");
        }
        List fees = utilService.load(FeeDetail.class, "id", feeDetailIds);
        String departId = "," + getDepartmentIdSeq(request) + ",";
        List tobeRemoved = new ArrayList();
        for (Iterator iter = fees.iterator(); iter.hasNext();) {
            FeeDetail fee = (FeeDetail) iter.next();
            if (StringUtils.contains(departId, "," + fee.getDepart().getId() + ","))
                tobeRemoved.add(fee);
        }
        for (Iterator iter = tobeRemoved.iterator(); iter.hasNext();) {
            utilService.remove((FeeDetail) iter.next());
        }
        return redirect(request, "search", "info.delete.success");
    }
    
    private void prepare(HttpServletRequest request) {
        addCollection(request, "stdTypeList", getStdTypes(request));
        addCollection(request, "adminList", getDeparts(request));
        addCollection(request, "feeTypeList", baseCodeService.getCodes(FeeType.class));
        addCollection(request, "feeModeList", baseCodeService.getCodes(FeeMode.class));
        addCollection(request, "currencyTypeList", baseCodeService.getCodes(CurrencyCategory.class));
    }
    
    public Float[] statFeeFor(String stdCode, Long stdTypeId, String year, String term,
            Long feeTypeId) {
        List stds = utilService.load(Student.class, "code", stdCode);
        if (stds.isEmpty())
            return new Float[] { new Float(0), new Float(0) };
        Student std = (Student) stds.get(0);
        
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(stdTypeId, year, term);
        if (null == calendar)
            return new Float[] { new Float(0), new Float(0) };
        FeeType type = (FeeType) utilService.get(FeeType.class, feeTypeId);
        if (null == type)
            return new Float[] { new Float(0), new Float(0) };
        
        return feeDetailService.statFeeFor(std, calendar, type);
    }
    
    /**
     * 导入收费信息
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
        Transfer transfer = ImporterServletSupport
                .buildEntityImporter(request, FeeDetail.class, tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        
        transfer.addListener(new ImporterForeignerListener(utilService))
                .addListener(
                        new FeeDetailImportListener(utilService, teachCalendarService
                                .getTeachCalendarDAO()));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "search", "info.import.success");
        }
    }
    
}

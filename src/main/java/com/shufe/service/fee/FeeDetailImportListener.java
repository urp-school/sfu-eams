//$Id: FeeDetail.java,v 1.5 2006/10/12 12:20:13 duanth Exp $
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
 * ============         ============        ====================================
 * zq                   2007-09-14          1.增加一startTransferItem()方法，用来
 *                                            查询数据库中已有的发票号，作更新；
 *                                          2.由于发票号不能查询出唯一的记录来，故取消
 *                                            了以发票号来更新，则把1这些语句注释掉了
 *                                            －－先另删掉这些注释，日后也许有用；
 *                                          3.endTransferItem()中增加了判断用户是否
 *                                            填写了“缴费日期”，若没填写则更为当前日期
 *                                            时间；
 *                                          4.在startTransferItem()中，增加了验证输入
 *                                            日期的合法性，若日期不为空；
 ********************************************************************************/
package com.shufe.service.fee;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.eams.system.basecode.industry.CurrencyCategory;
import com.shufe.dao.system.calendar.TeachCalendarDAO;
import com.shufe.model.fee.FeeDetail;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 一次性导入excel中的所有收费信息
 * 
 * @author chaostone
 * 
 */
public class FeeDetailImportListener extends ItemImporterListener {
    
    private List feeDetails = new ArrayList();
    
    private UtilService utilService;
    
    private TeachCalendarDAO teachCalendarDAO;
    
    private CurrencyCategory rmb = null;
    
    /**
     * 缓存日历
     */
    private Map calendarMap = new HashMap();
    
    public FeeDetailImportListener(UtilService utilService, TeachCalendarDAO teachCalendarDAO) {
        this.utilService = utilService;
        this.teachCalendarDAO = teachCalendarDAO;
    }
    
    public void startTransfer(TransferResult tr) {
        rmb = (CurrencyCategory) utilService.get(CurrencyCategory.class, CurrencyCategory.RMB);
        super.startTransfer(tr);
    }
    
    // FIXME 暂时不做了 ---- 先别删除下面注释掉的语句
    public void startTransferItem(TransferResult tr) {
        // 检验缴费日期的合法性
        Map map = this.importer.curDataMap();
        String strCreateAt = (String) map.get("createAt");
        if (StringUtils.isNotEmpty(strCreateAt)) {
            try {
                Date.valueOf(strCreateAt);
            } catch (Exception e) {
                tr.addFailure("create at is invalid Date-Format(yyyy-M-d).", strCreateAt);
            }
        }
        // Object obj1 = map.get("invoiceCode");
        // if (obj1 == null) {
        // return;
        // }
        // String invoiceCode = obj1.toString();
        // FIXME here 先别删除这些注释掉的语句
        // if (isValidInvoiceCode(tr, invoiceCode, LETTERNUMBERCHAIN)) {
        // Object obj2 = utilService.loadByKey(FeeDetail.class, "invoiceCode",
        // invoiceCode);
        // if (obj2 == null) {
        super.startTransferItem(tr);
        // } else {
        // this.importer.setCurrent(obj2);
        // }
        // } else {
        // tr.addFailure("invoice code is invalid.", invoiceCode);
        // }
    }
    
    public void endTransfer(TransferResult tr) {
        // 若是无错，则保存或更新
        if (!tr.hasErrors()) {
            utilService.saveOrUpdate(feeDetails);
        }
    }
    
    public void endTransferItem(TransferResult tr) {
        FeeDetail feeDetail = (FeeDetail) this.importer.getCurrent();
        // 学生
        checkEmpty(tr, feeDetail.getStd(), "student is empty.");
        // 收费部门
        checkEmpty(tr, feeDetail.getDepart(), "feeing department is empty.");
        // 发票号
        if (feeDetail.getInvoiceCode() == null) {
            tr.addMessage("no invoice code. so use default code.", "");
            feeDetail.setInvoiceCode(null);
        }
        // 交费类型
        checkEmpty(tr, feeDetail.getType(), "fee type is empty.");
        // 交费方式
        checkEmpty(tr, feeDetail.getMode(), "fee mode is empty.");
        // 应缴费用
        checkEmpty(tr, feeDetail.getShouldPay(), "should-payment is empty.");
        // 货币类型
        if (!ValidEntityPredicate.INSTANCE.evaluate(feeDetail.getCurrencyCategory())) {
            feeDetail.setCurrencyCategory(rmb);
        }
        // 收费人
        checkEmpty(tr, feeDetail.getWhoAdded(), "toll collector is empty.");
        feeDetail.setWhoModified(feeDetail.getWhoAdded());
        feeDetail.setModifyAt(new Date(System.currentTimeMillis()));
        // 汇率
        if (feeDetail.getRate() == null) {
            Float rateRMB = feeDetail.getCurrencyCategory().getRateToRMB();
            tr.addMessage("use rate of default value.", rateRMB);
            feeDetail.setRate(rateRMB);
        }
        // 实缴金额
        if (feeDetail.getPayed() == null) {
            feeDetail.setPayed(new Float(0));
        }
        // 折合人民币
        if (feeDetail.getToRMB() == null) {
            Float value = new Float(feeDetail.getPayed().floatValue()
                    * feeDetail.getRate().intValue());
            tr.addMessage("no value of toRMB, so default value assigned to one.", value);
            feeDetail.setToRMB(value);
        }
        // 学年度与学期
        String year = (String) importer.curDataMap().get("calendar.year");
        String term = (String) importer.curDataMap().get("calendar.term");
        if (StringUtils.isEmpty(year) || StringUtils.isEmpty(term)) {
            tr.addFailure("no calendar-year or calendar-term", "");
        } else if (null == feeDetail.getStd()) {
            tr.addFailure("can't get teachCalendar", "");
        } else {
            processTeachCalendar(tr, feeDetail);
        }
        // 缴费日期：为现在日期
        if (feeDetail.getCreateAt() == null) {
            feeDetail.setCreateAt(new Date(System.currentTimeMillis()));
        }
        
        // 如若无错，则加入list中
        if (!tr.hasErrors()) {
            feeDetails.add(feeDetail);
        }
    }
    
    /**
     * 学年、学期的处理
     * 
     * @param tr
     * @param feeDetail
     */
    private void processTeachCalendar(TransferResult tr, FeeDetail feeDetail) {
        String calendarMapId = feeDetail.getStd().getType().getId() + "_"
                + feeDetail.getCalendar().getYear() + "_" + feeDetail.getCalendar().getTerm();
        
        TeachCalendar calendar = (TeachCalendar) calendarMap.get(calendarMapId);
        if (null == calendar) {
            calendar = teachCalendarDAO.getTeachCalendar(feeDetail.getStd().getType().getId(),
                    feeDetail.getCalendar().getYear(), feeDetail.getCalendar().getTerm());
            if (null == calendar) {
                tr.addFailure("error.parameters.illegal", importer.curDataMap()
                        .get("calendar.year")
                        + " " + importer.curDataMap().get("calendar.term"));
            } else {
                calendarMap.put(calendarMapId, calendar);
            }
        }
        
        if (null == calendar) {
            tr.addFailure("can't findt teachCalendar with std code", feeDetail.getStd().getType()
                    .getId()
                    + ":"
                    + feeDetail.getCalendar().getYear()
                    + ":"
                    + feeDetail.getCalendar().getTerm());
        }
        feeDetail.setCalendar(calendar);
        
    }
    
    /**
     * 验证对象或数值是否为空
     * 
     * @param tr
     * @param data
     * @param obj
     * @param errorMessage
     */
    private boolean checkEmpty(TransferResult tr, Object obj, String errorMessage) {
        if (obj == null || (obj instanceof String) && StringUtils.isEmpty(((String) obj))) {
            tr.addFailure(errorMessage, "null");
            return true;
        }
        return false;
    }
    
    /** 发票号由数字组成 */
    private final int NUMBERCHAIN = 1;
    
    /** 发票号由字母和数字组成 */
    private final int LETTERNUMBERCHAIN = 2;
    
    /** 发票号由字母和数字组成，但号码的第一位必须为字母 */
    private final int FIRSTLETTERCHAIN = 3;
    
    /**
     * 验证发票号的合法性
     * 
     * @param tr
     * @param invoiceCode
     * @param flag
     * @return
     */
    protected boolean isValidInvoiceCode(TransferResult tr, String invoiceCode, int flag) {
        // FIXME: 调用处是在上面 FIXME 处
        if (checkEmpty(tr, invoiceCode, "invoiceCode is null")) {
            return false;
        }
        boolean isNumber = true;
        boolean isLetter = true;
        for (int i = 0; i < invoiceCode.length(); i++) {
            switch (flag) {
                case NUMBERCHAIN:
                    if (invoiceCode.charAt(i) >= '0' && invoiceCode.charAt(i) <= '9') {
                        isNumber = true;
                    } else {
                        isNumber = false;
                    }
                    if (isNumber == false) {
                        return false;
                    }
                    break;
                case FIRSTLETTERCHAIN:
                    if (i == 0
                            && (invoiceCode.charAt(i) >= 'A' && invoiceCode.charAt(i) <= 'Z') == false) {
                        return false;
                    }
                case LETTERNUMBERCHAIN:
                    if (invoiceCode.charAt(i) >= 'A' && invoiceCode.charAt(i) <= 'Z') {
                        isLetter = true;
                    } else {
                        isLetter = false;
                    }
                    if (invoiceCode.charAt(i) >= '0' && invoiceCode.charAt(i) <= '9') {
                        isNumber = true;
                    } else {
                        isNumber = false;
                    }
                    if (isLetter == false && isNumber == false) {
                        return false;
                    }
                    break;
                
                default:
                    return false;
            }
        }
        return true;
    }
    
    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }
    
}

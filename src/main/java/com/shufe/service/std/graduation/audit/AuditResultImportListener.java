//$Id: OtherGradeImportListener.java,v 1.1 2008-2-2 下午12:36:00 zhihe Exp $
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
 * @author zhihe
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *zhihe      2007-3-19         Created
 *  
 ********************************************************************************/

package com.shufe.service.std.graduation.audit;

import java.util.List;
import java.util.Map;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;

/**
 * 学生学历号和学位号导入监听器,实现全部数据导入的完整性。<br>
 * 依照学生、专业类别作为唯一标识
 * 
 * @author zhihe
 * 
 */
public class AuditResultImportListener extends ItemImporterListener {
    
    private UtilDao utilDao;
    
    public AuditResultImportListener() {
        super();
    }
    
    public AuditResultImportListener(UtilDao utilDao) {
        super();
        this.utilDao = utilDao;
    }
    
    public void startTransferItem(TransferResult tr) {
        // 为了更新查找已有的审核结果
        Map datas = importer.curDataMap();
        // 1.对未进行学位审核的学生不进行导入
        if (null == datas.get("majorType.code")) {// 检查该学生专业类别是否存在
            tr.addFailure("专业类别为空", importer.curDataMap().get("majorType.code"));
        }
        EntityQuery query = new EntityQuery(AuditResult.class, "audit");
        query.add(new Condition("audit.std.code=:stdCode", datas.get("std.code")));
        query.add(new Condition("audit.majorType.code=:majorTypeCode", datas.get("majorType.code")
                .toString()));
        List audits = (List) utilDao.search(query);
        if (audits.size() == 1) {
            importer.setCurrent(audits.get(0));
        }
        // 1.对未进行学位审核的学生不进行导入
        // 检查该学生审核结果是否存在
        if (audits.isEmpty()) {
            tr.addFailure("学生该专业类别审核结果不存在", importer.curDataMap().get("std.code"));
        }
    }
    
    public void endTransferItem(TransferResult tr) {
        AuditResult auditResult = (AuditResult) importer.getCurrent();
        utilDao.saveOrUpdate(auditResult);// 直接保存
    }
    
    public void setUtilDao(UtilDao utilDao) {
        this.utilDao = utilDao;
    }
    
}

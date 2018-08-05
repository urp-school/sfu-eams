//$Id: FineCourseImportListener.java,v 1.1 2007-4-18 下午09:39:12 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-4-18         Created
 *  
 ********************************************************************************/

package com.shufe.service.quality.fineCourse;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.shufe.model.quality.fineCourse.FineCourse;

/**
 * 精品课程导入监听器
 * 
 * @author chaostone
 * 
 */
public class FineCourseImportListener extends ItemImporterListener {

    FineCourseService fineCourseService;

    UtilService utilService;

    public FineCourseImportListener() {
        super();
    }

    public FineCourseImportListener(FineCourseService fineCourseService,
            UtilService utilService) {
        super();
        this.fineCourseService = fineCourseService;
        this.utilService = utilService;
    }

    public void endTransferItem(TransferResult tr) {
        FineCourse fineCourse = (FineCourse) importer.getCurrent();
        int errors = tr.errors();
        if (fineCourse == null) {
            tr.addFailure("error.parameters.illegal", "导入内容是非法数据！");
        }
        if (StringUtils.isEmpty(fineCourse.getCourseName())) {
            tr.addFailure("error.parameters.needed", "course name");
        }
        if (fineCourse.getLevel() == null) {
            tr.addFailure("error.parameters.illegal", "level.code非法数据!");
        }
        if (tr.errors() == errors) {
            EntityUtils.evictEmptyProperty(fineCourse);
            fineCourseService.saveOrUpdate(fineCourse);
        }
    }
}

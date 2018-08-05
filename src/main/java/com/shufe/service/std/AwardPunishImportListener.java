//$Id: OtherGradeImportListener.java,v 1.1 2007-3-19 下午12:36:00 chaostone Exp $
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
 *chaostone      2007-3-19         Created
 *  
 ********************************************************************************/

package com.shufe.service.std;

import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.std.awardPunish.Award;
import com.shufe.model.std.awardPunish.Punishment;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 奖惩导入监听器 * 
 * @author zhihe
 * 
 */
public class AwardPunishImportListener extends ItemImporterListener {
    
    private UtilDao utilDao;
    
    /** 缓存日历 */
    private Map calendarMap = new HashMap();
    
    private String kind;
    
    public AwardPunishImportListener() {
        super();
    }
    
    public AwardPunishImportListener(UtilDao utilDao, String kind) {
        super();
        this.utilDao = utilDao;
        this.kind = kind;
        List calendars = utilDao.loadAll(TeachCalendar.class);
        for (Iterator it = calendars.iterator(); it.hasNext();) {
            TeachCalendar calendar = (TeachCalendar) it.next();
            calendarMap.put(calendar.getStudentType().getId() + "_" + calendar.getYear() + "_"
                    + calendar.getTerm(), calendar);
        }
        
    }
    
    public void startTransferItem(TransferResult tr) {
        String name = (String) importer.curDataMap().get("name");
        if (StringUtils.isEmpty(name)) {
            tr.addFailure("error.parameters.illegal", "奖励/处分名称未填写");
        }
        String typeCode = String.valueOf(importer.curDataMap().get("type.code"));
        if (StringUtils.isEmpty(typeCode)) {
            tr.addFailure("error.parameters.illegal", "奖励/处分类别代码[" + typeCode + "]");
        }
        String stdCode = String.valueOf(importer.curDataMap().get("std.code"));
        if (StringUtils.isEmpty(stdCode)) {
            tr.addFailure("error.parameters.illegal", "学号[" + stdCode + "]");
        }
        String year = String.valueOf(importer.curDataMap().get("calendar.year"));
        String term = String.valueOf(importer.curDataMap().get("calendar.term"));
        if (StringUtils.isEmpty(year) || StringUtils.isEmpty(term)) {
            tr.addFailure("error.parameters.illegal", "教学日历["
                    + importer.curDataMap().get("calendar.year") + " "
                    + importer.curDataMap().get("calendar.term") + "]");
        }
        String presentOn = String
                .valueOf(importer.curDataMap().get("presentOn"));
        if (StringUtils.isEmpty(presentOn)) {
            tr.addFailure("error.parameters.illegal", "奖励/处分日期未填写");
        }
        String isValid = String
                .valueOf(importer.curDataMap().get("isValid"));
        if (StringUtils.isEmpty(isValid)) {
            tr.addFailure("error.parameters.illegal", "奖励/处分是否有效未填写");
        }
        if (tr.errors() == 0) {
            // 如果出现已有的则进行更新
            EntityQuery query = null;
            if ("award".equals(kind)) {
                query = new EntityQuery(Award.class, "awardPunish");
            } else {
                query = new EntityQuery(Punishment.class, "awardPunish");
            }
            query.add(new Condition("awardPunish.std.code = :stdCode", stdCode));
            query.add(new Condition("awardPunish.type.code = :typeCode", typeCode));
            query.add(new Condition("awardPunish.calendar.year = :year", year));
            query.add(new Condition("awardPunish.calendar.term = :term", term));
            query.add(new Condition("awardPunish.presentOn = :presentOn", Date.valueOf(presentOn)));
            List results = (List) utilDao.search(query);
            if (CollectionUtils.isNotEmpty(results)) {
                importer.setCurrent(results.get(0));
            }
        }
    }
    
    public void endTransferItem(TransferResult tr) {
        if (tr.errors() == 0) {
            if ("award".equals(kind)) {
                Award award = (Award) importer.getCurrent();  
                if (award.isVO()) {
                    StudentType studentType = (StudentType)award.getStd().getType();
                    String calendarMapId = studentType.getId() + "_"
                            + award.getCalendar().getYear() + "_"
                            + award.getCalendar().getTerm();
                    TeachCalendar teachCalendar = (TeachCalendar) calendarMap.get(calendarMapId);
                    //查找教学日历
                    if (null == teachCalendar) {
                        while (null != studentType.getSuperType()) {
                            studentType = studentType.getSuperType();
                            calendarMapId = award.getStd().getType().getSuperType().getId() + "_"
                            + award.getCalendar().getYear() + "_"
                            + award.getCalendar().getTerm();
                            teachCalendar = (TeachCalendar) calendarMap.get(calendarMapId);
                        }
                    }
                    if (null == award.getCalendar()) {
                        tr.addFailure("error.parameters.illegal", "该学生对应日历["
                                + importer.curDataMap().get("calendar.year") + " "
                                + importer.curDataMap().get("calendar.term") + "]");
                    }
                    award.setCalendar((TeachCalendar) calendarMap.get(calendarMapId));
                }
                
                utilDao.saveOrUpdate(award);
            } else {
                Punishment punishment = (Punishment) importer.getCurrent();
                if (punishment.isVO()) {
                    StudentType studentType = (StudentType)punishment.getStd().getType();
                    String calendarMapId = studentType.getId() + "_"
                            + punishment.getCalendar().getYear() + "_"
                            + punishment.getCalendar().getTerm();
                    TeachCalendar teachCalendar = (TeachCalendar) calendarMap.get(calendarMapId);
                    //查找教学日历
                    if (null == teachCalendar) {
                        while (null != studentType.getSuperType()) {
                            studentType = studentType.getSuperType();
                            calendarMapId = punishment.getStd().getType().getSuperType().getId() + "_"
                            + punishment.getCalendar().getYear() + "_"
                            + punishment.getCalendar().getTerm();
                            teachCalendar = (TeachCalendar) calendarMap.get(calendarMapId);
                        }
                    }
                    if (null == punishment.getCalendar()) {
                        tr.addFailure("error.parameters.illegal", "该学生对应日历["
                                + importer.curDataMap().get("calendar.year") + " "
                                + importer.curDataMap().get("calendar.term") + "]");
                    }
                    punishment.setCalendar((TeachCalendar) calendarMap.get(calendarMapId));
                }
                
                utilDao.saveOrUpdate(punishment);
            }
            
            
        }
    }
    
    public void setUtilDao(UtilDao utilDao) {
        this.utilDao = utilDao;
    }

    
    public void setCalendarMap(Map calendarMap) {
        this.calendarMap = calendarMap;
    }

    
    public void setKind(String kind) {
        this.kind = kind;
    }

}

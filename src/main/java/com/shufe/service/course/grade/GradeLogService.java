//$Id: GradeLogService.java,v 1.1 2009-7-9 下午02:02:09 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2009-7-9             Created
 *  
 ********************************************************************************/

package com.shufe.service.course.grade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ekingstar.security.User;
import com.shufe.model.course.grade.GradeLog;

/**
 * 成绩日志
 * 
 * @author zhouqi
 * 
 */
public interface GradeLogService {
    
    public GradeLog getGradeLog(final String catalogId);
    
    public GradeLog getGradeLog(final Long catalogId);
    
    /**
     * 成绩日志记录
     * 
     * @param user
     * @param grades
     * @param inputGradeMap
     *            key [grade] <br>
     *            key =
     *            gradeId_displayGradeScore_displayExamGrade1Score_displayExamGrade2Score[_...]<br>
     *            当与 scoreMap 同时为 null 时，视为新课录入成绩，即进入设置成绩百分比后，点击保存
     * @param scoreMap
     *            key [gradeScore] <br>
     *            key = gradeId
     * @return
     */
    public Collection buildGradeCatalogInfo(final User user, final List grades,
            final Map inputGradeMap, final Map scoreMap);
    
    public Collection buildGradeCatalogInfo(final User user, final List grades,
            final Map inputGradeMap, final Map scoreMap, boolean isRemove);
    
    public Collection buildGradeCatalogInfo(final User user, final List grades,
            final Map inputGradeMap, final Map scoreMap, final String content,
            final boolean isRemove);
}

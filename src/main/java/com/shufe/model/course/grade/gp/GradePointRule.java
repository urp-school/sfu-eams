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
 * chaostone             2007-1-2            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.gp;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.grade.Grade;

/**
 * 绩点映射规则
 * 
 * @author chaostone
 * 
 */
public class GradePointRule extends LongIdObject {
    
    private static final long serialVersionUID = 4800750976685704027L;
    
    public static final Long defaultRuleId = new Long(0);
    
    private String name;
    
    private StudentType stdType;// 基础学生类别
    
    private Set GPMappings = new HashSet(); // 绩点对照表
    
    private MarkStyle markStyle = new MarkStyle();// 成绩记录方式
    
    public Float calcGP(Grade grade) {
        Float score = grade.getScore();
        if (null == score || score.floatValue() <= 0) {
            return new Float(0);
        } else {
            for (Iterator iter = getGPMappings().iterator(); iter.hasNext();) {
                GPMapping gpMapping = (GPMapping) iter.next();
                if (gpMapping.inScope(score)) {
                    return gpMapping.getGp();
                }
            }
        }
        // 默认绩点为00;
        return new Float(0);
    }
    
    public Set getGPMappings() {
        return GPMappings;
    }
    
    public void setGPMappings(Set mappings) {
        GPMappings = mappings;
    }
    
    public MarkStyle getMarkStyle() {
        return markStyle;
    }
    
    public void setMarkStyle(MarkStyle markStyle) {
        this.markStyle = markStyle;
    }
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}

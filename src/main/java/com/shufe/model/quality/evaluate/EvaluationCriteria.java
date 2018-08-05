//$Id: EvaluationCriteria.java,v 1.1 2007-6-2 下午12:22:18 chaostone Exp $
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
 *chaostone      2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Department;

/**
 * 评教对照标准
 * 
 * @author chaostone
 * 
 */
public class EvaluationCriteria extends LongIdObject {
    
    /**
     * 
     */
    private static final long serialVersionUID = -6041943037223551591L;
    
    /** 默认对照标准ID */
    public static final Long DEFAULTID = new Long(1);
    
    /** 名称 */
    private String name;
    
    /** 具体分值对照项 */
    private Set criteriaItems = new HashSet();
    
    /** 制作部门 */
    private Department depart;
    
    public String getEvaluation(float score) {
        if (!CollectionUtils.isEmpty(criteriaItems)) {
            for (Iterator iter = criteriaItems.iterator(); iter.hasNext();) {
                EvaluationCriteriaItem item = (EvaluationCriteriaItem) iter.next();
                if (item.inScope(score)) {
                    return item.getName();
                }
            }
        }
        return new DecimalFormat("##.0").format(score);
    }
    
    public Set getCriteriaItems() {
        return criteriaItems;
    }
    
    public void setCriteriaItems(Set criteriaItems) {
        this.criteriaItems = criteriaItems;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Department getDepart() {
        return depart;
    }
    
    public void setDepart(Department depart) {
        this.depart = depart;
    }
    
    public void addItem(EvaluationCriteriaItem item) {
        item.setCriteria(this);
        getCriteriaItems().add(item);
    }
}

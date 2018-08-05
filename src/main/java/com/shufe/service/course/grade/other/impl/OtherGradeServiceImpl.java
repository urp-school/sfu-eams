//$Id: OtherGradeServiceImpl.java,v 1.1 2007-2-27 下午07:42:46 chaostone Exp $
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
 *chaostone      2007-2-27         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.grade.other.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.course.grade.other.OtherGradeService;

public class OtherGradeServiceImpl extends BasicService implements OtherGradeService {
    
    public OtherGrade getBestGrade(Student std, OtherExamCategory category) {
        EntityQuery query = new EntityQuery(OtherGrade.class, "grade");
        query.add(new Condition("grade.std.id=" + std.getId()));
        query.add(new Condition("grade.category.id=" + category.getId()));
        query.addOrder(new Order("grade.score", Order.DESC));
        List grades = (List) utilService.search(query);
        if (grades.isEmpty())
            return null;
        else
            return (OtherGrade) grades.get(0);
    }
    
    /**
     * isPerTermBest 为 null 或 false 时，为查询全部；否则为每学期最佳成绩
     * 
     * @see com.shufe.service.course.grade.other.OtherGradeService#getOtherGrades(com.shufe.model.std.Student,
     *      java.lang.Boolean)
     */
    public List getOtherGrades(Student std, Boolean isBest) {
        EntityQuery query = new EntityQuery(OtherGrade.class, "grade");
        query.add(new Condition("grade.std.id=" + std.getId()));
        query.addOrder(new Order("grade.category.kind.id"));
        query.addOrder(new Order("grade.score"));
        
        List grades = (List) utilService.search(query);
        if (grades.isEmpty()) {
            return Collections.EMPTY_LIST;
        } else {
            if (null != isBest && Boolean.TRUE.equals(isBest)) {
                Map gradesMap = new HashMap();
                for (Iterator it = grades.iterator(); it.hasNext();) {
                    OtherGrade grade = (OtherGrade) it.next();
                    gradesMap.put(grade.getCategory().getId(), grade);
                }
                return new ArrayList(gradesMap.values());
            } else {
                return grades;
            }
        }
    }
    
    public List getPassGradesOf(Student std, Collection otherExamCategories) {
        EntityQuery query = new EntityQuery(OtherGrade.class, "grade");
        query.add(new Condition("grade.std.id=" + std.getId()));
        if (otherExamCategories.size()!=0) {
            query.add(new Condition("grade.category in (:categories)", otherExamCategories));
        } else {
            return new ArrayList();
        }
        query.add(new Condition("grade.isPass=true"));
        return (List) utilDao.search(query);
    }
    
    public boolean isPass(Student std, OtherExamCategory category) {
        EntityQuery query = new EntityQuery(OtherGrade.class, "grade");
        query.add(new Condition("grade.std.id=" + std.getId()));
        query.add(new Condition("grade.category.id=" + category.getId()));
        query.add(new Condition("grade.isPass=true"));
        query.setSelect("count(*)");
        List rs = (List) utilDao.search(query);
        if (rs.isEmpty())
            return false;
        else {
            Number count = (Number) rs.get(0);
            if (null == count)
                return false;
            else
                return count.intValue() > 0;
        }
    }
    
}

//$Id: OtherGradeService.java,v 1.1 2007-2-27 下午07:40:16 chaostone Exp $
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

package com.shufe.service.course.grade.other;

import java.util.Collection;
import java.util.List;

import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.model.std.Student;

/**
 * 其他考试成绩服务接口
 * 
 * @author chaostone
 */
public interface OtherGradeService {
    
    public OtherGrade getBestGrade(Student std, OtherExamCategory category);
    
    /** 查询通过的考试成绩 */
    public List getPassGradesOf(Student std, Collection otherExamCategories);
    
    /**
     * 查询某类成绩类型是否通过
     */
    public boolean isPass(Student std, OtherExamCategory category);
    
    /** 查询学生所有校外考试每学期最佳成绩 */
    public List getOtherGrades(Student std, Boolean isBest);
}

//$Id: TeacherStatServiceImpl.java,v 1.1 2007-4-3 下午03:57:34 chaostone Exp $
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
 *chaostone      2007-4-3         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo.stat.impl;

import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.School;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.basecode.state.EduDegree;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.BasicService;
import com.shufe.service.system.baseinfo.stat.TeacherStatService;
import com.shufe.service.util.stat.StatHelper;
import com.shufe.util.DataRealmUtils;

public class TeacherStatServiceImpl extends BasicService implements TeacherStatService {
    
    public List statByDegree(DataRealm dataRelm) {
        EntityQuery query = new EntityQuery(Teacher.class, "teacher");
        query.setSelect("new  com.shufe.service.util.stat.StatItem(degreeInfo.degree.id,count(*))");
        query.join("left", "teacher.degreeInfo", "degreeInfo");
        dataRelm.setStudentTypeIdSeq(null);
        DataRealmUtils.addDataRealm(query, new String[] { "", "teacher.department.id" }, dataRelm);
        query.groupBy("degreeInfo.degree.id");
        query.add(new Condition("teacher.isTeaching=true"));
        List stats = (List) utilDao.search(query);
        new StatHelper(utilService).setStatEntities(stats, Degree.class);
        return stats;
    }
    
    public List statByEduDegree(DataRealm dataRelm) {
        EntityQuery query = new EntityQuery(Teacher.class, "teacher");
        query
                .setSelect("new  com.shufe.service.util.stat.StatItem(degreeInfo.eduDegreeInside.id,count(*))");
        query.join("left", "teacher.degreeInfo", "degreeInfo");
        dataRelm.setStudentTypeIdSeq(null);
        DataRealmUtils.addDataRealm(query, new String[] { "", "teacher.department.id" }, dataRelm);
        query.groupBy("degreeInfo.eduDegreeInside.id");
        query.add(new Condition("teacher.isTeaching=true"));
        List stats = (List) utilDao.search(query);
        new StatHelper(utilService).setStatEntities(stats, EduDegree.class);
        return stats;
    }
    
    public List statByTitle(DataRealm dataRelm) {
        EntityQuery query = new EntityQuery();
        query.setSelect("new  com.shufe.service.util.stat.StatItem(teacher.title.id,count(*))");
        query.setFrom("Teacher teacher");
        dataRelm.setStudentTypeIdSeq(null);
        DataRealmUtils.addDataRealm(query, new String[] { "", "teacher.department.id" }, dataRelm);
        query.groupBy("teacher.title.id");
        query.add(new Condition("teacher.isTeaching=true"));
        List stats = (List) utilDao.search(query);
        new StatHelper(utilService).setStatEntities(stats, TeacherTitle.class);
        return stats;
    }
    
    public List statByGraduateSchool(DataRealm dataRelm) {
        EntityQuery query = new EntityQuery(Teacher.class, "teacher");
        query
                .setSelect("new com.shufe.service.util.stat.StatItem(degreeInfo.graduateSchool.id,count(*))");
        query.join("left", "teacher.degreeInfo", "degreeInfo");
        dataRelm.setStudentTypeIdSeq(null);
        DataRealmUtils.addDataRealm(query, new String[] { "", "teacher.department.id" }, dataRelm);
        query.groupBy("degreeInfo.graduateSchool.id");
        query.add(new Condition("teacher.isTeaching=true"));
        List stats = (List) utilDao.search(query);
        new StatHelper(utilService).setStatEntities(stats, School.class);
        return stats;
    }
}

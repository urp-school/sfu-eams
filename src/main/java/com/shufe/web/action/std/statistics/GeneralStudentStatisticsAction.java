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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.web.action.std.statistics;

import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.util.DataRealmLimit;
import com.shufe.web.action.std.StudentSearchSupportAction;

/**
 * 学籍信息通用统计类
 */
class GeneralStudentStatisticsAction extends StudentSearchSupportAction {
    
    /**
     * 初始化学生统计菜单
     * 
     * @param dataRealmLimit
     */
    protected void initStudentStatisticsBar(DataRealmLimit dataRealmLimit) {
        String studentTypeIds = dataRealmLimit.getDataRealm().getStudentTypeIdSeq();
        String departmentIds = dataRealmLimit.getDataRealm().getDepartmentIdSeq();
        List stdTypes = studentTypeService.getStudentTypes(studentTypeIds);
        Collections.sort(stdTypes, new PropertyComparator("code"));
        Results.addObject("stdTypeList", stdTypes);
        Results.addObject("departmentList", departmentService.getColleges(departmentIds));
        Speciality speciality = new Speciality();
        speciality.setMajorType(new MajorType(MajorType.SECOND));
        Results.addObject("secondSpecialityList", specialityService.getSpecialities(speciality,
                studentTypeIds, departmentIds));
    }
}

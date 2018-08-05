//$Id: InstructWorkloadServiceImpl.java,v 1.4 2006/12/19 13:08:41 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-21         Created
 *  
 ********************************************************************************/

package com.shufe.service.workload.instruct.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.degree.instruct.InstructionDAO;
import com.shufe.dao.workload.instruct.InstructWorkloadDAO;
import com.shufe.model.std.graduation.practice.GraduatePractice;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.model.workload.Modulus;
import com.shufe.model.workload.instruct.InstructModulus;
import com.shufe.model.workload.instruct.InstructWorkload;
import com.shufe.service.BasicService;
import com.shufe.service.std.graduation.practice.GraduatePracticeService;
import com.shufe.service.workload.ModulusService;
import com.shufe.service.workload.instruct.InstructWorkloadService;

public class InstructWorkloadServiceImpl extends BasicService implements InstructWorkloadService {
    
    private InstructWorkloadDAO instructWorkloadDAO;
    
    private ModulusService instructModulusService;
    
    private GraduatePracticeService graduatePracticeService;
    
    private InstructionDAO instructionDAO;
    
    /**
     * @see com.shufe.service.workload.impl.InstructWorkloadService#getInstructModulus(java.lang.String,
     *      java.lang.String)
     */
    public InstructModulus getInstructModulus(Long studentTypeId, Long departmentId) {
        if (null == studentTypeId || null == departmentId) {
            return null;
        }
        Modulus modulus = new Modulus();
        modulus.getStudentType().setId(studentTypeId);
        modulus.getDepartment().setId(departmentId);
        return (InstructModulus) instructModulusService.getUniqueModulus(modulus, String
                .valueOf(studentTypeId));
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.impl.InstructWorkloadService#findStatisticObjects(org.hibernate.Criteria,
     *      int, int)
     */
    public Pagination findStatisticObjects(Criteria criteria, int pageNo, int pageSize) {
        return instructWorkloadDAO.dynaSearch(criteria, pageNo, pageSize);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.impl.InstructWorkloadService#getStatisticsObjects(com.shufe.model.workload.InstructWorkload,
     *      java.lang.String)
     */
    public Criteria getStatisticsObjects(InstructWorkload instructWorkload, String departmentIds,
            String age1, String age2) {
        return instructWorkloadDAO.getExampleInstructWorkloads(instructWorkload, departmentIds,
                age1, age2);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.InstructWorkloadService#affirmType(java.lang.String,
     *      java.lang.String)
     */
    public void affirmType(String affirmType, String teachWorkloadIds, boolean b) {
        if (StringUtils.isNotEmpty(teachWorkloadIds))
            instructWorkloadDAO.affirmType(affirmType, teachWorkloadIds, b);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.InstructWorkloadService#affirmALl(java.lang.String,
     *      java.lang.String, boolean)
     */
    public void affirmALl(String stdTypIds, String typeName, boolean b) {
        instructWorkloadDAO.updateAffirmAll(stdTypIds, typeName, b);
    }
    
    /**
     * 根据学生类别得到当前的教学日历.
     * 
     * @see com.shufe.service.workload.instruct.InstructWorkloadService#getCurrentCalendarByStdType(java.lang.String)
     */
    public Collection getCurrentCalendarByStdType(String studentTypeIds) {
        List teachCalendars = utilService.load(TeachCalendar.class, "studentType.id", SeqStringUtil
                .transformToLong(studentTypeIds));
        List currents = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        for (Iterator iter = teachCalendars.iterator(); iter.hasNext();) {
            TeachCalendar element = (TeachCalendar) iter.next();
            if (element.getStart().before(calendar.getTime())
                    && element.getFinish().after(calendar.getTime())) {
                currents.add(element);
            }
        }
        return currents;
    }
    
    /**
     * @see com.shufe.service.workload.instruct.InstructWorkloadService#statisticPractice(java.lang.String,
     *      java.lang.String, java.lang.Long, int, int)
     */
    public void statWorkload(TeachCalendar teachCalendar, DataRealm realm, String modulusType) {
        instructWorkloadDAO.batchRemove(teachCalendar, realm, modulusType);
        int fail = 0;
        // 组装工作量系数
        InstructModulus modulusExample = new InstructModulus();
        modulusExample.setItemType(modulusType);
        List instructModuluses = instructModulusService.getModulus(modulusExample, realm
                .getStudentTypeIdSeq());
        
        Map graduateModuluMap = new HashMap();
        for (Iterator iter = instructModuluses.iterator(); iter.hasNext();) {
            InstructModulus element = (InstructModulus) iter.next();
            graduateModuluMap.put(element.getStudentType().getId(), element);
        }
        
        // 加载统计数据
        List stdTypeTeachers = null;
        if (modulusType.equals(InstructModulus.PARCTICE)) {
            GraduatePractice graduatePractice = new GraduatePractice();
            graduatePractice.setTeachCalendar(teachCalendar);
            stdTypeTeachers = graduatePracticeService.getPropertyOfPractices(graduatePractice,
                    realm.getDepartmentIdSeq(), realm.getStudentTypeIdSeq(),
                    "student.type,practiceTeacher", Boolean.TRUE);
        } else if (modulusType.equals(InstructModulus.THESIS)) {
            stdTypeTeachers = instructionDAO.statStdTypeTeacher(teachCalendar, realm, Boolean.TRUE);
        } else {
            stdTypeTeachers = instructionDAO
                    .statStdTypeTeacher(teachCalendar, realm, Boolean.FALSE);
        }
        List instructWorkloads = new ArrayList();
        for (Iterator iter = stdTypeTeachers.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            Number stdNum = (Number) element[0];
            StudentType stdType = (StudentType) element[1];
            Teacher teacher = (Teacher) element[2];
            if (null == teacher)
                continue;
            InstructWorkload instructWorkload = new InstructWorkload(teacher);
            InstructModulus modulus = null;
            while (null == modulus && null != stdType) {
                modulus = (InstructModulus) graduateModuluMap.get(stdType.getId());
                stdType = (StudentType) stdType.getSuperType();
            }
            if (null == modulus) {
                fail++;
                continue;
            }
            instructWorkload.setModulus(modulus);
            instructWorkload.setStudentNumber(new Integer(stdNum.intValue()));
            instructWorkload.setStudentType((StudentType) element[1]);
            instructWorkload.setTeachCalendar(teachCalendar);
            instructWorkload.setTotleWorkload(new Float(modulus.getModulusValue().floatValue()
                    * stdNum.floatValue()));
            instructWorkloads.add(instructWorkload);
        }
        utilService.saveOrUpdate(instructWorkloads);
    }
    
    public void setInstructionDAO(InstructionDAO instructionDAO) {
        this.instructionDAO = instructionDAO;
    }
    
    /**
     * @param graduatePracticeService
     *            The graduatePracticeService to set.
     */
    public void setGraduatePracticeService(GraduatePracticeService graduatePracticeService) {
        this.graduatePracticeService = graduatePracticeService;
    }
    
    /**
     * @param instructModulusService
     *            The instructModulusService to set.
     */
    public void setInstructModulusService(ModulusService instructModulusService) {
        this.instructModulusService = instructModulusService;
    }
    
    /**
     * @param instructWorkloadDAO
     *            The instructWorkloadDAO to set.
     */
    public void setInstructWorkloadDAO(InstructWorkloadDAO instructWorkloadDAO) {
        this.instructWorkloadDAO = instructWorkloadDAO;
    }
    
}

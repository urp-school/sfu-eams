//$Id: Speciality2ndSignUpServiceImpl.java,v 1.1 2007-5-7 下午03:31:33 chaostone Exp $
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
 *chaostone      2007-5-7         Created
 *  
 ********************************************************************************/

package com.shufe.service.std.speciality2nd.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.functors.EqualPredicate;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.dao.std.speciality2nd.Speciality2ndSignUpDAO;
import com.shufe.model.std.Student;
import com.shufe.model.std.speciality2nd.MatriculateParams;
import com.shufe.model.std.speciality2nd.SignUpGPASetting;
import com.shufe.model.std.speciality2nd.SignUpSetting;
import com.shufe.model.std.speciality2nd.SignUpSpecialitySetting;
import com.shufe.model.std.speciality2nd.SignUpStudent;
import com.shufe.model.std.speciality2nd.SignUpStudentRecord;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.std.speciality2nd.Speciality2ndSignUpService;

public class Speciality2ndSignUpServiceImpl extends BasicService implements
        Speciality2ndSignUpService {
    
    Speciality2ndSignUpDAO speciality2ndSignUpDAO;
    
    public void autoMatriculate(SignUpSetting setting, MatriculateParams params) throws IOException {
        /* 报名级差设置 */
        List GPASettingList = utilService.loadAll(SignUpGPASetting.class);
        Map GPAGapMap = new HashMap();
        for (Iterator GradeSettingiter = GPASettingList.iterator(); GradeSettingiter.hasNext();) {
            SignUpGPASetting GPASetting = (SignUpGPASetting) GradeSettingiter.next();
            GPAGapMap.put(GPASetting.getToRank(), GPASetting.getGPAGap());
        }
        // 录取前初始化/准备
        speciality2ndSignUpDAO.initMatriculate(setting);
        
        List toAdjustList = new ArrayList(); // 待调剂学生列表
        List adjustSignUpStds = new ArrayList();
        List matriculatedRecords = new ArrayList();// 录取学生列表
        
        /* 查找录取学生,按照录取绩点优先进行录取 */
        List signUpStds = getSignUpStudents(setting);
        while (!signUpStds.isEmpty()) {
            SignUpStudent signUpStudent = (SignUpStudent) Collections.max(signUpStds,
                    new BeanComparator("matriculateGPA"));
            Integer rank = signUpStudent.getRank();
            rank = rank == null ? new Integer(1) : new Integer(rank.intValue() + 1);
            signUpStudent.setRank(rank);
            boolean isMatriculated = false;
            SignUpStudentRecord record = signUpStudent.getRecord(rank.intValue());
            if (null != record && record.getSpecialitySetting().getMatriculateReminder() > 0) {
                matriculatedRecords.add(record);
                record.getSpecialitySetting().getMatriculatedSignUpStds().add(record);
                isMatriculated = true;
            }
            if (isMatriculated) {
                signUpStds.remove(signUpStudent);
            } else {
                if (rank.intValue() >= signUpStudent.getRecords().size()) {
                    if (Boolean.TRUE.equals(signUpStudent.getIsAdjustable())
                            && Boolean.TRUE.equals(params.getAdjustable())) {
                        toAdjustList.add(signUpStudent);
                    }
                    signUpStds.remove(signUpStudent);
                } else {
                    Float matriculateGPA = signUpStudent.getMatriculateGPA();
                    Float GPAGap = (Float) GPAGapMap.get(new Integer(rank.intValue() + 1));
                    matriculateGPA = new Float(matriculateGPA.floatValue() - GPAGap.floatValue());
                    signUpStudent.setMatriculateGPA(matriculateGPA);
                }
            }
        }
        
        if (Boolean.TRUE.equals(params.getAdjustable())) {
            if (!toAdjustList.isEmpty()) {
                for (Iterator iter = setting.getSpecialitySettings().iterator(); iter.hasNext();) {
                    SignUpSpecialitySetting specialitySetting = (SignUpSpecialitySetting) iter
                            .next();
                    int reminder = specialitySetting.getMatriculateReminder();
                    while (reminder > 0 && !toAdjustList.isEmpty()) {
                        SignUpStudent adjustSignUpStd = (SignUpStudent) toAdjustList.get(0);
                        adjustSignUpStd.setIsAdjustMatriculated(Boolean.TRUE);
                        adjustSignUpStd.setMatriculated(specialitySetting);
                        adjustSignUpStds.add(adjustSignUpStd);
                        toAdjustList.remove(adjustSignUpStd);
                        reminder--;
                    }
                    if (toAdjustList.isEmpty())
                        break;
                }
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("matriculate finish! ready to synchronize to db:[Matriculate:"
                    + matriculatedRecords.size() + ",adjust:" + adjustSignUpStds.size() + "]");
        }
        speciality2ndSignUpDAO.batchMatriculateSignUpStudentRecords(setting, matriculatedRecords);
        speciality2ndSignUpDAO.batchMatriculateSignUpStudent(adjustSignUpStds);
    }
    
    public List getSignUpStudents(SignUpSetting setting) {
        EntityQuery query = new EntityQuery(SignUpStudent.class, "signUpStd");
        query.add(new Condition("signUpStd.setting.id=" + setting.getId()));
        return (List) utilDao.search(query);
    }
    
    public void autoAssignClass(List specialitySettings) {
        for (Iterator iter = specialitySettings.iterator(); iter.hasNext();) {
            SignUpSpecialitySetting specialitySetting = (SignUpSpecialitySetting) iter.next();
            List adminClasses = getCorrespondingAdminClasses(specialitySetting);
            // 检查容量
            int capacity = 0;
            for (Iterator iterator = adminClasses.iterator(); iterator.hasNext();) {
                AdminClass adminClass = (AdminClass) iterator.next();
                capacity += adminClass.freeCapacity();
            }
            if (capacity < specialitySetting.getMatriculatedSignUpStds().size()) {
                continue;
            }
            // 随机分班
            Iterator signUpStdIter = specialitySetting.getMatriculatedSignUpStds().iterator();
            Collections.sort(adminClasses, new PropertyComparator("name"));
            for (Iterator iterator = adminClasses.iterator(); iterator.hasNext();) {
                AdminClass adminClass = (AdminClass) iterator.next();
                for (int i = 0; i < adminClass.freeCapacity(); i++) {
                    if (signUpStdIter.hasNext()) {
                        Student std = ((SignUpStudent) signUpStdIter.next()).getStd();
                        adminClass.getStudents().add(std);
                    } else {
                        break;
                    }
                }
                // 更新在校人数和学籍有效人数
                adminClass.setActualStdCount(new Integer(adminClass.getStudents().size()));
                adminClass.setStdCount(adminClass.getActualStdCount());
            }
            // 保存数据
            utilDao.saveOrUpdate(adminClasses);
        }
    }
    
    public void statStdCountOf(Collection specialitySettings) {
        if (CollectionUtils.isEmpty(specialitySettings))
            return;
        // 查询报名人数和录取人数
        List settings = new ArrayList(specialitySettings);
        String hql = "select id,"
                + "(select count(*) from SignUpStudentRecord r where r.specialitySetting.id=specialitySetting.id) as num,"
                + "(select count(*) from SignUpStudent s where s.matriculated.id=specialitySetting.id) as matriculated"
                + " from SignUpSpecialitySetting specialitySetting where specialitySetting.id in(:ids)"
                + " order by specialitySetting.id";
        EntityQuery query = new EntityQuery();
        query.setQueryStr(hql);
        Map params = new HashMap();
        params.put("ids", EntityUtils.extractIds(specialitySettings));
        query.setParams(params);
        List counts = (List) utilDao.search(query);
        
        Map mmm = new HashMap();
        for (Iterator iter = counts.iterator(); iter.hasNext();) {
            Object[] count = (Object[]) iter.next();
            mmm.put(count[0], count);
        }
        for (int i = 0; i < settings.size(); i++) {
            SignUpSpecialitySetting setting = (SignUpSpecialitySetting) settings.get(i);
            Object[] count = (Object[]) mmm.get(setting.getId());
            setting.setTotal(new Integer(((Number) count[1]).intValue()));
            setting.setMatriculated(new Integer(((Number) count[2]).intValue()));
        }
    }
    
    public List statSignUpAndMatriculate(SignUpSetting setting) {
        StringBuffer sb = new StringBuffer("select setting");
        
        // 各个志愿的报名和录取人数
        for (int i = 0; i < setting.getChoiceCount().intValue(); i++) {
            sb
                    .append(",(select count(*) from SignUpStudentRecord r where r.specialitySetting.id =setting.id and r.rank="
                            + (i + 1) + ")");
            sb
                    .append(",(select count(*) from SignUpStudentRecord r where r.specialitySetting.id =setting.id and r.status=true and r.rank="
                            + (i + 1) + ")");
        }
        // 总报名人数
        sb
                .append(",(select count(*) from SignUpStudentRecord r where r.specialitySetting.id =setting.id)");
        // 总录取人数
        sb.append(",(select count(*) from SignUpStudent r where r.matriculated.id =setting.id)");
        // 调剂人数
        sb
                .append(",(select count(*) from SignUpStudent r where r.matriculated.id =setting.id and isAdjustMatriculated=true)");
        
        sb.append(" from SignUpSpecialitySetting setting where setting.setting.id="
                + setting.getId());
        EntityQuery query = new EntityQuery(sb.toString());
        return (List) utilDao.search(query);
    }
    
    public List statMatriculate(SignUpSetting setting) {
        StringBuffer sb = new StringBuffer("select setting");
        sb.append(",(select count(*) from SignUpStudent r where r.matriculated.id =setting.id)");
        sb.append(" from SignUpSpecialitySetting setting where setting.setting.id="
                + setting.getId());
        EntityQuery query = new EntityQuery(sb.toString());
        return (List) utilDao.search(query);
    }
    
    public List getCorrespondingAdminClasses(SignUpSpecialitySetting specialitySetting) {
        EntityQuery query = new EntityQuery(AdminClass.class, "adminClass");
        query.add(new Condition("adminClass.enrollYear=:enrollYear", specialitySetting.getSetting()
                .getEnrollTurn()));
        query.add(new Condition("adminClass.speciality=:speciality", specialitySetting
                .getSpeciality()));
        query.add(new Condition("adminClass.aspect=:aspect", specialitySetting.getAspect()));
        return (List) utilDao.search(query);
    }
    
    public List getUnsaturatedSpecialitySettings(SignUpSetting setting) {
        List specialitySettings = new ArrayList(setting.getSpecialitySettings());
        statStdCountOf(specialitySettings);
        CollectionUtils.filter(specialitySettings, new BeanPredicate("isSaturated", EqualPredicate
                .getInstance(Boolean.FALSE)));
        return specialitySettings;
    }
    
    public List getSignUpSettings(TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(SignUpSetting.class, "signUpSetting");
        query.add(new Condition("signUpSetting.calendar=:calendar", calendar));
        return (List) utilDao.search(query);
    }
    
    public void cancelMatriculate(SignUpStudent signUpStd) {
        signUpStd.cancelMatriculate();
        List persistList = new ArrayList();
        AdminClass adminClass = signUpStd.getStd().getSecondMajorClass();
        if (null != adminClass) {
            Student std = signUpStd.getStd();
            adminClass.getStudents().remove(std);
            // 如果在校，减少班级的在校人数
            if (std.isInSchool()) {
                adminClass.setActualStdCount(new Integer(
                        adminClass.getActualStdCount().intValue() - 1));
            }
            // 如果有效，降低班级的学籍有效人数
            if (Boolean.TRUE.equals(std.getState())) {
                adminClass.setStdCount(new Integer(adminClass.getStdCount().intValue() - 1));
            }
            persistList.add(adminClass);
        }
        persistList.add(signUpStd);
        persistList.add(signUpStd.getStd());
        utilDao.saveOrUpdate(persistList);
    }
    
    public void setSpeciality2ndSignUpDAO(Speciality2ndSignUpDAO speciality2ndSignUpDAO) {
        this.speciality2ndSignUpDAO = speciality2ndSignUpDAO;
    }
    
}

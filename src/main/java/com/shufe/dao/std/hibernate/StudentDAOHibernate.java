//$Id: StudentDAOHibernate.java,v 1.47 2007/01/12 01:28:00 yd Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone            2005-09-04          Created
 * zq                   2007-09-18          下面FIXME处，不好修改
 ********************************************************************************/

package com.shufe.dao.std.hibernate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.AbstractQuery;
import com.ekingstar.commons.query.hibernate.HibernateQuerySupport;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.std.StudentDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.util.DataRealmLimit;

public class StudentDAOHibernate extends BasicHibernateDAO implements StudentDAO {
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.std.StudentDAO#genCriteria(org.hibernate.Session,
     *      com.shufe.model.std.Student)
     */
    public Criteria constructStudentCriteria(Student std, List sortList) {
        Criteria criteria = this.constructStudentCriteriaWithOutOrder(std,
                StudentDAO.DEFAULTEXCLUDEDATTRS);
        return criteria;
    }
    
    private Criteria constructStudentCriteriaWithOutOrder(Student std, String[] excludedAttrs) {
        Criteria criteria = this.getSession().createCriteria(Student.class);
        if (std != null) {
            List criterions = CriterionUtils.getEntityCriterions(std, excludedAttrs);
            for (Iterator iter = criterions.iterator(); iter.hasNext();) {
                Criterion criterion = (Criterion) iter.next();
                criteria.add(criterion);
            }
            for (int i = 0; i < excludedAttrs.length; i++) {
                if (!excludedAttrs[i].equals("type")) {
                    addForeignerOf(excludedAttrs[i], std, criteria);
                } else {
                    if (ValidEntityPredicate.INSTANCE.evaluate(std.getType())) {
                        StudentType stdType = (StudentType) utilDao.get(StudentType.class, std
                                .getType().getId());
                        List stdTypes = stdType.getDescendants();
                        stdTypes.add(stdType);
                        StringBuffer sb = new StringBuffer();
                        for (int k = 0; k < stdTypes.size(); k++) {
                            StudentType one = (StudentType) stdTypes.get(k);
                            sb.append(one.getId() + ",");
                        }
                        criteria.add(Restrictions.in("type.id", SeqStringUtil.transformToLong(sb
                                .toString())));
                    }
                }
            }
            if (null != std.getAdminClasses() && !std.getAdminClasses().isEmpty()) {
                AdminClass adminClass = (AdminClass) std.getAdminClasses().iterator().next();
                List adminClassCriterions = CriterionUtils.getEntityCriterions("adminClass.",
                        adminClass);
                if (!adminClassCriterions.isEmpty()) {
                    Criteria adminClassCriteria = criteria.createCriteria("adminClasses",
                            "adminClass");
                    for (Iterator iter = adminClassCriterions.iterator(); iter.hasNext();) {
                        // FIXME 无法修改
                        adminClassCriteria.add((Criterion) iter.next());
                    }
                }
            }
        }
        return criteria;
    }
    
    public Criteria constructStdCountCriteria(Student std) {
        
        Criteria criteria = this.getSession().createCriteria(Student.class).addOrder(
                Order.desc("enrollYear")).addOrder(Order.asc("code"));
        String[] excludedAttrs = new String[] { "department", "firstMajor", "firstAspect",
                "studentStatusInfo", "basicInfo" };
        List criterions = CriterionUtils.getEntityCriterions(std, excludedAttrs);
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion criterion = (Criterion) iter.next();
            criteria.add(criterion);
        }
        for (int i = 0; i < excludedAttrs.length; i++) {
            addForeignerOf(excludedAttrs[i], std, criteria);
        }
        if (null != std.getAdminClasses() && !std.getAdminClasses().isEmpty()) {
            AdminClass adminClass = (AdminClass) std.getAdminClasses().iterator().next();
            List adminClassCriterions = CriterionUtils.getEntityCriterions("adminClass.",
                    adminClass);
            if (!adminClassCriterions.isEmpty()) {
                Criteria adminClassCriteria = criteria.createCriteria("adminClasses", "adminClass");
                for (Iterator iter = adminClassCriterions.iterator(); iter.hasNext();) {
                    adminClassCriteria.add((Criterion) iter.next());
                }
            }
        }
        criteria.setProjection(Projections.count("id"));
        return criteria;
    }
    
    private boolean addForeignerOf(String foreigner, Object std, Criteria criteria) {
        try {
            boolean flag = false;
            List departCriterions = CriterionUtils.getEntityCriterions(foreigner + ".",
                    PropertyUtils.getProperty(std, foreigner));
            if (!departCriterions.isEmpty()) {
                Criteria foreignerCriteria = criteria.createCriteria(foreigner, foreigner);
                for (Iterator iter = departCriterions.iterator(); iter.hasNext();) {
                    foreignerCriteria.add((Criterion) iter.next());
                }
                flag = true;
            }
            return flag;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.std.StudentDAO#generateStudentClassCriteria(com.shufe.model.system.baseinfo.AdminClass)
     */
    public Criteria constructStudentClassCriteria(AdminClass adminClass) {
        Criteria criteria = getSession().createCriteria(AdminClass.class);
        List criterions = CriterionUtils.getEntityCriterions("", adminClass,
                new String[] { "stdType" });
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            criteria.add((Criterion) iter.next());
        }
        if (ValidEntityPredicate.INSTANCE.evaluate(adminClass.getStdType())) {
            StudentType stdType = (StudentType) utilDao.get(StudentType.class, adminClass
                    .getStdType().getId());
            List stdTypes = stdType.getDescendants();
            stdTypes.add(stdType);
            StringBuffer sb = new StringBuffer();
            for (int k = 0; k < stdTypes.size(); k++) {
                StudentType one = (StudentType) stdTypes.get(k);
                sb.append(one.getId() + ",");
            }
            criteria.add(Restrictions
                    .in("stdType.id", SeqStringUtil.transformToLong(sb.toString())));
        }
        return criteria;
    }
    
    /*
     * （非 Javadoc）
     * 
     * @see com.shufe.dao.std.StudentDAO#batchCancelStudentClass(java.lang.String[])
     */
    public void batchCancelStudentClass(Object[] studentIdArray, Object[] ids) {
        for (int i = 0; i < ids.length; i++) {
            AdminClass adminClass = (AdminClass) utilDao.load(AdminClass.class, (Long) ids[i]);
            Set studentSet = adminClass.getStudents();
            for (int j = 0; j < studentIdArray.length; j++) {
                Student student = (Student) utilDao.load(Student.class, (Long) studentIdArray[j]);
                if (studentSet.contains(student)) {
                    studentSet.remove(student);
                    student.getAdminClasses().remove(adminClass);
                    if (student.isActive()) {
                        adminClass.setStdCount(adminClass.getStdCount() == null ? new Integer(0)
                                : new Integer(adminClass.getStdCount().intValue() - 1));
                    } else {
                        
                    }
                    if (student.isInSchool()) {
                        adminClass
                                .setActualStdCount(adminClass.getActualStdCount() == null ? new Integer(
                                        0)
                                        : new Integer(adminClass.getActualStdCount().intValue() - 1));
                    }
                } else {
                    
                }
                
                // getSessionFactory().evict(Student.class, stdNo[i]);
            }
            getHibernateTemplate().flush();
            getHibernateTemplate().clear();
        }
    }
    
    /*
     * （非 Javadoc）
     * 
     * @see com.shufe.dao.std.StudentDAO#loadAdminClassById(java.lang.Long)
     */
    public AdminClass loadAdminClassById(Long id) {
        AdminClass adminClass = (AdminClass) getSession().createQuery(
                "from AdminClass adminClass where adminClass.id = :id").setLong("id",
                id.longValue()).uniqueResult();
        return adminClass;
    }
    
    /*
     * （非 Javadoc）
     * 
     * @see com.shufe.dao.std.StudentDAO#batchAddStudentClass(java.lang.String[], java.lang.Long[])
     */
    public void batchAddStudentClass(Object[] studentIdArray, Object[] ids) {
        for (int i = 0; i < ids.length; i++) {
            AdminClass adminClass = (AdminClass) utilDao.load(AdminClass.class, (Long) ids[i]);
            Set studentSet = adminClass.getStudents();
            for (int j = 0; j < studentIdArray.length; j++) {
                Student student = (Student) utilDao.load(Student.class, (Long) studentIdArray[j]);
                if (studentSet.contains(student)) {
                    
                } else {
                    studentSet.add(student);
                    student.getAdminClasses().add(adminClass);
                    if (student.isActive()) {
                        adminClass.setStdCount(adminClass.getStdCount() == null ? new Integer(1)
                                : new Integer(adminClass.getStdCount().intValue() + 1));
                    } else {
                        
                    }
                    if (student.isInSchool()) {
                        adminClass
                                .setActualStdCount(adminClass.getActualStdCount() == null ? new Integer(
                                        1)
                                        : new Integer(adminClass.getActualStdCount().intValue() + 1));
                    }
                }
            }
            getHibernateTemplate().flush();
            getHibernateTemplate().clear();
        }
        
    }
    
    /*
     * （非 Javadoc）
     * 
     * @see com.shufe.dao.std.StudentDAO#batchResetStudentClass(java.lang.String[])
     */
    public void batchResetStudentClass(Long[] studentIdArray) {
        for (int i = 0; i < studentIdArray.length; i++) {
            Student student = (Student) utilDao.load(Student.class, studentIdArray[i]);
            Set adminClasses = student.getAdminClasses();
            
            Iterator iterator = adminClasses.iterator();
            while (iterator.hasNext()) {
                AdminClass adminClass = (AdminClass) iterator.next();
                Set studentSet = adminClass.getStudents();
                if (studentSet.contains(student)) {
                    studentSet.remove(student);
                    if (student.isInSchool()) {
                        adminClass
                                .setActualStdCount(adminClass.getActualStdCount() == null ? new Integer(
                                        0)
                                        : new Integer(adminClass.getActualStdCount().intValue() - 1));
                    }
                    if (student.isActive()) {
                        adminClass.setStdCount(adminClass.getStdCount() == null ? new Integer(0)
                                : new Integer(adminClass.getStdCount().intValue() - 1));
                    }
                } else {
                    
                }
                utilDao.saveOrUpdate(adminClass);
            }
            student.getAdminClasses().clear();
            
            getHibernateTemplate().flush();
            getHibernateTemplate().clear();
        }
    }
    
    /*
     * （非 Javadoc）
     * 
     * @see com.shufe.dao.std.StudentDAO#batchResetAdminClass(java.lang.Long[])
     */
    public void batchResetAdminClass(Long[] adminClassIds) {
        
        for (int i = 0; i < adminClassIds.length; i++) {
            AdminClass adminClass = (AdminClass) utilDao.load(AdminClass.class, adminClassIds[i]);
            Set studentSet = adminClass.getStudents();
            Iterator iterator = studentSet.iterator();
            while (iterator.hasNext()) {
                Student student = (Student) iterator.next();
                student.getAdminClasses().remove(adminClass);
                
                // getSessionFactory().evict(Student.class, stdNo[i]);
            }
            adminClass.getStudents().clear();
            adminClass.setActualStdCount(new Integer(0));
            adminClass.setStdCount(new Integer(0));
            
            getHibernateTemplate().flush();
            getHibernateTemplate().clear();
        }
    }
    
    public boolean checkExist(Long studentId) {
        return null != utilDao.get(Student.class, studentId);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.std.StudentDAO#getStudentById(java.lang.String)
     */
    public Map getBasicInfoName(String stdCode) {
        Criteria criteria = getSession().createCriteria(Student.class).addOrder(
                Order.desc("enrollYear")).addOrder(Order.asc("code"));
        criteria.add(Restrictions.eq("code", stdCode));
        List stdList = criteria.list();
        Student temp = null;
        if (stdList.isEmpty()) {
            return null;
        }
        temp = (Student) stdList.get(0);
        Map stdMap = new HashMap();
        stdMap.put("id", temp.getId());
        stdMap.put("name", temp.getName());
        stdMap.put("type.id", String.valueOf(temp.getType().getId()));
        stdMap.put("type.name", temp.getType().getName());
        stdMap.put("department.id", temp.getDepartment().getId());
        stdMap.put("department.name", temp.getDepartment().getName());
        stdMap.put("enrollYear", temp.getEnrollYear());
        stdMap.put("state.id", temp.getState().getId());
        stdMap.put("isActive", new Boolean(temp.isActive()));
        stdMap.put("isInSchool", new Boolean(temp.isInSchool()));
        if (null != temp.getFirstMajor()) {
            stdMap.put("firstMajor.name", temp.getFirstMajor().getName());
        } else {
            stdMap.put("firstMajor.name", "");
        }
        if (temp.getAdminClasses().isEmpty()) {
            stdMap.put("adminClasses.adminClass.name", "");
        } else {
            AdminClass adminClass = (AdminClass) temp.getAdminClasses().iterator().next();
            stdMap.put("adminClasses.adminClass.name", adminClass.getName());
        }
        stdMap.put("state", temp.getState());
        if (null == temp.getStudentStatusInfo()) {
            stdMap.put("studentStatusInfo", Boolean.TRUE);
        } else {
            stdMap.put("isInSchoolStatus", new Boolean(temp.isInSchool()));
        }
        if ((null == temp.getAbroadStudentInfo())
                || (null == temp.getAbroadStudentInfo().getHSKDegree())) {
            stdMap.put("abroadStudentInfo.HSKDegree.name", "");
        } else {
            stdMap.put("abroadStudentInfo.HSKDegree.name", temp.getAbroadStudentInfo()
                    .getHSKDegree().getName());
        }
        
        stdMap.put("studentStatusInfo.feeOrigin.name", (null == temp.getStudentStatusInfo()
                .getFeeOrigin()) ? "" : temp.getStudentStatusInfo().getFeeOrigin().getName());
        return stdMap;
        
    }
    
    public void batchSetStdToTutor(String stdIds, Long tutorId) {
        String hql = "update Student set teacher.id = :tutorId " + "where id in(:stdIds)";
        Query query = getSession().createQuery(hql);
        query.setLong("tutorId", tutorId.longValue());
        query.setParameterList("stdIds", SeqStringUtil.transformToLong(stdIds));
        query.executeUpdate();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.std.StudentDAO#getStdIdByStd(com.shufe.model.std.Student,
     *      java.lang.String, java.lang.String)
     */
    public List getStdIdByStd(Student student, String stdTypeSeq, String departmentSeq) {
        Criteria criteria = this.getSession().createCriteria(Student.class).addOrder(
                Order.desc("enrollYear")).addOrder(Order.asc("code"));
        Long[] departmentIds = SeqStringUtil.transformToLong(departmentSeq);
        if (null != departmentIds) {
            criteria.add(Restrictions.in("department.id", departmentIds));
        }
        Long[] stdTypeIds = SeqStringUtil.transformToLong(stdTypeSeq);
        if (null != stdTypeIds) {
            criteria.add(Restrictions.in("type.id", stdTypeIds));
        }
        List criterions = CriterionUtils.getEntityCriterions(student);
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion criterion = (Criterion) iter.next();
            criteria.add(criterion);
        }
        criteria.setProjection(Projections.property("id"));
        return criteria.list();
    }
    
    public Collection studentInfoStat(Student student, DataRealmLimit limit, String[] groupArray,
            Boolean isExactly) {
        Criteria criteria = null;
        if (Boolean.TRUE.equals(isExactly)) {
            criteria = getStudentInfoStatCriteria(student, groupArray, new String[] {
                    "abroadStudentInfo", "studentStatusInfo", "basicInfo", "firstMajor",
                    "firstAspect", "secondMajor", "secondAspect", "graduateAuditStatus",
                    "secondGraduateAuditStatus" });
        } else {
            criteria = getStudentInfoStatCriteria(student, groupArray, null);
        }
        if (null != limit) {
            if (StringUtils.isNotEmpty(limit.getDataRealm().getStudentTypeIdSeq())) {
                criteria.add(Restrictions.in("type.id", SeqStringUtil.transformToLong(limit
                        .getDataRealm().getStudentTypeIdSeq())));
            }
            if (StringUtils.isNotEmpty(limit.getDataRealm().getDepartmentIdSeq())) {
                criteria.add(Restrictions.in("department.id", SeqStringUtil.transformToLong(limit
                        .getDataRealm().getDepartmentIdSeq())));
            }
        }
        if (null != limit && null != limit.getPageLimit()) {
            return dynaSearch(criteria, limit.getPageLimit());
        } else {
            return criteria.list();
        }
    }
    
    private Criteria getStudentInfoStatCriteria(Student student, String[] groupArray,
            String[] excludedAttrs) {
        Criteria criteria = this.constructStudentCriteriaWithOutOrder(student,
                StudentDAO.DEFAULTEXCLUDEDATTRS);
        if (!ArrayUtils.isEmpty(excludedAttrs)) {
            for (int i = 0; i < excludedAttrs.length; i++) {
                try {
                    if (!ValidEntityPredicate.INSTANCE.evaluate(PropertyUtils.getProperty(student,
                            excludedAttrs[i]))) {
                        criteria.add(Restrictions.isNull(excludedAttrs[i]));
                    }
                } catch (Exception e) {
                    logger.error("error with property name " + excludedAttrs[i]);
                }
            }
        }
        
        ProjectionList projectionList = Projections.projectionList();
        
        if (null != groupArray) {
            for (int i = 0; i < groupArray.length; i++) {
                String[] temp = StringUtils.split(groupArray[i], ".");
                for (int j = 0; j < temp.length - 1; j++) {
                    String tempString = "";
                    for (int k = 0; k <= j; k++) {
                        tempString += temp[k];
                        if (k != j) {
                            tempString += ".";
                        }
                    }
                    criteria.createCriteria(tempString, temp[j]);
                }
                projectionList
                        .add(Projections.groupProperty(temp.length > 1 ? (temp[temp.length - 2]
                                + "." + temp[temp.length - 1]) : (temp[temp.length - 1]))/* ,groupArray[i] */);
                /*
                 * int matchCount = StringUtils.countMatches(groupArray[i], "."); if(matchCount==1){
                 * String[] temp = StringUtils.split(groupArray[i],".");
                 * criteria.createCriteria(temp[0],temp[0]);
                 * projectionList.add(Projections.groupProperty(groupArray[i]),groupArray[i]); }else
                 * if(matchCount==0){
                 * projectionList.add(Projections.groupProperty(groupArray[i]),groupArray[i]); }
                 */
            }
        }
        projectionList.add(Projections.rowCount(), "count");
        criteria.setProjection(projectionList);
        criteria.addOrder(Order.asc("count"));
        return criteria;
    }
    
    public void updateStudent(List QueryList) {
        for (Iterator iter = QueryList.iterator(); iter.hasNext();) {
            AbstractQuery element = (AbstractQuery) iter.next();
            Query query = getSession().createQuery(element.getQueryStr());
            HibernateQuerySupport.setParameter(query, element.getParams());
            query.executeUpdate();
        }
    }
    
}
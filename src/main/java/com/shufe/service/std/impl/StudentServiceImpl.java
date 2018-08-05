package com.shufe.service.std.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.ConditionUtils;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.eams.system.basecode.industry.AlterReason;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.dao.OldPagination;
import com.shufe.dao.std.StudentDAO;
import com.shufe.dao.system.baseinfo.AdminClassDAO;
import com.shufe.model.std.AbroadStudentInfo;
import com.shufe.model.std.BasicInfo;
import com.shufe.model.std.PlanAuditResult;
import com.shufe.model.std.Student;
import com.shufe.model.std.StudentStatusInfo;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.BasicService;
import com.shufe.service.std.StudentService;
import com.shufe.util.DataRealmLimit;

public class StudentServiceImpl extends BasicService implements StudentService {
    
    private StudentDAO studentDAO;
    
    private AdminClassDAO adminClassDAO;
    
    public Student getStudent(Long studentId) {
        return (Student) utilDao.get(Student.class, studentId);
    }
    
    public Student getStudent(String code) {
        List list = utilService.load(Student.class, "code", code);
        if (list.isEmpty()) {
            return null;
        } else {
            return (Student) list.get(0);
        }
    }
    
    public Student saveStudent(Student student, StudentStatusInfo statusInfo, BasicInfo basicInfo,
            AbroadStudentInfo abroadStudentInfo) {
        
        if (student != null) {
            /* 清除基本信息无效(外键)属性 */
            EntityUtils.evictEmptyProperty(basicInfo);
            /* 清除学籍详细信息无效(外键)属性 */
            EntityUtils.evictEmptyProperty(statusInfo);
            /* 清除留学生无效(外键)属性 */
            EntityUtils.evictEmptyProperty(abroadStudentInfo);
            /* 清除学生无效(外键)属性 */
            EntityUtils.evictEmptyProperty(student);
            student.setBasicInfo(basicInfo);
            student.setStudentStatusInfo(statusInfo);
            if (EntityUtils.isEmpty(abroadStudentInfo, true)) {
                student.setAbroadStudentInfo(null);
            } else {
                student.setAbroadStudentInfo(abroadStudentInfo);
            }
            student.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
            student.setModifyAt(new java.sql.Date(System.currentTimeMillis()));
            utilDao.saveOrUpdate(student);
        }
        return student;
    }
    
    public Student updateStudent(Student student, StudentStatusInfo statusInfo,
            BasicInfo basicInfo, AbroadStudentInfo abroadStudentInfo) {
        EntityUtils.evictEmptyProperty(student);
        Student saved = getStudent(student.getId());
        List updateList = new ArrayList();
        updateList.add(saved);
        EntityUtils.merge(saved, student);
        /* 修改基本信息 */
        EntityUtils.evictEmptyProperty(basicInfo);
        EntityUtils.merge(saved.getBasicInfo(), basicInfo);
        
        /* 保存学籍详细信息 */
        EntityUtils.evictEmptyProperty(statusInfo);
        EntityUtils.merge(saved.getStudentStatusInfo(), statusInfo);
        /* 保存留学生信息 */
        if (saved.getAbroadStudentInfo() == null) {
            EntityUtils.evictEmptyProperty(abroadStudentInfo);
            if (!EntityUtils.isEmpty(abroadStudentInfo, true)) {
                updateList.add(0, abroadStudentInfo);
                saved.setAbroadStudentInfo(abroadStudentInfo);
            }
        } else {
            EntityUtils.evictEmptyProperty(abroadStudentInfo);
            if (EntityUtils.isEmpty(abroadStudentInfo, true)) {
                saved.setAbroadStudentInfo(null);
            } else {
                EntityUtils.merge(saved.getAbroadStudentInfo(), abroadStudentInfo);
            }
        }
        /* 保存学生信息 */
        saved.setModifyAt(new Date(System.currentTimeMillis()));
        utilDao.saveOrUpdate(updateList);
        return saved;
    }
    
    public List findStudent(Student std) {
        return studentDAO.constructStudentCriteria(std, getDefaultSortList()).list();
    }
    
    public Pagination searchStudent(Student std, int pageNo, int pageSize) {
        return studentDAO.dynaSearch(
                studentDAO.constructStudentCriteria(std, getDefaultSortList()), pageNo,
                pageSize);
    }
    
    private Pagination searchStudent(Student std, int pageNo, int pageSize,
            String studentGraduateAuditStatus) {
        Criteria criteria = constructStudentCriteriaWithGraduateAuditStatus(std,
                studentGraduateAuditStatus);
        return studentDAO.dynaSearch(criteria, pageNo, pageSize);
    }
    
    public Pagination searchSecondSpecialityStudent(Student std, int pageNo, int pageSize,
            String studentGraduateAuditStatus) {
        String[] studentGraduateAuditStatusArray = StringUtils.split(studentGraduateAuditStatus,
                ",");
        Criteria criteria = constructSecondSpecialityStudentCriteriaWithGraduateAuditStatus(std,
                studentGraduateAuditStatusArray);
        return studentDAO.dynaSearch(criteria, pageNo, pageSize);
    }
    
    public Pagination searchSecondSpecialityStudent(Student std, int pageNo, int pageSize,
            String[] studentGraduateAuditStatusArray) {
        Criteria criteria = constructStudentCriteriaWithGraduateAuditStatusArray(std,
                studentGraduateAuditStatusArray);
        return studentDAO.dynaSearch(criteria, pageNo, pageSize);
    }
    
    private Criteria constructStudentCriteriaWithGraduateAuditStatus(Student std,
            String studentGraduateAuditStatus) {
        String[] studentGraduateAuditStatusArray = StringUtils.split(studentGraduateAuditStatus,
                ",");
        Criteria criteria = studentDAO.constructStudentCriteria(std, getDefaultSortList());
        addAuditStatus(studentGraduateAuditStatusArray, criteria, "graduateAuditStatus");
        return criteria;
    }
    
    private Criteria constructSecondSpecialityStudentCriteriaWithGraduateAuditStatus(Student std,
            String[] studentGraduateAuditStatusArray) {
        
        Criteria criteria = studentDAO.constructStudentCriteria(std, getDefaultSortList());
        addAuditStatus(studentGraduateAuditStatusArray, criteria, "secondGraduateAuditStatus");
        return criteria;
    }
    
    private Criteria constructStudentCriteriaWithGraduateAuditStatusArray(Student std,
            String[] studentGraduateAuditStatus) {
        if (studentGraduateAuditStatus.length < 1)
            return studentDAO.constructStudentCriteria(std, getDefaultSortList());
        String[] studentGraduateAuditStatusArray = StringUtils.split(studentGraduateAuditStatus[0],
                ",");
        Criteria criteria = studentDAO.constructStudentCriteria(std, getDefaultSortList());
        addAuditStatus(studentGraduateAuditStatusArray, criteria, "graduateAuditStatus");
        if (studentGraduateAuditStatus.length < 2)
            return criteria;
        studentGraduateAuditStatusArray = StringUtils.split(studentGraduateAuditStatus[1], ",");
        addAuditStatus(studentGraduateAuditStatusArray, criteria, "secondGraduateAuditStatus");
        return criteria;
    }
    
    /**
     * 由状态数组statusArray在criteria加入statusName的状态判断，1 <code>true</code>,0 <code>false</code>,null
     * <code>null</code>
     * 
     * @param statusArray
     * @param criteria
     */
    private void addAuditStatus(String[] statusArray, Criteria criteria, String statusName) {
        if (!ArrayUtils.isEmpty(statusArray)) {
            List criterionList = new ArrayList();
            String[] str = new String[] { "1", "0" };
            for (int i = 0; i < statusArray.length; i++) {
                if (ArrayUtils.contains(str, statusArray[i])) {
                    Criterion criterion = Restrictions.eq(statusName, ConvertUtils.convert(
                            statusArray[i], Boolean.class));
                    criterionList.add(criterion);
                } else if (statusArray[i].equals("null")) {
                    Criterion criterion = Restrictions.isNull(statusName);
                    criterionList.add(criterion);
                }
            }
            if (!criterionList.isEmpty()) {
                if (criterionList.size() == 1) {
                    criteria.add((Criterion) criterionList.get(0));
                } else {
                    Criterion criterion = Restrictions.or((Criterion) criterionList.get(0),
                            (Criterion) criterionList.get(1));
                    int criterionListSize = criterionList.size();
                    for (int i = 2; i < criterionListSize; i++) {
                        criterion = Restrictions.or(criterion, (Criterion) criterionList.get(i));
                    }
                    criteria.add(criterion);
                }
            }
        }
    }
    
    public Pagination searchStudent(Student student, int pageNo, int pageSize,
            String studentTypeIds, String departmentIds) {
        this.filterAuthorityByDepartment(departmentIds);
        this.filterAuthorityByStudentType(studentTypeIds);
        return this.searchStudent(student, pageNo, pageSize);
    }
    
    public Pagination searchStudent(Student student, int pageNo, int pageSize, Long teacherId) {
        EntityQuery query = new EntityQuery(Student.class, "std");
        query.add(new Condition("std.teacher.id=:teacherId", teacherId));
        query.setLimit(new PageLimit(pageNo, pageSize));
        return OldPagination.buildOldPagination((SinglePage) utilService.search(query));
    }
    
    public Pagination searchStudent(Student student, int pageNo, int pageSize,
            String studentTypeIds, String departmentIds, String studentGraduateAuditStatus) {
        this.filterAuthorityByDepartment(departmentIds);
        this.filterAuthorityByStudentType(studentTypeIds);
        return this.searchStudent(student, pageNo, pageSize, studentGraduateAuditStatus);
    }
    
    public Pagination searchSecondSpecialityStudent(Student student, int pageNo, int pageSize,
            String studentTypeIds, String departmentIds,
            String[] secondSpecialityStudentGraduateAuditStatus) {
        this.filterAuthorityByDepartment(departmentIds);
        this.filterAuthorityByStudentType(studentTypeIds);
        studentDAO.enbleFilter("filterSecondMajorStudent", null, null);
        return this.searchSecondSpecialityStudent(student, pageNo, pageSize,
                secondSpecialityStudentGraduateAuditStatus);
    }
    
    public Pagination searchSecondSpecialityStudent(Student student, int pageNo, int pageSize,
            String studentTypeIds, String departmentIds) {
        studentDAO.enbleFilter("filterSecondMajorStudent", null, null);
        return this.searchStudent(student, pageNo, pageSize, studentTypeIds, departmentIds);
    }
    
    public List searchStudent(Long[] studentIdArray) {
        return utilService.load(Student.class, "id", studentIdArray);
    }
    
    public List getStudentsByDepartment(String departIds) {
        this.filterAuthorityByDepartment(departIds);
        return utilService.loadAll(Student.class);
    }
    
    public void batchUpdateGraduateAuditStatus(Long[] studentIdArray, Boolean status) {
        Map valuesMap = new HashMap(1);
        valuesMap.put("graduateAuditStatus", status);
        utilService.update(PlanAuditResult.class, "std.id", studentIdArray, valuesMap);
    }
    
    public void batchUpdateSecondGraduateAuditStatus(Long[] studentIdArray, Boolean status) {
        Map valuesMap = new HashMap(1);
        valuesMap.put("secondGraduateAuditStatus", status);
        utilService.update(PlanAuditResult.class, "std.id",  studentIdArray, valuesMap);
    }
    
    public void batchUpdateGraduateAuditStatus(Collection stdIds, Boolean status) {
        Map valuesMap = new HashMap(1);
        valuesMap.put("graduateAuditStatus", status);
        utilService.update(PlanAuditResult.class, "std.id",  stdIds.toArray(), valuesMap);
    }
    
    public void batchUpdateSecondGraduateAuditStatus(Collection stdId, Boolean status) {
        Map valuesMap = new HashMap(1);
        valuesMap.put("secondGraduateAuditStatus", status);
        utilService.update(PlanAuditResult.class, "std.id",  stdId.toArray(), valuesMap);
    }
    
    public void batchUpdateGraduateAuditStatus(Collection stdId, MajorType majorType, Boolean status) {
        Map valuesMap = new HashMap(1);
        if (majorType.getId().equals(MajorType.FIRST))
            valuesMap.put("graduateAuditStatus", status);
        else if (majorType.getId().equals(MajorType.SECOND))
            valuesMap.put("secondGraduateAuditStatus", status);
        utilService.update(PlanAuditResult.class, "std.id",  stdId.toArray(), valuesMap);
    }
    
    public Pagination searchStudentClass(AdminClass adminClass, int pageNo, int pageSize,
            String studentTypeIds, String departmentIds) {
        this.filterAuthorityByDepartment(departmentIds);
        this.filterAuthorityByStudentType(studentTypeIds);
        return studentDAO.dynaSearch(studentDAO.constructStudentClassCriteria(adminClass),
                pageNo, pageSize);
    }
    
    public List findStudentClass(AdminClass adminClass, String studentTypeIds, String departmentIds) {
        this.filterAuthorityByDepartment(departmentIds);
        this.filterAuthorityByStudentType(studentTypeIds);
        return studentDAO.constructStudentClassCriteria(adminClass).list();
    }
    
    public void batchUpdateStudentClass(Object[] studentIdArray, Object[] adminClassIdArray) {
        studentDAO.batchAddStudentClass(studentIdArray, adminClassIdArray);
    }
    
    public void setAdminClassDAO(AdminClassDAO adminClassDAO) {
        this.adminClassDAO = adminClassDAO;
    }
    
    public void batchCancelStudentClass(Object[] studentIdArray, Object[] adminClassIdArray) {
        studentDAO.batchCancelStudentClass(studentIdArray, adminClassIdArray);
    }
    
    public void batchUpdateStudentInfoStatus(Long[] studentIdArray, Long infoStatus) {
        if (null == infoStatus)
            return;
        List stdList = utilService.load(Student.class, "id", studentIdArray);
        List updateList = new ArrayList();
        Set adminClassSet = new HashSet();
        for (Iterator iter = stdList.iterator(); iter.hasNext();) {
            Student std = (Student) iter.next();
            std.setState(new StudentState(infoStatus));
            updateList.add(std);
            if (CollectionUtils.isNotEmpty(std.getAdminClasses())) {
                adminClassSet.addAll(std.getAdminClasses());
            }
        }
        utilDao.saveOrUpdate(updateList);
        for (Iterator iter = adminClassSet.iterator(); iter.hasNext();) {
            AdminClass adminClass = (AdminClass) iter.next();
            adminClassDAO.updateActualStdCount(adminClass.getId());
            adminClassDAO.updateStdCount(adminClass.getId());
        }
    }
    
    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
    
    /**
     * 部门数据级权限
     * 
     * @param departmentIds
     */
    private void filterAuthorityByDepartment(String departmentIds) {
        if (departmentIds != null) {
            Map parameterMap = new HashMap(1);
            parameterMap.put("departmentIds", SeqStringUtil.transformToLong(departmentIds));
            studentDAO.enbleFilter("filterAuthorityByDepartment", parameterMap);
        }
    }
    
    /**
     * 学生类别数据级权限
     * 
     * @param departmentIds
     */
    private void filterAuthorityByStudentType(String studentTypeIds) {
        if (studentTypeIds != null) {
            Map parameterMap = new HashMap(1);
            parameterMap.put("studentTypeIds", SeqStringUtil.transformToLong(studentTypeIds));
            studentDAO.enbleFilter("filterAuthorityByStudentType", parameterMap);
        }
    }
    
    public void autoSplitClass(Long[] studentIdArray, Long[] classIdArray) {
        int stdNoLength = studentIdArray.length;
        int classIdLength = classIdArray.length;
        int[] splitNum = new int[classIdLength];
        for (int i = 0, j = 0; i < stdNoLength; i++, j++) {
            j = j % classIdLength;
            splitNum[j]++;
        }
        Long[] splitStdId;
        for (int i = 0, j = 0; i < classIdLength; i++) {
            splitStdId = (Long[]) ArrayUtils.subarray(studentIdArray, j, j + splitNum[i]);
            j += splitNum[i];
            /* 修改学生班级 */
            studentDAO.batchAddStudentClass(splitStdId, new Long[] { classIdArray[i] });
        }
    }
    
    public AdminClass loadAdminClassById(Long id) {
        /* 查询学生班级信息并返回结果 */
        return studentDAO.loadAdminClassById(id);
    }
    
    public List searchStudent(Student student, String studentTypeIds, String departmentIds) {
        this.filterAuthorityByDepartment(departmentIds);
        this.filterAuthorityByStudentType(studentTypeIds);
        return studentDAO.constructStudentCriteria(student, getDefaultSortList()).list();
    }
    
    public List searchStudentId(Student student) {
        return studentDAO.constructStudentCriteria(student, null).addOrder(Order.asc("id"))
                .setProjection(Projections.property("id")).list();
    }
    
    public List searchStudentId(Student student, String studentTypeIds, String departmentIds) {
        this.filterAuthorityByDepartment(departmentIds);
        this.filterAuthorityByStudentType(studentTypeIds);
        return studentDAO.constructStudentCriteria(student, null).addOrder(Order.asc("id"))
                .setProjection(Projections.property("id")).list();
    }
    
    public int countStudent(Student student, String studentTypeIds, String departmentIds) {
        this.filterAuthorityByDepartment(departmentIds);
        this.filterAuthorityByStudentType(studentTypeIds);
        Number stdCount = (Number) studentDAO.constructStdCountCriteria(student).list().get(0);
        return stdCount.intValue();
    }
    
    public List searchStudent(Student student, String studentTypeIds, String departmentIds,
            String studentGraduateAuditStatus) {
        this.filterAuthorityByDepartment(departmentIds);
        this.filterAuthorityByStudentType(studentTypeIds);
        Criteria criteria = constructStudentCriteriaWithGraduateAuditStatus(student,
                studentGraduateAuditStatus);
        return criteria.list();
    }
    
    public List searchSecondSpecialityStudent(Student student, String studentTypeIds,
            String departmentIds, String[] studentGraduateAuditStatus) {
        this.filterAuthorityByDepartment(departmentIds);
        this.filterAuthorityByStudentType(studentTypeIds);
        studentDAO.enbleFilter("filterSecondMajorStudent", null, null);
        Criteria criteria = constructStudentCriteriaWithGraduateAuditStatusArray(student,
                studentGraduateAuditStatus);
        return criteria.list();
    }
    
    public void batchResetStudentClass(Long[] studentIdArray) {
        studentDAO.batchResetStudentClass(studentIdArray);
        
    }
    
    public boolean checkStdNoIfExists(String code) {
        return utilService.exist(Student.class, "code", code);
    }
    
    public void batchResetAdminClass(Long[] adminClassIds) {
        studentDAO.batchResetAdminClass(adminClassIds);
    }
    
    public void batchCountActualStdCount(Long[] adminClassIds) {
        String hql = "select new map(size(adminClass.students) as studentSetSize, adminClass.id as adminClassId) from AdminClass adminClass where adminClass.id in (:ids) order by adminClass.code";
        Map parameterMap = new HashMap();
        parameterMap.put("ids", adminClassIds);
        List list = utilDao.searchHQLQuery(hql, parameterMap);
        parameterMap.clear();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Map element = (Map) iter.next();
            utilService.update(AdminClass.class, "id",
                    new Object[] { element.get("adminClassId") },
                    new String[] { "actualStdCount" },
                    new Object[] { element.get("studentSetSize") });
        }
    }
    
    public void batchIncreaseDecreaseActualStdCount(Long[] adminClassIds, Integer addend) {
        String hql = "update AdminClass set actualStdCount=actualStdCount+(:addend) where id in (:adminClassIds)";
        Map parameterMap = new HashMap();
        parameterMap.put("adminClassIds", adminClassIds);
        parameterMap.put("addend", addend);
        utilDao.executeUpdateHql(hql, parameterMap);
        parameterMap.clear();
    }
    
    public List searchStdNos(Student student) {
        return studentDAO.constructStudentCriteria(student, null).addOrder(Order.asc("code"))
                .setProjection(Projections.property("code")).list();
    }
    
    public List getAlterationReasonList(Long alterationTypeId) {
        List alterationReasonList = utilService.load(AlterReason.class, "alterMode.id",
                alterationTypeId);
        return alterationReasonList;
    }
    
    public void batchSetStdToTutor(String stdIds, Long tutorId) {
        studentDAO.batchSetStdToTutor(stdIds, tutorId);
    }
    
    public void batchUnSetStdToTutor(String stdIds) {
        List stdList = searchStudent(SeqStringUtil.transformToLong(stdIds));
        for (Iterator iter = stdList.iterator(); iter.hasNext();) {
            Student std = (Student) iter.next();
            std.setTeacher(null);
        }
        utilDao.saveOrUpdate(stdList);
    }
    
    public List getStdIdsByStd(Student student, String studentTypeIds, String departmentIds) {
        if (null != student) {
            student.setState(new StudentState(StudentState.ONCAMPUS));
        }
        return studentDAO.getStdIdByStd(student, studentTypeIds, departmentIds);
    }
    
    public Collection studentInfoStat(Student student, DataRealmLimit limit, String[] groupArray,
            Boolean isExactly) {
        return studentDAO.studentInfoStat(student, limit, groupArray, isExactly);
    }
    
    public Student getStd(String code) {
        return getStudent(code);
    }
    
    public void updateStudentAdminClass(Long stdId, Long[] adminClassIdArray,
            Boolean is2ndMajor) {
        List orig = new ArrayList();
        if (!ArrayUtils.isEmpty(adminClassIdArray)) {
            CollectionUtils.addAll(orig, adminClassIdArray);
        }
        List dest = EntityUtils.extractIds((((Student) utilService.get(Student.class, stdId))
                .getAdminClassSet(is2ndMajor)));
        List addClassList = (List) CollectionUtils.subtract(orig, dest);
        List subClassList = (List) CollectionUtils.subtract(dest, orig);
        this.batchCancelStudentClass(new Long[] { stdId }, subClassList.toArray());
        this.batchUpdateStudentClass(new Long[] { stdId }, addClassList.toArray());
    }
    
    public void updateStudentAdminClass(Long stdId, Collection adminClasses, Boolean is2ndMajor) {
        List orig = EntityUtils.extractIds(adminClasses);
        List dest = EntityUtils.extractIds(((Student) utilService.get(Student.class, stdId))
                .getAdminClassSet(is2ndMajor));
        List addClassList = (List) CollectionUtils.subtract(orig, dest);
        List subClassList = (List) CollectionUtils.subtract(dest, orig);
        this.batchCancelStudentClass(new Long[] { stdId }, subClassList.toArray());
        this.batchUpdateStudentClass(new Long[] { stdId }, addClassList.toArray());
    }
    
    public Collection search2ndSpecialityStudent(Student student, PageLimit pageLimit,
            String studentTypeIds, String firstDepartmentIds, String secondDepartmentIds,
            String andOr) {
        this.filterAuthorityByStudentType(studentTypeIds);
        List conditionList = ConditionUtils.extractConditions("student", student);
        EntityQuery studentQuery = new EntityQuery(Student.class, "student");
        studentQuery.setConditions(conditionList);
        studentQuery.setLimit(pageLimit);
        if (CollectionUtils.isNotEmpty(student.getAdminClasses())) {
            studentQuery.join("student.adminClasses", "adminClass");
            studentQuery.add(new Condition("adminClass in (:adminClasses)", student
                    .getAdminClasses()));
            if (student.getAdminClasses().size() == 2) {
                List adminClasses = new ArrayList(student.getAdminClasses());
                studentQuery.add(new Condition("exists (from AdminClass class"
                        + ((AdminClass) adminClasses.get(0)).getId() + " join class"
                        + ((AdminClass) adminClasses.get(0)).getId()
                        + ".students std where std.id=student.id and class"
                        + ((AdminClass) adminClasses.get(0)).getId() + ".id = :classId"
                        + ((AdminClass) adminClasses.get(0)).getId() + ")"
                        + " and exists (from AdminClass class"
                        + ((AdminClass) adminClasses.get(1)).getId() + " join class"
                        + ((AdminClass) adminClasses.get(1)).getId()
                        + ".students std where std.id=student.id and class"
                        + ((AdminClass) adminClasses.get(1)).getId() + ".id = :classId"
                        + ((AdminClass) adminClasses.get(1)).getId() + ")",
                        ((AdminClass) adminClasses.get(0)).getId(), ((AdminClass) adminClasses
                                .get(1)).getId()));
            }
        }
        Long[] firstDepartmentIdArray = SeqStringUtil.transformToLong(firstDepartmentIds);
        Long[] secondDepartmentIdArray = SeqStringUtil.transformToLong(secondDepartmentIds);
        if (!ArrayUtils.isEmpty(firstDepartmentIdArray)
                && !ArrayUtils.isEmpty(secondDepartmentIdArray)) {
            studentQuery
                    .add(new Condition(
                            "(student.firstMajor.department.id in (:firstMajor_department_id)) "
                                    + andOr
                                    + " (student.secondMajor.department.id in (:secondMajor_department_id))",
                            firstDepartmentIdArray, secondDepartmentIdArray));
        } else {
            if (!ArrayUtils.isEmpty(firstDepartmentIdArray)) {
                studentQuery.add(new Condition(
                        "student.firstMajor.department.id in (:firstMajor_department_id)",
                        firstDepartmentIdArray));
            }
            if (!ArrayUtils.isEmpty(secondDepartmentIdArray)) {
                studentQuery.add(new Condition(
                        "student.secondMajor.department.id in (:secondMajor_department_id)",
                        secondDepartmentIdArray));
            }
        }
        studentQuery.addOrder(new com.ekingstar.commons.query.Order("student.enrollYear",
                com.ekingstar.commons.query.Order.DESC));
        studentQuery.addOrder(new com.ekingstar.commons.query.Order("student.code"));
        return utilService.search(studentQuery);
    }
    
    /**
     * 获取默认排序
     * 
     * @return
     */
    private List getDefaultSortList() {
        List sortList = new ArrayList();
        sortList.add(new com.ekingstar.commons.query.Order("enrollYear",
                com.ekingstar.commons.query.Order.DESC));
        sortList.add(new com.ekingstar.commons.query.Order("code"));
        return sortList;
    }
    
    public void updateStudent(List QueryList) {
        studentDAO.updateStudent(QueryList);
    }
    
}

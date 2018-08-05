//$Id: TeachClass.java,v 1.16 2007/01/16 03:41:58 duanth Exp $
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
 * chaostone             2005-10-19         Created
 * hc 					 2005-12-19         add getPassStds() method
 * hc                    2006-05-15         add countPassStds() method
 ********************************************************************************/

package com.shufe.model.course.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.functors.AndPredicate;
import org.apache.commons.collections.functors.EqualPredicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.Component;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.service.course.arrange.task.predicate.BeanPredicateWrapper;

/**
 * 教学任务中的教学班.<br>
 * 教学班中规定了教学任务的主要开课对象： 1)enrollTurn学生的入学年份;<br>
 * 2)stdType面向的学生类别;<br>
 * 3)depart面向的院系;<br>
 * 4)speciality面向的专业;<br>
 * 5)aspect面向的专业方向;<br>
 * 6)adminClasses面向的行政班级;<br>
 * 若教学任务参与选课，此六项则作为默认的选课范围.<br>
 * name为教学班的名字，他不等同于教学班的名称.当只有一个行政班级是两者相同，没有班级时，为课程的名字.<br>
 * 教学班合班时，名字用","串起来.当超过固定宽度时，采用缩略标记法---后面追加"...".<br>
 * planStdCount为教学班级的计划人数，对应着行政班级的人数之和.计划人数可以在教学任务管理中进行修改.<br>
 * 在自动生成时为对应行政班级的人数，若没有行政班级则默认为0.手工添加班级时，将自动计算行政班的人数总和，<br>
 * 已确定计划人数. 计划人数主要主要在排课中使用.<br>
 * stdCount为教学班中的实际人数，对应着courseTakes中的size.<br>
 * courseTakes为实际上课的学生修读这门课修读信息.<br>
 * 
 * @see com.shufe.model.course.arrange.task.CourseTake <br>
 * @author chaostone 2005-10-21
 */
public class TeachClass implements Component {
    
    public static final String defaultName = "全校";
    
    /**
     * 是否需要指定学生
     */
    public static final int STD_NEED_ASSIGN = 2;
    
    /**
     * 学生是否已经指定
     */
    public static final int STD_ASSIGNED = 1;
    
    /**
     * 有选课学生
     */
    public static final int STD_ELECT = 4;
    
    public static final int STD_NULL = 0;
    
    /**
     * 教学班名称
     */
    private String name;
    
    /**
     * 性别
     */
    private Gender gender;
    
    /**
     * 所属教学任务
     */
    private TeachTask task;
    
    /**
     * 入学年份
     */
    private String enrollTurn;
    
    /**
     * 基础学生类别
     */
    private StudentType stdType;
    
    /**
     * 学生所在部门
     * 
     * @link association
     * @supplierCardinality 1
     */
    private Department depart = new Department();
    
    /**
     * 专业
     * 
     * @supplierCardinality 0...1
     */
    private Speciality speciality = new Speciality();
    
    /**
     * 专业方向
     * 
     * @supplierCardinality 0...1
     */
    private SpecialityAspect aspect = new SpecialityAspect();
    
    /**
     * 教学班的计划教学人数.自动生成教学任务时，若有行政班级，则对应行政班的班级人数总和.
     */
    private Integer planStdCount;
    
    /**
     * 是否把计划教学人数 作为选课人数上限
     */
    private Boolean isUpperLimit;
    
    /**
     * 学生数，对应着实际上课的人数
     */
    private Integer stdCount;
    
    /**
     * 班级学生状态(第一位(最低位)表示是否已经指定1指定,第二位表示是否需要指定).
     */
    private Integer stdState;
    
    /**
     * 该教学班中的所有实际上课学生的修读信息（包括行政班里的部分学生和选修该课的其他学生）
     * 
     * @see com.shufe.model.course.arrange.task.CourseTake
     */
    private Set courseTakes = new HashSet();
    
    /**
     * 退课名单
     */
    private Set withdraws = new HashSet();
    
    /**
     * 对应的行政班
     */
    private Set adminClasses = new HashSet();
    
    /**
     * 考试名单
     */
    private Set examTakes = new HashSet();
    
    public static TeachClass getDefault() {
        TeachClass teachClass = new TeachClass();
        teachClass.setPlanStdCount(new Integer(0));
        teachClass.setStdCount(new Integer(0));
        teachClass.setStdState(new Integer(0));
        return teachClass;
    }
    
    public TeachClass() {
        super();
    }
    
    /**
     * 仅以各个元素的主键创建一个教学班
     * 
     * @param enrollTurn
     * @param stdTypeId
     * @param departId
     * @param specialityId
     * @param aspectId
     * @param adminClassIds
     */
    public TeachClass(String enrollTurn, long stdTypeId, long departId, long specialityId,
            long aspectId, long[] adminClassIds) {
        this.enrollTurn = enrollTurn;
        stdType.setId(new Long(stdTypeId));
        depart.setId(new Long(departId));
        speciality.setId(new Long(specialityId));
        aspect.setId(new Long(aspectId));
        if (null != adminClassIds)
            for (int i = 0; i < adminClassIds.length; i++) {
                this.adminClasses.add(new AdminClass(new Long(adminClassIds[i])));
            }
    }
    
    public Boolean isStdAssigned() {
        if (null == stdState) {
            return new Boolean(false);
        } else {
            return new Boolean((stdState.intValue() & STD_ASSIGNED) > 0);
        }
    }
    
    public void setStdAssigned(Boolean isAssigned) {
        if (null == stdState) {
            stdState = new Integer(STD_NULL);
        }
        if (Boolean.TRUE.equals(isAssigned)) {
            stdState = new Integer(stdState.intValue() | STD_ASSIGNED);
        } else {
            if ((stdState.intValue() & STD_ASSIGNED) > 0) {
                stdState = new Integer(stdState.intValue() ^ STD_ASSIGNED);
            }
        }
    }
    
    public Boolean getIsStdNeedAssign() {
        if (null == stdState) {
            return null;
        }
        if ((stdState.intValue() & STD_NEED_ASSIGN) > 0) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
    
    public void setIsStdNeedAssign(Boolean needAssign) {
        if (null == stdState) {
            stdState = new Integer(STD_NULL);
        }
        if (null != needAssign) {
            if (Boolean.TRUE.equals(needAssign)) {
                stdState = new Integer(stdState.intValue() | STD_NEED_ASSIGN);
            } else {
                if ((stdState.intValue() & STD_NEED_ASSIGN) > 0) {
                    stdState = new Integer(stdState.intValue() ^ STD_NEED_ASSIGN);
                }
            }
        }
    }
    
    /**
     * 获得可能参加这门课的所有学生的修读信息<br>
     * 如果该教学班已经指定了学生,则直接返回courseTakes<br>
     * 否则将为行政班级中的学生自动生成一个修读信息放在返回结果中<br>
     * 
     * @return
     */
    public Set getPossibleCourseTakes() {
        if (Boolean.TRUE.equals(isStdAssigned())) {
            return getCourseTakes();
        } else {
            HashSet newCourseTakes = new HashSet();
            if (null != getCourseTakes()) {
                newCourseTakes.addAll(courseTakes);
            }
            if (null != getAdminClasses()) {
                for (Iterator iter = getAdminClasses().iterator(); iter.hasNext();) {
                    AdminClass adminClass = (AdminClass) iter.next();
                    for (Iterator iterator = adminClass.getStudents().iterator(); iterator
                            .hasNext();) {
                        CourseTake take = new CourseTake(getTask(), (Student) iterator.next(),
                                new CourseTakeType(CourseTakeType.COMPULSORY));
                        newCourseTakes.add(take);
                    }
                }
            }
            return newCourseTakes;
        }
    }
    
    /**
     * 获得教学班的所有学生名单（没有重修信息）
     * 
     * @return Student Set
     */
    public Set getStds() {
        Set stds = new HashSet();
        if (null != courseTakes && !courseTakes.isEmpty()) {
            for (Iterator iter = courseTakes.iterator(); iter.hasNext();) {
                CourseTake element = (CourseTake) iter.next();
                stds.add(element.getStudent());
            }
        }
        return stds;
    }
    
    /**
     * 返回正常参加上课的上课名单CourseTake
     * 
     * @return
     */
    public Set getNormalCourseTakes() {
        Set normalTakes = new HashSet();
        if (null != courseTakes && !courseTakes.isEmpty()) {
            for (Iterator iter = courseTakes.iterator(); iter.hasNext();) {
                CourseTake element = (CourseTake) iter.next();
                if (CourseTakeType.NORMAL_TYPES.contains(element.getCourseTakeType().getId()))
                    normalTakes.add(element);
            }
        }
        return normalTakes;
    }
    
    /**
     * TODO for test 获得所有不再任何教学班中的行政班中的学生修读信息
     */
    public Set getLonelyCourseTakes() {
        if (getCourseTakes() == null || getCourseTakes().isEmpty()) {
            return Collections.EMPTY_SET;
        }
        Set individualTakes = new HashSet();
        for (Iterator iter = getCourseTakes().iterator(); iter.hasNext();) {
            CourseTake courseTake = (CourseTake) iter.next();
            if (!CollectionUtils.intersection(courseTake.getStudent().getAdminClasses(),
                    getAdminClasses()).isEmpty()) {
                individualTakes.add(courseTake);
            }
        }
        return individualTakes;
    }
    
    /**
     * 向教学班中删除一批学生修读信息 并且增加学生人数
     * 
     * @param infos
     */
    public void removeCourseTakes(Collection courseTakes) {
        for (Iterator iter = courseTakes.iterator(); iter.hasNext();) {
            CourseTake courseTake = (CourseTake) iter.next();
            courseTake.setTask(null);
            getCourseTakes().remove(courseTake);
        }
        setStdCount(new Integer(getCourseTakes().size()));
    }
    
    /**
     * 向教学班中删除一个学生修读信息<br>
     * 并且增加学生人数
     * 
     * @param courseTake
     */
    public void removeCourseTake(CourseTake courseTake) {
        courseTake.setTask(null);
        getCourseTakes().remove(courseTake);
        decStdCount();
    }
    
    /**
     * 向教学班中添加一批学生修读信息 并且增加学生人数
     * 
     * @param infos
     */
    public void addCourseTakes(Collection courseTakes) {
        for (Iterator iter = courseTakes.iterator(); iter.hasNext();) {
            CourseTake courseTake = (CourseTake) iter.next();
            courseTake.setTask(getTask());
            getCourseTakes().add(courseTake);
        }
        setStdCount(new Integer(getCourseTakes().size()));
    }
    
    public List getExamTakes(ExamType examType) {
        if (CollectionUtils.isEmpty(getExamTakes())) {
            return Collections.EMPTY_LIST;
        } else {
            List rs = new ArrayList();
            for (Iterator iterator = getExamTakes().iterator(); iterator.hasNext();) {
                ExamTake take = (ExamTake) iterator.next();
                boolean found = false;
                if (examType.equals(take.getExamType())) {
                    found = true;
                }
                if (examType.getId().equals(ExamType.DELAY_AGAIN)) {
                    if (take.getExamType().getId().equals(ExamType.AGAIN)
                            || take.getExamType().getId().equals(ExamType.DELAY)) {
                        found = true;
                    }
                }
                if (found) {
                    rs.add(take);
                }
            }
            return rs;
        }
    }
    
    /**
     * 计算某一考试类型的考生人数
     * 
     * @param examType
     * @return
     */
    public int calcExamCount(ExamType examType) {
        return getExamTakes(examType).size();
    }
    
    /**
     * 向教学班中添加一个学生修读信息<br>
     * 并且增加学生人数
     * 
     * @param courseTake
     */
    public void addCourseTake(CourseTake courseTake) {
        courseTake.setTask(getTask());
        getCourseTakes().add(courseTake);
        incStdCount();
    }
    
    /**
     * 计算教学班中的计划人数.若存在行政班，则将行政班的人数之和看作新的计划人数. <br>
     * 实际调用该计算方法是由"使用者"决定.并非每次教学班中增加一个行政班均要重新计<br>
     * 算，可能不必要计算.
     */
    public void calcPlanStdCount(boolean byActual) {
        int count = 0;
        for (Iterator iter = adminClasses.iterator(); iter.hasNext();) {
            AdminClass adminClass = (AdminClass) iter.next();
            if (byActual) {
                count += adminClass.getActualStdCount().intValue();
            } else {
                count += adminClass.getPlanStdCount().intValue();
            }
        }
        setPlanStdCount(new Integer(count));
    }
    
    /**
     * 计算教学班中的实际人数.
     */
    public void calcStdCount() {
        if (null != courseTakes) {
            setStdCount(new Integer(courseTakes.size()));
        } else {
            setStdCount(new Integer(0));
        }
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer classInfo = new StringBuffer("[name=" + name + ",");
        if (null != stdType) {
            classInfo.append("stdType=").append(stdType.getId()).append(",");
        }
        if (null != depart) {
            classInfo.append("depart=").append(depart.getId()).append(",");
        }
        if (null != speciality) {
            classInfo.append("speciality=").append(speciality.getId()).append(",");
        }
        if (null != aspect) {
            classInfo.append("aspect=").append(aspect.getId()).append(",");
        }
        classInfo.append("planStdCount=").append(planStdCount + ",");
        classInfo.append("stdCount=").append(stdCount + ",");
        int i = 0;
        if (null != getAdminClasses()) {
            for (Iterator iter = getAdminClasses().iterator(); iter.hasNext();) {
                AdminClass adClass = (AdminClass) iter.next();
                classInfo.append("adminClass" + i++ + "=" + adClass.getId() + ",");
            }
        }
        i = 0;
        if (null != getCourseTakes()) {
            for (Iterator iter = getCourseTakes().iterator(); iter.hasNext();) {
                CourseTake info = (CourseTake) iter.next();
                classInfo.append("std" + i++ + "=" + info.getStudent().getCode() + ",");
            }
        }
        return classInfo.toString();
        
    }
    
    /**
     * 复制一个教学班，但并不复制他所在的教学任务引用<br>
     * 和教学班中的实际学生修读信息和实际学生数.
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        TeachClass one = new TeachClass();
        one.setEnrollTurn(getEnrollTurn());
        one.setStdType(getStdType());
        one.setDepart(getDepart());
        one.setSpeciality(getSpeciality());
        one.setAspect(getAspect());
        one.setName(getName());
        one.setPlanStdCount(getPlanStdCount());
        one.setStdCount(new Integer(0));
        one.setStdState(getStdState());
        return one;
    }
    
    public boolean hasStd(Student std) {
        return hasStd(std.getId());
    }
    
    public boolean hasStd(Long stdId) {
        return null != getCourseTake(stdId);
    }
    
    public CourseTake getCourseTake(Long stdId) {
        for (Iterator iter = getCourseTakes().iterator(); iter.hasNext();) {
            CourseTake take = (CourseTake) iter.next();
            if (take.getStudent().getId().equals(stdId)) {
                return take;
            }
        }
        return null;
    }
    
    public CourseTake getCourseTake(Student std) {
        return getCourseTake(std.getId());
    }
    
    public void incStdCount() {
        setStdCount(new Integer(getStdCount().intValue() + 1));
    }
    
    public void decStdCount() {
        setStdCount(new Integer(getStdCount().intValue() - 1));
    }
    
    /**
     * 含有一个或多个班级的任务进行构建教学班的名称
     */
    public void reNameByClass() {
        if (null != getAdminClasses() && !getAdminClasses().isEmpty()) {
            StringBuffer classNames = new StringBuffer("");
            List adminClassList = new ArrayList(getAdminClasses());
            Collections.sort(adminClassList, new BeanComparator("code"));
            for (Iterator iter = adminClassList.iterator(); iter.hasNext();) {
                AdminClass adminClass = (AdminClass) iter.next();
                if (classNames.length() != 0) {
                    classNames.append(" ");
                }
                classNames.append(adminClass.getName());
            }
            setName(classNames.toString());
        } else {
            setName(defaultName);
        }
    }
    
    public void processTaskForClass() {
        Set adminClasses = getAdminClasses();
        if (CollectionUtils.isEmpty(adminClasses)) {
            return;
        }
        Department oldDepart = null;
        StudentType oldStdType = null;
        Speciality oldMajor = null;
        SpecialityAspect oldMajorType = null;
        String oldEnroll = null;
        boolean isOldD = false;
        boolean isOldS = false;
        boolean isOldM = false;
        boolean isOldMT = false;
        boolean isOldE = false;
        for (Iterator it = adminClasses.iterator(); it.hasNext();) {
            AdminClass adminClass = (AdminClass) it.next();
            if (!isOldD) {
                if (null == oldDepart) {
                    oldDepart = adminClass.getDepartment();
                } else {
                    if (!ObjectUtils.equals(adminClass.getDepartment(), oldDepart)) {
                        oldDepart = new Department();
                        oldDepart.setId(Department.SCHOOLID);
                        isOldD = true;
                    }
                }
            }
            /*if (!isOldS) {
                if (null == oldStdType) {
                    oldStdType = adminClass.getStdType();
                } else {
                    if (!ObjectUtils.equals(adminClass.getStdType(), oldStdType)) {
                        isOldS = true;
                        oldStdType = null;
                    }
                }
            }*/
            if (!isOldM) {
                Speciality newMajor = adminClass.getSpeciality();
                if (null == oldMajor && null != newMajor) {
                    oldMajor = null == newMajor ? new Speciality() : newMajor;
                } else {
                    if (!ObjectUtils.equals(newMajor, oldMajor)) {
                        oldMajor = null;
                        isOldM = true;
                    }
                }
            }
            if (!isOldMT) {
                SpecialityAspect newMajorType = adminClass.getAspect();
                if (null == newMajorType) {
                    newMajorType = new SpecialityAspect();
                }
                if (null == oldMajorType && null != newMajorType) {
                    oldMajorType = newMajorType;
                } else {
                    if (!ObjectUtils.equals(newMajorType, oldMajorType) || null == newMajorType
                            && null == oldMajorType) {
                        oldMajorType = null;
                        isOldMT = true;
                    }
                }
            }
            if (!isOldE) {
                String newEnroll = adminClass.getEnrollYear();
                if (null == oldEnroll && StringUtils.isNotEmpty(newEnroll)) {
                    oldEnroll = newEnroll;
                } else {
                    if (!StringUtils.equals(newEnroll, oldEnroll)) {
                        oldEnroll = null;
                        isOldE = true;
                    }
                }
            }
        }
        if (null == oldDepart || null == oldDepart.getId()) {
            oldDepart = null;
        }
        setDepart(oldDepart);
        /*if (null == oldStdType || null == oldStdType.getId()) {
            oldStdType = null;
        }
        setStdType(oldStdType);*/
        if (null == oldMajor || null == oldMajor.getId()) {
            oldMajor = null;
        }
        setSpeciality(oldMajor);
        if (null == oldMajorType || null == oldMajorType.getId()) {
            oldMajorType = null;
        }
        setAspect(oldMajorType);
        setEnrollTurn(oldEnroll);
    }
    
    /** 返回有效学生的CourseTake，即学籍有效和状态在校的学生的CourseTake */
    public Collection getValidStdCourseTakes() {
        return CollectionUtils.select(this.getCourseTakes(), AndPredicate.getInstance(
                new BeanPredicateWrapper("student.state", new EqualPredicate(Boolean.TRUE)),
                new BeanPredicateWrapper("student.inSchool", new EqualPredicate(Boolean.TRUE))));
    }
    
    public void addExamTake(ExamTake take) {
        this.getExamTakes().add(take);
        take.setTask(getTask());
    }
    
    /**
     * TODO 上课班级的年级
     * 
     * @return
     */
    public String getAdminClassGrades() {
        if (CollectionUtils.isEmpty(this.adminClasses)) {
            return null;
        } else {
            Set gradeSet = new HashSet();
            for (Iterator it = this.adminClasses.iterator(); it.hasNext();) {
                AdminClass adminClass = (AdminClass) it.next();
                gradeSet.add(adminClass.getEnrollYear());
            }
            StringBuffer grades = new StringBuffer();
            for (Iterator it = gradeSet.iterator(); it.hasNext();) {
                String grade = (String) it.next();
                grades.append(grade);
                if (it.hasNext()) {
                    grades.append(", ");
                }
            }
            return grades.toString();
        }
    }
    
    /**
     * TODO 班级中的所属院系
     * 
     * @return
     */
    public String getAdminClassDepartments() {
        if (CollectionUtils.isEmpty(this.adminClasses)) {
            return "全校";
        } else {
            Set departmentSet = new HashSet();
            for (Iterator it = this.adminClasses.iterator(); it.hasNext();) {
                AdminClass adminClass = (AdminClass) it.next();
                departmentSet.add(adminClass.getDepartment().getName());
            }
            StringBuffer grades = new StringBuffer();
            for (Iterator it = departmentSet.iterator(); it.hasNext();) {
                String grade = (String) it.next();
                grades.append(grade);
                if (it.hasNext()) {
                    grades.append(", ");
                }
            }
            return grades.toString();
        }
    }
    
    /**
     * TODO 上课专业方向：班级的专业和方向的集合
     * 
     * @return
     */
    public String getAdminClassAspects() {
        if (CollectionUtils.isEmpty(this.adminClasses)) {
            return null;
        } else {
            Set departmentSet = new HashSet();
            for (Iterator it = this.adminClasses.iterator(); it.hasNext();) {
                AdminClass adminClass = (AdminClass) it.next();
                if (null != adminClass.getAspect()) {
                    departmentSet.add(adminClass.getAspect().getName());
                }
            }
            StringBuffer grades = new StringBuffer();
            for (Iterator it = departmentSet.iterator(); it.hasNext();) {
                String grade = (String) it.next();
                grades.append(grade);
                if (it.hasNext()) {
                    grades.append(", ");
                }
            }
            return grades.toString();
        }
    }
    
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return Returns the stdCount.
     */
    public Integer getStdCount() {
        return stdCount;
    }
    
    /**
     * @param stdCount
     *            The stdCount to set.
     */
    public void setStdCount(Integer stdCount) {
        this.stdCount = stdCount;
    }
    
    /**
     * @return Returns the courseTakes.
     */
    public Set getCourseTakes() {
        return courseTakes;
    }
    
    /**
     * @param courseTakes
     *            The courseTakes to set.
     */
    public void setCourseTakes(Set courseTakes) {
        this.courseTakes = courseTakes;
    }
    
    /**
     * @return Returns the adminClass.
     */
    public Set getAdminClasses() {
        return adminClasses;
    }
    
    /**
     * @param adminClass
     *            The adminClass to set.
     */
    public void setAdminClasses(Set adminClasses) {
        this.adminClasses = adminClasses;
    }
    
    /**
     * @return Returns the aspect.
     */
    public SpecialityAspect getAspect() {
        return aspect;
    }
    
    /**
     * @param aspect
     *            The aspect to set.
     */
    public void setAspect(SpecialityAspect aspect) {
        this.aspect = aspect;
    }
    
    /**
     * @return Returns the year.
     */
    public String getEnrollTurn() {
        return enrollTurn;
    }
    
    /**
     * @param year
     *            The year to set.
     */
    public void setEnrollTurn(String year) {
        this.enrollTurn = year;
    }
    
    /**
     * @return Returns the depart.
     */
    public Department getDepart() {
        return depart;
    }
    
    /**
     * @param depart
     *            The depart to set.
     */
    public void setDepart(Department depart) {
        this.depart = depart;
    }
    
    /**
     * @return Returns the speciality.
     */
    public Speciality getSpeciality() {
        return speciality;
    }
    
    /**
     * @param speciality
     *            The speciality to set.
     */
    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
    
    /**
     * @return Returns the stdType.
     */
    public StudentType getStdType() {
        return stdType;
    }
    
    /**
     * @param stdType
     *            The stdType to set.
     */
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    /**
     * @return Returns the task.
     */
    public TeachTask getTask() {
        return task;
    }
    
    /**
     * @param task
     *            The task to set.
     */
    public void setTask(TeachTask task) {
        this.task = task;
    }
    
    /**
     * @return Returns the planStdCount.
     */
    public Integer getPlanStdCount() {
        return planStdCount;
    }
    
    /**
     * @param planStdCount
     *            The planStdCount to set.
     */
    public void setPlanStdCount(Integer planStdCount) {
        this.planStdCount = planStdCount;
    }
    
    public Integer getStdState() {
        return stdState;
    }
    
    public void setStdState(Integer stdState) {
        this.stdState = stdState;
    }
    
    public Set getWithdraws() {
        return withdraws;
    }
    
    public void setWithdraws(Set withdraws) {
        this.withdraws = withdraws;
    }
    
    public Set getExamTakes() {
        return examTakes;
    }
    
    public void setExamTakes(Set examTakes) {
        this.examTakes = examTakes;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public String getAdminClassEnrollTurn() {
        if (CollectionUtils.isNotEmpty(this.adminClasses)) {
            Iterator it = this.adminClasses.iterator();
            String ss = ((AdminClass) it.next()).getEnrollYear();
            System.out.println(ss);
            return ss;
        }
        return "";
    }

    
    public Boolean getIsUpperLimit() {
        return isUpperLimit;
    }

    
    public void setIsUpperLimit(Boolean isUpperLimit) {
        this.isUpperLimit = isUpperLimit;
    }
}

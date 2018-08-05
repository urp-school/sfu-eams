//$Id: ElectStdScope.java,v 1.6 2006/12/29 14:04:03 duanth Exp $
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
 * chaostone             2005-10-16         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.election;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.course.task.TeachClass;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Department;

/**
 * 教学任务中的选课对象范围.
 * <p>
 * 选课对象范围定义了教学任务中允许选课的学生条件.选课对象范围定义为两种形式. 1)大范围非学号描述.采用了学生的入学时间和所属院系和专业等信息.
 * 这些条件的设置允许多值，两边和中间采用逗号间隔的形式存储, 如",2004-2,2004-3,"和",001,002,".
 * 2)学号段描述.采用了[startNO,endNo]的闭区间形势规定了学生的范围. 学号段的上下界不一定是真实的学号.例如[109000，199999]的形式就起到了[109*]的描述作用.
 * 这两者放在同一个实体中，在技术上允许把学号段看作对1)范围的进一步规定，从而使得业务规则扩充. 可是现在不需要.
 * <p>
 * 具体这些条件为: <br>
 * 1)enrollTurns 学生的所在年级,可以允许多个<br>
 * 2)stdTypeIds 学生类别代码串<br>
 * 3)departIds 院系代码串<br>
 * 4)specialityIds 专业代码串<br>
 * 5)aspectIds 专业方向代码串<br>
 * 6)adminClassIds 行政班级代码串 同一选课范围要么上述属性不空，要么下面两个属性同时不空.<br>
 * 7)startNo 起始学号 8)endNo 终止学号
 * <p>
 * 
 * @author chaostone 2005-10-16
 */
public class ElectStdScope extends LongIdObject implements Cloneable {
    
    private static final long serialVersionUID = -7877824368631199605L;
    
    public static final int ENROLLYEAR_MARK = 1;
    
    public static final int STDTYPE_MARK = 2;
    
    public static final int DEPART_MARK = 4;
    
    public static final int SPECIALITY_MARK = 8;
    
    public static final int ASPECT_MARK = 16;
    
    public static final int ADMINCLASS_MARK = 32;
    
    /** 关联的教学任务 */
    protected TeachTask task = new TeachTask();
    
    /**
     * 入学年份串 can be * to represent ommitted enroll turn,for example 2005-4,* can be multiple
     * seperate with comma for exampe 2005-3,3002-4;
     */
    private String enrollTurns;
    
    /** 学生类别ID串 */
    private String stdTypeIds;
    
    /** 所在部门ID串 */
    private String departIds;
    
    /** 专业ID串 */
    private String specialityIds;
    
    /** 专业方向ID串 */
    private String aspectIds;
    
    /** 班级ID串 */
    private String adminClassIds;
    
    /**
     * 起始学号<br>
     * startNo and endNo defined a individual stdNo scope for [startNo,endNo] erea.all other
     * attibute can be null,use the fowllowing stdNo to define elective student scope.
     */
    private String startNo;
    
    /**
     * 终止学号
     */
    private String endNo;
    
    public ElectStdScope() {
    }
    
    /**
     * 根据教学班中的开课对象,生成一个选课范围
     * 
     * @param teachClass
     */
    public ElectStdScope(TeachClass teachClass) {
        if (StringUtils.isNotEmpty(teachClass.getEnrollTurn()))
            this.enrollTurns = "," + teachClass.getEnrollTurn() + ",";
        this.stdTypeIds = "," + teachClass.getStdType().getId() + ",";
        if (null != teachClass.getDepart() && teachClass.getDepart().isPO())
            departIds = "," + teachClass.getDepart().getId() + ",";
        if (null != teachClass.getSpeciality() && teachClass.getSpeciality().isPO())
            specialityIds = "," + teachClass.getSpeciality().getId() + ",";
        if (null != teachClass.getAspect() && teachClass.getAspect().isPO())
            aspectIds = "," + teachClass.getAspect().getId() + ",";
        if (null != teachClass.getAdminClasses() && !teachClass.getAdminClasses().isEmpty()) {
            adminClassIds = ",";
            for (Iterator iter = teachClass.getAdminClasses().iterator(); iter.hasNext();)
                adminClassIds += ((AdminClass) iter.next()).getId() + ",";
        }
    }
    
    public ElectStdScope(Collection adminClasses, int initScope) {
        if (CollectionUtils.isEmpty(adminClasses)) {
            return;
        }
        // 根据初始化的范围，提取有效数据
        Set enrollYearSet = new HashSet();
        Set stdTypeIdSet = new HashSet();
        Set departIdSet = new HashSet();
        Set specialityIdSet = new HashSet();
        Set aspectIdSet = new HashSet();
        for (Iterator iterator = adminClasses.iterator(); iterator.hasNext();) {
            AdminClass adminClass = (AdminClass) iterator.next();
            // 如果有专业方向限制则院系等可以不要
            if ((initScope & ASPECT_MARK) > 0 && null != adminClass.getAspect()) {
                aspectIdSet.add(adminClass.getAspect().getId());
                initScope ^= SPECIALITY_MARK;
                initScope ^= DEPART_MARK;
            }
            if (((initScope & SPECIALITY_MARK) > 0) && ((initScope & ASPECT_MARK) != 1)
                    && null != adminClass.getSpeciality()) {
                specialityIdSet.add(adminClass.getSpeciality().getId());
                initScope ^= DEPART_MARK;
            }
            if (((initScope & DEPART_MARK) > 0) && null != adminClass.getDepartment()) {
                departIdSet.add(adminClass.getDepartment().getId());
            }
            if (((initScope & STDTYPE_MARK) > 0) && null != adminClass.getStdType()) {
                stdTypeIdSet.add(adminClass.getStdType().getId());
            }
            if ((initScope & ENROLLYEAR_MARK) > 0) {
                enrollYearSet.add(adminClass.getEnrollYear());
            }
        }
        // 设置范围
        setEnrollTurns(transformToString(enrollYearSet));
        setStdTypeIds(transformToString(stdTypeIdSet));
        setDepartIds(transformToString(departIdSet));
        setSpecialityIds(transformToString(specialityIdSet));
        setAspectIds(transformToString(aspectIdSet));
    }
    
    private String transformToString(Set objs) {
        if (CollectionUtils.isEmpty(objs))
            return null;
        StringBuffer sb = new StringBuffer(",");
        for (Iterator iterator = objs.iterator(); iterator.hasNext();) {
            Object obj = (Object) iterator.next();
            sb.append(obj).append(",");
        }
        return sb.toString();
    }
    
    /**
     * 依照给定的参数,决定从教学班中拷贝哪些属性 1所在年级，2学生类别，4院系，8专业，16方向，32班级
     * 
     * @param teachClass
     * @param initScope
     */
    public ElectStdScope(TeachClass teachClass, int initScope) {
        if (initScope / 32 == 1) {
            if (null != teachClass.getAdminClasses() && !teachClass.getAdminClasses().isEmpty()) {
                adminClassIds = ",";
                for (Iterator iter = teachClass.getAdminClasses().iterator(); iter.hasNext();)
                    adminClassIds += ((AdminClass) iter.next()).getId() + ",";
            }
        }
        initScope %= 32;
        if (initScope / 16 == 1) {
            if (null != teachClass.getAspect() && teachClass.getAspect().isPO())
                aspectIds = "," + teachClass.getAspect().getId() + ",";
        }
        initScope %= 16;
        if (initScope / 8 == 1) {
            if (null != teachClass.getSpeciality() && teachClass.getSpeciality().isPO())
                specialityIds = "," + teachClass.getSpeciality().getId() + ",";
        }
        initScope %= 8;
        if (initScope / 4 == 1) {
            if (null != teachClass.getDepart() && teachClass.getDepart().isPO())
                departIds = "," + teachClass.getDepart().getId() + ",";
        }
        initScope %= 4;
        if (initScope / 2 == 1) {
            this.stdTypeIds = "," + teachClass.getStdType().getId() + ",";
        }
        initScope %= 2;
        if (initScope == 1) {
            if (StringUtils.isNotEmpty(teachClass.getEnrollTurn()))
                this.enrollTurns = "," + teachClass.getEnrollTurn() + ",";
        }
    }
    
    public boolean emptyEnrollTurns() {
        return (null == enrollTurns || "".equals(enrollTurns));
    }
    
    public boolean emptyStdTypeIds() {
        return (null == stdTypeIds || "".equals(stdTypeIds));
    }
    
    public boolean emptyDepartIds() {
        return (null == departIds || "".equals(departIds));
    }
    
    public boolean emptySpecialityIds() {
        return (null == specialityIds || "".equals(specialityIds));
    }
    
    public boolean emptyAspectIds() {
        return (null == aspectIds || "".equals(aspectIds));
    }
    
    public boolean emptyAdminClassIds() {
        return (null == adminClassIds || "".equals(adminClassIds));
    }
    
    /**
     * 合并选课范围
     * 
     * @param other
     */
    public void merge(ElectStdScope other) {
        if (!other.emptyEnrollTurns()) {
            if (emptyEnrollTurns())
                setEnrollTurns(other.getEnrollTurns());
            else {
                setEnrollTurns(SeqStringUtil.mergeSeq(getEnrollTurns(), other.getEnrollTurns()));
            }
        }
        
        if (!other.emptyStdTypeIds()) {
            if (emptyStdTypeIds())
                setStdTypeIds(other.getStdTypeIds());
            else {
                setStdTypeIds(SeqStringUtil.mergeSeq(getStdTypeIds(), other.getStdTypeIds()));
            }
        }
        
        if (!other.emptyDepartIds()) {
            if (emptyDepartIds()) {
                setDepartIds(other.getDepartIds());
            } else {
                setDepartIds(SeqStringUtil.mergeSeq(getDepartIds(), other.getDepartIds()));
            }
        }
        if (!other.emptySpecialityIds()) {
            if (emptySpecialityIds()) {
                setSpecialityIds(other.getSpecialityIds());
            } else {
                setSpecialityIds(SeqStringUtil.mergeSeq(getSpecialityIds(), other
                        .getSpecialityIds()));
            }
        }
        if (!other.emptyAspectIds()) {
            if (emptyAspectIds()) {
                setAspectIds(other.getAspectIds());
            } else {
                setAspectIds(SeqStringUtil.mergeSeq(getAspectIds(), other.getAspectIds()));
            }
        }
        if (!other.emptyAdminClassIds()) {
            if (emptyAdminClassIds()) {
                setAdminClassIds(other.getAdminClassIds());
            } else {
                setAdminClassIds(SeqStringUtil.mergeSeq(getAdminClassIds(), other
                        .getAdminClassIds()));
            }
        }
    }
    
    /**
     * 以教学班的教学对象，合并到该选课范围
     * 
     * @param teachClass
     * @return
     */
    public ElectStdScope merge(TeachClass teachClass) {
        if (StringUtils.isNotEmpty(teachClass.getEnrollTurn())) {
            if (StringUtils.isEmpty(getEnrollTurns()))
                setEnrollTurns("," + teachClass.getEnrollTurn() + ",");
            else {
                if (!StringUtils.contains(getEnrollTurns(), teachClass.getEnrollTurn()))
                    setEnrollTurns(getEnrollTurns() + teachClass.getEnrollTurn() + ",");
            }
        }
        if (null != teachClass.getStdType() && teachClass.getStdType().isPO()) {
            if (StringUtils.isEmpty(getStdTypeIds()))
                setStdTypeIds("," + teachClass.getStdType().getId() + ",");
            else {
                if (!StringUtils.contains(getStdTypeIds(), teachClass.getStdType().getId()
                        .toString()))
                    setStdTypeIds(getStdTypeIds() + teachClass.getStdType().getId() + ",");
            }
        }
        if (null != teachClass.getDepart() && teachClass.getDepart().isPO()) {
            if (StringUtils.isEmpty(getDepartIds()))
                setDepartIds("," + teachClass.getDepart().getId() + ",");
            else {
                if (!StringUtils
                        .contains(getDepartIds(), teachClass.getDepart().getId().toString()))
                    setDepartIds(getDepartIds() + teachClass.getDepart().getId() + ",");
            }
        }
        if (null != teachClass.getSpeciality() && teachClass.getSpeciality().isPO()) {
            if (StringUtils.isEmpty(getSpecialityIds()))
                setSpecialityIds("," + teachClass.getSpeciality().getId() + ",");
            else {
                if (!StringUtils.contains(getSpecialityIds(), teachClass.getSpeciality().getId()
                        .toString()))
                    setSpecialityIds(getSpecialityIds() + teachClass.getSpeciality().getId() + ",");
            }
        }
        if (null != teachClass.getAspect() && teachClass.getAspect().isPO()) {
            if (StringUtils.isEmpty(getAspectIds()))
                setAspectIds("," + teachClass.getAspect().getId() + ",");
            else {
                if (!StringUtils
                        .contains(getAspectIds(), teachClass.getAspect().getId().toString()))
                    setAspectIds(getAspectIds() + teachClass.getAspect().getId() + ",");
            }
        }
        if (null != teachClass.getAdminClasses() && !teachClass.getAdminClasses().isEmpty()) {
            for (Iterator iter = teachClass.getAdminClasses().iterator(); iter.hasNext();) {
                String adminClassId = ((AdminClass) iter.next()).getId().toString();
                if (StringUtils.isEmpty(getAdminClassIds()))
                    setAdminClassIds("," + adminClassId + ",");
                else {
                    if (!StringUtils.contains(getAdminClassIds(), adminClassId))
                        setAdminClassIds(getAdminClassIds() + adminClassId + ",");
                }
            }
        }
        return this;
    }
    
    /**
     * 判断对应的学生是否在该组内
     * 
     * @param std
     * @return
     */
    public boolean isIn(SimpleStdInfo std) {
        if (null == std)
            return false;
        return isInStdNoSeg(std.getStdNo()) || isInScope(std);
    }
    
    /**
     * 判断是否在学号段范围内
     * 
     * @param std
     * @return
     */
    private boolean isInStdNoSeg(String stdNo) {
        if ((null != startNo && null != endNo)
                && (startNo.compareTo(stdNo) <= 0 && endNo.compareTo(stdNo) >= 0))
            return true;
        return false;
    }
    
    private boolean isInScope(SimpleStdInfo std) {
        // 先检查班级
        if (StringUtils.isNotEmpty(adminClassIds)) {
            for (Iterator iter = std.getAdminClassIds().iterator(); iter.hasNext();) {
                Long id = (Long) iter.next();
                if (StringUtils.contains(adminClassIds, "," + String.valueOf(id) + ","))
                    return true;
            }
            return false;
        } else {
            // 如果只是全校，只检查所在年级
            if (null == departIds || departIds.equals("," + Department.SCHOOLID.toString() + ",")) {
                if (StringUtils.isNotEmpty(enrollTurns)) {
                    if (StringUtils.contains(enrollTurns, "," + std.getEnrollTurn() + ","))
                        return true;
                    else
                        return false;
                }
                // 按需检查学生类别
                if (StringUtils.isNotEmpty(stdTypeIds)) {
                    if (StringUtils.contains(stdTypeIds, "," + std.getStdTypeId() + ","))
                        return true;
                    else
                        return false;
                }
                return true;
            } else {
                if (!StringUtils.contains(departIds, String.valueOf(std.getDepartId())))
                    return false;
                if (StringUtils.isNotEmpty(enrollTurns)
                        && !StringUtils.contains(enrollTurns, "," + std.getEnrollTurn() + ","))
                    return false;
                if (StringUtils.isNotEmpty(stdTypeIds)
                        && !StringUtils.contains(stdTypeIds, ","
                                + String.valueOf(std.getStdTypeId() + ",")))
                    return false;
                
                if (StringUtils.isNotEmpty(specialityIds)) {
                    String specialityId = "";
                    if (null != std.getSpecialityId())
                        specialityId = String.valueOf(std.getSpecialityId());
                    if (!(StringUtils.contains(specialityIds, "," + specialityId + ",")))
                        return false;
                }
                if (StringUtils.isNotEmpty(aspectIds)) {
                    String aspectId = null;
                    if (null != std.getAspectId())
                        aspectId = String.valueOf(std.getAspectId());
                    if (!(StringUtils.contains(aspectIds, "," + aspectId + ",")))
                        return false;
                }
            }
        }
        return true;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (StringUtils.isNotEmpty(getStartNo())) {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("startNo",
                    this.startNo).append("endNo", this.endNo).toString();
        } else {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("stdTypeIds",
                    this.stdTypeIds).append("departIds", this.departIds).append("enrollTurns",
                    this.enrollTurns).append("specialityIds", this.specialityIds).append(
                    "aspectIds", this.aspectIds).append("adminClassIds", this.adminClassIds)
                    .toString();
        }
        
    }
    
    /**
     * 克隆本范围
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        try {
            ElectStdScope scope = (ElectStdScope) BeanUtils.cloneBean(this);
            scope.setId(null);
            scope.setTask(null);
            return scope;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    /**
     * 是否相同的范围
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean isSame(Object object) {
        if (!(object instanceof ElectStdScope)) {
            return false;
        }
        ElectStdScope rhs = (ElectStdScope) object;
        return SeqStringUtil.isEqualSeq(getEnrollTurns(), rhs.getEnrollTurns())
                && SeqStringUtil.isEqualSeq(getStdTypeIds(), rhs.getStdTypeIds())
                && SeqStringUtil.isEqualSeq(getDepartIds(), rhs.getDepartIds())
                && SeqStringUtil.isEqualSeq(getSpecialityIds(), rhs.getSpecialityIds())
                && SeqStringUtil.isEqualSeq(getAspectIds(), rhs.getAspectIds())
                && SeqStringUtil.isEqualSeq(getAdminClassIds(), rhs.getAdminClassIds())
                && SeqStringUtil.isEqualSeq(getStartNo(), rhs.getStartNo())
                && SeqStringUtil.isEqualSeq(getEndNo(), rhs.getEndNo());
        
    }
    
    /**
     * 是否是一个无意义的空范围
     * 
     * @return
     */
    public boolean isEmptyScope() {
        return StringUtils.isEmpty(enrollTurns) && StringUtils.isEmpty(stdTypeIds)
                && StringUtils.isEmpty(departIds) && StringUtils.isEmpty(specialityIds)
                && StringUtils.isEmpty(aspectIds) && StringUtils.isEmpty(adminClassIds)
                && StringUtils.isEmpty(startNo) && StringUtils.isEmpty(endNo);
        
    }
    
    public TeachTask getTask() {
        return task;
    }
    
    public void setTask(TeachTask teachTask) {
        this.task = teachTask;
    }
    
    public String getAdminClassIds() {
        return adminClassIds;
    }
    
    public void setAdminClassIds(String adminClassIds) {
        this.adminClassIds = adminClassIds;
    }
    
    public String getAspectIds() {
        return aspectIds;
    }
    
    public void setAspectIds(String aspectIds) {
        this.aspectIds = aspectIds;
    }
    
    public String getDepartIds() {
        return departIds;
    }
    
    public void setDepartIds(String departIds) {
        this.departIds = departIds;
    }
    
    public String getSpecialityIds() {
        return specialityIds;
    }
    
    public void setSpecialityIds(String specialityIds) {
        this.specialityIds = specialityIds;
    }
    
    public String getStdTypeIds() {
        return stdTypeIds;
    }
    
    public void setStdTypeIds(String stdTypeIds) {
        this.stdTypeIds = stdTypeIds;
    }
    
    public String getEnrollTurns() {
        return enrollTurns;
    }
    
    public void setEnrollTurns(String belongToYear) {
        this.enrollTurns = belongToYear;
    }
    
    public String getEndNo() {
        return endNo;
    }
    
    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }
    
    public String getStartNo() {
        return startNo;
    }
    
    public void setStartNo(String startNo) {
        this.startNo = startNo;
    }
    
}

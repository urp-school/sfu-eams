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
 * chaostone             2006-6-5            Created
 *  
 ********************************************************************************/

package com.shufe.model.course.election;

import com.ekingstar.eams.system.basecode.state.Gender;

/**
 * 选课范围初始化参数设置
 * 
 * @author chaostone
 */
public class ElectInitParams {
    
    public static String ADD = "add";
    
    public static String REMOVE = "remove";
    
    public static String MERGE = "merge";
    
    public static String NULL = "null";
    
    /** 是否删除已有的选课范围 */
    boolean removeExistedScope;
    
    /** 通过教学班初始化选课范围 1所在年级，2学生类别，4院系，8专业，16方向，32班级 */
    int initByTeachClass;
    
    /** 共享选课范围的条件 1所在年级，2学生类别，4院系，8专业，16方向 */
    int shareScopeCondition;
    
    /** 通过行政班信息添加选课范围1所在年级，2学生类别，4院系，8专业，16方向 */
    int addScopeByAdminClass;
    
    /** 对于选课范围的添加/删除 */
    String batchAction;
    
    /** 是否可选 */
    boolean isElectable;
    
    /** 所在年级 */
    String enrollTurns;
    
    String stdCountSwitchs;
    
    Integer stdCountValue;
    
    Integer stdCount;
    
    /** 依照教室容量更新选课人数上限 */
    boolean updateStdMaxCountByRoomConfig;
    
    /** 依照计划人数更新选课人数上限 */
    boolean updateStdMaxCountByPlanCount;
    
    /** 依照教室容量和计划人数最小值，更新选课人数上限 */
    boolean updateMinWithRoomConfigAndPlanCount;
    
    /** 依照教室真实人数和计划人数最小值，更新选课人数上限 */
    boolean updateRoomCapacityOrMinStdCount;
    
    /** 是否设置人数下限 */
    boolean settingMinStdCount;
    
    /** 人数下限 */
    Integer minStdCount;
    
    /** 是否设置人数上限 */
    boolean settingMaxStdCount;
    
    /** 人数上限 */
    Integer maxStdCount;
    
    /** 选课范围 */
    ElectStdScope scope = new ElectStdScope();
    
    /** 是否允许退课 */
    Boolean isCancelable;
    
    /** 男女生 */
    Gender gender;
    
    public String getEnrollTurns() {
        return enrollTurns;
    }
    
    public void setEnrollTurns(String enrollTurns) {
        this.enrollTurns = enrollTurns;
    }
    
    public int isInitByTeachClass() {
        return initByTeachClass;
    }
    
    public void setInitByTeachClass(int initByTeachClass) {
        this.initByTeachClass = initByTeachClass;
    }
    
    public int getAddScopeByAdminClass() {
        return addScopeByAdminClass;
    }
    
    public void setAddScopeByAdminClass(int addScopeByAdminClass) {
        this.addScopeByAdminClass = addScopeByAdminClass;
    }
    
    public Integer getMaxStdCount() {
        return maxStdCount;
    }
    
    public void setMaxStdCount(Integer maxStdCount) {
        this.maxStdCount = maxStdCount;
    }
    
    public Integer getMinStdCount() {
        return minStdCount;
    }
    
    public void setMinStdCount(Integer minStdCount) {
        this.minStdCount = minStdCount;
    }
    
    public boolean isRemoveExistedScope() {
        return removeExistedScope;
    }
    
    public void setRemoveExistedScope(boolean removeExistedScope) {
        this.removeExistedScope = removeExistedScope;
    }
    
    public boolean isSettingMaxStdCount() {
        return settingMaxStdCount;
    }
    
    public void setSettingMaxStdCount(boolean settingMaxStdCount) {
        this.settingMaxStdCount = settingMaxStdCount;
    }
    
    public boolean isSettingMinStdCount() {
        return settingMinStdCount;
    }
    
    public void setSettingMinStdCount(boolean settingMinStdCount) {
        this.settingMinStdCount = settingMinStdCount;
    }
    
    public boolean isUpdateStdMaxCountByPlanCount() {
        return updateStdMaxCountByPlanCount;
    }
    
    public void setUpdateStdMaxCountByPlanCount(boolean updateStdMaxCountByPlanCount) {
        this.updateStdMaxCountByPlanCount = updateStdMaxCountByPlanCount;
    }
    
    public boolean isUpdateStdMaxCountByRoomConfig() {
        return updateStdMaxCountByRoomConfig;
    }
    
    public void setUpdateStdMaxCountByRoomConfig(boolean updateStdMaxCountByRoomConfig) {
        this.updateStdMaxCountByRoomConfig = updateStdMaxCountByRoomConfig;
    }
    
    public boolean getIsElectable() {
        return isElectable;
    }
    
    public void setIsElectable(boolean isElectable) {
        this.isElectable = isElectable;
    }
    
    public String getBatchAction() {
        return batchAction;
    }
    
    public Boolean getIsCancelable() {
        return isCancelable;
    }
    
    public void setIsCancelable(Boolean isCancelable) {
        this.isCancelable = isCancelable;
    }
    
    public void setBatchAction(String batchAction) {
        this.batchAction = batchAction;
    }
    
    public ElectStdScope getScope() {
        return scope;
    }
    
    public void setScope(ElectStdScope scope) {
        this.scope = scope;
    }
    
    public int getShareScopeCondition() {
        return shareScopeCondition;
    }
    
    public void setShareScopeCondition(int shareScopeCondition) {
        this.shareScopeCondition = shareScopeCondition;
    }
    
    public String getStdCountSwitchs() {
        return stdCountSwitchs;
    }
    
    public void setStdCountSwitchs(String stdCountSwitch) {
        this.stdCountSwitchs = stdCountSwitch;
    }
    
    public Integer getStdCountValue() {
        return stdCountValue;
    }
    
    public void setStdCountValue(Integer stdCountType) {
        this.stdCountValue = stdCountType;
    }
    
    public Integer getStdCount() {
        return stdCount;
    }
    
    public void setStdCount(Integer stdCount) {
        this.stdCount = stdCount;
    }
    
    public boolean isUpdateMinWithRoomConfigAndPlanCount() {
        return updateMinWithRoomConfigAndPlanCount;
    }
    
    public void setUpdateMinWithRoomConfigAndPlanCount(boolean updateMinWithRoomConfigAndPlanCount) {
        this.updateMinWithRoomConfigAndPlanCount = updateMinWithRoomConfigAndPlanCount;
    }
    
    public boolean isUpdateRoomCapacityOrMinStdCount() {
        return updateRoomCapacityOrMinStdCount;
    }
    
    public void setUpdateRoomCapacityOrMinStdCount(boolean updateStdCount) {
        this.updateRoomCapacityOrMinStdCount = updateStdCount;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

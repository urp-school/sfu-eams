package com.shufe.model.course.task;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;

/**
 * 实验室样表要求
 * 
 * @author fengxutao
 * 
 */
public class LaboratoryRequirement extends LongIdObject {

    private static final long serialVersionUID = 6128321440719074794L;

    /** 教学任务 */
    private TeachTask task;

    /** 实验地点 **/
    private ClassroomType classroomType;

    /** 计划实验总学时 **/
    private Float overallUnit;

    /** 实验占总成绩的比例 **/
    private String propExperimental;

    /** 实验上机时间 **/
    private String timeDescrition;

    /** 实验所需软件及环境要求 **/
    private String experimentRequirement;

    /** 实验项目安排 **/
    private String projectDescrition;

    public TeachTask getTask() {
        return task;
    }

    public void setTask(TeachTask task) {
        this.task = task;
    }

    public ClassroomType getClassroomType() {
        return classroomType;
    }

    public void setClassroomType(ClassroomType classroomType) {
        this.classroomType = classroomType;
    }

    public Float getOverallUnit() {
        return overallUnit;
    }

    public void setOverallUnit(Float overallUnit) {
        this.overallUnit = overallUnit;
    }

    public String getPropExperimental() {
        return propExperimental;
    }

    public void setPropExperimental(String propExperimental) {
        this.propExperimental = propExperimental;
    }

    public String getTimeDescrition() {
        return timeDescrition;
    }

    public void setTimeDescrition(String timeDescrition) {
        this.timeDescrition = timeDescrition;
    }

    public String getExperimentRequirement() {
        return experimentRequirement;
    }

    public void setExperimentRequirement(String experimentRequirement) {
        this.experimentRequirement = experimentRequirement;
    }

    public String getProjectDescrition() {
        return projectDescrition;
    }

    public void setProjectDescrition(String projectDescrition) {
        this.projectDescrition = projectDescrition;
    }
}

//$Id: TeachTaskFilterCategory.java,v 1.2 2006/08/24 12:32:50 duanth Exp $
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
 * chaostone             2005-10-22         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.task;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * 教学任务过滤模式（按什么类别过滤）
 * 
 * @author chaostone 2005-10-22
 */
public abstract class TeachTaskFilterCategory {
    /**
     * 过滤的类别
     */
    private String name;

    private String prefix = "from TeachTask as task ";

    private String postfix;
    
    private static final Map INSTANCES = new HashMap();
    
    protected TeachTaskFilterCategory(String name) {
        this.name = name;
    }

    /**
     * @return Returns the category.
     */
    public String getCategoryName() {
        return name;
    }

    /**
     * @return Returns the postfix.
     */
    public String getPostfix() {
        return postfix;
    }

    /**
     * @param postfix
     *            The postfix to set.
     */
    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    /**
     * @return Returns the prefix.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix
     *            The prefix to set.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 形成过滤的匹配串
     * 
     * @return
     */
    public abstract String getFilterString();

    public Query createQuery(Session session) {
        return session.createQuery(prefix + getFilterString() + postfix);
    }

    public String getQueryString() {
        return prefix + getFilterString() + postfix;
    }

    /**
     * 从开课部门角度过滤
     */
    public static final TeachTaskFilterCategory TEACHDEPART = new TeachTaskFilterCategory(
            "teachDepart") {
        public String getFilterString() {
            return " where task.arrangeInfo.teachDepart.id = :id ";
        }
    };

    /**
     * 从上课部门角度过滤
     */
    public static final TeachTaskFilterCategory DEPART = new TeachTaskFilterCategory(
            "depart") {
        public String getFilterString() {
            return " where task.teachClass.depart.id = :id ";
        }
    };

    /**
     * 从专业角度过滤
     */
    public static final TeachTaskFilterCategory SPECIALITY = new TeachTaskFilterCategory(
            "speciality") {
        public String getFilterString() {
            return " where task.teachClass.speciality.id = :id ";
        }
    };

    /**
     * 从专业方向角度过滤
     */
    public static final TeachTaskFilterCategory SPECIALITY_ASPECT = new TeachTaskFilterCategory(
            "aspect") {
        public String getFilterString() {
            return " where task.teachClass.aspect.id= :id ";
        }
    };

    /**
     * 从行政班级角度过滤
     */
    public static final TeachTaskFilterCategory ADMINCLASS = new TeachTaskFilterCategory(
            "adminClass") {
        public String getFilterString() {
            return " inner join task.teachClass.adminClasses as adminClass "
                    + "where adminClass.id= :id ";
        }
    };

    /**
     * 从课程类别角度过滤
     */
    public static final TeachTaskFilterCategory COURSETYPE = new TeachTaskFilterCategory(
            "courseType") {
        public String getFilterString() {
            return " where task.courseType.id= :id ";
        }
    };

    /**
     * 从学生类别角度过滤
     */
    public static final TeachTaskFilterCategory STDTYPE = new TeachTaskFilterCategory(
            "stdType") {
        public String getFilterString() {
            return " where task.teachClass.stdType.id= :id ";
        }
    };

    /**
     * 从教师角度过滤
     */
    public static final TeachTaskFilterCategory TEACHER = new TeachTaskFilterCategory(
            "teacher") {
        public String getFilterString() {
            return " join task.arrangeInfo.teachers as teacher" +
            		" where teacher.id = :id ";
        }
    };

    /**
     * 将学生的学号和所在行政班级(包括二专业)的一并考虑在内. 实际上是检查学生选修的课程和必修的课程.
     */
    public static final TeachTaskFilterCategory STD = new TeachTaskFilterCategory(
            "std") {
        public String getFilterString() {
            return " join task.teachClass.courseTakes as takeInfo"
                    + " where (takeInfo.student.id= :id)";
        }
    };
    static {
        INSTANCES.put(TEACHDEPART.name, TEACHDEPART);
        INSTANCES.put(DEPART.name, DEPART);
        INSTANCES.put(SPECIALITY.name, SPECIALITY);
        INSTANCES.put(SPECIALITY_ASPECT.name, SPECIALITY_ASPECT);
        INSTANCES.put(COURSETYPE.name, COURSETYPE);
        INSTANCES.put(ADMINCLASS.name, ADMINCLASS);
        INSTANCES.put(STDTYPE.name, STDTYPE);
        INSTANCES.put(TEACHER.name, TEACHER);
        INSTANCES.put(STD.name, STD);
    }

    public static TeachTaskFilterCategory getCategorty(String name) {
        return (TeachTaskFilterCategory) INSTANCES.get(name);
    }
}

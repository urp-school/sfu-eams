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
 * chaostone             2006-12-13            Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.grade.hibernate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;

import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.security.User;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.grade.GradeDAO;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;

public class GradeDAOHibernate extends BasicHibernateDAO implements GradeDAO {
    
    // protected GradeLogService gradeLogService;
    
    public boolean needReStudy(Student std, Course course) {
        String hql = "select grade.isPass from CourseGrade grade where grade.std.id=" + std.getId()
                + " and grade.course.id=" + course.getId();
        Query query = getSession().createQuery(hql);
        List rs = query.list();
        if (rs.isEmpty())
            return false;
        else {
            for (Iterator iter = rs.iterator(); iter.hasNext();) {
                Boolean isPass = (Boolean) iter.next();
                if (Boolean.TRUE.equals(isPass))
                    return false;
            }
            return true;
        }
    }
    
    public boolean isPass(Student std, Course course) {
        String hql = "select grade.isPass from CourseGrade grade where grade.std.id=" + std.getId()
                + " and grade.course.id=" + course.getId();
        Query query = getSession().createQuery(hql);
        List rs = query.list();
        if (rs.isEmpty())
            return false;
        else {
            for (Iterator iter = rs.iterator(); iter.hasNext();) {
                Boolean isPass = (Boolean) iter.next();
                if (Boolean.TRUE.equals(isPass))
                    return true;
            }
            return false;
        }
    }
    
    public Map getGradeCourseMap(Long stdId) {
        Query query = getSession().getNamedQuery("getGradeCourses");
        query.setParameter("stdId", stdId);
        List rs = query.list();
        Map courseMap = new HashMap(rs.size());
        for (Iterator iter = rs.iterator(); iter.hasNext();) {
            Object obj[] = (Object[]) iter.next();
            if (null != obj[1]) {
                courseMap.put(obj[0], (Boolean) obj[1]);
            } else {
                courseMap.put(obj[0], Boolean.FALSE);
            }
        }
        return courseMap;
    }
    
    public void removeExamGrades(TeachTask task, GradeType gradeType, User user) {
        String hql = "from ExamGrade examGrade where examGrade.courseGrade.task.id=" + task.getId()
                + " and examGrade.gradeType.id=" + gradeType.getId();
        List examGrades = getSession().createQuery(hql).list();
        Set grades = new HashSet();
        for (Iterator it = examGrades.iterator(); it.hasNext();) {
            ExamGrade exam = (ExamGrade) it.next();
            CourseGrade grade = exam.getCourseGrade();
            grades.add(grade);
            grade.getExamGrades().remove(exam);
        }
        // grades.addAll(gradeLogService.buildGradeCatalogInfo(user, new ArrayList(grades), null,
        // null, "这位学生的这门课有部分成绩被删除了。", true));
        utilDao.saveOrUpdate(grades);
    }
    
    public void removeGrades(TeachTask task, User user) {
        String hql = "update CourseTake set courseGrade=null where task=:task";
        Query updateTake = getSession().createQuery(hql);
        updateTake.setParameter("task", task);
        updateTake.executeUpdate();
        
        hql = "from CourseGrade courseGrade where courseGrade.task.id=" + task.getId();
        List grades = getSession().createQuery(hql).list();
        // utilDao.saveOrUpdate(gradeLogService.buildGradeCatalogInfo(user, grades, null, null,
        // "这位学生的这门课其所有成绩被删除了。", true));
        utilDao.remove(grades);
    }
    
    public void publishCourseGrade(TeachTask task, Boolean isPublished) {
        String hql = "update CourseGrade set status="
                + (Boolean.TRUE.equals(isPublished) ? Grade.PUBLISHED : Grade.CONFIRMED)
                + " where task.id=" + task.getId();
        getSession().createQuery(hql).executeUpdate();
    }
    
    public void publishExamGrade(TeachTask task, GradeType gradeType, Boolean isPublished) {
        String hql = "update ExamGrade set status="
                + (Boolean.TRUE.equals(isPublished) ? Grade.PUBLISHED : Grade.CONFIRMED)
                + "where gradeType.id=" + gradeType.getId()
                + " exists(from CourseGrade cg where cg.task.id=" + task.getId()
                + "and cg.id=courseGrade.id)";
        getSession().createQuery(hql).executeUpdate();
    }
    
    public void publishExamGrade(TeachTask task, List gradeTypes, Boolean isPublished) {
        StringBuffer sb = new StringBuffer(",");
        for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
            GradeType gradeType = (GradeType) iter.next();
            sb.append(gradeType.getId()).append(",");
        }
        
        String hql = "update ExamGrade set status="
                + (Boolean.TRUE.equals(isPublished) ? Grade.PUBLISHED : Grade.CONFIRMED)
                + " where id in (select id from ExamGrade where instr('" + sb
                + "',','||gradeType.id||',')>0 " + " and courseGrade.task.id=" + task.getId() + ")";
        getSession().createQuery(hql).executeUpdate();
    }
    
    public Long getGradeMaxCalendarId(Student std) {
        String hql = "select Max(cg.calendar.id) from CourseGrade cg where cg.std.id="
                + std.getId();
        Query query = getSession().createQuery(hql);
        Long calendarId = (Long) query.list().get(0);
        return calendarId;
    }
    
    // public final void setGradeLogService(GradeLogService gradeLogService) {
    // this.gradeLogService = gradeLogService;
    // }
}

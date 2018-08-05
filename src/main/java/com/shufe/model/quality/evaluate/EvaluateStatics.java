package com.shufe.model.quality.evaluate;

import java.text.DecimalFormat;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;


public class EvaluateStatics extends LongIdObject {
    
    private static final long serialVersionUID = 1416090442769945717L;
    
    /** 教学任务 */
    private TeachTask task = new TeachTask();
    
    /** 教学日历 */
    private TeachCalendar calendar = new TeachCalendar();
    
    /** 学生类别 */
    private StudentType stdType;
    
    /** 学生评教的课程 */
    private Course course = new Course();
    
    /** 任课教师 */
    private Teacher teacher = new Teacher();
    
    /** 开课院系 */
    private Department depart = new Department();
    
    /** 课程序号 */
    private String taskSeqNo;
    
    /** 评A的人次 */
    private Long contA;
    
    /** 评B的人次 */
    private Long contB;
    
    /** 评C的人次 */
    private Long contC;
    
    /** 评D的人次 */
    private Long contD;
    
    /** 评E的人次 */
    private Long contE;
    
    
    /** 评教人数 */
    private Integer aumtCont;
    
    /** 总得分 */
    private Float score;

    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    
    public TeachCalendar getCalendar() {
        return calendar;
    }


    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }


    
    public Long getContA() {
        return contA;
    }


    
    public void setContA(Long contA) {
        this.contA = contA;
    }


    
    public Long getContB() {
        return contB;
    }


    
    public void setContB(Long contB) {
        this.contB = contB;
    }


    
    public Long getContC() {
        return contC;
    }


    
    public void setContC(Long contC) {
        this.contC = contC;
    }


    
    public Long getContD() {
        return contD;
    }


    
    public void setContD(Long contD) {
        this.contD = contD;
    }


    
    public Long getContE() {
        return contE;
    }


    
    public void setContE(Long contE) {
        this.contE = contE;
    }


    
    public Course getCourse() {
        return course;
    }


    
    public void setCourse(Course course) {
        this.course = course;
    }


    
    public Department getDepart() {
        return depart;
    }


    
    public void setDepart(Department depart) {
        this.depart = depart;
    }


    
    public StudentType getStdType() {
        return stdType;
    }


    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }


    
    public TeachTask getTask() {
        return task;
    }


    
    public void setTask(TeachTask task) {
        this.task = task;
    }


    
    public String getTaskSeqNo() {
        return taskSeqNo;
    }


    
    public void setTaskSeqNo(String taskSeqNo) {
        this.taskSeqNo = taskSeqNo;
    }


    
    public Teacher getTeacher() {
        return teacher;
    }


    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public Integer getAumtCont() {
        return aumtCont;
    }

    public void setAumtCont(Integer aumtCont) {
        this.aumtCont = aumtCont;
    }



    public Float getScore() {
        return score;
    }
    
    public void setScore(Float score) {
        this.score = score;
    }
    
    public String getScoreString(){
        float all = score.floatValue();
        DecimalFormat df = new DecimalFormat("0.0"); 
        String allString = df.format(all);
        return allString;
    }
    public String getPesentA(){
        long a = contA.longValue();
        long all = contA.longValue()+contB.longValue()+contC.longValue()+contD.longValue()+contE.longValue();
        float pesentA = ((float)a/all)*100;
        DecimalFormat df = new DecimalFormat("0.0"); 
        String pesentAString = df.format(pesentA)+"%";
        return pesentAString;
    }
    
    public String getPesentB(){
        long a = contB.longValue();
        long all = contA.longValue()+contB.longValue()+contC.longValue()+contD.longValue()+contE.longValue();
        float pesentA = ((float)a/all)*100;
        DecimalFormat df = new DecimalFormat("0.0"); 
        String pesentAString = df.format(pesentA)+"%";
        return pesentAString;
    }
    
    public String getPesentC(){
        long a = contC.longValue();
        long all = contA.longValue()+contB.longValue()+contC.longValue()+contD.longValue()+contE.longValue();
        float pesentA = ((float)a/all)*100;
        DecimalFormat df = new DecimalFormat("0.0"); 
        String pesentAString = df.format(pesentA)+"%";
        return pesentAString;
    }
   
    public String getPesentD(){
        long a = contD.longValue();
        long all = contA.longValue()+contB.longValue()+contC.longValue()+contD.longValue()+contE.longValue();
        float pesentA = ((float)a/all)*100;
        DecimalFormat df = new DecimalFormat("0.0"); 
        String pesentAString = df.format(pesentA)+"%";
        return pesentAString;
    }

    
    public String getPesentE(){
        long a = contE.longValue();
        long all = contA.longValue()+contB.longValue()+contC.longValue()+contD.longValue()+contE.longValue();
        float pesentA = ((float)a/all)*100;
        DecimalFormat df = new DecimalFormat("0.0"); 
        String pesentAString = df.format(pesentA)+"%";
        return pesentAString;
    }
}

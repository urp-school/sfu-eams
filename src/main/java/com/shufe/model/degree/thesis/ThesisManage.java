//$Id: ThesisManage.java,v 1.9 2007/01/25 05:11:57 cwx Exp $
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
 * chenweixiong              2006-10-18         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ekingstar.commons.model.AbstractEntity;
import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.degree.thesis.annotate.Annotate;
import com.shufe.model.degree.thesis.answer.FormalAnswer;
import com.shufe.model.degree.thesis.answer.PreAnswer;
import com.shufe.model.degree.thesis.process.Schedule;
import com.shufe.model.degree.thesis.process.TacheSetting;
import com.shufe.model.degree.thesis.topicOpen.TopicOpen;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 论文
 * 
 * @author Administrator
 */
public class ThesisManage extends LongIdObject {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2215183995390542263L;

	private TopicOpen topicOpen = new TopicOpen(); // 论文开题
    
    private Student student = new Student();// 学生
    
    private Set preAnswerSet = new HashSet(); // 论文预答辩
    
    private Thesis thesis = new Thesis(); // 定稿的论文
    
    private Annotate annotate = new Annotate(); // 论文评阅
    
    private FormalAnswer formalAnswer = new FormalAnswer(); // 论文答辩
    
    private TeachCalendar teachCalendar = new TeachCalendar();// 教学日历
    
    private Schedule schedule = new Schedule(); // 进度安排
    
    private MajorType majorType;// 专业类别
    
    private Teacher tutor; // 指导教师
    
    public ThesisManage() {
        super();
    }
    
    public ThesisManage(Student student, MajorType majorType) {
    	this.majorType=majorType;
        if (MajorType.FIRST.equals(majorType.getId())) {
            this.tutor = student.getTeacher();
        }
        this.student = student;
    }
    
 
    public void addAnswers(PreAnswer preAnswer) {
        this.preAnswerSet.add(preAnswer);
        preAnswer.setThesisManage(this);
    }

    
    public MajorType getMajorType() {
		return majorType;
	}

	public void setMajorType(MajorType majorType) {
		this.majorType = majorType;
	}

	public Thesis getThesis() {
        return thesis;
    }
    
    /**
     * @param thesis The thesis to set.
     */
    public void setThesis(Thesis thesis) {
        this.thesis = thesis;
    }
    
    /**
     * @return Returns the annotate.
     */
    public Annotate getAnnotate() {
        return annotate;
    }
    
    /**
     * @param annotate The annotate to set.
     */
    public void setAnnotate(Annotate annotate) {
        this.annotate = annotate;
    }
    
    /**
     * @return Returns the preAnswerSet.
     */
    public Set getPreAnswerSet() {
        return preAnswerSet;
    }
    
    /**
     * @param preAnswerSet The preAnswerSet to set.
     */
    public void setPreAnswerSet(Set preAnswerSet) {
        this.preAnswerSet = preAnswerSet;
    }
    
    /**
     * @return Returns the student.
     */
    public Student getStudent() {
        return student;
    }
    
    /**
     * @param student The student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }
    
    /**
     * @return Returns the topicOpen.
     */
    public TopicOpen getTopicOpen() {
        return topicOpen;
    }
    
    /**
     * @param topicOpen The topicOpen to set.
     */
    public void setTopicOpen(TopicOpen topicOpen) {
        this.topicOpen = topicOpen;
    }
    
    /**
     * @return Returns the teachCalendar.
     */
    public TeachCalendar getTeachCalendar() {
        return teachCalendar;
    }
    
    /**
     * @param teachCalendar The teachCalendar to set.
     */
    public void setTeachCalendar(TeachCalendar teachCalendar) {
        this.teachCalendar = teachCalendar;
    }
    
    /**
     * @return Returns the formalAnswer.
     */
    public FormalAnswer getFormalAnswer() {
        return formalAnswer;
    }
    
    /**
     * @param formalAnswer The formalAnswer to set.
     */
    public void setFormalAnswer(FormalAnswer formalAnswer) {
        this.formalAnswer = formalAnswer;
    }
    
    /**
     * @return Returns the schedule.
     */
    public Schedule getSchedule() {
        return schedule;
    }
    
    /**
     * @param schedule The schedule to set.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    /**
     * 得到学生最近一次的预答辩申请信息
     * 
     * @return
     */
    public PreAnswer getPreAnswer() {
        PreAnswer preAnswer = new PreAnswer();
        int num = 0;
        if (this.getPreAnswerSet().size() > 0) {
            for (Iterator iter = this.getPreAnswerSet().iterator(); iter.hasNext();) {
                PreAnswer element = (PreAnswer) iter.next();
                if (num < element.getAnswerNum().intValue()) {
                    num = element.getAnswerNum().intValue();
                    preAnswer = element;
                }
            }
        }
        return preAnswer;
    }
    
    /**
     * 得到对应的存储对象
     * 
     * @param processName
     * @return
     */
    public ThesisStore getProcessObject(String processName) {
        if ("topicOpen".equals(processName)) {
            return (ThesisStore) this.getTopicOpen();
        } else if ("annotate".equals(processName)) {
            return (ThesisStore) this.getAnnotate();
        } else if ("preAnswer".equals(processName)) {
            return (ThesisStore) this.getPreAnswer();
        } else if ("formalAnswer".equals(processName)) {
            return (ThesisStore) this.getFormalAnswer();
        } else if ("thesis".equals(processName)) {
            return (ThesisStore) this.getThesis();
        } else {
            throw new RuntimeException("no find this processName=" + processName
                    + ",please check it again");
        }
    }
    
    /**
     * @param processName
     * @return
     */
    public ThesisStore getProcessById(String processid) {
        if ("01".equals(processid)) {
            return (ThesisStore) this.getTopicOpen();
        } else if ("03".equals(processid)) {
            return (ThesisStore) this.getAnnotate();
        } else if ("04".equals(processid)) {
            return (ThesisStore) this.getPreAnswer();
        } else if ("05".equals(processid)) {
            return (ThesisStore) this.getFormalAnswer();
        } else if ("06".equals(processid)) {
            return (ThesisStore) this.getThesis();
        } else {
            return new ThesisStore();
        }
    }
    
    /**
     * 得到存储对象 并且添加下载的文件名和显示的文件名
     * 
     * @param processName
     * @param downloadName
     * @param displayName
     * @return
     */
    public ThesisStore setProcessObject(String processName, String downloadName, String displayName) {
        ThesisStore thesisStore = new ThesisStore();
        try {
            thesisStore = getProcessObject(processName);
        } catch (Exception e) {
            throw new RuntimeException("no find this processName=" + processName
                    + ",please check it again");
        }
        thesisStore.setDisplayName(displayName);
        thesisStore.setDownloadName(downloadName);
        return thesisStore;
    }
    
    /**
     * @param processName
     * @param downloadName
     * @param displayName
     * @return
     */
    public ThesisStore setProcessById(String processId, String downloadName, String displayName) {
        ThesisStore thesisStore = new ThesisStore();
        try {
            thesisStore = getProcessById(processId);
        } catch (Exception e) {
            throw new RuntimeException("no find this processId=" + processId
                    + ",please check it again");
        }
        thesisStore.setDisplayName(displayName);
        thesisStore.setDownloadName(downloadName);
        return thesisStore;
    }
    
    /**
     * 得到当前应该做的进度
     * 
     * @return
     */
    public TacheSetting getTacheSetting() {
        TacheSetting setting = new TacheSetting();
        long time = Long.MAX_VALUE;
        Calendar now = Calendar.getInstance();
        if (null == this.getSchedule()) {
            return setting;
        }
        for (Iterator iter = this.getSchedule().getTacheSettings().iterator(); iter.hasNext();) {
            TacheSetting element = (TacheSetting) iter.next();
            long elementTimeMillis = element.getPlanedTimeOn().getTime();
            long nowTimeMillis = now.getTimeInMillis();
            long temp = 0;
            if (nowTimeMillis > elementTimeMillis) {
                temp = nowTimeMillis;
                nowTimeMillis = elementTimeMillis;
                elementTimeMillis = temp;
            }
            if (elementTimeMillis - nowTimeMillis < time) {
                time = elementTimeMillis - nowTimeMillis;
                setting = element;
            }
        }
        return setting;
    }
    
    /**
     * 得到当前正在做的进程
     * 
     * @return
     */
    public String getProcess() {
        if (null != this.getFormalAnswer() && null != this.getFormalAnswer().getFinishOn()) {
            return "over";
        } else if (null != this.getAnnotate() && null != this.getAnnotate().getFinishOn()) {
            return "formalAnswer";
        } else if (null != this.getPreAnswer() && null != this.getPreAnswer().getFinishOn()) {
            return "annotate";
        } else if (null != this.getTopicOpen() && null != this.getTopicOpen().getFinishOn()) {
            return "preAnswer";
        } else {
            return "topicOpen";
        }
    }
    
    /**
     * 判断对应的步骤是否满足id是否满足要求
     * 
     * @param thesisStore
     * @return
     */
    public boolean checkObjectId(ThesisStore thesisStore) {
        boolean flag = true;
        if (null == thesisStore || null == thesisStore.getId()
                || new Long(0).equals(thesisStore.getId())) {
            flag = false;
        }
        return flag;
    }
    
    /**
     * 判断论文管理里面是否有符合条件的tacheId
     * 
     * @param tacheId
     * @return
     */
    public boolean containTache(Long tacheId) {
        boolean flag = false;
        if (null == this.getSchedule()) {
            return flag;
        }
        for (Iterator iter = this.getSchedule().getTacheSettings().iterator(); iter.hasNext();) {
            TacheSetting setting = (TacheSetting) iter.next();
            if (tacheId.equals(setting.getTache().getId())) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    public boolean isNotNull(AbstractEntity entity) {
        boolean flag = true;
        if (null == entity || null == entity.getEntityId()
                || new Long(0).equals(entity.getEntityId())) {
            flag = false;
        }
        return flag;
    }
    
    /**
     * @return Returns the tutor.
     */
    public Teacher getTutor() {
        return tutor;
    }
    
    /**
     * @param tutor The tutor to set.
     */
    public void setTutor(Teacher tutor) {
        this.tutor = tutor;
    }
    
}

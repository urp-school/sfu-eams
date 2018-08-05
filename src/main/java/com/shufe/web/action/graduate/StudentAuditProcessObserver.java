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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/

package com.shufe.web.action.graduate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachCommon;
import com.shufe.model.std.Student;
import com.shufe.web.OutputMessage;
import com.shufe.web.OutputProcessObserver;
import com.shufe.web.OutputWebObserver;

/**
 * 批量审核特定条件内学生观察者
 */
public class StudentAuditProcessObserver extends OutputProcessObserver {
    
    public static final StudentAuditProcessObserver NULL_OBSERVER = new NullObserver();
    
    private static class NullObserver extends StudentAuditProcessObserver {
        
        public NullObserver() {
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#addAuditRes(java.lang.String,
         *      java.util.Map)
         */
        public void addAuditRes(String code, Map resMap) throws IOException {
            // super.addAuditRes(code, resMap);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#addRemark(java.lang.String,
         *      java.lang.String)
         */
        public void addRemark(String code, String remark) {
            // super.addRemark(code, remark);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#addNoTeachPlanError(java.lang.String,
         *      java.lang.String, java.lang.Long)
         */
        public void addNoTeachPlanError(String code, String remark, Long stdId) {
            // super.addNoTeachPlanError(code, remark, stdId);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#addResult(java.lang.String,
         *      boolean, java.lang.Long)
         */
        public void addResult(String code, boolean flag, Long id, Integer majorTypeId) {
            // super.addResult(code, flag, id);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#addResult(java.lang.String,
         *      java.lang.String)
         */
        public void addResult(String code, String result) {
            // super.addResult(code, result);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#addStdOutputNotify(java.lang.String,
         *      java.lang.String)
         */
        public void addStdOutputNotify(String code, String name) throws IOException {
            // super.addStdOutputNotify(code, name);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#notifyFinish()
         */
        public void notifyFinish() throws IOException {
            // super.notifyFinish();
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#notifyFinish(int, long)
         */
        public void notifyFinish(int count, long time) throws IOException {
            // super.notifyFinish(count, time);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#notifyGenResult(int, int)
         */
        public void notifyGenResult(int schemeCount, int taskCount) throws IOException {
            // super.notifyGenResult(schemeCount, taskCount);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#notifyStart(java.lang.String,
         *      int)
         */
        public void notifyStart(String summary, int count) {
            // super.notifyStart(summary, count);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#outputNotify(int, int,
         *      com.shufe.model.course.plan.TeachPlan)
         */
        public void outputNotify(int term, int courseCount, TeachPlan plan) throws IOException {
            // super.outputNotify(term, courseCount, plan);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#outputNotify(int,
         *      com.shufe.model.course.plan.TeachPlan, java.lang.String)
         */
        public void outputNotify(int term, TeachPlan plan, String messageKey) throws IOException {
            // super.outputNotify(term, plan, messageKey);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#outputNotify(java.util.Map)
         */
        public void outputNotify(Map parameterMap) throws IOException {
            // super.outputNotify(parameterMap);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputProcessObserver#notifyStart(java.lang.String, int,
         *      java.lang.String[])
         */
        public void notifyStart(String summary, int count, String[] msgs) {
            // super.notifyStart(summary, count, msgs);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputProcessObserver#outputNotify(int, com.shufe.web.OutputMessage,
         *      boolean)
         */
        public void outputNotify(int level, OutputMessage msgObj, boolean increaceProcess) {
            // super.outputNotify(level, msgObj, increaceProcess);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputProcessObserver#outputNotify(int, com.shufe.web.OutputMessage)
         */
        public void outputNotify(int level, OutputMessage msgObj) throws IOException {
            // super.outputNotify(level, msgObj);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputProcessObserver#outputTemplate()
         */
        public void outputTemplate() {
            // super.outputTemplate();
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputProcessObserver#setOverallCount(int)
         */
        public void setOverallCount(int count) {
            // super.setOverallCount(count);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputProcessObserver#setPath(java.lang.String)
         */
        public void setPath(String path) {
            // super.setPath(path);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputProcessObserver#setSummary(java.lang.String)
         */
        public void setSummary(String msg) {
            // super.setSummary(msg);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputWebObserver#notifyStart()
         */
        public void notifyStart() throws IOException {
            // super.notifyStart();
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputWebObserver#outputNotify(com.shufe.web.OutputMessage)
         */
        public void outputNotify(OutputMessage msgObj) throws IOException {
            // super.outputNotify(msgObj);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputWebObserver#setLocale(java.util.Locale)
         */
        public void setLocale(Locale locale) {
            // super.setLocale(locale);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputWebObserver#setResourses(org.apache.struts.util.MessageResources)
         */
        public void setResourses(MessageResources resourses) {
            // super.setResourses(resourses);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.OutputWebObserver#setWriter(java.io.PrintWriter)
         */
        public void setWriter(PrintWriter writer) {
            // super.setWriter(writer);
        }
        
        /*
         * （非 Javadoc）
         * 
         * @see com.shufe.web.action.graduate.StudentAuditProcessObserver#increaceProcess(int)
         */
        public void increaceProcess(int increaceProcess) {
            // super.increaceProcess(increaceProcess);
        }
        
    }
    
    public static final int pageSize = 100;
    
    public StudentAuditProcessObserver() {
        super();
    }
    
    public void increaceProcess(int increaceProcess) {
        try {
            writer.print("<script>increaceProcess(" + increaceProcess + ");</script>\n");
            writer.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public void notifyStart(String summary, int count) {
        try {
            setSummary(summary);
            setOverallCount(count);
        } catch (Exception e) {
            throw new RuntimeException("IO Exeption:" + e.getMessage());
        }
    }
    
    public void addRemark(String code, String remark) {
        try {
            writer.print("<script>addRemark('" + code + "','" + remark + "');</script>\n");
            writer.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public void addNoTeachPlanError(String code, String remark, Long stdId) {
        try {
            writer.print("<script>addNoTeachPlanError('" + code + "','" + remark + "'," + stdId
                    + ");</script>\n");
            writer.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public void addResult(String code, String result) {
        try {
            writer.print("<script>addResult('" + code + "','" + result + "');</script>\n");
            writer.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public void addResult(String code, boolean flag, Long id, MajorType majorType,
            Long auditStandardId, String terms) {
        try {
            writer.print("<script>addResult('" + code + "'," + flag + "," + id + ","
                    + majorType.getId() + "," + auditStandardId + ",'" + terms + "');</script>\n");
            writer.flush();
        } catch (Exception e) {
        	 // TODO: handle exception
        }
    }
    
    public void notifyFinish() throws IOException {
        outputNotify(OutputWebObserver.good, new OutputMessage("", "本次毕业审核结束。"), false);
    }
    
    public void notifyFinish(int count, long time) throws IOException {
        outputNotify(OutputWebObserver.good, new OutputMessage("", "本次毕业审核结束。"), false);
        outputNotify(OutputWebObserver.good, new OutputMessage("", "共审核" + count + "位学生,耗时"
                + formatTime(time)), false);
    }
    
    public void notifyGenResult(int schemeCount, int taskCount) throws IOException {
        OutputMessage message = new OutputMessage("", taskCount + " Tasks in " + schemeCount
                + " Schemes generated");
        outputNotify(OutputWebObserver.good, message, false);
    }
    
    /*
     * public void notifyGenResult(int schemeCount, int taskCount) throws IOException {
     * OutputMessage message = new OutputMessage("", taskCount + " Tasks in " + schemeCount + "
     * Schemes generated"); outputNotify(OutputWebObserver.good, message); }
     */

    public String message(Object msgObj) {
        OutputMessage message = (OutputMessage) msgObj;
        return messageOf(message.getKey()) + message.getMessage() + "<br>";
    }
    
    public void outputNotify(int term, int courseCount, TeachPlan plan) throws IOException {
        OutputMessage message = new OutputMessage("", StringUtils.repeat("&nbsp;", 9) + term
                + StringUtils.repeat("&nbsp;", 13) + courseCount + StringUtils.repeat("&nbsp;", 8)
                + new TeachCommon(plan).toString());
        outputNotify(OutputWebObserver.good, message);
    }
    
    public void addStdOutputNotify(String code, String name) throws IOException {
        try {
            writer.print("<script>addStd('" + code + "','" + name + "');</script>\n");
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException("IO Exeption:" + e.getMessage());
        }
    }
    
    public void addAuditRes(String code, Map resMap) throws IOException {
        try {
            writer.print("<script>addAuditRes('" + code + "','" + resMap + "');</script>\n");
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException("IO Exeption:" + e.getMessage());
        }
    }
    
    public void outputNotify(int term, TeachPlan plan, String messageKey) throws IOException {
        OutputMessage message = new OutputMessage("", StringUtils.repeat("&nbsp;", 9)
                + messageOf(messageKey) + StringUtils.repeat("&nbsp;", 8)
                + new TeachCommon(plan).toString());
        outputNotify(OutputWebObserver.good, message);
    }
    
    public void outputNotify(Map parameterMap) throws IOException {
        Student student = (Student) parameterMap.get("student");
        Map stdResult = (Map) parameterMap.get("stdResult");
        StringBuffer buf = new StringBuffer();
        buf.append(student.getName() + "[" + student.getCode() + "]" + ": ");
        if (student.getFirstMajor() != null && student.getFirstMajor().isPO()) {
            buf.append(messageOf("entity.firstSpeciality") + "["
                    + student.getFirstMajor().getName() + "] "
                    + teachPlanCompletedStatus(stdResult.get("isFirstTeachPlanCompleted")));
        }
        if (student.getSecondMajor() != null && student.getSecondMajor().isPO()) {
            buf.append(messageOf("entity.secondSpeciality") + "["
                    + student.getSecondMajor().getName() + "] "
                    + teachPlanCompletedStatus(stdResult.get("isSecondTeachPlanCompleted")));
        }
        buf.append("<BR>");
        writer.println(buf);
        writer.flush();
    }
    
    public String teachPlanCompletedStatus(Object teachPlanCompletedStatus) {
        if (teachPlanCompletedStatus == null) {
            return messageOf("common.null");
        } else if (teachPlanCompletedStatus instanceof Boolean) {
            if (((Boolean) teachPlanCompletedStatus).booleanValue()) {
                return messageOf("attr.graduate.pass");
            } else if (!((Boolean) teachPlanCompletedStatus).booleanValue()) {
                return messageOf("attr.graduate.noPass");
            }
        }
        return null;
    }
    
    public StringBuffer formatTime(long time) {
        long millis = 0;
        long second = 0;
        long minute = 0;
        long hour = 0;
        long oldTemp = 0;
        long newTemp = 0;
        oldTemp = time / 1000;
        millis = time - oldTemp * 1000;
        newTemp = oldTemp / 60;
        second = oldTemp - newTemp * 60;
        oldTemp = newTemp;
        newTemp = oldTemp / 60;
        minute = oldTemp - newTemp * 60;
        hour = newTemp;
        StringBuffer buf = new StringBuffer();
        buf.append(messageOf("common.time.useTime") + ":" + hour + messageOf("common.time.hour")
                + minute + messageOf("common.time.minute") + second
                + messageOf("common.time.second") + millis + messageOf("common.time.millis"));
        return buf;
    }
}

package com.shufe.web.action.quality.evaluate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.quality.evaluate.EvaluateResult;
import com.shufe.model.quality.evaluate.EvaluateStatics;
import com.shufe.model.quality.evaluate.QuestionResult;
import com.shufe.model.quality.evaluate.Questionnaire;
import com.shufe.model.quality.evaluate.QuestionnaireStat;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.system.baseinfo.TeacherService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;


public class EvaluateStatisticsAction extends CalendarRestrictionSupportAction {
    
    TeachTaskService teachTaskService;
    
    TeacherService teacherService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeDepart);
        request.setAttribute("openCollegeList", departmentService
                .getTeachDeparts(getDepartmentIdSeq(request)));
        setCalendarDataRealm(request, hasStdTypeCollege);
        addCollection(request, "departments", baseCodeService.getCodes(Department.class));
        addCollection(request, "questionnaires", utilService.load(Questionnaire.class, "state",
                Boolean.TRUE));
        return forward(request);
    }
    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request,"calendar.id");
        Long deparmentid =getLong(request,"evaluateStatics.department.id");
        String taskSeqNo = get(request,"evaluateStatics.taskSeqNo");
        String courseName = get(request,"evaluateStatics.course.name");
        String teacherName = get(request,"evaluateStatics.teacher.name");
        String teacherCode = get(request,"evaluateStatics.teacher.code");
        EntityQuery entityQuery = new EntityQuery(EvaluateStatics.class,"evaluateStatics");
        entityQuery.add(new Condition("evaluateStatics.calendar.id="+calendarId));
        if(null!=deparmentid){
            entityQuery.add(new Condition("evaluateStatics.depart.id="+deparmentid));
        }
        if(null!=taskSeqNo&&!"".equals(taskSeqNo)){
            entityQuery.add(new Condition("evaluateStatics.taskSeqNo='"+taskSeqNo+"'"));
        }
        if(null!=courseName&&!"".equals(courseName)){
            entityQuery.add(new Condition("evaluateStatics.course.name='"+courseName+"'"));
        }
        if(null!=teacherName&&!"".equals(teacherName)){
            entityQuery.add(new Condition("evaluateStatics.teacher.name='"+teacherName+"'"));
        }
        if(null!=teacherCode&&!"".equals(teacherCode)){
            entityQuery.add(new Condition("evaluateStatics.teacher.code='"+teacherCode+"'"));
        }
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.setOrders(OrderUtils.parser(get(request, "orderBy")));
        Collection list = utilService.search(entityQuery);
        addCollection(request, "list", list);
        return forward(request);
    }
    
    public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "evaluateResult.teachCalendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        EntityQuery query = new EntityQuery(EvaluateStatics.class,"evaluateStatics");
        query.add(new Condition("evaluateStatics.calendar.id="+calendarId));
        Collection reMoveList = utilService.search(query);
        utilService.remove(reMoveList);
        EntityQuery entityQuery = new EntityQuery(EvaluateResult.class,"evaluateResult");
        entityQuery.add(new Condition("evaluateResult.teachCalendar.id="+calendarId));
        entityQuery.groupBy("evaluateResult.task.id,evaluateResult.teacher.id");
        entityQuery.setSelect("select evaluateResult.task.id,evaluateResult.teacher.id");
        Collection list = utilService.search(entityQuery);
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object[] evaluateResult = (Object[])iter.next();
            Long jxrwid = (Long)evaluateResult[0];
            Long teacherid = (Long)evaluateResult[1];
            TeachTask teachtask= teachTaskService.getTeachTask(jxrwid);
            Teacher teacher = teacherService.getTeacherById(teacherid);
            EvaluateStatics evaluateResultO = new EvaluateStatics();
            evaluateResultO.setTask(teachtask);
            evaluateResultO.setTaskSeqNo(teachtask.getSeqNo());
            evaluateResultO.setDepart(teachtask.getArrangeInfo().getTeachDepart());
            evaluateResultO.setTeacher(teacher);
            evaluateResultO.setCalendar(calendar);
            evaluateResultO.setCourse(teachtask.getCourse());
            evaluateResultO.setStdType(teachtask.getCalendar().getStudentType());
            
            EntityQuery queryA = new EntityQuery(QuestionResult.class,"qt");
            queryA.setSelect("select count(*)");
            queryA.add(new Condition("qt.option.id=85"));
            queryA.add(new Condition("qt.result.task.id ="+jxrwid));
            queryA.add(new Condition("qt.result.teacher.id ="+teacherid));
            List listA = (List) utilService.search(queryA);
            Long countA = new Long(0);
            if(listA.size()!=0){
                countA = (Long) listA.get(0);
            }
            evaluateResultO.setContA(countA);
            EntityQuery queryB = new EntityQuery(QuestionResult.class,"qt");
            queryB.setSelect("select count(*)");
            queryB.add(new Condition("qt.option.id=84"));
            queryB.add(new Condition("qt.result.task.id ="+jxrwid));
            queryB.add(new Condition("qt.result.teacher.id ="+teacherid));
            List listB = (List) utilService.search(queryB);
            Long countB = new Long(0);
            if(listB.size()!=0){
                countB = (Long) listB.get(0);
            }
            evaluateResultO.setContB(countB);
            EntityQuery queryC = new EntityQuery(QuestionResult.class,"qt");
            queryC.setSelect("select count(*)");
            queryC.add(new Condition("qt.option.id=83"));
            queryC.add(new Condition("qt.result.task.id ="+jxrwid));
            queryC.add(new Condition("qt.result.teacher.id ="+teacherid));
            List listC = (List) utilService.search(queryC);
            Long countC = new Long(0);
            if(listC.size()!=0){
                countC = (Long) listC.get(0);
            }
            evaluateResultO.setContC(countC);
            EntityQuery queryD = new EntityQuery(QuestionResult.class,"qt");
            queryD.setSelect("select count(*)");
            queryD.add(new Condition("qt.option.id=82"));
            queryD.add(new Condition("qt.result.task.id ="+jxrwid));
            queryD.add(new Condition("qt.result.teacher.id ="+teacherid));
            List listD = (List) utilService.search(queryD);
            Long countD = new Long(0);
            if(listD.size()!=0){
                countD = (Long) listD.get(0);
            }
            evaluateResultO.setContD(countD);
            EntityQuery queryE = new EntityQuery(QuestionResult.class,"qt");
            queryE.setSelect("select count(*)");
            queryE.add(new Condition("qt.option.id=81"));
            queryE.add(new Condition("qt.result.task.id ="+jxrwid));
            queryE.add(new Condition("qt.result.teacher.id ="+teacherid));
            List listE = (List) utilService.search(queryE);
            Long countE = new Long(0);;
            if(listE.size()!=0){
                countE = (Long) listE.get(0);
            }
            evaluateResultO.setContE(countE);
            EntityQuery queryScore = new EntityQuery(QuestionnaireStat.class,"qt");
            queryScore.setSelect("select qt.score");
            queryScore.add(new Condition("qt.task.id ="+jxrwid));
            queryScore.add(new Condition("qt.teacher.id ="+teacherid));
            List queryScoreList = (List) utilService.search(queryScore);
            Float score = new Float(0);
            if(queryScoreList.size()!=0){
                score = (Float) queryScoreList.get(0);
            }
            evaluateResultO.setScore(score);
            EntityQuery queryAumtCont = new EntityQuery(QuestionnaireStat.class,"qt");
            queryAumtCont.setSelect("select qt.validTickets");
            queryAumtCont.add(new Condition("qt.task.id ="+jxrwid));
            queryAumtCont.add(new Condition("qt.teacher.id ="+teacherid));
            List listAumtCont = (List) utilService.search(queryAumtCont);
            Integer aumtCont = null;
            if(listAumtCont.size()!=0){
                aumtCont = (Integer)listAumtCont.get(0);
            }
            evaluateResultO.setAumtCont(aumtCont);
            utilService.saveOrUpdate(evaluateResultO);
        }
        return forward(request);
    }

    
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }

}

//$Id: AnnotateAdminAction.java,v 1.14 2006/12/19 05:02:29 cwx Exp $
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
 * chenweixiong              2006-11-7         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.annotate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.annotate.Annotate;
import com.shufe.model.degree.thesis.annotate.BookModelForExport;
import com.shufe.model.degree.thesis.annotate.EvaluateIdea;
import com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook;
import com.shufe.model.degree.thesis.answer.AnswerTeam;
import com.shufe.model.degree.thesis.topicOpen.TopicOpen;
import com.shufe.model.std.Student;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.service.degree.thesis.annotate.AnnotateService;
import com.shufe.util.DataRealmUtils;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

public class AnnotateAdminAction extends RestrictionSupportAction {
    
    private AnnotateService annotateService;
    
    private ThesisManageService thesisManageService;
    
    /**
     * 加载论文评阅首页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 根据条件查询学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String hasWrite = get(request, "hasWrite");
        
        EntityQuery query = new EntityQuery(ThesisManage.class, "thesisManage");
        List stdConditions = QueryRequestSupport.extractConditions(request, Student.class,
                "student", "student.type.id,student.department.id");
        for (Iterator it = stdConditions.iterator(); it.hasNext();) {
            Condition condition = (Condition) it.next();
            String fieldPath = condition.getContent();
            condition.setContent(StringUtils
                    .replace(fieldPath, "student.", "thesisManage.student."));
        }
        query.getConditions().addAll(stdConditions);
        DataRealmUtils.addDataRealms(query, new String[] { "thesisManage.student.type.id",
                "thesisManage.student.department.id" }, restrictionHelper.getDataRealmsWith(
                getLong(request, "student.type.id"), request));
        query.add(new Condition("thesisManage.annotate is "
                + (StringUtils.equals(hasWrite, "true") ? "not" : "") + " null"));
        List bookConditions = QueryRequestSupport.extractConditions(request,
                ThesisAnnotateBook.class, "annotateBook", null);
        if (!CollectionUtils.isEmpty(bookConditions)) {
            query.join("thesisManage.annotate.annotateBooks", "annotateBook");
            query.getConditions().addAll(bookConditions);
        }
        Date finishOnStart = RequestUtils.getDate(request, "finishOnStart");
        if (null != finishOnStart) {
            query.add(new Condition("thesisManage.annotate.finishOn >= (:finishOnStart)",
                    finishOnStart));
        }
        Date finishOnEnd = RequestUtils.getDate(request, "finishOnEnd");
        if (null != finishOnEnd) {
            query
                    .add(new Condition("thesisManage.annotate.finishOn <= (:finishOnEnd)",
                            finishOnEnd));
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "studentPage", utilService.search(query));
        
        Calendar calendar = Calendar.getInstance();
        request.setAttribute("year", String.valueOf(calendar.get(Calendar.YEAR)).substring(2, 4));
        request.setAttribute("hasWrite", hasWrite);
        request.setAttribute("flag", "admin");
        return forward(request, "stdList");
    }
    
    /**
     * 展示所有的论文评阅书的列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doAnnotateBooks(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(ThesisAnnotateBook.class, "annotateBook");
        populateConditions(request, query);
        List stdConditions = QueryRequestSupport.extractConditions(request, Student.class,
                "student", "student.type.id,student.department.id");
        for (Iterator it = stdConditions.iterator(); it.hasNext();) {
            Condition condition = (Condition) it.next();
            String fieldPath = condition.getContent();
            condition.setContent(StringUtils.replace(fieldPath, "student.",
                    "annotateBook.thesisManage.student."));
        }
        query.getConditions().addAll(stdConditions);
        String isHasMark = get(request, "isHasMark");
        DataRealmUtils.addDataRealms(query, new String[] {
                "annotateBook.thesisManage.student.type.id",
                "annotateBook.thesisManage.student.department.id" }, restrictionHelper
                .getDataRealmsWith(getLong(request, "student.type.id"), request));
        if (StringUtils.isNotEmpty(isHasMark)) {
            query.add(new Condition("annotateBook.evaluateIdea.mark is "
                    + (StringUtils.equals(isHasMark, "1") ? "not" : "") + " null"));
        }
        Date finishOnStart = RequestUtils.getDate(request, "finishOnStart");
        if (null != finishOnStart) {
            query.add(new Condition(
                    "annotateBook.thesisManage.annotate.finishOn >= (:finishOnStart)",
                    finishOnStart));
        }
        Date finishOnEnd = RequestUtils.getDate(request, "finishOnEnd");
        if (null != finishOnEnd) {
            query.add(new Condition(
                    "annotateBook.thesisManage.annotate.finishOn <= (:finishOnEnd)", finishOnEnd));
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "annotatePagination", utilService.search(query));
        request.setAttribute("flag", "admin");
        return forward(request, "annotateBookList");
    }
    
    /**
     * 录入论文分数
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doInsertIntoMark(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String isInsert = request.getParameter("isInsert");
        String annotateBookIdSeq = request.getParameter("annotateBookIdSeq");
        if ("load".equals(isInsert)) {
            List annotateBooks = utilService.load(ThesisAnnotateBook.class, "id", SeqStringUtil
                    .transformToLong(annotateBookIdSeq));
            request.setAttribute("annotateBooks", annotateBooks);
            request.setAttribute("pageNo", request.getParameter("pageNo"));
            request.setAttribute("pageSize", request.getParameter("pageSize"));
            request.setAttribute("annotateBookIdSeq", annotateBookIdSeq);
            return forward(request, "loadData");
        } else if ("insert".equals(isInsert)) {
            Long[] annotateBookIds = SeqStringUtil.transformToLong(annotateBookIdSeq);
            List bookList = new ArrayList();
            for (int i = 0; i < annotateBookIds.length; i++) {
                ThesisAnnotateBook thesisAnnotateBook = (ThesisAnnotateBook) utilService.load(
                        ThesisAnnotateBook.class, annotateBookIds[i]);
                String mark = request.getParameter("mark" + annotateBookIds[i]);
                EvaluateIdea evaluateIdea = thesisAnnotateBook.getEvaluateIdea();
                if (null == evaluateIdea) {
                    evaluateIdea = new EvaluateIdea();
                }
                if (StringUtils.isNotBlank(mark)) {
                    evaluateIdea.setMark(Float.valueOf(mark));
                }
                String level = request.getParameter("level" + annotateBookIds[i]);
                if (StringUtils.isNotBlank(level)) {
                    evaluateIdea.setLearningLevel(level);
                }
                thesisAnnotateBook.setEvaluateIdea(evaluateIdea);
                String isReply = request.getParameter("isReply" + annotateBookIds[i]);
                if (StringUtils.isNotBlank(isReply)) {
                    thesisAnnotateBook.setIsReply(isReply);
                }
                String avgMark = request.getParameter("avg" + annotateBookIds[i]);
                if (StringUtils.isNotBlank(avgMark)) {
                    Annotate annotate = thesisAnnotateBook.getThesisManage().getAnnotate();
                    annotate.setAvgMark(Float.valueOf(avgMark));
                    utilService.saveOrUpdate(annotate);
                }
                AnswerTeam team = thesisAnnotateBook.getAnswerTem();
                if (null == team) {
                    team = new AnswerTeam();
                }
                team.setName(request.getParameter("evaluateName" + annotateBookIds[i]));
                team.setDepart(request.getParameter("departName" + annotateBookIds[i]));
                thesisAnnotateBook.setAnswerTem(team);
                bookList.add(team);
                bookList.add(thesisAnnotateBook);
            }
            utilService.saveOrUpdate(bookList);
            return redirect(request, "doAnnotateBooks", "info.add.success");
        } else {
            return null;
        }
    }
    
    /**
     * 根据查询条件计算这些学生的平均成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doCalendarAvgMark(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String startNo = request.getParameter("startNo");
        String endNo = request.getParameter("endNo");
        annotateService.batchUpdateAvgMark(startNo, endNo, getDepartmentIdSeq(request),
                getStdTypeIdSeq(request));
        return redirect(request, "doAnnotateBooks", "info.action.success");
    }
    
    /**
     * 计算平均分
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward calcAvgMark(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String annotateBookIdSeq = request.getParameter("annotateBookIdSeq");
        List annotateBooks = utilService.load(ThesisAnnotateBook.class, "id", SeqStringUtil
                .transformToLong(annotateBookIdSeq));
        Set annotateSet = new HashSet();
        for (Iterator iter = annotateBooks.iterator(); iter.hasNext();) {
            ThesisAnnotateBook element = (ThesisAnnotateBook) iter.next();
            annotateSet.add(element.getThesisManage().getAnnotate());
        }
        for (Iterator iter = annotateSet.iterator(); iter.hasNext();) {
            Annotate element = (Annotate) iter.next();
            element.calcAvgScore();
        }
        utilService.saveOrUpdate(annotateSet);
        return redirect(request, "doAnnotateBooks", "info.action.success");
    }
    
    /**
     * 双盲选中。
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doubleChoose(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String thesisManageSeq = request.getParameter("thesisManageSeq");
        Boolean isAffirm = Boolean.valueOf(request.getParameter("isAffirm"));
        annotateService.batchUpdateAnnotateDoubleBlind(isAffirm, thesisManageSeq);
        String message = "";
        if (Boolean.TRUE.equals(isAffirm)) {
            message = "double.choose";
        } else {
            message = "double.notChoose";
        }
        return redirect(request, "doStdList", message);
    }
    
    /**
     * 得到一个学生的论文评阅信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward annotateInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdId = request.getParameter("studentId");
        Student student = (Student) utilService.get(Student.class, Long.valueOf(stdId));
        StudentType stdType = student.getType();
        List thesisManagerList = utilService
                .load(ThesisManage.class, "student.id", student.getId());
        if (thesisManagerList.size() > 0) {
            ThesisManage thesisManage = (ThesisManage) thesisManagerList.get(0);
            if (thesisManage.checkObjectId(thesisManage.getAnnotate())) {
                request.setAttribute("thesisAnnotate", thesisManage.getAnnotate());
            }
            request.setAttribute("thesisManage", thesisManage);
        }
        request.setAttribute("student", student);
        request.setAttribute("flag", "admin");
        if (Degree.MASTER.equals(stdType.getDegree().getId())) {
            request.setAttribute("studentFlag", "master");
            return forward(request, "../annotateInfo");
        } else if (Degree.DOCTOR.equals(stdType.getDegree().getId())) {
            request.setAttribute("studentFlag", "doctor");
            return forward(request, "../annotateInfo");
        } else {
            return forward(request, "error");
        }
    }
    
    /**
     * 初始化论文评阅书
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doInitaliBook(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String prefix = request.getParameter("markYear");
        int teaerNumber = Integer.valueOf(request.getParameter("exportNum")).intValue();
        int suffix = Integer.valueOf(request.getParameter("beginNumber")).intValue();
        int virtualNum = Integer.valueOf(request.getParameter("virtualNum")).intValue();
        String thesisManageIdSeq = request.getParameter("thesisManageIdSeq");
        List thesisManages = utilService.load(ThesisManage.class, "id", SeqStringUtil
                .transformToLong(thesisManageIdSeq));
        List tempAnnotates = new ArrayList();
        for (Iterator iter = thesisManages.iterator(); iter.hasNext();) {
            ThesisManage tempThesis = (ThesisManage) iter.next();
            Annotate annotate = tempThesis.getAnnotate();
            List annotateBooksList = new ArrayList();
            Set bookSet = annotate.getAnnotateBooks();
            annotateBooksList.addAll(bookSet);
            Collections.sort(annotateBooksList);
            for (int i = 0; i < teaerNumber; i++) {
                if (i < annotateBooksList.size()) {
                    ThesisAnnotateBook thesisAnnotateBook = (ThesisAnnotateBook) annotateBooksList
                            .get(i);
                    thesisAnnotateBook.setSerial(getComponetString(prefix, suffix, virtualNum));
                } else {
                    ThesisAnnotateBook thesisAnnotateBook = new ThesisAnnotateBook();
                    thesisAnnotateBook.setSerial(getComponetString(prefix, suffix, virtualNum));
                    thesisAnnotateBook.setThesisManage(tempThesis);
                    EntityUtils.evictEmptyProperty(thesisAnnotateBook);
                    bookSet.add(thesisAnnotateBook);
                }
                suffix++;
            }
            for (int i = teaerNumber; i < annotateBooksList.size(); i++) {
                bookSet.remove(annotateBooksList.get(i));
            }
            utilService.saveOrUpdate(bookSet);
            annotate.setAnnotateBooks(bookSet);
            tempAnnotates.add(annotate);
        }
        utilService.saveOrUpdate(tempAnnotates);
        return redirect(request, "doStdList", "info.action.success");
    }
    
    /**
     * 这个方法主要是组装论文编号 根据给定的前缀和后缀生成一个序号
     * 
     * @param suffix
     * @param validateNumbers
     *            有效位数
     * @return
     */
    public String getComponetString(String prefix, int suffix, int validateNumbers) {
        StringBuffer sbf = new StringBuffer(prefix);
        String suffixS = String.valueOf(suffix);
        int count = validateNumbers - suffixS.length();
        for (int i = 0; i < count; i++) {
            sbf.append("0");
        }
        sbf.append(suffixS);
        return sbf.toString();
    }
    
    /**
     * 填写论文评阅书
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @deprecated //写论文评阅书 去掉 现在不需要这个功能
     * @return
     * @throws Exception
     */
    public ActionForward doWriteBook(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String thesisId = request.getParameter("thesisId");
        ThesisManage thesisManage = (ThesisManage) utilService.load(ThesisManage.class, Long
                .valueOf(thesisId));
        String flag = request.getParameter("book");
        if (StringUtils.isNotEmpty(flag)) {
            initBaseCodes(request, "teacherTitles", TeacherTitle.class);
            request.setAttribute("bookSet", thesisManage.getAnnotate().getAnnotateBooks());
            if (Degree.DOCTOR.equals(thesisManage.getStudent().getType().getDegree().getId())) {
                request.setAttribute("studentFlag", "doctor");
            }
            request.setAttribute("thesisManage", thesisManage);
            request.setAttribute("flag", flag);
            return forward(request, "writeBook");
        }
        int count = Integer.valueOf(request.getParameter("booksNumber")).intValue();// 书的份数。
        String idSeq = request.getParameter("idSeq");
        for (int i = 1; i <= count; i++) {
            if (StringUtils.isNotBlank(idSeq)) {
                int temp = Integer.valueOf(idSeq).intValue();
                i = temp;
            }
            ThesisAnnotateBook thesisAnnotateBook = (ThesisAnnotateBook) RequestUtil.populate(
                    request, ThesisAnnotateBook.class, "annotateBook" + i);
            if (thesisAnnotateBook.getAnswerTem().checkedId()) {
                AnswerTeam answerTeam = (AnswerTeam) utilService.load(AnswerTeam.class,
                        thesisAnnotateBook.getAnswerTem().getId());
                EntityUtils.merge(answerTeam, thesisAnnotateBook.getAnswerTem());
                utilService.saveOrUpdate(answerTeam);
            } else {
                utilService.saveOrUpdate(thesisAnnotateBook.getAnswerTem());
            }
            thesisAnnotateBook.setThesisManage(thesisManage);
            utilService.saveOrUpdate(thesisAnnotateBook);
        }
        if (Degree.DOCTOR.equals(thesisManage.getStudent().getType().getDegree().getId())) {
            Float avgMark = new Float(request.getParameter("avgMark"));
            Annotate annotate = thesisManage.getAnnotate();
            annotate.setAvgMark(avgMark);
            utilService.saveOrUpdate(annotate);
        }
        return redirect(request, "doStdList", "info.action.success");
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        String flag = request.getParameter("flag");
        String stdTypeIdSeq = getStdTypeIdSeq(request);
        String departIdSeq = getDepartmentIdSeq(request);
        if (StringUtils.isNotBlank(flag) && "bookList".equals(flag)) {
            Student student = (Student) populate(request, Student.class);
            ThesisManage thesisManage = new ThesisManage();
            thesisManage.setStudent(student);
            ThesisAnnotateBook thesisAnnotateBook = (ThesisAnnotateBook) RequestUtil.populate(
                    request, ThesisAnnotateBook.class, "annotateBook");
            thesisAnnotateBook.setThesisManage(thesisManage);
            String isHasMark = request.getParameter("isHasMark");
            List tempList = annotateService.getAnnotateBooks(thesisAnnotateBook, departIdSeq,
                    stdTypeIdSeq, isHasMark);
            List returnList = new ArrayList();
            transPortModels(tempList, returnList);
            return returnList;
        } else {
            Student student = (Student) populate(request, Student.class);
            ThesisManage thesisManage = new ThesisManage();
            thesisManage.setStudent(student);
            String serialNo = request.getParameter("serialNo");
            if (StringUtils.isNotBlank(serialNo)) {
                ThesisAnnotateBook book = new ThesisAnnotateBook();
                book.setSerial(serialNo);
                Set bookSet = new HashSet();
                bookSet.add(book);
                thesisManage.getAnnotate().setAnnotateBooks(bookSet);
            }
            
            String isFinish = request.getParameter("isFinish");
            List stds = new ArrayList();
            String notNulls = "";
            String nulls = "";
            if ("true".equals(isFinish)) {
                notNulls += "annotate";
            } else {
                nulls += "annotate";
            }
            stds = thesisManageService.getThesissNullNotNull(thesisManage, departIdSeq,
                    stdTypeIdSeq, notNulls, nulls);
            return stds;
        }
    }
    
    /**
     * 把书类型转换成导出数据的类型
     * 
     * @param bookList
     * @param returnList
     */
    public void transPortModels(List bookList, List returnList) {
        for (Iterator iter = bookList.iterator(); iter.hasNext();) {
            ThesisAnnotateBook book = (ThesisAnnotateBook) iter.next();
            ThesisManage thesisManage = book.getThesisManage();
            BookModelForExport exportModel = new BookModelForExport();
            exportModel.setStudent(thesisManage.getStudent());
            exportModel.setThesiAnnotateBook(book);
            TopicOpen topicOpen = thesisManage.getTopicOpen();
            if (null != topicOpen.getThesisPlan()
                    && StringUtils.isNotBlank(topicOpen.getThesisPlan().getThesisTopicArranged())) {
                exportModel.setThesisTopicOpen(topicOpen.getThesisPlan().getThesisTopicArranged());
            } else if (null != topicOpen.getThesisPlan()
                    && StringUtils.isNotBlank(topicOpen.getThesisTopic())) {
                exportModel.setThesisTopicOpen(topicOpen.getThesisTopic());
            } else {
                exportModel.setThesisTopicOpen("no topic");
            }
            exportModel.setAvgMark(thesisManage.getAnnotate().getAvgMark());
            if ("A".equals(book.getIsReply())) {
                exportModel.setIsPassedName("同意");
            } else if ("M".equals(book.getIsReply())) {
                exportModel.setIsPassedName("修改后答辩");
            } else if ("D".equals(book.getIsReply())) {
                exportModel.setIsPassedName("不同意");
            } else {
                exportModel.setIsPassedName(null);
            }
            if (null != book.getEvaluateIdea()) {
                exportModel.setMark(book.getEvaluateIdea().getMark());
                if ("A".equals(book.getEvaluateIdea().getLearningLevel())) {
                    exportModel.setDegreeLevelName("优秀");
                } else if ("B".equals(book.getEvaluateIdea().getLearningLevel())) {
                    exportModel.setDegreeLevelName("良好");
                } else if ("C".equals(book.getEvaluateIdea().getLearningLevel())) {
                    exportModel.setDegreeLevelName("一般");
                } else if ("D".equals(book.getEvaluateIdea().getLearningLevel())) {
                    exportModel.setDegreeLevelName("不及格");
                } else {
                    exportModel.setDegreeLevelName(null);
                }
            } else {
                exportModel.setMark(null);
            }
            returnList.add(exportModel);
        }
    }
    
    /**
     * 取消学生的论文评阅完成确认
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancelAffirm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String thesisManageIdSeq = request.getParameter("thesisManageIdSeq");
        List thesisManages = utilService.load(ThesisManage.class, "id", SeqStringUtil
                .transformToLong(thesisManageIdSeq));
        List annotates = new ArrayList();
        for (Iterator iter = thesisManages.iterator(); iter.hasNext();) {
            ThesisManage element = (ThesisManage) iter.next();
            annotates.add(element.getAnnotate());
        }
        for (Iterator iter = annotates.iterator(); iter.hasNext();) {
            Annotate element = (Annotate) iter.next();
            element.setFinishOn(null);
        }
        utilService.saveOrUpdate(annotates);
        return redirect(request, "doStdList", "info.action.success");
    }
    
    /**
     * 删除选择学生的编号
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doDeleteNo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String thesisManageIdSeq = request.getParameter("thesisManageIdSeq");
        List thesisManages = utilService.load(ThesisManage.class, "id", SeqStringUtil
                .transformToLong(thesisManageIdSeq));
        List annotates = new ArrayList();
        for (Iterator iter = thesisManages.iterator(); iter.hasNext();) {
            ThesisManage element = (ThesisManage) iter.next();
            Set annotateBooks = element.getAnnotate().getAnnotateBooks();
            if (null != annotateBooks) {
                annotateBooks.clear();
                annotates.add(element.getAnnotate());
            }
        }
        utilService.saveOrUpdate(annotates);
        annotateService.batchUpdateDeleteBookNo(thesisManageIdSeq);
        return redirect(request, "doStdList", "info.action.success");
    }
    
    /**
     * @param annotateService
     *            The annotateService to set.
     */
    public void setAnnotateService(AnnotateService annotateService) {
        this.annotateService = annotateService;
    }
    
    /**
     * @param thesisManageService
     *            The thesisManageService to set.
     */
    public void setThesisManageService(ThesisManageService thesisManageService) {
        this.thesisManageService = thesisManageService;
    }
}

//$Id: CreditConstraintServiceImpl.java,v 1.14 2006/12/20 05:48:50 duanth Exp $
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
 * chaostone             2005-12-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.election.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.math.NumberRange;

import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.limit.SinglePage;
import com.shufe.dao.course.election.CreditConstraintDAO;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.course.election.CreditConstraint;
import com.shufe.model.course.election.CreditInitParams;
import com.shufe.model.course.election.SpecialityCreditConstraint;
import com.shufe.model.course.election.StdCreditConstraint;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachCommon;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.OnCampusTimeNotFoundException;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.BasicService;
import com.shufe.service.OutputObserver;
import com.shufe.service.course.election.CreditConstraintInitMessage;
import com.shufe.service.course.election.CreditConstraintService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.DataRealmLimit;
import com.shufe.web.OutputMessage;
import com.shufe.web.OutputProcessObserver;

/**
 * 选课学分限制服务实现
 * 
 * @author chaostone 2005-12-11
 */
public class CreditConstraintServiceImpl extends BasicService implements CreditConstraintService {
    
    private CreditConstraintDAO creditDAO;
    
    private TeachCalendarService calendarService;
    
    private TeachPlanService teachPlanService;
    
    public Collection getStdCreditConstraints(List conditions, DataRealmLimit limit, List sortList) {
        if (null != limit && null != limit.getPageLimit() && limit.getPageLimit().isValid()) {
            return creditDAO.getStdCreditConstraints(conditions, limit, sortList);
        } else {
            if (null != limit && null != limit.getPageLimit()) {
                return new SinglePage(limit.getPageLimit().getPageNo(), limit.getPageLimit()
                        .getPageSize(), 0, Collections.EMPTY_LIST);
            } else
                return Collections.EMPTY_LIST;
        }
    }
    
    /**
     * @see com.shufe.service.course.election.CreditConstraintService#getPersonCreditConstraints(java.lang.Long)
     */
    public StdCreditConstraint getPersonCreditConstraints(Long constraintId) {
        StdCreditConstraint constraint = (StdCreditConstraint) utilDao.get(
                StdCreditConstraint.class, constraintId);
        // i don't want to destroy stdCreditConstraint's state in session
        StdCreditConstraint rsConstaint = new StdCreditConstraint();
        rsConstaint.setStd(null);
        rsConstaint.setCalendar(null);
        rsConstaint.setElectedCredit(constraint.getElectedCredit());
        rsConstaint.setAwardedCredit(constraint.getAwardedCredit());
        rsConstaint.setMaxCredit(constraint.getMaxCredit());
        rsConstaint.setMinCredit(constraint.getMinCredit());
        return rsConstaint;
    }
    
    /**
     * @see com.shufe.service.course.election.CreditConstraintService#updateSpecialityCreditConstraints(java.io.Serializable[],
     *      java.lang.Float, boolean, boolean)
     */
    public void updateSpecialityCreditConstraints(Serializable[] ids, Float value, boolean isCeil,
            boolean isUpdateStdCascade) {
        List constraints = creditDAO.getCreditConstraints(ids, SpecialityCreditConstraint.class);
        updateCreditConstraints(constraints, value, isCeil);
        if (isUpdateStdCascade) {
            List stdCreditConstraints = creditDAO.getStdCreditConstraintsBySpeciality(ids);
            updateCreditConstraints(stdCreditConstraints, value, isCeil);
        }
    }
    
    /**
     * 查询学生学分限制
     * 
     * @param stdId
     * @param calendars
     * @return
     */
    public List getStdCreditConstraints(Student std, Collection calendars) {
        if (calendars.isEmpty())
            return Collections.EMPTY_LIST;
        else
            return creditDAO.getStdCreditConstraints(std, calendars);
    }
    
    public StdCreditConstraint getStdCreditConstraint(Long stdId, TeachCalendar calendar) {
        return creditDAO.getStdCreditConstraint(stdId, calendar);
    }
    
    /**
     * @see com.shufe.service.course.election.CreditConstraintService#updateSpecialityCreditConstraints(java.io.Serializable[],
     *      java.lang.Float, java.lang.Float, boolean)
     */
    public void updateSpecialityCreditConstraints(Serializable[] ids, Float ceil, Float floor,
            boolean isUpdateStdCascade) {
        List constraints = creditDAO.getCreditConstraints(ids, SpecialityCreditConstraint.class);
        updateCreditConstraints(constraints, ceil, floor);
        if (isUpdateStdCascade) {
            List stdCreditConstraints = creditDAO.getStdCreditConstraintsBySpeciality(ids);
            updateCreditConstraints(stdCreditConstraints, ceil, floor);
        }
    }
    
    /**
     * @see com.shufe.service.course.election.CreditConstraintService#updateStdCreditConstraints(java.io.Serializable[],
     *      java.lang.Float, boolean)
     */
    public void updateStdCreditConstraints(Serializable[] ids, Float value, boolean isCeil) {
        List constraints = creditDAO.getCreditConstraints(ids, StdCreditConstraint.class);
        updateCreditConstraints(constraints, value, isCeil);
    }
    
    /**
     * @see com.shufe.service.course.election.CreditConstraintService#updateStdCreditConstraints(java.io.Serializable[],
     *      java.lang.Float, java.lang.Float)
     */
    public void updateStdCreditConstraints(Serializable[] ids, Float ceil, Float floor) {
        List constraints = creditDAO.getCreditConstraints(ids, StdCreditConstraint.class);
        this.updateCreditConstraints(constraints, ceil, floor);
    }
    
    /**
     * 更新学分限制
     * 
     * @param constraintClass
     * @param ids
     * @param value
     * @param isCeil
     */
    private void updateCreditConstraints(Collection constraints, Float value, boolean isCeil) {
        for (Iterator iter = constraints.iterator(); iter.hasNext();) {
            CreditConstraint constraint = (CreditConstraint) iter.next();
            if (isCeil)
                constraint.setMaxCredit(value);
            else
                constraint.setMinCredit(value);
            creditDAO.update(constraint);
        }
    }
    
    /**
     * 更新选课学分上下限
     * 
     * @param constraints
     * @param ceil
     * @param floor
     */
    private void updateCreditConstraints(Collection constraints, Float ceil, Float floor) {
        for (Iterator iter = constraints.iterator(); iter.hasNext();) {
            CreditConstraint constraint = (CreditConstraint) iter.next();
            constraint.setMaxCredit(ceil);
            constraint.setMinCredit(floor);
            creditDAO.update(constraint);
        }
    }
    
    /**
     * 选课专业学分初始化
     */
    public void initCreditConstraint(TeachCommon common, TeachCalendar calendar,
            OutputProcessObserver observer) {
        doInitCreditConstraint(teachPlanService.getTeachPlans(new TeachPlan(common), null, null,
                null, null), calendar, observer);
    }
    
    /**
     * 选课专业学分初始化
     */
    public void initCreditConstraint(String stdTypes, String departs, TeachCalendar calendar,
            OutputProcessObserver observer) {
        TeachPlan plan = new TeachPlan();
        DataRealmLimit limit = new DataRealmLimit(stdTypes, departs);
        limit.setPageLimit(null);
        List sortList = new ArrayList();
        sortList.add(new Order("enrollTurn"));
        sortList.add(new Order("department"));
        Collection plans = teachPlanService
                .getTeachPlans(plan, limit, sortList, Boolean.TRUE, null);
        observer.notifyStart(observer.messageOf("info.creditInit.management") + "(" + plans.size()
                + ")", plans.size(), null);
        doInitCreditConstraint(plans, calendar, observer);
    }
    
    /**
     * 
     * @param plans
     * @param calendar
     * @param observer
     */
    private void doInitCreditConstraint(Collection plans, TeachCalendar calendar,
            OutputProcessObserver observer) {
        
        // 计算培养计划的学期到指定学期之间的间隔
        TermCalculator termCalc = new TermCalculator(calendarService, calendar);
        for (Iterator iter = plans.iterator(); iter.hasNext();) {
            TeachPlan plan = (TeachPlan) iter.next();
            debug("CreditConstraintInit:plan id:" + plan.getId());
            TeachCommon common = new TeachCommon(plan);
            common.setCalendar(calendar);
            SpecialityCreditConstraint constraint = null;
            
            // 防止重复
            List constraints = creditDAO.getSpecialityCreditConstraints(common);
            if (!constraints.isEmpty()) {
                constraint = (SpecialityCreditConstraint) creditDAO.getSpecialityCreditConstraints(
                        common).get(0);
            }
            if (null != constraint) {
                if (null != observer) {
                    observer.outputNotify(OutputObserver.warnning, new CreditConstraintInitMessage(
                            "error.specialityCreditConstraint.existed", common, constraint
                                    .getMaxCredit()), true);
                    continue;
                }
            }
            // 计算学期数
            NumberRange termRange = new NumberRange(new Integer(1), plan.getTermsCount());
            int term = 0;
            try {
                term = termCalc.getTerm(plan.getStdType(), plan.getEnrollTurn(), Boolean.TRUE);
            } catch (OnCampusTimeNotFoundException e) {
                observer.outputNotify(OutputObserver.error, new OutputMessage("", e.getMessage()),
                        true);
                continue;
            }
            if (!termRange.containsInteger(term)) {
                if (null != observer)
                    observer.outputNotify(OutputObserver.warnning,
                            new CreditConstraintInitMessage(
                                    "error.specialityCreditConstraint.expiry",
                                    new TeachCommon(plan), null), true);
                continue;
            }
            // 计算计划对应学期的学分
            Float credit = teachPlanService.getCreditByTerm(plan, term);
            if (null == credit)
                credit = new Float(0);
            if (null != observer)
                observer.outputNotify(OutputObserver.good, new CreditConstraintInitMessage(
                        "info.init.credit.for", common, credit), true);
            creditDAO.initCreditConstraint(common, calendar, credit);
            
        }
    }
    
    /**
     * 选课专业学分限制初始化
     */
    public void initCreditConstraint(Long[] specialityConstraintIds, TeachCalendar calendar) {
        List constraints = this.creditDAO.getCreditConstraints(specialityConstraintIds,
                SpecialityCreditConstraint.class);
        for (Iterator iter = constraints.iterator(); iter.hasNext();) {
            SpecialityCreditConstraint constraint = (SpecialityCreditConstraint) iter.next();
            TermCalculator termCalc = new TermCalculator(calendarService, constraint.getCalendar());
            TeachCommon common = new TeachCommon();
            common.setEnrollTurn(constraint.getEnrollTurn());
            common.setStdType(constraint.getStdType());
            common.setDepart(constraint.getDepart());
            common.setSpeciality(constraint.getSpeciality());
            common.setAspect(constraint.getAspect());
            common.setCalendar(constraint.getCalendar());
            Collection plans = teachPlanService.getTeachPlans(new TeachPlan(common), null, null,
                    null, null);
            if (plans.isEmpty()) {
                debug("not found speciality plan for " + common);
                continue;
            }
            TeachPlan plan = (TeachPlan) plans.iterator().next();
            NumberRange termRange = new NumberRange(new Integer(1), plan.getTermsCount());
            int term = 0;
            try {
                term = termCalc.getTerm(plan.getStdType(), plan.getEnrollTurn(), Boolean.TRUE);
            } catch (Exception e) {
                info("doCreditInit:" + e.getMessage());
                continue;
            }
            if (!termRange.containsInteger(term)) {
                debug("specialityCreditConstraint.expiry for " + new TeachCommon(plan));
                continue;
            }
            Float credit = teachPlanService.getCreditByTerm(plan, term);
            creditDAO.initCreditConstraint(common, calendar, credit);
        }
    }
    
    public void statStdCreditConstraint(Long[] stdCreditConstraintIds, CreditInitParams params) {
        List creditConstraints = creditDAO.getCreditConstraints(stdCreditConstraintIds,
                StdCreditConstraint.class);
        for (Iterator iter = creditConstraints.iterator(); iter.hasNext();) {
            StdCreditConstraint constraint = (StdCreditConstraint) iter.next();
            creditDAO.statStdCreditConstraint(constraint.getStd().getId(), params);
        }
    }
    
    public void statStdCreditConstraint(StdCreditConstraint constraint, CreditInitParams params,
            OutputProcessObserver observer) {
        List stdIds = creditDAO.getStdIdHasConstraint(constraint);
        observer.notifyStart("学生选课学分/奖励学分初始化(" + stdIds.size() + ")个学生", stdIds.size(), null);
        for (Iterator iter = stdIds.iterator(); iter.hasNext();) {
            Long stdId = (Long) iter.next();
            StringBuffer buffer = new StringBuffer("[" + stdId + "]");
            StdCreditConstraint sc = creditDAO.statStdCreditConstraint(stdId, params);
            if (null != sc.getElectedCredit()) {
                buffer.append(" 已选:").append(sc.getElectedCredit());
            }
            if (null != sc.getAwardedCredit()) {
                buffer.append(" 奖励:").append(sc.getAwardedCredit());
            }
            if (null != sc.getGPA()) {
                buffer.append(" 平均绩点:").append(sc.getGPA());
            }
            observer.outputNotify(OutputObserver.good, new OutputMessage("", buffer.toString()),
                    true);
        }
    }
    
    public void batchAddCredit(Collection stdIds, CreditConstraint credit) {
        creditDAO.batchAddCredit(stdIds, credit);
    }
    
    public void batchAddCredit(Student std, DataRealm realm, CreditConstraint credit) {
        creditDAO.batchAddCredit(std, realm, credit);
    }
    
    public Collection getStdWithoutCredit(Student std, TeachCalendar calendar,
            DataRealmLimit limit, List orderList) {
        return creditDAO.getStdWithoutCredit(std, calendar, limit, orderList);
    }
    
    /**
     * @see com.shufe.service.course.election.CreditConstraintService#setCreditDAO(com.shufe.dao.course.election.CreditConstraintDAO)
     */
    public void setCreditDAO(CreditConstraintDAO creditDAO) {
        this.creditDAO = creditDAO;
    }
    
    public void setCalendarService(TeachCalendarService calendarService) {
        this.calendarService = calendarService;
    }
    
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
}

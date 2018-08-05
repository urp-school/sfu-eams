//$Id: ElectStdScopeServiceImpl.java,v 1.5 2006/12/30 01:28:21 duanth Exp $
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
 * chaostone             2005-12-5         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.election.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.dao.course.election.ElectStdScopeDAO;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.model.course.election.ElectInitParams;
import com.shufe.model.course.election.ElectStdScope;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.service.BasicService;
import com.shufe.service.OutputObserver;
import com.shufe.service.course.election.ElectStdScopeService;
import com.shufe.web.OutputMessage;
import com.shufe.web.OutputProcessObserver;

/**
 * @author chaostone 2005-12-10
 */
public class ElectStdScopeServiceImpl extends BasicService implements ElectStdScopeService {
    
    private TeachTaskDAO taskDAO;
    
    private ElectStdScopeDAO electStdScopeDAO;
    
    public void batchUpdateTasksElectInfo(Collection tasks, ElectInitParams setting,
            OutputProcessObserver observer) {
        if (null != observer) {
            observer
                    .notifyStart(observer
                            .messageOf("info.scope.setting", new Integer(tasks.size())), tasks
                            .size(), null);
        }
        Map scopeMap = new HashMap();
        
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            task.getElectInfo().setIsElectable(new Boolean(setting.getIsElectable()));
            task.getTeachClass().setGender(setting.getGender()); // 男女生
            if (setting.isRemoveExistedScope()) {
                task.getElectInfo().getElectScopes().clear();
            }
            // 同一课程代码共享范围
            if (0 != setting.getAddScopeByAdminClass()) {
                int shareCondition = setting.getShareScopeCondition();
                List cons = new ArrayList();
                cons.add(new Condition("task.course.id=" + task.getCourse().getId()));
                cons.add(new Condition("task.calendar.id=" + task.getCalendar().getId()));
                ElectStdScope reserved = new ElectStdScope();
                if ((shareCondition & ElectStdScope.ENROLLYEAR_MARK) > 0) {
                    if (StringUtils.isEmpty(task.getTeachClass().getEnrollTurn())) {
                        cons.add(new Condition("task.teachClass.enrollTurn is null"));
                    } else {
                        cons.add(new Condition("task.teachClass.enrollTurn='"
                                + task.getTeachClass().getEnrollTurn() + "'"));
                        reserved.setEnrollTurns("," + task.getTeachClass().getEnrollTurn() + ",");
                    }
                }
                if ((shareCondition & ElectStdScope.STDTYPE_MARK) > 0) {
                    if (null == task.getTeachClass().getStdType()) {
                        cons.add(new Condition("task.teachClass.stdType is null"));
                    } else {
                        cons.add(new Condition("task.teachClass.stdType.id="
                                + task.getTeachClass().getStdType().getId()));
                        reserved.setStdTypeIds("," + task.getTeachClass().getStdType().getId()
                                + ",");
                    }
                }
                if ((shareCondition & ElectStdScope.DEPART_MARK) > 0) {
                    if (null == task.getTeachClass().getDepart()) {
                        cons.add(new Condition("task.teachClass.depart is null"));
                    } else {
                        cons.add(new Condition("task.teachClass.depart.id="
                                + task.getTeachClass().getDepart().getId()));
                        reserved.setDepartIds("," + task.getTeachClass().getDepart().getId() + ",");
                    }
                }
                if ((shareCondition & ElectStdScope.SPECIALITY_MARK) > 0) {
                    if (null == task.getTeachClass().getSpeciality()) {
                        cons.add(new Condition("task.teachClass.speciality is null"));
                    } else {
                        cons.add(new Condition("task.teachClass.speciality.id="
                                + task.getTeachClass().getSpeciality().getId()));
                        reserved.setSpecialityIds(","
                                + task.getTeachClass().getSpeciality().getId() + ",");
                    }
                }
                if ((shareCondition & ElectStdScope.ASPECT_MARK) > 0) {
                    if (null == task.getTeachClass().getAspect()) {
                        cons.add(new Condition("task.teachClass.aspect is null"));
                    } else {
                        cons.add(new Condition("task.teachClass.aspect.id="
                                + task.getTeachClass().getAspect().getId()));
                        reserved.setAspectIds("," + task.getTeachClass().getAspect().getId() + ",");
                    }
                }
                
                ElectStdScope scope = null;
                StringBuffer sb = new StringBuffer();
                for (Iterator iterator = cons.iterator(); iterator.hasNext();) {
                    Condition con = (Condition) iterator.next();
                    sb.append(con.getContent());
                }
                if (scopeMap.containsKey(sb.toString())) {
                    scope = (ElectStdScope) scopeMap.get(sb.toString());
                    scope = (ElectStdScope) scope.clone();
                } else {
                    EntityQuery query = new EntityQuery(TeachTask.class, "task");
                    query.getConditions().addAll(cons);
                    query.setSelect("distinct task.teachClass.adminClasses");
                    scope = new ElectStdScope(utilService.search(query), setting
                            .getAddScopeByAdminClass());
                }
                if (!scope.isEmptyScope()) {
                    scope.merge(reserved);
                    if (StringUtils.isEmpty(scope.getStdTypeIds())) {
                        scope.setStdTypeIds("," + task.getTeachClass().getStdType().getId() + ",");
                    }
                    if (StringUtils.isEmpty(scope.getDepartIds())) {
                        scope.setDepartIds("," + task.getTeachClass().getDepart().getId() + ",");
                    }
                    addScope(scope, task);
                }
            }
            if (setting.isInitByTeachClass() != 0) {
                ElectStdScope scope = new ElectStdScope(task.getTeachClass(), setting
                        .isInitByTeachClass());
                addScope(scope, task);
            }
            if (StringUtils.equals(setting.getBatchAction(), ElectInitParams.ADD)) {
                addScope((ElectStdScope) setting.getScope().clone(), task);
            }
            if (StringUtils.equals(setting.getBatchAction(), ElectInitParams.REMOVE)) {
                removeScope((ElectStdScope) setting.getScope().clone(), task);
            }
            if (StringUtils.equals(setting.getBatchAction(), ElectInitParams.MERGE)) {
                for (Iterator iterator = task.getElectInfo().getElectScopes().iterator(); iterator
                        .hasNext();) {
                    ElectStdScope element = (ElectStdScope) iterator.next();
                    element.merge(setting.getScope());
                }
            }
            if (setting.isUpdateStdMaxCountByPlanCount()) {
                MaxStdCountByPlanPolicy policy = new MaxStdCountByPlanPolicy();
                policy.updateStdCountLimited(task);
            }
            if (setting.isUpdateStdMaxCountByRoomConfig()) {
                MaxStdCountByRoomConfigPolicy policy = new MaxStdCountByRoomConfigPolicy();
                policy.updateStdCountLimited(task);
            }
            if (setting.isUpdateMinWithRoomConfigAndPlanCount()) {
                MaxStdCountByPlanPolicy policy1 = new MaxStdCountByPlanPolicy();
                policy1.updateStdCountLimited(task);
                MaxStdCountByRoomConfigPolicy policy2 = new MaxStdCountByRoomConfigPolicy();
                policy2.updateStdCountLimited(task);
                task.getElectInfo().setMaxStdCount(
                        new Integer(Math.min(policy1.getStdCountLimited().intValue(), policy2
                                .getStdCountLimited().intValue())));
            }
            if (setting.isUpdateRoomCapacityOrMinStdCount()) {
                MaxStdCountByPlanPolicy policy1 = new MaxStdCountByPlanPolicy();
                policy1.updateStdCountLimited(task);
                RoomCapacityOrMinStdCount policy2 = new RoomCapacityOrMinStdCount();
                policy2.updateStdCountLimited(task);
                task.getElectInfo().setMaxStdCount(
                        new Integer(Math.min(policy1.getStdCountLimited().intValue(), policy2
                                .getStdCountLimited().intValue())));
            }
            if (setting.isSettingMinStdCount()) {
                task.getElectInfo().setMinStdCount(setting.getMinStdCount());
            }
            if (setting.isSettingMaxStdCount()) {
                task.getElectInfo().setMaxStdCount(setting.getMaxStdCount());
            }
            if (null != setting.getIsCancelable()) {
                task.getElectInfo().setIsCancelable(setting.getIsCancelable());
            }
            if (null != setting.getStdCountValue()) {
                switch (setting.getStdCountValue().intValue()) {
                    case 0:
                        Collection rooms = task.getArrangeInfo().getCourseArrangedRooms();
                        int minCount = -1;
                        if (StringUtils.contains("," + setting.getStdCountSwitchs() + ",", ",0,")) {
                            for (Iterator it = rooms.iterator(); it.hasNext();) {
                                Classroom room = (Classroom) it.next();
                                minCount = (minCount != -1 ? Math.min(minCount, room
                                        .getCapacityOfCourse().intValue()) : room
                                        .getCapacityOfCourse().intValue());
                            }
                        }
                        if (StringUtils.contains("," + setting.getStdCountSwitchs() + ",", ",1,")) {
                            minCount = (minCount != -1 ? Math.min(minCount, task.getTeachClass()
                                    .getPlanStdCount().intValue()) : task.getTeachClass()
                                    .getPlanStdCount().intValue());
                        }
                        if (StringUtils.contains("," + setting.getStdCountSwitchs() + ",", ",2,")) {
                            for (Iterator it = rooms.iterator(); it.hasNext();) {
                                Classroom room = (Classroom) it.next();
                                minCount = (minCount != -1 ? Math.min(minCount, room.getCapacity()
                                        .intValue()) : room.getCapacity().intValue());
                            }
                        }
                        if (minCount == -1) {
                            minCount = 0;
                        }
                        task.getElectInfo().setMaxStdCount(new Integer(minCount));
                        break;
                    
                    case 1:
                        task.getElectInfo().setMaxStdCount(setting.getStdCount());
                        break;
                }
            }
            
            if (null != observer) {
                observer.outputNotify(OutputObserver.good, new OutputMessage("", "["
                        + task.getSeqNo() + "]" + task.getCourse().getName()), true);
            }
        }
        taskDAO.updateTasks(tasks);
    }
    
    /**
     * 向教学任务添加选课范围（重复不添加）
     * 
     * @param scope
     * @param task
     */
    private void addScope(ElectStdScope scope, TeachTask task) {
        boolean findDefaultScope = false;
        for (Iterator iterator = task.getElectInfo().getElectScopes().iterator(); iterator
                .hasNext();) {
            ElectStdScope element = (ElectStdScope) iterator.next();
            if (element.isSame(scope)) {
                findDefaultScope = true;
                break;
            }
        }
        if (!findDefaultScope) {
            scope.setTask(task);
            task.getElectInfo().getElectScopes().add(scope);
        }
    }
    
    public List getMaxStdCountNotEqualRoomCapacity(Long calendarId) {
        return electStdScopeDAO.getMaxStdCountNotEqualRoomCapacity(calendarId);
    }
    
    /**
     * 向教学任务添加选课范围（重复不添加）
     * 
     * @param scope
     * @param task
     */
    private void removeScope(ElectStdScope scope, TeachTask task) {
        ElectStdScope target = null;
        for (Iterator iterator = task.getElectInfo().getElectScopes().iterator(); iterator
                .hasNext();) {
            ElectStdScope element = (ElectStdScope) iterator.next();
            if (element.isSame(scope)) {
                target = element;
                break;
            }
        }
        if (null != target) {
            task.getElectInfo().getElectScopes().remove(target);
        }
    }
    
    /**
     * @param taskDAO
     *            The taskDAO to set.
     */
    public void setTeachTaskDAO(TeachTaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }
    
    public void setElectStdScopeDAO(ElectStdScopeDAO electStdScopeDAO) {
        this.electStdScopeDAO = electStdScopeDAO;
    }
    
}

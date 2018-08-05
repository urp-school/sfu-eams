//$Id: TeacherOtherOption.java,v 1.1 2009-2-18 下午01:09:31 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2009-2-18             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.task;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.helper.BaseInfoSearchHelper;
import com.shufe.web.helper.TeachTaskSearchHelper;

/**
 * @author zhouqi
 * 
 */
public class TeacherOtherOptionAction extends RestrictionSupportAction {
    
    protected TeachTaskSearchHelper teachTaskSearchHelper;
    
    protected BaseInfoSearchHelper baseInfoSearchHelper;
    
    public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
        this.baseInfoSearchHelper = baseInfoSearchHelper;
    }
    
    /**
     * 早课老师住宿凭证
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery queryTask = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
        queryTask.setLimit(null);
        
        EntityQuery query = new EntityQuery(CourseActivity.class, "activity");
        query.join("activity.task", "task");
        query.add(queryTask.getConditions());
        query.add(new Condition("activity.time.startUnit = 1"));
        query.add(new Condition("activity.teacher is not null"));
//        Collection activities = utilService.search(query);
        
//        Collection results = new ArrayList();
//        for (Iterator it = activities.iterator(); it.hasNext();) {
//            CourseActivity activity = (CourseActivity) it.next();
//            TempObject tObject = new TempObject();
//            int weekCount = activity.getActivityLastWeek() - activity.getActivityFirstWeek() + 1;
//            tObject.setCount(new Integer(weekCount));
//            tObject.setActivity(activity);
//            tObject.setIsEven(new Boolean(weekCount % 2 == 0));
//            for (int i = 0; i < weekCount / 2; i++) {
//                tObject.getList().add("1");
//            }
//            results.add(tObject);
//        }
//        if (CollectionUtils.isEmpty(results)) {
//            TempObject tObject = new TempObject();
//            tObject.setCount(new Integer(0));
//            results.add(tObject);
//        }
        
        return utilService.search(query);
    }
    
    public void setTeachTaskSearchHelper(TeachTaskSearchHelper teachTaskSearchHelper) {
        this.teachTaskSearchHelper = teachTaskSearchHelper;
    }
}

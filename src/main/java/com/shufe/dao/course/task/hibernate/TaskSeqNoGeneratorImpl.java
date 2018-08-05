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
 * chaostone             2006-6-3            Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.task.hibernate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.task.TaskSeqNoGenerator;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 课程序号生成器,这部分代码暂时生成4位的连续课程序号(字符型).
 * 
 * @author chaostone
 * 
 */

public class TaskSeqNoGeneratorImpl extends BasicHibernateDAO implements TaskSeqNoGenerator {
    
    public final static String initSeqNo = "0001";
    
    public void genTaskSeqNo(TeachTask task) {
        if (!StringUtils.isEmpty(task.getSeqNo())) {
            return;
        }
        synchronized (this) {
            String hql = "select seqNo from TeachTask  where calendar.id="
                    + task.getCalendar().getId() + " order by seqNo";
            List taskSeqNos = getSession().createQuery(hql).list();
            int newSeqNo = 0;
            for (Iterator iter = taskSeqNos.iterator(); iter.hasNext();) {
                String seqNo = (String) iter.next();
                if (NumberUtils.toInt(seqNo) - newSeqNo >= 2) {
                    break;
                } else {
                    newSeqNo = NumberUtils.toInt(seqNo);
                }
            }
            newSeqNo++;
            task
                    .setSeqNo(StringUtils.repeat("0", 4 - String.valueOf(newSeqNo).length())
                            + newSeqNo);
        }
    }
    
    public void genTaskSeqNos(TeachTask[] tasks) {
        genTaskSeqNos(Arrays.asList(tasks));
    }
    
    /**
     * 分配一组序号
     * 
     * @param tasks
     */
    public void genTaskSeqNos(Collection tasks) {
        Map calendarTasks = new MultiValueMap();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (StringUtils.isEmpty(task.getSeqNo()))
                calendarTasks.put(task.getCalendar(), task);
        }
        for (Iterator iter = calendarTasks.keySet().iterator(); iter.hasNext();) {
            TeachCalendar calendar = (TeachCalendar) iter.next();
            genTaskSeqNos(calendar, (Collection) calendarTasks.get(calendar));
        }
    }
    
    private void genTaskSeqNos(TeachCalendar calendar, Collection tasks) {
        if (tasks.isEmpty())
            return;
        synchronized (this) {
            String hql = "select seqNo from TeachTask  where calendar.id=" + calendar.getId()
                    + " order by seqNo";
            List allSeqNos = getSession().createQuery(hql).list();
            int newSeqNo = 0;
            int allocated = 0;
            Iterator taskIter = tasks.iterator();
            for (Iterator iter = allSeqNos.iterator(); iter.hasNext();) {
                String seqNo = (String) iter.next();
                // 中间有空隙
                if ((NumberUtils.toInt(seqNo) - newSeqNo >= 2)) {
                    int gap = NumberUtils.toInt(seqNo) - newSeqNo - 1;
                    for (int i = 0; i < gap; i++) {
                        allocated++;
                        newSeqNo++;
                        TeachTask task = (TeachTask) taskIter.next();
                        task.setSeqNo(StringUtils
                                .repeat("0", 4 - String.valueOf(newSeqNo).length())
                                + newSeqNo);
                        if (allocated >= tasks.size())
                            break;
                    }
                    if (allocated >= tasks.size())
                        break;
                }
                newSeqNo = NumberUtils.toInt(seqNo);
            }
            // 剩余末尾的序号
            while (allocated < tasks.size()) {
                newSeqNo++;
                allocated++;
                TeachTask task = (TeachTask) taskIter.next();
                task.setSeqNo(StringUtils.repeat("0", 4 - String.valueOf(newSeqNo).length())
                        + newSeqNo);
            }
        }
    }
}

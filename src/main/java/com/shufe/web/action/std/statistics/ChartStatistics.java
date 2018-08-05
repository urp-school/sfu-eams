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

package com.shufe.web.action.std.statistics;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.data.general.DefaultPieDataset;

import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.util.stat.GeneralDatasetProducer;
import com.shufe.util.DataRealmLimit;

/**
 * 学籍信息图表统计类
 */
public class ChartStatistics extends GeneralStudentStatisticsAction {
    
    /**
     * 学生统计图表菜单页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward studentInfoStatForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        DataRealmLimit dataRealmLimit = getDataRealmLimit(request);
        initStudentStatisticsBar(dataRealmLimit);
        Results.addObject("studentStateList", utilService.loadAll(StudentState.class));
        return forward(request);
    }
    
    /**
     * 学生统计图表结果页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward studentInfoStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        DataRealmLimit dataRealmLimit = getDataRealmLimit(request);
        Collection studentInfoStatList = null;
        Student std = (Student) populate(request, Student.class, "student");
        String adminClasssId1String = request.getParameter("adminClasssId1");
        String adminClasssId2String = request.getParameter("adminClasssId2");
        Set adminClassSet = new HashSet();
        
        if (StringUtils.isNotEmpty(adminClasssId1String)) {
            AdminClass adminClass = new AdminClass(Long.valueOf(adminClasssId1String));
            adminClassSet.add(adminClass);
        }
        if (StringUtils.isNotEmpty(adminClasssId2String)) {
            AdminClass adminClass = new AdminClass(Long.valueOf(adminClasssId2String));
            adminClassSet.add(adminClass);
        }
        std.setAdminClasses(adminClassSet);
        String statElement = get(request, "statElement");
        String[] groupArray = StringUtils.split(statElement, ",");
        request.setAttribute("groupArray", groupArray);
        Boolean isExactly = new Boolean(false);
        studentInfoStatList = studentService.studentInfoStat(std, dataRealmLimit, groupArray,
                isExactly);
        addCollection(request, "studentInfoStatList", studentInfoStatList);
        DefaultPieDataset dataSet = new DefaultPieDataset();
        if (ArrayUtils.isEmpty(groupArray)) {
            for (Iterator iter = studentInfoStatList.iterator(); iter.hasNext();) {
                Integer element = (Integer) iter.next();
                dataSet.setValue("全部", element.intValue());
            }
        } else {
            for (Iterator iter = studentInfoStatList.iterator(); iter.hasNext();) {
                Object[] element = (Object[]) iter.next();
                String key = "";
                for (int i = 0; i < groupArray.length; i++) {
                    if (element[i] instanceof String) {
                        key += " " + element[i];
                    } else if (element[i] != null) {
                        try {
                            key += " " + PropertyUtils.getProperty(element[i], "name");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
                dataSet.setValue(key, ((Integer) element[groupArray.length]).intValue());
            }
        }
        request.setAttribute("generalDatasetProducer", new GeneralDatasetProducer("dataSet",
                dataSet));
        return forward(request);
    }
    
}

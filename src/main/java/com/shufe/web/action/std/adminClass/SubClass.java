//$Id: A.java,v 1.1 2007-12-4 下午08:04:22 zhouqi Exp $
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
 * zhouqi              2007-12-4         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.adminClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;

public class SubClass {
    
    AdminClass adminClass;
    
    List stds = new ArrayList();
    
    int beginIndex;
    
    int stdCount;
    
    int boyCount;
    
    int girlCount;
    
    /**
     * @param adminClass
     * @param boyCount
     * @param girlCount
     */
    public SubClass(AdminClass adminClass, int stdCount, int boyCount, int girlCount) {
        super();
        this.adminClass = adminClass;
        this.stdCount = stdCount;
        this.boyCount = boyCount;
        this.girlCount = girlCount;
    }
    
    /**
     * 制作模板报表时使用。
     * 
     * @param index
     *            form 1
     * @return
     */
    public String indexNo(int index) {
        if (index < stds.size()) {
            return beginIndex + (index + 1) + "";
        } else {
            return "";
        }
    }
    
    /**
     * @param adminClass
     * @param stdPerClass
     * @param isOnCompus
     * @return
     */
    public static List buildClasses(AdminClass adminClass, int stdPerClass, Boolean isOnCompus) {
        List subClasses = new ArrayList();
        List stds = null;
        if (null == isOnCompus) {
            stds = new ArrayList(adminClass.getStudents());
        } else {
            stds = (List) CollectionUtils.select(adminClass.getStudents(), new Predicate() {
                
                public boolean evaluate(Object arg0) {
                    Student std = (Student) arg0;
                    return std.getState().isActive() && std.getState().isInSchool();
                }
            });
        }
        Collections.sort(stds, new PropertyComparator("code"));
        //
        int boyCount = 0;
        int girlCount = 0;
        int stdCount = stds.size();
        for (Iterator it1 = stds.iterator(); it1.hasNext();) {
            Student std = (Student) it1.next();
            Gender gender = std.getBasicInfo().getGender();
            if (gender != null) {
                if (gender.getId().longValue() == 1) {
                    boyCount++;
                } else if (gender.getId().longValue() == 2) {
                    girlCount++;
                }
            }
        }
        // split
        int begin = 0;
        while (stds.size() - begin > stdPerClass) {
            SubClass subClass = new SubClass(adminClass, stdCount, boyCount, girlCount);
            int end = begin + stdPerClass;
            if (stds.size() < begin + stdPerClass) {
                end = subClass.stds.size();
            }
            subClass.stds = (new ArrayList(stds.subList(begin, end)));
            subClass.beginIndex = begin;
            subClasses.add(subClass);
            begin += stdPerClass;
        }
        // last
        for (Iterator iter = subClasses.iterator(); iter.hasNext();) {
            SubClass element = (SubClass) iter.next();
            stds.removeAll(element.stds);
        }
        SubClass last = new SubClass(adminClass, stdCount, boyCount, girlCount);
        last.beginIndex = begin;
        last.setStds(stds);
        subClasses.add(last);
        return subClasses;
    }
    
    public int getStdCount() {
        return stdCount;
    }
    
    public void setStdCount(int stdCount) {
        this.stdCount = stdCount;
    }
    
    public AdminClass getAdminClass() {
        return adminClass;
    }
    
    public void setAdminClass(AdminClass adminClass) {
        this.adminClass = adminClass;
    }
    
    public int getBeginIndex() {
        return beginIndex;
    }
    
    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }
    
    public int getBoyCount() {
        return boyCount;
    }
    
    public void setBoyCount(int boyCount) {
        this.boyCount = boyCount;
    }
    
    public int getGirlCount() {
        return girlCount;
    }
    
    public void setGirlCount(int girlCount) {
        this.girlCount = girlCount;
    }
    
    public List getStds() {
        return stds;
    }
    
    public void setStds(List stds) {
        this.stds = stds;
    }
    
}

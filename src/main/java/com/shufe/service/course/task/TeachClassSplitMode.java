//$Id: TeachClassSplitMode.java,v 1.4 2006/12/19 10:07:07 duanth Exp $
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
 * chaostone             2005-10-21         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import com.ekingstar.eams.system.basecode.state.Gender;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachClass;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.course.arrange.task.CourseTakeOfClassPredicate;
import com.shufe.service.course.arrange.task.CourseTakeOfGenderPredicate;
import com.shufe.service.course.arrange.task.CourseTakeOfStdNoPredicate;

/**
 * 教学班拆分模式.
 * 
 * <pre>
 *               不负责检查数据参数（TeachClass，num）的合法性.
 *               教学班拆分并不影响要拆分的班级，只是按照要求拆分成指定数目的新的班级.
 *               教学班拆分具有四种模式： 
 *               1)AVG_SHARE   平均分配模式,将所有教学班中的学生(courseTakes)平均分配到指定数目的班级中.
 *               2)KEEP        保留行政班模式,将教学班中的行政班全部保留下来单独作为一个新的行政班. 
 *                             若存在不在任何行政班的学生则分如剩余数目的新教学班中.
 *               3)KEEP_SHARE  保留行政班,并均分不在任何行政班的学生. 
 *               4)CUSTOM_SHARE自定义拆分模式,不保留任何行政的信息,直接将指定数目学生分割到新的教学班中.
 *                             自定义拆分中，各个教学班指定的数目总和应和拆分前教学班的计划人数总和相等.
 *                             可以不和实际的人数相等.
 *               5)GENDER_AWARE区分男女生进行分班,
 *               6)STDNO_AWARE 区分学生学号，对单双号进行拆分
 *               所有的拆分模式中，将按照拆分的顺序在教学班的名字后面添加一个拆分序号.
 * &lt;br&gt;
 *               并实际计算教学班中的实际人数和计划人数.
 *               1)计划人数计算规则：不存在行政班时，计划人数和要拆分的教学班计划人数相等.
 *               存在行政班时，计划人数按照对应的行政班的人数设置(也不进行调节).
 *               2)实际人数计算规则：不存在行政班时，实际人数和要拆分的教学班实际人数相等.
 * 
 * &#064;author chaostone 2005-10-21
 * 
 */
public abstract class TeachClassSplitMode {
    
    /**
     * 拆分模式的名称
     */
    protected String name;
    
    /**
     * 自定义拆分中用到的数目数组
     */
    protected Integer[] stdNums = null;
    
    protected TeachClassSplitMode(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
    
    public void setStdNums(Integer[] stdNums) {
        this.stdNums = stdNums;
    }
    
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    private static Map splitModes = new HashMap();
    
    /**
     * 将教学班对应的行政班全部去除，均分所有的人数
     */
    public static final TeachClassSplitMode AVG_SHARE = new TeachClassSplitMode("avgShare") {
        
        public TeachClass[] splitClass(TeachClass target, int num) {
            List courseTakes = new ArrayList();
            courseTakes.addAll(target.getPossibleCourseTakes());
            Collections.sort(courseTakes);
            target.setStdAssigned(Boolean.TRUE);
            
            TeachClass[] resultClasses = new TeachClass[num];
            int avgCount = target.getPlanStdCount().intValue() / num;
            int lastCount = target.getPlanStdCount().intValue() - avgCount * (num - 1);
            Iterator courseTakeIter = courseTakes.iterator();
            /*------------按照平均数拆分-------------*/
            for (int i = 1; i < num; i++) {
                resultClasses[i - 1] = newTeachClass(target, avgCount, courseTakeIter, i);
            }
            resultClasses[num - 1] = newTeachClass(target, lastCount, courseTakeIter, num);
            return resultClasses;
            
        }
    };
    
    /**
     * 将教学班对应的行政班全部去除，按照指定的数量所有的人数
     */
    public static final TeachClassSplitMode CUSTOM_SHARE = new TeachClassSplitMode("customShare") {
        
        public TeachClass[] splitClass(TeachClass target, int num) {
            List courseTakes = new ArrayList();
            courseTakes.addAll(target.getPossibleCourseTakes());
            Collections.sort(courseTakes);
            target.setStdAssigned(Boolean.TRUE);
            Iterator stdIt = courseTakes.iterator();
            TeachClass[] classes = new TeachClass[num];
            for (int i = 0; i < classes.length; i++) {
                classes[i] = newTeachClass(target, stdNums[i].intValue(), stdIt, i);
            }
            return classes;
        }
        
    };
    
    /**
     * 拆分中保留教学班中对应的所有的行政班，将剩余的人数按照剩余的数目均分.<br>
     * 如果num小于行政班个数，则按照KEEPADMINCLASS_SHARE模式分解;<br>
     * 如果num大于行政班个数，将剩余的人数按照剩余的数目均分.<br>
     * 保留行政班服务中要求行政班一定存在.
     */
    public static final TeachClassSplitMode KEEP = new TeachClassSplitMode("keep") {
        
        public TeachClass[] splitClass(TeachClass target, int num) {
            if (num <= target.getAdminClasses().size()) {
                return KEEP_SHARE.splitClass(target, num);
            } else {
                TeachClass[] classes = new TeachClass[num];
                // 拆分为单独的行政班
                TeachClass[] adminClasses = new TeachClass[0];
                adminClasses = SplitAdminStds(target);
                
                // 再拆分为单独的选课班级
                TeachClass[] electiveClasses = splitStds(target, num - (adminClasses.length),
                        target.getLonelyCourseTakes());
                
                // 合并在一起
                for (int i = 0; i < num; i++) {
                    if (i < adminClasses.length) {
                        classes[i] = adminClasses[i];
                    } else {
                        classes[i] = electiveClasses[num - i - 1];
                    }
                }
                return classes;
            }
        }
    };
    
    /**
     * 保留教学班中对应的所有的行政班.<br>
     * 如果num不大于行政班个数，则忽略num,将剩余的人数均分到行政班对应的教学班中.<br>
     * 如果num大于行政班个数，则按照KEEPADMINCLASS模式分解. 保留行政班服务中要求行政班一定存在. 每个班级的计划人数由原来班级的计划人数
     */
    public static final TeachClassSplitMode KEEP_SHARE = new TeachClassSplitMode("keepShare") {
        
        public TeachClass[] splitClass(TeachClass target, int num) {
            if (num > target.getAdminClasses().size()) {
                return KEEP.splitClass(target, num);
            } else {
                num = target.getAdminClasses().size();
                // 拆分为单独的行政班
                TeachClass[] adminClasses = new TeachClass[0];
                adminClasses = SplitAdminStds(target);
                // 再拆分为单独的选课学生
                Set lonelyTakes = target.getLonelyCourseTakes();
                if (!lonelyTakes.isEmpty()) {
                    TeachClass[] classes = new TeachClass[num];
                    TeachClass[] electiveClasses = splitStds(target, num, lonelyTakes);
                    /**
                     * 合并选课学生班级的实际人数以及计划人数
                     */
                    for (int i = 0; i < num; i++) {
                        classes[i] = adminClasses[i];
                        classes[i].setStdCount(new Integer(adminClasses[i].getStdCount().intValue()
                                + electiveClasses[i].getStdCount().intValue()));
                        
                        classes[i].setPlanStdCount(new Integer(adminClasses[i].getPlanStdCount()
                                .intValue()
                                + electiveClasses[i].getStdCount().intValue()));
                        
                        classes[i].addCourseTakes(electiveClasses[i].getCourseTakes());
                        electiveClasses[i].getCourseTakes().clear();
                    }
                    return classes;
                } else {
                    return adminClasses;
                }
            }
        }
    };
    
    /**
     * 区分性别对男女生进行分班.<br>
     * 按照指定的男女生的班级个数,分别进行分班.<br>
     * 如果实际人数为0,则将拆分前计划人数的平均数，指定为新班级的计划人数.<br>
     */
    public static final TeachClassSplitMode GENDER_AWARE = new TeachClassSplitMode("genderAware") {
        
        public TeachClass[] splitClass(TeachClass target, int num) {
            Set courseTakes = target.getPossibleCourseTakes();
            target.setStdAssigned(Boolean.TRUE);
            Integer avgStdCount = new Integer(target.getPlanStdCount().intValue() / num);
            // 男生班
            Collection maleStds = CollectionUtils.select(courseTakes,
                    new CourseTakeOfGenderPredicate(Gender.MALE));
            target.setPlanStdCount(new Integer(maleStds.size()));
            TeachClass[] maleClasses = splitStds(target, stdNums[0].intValue(), maleStds);
            // 女生班
            Collection femaleStds = CollectionUtils.select(courseTakes,
                    new CourseTakeOfGenderPredicate(Gender.FEMALE));
            target.setPlanStdCount(new Integer(femaleStds.size()));
            TeachClass[] femaleClasses = splitStds(target, stdNums[1].intValue(), femaleStds);
            
            
            for (int i = 0; i < maleClasses.length; i++) {
                if (maleClasses[i].getStdCount().equals(new Integer(0))) {
                    maleClasses[i].setPlanStdCount(avgStdCount);
                }
                maleClasses[i].setName(maleClasses[i].getName() + "男");
            }
            
            for (int i = 0; i < femaleClasses.length; i++) {
                if (femaleClasses[i].getStdCount().equals(new Integer(0))) {
                    femaleClasses[i].setPlanStdCount(avgStdCount);
                }
                femaleClasses[i].setName(femaleClasses[i].getName() + "女");
            }
            
            // 合并
            TeachClass[] classes = (TeachClass[]) ArrayUtils.addAll(maleClasses, femaleClasses);
            
            return classes;
        }
        
    };
    
    /**
     * 按照具体的班级拆分
     */
    public static final TeachClassSplitMode ADMINCLASS_SPLIT = new SimpleAdminClassSplitMode(
            "adminClassSplit");
    
    /**
     * 区分学号单双进行分班.<br>
     * 按照指定的单双学号的的班级个数,分别进行分班.<br>
     * 如果实际人数为0,则将拆分前计划人数的平均数，指定为新班级的计划人数.<br>
     */
    public static final TeachClassSplitMode STDNO_AWARE = new TeachClassSplitMode("stdnoAware") {
        
        public TeachClass[] splitClass(TeachClass target, int num) {
            Set courseTakes = target.getPossibleCourseTakes();
            // target.getCourseTakes().clear();
            target.setStdAssigned(Boolean.TRUE);
            Integer avgStdCount = new Integer(target.getPlanStdCount().intValue() / num);
            // 单号班
            Collection oddNoStds = CollectionUtils.select(courseTakes,
                    new CourseTakeOfStdNoPredicate(true));
            target.setPlanStdCount(new Integer(oddNoStds.size()));
            TeachClass[] oddNoClasses = splitStds(target, stdNums[0].intValue(), oddNoStds);
            // 双号班
            Collection evenNoStds = CollectionUtils.select(courseTakes,
                    new CourseTakeOfStdNoPredicate(false));
            target.setPlanStdCount(new Integer(evenNoStds.size()));
            TeachClass[] evenNoClasses = splitStds(target, stdNums[1].intValue(), evenNoStds);
            // 合并
            TeachClass[] classes = (TeachClass[]) ArrayUtils.addAll(oddNoClasses, evenNoClasses);
            
            for (int i = 0; i < oddNoClasses.length; i++) {
                if (oddNoClasses[i].getStdCount().equals(new Integer(0))) {
                    oddNoClasses[i].setPlanStdCount(avgStdCount);
                }
                oddNoClasses[i].setName(oddNoClasses[i].getName() + "单");
            }
            
            for (int i = 0; i < evenNoClasses.length; i++) {
                if (evenNoClasses[i].getStdCount().equals(new Integer(0))) {
                    evenNoClasses[i].setPlanStdCount(avgStdCount);
                }
                evenNoClasses[i].setName(evenNoClasses[i].getName() + "双");
            }
            return classes;
        }
    };
    
    public abstract TeachClass[] splitClass(TeachClass target, int num);
    
    public static TeachClassSplitMode getMode(String modeName) {
        return (TeachClassSplitMode) splitModes.get(modeName);
    }
    
    /**
     * 将给定的学生拆分到指定数目的选课教学班里.<br>
     * 如果num大于stds集合中的学生数,则所有学生分入到最后一个班中.
     * 
     * @param target
     * @param num
     * @param courseTakes
     * @return
     * @throws Exception
     */
    protected TeachClass[] splitStds(TeachClass target, int num, Collection courseTakes) {
        TeachClass[] resultClasses = new TeachClass[num];
        // int avgCount = courseTakes.size() / num;
        // int lastCount = courseTakes.size() - avgCount * (num - 1);
        int avgCount = target.getPlanStdCount().intValue() / num;
        int lastCount = target.getPlanStdCount().intValue() - avgCount * (num - 1);
        Iterator courseTakeIter = courseTakes.iterator();
        /*------------按照平均数拆分-------------*/
        for (int i = 1; i < num; i++) {
            resultClasses[i - 1] = newTeachClassWithAdminClass(target, avgCount, courseTakeIter, i);
        }
        resultClasses[num - 1] = newTeachClassWithAdminClass(target, lastCount, courseTakeIter, num);
        return resultClasses;
    }
    
    /**
     * 根据指定数量生成一个教学班<br>
     * 依据count更新教学班级中的计划人数和实际人数.<br>
     * 生成的教学班中没有任何行政班级与之关联<br>
     * 
     * @param target
     * @param count
     * @param courseTakeIt
     * @param turn
     * @return
     */
    protected TeachClass newTeachClass(TeachClass target, int count, Iterator courseTakeIt, int turn) {
        TeachClass one = (TeachClass) target.clone();
        one.setName(target.getName() + turn);
        // 计划人数
        one.setPlanStdCount(new Integer(count));
        one.setCourseTakes(new HashSet());
        one.setAdminClasses(null);
        
        for (int j = 0; j < count && courseTakeIt.hasNext(); j++) {
            one.addCourseTake((CourseTake) courseTakeIt.next());
        }
        // 实际人数
        // one.setStdCount(new Integer(one.getCourseTakes().size()));
        return one;
    }
    
    /**
     * 根据指定数量生成一个教学班<br>
     * 依据count更新教学班级中的计划人数和实际人数.<br>
     * 生成的教学班中与原来行政班级关联<br>
     * 
     * @param target
     * @param count
     * @param courseTakeIt
     * @param turn
     * @return
     */
    protected TeachClass newTeachClassWithAdminClass(TeachClass target, int count, Iterator courseTakeIt, int turn) {
        TeachClass one = (TeachClass) target.clone();
        one.setName(target.getName() + turn);
        // 计划人数
        one.setPlanStdCount(new Integer(count));
        one.setCourseTakes(new HashSet());
        Set adminClassSet = new HashSet();
        for (Iterator iter = target.getAdminClasses().iterator(); iter.hasNext();) {
            AdminClass adminClass = (AdminClass) iter.next();
            adminClassSet.add(adminClass);    
        }
        one.setAdminClasses(adminClassSet);
        
        for (int j = 0; j < count && courseTakeIt.hasNext(); j++) {
            one.addCourseTake((CourseTake) courseTakeIt.next());
        }
        // 实际人数
        // one.setStdCount(new Integer(one.getCourseTakes().size()));
        return one;
    }
    
    /**
     * 拆分为原来的行政班<br>
     * 1)指定新的教学班和行政班的联系<br>
     * 2)计划人数=每个班级的实际人数<br>
     * 
     * @param target
     * @return
     * @throws Exception
     */
    protected TeachClass[] SplitAdminStds(TeachClass target) {
        TeachClass[] classes = new TeachClass[target.getAdminClasses().size()];
        int i = 0;
        for (Iterator iter = target.getAdminClasses().iterator(); iter.hasNext();) {
            // 第i个教学班中的行政班级
            AdminClass one = (AdminClass) iter.next();
            classes[i] = (TeachClass) target.clone();
            classes[i].setName(one.getName());
            Set adminClassSet = new HashSet();
            adminClassSet.add(one);
            classes[i].setAdminClasses(adminClassSet);
            /**
             * 如果已经指定了学生,则将该班级的学生分入到教学班内<br>
             * 然后更新班级计划人数和实际人数<br>
             */
            classes[i].addCourseTakes(CollectionUtils.select(target.getCourseTakes(),
                    new CourseTakeOfClassPredicate(one)));
            classes[i].setPlanStdCount(one.getActualStdCount());
            classes[i].setStdCount(new Integer(classes[i].getCourseTakes().size()));
            i++;
        }
        return classes;
    }
    
    static {
        splitModes.put(AVG_SHARE.name, AVG_SHARE);
        splitModes.put(KEEP.name, KEEP);
        splitModes.put(KEEP_SHARE.name, KEEP_SHARE);
        splitModes.put(CUSTOM_SHARE.name, CUSTOM_SHARE);
        splitModes.put(GENDER_AWARE.name, GENDER_AWARE);
        splitModes.put(STDNO_AWARE.name, STDNO_AWARE);
        splitModes.put(ADMINCLASS_SPLIT.name, ADMINCLASS_SPLIT);
    }
}

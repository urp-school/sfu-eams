//$Id: StdImportListener.java,v 1.1 2007-4-5 下午04:03:16 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-4-5         Created
 *  
 ********************************************************************************/

package com.shufe.service.std;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.std.AbroadStudentInfo;
import com.shufe.model.std.BasicInfo;
import com.shufe.model.std.Student;
import com.shufe.model.std.StudentStatusInfo;

/**
 * 学生信息导入监听器。
 * 
 * @author chaostone
 */
public class StdImportListener extends ItemImporterListener {
    
    protected UtilDao utilDao;
    
    protected Map stds = new HashMap();
    
    public StdImportListener(UtilDao utilDao) {
        this.utilDao = utilDao;
    }
    
    public void endTransfer(TransferResult tr) {
        if (!stds.isEmpty()) {
            utilDao.saveOrUpdate(stds.values());
        }
        super.endTransfer(tr);
    }
    
    public void startTransferItem(TransferResult tr) {
        Object[] datas = (Object[]) importer.getCurData();
        if (ValidEntityKeyPredicate.INSTANCE.evaluate(datas[0])) {
            if (stds.containsKey(datas[0])) {
                importer.setCurrent(stds.get(datas[0]));
                //tr.addFailure("学号有重复", datas[0]);
            } else {
                List stds = utilDao.load(Student.class, "code", new Object[] { datas[0] });
                if (stds.size() > 0) {
                    importer.setCurrent(stds.get(0));
                }
            }
        }
    }
    
    public void endTransferItem(TransferResult tr) {
        Student std = (Student) importer.getCurrent();
        int errors = tr.errors();
        if (!checkEnrollTurn(std.getEnrollYear())) {
            tr.addFailure("error.enrollTurn", std.getEnrollYear());
        }
        if (std.getType() == null || std.getDepartment() == null) {
            tr.addFailure("error.parameters.needed", "department or studentType");
        }
        if (StringUtils.isBlank(std.getCode())) {
            tr.addFailure("error.parameters.needed", "code");
        }
        if (null==std.getGender()) {
            tr.addFailure("error.parameters.illegal", "Gender of value is null or error.");
        }
        checkSpecialityAspect(tr, std);
        if (tr.errors() == errors) {
            if (null == std.getStudentStatusInfo()) {
                std.setStudentStatusInfo(new StudentStatusInfo());
            }
            if (!ValidEntityPredicate.INSTANCE.evaluate(std.getState())) {
                std.setState(
                        new StudentState(StudentState.ONCAMPUS));
            }
            if (null == std.getCreateAt()) {
                std.setCreateAt(new Date(System.currentTimeMillis()));
            }
            std.setModifyAt(new Date(System.currentTimeMillis()));
            
            BasicInfo basicInfo = std.getBasicInfo();
            StudentStatusInfo studentStatusInfo = std.getStudentStatusInfo();
            AbroadStudentInfo abroadStudentInfo = std.getAbroadStudentInfo();
            EntityUtils.evictEmptyProperty(std);
            EntityUtils.evictEmptyProperty(basicInfo);
            EntityUtils.evictEmptyProperty(studentStatusInfo);
            EntityUtils.evictEmptyProperty(abroadStudentInfo);
            if (EntityUtils.isEmpty(abroadStudentInfo, true)) {
                abroadStudentInfo = null;
            }
            std.setBasicInfo(basicInfo);
            std.setStudentStatusInfo(studentStatusInfo);
            std.setAbroadStudentInfo(abroadStudentInfo);
            if (stds.size() >= 500) {
                utilDao.saveOrUpdate(stds.values());
                stds.clear();
            }
            stds.put(std.getCode(), std);
        }
    }
    
    /**
     * 判断专业与方向的归属
     * 
     * @param tr
     * @param std
     */
    protected void checkSpecialityAspect(TransferResult tr, Student std) {
        // 专业不属于院系
        /*if (null != std.getFirstMajor() && null != std.getFirstMajor().getId()) {
            if (!std.getFirstMajor().getDepartment().equals(std.getDepartment())) {
                tr.addFailure("error.speciality.deparment.notIn", std.getName() + ":"
                        + std.getFirstMajor().getCode());
            }
        }*/
        // 专业方向不属于专业
        if (null != std.getFirstAspect() && null != std.getFirstAspect().getId()) {
            if (null == std.getFirstMajor()
                    || null != std.getFirstAspect()
                    && !std.getFirstMajor().getId().equals(
                            std.getFirstAspect().getSpeciality().getId())) {
                tr.addFailure("error.aspect.speciality.notWith", std.getName() + ":"
                        + std.getFirstAspect().getCode());
            }
        }
    }
    
    public boolean checkEnrollTurn(String enrollturn) {
        if (StringUtils.isEmpty(enrollturn))
            return false;
        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern = null;
        try {
            pattern = compiler.compile("(\\d{4})[\\-]\\d+");
        } catch (MalformedPatternException e) {
            return false;
        }
        PatternMatcher matcher = new Perl5Matcher();
        return matcher.matches(enrollturn, pattern);
    }
}

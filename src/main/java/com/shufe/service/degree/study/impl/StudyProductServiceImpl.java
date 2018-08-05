//$Id: StudyThesisServiceImpl.java,v 1.1 2007-3-5 13:58:46 Administrator Exp $
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
 * chenweixiong              2007-3-5         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.study.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.model.AbstractEntity;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.degree.study.StudyProductDAO;
import com.shufe.model.degree.study.Literature;
import com.shufe.model.degree.study.LiteratureAward;
import com.shufe.model.degree.study.Project;
import com.shufe.model.degree.study.ProjectAward;
import com.shufe.model.degree.study.StudyAward;
import com.shufe.model.degree.study.StudyMeeting;
import com.shufe.model.degree.study.StudyThesis;
import com.shufe.model.degree.study.StudyThesisAward;
import com.shufe.model.degree.study.ThesisIndex;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.service.BasicService;
import com.shufe.service.degree.study.StudyProductService;
import com.shufe.service.util.stat.StatUtils;

public class StudyProductServiceImpl extends BasicService implements StudyProductService {
    
    public StudyProductDAO studyProductDAO;
    
    /**
     * @param studyProductDAO
     *            The studyProductDAO to set.
     */
    public void setStudyProductDAO(StudyProductDAO studyProductDAO) {
        this.studyProductDAO = studyProductDAO;
    }
    
    /**
     * @see com.shufe.service.degree.study.StudyProductService#getStdPropertyOfProduct(Map,
     *      java.lang.String, java.lang.String)
     */
    public Map getStdPropertyOfProduct(Map studyValueMap, String departIdSeq, String stdTypeIdSeq) {
        Map returnMap = new HashMap();
        // 论文
        StudyThesis thesis = new StudyThesis();
        EntityUtils.populate(studyValueMap, thesis);
        List statisThesises = studyProductDAO.getStdPropertyProduct(thesis, departIdSeq,
                stdTypeIdSeq, "student.department,student.type,publicationLevel", true);
        setValueToMap(statisThesises, returnMap, "thesis");
        
        // 项目
        Project project = new Project();
        EntityUtils.populate(studyValueMap, project);
        List statisticProject = studyProductDAO.getStdPropertyProduct(project, departIdSeq,
                stdTypeIdSeq, "student.department,student.type,projectType", false);
        setValueToMap(statisticProject, returnMap, "project");
        
        // 著作
        Literature literature = new Literature();
        EntityUtils.populate(studyValueMap, literature);
        List statisticLiterature = studyProductDAO.getStdPropertyProduct(literature, departIdSeq,
                stdTypeIdSeq, "student.department,student.type,literatureType", false);
        setValueToMap(statisticLiterature, returnMap, "literature");
        return returnMap;
    }
    
    public void setValueToMap(List studyProducts, Map value, String diffStr) {
        for (Iterator iter = studyProducts.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            int i = 0;
            if ("thesis".equals(diffStr)) {
                i = 1;
            }
            Integer productNum = (Integer) element[i];
            Department depart = (Department) element[i + 1];
            StudentType type = (StudentType) element[i + 2];
            AbstractEntity entity = (AbstractEntity) element[i + 3];
            String departKey = depart.getId() + "_" + type.getId();
            if ("thesis".equals(diffStr)) {
                Integer stdNum = (Integer) element[0];
                StatUtils.setValueToMap("department", depart, "set", value);
                StatUtils.setValueToMap(depart.getId() + "_stdType", type, "set", value);
                StatUtils.setValueToMap(departKey + "_std", stdNum, "integer", value);
                StatUtils.setValueToMap("0_0_std", stdNum, "integer", value);
                StatUtils.setValueToMap(departKey + "_total", productNum, "integer", value);
                StatUtils.setValueToMap("0_0_total", productNum, "integer", value);
            }
            departKey = departKey + "_" + diffStr + entity.getEntityId();
            StatUtils.setValueToMap(departKey, productNum, "integer", value);
            StatUtils.setValueToMap("0_0_" + diffStr + entity.getEntityId(), productNum, "integer",
                    value);
        }
    }
    
    /**
     * @see com.shufe.service.degree.study.StudyProductService#getStudyProductPagi(java.lang.Object,
     *      java.lang.String, java.lang.String, int, int)
     */
    public Pagination getStudyProductPagi(Object studyProduct, String departIdSeq,
            String stdTypeSeq, int pageNo, int pageSize) {
        return studyProductDAO.getStudyProductPagi(studyProduct, departIdSeq, stdTypeSeq, pageNo,
                pageSize);
    }
    
    /**
     * @see com.shufe.service.degree.study.StudyProductService#getStudyProducts(java.lang.Object,
     *      java.lang.String, java.lang.String)
     */
    public List getStudyProducts(Object studyProduct, String departIdSeq, String stdTypeSeq) {
        return studyProductDAO.getStudyProducts(studyProduct, departIdSeq, stdTypeSeq);
    }
    
    public void batchUpdate(String entityName, String[] properties, Object[] values, String idSeq) {
        studyProductDAO.batchUpdate(entityName, properties, values, idSeq);
    }
    
    public void batchDelete(String entityName, String idSeq) {
        studyProductDAO.batchDelete(entityName, idSeq);
    }
    
    /**
     * 根据学生的非业务主键id 得到这个学生所对应的教学成果的信息表
     * 
     * @see com.shufe.service.degree.study.StudyProductService#getStudyProducts(java.lang.Long,
     *      Boolean, Boolean)
     */
    public Map getStudyProducts(Long stdId, Boolean isNeedList, Boolean isPassed) {
        Map studyResults = new HashMap();
        Set awardSet = new HashSet();
        // 论文获奖
        Map valueMap = new HashMap();
        valueMap.put("student.id", stdId);
        if (null != isPassed) {
            valueMap.put("isPassCheck", Boolean.TRUE);
        }
        Map studyMap = new HashMap();
        studyMap.putAll(valueMap);
        List thesisAwards = utilService.load(StudyThesisAward.class, studyMap);
        awardSet.addAll(thesisAwards);
        // 著作获奖
        studyMap.clear();
        studyMap.putAll(valueMap);
        List literatureAwards = utilService.load(LiteratureAward.class, studyMap);
        awardSet.addAll(literatureAwards);
        // 项目获奖
        studyMap.clear();
        studyMap.putAll(valueMap);
        List projectAwards = utilService.load(ProjectAward.class, studyMap);
        awardSet.addAll(projectAwards);
        for (Iterator iter = awardSet.iterator(); iter.hasNext();) {
            StudyAward studyAward = (StudyAward) iter.next();
            StatUtils.setValueToMap("awards" + studyAward.getType().getId(), new Integer(1),
                    "integer", studyResults);
        }
        // 学生科研论文
        studyMap.clear();
        studyMap.putAll(valueMap);
        List studyThesiss = utilService.load(StudyThesis.class, studyMap);
        for (Iterator iter = studyThesiss.iterator(); iter.hasNext();) {
            StudyThesis studyThesis = (StudyThesis) iter.next();
            if (studyThesis.getPublicationLevel().getId().toString().startsWith("1")) {
                StatUtils.setValueToMap("abroad", new Integer(1), "integer", studyResults);
            } else {
                StatUtils.setValueToMap("inside", new Integer(1), "integer", studyResults);
            }
            for (Iterator iterator = studyThesis.getIndexes().iterator(); iterator.hasNext();) {
                ThesisIndex thesisIndex = (ThesisIndex) iterator.next();
                StatUtils.setValueToMap("index" + thesisIndex.getThesisIndexType().getId(),
                        new Integer(1), "integer", studyResults);
            }
        }
        // 学生科研会议
        studyMap.clear();
        studyMap.putAll(valueMap);
        List studyMeetings = utilService.load(StudyMeeting.class, studyMap);
        for (Iterator iter = studyMeetings.iterator(); iter.hasNext();) {
            StudyMeeting studyMeeting = (StudyMeeting) iter.next();
            StatUtils.setValueToMap("meeting" + studyMeeting.getMeetingType().getId(), new Integer(
                    1), "integer", studyResults);
        }
        // 学生科研著作
        studyMap.clear();
        studyMap.putAll(valueMap);
        List literatures = utilService.load(Literature.class, studyMap);
        for (Iterator iter = literatures.iterator(); iter.hasNext();) {
            Literature literature = (Literature) iter.next();
            StatUtils.setValueToMap("literature" + literature.getLiteratureType().getId(),
                    new Integer(1), "integer", studyResults);
        }
        List projects = utilService.load(Project.class, valueMap);
        if (isNeedList.booleanValue()) {
            studyResults.put("thesisAwards", thesisAwards);
            studyResults.put("literatureAwards", literatureAwards);
            studyResults.put("projectAwards", projectAwards);
            studyResults.put("studyThesiss", studyThesiss);
            studyResults.put("studyMeetings", studyMeetings);
            studyResults.put("literatures", literatures);
            studyResults.put("projects", projects);
        }
        return studyResults;
    }
}

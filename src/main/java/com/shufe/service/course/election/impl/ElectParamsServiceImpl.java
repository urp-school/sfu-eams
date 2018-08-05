//$Id: ElectParamsServiceImpl.java,v 1.1 2006/08/02 00:53:05 duanth Exp $
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

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.dao.course.election.ElectParamsDAO;
import com.shufe.model.course.election.ElectParams;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.election.ElectParamsService;

public class ElectParamsServiceImpl extends BasicService implements ElectParamsService {
    
    private ElectParamsDAO electParamsDAO;
    
    /**
     * @see com.shufe.service.course.election.ElectParamsService#getElectParams(java.io.Serializable)
     */
    public ElectParams getElectParams(Serializable id) {
        return (ElectParams) electParamsDAO.load(ElectParams.class, id);
    }
    
    /**
     * @see com.shufe.service.course.election.ElectParamsService#getElectParams(com.shufe.model.course.election.ElectParams)
     */
    public List getElectParams(ElectParams params) {
        return electParamsDAO.getElectParams(params);
    }
    
    /**
     * @see com.shufe.service.course.election.ElectParamsService#saveElectParams(com.shufe.model.course.election.ElectParams)
     */
    public void saveElectParams(ElectParams params) throws PojoExistException {
        if (null == params) {
            return;
        }
        params.padTime();
        if (!params.isPO()) {
            ElectParams example = ElectParams.getEmptyParams();
            example.setCalendar(params.getCalendar());
            example.setTurn(params.getTurn());
            List existed = getElectParams(example);
            for (Iterator iter = existed.iterator(); iter.hasNext();) {
                ElectParams oneParams = (ElectParams) iter.next();
                if (!CollectionUtils.intersection(params.getEnrollTurns(),
                        oneParams.getEnrollTurns()).isEmpty()
                        && !CollectionUtils.intersection(params.getDeparts(),
                                oneParams.getDeparts()).isEmpty()
                        && !CollectionUtils.intersection(params.getStdTypes(),
                                oneParams.getStdTypes()).isEmpty()
                        && oneParams.isTimeCollision(params)) {
                    throw new PojoExistException("error.electParams.existed");
                }
            }
        }
        electParamsDAO.saveElectParams(params);
    }
    
    public ElectParams getAvailElectParams(Student std) {
        List existed = electParamsDAO.getAvailElectParams(std);
        // 取时间最近的选择参数
        PropertyComparator pc = new PropertyComparator("startDate");
        Collections.sort(existed, pc);
        return existed.isEmpty() ? null : (ElectParams) existed.get(0);
    }
    
    /**
     * 
     */
    public List getElectParams(String stdTypeIds, TeachCalendar calendar) {
        if (StringUtils.isNotEmpty(stdTypeIds) && null != calendar.getId()) {
            return electParamsDAO.getElectParams(SeqStringUtil.transformToLong(stdTypeIds),
                    calendar);
        } else
            return Collections.EMPTY_LIST;
    }
    
    /**
     * @see com.shufe.service.course.election.ElectParamsService#setElectParamsDAO(com.shufe.dao.course.election.ElectParamsDAO)
     */
    public void setElectParamsDAO(ElectParamsDAO paramsDAO) {
        this.electParamsDAO = paramsDAO;
    }
    
}

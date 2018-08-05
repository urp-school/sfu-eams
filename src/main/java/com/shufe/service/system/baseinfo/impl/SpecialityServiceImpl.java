//$Id: SpecialityServiceImpl.java,v 1.2 2006/08/17 12:26:37 duanth Exp $
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
 * chaostone             2005-9-15         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo.impl;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.dao.system.baseinfo.SpecialityDAO;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.service.BasicService;
import com.shufe.service.system.baseinfo.SpecialityService;

public class SpecialityServiceImpl extends BasicService implements SpecialityService {
    
    private SpecialityDAO specialityDAO = null;
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityService#setSpecialityDAO(com.shufe.dao.system.baseinfo.SpecialityDAO)
     */
    public void setSpecialityDAO(SpecialityDAO specialityDAO) {
        this.specialityDAO = specialityDAO;
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityService#getSpeciality(java.lang.Long)
     */
    public Speciality getSpeciality(Long id) {
        try {
            return specialityDAO.getSpeciality(id);
        } catch (ObjectRetrievalFailureException e) {
            return null;
        }
    }
    
    public Speciality getSpeciality(String code) {
        EntityQuery query = new EntityQuery(Speciality.class, "speciality");
        query.add(new Condition("speciality.code=:code", code));
        List rs = (List) utilDao.search(query);
        if (rs.isEmpty())
            return null;
        else
            return (Speciality) rs.get(0);
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityService#getSpecialities()
     */
    public List getSpecialities() {
        return specialityDAO.getSpecialities();
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityService#getSpecialities(com.shufe.model.system.baseinfo.Speciality,
     *      int, int)
     */
    public Pagination getSpecialities(Speciality speciality, int pageNo, int pageSize) {
        return specialityDAO.getSpecialities(speciality, pageNo, pageSize);
    }
    
    public Pagination getSpecialities(Speciality speciality, String stdTypeIdSeq,
            String departIdSeq, int pageNo, int pageSize) {
        if (StringUtils.isEmpty(stdTypeIdSeq) || StringUtils.isEmpty(departIdSeq) || pageNo < 1
                || pageSize < 1)
            return new Pagination(new Result(0, Collections.EMPTY_LIST));
        else
            return specialityDAO.getSpecialities(speciality, SeqStringUtil
                    .transformToLong(stdTypeIdSeq), SeqStringUtil.transformToLong(departIdSeq),
                    pageNo, pageSize);
    }
    
    public List getSpecialities(Speciality speciality, String stdTypeIdSeq, String departIdSeq) {
        if (StringUtils.isEmpty(stdTypeIdSeq) || StringUtils.isEmpty(departIdSeq))
            return Collections.EMPTY_LIST;
        else
            return specialityDAO.getSpecialities(speciality, SeqStringUtil
                    .transformToLong(stdTypeIdSeq), SeqStringUtil.transformToLong(departIdSeq));
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityService#getAllSpecialities(com.shufe.model.system.baseinfo.Speciality,
     *      int, int)
     */
    public Pagination getAllSpecialities(Speciality speciality, int pageNo, int pageSize) {
        return specialityDAO.getAllSpecialities(speciality, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityService#getSpecialities(com.shufe.model.system.baseinfo.Speciality)
     */
    public List getSpecialities(Speciality speciality) {
        return specialityDAO.getSpecialities(speciality);
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityService#removeSpeciality(java.lang.String)
     */
    public void removeSpeciality(Long id) {
        if (null == id || "".equals(id))
            return;
        try {
            specialityDAO.removeSpeciality(id);
        } catch (PojoNotExistException e) {
            return;
        }
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityService#saveSpeciality(com.shufe.model.system.baseinfo.Speciality)
     */
    public void saveOrUpdate(Speciality speciality) throws PojoExistException {
        try {
            if (!speciality.isPO()) {
                speciality.setCreateAt(new Date(System.currentTimeMillis()));
            }
            speciality.setModifyAt(new Date(System.currentTimeMillis()));
            EntityUtils.evictEmptyProperty(speciality);
            specialityDAO.saveOrUpdate(speciality);
        } catch (DataIntegrityViolationException e) {
            throw new PojoExistException("speciality already exits:" + speciality);
        } catch (ConstraintViolationException e) {
            throw new PojoExistException("speciality already exits:" + speciality);
        }
    }
    
    public List getUserSpecialitiesByDepartment(String departmentIds) {
        specialityDAO.enbleFilter("filterSpecialityByDepartment", new String[] { "departmentIds" },
                new Object[] { "," + departmentIds + "," });
        return getSpecialities((Speciality) null);
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityService#getSpecialitiesByDepartmentIds(java.lang.String)
     */
    public List getSpecialitiesByDepartmentIds(String departmentIds) {
        if (StringUtils.isEmpty(departmentIds))
            return null;
        else
            return specialityDAO.getSpecialitiesByDepartmentIds(SeqStringUtil
                    .transformToLong(departmentIds));
    }
    
    public Collection getSpecialities(MajorType majorType) {
        return getSpecialities(Boolean.TRUE, new MajorType(MajorType.FIRST));
    }
    
    public Collection getSpecialities(Boolean state, MajorType majorType) {
        EntityQuery query = new EntityQuery(Speciality.class, "speciality");
        query.add(new Condition("speciality.state = :state", null == state ? Boolean.TRUE : state));
        query.add(new Condition("speciality.majorType.id = (:majorTypeId)",
                (null == majorType || null == majorType.getId()) ? MajorType.FIRST : majorType
                        .getId()));
        return utilService.search(query);
    }
}

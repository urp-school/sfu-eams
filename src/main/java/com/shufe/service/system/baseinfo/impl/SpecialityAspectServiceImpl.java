//$Id: SpecialityAspectServiceImpl.java,v 1.1 2006/08/02 00:53:09 duanth Exp $
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.dao.system.baseinfo.SpecialityAspectDAO;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.service.BasicService;
import com.shufe.service.system.baseinfo.SpecialityAspectService;

public class SpecialityAspectServiceImpl extends BasicService implements SpecialityAspectService {
    
    private SpecialityAspectDAO specialityDirectionDAO = null;
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityAspectService#setSpecialityAspectDAO(com.shufe.dao.system.baseinfo.SpecialityAspectDAO)
     */
    public void setSpecialityAspectDAO(SpecialityAspectDAO specialityDirectionDAO) {
        this.specialityDirectionDAO = specialityDirectionDAO;
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityAspectService#getSpecialityAspect(java.lang.Long)
     */
    public SpecialityAspect getSpecialityAspect(Long id) {
        try {
            return specialityDirectionDAO.getSpecialityAspect(id);
        } catch (ObjectRetrievalFailureException e) {
            return null;
        }
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityAspectService#getSpecialityAspects()
     */
    public List getSpecialityAspects() {
        return specialityDirectionDAO.getSpecialityAspects();
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityAspectService#getSpecialityAspects(com.shufe.model.system.baseinfo.SpecialityAspect,
     *      int, int)
     */
    public Pagination getSpecialityAspects(SpecialityAspect aspect, int pageNo, int pageSize) {
        return specialityDirectionDAO.getSpecialityAspects(aspect, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityAspectService#getAllSpecialityAspects(com.shufe.model.system.baseinfo.SpecialityAspect,
     *      int, int)
     */
    public Pagination getAllSpecialityAspects(SpecialityAspect aspect, int pageNo, int pageSize) {
        return specialityDirectionDAO.getAllSpecialityAspects(aspect, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityAspectService#getSpecialityAspects(com.shufe.model.system.baseinfo.SpecialityAspect)
     */
    public List getSpecialityAspects(SpecialityAspect specialityDirection) {
        return specialityDirectionDAO.getSpecialityAspects(specialityDirection);
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityAspectService#removeSpecialityAspect(java.lang.String)
     */
    public void removeSpecialityAspect(Long id) {
        if (null == id)
            return;
        try {
            specialityDirectionDAO.removeSpecialityAspect(id);
        } catch (PojoNotExistException e) {
            return;
        }
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityAspectService#saveSpecialityAspect(com.shufe.model.system.baseinfo.SpecialityAspect)
     */
    public void saveOrUpdate(SpecialityAspect specialityAspect) throws PojoExistException {
        try {
            if (!specialityAspect.isPO()) {
                specialityAspect.setCreateAt(new Date(System.currentTimeMillis()));
            }
            specialityAspect.setModifyAt(new Date(System.currentTimeMillis()));
            specialityDirectionDAO.saveOrUpdate(specialityAspect);
        } catch (DataIntegrityViolationException e) {
            throw new PojoExistException("specialityDirection already exits:" + specialityAspect);
        } catch (ConstraintViolationException e) {
            throw new PojoExistException("specialityDirection already exits:" + specialityAspect);
        }
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.SpecialityAspectService#getSpecialityAspects(java.lang.String)
     */
    public List getSpecialityAspects(Long specialityId) {
        SpecialityAspect aspect = new SpecialityAspect();
        Speciality speciality = new Speciality();
        speciality.setId(specialityId);
        aspect.setSpeciality(speciality);
        return getSpecialityAspects(aspect);
    }
    
    /*
     * （非 Javadoc）
     * 
     * @see com.shufe.service.system.baseinfo.SpecialityAspectService#listSecondSpecialityAspect()
     */
    public List listSecondSpecialityAspect() {
        return utilDao.searchNamedQuery("listSecondSpecialityAspect", (Map) null);
    }
    
    public List listSecondSpecialityAspect(String departmentIds) {
        Map params = new HashMap();
        params.put("departmentIds", SeqStringUtil.transformToLong(departmentIds));
        return utilDao.searchNamedQuery("listSecondSpecialityAspectWithDepartmentIds", params);
    }
    
    public Collection getSpecialityAspects(MajorType majorType) {
        return getSpecialityAspects(Boolean.TRUE, new MajorType(MajorType.FIRST));
    }
    
    public Collection getSpecialityAspects(Boolean state, MajorType majorType) {
        EntityQuery query = new EntityQuery(SpecialityAspect.class, "aspect");
        query.add(new Condition("aspect.speciality.state = :state", null == state ? Boolean.TRUE
                : state));
        query.add(new Condition("aspect.speciality.majorType.id = (:majorTypeId)",
                (null == majorType || null == majorType.getId()) ? MajorType.FIRST : majorType
                        .getId()));
        return utilService.search(query);
    }
}

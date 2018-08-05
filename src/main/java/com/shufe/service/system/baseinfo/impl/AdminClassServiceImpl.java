//$Id: AdminClassServiceImpl.java,v 1.4 2006/12/04 12:59:30 yd Exp $
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
import java.util.Collections;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.dao.system.baseinfo.AdminClassDAO;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.BasicService;
import com.shufe.service.system.baseinfo.AdminClassService;

public class AdminClassServiceImpl extends BasicService implements AdminClassService {
    
    private AdminClassDAO adminClassDAO = null;
    
    /**
     * @see com.shufe.service.system.baseinfo.AdminClassService#setAdminClassDAO(com.shufe.dao.system.baseinfo.AdminClassDAO)
     */
    public void setAdminClassDAO(AdminClassDAO adminClassDAO) {
        this.adminClassDAO = adminClassDAO;
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.AdminClassService#getAdminClass(java.lang.Long)
     */
    public AdminClass getAdminClass(Long id) {
        try {
            return adminClassDAO.getAdminClass(id);
        } catch (ObjectRetrievalFailureException e) {
            return null;
        }
    }
    
    public AdminClass getAdminClass(String code) {
        EntityQuery query = new EntityQuery(AdminClass.class, "adminClass");
        query.add(new Condition("adminClass.code=:code", code));
        List rs = (List) utilDao.search(query);
        if (rs.isEmpty())
            return null;
        else
            return (AdminClass) rs.get(0);
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.AdminClassService#getAdminClasses(java.lang.String)
     */
    public List getAdminClassesById(String classIdSeq) {
        if (StringUtils.isEmpty(classIdSeq)) {
            return Collections.EMPTY_LIST;
        } else {
            return adminClassDAO.getAdminClassesById(SeqStringUtil.transformToLong(classIdSeq));
        }
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.AdminClassService#getAdminClasses(com.shufe.model.system.baseinfo.AdminClass,
     *      java.lang.String, java.lang.String, int, int)
     */
    public Pagination getAdminClasses(AdminClass adminClass, String stdTypeIdSeq,
            String departIdSeq, int pageNo, int pageSize) {
        return adminClassDAO.getAdminClasses(adminClass, SeqStringUtil
                .transformToLong(stdTypeIdSeq), SeqStringUtil.transformToLong(departIdSeq), pageNo,
                pageSize);
    }
    
    public List getAdminClasses(AdminClass adminClass, String stdTypeIdSeq, String departIdSeq) {
        if (StringUtils.isNotEmpty(departIdSeq) && StringUtils.isNotEmpty(stdTypeIdSeq))
            return adminClassDAO.getAdminClasses(adminClass, SeqStringUtil
                    .transformToLong(stdTypeIdSeq), SeqStringUtil.transformToLong(departIdSeq));
        else
            return Collections.EMPTY_LIST;
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.AdminClassService#getAdminClasss(com.shufe.model.system.baseinfo.AdminClass)
     */
    public List getAdminClasses(AdminClass adminClass) {
        return adminClassDAO.getAdminClasses(adminClass);
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.AdminClassService#removeAdminClass(java.lang.String)
     */
    public void removeAdminClass(Long id) {
        if (null == id)
            return;
        try {
            adminClassDAO.removeAdminClass(id);
        } catch (PojoNotExistException e) {
            return;
        }
    }
    
    /**
     * @see com.shufe.service.system.baseinfo.AdminClassService#saveAdminClass(com.shufe.model.system.baseinfo.AdminClass)
     */
    public void saveOrUpdate(AdminClass adminClass) throws PojoExistException {
        try {
            if (null == adminClass.getState()) {
                adminClass.setState(Boolean.TRUE);
            }
            if (null == adminClass.getCreateAt()) {
                adminClass.setCreateAt(new Date(System.currentTimeMillis()));
            }
            if (null == adminClass.getStdCount()) {
                adminClass.setStdCount(new Integer(0));
            }
            if (null == adminClass.getActualStdCount()) {
                adminClass.setActualStdCount(new Integer(0));
            }
            if (!adminClass.isPO()) {
                adminClass.setCreateAt(new Date(System.currentTimeMillis()));
            }
            EntityUtils.evictEmptyProperty(adminClass);
            adminClass.setModifyAt(new Date(System.currentTimeMillis()));
            adminClassDAO.saveOrUpdate(adminClass);
        } catch (DataIntegrityViolationException e) {
            throw new PojoExistException("adminClass already exits:" + adminClass);
        } catch (ConstraintViolationException e) {
            throw new PojoExistException("adminClass already exits:" + adminClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    /**
     * @see AdminClassService#getAdminClassesExactly(AdminClass)
     */
    public List getAdminClassesExactly(AdminClass adminClass) {
        if (ValidEntityPredicate.INSTANCE.evaluate(adminClass.getDepartment()))
            return Collections.EMPTY_LIST;
        return adminClassDAO.getAdminClassesExactly(adminClass);
    }
    
    public int updateActualStdCount(Long adminClassId) {
        return adminClassDAO.updateActualStdCount(adminClassId);
    }
    
    public int updateStdCount(Long adminClassId) {
        return adminClassDAO.updateStdCount(adminClassId);
    }
    
    public void batchUpdateStdCountOfClass(String adminClassIdSeq) {
        Long[] adminClassIds = SeqStringUtil.transformToLong(adminClassIdSeq);
        if (null != adminClassIds) {
            for (int i = 0; i < adminClassIds.length; i++) {
                adminClassDAO.updateActualStdCount(adminClassIds[i]);
                adminClassDAO.updateStdCount(adminClassIds[i]);
            }
        }
    }
    
    public void batchUpdateStdCountOfClass(Long[] adminClassIds) {
        if (null != adminClassIds) {
            for (int i = 0; i < adminClassIds.length; i++) {
                adminClassDAO.updateActualStdCount(adminClassIds[i]);
                adminClassDAO.updateStdCount(adminClassIds[i]);
            }
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.system.baseinfo.AdminClassService#getAdminClassIdsByAdminClass(com.shufe.model.system.baseinfo.AdminClass,
     *      java.lang.String, java.lang.String)
     */
    public List getAdminClassIds(AdminClass adminClass, String departmentIds, String stdTypeIds) {
        return adminClassDAO.getAdminClassIds(adminClass, departmentIds, stdTypeIds);
    }
    
}

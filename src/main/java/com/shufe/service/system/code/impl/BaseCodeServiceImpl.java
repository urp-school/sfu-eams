//$Id: BaseCodeServiceImpl.java,v 1.4 2007/01/13 06:15:25 duanth Exp $
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
 * chaostone             2005-9-8         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.code.impl;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.BaseCode;
import com.ekingstar.eams.system.basecode.BaseCodeNotDefinedException;
import com.ekingstar.eams.system.basecode.Coder;
import com.ekingstar.eams.system.basecode.dao.BaseCodeDao;
import com.ekingstar.eams.system.basecode.service.BaseCodeService;
import com.shufe.service.BasicService;

/**
 * 基础代码数据服务实现类
 * 
 * @author chaostone 2005-9-8
 */
public class BaseCodeServiceImpl extends BasicService implements BaseCodeService {
    
    /**
     * 数据存取接口
     */
    BaseCodeDao baseCodeDao = null;
    
    /**
     * @see BaseCodeService#getAllCode()
     */
    public List getCoders() {
        return baseCodeDao.getCoders();
    }
    
    /**
     * @see BaseCodeService#getCode(String, Integer)
     */
    public BaseCode getCode(String codeName, Long codeId) {
        try {
            BaseCode code = null;
            if (!StringUtils.contains(codeName, '.')) {
                code = baseCodeDao.getCode(getCodeClass(codeName), codeId);
            } else {
                code = baseCodeDao.getCode(Class.forName(codeName), codeId);
            }
            return code;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * @see com.shufe.service.system.code.BaseCodeService#getCode(java.lang.Class, java.lang.String)
     */
    public BaseCode getCode(Class codeClass, Long codeId) {
        try {
            return baseCodeDao.getCode(codeClass, codeId);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * @see com.shufe.service.system.code.BaseCodeService#getCodes(java.lang.Class)
     */
    public List getCodes(Class codeClass) {
        return baseCodeDao.getCodes(codeClass);
    }
    
    public List getCodes(Class clazz, Long[] ids) {
        EntityQuery entityQuery = new EntityQuery(clazz, "code");
        entityQuery.add(new Condition("code.id in(:ids)", ids));
        return (List) utilDao.search(entityQuery);
    }
    
    /**
     * @see BaseCodeService#removeCode(String, String)
     */
    public void removeCode(String codeName, Long codeId) {
        Class codeClass = getCodeClass(codeName);
        if (null != codeClass) {
            baseCodeDao.removeCode(codeClass, codeId);
        }
    }
    
    public Collection getCodes(BaseCode arg0) {
        return baseCodeDao.getCodes(arg0);
    }
    
    /**
     * @see BaseCodeService#saveCode(BaseCode)
     */
    public void saveOrUpdate(BaseCode code) throws PojoExistException {
        try {
            if (!code.isPO()) {
                code.setCreateAt(new Date(System.currentTimeMillis()));
            }
            code.setModifyAt(new Date(System.currentTimeMillis()));
            baseCodeDao.saveOrUpdate(code);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new PojoExistException("base code already existed!");
        } catch (ConstraintViolationException e) {
            throw new PojoExistException("course already exits:" + code);
        }
    }
    
    /**
     * 检查对应的代码类是否定义，没有定义返回nullClass.
     * 
     * @param codeName
     * @return
     * @throws BaseCodeNotDefinedException
     */
    public Class getCodeClass(String codeName) {
        try {
            EntityQuery query = new EntityQuery(Coder.class, "coder");
            query.add(new Condition("coder.engName=:name", codeName));
            List rs = (List) utilDao.search(query);
            if (1 != rs.size()) {
                throw new BaseCodeNotDefinedException(codeName + " is not defined!");
            } else {
                return Class.forName(codeName);
            }
        } catch (ClassNotFoundException e) {
            throw new BaseCodeNotDefinedException(codeName + " is not defined!");
        }
    }
    
    /**
     * @see BaseCodeService#setBaseCodeDao(BaseCodeDao)
     */
    public void setBaseCodeDao(BaseCodeDao baseCodeDao) {
        this.baseCodeDao = baseCodeDao;
    }
    
    public BaseCode getCode(Class codeClass, String code) {
        throw new RuntimeException();
    }
}

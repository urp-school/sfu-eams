//$Id: CriterionUtils.java,v 1.2 2006/10/16 00:34:25 duanth Exp $
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
 * chaostone             2005-10-27         Created
 *  
 ********************************************************************************/

package com.shufe.dao.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.commons.model.Component;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;

/**
 * 条件查询工具类 主要为简单实体查询提供帮助. 对样例（Example）查询提供了扩展对实体类的标识符和排除非空属性上（包括""和0）提供更进一步的支持， 该工具类多对一关联也提供了查询支持.
 * 对于实体类中的属性映射为Component的在原来的Example就提供支持，不用另外写给予他们属性的查询.
 * 
 * @author chaostone 2005-10-28
 */
public class CriterionUtils {
    
    protected static final Logger logger = LoggerFactory.getLogger(CriterionUtils.class);
    
    public static void addCriterionsFor(Criteria criteria, List criterions) {
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            criteria.add((Criterion) iter.next());
        }
    }
    
    public static List getEntityCriterions(Object entity) {
        return getEntityCriterions("", entity, null, MatchMode.ANYWHERE, true);
    }
    
    public static List getEntityCriterions(Object entity, boolean ignoreZero) {
        return getEntityCriterions("", entity, null, MatchMode.ANYWHERE, ignoreZero);
    }
    
    public static List getEntityCriterions(Object entity, String[] excludePropertes) {
        return getEntityCriterions("", entity, excludePropertes, MatchMode.ANYWHERE, true);
    }
    
    public static List getEntityCriterions(String nestedName, Object entity) {
        return getEntityCriterions(nestedName, entity, null, MatchMode.ANYWHERE, true);
    }
    
    public static List getEntityCriterions(String nestedName, Object entity,
            String[] excludePropertes) {
        return getEntityCriterions(nestedName, entity, excludePropertes, MatchMode.ANYWHERE, true);
    }
    
    public static Example getExampleCriterion(Object entity) {
        return getExampleCriterion(entity, null, MatchMode.ANYWHERE);
    }
    
    public static Example getExampleCriterion(Object entity, String[] excludePropertes,
            MatchMode mode) {
        Example example = Example.create(entity)
                .setPropertySelector(new NotEmptyPropertySelector());
        if (null != mode) {
            example.enableLike(mode);
        }
        if (null != excludePropertes) {
            for (int i = 0; i < excludePropertes.length; i++) {
                example.excludeProperty(excludePropertes[i]);
            }
        }
        return example;
    }
    
    /**
     * 获得实体类的属性和多对一属性（主键）的查询条件. （包括外键和组件以及组件内的外键），字符串类型可以采用模糊查询.
     * 
     * @param entity
     * @param excludePropertes
     * @param mode
     * @return
     */
    public static List getEntityCriterions(String nestedName, Object entity,
            String[] excludePropertes, MatchMode mode, boolean ignoreZero) {
        if (null == entity) {
            return Collections.EMPTY_LIST;
        }
        List criterions = new ArrayList();
        
        BeanMap map = new BeanMap(entity);
        
        Set keySet = map.keySet();
        
        Collection properties = null;
        if (null != excludePropertes) {
            properties = CollectionUtils.subtract(keySet, Arrays.asList(excludePropertes));
        } else {
            List proList = new ArrayList();
            proList.addAll(keySet);
            properties = proList;
        }
        properties.remove("class");
        for (Iterator iter = properties.iterator(); iter.hasNext();) {
            String propertyName = (String) iter.next();
            if (!PropertyUtils.isWriteable(entity, propertyName)) {
                continue;
            }
            Object value = map.get(propertyName);
            addCriterion(nestedName, entity, excludePropertes, propertyName, value, criterions,
                    mode, ignoreZero);
        }
        return criterions;
    }
    
    public static List getEqCriterions(Object entity, String[] properties) {
        List criterions = new ArrayList();
        BeanMap map = new BeanMap(entity);
        for (int i = 0; i < properties.length; i++) {
            criterions.add(Restrictions.eq(properties[i], map.get(properties[i])));
        }
        return criterions;
    }
    
    public static List getForeignerCriterions(Object entity) {
        BeanMap map = new BeanMap(entity);
        return getForeignerCriterions(entity, map.keySet());
    }
    
    public static List getForeignerCriterions(Object entity, Collection properties) {
        List criterions = new ArrayList();
        BeanMap map = new BeanMap(entity);
        
        for (Iterator iter = properties.iterator(); iter.hasNext();) {
            String propertyName = (String) iter.next();
            Object foreigner = map.get(propertyName);
            
            if (foreigner instanceof Entity) {
                BeanMap foreignerMap = new BeanMap(foreigner);
                Object foreignKey = foreignerMap.get(((Entity) foreigner).key());
                // 该值不能为空，而且要么不是String类型，要么是不空String类型变量.
                if (ValidEntityKeyPredicate.getInstance().evaluate(foreignKey)) {
                    // 在查询中添加该键值.
                    criterions.add(Restrictions.eq(propertyName + "." + ((Entity) foreigner).key(),
                            foreignKey));
                }
            }
        }
        return criterions;
    }
    
    public static List getForeignerCriterions(Object entity, String[] properties) {
        return getForeignerCriterions(entity, Arrays.asList(properties));
    }
    
    public static List getLikeCriterions(Object entity, String[] Properties) {
        return getLikeCriterions(entity, Properties, MatchMode.ANYWHERE);
    }
    
    /**
     * 返回非空字符串属性的like条件列表
     * 
     * @param entity
     * @param properties
     * @param mode
     * @return
     */
    public static List getLikeCriterions(Object entity, String[] properties, MatchMode mode) {
        List criterions = new ArrayList();
        BeanMap map = new BeanMap(entity);
        for (int i = 0; i < properties.length; i++) {
            Object value = map.get(properties[i]);
            if ((value instanceof String) && (StringUtils.isNotEmpty((String) value))) {
                criterions.add(Restrictions.like(properties[i], (String) value, mode));
            }
        }
        return criterions;
    }
    
    /**
     * 返回默认采取MatchMode.ANYWHERE的实体参数map
     * 
     * @param entity
     * @return
     */
    public static Map getParamsMap(Entity entity) {
        if (null == entity) {
            return Collections.EMPTY_MAP;
        }
        return getParamsMap(entity, MatchMode.ANYWHERE);
    }
    
    /**
     * 将一个实体类中的非空属性及其值.<br>
     * 实体类中的component的属性将会级联描述，<br>
     * 其内部的属性完全看作没有component包装一样，但结果map中的名称是component.attr形式的.
     * 
     * @param entity,传递null,返回空map.
     * @param mode
     *            若含有非空字符串，采用的like策略
     * @return
     */
    public static Map getParamsMap(Entity entity, MatchMode mode) {
        if (null == entity) {
            return Collections.EMPTY_MAP;
        }
        Map datas = new HashMap();
        String attr = "";
        try {
            Map beanMap = PropertyUtils.describe(entity);
            
            for (Iterator iter = beanMap.keySet().iterator(); iter.hasNext();) {
                attr = (String) iter.next();
                Object value = PropertyUtils.getProperty(entity, attr);
                if (value == null) {
                    continue;
                } else {
                    addTrivialAttr(datas, attr, value, mode);
                    if (value instanceof Entity) {
                        String key = ((Entity) value).key();
                        value = PropertyUtils.getProperty(entity, attr + "." + key);
                        if (ValidEntityKeyPredicate.getInstance().evaluate(value)) {
                            datas.put(attr + "." + key, value);
                        }
                    }
                }
            }
            return datas;
        } catch (Exception e) {
            logger.error("[converToMap]:error occur in converToMap of bean" + entity
                    + "with attr named " + attr);
            e.printStackTrace();
        }
        return Collections.EMPTY_MAP;
    }
    
    static Criterion eqCriterion(String name, Object value) {
        logger.debug("[CriterionUtils]:" + name + ":" + value);
        return Restrictions.eq(name, value);
    }
    
    static Criterion likeCriterion(String name, String value, MatchMode mode) {
        logger.debug("[CriterionUtils]:" + name + ":" + value);
        return Restrictions.like(name, value, mode);
    }
    
    /**
     * 添加一个查询条件
     * 
     * @param entity
     * @param excludePropertes
     * @param path
     * @param value
     * @param criterions
     * @param mode
     */
    private static void addCriterion(String nestedName, Object entity, String[] excludePropertes,
            String path, Object value, List criterions, MatchMode mode, boolean ignoreZero) {
        
        if (null == value) {
            return;
        }
        addPrimativeCriterion(nestedName + path, value, criterions, ignoreZero);
        
        if (value instanceof String) {
            if (StringUtils.isNotEmpty((String) value)) {
                criterions.add(likeCriterion(nestedName + path, (String) value, mode));
            }
        } else if (value instanceof Entity) {
            BeanMap foreignerMap = new BeanMap(value);
            Object foreignKey = foreignerMap.get(((Entity) value).key());
            // 该值不能为空，而且要么不是String类型，要么是不空String类型变量.
            if (ValidEntityKeyPredicate.getInstance().evaluate(foreignKey)) {
                // 在查询中添加该键值.
                criterions.add(eqCriterion(nestedName + path + "." + ((Entity) value).key(),
                        foreignKey));
            }
        } else if (value instanceof Component) {
            criterions.addAll(getComponentCriterions(nestedName, entity, path, excludePropertes,
                    mode, ignoreZero));
        }
    }
    
    /**
     * 针对内建数据类型和日期类型添加查询条件 因为很多从页面回传的""字符串在转化成数字时为0,所以这里忽略0
     * 
     * @param name
     * @param value
     * @param criterions
     */
    private static void addPrimativeCriterion(String name, Object value, List criterions,
            boolean ignoreZero) {
        Criterion criterion = null;
        if (value instanceof Number) {
            if (ignoreZero) {
                if (0 != ((Number) value).intValue()) {
                    criterion = eqCriterion(name, value);
                }
            } else {
                criterion = eqCriterion(name, value);
            }
        }
        if ((value instanceof Character) || (value instanceof Boolean)) {
            criterion = eqCriterion(name, value);
        }
        if ((value instanceof Date)) {
            criterion = eqCriterion(name, value);
        }
        if (null != criterion) {
            criterions.add(criterion);
        }
    }
    
    /**
     * 为converToMap使用的私有方法
     * 
     * @param datas
     * @param name
     * @param value
     * @param mode
     */
    private static void addTrivialAttr(Map datas, String name, Object value, MatchMode mode) {
        if (value instanceof Number && ((Number) value).intValue() != 0) {
            datas.put(name, value);
        }
        if (value instanceof String && StringUtils.isNotBlank((String) value)) {
            String strValue = (String) value;
            if (mode.equals(MatchMode.ANYWHERE)) {
                value = "%" + strValue + "%";
            } else if (mode.equals(MatchMode.START)) {
                value = strValue + "%";
            } else if (mode.equals(MatchMode.END)) {
                value = "%" + strValue;
            }
            datas.put(name, value);
        }
        if (value instanceof Component) {
            datas.putAll(converToMap(name, (Component) value, mode));
        }
        if (value instanceof Entity) {
            try {
                String key = ((Entity) value).key();
                value = PropertyUtils.getProperty(value, key);
                if (ValidEntityKeyPredicate.getInstance().evaluate(value)) {
                    datas.put(name + "." + key, value);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    
    private static Map converToMap(String prefix, Component component, MatchMode mode) {
        if (null == component) {
            return Collections.EMPTY_MAP;
        }
        Map datas = new HashMap();
        String attr = "";
        try {
            Map beanMap = PropertyUtils.describe(component);
            for (Iterator iter = beanMap.keySet().iterator(); iter.hasNext();) {
                attr = (String) iter.next();
                Object value = PropertyUtils.getProperty(component, attr);
                if (value == null) {
                    continue;
                } else {
                    addTrivialAttr(datas, prefix + "." + attr, value, mode);
                }
            }
            return datas;
            
        } catch (Exception e) {
            System.out.println("[converToMap]:error occur in converToMap of component" + component
                    + "with attr named " + attr);
            e.printStackTrace();
            
        }
        return Collections.EMPTY_MAP;
    }
    
    /**
     * 返回实体类内部组件的查询条件
     * 
     * @param entity
     * @param property
     *            组件在实体类中的名称允许级联例如outcomponent.innercomponent
     * @param excludePropertes
     *            每个元素形式如entityProperty.componentProperty
     * @param enableLike
     * @return
     */
    private static List getComponentCriterions(String nestedName, Object entity, String property,
            String[] excludePropertes, MatchMode mode, boolean ignoreZero) {
        List criterions = new ArrayList();
        
        Component component = null;
        try {
            component = (Component) PropertyUtils.getProperty(entity, property);
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
        if (null == component) {
            return Collections.EMPTY_LIST;
        }
        BeanMap map = new BeanMap(component);
        Set properties = map.keySet();
        Set excludeSet = null;
        if (null != excludePropertes) {
            excludeSet = new HashSet();
            excludeSet.addAll(Arrays.asList(excludePropertes));
        } else {
            excludeSet = Collections.EMPTY_SET;
        }
        
        for (Iterator iter = properties.iterator(); iter.hasNext();) {
            String propertyName = (String) iter.next();
            String cascadeName = property + "." + propertyName;
            if (excludeSet.contains(cascadeName) || StringUtils.equals("class", propertyName)) {
                continue;
            }
            if (!PropertyUtils.isWriteable(component, propertyName)) {
                continue;
            }
            
            Object value = map.get(propertyName);
            addCriterion(nestedName, entity, excludePropertes, cascadeName, value, criterions,
                    mode, ignoreZero);
        }
        return criterions;
    }
    
}

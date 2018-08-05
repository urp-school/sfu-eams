//$Id: RequestUtil.java,v 1.30 2007/01/04 00:53:58 duanth Exp $
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
 * chaostone             2005-11-1         Created
 *  
 ********************************************************************************/

package com.shufe.util;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.Model;
import com.ekingstar.commons.model.type.EntityType;
import com.ekingstar.commons.utils.web.RequestUtils;

public class RequestUtil {

	protected static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);
	
	/**
	 * 从request中获取指定类的对象
	 * 
	 * @deprecated
	 * @param request
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Object populate(HttpServletRequest request, Class clazz, String name) {
		return populate(request, clazz, name, false);
	}

	public static Object populate(HttpServletRequest request, Class clazz, boolean emptyIsNull) {
		return populate(request, clazz, EntityUtils.getCommandName(clazz), emptyIsNull);
	}

	public static Object populate(HttpServletRequest request, Class clazz, String name,
			boolean emptyIsNull) {
		Object obj = null;
		try {
			if (clazz.isInterface()) {
				EntityType type = Model.context.getEntityType(clazz);
				obj = type.newInstance();
			} else {
				obj = clazz.newInstance();
			}
		} catch (Exception e) {
			throw new RuntimeException("[RequestUtil.populate]: error in in initialize " + clazz);
		}
		return populate(request, obj, name, emptyIsNull);
	}

	/**
	 * @param request
	 * @param object
	 * @param emptyIsNull
	 * @return
	 */
	public static Object populate(HttpServletRequest request, Object object, boolean emptyIsNull) {
		populate(request, object, EntityUtils.getCommandName(object), emptyIsNull);
		return object;
	}

	/**
	 * 按照名称前缀，将request的参数populate到一个既定对象上去<br>
	 * 基本类型里除了boolean之外，全部对""对象赋予了默认值.
	 * 
	 * @param request
	 * @param object
	 * @param name
	 * @return
	 */
	public static Object populate(HttpServletRequest request, Object object, String name) {
		populate(request, object, name, false);
		return object;
	}

	public static Object populate(HttpServletRequest request, Object object, String name,
			boolean emptyIsNull) {
		if (emptyIsNull) {
			ConvertUtils.register(new IntegerConverter(null), Integer.class);
			ConvertUtils.register(new LongConverter(null), Long.class);
			ConvertUtils.register(new FloatConverter(null), Float.class);
			ConvertUtils.register(new DoubleConverter(null), Double.class);
		} else {
			ConvertUtils.register(new IntegerConverter(new Integer(0)), Integer.class);
			ConvertUtils.register(new LongConverter(new Long(0)), Long.class);
			ConvertUtils.register(new FloatConverter(new Float(0.0)), Float.class);
			ConvertUtils.register(new DoubleConverter(new Double(0.0)), Double.class);
		}
		Map params = RequestUtils.getParams(request, name);
		for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
			String attr = (String) iter.next();
			// 对于实体类的主键采取null
			String strValue = (String) params.get(attr);
			if (attr.equals("id") && StringUtils.isEmpty(strValue)) {
				try {
					PropertyUtils.setProperty(object, attr, null);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				populateValue(object, attr, strValue);
			}
		}
		// 注册回有默认值的转换器
		if (!emptyIsNull) {
			ConvertUtils.register(new IntegerConverter(null), Integer.class);
			ConvertUtils.register(new LongConverter(null), Long.class);
			ConvertUtils.register(new FloatConverter(null), Float.class);
			ConvertUtils.register(new DoubleConverter(null), Double.class);
		}
		return object;
	}

	public static void populateValue(Object object, String attr, Object value) {
		try {
			BeanUtils.copyProperty(object, attr, value);
		} catch (Exception e) {
			// e.printStackTrace();
			// 为没有实例化的属性进行实例化
			String parentAttr = StringUtils.substring(attr, 0, attr.lastIndexOf("."));
			Class type = null;
			while (type == null && StringUtils.isNotEmpty(parentAttr)) {
				try {
					type = PropertyUtils.getPropertyType(object, parentAttr);
					if (null == type) {
						throw new RuntimeException("illegal property :" + attr);
					}
					if (type.isInterface()) {
						EntityType entityType = Model.context.getEntityType(type.getName());
						if (null == entityType) {
							logger.error("cannot find default classType for" + type);
							break;
						}
						type = entityType.getEntityClass();
					}
				} catch (Exception ee) {
					parentAttr = StringUtils.substring(parentAttr, 0, parentAttr.lastIndexOf("."));
				}
			}
			if (null != type) {
				try {
					PropertyUtils.setProperty(object, parentAttr, type.newInstance());
					BeanUtils.copyProperty(object, attr, value);
				} catch (Exception ee) {

				}
			}
		}
	}
}
